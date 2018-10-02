package jsonlenium.spock

import jodd.util.ClassLoaderUtil
import jsonlenium.build.CompileTest
import spock.lang.Specification
import spock.lang.Unroll

import static jsonlenium.spock.fixture.CompileTestExpectFixture.*
import static jsonlenium.spock.fixture.CompileTestFixture.*

class JSONファイル取得処理 extends Specification {
    def ファイルパス指定でテストファイルが取得できること() {
        expect:
        JSONファイルリスト_ファイル指定().size() == 1
    }

    def ディレクトリパス指定でテストファイルが取得できること() {
        expect:
        JSONファイルリスト_ディレクトリ指定().size() >= 1
    }

    def ファイルパス指定でテストファイルが取得できないこと() {
        expect:
        JSON存在しないファイルリスト_ファイル指定().size() == 0
    }
}

class JSONファイル検証処理_正常系 extends Specification {
    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_testcase(#attr)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        attr                 | filePath                       | expect
        "selector"           | "/json/testcase/ok/01/01.json" | 想定結果_正常系_testcase_selector()
        "xpath"              | "/json/testcase/ok/01/02.json" | 想定結果_正常系_testcase_xpath()
        "attr"               | "/json/testcase/ok/03/01.json" | 想定結果_正常系_testcase_attr()
        "expect_all"         | "/json/testcase/ok/04/01.json" | 想定結果_正常系_testcase_expect_all()
        "regexp"             | "/json/testcase/ok/06/01.json" | 想定結果_正常系_testcase_regexp()
        "selector and xpath" | "/json/testcase/ok/07/01.json" | 想定結果_正常系_testcase_selector_and_xpath()
        "not_expect"         | "/json/testcase/ok/08/01.json" | 想定結果_正常系_testcase_not_expect()
        "not_expect_all"     | "/json/testcase/ok/09/01.json" | 想定結果_正常系_testcase_not_expect_all()
        "meta"               | "/json/testcase/ok/11/01.json" | 想定結果_正常系_testcase_meta()
        "catalyst"           | "/json/testcase/ok/12/01.json" | 想定結果_正常系_testcase_catalyst()
        "k3c"                | "/json/testcase/ok/13/01.json" | 想定結果_正常系_testcase_k3c()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_meta(#attr)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        attr       | filePath                   | expect
        "selector" | "/json/meta/ok/01/01.json" | 想定結果_正常系_meta_selector()
        "regexp"   | "/json/meta/ok/03/01.json" | 想定結果_正常系_meta_regexp()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_catalyst(#attr)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        attr        | filePath                       | expect
        "selector"  | "/json/catalyst/ok/01/01.json" | 想定結果_正常系_catalyst_selector()
        "regexp"    | "/json/catalyst/ok/05/01.json" | 想定結果_正常系_catalyst_regexp()
        "javar"     | "/json/catalyst/ok/03/01.json" | 想定結果_正常系_catalyst_jsvar()
        "not_javar" | "/json/catalyst/ok/04/01.json" | 想定結果_正常系_catalyst_not_jsvar()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_k3c(#attr)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        attr       | filePath                  | expect
        "selector" | "/json/k3c/ok/01/01.json" | 想定結果_正常系_k3c_selector()
        "regexp"   | "/json/k3c/ok/03/01.json" | 想定結果_正常系_k3c_regexp()
    }

    def テストファイルから生成されるオブジェクトの構造が正しいこと_browsererror() {
        expect:
        想定結果_正常系_browsererror() == テストケースリスト("/json/browsererror/ok/01/01.json")
    }

    def テストファイルから生成されるオブジェクトの構造が正しいこと_input() {
        expect:
        想定結果_正常系_input() == テストケースリスト("/json/input/ok/01/01.json")
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_click(#attr)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        attr            | filePath                    | expect
        "selector"      | "/json/click/ok/01/01.json" | 想定結果_正常系_click_selector()
        "selector,attr" | "/json/click/ok/01/02.json" | 想定結果_正常系_click_selector_attr()
        "xpath"         | "/json/click/ok/01/03.json" | 想定結果_正常系_click_xpath()
        "xpath,attr"    | "/json/click/ok/01/04.json" | 想定結果_正常系_click_xpath_attr()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_jsonpath(#attr)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        attr             | filePath                       | expect
        "selector"       | "/json/jsonpath/ok/01/01.json" | 想定結果_正常系_jsonpath_selector()
        "expect_all"     | "/json/jsonpath/ok/02/01.json" | 想定結果_正常系_jsonpath_expect_all()
        "not_expect"     | "/json/jsonpath/ok/03/01.json" | 想定結果_正常系_jsonpath_not_expect()
        "not_expect_all" | "/json/jsonpath/ok/04/01.json" | 想定結果_正常系_jsonpath_not_expect_all()
    }

    def テストファイルから生成されるオブジェクトの構造が正しいこと_useragent() {
        expect:
        想定結果_正常系_useragent() == テストケースリスト("/json/useragent/ok/01/01.json")
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_case_sensitive(#attr)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        attr              | filePath                             | expect
        "case_sensitive1" | "/json/case_sensitive/ok/01/01.json" | 想定結果_正常系_case_sensitive_default()
        "case_sensitive2" | "/json/case_sensitive/ok/01/02.json" | 想定結果_正常系_case_sensitive_enabled()
        "case_sensitive3" | "/json/case_sensitive/ok/01/03.json" | 想定結果_正常系_case_sensitive_disabled()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しいこと_複数ファイルの場合(#title)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        title      | filePath                | expect
        "testcase" | "/json/testcase/ok/02/" | 想定結果_正常系_複数ファイル_testcase()
        "meta"     | "/json/meta/ok/02/"     | 想定結果_正常系_複数ファイル_meta()
        "catalyst" | "/json/catalyst/ok/02/" | 想定結果_正常系_複数ファイル_catalyst()
        "k3c"      | "/json/k3c/ok/02/"      | 想定結果_正常系_複数ファイル_k3c()
    }

    @Unroll
    "テスト想定結果がnullの場合のオブジェクトの構造が正しいこと(#title)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        title      | filePath                       | expect
        "selector" | "/json/testcase/ok/10/01.json" | 想定結果_正常系_想定結果null_selector()
        "xpath"    | "/json/testcase/ok/10/02.json" | 想定結果_正常系_想定結果null_xpath()
        "jsonpath" | "/json/testcase/ok/10/03.json" | 想定結果_正常系_想定結果null_jsonpath()
    }
}

class JSONファイル検証処理_異常系 extends Specification {
    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_testcase(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause                         | filePath                       | expect
        "invalid hierarchy(testcase)" | "/json/testcase/ng/01/01.json" | 想定結果_異常系_testcase_invalid_hierarchy()
        "invalid type(testcase)"      | "/json/testcase/ng/02/01.json" | 想定結果_異常系_testcase_invalid_type()
        "empty element(testcase)"     | "/json/testcase/ng/03/01.json" | 想定結果_異常系_testcase_empty_element()
        "invalid type(selector)"      | "/json/testcase/ng/04/01.json" | 想定結果_異常系_testcase_selector_invalid_type()
        "invalid attr type(selector)" | "/json/testcase/ng/05/01.json" | 想定結果_異常系_testcase_selector_invalid_attr_type()
        "invalid hierarchy(selector)" | "/json/testcase/ng/06/01.json" | 想定結果_異常系_testcase_selector_invalid_hierarchy()
        "invalid type(xpath)"         | "/json/testcase/ng/07/01.json" | 想定結果_異常系_testcase_xpath_invalid_type()
        "invalid attr type(xpath)"    | "/json/testcase/ng/08/01.json" | 想定結果_異常系_testcase_xpath_invalid_attr_type()
        "invalid hierarchy(xpath)"    | "/json/testcase/ng/09/01.json" | 想定結果_異常系_testcase_xpath_invalid_hierarchy()
        "expect not found"            | "/json/testcase/ng/10/01.json" | 想定結果_異常系_testcase_expect_not_found()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_url(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                  | expect
        "required"          | "/json/url/ng/01/01.json" | 想定結果_異常系_url_required()
        "invalid hierarchy" | "/json/url/ng/02/01.json" | 想定結果_異常系_url_invalid_hierarchy()
        "invalid type"      | "/json/url/ng/03/01.json" | 想定結果_異常系_url_invalid_type()
        "invalid format"    | "/json/url/ng/04/01.json" | 想定結果_異常系_url_invalid_format()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_catalyst(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause                | filePath                       | expect
        "invalid hierarchy1" | "/json/catalyst/ng/01/01.json" | 想定結果_異常系_catalyst_invalid_hierarchy1()
        "invalid hierarchy2" | "/json/catalyst/ng/01/02.json" | 想定結果_異常系_catalyst_invalid_hierarchy2()
        "invalid hierarchy3" | "/json/catalyst/ng/01/03.json" | 想定結果_異常系_catalyst_invalid_hierarchy3()
        "invalid type1"      | "/json/catalyst/ng/02/01.json" | 想定結果_異常系_catalyst_invalid_type1()
        "invalid type2"      | "/json/catalyst/ng/02/02.json" | 想定結果_異常系_catalyst_invalid_type2()
        "invalid type3"      | "/json/catalyst/ng/02/03.json" | 想定結果_異常系_catalyst_invalid_type3()
        "invalid type4"      | "/json/catalyst/ng/02/04.json" | 想定結果_異常系_catalyst_invalid_type4()
        "expect not found"   | "/json/catalyst/ng/03/01.json" | 想定結果_異常系_catalyst_expect_not_found()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_k3c(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause                | filePath                  | expect
        "invalid hierarchy1" | "/json/k3c/ng/01/01.json" | 想定結果_異常系_k3c_invalid_hierarchy1()
        "invalid hierarchy2" | "/json/k3c/ng/01/02.json" | 想定結果_異常系_k3c_invalid_hierarchy2()
        "invalid hierarchy3" | "/json/k3c/ng/01/03.json" | 想定結果_異常系_k3c_invalid_hierarchy3()
        "invalid type1"      | "/json/k3c/ng/02/01.json" | 想定結果_異常系_k3c_invalid_type1()
        "invalid type2"      | "/json/k3c/ng/02/02.json" | 想定結果_異常系_k3c_invalid_type2()
        "invalid type3"      | "/json/k3c/ng/02/03.json" | 想定結果_異常系_k3c_invalid_type3()
        "invalid type4"      | "/json/k3c/ng/02/04.json" | 想定結果_異常系_k3c_invalid_type4()
        "expect not found"   | "/json/k3c/ng/03/01.json" | 想定結果_異常系_k3c_expect_not_found()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_meta(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause                | filePath                   | expect
        "invalid hierarchy1" | "/json/meta/ng/01/01.json" | 想定結果_異常系_meta_invalid_hierarchy1()
        "invalid hierarchy2" | "/json/meta/ng/01/02.json" | 想定結果_異常系_meta_invalid_hierarchy2()
        "invalid type1"      | "/json/meta/ng/02/01.json" | 想定結果_異常系_meta_invalid_type1()
        "invalid type2"      | "/json/meta/ng/02/02.json" | 想定結果_異常系_meta_invalid_type2()
        "invalid type3"      | "/json/meta/ng/02/03.json" | 想定結果_異常系_meta_invalid_type3()
        "invalid type4"      | "/json/meta/ng/02/04.json" | 想定結果_異常系_meta_invalid_type4()
        "expect not found"   | "/json/meta/ng/03/01.json" | 想定結果_異常系_meta_expect_not_found()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_browsererror(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                           | expect
        "invalid hierarchy" | "/json/browsererror/ng/01/01.json" | 想定結果_異常系_browsererror_invalid_hierarchy()
        "invalid type"      | "/json/browsererror/ng/02/01.json" | 想定結果_異常系_browsererror_invalid_type()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_testsuite(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                        | expect
        "required"          | "/json/testsuite/ng/01/01.json" | 想定結果_異常系_testsuite_required()
        "invalid hierarchy" | "/json/testsuite/ng/02/01.json" | 想定結果_異常系_testsuite_invalid_hierarchy()
        "invalid type1"     | "/json/testsuite/ng/03/01.json" | 想定結果_異常系_testsuite_invalid_type1()
        "invalid type2"     | "/json/testsuite/ng/03/02.json" | 想定結果_異常系_testsuite_invalid_type2()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_input(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause                | filePath                    | expect
        "invalid hierarchy1" | "/json/input/ng/01/01.json" | 想定結果_異常系_input_invalid_hierarchy1()
        "invalid hierarchy2" | "/json/input/ng/01/02.json" | 想定結果_異常系_input_invalid_hierarchy2()
        "invalid type1"      | "/json/input/ng/02/01.json" | 想定結果_異常系_input_invalid_type1()
        "invalid type2"      | "/json/input/ng/02/02.json" | 想定結果_異常系_input_invalid_type2()
        "invalid type3"      | "/json/input/ng/02/03.json" | 想定結果_異常系_input_invalid_type3()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_click(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                    | expect
        "invalid hierarchy" | "/json/click/ng/01/01.json" | 想定結果_異常系_click_invalid_hierarchy()
        "invalid type1"     | "/json/click/ng/02/01.json" | 想定結果_異常系_click_invalid_type1()
        "invalid type2"     | "/json/click/ng/02/02.json" | 想定結果_異常系_click_invalid_type2()
        "expect not found1" | "/json/click/ng/03/01.json" | 想定結果_異常系_click_expect_not_found1()
        "expect not found2" | "/json/click/ng/03/02.json" | 想定結果_異常系_click_expect_not_found2()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_title(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                    | expect
        "invalid hierarchy" | "/json/title/ng/02/01.json" | 想定結果_異常系_title_invalid_hierarchy()
        "invalid type"      | "/json/title/ng/01/01.json" | 想定結果_異常系_title_invalid_type()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_attr(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                   | expect
        "invalid hierarchy" | "/json/attr/ng/01/01.json" | 想定結果_異常系_attr_invalid_hierarchy()
        "invalid type1"     | "/json/attr/ng/02/01.json" | 想定結果_異常系_attr_invalid_type1()
        "invalid type1"     | "/json/attr/ng/02/02.json" | 想定結果_異常系_attr_invalid_type2()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_expect(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                     | expect
        "invalid hierarchy" | "/json/expect/ng/01/01.json" | 想定結果_異常系_expect_invalid_hierarchy()
        "invalid type"      | "/json/expect/ng/02/01.json" | 想定結果_異常系_expect_invalid_type()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_expect_all(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                         | expect
        "invalid hierarchy" | "/json/expect_all/ng/01/01.json" | 想定結果_異常系_expect_all_invalid_hierarchy()
        "invalid type"      | "/json/expect_all/ng/02/01.json" | 想定結果_異常系_expect_all_invalid_type()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_not_expect(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                         | expect
        "invalid hierarchy" | "/json/not_expect/ng/01/01.json" | 想定結果_異常系_not_expect_invalid_hierarchy()
        "invalid type"      | "/json/not_expect/ng/02/01.json" | 想定結果_異常系_not_expect_invalid_type()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_not_expect_all(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                             | expect
        "invalid hierarchy" | "/json/not_expect_all/ng/01/01.json" | 想定結果_異常系_not_expect_all_invalid_hierarchy()
        "invalid type"      | "/json/not_expect_all/ng/02/01.json" | 想定結果_異常系_not_expect_all_invalid_type()
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_jsonpath(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                       | expect
        "invalid hierarchy" | "/json/jsonpath/ng/01/01.json" | 想定結果_異常系_jsonpath_invalid_hierarchy()
        "invalid type"      | "/json/jsonpath/ng/02/01.json" | 想定結果_異常系_jsonpath_invalid_type()
    }

    def テストファイルから生成されるオブジェクトの構造が正しくないこと_useragent() {
        expect:
        想定結果_異常系_useragent() == テストケースリスト("/json/useragent/ng/01/01.json")
    }

    @Unroll
    "テストファイルから生成されるオブジェクトの構造が正しくないこと_case_sensitive(#cause)"() {
        expect:
        expect == テストケースリスト(filePath)

        where:
        cause               | filePath                             | expect
        "invalid hierarchy" | "/json/case_sensitive/ng/01/01.json" | 想定結果_異常系_case_sensitive_invalid_hierarchy()
        "invalid type"      | "/json/case_sensitive/ng/02/01.json" | 想定結果_異常系_case_sensitive_invalid_type()
    }
}

class JSONファイル生成処理_正常系 extends Specification {
    @Unroll
    "テストファイルの生成に成功すること(#event)"() {
        setup:
        def compile = new CompileTest()

        expect:
        compile.testcases(['path': ClassLoaderUtil.getResource(filePath).path]) == 想定結果_正常系_テスト生成()

        where:
        event               | filePath
        "testcase(uitest)"  | "/json/compile/ok/01/01.json"
        "meta"              | "/json/compile/ok/01/02.json"
        "catalyst"          | "/json/compile/ok/01/03.json"
        "k3c"               | "/json/compile/ok/01/04.json"
        "testcase(restapi)" | "/json/compile/ok/01/05.json"
    }
}

class JSONファイル生成処理_異常系 extends Specification {
    @Unroll
    "テストファイルの生成に失敗すること(#cause)"() {
        setup:
        def compile = new CompileTest()

        expect:
        compile.testcases(['path': path]) == expect

        where:
        cause                  | path                                                            || expect
        "testfile not found"   | "dummy.json"                                                    || 想定結果_異常系_テスト生成_testfile_not_found()
        "invalid testfile"     | ClassLoaderUtil.getResource("/json/compile/ng/01/01.json").path || 想定結果_異常系_テスト生成_invalid_testfile()
        "invalid file format1" | ClassLoaderUtil.getResource("/json/compile/ng/02/01.json").path || 想定結果_異常系_テスト生成_invalid_file_format1()
        "invalid file format2" | ClassLoaderUtil.getResource("/json/compile/ng/02/02.json").path || 想定結果_異常系_テスト生成_invalid_file_format2()
    }
}
