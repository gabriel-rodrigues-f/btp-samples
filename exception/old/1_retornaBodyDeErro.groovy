import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    message.setProperty('entryBody', !message.getBody(String))
    return message
}
w