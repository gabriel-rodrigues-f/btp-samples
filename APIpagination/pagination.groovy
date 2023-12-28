import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    Integer skip    = message.getProperty('pSkip')  as Integer
    Integer top     = message.getProperty('pTop')   as Integer
    def first       = message.getProperty('pFirst')
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

    message.with {
        setProperty('pMonthQuery'   ,monthQuery)
        setProperty('pYearQuery'    ,yearQuery.toString())
        setProperty('pFirst'        ,first)
    }
    return message
}
