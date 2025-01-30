import com.sap.gateway.ip.core.customdev.util.Message
import java.math.BigDecimal

def Message processData(Message message) {
  def messageLog              = messageLogFactory.getMessageLog(message)
  def messageIdentifier   = message.getProperty('messageIdentifier') as String ?: 'Integration Flow'

  if (messageLog) {
    def messageBytes        = message.getBody(String).getBytes().length
    def messageKiloBytes    = messageBytes / 1024.0

    if (messageBytes) {
      def roundedKiloBytes = new BigDecimal(messageKiloBytes).setScale(2, BigDecimal.ROUND_HALF_UP).toString()

      messageLogFactory
                .getMessageLog(message)
                .addCustomHeaderProperty("${messageIdentifier} - Total KiloBytes", roundedKiloBytes)
    }
  }
  return message
}
