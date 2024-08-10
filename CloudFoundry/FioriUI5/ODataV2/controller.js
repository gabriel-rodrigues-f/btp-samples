sap.ui.define([
  "sap/ui/core/mvc/Controller",
  "com/lab2dev/poc/model/models",
],
  function (Controller, models) {
      "use strict"

      return Controller.extend("com.lab2dev.poc.controller.Home", {

          _mockCreateRequest: () => ({
              receiver: "USCU_S02",
              payment_condition: "NT30",
              total_amount: 1000.00,
              status: "pending",
              items: [
                  {
                      items: "10",
                      material: "MZ-FG-C990",
                      quantity: 10,
                      quantity_unit: "PC",
                      amount: 1000.00
                  }
              ]
          }),

          _mockUpdateRequest: () => ({
              receiver: "USCU_S03",
              payment_condition: "NT40",
              total_amount: 1000.00,
              status: "pending",
              items: [
                  {
                      items: "10",
                      material: "MZ-FG-C990",
                      quantity: 10,
                      quantity_unit: "PC",
                      amount: 1000.00
                  }
              ]
          }),

          onInit: async function () {
              /*
              READ 
              await models.read({ sPath: "/SalesOrderDraft" })

              REMOVE
              await models.remove({ sPath: "/SalesOrderDraft(ID=047d35ad-e452-4476-94f4-0bd4bea87144)" })                

              CREATE
              await models.create({ sPath: "/SalesOrderDraft", oBody: {...} })

              UPDATE
              await models.update({ sPath: "ID=047d35ad-e452-4476-94f4-0bd4bea87144)", oBody: {...} })
              */
          }
      })
  })
