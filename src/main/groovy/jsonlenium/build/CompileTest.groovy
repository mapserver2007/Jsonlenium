package jsonlenium.build

import groovyx.net.http.URIBuilder
import jsonlenium.build.util.EventFactory
import jsonlenium.build.util.MessageLocale
import jsonlenium.build.util.TestFileGenerator
import jsonlenium.constant.JsonleniumEnv
import jsonlenium.ui.Jsonlenium
import jsonlenium.util.Message
import org.codehaus.groovy.tools.Utilities
import spock.lang.Ignore
import spock.lang.Unroll

class CompileTest extends Jsonlenium {
    @Unroll
    "#item.title"(item) {
        expect:
        item.result

        where:
        item << testcases([
            'path': System.getProperty(JsonleniumEnv.PATH),
            'url' : System.getProperty(JsonleniumEnv.URL),
            'project_dir': System.getProperty(JsonleniumEnv.PROJECT_DIR)
        ])
    }

    @Ignore
    testcases(env) {
        def testData
        def testcases = []
        def failureTestcases = []
        def eol = Utilities.eol()
        def testCaseFactory = new EventFactory()
        testCaseFactory.loadTestCase()
        MessageLocale.load()

        try {
            def absolutePath = new File(env['path'])
            def relativePath = new File("${env['project_dir']}${File.separator}${env['path']}")
            def url = env['url']

            if (absolutePath.exists()) {
                testData = testCaseFactory.getTestFileList(absolutePath.canonicalPath)
            } else if (relativePath.exists()) {
                testData = testCaseFactory.getTestFileList(relativePath.canonicalPath)
            } else if (url != "" && url != null) {
                testData = new URIBuilder(url + "?" + (new Date().getTime() / 1000).toString())
            } else {
                testcases = [title: "${Message.COMPILE_RESULT.toString()}: ${Message.COMPILE_JSON_NOT_FOUND.toString()}", result: false]
                return [testcases]
            }

            try {
                testCaseFactory.createTestCase(testData) { List<?> testcase, isCreateSuccess ->
                    if (isCreateSuccess) {
                        testCaseFactory.concat(testcases, testcase)
                    } else {
                        testcase.each {
                            failureTestcases << it
                        }
                    }
                }
            } catch (Throwable e) {
                failureTestcases << [
                    title: "${Message.COMPILE_RESULT}: ${Message.COMPILE_INTERNAL_ERROR}",
                    errorMessages: ["${e.message}${eol}${e.stackTrace.join(eol)}"]
                ]
            }

            if (failureTestcases.empty) {
                def generator = new TestFileGenerator()
                def successNum = 0
                def failureNum = 0
                testcases = generator.reconstruct(testcases)
                generator.generate(testcases) { result ->
                    if (result['isGenerated']) {
                        successNum += result['testNum']
                    } else {
                        System.err.println "[NG]${result['errorMessage']}"
                        failureNum += result['testNum']
                    }
                }
                testcases = [
                    title: sprintf("${Message.COMPILE_RESULT.toString()}: ${Message.COMPILE_CREATED_TESTCASE.toString()}", successNum, failureNum),
                    result: failureNum == 0
                ]
            } else {
                def errorCount = 0
                failureTestcases.each {
                    it['errorMessages'].each {
                        System.err.println "[NG]${it}"
                        errorCount++
                    }
                }
                testcases = [
                    title: sprintf("${Message.COMPILE_RESULT.toString()}: ${Message.COMPILE_INVALID_TESTCASE.toString()}", errorCount),
                    result: false
                ]
            }
        } catch (Throwable e) {
            System.err.println "[NG]${e.message}${eol}${e.stackTrace.join(eol)}"
            testcases = [title: "${Message.COMPILE_RESULT.toString()}: ${e.message}", result: false]
        }

        [testcases]
    }
}
