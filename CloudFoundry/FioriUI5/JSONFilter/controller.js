sap.ui.define([
    "sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
    ], function(Filter, FilterOperator) {
        return {
            // Crie métodos handleSearch com base nos Inputs que deseje utilizar o filtro 
            handleSearchField: function(oEvent){
                const sQuery = oEvent.getParameter("newValue")
            
                // Somente passe a Query que deseja filtrar
                this.onSearch(sQuery)
            },
            onSearch: function(sQuery){
                const aFilters = []
                const elementsForFilter = ["coluna_1", "valor"] // key das colunas que quero filtrar
            
                if(sQuery){
                    const oFilter = new Filter({ 
                        filters: this._generateFilter(elementsForFilter, sQuery)
                    })
            
                    aFilters.push(oFilter)
                }
            
                const oTable = this.byId("idTable") // ID da tabela
                const oBinding = oTable.getBinding("rows") // Mude conforme o Tipo de tabela
                oBinding.filter(aFilters, "tableFilter") // Faça o nome do filtro como desejar
            },
            _generateFilter: function(aFiltersElements, sQuery){
                const oTablePropertiesData = this.getModel("tableProperties").getData()
            
                return aFiltersElements.map((sKey) => {
                    // Esse switch abaixo pode ser utilizado para criar filtros personalizados ou manter o padrão Contains.
                    switch(sKey){
                        case 'coluna-1': {
                            const oMonthItemsEntries = Object.entries(oTablePropertiesData.coluna_1.items)
                            
                            return new Filter(sKey, (oVal) => {
                                return oMonthItemsEntries.find(([sKey, sValue]) => sKey === oVal && sValue.includes(sQuery))
                            })
                        }
                        default: {
                            return new Filter(sKey, FilterOperator.Contains, sQuery)
                        }
                    }
                })
            }
        }
    }
)