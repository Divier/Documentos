/**
 * Controlador encargado del manejo de las plantillas
 */
Documentos.PlantillasController = function() {
	this.ordenesJerarquicos = null;
	this.disabledEventsMaintabs = false;
	this.$workArea = null;
	this.queryDialog = null;
	this.querySentence = null;
	this.imageSrc = null;
	this.properties = null;
	this.isEditVariable = null;
//	this.primeraVez = {};
	this.variablesArray = null;
	this.variablesJSON = {};
	this.idFirma = null;
	this.workAreaClass = "workArea";
	this.mainWorkArea = "#workArea-0";
	this.numeroGrupoAttr = "numeroGrupo";
	this.creaPestania = -1;
	this.numeroGrupos = 1;
	this.maxHeightSection = 0.0;
	this.maxHeightItem = 0.0;
	this.maxWidthItem = 0.0;
	this.focusClass = "focus_item";
	this.momentPattern = /([']{1})(.+)([']{1})/;
};

/**
 * Funcion que inicializa el mapa y eventos necesarios
 */
Documentos.PlantillasController.prototype.init = function() {

	/***************************************************************************
	 * Eventos de navegacion en la pagina
	 **************************************************************************/

	// Selecciona volver al formulario
	$("#areaTrabajoForm\\:volver").on('click', this.onBack);

	/***************************************************************************
	 * Eventos de carga de propiedades de la pagina de acuerdo a la
	 * configuracion
	 **************************************************************************/
	
	// Verificar si es edicion o es nuevo
	this.isEditVariable = $(Documentos.Plantillas.mainWorkArea).find("[id$='xmlPlantilla']").val() != "";
	// Properties de la pagina por defecto
	this.properties = Documentos.propertiesPage;
	// Carga el contenido de la plantilla Llena las propiedades de la plantilla
	Documentos.Plantillas.cargarPlantilla(0);

	// Cargar Variables
	this.variablesArray = new Array();

	/***************************************************************************
	 * Eventos sobre los item agregados a la plantilla
	 **************************************************************************/

	// Si se da click sobre el body se deseleccionan los items
	$(document).on('click', '.workArea', this.onClickWorkArea);

	// Al presionar el boton derecho sobre un item
	// this.$workArea.on("contextmenu", ".item", function(e) {
	// alert('Context Menu event has fired!');
	// return false;
	// });

	/***************************************************************************
	 * Eventos del editor de texto
	 **************************************************************************/
	// The "instanceCreated" event is fired for every editor instance created.
	CKEDITOR.on('instanceCreated', this.onInstanceCreatedCKEDITOR);
	// The "instanceReady" event is fired for every editor instance ready.
	CKEDITOR.on('instanceReady', this.onInstanceReadyCKEDITOR);

	// Eventos cambio de datos en properties
	$('#areaTrabajoForm\\:propertiesDialog').on("spinchange", ".number,.number_integer",
			Documentos.Plantillas.onChangeProperty);
	$('#areaTrabajoForm\\:propertiesDialog')
			.on(
					"change",
					".text, .template_editor_inputs_TextAreaSkin-textarea, .template_editor_inputs_SelectSkin-select",
					Documentos.Plantillas.onChangeProperty);

	$("#areaTrabajoForm\\:propertiesButton").on("click",
			this.onClickPropertiesButton);
	

	//Evento cuando se da click para agregar un grupo
	$(document).on('click', '.cke_button__grupo', Documentos.Plantillas.onClickGroupButton);

	//Evento cuando se da click para agregar una firma
	$(document).on('click', '.cke_button__firmamecanica', Documentos.Plantillas.onClickFirmaButton);
	
	//Mostrar la pestania del grupo cuando se haga doble click sobre el grupo
	$(document).on('dblclick', '.grupo', Documentos.Plantillas.onDblClickGrupo);	
	
};

/**
 * Se ejecuta cuando se da click sobre un boton de firma
 */
Documentos.PlantillasController.prototype.onClickFirmaButton = function(event) {
	console.debug("onClickFirmaButton");
	
	//Se guarda en el campo el contenido del area de trabajo actual
	Documentos.Plantillas.setHTMLAttributes();
	var $tabSection = Documentos.Plantillas.getTab(PF("tabView").getActiveIndex());
	$tabSection.find("[id$='xmlPlantilla']").val(Documentos.CABECERA_HTML + $('<div>').append(Documentos.Plantillas.$workArea.clone()).html() + Documentos.HEADER_HTML);
	
	//Ejecuta un evento para crear la nueva pestania
	$("#areaTrabajoForm\\:verFirma").click();
	return true;
};

/**
 * Se ejecuta cuando se da click sobre un boton de grupo
 */
Documentos.PlantillasController.prototype.onClickGroupButton = function(event) {
	console.debug("onClickGroupButton");
	
	//Indica las pestania creada
	Documentos.Plantillas.creaPestania = Documentos.Plantillas.numeroGrupos;
	$("#areaTrabajoForm\\:numeroGrupo").val(Documentos.Plantillas.numeroGrupos);

	// Nombre del grupo
	$("#grupo_" + Documentos.Plantillas.numeroGrupos).find(".grupo-content")
			.eq(0).html("grupo_" + Documentos.Plantillas.numeroGrupos);
	
	//Se guarda en el campo el contenido del area de trabajo actual
	Documentos.Plantillas.setHTMLAttributes();
	var $tabSection = Documentos.Plantillas.getTab(PF("tabView").getActiveIndex());
	$tabSection.find("[id$='xmlPlantilla']").val(Documentos.CABECERA_HTML + 
			$('<div>').append(Documentos.Plantillas.$workArea.clone()).html() + Documentos.HEADER_HTML);
	
	// Aumenta el numero de grupos creados
	Documentos.Plantillas.numeroGrupos++;

	//Pone el valor del atributo de numero de grupos
	$(Documentos.Plantillas.mainWorkArea).attr(Documentos.Plantillas.numeroGrupoAttr, Documentos.Plantillas.numeroGrupos);
	
	//Ejecuta un evento para crear la nueva pestania
	$("#areaTrabajoForm\\:verGrupo").click();
	return true;
};

/**
 * Se ejecuta cuando se da doble click sobre un grupo
 */
Documentos.PlantillasController.prototype.onDblClickGrupo = function(event) {
	console.debug("onDblClickGrupo");
	var tab = $(this).attr("tab");
//	var $content = $(this).find(".grupo_content");
	//busca el las pesta�a del grupo
	var tabNumber = 0;
	Documentos.Plantillas.getTabs()
			.each(function(index, section) { 
		if($(section).find("#workArea-" + tab).length > 0) {
//			$(section).find("[id$='xmlPlantilla']").val($content.html());
			tabNumber = index;
			return false;
		}
	});
//	if($content.length > 0 && $content.html() != "") {
//		$("#areaTrabajoForm\\:tabs\\:" + tab + "\\:xmlPlantilla")
//		.val( $content.html());			
//	}
	PF("tabView").select(tabNumber);
};

/**
 * Se ejecuta cuando se muestra una pesta�a de los grupos
 */
Documentos.PlantillasController.prototype.onShowGroupTab = function(event) {
	console.debug("onShowGroupTab");
	// Carga el contenido de la plantilla Llena las propiedades de la plantilla
	Documentos.Plantillas.cargarPlantilla(PF("tabView").getActiveIndex());
	// Ajusta las propiedades de la pagina por defecto,
	Documentos.Plantillas.showProperties(Documentos.propertiesPage["parentId"], Documentos.propertiesPage);
};

/**
 * Se ejecuta cuando se da click sobre el area de trabajo
 */
