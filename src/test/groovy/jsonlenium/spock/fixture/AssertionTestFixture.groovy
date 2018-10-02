package jsonlenium.spock.fixture

import jodd.util.ClassLoaderUtil
import jsonlenium.build.util.EventFactory
import jsonlenium.constant.JsonleniumEnv
import jsonlenium.constant.TestState
import geb.Page
import geb.js.JavascriptInterface
import geb.navigator.Navigator
import groovy.json.JsonSlurper
import jsonlenium.build.util.TestFileGenerator
import jsonlenium.ui.page.HtmlPage
import jsonlenium.ui.page.JsonPage
import jsonlenium.ui.page.WebPage
import jsonlenium.ui.util.WebPageWait
import org.codehaus.groovy.tools.Utilities
import org.junit.Ignore
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogEntry
import org.openqa.selenium.logging.Logs
import org.openqa.selenium.remote.RemoteWebDriver
import spock.lang.Specification

import java.util.logging.Level

class MockFixture extends Specification {
    @Ignore
    def getWebPageSpy() {
        def page = Mock(Page)
        def navigator = Mock(Navigator)
        def js = Mock(JavascriptInterface)
        def pageWait = Mock(WebPageWait)
        pageWait.waitForPresenceOfElementLocated(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        pageWait.waitForUntilClickable(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        js.exec(_) >> ""
        page.js >> js
        page.driver >> Mock(RemoteWebDriver)
        page.driver.windowHandles >> ["dummy"]
        page.$(_) >> navigator
        Spy(WebPage, constructorArgs: [page, pageWait])
    }

    @Ignore
    def getWebPageSpyForBrowserOperation() {
        def page = Mock(Page)
        def navigator = Mock(Navigator)
        def js = Mock(JavascriptInterface)
        def pageWait = Mock(WebPageWait)
        pageWait.waitForPresenceOfElementLocated(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        pageWait.waitForUntilClickable(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        js.exec(_) >> ""
        page.js >> js
        page.driver >> Mock(RemoteWebDriver)
        page.driver.windowHandles >> ["dummy"]
        page.driver.switchTo() >> Mock(WebDriver.TargetLocator)
        page.driver.switchTo().alert() >> {
            throw new NoAlertPresentException()
        }
        page.driver.manage() >> Mock(WebDriver.Options)
        page.driver.manage().logs() >> Mock(Logs)
        page.driver.manage().logs().get(_) >> Spy(LogEntries, constructorArgs: [new ArrayList<LogEntry>()])
        page.$(_) >> navigator
        Spy(WebPage, constructorArgs: [page, pageWait])
    }

    @Ignore
    def getWebPageSpyForCatalystOperation(String text) {
        def page = Mock(Page)
        def navigator = Mock(Navigator)
        def js = Mock(JavascriptInterface)
        def pageWait = Mock(WebPageWait)
        pageWait.waitForPresenceOfElementLocated(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        pageWait.waitForUntilClickable(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        js.exec(_) >> text
        page.js >> js
        page.driver >> Mock(RemoteWebDriver)
        page.driver.windowHandles >> ["dummy"]
        page.$(_) >> navigator
        Spy(WebPage, constructorArgs: [page, pageWait])
    }

    @Ignore
    def getWebPageSpyForK3COperation(String text) {
        def page = Mock(Page)
        def navigator = Mock(Navigator)
        def js = Mock(JavascriptInterface)
        def pageWait = Mock(WebPageWait)
        pageWait.waitForPresenceOfElementLocated(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        pageWait.waitForUntilClickable(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        js.exec(_) >> text
        page.js >> js
        page.driver >> Mock(RemoteWebDriver)
        page.driver.windowHandles >> ["dummy"]
        page.$(_) >> navigator
        Spy(WebPage, constructorArgs: [page, pageWait])
    }

    @Ignore
    def getWebPageSpyForBrowserErrorOperation() {
        def page = Mock(Page)
        def navigator = Mock(Navigator)
        def js = Mock(JavascriptInterface)
        def pageWait = Mock(WebPageWait)
        pageWait.waitForPresenceOfElementLocated(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        pageWait.waitForUntilClickable(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        js.exec(_) >> ""
        page.js >> js
        page.driver >> Mock(RemoteWebDriver)
        page.driver.windowHandles >> ["dummy"]
        page.driver.switchTo() >> Mock(WebDriver.TargetLocator)
        page.driver.switchTo().alert() >> {
            throw new NoAlertPresentException()
        }
        page.driver.manage() >> Mock(WebDriver.Options)
        page.driver.manage().logs() >> Mock(Logs)
        page.driver.manage().logs().get(_) >> Spy(LogEntries, constructorArgs: [new ArrayList<LogEntry>()])
        page.$(_) >> navigator
        Spy(WebPage, constructorArgs: [page, pageWait])
    }

    @Ignore
    def getWebPageSpyForBrowserErrorOperation(Level level) {
        def page = Mock(Page)
        def navigator = Mock(Navigator)
        def js = Mock(JavascriptInterface)
        def pageWait = Mock(WebPageWait)
        pageWait.waitForPresenceOfElementLocated(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        pageWait.waitForUntilClickable(_, _) >> { arg1, arg2 -> arg2.call(arg1) }
        js.exec(_) >> ""
        page.js >> js
        page.driver >> Mock(RemoteWebDriver)
        page.driver.windowHandles >> ["dummy"]
        page.driver.switchTo() >> Mock(WebDriver.TargetLocator)
        page.driver.switchTo().alert() >> {
            throw new NoAlertPresentException()
        }
        def errorEntries = new ArrayList<LogEntry>()
        errorEntries << new LogEntry(level, 946684800000L, "error")
        page.driver.manage() >> Mock(WebDriver.Options)
        page.driver.manage().logs() >> Mock(Logs)
        page.driver.manage().logs().get(_) >> Spy(LogEntries, constructorArgs: [errorEntries])
        page.$(_) >> navigator
        Spy(WebPage, constructorArgs: [page, pageWait])
    }

    @Ignore
    def getJsonPageSpy(String json) {
        def conn = Mock(HttpURLConnection)
        conn.getURL() >> new URL("http://example.com/")
        conn.getResponseCode() >> HttpURLConnection.HTTP_OK
        conn.getInputStream() >> new ByteArrayInputStream(json.getBytes("UTF-8"))
        Spy(JsonPage, constructorArgs: [conn])
    }

    @Ignore
    def getHtmlPageSpy(List textList) {
        def conn = Mock(HttpURLConnection)
        def response = "<html><body>"
        textList.each {
            response += "<div class=\"test\">"
            response += it
            response += "</div>"
        }
        response += "</body></html>"
        conn.getURL() >> new URL("http://example.com/")
        conn.getResponseCode() >> HttpURLConnection.HTTP_OK
        conn.getInputStream() >> new ByteArrayInputStream(response.getBytes("UTF-8"))
        Spy(HtmlPage, constructorArgs: [conn])
    }

    @Ignore
    def ブラウザ要素を設定(List textList) {
        def webPage = getWebPageSpy()
        def navigatorList = []
        textList.each { expect ->
            navigatorList << Mock(Navigator) {
                attr(_) >> ""
                text() >> expect
            }
        }
        webPage.navigator >> navigatorList
        webPage
    }

    def ブラウザ属性の要素を設定(List textList) {
        def webPage = getWebPageSpy()
        def navigatorList = []
        textList.each { expect ->
            navigatorList << Mock(Navigator) {
                attr(_) >> expect
                text() >> ""
            }
        }
        webPage.navigator >> navigatorList
        webPage
    }

    @Ignore
    def パーサ要素を設定(List textList) {
        def htmlPage = getHtmlPageSpy(textList)
        htmlPage.getName() >> HtmlPage.name
        htmlPage
    }

    @Ignore
    def パーサ属性の要素を設定(List htmlList) {
        def htmlPage = getHtmlPageSpy(htmlList)
        htmlPage.getName() >> HtmlPage.name
        htmlPage
    }

    @Ignore
    def 属性を設定(List attrList) {
        def webPage = getWebPageSpy()
        def navigatorList = []
        attrList.each { expect ->
            navigatorList << Mock(Navigator) {
                attr(_) >> expect
            }
        }
        webPage.navigator >> navigatorList
        webPage
    }

    @Ignore
    def 要素を空で設定() {
        def webPage = getWebPageSpy()
        webPage.navigator >> []
        webPage
    }

    @Ignore
    def クリックできる要素を設定(List textList) {
        def webPage = getWebPageSpyForBrowserOperation()
        def navigatorList = []
        textList.each { expect ->
            navigatorList << Mock(Navigator) {
                attr(_) >> ""
                text() >> expect
            }
        }
        webPage.navigator >> navigatorList
        webPage
    }

    @Ignore
    def テキスト入力できる要素を設定(List textList, String tagName) {
        def webPage = getWebPageSpyForBrowserOperation()
        def navigatorList = []
        textList.each { expect ->
            navigatorList << Mock(Navigator) {
                attr(_) >> ""
                text() >> expect
            }
        }
        webPage.navigator >> navigatorList
        webPage
    }

    @Ignore
    def JSON要素を設定(String json) {
        def jsonPage = getJsonPageSpy(json)
        jsonPage.getName() >> JsonPage.name
        jsonPage
    }
}

class TestcaseFixture {
    static テストケース取得(String filePath) {
        def factory = new EventFactory()
        factory.loadTestCase()
        def fileList = factory.getTestFileList(ClassLoaderUtil.getResource(filePath).path)
        def testcases = new ArrayList<HashMap<String, ?>>()
        factory.createTestCase(fileList) { List<?> testcase, boolean isCreateSuccess ->
            testcases.addAll(testcase)
        }

        def generator = new TestFileGenerator()
        testcases = generator.reconstruct(testcases)
        generator.generate(testcases) {}
        def file = new File(System.getProperty(JsonleniumEnv.USER_DIR) + "/build/json/T0001.json")
        def tests = new JsonSlurper().parse(file)

        tests[0]['testcase']
    }
}

class UiAssertionTestFixture {
    static BrowserMock_異なる内容_マッチする要素を取得() {
        new MockFixture().ブラウザ要素を設定(["result", "result2"])
    }

    static BrowserMock_同じ内容_マッチする要素を取得() {
        new MockFixture().ブラウザ要素を設定(["result", "result"])
    }

    static BrowserMock_異なる内容_マッチしない要素を取得() {
        new MockFixture().ブラウザ要素を設定(["result2", "result2"])
    }

    static BrowserMock_同じ内容_マッチしない要素を取得() {
        new MockFixture().ブラウザ要素を設定(["result", "result2"])
    }

    static BrowserMock_異なる内容_マッチする属性の要素を取得() {
        new MockFixture().ブラウザ属性の要素を設定(["result", "result2"])
    }

    static BrowserMock_同じ内容_マッチする属性の要素を取得() {
        new MockFixture().ブラウザ属性の要素を設定(["result", "result"])
    }

    static BrowserMock_大文字小文字区別なし要素を取得() {
        new MockFixture().ブラウザ要素を設定(["aaa"])
    }

    static BrowserMock_大文字小文字区別なし属性を取得() {
        new MockFixture().ブラウザ属性の要素を設定(["aaa"])
    }

    static ParserMock_異なる内容_マッチする要素を取得() {
        new MockFixture().パーサ要素を設定(["result", "result2"])
    }

    static ParserMock_同じ内容_マッチする要素を取得() {
        new MockFixture().パーサ要素を設定(["result", "result"])
    }

    static ParserMock_異なる内容_マッチしない要素を取得() {
        new MockFixture().パーサ要素を設定(["result2", "result2"])
    }

    static ParserMock_同じ内容_マッチしない要素を取得() {
        new MockFixture().パーサ要素を設定(["result", "result2"])
    }

    static ParserMock_波ダッシュを取得() {
        new MockFixture().パーサ要素を設定(["〜"])
    }

    static ParserMock_全角チルダを取得() {
        new MockFixture().パーサ要素を設定(["～"])
    }

    static ParserMock_大文字小文字区別なし要素を取得() {
        new MockFixture().パーサ要素を設定(["aaa"])
    }

    static ParserMock_大文字小文字区別なし属性を取得() {
        new MockFixture().パーサ属性の要素を設定(["<p id=\"aaa\"></p>"])
    }

    static Mock_空の要素を取得() {
        new MockFixture().要素を空で設定()
    }

    static Mock_クリックできる要素を取得() {
        new MockFixture().クリックできる要素を設定(["result"])
    }

    static Mock_inputタグ入力できる要素を取得() {
        new MockFixture().テキスト入力できる要素を設定(["result", "result"], "input")
    }

    static Mock_textareaタグ入力できる要素を取得() {
        new MockFixture().テキスト入力できる要素を設定(["result", "result"], "textarea")
    }
}

class UiAssertionTestExpectFixture {
    static 想定結果_アサーションOK() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'result', actual: 'result', path: '.test')"
            ]
        ]
    }

    static 想定結果_アサーションOK_attr() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'result', actual: 'result', attr: 'attr', path: 'selector')"
            ]
        ]
    }

    static 想定結果_アサーションNG_not_expect() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 (expect: 'result', actual: 'result2', path: '.test')",
            ]
        ]
    }

