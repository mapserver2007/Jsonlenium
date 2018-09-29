package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.util.Message

@AttributeName("case_sensitive")
@AttributeType(Boolean.class)
class CaseSensitive extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestCase.toString() &&
            parent != Catalyst.toString() &&
            parent != K3C.toString() &&
            parent != Meta.toString() &&
            parent != Click.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [CaseSensitive.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }
    }
}
