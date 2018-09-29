package jsonlenium.ui.page

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.openqa.selenium.WebDriver

class HtmlPage implements IPage {
    private HttpURLConnection conn
    private String currentUrl
    private Document document
    private Elements element
    private final int TIMEOUT = 30000

    HtmlPage(HttpURLConnection conn) {
        this.conn = conn
    }

    HtmlPage read(@ClosureParams(value = SimpleType, options = ["java.net.URLConnection"]) Closure closure) {
        if (document != null) {
            return this
        }

        try {
            closure.call(conn)
            conn.instanceFollowRedirects = true
            conn.setConnectTimeout(TIMEOUT)
            conn.setReadTimeout(TIMEOUT)
            currentUrl = conn.getURL().toString()
            if (conn.responseCode ==~ /[4-5]\d{2}/) {
                document = Jsoup.parse(conn.errorStream, null, currentUrl)
            } else {
                document = Jsoup.parse(conn.inputStream, null, currentUrl)
            }
        } catch (IOException | MalformedURLException ignore) {
            // In case of Content-type which can not be analyzed, processing is not executed
            // or request to unknown host.
            document = new Document(conn.getURL().toString())
        }

        this
    }

    HtmlPage selector(String text) {
        element = document?.select(text)
        this
    }

    HtmlPage xpath(String text) {
        throw new UnsupportedOperationException()
    }

    HtmlPage click() {
        throw new UnsupportedOperationException()
    }

    HtmlPage js(String jsCode) {
        throw new UnsupportedOperationException()
    }

    HtmlPage json(String jsonPath) {
        throw new UnsupportedOperationException()
    }

    HtmlPage waitFor(Closure closure) {
        throw new UnsupportedOperationException()
    }

    WebDriver getDriver() {
        throw new UnsupportedOperationException()
    }

    String getJsResponse() {
        throw new UnsupportedOperationException()
    }

    Iterable<Element> getNavigator() {
        element ?: Collections.emptyList()
    }

    String getName() {
        this.class.typeName
    }

    String getCurrentUrl() {
        currentUrl
    }
}
