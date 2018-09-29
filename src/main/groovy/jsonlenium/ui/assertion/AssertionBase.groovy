package jsonlenium.ui.assertion

import jsonlenium.ui.operator.IOperator
import jsonlenium.ui.util.RunAssert

abstract class AssertionBase {
    protected RunAssert runAssert
    protected IOperator web

    AssertionBase() {
        this.runAssert = new RunAssert()
    }

    void setWeb(IOperator web) {
        this.web = web
    }

    abstract Map<String, ?> assertTest(Map<String, ?> testcase)
}
