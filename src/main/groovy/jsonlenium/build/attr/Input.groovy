package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.util.Message

@AttributeName("input")
@AttributeType(List.class)
class Input extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestCase.toString() &&
            parent != Click.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [Input.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }

        if (value instanceof List && (value as List).empty) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_EMPTY_ELEMENT_ERROR.toString(), ["\$." + (pathList.empty ? "" : "${pathList.join('.')}.") + Input.toString()])
            next = false
            return
        }

        next = true
    }
}
