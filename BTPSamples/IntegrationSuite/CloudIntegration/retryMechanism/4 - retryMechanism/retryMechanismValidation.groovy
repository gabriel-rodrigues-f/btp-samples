import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def properties          = message.getProperties()
    String pRetry           = properties.pRetry                 as String ?: ''
    String pResponseError   = properties.CamelExceptionCaught   as String ?: ''
    def pResult             = false

    def errors = [
        'HTTP Request failed with error'
        , 'Service Unavailable'
        , 'Connection reset'
        , 'Read timed out'
        ]

    if (errors.any { error -> pResponseError.contains(error) } || pRetry.contains('Yes')) {
        pResult = true
        message.setProperty('pNewRetry', pResult)
    }
    return message
}
