import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    def base64          = "dGVzdGFuZG8="
    def decodedPayload  = base64.decodeBase64()
    def decodedText     = new String(decodedPayload, "UTF-8")
    print decodedText

    return message
}