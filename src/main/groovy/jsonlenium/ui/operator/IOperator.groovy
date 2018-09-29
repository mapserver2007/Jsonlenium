package jsonlenium.ui.operator

import jsonlenium.ui.util.WebNavigator

interface IOperator {
    IOperator execSelector()
    IOperator execXpath()
    IOperator execJsonPath()
    IOperator execJs(String jsCode)
    IOperator execEvent()
    IOperator waitForExecJs(String jsCode)
    IOperator click()
    IOperator preventDefault()
    String getCurrentUrl()
    WebNavigator getElements()
    String getJsResponse()
    Object getEventResponse()
    String getDriverName()
    void setTestcase(Map<String, ?> testcase)
}
