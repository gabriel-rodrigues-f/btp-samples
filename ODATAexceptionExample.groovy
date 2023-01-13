import com.sap.gateway.ip.core.customdev.util.Message
import java.util.HashMap

def Message processData(Message message) {
    // get a map of properties
    def map = message.getProperties()

    // get an exception java class instance
    def ex = map.get('CamelExceptionCaught')
    if (ex != null) {
        // an odata v2 adapter throws an instance of com.sap.gateway.core.ip.component.odata.exception.OsciException
        if (ex.getClass().getCanonicalName().equals('com.sap.gateway.core.ip.component.odata.exception.OsciException')) {
            // save the http error request uri as a message attachment
            def messageLog = messageLogFactory.getMessageLog(message)
            messageLog.addAttachmentAsString('http.requestUri', ex.getRequestUri(), 'text/plain')
            // copy the http error request uri to an exchange property
            message.setProperty('http.requestUri', ex.getRequestUri())

            // copy the http error response body as an attachment
            messageLog.addAttachmentAsString('http.response', message.getBody(), 'text/plain')
            // copy the http error response body as a propert
            message.setProperty('http.response', message.getBody())

            // copy the http error response body as an attachment
            messageLog.addAttachmentAsString('http.statusCode', message.getHeaders().get('CamelHttpResponseCode').toString(), 'text/plain')

            // copy the http error response body as a property
            message.setProperty('http.statusCode', message.getHeaders().get('CamelHttpResponseCode').toString())
        }
    }

    return message
}
