package jsonlenium.build.attr

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

abstract class AttributeBase {
    boolean next
    abstract void read(Object value, String parent, List<String> pathList, @ClosureParams(value = SimpleType, options = ["java.lang.String", "java.util.List"]) Closure closure)
}
