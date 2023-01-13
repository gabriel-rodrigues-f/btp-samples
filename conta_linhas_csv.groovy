import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
 
 def QtdLin = 0;
 def lines = message.getBody(java.lang.String) as String;
    lines.eachLine {
    QtdLin++} 
 
 message.setProperty("countcsv",QtdLin);
 return message;
}