import com.sap.gateway.ip.core.customdev.util.Message
import java.math.BigDecimal

def Message processData(Message message) {
  def messageLog              = messageLogFactory.getMessageLog(message)
  def properties              = message.getProperties()
  def camelSplitComplete      = properties.CamelSplitComplete                 ?: ''
  def camelSplitSize          = properties.CamelSplitSize                     ?: ''
  def messageIdentifier   = properties.messageIdentifier as String   ?: 'Integration Flow'

  if (messageLog && message.getBody(String)) {
    def messageBytes        = message.getBody(String).getBytes().length
    def messageKiloBytes    = messageBytes / 1024.0

    if (messageBytes && camelSplitComplete.toString().equalsIgnoreCase('true')) {
      def roundedKiloBytes        = new BigDecimal(messageKiloBytes).setScale(2, BigDecimal.ROUND_HALF_UP)
      def updatedCamelSplitSize   = (messageKiloBytes * camelSplitSize.toInteger()).setScale(2, BigDecimal.ROUND_HALF_UP)

      messageLog.with {
        addCustomHeaderProperty("${messageIdentifier} - Total KiloBytes", "${roundedKiloBytes}")
        addCustomHeaderProperty("${messageIdentifier} - Iterations", "${camelSplitSize}")
        addCustomHeaderProperty("${messageIdentifier} - KiloBytes x Iterations", "${updatedCamelSplitSize}")
      }
    }
  }
  return message
}
