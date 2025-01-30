import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

/*
payload
{
    "ProfitCenterCode": "123",
    "ProfitCenterName": "teste",
    "IsActive": "false"
}

property
businessProperties: ["ProfitCenterCode", "ProfitCenterName"]
*/

Message processData(Message message) {
    def slurper = new JsonSlurper();
    def body    = message.getBody(String)
    
    def root    = slurper.parseText(body)
    def properties      = message.getProperties()
    def businessProperties = properties.containsKey('businessProperties') 
                            ? slurper.parseText(properties.businessProperties) 
                            : []

    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog) {
        businessProperties.each { field ->
            if (root.containsKey(field)) {
                def value = root[field] as String ?: ''
                messageLog.with {
                    addCustomHeaderProperty(field, value)
                }
            }
        }
    }

    return message
}
