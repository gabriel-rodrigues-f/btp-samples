import com.sap.gateway.ip.core.customdev.util.Message

/* Payload para teste
<root>
    <errordetails>
        <message>Error message 1</message>
        <message>Error message 2</message>
    </errordetails>
</root>
 */

def Message processData(Message message) {
  def inboundPayload  = message.getBody(String)
  def properties      = message.getProperties()
  def camelExceptionCaught    = properties.CamelExceptionCaught as String ?: ''
  def hasErrorDetailsTag      = inboundPayload =~ '<errordetails>' ? true : false
  def exceptionMessages       = [] as Set

  if (hasErrorDetailsTag) {
    def xmlErrorPath        = new XmlSlurper().parseText(inboundPayload)
    def xErrorDetailsNode   = xmlErrorPath.'**'.find { xmlTag -> xmlTag.name() == 'errordetails' }

    xErrorDetailsNode.'**'.findAll { xmlTag -> xmlTag.name() == 'message' }.each { messageTag ->
      exceptionMessages << messageTag.text()
  }
}

  exceptionMessages = exceptionMessages.isEmpty() ? camelExceptionCaught : exceptionMessages.join('\n')
  message.setProperties([
        response: exceptionMessages,
        logDescription: 'Error'
    ])
  return message
}
