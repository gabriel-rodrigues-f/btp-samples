import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def messageLog      = messageLogFactory.getMessageLog(message)

    def logProperty     = message.getProperty('logProperty') as String
    def logHeader       = message.getProperty('logHeader') as String
    def logBody         = message.getProperty('logBody') as String

    String content = ''

    if (messageLog) {
        messageLog.setStringProperty('LogType', 'Payload')

        //  equalsCaseIgnore ignora o case da palavra e faz a comparação
        //  esse trecho verifica se logProperty está setado como 'yes'

        if (logProperty.equalsIgnoreCase('yes')) {
            def propertyMap = message.getProperties()
            StringBuffer buffer = new StringBuffer()

            //  entrySet() transforma o objeto em um conjunto
            for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                buffer.append('<').append(entry.getKey()).append('>')
                buffer.append(entry.getValue())
                buffer.append('</').append(entry.getKey()).append('>\n')
            }
            content = content + '\n' + buffer.toString()
        }

        //  equalsCaseIgnore ignora o case da palavra e faz a comparação
        //  esse trecho verifica se logHeader está setado como 'yes'
        if (logHeader.equalsIgnoreCase('yes')) {
            def header = message.getHeaders() as String
            content = content + '\n' + header
        }

        //  equalsCaseIgnore ignora o case da palavra e faz a comparação
        //  esse trecho verifica se logBody está setado como 'yes'
        if (logBody.equalsIgnoreCase('yes')) {
            def body = message.getBody(String) as String
            content = content + '\n' + body
        }

        //  Verifica se 'content' está vazio
        if (content) {
            messageLog.addAttachmentAsString('Error: ', content, 'text/plain')
        }
    }

    return message
}
