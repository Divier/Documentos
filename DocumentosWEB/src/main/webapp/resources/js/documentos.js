//Funciones comunes a todo el proyecto

var Documentos = {};

Documentos.plantilla_id = null;
Documentos.event = null;
// valor del cm en px
Documentos.CENTIMETRO = 37.795276;
// Tamanios de las paginas en cm
Documentos.ALTO_PAGINA = 27.94;
Documentos.ALTO_PAGINA_CARTA = 27.94;
Documentos.ANCHO_PAGINA_CARTA = 21.59;
Documentos.ALTO_PAGINA_LEGAL = 35.56;
Documentos.ANCHO_PAGINA_LEGAL = 21.59;
Documentos.ALTO_PAGINA_A4 = 29.7;
Documentos.ANCHO_PAGINA_A4 = 21.0;
Documentos.ALTO_PAGINA_EJECUTIVO = 26.7;
Documentos.ANCHO_PAGINA_EJECUTIVO = 18.4;
// Correndonden a los tamanios de las paginas
Documentos.PERSONALIZADO = 1;
Documentos.CARTA = 2;
Documentos.LEGAL = 3;
Documentos.A4 = 4;
Documentos.EJECUTIVO = 5;
// Corresponden a los tipos de variable
Documentos.ID_TIPO_VARIABLE_NUMERO = 1;
Documentos.ID_TIPO_VARIABLE_URL = 2;
Documentos.ID_TIPO_VARIABLE_TEXTO = 3;
Documentos.ID_TIPO_VARIABLE_FECHA = 4;
Documentos.ID_TIPO_VARIABLE_IMAGEN = 5;
Documentos.ID_TIPO_VARIABLE_IMAGEN_VARIABLE = 6;
// Corresponden a los ordenes jerarquicos
Documentos.ID_ORDEN_ORGANIZACION = 1;
Documentos.ID_ORDEN_PROCESO = 2;
Documentos.ID_ORDEN_PLANTILLA = 3;
// Corresponden a los formatos de numeros negativos
Documentos.SIGNO_NEGATIVO = 1;
Documentos.COLOR_ROJO = 2;
Documentos.PARENTESIS = 3;
Documentos.COLOR_ROJO_PARENTESIS = 4;
// Fechas
Documentos.FORMATO_FECHA = "YYYY-MM-DD";
Documentos.LOCALE = "es";
// Atributos items plantillas
Documentos.ITEM_CLASS = "item";
Documentos.X = "x";
Documentos.Y = "y";
Documentos.ANCHO_ITEM = "ancho_item";
Documentos.ALTO_ITEM = "alto_item";
Documentos.TIPO = "tipo";
Documentos.TIPO_VARIABLE = "tipoVariable";
Documentos.ALTO_IMAGEN = "altoImagen";
Documentos.ANCHO_IMAGEN = "anchoImagen";
Documentos.FORMATO = "formato";
Documentos.ALTO = "alto";
Documentos.ANCHO = "ancho";
Documentos.INPUT_TITULO = "tituloInput";
Documentos.INPUT_ENCABEZADO = "encabezadoInput";
Documentos.INPUT_CUERPO = "cuerpoInput";
Documentos.INPUT_PIE_PAGINA = "piePaginaInput";
Documentos.INPUT_ULTIMO_PIE_PAGINA = "ultimoPiePaginaInput";
Documentos.INPUT_MARGEN_SUPERIOR = "margenSuperior";
Documentos.INPUT_MARGEN_INFERIOR = "margenInferior";
Documentos.INPUT_MARGEN_IZQUIERDA = "margenIzquierda";
Documentos.INPUT_MARGEN_DERECHA = "margenDerecha";
Documentos.CABECERA_HTML = "<html><head><title>Documento</title></head><body>";
Documentos.HEADER_HTML = "</body></html>";

Documentos.CONTEXTO = "/DocumentosWEB";

