import com.sap.gateway.ip.core.customdev.util.Message
import com.sap.it.api.asdk.datastore.DataStoreService
import com.sap.it.api.asdk.runtime.Factory

def processData(Message message) {
    def service = Factory(DataStoreService).getService()
    if (service) {
        def dsEntry = service.get('DatastoreName', 'EntryId')
        def result  = new String(dsEntry.getDataAsArray())
        message.setBody(result)
    }
    return message
}
