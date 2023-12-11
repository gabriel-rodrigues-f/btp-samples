import com.sap.gateway.ip.core.customdev.util.Message
import java.net.URLDecoder
import java.nio.charset.Charset

Message processData(Message message) {
    String httpUri = message.getHeader('CamelHttpUri', String)

    if (httpUri) {
        def businessOneIndex    = httpUri.indexOf("businessone/") + "businessone/".length()
        def resourceAndQuery    = httpUri.substring(businessOneIndex)
        def parts               = resourceAndQuery.tokenize('?')
        def resource            = parts[0]
        def queryParams         = parts.size() > 1 ? parts[1] : null
        def queryString         = queryParams ? URLDecoder.decode(queryParams, Charset.defaultCharset().name()) : null

        message.setProperty("pResource"         ,resource)
        if (queryString) {
            message.setProperty("pQueryParam"   ,queryString)
        }
    }

    return message
}
