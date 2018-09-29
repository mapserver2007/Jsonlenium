package jsonlenium.spock.fixture

import jsonlenium.build.util.EventFactory
import jsonlenium.constant.Event

class CompileTestFixture {
    static List<File> JSONファイルリスト_ファイル指定() {
        def factory = new EventFactory()
        factory.loadTestCase()
        factory.getTestFileList(Class.getResource("/json/testcase/ok/01/01.json").path)
    }

    static List<File> JSON存在しないファイルリスト_ファイル指定() {
        def factory = new EventFactory()
        factory.loadTestCase()
        factory.getTestFileList("dummy.json")
    }

    static List<File> JSONファイルリスト_ディレクトリ指定() {
        def factory = new EventFactory()
        factory.loadTestCase()
        factory.getTestFileList(Class.getResource("/json").path)
    }

    static テストケースリスト(String filePath) {
        def factory = new EventFactory()
        factory.loadTestCase()
        def fileList = factory.getTestFileList(Class.getResource(filePath).path)
        def testcases = new ArrayList<HashMap<String, ?>>()
        factory.createTestCase(fileList) { List<?> testcase, boolean isCreateSuccess ->
            testcases.addAll(testcase)
        }
        testcases
    }
}

class CompileTestExpectFixture {
    static 想定結果_正常系_testcase_selector() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/01/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample selector] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "sample selector",
                        'xpath'          : null,
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_xpath() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/01/02.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample xpath] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'xpath'          : "sample xpath",
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_attr() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/03/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample selector] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "sample selector",
                        'xpath'          : null,
                        'attr'           : "sample attribute",
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_expect_all() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/04/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample selector] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "sample selector",
                        'xpath'          : null,
                        'event'          : Event.UI_TEST,
                        'attr'           : null,
                        'expect'         : "sample expectation values",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : false
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_regexp() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/06/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample selector] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "sample selector",
                        'xpath'          : null,
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "(?s)value",
                        'isNot'          : false,
                        'isRegex'        : true,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_selector_and_xpath() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/07/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "selector value",
                        'xpath'          : "xpath value",
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "value",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_not_expect() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/08/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "selector value",
                        'xpath'          : null,
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "value",
                        'isNot'          : true,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_not_expect_all() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/09/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "selector value",
                        'xpath'          : null,
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "value",
                        'isNot'          : true,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : false
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_meta() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/11/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] [meta(1/2)] [meta[name='key1'],meta[property='key1']] (${canonicalPath})",
            'testcase': [
                'url'       : "http://example.com/",
                'events'    : [
                    [
                        'selector': "selector value",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : "meta[name='key1'],meta[property='key1']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 31
            ]
        ]
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] [meta(2/2)] [meta[name='key2'],meta[property='key2']] (${canonicalPath})",
            'testcase': [
                'url'       : "http://example.com/",
                'events'    : [
                    [
                        'selector': "selector value",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : "meta[name='key2'],meta[property='key2']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 31
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_catalyst() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/12/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] [catalyst(1/2)] [s.key1] (${canonicalPath})",
            'testcase': [
                'url'       : "http://example.com/",
                'events'    : [
                    [
                        'selector': "selector value",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : null,
                        'attr'           : "s.key1",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 40
            ]
        ]
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] [catalyst(2/2)] [s.key2] (${canonicalPath})",
            'testcase': [
                'url'       : "http://example.com/",
                'events'    : [
                    [
                        'selector': "selector value",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : null,
                        'attr'           : "s.key2",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 40
            ]
        ]
        testcases
    }

    static 想定結果_正常系_testcase_k3c() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/13/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] [k3c(1/2)] [k3c.atrack.val.key1] (${canonicalPath})",
            'testcase': [
                'url'       : "http://example.com/",
                'events'    : [
                    [
                        'selector': "selector value",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key1",
                        'event'          : Event.K3C,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 40
            ]
        ]
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [selector value] [k3c(2/2)] [k3c.atrack.val.key2] (${canonicalPath})",
            'testcase': [
                'url'       : "http://example.com/",
                'events'    : [
                    [
                        'selector': "selector value",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key2",
                        'event'          : Event.K3C,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 40
            ]
        ]
        testcases
    }

    static 想定結果_正常系_meta_selector() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ok/01/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(1/2)] [meta[name='key1'],meta[property='key1']] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key1'],meta[property='key1']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(2/2)] [meta[name='key2'],meta[property='key2']] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key2'],meta[property='key2']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases
    }

    static 想定結果_正常系_meta_regexp() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ok/03/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(1/2)] [meta[name='key1'],meta[property='key1']] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key1'],meta[property='key1']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(2/2)] [meta[name='key2'],meta[property='key2']] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key2'],meta[property='key2']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "(?s)value2",
                        'isRegex'        : true,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases
    }

    static 想定結果_正常系_catalyst_selector() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ok/01/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(1/2)] [s.key1] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key1",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(2/2)] [s.key2] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key2",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_catalyst_regexp() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ok/05/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(1/2)] [s.key1] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key1",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(2/2)] [s.key2] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key2",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "(?s)value2",
                        'isRegex'        : true,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_catalyst_jsvar() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ok/03/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst] [s.enable_jsvar_value] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.enable_jsvar_value",
                        'event'          : Event.CATALYST,
                        'jsvar'          : true,
                        'expect'         : "s.prop1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_catalyst_not_jsvar() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ok/04/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst] [s.begin 's.' value] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.begin 's.' value",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "s.kakaku.com",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_k3c_selector() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ok/01/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(1/2)] [k3c.atrack.val.key1] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key1",
                        'event'          : Event.K3C,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(2/2)] [k3c.atrack.val.key2] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key2",
                        'event'          : Event.K3C,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_k3c_regexp() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ok/03/01.json").path).canonicalPath
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(1/2)] [k3c.atrack.val.key1] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key1",
                        'event'          : Event.K3C,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(2/2)] [k3c.atrack.val.key2] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key2",
                        'event'          : Event.K3C,
                        'expect'         : "(?s)value2",
                        'isRegex'        : true,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_browsererror() {
        def canonicalPath = new File(Class.getResource("/json/browsererror/ok/01/01.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [browser_error] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'url'  : "http://sample.com/",
                            'event': Event.BROWSER_ERROR
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 5
                ]
            ]
        ]
    }

    static 想定結果_正常系_input() {
        def canonicalPath = new File(Class.getResource("/json/input/ok/01/01.json").path).canonicalPath
        [
            [
                'title'   : "[input] [testsuite] [ui_test] [sample selector2] [sample selector] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'selector': "sample selector2",
                            'xpath'   : null,
                            'value'   : "sample value",
                            'event'   : Event.INPUT
                        ],
                        [
                            'selector'       : "sample selector",
                            'xpath'          : null,
                            'attr'           : null,
                            'event'          : Event.UI_TEST,
                            'expect'         : 'sample expectation value',
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true,
                            'anyMatch'       : true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 7
                ]
            ]
        ]
    }

    static 想定結果_正常系_click_selector() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ok/01/01.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample selector] [sample selector from click] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector': "sample selector",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : "sample selector from click",
                        'xpath'          : null,
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value from click",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 32
            ]
        ]
        testcases
    }

    static 想定結果_正常系_click_selector_attr() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ok/01/02.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample selector] [sample selector from click] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector': "sample selector",
                        'xpath'   : null,
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : "sample selector from click",
                        'xpath'          : null,
                        'attr'           : "sample attr from click",
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value from click",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 32
            ]
        ]
        testcases
    }

    static 想定結果_正常系_click_xpath() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ok/01/03.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample xpath] [sample xpath from click] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector': null,
                        'xpath'   : "sample xpath",
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : null,
                        'xpath'          : "sample xpath from click",
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value from click",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 32
            ]
        ]
        testcases
    }

    static 想定結果_正常系_click_xpath_attr() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ok/01/04.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample xpath] [sample xpath from click] (${canonicalPath})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector': null,
                        'xpath'   : "sample xpath",
                        'attr'    : null,
                        'event'   : Event.CLICK
                    ],
                    [
                        'selector'       : null,
                        'xpath'          : "sample xpath from click",
                        'attr'           : "sample attr from click",
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value from click",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 32
            ]
        ]
        testcases
    }

    static 想定結果_正常系_jsonpath_selector() {
        def canonicalPath = new File(Class.getResource("/json/jsonpath/ok/01/01.json").path).canonicalPath
        [
            [
                'title': "[jsonapi] [testsuite] [rest_api_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url': "http://sample.com/",
                    'events': [
                        [
                            'jsonpath'       : "sample path",
                            'event'          : Event.REST_API_TEST,
                            'expect'         : "sample value",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 1
                ]
            ]
        ]
    }

    static 想定結果_正常系_jsonpath_expect_all() {
        def canonicalPath = new File(Class.getResource("/json/jsonpath/ok/02/01.json").path).canonicalPath
        [
            [
                'title'   : "[jsonapi] [testsuite] [rest_api_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'jsonpath'       : "sample path",
                            'event'          : Event.REST_API_TEST,
                            'expect'         : "sample value",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 1
                ]
            ]
        ]
    }

    static 想定結果_正常系_jsonpath_not_expect() {
        def canonicalPath = new File(Class.getResource("/json/jsonpath/ok/03/01.json").path).canonicalPath
        [
            [
                'title'   : "[jsonapi] [testsuite] [rest_api_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'jsonpath'       : "sample path",
                            'event'          : Event.REST_API_TEST,
                            'expect'         : "sample value",
                            'isNot'          : true,
                            'isRegex'        : false,
                            'isCaseSensitive': true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 1
                ]
            ]
        ]
    }

    static 想定結果_正常系_jsonpath_not_expect_all() {
        def canonicalPath = new File(Class.getResource("/json/jsonpath/ok/04/01.json").path).canonicalPath
        [
            [
                'title'   : "[jsonapi] [testsuite] [rest_api_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'jsonpath'       : "sample path",
                            'event'          : Event.REST_API_TEST,
                            'expect'         : "sample value",
                            'isNot'          : true,
                            'isRegex'        : false,
                            'isCaseSensitive': true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 1
                ]
            ]
        ]
    }

    static 想定結果_正常系_useragent() {
        def canonicalPath = new File(Class.getResource("/json/useragent/ok/01/01.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [ui_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'selector'       : "sample path",
                            'xpath'          : null,
                            'attr'           : null,
                            'event'          : Event.UI_TEST,
                            'expect'         : "result",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true,
                            'anyMatch'       : true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : "UserAgent",
                    'cacheclear': true,
                    'weight'    : 52
                ]
            ]
        ]
    }

    static 想定結果_正常系_case_sensitive_default() {
        def canonicalPath = new File(Class.getResource("/json/case_sensitive/ok/01/01.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [ui_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://example.com/",
                    'events'    : [
                        [
                            'selector'       : "sample path",
                            'xpath'          : null,
                            'attr'           : null,
                            'event'          : Event.UI_TEST,
                            'expect'         : "result",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true,
                            'anyMatch'       : true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 2
                ]
            ]
        ]
    }

    static 想定結果_正常系_case_sensitive_enabled() {
        def canonicalPath = new File(Class.getResource("/json/case_sensitive/ok/01/02.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [ui_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://example.com/",
                    'events'    : [
                        [
                            'selector'       : "sample path",
                            'xpath'          : null,
                            'attr'           : null,
                            'event'          : Event.UI_TEST,
                            'expect'         : "result",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true,
                            'anyMatch'       : true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 2
                ]
            ]
        ]
    }

    static 想定結果_正常系_case_sensitive_disabled() {
        def canonicalPath = new File(Class.getResource("/json/case_sensitive/ok/01/03.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [ui_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://example.com/",
                    'events'    : [
                        [
                            'selector'       : "sample path",
                            'xpath'          : null,
                            'attr'           : null,
                            'event'          : Event.UI_TEST,
                            'expect'         : "result",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': false,
                            'anyMatch'       : true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 2
                ]
            ]
        ]
    }

    static 想定結果_正常系_複数ファイル_testcase() {
        def testcases = []
        def canonicalPath1 = new File(Class.getResource("/json/testcase/ok/02/01.json").path).canonicalPath
        def canonicalPath2 = new File(Class.getResource("/json/testcase/ok/02/02.json").path).canonicalPath
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample selector] (${canonicalPath1})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "sample selector",
                        'xpath'          : null,
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath1,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases << [
            'title'   : "[test] [testsuite] [ui_test] [sample xpath] (${canonicalPath2})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'xpath'          : "sample xpath",
                        'attr'           : null,
                        'event'          : Event.UI_TEST,
                        'expect'         : "sample expectation value",
                        'isNot'          : false,
                        'isRegex'        : false,
                        'isCaseSensitive': true,
                        'anyMatch'       : true
                    ]
                ],
                'src'       : canonicalPath2,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 2
            ]
        ]
        testcases
    }

    static 想定結果_正常系_複数ファイル_meta() {
        def testcases = []
        def canonicalPath1 = new File(Class.getResource("/json/meta/ok/02/01.json").path).canonicalPath
        def canonicalPath2 = new File(Class.getResource("/json/meta/ok/02/02.json").path).canonicalPath
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(1/2)] [meta[name='key1'],meta[property='key1']] (${canonicalPath1})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key1'],meta[property='key1']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath1,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(2/2)] [meta[name='key2'],meta[property='key2']] (${canonicalPath1})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key2'],meta[property='key2']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath1,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(1/2)] [meta[name='key3'],meta[property='key3']] (${canonicalPath2})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key3'],meta[property='key3']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value3",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath2,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases << [
            'title'   : "[testmeta] [testsuite] [meta(2/2)] [meta[name='key4'],meta[property='key4']] (${canonicalPath2})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : "meta[name='key4'],meta[property='key4']",
                        'attr'           : "content",
                        'event'          : Event.META,
                        'expect'         : "value4",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath2,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 1
            ]
        ]
        testcases
    }

    static 想定結果_正常系_複数ファイル_catalyst() {
        def testcases = []
        def canonicalPath1 = new File(Class.getResource("/json/catalyst/ok/02/01.json").path).canonicalPath
        def canonicalPath2 = new File(Class.getResource("/json/catalyst/ok/02/02.json").path).canonicalPath
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(1/2)] [s.key1] (${canonicalPath1})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key1",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath1,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(2/2)] [s.key2] (${canonicalPath1})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key2",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath1,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(1/2)] [s.key3] (${canonicalPath2})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key3",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value3",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath2,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testcatalyst] [testsuite] [catalyst(2/2)] [s.key4] (${canonicalPath2})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "s.key4",
                        'event'          : Event.CATALYST,
                        'jsvar'          : false,
                        'expect'         : "value4",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath2,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_複数ファイル_k3c() {
        def testcases = []
        def canonicalPath1 = new File(Class.getResource("/json/k3c/ok/02/01.json").path).canonicalPath
        def canonicalPath2 = new File(Class.getResource("/json/k3c/ok/02/02.json").path).canonicalPath
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(1/2)] [k3c.atrack.val.key1] (${canonicalPath1})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key1",
                        'event'          : Event.K3C,
                        'expect'         : "value1",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath1,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(2/2)] [k3c.atrack.val.key2] (${canonicalPath1})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key2",
                        'event'          : Event.K3C,
                        'expect'         : "value2",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath1,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(1/2)] [k3c.atrack.val.key3] (${canonicalPath2})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key3",
                        'event'          : Event.K3C,
                        'expect'         : "value3",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath2,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases << [
            'title'   : "[testk3c] [testsuite] [k3c(2/2)] [k3c.atrack.val.key4] (${canonicalPath2})",
            'testcase': [
                'url'       : "http://sample.com/",
                'events'    : [
                    [
                        'selector'       : null,
                        'attr'           : "k3c.atrack.val.key4",
                        'event'          : Event.K3C,
                        'expect'         : "value4",
                        'isRegex'        : false,
                        'isCaseSensitive': true
                    ]
                ],
                'src'       : canonicalPath2,
                'useragent' : null,
                'cacheclear': false,
                'weight'    : 10
            ]
        ]
        testcases
    }

    static 想定結果_正常系_想定結果null_selector() {
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/10/01.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [ui_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'selector'       : "sample path",
                            'xpath'          : null,
                            'attr'           : null,
                            'event'          : Event.UI_TEST,
                            'expect'         : "result",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true,
                            'anyMatch'       : true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 2
                ]
            ]
        ]
    }

    static 想定結果_正常系_想定結果null_xpath() {
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/10/02.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [ui_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'selector'       : null,
                            'xpath'          : "sample path",
                            'attr'           : null,
                            'event'          : Event.UI_TEST,
                            'expect'         : "result",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true,
                            'anyMatch'       : true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 2
                ]
            ]
        ]
    }

    static 想定結果_正常系_想定結果null_jsonpath() {
        def canonicalPath = new File(Class.getResource("/json/testcase/ok/10/03.json").path).canonicalPath
        [
            [
                'title'   : "[test] [testsuite] [rest_api_test] [sample path] (${canonicalPath})",
                'testcase': [
                    'url'       : "http://sample.com/",
                    'events'    : [
                        [
                            'jsonpath'       : "sample path",
                            'event'          : Event.REST_API_TEST,
                            'expect'         : "result",
                            'isNot'          : false,
                            'isRegex'        : false,
                            'isCaseSensitive': true
                        ]
                    ],
                    'src'       : canonicalPath,
                    'useragent' : null,
                    'cacheclear': false,
                    'weight'    : 1
                ]
            ]
        ]
    }

    static 想定結果_異常系_testcase_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].testcase'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase'はarray型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_empty_element() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/03/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase'の要素を1つ以上記述してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_selector_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/04/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].selector'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_selector_invalid_attr_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/05/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].attr'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_selector_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/06/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].selector'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_xpath_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/07/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].xpath'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_xpath_invalid_attr_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/08/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].attr'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_xpath_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/09/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].xpath'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testcase_expect_not_found() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testcase/ng/10/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0]'に対して'expect','expect_all','not_expect','not_expect_all'のいずれかの指定は必須です(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_url_required() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/url/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'url'の指定は必須です(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_url_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/url/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].url'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_url_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/url/ng/03/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].url'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_url_invalid_format() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/url/ng/04/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].url'の値'invalid url'は正しくありません(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_invalid_hierarchy1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'prop1'を指定することはできません(原因:未定義の属性です)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_invalid_hierarchy2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/01/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].catalyst[1].catalyst'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_invalid_hierarchy3() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/01/03.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].name'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_invalid_type1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].catalyst[1].expect'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_invalid_type2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/02/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].catalyst'はarray型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_invalid_type3() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/02/03.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].catalyst'の要素を1つ以上記述してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_invalid_type4() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/02/04.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].catalyst[0].name'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_catalyst_expect_not_found() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/catalyst/ng/03/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].catalyst[1]'に対して'expect','not_expect'のいずれかの指定は必須です(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_invalid_hierarchy1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'prop1'を指定することはできません(原因:未定義の属性です)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_invalid_hierarchy2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/01/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].k3c[1].k3c'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_invalid_hierarchy3() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/01/03.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].name'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_invalid_type1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].k3c[1].expect'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_invalid_type2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/02/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].k3c'はarray型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_invalid_type3() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/02/03.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].k3c'の要素を1つ以上記述してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_invalid_type4() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/02/04.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].k3c[0].name'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_k3c_expect_not_found() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/k3c/ng/03/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].k3c[1]'に対して'expect','not_expect'のいずれかの指定は必須です(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_meta_invalid_hierarchy1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].meta[1].meta'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_meta_invalid_hierarchy2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ng/01/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].name'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_meta_invalid_type1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].meta[1].expect'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_meta_invalid_type2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ng/02/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].meta'はarray型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_meta_invalid_type3() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ng/02/03.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].meta'の要素を1つ以上記述してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_meta_invalid_type4() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ng/02/04.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].meta[0].name'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_meta_expect_not_found() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/meta/ng/03/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].meta[1]'に対して'expect','not_expect'のいずれかの指定は必須です(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_browsererror_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/browsererror/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].browsererror'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_browsererror_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/browsererror/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].browsererror'はboolean型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testsuite_required() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testsuite/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'testsuite'の指定は必須です(${canonicalPath})",
                "'url'の指定は必須です(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testsuite_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testsuite/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testsuite'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
                "'url'の指定は必須です(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testsuite_invalid_type1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testsuite/ng/03/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite'はarray型で指定してください(${canonicalPath})",
                "'url'の指定は必須です(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_testsuite_invalid_type2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/testsuite/ng/03/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite'の要素を1つ以上記述してください(${canonicalPath})",
                "'url'の指定は必須です(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_input_invalid_hierarchy1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/input/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].input'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_input_invalid_hierarchy2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/input/ng/01/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].value'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_input_invalid_type1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/input/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].input'はarray型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_input_invalid_type2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/input/ng/02/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].input'の要素を1つ以上記述してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_input_invalid_type3() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/input/ng/02/03.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].input[0].value'はstring型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_click_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].click'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_click_invalid_type1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].click'はobject型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_click_invalid_type2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ng/02/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].click'の要素を1つ以上記述してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_click_expect_not_found1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ng/03/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].click'に対して'expect','expect_all','not_expect','not_expect_all'のいずれかの指定は必須です(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_click_expect_not_found2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/click/ng/03/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].click.click'に対して'expect','expect_all','not_expect','not_expect_all'のいずれかの指定は必須です(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_title_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/title/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].title'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_title_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/title/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.title'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_attr_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/attr/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].attr'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_attr_invalid_type1() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/attr/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].attr'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_attr_invalid_type2() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/attr/ng/02/02.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].attr'はstring型で指定してください(${canonicalPath})"
            ]
        ]
        testcases
    }

    static 想定結果_異常系_expect_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/expect/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].expect'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_expect_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/expect/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].expect'はstring型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_expect_all_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/expect_all/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].expect_all'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_expect_all_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/expect_all/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].expect_all'はstring型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_not_expect_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/not_expect/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].not_expect'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_not_expect_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/not_expect/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].not_expect'はstring型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_not_expect_all_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/not_expect_all/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].not_expect_all'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_not_expect_all_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/not_expect_all/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].not_expect_all'はstring型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_jsonpath_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/jsonpath/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].jsonpath'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_jsonpath_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/jsonpath/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].jsonpath'はstring型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_useragent() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/useragent/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.useragent'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_case_sensitive_invalid_hierarchy() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/case_sensitive/ng/01/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].case_sensitive'を指定することはできません(原因:この階層では使用できません)(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_異常系_case_sensitive_invalid_type() {
        def testcases = []
        def canonicalPath = new File(Class.getResource("/json/case_sensitive/ng/02/01.json").path).canonicalPath
        testcases << [
            'errorMessages': [
                "'\$.testsuite[0].testcase[0].case_sensitive'はboolean型で指定してください(${canonicalPath})",
            ]
        ]
        testcases
    }

    static 想定結果_正常系_テスト生成() {
        [
            [
                'title': "テストケース作成結果: テストケースを作成しました(成功: 4, 失敗: 0)",
                'result': true
            ]
        ]
    }

    static 想定結果_異常系_テスト生成_testfile_not_found() {
        [
            [
                'title': "テストケース作成結果: JSONテストファイルが見つかりませんでした",
                'result' :false
            ]
        ]
    }

    static 想定結果_異常系_テスト生成_invalid_testfile() {
        [
            [
                'title': "テストケース作成結果: テストファイルに4個の誤りがあります",
                'result' :false
            ]
        ]
    }

    static 想定結果_異常系_テスト生成_invalid_file_format1() {
        [
            [
                'title': "テストケース作成結果: テストファイルに1個の誤りがあります",
                'result' :false
            ]
        ]
    }

    static 想定結果_異常系_テスト生成_invalid_file_format2() {
        [
            [
                'title': "テストケース作成結果: テストファイルに3個の誤りがあります",
                'result' :false
            ]
        ]
    }
}
