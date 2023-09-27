import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message payload) {
    def inboundPayload              = payload.getBody(String)
    def map                         = payload.getProperties()
    def camelExceptionCaught        = map.CamelExceptionCaught as String ?: ''
    def AhcOperationFailedException = camelExceptionCaught.getClass().getCanonicalName().equals("org.apache.camel.component.ahc.AhcOperationFailedException") ?: ''
    def hasErrorDetailsTag          = inboundPayload =~ '<errordetails>' ? true : false
    def exceptionMessages           = []

    if (hasErrorDetailsTag) {
        def xmlErrorPath = new XmlSlurper().parseText(inboundPayload)
        def xErrorDetailsNode = xmlErrorPath.'**'.find { xmlTag -> xmlTag.name() == 'errordetails' }

        xErrorDetailsNode.'**'.findAll { xmlTag -> xmlTag.name() == 'message' }.each { messageTag ->
            exceptionMessages << messageTag.text()
        }
    }

    exceptionMessages   = exceptionMessages ?:  [camelExceptionCaught]
    payload.setProperty('response', exceptionMessages.join('\n') ?: inboundPayload)
    return payload
}