Documentos.PlantillasController.prototype.onClickWorkArea = function(e) {
	console.debug("this.$workArea.click");
	Documentos.Plantillas.deselectItems();
	var $element = $(e.target);
	if ($element.hasClass("variable")) {
		console.debug("this.$workArea.click VARIABLE");
		$element.parent().addClass(Documentos.Plantillas.focusClass);
		if ($element.attr(Documentos.TIPO_VARIABLE) == Documentos.ID_TIPO_VARIABLE_IMAGEN) {
        	$element.attr(Documentos.ALTO_IMAGEN, $element.height() );	
        	$element.attr(Documentos.ANCHO_IMAGEN, $element.width() );	
			Documentos.Plantillas.showProperties($element.attr("id"), Documentos.propertiesImage);
		} else if ($element.attr(Documentos.TIPO_VARIABLE) == Documentos.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
        	$element.attr(Documentos.ALTO_IMAGEN, $element.height() );	
        	$element.attr(Documentos.ANCHO_IMAGEN, $element.width() );	
			Documentos.Plantillas.showProperties($element.attr("id"), Documentos.propertiesVariableImage);
		} else if ($element.attr(Documentos.TIPO_VARIABLE) == Documentos.ID_TIPO_VARIABLE_NUMERO) {
			Documentos.Plantillas.showProperties($element.attr("id"), Documentos.propertiesNumber);
			Documentos.Plantillas.onChangePresentacion();
			Documentos.Plantillas.onChangeNumerosNegativos();
		} else if ($element.attr(Documentos.TIPO_VARIABLE) == Documentos.ID_TIPO_VARIABLE_FECHA) {
			Documentos.Plantillas.showProperties($element.attr("id"), Documentos.propertiesVariable);
			$( "#valorDefecto" ).datepicker();
			$( "#valorDefecto" ).datepicker("hide");
			$( "#valorDefecto" ).datepicker( "option", "dateFormat", 'yy-mm-dd' );
			$( "#valorDefecto" ).datepicker( "setDate", $element.attr("valorDefecto")  );
			$element.attr("valorDefectoFecha", moment($element.attr("valorDefecto"), Documentos.FORMATO_FECHA, Documentos.LOCALE)
					.format(moment().toMomentFormatString($element.attr("formatoVariable"))));
		} else {
			Documentos.Plantillas.showProperties($element.attr("id"), Documentos.propertiesText);			
		}
	} else if ($element.hasClass("grupo") || $element.hasClass("grupo-content")) {
		console.debug("this.$workArea.click GRUPO");
		if ($element.hasClass("grupo-content")) { 
			$element = $element.parent();
		}		
		$element.parent().addClass(Documentos.Plantillas.focusClass);
		Documentos.Plantillas.showProperties($element.attr("id"), Documentos.propertiesGroup);
	} else if ($element.hasClass("firmamecanica")) {
		console.debug("this.$workArea.click FIRMAMECANICA");
		$element.parent().addClass(Documentos.Plantillas.focusClass);
		Documentos.Plantillas.showProperties($element.attr("id"), Documentos.propertiesSign);
	}
	return true;
};

/**
 * Se ejecuta cuando visualiza las propiedades
 */
Documentos.PlantillasController.prototype.onClickPropertiesButton = function(
		event) {
	console.log("click:propertiesButton");
	if ($(this).css("right") == "205px") {
		$("#areaTrabajoForm\\:propertiesDialog").animate({
			right : '-300px'
		}, 'slow');
		$(this).animate({
			right : '-100px'
		}, 'slow');
	} else {
		$("#areaTrabajoForm\\:propertiesDialog").animate({
			right : '5px'
		}, 'slow');
		$(this).animate({
			right : '205px'
		}, 'slow');
	}
};
/**
 * Se ejecuta cuando se configura una firma mecanica
 */
Documentos.PlantillasController.prototype.addFirmaMecanica = function(
		firma) {
	console.debug("addFirmaMecanica");
	var $elemento = $("#" + Documentos.Plantillas.idFirma);
	// Verifica si existe en el area de trabajo otra variable con el mismo
	// nombre
	var itemId = "_";
	var cantidad = $(".firmamecanica").length;
	//Determina la cantidad de firmas que hay en la plantilla para identificarlos
	if (cantidad > 1) {
		for (var int = 1; true; int++) {
			if ($(".firmamecanica[item=" + int + "]").length == 0) {
				$elemento.attr(Documentos.ITEM_CLASS, int);
				itemId += int;  
				break;
			}
		}
	} else {
		$elemento.attr(Documentos.ITEM_CLASS, 0);	   
	}
	//Coloca los atributos para la generacion posterior
	//Verifica si se debe mostrar o no el nombre del funcionario
	if(firma.mostrarNombreFuncionario) {
		$elemento.find(".firmanombre .campofirma").text("{NOMBRE_FUNCIONARIO" + itemId + "}");	
		$elemento.find(".firmanombre").attr("field", "NOMBRE_FUNCIONARIO" + itemId );						
	} else {
		//Si no se debe mostrar se quita el elemento del componente de firma
		$elemento.find(".firmanombre").remove();	
	}   
	//Verifica si se debe mostrar o no el cargo del funcionario
	if(firma.mostrarCargoFuncionario) {
		$elemento.find(".firmacargo .campofirma").text("{CARGO_FUNCIONARIO" + itemId + "}");
		$elemento.find(".firmacargo").attr("field", "CARGO_FUNCIONARIO" + itemId);						
	} else {
		//Si no se debe mostrar se quita el elemento del componente de firma
		$elemento.find(".firmacargo").remove();	
	} 
	$elemento.find("img").attr("srcField", "$F{FIRMA_FUNCIONARIO" + itemId + "}");	
	$elemento.find("img").attr("field", "FIRMA_FUNCIONARIO" + itemId );	
	
	//Pone los atributos necesarios para posteriormente guardar los datos
	$elemento.attr("text", $elemento.text());
	$elemento.attr("cargo", "");	
	$elemento.attr("funcionario", "");	
	$elemento.attr("tipoFechaReferencia", "");	
	$elemento.attr("tipoFirmaPlantilla", "");	
	$elemento.attr("variable", "");	
	if(firma.cargoDTO != undefined) {
		$elemento.attr("cargo", firma.cargoDTO.nombreCargo);		
	}
	if(firma.funcionarioDTO != undefined) {
		$elemento.attr("funcionario", firma.funcionarioDTO.nombreFuncionario);	
		firma.funcionarioDTO.firma = null;
	}
	if(firma.tipoFechaReferenciaDTO != undefined) {
		$elemento.attr("tipoFechaReferencia", firma.tipoFechaReferenciaDTO.nombreTipoFecha);
	}
	if(firma.tipoFirmaPlantillaDTO != undefined) {
		$elemento.attr("tipoFirmaPlantilla", firma.tipoFirmaPlantillaDTO.nombre);
	}
	if(firma.variableDTO != undefined) {
		$elemento.attr("variable", firma.variableDTO.nombreVariable);
	}
	$elemento.attr("mostrarNombreFuncionario", (firma.mostrarNombreFuncionario) ? Documentos.labelPlantilla.si : Documentos.labelPlantilla.no);
	$elemento.attr("mostrarCargoFuncionario", (firma.mostrarCargoFuncionario) ? Documentos.labelPlantilla.si : Documentos.labelPlantilla.no);
	$elemento.attr("firma", JSON.stringify(firma));
};

/**
 * Se ejecuta antes de mostrar el dialogo de propiedades
 */
