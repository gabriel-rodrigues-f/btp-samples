#   Services

- Utiliza-se annotations para validar campos através das services do CAP;

##  Validating User Input


- `@mandatory`: campo obrigatório;

```javascript
entity Books : cuid, managed {
    title : localized String(255) @mandatory;
    stock : Integer default 0;
}
```

- `@assert.target`: utilizado para garantir que há correspondência de relacionamento no banco

```javascript
entity Books : cuid, managed {
    author : Association to Authors @mandatory @assert.target;
}
```

- `@assert.range`: garante que o valor preenchido sempre estará dentro do range de como a entidade foi construída. Utilizar `@assert.range` em `enums` nos garante que apenas valores do enum serão aceitos.

```javascript
entity Books : cuid, managed {
    gente : Gente @assert.range true;
}

type Gente : Integer enum {
    fiction = 1;
    non_fiction = 2;
}
```