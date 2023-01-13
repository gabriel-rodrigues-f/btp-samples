import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*
import groovy.xml.*
import java.nio.charset.StandardCharsets

Message processData(Message message) {
  //  Inicia a leitura do arquivo
  InputStream reader = message.getBody(InputStream)

  //  Define 'xmlOdata' como o objeto pai para que a partir dele possamos acessar seus campos
  def xmlOdata = new XmlSlurper().parse(reader)

  //  Modelo de switch
  TpCalc = xmlOdata.MTBolsa.TpCalc.text()
  switch (TpCalc) {
      case 'P':
      tpCalc = 'Percentual'
      break
      case 'N':
      tpCalc = 'Nominal'
      break
      case 'C':
      tpCalc = 'Chegada'
      break
      default:
        tpCalc = ''
      break
  }

//  Define o JSON de saída
  builder = new JsonBuilder()
  builder {
        'calculationType' tpCalc
  }

  jsonString = builder.toString()
  jsonString = unescapeUnicode(jsonString)
  message.setBody(jsonString)
  return message
}

//Transform special characters
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
