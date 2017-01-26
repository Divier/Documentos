package co.com.datatools.documentos.constantes;

public class ConstantesDocumentos {

    // Corresponden a los tipos de archivo que se van a tener en cuenta
    // para guardar documentos en el repositorio de alfresco
    public final static String TIPO_ARCHIVO_DOCUMENTO_PDF = "application/pdf";

    public final static String NOMBRE_ARCHIVO_PDF_COMBINADO = "PDFcombinado_";

    public static final int TAMANO_MAXIMO_DESCRIPCION = 300;

    public static final int TAMANO_MAXIMO_UBICACION = 255;

    public final static String EXPRESION_REGULAR_UBICACION = "([~]{0,1}[/]{1,2}[a-zA-Z0-9ñÑáéíóúüÁÉÍÓÚÜ\\_\\-\\.\n\\s\\ ]+)+";

    /**
     * Nombre del bundle de mensajes
     */
    public static final String NOMBRE_BUNDLE_DOCUMENTOS = "labelDocumentos";

    // llaves de mensajes
    public static final String LLAVE_MSG_ERROR_ULTIMA_VERSION = "msg_error_ultima_version";

    // Parametros imagenes en edicion
    public static final String INDEX = "indexImagen";
    public static final String CONSECUTIVO = "consecutivo";

    public static final String ID_APLICACION = "co.com.datatools.documentos.ID_APLICACION";

}
