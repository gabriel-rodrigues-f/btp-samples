import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def properties      = message.getProperties()
    def errorResponse   = properties.CamelExceptionCaught as String ?: ''

    if (shouldTriggerRetry(errorResponse)) {
        message.setProperty('p_RealizeRetry', 1)
    }
    return message
}

def shouldTriggerRetry(String errorResponse) {
    def retryKeywords = [
        'Read timed out'
        ,'Connection reset'
        ,'HTTP Request failed with error'
        ,'Service Unavailable'
        ]
    return retryKeywords.any { error -> errorResponse.contains(error) }
}
