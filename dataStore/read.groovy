import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.asdk.datastore.*
import com.sap.it.api.asdk.runtime.*

Message processData(Message message) {
    def service     = new Factory(DataStoreService.class).getService()
    if(service) {
        def dsEntry = service.get("DatastoreName","EntryId")
        def result  = new String(dsEntry.getDataAsArray())
        message.setBody(body)
    }
    return message
}
