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
@EventType(Event.K3C)
class K3C extends EventBase {
    @Override
    void createEvent(Map<String, List<Map<String, ?>>> data, URIBuilder uri, @ClosureParams(value = SimpleType, options = ["java.util.List", "java.lang.Integer", "java.lang.String"]) Closure closure) {
        if (data.k3c == null) {
            return
        }

        def testsuite = data.testsuite as Map<String, Integer>
        def size = data.k3c.size()

        EventUtil.createK3CEvent(data) { event, weight, index ->
            def title = ""

            if (data.title != null) {
                title <<= "[${data.title}] "
                if (testsuite.size > 1) {
                    title <<= "[${TestSuite.toString().toLowerCase()}(${testsuite.index + 1}/${testsuite.size})] "
                } else {
                    title <<= "[${TestSuite.toString().toLowerCase()}] "
                }
            }

            if (size > 1) {
                title <<= "[${K3C.toString().toLowerCase()}(${index + 1}/${size})] "
            } else {
                title <<= "[${K3C.toString().toLowerCase()}] "
            }

            title <<= "[${event.attr}]"

            closure.call([event], weight, title)
        }
    }
}
