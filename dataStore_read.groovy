import com.sap.gateway.ip.core.customdev.util.Message

//Imports for DataStoreService-class
import com.sap.it.api.asdk.datastore.*
import com.sap.it.api.asdk.runtime.*

Message processData(Message message) {
	
	//Get service instance
	def service = new Factory(DataStoreService.class).getService()
	
	//Check if valid service instance was retrieved
	if( service != null) {			
		//Read data store entry via id
		def dsEntry = service.get("DatastoreName","EntryId")
		def result = new String(dsEntry.getDataAsArray())
		message.setBody(body)
	}		
    return message
}