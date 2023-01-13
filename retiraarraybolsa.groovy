import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {

       def body = message.getBody(java.lang.String);
       
       
       body = body.replaceAll("version=&quot;1.0&quot;", "") 
       body = body.replaceAll("encoding=&quot;utf-16&quot;", "") 
       
        body = body.replaceAll("&lt;", "<")
        body = body.replaceAll("&gt;", ">")
        body = body.replaceAll('&quot;', '"')
        
        body = body.replaceAll("<\\?xml", "")
        body = body.replaceAll("\\?>", "")
        
       body = body.replaceAll("<chargeableItemChargeMassRequest>", "") 
       body = body.replaceAll("</chargeableItemChargeMassRequest>", "")
   
        message.setBody(body)
        
       return message;
}