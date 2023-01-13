import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonBuilder
import groovy.util.XmlSlurper

Message processData(Message message) {
  //  Inicia a leitura do arquivo
  InputStream reader = message.getBody(InputStream)

  //  Define 'cupomXml' como o objeto pai para que a partir dele possamos acessar seus campos
  //  Observar que aqui recebemos um arquivo XML, portanto utilizaremos o método 'XmlSlurper' para que seja possível acessar suas propriedades
  cupomXml = new XmlSlurper().parse(reader)

  //  Define o JSON de saída
  JsonBuilder builder = new JsonBuilder()
  builder {
    'excludedAt' cupomXml.DataExclusao.text()
  }
  message.setBody(builder.toString())
  return message
}
