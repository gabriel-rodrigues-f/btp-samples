/*  cuid
  Atalho para entidade que possui uma chave do tipo Universal Unique ID.
  É possível tipar uma entidade diretamente como cuid, ou criá-la com um campo do tipo UUID
  https://cap.cloud.sap/docs/guides/domain-modeling#prefer-canonic-keys
 */

using {cuid} from '@sap/cds/common';

entity AnyEntity : cuid {};

entity OtherEntity {
  key ID : UUID;
}
