package jsonlenium.ui

import jsonlenium.constant.Driver
import jsonlenium.constant.EventState
import jsonlenium.constant.JsonleniumEnv
import jsonlenium.constant.TestState
import jsonlenium.ui.operator.BrowserOperator
import jsonlenium.ui.operator.ParserOperator
import jsonlenium.ui.util.RunTest
import geb.driver.CachingDriverFactory
import geb.error.GebAssertionError
import geb.error.GebException
import geb.spock.GebSpec
import geb.waiting.WaitTimeoutException
import jsonlenium.ui.exception.ParserOperatorException
import jsonlenium.ui.page.HtmlPage
import jsonlenium.ui.page.JsonPage
import jsonlenium.ui.page.WebPage
import jsonlenium.ui.util.WebDriverFactory
import jsonlenium.ui.util.WebPageWait
import jsonlenium.util.Message
import org.codehaus.groovy.tools.Utilities
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriverException
import spock.lang.Ignore

import java.util.regex.PatternSyntaxException

class Jsonlenium extends GebSpec {
    def messages = []
    def assertResult

    @Ignore
    runTest(Map<String, ?> testcase) {
        if (testcase == null) {
            return false
        }

        def eol = Utilities.eol()

        try {
            runLiteTest(testcase) && runBrowserTest(testcase)
        } catch (PatternSyntaxException e) {
            messages << ["${TestState.NG.toString()}": "${sprintf(Message.ASSERTION_REGULAR_EXPRESSION_ERROR.toString(), e.message)}${eol}\tat ${testcase.src}"]
            assertResult = false
        } catch (WebDriverException | GebException | GebAssertionError e) {
            messages << ["${TestState.NG.toString()}": "${sprintf(Message.ASSERTION_WEBDRIVER_ERROR.toString(), e.message)}${eol}\tat ${testcase.src}"]
            assertResult = false
        } catch (Throwable e) {
            messages << ["${TestState.FATAL.toString()}": "${e.message}${eol}${e.stackTrace.join(eol)}"]
            assertResult = false
        }

        messages.each { Map<String, String> elem ->
            elem.each { key, value ->
                if (key == TestState.OK.toString() || key == TestState.INFO.toString()) {
                    println "[${key}]${value}"
                } else {
                    System.err.println "[${key}]${value}"
                }
            }
        }

        assertResult
    }

    @Ignore
    startBrowser(String url) {
        go url
    }

    @Ignore
    quitBrowser() {
        driver.quit()
        CachingDriverFactory.clearCache()
        resetBrowser()
    }

    @Ignore
    runBrowserTest(Map<String, ?> testcase, int retryCount = JsonleniumEnv.MAX_RETRY) {
        try {
            if (testcase.useragent != null) {
                quitBrowser()
                driver = WebDriverFactory.getDriver { ['userAgent': testcase.useragent] }
            }

            try {
                startBrowser(testcase.url)
                // In rare cases, the browser becomes unresponsive.
                // Accessing the driver object at this time will result in a TimeoutException.
                // This process is checking it.
                driver.currentUrl
            } catch (TimeoutException e) {
                if (retryCount < 1) {
                    throw e
                }
                retryCount = retryCount - 1
                messages << ["${TestState.INFO.toString()}": "${sprintf(Message.ASSERTION_RETRY_BROWSER.toString(), e.class, JsonleniumEnv.MAX_RETRY - retryCount)}"]
                // If TimeoutException occurs and the browser does not respond, restart the browser.
                driver.navigate().refresh()
                driver.currentUrl
            }

            assertResult = true
            def eol = Utilities.eol()
            def page = new WebPage(browser.page, new WebPageWait(driver))
            def runTest = new RunTest(new BrowserOperator(page))
            runTest.loadBrowserEvent(testcase) { event ->
                runTest.fireEvent(event) { result ->
                    switch (result.state) {
                        case TestState.OK:
                            // force cache clear for headless
                            def driverName = System.getProperty(JsonleniumEnv.DRIVER)
                            def windowSize = page.driver.windowHandles.size()
                            testcase.cacheclear |= (driverName == Driver.HEADLESS_CHROME && windowSize > 1)
                            break
                        case TestState.NG:
                            testcase.eventState = EventState.STOP
                            result.messages.each { message ->
                                messages << ["${TestState.NG.toString()}": "${message}${eol}\tat ${testcase.src}"]
                            }
                            assertResult = false
                            return
                    }
                }
                runTest.assertion(event) { result ->
                    switch (result.state) {
                        case TestState.IGNORE:
                            break
                        case TestState.OK:
                            result.messages.each { message ->
                                messages << ["${TestState.OK.toString()}": message]
                            }
                            break
                        case TestState.NG:
                        case TestState.CONDITIONAL_NG:
                            result.messages.each { message ->
                                messages << ["${TestState.NG.toString()}": "${message}${eol}\tat ${testcase.src}"]
                            }
                            break
                    }
                    assertResult &= result.state.value()
                }
            }
        } catch (TimeoutException | WaitTimeoutException e) {
            if (retryCount < 1) {
                throw e
            }
            retryCount = retryCount - 1
            messages << ["${TestState.INFO.toString()}": "${sprintf(Message.ASSERTION_RETRY_BROWSER.toString(), e.class, JsonleniumEnv.MAX_RETRY - retryCount)}"]
            quitBrowser()
            getBrowser()
            runBrowserTest(testcase, retryCount)
        } finally {
            if (testcase.cacheclear) {
                quitBrowser()
            }
        }
    }

    @Ignore
    runLiteTest(Map<String, ?> testcase, int retryCount = JsonleniumEnv.MIN_RETRY) {
        def isContinuous = true
        def events = testcase.events as List
        if (events.size() > 1) {
            return isContinuous
        }

        assertResult = true
        def eol = Utilities.eol()
        URLConnection conn

        try {
            def uri = new URL(testcase.url)
            conn = (HttpURLConnection) uri.openConnection()
            def runTest = new RunTest(new ParserOperator(new HtmlPage(conn), new JsonPage(conn)))
            testcase.retry = retryCount
            runTest.loadParserEvent(testcase) { event ->
                runTest.assertion(event) { result ->
                    switch (result.state) {
                        case TestState.IGNORE:
                            break
                        case TestState.OK:
                            result.messages.each { message ->
                                messages << ["${TestState.OK.toString()}": message]
                            }
                            isContinuous = false
                            break
                        case TestState.NG:
                            result.messages.each { message ->
                                messages << ["${TestState.NG.toString()}": "${message}${eol}\tat ${testcase.src}"]
                            }
                            isContinuous = false
                            break
                        case TestState.CONDITIONAL_NG:
                            isContinuous = true
                            return
                    }
                    assertResult &= result.state.value()
                }
            }
        } catch (ParserOperatorException e) {
            if (retryCount < 1) {
                throw e
            }
            retryCount = retryCount - 1
            messages << ["${TestState.INFO.toString()}": "${sprintf(Message.ASSERTION_RETRY_PARSER.toString(), e.class, JsonleniumEnv.MIN_RETRY - retryCount)}"]
            isContinuous = false
            runLiteTest(testcase, retryCount)
        } catch (UnsupportedOperationException ignore) {
            isContinuous = false
        } finally {
            if (conn != null) {
                conn.disconnect()
            }
        }

        isContinuous
    }
}
