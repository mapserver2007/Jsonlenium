package jsonlenium.build.event

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import groovyx.net.http.URIBuilder

abstract class EventBase {
    abstract void createEvent(Map<String, List<Map<String, ?>>> data,
                              URIBuilder uri,
                              @ClosureParams(value = SimpleType, options = ["java.util.List", "java.lang.Integer", "java.lang.String"]) Closure closure)
}
