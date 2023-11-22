import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def messageLog          = messageLogFactory.getMessageLog(message)
    def camelSplitComplete  = message.getProperty("CamelSplitComplete") ?: ""
    def camelSplitSize      = message.getProperty("CamelSplitSize") ?: ""

    if(messageLog){
        def messageSize = message.getBody(String).getBytes().length as String
        if (messageSize && camelSplitComplete) {

            messageLogFactory
                .getMessageLog(message)
                .addCustomHeaderProperty("payloadSize", messageSize)

            messageLogFactory
                .getMessageLog(message)
                .addCustomHeaderProperty("split iterations", camelSplitSize)
        }
    }
    return message
}
