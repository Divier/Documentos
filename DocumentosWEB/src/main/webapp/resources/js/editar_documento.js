/**
 * Controlador encargado del manejo de las plantillas
 */
Documentos.DocumentosController = function() {
	this.disabledEventsMaintabs = false;
	this.$workArea = null;
	this.imageSrc = null;
	this.properties = null;
	this.workAreaClass = "workArea";
	this.mainWorkArea = "#workArea-0";
	this.numeroGrupoAttr = "numeroGrupo";
	this.creaPestania = -1;
	this.numeroGrupos = 1;
	this.maxHeightSection = 0.0;
	this.maxHeightItem = 0.0;
	this.maxWidthItem = 0.0;
	this.focusClass = "focus_item";
};

/**
 * Funcion que inicializa el mapa y eventos necesarios
 */
Documentos.DocumentosController.prototype.init = function() {

	/***************************************************************************
	 * Eventos de navegacion en la pagina
	 **************************************************************************/

	// Selecciona volver al formulario
	$("#areaTrabajoForm\\:volver").on('click', this.onBack);

	/***************************************************************************
	 * Eventos de carga de propiedades de la pagina de acuerdo a la
	 * configuracion
	 **************************************************************************/
	// Properties de la pagina por defecto
	this.properties = Documentos.propertiesPage;
	// Carga el contenido de la plantilla Llena las propiedades de la plantilla
	Documentos.DocumentosEd.cargarDocumento(0);

	/***************************************************************************
	 * Eventos del editor de texto
	 **************************************************************************/
	// The "instanceCreated" event is fired for every editor instance created.
	CKEDITOR.on('instanceCreated', this.onInstanceCreatedCKEDITOR);
	// The "instanceReady" event is fired for every editor instance ready.
	CKEDITOR.on('instanceReady', this.onInstanceReadyCKEDITOR);

	// Eventos cambio de datos en properties
	$('#areaTrabajoForm\\:propertiesDialog').on("spinchange", ".number,.number_integer",
			Documentos.DocumentosEd.changeProperty);
	$('#areaTrabajoForm\\:propertiesDialog')
			.on(
					"change",
					".text, .template_editor_inputs_TextAreaSkin-textarea, .template_editor_inputs_SelectSkin-select",
					Documentos.DocumentosEd.changeProperty);

	$("#areaTrabajoForm\\:propertiesButton").on("click",
			this.onClickPropertiesButton);
	
//	$("#areaTrabajoForm").on("click", ".cke_widget_wrapper",
//			function(e) { 
//				e.preventDefault();
//				$(".focus_item").removeClass("focus_item"); 
//				$(this).addClass("focus_item");
//			});

	$("#areaTrabajoForm").on("click", "img.item",
	function(e) { 
		console.debug("img.item.click");
		Documentos.DocumentosEd.deselectItems();
		var $element = $(this);
		if ($element.attr(Documentos.TIPO_VARIABLE) == Documentos.ID_TIPO_VARIABLE_IMAGEN || 
				$element.attr(Documentos.TIPO_VARIABLE) == Documentos.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
        	$element.attr(Documentos.ALTO_IMAGEN, $element.height() );	
        	$element.attr(Documentos.ANCHO_IMAGEN, $element.width() );	
			Documentos.DocumentosEd.showProperties($element.attr("id"), Documentos.propertiesEditImage);
		}
		return true;
	});
	
};

/**
 * Se ejecuta cuando se muestra una pestana de los grupos
 */
Documentos.DocumentosController.prototype.onShowGroupTab = function(event) {
	console.debug("onShowGroupTab");
	// Carga el contenido de la plantilla Llena las propiedades de la plantilla
	Documentos.DocumentosEd.cargarDocumento(PF("tabView").getActiveIndex());
	// Ajusta las propiedades de la pagina por defecto,
	Documentos.DocumentosEd.showProperties(Documentos.propertiesPage["parentId"], Documentos.propertiesPage);
};

