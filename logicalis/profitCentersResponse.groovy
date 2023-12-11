import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

Message processData(Message message) {
    def body    = message.getBody(String)
    def builder = new JsonBuilder()
    def headers = message.getHeaders()
    def jsonRoot    = new JsonSlurper().parseText(body)
    def camelHttpResponseCode   = headers.CamelHttpResponseCode     as String ?: ''
    def camelHttpResponseText   = headers.CamelHttpResponseText     as String ?: 'Error'
    def camelHttpUrl            = headers.CamelHttpUrl              as String ?: ''

    builder {
        data    jsonRoot
        status  {
            code    camelHttpResponseCode
            text    camelHttpResponseText
        }
        link camelHttpUrl
    }

    def jsonString = unescapeUnicode(JsonOutput.prettyPrint(builder.toString()))
    message.setBody(jsonString)
    return message
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
