package jsonlenium.build.util

import jsonlenium.constant.JsonleniumEnv

class MessageLocale {
    static void load() {
        def locale = System.getProperty(JsonleniumEnv.LOCALE)
        switch (locale) {
            case "ja_JP":
            case "ja":
                Locale.setDefault(Locale.JAPAN)
                break
            case "en_US":
            case "en":
                Locale.setDefault(Locale.US)
                break
            default:
                Locale.setDefault(Locale.JAPAN)
                break
        }
    }
}