Documentos.PlantillasController.prototype.beforeShowProperties = function() {
	console.debug("beforeShowProperties");
	$(this).find("#areaTrabajoForm\\:propertiesDialog .panel-formulario").html("");

	// Elemento al que se le deben agregar las propiedades
	var $parentId = $("#" + Documentos.Plantillas.properties.parentId);
	if (Documentos.Plantillas.properties.parentId == Documentos.Plantillas.workAreaClass) {
		$parentId = Documentos.Plantillas.$workArea;
	}

	// Recorrer properties y agregar valor o valor por defecto

	// Llena valores de acuerdo a las propiedades
	Documentos.Plantillas.properties.properties.forEach(function(property) {
//		console.log(property);
		property.fields.forEach(function(field) {
//			console.log(field);
			// valido si ya existia esta propiedad creada
			if ($parentId.attr(field.id) != undefined) {
				field.value = $parentId.attr(field.id);
				if (field.isSelect) {
					field.options.forEach(function(option) {
						if (option.value == field.value) {
							option.selected = true;
						} else {
							option.selected = false;
						}
					});
				}
			}
		});
	});

	// Genera los campos de propiedades para editarlos
	Documentos.generaHtmlDinamico(Documentos.Plantillas.properties,
			'#template_properties',
			'#areaTrabajoForm\\:propertiesDialog .panel-formulario');

	// spiner en campos numericos
	$('#areaTrabajoForm\\:propertiesDialog .panel-formulario').find(".number")
			.spinner({
				step : 0.01,
				numberFormat : "n",

				spin : function(event, ui) {
			        if ((event.target.id == Documentos.INPUT_ENCABEZADO || event.target.id == Documentos.INPUT_PIE_PAGINA  
			        		|| event.target.id == Documentos.ALTO_IMAGEN )
			        	&& ui.value > Documentos.Plantillas.maxHeightSection ) {
			          $( this ).spinner( "value", Documentos.Plantillas.maxHeightSection.toFixed(1) );
			          return false;
			        } else if (ui.value < 0) {
						$(this).spinner("value", 0);
						return false;
					}
				}
			});
	$('#areaTrabajoForm\\:propertiesDialog .panel-formulario').find(".number_integer")
	.spinner({
		step : 1,
		numberFormat : "n",

		spin : function(event, ui) {
	        if ((event.target.id == Documentos.ALTO_IMAGEN )
		        	&& ui.value > Documentos.convert_centimetros_pixeles(Documentos.Plantillas.maxHeightItem) ) {
		          $( this ).spinner( "value", Documentos.convert_centimetros_pixeles(Documentos.Plantillas.maxHeightItem).toFixed(0) );
		          return false;
		        } else if ((event.target.id == Documentos.ANCHO_IMAGEN )
			        	&& ui.value > Documentos.convert_centimetros_pixeles(Documentos.Plantillas.maxWidthItem) ) {
			          $( this ).spinner( "value", Documentos.convert_centimetros_pixeles(Documentos.Plantillas.maxWidthItem).toFixed(0) );
			          return false;
			    } else if (ui.value < 0) {
					$(this).spinner("value", 0);
					return false;
				}
		}
	});
	// Cuando el readonly es false
	$('#areaTrabajoForm\\:propertiesDialog .panel-formulario').find(
			".text[readonly='false']").attr("readonly", false);
	
	//Si son las propiedades de la pestania de grupo no las deja editar
	if(Documentos.Plantillas.$workArea.attr("tab") != "0" && 
			Documentos.Plantillas.properties.parentId == Documentos.Plantillas.workAreaClass) {
		$('#areaTrabajoForm\\:propertiesDialog .panel-formulario').find(
		"input,select").attr("disabled", true);
		$('#areaTrabajoForm\\:propertiesDialog .panel-formulario').find(
				'.ui-spinner a.ui-spinner-button').css('display','none');
	}
};

/**
 * Evento al crear una instancia de ckeditor
 */
Documentos.PlantillasController.prototype.onInstanceCreatedCKEDITOR = function(
		event) {
	var editor = event.editor;
	var element = editor.element;
	console.debug("onInstanceCreatedCKEDITOR");

	// Customize editors for headers and tag list.
	// These editors don't need features like smileys, templates, iframes etc.
	if (element.hasClass('band_content')) {
		// if ( element.is( 'h1', 'h2', 'h3' ) || element.getAttribute( 'id' )
		// ==
		// 'taglist' ) {
		// Customize the editor configurations on "configLoaded" event,
		// which is fired after the configuration file loading and
		// execution. This makes it possible to change the
		// configurations before the editor initialization takes place.
		// editor.on( 'configLoaded', function() {
//		 editor.config.language = 'es';
		//
		// // Remove unnecessary plugins to make the editor simpler.
		var configCK = Documentos.ckeditorConfigEncabezado;
		if(element.getAttribute( 'id' ).indexOf("cuerpo") != -1) {
			if(PF("tabView").getActiveIndex() > 0) {
				configCK = Documentos.ckeditorConfigGrupo;				
			} else {
				configCK = Documentos.ckeditorConfigCuerpo;				
			}	
		} else if(element.getAttribute( 'id' ).indexOf("piePagina") != -1) {
			configCK = Documentos.ckeditorConfigPiePagina;
		} 
		editor.config.maxLength = 0; 
		editor.config.extraPlugins = configCK.extraPlugins;
		editor.config.removePlugins = configCK.removePlugins;		
		editor.config.font_defaultLabel = configCK.font_defaultLabel;
		editor.config.fontSize_defaultLabel = configCK.fontSize_defaultLabel;
		editor.config.font_names = configCK.font_names;		
		editor.config.toolbar_myToolbar = Documentos.myToolbar;		
		editor.config.removeDialogTabs = configCK.removeDialogTabs;
		if(element.getAttribute( 'customTitle' ) != undefined) {
			editor.config.title = element.getAttribute( 'customTitle' );			
		}
//		editor.config.forcePasteAsPlainText = true;
	}
};

/**
 * Evento cuando esta lista una instancia de ckeditor
 */
Documentos.PlantillasController.prototype.onInstanceReadyCKEDITOR = function(
		event) {
	console.debug("onInstanceReadyCKEDITOR");
	var editor = event.editor;

	// Cambia el tamanio del ckeditor
	 var anchoCke = parseFloat(Documentos.Plantillas.$workArea.attr(Documentos.ANCHO)) -
	 				(parseFloat(Documentos.Plantillas.$workArea.attr("margenIzquierda")) +
	 				parseFloat(Documentos.Plantillas.$workArea.attr("margenDerecha")));
	 $(".cke").css("width", anchoCke + "cm");
	
	// Hace que todas las instancias se puedan editar
	editor.setReadOnly(false);

	// Maneja el evento de cambio en el editor
	editor.on('change', Documentos.Plantillas.onChangeCKEditor);
	
	editor.on('paste', function (ev) {
	        ev.data.dataValue = ev.data.dataValue.replace(/<br( [^>]*)?>/gi, '</p><p>');
	});
};

/**
 * Evento al cambiar el texto de las secciones editables
 */
Documentos.PlantillasController.prototype.onChangeCKEditor = function(event) {
	console.debug("onChangeCKEditor");
	var editor = event.editor;
	
	//Ajusta el tamano de 
	Documentos.Plantillas.adjustSizeWorkArea($("#" + editor.name).parent(), 
			$("#" + editor.name).parent().height());
	
	//Funcion para validar si el grupo se encuentra en una pestania deshabilitada
	var inTabDisabled = function (tab) {
		var inTabDisabledReturn = true;
		$(document).find("#grupo_" + tab)
				.each(function(index, section) { 
					if($(section).closest("[disabledTab='true']").length == 0 ) {	
						inTabDisabledReturn = false;
						return false;
					}
				});
		return inTabDisabledReturn;
	};
	
	//Pone como no editables los caption
	Documentos.Plantillas.$workArea.find("caption").attr("contenteditable", "false");
	Documentos.Plantillas.$workArea.find("caption").attr("title", Documentos.labelPlantilla.tituloNoEditable);
	
	//busca el las pesta�a del grupo para deshabilitar las que no estan
	//y habilitar las pestanias que estan
	Documentos.Plantillas.getTabs()
			.each(function(index, section) { 
			//Verifica el id de la pestania
			var tab = $(section).find(".workArea").eq(0).attr("tab");
			//Verifica qu exista el grupo de las pestania
			if(tab != "0" && 
					($(document).find("#grupo_" + tab).length == 0 || inTabDisabled(tab))) {				
				//Si no existe deshabilita las pestania
				PF('tabView').disable(index);
				$(section).find("[id$='disabled']").val(true);
				$(section).find(".workArea").eq(0).attr("disabledTab", "true");
			} else {
				//Si existe habilita las pestania
				PF('tabView').enable(index);
				$(section).find("[id$='disabled']").val(false);	
				$(section).find(".workArea").eq(0).attr("disabledTab", "false");		
			}	
	});
};




