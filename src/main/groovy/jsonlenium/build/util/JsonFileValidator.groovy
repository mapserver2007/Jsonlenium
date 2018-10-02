package jsonlenium.build.util

import jsonlenium.util.Message

class JsonFileValidator {
    private String fileLocation
    private List<String> errorMessages
    private AttributeFactory attributeFactory

    JsonFileValidator() {
        errorMessages = []
        attributeFactory = new AttributeFactory()
    }

    def setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation
    }

    def isValid() {
        return !errorMessages.any()
    }

    def getErrorMessages() {
        return errorMessages.unique()
    }

    def validate(Map<String, ?> elem) {
        def attrPathList = []
        elem.each { attr, value ->
            validate(attr, value, attrPathList, null)
        }
        attributeFactory.loadRequiredError {
            errorMessages << sprintf(Message.COMPILE_INVALID_ATTRIBUTE_REQUIRED_ERROR.toString(), it) +
                sprintf(Message.ERROR_FILE_LOCATION.toString(), fileLocation)
        }
    }

    def validate(List<Map<String, ?>> list, List<String> attrPathList, String parentAttr) {
        list.eachWithIndex { elem, index ->
            List<String> attrHorizontalPath = attrPathList.clone()
            attrHorizontalPath << "${parentAttr}[${index}]"
            attributeFactory.loadAnyRequiredError(elem, parentAttr, "expectation") {
                errorMessages << sprintf(Message.COMPILE_INVALID_ATTRIBUTE_ANY_REQUIRED_ERROR.toString(), "\$." + attrHorizontalPath.join('.'), it) +
                    sprintf(Message.ERROR_FILE_LOCATION.toString(), fileLocation)
            }
            validate(elem, attrHorizontalPath, parentAttr)
        }
    }

    def validate(Map<String, ?> elem, List<String> attrPathList, String parentAttr) {
        elem.each { String attr, value ->
            attributeFactory.loadAnyRequiredError(elem, parentAttr, "expectation") {
                errorMessages << sprintf(Message.COMPILE_INVALID_ATTRIBUTE_ANY_REQUIRED_ERROR.toString(), "\$." + attrPathList.join('.'), it) +
                    sprintf(Message.ERROR_FILE_LOCATION.toString(), fileLocation)
            }
            validate(attr, value, attrPathList, parentAttr)
        }
    }

    def validate(String attr, Object value, List<String> attrPathList, String parentAttr) {
        def instance = attributeFactory.getInstance(attr)
        if (attributeFactory.requireds.containsKey(attr)) {
            attributeFactory.requireds[attr] = true
        }
        if (instance != null) {
            if (attributeFactory.types[attr] != null && value != null && attributeFactory.types[attr].isInstance(value) == false) {
                if (value instanceof Map) {
                    attrPathList << attr
                }
                errorMessages << sprintf(Message.COMPILE_INVALID_ATTRIBUTE_TYPE_ERROR.toString(),
                    ["\$." + (attrPathList.empty ? "" : "${attrPathList.join('.')}.") + attr, attributeFactory.getTypeName(attr)]) + sprintf(Message.ERROR_FILE_LOCATION.toString(), fileLocation)
                instance.next = false
            } else {
                instance.read(value, parentAttr, attrPathList) { message, params ->
                    errorMessages << sprintf(message, params) + sprintf(Message.ERROR_FILE_LOCATION.toString(), fileLocation)
                }
                if (value instanceof Map) {
                    attrPathList << attr
                }
                if (instance.next) {
                    validate(value, attrPathList, attr)
                }
            }
        } else {
            errorMessages << sprintf(Message.COMPILE_INVALID_ATTRIBUTE_SPECIFIDED_ERROR.toString(), attr, Message.COMPILE_INVALID_CAUSE_UNDEFINED_ATTRIBUTE) +
                sprintf(Message.ERROR_FILE_LOCATION.toString(), fileLocation)
        }
    }
}
