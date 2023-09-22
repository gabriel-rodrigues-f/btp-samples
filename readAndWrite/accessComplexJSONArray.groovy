import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import java.util.LinkedHashMap

def Message processData(Message message){
    InputStream reader = message.getBody(InputStream)
    def jsonPath       = new JsonSlurper().parse(reader)   
    def builder        = new JsonBuilder()
    def jsonMetaMes    = jsonPath.METAMES
    def jsonPosicao    = jsonPath.POSICAO
    def jsonRecebiveis = jsonPosicao.RECEBIVEIS
 
    builder {
            sucesso       jsonPath.sucesso
            startAt       jsonPath.STARTAT
            endAt         jsonPath.ENDAT
            elapsedTime   jsonPath.ELAPSEDTIME
            pid           jsonPath.PID
            dados {
                dataRelatorio  jsonPath.DATARELATORIO
                diasUteis      jsonPath.DIASUTEIS
                metaMes {
                    objetivo {
                        valor jsonMetaMes.OBJETIVO.VALOR
                        perc  jsonMetaMes.OBJETIVO.PERC
                    }
                    realizado {
                        valor jsonMetaMes.REALIZADO.VALOR
                        perc  jsonMetaMes.REALIZADO.PERC
                    }
                    falta {
                        valor jsonMetaMes.FALTA.VALOR
                        perc  jsonMetaMes.FALTA.PERC
                    }
                    faltaDia {
                        valor jsonMetaMes.FALTADIA.VALOR
                        perc  jsonMetaMes.FALTADIA.PERC
                    }
                }
                posicao {
                    totalFaturado  jsonPosicao.TOTALFATURADO
                    nTotalFaturado jsonPosicao.NTOTALFATURADO
                    recebiveis jsonRecebiveis.collect { recebivel ->
                        [
                            centro        :recebivel.CENTRO,
                            patioExterno  :[
                                qtd   :recebivel.PATIOEXTERNO.QTD,
                                valor :recebivel.PATIOEXTERNO.VALOR
                            ],
                            patioInterno  :[
                                qtd   :recebivel.PATIOINTERNO.QTD,
                                valor :recebivel.PATIOINTERNO.VALOR,
                            ],
                            faturado :[
                                qtd   :recebivel.FATURADO.QTD,
                                valor :recebivel.FATURADO.VALOR,
                            ],
                            potencial :[
                                qtd   :recebivel.POTENCIAL.QTD,
                                valor :recebivel.POTENCIAL.VALOR,
                            ]
                        ]
                    }
                }
            }
        }
        
    def jsonString  = builder.toString()
    jsonString      = unescapeUnicode(JsonOutput.prettyPrint(jsonString))
    message.setBody(jsonString)
    return message
}

def unescapeUnicode(def inp) {
    (inp =~ /\\u([0-9a-f]{2})([0-9a-f]{2})/).each { m ->
        def uniAsString = new String([
                                Integer.parseInt(m[1], 16),
                                Integer.parseInt(m[2], 16)
                            ] as byte[], StandardCharsets.UTF_16)
        inp = inp.replace(m[0], uniAsString)
}
    return inp
}
