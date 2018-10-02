package jsonlenium.build.util

import groovy.json.JsonException
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import groovyx.net.http.URIBuilder
import jsonlenium.annotation.TestCase
import jsonlenium.build.attr.CaseSensitive
import jsonlenium.build.attr.TestSuite
import jsonlenium.build.attr.Title
import jsonlenium.build.attr.Url
import jsonlenium.build.event.EventBase
import jsonlenium.build.io.JsonReader
import jsonlenium.constant.EventWeight
import jsonlenium.constant.JsonleniumEnv
import jsonlenium.constant.Path
import jsonlenium.util.Message

class EventFactory {
    private List<Class<EventBase>> eventClasses

    EventFactory() {
        AnnotationUtil.forClasses()
        eventClasses = []
    }

    void loadTestCase() {
        def rootPath = System.getProperty(JsonleniumEnv.USER_DIR)
        def testcaseDir = new File(rootPath + Path.JSONLENIUM_EVENT_CLASS_DIR_PATH)

        if (testcaseDir.isDirectory()) {
            testcaseDir.eachFile { f ->
                if (f.directory) {
                    return
                }

                def classPath = null
                (f.canonicalPath =~ /.+(?:\\|\/)([a-zA-Z0-9_]+)\.(?:groovy|java)$/).each { matchAll, match ->
                    classPath = "${Path.JSONLENIUM_EVENT_PACKAGE_PATH}.${match}"
                }

                def url = new URL[1]
                url[0] = f.toURI().toURL()
                def classLoader = new URLClassLoader(url)
                Class<EventBase> clazz = Class.forName(classPath, false, classLoader)
                if (clazz.name == EventBase.class.name) {
                    return
                }

                def testcase = clazz.getAnnotation(TestCase.class)
                if (testcase != null) {
                    eventClasses << clazz
                }
            }
        }
    }

    void createTestCase(Object json, String fileLocation, @ClosureParams(value = SimpleType, options = ["java.util.List", "java.lang.Boolean"]) Closure closure) {
        def validator = new JsonFileValidator()
        validator.setFileLocation(fileLocation)
        validator.validate(json)

        if (!validator.isValid()) {
            closure.call([[errorMessages: validator.getErrorMessages()]], false)
        } else {
            loadElement(json[TestSuite.toString()]) { Map<String, ?> elem ->
                def uri = new URIBuilder(elem.url)
                elem.src = fileLocation
                elem.title = json[Title.toString()]
                eventClasses.each { clazz ->
                    def instance = clazz.newInstance()
                    def testcases = []
                    instance.createEvent(elem, uri) { events, weight, title ->
                        def cacheClear = CacheClearUtil.isCacheClear(uri, testcases, elem)
                        testcases << [
                            title   : "${title} (${elem.src})",
                            testcase: [
                                'url'       : uri.toString(),
                                'events'    : events,
                                'src'       : elem.src,
                                'useragent' : elem.useragent,
                                'cacheclear': cacheClear,
                                'weight'    : cacheClear ? weight + EventWeight.BROWSER_RESTART.value() : weight
                            ]
                        ]
                        events.each { Map<String, ?> event ->
                            if (event.isRegex && event.isCaseSensitive == false) {
                                closure.call([[errorMessages:
                                                   [sprintf(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), CaseSensitive.toString(), Message.COMPILE_INVALID_CAUSE_CASE_SENSITIVE)]]
                                ], false)
                            }
                        }
                    }
                    if (testcases.any()) {
                        closure.call(testcases, true)
                    }
                }
            }
        }
    }

    void createTestCase(List<File> testFileList, @ClosureParams(value = SimpleType, options = ["java.util.List", "java.lang.Boolean"]) Closure closure) {
        testFileList.each { testFile ->
            try {
                createTestCase(new JsonReader().parse(testFile), testFile.canonicalPath, closure)
            } catch (JsonException e) {
                closure.call(
                    [[errorMessages: [sprintf(Message.COMPILE_INVALID_TESTFILE_FORMAT.toString(), testFile.canonicalPath, e.message)]]], false
                )
            }
        }
    }

    void loadElement(List<Map<String, ?>> testsuite, @ClosureParams(value = SimpleType, options = ["java.util.Map"]) Closure closure) {
        def size = testsuite.size()
        testsuite.eachWithIndex { elem, index ->
            elem.keySet().each {
                if (it == Url.toString()) {
                    return
                }
                def attr = it.toString()
                def testcase = [:]
                testcase[attr] = elem[attr]
                testcase.url = elem.url
                testcase.useragent = elem.useragent
                testcase.testsuite = [
                    'size': size, 'index': index
                ]

                closure.call(testcase)
            }
        }
    }

    boolean concat(List<Map<String, ?>> list1, List<Map<String, ?>> list2) {
        if (list2.any()) {
            if (list1.any()) {
                if ((CacheClearUtil.isCacheClear(new URI(list1.last()['testcase']['url']), new URI(list2.first()['testcase']['url'])))) {
                    list1.last()['testcase']['cacheclear'] = true
                } else if (list2.last()['testcase']['useragent'] != null &&
                    (list1.last()['testcase']['useragent'] != list2.last()['testcase']['useragent'])) {
                    list1.last()['testcase']['cacheclear'] = true
                }
            }
        }
        list1.addAll(list2)
    }

    List<File> getTestFileList(String filePath) {
        def file = new File(filePath)
        def testFileList = new ArrayList<File>()

        if (!file.exists()) {
            return testFileList
        }

        if (file != null) {
            if (file.isDirectory()) {
                file.eachFileRecurse { f ->
                    if (f.file && f.name =~ /.+\.json$/) {
                        testFileList.add(f)
                    }
                }
            } else {
                if (file.file && file.name =~ /.+\.json$/) {
                    testFileList.add(file)
                }
            }
        }

        testFileList
    }
}
