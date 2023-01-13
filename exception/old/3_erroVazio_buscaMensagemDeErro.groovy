import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def map = message.getProperties()
    if (ex) {
        message.setProperty('pRetorno', map.get('CamelExceptionCaught'))
    }
    return message
}
