sap.ui.define([], function () {

  "use strict"

  return {
      _makeResponseStatus: () => ({
          CREATED: 201,
          OK: 200,
          NO_CONTENT: 204,
          SERVER_ERROR: 500
      }),

      adaptRequest: function (oDataModel, sPath, sMethod, oParameters) {
          const { CREATED, OK, NO_CONTENT, SERVER_ERROR } = this._makeResponseStatus()
          
          return new Promise((resolve, reject) => {
              oDataModel[sMethod.toLowerCase()](sPath, {
                  parameters: oParameters,
                  success: function (oData, response) {
                      const httpResponse = {
                          status: response.status,
                          body: oData
                      }

                      if (response.status === OK) httpResponse.ok = true
                      if (response.status === NO_CONTENT) httpResponse.noContent = true
                      if (response.status === CREATED) httpResponse.created = true

                      resolve(httpResponse)
                  },
                  error: function (error) {
                      reject({
                          status: SERVER_ERROR,
                          error: error.message || "Erro inesperado"
                      })
                  }
              })
          })
      }
  }
})
