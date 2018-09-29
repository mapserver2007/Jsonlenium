package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.annotation.Required
import jsonlenium.util.Message

@Required
@AttributeName("testsuite")
@AttributeType(List.class)
class TestSuite extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != null) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [TestSuite.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            next = false
            return
        }

        if (value instanceof List && (value as List).empty) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_EMPTY_ELEMENT_ERROR.toString(), ["\$." + (pathList.empty ? "" : "${pathList.join('.')}.") + TestSuite.toString()])
            next = false
            return
        }

        next = true
    }
}
