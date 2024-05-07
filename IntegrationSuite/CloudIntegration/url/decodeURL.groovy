import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def camelHttpQuery          = message.getHeader('CamelHttpQuery', String) ?: ''

    if (camelHttpQuery) {
        def decodedCamelHttpQuery   = URLDecoder.decode(camelHttpQuery, 'UTF-8')
        message.setHeader('CamelHttpQuery', decodedCamelHttpQuery)
        println decodedCamelHttpQuery
    }
    return message
}
