package jsonlenium.build.util

import jsonlenium.build.attr.TestCase
import jsonlenium.build.attr.Url
import jsonlenium.build.attr.UserAgent

import groovyx.net.http.URIBuilder

class CacheClearUtil {
    static boolean isCacheClear(URI uri1, URI uri2) {
        uri1.host != uri2.host
    }

    static boolean isCacheClear(URIBuilder uri, List<Map<String, ?>> testcases, Map<String, ?> testcase) {
        def currentUserAgent = testcase.useragent
        if (currentUserAgent != null) {
            return true
        }

        if (testcases.any()) {
            def prevUri = new URI(testcases.last()[TestCase.toString()][Url.toString()])
            def prevUrl = new URI(prevUri.host)
            def currentUrl = new URI(uri.host)

            if (CacheClearUtil.isCacheClear(currentUrl, prevUrl)) {
                return true
            }

            def prevUserAgent = testcases.last()[TestCase.toString()][UserAgent.toString()]
            if (prevUserAgent != null && currentUserAgent != null) {
                return prevUserAgent != currentUserAgent
            }
        }

        false
    }
}
