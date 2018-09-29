package jsonlenium.ui.util

import jsonlenium.constant.Driver
import jsonlenium.constant.JsonleniumEnv
import org.apache.commons.lang.StringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.ProtocolHandshake

import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

class WebDriverFactory {
    static WebDriver getDriver() {
        getDriver(System.getProperty(JsonleniumEnv.DRIVER))
    }

    static WebDriver getDriver(String driverName) {
        getDriver(driverName) { [:] }
    }

    static WebDriver getDriver(Closure closure) {
        getDriver(System.getProperty(JsonleniumEnv.DRIVER), closure)
    }

    static WebDriver getDriver(String driverName, Closure closure) {
        Logger.getLogger(ProtocolHandshake.class.getName()).setLevel(Level.OFF)
        switch (driverName) {
            case Driver.CHROME:
                return getChromeDriver(closure)
            case Driver.HEADLESS_CHROME:
                return getChromeDriver(closure, ["--headless", "-disable-gpu"])
            case Driver.PHANTOM_JS:
                Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF)
                return getPhantomJsDriver(closure)
            default:
                throw new IllegalArgumentException()
        }
    }

    static WebDriver getChromeDriver(Closure closure, List<String> args = []) {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true")
        Map<String, String> options = closure.call()
        def chromeOptions = new ChromeOptions()
        if (args.any()) {
            chromeOptions.addArguments(args)
        }
        if (options?.userAgent != null) {
            chromeOptions.setExperimentalOption("mobileEmulation", [
                "userAgent": options.userAgent
            ])
        }
        def timeout = !StringUtils.isBlank(System.getProperty(JsonleniumEnv.TIMEOUT)) ?
            Long.parseLong(System.getProperty(JsonleniumEnv.TIMEOUT), 10) : 30L
        def driver = new ChromeDriver(chromeOptions)
        // Since '-Dtimeout' option is in seconds, 'TimeUnit' is handled by SECOND
        driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS)
        driver.manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS)

        driver
    }

    static WebDriver getPhantomJsDriver(Closure closure) {
        DesiredCapabilities caps = new DesiredCapabilities()
        caps.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            System.getProperty("webdriver.ghost.driver")
        )
        caps.setCapability(
            PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
            ["--webdriver-loglevel=NONE", "--ignore-ssl-errors=yes"]
        )
        caps.setCapability(
            PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
            ["--loglevel=NONE", "--ignore-ssl-errors=yes"]
        )

        Map<String, String> options = closure.call()
        if (options?.userAgent != null) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", options.userAgent)
        }
        def timeout = !StringUtils.isBlank(System.getProperty(JsonleniumEnv.TIMEOUT)) ?
            Long.parseLong(System.getProperty(JsonleniumEnv.TIMEOUT), 10) : 30L
        def driver = new PhantomJSDriver(caps)
        // Since '-Dtimeout' option is in seconds, 'TimeUnit' is handled by SECOND
        driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS)
        driver.manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS)

        driver
    }
}
