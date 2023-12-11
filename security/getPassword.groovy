import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.mapping.ValueMappingApi
import com.sap.it.api.securestore.SecureStoreService
import com.sap.it.api.securestore.UserCredential
import com.sap.it.api.securestore.exception.SecureStoreException

def Message processData(Message message) {
    def apiKeyAlias             = message.getProperty("secureParameter")
    def secureStorageService    = ITApiFactory.getService(SecureStoreService.class, null)
    
    try{
        def secureParameter     = secureStorageService.getUserCredential(apiKeyAlias)
        def apikey              = secureParameter.getPassword().toString()
        message.setProperty("p_userpass", apikey)
    } catch(Exception e){
        throw new SecureStoreException("Secure Parameter not available")
    }
    return message
}
