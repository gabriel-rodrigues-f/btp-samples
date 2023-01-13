import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*
import groovy.xml.*
import java.nio.charset.StandardCharsets
import java.util.Date

def Message processData(Message message) {
    //  Inicia a leitura do arquivo
    InputStream reader = message.getBody(InputStream)

    //  Define 'testeXml' como o objeto pai para que a partir dele possamos acessar seus campos
    def testeXml = new XmlSlurper().parse(reader)

    //  Modelo de split
    String inputDenvio = testeXml.TESTE.text().split(' ')[0]

    //  Define o JSON de saída
    def builder = new JsonBuilder()
    builder {
            'testeSubString' inputDenvio
    }
    message.setBody(builder.toString())
    return message
}
