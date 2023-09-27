import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def map             = message.getProperties()
    def exception       = map.CamelExceptionCaught  as String ?: ''
    def errordetails    = ""
    
    if (exception) {
        def messageLog  = messageLogFactory.getMessageLog(message)
        errordetails    = exception.toString()
        message.setProperty("pRetorno", errordetails)
    }
    return message
}
