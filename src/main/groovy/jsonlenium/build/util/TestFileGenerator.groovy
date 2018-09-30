package jsonlenium.build.util

import groovy.json.JsonOutput
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import jsonlenium.constant.JsonleniumEnv
import jsonlenium.constant.Path
import org.apache.commons.io.FileUtils
import org.apache.commons.lang.StringUtils

import java.nio.charset.StandardCharsets

class TestFileGenerator {
    void write(File file, String content, @ClosureParams(value = SimpleType, options = ["java.util.Map"]) Closure closure) {
        def result = [:]
        try {
            FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8.toString())
            result['isGenerated'] = file.exists()
        } catch (IOException e) {
            result['isGenerated'] = false
            result['errorMessage'] = e.message
        }

        closure.call(result)
    }

    List<List<Map<String, ?>>> reconstruct(List<?> testsuite) {
        def division = System.getProperty(JsonleniumEnv.DIVISION)
        int dividedNum = StringUtils.isBlank(division) ? 1 : Integer.parseInt(division, 10)

        List<List<Map<String, ?>>> divisionList = new ArrayList<>(dividedNum)
        List<Integer> divisionSumList = new ArrayList<>(dividedNum)
        dividedNum.times {
            divisionList[it] = []
            divisionSumList[it] = 0
        }

        testsuite.each { Map<String, Map<String, ?>> testcases ->
            def weight = testcases.testcase.weight
            def index = divisionSumList.indexOf(Collections.min(divisionSumList))
            divisionSumList[index] += weight
            divisionList[index] << testcases
        }

        divisionList
    }

    void generate(List<List<Map<String, ?>>> testsuite, @ClosureParams(value = SimpleType, options = ["java.util.Map"]) Closure closure) {
        def templateFile = new File(getClass().getResource(Path.JSONLENIUM_TEMPLATE_FILE_PATH).path)
        def sourceCode = FileUtils.readFileToString(templateFile, "UTF-8")

        def rootPath = System.getProperty(JsonleniumEnv.USER_DIR)
        def srcDir = new File(rootPath + Path.GENERATE_JSONLENIUM_CLASS_BUILD_PATH)
        def buildDir = new File(rootPath + Path.GENERATE_JSONLENIUM_JSON_BUILD_PATH)

        testsuite.eachWithIndex { testcase, index ->
            def suffixIndex = StringUtils.leftPad((index + 1).toString(), 4, '0')
            def name = Path.JSONLENIUM_TEST_CLASS_NAME + suffixIndex
            def groovyFile = new File("${srcDir.absolutePath}/${name}.groovy")
            def jsonFile = new File("${buildDir.absolutePath}/${name}.json")
            def groovySourceCode = sourceCode.replaceAll(Path.JSONLENIUM_TEMPLATE_CLASS_STRING, name)
                .replaceAll(Path.JSONLENIUM_TEMPLATE_JSONFILE_STRING, name)
            def jsonContent = JsonOutput.toJson(testcase)

            write(groovyFile, groovySourceCode) { result ->
                result.filePath = groovyFile.canonicalPath
            }
            write(jsonFile, jsonContent) { result ->
                result.filePath = jsonFile.canonicalPath
                result.testNum = testcase.size()
                closure.call(result)
            }
        }

        testsuite.size()
    }
}
