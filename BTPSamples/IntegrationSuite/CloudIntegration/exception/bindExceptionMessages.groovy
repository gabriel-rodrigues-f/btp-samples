import com.sap.gateway.ip.core.customdev.util.Message

/* Payload para teste
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <Body>
    <PurchaseOrderStatusNotificationMsg>
        <PurchaseOrderHeader>
        <LogMessage>
            <LogMessageText>First log message</LogMessageText>
            <LogMessageText>Second log message</LogMessageText>
        </LogMessage>
        </PurchaseOrderHeader>
    </PurchaseOrderStatusNotificationMsg>
    </Body>
</root>
 */

def Message processData(Message message) {
  InputStream reader  = message.getBody(InputStream)
  def root            = new XmlSlurper().parse(reader)
  def logMessages     = root.Body.PurchaseOrderStatusNotificationMsg.PurchaseOrderHeader.LogMessage
  def fullLogMessage  = logMessages.LogMessageText.collect { logMessage -> logMessage.text() }.join(' / ')
  message.setProperty('Comments', fullLogMessage)
  return message
}
