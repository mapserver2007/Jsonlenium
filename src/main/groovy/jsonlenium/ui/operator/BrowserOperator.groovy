package jsonlenium.ui.operator


import jsonlenium.constant.JsonleniumEnv
import jsonlenium.constant.Event
import jsonlenium.ui.page.IPage
import jsonlenium.ui.util.WebNavigator
import org.openqa.selenium.Dimension
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.UnsupportedCommandException
import org.openqa.selenium.logging.LogType

class BrowserOperator implements IOperator {
    private IPage page
    private Object eventResponse
    private Map<String, ?> testcase
    private String selector
    private String xpath
    private String attr
    private Event event

    private final String JS_CODE_DISABLED_ONBEFOREUNLOAD = "((function(){return this;}).apply(null,[])||{}).onbeforeunload=null;"
    private final String JS_CODE_GET_SCROLL_WIDTH = "return window.document.documentElement.scrollWidth;"
    private final String JS_CODE_GET_SCROLL_HEIGHT = "return window.document.documentElement.scrollHeight;"
    private final String JS_CODE_PREVENT_DEFAULT_CLICK = "var _j=document.querySelector(\"%s\");if(!!_j){_j.addEventListener(\"click\",function(e){e.preventDefault();});}"

    BrowserOperator(IPage page) {
        this.page = page
        pageInitialized()
    }

    IOperator execSelector() {
        if (selector == null || selector.isEmpty()) {
            return this
        }
        page.selector(selector)
        this
    }

    IOperator execXpath() {
        if (xpath == null || xpath.isEmpty()) {
            return this
        }
        page.xpath(xpath)
        this
    }

    IOperator execJsonPath() {
        page.selector("pre")
        this
    }

    IOperator execJs(String jsCode) {
        page.js(jsCode)
        this
    }

    IOperator waitForExecJs(String jsCode) {
        page.waitFor {
            execJs(jsCode).jsResponse == "true"
        }
        this
    }

    IOperator execEvent() {
        eventResponse = null
        switch (event) {
            case Event.BROWSER_ERROR:
                eventResponse = page.driver.manage().logs().get(LogType.BROWSER)
                break
            case Event.CLICK:
            case Event.UI_TEST:
                def window = page.driver.windowHandles
                if (window.size() > 1) { // close and switch window if exist multiple window
                    def newTabWindowId = window.last()
                    window.each { windowId ->
                        if (newTabWindowId != windowId) {
                            page.driver.switchTo().window(windowId).close()
                        }
                    }
                    try {
                        page.driver.switchTo().window(newTabWindowId) // switch to new window
                        pageInitialized()
                    } catch (TimeoutException ignore) {
                        // Ignore responses that do not return long (almost advertisement related request).
                    }
                }

                // Chrome can not click on elements that can not be seen without scrolling.
                // Therefore, expand the chrome to the maximum window size.aga
                def width = page.js(JS_CODE_GET_SCROLL_WIDTH).jsResponse
                def height = page.js(JS_CODE_GET_SCROLL_HEIGHT).jsResponse

                if (!width.isEmpty() && !height.isEmpty()) {
                    page.driver.manage().window().setSize(
                        new Dimension(Integer.decode(width), Integer.decode(height))
                    )
                }

                break
        }

        this
    }

    IOperator click() {
        page.click()
        try {
            // Force to close alert, prompt, confirm dialog.
            page.driver.switchTo().alert().accept()
        } catch (NoAlertPresentException | UnsupportedCommandException ignore) {
            // Because PhantomJS driver doesn't support with alert(), confirm() and prompt(),
            // UnsupportedCommandException occurs.
            // Also, NoAlertPresentException occurs on pages that do not have an alert dialog.
        } catch (TimeoutException ignore) {
            // Ignore responses that do not return long (almost advertisement related request).
        }

        this
    }

    IOperator preventDefault() {
        page.js(sprintf(JS_CODE_PREVENT_DEFAULT_CLICK, selector))
        this
    }

    String getCurrentUrl() {
        page.currentUrl
    }

    WebNavigator getElements() {
        new WebNavigator(page.navigator, testcase)
    }

    String getJsResponse() {
        page.jsResponse
    }

    Object getEventResponse() {
        eventResponse
    }

    String getDriverName() {
        System.getProperty(JsonleniumEnv.DRIVER)
    }

    void pageInitialized() {
        execJs(JS_CODE_DISABLED_ONBEFOREUNLOAD)
    }

    void setTestcase(Map<String, ?> testcase) {
        this.testcase = testcase
        selector = testcase.selector
        xpath = testcase.xpath
        attr = testcase.attr
        event = testcase.event as Event
    }
}
