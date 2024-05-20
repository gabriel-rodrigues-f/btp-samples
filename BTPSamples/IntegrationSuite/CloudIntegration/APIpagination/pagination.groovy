import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
  def properties  = message.getProperties()
  Integer skip    = properties.pSkip  as Integer  ?: 0
  Integer top     = properties.pTop   as Integer  ?: 0
  def first       = properties.pFirst
  String newSkip  = ''

  def now         = Calendar.instance
  def yearQuery   = now[java.util.Calendar.YEAR]
  def monthQuery  = String.format('%02d', now[java.util.Calendar.MONTH] + 1)

  if (first == 'false') {
    Integer result  = skip + top
    newSkip         = result.toString()
    message.setProperty('pSkip', newSkip)
    } else if (first) {
    first = 'false'
  }

  message.setProperties([
        pMonthQuery :monthQuery
        ,pYearQuery :yearQuery.toString()
        ,pFirst     :first
    ])
  return message
}
