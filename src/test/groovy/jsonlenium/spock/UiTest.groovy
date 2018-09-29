package jsonlenium.spock

import jsonlenium.ui.operator.BrowserOperator
import jsonlenium.ui.operator.ParserOperator
import jsonlenium.ui.util.RunTest
import spock.lang.Specification
import spock.lang.Unroll

import static jsonlenium.spock.fixture.BrowserErrorAssertionTestExpectFixture.*
import static jsonlenium.spock.fixture.BrowserErrorAssertionTestFixture.*
import static jsonlenium.spock.fixture.CatalystAssertionTestExpectFixture.*
import static jsonlenium.spock.fixture.CatalystAssertionTestFixture.*
import static jsonlenium.spock.fixture.K3CAssertionTestExpectFixture.*
import static jsonlenium.spock.fixture.K3CAssertionTestFixture.*
import static jsonlenium.spock.fixture.MetaAssertionTestExpectFixture.*
import static jsonlenium.spock.fixture.MetaAssertionTestFixture.*
import static jsonlenium.spock.fixture.RestApiAssertionTestExpectFixture.*
import static jsonlenium.spock.fixture.RestApiAssertionTestFixture.*
import static jsonlenium.spock.fixture.TestcaseFixture.テストケース取得
import static jsonlenium.spock.fixture.UiAssertionTestExpectFixture.*
import static jsonlenium.spock.fixture.UiAssertionTestFixture.*

class アサーション処理_UI_パーサ extends Specification {
    @Unroll
    "指定したパスにマッチした場合の結果が正しいこと(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new ParserOperator(htmlPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr                   | filePath                       | htmlPage                      | expect
        "selector"             | "/json/uiassert/ng/01/01.json" | ParserMock_異なる内容_マッチする要素を取得() | 想定結果_アサーションOK()
        "selector(expect_all)" | "/json/uiassert/ng/01/03.json" | ParserMock_同じ内容_マッチする要素を取得()  | 想定結果_アサーションOK()
    }

    @Unroll
    "指定したパスにマッチしない場合の結果が正しいこと(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new ParserOperator(webPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr                       | filePath                       | webPage                        | expect
        "selector(not_expect)"     | "/json/uiassert/ok/01/01.json" | ParserMock_異なる内容_マッチしない要素を取得() | 想定結果_アサーションNG_not_expect()
        "selector(not_expect_all)" | "/json/uiassert/ok/01/03.json" | ParserMock_同じ内容_マッチしない要素を取得()  | 想定結果_アサーションNG_not_expect_all()
    }

    @Unroll
    "特殊な文字を取得した場合の結果が正しいこと(#character)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new ParserOperator(webPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        character     | filePath                       | webPage               | expect
        "wave dash"   | "/json/uiassert/ok/04/01.json" | ParserMock_波ダッシュを取得() | 想定結果_アサーションOK_波ダッシュ全角チルダ()
        "width tilda" | "/json/uiassert/ok/04/02.json" | ParserMock_全角チルダを取得() | 想定結果_アサーションOK_波ダッシュ全角チルダ()
    }

    @Unroll
    "大文字小文字を区別しない場合の結果が正しいこと(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new ParserOperator(webPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr       | filePath                       | webPage                       | expect
        "selector" | "/json/uiassert/ok/05/01.json" | ParserMock_大文字小文字区別なし要素を取得() | 想定結果_アサーションOK_大文字小文字区別なし()
        "attr"     | "/json/uiassert/ok/05/02.json" | ParserMock_大文字小文字区別なし属性を取得() | 想定結果_アサーションOK_大文字小文字区別なし_attr()
    }
}

