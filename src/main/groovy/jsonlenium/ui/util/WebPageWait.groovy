package jsonlenium.ui.util

import geb.navigator.Navigator
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration
import java.util.function.Function

class WebPageWait {
    private WebDriver driver
    private final int POLLING_CYCLE = 1

    WebPageWait(WebDriver driver) {
        this.driver = driver
    }

    void waitForPresenceOfElementLocated(By locator, long timeout, @ClosureParams(value = SimpleType, options = ["org.openqa.selenium.By"]) Closure closure) {
        if (timeout > 0) {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(locator))
        }
        closure.call(locator)
    }

    void waitForUntilClickable(Navigator navigator, long timeout, @ClosureParams(value = SimpleType, options = ["geb.navigator.Navigator"]) Closure closure) {
        new FluentWait(navigator).withTimeout(Duration.ofSeconds(timeout))
            .pollingEvery(Duration.ofSeconds(POLLING_CYCLE))
            .ignoring(WebDriverException)
            .until(new Function<Navigator, Navigator>() {
                Navigator apply(Navigator waitedNavigator) {
                    closure.call(waitedNavigator)
                }
            })
    }
}
