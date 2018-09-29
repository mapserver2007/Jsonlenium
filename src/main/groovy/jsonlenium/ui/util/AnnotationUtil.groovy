package jsonlenium.ui.util

import jsonlenium.annotation.AssertionType

class AnnotationUtil {
    static void forClasses() {
        Class.metaClass.toString() {
            def assertion = delegate.annotations.find { it instanceof AssertionType }
            if (assertion != null) {
                return ((AssertionType)assertion).value().toString()
            }

            delegate
        }
    }
}