    static 想定結果_アサーションNG_not_expect_all() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 (expect: 'result', actual: 'result2', path: '.test')",
            ]
        ]
    }

    static 想定結果_アサーションNG_パスが存在しない() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 (expect: 'result', actual: 'null', path: '.test')"
            ]
        ]
    }

    static 想定結果_アサーションOK_波ダッシュ全角チルダ() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: '～', actual: '～', path: '.test')"
            ]
        ]
    }

    static 想定結果_アサーションOK_大文字小文字区別なし() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'AAA', actual: 'aaa', path: '.test', case_sensitive: 'false')"
            ]
        ]
    }

    static 想定結果_アサーションOK_大文字小文字区別なし_attr() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'AAA', actual: 'aaa', attr: 'id', path: '.test p', case_sensitive: 'false')"
            ]
        ]
    }
}

class CatalystAssertionTestFixture {
    static Mock_Catalyst要素を取得_マッチする場合() {
        new MockFixture().getWebPageSpyForCatalystOperation(["value1"])
    }

    static Mock_Catalyst要素を取得_マッチしない場合() {
        new MockFixture().getWebPageSpyForCatalystOperation("invalid value")
    }

    static Mock_Catalyst_大文字小文字区別なし要素を取得_マッチする場合() {
        new MockFixture().getWebPageSpyForCatalystOperation("VALUE1")
    }
}

