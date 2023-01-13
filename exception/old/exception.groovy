import com.sap.gateway.ip.core.customdev.util.SoapHeader;
import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.*;
import groovy.xml.*;
import java.util.*;

def Message isErrorBodyEmpty(Message message){
    def body = message.getBody(String)
    message.setProperty('EmptyPayload', body == null || body.isEmpty())
    return message
}

def Message findErrorException(Message message){
	// get a map of iflow properties
	def map = message.getProperties()
	def logException = map.get("ExceptionLogging")
	def errordetails = ""
	
	// get an exception java class instance
	def ex = map.get("CamelExceptionCaught")
	if (ex!=null) 
	{
	// save the error response as a message attachment 
	def messageLog = messageLogFactory.getMessageLog(message);
	
	errordetails = ex.toString()
	message.setProperty("pRetorno", errordetails);
	}

	return message;
}

def Message convertErrorData(Message message){
    
    def returnMessage = message.getProperty("pRetorno");
    returnMessage = returnMessage.replaceAll("<", "");
    returnMessage = returnMessage.replaceAll(">", "");
    
    message.setProperty("pRetorno", returnMessage);
    return message;
}

def Message formatErrorPayload(Message message){
	StringBuffer reqXML = new StringBuffer(); 
	def payload = message.getBody(java.lang.String) as String; 
	def tokens = payload.split("(?=<)|(?<=>)"); 
	def flag  = false;
	def start = false;
	def pRetorno = "" as String;
	def pCustomError = message.getProperty("P_CustomError");

	for(int i=0;i<tokens.length;i++) { 
		if( tokens[i] == "<errordetails>" ){
		    reqXML.append(tokens[i]);
		    flag  = false;
		    start = true;
		}
		else if( tokens[i] == "<errordetail>" ){
		    reqXML.append(tokens[i]);
		    flag = false;
		}
		else if( tokens[i] == "<message>" ){
		    flag = true;
		}
		else if( tokens[i] == "</errordetails>" || tokens[i] == "</errordetail>" || tokens[i] == "</message>" ){
		    if( start == true ){
    		    reqXML.append(tokens[i]);
    		    flag = false;
		    }
		}
		if( start == true && flag == true){
		    reqXML.append(tokens[i]);
		}
	}
	
    pRetorno = reqXML.toString();
    
    if( pRetorno != "" ){
	    message.setProperty("pRetorno", pRetorno);
	}
	else if( pRetorno == ""){
	    if ( pCustomError == "" || pCustomError == null){
          def map = message.getProperties()
          def logException = map.get("ExceptionLogging")
          def errordetails = ""
          def ex = map.get("CamelExceptionCaught")
          if (ex!=null) 
          {
            def messageLog = messageLogFactory.getMessageLog(message);
            errordetails = ex.toString()
            message.setProperty("pRetorno", errordetails);         
		  }
		  else{
		    message.setProperty("pRetorno", "");           
		  }
		}else{
			message.setProperty("pRetorno", pCustomError);         
		}
	}
	
	return message; 
	
}

def Message logMessagePayload(Message message) {
    //Body
    def messageLog = messageLogFactory.getMessageLog(message);
    
    def logProperty = message.getProperty("logProperty") as String;
    def logHeader = message.getProperty("logHeader") as String;
    def logBody = message.getProperty("logBody") as String;
    String content = "";
    
    if(messageLog != null) {
        messageLog.setStringProperty("Logging#1", "Payload");
        
        if (logProperty != null && logProperty.equalsIgnoreCase("yes")) {
            def propertyMap = message.getProperties();
            StringBuffer buffer = new StringBuffer();
            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                buffer.append("<").append(entry.getKey()).append(">");
                buffer.append(entry.getValue());
                buffer.append("</").append(entry.getKey()).append(">\n");
            }
            content = content + "\n" + buffer.toString();
        }
        
        if (logHeader != null && logHeader.equalsIgnoreCase("yes")) {
            def header = message.getHeaders() as String;
            content = content + "\n" + header;
        }
        
        if (logBody == null || logBody.equalsIgnoreCase("yes")) {
            def body = message.getBody(java.lang.String) as String;
            content = content + "\n" + body;
        }
        
        if (content.length() > 0) {
            messageLog.addAttachmentAsString("Payload Entrada", content, "text/plain");    
        }
    }

    return message;  
}

def Message logErrorPayload(Message message) {
    //Body
    def messageLog = messageLogFactory.getMessageLog(message);
    
    def logProperty = message.getProperty("logProperty") as String;
    def logHeader = message.getProperty("logHeader") as String;
    def logBody = message.getProperty("logBody") as String;
    def idTransaction = message.getProperty("p_numeroCte") as String;
    
    String content = "";
    
    if(messageLog != null) {
        messageLog.setStringProperty("Logging#1", "Payload");
        
        if (logProperty != null && logProperty.equalsIgnoreCase("yes")) {
            def propertyMap = message.getProperties();
            StringBuffer buffer = new StringBuffer();
            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                buffer.append("<").append(entry.getKey()).append(">");
                buffer.append(entry.getValue());
                buffer.append("</").append(entry.getKey()).append(">\n");
            }
            content = content + "\n" + buffer.toString();
        }
        
        if (logHeader != null && logHeader.equalsIgnoreCase("yes")) {
            def header = message.getHeaders() as String;
            content = content + "\n" + header;
        }
        
        if (logBody == null || logBody.equalsIgnoreCase("yes")) {
            def body = message.getBody(java.lang.String) as String;
            content = content + "\n" + body;
        }
        
        if (content.length() > 0) {
            messageLog.addAttachmentAsString("Payload Erro - Transaction: " + idTransaction, content, "text/plain");    
        }
    }

    return message;    
}

def String getProperty(String propertyName, MappingContext context) {

    def property = context.getProperty(propertyName);
	return property.toString();

}