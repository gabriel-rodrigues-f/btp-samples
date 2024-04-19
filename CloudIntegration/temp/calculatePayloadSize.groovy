import com.sap.gateway.ip.core.customdev.util.Message
import java.math.BigDecimal

def Message processData(Message request) {
  def messageLog          = messageLogFactory.getMessageLog(request)
  def payloadSizeProperty = request.getProperty('p_identificadorPayload') as String ?: 'Integration Flow'

  if (messageLog) {
    def messageBytes        = request.getBody(String).getBytes().length
    def messageKiloBytes    = messageBytes / 1024.0

    if (messageBytes) {
      def roundedKiloBytes = new BigDecimal(messageKiloBytes).setScale(2, BigDecimal.ROUND_HALF_UP).toString()
      messageLogFactory
                .getMessageLog(request)
                .addCustomHeaderProperty("${payloadSizeProperty} - Total KiloBytes", roundedKiloBytes)
    }
  }
  return request
}
