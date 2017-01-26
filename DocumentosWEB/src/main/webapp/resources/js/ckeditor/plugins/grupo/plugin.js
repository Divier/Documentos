/**
 * Pluging de variables
 */

'use strict';

( function() {
	var isBrowserSupported = !CKEDITOR.env.ie || CKEDITOR.env.version > 8;

	CKEDITOR.plugins.add( 'grupo', {
		requires: 'widget',
		lang: 'es',

		init: function( editor ) {
			
	        editor.ui.addButton( 'grupo',
	        {
	            label: editor.lang.grupo.button,
	            command: 'grupo',
	            icon: this.path + 'icons/grupo.png'
	        } );
	        editor.widgets.add( 'grupo', {

	            template: '<div class="grupo">' +
                '<div class="grupo-content">GRUPO</div>' +
                '</div>',

                allowedContent:
                    'div(!grupo); div(!grupo-content);',
                    
//                editables: {
//                    content: {
//                        selector: '.grupo-content'
//                    }
//                },

                requiredContent: 'div(grupo)',

	            upcast: function( element ) {
	            	return element.name == 'div' && element.hasClass( 'grupo' );
	            },

	            init: function() {
	                this.setData( 'idGrupo', '' );
	                this.setData( 'indexGrupo', '' );
	            }
	            ,

	            data: function() {
	            	if(this.element.getAttribute("id")  == undefined) {
	            		console.debug("data:pluginGrupo");
		            	//Ocultar editor
		            	$("." + this.editor.id).hide();
		            	//Colocar id correcto para identificarlo en cualquier lado
		            	this.element.setAttribute("id", "grupo_" + Documentos.Plantillas.numeroGrupos);	
		            	this.element.setAttribute("tab", Documentos.Plantillas.numeroGrupos);	  
		            	this.element.setAttribute("title", "grupo_" + Documentos.Plantillas.numeroGrupos);	 
	            	}
	            }
	            
	        } );
		}	
	} );
} )();
