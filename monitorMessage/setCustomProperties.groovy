import com.sap.gateway.ip.core.customdev.util.Message;

Message processData(Message message) {
	messageLog = messageLogFactory.getMessageLog(message)
	if(messageLog){
		po_number = message.getHeaders().po_number
		if(po_number){
			messageLog.addCustomHeaderProperty("po_number", po_number)
        }
	}
	return message
}
