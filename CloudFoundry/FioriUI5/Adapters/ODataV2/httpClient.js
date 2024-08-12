sap.ui.define([
  "sap/ui/model/odata/v2/ODataModel",
], function (ODataModel) {

  "use strict"

  return {
    _makeResponseStatus: () => ({
      CREATED: 201,
      OK: 200,
      NO_CONTENT: 204,
      SERVER_ERROR: 500
    }),

    _getODataModel: async () => {
      const oDataModel = new ODataModel("/v2/fiori/")
      return new Promise(function (resolve, reject) {
        oDataModel.attachMetadataLoaded(_ => resolve(oDataModel))
        oDataModel.attachMetadataFailed(_ => reject(new Error("It was not possible to read the metadata")))
      })
    },

    _makeSuccessResponse: function (oData, oResponse) {
      const { CREATED, OK, NO_CONTENT } = this._makeResponseStatus()
      const httpResponse = { status: oResponse.statusCode, body: oData }
      if (oResponse.statusCode == OK) httpResponse.ok = true
      if (oResponse.statusCode == NO_CONTENT) httpResponse.noContent = true
      if (oResponse.statusCode == CREATED) httpResponse.created = true
      return httpResponse
    },

    _makeErrorResponse: function (oError) {
      const { SERVER_ERROR } = this._makeResponseStatus()
      return {
        status: SERVER_ERROR,
        error: oError.message || "Unexpected error"
      }
    },

    adaptRequest: async function ({ sPath, sMethod, oBody }) {
      const oParams = oBody ? [sPath, oBody] : [sPath]
      try {
        const oConnection = await this._getODataModel()
        return new Promise((resolve, reject) => {
          oConnection[sMethod](...oParams, {
            success: (oData, oResponse) => resolve(this._makeSuccessResponse(oData, oResponse)),
            error: oError => reject(this._makeErrorResponse(oError))
          })
        })
      } catch (oError) { return this._makeErrorResponse(oError) }
    }
  }
})
