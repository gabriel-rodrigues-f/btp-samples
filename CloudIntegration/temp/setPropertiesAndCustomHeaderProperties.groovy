import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

Message processData(Message request) {
    Map<String, String> properties  = request.getProperties()
    Map<String, String> headers     = request.getHeaders()
    String idLoja   = properties.idLoja as String ?: ''

    messageLog = messageLogFactory.getMessageLog(request)
    if (messageLog && idLoja) {
        messageLog.with {
            addCustomHeaderProperty('idLoja'        ,idLoja)
            addCustomHeaderProperty('idCadastro'    ,headers.CamelHttpPath)
        }
    }
    return request
}
