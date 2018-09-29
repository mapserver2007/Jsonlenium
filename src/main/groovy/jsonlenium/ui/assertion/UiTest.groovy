package jsonlenium.ui.assertion

import jsonlenium.annotation.AssertionType
import jsonlenium.annotation.OperationType
import jsonlenium.annotation.TestCase
import jsonlenium.constant.Event
import jsonlenium.constant.Operation
import jsonlenium.constant.TestState
import jsonlenium.ui.exception.ParserOperatorException
import jsonlenium.util.Message
import org.jsoup.select.Selector.SelectorParseException
import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.InvalidSelectorException

@TestCase
@AssertionType(Event.UI_TEST)
@OperationType([Operation.BROWSER, Operation.PARSER])
class UiTest extends AssertionBase {
    @Override
    Map<String, ?> assertTest(Map<String, ?> testcase) {
        def messages
        def state
        web.testcase = testcase

        try {
            def page = web.execEvent()
                .execSelector()
                .execXpath()
                .elements

            Map<String, List<String>> assertResult = TestState.values().collectEntries { [(it): []] }

            switch (testcase.operation) {
                case Operation.BROWSER:
                    state = page.read { elem, attr ->
                        String actual = null
                        if (elem != null) {
                            actual = compatibleActual(attr) {
                                elem.attr(attr)
                            } ?: elem.text() != "" ? elem.text() : elem.@innerText
                        }
                        runAssert.assertion(testcase, Optional.ofNullable(actual))
                        assertResult[runAssert.state] << runAssert.message
                        runAssert.state
                    }
                    break
                case Operation.PARSER:
                    state = page.parse { elem, attr ->
                        String actual = null
                        if (elem != null) {
                            actual = compatibleActual(attr) {
                                elem.attr(attr)
                            } ?: elem.text().empty ? elem.data() : elem.text()
                        } else {
                            if (testcase.retry) {
                                throw new ParserOperatorException()
                            }
                        }
                        runAssert.assertion(testcase, Optional.ofNullable(actual))
                        assertResult[runAssert.state] << runAssert.message
                        runAssert.state
                    }
                    break
            }

            messages = [assertResult.find { it.key == state }.value.first()]

            [state: state, messages: messages]
        } catch (SelectorParseException | InvalidElementStateException | InvalidSelectorException | IllegalStateException e) {
            messages = [sprintf(Message.ASSERTION_PATH_ILLEGAL_ERROR.toString(), testcase.selector ?: testcase.xpath)]
            state = TestState.CONDITIONAL_NG

            [state: state, messages: messages]
        }
    }

    String compatibleActual(String attr, Closure closure) {
        def compatibleValue = null
        if (attr != null) {
            compatibleValue = closure.call()
            switch (attr) {
                case "checked":
                    if (compatibleValue == "true") { // for Browser
                        compatibleValue = "checked"
                    }
                    break
                case "selected":
                    if (compatibleValue == "true") { // for Browser
                        compatibleValue = "selected"
                    }
                    break
            }
        }

        compatibleValue
    }
}