class CatalystAssertionTestExpectFixture {
    static 想定結果_Catalyst_アサーションOK() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'value1', actual: 'value1', attr: 's.key1', path: 'null')"
            ]
        ]
    }

    static 想定結果_Catalyst_アサーションNG() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 (expect: 'value1', actual: 'invalid value', attr: 's.key1', path: 'null')"
            ]
        ]
    }

    static 想定結果_Catalyst_アサーションOK_大文字小文字区別なし() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'value1', actual: 'VALUE1', attr: 's.key1', path: 'null', case_sensitive: 'false')"
            ]
        ]
    }
}

class MetaAssertionTestFixture {
    static Mock_Meta要素を取得_マッチする場合() {
        new MockFixture().パーサ属性の要素を設定(["<meta name=\"key1\" content=\"value1\"/>"])
    }

    static Mock_Meta要素を取得_マッチしない場合() {
        new MockFixture().パーサ属性の要素を設定(["<meta name=\"key1\" content=\"value2\"/>"])
    }

    static Mock_Meta_大文字小文字区別なし要素を取得_マッチする場合() {
        new MockFixture().パーサ属性の要素を設定(["<meta name=\"key1\" content=\"value1\"/>"])
    }
}

class MetaAssertionTestExpectFixture {
    static 想定結果_Meta_アサーションOK() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'value1', actual: 'value1', attr: 'content', path: 'meta[name='key1'],meta[property='key1']')"
            ]
        ]
    }

    static 想定結果_Meta_アサーションNG() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 (expect: 'value1', actual: 'value2', attr: 'content', path: 'meta[name='key1'],meta[property='key1']')"
            ]
        ]
    }

    static 想定結果_Meta_アサーションOK_大文字小文字区別なし() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'VALUE1', actual: 'value1', attr: 'content', path: 'meta[name='key1'],meta[property='key1']', case_sensitive: 'false')"
            ]
        ]
    }
}