class アサーション処理_UI_ブラウザ extends Specification {
    @Unroll
    "指定したパスにマッチする場合の結果が正しいこと(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr                   | filePath                       | webPage                        | expect
        "selector"             | "/json/uiassert/ng/01/01.json" | BrowserMock_異なる内容_マッチする要素を取得()    | 想定結果_アサーションOK()
        "xpath"                | "/json/uiassert/ng/01/02.json" | BrowserMock_異なる内容_マッチする要素を取得()    | 想定結果_アサーションOK()
        "attr"                 | "/json/uiassert/ng/01/05.json" | BrowserMock_異なる内容_マッチする属性の要素を取得() | 想定結果_アサーションOK_attr()
        "selector(expect_all)" | "/json/uiassert/ng/01/03.json" | BrowserMock_同じ内容_マッチする要素を取得()     | 想定結果_アサーションOK()
        "xpath(expect_all)"    | "/json/uiassert/ng/01/04.json" | BrowserMock_同じ内容_マッチする要素を取得()     | 想定結果_アサーションOK()
        "attr(expect_all)"     | "/json/uiassert/ng/01/06.json" | BrowserMock_同じ内容_マッチする属性の要素を取得()  | 想定結果_アサーションOK_attr()
    }

    @Unroll
    "指定したパスにマッチしない場合の結果が正しいこと(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr                       | filePath                       | webPage                         | expect
        "selector(not_expect)"     | "/json/uiassert/ok/01/01.json" | BrowserMock_異なる内容_マッチしない要素を取得() | 想定結果_アサーションNG_not_expect()
        "xpath(not_expect)"        | "/json/uiassert/ok/01/02.json" | BrowserMock_異なる内容_マッチしない要素を取得() | 想定結果_アサーションNG_not_expect()
        "selector(not_expect_all)" | "/json/uiassert/ok/01/03.json" | BrowserMock_同じ内容_マッチしない要素を取得()  | 想定結果_アサーションNG_not_expect_all()
        "xpath(not_expect_all)"    | "/json/uiassert/ok/01/04.json" | BrowserMock_同じ内容_マッチしない要素を取得()  | 想定結果_アサーションNG_not_expect_all()
    }

    @Unroll
    "指定したパスが存在しない場合の結果が正しいこと(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr       | filePath                       | webPage        | expect
        "selector" | "/json/uiassert/ng/02/01.json" | Mock_空の要素を取得() | 想定結果_アサーションNG_パスが存在しない()
        "xpath"    | "/json/uiassert/ng/02/01.json" | Mock_空の要素を取得() | 想定結果_アサーションNG_パスが存在しない()
    }

    def 要素をクリックして取得した結果が正しいこと() {
        setup:
        def webPage = Mock_クリックできる要素を取得()
        def testcase = テストケース取得("/json/uiassert/ok/02/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_アサーションOK() == actual
    }

    @Unroll
    "テキスト入力ができること(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr       | filePath                       | webPage                     | expect
        "input"    | "/json/uiassert/ok/03/01.json" | Mock_inputタグ入力できる要素を取得()    | 想定結果_アサーションOK()
        "textarea" | "/json/uiassert/ok/03/01.json" | Mock_textareaタグ入力できる要素を取得() | 想定結果_アサーションOK()
    }

    @Unroll
    "大文字小文字を区別しない場合の結果が正しいこと(#attr)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        attr       | filePath                       | webPage                       | expect
        "selector" | "/json/uiassert/ok/05/01.json" | BrowserMock_大文字小文字区別なし要素を取得() | 想定結果_アサーションOK_大文字小文字区別なし()
        "attr"     | "/json/uiassert/ok/05/02.json" | BrowserMock_大文字小文字区別なし属性を取得() | 想定結果_アサーションOK_大文字小文字区別なし_attr()
    }
}

