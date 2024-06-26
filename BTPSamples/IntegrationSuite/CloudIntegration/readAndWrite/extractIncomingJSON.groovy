import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def Message processData(Message message) {
    def body                = message.getBody(String)
    def slurper             = new JsonSlurper().parseText(body)
    def unicodeRequestBody  = unescapeUnicode(JsonOutput.toJson(slurper.json))
    
    message.setProperty("requestBody", unicodeRequestBody)
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
