import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder

def Message processData(Message payload) {
    def builder             = new JsonBuilder()
    def unescapePayload

    builder {
        message "An Error Occurred"
        status  {
            code    500
            text    "Internal Server Error"
        }
    }

    unescapePayload = builder.toString()
    payload.setBody(unescapePayload)
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
