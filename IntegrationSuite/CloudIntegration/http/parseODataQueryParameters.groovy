import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.Charset

Message processData(Message message) {
  String httpQuery = message.getHeader('CamelHttpQuery', String)
  if (httpQuery) {
    Map<String, String> queryParameters = URLDecoder.decode(httpQuery, Charset.defaultCharset().name())
            .tokenize('&')
            .collectEntries { param ->
                def parts = param.tokenize('=')
                def key = parts[0].startsWith('$') ? parts[0] : parts[0].replace("\$", '')
                def value = parts[1]
                [(key): value]
            }
    def queryString = queryParameters.collect { entry ->
      "${entry.key}=${entry.value}"
        }.join('&')

    message.setProperty('pQueryParam', queryString)
  }

  return message
}
