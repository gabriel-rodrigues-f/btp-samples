sap.ui.define([], function () {

  "use strict"

  return {
    _makeResponseStatus: () => ({
      CREATED: 201,
      OK: 200,
      NO_CONTENT: 204,
      SERVER_ERROR: 500
    }),

    adaptRequest: async function ({
      oBody,
      sPath,
      sMethod,
      oHeaders = {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }) {
      const { CREATED, OK, NO_CONTENT, SERVER_ERROR } = this._makeResponseStatus()
      try {
        let responseBody
        const response = await fetch(sPath, {
          method: sMethod,
          headers: oHeaders,
          body: oBody ? JSON.stringify(oBody) : null
        })

        responseBody = await response.text()
        responseBody = responseBody ? JSON.parse(responseBody) : {}

        const httpResponse = { status: response.status, body: responseBody, }

        if (response.status === OK) httpResponse.ok = true
        if (response.status === NO_CONTENT) httpResponse.noContent = true
        if (response.status === CREATED) httpResponse.created = true
        return httpResponse
      } catch (error) {
        return {
          status: SERVER_ERROR,
          error: error.message || "Erro inesperado"
        }
      }
    }
  }
})