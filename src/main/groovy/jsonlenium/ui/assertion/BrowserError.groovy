package jsonlenium.ui.assertion

import jsonlenium.annotation.OperationType
import jsonlenium.annotation.TestCase
import jsonlenium.constant.Event
import jsonlenium.annotation.AssertionType
import jsonlenium.constant.Operation
import org.openqa.selenium.logging.LogEntries

@TestCase
@AssertionType(Event.BROWSER_ERROR)
@OperationType(Operation.BROWSER)
class BrowserError extends AssertionBase {
    @Override
    Map<String, ?> assertTest(Map<String, ?> testcase) {
        def messages = []
        def state
        web.testcase = testcase

        def logEntries = (LogEntries)web.execEvent().eventResponse
        runAssert.assertion(testcase, web.driverName, logEntries)
        state = runAssert.state
        messages << runAssert.message

        [state: state, messages: messages]
    }
}
