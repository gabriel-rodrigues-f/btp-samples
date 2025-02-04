#   Aspects and Types

```javascript
```

##  Code List

```javascript
aspect sap.common.CodeList{
    name    : localized String(111);
    descr   : localized String(111);
}

// ou

using { CodeList } from '@sap/cds/common';

```

##  Countries

```javascript
entity sap.common.Countries : CodeList {
    key code: String(3);
}
```

- ou seja, utilizando um tipo `Country`, que possui `Association to Countries`, que é `extension` de `CodeList`, nosso atributo terá os campos `name`, `descr` (de CodeList) e `code` (de Countries).

##  Country

- `type Country : Association to sap.common.Countries;`

```javascript
using { Country } from '@sap/cds/common';

entity Addresses {
    street  : String(100);
    town    : String(100;
    country : Country;  //   name, descr e code
    )
}
```

- gera o DDL abaixo

```sql
CREATE TABLE Addresses (
  street NVARCHAR(100),
  town NVARCHAR(100),
  country_code NVARCHAR(3) -- foreign key
);
```

##  Currency

- `type Currency : Association to sap.common.Currencies;`

##  Language

- `type Language : Association to sap.common.Languages;`

##  Timezone

- `type Timezone : Association to sap.common.Timezones;`
