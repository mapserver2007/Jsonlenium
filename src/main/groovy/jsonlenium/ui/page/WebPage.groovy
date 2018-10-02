package jsonlenium.ui.page

import geb.Page
import geb.navigator.Navigator
import jsonlenium.ui.util.WebPageWait
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver

class WebPage implements IPage {
    private Page page
    private WebPageWait pageWait
    private Navigator navigator
    private String jsResponse
    private long timeout

    private final long NOWAIT = -1L
    private final long CLICK_AND_PRESENCE_OF_ELEMENT_LOCATED_TIMEOUT = 30L

    WebPage(Page page, WebPageWait pageWait) {
        this.page = page
        this.pageWait = pageWait
        timeout = NOWAIT
    }

    WebPage read(Closure closure) {
        throw new UnsupportedOperationException()
    }

    WebPage selector(String selector) {
        pageWait.waitForPresenceOfElementLocated(By.cssSelector(selector), timeout) {
            timeout = NOWAIT
            navigator = page.$(By.cssSelector(selector))
        }
        this
    }

    WebPage xpath(String xpath) {
        pageWait.waitForPresenceOfElementLocated(By.xpath(xpath), timeout) {
            timeout = NOWAIT
            navigator = page.$(it)
        }
        this
    }

    WebPage click() {
        try {
            pageWait.waitForUntilClickable(navigator, CLICK_AND_PRESENCE_OF_ELEMENT_LOCATED_TIMEOUT) { waitedNavigator ->
                // After clicking, it may take time until the element is drawn, so wait for a long time
                timeout = CLICK_AND_PRESENCE_OF_ELEMENT_LOCATED_TIMEOUT
                navigator = waitedNavigator.click()
            }
        } catch (TimeoutException ignore) {
            // Ignore responses that do not return long (almost advertisement related request).
        }

        this
    }

    WebPage js(String jsCode) {
        jsResponse = page.js.exec(jsCode)
        this
    }

    WebPage json(String jsonPath) {
        throw new UnsupportedOperationException()
    }

    WebPage waitFor(Closure closure) {
        page.waitFor {
            closure.call()
        }
        this
    }

    WebDriver getDriver() {
        page.driver
    }

    String getJsResponse() {
        jsResponse
    }

    Iterable<Navigator> getNavigator() {
        navigator.toList()
    }

    String getName() {
        this.class.typeName
    }

    String getCurrentUrl() {
        driver.currentUrl
    }
}
