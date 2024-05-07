import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import groovy.json.JsonBuilder
import groovy.json.JsonOutput

Message processData(Message payload) {
    def body            = payload.getBody(String)
    def root            = new XmlSlurper().parseText(body)
    def builder         = new JsonBuilder()
    def properties      = payload.getProperties()
    def headers         = payload.getHeaders()
    def camelException  = properties.CamelExceptionCaught as String
    def errorMessage    = root.message.text()
    def camelHttpResponseCode = headers.CamelHttpResponseCode as String

    builder {
        'message' camelException
        'status' {
            'code'          camelHttpResponseCode
            'description'   errorMessage
        }
    }
    
    def jsonString      = builder.toString()
    jsonString          = unescapeUnicode(JsonOutput.prettyPrint(jsonString))
    payload.setBody(jsonString)
    
    return payload
}

def unescapeUnicode(def inp) {
    (inp =~ /\\u([0-9a-f]{2})([0-9a-f]{2})/).each { m ->
        def uniAsString = new String([
                                Integer.parseInt(m[1], 16),
                                Integer.parseInt(m[2], 16)
                            ] as byte[], StandardCharsets.UTF_16)
        inp = inp.replace(m[0], uniAsString)
}
    return inp
}
