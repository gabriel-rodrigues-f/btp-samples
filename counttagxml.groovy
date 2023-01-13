import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
 
 def body = message.getBody(java.lang.String) as String;
 def xml = new XmlSlurper().parseText(body)
 
 def campos = xml.'**'.findAll { it.CURSO != ""}
 
 message.setProperty("countcsv",campos.size());
 return message;
}