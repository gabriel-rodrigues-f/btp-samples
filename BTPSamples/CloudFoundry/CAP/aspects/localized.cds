/* localized
  Utilizado para atributos que precisam de tradução
 */

using { cuid, managed } from '@sap/cds/common';

 entity Books: cuuid, managed {
  title       : localized String; // behind de scenes => sap.common.Locale
  description : localized String; // behind de scenes => sap.common.Locale
  price       : Decimal;
  currency    : Currency
 }