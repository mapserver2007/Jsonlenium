package jsonlenium.build.util

import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.EventType

class AnnotationUtil {
    static void forClasses() {
        Class.metaClass.toString() {
            def attribute = delegate.annotations.find { it instanceof AttributeName }
            if (attribute != null) {
                return ((AttributeName)attribute).value()
            }

            def event = delegate.annotations.find { it instanceof EventType }
            if (event != null) {
                return ((EventType)event).value().toString()
            }

            delegate
        }
    }
}