/**
 * Borra la firmas vacias
 */
Documentos.PlantillasController.prototype.hideSign = function() {
	//Quita las firmas que hayan sido canceladas
	$(".firmamecanica")
			.each(function(index, section) { 
		if($(section).text() == "--") {
			$(section).parent().addClass("removerFirma");
		}
	});
	$(".removerFirma").remove();
};
/**
 * Evento al terminar de cambiar el tamanio de una seccion
 */
Documentos.PlantillasController.prototype.onStopResizeSection = function(event,
		ui) {
	console.debug("onStopResizeSection");
	Documentos.Plantillas.deselectItems();
	//Ajusta el tamanio de las secciones
	Documentos.Plantillas.adjustSizeWorkArea(ui.element, ui.size.height);
};

/**
 * Evento al terminar de cambiar el tamanio de una seccion
 */
Documentos.PlantillasController.prototype.adjustSizeWorkArea = function(section,
		height) {
	console.debug("adjustSizeWorkArea");
	var suma_altura = 0;
	//Suma las alturas de las secciones
	$.each(Documentos.Plantillas.$workArea.find(".band"), function(key, value) {
		if (section.attr("id") == value.id) {
			suma_altura += height;
		} else {
			suma_altura += $(value).height();
		}
	});
	suma_altura += 8;
	// Mostar u ocultar el label de la seccion
	if (height <= 0) {
		section.find(".bandName").eq(0).hide();
		if(section.find(".band_content").text() == "") {
			section.find(".band_content").attr("contenteditable", false);				
			section.find(".band_content").html("");
		}
	} else {
		section.find(".band_content").attr("contenteditable", true);	
		section.find(".bandName").eq(0).show();
	}
	//Ajusta el tamanio del area de trabajo
	Documentos.Plantillas.$workArea.animate({
		height : suma_altura + 'px'
	}, 'slow');
	//Cambia el valor de la propiedad
	$("#" + section.attr("id") + "Input").val(
			Documentos.convert_pixeles_centimetros(section.height()));
	// Elemento al que se le deben agregar las propiedades
	var $parentId = $("#" + Documentos.Plantillas.properties.parentId);
	if (Documentos.Plantillas.properties.parentId == Documentos.Plantillas.workAreaClass) {
		$parentId = Documentos.Plantillas.$workArea;
	}
	// Actualiza el valor en las propiedades
	$parentId.attr(section.attr("id") + "Input", Documentos
			.convert_pixeles_centimetros(section.height()));
	// Actualiza el valor en la propiedad del alto total
	$parentId.attr("altoTotal", Documentos
			.convert_pixeles_centimetros(suma_altura));


	//Calcula el maximo tamano de las secciones e items
	Documentos.Plantillas.setMaxSizeItems();
	
	// ajusta la altura del contenido
//	var height = $("#areaTrabajoForm").height();
//	$(".contenido").eq(0).animate({
//		height : height + "px"
//	}, 'slow');

};

/**
 * Evento al continuar muestra espacio de trabajo
 */
Documentos.PlantillasController.prototype.onContinue = function() {
	console.debug("onContinue");

	// Cargar Variables
	var variablesJSONTemp = JSON.parse($("#areaTrabajoForm\\:variables").val());
	Documentos.Plantillas.variablesArray = new Array();
	Documentos.Plantillas.variablesJSON = {};
	Documentos.Plantillas.ordenesJerarquicos = [
	     	                  {id:Documentos.ID_ORDEN_ORGANIZACION,nombre:Documentos.labelPlantilla.ordenJerarquico.organizacion, opciones:[]},
	     	                  {id:Documentos.ID_ORDEN_PROCESO,nombre:Documentos.labelPlantilla.ordenJerarquico.proceso, opciones:[]},
	     	                  {id:Documentos.ID_ORDEN_PLANTILLA,nombre:Documentos.labelPlantilla.ordenJerarquico.plantilla, opciones:[]}
	     	                  ];
	variablesJSONTemp.forEach(function(variable) {
		Documentos.Plantillas.variablesArray.push([ variable.nombreVariable,
				variable.idVariable ]);
		Documentos.Plantillas.variablesJSON[variable.idVariable] = variable;
		//Utilizado para asignar los valores en la adicion de variables
		if(variable.contextoAplicacionVariableDTO.idContextoAplicacion == Documentos.ID_ORDEN_ORGANIZACION) {
			Documentos.Plantillas.ordenesJerarquicos[0].opciones.push([ variable.nombreVariable,
			                                               				variable.idVariable ]);			
		} else if(variable.contextoAplicacionVariableDTO.idContextoAplicacion == Documentos.ID_ORDEN_PROCESO) {
			Documentos.Plantillas.ordenesJerarquicos[1].opciones.push([ variable.nombreVariable,
				                                               				variable.idVariable ]);			
		} else if(variable.contextoAplicacionVariableDTO.idContextoAplicacion == Documentos.ID_ORDEN_PLANTILLA) {
				Documentos.Plantillas.ordenesJerarquicos[2].opciones.push([ variable.nombreVariable,
					                                               				variable.idVariable ]);			
		}
	});
	//Mostrar area de trabajo
	$('#cuadro').animate({
		left : '-100%'
	}, 'slow', function() {
		// Muestra el dialogo de propiedades
		PF('propertiesDialog').show();
		// Ajusta la posicion del dialogo
		$("#areaTrabajoForm\\:propertiesDialog").css("right", "-300px");
		$("#areaTrabajoForm\\:propertiesButton").css("right", "-100px");
		$("#areaTrabajoForm\\:propertiesButton").removeClass("hidden");
	});
	$('#contenedor').css('overflow', 'auto');
	var height = $(window).height();;
	$(".contenido").animate({
		height : height + "px",
		"padding-top" : "0px",
		"min-width" : "1250px"
	}, 'slow');
	// Oculta el menu
	$(".navbar").hide();
	// Carga el contenido de la plantilla Llena las propiedades de la plantilla
	// Recorre las pestanias para actualizar los xml para su posterior  
	// generacion o guardado
	Documentos.Plantillas.cargarPlantilla(PF("tabView").getActiveIndex());
	//Inicializa el numero de grupos
	Documentos.Plantillas.numeroGrupos = PF("tabView").getLength();
	if($(Documentos.Plantillas.mainWorkArea).attr(Documentos.Plantillas.numeroGrupoAttr) != undefined && 
			parseInt($(Documentos.Plantillas.mainWorkArea).attr(Documentos.Plantillas.numeroGrupoAttr)) >= PF("tabView").getLength()) {
		Documentos.Plantillas.numeroGrupos = $(Documentos.Plantillas.mainWorkArea).attr(Documentos.Plantillas.numeroGrupoAttr);		
	} else {
		$(Documentos.Plantillas.mainWorkArea).attr(Documentos.Plantillas.numeroGrupoAttr, Documentos.Plantillas.numeroGrupos);
	}

	//Calcula el maximo tamano de las secciones de encabezado y pie de pagina
	Documentos.Plantillas.maxHeightSection = parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.ALTO))/3;
	
	//Calcula el maximo tamano de las secciones e items
	Documentos.Plantillas.setMaxSizeItems();
	
	//cambia el maximo tama�o de las secciones de encabezado y pie de pagina
	$("#encabezado .band_content, #piePagina .band_content, #encabezado, #piePagina")
			.css("max-height" , Documentos.Plantillas.maxHeightSection + "cm");
	
	//Ajusta el tama�o del area de trabajo
	Documentos.Plantillas.adjustSizeWorkArea($("#piePagina").parent(), 
			$("#piePagina").parent().height());
};

