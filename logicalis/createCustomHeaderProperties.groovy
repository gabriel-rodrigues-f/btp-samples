import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

Message processData(Message message) {
    def body    = message.getBody(String)
    def root    = new JsonSlurper().parseText(body)
    def profitCenterCode        = root.ProfitCenterCode     as String ?: ''
    def profitCenterName        = root.ProfitCenterName     as String ?: ''
    def isActive                = root.IsActive             as String ?: ''

	messageLog = messageLogFactory.getMessageLog(message)
	if(messageLog){
		if(profitCenterCode){
            messageLog.with {
                addCustomHeaderProperty("Profit Center Code"    ,profitCenterCode)
                addCustomHeaderProperty("Profit Center Name"    ,profitCenterName)
                addCustomHeaderProperty("Is active"             ,isActive)
            }
        }
	}
	return message
}