class K3CAssertionTestFixture {
    static Mock_K3C要素を取得_マッチする場合() {
        new MockFixture().getWebPageSpyForK3COperation("value1")
    }

    static Mock_K3C要素を取得_マッチしない場合() {
        new MockFixture().getWebPageSpyForK3COperation("value2")
    }

    static Mock_K3C_大文字小文字区別なし要素を取得_マッチする場合() {
        new MockFixture().getWebPageSpyForK3COperation("VALUE1")
    }
}

class K3CAssertionTestExpectFixture {
    static 想定結果_K3C_アサーションOK() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'value1', actual: 'value1', attr: 'k3c.atrack.val.key1', path: 'null')"
            ]
        ]
    }

    static 想定結果_K3C_アサーションNG() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 (expect: 'value1', actual: 'value2', attr: 'k3c.atrack.val.key1', path: 'null')"
            ]
        ]
    }

    static 想定結果_K3C_アサーションOK_大文字小文字区別なし() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'value1', actual: 'VALUE1', attr: 'k3c.atrack.val.key1', path: 'null', case_sensitive: 'false')"
            ]
        ]
    }
}

class BrowserErrorAssertionTestFixture {
    static Mock_BrowserErrorが発生しない場合() {
        new MockFixture().getWebPageSpyForBrowserErrorOperation()
    }

