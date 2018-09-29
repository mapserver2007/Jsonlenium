package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.annotation.AnyRequired
import jsonlenium.util.Message

@AttributeName("expect")
@AttributeType(String.class)
@AnyRequired(key="expectation", target=["testcase", "click", "meta", "catalyst", "k3c", "prevent_default"])
class Expect extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestCase.toString() &&
            parent != Catalyst.toString() &&
            parent != K3C.toString() &&
            parent != Meta.toString() &&
            parent != Click.toString() &&
            parent != PreventDefault.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [Expect.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }
    }
}
