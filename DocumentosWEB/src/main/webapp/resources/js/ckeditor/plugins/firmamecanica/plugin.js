/**
 * Pluging de variables
 */

'use strict';

( function() {
	var isBrowserSupported = !CKEDITOR.env.ie || CKEDITOR.env.version > 8;

	CKEDITOR.plugins.add( 'firmamecanica', {
		requires: 'widget',
		lang: 'es',

		init: function( editor ) {
			
	        editor.ui.addButton( 'firmamecanica',
	        {
	            label: editor.lang.firmamecanica.button,
	            command: 'firmamecanica',
	            icon: this.path + 'icons/firmamecanica.png'
	        } );
	        editor.widgets.add( 'firmamecanica', {

	            template: '<div class="firmamecanica"><img src="' + Documentos.CONTEXTO + '/javax.faces.resource/firma.png.xhtml?ln=img"/><hr>' +
	            		'<p class="firmanombre" style="line-height:1.5"><span class="campofirma" contenteditable="false">-</span></p>' + 
	            		'<p class="firmacargo" style="line-height:1.5"><span class="campofirma" contenteditable="false">-</span></p></div>',

                allowedContent:
                    'p(!firmanombre) ;p(!firmacargo) ; div(!firmamecanica); hr; img; span[contenteditable](!campofirma);' ,

                requiredContent: 'div(firmamecanica)',
                
                editables: {
                    title: {
                        selector: '.firmanombre',
                        disallowedContent : 'img; table; ul; ol; p(!text-align)'
                    },
                    content: {
                        selector: '.firmacargo',
                        disallowedContent : 'img; table; ul; ol; p(!text-align)'
                    }
                },

	            upcast: function( element ) {
	            	return (element.name == 'div' && element.hasClass( 'firmamecanica' ));
	            },

	            data: function() {
	            	if(this.element.getAttribute("id")  == undefined) {
	            		console.debug("data:pluginFirma");
		        		// Genera un numero aleatorio que servira como id de la firma
		        		var idFirma = Math.random().toString(36).slice(2);
		            	this.element.setAttribute("id", idFirma);
		            	Documentos.Plantillas.idFirma = idFirma;
		            	//Ocultar editor
		            	$("." + this.editor.id).hide();
	            	}
	            }
	            
	        } );
		}
	
	} );
} )();
