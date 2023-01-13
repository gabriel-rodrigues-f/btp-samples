import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import groovy.json.*
import groovy.xml.*
import java.util.*

def Message processData(Message payload) {
    InputStream reader  = payload.getBody(InputStream)
    def jsonPath        = new JsonSlurper().parse(reader)

    def writer          = new StringWriter()
    def builder         = new MarkupBuilder(writer)

    builder {
        jsonPath.each {
            node -> "$node.key"("$node.value")
        }
    }

    payload.setBody(writer.toString())
    return payload
}
