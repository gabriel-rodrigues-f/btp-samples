import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def messageLog          = messageLogFactory.getMessageLog(message)
    if(messageLog){
        def splitCounterName    = message.getProperty("pSplitCounterName")  ?: "Split Counter"
        def camelSplitComplete  = message.getProperty("CamelSplitComplete") ?: ""
        def loopCounter         = message.getProperty("CamelSplitSize") as String
        if (loopCounter && camelSplitComplete) {
            messageLogFactory
                .getMessageLog(message)
                .addCustomHeaderProperty(splitCounterName, loopCounter)
        }
    }
    return message
}
