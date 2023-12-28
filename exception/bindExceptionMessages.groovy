import com.sap.gateway.ip.core.customdev.util.Message
import groovy.util.XmlSlurper

def Message processData(Message message) {
    def body    = message.getBody(String)
    def root    = new XmlSlurper().parseText(body)
    def logMessages     = root.Body.PurchaseOrderStatusNotificationMsg.PurchaseOrderHeader.LogMessage
    def fullLogMessage  = logMessages.LogMessageText.collect { logMessage -> logMessage.text() }.join(' / ')
    message.setProperty('Comments', fullLogMessage)
    return message
}
