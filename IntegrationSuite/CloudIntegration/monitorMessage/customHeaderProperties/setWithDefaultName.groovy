import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.JsonSlurper;

/* 
{
    "ProfitCenterCode": "123",
    "ProfitCenterName": "Example Profit Center",
    "IsActive": "true"
}
 */

Message processData(Message message) {
    def body = message.getBody(String);
    def root = new JsonSlurper().parseText(body);

    //  Define os campos 
    def fieldsToExtract = ["ProfitCenterCode", "ProfitCenterName", "IsActive"];

    
    messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog) {
        fieldsToExtract.each { field ->
            if (root.containsKey(field)) {
                def value = root[field] as String ?: ''  ;
                messageLog.with {
                    addCustomHeaderProperty(field, value); 
                };
            };
        };
    };
    return message;
};
