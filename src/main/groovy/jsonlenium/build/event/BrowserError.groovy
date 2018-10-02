package jsonlenium.build.event

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import groovyx.net.http.URIBuilder
import jsonlenium.annotation.EventType
import jsonlenium.annotation.TestCase
import jsonlenium.build.attr.TestSuite
import jsonlenium.constant.Event
import jsonlenium.constant.EventWeight

@TestCase
@EventType(Event.BROWSER_ERROR)
class BrowserError extends EventBase {
    @Override
    void createEvent(Map<String, List<Map<String, ?>>> data, URIBuilder uri, @ClosureParams(value = SimpleType, options = ["java.util.List", "java.lang.Integer", "java.lang.String"]) Closure closure) {
        if (data.browsererror == null || data.browsererror == false) {
            return
        }

        def testsuite = data.testsuite as Map<String, Integer>
        def event = [
            'url': uri.toString(),
            'event': Event.BROWSER_ERROR
        ]
        def title = ""

        if (data.title != null) {
            title <<= "[${data.title}] "
            if (testsuite.size > 1) {
                title <<= "[${TestSuite.toString().toLowerCase()}(${testsuite.index + 1}/${testsuite.size})] "
            } else {
                title <<= "[${TestSuite.toString().toLowerCase()}] "
            }
        }

        title <<= "[${BrowserError.toString().toLowerCase()}]"

        closure.call([event], EventWeight.BROWSER_ERROR.value(), title)
    }
}
