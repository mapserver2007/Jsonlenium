package jsonlenium.build.util


import jsonlenium.build.attr.Catalyst
import jsonlenium.build.attr.Expect
import jsonlenium.build.attr.ExpectAll
import jsonlenium.build.attr.K3C
import jsonlenium.build.attr.Meta
import jsonlenium.build.attr.NotExpect
import jsonlenium.build.attr.NotExpectAll
import jsonlenium.constant.Event
import jsonlenium.constant.EventWeight
import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType

class EventUtil {
    static void createMetaEvent(Map<String, ?> elem, @ClosureParams(value = SimpleType, options = ["java.util.Map", "java.lang.Integer", "java.lang.Integer"]) Closure closure) {
        def factory = new ExpectFactory()
        elem.meta.eachWithIndex { Map<String, String> meta, Integer index ->
            if (meta.case_sensitive == null) {
                meta.case_sensitive = true
            }
            def expect = factory.createExpect(meta.expect, meta.case_sensitive)
            closure.call([
                'selector'       : "meta[name='${meta.name}'],meta[property='${meta.name}']",
                'attr'           : "content",
                'event'          : Event.META,
                'expect'         : expect.value,
                'isRegex'        : expect.isRegex,
                'isCaseSensitive': expect.isCaseSensitive
            ], EventWeight.META.value(), index)
        }
    }

    static void createCatalystEvent(Map<String, ?> elem, @ClosureParams(value = SimpleType, options = ["java.util.Map", "java.lang.Integer", "java.lang.Integer"]) Closure closure) {
        def factory = new ExpectFactory()
        elem.catalyst.eachWithIndex { Map<String, String> catalyst, Integer index ->
            if (catalyst.case_sensitive == null) {
                catalyst.case_sensitive = true
            }
            def expect = factory.createExpect(catalyst.expect, catalyst.case_sensitive)
            def attr = catalyst.name
            if (attr.length() >= 2 && attr.substring(0, 2) != "s.") {
                attr = "s.${attr}"
            }
            closure.call([
                'selector'       : null,
                'attr'           : attr,
                'event'          : Event.CATALYST,
                'jsvar'          : expect.value ==~ /\As\.(?!(kakaku|coreprice)).+\z/,
                'expect'         : expect.value,
                'isRegex'        : expect.isRegex,
                'isCaseSensitive': expect.isCaseSensitive
            ], EventWeight.CATALYST.value(), index)
        }
    }

    static void createK3CEvent(Map<String, ?> elem, @ClosureParams(value = SimpleType, options = ["java.util.Map", "java.lang.Integer", "java.lang.Integer"]) Closure closure) {
        def factory = new ExpectFactory()
        elem.k3c.eachWithIndex { Map<String, String> k3c, Integer index ->
            if (k3c.case_sensitive == null) {
                k3c.case_sensitive = true
            }
            def expect = factory.createExpect(k3c.expect, k3c.case_sensitive)
            closure.call([
                'selector'       : null,
                'attr'           : "k3c.atrack.val.${k3c.name}",
                'event'          : Event.K3C,
                'expect'         : expect.value,
                'isRegex'        : expect.isRegex,
                'isCaseSensitive': expect.isCaseSensitive
            ], EventWeight.K3C.value(), index)
        }
    }

    static void createRestApiEvent(Map<String, ?> elem, @ClosureParams(value = SimpleType, options = ["java.util.Map", "java.lang.Integer"]) Closure closure) {
        def factory = new ExpectFactory()
        def event = [:]
        if (elem.case_sensitive == null) {
            elem.case_sensitive = true
        }
        if (elem.containsKey(Expect.toString())) {
            def expect = factory.createExpect(elem.expect, elem.case_sensitive)
            event = [
                'jsonpath'       : elem.jsonpath,
                'event'          : Event.REST_API_TEST,
                'expect'         : expect.value,
                'isNot'          : false,
                'isRegex'        : expect.isRegex,
                'isCaseSensitive': expect.isCaseSensitive
            ]
        } else if (elem.containsKey(ExpectAll.toString())) {
            def expect = factory.createExpect(elem.expect_all, elem.case_sensitive)
            event = [
                'jsonpath'       : elem.jsonpath,
                'event'          : Event.REST_API_TEST,
                'expect'         : expect.value,
                'isNot'          : false,
                'isRegex'        : expect.isRegex,
                'isCaseSensitive': expect.isCaseSensitive
            ]
        } else if (elem.containsKey(NotExpect.toString())) {
            def expect = factory.createExpect(elem.not_expect, elem.case_sensitive)
            event = [
                'jsonpath'       : elem.jsonpath,
                'event'          : Event.REST_API_TEST,
                'expect'         : expect.value,
                'isNot'          : true,
                'isRegex'        : expect.isRegex,
                'isCaseSensitive': expect.isCaseSensitive
            ]
        } else if (elem.containsKey(NotExpectAll.toString())) {
            def expect = factory.createExpect(elem.not_expect_all, elem.case_sensitive)
            event = [
                'jsonpath'       : elem.jsonpath,
                'event'          : Event.REST_API_TEST,
                'expect'         : expect.value,
                'isNot'          : true,
                'isRegex'        : expect.isRegex,
                'isCaseSensitive': expect.isCaseSensitive
            ]
        }

        closure.call(event, EventWeight.REST_API_TEST.value())
    }

