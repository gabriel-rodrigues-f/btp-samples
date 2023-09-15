import com.sap.gateway.ip.core.customdev.util.Message
import groovy.util
import groovy.util.XmlSlurper

def Message processData(Message message) {	
    def root            = new XmlSlurper().parseText(message.getBody(String))
    def logMessages     = root.Body.PurchaseOrderStatusNotificationMsg.PurchaseOrderHeader.LogMessage
    def logMessageTexts = logMessages.LogMessageText.collect { it.text() }.join(" / ")
    message.setProperty("Comments", logMessageTexts)
    return message
}
