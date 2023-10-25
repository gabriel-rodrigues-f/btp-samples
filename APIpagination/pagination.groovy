def Message setPagingQuery(Message message){
    def pSkip       = message.getProperty("pSkip")
    def pTop        = message.getProperty("pTop")
    def pFirst      = message.getProperty("pFirst")
    String newSkip  = ""
    
    // Monta Paginação
    if (pFirst == "FALSE"){
        def result  = pSkip.toInteger() + pTop.toInteger()
        newSkip     = result
        message.setProperty("pSkip", newSkip)
    }
    else if (pFirst == "TRUE"){
        pFirst = "FALSE"
    }

	
	// Query Filters
	def now         = Calendar.instance
	def yearQuery   = now[java.util.Calendar.YEAR]
	def monthQuery  = String.format("%2d"   ,now[java.util.Calendar.MONTH] + 1).replace(" ", "0")		
	message.setProperty("pMonthQuery"       ,monthQuery.toString())
	message.setProperty("pYearQuery"        ,yearQuery.toString())
	message.setProperty("pFirst"            ,pFirst)
	return message
}
