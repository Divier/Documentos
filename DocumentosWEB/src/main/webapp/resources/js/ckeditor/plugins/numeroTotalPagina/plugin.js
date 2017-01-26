/**
 * Pluging de variables
 */

'use strict';

( function() {
	var isBrowserSupported = !CKEDITOR.env.ie || CKEDITOR.env.version > 8;

	CKEDITOR.plugins.add( 'numeroTotalPagina', {
		requires: 'widget',
		lang: 'es',

		init: function( editor ) {
			
	        editor.ui.addButton( 'numeroTotalPagina',
	        {
	            label: editor.lang.numeroTotalPagina.button,
	            command: 'numeroTotalPagina',
	            icon: this.path + 'icons/numeroTotalPagina.png'
	        } );
	        
	        editor.widgets.add( 'numeroTotalPagina', {

	            template:
	                '<strong class="numeroTotalPagina">$(NUMERO_TOTAL_PAGINA)' +
	                '</strong>',

	            editables: {
	            	variable: {
	                    selector: '.numeroTotalPagina',
	                    allowedContent: ''
	                }
	            },

	            allowedContent:
	                'strong(!numeroTotalPagina)',

	            requiredContent: 'strong(numeroTotalPagina)',

	            upcast: function( element ) {
	                return (element.name == 'strong') && element.hasClass( 'numeroTotalPagina' );
	            }
	            
	        } );
		}
	} );
} )();