Documentos.myToolbar = [
                  [ 'Cut','Copy','Paste','-','Undo','Redo' ] ,
                  [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] ,	                  
                  [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] ,
                  [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','lineheight','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
                  [ 'Link','Unlink','Anchor' ],
                  [ 'Table','SpecialChar','PageBreak'] ,
                  [ 'Font','FontSize' ] ,
                  [ 'TextColor','BGColor' ],
                  ['CharCount'],
                  [ 'variable', 'grupo', 'firmamecanica','-','numeroPagina','numeroTotalPagina','-','editable' ]
              ];
Documentos.ckeditorConfigEncabezado = {
		removePlugins : 'flash,'
				+ 'forms,iframe,newpage,removeformat,line,horizontalrule,'
				+ 'smiley,stylescombo,templates,about,div,image,language,help,grupo,firmamecanica,pagebreak,numeroTotalPagina,numeroPagina,link,blockquote,format,showblocks,bidi',

		extraPlugins : 'variable,editable,tableresize,lineheight,magicline',
		font_defaultLabel : 'arial',
		fontSize_defaultLabel : '12',
		font_names : 'arial/Arial; calibri/Calibri; times/Times',
		toolbar : 'myToolbar',
		toolbar_myToolbar : this.myToolbar,
		// Simplify the dialog windows.
		removeDialogTabs : 'image:advanced;link:advanced;table:advanced;tabletools:advanced'
	};
Documentos.ckeditorConfigPiePagina = {
		removePlugins : 'flash,'
				+ 'forms,iframe,newpage,removeformat,line,horizontalrule,'
				+ 'smiley,stylescombo,templates,about,div,image,language,help,grupo,firmamecanica,pagebreak,link,blockquote,format,showblocks,bidi',

		extraPlugins : 'variable,numeroTotalPagina,numeroPagina,editable,tableresize,lineheight,magicline',
		font_defaultLabel : 'arial',
		fontSize_defaultLabel : '12',
		font_names : 'arial/Arial; calibri/Calibri; times/Times',
		toolbar : 'myToolbar',
		toolbar_myToolbar : this.myToolbar,
		// Simplify the dialog windows.
		removeDialogTabs : 'image:advanced;link:advanced;table:advanced;tabletools:advanced'
	};
Documentos.ckeditorConfigCuerpo = {
		removePlugins : 'flash,'
				+ 'forms,iframe,newpage,removeformat,line,horizontalrule,'
				+ 'smiley,stylescombo,templates,about,div,image,language,help,numeroTotalPagina,numeroPagina,link,blockquote,format,showblocks,bidi',

		extraPlugins : 'variable,grupo,firmamecanica,editable,tableresize,lineheight,magicline,charcount',
		font_defaultLabel : 'arial',
		fontSize_defaultLabel : '12',
		font_names : 'arial/Arial; calibri/Calibri; times/Times',
		toolbar : 'myToolbar',
		toolbar_myToolbar : this.myToolbar,
		// Simplify the dialog windows.
		removeDialogTabs : 'image:advanced;link:advanced;table:advanced;tabletools:advanced'	
	};
Documentos.ckeditorConfigGrupo = {
		removePlugins : 'flash,'
				+ 'forms,iframe,newpage,removeformat,line,horizontalrule,'
				+ 'smiley,stylescombo,templates,about,div,image,language,help,numeroTotalPagina,numeroPagina,firmamecanica,link,blockquote,format,showblocks,bidi',

		extraPlugins : 'variable,grupo,firmamecanica,editable,tableresize,lineheight,magicline,charcount',
		font_defaultLabel : 'arial',
		fontSize_defaultLabel : '12',
		font_names : 'arial/Arial; calibri/Calibri; times/Times',
		toolbar : 'myToolbar',
		toolbar_myToolbar : this.myToolbar,
		// Simplify the dialog windows.
		removeDialogTabs : 'image:advanced;link:advanced;table:advanced;tabletools:advanced'	
	};
//
var $menu = null;

$(function() {

	// CKEDITOR.disableAutoInline = true;
	//
	// $(document).ready(function() {
	// $('#encabezado').ckeditor(); // Use CKEDITOR.inline().
	// });
	// Documentos.header_init();

});

/*
 * Esto pone los circulitos en el menu y los texticos de ayuda
 */
// Documentos.header_init = function() {
// $(document).keydown(Documentos.keypress);
// };
//
//
// Documentos.keypress = function(e) {
// if (e.which === 90 && e.ctrlKey) {
// console.log('control + z');
// }
// };

String.prototype.replaceAt = function(index, character) {
	return this.substr(0, index) + character
			+ this.substr(index + character.length);
}

/**
 * Convierte pixeles a centimetros
 */
Documentos.convert_pixeles_centimetros = function(pixeles) {
	return +Math.round((pixeles / Documentos.CENTIMETRO) * 100) / 100;
};

/**
 * Convierte centimetros a pixeles
 */
Documentos.convert_centimetros_pixeles = function(centimetros) {
	return +Math.round((centimetros * Documentos.CENTIMETRO) * 100) / 100;
};

/**
 * Reemplazar en submenu
 */
Documentos.generaHtmlDinamico = function(options, selector, container) {

	// MUestra el submenu de acuerdo a la opcion seleccionada
	var template = $(selector).html().replace(/%7B/g, '{').replace(/%7D/g, '}');
	Mustache.parse(template); // optional, speeds up future uses
	var rendered = Mustache.render(template, options);
	$(container).html(rendered);
	$(container).removeClass("hidden");
};

/**
 * Reemplaza lo que se encuentre seleccionado
 */
Documentos.replaceSelection = function(html, selectInserted) {
	var sel, range, fragment;

	if (typeof window.getSelection != "undefined") {
		// IE 9 and other non-IE browsers
		sel = window.getSelection();

		// Test that the Selection object contains at least one Range
		if (sel.getRangeAt && sel.rangeCount) {
			// Get the first Range (only Firefox supports more than one)
			range = window.getSelection().getRangeAt(0);
			range.deleteContents();

			// Create a DocumentFragment to insert and populate it with HTML
			// Need to test for the existence of range.createContextualFragment
			// because it's non-standard and IE 9 does not support it
			if (range.createContextualFragment) {
				fragment = range.createContextualFragment(html);
			} else {
				// In IE 9 we need to use innerHTML of a temporary element
				var div = document.createElement("div"), child;
				div.innerHTML = html;
				fragment = document.createDocumentFragment();
				while ((child = div.firstChild)) {
					fragment.appendChild(child);
				}
			}
			var firstInsertedNode = fragment.firstChild;
			var lastInsertedNode = fragment.lastChild;
			range.insertNode(fragment);
			if (selectInserted) {
				if (firstInsertedNode) {
					range.setStartBefore(firstInsertedNode);
					range.setEndAfter(lastInsertedNode);
				}
				sel.removeAllRanges();
				sel.addRange(range);
			}
		}
	} else if (document.selection && document.selection.type != "Control") {
		// IE 8 and below
		range = document.selection.createRange();
		range.pasteHTML(html);
	}
}

$.fn.ignore = function(sel) {
	return this.clone().find(sel||">*").remove().end();
};