#   Controle de concorrência

##  Lock Otimista

- O código abaixo evita a ocorrência de locks, gerenciando a versão do registro no banco com o campo `etag`;
- enviamos o header `If-Match` no enviando a versão do etag para o servidor.

```javascript
entity Books : cuid, managed {
    ...
}

annotate Books with {
    modifiedAt @odata.etag;
}
```

- Dessa maneira conseguiremos utilizar o header ETag para fazer o controle de `Lock Otimista`.

```md
### Consultando o registro para obter o ETag
# @name BOOK_BY_ID
GET {{BASE_URL}}/books/Books/689287f2-44cc-475b-9703-87d3a84bf79d

@IF_MATCH = {{BOOK_BY_ID.response.headers.ETag}}


### Tentando atualizar o registro, enviando o ETag obtido na requisição anterior
PATCH {{BASE_URL}}/books/Books(689287f2-44cc-475b-9703-87d3a84bf79d)
Content-Type: application/json
If-Match: {{IF_MATCH}}

{
  "title": "Romeo and Juliet II"
}
```

- Caso essa entidade já tenha sido alterada, o valor do `ETag` terá sido atualizado e vamos receber um erro 412 na requisição.