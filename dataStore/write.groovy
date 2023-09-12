import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.asdk.datastore.*
import com.sap.it.api.asdk.runtime.*

Message processData(Message message) {
	def payload = "This is sample data"
	def service = new Factory(DataStoreService.class).getService()
	if(service) {		
		def dBean 				= new DataBean()
		def dConfig				= new DataConfig()
		dBean.setDataAsArray(	payload.getBytes("UTF-8"))		
		dConfig.setStoreName(	"DatastoreFromGroovyASDK")
		dConfig.setId(			"TestEntry")
		dConfig.setOverwrite(	true)
		result 					= service.put(dBean,dConfig)
	}		
    return message
}
