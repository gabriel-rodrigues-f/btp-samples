import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.SecureStoreService
import com.sap.it.api.securestore.exception.SecureStoreException

def Message processData(Message message) {
    def apiKeyAlias             = "APIKEYName"
    def secureStorageService    = ITApiFactory.getService(SecureStoreService.class, null)

    try {
        def secureParameter     = secureStorageService.getUserCredential(apiKeyAlias)
        def apikey              = secureParameter.getPassword().toString()
        message.setProperty('pUserPassword', apikey)
    } catch (Exception e) {
        throw new SecureStoreException('Secure Parameter not available')
    }
    return message
}
