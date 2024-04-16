/*  IMPORTANTE
    Declarar as propriedades abaixo em content-modifier no início do IFlow

    exceptionOutputFormat  se 'json', imprime payload de saída no formato JSON
                    caso vazio ou 'xml', imprime no formato XML

    logProperty,    se 'yes', anexa log das propriedades no Monitor Message
    logHeader,      se 'yes', anexa log dos header no Monitor Message
    logBody,        se 'yes', anexa payload da exception no Monitor Message
*/

import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.xml.MarkupBuilder

import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.xml.MarkupBuilder

def Message processData(Message payload) {
    def inboundPayload          = payload.getBody(String)
    def properties              = payload.getProperties()
    def camelExceptionCaught    = properties.CamelExceptionCaught as String ?: ''

    def exceptionOutputFormat   = properties.exceptionOutputFormat as String ?: ''
    def logProperty             = properties.logProperty   as String ?: ''
    def logHeader               = properties.logHeader     as String ?: ''
    def logBody                 = properties.logBody       as String ?: ''

    def headers = payload.getHeaders()
    def camelHttpResponseCode = headers.CamelHttpResponseCode as String ?: ''
    def camelHttpResponseText = headers.CamelHttpResponseText as String ?: ''

    def ruleToAttachMessage = 'yes'
    def hasErrorDetailsTag  = inboundPayload =~ '<errordetails>' ? true : false

    def messageLog = messageLogFactory.getMessageLog(payload)
    def exceptionMessages, logAttachment

    if (hasErrorDetailsTag) {
        def xmlErrorPath        = new XmlSlurper().parseText(inboundPayload)
        def xErrorDetailsNode   = xmlErrorPath.'**'.find { xmlTag -> xmlTag.name() == 'errordetails' }

        exceptionMessages = xErrorDetailsNode.'**'.findAll {
            xmlTag -> xmlTag.name() == 'message' }.collect { messageTag -> messageTag.text() }
        }

    exceptionMessages = exceptionMessages ?: camelExceptionCaught

    payload.setProperty('response', exceptionMessages)

    if (messageLog) {
        messageLog.setStringProperty('LogType', 'Payload')

        if (logProperty.equalsIgnoreCase(ruleToAttachMessage)) {
            StringBuffer propertiesBuffer = new StringBuffer()

            properties.eachWithIndex { property, index ->
                propertiesBuffer.append("${index + 1}: ${property.key}: ${property.value}\n")
            }
            def propertyAttachment = "Properties:\n${propertiesBuffer}\n"
            logAttachment = propertyAttachment
        }

        if (logHeader.equalsIgnoreCase(ruleToAttachMessage)) {
            StringBuffer headersBuffer = new StringBuffer()

            headers.eachWithIndex { header, index ->
                headersBuffer.append("${index + 1}: ${header.key}: ${header.value}\n")
            }

            def headerAttachment = "Headers:\n${headersBuffer}\n"
            logAttachment = logAttachment ? logAttachment + headerAttachment : headerAttachment
        }

        if (logBody.equalsIgnoreCase(ruleToAttachMessage)) {
            def bodyAttachment = "Body:\n${inboundPayload}"
            logAttachment = logAttachment ? logAttachment + bodyAttachment : bodyAttachment
        }

        if (logAttachment) {
            messageLog.addAttachmentAsString('Error: ', logAttachment, 'text/plain')
        }
    }

    if (exceptionOutputFormat.equalsIgnoreCase('json')) {
        def builder     = new JsonBuilder()
        def messageList = hasErrorDetailsTag ? exceptionMessages : [exceptionMessages]

        builder.exceptionMessages {
            messages(messageList)
            'status' {
                code(camelHttpResponseCode)
                description(camelHttpResponseText)
            }
        }

        def jsonString = builder.toString()
        jsonString = unescapeUnicode(JsonOutput.prettyPrint(jsonString))
        payload.setBody(jsonString)
    } else {
        def writer  = new StringWriter()
        def builder = new MarkupBuilder(writer)

        builder.exceptionMessages {
            if (hasErrorDetailsTag) {
                exceptionMessages.eachWithIndex { message, index -> "message${index + 1}"(message) }
            } else {
                message(exceptionMessages)
            }
            'status' {
                code(camelHttpResponseCode)
                description(camelHttpResponseText)
            }
        }
        payload.setBody(writer.toString())
    }
    return payload
    }

def unescapeUnicode(def inp) {
    (inp =~ /\\u([0-9a-f]{2})([0-9a-f]{2})/).each { m ->
        def uniAsString = new String([
            Integer.parseInt(m[1], 16),
            Integer.parseInt(m[2], 16)
        ] as byte[], StandardCharsets.UTF_16)
        inp = inp.replace(m[0], uniAsString)
}
    return inp
}
