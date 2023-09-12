import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.MarkupBuilder
import java.nio.charset.StandardCharsets

Message processData(Message message) {
  InputStream reader    = message.getBody(InputStream)
  def xmlPath           = new XmlSlurper().parse(reader)
  def writer            = new StringWriter()
  def builder           = new MarkupBuilder(writer)
  def myXmlField        = xmlPath.field.text()
  builder.output {
        'exemploSaida' myXmlField
  }
  message.setBody(writer.toString())
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
