package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.annotation.Required
import jsonlenium.util.Message

@Required
@AttributeName("url")
@AttributeType(String.class)
class Url extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        if (parent != TestSuite.toString()) {
            closure.call(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), ["\$.${(pathList + [Url.toString()]).join(".")}", Message.COMPILE_INVALID_CAUSE_HIERARCHY.toString()])
            return
        }

        def uri = null
        try {
            uri = new URI(value) // sometimes throwable an exception
        } catch (Exception ignore) {
            // nothing to do
        } finally {
            if (uri?.host == null || uri?.scheme == null) {
                closure.call(Message.COMPILE_INVALID_ATTRIBUTE_VALUE_ERROR.toString(), ["\$." + (pathList.empty ? "" : "${pathList.join('.')}.") + Url.toString(), value])
            }
        }
    }
}
