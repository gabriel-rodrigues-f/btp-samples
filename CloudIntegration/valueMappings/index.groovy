import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.ITApiFactory
import com.sap.it.api.mapping.ValueMappingApi

def Message processData(Message message) {
    def valueMapApi         = ITApiFactory.getApi(ValueMappingApi, null)

    def sourceAgency        = 'SALESFORCE'
    def sourceIdentifier    = 'ID'
    def sourceValue         = '1'
    def targetAgency        = 'SAP'
    def targetIdentifier    = 'ID'

    /*  Todos os campos devem ser do tipo 'string'
        O código abaixo realiza um DE X PARA com um Value Mapping que armazena o valor dentro de uma constante
     */
    def value = valueMapApi.getMappedValue(sourceAgency, sourceIdentifier, sourceValue, targetAgency, targetIdentifier)

    message.setProperty('pStatus', value)
    return message
}
