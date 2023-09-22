import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
	def messageLog = messageLogFactory.getMessageLog(message)
	if(messageLog){
		def po_number = message.getHeaders().po_number
		if(po_number){
			messageLog.addCustomHeaderProperty("po_number", po_number);	
        }
	}
	return message
}