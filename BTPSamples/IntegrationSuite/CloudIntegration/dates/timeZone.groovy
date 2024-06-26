import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
  def localTimeZone   = TimeZone.getTimeZone('America/Sao_Paulo')
  def cal             = Calendar.instance
  def timeFormat      = '\'PT\'HH\'H\'mm\'M\'ss\'S\''
  def date            = cal.time
  def dateFormat      = 'yyyy-MM-dd\'T00:00:00\''
  message.setProperties([
        'nowDateUtc'    :date.format(dateFormat, localTimeZone),
        'nowTimeUtc'    :date.format(timeFormat, localTimeZone)
    ])
  return message
}
