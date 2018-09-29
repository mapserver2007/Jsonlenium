package jsonlenium.ui.page

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.internal.JsonContext
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.openqa.selenium.WebDriver

class JsonPage<T> implements IPage {
    private HttpURLConnection conn
    private String currentUrl
    private DocumentContext document
    private T element
    private final int TIMEOUT = 30000

    JsonPage(HttpURLConnection conn) {
        this.conn = conn
    }

    JsonPage read(@ClosureParams(value = SimpleType, options = ["java.net.URLConnection"]) Closure closure) {
        if (document != null) {
            return this
        }

        try {
            closure.call(conn)
            conn.instanceFollowRedirects = true
            conn.setConnectTimeout(TIMEOUT)
            conn.setReadTimeout(TIMEOUT)
            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                currentUrl = conn.getURL().toString()
                document = JsonPath.parse(conn.inputStream)
            }
        } catch (MalformedURLException ignore) {
            document = new JsonContext()
        }

        this
    }

    JsonPage selector(String text) {
        throw new UnsupportedOperationException()
    }

    JsonPage xpath(String text) {
        throw new UnsupportedOperationException()
    }

    JsonPage click() {
        throw new UnsupportedOperationException()
    }

    JsonPage js(String jsCode) {
        throw new UnsupportedOperationException()
    }

    JsonPage json(String jsonPath) {
        try {
            element = document?.read(jsonPath)
        } catch (IllegalArgumentException ignore) {
            // root can not be null
            element = null
        }
        this
    }

    JsonPage waitFor(Closure closure) {
        throw new UnsupportedOperationException()
    }

    WebDriver getDriver() {
        throw new UnsupportedOperationException()
    }

    String getJsResponse() {
        throw new UnsupportedOperationException()
    }

    Iterable<T> getNavigator() {
        def navigator = element
        if (navigator == null) {
            navigator = Collections.emptyList()
        } else if (!(navigator instanceof Iterable)) {
            navigator = [element]
        }

        navigator
    }

    String getName() {
        this.class.typeName
    }

    String getCurrentUrl() {
        currentUrl
    }
}