/**
 * Se ejecuta cuando visualiza las propiedades
 */
Documentos.DocumentosController.prototype.onClickPropertiesButton = function(
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
 * Se ejecuta antes de mostrar el dialogo de propiedades
 */
Documentos.DocumentosController.prototype.beforeShowProperties = function() {
	console.debug("beforeShowProperties");
	$(this).find("#areaTrabajoForm\\:propertiesDialog .panel-formulario").html("");

	// Elemento al que se le deben agregar las propiedades
	var $parentId = $("#" + Documentos.DocumentosEd.properties.parentId);
	if (Documentos.DocumentosEd.properties.parentId == Documentos.DocumentosEd.workAreaClass) {
		$parentId = Documentos.DocumentosEd.$workArea;
	}

	// Recorrer properties y agregar valor o valor por defecto

	// Llena valores de acuerdo a las propiedades
	Documentos.DocumentosEd.properties.properties.forEach(function(property) {
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
	Documentos.generaHtmlDinamico(Documentos.DocumentosEd.properties,
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
			        	&& ui.value > Documentos.DocumentosEd.maxHeightSection ) {
			          $( this ).spinner( "value", Documentos.DocumentosEd.maxHeightSection.toFixed(1) );
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
		        	&& ui.value > Documentos.convert_centimetros_pixeles(Documentos.DocumentosEd.maxHeightItem) ) {
		          $( this ).spinner( "value", Documentos.convert_centimetros_pixeles(Documentos.DocumentosEd.maxHeightItem).toFixed(0) );
		          return false;
		        } else if ((event.target.id == Documentos.ANCHO_IMAGEN )
			        	&& ui.value > Documentos.convert_centimetros_pixeles(Documentos.DocumentosEd.maxWidthItem) ) {
			          $( this ).spinner( "value", Documentos.convert_centimetros_pixeles(Documentos.DocumentosEd.maxWidthItem).toFixed(0) );
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
	if(Documentos.DocumentosEd.$workArea.attr("tab") != "0" && 
			Documentos.DocumentosEd.properties.parentId == Documentos.DocumentosEd.workAreaClass) {
		$('#areaTrabajoForm\\:propertiesDialog .panel-formulario').find(
		"input,select").attr("disabled", true);
		$('#areaTrabajoForm\\:propertiesDialog .panel-formulario').find(
				'.ui-spinner a.ui-spinner-button').css('display','none');
	}
};

/**
 * Evento al crear una instancia de ckeditor
 */
Documentos.DocumentosController.prototype.onInstanceCreatedCKEDITOR = function(
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
		editor.config.removePlugins = configCK.removePlugins + ",grupo";		
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
Documentos.DocumentosController.prototype.onInstanceReadyCKEDITOR = function(
		event) {
	console.debug("onInstanceReadyCKEDITOR");
	var editor = event.editor;

	// Cambia el tamanio del ckeditor
	 var anchoCke = parseFloat(Documentos.DocumentosEd.$workArea.attr(Documentos.ANCHO)) -
	 				(parseFloat(Documentos.DocumentosEd.$workArea.attr("margenIzquierda")) +
	 				parseFloat(Documentos.DocumentosEd.$workArea.attr("margenDerecha")));
	 $(".cke").css("width", anchoCke + "cm");
	
	// Hace que todas las instancias se puedan editar
	editor.setReadOnly(false);

	//Convirtiendo a no editable
	$(".noEditable").attr("contenteditable", "false");
	
	//Quitando imagen por defecto de firmas
	$(".firmamecanica img").addClass("firmadoc");
	
//	editor.getCommand('variable').disable();
//	editor.getCommand('commandEditable').disable();
//	editor.getCommand('firmamecanica').disable();
	

	editor.on('focus', function (ev) {
		ev.editor.getCommand('variable').disable();
		ev.editor.getCommand('firmamecanica').disable();
		ev.editor.getCommand('commandEditable').disable();
	});
	
	// Maneja el evento de cambio en el editor
	editor.on('change', Documentos.DocumentosEd.onChangeCKEditor);
	
	editor.on('paste', function (ev) {
	        ev.data.dataValue = ev.data.dataValue.replace(/<br( [^>]*)?>/gi, '</p><p>');
	});
	editor.document.on('keydown', function(e) {
		if(CKEDITOR.currentInstance != undefined) {
			var selection = CKEDITOR.currentInstance.getSelection();
	        var ranges = selection.getRanges();
	        if (ranges != null) {
	            var range = ranges[0];
	            if (range != null) {
	                range = range.clone();

	                var startNode = range.startContainer;
	                var endNode = range.endContainer;

	                var cancelEvent = false;

	                var pos = startNode.getPosition(endNode);
	                switch (pos) {
	                    case CKEDITOR.POSITION_IDENTICAL: {
	                        console.log("Key = " + e.data.$.keyCode);
	                        switch (e.data.$.keyCode) {
	                            case 8: { //BACKSPACE
	                            	if ($(".cke_widget_wrapper.cke_widget_inline.cke_widget_selected.cke_widget_focused").length > 0) {
	                                    cancelEvent = true;                            		
	                            	}
	                                break;
	                            }
	                            case 46: { //DEL
	                            	if ($(".cke_widget_wrapper.cke_widget_inline.cke_widget_selected.cke_widget_focused").length > 0) {
	                                    cancelEvent = true;                            		
	                            	}
	                                break;
	                            }
	                            default: {
	                                return true;
	                            }
	                        }
	                        break;
	                    }
	                    case CKEDITOR.POSITION_DISCONNECTED: {
	                        console.log("*** Disconnected nodes?!? (This should not be possible in a selection?!?) - rejected!!!");
	                        cancelEvent = true;
	                        break;
	                    }
	                    case CKEDITOR.POSITION_PRECEDING: {
	                        var temp = startNode.getParent();
	                        while (temp != null && temp.$ != endNode.getParent().$) {
	                            if (Documentos.DocumentosEd.isReadOnlyTree(temp)) {
	                                cancelEvent = true;
	                                break;
	                            }
	                            temp = temp.getNext();
	                        }
	                        break;
	                    }
	                    default : {
	                        console.log("*** Not handled case???" + pos);
	                        break;
	                    }
	                }

	                if (cancelEvent) {
	                    console.log("*** Canceling event...");

	                    //Cancel the event
	                    e.cancelBubble = true;
	                    e.returnValue = false;
	                    e.cancel();
	                    e.stop();
	                    return false;
	                }
	            }
	        }
		}
        
        return true;
    } );
};

/**
 * Evento al cambiar el texto de las secciones editables
 */
Documentos.DocumentosController.prototype.onChangeCKEditor = function(event) {
	console.debug("onChangeCKEditor");
	var editor = event.editor;
	
	//Ajusta el tamano de 
	Documentos.DocumentosEd.adjustSizeWorkArea($("#" + editor.name).parent(), 
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
	
	//busca el las pesta�a del grupo para deshabilitar las que no estan
	//y habilitar las pestanias que estan
	Documentos.DocumentosEd.getTabs()
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
 * Evento al terminar de cambiar el tamanio de una seccion
 */
Documentos.DocumentosController.prototype.onStopResizeSection = function(event,
		ui) {
	console.debug("onStopResizeSection");
	Documentos.DocumentosEd.deselectItems();
	//Ajusta el tamanio de las secciones
	Documentos.DocumentosEd.adjustSizeWorkArea(ui.element, ui.size.height);
};

/**
 * Evento al terminar de cambiar el tamanio de una seccion
 */
Documentos.DocumentosController.prototype.adjustSizeWorkArea = function(section,
		height) {
	console.debug("adjustSizeWorkArea");
	var suma_altura = 0;
	//Suma las alturas de las secciones
	$.each(Documentos.DocumentosEd.$workArea.find(".band"), function(key, value) {
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
	Documentos.DocumentosEd.$workArea.animate({
		height : suma_altura + 'px'
	}, 'slow');
	//Cambia el valor de la propiedad
	$("#" + section.attr("id") + "Input").val(
			Documentos.convert_pixeles_centimetros(section.height()));
	// Elemento al que se le deben agregar las propiedades
	var $parentId = $("#" + Documentos.DocumentosEd.properties.parentId);
	if (Documentos.DocumentosEd.properties.parentId == Documentos.DocumentosEd.workAreaClass) {
		$parentId = Documentos.DocumentosEd.$workArea;
	}
	// Actualiza el valor en las propiedades
	$parentId.attr(section.attr("id") + "Input", Documentos
			.convert_pixeles_centimetros(section.height()));
	// Actualiza el valor en la propiedad del alto total
	$parentId.attr("altoTotal", Documentos
			.convert_pixeles_centimetros(suma_altura));


	//Calcula el maximo tamano de las secciones e items
	Documentos.DocumentosEd.setMaxSizeItems();
	
	// ajusta la altura del contenido
//	var height = $("#areaTrabajoForm").height();
//	$(".contenido").eq(0).animate({
//		height : height + "px"
//	}, 'slow');

};

/**
 * Evento al continuar muestra espacio de trabajo
 */
Documentos.DocumentosController.prototype.onContinue = function() {
	console.debug("onContinue");

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
	Documentos.DocumentosEd.cargarDocumento(PF("tabView").getActiveIndex());
	//Inicializa el numero de grupos
	Documentos.DocumentosEd.numeroGrupos = PF("tabView").getLength();
	if($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.DocumentosEd.numeroGrupoAttr) != undefined && 
			parseInt($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.DocumentosEd.numeroGrupoAttr)) >= PF("tabView").getLength()) {
		Documentos.DocumentosEd.numeroGrupos = $(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.DocumentosEd.numeroGrupoAttr);		
	} else {
		$(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.DocumentosEd.numeroGrupoAttr, Documentos.DocumentosEd.numeroGrupos);
	}

	//Calcula el maximo tamano de las secciones de encabezado y pie de pagina
	Documentos.DocumentosEd.maxHeightSection = parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.ALTO))/3;
	
	//Calcula el maximo tamano de las secciones e items
	Documentos.DocumentosEd.setMaxSizeItems();
	
	//cambia el maximo tama�o de las secciones de encabezado y pie de pagina
	$("#encabezado .band_content, #piePagina .band_content, #encabezado, #piePagina")
			.css("max-height" , Documentos.DocumentosEd.maxHeightSection + "cm");
	
	//Ajusta el tama�o del area de trabajo
	Documentos.DocumentosEd.adjustSizeWorkArea($("#piePagina").parent(), 
			$("#piePagina").parent().height());
};

/**
 * AJusta los valores maximos para las imagenes
 */
Documentos.DocumentosController.prototype.setMaxSizeItems = function() {
	//Calcula el maximo alto de los items
	Documentos.DocumentosEd.maxHeightItem = parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.ALTO))
	- (parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.INPUT_ENCABEZADO)) + 
			parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.INPUT_PIE_PAGINA)) + 
			parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.INPUT_MARGEN_SUPERIOR)) + 
			parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.INPUT_MARGEN_INFERIOR)) + 
			Documentos.convert_pixeles_centimetros(30));
	$(Documentos.DocumentosEd.mainWorkArea + " img").css("max-height" , Documentos.DocumentosEd.maxHeightItem + "cm");
	//Calcula el maximo ancho de los items
	Documentos.DocumentosEd.maxWidthItem = parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.ANCHO))
	- ( parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.INPUT_MARGEN_DERECHA)) + 
			parseFloat($(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.INPUT_MARGEN_IZQUIERDA)));
};

