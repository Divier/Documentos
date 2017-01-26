//Variables de propiedades
//Propiedades de la pagina
Documentos.propertiesPage =  { 
		id: "page",
		parentId: "workArea",
		properties	 :[
		{
			title : Documentos.labelPlantilla.margenes,
			description : Documentos.labelPlantilla.conf_margenes,
			fields : [ {
				label : Documentos.labelPlantilla.superior,
				id : "margenSuperior",
				maxlength : "4",
				readonly : "true",
				value : "3.0",
				isText : true,
				isTextarea : false,
				isSelect : false,
				classname: "number"
			}, {
				label : Documentos.labelPlantilla.inferior,
				id : "margenInferior",
				maxlength : "4",
				readonly : "true",
				value : "3.0",
				isText : true,
				isTextarea : false,
				isSelect : false,
				classname: "number"
			}, {
				label : Documentos.labelPlantilla.izquierda,
				id : "margenIzquierda",
				maxlength : "4",
				readonly : "true",
				value : "3.0",
				isText : true,
				isTextarea : false,
				isSelect : false,
				classname: "number"
			}, {
				label : Documentos.labelPlantilla.derecha,
				id : "margenDerecha",
				maxlength : "4",
				readonly : "true",
				value : "3.0",
				isText : true,
				isTextarea : false,
				isSelect : false,
				classname: "number"
			} ]
		},
		{
			title : Documentos.labelPlantilla.tamanio_pagina,
			description : Documentos.labelPlantilla.conf_tamanio_pagina,
			fields : [
					{
						label : Documentos.labelPlantilla.formato,
						id : "formato",
						isText : false,
						isTextarea : false,
						isSelect : true,
						options : [ {
							label : Documentos.labelPlantilla.personalizado,
							value : Documentos.PERSONALIZADO,
							selected : false
						}, {
							label : Documentos.labelPlantilla.carta,
							value : Documentos.CARTA,
							selected : true
						}, {
							label : Documentos.labelPlantilla.legal,
							value : Documentos.LEGAL,
							selected : false
						}, {
							label : Documentos.labelPlantilla.a4,
							value : Documentos.A4,
							selected : true
						}, {
							label : Documentos.labelPlantilla.ejecutivo,
							value : Documentos.EJECUTIVO,
							selected : false
						} ],
						value: Documentos.CARTA
					},
					{
						label : Documentos.labelPlantilla.ancho,
						id : "ancho",
						maxlength : "5",
						readonly : "true",
						value :  Documentos.ANCHO_PAGINA_CARTA,
						isText : true,
						isTextarea : false,
						isSelect : false,
						classname: "number"
					},
					{
						label : Documentos.labelPlantilla.alto,
						id : "alto",
						maxlength : "5",
						readonly : "true",
						value : Documentos.ALTO_PAGINA_CARTA,
						isText : true,
						isTextarea : false,
						isSelect : false,
						classname: "number"
					},
					{
						label : null,
						id : "altoTotal",
						value : Documentos.ALTO_PAGINA_CARTA,
						isText : false,
						isTextarea : false,
						isSelect : false
					}  ]
		},
		{
			title : Documentos.labelPlantilla.tamanio_secciones,
			description : Documentos.labelPlantilla.conf_tamanio_secciones,
			fields : [
					{
						label : Documentos.labelPlantilla.titulo,
						id : "tituloInput",
						maxlength : "5",
						readonly : "true",
						value : 3,
						isText : false,
						isTextarea : false,
						isSelect : false,
						classname: "number"
					},
					{
						label : Documentos.labelPlantilla.encabezado,
						id : "encabezadoInput",
						maxlength : "5",
						readonly : "true",
						value : 3,
						isText : true,
						isTextarea : false,
						isSelect : false,
						classname: "number"
					},
					{
						label : Documentos.labelPlantilla.pie_pagina,
						id : "piePaginaInput",
						maxlength : "5",
						readonly : "false",
						value : 3,
						isText : true,
						isTextarea : false,
						isSelect : false,
						classname: "number"
					},
					{
						label : Documentos.labelPlantilla.pie_ultima_pagina,
						id : "ultimoPiePaginaInput",
						maxlength : "5",
						readonly : "false",
						value : 3,
						isText : false,
						isTextarea : false,
						isSelect : false,
						classname: "number"
					},
					{
						label : Documentos.labelPlantilla.cuerpo,
						id : "cuerpoInput",
						maxlength : "5",
						readonly : "false",
						value : 15.94,
						isText : true,
						isTextarea : false,
						isSelect : false,
						classname: "number"
					} ]
		} ] };

