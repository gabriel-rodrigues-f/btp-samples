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