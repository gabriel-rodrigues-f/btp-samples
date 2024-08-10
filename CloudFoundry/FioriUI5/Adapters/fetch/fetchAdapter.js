sap.ui.define([
  "sap/ui/model/odata/v2/ODataModel"
], function (ODataModel) {

  "use strict"

  return {
    _makeResponseStatus: () => ({
      CREATED: 201,
      OK: 200,
      NO_CONTENT: 204,
      SERVER_ERROR: 500
    }),

    _getODataModel: async function () {
      const oDataModel = new ODataModel("/northwind/northwind.svc/")
      return new Promise(function (resolve, reject) {
        oDataModel.attachMetadataLoaded(() => resolve(oDataModel))
        oDataModel.attachMetadataFailed(() => reject("Serviço indisponível no momento."))
      })
    },

    _makeSuccessResponse: function (oData, oResponse) {
      const { CREATED, OK, NO_CONTENT } = this._makeResponseStatus()
      const httpResponse = { status: oResponse.statusCode, body: oData }
      if (oResponse.statusCode === OK) httpResponse.ok = true
      if (oResponse.statusCode === NO_CONTENT) httpResponse.ok = true
      if (oResponse.statusCode === CREATED) httpResponse.noContent = true
      return httpResponse
    },

    _makeErrorResponse: function (oError) {
      const { SERVER_ERROR } = this._makeResponseStatus()
      return { status: SERVER_ERROR, error: oError.message || "Erro inesperado" }
    },

    _makeResponse: function (resolve, reject) {
      return {
        success: (oData, oResponse) => resolve(this._makeSuccessResponse(oData, oResponse)),
        error: oError => reject(this._makeErrorResponse(oError))
      }
    },

    adaptRequest: async function ({ sPath, sMethod, oBody }) {
      const oODataclient = await this._getODataModel()
      const sFormattedMethod = sMethod.toLowerCase()
      const oParams = oBody ? { sPath, oBody } : sPath
      try {
        return new Promise((resolve, reject) => oODataclient[sFormattedMethod](...oParams, this._makeResponse((resolve, reject))))
      } catch (oError) { this._makeErrorResponse(oError) }
    }
  }
})