/**
 * Vuelve a las propiedades iniciales de la plantilla
 */
Documentos.DocumentosController.prototype.onBack = function(e) {
	console.debug("onBack");
//	e.preventDefault();
	Documentos.DocumentosEd.processHtml();
	$('#cuadro').animate({
		left : '0'
	}, 'slow');
	$('#contenedor').css('overflow', 'hidden');
	$('#contenedor').animate({
		scrollTop : 0
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
Documentos.DocumentosController.prototype.changeProperty = function(e) {
	console.debug("changeProperty");
	e.preventDefault();
	// Elemento al que se le deben agregar las propiedades
	var $parentId = $("#" + Documentos.DocumentosEd.properties.parentId);
	if (Documentos.DocumentosEd.properties.parentId == Documentos.DocumentosEd.workAreaClass) {
		$parentId = Documentos.DocumentosEd.$workArea;
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
			Documentos.DocumentosEd.$workArea.animate({
				width : $("#ancho").val() + 'cm'
			}, 'slow');
		}
		if(this.id == Documentos.ALTO) {
			//Calcula el maximo tama�o de las secciones de encabezado y pie de pagina
			Documentos.DocumentosEd.maxHeightSection = (this.value / 3);
			$("#encabezado .band_content, #piePagina .band_content, #encabezado, #piePagina")
					.css("max-height" , Documentos.DocumentosEd.maxHeightSection + "cm");

			//Calcula el maximo tamano de las secciones e items
			Documentos.DocumentosEd.setMaxSizeItems();
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
			Documentos.DocumentosEd.maxHeightSection = (alto / 3);

			//Calcula el maximo tamano de las secciones e items
			Documentos.DocumentosEd.setMaxSizeItems();
		}
		
		//cambia el maximo tama�o de las secciones de encabezado y pie de pagina
		$("#encabezado .band_content, #piePagina .band_content, #encabezado, #piePagina")
				.css("max-height" , Documentos.DocumentosEd.maxHeightSection + "cm");
		// Asigna el valor del ancho total del area de trabajo
		Documentos.DocumentosEd.$workArea.animate({
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
		Documentos.DocumentosEd.$workArea.find(id).animate(
				{
					height : $(this).val() + 'cm'
				},
				'slow',
				function() {
					Documentos.DocumentosEd.adjustSizeWorkArea(Documentos.DocumentosEd.$workArea.find(id), 
							Documentos.DocumentosEd.$workArea.find(id).height());
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
		Documentos.DocumentosEd.$workArea.find(".band_content").animate({
			"margin-left" : $(this).val() + 'cm'
		}, 'slow');
	} else if (this.id == Documentos.INPUT_MARGEN_DERECHA) {
		// Modifica el area de trabajo segun el cambio realizado
		Documentos.DocumentosEd.$workArea.find(".band_content").animate({
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
		Documentos.DocumentosEd.adjustSizeWorkArea(Documentos.DocumentosEd.$workArea.find("#cuerpo"), 
				Documentos.DocumentosEd.$workArea.find("#cuerpo").height());
	}

	// Cambia el tamaño del ckeditor
	var anchoCke = parseFloat(Documentos.DocumentosEd.$workArea.attr(Documentos.ANCHO))
			- (parseFloat(Documentos.DocumentosEd.$workArea
					.attr("margenIzquierda")) + parseFloat(Documentos.DocumentosEd.$workArea
					.attr("margenDerecha")));
	$(".cke").css("width", anchoCke + "cm");
};

/**
 * Funcion de deseleccionar un item
 */
Documentos.DocumentosController.prototype.deselectItems = function() {
	console.debug("deselectItems");
	// e.preventDefault();
	// e.stopPropagation();
//	if ($("." + Documentos.DocumentosEd.focusClass).length > 0) {
//		$("." + Documentos.DocumentosEd.focusClass).removeClass(Documentos.DocumentosEd.focusClass);
//		// Ajusta las propiedades de la pagina por defecto,
//		Documentos.DocumentosEd.showProperties(Documentos.propertiesPage["parentId"], 
//				Documentos.propertiesPage);
//	}
	//Quita la seleccion
	$("." + Documentos.DocumentosEd.focusClass).removeClass(Documentos.DocumentosEd.focusClass);
	// Ajusta las propiedades de la pagina por defecto,
	Documentos.DocumentosEd.showProperties(Documentos.propertiesPage["parentId"], 
			Documentos.propertiesPage);
};

/**
 * Funcion de mostrar las propiedades
 */
Documentos.DocumentosController.prototype.showProperties = function(parentId, properties) {
	console.debug("showProperties " + parentId);
	// Si son las propiedades de
	// Ajusta las propiedades que se deben mostrar
	Documentos.DocumentosEd.properties = properties;
	Documentos.DocumentosEd.properties["parentId"] = parentId;
	// Se oculta y muestra para actualizar la lista de propiedades
//	PF('propertiesDialog').hide();
//	PF('propertiesDialog').show();
	Documentos.DocumentosEd.beforeShowProperties();

};

/**
 * Procesar html para ponerlo en el campo xmlDocumento
 */
Documentos.DocumentosController.prototype.processHtml = function() {
	console.debug("processHtml");
	// Solo lo ejecuta si no esta creando una pestania de grupo para que
	// no se actualice incorrectamente
	if (Documentos.DocumentosEd.creaPestania == -1) {
		var $section = Documentos.DocumentosEd.$workArea.closest(".ui-tabs-panel");
		// Quita los elementos basura para facilitar la conversion a
		// jrxml
		Documentos.DocumentosEd.setHTMLAttributes();
		$section.find("[id$='xmlDocumento']").val(Documentos.CABECERA_HTML
				+ $('<div>').append(Documentos.DocumentosEd.$workArea.clone()).html() 
				+ Documentos.HEADER_HTML);
	} else {
		// Es la creacion de una pestania entonces se actualizan todas
		// las pestanias

		Documentos.DocumentosEd.getTabs()
				.each(function(index, section) { 
			if(index != PF("tabView").getActiveIndex()) {
				// Carga el contenido de la plantilla Llena las propiedades de
				// la plantilla
				Documentos.DocumentosEd.cargarDocumento(index);
			}
		});
		// Carga el contenido de la plantilla Llena las propiedades de
		// la plantilla
		Documentos.DocumentosEd.cargarDocumento(PF("tabView").getActiveIndex());
	}
	Documentos.DocumentosEd.creaPestania = -1;
};

/**
 * Guardar
 */
Documentos.DocumentosController.prototype.onSave = function() {
	console.debug("onSave");
	
	//Pone el valor del atributo de numero de grupos
	$(Documentos.DocumentosEd.mainWorkArea).attr(Documentos.DocumentosEd.numeroGrupoAttr, Documentos.DocumentosEd.numeroGrupos);
	Documentos.DocumentosEd.processHtml();
	Documentos.DocumentosEd.processHTMLGroups();
};

/**
 * Se ejecuta cundo se necesita ver el Preliminar de la plantilla
 */
Documentos.DocumentosController.prototype.onPreview = function() {
	console.debug("onPreview::Inicio");
	Documentos.DocumentosEd.processHtml();
	Documentos.DocumentosEd.processHTMLGroups();
	console.debug("onPreview::Fin");
};

/**
 * Coloca los atributos necesarios para la generacion y 
 * quita los elementos basura para facilitar la conversion a jrxml
 */
Documentos.DocumentosController.prototype.setHTMLAttributes = function() {
	// Recorremos cada seccion
	Documentos.DocumentosEd.$workArea.find(".band").each(function(index, section) {
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
			if(!$(child).is("table") ) {
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
						$(childVariable).attr("id_grupo", "");						
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
				$(child).addClass("ckeditorItemFirma");
				hasContent = true;
			});
		};
		$bandContent.children().each(recorrerItems);
		if (!hasContent) {
			$bandContent.html("");
		}
		
	});

	// Quita los elementos basura para facilitar la conversion a jrxml
//	var $workAreaTemp = Documentos.DocumentosEd.$workArea.clone();
	Documentos.DocumentosEd.$workArea.find(".ui-resizable-handle").remove();

//	return $workAreaTemp;
};

/**
 * Ajusta los grupos para la generacion del documento de prueba y guardado
 */
Documentos.DocumentosController.prototype.processHTMLGroups = function() {
	console.debug("processHTMLGroups");
	// Recorre las pestanias para actualizar los xml para su posterior
	// generacion o guardado XXXXX
	$(Documentos.DocumentosEd.getTabs().get().reverse())
			.each(function(index, section) { 
		// verifica que exista el area de trabajo
		if($(section).find(".workArea").length > 0) {
			// Quita los elementos basura para facilitar la conversion a jrxml
			var $workAreaTemp = $(section).find(".workArea").clone();
			// Si es grupo
			var $grupos = $workAreaTemp.find(".grupo");
			$grupos.each(function(indexGrupo,childGrupo) {
				var tab = $(childGrupo).attr("tab");
				Documentos.DocumentosEd.getTabs()
						.each(function(index2, section2) { 
					if($(section2).find("#workArea-" + tab).length > 0) {
						var $grupoContent = $(childGrupo).find(".grupo-content");
						if($grupoContent.length == 0) {
							$grupoContent = $("<div class='grupo-content hidden'></div>");
							$(childGrupo).append($grupoContent);
						}
						$grupoContent.attr("html_grupo", 
								$(section2).find("[id$='xmlDocumento']").val());
						return false;
					}
				});
			});
			//Si hay grupos vuelve a cambiar el html para poner el grupo
			if($grupos.length > 0) {
				$(section).find("[id$='xmlDocumento']").val(Documentos.CABECERA_HTML + 
						$('<div>').append($workAreaTemp).html() + Documentos.HEADER_HTML);
			}
		}
	});
};

/**
 * Carga la plantilla de acuerdo a sus propiedades
 */
Documentos.DocumentosController.prototype.cargarDocumento = function(index) {
	console.debug("cargarDocumento");
	//Verifico que se haya enviado un index valido
	var $tabSection = Documentos.DocumentosEd.getTab(index);
	if ($tabSection.length > 0) {
		Documentos.DocumentosEd.$workArea = $tabSection.find(".workArea");
		var $xmlDocumento = $tabSection.find("[id$='xmlDocumento']");
		// if(Documentos.DocumentosEd.isEditVariable) {
		if ($xmlDocumento.val() != "") {
			var $html = $($xmlDocumento.val());

			//Busca el elemento que contiene el workarea
			$.each($html,
					function(indexHtml, elementoHtml) {
						if($(elementoHtml).hasClass(Documentos.DocumentosEd.workAreaClass)) {
							$html = $(elementoHtml);
						}
					});	
			
			// Traemos los atributos del html
			var attributes = $html.prop("attributes");

			// ponemos los atributos del html al workarea
			$.each(attributes,
					function() {
						if (this.name != "id") {
							Documentos.DocumentosEd.$workArea.attr(this.name,
									this.value);
						}
					});

			Documentos.DocumentosEd.$workArea.html($html.html());

			// Inicializa el acceso a las variables
//			Documentos.DocumentosEd.primeraVez = {};
		}

		//Pone los eventos sobre la seccion que se esta cargando
		if (index == PF("tabView").getActiveIndex() && Documentos.DocumentosEd.creaPestania == -1) {
			//Quitar encabezado y pide pagina para las pestanias de grupos
			if(index > 0) {
				var margenIzquierda = $(Documentos.DocumentosEd.mainWorkArea).attr("margenIzquierda");
				var margenDerecha = $(Documentos.DocumentosEd.mainWorkArea).attr("margenIzquierda");				
				Documentos.DocumentosEd.$workArea.find("#encabezado").addClass("hidden").css("height", "0cm");
				Documentos.DocumentosEd.$workArea.find("#piePagina").addClass("hidden").css("height", "0cm");
				Documentos.DocumentosEd.$workArea.attr(Documentos.INPUT_ENCABEZADO, "0").attr(Documentos.INPUT_PIE_PAGINA, "0")
								.attr("margenIzquierda", margenIzquierda).attr("margenDerecha", margenDerecha)
								.attr("margenInferior", "0").attr("margenSuperior", "0");
				Documentos.DocumentosEd.$workArea.find(".band_content").css("margin-left" , margenIzquierda + 'cm').css("margin-right" , margenDerecha + 'cm');
				Documentos.DocumentosEd.adjustSizeWorkArea(Documentos.DocumentosEd.$workArea.find("#piePagina"), 0);
			}
			// Cambia el tamanio del ckeditor
			var anchoCke = parseFloat(Documentos.DocumentosEd.$workArea
					.attr(Documentos.ANCHO))
					- (parseFloat(Documentos.DocumentosEd.$workArea
							.attr("margenIzquierda")) + parseFloat(Documentos.DocumentosEd.$workArea
							.attr("margenDerecha")));

			// Genera de nuevo las instacias del ckeditor
			// Si es seccion se renueva el ckeditor
			CKEDITOR.disableAutoInline = true;
			var $secciones = Documentos.DocumentosEd.$workArea
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
				config.removePlugins = config.removePlugins + ",grupo";	
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
			Documentos.DocumentosEd.$workArea.find(".band").resizable({
				handles : "s",
				maxWidth : Documentos.DocumentosEd.$workArea.width(),
				stop : Documentos.DocumentosEd.onStopResizeSection
			});
			//Quita el html de los grupos para manejarlo en el 
			//momento de generar la vista preliminar o guardar
			$(".grupo-content").attr("html_grupo", "");
		}

		// Elemento al que se le deben agregar las propiedades
		var $parentId = $("#" + Documentos.DocumentosEd.properties.parentId);
		if (Documentos.DocumentosEd.properties.parentId == Documentos.DocumentosEd.workAreaClass) {
			$parentId = Documentos.DocumentosEd.$workArea;
		}
		// Llena valores de acuerdo a las propiedades
		Documentos.DocumentosEd.properties.properties.forEach(function(property) {
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
Documentos.DocumentosController.prototype.getTabs = function() {
	return $("#areaTrabajoForm\\:tabs").find("[role=tabpanel]");
};

/**
 * Retorna el elemento de la pestania solicitada
 */
Documentos.DocumentosController.prototype.getTab = function(index) {
	return $("#areaTrabajoForm\\:tabs").find("[role=tabpanel]").eq(index);
};


Documentos.DocumentosController.prototype.isReadOnlyTree = function (node) {
    if (node.type == CKEDITOR.NODE_ELEMENT) {
        var childs = node.$.childNodes;
        for ( var i=0; i < childs.length; i++ )
            if (Documentos.DocumentosEd.isReadOnlyTree(new CKEDITOR.dom.node(childs[ i ]))) {
                return true;
            }
        return false;
    } else if (node.type == CKEDITOR.NODE_TEXT) {
        return node.isReadOnly();
    } else {
        return false;
    }
}

/**
 * Funcion que inicializa el controlador de rutas para utilizarlo en toda la
 * aplicacion
 */
$(function() {
	Documentos.DocumentosEd = new Documentos.DocumentosController();
	Documentos.DocumentosEd.init();
});
