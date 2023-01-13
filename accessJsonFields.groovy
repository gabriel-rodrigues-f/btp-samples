import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*
import groovy.xml.*
import java.nio.charset.StandardCharsets

Message processData(Message message) {
    //  Inicia a leitura do arquivo
    InputStream reader = message.getBody(InputStream)

    //  Define 'jsonOdata' como o objeto pai para que a partir dele possamos acessar seus campos
    //  Observar que aqui recebemos um arquivo JSON, portanto utilizaremos o método 'JsonSlurper' para que seja possível acessar suas propriedades
    def jsonOdata = new JsonSlurper().parse(reader)
    def jsonPaidAt = jsonOdata.paidAt.paidTime

    //  Define o JSON de saída
    def builder = new JsonBuilder()
    builder {
        'paidAtDate' jsonPaidAt
    }

    def jsonString = builder.toString()
    jsonString = unescapeUnicode(jsonString)
    message.setBody(jsonString)
    return message
}

//  Trata caracteres especiais
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
