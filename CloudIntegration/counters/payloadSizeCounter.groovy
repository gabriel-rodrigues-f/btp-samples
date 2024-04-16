import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets

def Message processData(Message message) {
    def body        = message.getBody(String)
    def pCounter    = message.getProperty('pCounter')
    def messageSize = body.getBytes().length

    messageSize += pCounter.toInteger()

    message.setProperty('pCounter', messageSize.toInteger())
    return message
}
