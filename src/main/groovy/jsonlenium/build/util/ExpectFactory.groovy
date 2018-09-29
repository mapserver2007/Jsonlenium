package jsonlenium.build.util

class ExpectFactory {
    def createExpect(String text, boolean isCaseSensitive) {
        def expect = ['value': text?.replace("\\", ""), 'isRegex': false]
        (text =~ /^\/(.+)\/$/).each { matchAll, match ->
            expect = ['value': "(?s)${match}", 'isRegex': true]
        }
        expect.isCaseSensitive = isCaseSensitive
        expect
    }
}
