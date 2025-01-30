import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder
import groovy.json.JsonOutput

Message processData(Message payload) {
    def properties  = payload.getProperties()
    def headers     = payload.getHeaders()
    def exceptionMessage    = properties.CamelExceptionCaught as String
    def exceptionCode       = headers.CamelHttpResponseCode as String

    def builder = new JsonBuilder()
    builder {
        'message'       exceptionMessage
        'statusCode'    exceptionCode
    }
    
    def jsonString = builder.toString()
    jsonString = JsonOutput.prettyPrint(jsonString)
    payload.setBody(jsonString)
    return payload
}
