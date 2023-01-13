import com.sap.gateway.ip.core.customdev.util.Message
import java.util.HashMap

def Message processData(Message message) {
    //	Body
    def body = message.getBody()
    
    //	Segura o evento pelo tempo determinado em ms (1000 = 1s)
    sleep(1000)
    message.setBody(body)
    return message
}