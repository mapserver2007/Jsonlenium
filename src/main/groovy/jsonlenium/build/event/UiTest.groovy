package jsonlenium.build.event


import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import groovyx.net.http.URIBuilder
import jsonlenium.annotation.EventType
import jsonlenium.annotation.TestCase
import jsonlenium.build.attr.TestSuite
import jsonlenium.build.util.EventUtil
import jsonlenium.constant.Event

@TestCase
@EventType(Event.UI_TEST)
class UiTest extends EventBase {
    @Override
    void createEvent(Map<String, List<Map<String, ?>>> data, URIBuilder uri, @ClosureParams(value = SimpleType, options = ["java.util.List", "java.lang.Integer", "java.lang.String"]) Closure closure) {
        if (data.testcase == null) {
            return
        }

        def testsuite = data.testsuite as Map<String, Integer>
        def size = data.testcase.size()

        data.testcase.eachWithIndex { elem, index ->
            if (elem.selector == null && elem.xpath == null) {
                return
            }

            if (elem.case_sensitive == null) {
                elem.case_sensitive = true
            }

            EventUtil.createEvent(elem) { ... args ->
                String title = ""
                List<Map<String, ?>> events = args[0]
                int weight = args[1]
                def otherEvents = []

                if (data.title != null) {
                    title <<= "[${data.title}] "
                    if (testsuite.size > 1) {
                        title <<= "[${TestSuite.toString().toLowerCase()}(${testsuite.index + 1}/${testsuite.size})] "
                    } else {
                        title <<= "[${TestSuite.toString().toLowerCase()}] "
                    }
                }

                if (size > 1) {
                    title <<= "[${UiTest.toString().toLowerCase()}(${index + 1}/${size})] "
                } else {
                    title <<= "[${UiTest.toString().toLowerCase()}] "
                }

                events.each { event ->
                    def path = event.selector ?: event.xpath
                    if (path != null) {
                        title <<= "[${path}] "
                    }
                }

                if (args.size() > 3) {
                    otherEvents << args[2]
                    title <<= "${args[3]} "
                }

                closure.call(events + otherEvents, weight, title.trim())
            }
        }
    }
}
