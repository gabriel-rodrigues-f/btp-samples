import com.sap.gateway.ip.core.customdev.util.Message

//Imports for DataStoreService-class
import com.sap.it.api.asdk.datastore.*
import com.sap.it.api.asdk.runtime.*

Message processData(Message message) {
	
	//Data to be stored in datatore
	def payload = "This is sample data"
	//Get service instance
	def service = new Factory(DataStoreService.class).getService()
	
	//Check if valid service instance was retrieved
	if( service != null) {		
		def dBean = new DataBean()
		dBean.setDataAsArray(payload.getBytes("UTF-8"))		
		//Class model offers headers, but for me it didn't work out
		//Map<String, Object> headers = ["headerName1":"me", "anotherHeader": false]
		//dBean.setHeaders(headers)
		
		//Define datatore name and entry id
		def dConfig = new DataConfig()
		dConfig.setStoreName("DatastoreFromGroovyASDK")
		dConfig.setId("TestEntry")
		dConfig.setOverwrite(true)
		
		//Write to data store
		result = service.put(dBean,dConfig)
	}		
    return message
}