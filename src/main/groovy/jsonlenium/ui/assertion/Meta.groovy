package jsonlenium.ui.assertion

import jsonlenium.annotation.AssertionType
import jsonlenium.annotation.OperationType
import jsonlenium.annotation.TestCase
import jsonlenium.constant.Event
import jsonlenium.constant.Operation
import jsonlenium.constant.TestState

@TestCase
@AssertionType(Event.META)
@OperationType([Operation.BROWSER, Operation.PARSER])
class Meta extends AssertionBase {
    @Override
    Map<String, ?> assertTest(Map<String, ?> testcase) {
        def messages = []
        def state
        web.testcase = testcase

        def page = web.execSelector().elements

        switch (testcase.operation) {
            case Operation.BROWSER:
                state = page.read { elem, attr ->
                    String actual = null
                    if (elem != null) {
                        actual = attr != null ? elem.attr(attr) : elem.text() != "" ? elem.text() : elem.@innerText
                    }
                    // Robots and noodp have the same name attribute robots.
                    // Since it is almost impossible to inspect noodp, noydir, noarchive exclude it from inspection targets,
                    // and if "name = robots" is specified, only check index and follow.
                    if (actual == "noodp" || actual == "noydir" || actual == "noarchive") {
                        return TestState.IGNORE
                    }
                    runAssert.assertion(testcase, Optional.ofNullable(actual))
                    messages << runAssert.message
                    runAssert.state
                }
                break
            case Operation.PARSER:
                state = page.parse { elem, attr ->
                    String actual = null
                    if (elem != null) {
                        actual = attr != null ? elem.attr(attr) : elem.text()
                    }
                    // Robots and noodp have the same name attribute robots.
                    // Since it is almost impossible to inspect noodp, noydir, noarchive exclude it from inspection targets,
                    // and if "name = robots" is specified, only check index and follow.
                    if (actual == "noodp" || actual == "noydir" || actual == "noarchive") {
                        return TestState.IGNORE
                    }
                    runAssert.assertion(testcase, Optional.ofNullable(actual))
                    messages << runAssert.message
                    runAssert.state
                }
                break
        }

        [state: state, messages: messages]
    }
}
