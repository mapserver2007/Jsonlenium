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
@AssertionType(Event.K3C)
@OperationType(Operation.BROWSER)
class K3C extends AssertionBase {
    @Override
    Map<String, ?> assertTest(Map<String, ?> testcase) {
        def messages = []
        def state
        String actual
        web.testcase = testcase

        try {
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
