package jsonlenium.annotation

import jsonlenium.constant.Operation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface OperationType {
    Operation[] value()
}
