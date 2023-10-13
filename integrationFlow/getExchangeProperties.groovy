import com.sap.gateway.ip.core.customdev.util.Message
import org.apache.camel.Exchange
import org.apache.camel.builder.SimpleBuilder

def Message processData(Message message) {
	Exchange exchange       = message.exchange
	StringBuilder builder   = new StringBuilder()
	
	def evaluateSimple = { simpleExpression ->
		SimpleBuilder.simple(simpleExpression).evaluate(exchange, String)
	}
	
	builder << 'Camel ID: '     + evaluateSimple('${camelId}')      + '\r\n'
	builder << 'Exchange ID: '  + evaluateSimple('${exchangeId}')   + '\r\n'
	builder << evaluateSimple('${messageHistory}') + '\r\n'
	message.setBody(builder.toString())	
	return message
}