class アサーション処理_Catalyst extends Specification {
    def 指定した値にマッチした場合の結果が正しいこと() {
        setup:
        def webPage = Mock_Catalyst要素を取得_マッチする場合()
        def testcase = テストケース取得("/json/catalystassert/ok/01/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_Catalyst_アサーションOK() == actual
    }

    def 指定した値にマッチしない場合の結果が正しいこと() {
        setup:
        def webPage = Mock_Catalyst要素を取得_マッチしない場合()
        def testcase = テストケース取得("/json/catalystassert/ng/01/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_Catalyst_アサーションNG() == actual
    }

    def 大文字小文字を区別しない場合の結果が正しいこと() {
        setup:
        def webPage = Mock_Catalyst_大文字小文字区別なし要素を取得_マッチする場合()
        def testcase = テストケース取得("/json/catalystassert/ok/02/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_Catalyst_アサーションOK_大文字小文字区別なし() == actual
    }
}

class アサーション処理_Meta extends Specification {
    def 指定した値にマッチした場合の結果が正しいこと() {
        setup:
        def webPage = Mock_Meta要素を取得_マッチする場合()
        def testcase = テストケース取得("/json/metaassert/ok/01/01.json") as Map
        def runTest = new RunTest(new ParserOperator(webPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_Meta_アサーションOK() == actual
    }

    def 指定した値にマッチしない場合の結果が正しいこと() {
        setup:
        def webPage = Mock_Meta要素を取得_マッチしない場合()
        def testcase = テストケース取得("/json/metaassert/ng/01/01.json") as Map
        def runTest = new RunTest(new ParserOperator(webPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_Meta_アサーションNG() == actual
    }

    def 大文字小文字を区別しない場合の結果が正しいこと() {
        setup:
        def webPage = Mock_Meta_大文字小文字区別なし要素を取得_マッチする場合()
        def testcase = テストケース取得("/json/metaassert/ok/02/01.json") as Map
        def runTest = new RunTest(new ParserOperator(webPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_Meta_アサーションOK_大文字小文字区別なし() == actual
    }
}

class アサーション処理_K3C extends Specification {
    def 指定した値にマッチした場合の結果が正しいこと() {
        setup:
        def webPage = Mock_K3C要素を取得_マッチする場合()
        def testcase = テストケース取得("/json/k3cassert/ok/01/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_K3C_アサーションOK() == actual
    }

    def 指定した値にマッチしない場合の結果が正しいこと() {
        setup:
        def webPage = Mock_K3C要素を取得_マッチしない場合()
        def testcase = テストケース取得("/json/k3cassert/ng/01/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_K3C_アサーションNG() == actual
    }

    def 大文字小文字を区別しない場合の結果が正しいこと() {
        setup:
        def webPage = Mock_K3C_大文字小文字区別なし要素を取得_マッチする場合()
        def testcase = テストケース取得("/json/k3cassert/ok/02/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_K3C_アサーションOK_大文字小文字区別なし() == actual
    }
}

class アサーション処理_BrowserError extends Specification {
    def ブラウザエラーが発生しない場合の結果が正しいこと() {
        setup:
        def webPage = Mock_BrowserErrorが発生しない場合()
        def testcase = テストケース取得("/json/browsererrorassert/ok/01/01.json") as Map
        def runTest = new RunTest(new BrowserOperator(webPage))
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_BrowserError_アサーションOK() == actual
    }

    @Unroll
    "ブラウザエラーが発生する場合の結果が正しいこと(#driver)"() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def operator = Spy(BrowserOperator, constructorArgs: [webPage])
        operator.driverName >> driver
        def runTest = new RunTest(operator)
        def actual
        runTest.loadBrowserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        driver      | filePath                                 | webPage                              | expect
        "chrome"    | "/json/browsererrorassert/ng/01/01.json" | Mock_BrowserErrorが発生する場合_Chrome()    | 想定結果_BrowserError_アサーションNG_Chrome()
        "phantomjs" | "/json/browsererrorassert/ng/01/01.json" | Mock_BrowserErrorが発生する場合_PhantomJS() | 想定結果_BrowserError_アサーションNG_PhantomJS()
    }
}

class アサーション処理_RestApi extends Specification {
    @Unroll
    指定したパスにマッチする場合の結果が正しいこと() {
        setup:
        def jsonPage = Mock_JSON_マッチする要素を取得()
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new ParserOperator(jsonPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        filePath                            | expect
        "/json/restapiassert/ok/01/01.json" | 想定結果_RestApi_アサーションOK_expect()
        "/json/restapiassert/ok/01/02.json" | 想定結果_RestApi_アサーションOK_expect()
        "/json/restapiassert/ok/02/01.json" | 想定結果_RestApi_アサーションOK_not_expect()
        "/json/restapiassert/ok/02/02.json" | 想定結果_RestApi_アサーションOK_not_expect()
    }

    @Unroll
    指定したパスにマッチしない場合の結果が正しいこと() {
        setup:
        def testcase = テストケース取得(filePath) as Map
        def runTest = new RunTest(new ParserOperator(jsonPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        expect == actual

        where:
        filePath                            | jsonPage                | expect
        "/json/restapiassert/ng/01/01.json" | Mock_JSON_マッチしない要素を取得() | 想定結果_RestApi_アサーションNG()
        "/json/restapiassert/ng/01/02.json" | Mock_JSON_不正なJSONデータを取得() | 想定結果_RestApi_アサーションNG()
    }

    def 大文字小文字を区別しない場合の結果が正しいこと() {
        setup:
        def jsonPage = Mock_JSON_大文字小文字区別なし要素を取得_マッチする場合()
        def testcase = テストケース取得("/json/restapiassert/ok/03/01.json") as Map
        def runTest = new RunTest(new ParserOperator(jsonPage))
        def actual
        runTest.loadParserEvent(testcase) { event ->
            runTest.fireEvent(event) { result1 ->
                // nothing to do
            }
            runTest.assertion(event) { result2 ->
                actual = result2
            }
        }

        expect:
        想定結果_RestApi_アサーションOK_大文字小文字区別なし() == actual
    }
}