    static Mock_BrowserErrorが発生する場合_Chrome() {
        new MockFixture().getWebPageSpyForBrowserErrorOperation(Level.SEVERE)
    }

    static Mock_BrowserErrorが発生する場合_PhantomJS() {
        new MockFixture().getWebPageSpyForBrowserErrorOperation(Level.WARNING)
    }
}

class BrowserErrorAssertionTestExpectFixture {
    static 想定結果_BrowserError_アサーションOK() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 ブラウザエラーなし (http://sample.com/)"
            ]
        ]
    }

    static 想定結果_BrowserError_アサーションNG_Chrome() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 ブラウザエラーあり (http://sample.com/)${Utilities.eol()}\t[2000-01-01T09:00:00+0900] [SEVERE] error"
            ]
        ]
    }

    static 想定結果_BrowserError_アサーションNG_PhantomJS() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 ブラウザエラーあり (http://sample.com/)${Utilities.eol()}\t[2000-01-01T09:00:00+0900] [WARNING] error"
            ]
        ]
    }
}

class RestApiAssertionTestFixture {
    static Mock_JSON_マッチする要素を取得() {
        new MockFixture().JSON要素を設定("{\"name\":\"test\"}")
    }

    static Mock_JSON_マッチしない要素を取得() {
        new MockFixture().JSON要素を設定("{\"dummy\":\"test\"}")
    }

    static Mock_JSON_不正なJSONデータを取得() {
        new MockFixture().JSON要素を設定("text")
    }

    static Mock_JSON_大文字小文字区別なし要素を取得_マッチする場合() {
        new MockFixture().JSON要素を設定("{\"name\":\"TEST\"}")
    }
}

class RestApiAssertionTestExpectFixture {
    static 想定結果_RestApi_アサーションOK_expect() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'test', actual: 'test', path: '\$.name')"
            ]
        ]
    }

    static 想定結果_RestApi_アサーションOK_not_expect() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (not_expect: 'not_test', actual: 'test', path: '\$.name')"
            ]
        ]
    }

    static 想定結果_RestApi_アサーションNG() {
        [
            'state'   : TestState.NG,
            'messages': [
                "テスト失敗 (expect: 'test', actual: 'null', path: '\$.name')"
            ]
        ]
    }

    static 想定結果_RestApi_アサーションOK_大文字小文字区別なし() {
        [
            'state'   : TestState.OK,
            'messages': [
                "テスト成功 (expect: 'test', actual: 'TEST', path: '\$.name', case_sensitive: 'false')"
            ]
        ]
    }
}
