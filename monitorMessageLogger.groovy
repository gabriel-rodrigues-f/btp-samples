/*  IMPORTANTE
    Declarar as propriedades abaixo em content-modifier no início do IFlow

    logProperty,    se 'yes', anexa log das propriedades no Monitor Message
    logHeader,      se 'yes', anexa log dos header no Monitor Message
    logBody,        se 'yes', anexa payload da exception no Monitor Message
*/

import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import groovy.json.*
import groovy.xml.*
import java.util.*

def Message processData(Message payload) {
    def body                        = payload.getBody(String)
    def map                         = payload.getProperties()
    def headers                     = payload.getHeaders()

    def logProperty                 = map.get('logProperty')            as String ?: ''
    def logHeader                   = map.get('logHeader')              as String ?: ''
    def logBody                     = map.get('logBody')                as String ?: ''
    def logAttachment
    def ruleToAttachMessage         = 'yes'

    def messageLogFactory           = messageLogFactory.getMessageLog(payload)

    if (messageLogFactory) {
        messageLogFactory.setStringProperty('LogType', 'Payload')

        if (logProperty.equalsIgnoreCase(ruleToAttachMessage)) {
            StringBuffer propertiesBuffer = new StringBuffer()

            map.eachWithIndex { property, index ->
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
            messageLogFactory.addAttachmentAsString('Log: ', logAttachment, 'text/plain')
        }
    }

    return payload
}
