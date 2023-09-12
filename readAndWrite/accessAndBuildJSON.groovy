import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

Message processData(Message message) {
    InputStream reader  = message.getBody(InputStream)
    def jsonPath        = new JsonSlurper().parse(reader)
    def builder         = new JsonBuilder()
    def exampleField    = jsonPath.exemploEntrada
    builder {
        'exemploSaida' exampleField
    }
    def jsonString      = builder.toString()
    jsonString          = unescapeUnicode(jsonString)
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
