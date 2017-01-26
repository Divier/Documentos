/**
 * Pluging de variables
 */

'use strict';

( function() {
	var isBrowserSupported = !CKEDITOR.env.ie || CKEDITOR.env.version > 8;

	CKEDITOR.plugins.add( 'editable', {
		lang: 'es',

//		  requires : [ 'styles', 'button' ],

		  init : function( editor )
		  {
		    // This "addButtonCommand" function isn't needed, but
		    // would be useful if you want to add multiple buttons
		    var addButtonCommand = function( buttonName, buttonLabel, commandName, styleDefiniton )
		    {
		      var style = new CKEDITOR.style( styleDefiniton );
		      editor.attachStyleStateChange( style, function( state )
		        {
		          !editor.readOnly && editor.getCommand( commandName ).setState( state );
		        });

		      editor.addCommand( commandName, new CKEDITOR.styleCommand( style ) );
		      editor.ui.addButton( buttonName,
		        {
		          label : buttonLabel,
		          command : commandName,
		          icon: CKEDITOR.plugins.getPath('editable') + 'icons/editable.png'
		        });
		    };

		    var config = editor.config,
		      lang = editor.lang;

		    // This version uses the language functionality, as used in "basicstyles"
		    // you'll need to add the label to each language definition file
//		    addButtonCommand( 'editable'   , lang.editable    , 'editable'   , config.coreStyles_editable );

		    // This version hard codes the label for the button by replacing `lang.editable` with 'editable'
		    addButtonCommand( 'editable'   , editor.lang.editable.button   , 'commandEditable'   , config.coreStyles_editable );
		  }
	} );

	// The basic configuration that you requested
	CKEDITOR.config.coreStyles_editable = { element : 'span', attributes : {'class': 'noEditable', 'title': Documentos.labelPlantilla.editablePlugin.title} };
} )();
