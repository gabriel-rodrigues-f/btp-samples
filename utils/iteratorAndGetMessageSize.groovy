import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def body        = message.getBody(String)
    def messageSize = body.getBytes().length ?: 0
    def pCounter    = message.getProperty("pCounter") ?: 0
        
    if(pCounter){
        messageSize = messageSize.toInteger() + pCounter.toInteger()
    }

    message.setProperty("pCounter", messageSize)
    return message
}
