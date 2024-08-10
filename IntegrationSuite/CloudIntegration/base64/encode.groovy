import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
  def payload         = 'testando'
  def encodedPayload  = payload.bytes.encodeBase64().toString()

  message.setBody(encodedPayload)
  return message
}
