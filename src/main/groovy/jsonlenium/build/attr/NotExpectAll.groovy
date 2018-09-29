package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.annotation.AnyRequired
import jsonlenium.util.Message

@AttributeName("not_expect_all")
@AttributeType(String.class)
@AnyRequired(key="expectation", target=["testcase", "click", "prevent_default"])
class NotExpectAll extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestCase.toString() &&
            parent != Click.toString() &&
            parent != PreventDefault.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [NotExpectAll.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }
    }
}
