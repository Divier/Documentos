/**
 * Pluging de variables
 */

'use strict';

( function() {
	var isBrowserSupported = !CKEDITOR.env.ie || CKEDITOR.env.version > 8;

	CKEDITOR.plugins.add( 'numeroPagina', {
		requires: 'widget',
		lang: 'es',

		init: function( editor ) {
			
	        editor.ui.addButton( 'numeroPagina',
	        {
	            label: editor.lang.numeroPagina.button,
	            command: 'numeroPagina',
	            icon: this.path + 'icons/numeroPagina.png'
	        } );
	        editor.widgets.add( 'numeroPagina', {

	            template:
	                '<strong class="numeroPagina">$(NUMERO_PAGINA)' +
	                '</strong>',

	            editables: {
	            	variable: {
	                    selector: '.numeroPagina',
	                    allowedContent: ''
	                }
	            },

	            allowedContent:
	                'strong(!numeroPagina)',

	            requiredContent: 'strong(numeroPagina)',

	            upcast: function( element ) {
	                return (element.name == 'strong') && element.hasClass( 'numeroPagina' );
	            }
	            
	        } );
		}
	} );
} )();
