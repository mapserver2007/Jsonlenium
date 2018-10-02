package jsonlenium.build.util

import groovy.transform.stc.ClosureParams
import groovy.transform.stc.SimpleType
import jsonlenium.annotation.AnyRequired
import jsonlenium.annotation.AttributeName
import jsonlenium.annotation.AttributeType
import jsonlenium.annotation.Required
import jsonlenium.build.attr.AttributeBase
import jsonlenium.constant.JsonleniumEnv
import jsonlenium.constant.Path

class AttributeFactory {
    private Map<String, Boolean> requireds
    private Map<String, Map<String, List<String>>> anyRequireds
    private Map<String, AttributeBase> attributes
    private Map<String, Class<?>> types

    private Map<String, String> typeAliases = [
        'java.lang.String': 'string',
        'java.lang.Boolean': 'boolean',
        'java.util.Map': 'object',
        'java.util.List': 'array'
    ]

    AttributeFactory() {
        attributes = [:]
        requireds = new TreeMap<>()
        anyRequireds = new TreeMap<>()
        types = [:]
        loadAttribute()
    }

    void loadRequiredError(@ClosureParams(value = SimpleType.class, options = "java.lang.String") Closure closure) {
        requireds.each { key, value ->
            if (value == false) {
                closure.call(key)
            }
        }
    }

    void loadAnyRequiredError(Map<String, ?> elem, String parentAttr, String anyRequiredGroupKey, @ClosureParams(value = SimpleType.class, options = "java.lang.String") Closure closure) {
        def isValid = false
        elem.each { key, value ->
            if (anyRequireds[anyRequiredGroupKey].containsKey(key) && (value instanceof List || value instanceof Map)) {
                isValid |= true
            }
            if (anyRequireds[anyRequiredGroupKey].containsKey(parentAttr)) {
                if (anyRequireds[anyRequiredGroupKey][parentAttr].contains(key)) {
                    isValid |= true
                }
            } else {
                isValid |= true
            }
        }
        if (!isValid) {
            closure.call(anyRequireds[anyRequiredGroupKey][parentAttr].collect { "'${it}'" }.sort().join(","))
        }
    }

    void loadAttribute() {
        def rootPath = System.getProperty(JsonleniumEnv.USER_DIR)
        def attrDir = new File(rootPath + Path.JSONLENIUM_ATTRIBUTE_CLASS_DIR_PATH)

        if (attrDir.isDirectory()) {
            attrDir.eachFile { f ->
                if (f.directory) {
                    return
                }

                def classPath = null
                (f.canonicalPath =~ /.+(?:\\|\/)([a-zA-Z0-9_]+)\.(?:groovy|java)$/).each { matchAll, match ->
                    classPath = "${Path.JSONLENIUM_ATTRIBUTE_PACKAGE_PATH}.${match}"
                }

                def url = new URL[1]
                url[0] = f.toURI().toURL()
                def classLoader = new URLClassLoader(url)
                Class<AttributeBase> clazz = Class.forName(classPath, false, classLoader)
                if (clazz.name == AttributeBase.class.name) {
                    return
                }

                def attributeName = clazz.getAnnotation(AttributeName.class)
                if (attributeName != null) {
                    def attr = attributeName.value()
                    def instance = clazz.newInstance()
                    instance.next = false
                    attributes[attr] = instance
                    def required = clazz.getAnnotation(Required.class)
                    if (required != null) {
                        requireds[attr] = false
                    }
                    def anyRequired = clazz.getAnnotation(AnyRequired.class)
                    if (anyRequired != null) {
                        if (!anyRequireds.containsKey(anyRequired.key())) {
                            anyRequireds[anyRequired.key()] = [:]
                        }
                        anyRequired.target().each {
                            if (!anyRequireds[anyRequired.key()].containsKey(it)) {
                                anyRequireds[anyRequired.key()][it] = []
                            }
                            anyRequireds[anyRequired.key()][it] << attr
                        }
                    }
                    def attributeType = clazz.getAnnotation(AttributeType.class)
                    if (attributeType != null) {
                        types[attr] = attributeType.value()
                    }
                }
            }
        }
    }

    AttributeBase getInstance(String attr) {
        attributes[attr]
    }

    String getTypeName(String attr) {
        typeAliases[types[attr].typeName]
    }
}
