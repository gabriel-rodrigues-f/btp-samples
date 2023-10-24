import com.sap.gateway.ip.core.customdev.util.Message
import java.util.HashMap
import com.sap.it.api.ITApiFactory
import com.sap.it.api.ITApi
import com.sap.it.api.mapping.ValueMappingApi

def Message processData(Message message) {
    def valueMapApi         = ITApiFactory.getApi(ValueMappingApi.class, null)
    def properties          = message.getProperties()

    def sourceAgency        = properties.get('sourceAgency')
    def sourceIdentifier    = properties.get('sourceIdentifier')
    def sourceValue         = properties.get('sourceValue')
    def targetAgency        = properties.get('targetAgency')
    def targetIdentifier    = properties.get('targetIdentifier')

    def value = valueMapApi.getMappedValue(sourceAgency, sourceIdentifier, sourceValue, targetAgency, targetIdentifier)

    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog) {
        if (value) {
            messageLog.addAttachmentAsString('Output : Target Value Mapping is  ', value, 'text/plain')
        }
        else {
            messageLog.addAttachmentAsString('Output : Target Value Mapping is  ', 'Not Found', 'text/plain')
        }
    }
    return message
}
