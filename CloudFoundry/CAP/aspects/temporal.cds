/*  temporal
  Adiciona dois campos:
    validFrom
    validTo

    Este aspecto basicamente adiciona dois elementos canônicos, validFrom e validTo a uma entidade.
    Ele também adiciona uma anotação de tag que conecta o suporte integrado do compilador CDS e do tempo de execução para dados temporais.
    Esse suporte integrado cobre o tratamento de registros e intervalos de tempo com data efetiva, incluindo viagens no tempo. Tudo que você precisa fazer é adicionar o aspecto temporal às respectivas entidades como segue.
*/