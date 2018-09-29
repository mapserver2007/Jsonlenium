package jsonlenium.ui.util

import jsonlenium.constant.TestState
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

class WebNavigator<T> {
    private Iterable<T> navigator
    private Map<String, ?> testcase

    WebNavigator(Iterable<T> navigator, Map<String, ?> testcase) {
        this.navigator = navigator
        this.testcase = testcase
    }

    boolean empty() {
        navigator.size() == 0
    }

    int size() {
        navigator.size()
    }

    TestState read(@ClosureParams(value = SimpleType, options = ["geb.navigator.Navigator", "java.lang.String"]) Closure<T> closure) {
        evaluate(closure)
    }

    TestState parse(@ClosureParams(value = SimpleType, options = ["org.jsoup.nodes.Element", "java.lang.String"]) Closure<T> closure) {
        evaluate(closure)
    }

    Iterable<T> readAll(@ClosureParams(value = SimpleType, options = ["geb.navigator.Navigator"]) Closure<T> closure) {
        navigator.each {
            closure.call(it)
        }
    }

    private TestState evaluate(Closure<T> closure) {
        TestState state
        List<TestState> states = []

        if (navigator.size() > 0) {
            navigator.each { T elem ->
                states << closure(elem, testcase.attr)
            }
        } else {
            states << closure(null, testcase.attr)
        }

        states = states.findAll { it != TestState.IGNORE }

        if (testcase.anyMatch) {
            state = states.any { it == TestState.OK } ? TestState.OK : TestState.NG
        } else {
            state = states.every { it == TestState.OK } ? TestState.OK : TestState.NG
        }

        state
    }
}