//Propiedades de item
Documentos.propertiesItem =  {  
		id: "item",
		properties	 :[
               		{
               			title : Documentos.labelPlantilla.general,
               			description : Documentos.labelPlantilla.conf_general,
               			fields : [ {
               				label : Documentos.labelPlantilla.editable,
               				id : "editable",
               				value : true,
               				isText : false,
               				isTextarea : false,
               				isSelect : true,
               				classname: "",
               				value: true,
               				disabled: "disabled",
       						options : [ {
       							label : Documentos.labelPlantilla.si,
       							value : true,
    							selected : true
       						}, {
       							label : Documentos.labelPlantilla.no,
       							value : false,
    							selected : false
       						} ]
               			}, {
               				label : Documentos.labelPlantilla.ancho,
               				id : "ancho_item",
               				maxlength : "4",
               				readonly : "true",
               				value : 3.0,
               				isText : true,
               				isTextarea : false,
               				isSelect : false,
               				disabled: "disabled",
               				classname: "number"
               			}, {
               				label : Documentos.labelPlantilla.alto,
               				id : "alto_item",
               				maxlength : "4",
               				readonly : "true",
               				value : 1.0,
               				isText : true,
               				isTextarea : false,
               				isSelect : false,
               				disabled: "disabled",
               				classname: "number"
               			} ]
               		} ] };

//Propiedades de variable
Documentos.propertiesVariable =  {  
		id: "variable",
		properties	 :[
             		{
             			title : Documentos.labelPlantilla.general,
             			description : Documentos.labelPlantilla.conf_general,
             			fields : [ {
             				label : Documentos.labelPlantilla.tipoVariable,
             				id : "nombreTipoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			},{
             				label : Documentos.labelPlantilla.jerarquiaVariable,
             				id : "nombreContextoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			},{
             				label : Documentos.labelPlantilla.formatoVariable,
             				id : "formatoVariable",
             				maxlength : "250",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			}, {
             				label : Documentos.labelPlantilla.valorDefecto,
             				id : "valorDefecto",
             				maxlength : "255",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			}, {
             				label : Documentos.labelPlantilla.proceso,
             				id : "proceso",
             				maxlength : "100",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			} ]
             		} ] };

//Propiedades de imagen
Documentos.propertiesImage =  {  
		id: "image",
		properties	 :[
             		{
             			title : Documentos.labelPlantilla.general,
             			description : Documentos.labelPlantilla.conf_general,
             			fields : [ {
             				label : Documentos.labelPlantilla.tipoVariable,
             				id : "nombreTipoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			},{
             				label : Documentos.labelPlantilla.jerarquiaVariable,
             				id : "nombreContextoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			}, {
             				label : Documentos.labelPlantilla.ancho,
             				id : "anchoImagen",
             				maxlength : "50",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "number_integer"
             			}, {
             				label : Documentos.labelPlantilla.alto,
             				id : "altoImagen",
             				maxlength : "50",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "number_integer"
             			}, {
             				label : Documentos.labelPlantilla.proceso,
             				id : "proceso",
             				maxlength : "100",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			} ]
             		} ] };

