/**
 * Pluging de variables
 */

'use strict';

( function() {
	var isBrowserSupported = !CKEDITOR.env.ie || CKEDITOR.env.version > 8;

	CKEDITOR.plugins.add( 'variable', {
		requires: 'widget,dialog',
		lang: 'es',

		onLoad: function( editor ) {
			CKEDITOR.dialog.add( 'variable', this.path + 'dialogs/variable.js' );
		},

		init: function( editor ) {
			
	        editor.ui.addButton( 'variable',
	        {
	            label: editor.lang.variable.button,
	            command: 'variable',
	            icon: this.path + 'icons/variable.png'
	        } );
	        editor.widgets.add( 'variable', {

	            template:
	                '<strong class="variable">' +
	                '</strong>',

	            editables: {
	            	variable: {
	                    selector: '.variable',
	                    allowedContent: ''
	                }
	            },

	            allowedContent:
	                'strong(!variable); img(!variable)',

//	            requiredContent: 'strong(variable)',

	            dialog: 'variable',

	            upcast: function( element ) {
	                return (element.name == 'strong'|| element.name == 'img') && element.hasClass( 'variable' );
	            },

	            init: function() {
	                this.setData( 'idVariable', '' );
	            },

	            data: function() {
	            	if (Documentos.Plantillas != undefined) {
	                	var variable = Documentos.Plantillas.variablesJSON[this.data.idVariable];
	                	if(!Documentos.Plantillas.isEditVariable) {
							if ( variable == undefined || variable.nombreVariable == '' ) {
			                    this.element.setText( '' );	                	
			                }
			                else {
			                    if(variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_IMAGEN ||
			                    		variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
				                    this.element.setHtml( "" );
			                    	var $elemento = $("<img/>");
			                    	
			                    	if(variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_IMAGEN) {
				                    	$elemento.attr("src", variable.encodedImagen);
				                    } else {
				                    	$elemento.attr("src", Documentos.CONTEXTO + '/javax.faces.resource/imagen.png.xhtml?ln=img');	
				                    	$elemento.addClass("imagen-variable");			                    			
				                    }
			                    	
			                    	$elemento.attr("src", variable.encodedImagen);
			                    	$elemento.attr("alt", variable.nombreVariable);
			                    	$elemento.addClass("variable");
			                    	//Verifica si existe en el area de trabajo otra variable con el mismo nombre
			                    	var cantidad = $(".variable[nombreVariable='" + variable.nombreVariable + "']").length;
			                    	if($elemento.attr("id") != ("variableImg" + variable.idVariable) && cantidad > 0) {
			                    		for (var int = 1; true; int++) {
			    	                    	if($("#variableImg" + variable.idVariable + "_" + int ).length == 0) {
						                    	$elemento.attr("id", "variableImg" + variable.idVariable + "_" + int);
						                    	break;
			    	                    	}
										}
			                    	} else {
				                    	$elemento.attr("id", "variableImg" + variable.idVariable );	                		
			                    	}
			                    	$elemento.attr("variable", JSON.stringify(variable));
			                    	$elemento.attr("idVariable", variable.idVariable );    
			                    	$elemento.attr("nombreVariable", variable.nombreVariable ); 
			                    	$elemento.attr("title", variable.nombreVariable + ": " + variable.descripcionVariable );    
			                    	$elemento.attr("formatoVariable", variable.formatoVariable );
			                    	$elemento.attr("valorDefecto", variable.valorDefecto );	     
			                    	$elemento.attr(Documentos.TIPO_VARIABLE, variable.tipoVariableDTO.idTipoVariable );
			                    	$elemento.attr("nombreTipoVariable", variable.tipoVariableDTO.nombreTipoVariable );
			                    	$elemento.attr("nombreContextoVariable", variable.contextoAplicacionVariableDTO.nombreContextoAplicacion );
				                    //Si tiene proceso asociado se coloca el atributo
				                    if(variable.procesoDTO != undefined) {
				                    	$elemento.attr("proceso", variable.procesoDTO.nombreProceso );			                    	
				                    }
			                    	//Verifica que exista el formato de
			                    	if(variable.formatoVariable != undefined) {
				                    	var formato = variable.formatoVariable.split(",");
				                    	$elemento.css("height", formato[0] + "px");
				                    	$elemento.css("width", formato[1] + "px");	
				                    	$elemento.attr(Documentos.ALTO_IMAGEN, formato[0] );	
				                    	$elemento.attr(Documentos.ANCHO_IMAGEN, formato[1] );		                    		
			                    	} else {
				                    	$elemento.attr(Documentos.ALTO_IMAGEN, $elemento.height() );	
				                    	$elemento.attr(Documentos.ANCHO_IMAGEN, $elemento.width() );			                    		
			                    	}
			                    	if (variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
			                    		$elemento.attr("longitud", variable.longitudVariable );	 	
				            		}
			                    	this.element.appendHtml($elemento.get(0).outerHTML);
			                    	this.element.removeClass('variable');
			                    	$elemento.attr("data-cke-widget-data", JSON.stringify(this.data));
			                    	$elemento.attr("data-widget", "variable");
			                    	$elemento.attr("data-cke-widget-keep-attr", "0");
			                    	$elemento.addClass("cke_widget_element");
			                    	$(this.element.$).parent().css("vertical-align", "top");
			                    	$(this.element.$).css("vertical-align", "top");
			                    	
			                    } else {
				                    this.element.setHtml( "" );
			                    	//Verifica si existe en el area de trabajo otra variable con el mismo nombre
			                    	var cantidad = $(".variable[nombreVariable='" + variable.nombreVariable + "']").length;
			                    	if(this.element.getAttribute("id") != ("variable" + variable.idVariable) && cantidad > 0) {
			                    		for (var int = 1; int <= cantidad; int++) {
			    	                    	if($("#variable" + variable.idVariable + "_" + int ).length == 0) {
			    			                    this.element.setText( "$F{" + variable.nombreVariable + "_" + int + "}" );
			    	                    		this.element.setAttribute("id", "variable" + variable.idVariable + "_" + int ); 
							                    this.element.setAttribute("numero", int  );  
						                    	break;
			    	                    	}
										}
			                    	} else {
					                    this.element.setText( "$F{" + variable.nombreVariable + "}" );
			                    		this.element.setAttribute("id", "variable" + variable.idVariable );	               		
			                    	}	
			                    	this.element.addClass("variable");
			                    	this.element.setAttribute("variable", JSON.stringify(variable)); 
				                    this.element.setAttribute("idVariable", variable.idVariable );  
				                    this.element.setAttribute("nombreVariable", variable.nombreVariable ); 
				                    this.element.setAttribute("title", variable.descripcionVariable );   
				                    this.element.setAttribute("formatoVariable", variable.formatoVariable );
				                    this.element.setAttribute("valorDefecto", variable.valorDefecto );	     
				                    this.element.setAttribute(Documentos.TIPO_VARIABLE, variable.tipoVariableDTO.idTipoVariable );	
				                    this.element.setAttribute("nombreTipoVariable", variable.tipoVariableDTO.nombreTipoVariable );
				                    this.element.setAttribute("nombreContextoVariable", variable.contextoAplicacionVariableDTO.nombreContextoAplicacion );
				                    //Si tiene proceso asociado se coloca el atributo
				                    if(variable.procesoDTO != undefined) {
					                    this.element.setAttribute("proceso", variable.procesoDTO.nombreProceso );			                    	
				                    }

				            		if (variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_NUMERO) {
				            			//El formato puede llegar vacio
				            			if(variable.formatoVariable == "" || variable.formatoVariable == undefined) {
						                    this.element.setAttribute("decimales", "0" );				            				
				            			} else {
						                    this.element.setAttribute("decimales", variable.formatoVariable );			            				
				            			}	  
					                    //Valor por defecto de presentacion son numeros
					                    this.element.setAttribute("presentacionVariable", Documentos.labelPlantilla.presentacionVariableNumero );	
					                    this.element.setAttribute("formatoPresentacionVariable", Documentos.labelPlantilla.mayuscula );	
					                    this.element.setAttribute("separadorMiles", Documentos.labelPlantilla.no);	   
					                    this.element.setAttribute("formatoNumerosNegativos", Documentos.SIGNO_NEGATIVO);	   
				            		} else if (variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_TEXTO ||
				            				variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_URL) {
				            			//la longitud puede llegar vacia
				            			if(variable.formatoVariable == "" || variable.formatoVariable == undefined) {
						                    this.element.setAttribute("longitud", "0" );	 
				            			} else {
						                    this.element.setAttribute("longitud", variable.longitudVariable );	 		            				
				            			}		
				            		}
				            		//Se cambia el valor de acuerdo al formato de fecha 
				            		else if (variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_FECHA) {
				            			 this.element.setAttribute("valorDefectoFecha", moment(variable.valorDefecto, Documentos.FORMATO_FECHA, Documentos.LOCALE	)
				            					.format(moment().toMomentFormatString(variable.formatoVariable)));
				            		} else if(variable.tipoVariableDTO.idTipoVariable == Documentos.ID_TIPO_VARIABLE_IMAGEN_VARIABLE){
				            			this.element.setAttribute("tipoImagen", Documentos.labelPlantilla.general );
				            		}
			                    }
			                }			                
							Documentos.Plantillas.isEditVariable = true;            		
		            	}
	            		
	            	} 
	            }
	            
	        } );
		}
	} );
} )();
