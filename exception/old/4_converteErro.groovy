import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def response = message.getProperty('pRetorno');
    response = response.replaceAll('<', '');
    response = response.replaceAll('>', '');

    message.setProperty('pRetorno', response);
    return message
}
