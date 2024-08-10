/* managed
  Adiciona automaticamente 4 campos na entidade:
  createdAt
  createdBy
  updatedAt
  updatedBy

  Utilizamos as anotações "@" para captirar propriedades através dos handlers.
*/

using { managed } from '@sap/cds/common';

entity AnyEntity : managed {};

entity OtherEntity {
  createdAt  : Timestamp @cds.on.insert : $now;
  createdBy  : User      @cds.on.insert : $user;
  modifiedAt : Timestamp @cds.on.insert : $now  @cds.on.update : $now;
  modifiedBy : User      @cds.on.insert : $user @cds.on.update : $user;
};