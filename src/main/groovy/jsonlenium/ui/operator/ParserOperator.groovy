package jsonlenium.ui.operator

import com.jayway.jsonpath.PathNotFoundException
import jsonlenium.ui.page.HtmlPage
import jsonlenium.ui.page.IPage
import jsonlenium.ui.page.JsonPage
import jsonlenium.ui.util.WebNavigator

class ParserOperator implements IOperator {
    private IPage currentPage
    private Map<String, IPage> pages
    private Map<String, ?> testcase
    private String selector
    private String xpath
    private String jsonPath
    private String userAgent

    ParserOperator(IPage... args) {
        pages = [:]
        args.each {
            pages[it.name] = it
        }
    }

    IOperator execSelector() {
        currentPage = pages[HtmlPage.name].read { conn ->
            if (userAgent != null) {
                conn.setRequestProperty('User-Agent', userAgent)
            }
        }
        if (selector != null) {
            currentPage.selector(selector)
        }
        this
    }

    IOperator execXpath() {
        if (xpath != null) {
            currentPage.xpath(xpath)
        }
        this
    }

    IOperator execJsonPath() {
        currentPage = pages[JsonPage.name].read { conn ->
            if (userAgent != null) {
                conn.setRequestProperty('User-Agent', userAgent)
            }
        }
        if (jsonPath != null) {
            try {
                currentPage.json(jsonPath)
            } catch (FileNotFoundException | PathNotFoundException e) {
                // Continue processing
            }
        }
        this
    }

    IOperator execJs(String jsCode) {
        throw new UnsupportedOperationException()
    }

    IOperator execEvent() {
        this
    }

    IOperator waitForExecJs(String jsCode) {
        throw new UnsupportedOperationException()
    }

    IOperator click() {
        throw new UnsupportedOperationException()
    }

    IOperator preventDefault() {
        throw new UnsupportedOperationException()
    }

    String getCurrentUrl() {
        currentPage.currentUrl
    }

    WebNavigator getElements() {
        new WebNavigator(currentPage.navigator, testcase)
    }

    String getJsResponse() {
        throw new UnsupportedOperationException()
    }

    Object getEventResponse() {
        throw new UnsupportedOperationException()
    }

    String getDriverName() {
        throw new UnsupportedOperationException()
    }

    void setTestcase(Map<String, ?> testcase) {
        this.testcase = testcase
        selector = testcase.selector
        xpath = testcase.xpath
        jsonPath = testcase.jsonpath
        userAgent = testcase.useragent
    }
}
