import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def body    = message.getBody(String)
    def xml     = new XmlSlurper().parseText(body)
    def xFields = xml.'**'.findAll { tag -> tag.tagName != '' }
    message.setProperty('tag', xFields.size())
    return message
}
