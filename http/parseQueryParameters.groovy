import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def param    = message.getProperty("param")
    param        = param.replaceAll("%20"    ," ").toString()
    param        = param.replaceAll("%27"    ,"'").toString()

    return message
}
