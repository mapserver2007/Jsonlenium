package jsonlenium.ui.assertion

import jsonlenium.annotation.AssertionType
import jsonlenium.annotation.OperationType
import jsonlenium.annotation.TestCase
import jsonlenium.constant.Event
import jsonlenium.constant.Operation

@TestCase
@AssertionType(Event.REST_API_TEST)
@OperationType(Operation.PARSER)
class RestApiTest extends AssertionBase {
    @Override
    Map<String, ?> assertTest(Map<String, ?> testcase) {
        def messages = []
        def state
        web.testcase = testcase

        def page = web.execJsonPath().elements

        state = page.read { elem, ignore ->
            runAssert.assertion(testcase, elem ?: Optional.ofNullable(elem))
            messages << runAssert.message
            runAssert.state
        }

        [state: state, messages: messages]
    }
}
