package jsonlenium.build.attr

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType

@AttributeName("remark")
@AttributeType(String.class)
class Remark extends AttributeBase {
    @Override
    void read(Object value, String parent, List<String> pathList, Closure closure) {
        // nothing to do
    }
}
