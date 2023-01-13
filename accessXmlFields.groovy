import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.*
import groovy.xml.*
import java.nio.charset.StandardCharsets

Message buildScholarshipSchema(Message message) {
  //  Inicia a leitura do XML
  InputStream reader = message.getBody(InputStream)

  //  Define 'xmlOdata' como o objeto pai para que, à partir dele possamos acessar seus campos
  def xmlOdata = new XmlSlurper().parse(reader)
  def xmlUpdatedAt = xmlOdata.MTBolsa.DtHrMod.text()

  //  Converte timestamp para Unix Epoch
  def unixUpdatedAt = Date.parse('dd.MM.yyyy HH:mm:ss', xmlUpdatedAt).getTime()

  //  Define o JSON de saída
  def builder = new JsonBuilder()
  builder {
        'chargePlanPosition' xmlOdata.MTBolsa.CodBolsa.text()
        'retroactive' xmlOdata.MTBolsa.Retroativa.text()
        'dropoutMore' xmlOdata.MTBolsa.MultaEvasao.text()
        'calculationBases' xmlOdata.MTBolsa.TpBase.text()
        'value' xmlOdata.MTBolsa.Valor.text()
        'updatedAt' unixUpdatedAt
        'calculationType' xmlOdata.MTBolsa.TpCalc.text()
        'installmentTo' xmlOdata.MTBolsa.ParcelaAte.text()
        'type' xmlOdata.MTBolsa.Tipo.text()
        'contractNumber' xmlOdata.MTBolsa.Contrato.text()
        'notes' xmlOdata.MTBolsa.Observacao.text()
        'installmentFrom' xmlOdata.MTBolsa.ParcelaDe.text()
        'renewable' xmlOdata.MTBolsa.Renovavel.text()
        'scholarshipDescription' xmlOdata.MTBolsa.DescrBolsa.text()
        'priority' xmlOdata.MTBolsa.PriCalc.text()
        'userUpdated' xmlOdata.MTBolsa.UsuarioMod.text()
  }
  def jsonString = builder.toString()
  jsonString = unescapeUnicode(jsonString)
  message.setBody(jsonString)
  return message
}

Message buildVoucherSchema(Message message) {
  //  Inicia a leitura do arquivo
  InputStream reader = message.getBody(InputStream)

  //  Define 'xmlOdata' como o objeto pai para que a partir dele possamos acessar seus campos
  //  Observar que aqui recebemos um arquivo XML, portanto utilizaremos o método 'XmlSlurper' para que seja possível acessar suas propriedades
  def xmlOdata = new XmlSlurper().parse(reader)
  def xmlExcludedAt = xmlOdata.MTCupom.DataExclusao.text()
  def xmlAppliedAt = xmlOdata.MTCupom.DataExclusao.text()

  //  Converte timestamp para Unix Epoch
  def unixExcludedAt = Date.parse('dd.MM.yyyy HH:mm:ss', xmlExcludedAt).getTime()
  def unixAppliedAt = Date.parse('dd.MM.yyyy HH:mm:ss', xmlAppliedAt).getTime()

  //  Define o JSON de saída
  def builder = new JsonBuilder()
  builder {
        'excludedAt' unixExcludedAt
        'type' xmlOdata.MTCupom.Tipo.text()
        'contractNumber' xmlOdata.MTCupom.Contrato.text()
        'voucherDescription' xmlOdata.MTCupom.NomeCupom.text()
        'appliedAt' unixAppliedAt
        'calculationType' xmlOdata.MTCupom.TipoCalculo.text()
        'qntInstallments' xmlOdata.MTCupom.QtdMeses.text()
        'amount' xmlOdata.MTCupom.Valor.text()
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
