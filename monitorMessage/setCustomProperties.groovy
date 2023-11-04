import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
	messageLog = messageLogFactory.getMessageLog(message)
	if(messageLog){
		pTopic = message.getProperty("pTopic")
		if(pTopic){
			messageLog.addCustomHeaderProperty("pTopic", pTopic)
        }
	}
	return message
}
