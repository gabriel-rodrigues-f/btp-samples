import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message request) {
    InputStream body  = request.getBody(InputStream)
    Map<String, String> properties  = request.getProperties()
    Map<String, String> headers     = request.getHeaders()
    String logProperty  = properties.logProperty       as String ?: ''
    String logHeader    = properties.logHeader         as String ?: ''
    String logBody      = properties.logBody           as String ?: ''
    String ruleToAttachMessage = 'true'
    def messageLogFactory   = messageLogFactory.getMessageLog(request)
    def logAttachment

    if (messageLogFactory) {
        messageLogFactory.setStringProperty('LogType', 'payload')
        if (logProperty.equalsIgnoreCase(ruleToAttachMessage)) {
            StringBuffer propertiesBuffer = new StringBuffer()
            properties.eachWithIndex { property, index ->
                propertiesBuffer.append("${index + 1}: $property.key: $property.value\n")
            }
            def propertyAttachment  = "Properties:\n${propertiesBuffer}\n"
            logAttachment           = propertyAttachment
        }
        if (logHeader.equalsIgnoreCase(ruleToAttachMessage)) {
            StringBuffer headersBuffer = new StringBuffer()
            headers.eachWithIndex { header, index ->
                headersBuffer.append("${index + 1}: $header.key: $header.value\n")}
            def headerAttachment    = "Headers:\n${headersBuffer}\n"
            logAttachment           = logAttachment
            ? logAttachment + headerAttachment
            : headerAttachment
        }
        if (logBody.equalsIgnoreCase(ruleToAttachMessage)) {
            def bodyAttachment      = "Body:\n${body}"
            logAttachment           = logAttachment
            ? logAttachment + bodyAttachment
            : bodyAttachment
        }

        if (logAttachment) {
            messageLogFactory.addAttachmentAsString("Log", logAttachment, 'text/plain')
        }
    }
    return request
}
