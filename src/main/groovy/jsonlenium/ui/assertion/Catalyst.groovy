package jsonlenium.ui.assertion

import jsonlenium.annotation.AssertionType
import jsonlenium.annotation.OperationType
import jsonlenium.annotation.TestCase
import jsonlenium.constant.Event
import jsonlenium.constant.Operation
import jsonlenium.constant.TestState
import groovy.json.JsonSlurper
import jsonlenium.util.Message
import org.openqa.selenium.WebDriverException

@TestCase
@AssertionType(Event.CATALYST)
@OperationType(Operation.BROWSER)
class Catalyst extends AssertionBase {
    @Override
    Map<String, ?> assertTest(Map<String, ?> testcase) {
        def messages = []
        def state
        web.testcase = testcase
        String actual

        try {
            if (testcase.jsvar) {
                testcase.expect = web.execJs("return ${testcase.expect};").jsResponse
            }
            actual = web.execJs("return ${testcase.attr};").jsResponse
        } catch (WebDriverException e) {
            try {
                def jsonParser = new JsonSlurper()
                Map message = jsonParser.parseText(e.message)
                messages << sprintf(Message.ASSERTION_RUNTIME_ERROR.toString(), message.errorMessage)
            } catch (Exception ignore) {
                messages << sprintf(Message.ASSERTION_RUNTIME_ERROR.toString(), e.message)
            }
            state = TestState.NG

            return [state: state, messages: messages]
        }

        runAssert.assertion(testcase, Optional.ofNullable(actual))
        state = runAssert.state
        messages << runAssert.message

        [state: state, messages: messages]
    }
}
