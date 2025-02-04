#   Associations

##  Association To one

- O código abaixo permite navegar até a entidade `Authors` através da entidade `Books` em 1 x 1.

```javascript
namespace com.sap.upskilling;

entity Books {
    key ID  : UUID;
    author  : association to Authors;
    genre   : Genre;
};

type Genre : Integer enum {
    fiction = 1;
    non_fiction = 2;
}

entity Authors {
    key ID  : UUID;
    name    : String(50);
    email   : String(50);
};
```

##  Association To many

- O código abaixo permite navegar até a entidade `Authors` através da entidade `Books` em 1 x n.
- `$self` refere-se ao id da entidade atual;

```javascript
namespace com.sap.upskilling;

entity Books {
    key ID  : UUID;
    authors : Association to Authors;
}

entity Authors {
    key ID  : UUID;
    books   : Association to many Books
                on books.author = $self;
}
```

##  Composition To many
- Possui cascade delete;
- Deep insert - através do OData `Orders` tanto Order quanto OrderItem podem ser criados.

```javascript
namespace com.sap.upskilling;

entity Orders {
    key ID  : UUID;
    items   : Composition of many OrderItems
                on items.order = $self;
}

entity OrderItems {
    key order       : Association to Orders;
    key position    : Integer;
}
```

##  Association to many x Composition of many
- `Association to many` é utilizado para relacionar objetos que fazem parte do mesmo contexto delimitado, tal que a existência de um não depende da existência do outro;
- `Composition to many` é utilizado quando a existência do objeto que compoõe não faz sentido sem que o objeto principal exista;

##  Exemplos de queries

- Preços: `{{host}}/Books?$select=ID,Price_Currency`;

```json
{
    "@odata.context": "$metadata#Books",
    "value": [
        {
            "ID": "1",
            "Price_Currency": "BRL"
        },
        {
            "ID": "2",
            "Price_Currency": "BRL"
        }
    ]
}
```

- Livros com o nome do autor associado: `{{host}}/Books?$select=ID,Price_Currency,Author/Name&$expand=Author&$filter=Author/Name eq 'Ana Costa'`;
 
```json
{
    "@odata.context": "$metadata#Books",
    "value": [
        {
            "ID": "5",
            "Price_Currency": "BRL",
            "Author_Name": "Ana Costa",
            "Author": {
                "ID": "5",
                "Name": "Ana Costa",
                "DateOfBirth": "1992-07-10"
            }
        }
    ]
}
```
- Autores e seus livros associados: `{{host}}/Authors?$select=ID,Name&$expand=Books($select=ID,Price_Currency)`;

```json
{
    "@odata.context": "$metadata#Authors",
    "value": [
        {
            "ID": "1",
            "Name": "João Silva",
            "Books": [
                {
                    "ID": "1",
                    "Price_Currency": "BRL"
                }
            ]
        },
        {
            "ID": "2",
            "Name": "Maria Oliveira",
            "Books": [
                {
                    "ID": "2",
                    "Price_Currency": "BRL"
                }
            ]
        }
    ]
}
```
- Pedidos e seus itens associados: `{{host}}/Orders?$select=ID&$expand=Items($select=Orders)`

```json
{
    "@odata.context": "$metadata#Orders",
    "value": [
        {
            "ID": "1",
            "Items": [
                {
                    "Orders_ID": "1"
                }
            ]
        },
        {
            "ID": "2",
            "Items": [
                {
                    "Orders_ID": "2"
                }
            ]
        }
    ]
}
```

- Livros com autor específico: `{{host}}/Books?$select=ID,Price_Currency&$expand=Author($select=Name)&$filter=Author/Name eq 'Nome do Autor'`

```json
{
    "@odata.context": "$metadata#Books",
    "value": [
        {
            "ID": "5",
            "Price_Currency": "BRL",
            "Author": {
                "Name": "Ana Costa",
                "ID": "5"
            }
        }
    ]
}
```

7. Incorrect! User is a common type; cuid, managed and Countryare common aspects.
8. epoch : Association to Epochs;
9. publCountry : Association to Country;
10. Incorrect! To request the German version of the data, you can use the query string sap-locale=de or the request header Accept-Language: de.
11. Order and contained order items can be created jointly via a single OData request.