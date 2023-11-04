import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message){
    def map             = message.getProperties()
    String pRetorna     = map.get("pRetorna") as String
	String pRetornoErro = map.get("CamelExceptionCaught") as String
	def pResult         = "0"

	if (pRetornoErro.contains("Read timed out")
		|| pRetornoErro.contains("Connection reset")
		|| pRetornoErro.contains("HTTP Request failed with error")
		|| pRetornoErro.contains("Service Unavailable")
		|| pRetorna.contains("Sim")
		|| pRetornoErro.contains("bloqueado por usuário")
		|| pRetornoErro.contains("Ordem de Referencia já localizada para a cotacao")
    ) {	
		pResult = "1"
		message.setProperty("p_RealizeRetry", pResult)	
	}	
	return message
}
