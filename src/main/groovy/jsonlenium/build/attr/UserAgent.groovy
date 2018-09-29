package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.util.Message

@AttributeName("useragent")
@AttributeType(String.class)
class UserAgent extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestSuite.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [UserAgent.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }
    }
}
