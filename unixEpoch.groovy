import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*
import groovy.xml.*
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

Message processData(Message message) {
  //  Inicia a leitura do arquivo
  InputStream reader = message.getBody(InputStream)

  //  Define 'bolsaxml' como o objeto pai para que a partir dele possamos acessar seus campos
  def bolsaxml = new XmlSlurper().parse(reader)
  def inputDenvio = bolsaxml.ModBolsa.DataEnvio.text()

  //  Converte timestamp para Unix Epoch
  //  https://www.epochconverter.com/
  def unixDataEnvio = Date.parse('dd.MM.yyyy HH:mm:ss', inputDenvio).getTime()

  //  Define JSON de saída
  def builder = new JsonBuilder()
  builder {
        'sentAt' unixDataEnvio
  }

  message.setBody(builder.toString())
  return message
}