/**
 * AJusta los valores maximos para las imagenes
 */
Documentos.PlantillasController.prototype.setMaxSizeItems = function() {
	//Calcula el maximo alto de los items
	Documentos.Plantillas.maxHeightItem = parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.ALTO))
	- (parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.INPUT_ENCABEZADO)) + 
			parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.INPUT_PIE_PAGINA)) + 
			parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.INPUT_MARGEN_SUPERIOR)) + 
			parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.INPUT_MARGEN_INFERIOR)) + 
			Documentos.convert_pixeles_centimetros(30));
	$(Documentos.Plantillas.mainWorkArea + " img").css("max-height" , Documentos.Plantillas.maxHeightItem + "cm");
	//Calcula el maximo ancho de los items
	Documentos.Plantillas.maxWidthItem = parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.ANCHO))
	- ( parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.INPUT_MARGEN_DERECHA)) + 
			parseFloat($(Documentos.Plantillas.mainWorkArea).attr(Documentos.INPUT_MARGEN_IZQUIERDA)));
};

/**
 * Vuelve a las propiedades iniciales de la plantilla
 */
Documentos.PlantillasController.prototype.onBack = function(e) {
	console.debug("onBack");
//	e.preventDefault();
	Documentos.Plantillas.processHtml();
	$('#cuadro').animate({
		left : '0'
	}, 'slow');
	$('#contenedor').css('overflow', 'hidden');
	$('#contenedor').animate({
		scrollTop : 0,
		scrollLeft : 0
	}, 'slow');
	// Oculta el dialogo de propiedades
	PF('propertiesDialog').hide();
	$(".contenido").animate({
		height : "500px",
		"padding-top" : "0px",
		"min-width" : "750px"
	}, 'slow');
	// Muestra el menu
	$(".navbar").show();
	$("#areaTrabajoForm\\:propertiesButton").addClass("hidden");
};

/**
 * Eventos en las propiedades
 */
Documentos.PlantillasController.prototype.onChangeProperty = function(e) {
	console.debug("onChangeProperty");
	e.preventDefault();
	// Elemento al que se le deben agregar las propiedades
	var $parentId = $("#" + Documentos.Plantillas.properties.parentId);
	if (Documentos.Plantillas.properties.parentId == Documentos.Plantillas.workAreaClass) {
		$parentId = Documentos.Plantillas.$workArea;
	}
	// Actualiza el valor en las propiedades
	$parentId.attr(this.id, this.value);

	// en caso de cambiar el ancho o alto de la pagina
	if (this.id == Documentos.ANCHO || this.id == Documentos.ALTO) {
		if (($("#ancho").val() != Documentos.ANCHO_PAGINA_CARTA || 
				$("#alto").val() != Documentos.ALTO_PAGINA_CARTA)
			&& ($("#ancho").val() != Documentos.ANCHO_PAGINA_LEGAL || 
				$("#alto").val() != Documentos.ALTO_PAGINA_LEGAL)
			&& ($("#ancho").val() != Documentos.ANCHO_PAGINA_A4 || 
				$("#alto").val() != Documentos.ALTO_PAGINA_A4)
			&& ($("#ancho").val() != Documentos.ANCHO_PAGINA_EJECUTIVO || 
				$("#alto").val() != Documentos.ALTO_PAGINA_EJECUTIVO)) {
			$("#formato").val(Documentos.PERSONALIZADO);
		}
		if(this.id == Documentos.ANCHO) {
			// Asigna el valor del ancho total del area de trabajo
			Documentos.Plantillas.$workArea.animate({
				width : $("#ancho").val() + 'cm'
			}, 'slow');
		}
		if(this.id == Documentos.ALTO) {
			//Calcula el maximo tama�o de las secciones de encabezado y pie de pagina
			Documentos.Plantillas.maxHeightSection = (this.value / 3);
			$("#encabezado .band_content, #piePagina .band_content, #encabezado, #piePagina")
					.css("max-height" , Documentos.Plantillas.maxHeightSection + "cm");

			//Calcula el maximo tamano de las secciones e items
			Documentos.Plantillas.setMaxSizeItems();
		}
	}
	// en caso de cambiar el formato
	else if (this.id == Documentos.FORMATO) {
		var alto = 0;
		var ancho = 0;
		if ($("#formato").val() == Documentos.CARTA) {
			alto = Documentos.ALTO_PAGINA_CARTA;
			ancho = Documentos.ANCHO_PAGINA_CARTA;
		} else if ($("#formato").val() == Documentos.LEGAL) {
			alto = Documentos.ALTO_PAGINA_LEGAL;
			ancho = Documentos.ANCHO_PAGINA_LEGAL;
		} else if ($("#formato").val() == Documentos.A4) {
			alto = Documentos.ALTO_PAGINA_A4;
			ancho = Documentos.ANCHO_PAGINA_A4;
		} else if ($("#formato").val() == Documentos.EJECUTIVO) {
			alto = Documentos.ALTO_PAGINA_EJECUTIVO;
			ancho = Documentos.ANCHO_PAGINA_EJECUTIVO;
		}
		if(alto > 0) {
			$("#alto").val(alto);
			$("#ancho").val(ancho);
			// Actualiza el valor en las propiedades
			$parentId.attr(Documentos.ALTO, alto);
			$parentId.attr(Documentos.ANCHO, ancho);
			Documentos.Plantillas.maxHeightSection = (alto / 3);

			//Calcula el maximo tamano de las secciones e items
			Documentos.Plantillas.setMaxSizeItems();
		}
		
		//cambia el maximo tama�o de las secciones de encabezado y pie de pagina
		$("#encabezado .band_content, #piePagina .band_content, #encabezado, #piePagina")
				.css("max-height" , Documentos.Plantillas.maxHeightSection + "cm");
		// Asigna el valor del ancho total del area de trabajo
		Documentos.Plantillas.$workArea.animate({
			width : $("#ancho").val() + 'cm'
		}, 'slow');
	}
	// en caso de cambiar el tamaño de las secciones
	else if (this.id == Documentos.INPUT_TITULO || this.id == Documentos.INPUT_ENCABEZADO
			|| this.id == Documentos.INPUT_PIE_PAGINA || this.id == Documentos.INPUT_ULTIMO_PIE_PAGINA
			|| this.id == Documentos.INPUT_CUERPO) {
		// Modifica el area de trabajo segun el cambio realizado
		// Los ids de los campos se les incluyo la cadena 'Input'
		var id = "#" + $(this).attr("id").replace("Input", "");
		Documentos.Plantillas.$workArea.find(id).animate(
				{
					height : $(this).val() + 'cm'
				},
				'slow',
				function() {
					Documentos.Plantillas.adjustSizeWorkArea(Documentos.Plantillas.$workArea.find(id), 
							Documentos.Plantillas.$workArea.find(id).height());
				});
		// Mostar u ocultar el label de la seccion
		if ($(this).val() <= 0) {
			$(id).find(".bandName").eq(0).hide();
			if($(id).find(".band_content").text() == "") {
				$(id).find(".band_content").attr("contenteditable", false);				
				$(id).find(".band_content").html("");
			}
		} else {
			$(id).find(".band_content").attr("contenteditable", true);	
			$(id).find(".bandName").eq(0).show();
		}
	}
	// en caso de cambiar las margenes
	else if (this.id == Documentos.INPUT_MARGEN_IZQUIERDA) {
		// Modifica el area de trabajo segun el cambio realizado
		Documentos.Plantillas.$workArea.find(".band_content").animate({
			"margin-left" : $(this).val() + 'cm'
		}, 'slow');
	} else if (this.id == Documentos.INPUT_MARGEN_DERECHA) {
		// Modifica el area de trabajo segun el cambio realizado
		Documentos.Plantillas.$workArea.find(".band_content").animate({
			"margin-right" : $(this).val() + 'cm'
		}, 'slow');
	}// en caso de cambiar el formato de la variable
	else if (this.id == Documentos.ANCHO_IMAGEN) {
		$parentId.css("height", $("#" + Documentos.ALTO_IMAGEN).val() + "px");
		$parentId.css("width", $(this).val() + "px");
	}
	else if (this.id == Documentos.ALTO_IMAGEN) {
		$parentId.css("width", $("#" + Documentos.ANCHO_IMAGEN).val() + "px");
		$parentId.css("height", $(this).val() + "px");
		
		//Ajusta el tamano del area de trabajo
		Documentos.Plantillas.adjustSizeWorkArea(Documentos.Plantillas.$workArea.find("#cuerpo"), 
				Documentos.Plantillas.$workArea.find("#cuerpo").height());
	}
	else if (this.id == "editable") {
		if($(this).val() == Documentos.labelPlantilla.no) {
			$parentId.addClass("noEditable");			
		} else {
			$parentId.removeClass("noEditable");			
		}
	}
	else if (this.id == "formatoVariable") {
		//Se cambia el valor de acuerdo al formato de fecha cambiado
		$parentId.attr("valorDefectoFecha", moment($("#valorDefecto").val(), Documentos.FORMATO_FECHA, Documentos.LOCALE	)
				.format(moment().toMomentFormatString($("#formatoVariable").val())));
//		$( "#valorDefecto" ).datepicker( "option", "dateFormat", $("#formatoVariable").val() );
	}
	else if (this.id == "valorDefecto") {
		//Se cambia el valor de acuerdo al formato de fecha 
		if ($parentId.attr(Documentos.TIPO_VARIABLE) == Documentos.ID_TIPO_VARIABLE_FECHA) {
			$parentId.attr("valorDefectoFecha", moment($("#valorDefecto").val(), Documentos.FORMATO_FECHA, Documentos.LOCALE	)
					.format(moment().toMomentFormatString($("#formatoVariable").val())));
		}
	}
	else if (this.id == "presentacionVariable") {
		Documentos.Plantillas.onChangePresentacion();
	}
	else if (this.id == "formatoNumerosNegativos") {
		Documentos.Plantillas.onChangeNumerosNegativos();
	}

	// Cambia el tamaño del ckeditor
	var anchoCke = parseFloat(Documentos.Plantillas.$workArea.attr(Documentos.ANCHO))
			- (parseFloat(Documentos.Plantillas.$workArea
					.attr("margenIzquierda")) + parseFloat(Documentos.Plantillas.$workArea
					.attr("margenDerecha")));
	$(".cke").css("width", anchoCke + "cm");
};

