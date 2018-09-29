package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.util.Message

@AttributeName("prevent_default")
@AttributeType(Map.class)
class PreventDefault extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestCase.toString() &&
            parent != Click.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [PreventDefault.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            next = false
            return
        }

        if (!value.any()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_EMPTY_ELEMENT_ERROR.toString(), ["\$." + (pathList.empty ? "" : "${pathList.join('.')}.") + PreventDefault.toString()])
            next = false
            return
        }

        next = true
    }
}
