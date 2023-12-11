import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

Message processData(Message message) {
    def jsonPayload = '''
    {
    "name": "John",
    "age": 30,
    "city": "New York"
    }
    '''

    def input           = new JsonSlurper().parseText(jsonPayload)
    def writer          = new StringWriter()
    def indentPrinter   = new IndentPrinter(writer, ' ')
    def builder         = new MarkupBuilder(indentPrinter)

    builder.person {
        name input.name
        age input.age
        city input.city
    }

    message.setBody(writer.toString())
    return message
}