Documentos.PlantillasController.prototype.onChangePresentacion = function() {
	console.debug("onChangePresentacion");
	if($("#presentacionVariable").val() != Documentos.labelPlantilla.presentacionVariableLetras) {
		$(".formatoPresentacionVariable").parent().hide();
		$(".separadorMiles").parent().show();
		$(".formatoNumerosNegativos").parent().show();
		
	} else {
		$(".formatoPresentacionVariable").parent().show();
		$(".separadorMiles").parent().hide();	
		$(".formatoNumerosNegativos").parent().hide();		
	}
	
};

Documentos.PlantillasController.prototype.onChangeNumerosNegativos = function() {
	console.debug("onChangeNumerosNegativos");
	if($("#formatoNumerosNegativos").val() == Documentos.COLOR_ROJO || 
			$("#formatoNumerosNegativos").val() == Documentos.COLOR_ROJO_PARENTESIS) {
		$("#formatoNumerosNegativos").addClass("colorRojo");
	} else {
		$("#formatoNumerosNegativos").removeClass("colorRojo");
	}	
};


/**
 * Funcion de deseleccionar un item
 */
Documentos.PlantillasController.prototype.deselectItems = function() {
	console.debug("deselectItems");
	// e.preventDefault();
	// e.stopPropagation();
//	if ($("." + Documentos.Plantillas.focusClass).length > 0) {
//		$("." + Documentos.Plantillas.focusClass).removeClass(Documentos.Plantillas.focusClass);
//		// Ajusta las propiedades de la pagina por defecto,
//		Documentos.Plantillas.showProperties(Documentos.propertiesPage["parentId"], 
//				Documentos.propertiesPage);
//	}
	//Quita la seleccion
	$("." + Documentos.Plantillas.focusClass).removeClass(Documentos.Plantillas.focusClass);
	// Ajusta las propiedades de la pagina por defecto,
	Documentos.Plantillas.showProperties(Documentos.propertiesPage["parentId"], 
			Documentos.propertiesPage);
};

/**
 * Funcion de mostrar las propiedades
 */
Documentos.PlantillasController.prototype.showProperties = function(parentId, properties) {
	console.debug("showProperties " + parentId);
	// Si son las propiedades de
	// Ajusta las propiedades que se deben mostrar
	Documentos.Plantillas.properties = properties;
	Documentos.Plantillas.properties["parentId"] = parentId;
	// Se oculta y muestra para actualizar la lista de propiedades
//	PF('propertiesDialog').hide();
//	PF('propertiesDialog').show();
	Documentos.Plantillas.beforeShowProperties();

};

/**
 * Procesar html para ponerlo en el campo xmlPlantilla
 */
Documentos.PlantillasController.prototype.processHtml = function() {
	console.debug("processHtml");
	// Solo lo ejecuta si no esta creando una pestania de grupo para que
	// no se actualice incorrectamente
	if (Documentos.Plantillas.creaPestania == -1) {
		var $section = Documentos.Plantillas.$workArea.closest(".ui-tabs-panel");
		// Quita los elementos basura para facilitar la conversion a
		// jrxml
		Documentos.Plantillas.setHTMLAttributes();
		$section.find("[id$='xmlPlantilla']").val(Documentos.CABECERA_HTML
				+ $('<div>').append(Documentos.Plantillas.$workArea.clone()).html() 
				+ Documentos.HEADER_HTML);
	} else {
		// Es la creacion de una pestania entonces se actualizan todas
		// las pestanias

		Documentos.Plantillas.getTabs()
				.each(function(index, section) { 
			if(index != PF("tabView").getActiveIndex()) {
				// Carga el contenido de la plantilla Llena las propiedades de
				// la plantilla
				Documentos.Plantillas.cargarPlantilla(index);
			}
		});
		// Carga el contenido de la plantilla Llena las propiedades de
		// la plantilla
		Documentos.Plantillas.cargarPlantilla(PF("tabView").getActiveIndex());
	}
	Documentos.Plantillas.creaPestania = -1;
};

/**
 * Guardar
 */
Documentos.PlantillasController.prototype.onSave = function() {
	console.debug("onSave");
	
	//Pone el valor del atributo de numero de grupos
	$(Documentos.Plantillas.mainWorkArea).attr(Documentos.Plantillas.numeroGrupoAttr, Documentos.Plantillas.numeroGrupos);
	Documentos.Plantillas.processHtml();
	Documentos.Plantillas.processHTMLGroups();
};

/**
 * Se ejecuta cundo se necesita ver el Preliminar de la plantilla
 */
Documentos.PlantillasController.prototype.onPreview = function() {
	console.debug("onPreview::Inicio");
	Documentos.Plantillas.processHtml();
	Documentos.Plantillas.processHTMLGroups();
	console.debug("onPreview::Fin");
};

/**
 * Coloca los atributos necesarios para la generacion y 
 * quita los elementos basura para facilitar la conversion a jrxml
 */
