import com.sap.gateway.ip.core.customdev.util.Message
import org.apache.camel.Exchange
import org.apache.camel.builder.SimpleBuilder

def Message processData(Message message) {
    Exchange exchange   = message.exchange

    def evaluateSimple  = { simpleExpression ->
        SimpleBuilder.simple(simpleExpression).evaluate(exchange, String)
    }

    message.setProperty('integrationFlowId', evaluateSimple('${camelId}'))
    return message
}