    static void createEvent(Map<String, ?> elem, @ClosureParams(value = SimpleType, options = ["java.util.Map"]) Closure closure) {
        def factory = new ExpectFactory()
        def events = []
        def eventWeight = 0
        def inheritEvent = null
        (1..10).find { depth -> // max nest up to 10
            def isBreak = false
            // events: input, click
            if (elem.input != null) {
                elem.input.each { Map<String, ?> input ->
                    events << [
                        'selector': input.selector,
                        'xpath': input.xpath,
                        'value': input.value,
                        'event': Event.INPUT
                    ]
                }
                eventWeight += EventWeight.INPUT.value()
            }
            if (elem.click != null) {
                if (inheritEvent != null) {
                    events << [
                        'selector': inheritEvent.selector,
                        'xpath'   : inheritEvent.xpath,
                        'attr'    : inheritEvent.attr,
                        'event'   : Event.PREVENT_DEFAULT_CLICK
                    ]
                    eventWeight += EventWeight.PREVENT_DEFAULT_CLICK.value()
                    inheritEvent = null
                } else {
                    events << [
                        'selector': elem.selector,
                        'xpath'   : elem.xpath,
                        'attr'    : elem.attr,
                        'event'   : Event.CLICK
                    ]
                    eventWeight += EventWeight.CLICK.value()
                }
                elem = elem.click
            } else if (elem.prevent_default != null) {
                if ((elem.prevent_default as Map).click != null) {
                    inheritEvent = elem
                }
                elem = elem.prevent_default
            } else {
                if (elem.meta != null && elem.meta instanceof List) {
                    def meta = elem.meta as List
                    def size = meta.size()
                    createMetaEvent(elem) { event, weight, index ->
                        if (size > 1) {
                            closure.call(events, eventWeight + weight, event, "[${Meta.toString().toLowerCase()}(${index + 1}/${size})] [${event.selector}]")
                        } else {
                            closure.call(events, eventWeight, event, "[${Meta.toString().toLowerCase()}] [${event.selector}]")
                        }
                    }
                    isBreak = true
                } else if (elem.catalyst != null && elem.catalyst instanceof List) {
                    def catalyst = elem.catalyst as List
                    def size = catalyst.size()
                    createCatalystEvent(elem) { event, weight, index ->
                        if (size > 1) {
                            closure.call(events, eventWeight + weight, event, "[${Catalyst.toString().toLowerCase()}(${index + 1}/${size})] [${event.attr}]")
                        } else {
                            closure.call(events, eventWeight + weight, event, "[${Catalyst.toString().toLowerCase()}] [${event.attr}]")
                        }
                    }
                    isBreak = true
                } else if (elem.k3c != null && elem.k3c instanceof List) {
                    def k3c = elem.k3c as List
                    def size = k3c.size()
                    createK3CEvent(elem) { event, weight, index ->
                        if (size > 1) {
                            closure.call(events, eventWeight + weight, event, "[${K3C.toString().toLowerCase()}(${index + 1}/${size})] [${event.attr}]")
                        } else {
                            closure.call(events, eventWeight + weight, event, "[${K3C.toString().toLowerCase()}] [${event.attr}]")
                        }
                    }
                    isBreak = true
                } else {
                    if (elem.case_sensitive == null) {
                        elem.case_sensitive = true
                    }
                    if (elem.containsKey(Expect.toString())) {
                        def expect = factory.createExpect(elem.expect, elem.case_sensitive)
                        events << [
                            'selector'       : elem.selector,
                            'xpath'          : elem.xpath,
                            'attr'           : elem.attr,
                            'event'          : Event.UI_TEST,
                            'expect'         : expect.value,
                            'isNot'          : false,
                            'isRegex'        : expect.isRegex,
                            'isCaseSensitive': expect.isCaseSensitive,
                            'anyMatch'       : true
                        ]
                    } else if (elem.containsKey(ExpectAll.toString())) {
                        def expect = factory.createExpect(elem.expect_all, elem.case_sensitive)
                        events << [
                            'selector'       : elem.selector,
                            'xpath'          : elem.xpath,
                            'attr'           : elem.attr,
                            'event'          : Event.UI_TEST,
                            'expect'         : expect.value,
                            'isNot'          : false,
                            'isRegex'        : expect.isRegex,
                            'isCaseSensitive': expect.isCaseSensitive,
                            'anyMatch'       : false
                        ]
                    } else if (elem.containsKey(NotExpect.toString())) {
                        def expect = factory.createExpect(elem.not_expect, elem.case_sensitive)
                        events << [
                            'selector'       : elem.selector,
                            'xpath'          : elem.xpath,
                            'attr'           : elem.attr,
                            'event'          : Event.UI_TEST,
                            'expect'         : expect.value,
                            'isNot'          : true,
                            'isRegex'        : expect.isRegex,
                            'isCaseSensitive': expect.isCaseSensitive,
                            'anyMatch'       : true
                        ]
                    } else if (elem.containsKey(NotExpectAll.toString())) {
                        def expect = factory.createExpect(elem.not_expect_all, elem.case_sensitive)
                        events << [
                            'selector'       : elem.selector,
                            'xpath'          : elem.xpath,
                            'attr'           : elem.attr,
                            'event'          : Event.UI_TEST,
                            'expect'         : expect.value,
                            'isNot'          : true,
                            'isRegex'        : expect.isRegex,
                            'isCaseSensitive': expect.isCaseSensitive,
                            'anyMatch'       : false
                        ]
                    }

                    closure.call(events, eventWeight += EventWeight.UI_TEST.value())
                    isBreak = true
                }
            }

            isBreak
        }
    }
}