Documentos.PlantillasController.prototype.setHTMLAttributes = function() {
	// Recorremos cada seccion
	Documentos.Plantillas.$workArea.find(".band").each(function(index, section) {
		$(section).attr(Documentos.ALTO, $(section).height());
		var $bandContent = $(section).find(".band_content");
		var hasContent = false;		
		var recorrerItems = function(index, child) {
			//Cuando hay elementos anidados en otros divs
			if($(child).is("div") && !$(child).hasClass("cke_widget_wrapper")
					&& $(child).children("p,table,div,ul,ol,h1,h2,h3").length > 0) {
				$(child).children("p,table,div,ul,ol,h1,h2,h3").each(recorrerItems);
				return true;
			}
			
			// Agregamos los atributos necesarios
			$(child).attr(Documentos.X,$(child).position().left);
			$(child).attr(Documentos.Y,$(child).position().top);
			$(child).attr(Documentos.ANCHO_ITEM,$(child).width());
			$(child).attr(Documentos.ALTO_ITEM,$(child).height());
			$(child).attr(Documentos.TIPO,$(child).data("type"));
			//Si es tabla no colocar el interlineado
			if(!$(child).is("table")  && (child.style.lineHeight == "" || isNaN(child.style.lineHeight))) {
				var lineHeight = (parseFloat( $(child).css("line-height").replace(/px/g, '') )/12).toFixed(1);
				$(child).css("line-height",lineHeight);				
			}
			if (!$(child).hasClass(Documentos.ITEM_CLASS)) {
				$(child).addClass("ckeditorItem");
			}
			if ($(child).text() != null
					&& $(child).text() != "") {
				hasContent = true;
			}
//			console.log(child);
			// Busca las variables de tipo
			// imagen
			var $variables = $(child).find(
					".variable");
			
			$variables.each(function(indexVariable,	childVariable) {
				if ($(childVariable).is("img")) {
					// Agregamos los atributos necesarios
					$(childVariable).attr(Documentos.X, $(childVariable).parents("span").position().left);
					if($(child).closest(".band_content").attr("id").indexOf("cuerpo") != -1) {
						$(childVariable).attr(Documentos.Y, $(childVariable).parents("span").position().top - $(child).position().top);		
					} else {
						$(childVariable).attr(Documentos.Y, $(childVariable).parents("span").position().top);						
					}
					$(childVariable).attr(Documentos.ANCHO_ITEM, $(childVariable).width());
					$(childVariable).attr(Documentos.ALTO_ITEM, $(childVariable).height());
					$(childVariable).attr(Documentos.TIPO, "imagen");
					$(childVariable).addClass(Documentos.ITEM_CLASS);
					//Utilizado para imagenes variables
					if($(childVariable).attr("tipoVariable") == Documentos.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
						$(childVariable).attr("id_grupo", $(childVariable).attr("nombreVariable"));						
					}
					hasContent = true;
				} else if ($(childVariable).text() == "") {
					if ($(childVariable).find("img.variable").length > 0) {
						//Caso de imagenes locales
						$(childVariable).removeClass("variable");
					} else {
						//Caso de variables vacias
						$(childVariable).addClass("toRemove");						
					} 
				}
			});
			// Quita las variables con texto vacio para evitar errores en generacion
			$(".toRemove").remove();
			// Si es tabla, cada fila debe tener las propiedades
			var $filas = $(child).find("tr");
			$filas.each(function(indexFila, childFila) {
				// Agregamos los atributos necesarios
				$(childFila).attr(Documentos.X,$(childFila).position().left);
				if($(child).closest(".band_content").attr("id").indexOf("cuerpo") != -1) {
					$(childFila).attr(Documentos.Y,$(childFila).position().top	- $(child).position().top);	
				} else {
					$(childFila).attr(Documentos.Y,$(childFila).position().top);					
				}
				$(childFila).attr(Documentos.ANCHO_ITEM,$(childFila).width());
				$(childFila).attr(Documentos.ALTO_ITEM,$(childFila).height());
				$(childFila).attr(Documentos.TIPO,"fila");
			});
			
			// Si es tabla, cada columna debe tener las propiedades
			var $columnas = $(child).find("td,th,caption");
			$columnas.each(function(indexColumna, childColumna) {
				// Agregamos los atributos necesarios
				$(childColumna).attr(Documentos.X,$(childColumna).position().left);
				if($(child).closest(".band_content").attr("id").indexOf("cuerpo") != -1) {
					$(childColumna).attr(Documentos.Y,$(childColumna).position().top	- $(child).position().top);	
				} else {
					$(childColumna).attr(Documentos.Y,$(childColumna).position().top);					
				}
				$(childColumna).attr(Documentos.ANCHO_ITEM,$(childColumna).width());
				$(childColumna).attr(Documentos.ALTO_ITEM,$(childColumna).height());
				$(childColumna).attr(Documentos.TIPO,"columna");
				$(child).attr(Documentos.ALTO_ITEM,$(child).height() + 10);
				
//				//Verifica si existe un texto fuera de algun tag de parrafo
//				var contenido = $(childColumna).ignore("p").text();
//				if(contenido != undefined && contenido != "") {
//					$(childColumna).html($(childColumna).html().replace(contenido.trim(),""));
//					var p = $('<p></p>');
//					p.text(contenido);
//					p.prependTo( $(childColumna) );
//				}
				//Verifica items dentro de la columna
				$(childColumna).children("table,div").each(recorrerItems);
				hasContent = true;
			});
			// Si es grupo
			var $grupos = $(child).find(".grupo");
			$grupos.each(function(indexGrupo,childGrupo) {
				// Agregamos los atributos necesarios
//				$(childGrupo).attr(Documentos.X,$(child).position().left);
//				$(childGrupo).attr(Documentos.Y,$(child).position().top);
//				$(childGrupo).attr(Documentos.ANCHO_ITEM,$(child).width());
//				$(childGrupo).attr(Documentos.ALTO_ITEM,$(child).height());
				$(child).attr(Documentos.TIPO, "grupo");
				$(child).attr("id_grupo", $(childGrupo).attr("id"));
				if (!$(child).hasClass(Documentos.ITEM_CLASS)) {
					$(child).addClass("ckeditorItemGrupo");
				}
				hasContent = true;
			});

			// Busca las firmas
			// imagen
			var $firma = $(child).find(
					".firmamecanica");
			
			$firma.children().each(function(indexFirma,	childFirma) {
				// Agregamos los atributos necesarios
				$(childFirma).attr(Documentos.X, $(childFirma).parents(".cke_widget_wrapper").position().left);
				$(childFirma).attr(Documentos.Y, $(childFirma).position().top );
				$(childFirma).attr(Documentos.ANCHO_ITEM,	$(childFirma).width());
				$(childFirma).attr(Documentos.ALTO_ITEM, $(childFirma).height());
				if ($(child).is("div")) {
					$(child).addClass("ckeditorItemFirma");
				}
				hasContent = true;
			});
		};
		$bandContent.children().each(recorrerItems);
		if (!hasContent) {
			$bandContent.html("");
		}
		
	});

	// Quita los elementos basura para facilitar la conversion a jrxml
//	var $workAreaTemp = Documentos.Plantillas.$workArea.clone();
	Documentos.Plantillas.$workArea.find(".ui-resizable-handle").remove();

//	return $workAreaTemp;
};

/**
 * Ajusta los grupos para la generacion del documento de prueba y guardado
 */
