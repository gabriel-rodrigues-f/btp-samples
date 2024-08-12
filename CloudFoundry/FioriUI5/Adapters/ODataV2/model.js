read: async function ({ sPath }) {
  return await httpClient.adaptRequest({
    sMethod: 'read',
    sPath
  })
},

remove: async function ({ sPath }) {
  return await httpClient.adaptRequest({
    sMethod: 'remove',
    sPath
  })
},

create: async function ({ sPath, oBody }) {
  return await httpClient.adaptRequest({
    sMethod: 'created',
    sPath,
    oBody
  })
},

update: async function ({ sPath, oBody }) {
  return await httpClient.adaptRequest({
    sMethod: 'update',
    sPath,
    oBody
  })
}