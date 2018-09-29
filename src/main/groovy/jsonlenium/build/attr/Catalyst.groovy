package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.util.Message

@AttributeName("catalyst")
@AttributeType(List.class)
class Catalyst extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestSuite.toString() && parent != Click.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [Catalyst.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            next = false
            return
        }

        if (value instanceof List && (value as List).empty) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_EMPTY_ELEMENT_ERROR.toString(), ["\$." + (pathList.empty ? "" : "${pathList.join('.')}.") + Catalyst.toString()])
            next = false
            return
        }

        next = true
    }
}
