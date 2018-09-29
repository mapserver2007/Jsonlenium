package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.util.Message

@AttributeName("name")
@AttributeType(String.class)
class Name extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != Catalyst.toString() &&
            parent != Meta.toString() &&
            parent != K3C.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [Name.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }
    }
}
