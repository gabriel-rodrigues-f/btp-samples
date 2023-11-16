import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.MarkupBuilder

Message processData(Message message) {
    InputStream reader  = message.getBody(InputStream)
    def xmlPath     = new XmlSlurper().parse(reader)
    def writer      = new StringWriter()
    def builder     = new MarkupBuilder(writer)
    def docNum      = xmlPath.Fatura_Ordens.Docnum.text()

	messageLog = messageLogFactory.getMessageLog(message)
	if(messageLog){
        idTransaction   = message.getProperty("pIdTransaction")
		if(idTransaction){
            messageLog.addCustomHeaderProperty("idTransaction"  ,idTransaction)
        }
	}
    message.setProperty("pDocnum"   ,docNum)
    message.setHeaders([
        "SAP_ApplicationID" :docNum,
        "SAP_MessageType"   :"Billing Document Number"
        ])
	return message
}
