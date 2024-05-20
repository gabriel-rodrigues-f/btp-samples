type Country : Association to sap.common.Countries;

entity Addresses {
  street  : String;
  town    : String;
  country : Country; //> using reuse type
}


/* 
  The code lists define a key element code, which results in a foreign key column country_code in your SQL table for Addresses.
  For example:

CREATE TABLE Addresses (
  street NVARCHAR(5000),
  town NVARCHAR(5000),
  country_code NVARCHAR(3) -- foreign key
);
 */