//Propiedades de numero
Documentos.propertiesNumber =  {  
		id: "number",
		properties	 :[
             		{
             			title : Documentos.labelPlantilla.general,
             			description : Documentos.labelPlantilla.conf_general,
             			fields : [ {
             				label : Documentos.labelPlantilla.tipoVariable,
             				id : "nombreTipoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			},{
             				label : Documentos.labelPlantilla.jerarquiaVariable,
             				id : "nombreContextoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			}, {
             				label : Documentos.labelPlantilla.decimales,
             				id : "decimales",
             				maxlength : "2",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "number_integer"
             			}, {
             				label : Documentos.labelPlantilla.valorDefecto,
             				id : "valorDefecto",
             				maxlength : "24",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			}, {
             				label : Documentos.labelPlantilla.presentacionVariable,
             				id : "presentacionVariable",
    						isText : false,
    						isTextarea : false,
    						isSelect : true,
    						options : [ {
    							label : Documentos.labelPlantilla.presentacionVariableNumero,
    							value : Documentos.labelPlantilla.presentacionVariableNumero,
    							selected : true
    						}, {
    							label : Documentos.labelPlantilla.presentacionVariableLetras,
    							value : Documentos.labelPlantilla.presentacionVariableLetras,
    							selected : false
    						}, {
    							label : Documentos.labelPlantilla.valorMoneda,
    							value : Documentos.labelPlantilla.valorMoneda,
    							selected : false
    						} ]
             			},{
             				label: Documentos.labelPlantilla.tipoLetra,
             				id: "formatoPresentacionVariable",
             				isText : false,
             				isTextarea : false,
    						isSelect : true,
    						options : [ {
    							label : Documentos.labelPlantilla.mayuscula,
    							value : Documentos.labelPlantilla.mayuscula,
    							selected : true,
    						},{
    							label : Documentos.labelPlantilla.minuscula,
    							value : Documentos.labelPlantilla.minuscula,
    							selected : true,
    						} ],
             				classname: "formatoPresentacionVariable"
             			}, {
             				label : Documentos.labelPlantilla.separadorMiles,
             				id : "separadorMiles",
    						isText : false,
    						isTextarea : false,
    						isSelect : true,
    						options : [ {
       							label : Documentos.labelPlantilla.no,
       							value : Documentos.labelPlantilla.no,
    							selected : true
       						}, {
       							label : Documentos.labelPlantilla.si,
       							value : Documentos.labelPlantilla.si,
    							selected : false
       						} ],
             				classname: "separadorMiles"
             			}, {
             				label : Documentos.labelPlantilla.formatoNumerosNegativos.label,
             				id : "formatoNumerosNegativos",
    						isText : false,
    						isTextarea : false,
    						isSelect : true,
    						options : [ {
       							label : Documentos.labelPlantilla.formatoNumerosNegativos.signoNegativo,
       							value : Documentos.SIGNO_NEGATIVO,
    							selected : true,
                 				classname: "colorNormal"
       						}, {
       							label : Documentos.labelPlantilla.formatoNumerosNegativos.colorRojo,
       							value : Documentos.COLOR_ROJO,
    							selected : false,
                 				classname: "colorRojo"
       						}, {
       							label : Documentos.labelPlantilla.formatoNumerosNegativos.parentesis,
       							value : Documentos.PARENTESIS,
    							selected : false,
                 				classname: "colorNormal"
       						}, {
       							label : Documentos.labelPlantilla.formatoNumerosNegativos.parentesisColorRojo,
       							value : Documentos.COLOR_ROJO_PARENTESIS,
    							selected : false,
                 				classname: "colorRojo"
       						} ],
             				classname: "formatoNumerosNegativos"
             			}, {
             				label : Documentos.labelPlantilla.proceso,
             				id : "proceso",
             				maxlength : "100",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			}  ]
             		} ] };

//Propiedades de numero
Documentos.propertiesText =  {  
		id: "text",
		properties	 :[
           		{
           			title : Documentos.labelPlantilla.general,
           			description : Documentos.labelPlantilla.conf_general,
           			fields : [ {
         				label : Documentos.labelPlantilla.tipoVariable,
         				id : "nombreTipoVariable",
         				maxlength : "250",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			},{
         				label : Documentos.labelPlantilla.jerarquiaVariable,
         				id : "nombreContextoVariable",
         				maxlength : "250",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			}, {
           				label : Documentos.labelPlantilla.longitud,
           				id : "longitud",
           				maxlength : "10",
  						readonly : "false",
           				value : "",
           				isText : true,
           				isTextarea : false,
           				isSelect : false,
           				classname: "number_integer"
           			}, {
           				label : Documentos.labelPlantilla.valorDefecto,
           				id : "valorDefecto",
           				maxlength : "255",
  						readonly : "false",
           				value : "",
           				isText : false,
           				isTextarea : true,
           				isSelect : false,
           				classname: "text"
           			}, {
         				label : Documentos.labelPlantilla.proceso,
         				id : "proceso",
         				maxlength : "100",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			}  ]
           		} ] };

//Propiedades de grupo
Documentos.propertiesGroup =  {  
		id: "group",
		properties	 :[
           		{
           			title : Documentos.labelPlantilla.general,
           			description : Documentos.labelPlantilla.conf_general,
           			fields : [ {
        						label : Documentos.labelPlantilla.editable,
        						id : "editable",
        						isText : false,
        						isTextarea : false,
        						isSelect : true,
        						options : [ {
        							label : Documentos.labelPlantilla.si,
        							value : Documentos.labelPlantilla.si,
        							selected : true
        						}, {
        							label : Documentos.labelPlantilla.no,
        							value : Documentos.labelPlantilla.no,
        							selected : false
        						} ]
        					} ]
           		} ] };

