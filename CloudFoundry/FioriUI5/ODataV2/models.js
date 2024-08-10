
sap.ui.define([
  "sap/ui/model/json/JSONModel",
  "sap/ui/Device",
  "com/lab2dev/poc/adapters/httpClient",
],
  function (JSONModel, Device, httpClient) {
      "use strict"

      return {
          /**
           * Provides runtime info for the device the UI5 app is running on as JSONModel
           */
          createDeviceModel: function () {
              var oModel = new JSONModel(Device)
              oModel.setDefaultBindingMode("OneWay")
              return oModel
          },

          read: async function ({ sPath }) {
              return await httpClient.adaptRequest({
                  sMethod: 'READ',
                  sPath
              })
          },

          remove: async function ({ sPath }) {
              return await httpClient.adaptRequest({
                  sMethod: 'REMOVE',
                  sPath
              })
          },

          create: async function ({ sPath, oBody }) {
              return await httpClient.adaptRequest({
                  sMethod: 'CREATE',
                  sPath,
                  oBody
              })
          },

          update: async function ({ sPath, oBody }) {
              return await httpClient.adaptRequest({
                  sMethod: 'UPDATE',
                  sPath,
                  oBody
              })
          }
      }
  })