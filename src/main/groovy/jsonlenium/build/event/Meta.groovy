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
@EventType(Event.META)
class Meta extends EventBase {
    @Override
    void createEvent(Map<String, List<Map<String, ?>>> data, URIBuilder uri, @ClosureParams(value = SimpleType, options = ["java.util.List", "java.lang.Integer", "java.lang.String"]) Closure closure) {
        if (data.meta == null) {
            return
        }

        def testsuite = data.testsuite as Map<String, Integer>
        def size = data.meta.size()

        EventUtil.createMetaEvent(data) { event, weight, index ->
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
                title <<= "[${Meta.toString().toLowerCase()}(${index + 1}/${size})] "
            } else {
                title <<= "[${Meta.toString().toLowerCase()}] "
            }

            title <<= "[${event.selector}]"

            closure.call([event], weight, title)
        }
    }
}
