import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
	
	def body = message.getBody(String.class);
	
    def nometipo = body.find{'(?<=filename=")(.*)(?=.csv")'};
    message.setProperty("P_Tipo", nometipo);

    body = body.replaceAll('(?m)^\\s*---+.*$', '');
    body = body.replaceAll('(?m)^\\s*Content+.*$', '');
    body = body.replaceAll('(?m)^\\s*' '+.*$', '');
    
	message.setBody(body);
	return message;
}