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
                  ...oParameters,
                  success: function (oData, response) {
                      const httpResponse = {
                          status: response.statusCode,
                          body: oData
                      }

                      if (response.statusCode === OK) httpResponse.ok = true
                      if (response.statusCode === NO_CONTENT) httpResponse.noContent = true
                      if (response.statusCode === CREATED) httpResponse.created = true
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