import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
  def param    = message.getProperty('param')
  param        = param.with {
        replaceAll('%20', ' ').toString()
        replaceAll('%27', "'").toString()
  }
  // param        = param.replaceAll('%20', ' ').toString()
  // param        = param.replaceAll('%27', "'").toString()
  message.setProperty(param)
  return message
}