Documentos.PlantillasController.prototype.processHTMLGroups = function() {
	console.debug("processHTMLGroups");
	// Recorre las pestanias para actualizar los xml para su posterior
	// generacion o guardado XXXXX
	$(Documentos.Plantillas.getTabs().get().reverse())
			.each(function(index, section) { 
		// verifica que exista el area de trabajo
		if($(section).find(".workArea").length > 0) {
			// Quita los elementos basura para facilitar la conversion a jrxml
			var $workAreaTemp = $(section).find(".workArea").clone();
			// Si es grupo
			var $grupos = $workAreaTemp.find(".grupo");
			$grupos.each(function(indexGrupo,childGrupo) {
				var tab = $(childGrupo).attr("tab");
				Documentos.Plantillas.getTabs()
						.each(function(index2, section2) { 
					if($(section2).find("#workArea-" + tab).length > 0) {
						var $grupoContent = $(childGrupo).find(".grupo-content");
						if($grupoContent.length == 0) {
							$grupoContent = $("<div class='grupo-content hidden'></div>");
							$(childGrupo).append($grupoContent);
						}
						$grupoContent.attr("html_grupo", 
								$(section2).find("[id$='xmlPlantilla']").val());
						return false;
					}
				});
			});
			//Si hay grupos vuelve a cambiar el html para poner el grupo
			if($grupos.length > 0) {
				$(section).find("[id$='xmlPlantilla']").val(Documentos.CABECERA_HTML + 
						$('<div>').append($workAreaTemp).html() + Documentos.HEADER_HTML);
			}
		}
	});
};

/**
 * Carga la plantilla de acuerdo a sus propiedades
 */
Documentos.PlantillasController.prototype.cargarPlantilla = function(index) {
	console.debug("cargarPlantilla");
	//Verifico que se haya enviado un index valido
	var $tabSection = Documentos.Plantillas.getTab(index);
	if ($tabSection.length > 0) {
		Documentos.Plantillas.$workArea = $tabSection.find(".workArea");
		var $xmlPlantilla = $tabSection.find("[id$='xmlPlantilla']");
		// if(Documentos.Plantillas.isEditVariable) {
		if ($xmlPlantilla.val() != "") {
			var $html = $($xmlPlantilla.val());

			//Busca el elemento que contiene el workarea
			$.each($html,
					function(indexHtml, elementoHtml) {
						if($(elementoHtml).hasClass(Documentos.Plantillas.workAreaClass)) {
							$html = $(elementoHtml);
						}
					});	
			
			// Traemos los atributos del html
			var attributes = $html.prop("attributes");

			// ponemos los atributos del html al workarea
			$.each(attributes,
					function() {
						if (this.name != "id") {
							Documentos.Plantillas.$workArea.attr(this.name,
									this.value);
						}
					});

			Documentos.Plantillas.$workArea.html($html.html());

			// Inicializa el acceso a las variables
//			Documentos.Plantillas.primeraVez = {};
		}

		//Pone los eventos sobre la seccion que se esta cargando
		if (index == PF("tabView").getActiveIndex() && Documentos.Plantillas.creaPestania == -1) {
			//Quitar encabezado y pide pagina para las pestanias de grupos
			if(index > 0) {
				var margenIzquierda = $(Documentos.Plantillas.mainWorkArea).attr("margenIzquierda");
				var margenDerecha = $(Documentos.Plantillas.mainWorkArea).attr("margenIzquierda");				
				Documentos.Plantillas.$workArea.find("#encabezado").addClass("hidden").css("height", "0cm");
				Documentos.Plantillas.$workArea.find("#piePagina").addClass("hidden").css("height", "0cm");
				Documentos.Plantillas.$workArea.attr(Documentos.INPUT_ENCABEZADO, "0").attr(Documentos.INPUT_PIE_PAGINA, "0")
								.attr("margenIzquierda", margenIzquierda).attr("margenDerecha", margenDerecha)
								.attr("margenInferior", "0").attr("margenSuperior", "0");
				Documentos.Plantillas.$workArea.find(".band_content").css("margin-left" , margenIzquierda + 'cm').css("margin-right" , margenDerecha + 'cm');
				Documentos.Plantillas.adjustSizeWorkArea(Documentos.Plantillas.$workArea.find("#piePagina"), 0);
				//Asigna ancho del grupo
				Documentos.Plantillas.$workArea
						.css("width", Documentos.Plantillas.$workArea
						.attr(Documentos.ANCHO) + "cm");
				Documentos.Plantillas.$workArea
						.css("overflow", "hide");
			}
			// Cambia el tamanio del ckeditor
			var anchoCke = parseFloat(Documentos.Plantillas.$workArea
					.attr(Documentos.ANCHO))
					- (parseFloat(Documentos.Plantillas.$workArea
							.attr("margenIzquierda")) + parseFloat(Documentos.Plantillas.$workArea
							.attr("margenDerecha")));

			// Genera de nuevo las instacias del ckeditor
			// Si es seccion se renueva el ckeditor
			CKEDITOR.disableAutoInline = true;
			var $secciones = Documentos.Plantillas.$workArea
					.find(".band_content");
			$secciones.each(function(indexSeccion, seccion) {
				//Selecciona la configuracion adecuada
				var config = Documentos.ckeditorConfigEncabezado;
				if(seccion.id.indexOf("cuerpo") != -1) {
					if(PF("tabView").getActiveIndex() > 0) {
						config = Documentos.ckeditorConfigGrupo;				
					} else {
						config = Documentos.ckeditorConfigCuerpo;				
					}		
				} else if(seccion.id.indexOf("piePagina") != -1) {
					config = Documentos.ckeditorConfigPiePagina;
				}
				if (typeof (CKEDITOR.instances[seccion.id]) == 'undefined') {
					CKEDITOR.inline(seccion.id, CKEDITOR.tools.extend({},
							config, true));
				} else {
					CKEDITOR.remove(CKEDITOR.instances[seccion.id]);
					$("#" + seccion.id).removeClass("cke_editable_inline cke_contents_ltr cke_editable cke_show_borders");
					$("#cke_" + seccion.id).remove();
					CKEDITOR.inline(seccion.id, CKEDITOR.tools.extend({},
							config, true));
					CKEDITOR.config.width = Documentos
							.convert_centimetros_pixeles(anchoCke);
				}
			});
			CKEDITOR.disableAutoInline = false;

			/***********************************************************************
			 * Eventos sobre las secciones del area de trabajo
			 **********************************************************************/
			// Secciones que pueden cambiar de tamanio
			Documentos.Plantillas.$workArea.find(".band").resizable({
				handles : "s",
				maxWidth : Documentos.Plantillas.$workArea.width(),
				stop : Documentos.Plantillas.onStopResizeSection
			});
			//Quita el html de los grupos para manejarlo en el 
			//momento de generar la vista preliminar o guardar
			$(".grupo-content").attr("html_grupo", "");
		}

		// Elemento al que se le deben agregar las propiedades
		var $parentId = $("#" + Documentos.Plantillas.properties.parentId);
		if (Documentos.Plantillas.properties.parentId == Documentos.Plantillas.workAreaClass) {
			$parentId = Documentos.Plantillas.$workArea;
		}
		// Llena valores de acuerdo a las propiedades
		Documentos.Plantillas.properties.properties.forEach(function(property) {
			property.fields.forEach(function(field) {
//				console.log(field);
				// valido si ya existia esta propiedad creada
				if ($parentId.attr(field.id) == undefined) {
					$parentId.attr(field.id, field.value);
				}
			});
		});
	}
};

/**
 * Retorna los elementos del contenido de las pestanias
 */
Documentos.PlantillasController.prototype.getTabs = function() {
	return $("#areaTrabajoForm\\:tabs").find("[role=tabpanel]");
};

/**
 * Retorna el elemento de la pestania solicitada
 */
Documentos.PlantillasController.prototype.getTab = function(index) {
	return $("#areaTrabajoForm\\:tabs").find("[role=tabpanel]").eq(index);
};

/**
 * Funcion que inicializa el controlador de rutas para utilizarlo en toda la
 * aplicacion
 */
$(function() {
	Documentos.Plantillas = new Documentos.PlantillasController();
	Documentos.Plantillas.init();
});
