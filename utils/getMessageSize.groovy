import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def body    = message.getBody(String)
    println body.getBytes().length
    return message
}