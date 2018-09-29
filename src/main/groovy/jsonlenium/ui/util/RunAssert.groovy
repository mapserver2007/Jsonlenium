package jsonlenium.ui.util

import jsonlenium.constant.Driver
import jsonlenium.constant.TestState
import groovy.json.JsonException
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.json.StringEscapeUtils
import jsonlenium.util.Message
import org.codehaus.groovy.tools.Utilities
import org.openqa.selenium.logging.LogEntries

import java.util.logging.Level
import java.util.regex.Matcher
import java.util.regex.Pattern

class RunAssert {
    private String message
    private TestState state

    def getState() {
        return state
    }

    def getMessage() {
        return message
    }

    def normalize(String s) {
        if (s == null || s.empty) {
            return s
        }
        s = s.replaceAll(/\r\n|\n|\r/, "\\\\n")
        // When input a wavedash (UTF-8:0x301C and Shift_JIS:0x8160),
        // replace it with full width tilde(UTF-8:0xFF5E).
        // Because it ignores trivial differences due to differences in character codes at creating test case.
        char[] c = s.toCharArray()
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 0x301C || c[i] == 0x8160) {
                c[i] = 0xFF5E
            }
        }
        c.toString()
    }

    def assertion(Map<String, ?> testcase) {
        assertion(testcase, Optional.ofNullable(null))
    }

    def assertion(Map<String, ?> testcase, String actual) {
        assertion(testcase, Optional.ofNullable(actual))
    }

    def assertion(Map<String, ?> testcase, Optional<String> nullableActual) {
        def actual = normalize(nullableActual.orElse(null))
        testcase.expect = normalize(testcase.expect)

        def assertMessage
        if (testcase.attr != null) {
            if (testcase.isCaseSensitive) {
                assertMessage = sprintf(testcase.isNot ? Message.ASSERTION_RESULT_04.toString() : Message.ASSERTION_RESULT_02.toString(),
                    testcase.expect, actual, testcase.attr, testcase.selector ?: testcase.xpath ?: testcase.jsonpath)
            } else {
                assertMessage = sprintf(testcase.isNot ? Message.ASSERTION_RESULT_08.toString() : Message.ASSERTION_RESULT_06.toString(),
                    testcase.expect, actual, testcase.attr, testcase.selector ?: testcase.xpath ?: testcase.jsonpath, testcase.isCaseSensitive)
            }
        } else {
            if (testcase.isCaseSensitive) {
                assertMessage = sprintf(testcase.isNot ? Message.ASSERTION_RESULT_03.toString() : Message.ASSERTION_RESULT_01.toString(),
                    testcase.expect, actual, testcase.selector ?: testcase.xpath ?: testcase.jsonpath)
            } else {
                assertMessage = sprintf(testcase.isNot ? Message.ASSERTION_RESULT_07.toString() : Message.ASSERTION_RESULT_05.toString(),
                    testcase.expect, actual, testcase.selector ?: testcase.xpath ?: testcase.jsonpath, testcase.isCaseSensitive)
            }
        }
        if (testcase.isRegex) {
            def regexp = Pattern.compile(testcase.expect)
            Matcher matches = actual =~ regexp
            if (testcase.isNot) {
                if (matches.count == 0) {
                    state = TestState.OK
                    message = "${Message.ASSERTION_SUCCESS} ${assertMessage}"
                } else {
                    state = TestState.NG
                    message = "${Message.ASSERTION_FAILURE} ${assertMessage}"
                }
            } else {
                if (matches.count > 0) {
                    state = TestState.OK
                    message = "${Message.ASSERTION_SUCCESS} ${assertMessage}"
                } else {
                    state = TestState.NG
                    message = "${Message.ASSERTION_FAILURE} ${assertMessage}"
                }
            }
        } else {
            def isOK
            if (testcase.isNot) {
                if (testcase.isCaseSensitive) {
                    isOK = actual != testcase.expect
                } else {
                    isOK = !actual?.equalsIgnoreCase(testcase.expect)
                }
            } else {
                if (testcase.isCaseSensitive) {
                    isOK = actual == testcase.expect
                } else {
                    isOK = actual?.equalsIgnoreCase(testcase.expect)
                }
            }

            if (isOK) {
                state = TestState.OK
                message = "${Message.ASSERTION_SUCCESS} ${assertMessage}"
            } else {
                state = TestState.NG
                message = "${Message.ASSERTION_FAILURE} ${assertMessage}"
            }
        }
    }

    def assertion(Map<String, ?> testcase, List<Map<String, ?>> actual) {
        if (!testcase.isRegex && testcase.expect != null) {
            try {
                testcase.expect = StringEscapeUtils.unescapeJava(JsonOutput.toJson(new JsonSlurper().parseText(testcase.expect)))
            } catch (JsonException ignore) {
                // nothing to do
            }
        }
        assertion(testcase, Optional.ofNullable(StringEscapeUtils.unescapeJava(JsonOutput.toJson(actual))))
    }

    def assertion(Map<String, ?> testcase, Map<String, ?> actual) {
        if (!testcase.isRegex && testcase.expect != null) {
            try {
                testcase.expect = StringEscapeUtils.unescapeJava(JsonOutput.toJson(new JsonSlurper().parseText(testcase.expect)))
            } catch (JsonException ignore) {
                // nothing to do
            }
        }
        assertion(testcase, Optional.ofNullable(StringEscapeUtils.unescapeJava(JsonOutput.toJson(actual))))
    }

    def assertion(Map<String, ?> testcase, String driver, LogEntries logEntries) {
        def filteredLogEntries
        switch (driver) {
            case Driver.CHROME:
            case Driver.HEADLESS_CHROME:
                filteredLogEntries = logEntries.filter(Level.SEVERE)
                break
            case Driver.PHANTOM_JS:
                filteredLogEntries = logEntries.filter(Level.WARNING)
                break
            default:
                filteredLogEntries = logEntries.toList()
        }

        if (filteredLogEntries.any()) {
            def eol = Utilities.eol()
            state = TestState.NG
            message = "${Message.ASSERTION_FAILURE} ${Message.ASSERTION_EXIST_BROWSER_ERROR} (${testcase.url})${eol}\t${filteredLogEntries.join(eol)}"
        } else {
            state = TestState.OK
            message = "${Message.ASSERTION_SUCCESS} ${Message.ASSERTION_NOT_EXIST_BROWSER_ERROR} (${testcase.url})"
        }
    }
}
