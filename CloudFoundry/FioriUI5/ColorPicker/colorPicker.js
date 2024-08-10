sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast",
    "sap/ui/unified/ColorPickerPopover"
],
    function (Controller, JSONModel, MessageToast) {
        "use strict";

        const sectorModel = {
            sectors: [
                {
                    Sector: "Setor Roxo claro",
                    Floor: "1°",
                    NumberOfRooms: "2",
                    Color: 'rgb(108,30,232)'
                },
                {
                    Sector: "Setor Roxo",
                    Floor: "Térreo",
                    NumberOfRooms: "5",
                    Color: 'rgb(147,44,209)'
                },
                {
                    Sector: "Setor Escuro",
                    Floor: "5°",
                    NumberOfRooms: "1",
                    Color: 'rgb(82,20,120)'
                },
                {
                    Sector: "Setor Amarelo",
                    Floor: "8°",
                    NumberOfRooms: "3",
                    Color: 'yellow'
                },
                {
                    Sector: "Setor Rosa",
                    Floor: "2°",
                    NumberOfRooms: "2",
                    Color: 'pink'
                }
            ]
        };

        return Controller.extend("com.lab.project1.controller.Home", {
            onInit: function () {

                const oModel = new JSONModel(sectorModel);
                this.getView().setModel(oModel);

                const storedSectors = localStorage.getItem("sectorsData");
                if (storedSectors) {
                    const sectorsList = JSON.parse(storedSectors);
                    this.getView().getModel().setProperty("/sectors", sectorsList);
                }

                this.generatorGridList();
            },

            generatorGridList: function () {
                $.sap.require('sap.ui.layout.cssgrid.GridBasicLayout');

                const oView = this.getView()

                const gridList = new sap.f.GridList({
                    id: `${oView.getId() + "--exportSectors"}`,
                    customLayout: new sap.ui.layout.cssgrid.GridBasicLayout({
                        gridTemplateColumns: "repeat(auto-fill, minmax(16rem, 1fr))",
                        gridGap: "1.0rem"
                    }),
                    items: {
                        path: "/sectors",
                        template: new sap.f.GridListItem({
                            content: [
                                new sap.m.VBox({
                                    items: [
                                        new sap.ui.core.HTML({
                                            content: "<div style='background:{Color};height:70px;'/>"
                                        }),
                                        new sap.m.VBox({
                                            items: [
                                                new sap.m.Title({
                                                    text: "{Sector}",
                                                    wrapping: true
                                                }).addStyleClass("sapUiSmallMarginBegin sapUiTinyMarginTop sapUiTinyMarginBottom"),
                                                new sap.m.VBox({
                                                    items: [
                                                        new sap.m.HBox({
                                                            items: [
                                                                new sap.ui.core.Icon({
                                                                    src: "sap-icon://customer-and-supplier",
                                                                }).addStyleClass("sapUiTinyMarginEnd"),
                                                                new sap.m.Label({
                                                                    text: "Andar: {Floor}",
                                                                    wrapping: true
                                                                })
                                                            ],
                                                        }).addStyleClass("sapUiSmallMarginBegin")
                                                    ],
                                                }),
                                                new sap.m.VBox({
                                                    items: [
                                                        new sap.m.HBox({
                                                            items: [
                                                                new sap.ui.core.Icon({
                                                                    src: "sap-icon://meeting-room",
                                                                }).addStyleClass("sapUiTinyMarginEnd"),
                                                                new sap.m.Label({
                                                                    text: "Quantidade de salas: {NumberOfRooms}",
                                                                    wrapping: true
                                                                })
                                                            ],
                                                        }).addStyleClass("sapUiSmallMarginBegin sapUiTinyMarginBottom")
                                                    ],
                                                })
                                            ]
                                        })
                                    ]
                                })
                            ]
                        })
                    }
                });
                var oPanel = this.getView().byId("panelSectors");
                oPanel.removeAllContent();
                oPanel.addContent(gridList);
            }
        });
    });
