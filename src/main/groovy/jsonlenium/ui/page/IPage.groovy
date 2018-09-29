package jsonlenium.ui.page

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.openqa.selenium.WebDriver

interface IPage {
    IPage read(@ClosureParams(value = SimpleType, options = ["java.net.URLConnection"]) Closure closure)
    IPage selector(String text)
    IPage xpath(String text)
    IPage click()
    IPage js(String jsCode)
    IPage json(String jsonPath)
    IPage waitFor(Closure closure)
    WebDriver getDriver()
    String getJsResponse()
    Iterable getNavigator()
    String getName()
    String getCurrentUrl()
}
