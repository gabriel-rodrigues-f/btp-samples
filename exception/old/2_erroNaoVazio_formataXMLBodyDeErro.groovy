import com.sap.gateway.ip.core.customdev.util.Message

//  formatErrorPayload
//  Transforma o payload de erro em XML
def Message processData(Message message) {
    StringBuffer reqXML = new StringBuffer()
    def payload         = message.getBody(String)
    def tokens			= payload.split('(?=<)|(?<=>)') // retorna tudo que estiver depois de < ou antes de >
    def flag			= false
    def start			= false
    String pRetorno		= ''
    def pCustomError

    for (int i = 0; i < tokens.length; i++) {
        if ( tokens[i] == '<errordetails>' ) {
            reqXML.append(tokens[i])
            flag  = false
            start = true
        }
        if ( start == true && flag == true) {
            reqXML.append(tokens[i])
        }
        else if ( tokens[i] == '<errordetail>' ) {
            reqXML.append(tokens[i])
            flag = false
        }
        else if ( tokens[i] == '<message>' ) {
            flag = true
        }
        else if ( tokens[i] == '</errordetails>' || tokens[i] == '</errordetail>' || tokens[i] == '</message>' ) {
            if ( start == true ) {
                reqXML.append(tokens[i])
                flag = false
            }
        }
    }

    pRetorno = reqXML.toString()

    if (pRetorno) {
        message.setProperty('pRetorno', pRetorno)
    }
    else if (pRetorno) {
        if (pCustomError) {
            def map = message.getProperties()
            def errordetails = ''
            def ex = map.get('CamelExceptionCaught')
            if (ex) {
                errordetails = ex.toString()
                message.setProperty('pRetorno', errordetails)
            }
          else {
                message.setProperty('pRetorno', '')
          }
        }else {
            message.setProperty('pRetorno', pCustomError)
        }
    }

    return message
}
