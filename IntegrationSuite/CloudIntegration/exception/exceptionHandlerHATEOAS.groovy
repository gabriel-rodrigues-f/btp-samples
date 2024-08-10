import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/* Payload para teste
    { "status": true }
 */

def Message processData(Message message) {
  def inboundPayload  = message.getBody(String)
  def builder         = new JsonBuilder()
  def jsonRoot        = new JsonSlurper().parseText(inboundPayload)
  def properties      = message.getProperties()
  def headers         = message.getHeaders()
  def camelExceptionCaught    = properties.CamelExceptionCaught   as String ?: ''
  def camelHttpResponseCode   = headers.CamelHttpResponseCode     as String ?: 500
  def camelHttpResponseText   = headers.CamelHttpResponseText     as String ?: 'Error'
  def camelHttpUrl            = headers.CamelHttpUrl              as String ?: ''
  def hasErrorDetailsTag      = inboundPayload =~ '<errordetails>' ? true : false
  def exceptionMessages       = [] as Set
  def response

  if (hasErrorDetailsTag) {
    def xmlErrorPath        = new XmlSlurper().parseText(inboundPayload)
    def xErrorDetailsNode   = xmlErrorPath.'**'.find { xmlTag -> xmlTag.name() == 'errordetails' }

    xErrorDetailsNode.'**'.findAll { xmlTag -> xmlTag.name() == 'message' }.each { messageTag ->
      exceptionMessages << messageTag.text()
  }
}

  exceptionMessages = exceptionMessages.isEmpty() ? camelExceptionCaught : exceptionMessages.join('\n')

  builder {
        data jsonRoot
        status {
      code    camelHttpResponseCode
      text    camelHttpResponseText
        }
        links {
      self    camelHttpUrl
        }
  }

  response = unescapeUnicode(JsonOutput.prettyPrint(builder.toString()))
  message.setBody(response)
  message.setHeaders([
        'CamelHttpResponseCode' :'500',
        'CamelHttpResponseText' :'Error'
    ])
  return message
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
