import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    // get a map of properties
    def map = message.getProperties()

    // get an exception java class instance
    def ex = map.get('CamelExceptionCaught')
    if (ex != null) {
        // an http adapter throws an instance of org.apache.camel.component.ahc.AhcOperationFailedException
        if (ex.getClass().getCanonicalName().equals('org.apache.camel.component.ahc.AhcOperationFailedException')) {
            // save the http error response as a message attachment
            def messageLog = messageLogFactory.getMessageLog(message)
            messageLog.addAttachmentAsString('http.ResponseBody', ex.getResponseBody(), 'text/plain')

            // copy the http error response to an exchange property
            message.setProperty('http.ResponseBody', ex.getResponseBody())

            // copy the http error response to the message body
            message.setBody(ex.getResponseBody())

            // copy the value of http error code (i.e. 500) to a property
            message.setProperty('http.StatusCode', ex.getStatusCode())

            // copy the value of http error text (i.e. "Internal Server Error") to a property
            message.setProperty('http.StatusText', ex.getStatusText())
        }
    }

    return message
}