//Propiedades de imagen variable
Documentos.propertiesVariableImage =  {  
		id: "image",
		properties	 :[ 
             		{
             			title : Documentos.labelPlantilla.general,
             			description : Documentos.labelPlantilla.conf_general,
             			fields : [ {
             				label : Documentos.labelPlantilla.tipoVariable,
             				id : "nombreTipoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			},{
             				label : Documentos.labelPlantilla.jerarquiaVariable,
             				id : "nombreContextoVariable",
             				maxlength : "250",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			}, {
    						label : Documentos.labelPlantilla.longitud,
    		   				id : "longitud",
    		   				maxlength : "10",
    							readonly : "false",
    		   				value : "",
    		   				isText : true,
    		   				isTextarea : false,
    		   				isSelect : false,
    		   				classname: "number_integer"
    		   			}, {
               				label : Documentos.labelPlantilla.valorDefecto,
               				id : "valorDefecto",
               				maxlength : "255",
      						readonly : "false",
               				value : "",
               				isText : false,
               				isTextarea : true,
               				isSelect : false,
               				classname: "text"
               			} , {
             				label : Documentos.labelPlantilla.ancho,
             				id : "anchoImagen",
             				maxlength : "50",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "number_integer"
             			}, {
             				label : Documentos.labelPlantilla.alto,
             				id : "altoImagen",
             				maxlength : "50",
    						readonly : "false",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "number_integer"
             			}, {
             				label : Documentos.labelPlantilla.proceso,
             				id : "proceso",
             				maxlength : "100",
    						readonly : "true",
             				value : "",
             				isText : true,
             				isTextarea : false,
             				isSelect : false,
             				classname: "text"
             			},{
             				label : Documentos.labelPlantilla.tipoImagen,
             				id : "tipoImagen",
             				isText : false,
    						isTextarea : false,
    						isSelect : true,
    						options : [  {
       							label : Documentos.labelPlantilla.general,
       							value : Documentos.labelPlantilla.general,
    							selected : true,
       						},
    						{
       							label : Documentos.labelPlantilla.firma,
       							value : Documentos.labelPlantilla.firma,
    							selected : false,
       						}]
             			}]
             		} ] };
//Propiedades de firma
Documentos.propertiesSign =  {  
		id: "firma",
		properties	 :[
         		{
         			title : Documentos.labelPlantilla.general,
         			description : Documentos.labelPlantilla.conf_general,
         			fields : [ {
         				label : Documentos.labelPlantilla.tipoFirmaPlantilla,
         				id : "tipoFirmaPlantilla",
         				maxlength : "50",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			},{
         				label : Documentos.labelPlantilla.mostrarNombreFuncionario,
         				id : "mostrarNombreFuncionario",
         				maxlength : "50",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			},{
         				label : Documentos.labelPlantilla.mostrarCargoFuncionario,
         				id : "mostrarCargoFuncionario",
         				maxlength : "50",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			},{
         				label : Documentos.labelPlantilla.cargo,
         				id : "cargo",
         				maxlength : "50",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			}, {
         				label : Documentos.labelPlantilla.funcionario,
         				id : "funcionario",
         				maxlength : "50",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			},{
         				label : Documentos.labelPlantilla.tipoFechaReferencia,
         				id : "tipoFechaReferencia",
         				maxlength : "50",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			},{
         				label : Documentos.labelPlantilla.variable,
         				id : "variable",
         				maxlength : "50",
						readonly : "true",
         				value : "",
         				isText : true,
         				isTextarea : false,
         				isSelect : false,
         				classname: "text"
         			}]
         		} ] };


//Propiedades de imagen
Documentos.propertiesEditImage =  {  
		id: "imageEdit",
		properties	 :[
           		{
           			title : Documentos.labelPlantilla.general,
           			description : Documentos.labelPlantilla.conf_general,
           			fields : [ {
           				label : Documentos.labelPlantilla.ancho,
           				id : "anchoImagen",
           				maxlength : "50",
  						readonly : "false",
           				value : "",
           				isText : true,
           				isTextarea : false,
           				isSelect : false,
           				classname: "number_integer"
           			}, {
           				label : Documentos.labelPlantilla.alto,
           				id : "altoImagen",
           				maxlength : "50",
  						readonly : "false",
           				value : "",
           				isText : true,
           				isTextarea : false,
           				isSelect : false,
           				classname: "number_integer"
           			} ]
           		} ] };