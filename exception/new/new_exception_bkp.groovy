/*  IMPORTANTE
    Declarar as propriedades abaixo em content-modifier no início do IFlow

    outputDataType  se 'json', imprime payload de saída no formato JSON
                    caso vazio ou 'xml', imprime no formato XML

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
    def inboundPayload              = payload.getBody(String)

    def map                         = payload.getProperties()
    def camelExceptionCaught        = map.get('CamelExceptionCaught')   as String ?: ''
    def outputDataType              = map.get('outputDataType')         as String ?: ''
    def logProperty                 = map.get('logProperty')            as String ?: ''
    def logHeader                   = map.get('logHeader')              as String ?: ''
    def logBody                     = map.get('logBody')                as String ?: ''

    def headers                     = payload.getHeaders()
    def camelHttpResponseCode       = headers.get('CamelHttpResponseCode') as String ?: ''
    def camelHttpResponseText       = headers.get('CamelHttpResponseText') as String ?: ''

    def ruleToAttachMessage         = 'yes'
    def hasErrorDetailsTag          = inboundPayload =~ '<errordetails>' ? true : false

    def messageLog                  = messageLogFactory.getMessageLog(payload)
    def                             exceptionMessages, logAttachment

    /*
        Verifica se possui a tag <errordetails> e, caso sim, itera
        sobre ela até a tag <message> para obter o conteúdo da Exception.
     */

    if (hasErrorDetailsTag) {
        def messages            = []
        def xmlErrorPath        = new XmlSlurper().parseText(inboundPayload)
        def xErrorDetailsNode   = xmlErrorPath.'**'.find    { xmlTag -> xmlTag.name() == 'errordetails' }

        exceptionMessages       = xErrorDetailsNode.'**'.findAll {
            xmlTag -> xmlTag.name() == 'message' }.each { messageTag -> messages << messageTag.text() }
        }

    /*
        Caso não seja recebido um payload de Exception,
        então o programa captará a propriedade 'CamelExceptionCaught'
     */
    exceptionMessages              = exceptionMessages ?: camelExceptionCaught

    payload.setProperty('response', exceptionMessages)

    if (messageLog) {
        /*
           Nomeia, por boas práticas, o tipo do log recebido
         */
        messageLog.setStringProperty('LogType', 'Payload')

        /*
            Caso a propriedade 'logProperty' seja 'yes', então seu conteúdo será logado no Monitor Message do CPI
         */
        if (logProperty.equalsIgnoreCase(ruleToAttachMessage)) {
            StringBuffer propertiesBuffer = new StringBuffer()

            map.eachWithIndex { property, index ->
                propertiesBuffer.append("${index + 1}: $property.key: $property.value\n")
            }

            def propertyAttachment  = "Properties:\n${propertiesBuffer}\n"
            logAttachment           = propertyAttachment
        }

        /*
            Caso a propriedade 'logHeader' seja 'yes', então seu conteúdo será logado no Monitor Message do CPI
        */
        if (logHeader.equalsIgnoreCase(ruleToAttachMessage)) {
            StringBuffer headersBuffer = new StringBuffer()

            headers.eachWithIndex { header, index ->
                headersBuffer.append("${index + 1}: $header.key: $header.value\n")}

            def headerAttachment    = "Headers:\n${headersBuffer}\n"
            logAttachment           = logAttachment
            ? logAttachment + headerAttachment
            : headerAttachment
        }

        /*
            Caso a propriedade 'logBody' seja 'yes', então seu conteúdo será logado no Monitor Message do CPI
        */
        if (logBody.equalsIgnoreCase(ruleToAttachMessage)) {
            def bodyAttachment      = "Body:\n${inboundPayload}"
            logAttachment           = logAttachment
            ? logAttachment + bodyAttachment
            : bodyAttachment
        }

        if (logAttachment) {
            messageLog.addAttachmentAsString('Error: ', logAttachment, 'text/plain')
        }
    }

    /*
        Caso a propriedade 'outputDataType' seja 'json', então será impresso um payload de outbound no formato JSON
        caso seja vazio ou 'xml', então a saída será em formato XML.
    */

    if (outputDataType.equalsIgnoreCase('json')) {
        def builder     = new JsonBuilder()
        builder.exceptionMessages {
            if ( hasErrorDetailsTag ) {
                exceptionMessages.eachWithIndex { message, index -> "message${index + 1 }"("$message") }
                } else {
                message(exceptionMessages)
            }
            'status' {
                code(camelHttpResponseCode)
                description(camelHttpResponseText)
            }
        }

        def jsonString = builder.toString()
        jsonString = unescapeUnicode(JsonOutput.prettyPrint(jsonString))
        payload.setBody(jsonString)
    } else {
        def writer      = new StringWriter()
        def builder     = new MarkupBuilder(writer)

        builder.exceptionMessages {
            if ( hasErrorDetailsTag ) {
                exceptionMessages.eachWithIndex { message, index ->  "message${index + 1}"(message) }
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

/*
    Trata a mensagem de saída para que sejam impressos corretamente os caracteres especiais
 */
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
