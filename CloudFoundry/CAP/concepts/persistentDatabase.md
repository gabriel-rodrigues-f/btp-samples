#   Persistent Database

1. Adicionar a configuração abaixo no package.json, em cds/requires/ para que os dados do banco de desenvolvimento sejam persistêntes.


```json
"db": {
        "[development]": {
          "kind": "sqlite",
          "credentials": {
            "url": "db.sqlite"
          }
        }
      }
```

2. Executar o comando `cds deploy`:
   1. Cria um arquivo na raíz do projeto contendo os dados do banco de desenvolvimento;
   2. Cria as tabelas de acordo com as definições da CDS;
   3. Popular o banco com os dados do CSV presente em ./db/data