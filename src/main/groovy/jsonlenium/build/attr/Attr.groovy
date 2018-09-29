package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.util.Message

@AttributeName("attr")
@AttributeType(String.class)
class Attr extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestCase.toString() &&
            parent != Click.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [Attr.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }
    }
}
