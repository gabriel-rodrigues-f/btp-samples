import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.JsonSlurper;
/* 
{
    "ProfitCenterCode": "123",
    "ProfitCenterName": "Example Profit Center",
    "IsActive": "false"
}
*/

Message processData(Message message) {
    def body = message.getBody(String);
    def root = new JsonSlurper().parseText(body);

    def fieldsToExtract     = [ "ProfitCenterCode",  "ProfitCenterName",  "IsActive"]
    def customFieldNames    = [
      "ProfitCenterCode"    :"Profit Center Code",
      "ProfitCenterName"    :"Profit Center Name",
      "IsActive"            :"Active Status"
    ];
    
    messageLog              = messageLogFactory.getMessageLog(message);

    if (messageLog) {
        fieldsToExtract.each { field ->
            if (root.containsKey(field)) {
                def value = root[field] as String ?: '';
                def headerName = customFieldNames.get(field, field);
                messageLog.with {
                    addCustomHeaderProperty(headerName, value);
                };
            };
        };
    };
    return message;
};
