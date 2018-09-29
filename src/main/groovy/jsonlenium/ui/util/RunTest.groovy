package jsonlenium.ui.util

import jsonlenium.annotation.OperationType
import jsonlenium.annotation.TestCase
import jsonlenium.build.event.EventBase
import jsonlenium.constant.EventState
import jsonlenium.constant.JsonleniumEnv
import jsonlenium.constant.Operation
import jsonlenium.constant.Path
import jsonlenium.constant.TestState
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import jsonlenium.constant.Event
import jsonlenium.ui.assertion.AssertionBase
import jsonlenium.ui.operator.IOperator
import jsonlenium.util.Message
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.InvalidSelectorException

class RunTest {
    private IOperator web
    private Map<Operation, List<Class<AssertionBase>>> assertionClasses

    RunTest(IOperator web) {
        AnnotationUtil.forClasses()
        this.web = web
        assertionClasses = [:]
        for (def key : Operation.values()) {
            assertionClasses[key] = []
        }
        loadTestCase()
    }

    void loadBrowserEvent(Map<String, ?> testcase, @ClosureParams(value = SimpleType, options = ["java.util.Map"]) Closure closure) {
        testcase.eventState = EventState.START
        testcase.events.each { Map<String, ?> event ->
            if (testcase.eventState == EventState.STOP) {
                return
            }
            event.url = testcase.url
            event.useragent = testcase.useragent
            switch (event.event as Event) {
                case Event.INPUT:
                case Event.CLICK:
                case Event.PREVENT_DEFAULT_CLICK:
                    event.operation = Operation.NONE
                    break
                default:
                    event.operation = Operation.BROWSER
                    break
            }
            closure.call(event)
        }
    }

    void loadParserEvent(Map<String, ?> testcase, @ClosureParams(value = SimpleType, options = ["java.util.Map"]) Closure closure) {
        testcase.eventState = EventState.START
        testcase.events.each { Map<String, ?> event ->
            if (testcase.eventState == EventState.STOP) {
                return
            }
            event.url = testcase.url
            event.useragent = testcase.useragent
            switch (event.event as Event) {
                case Event.INPUT:
                case Event.CLICK:
                case Event.PREVENT_DEFAULT_CLICK:
                    event.operation = Operation.NONE
                    break
                default:
                    event.operation = Operation.PARSER
                    event.retry = testcase.retry > 0
                    break
            }
            event.useragent = testcase.useragent
            closure.call(event)
        }
    }

    void loadTestCase() {
        def rootPath = System.getProperty(JsonleniumEnv.USER_DIR)
        def testcaseDir = new File(rootPath + Path.JSONLENIUM_ASSERTION_CLASS_DIR_PATH)

        if (testcaseDir.isDirectory()) {
            testcaseDir.eachFile { f ->
                if (f.directory) {
                    return
                }

                def classPath = null
                (f.canonicalPath =~ /.+(?:\\|\/)([a-zA-Z0-9_]+)\.(?:groovy|java)$/).each { matchAll, match ->
                    classPath = "${Path.JSONLENIUM_ASSERTION_PACKAGE_PATH}.${match}"
                }

                def url = new URL[1]
                url[0] = f.toURI().toURL()
                def classLoader = new URLClassLoader(url)
                Class<AssertionBase> clazz = Class.forName(classPath, false, classLoader)
                if (clazz.name == EventBase.class.name) {
                    return
                }

                def testcase = clazz.getAnnotation(TestCase)
                if (testcase != null) {
                    def browserType = clazz.getAnnotation(OperationType)
                    if (browserType != null) {
                        browserType.value().each {
                            assertionClasses[it] << clazz
                        }
                    }
                }
            }
        }
    }

    void fireEvent(Map<String, ?> testcase, Closure closure) {
        def event = testcase.event as Event
        web.testcase = testcase
        try {
            switch (event) {
                case Event.CLICK:
                    web.execEvent()
                        .execSelector()
                        .execXpath()
                        .click()
                        .waitForExecJs("return document.readyState == 'complete';")
                    closure.call([state: TestState.OK])
                    break
                case Event.PREVENT_DEFAULT_CLICK:
                    web.execEvent()
                        .execSelector()
                        .execXpath()
                        .preventDefault()
                        .click()
                        .waitForExecJs("return document.readyState == 'complete';")
                    closure.call([state: TestState.OK])
                    break
                case Event.INPUT:
                    web.execSelector()
                        .execXpath()
                        .elements.readAll { elem ->
                        def tag = elem.tag()
                        if (tag == 'input' || tag == 'textarea') {
                            if (elem.isDisplayed()) { // If the element type is hidden, can not set the value
                                elem.value(testcase.value)
                            }
                        }
                    }
                    break
            }
        } catch (InvalidElementStateException | InvalidSelectorException e) {
            def messages = []
            def path = testcase.selector ?: testcase.xpath
            messages << sprintf(Message.ASSERTION_PATH_NOT_MATCHED_ERROR.toString(), testcase.url, path)
            closure.call([
                state: TestState.NG,
                messages: messages
            ])
        }
    }

    void assertion(Map<String, ?> testcase, @ClosureParams(value = SimpleType, options = ["java.util.Map"]) Closure closure) {
        def clazz = assertionClasses[testcase.operation].find { it.toString() == testcase.event }
        if (clazz != null) {
            def instance = clazz.newInstance()
            instance.web = web
            closure.call(instance.assertTest(testcase))
        }
    }
}
