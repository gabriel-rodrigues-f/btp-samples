import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*
import java.nio.charset.StandardCharsets


// Template para validar se a entrada trata-se de um JSON
Message processData(Message payload) {

    //  Declara o método que constrói o json de saída
    def builder = new JsonBuilder()

    try {
        //  Faz a leitura do arquivo
        InputStream reader = payload.getBody(InputStream)

        //  Declara que jsonPath é a raíz do JSON que foi lido
        def jsonPath = new JsonSlurper().parse(reader)

        // Monta a estrutura de saída
        builder {
            'status' 500
            'message' 'Internal Server Error'
        }
        //  Captura a exception
    } catch (Exception error) {
        builder {
            'status' 500
            'message' 'Internal Server Error'
        }
    }

    def jsonString = builder.toString()
    jsonString = unescapeUnicode(jsonString)
    payload.setBody(jsonString)
    return payload
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

