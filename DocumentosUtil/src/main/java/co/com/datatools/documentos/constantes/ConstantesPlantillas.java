package co.com.datatools.documentos.constantes;

/**
 * Clase que encapsula los valores constantes para el m�dulo de Plantillas
 * 
 * @author julio.pinzon
 * 
 */
public class ConstantesPlantillas {

    // Corresponden numeros que representan los estados de plantillas
    public final static short ID_ESTADO_PLANTILLA_ACTIVO = 1;
    public final static short ID_ESTADO_PLANTILLA_ANULADO = 0;

    // Corresponden a los numeros que representan el contexto aplicación variable
    public final static int ID_CONTEXTO_APLICACION_VARIABLE_ORGANIZACION = 1;
    public final static int ID_CONTEXTO_APLICACION_VARIABLE_PROCESO = 2;
    public final static int ID_CONTEXTO_APLICACION_VARIABLE_PLANTILLA = 3;

    // Corresponden a los tipos de variable
    public final static int ID_TIPO_VARIABLE_NUMERO = 1;
    public final static int ID_TIPO_VARIABLE_URL = 2;
    public final static int ID_TIPO_VARIABLE_TEXTO = 3;
    public final static int ID_TIPO_VARIABLE_FECHA = 4;
    public final static int ID_TIPO_VARIABLE_IMAGEN_LOCAL = 5;
    public final static int ID_TIPO_VARIABLE_IMAGEN_VARIABLE = 6;

    // Corresponden a los tipos de firma de plantillas
    public final static int ID_TIPO_FIRMA_PLANTILLA_FUNCIONARIO = 1;
    public final static int ID_TIPO_FIRMA_PLANTILLA_CARGO = 2;
    public final static int ID_TIPO_FIRMA_PLANTILLA_FUNCIONARIO_GENERA_DOCUMENTO = 3;

    // Corresponden a los tipos de fecha referencia
    public static final int ID_TIPO_FECHA_REFERENCIA_FECHA_ACTUAL = 1;
    public static final int ID_TIPO_FECHA_REFERENCIA_FECHA_GENERACION = 2;
    public static final int ID_TIPO_FECHA_REFERENCIA_FECHA_VARIABLE_PLANTILLA = 3;

    // Llave de error al crear plantilla
    public static final String LLAVE_MENSAJE_ERROR_CODIGO_EXISTE = "msg_codigo_existe";
    public static final String LLAVE_MENSAJE_ERROR_NOMBRE_EXISTE = "msg_nombre_existe";
    public static final String LLAVE_MENSAJE_ERROR_FECHA_INICIO_MENOR_FINAL = "msg_fecha_inicio_menor_final";
    public static final String LLAVE_MSG_ERROR_CRUCE_VIGENCIA = "msg_error_cruce_vigencia";
    public static final String LLAVE_MSG_ERROR_CRUCE_VIGENCIA_SIN_FECHA_FIN = "msg_error_cruce_vigencia_sin_fecha_fin";
    public static final String LLAVE_MSG_ERROR_HUECO_ANTERIOR = "msg_error_hueco_anterior";
    public static final String LLAVE_MSG_ERROR_HUECO_POSTERIOR = "msg_error_hueco_posterior";
    public static final String LLAVE_MSG_ERROR_FECHA_INICIO_IGUAL = "msg_error_fecha_inicio_igual";
    public static final String LLAVE_MSG_ERROR_FECHA_INICIO_ELIMINAR = "msg_error_fecha_inicio_menor_actual_eliminar";
    public static final String LLAVE_MSG_ERROR_FECHA_INICIO_EDITAR = "msg_error_fecha_inicio_menor_actual_editar";
    public static final String LLAVE_MSG_ERROR_FECHA_INICIO_FECHAS = "msg_error_fecha_inicio_menor_actual_fechas";

    // Nombre de imagen en documento preliminar
    public static final String PREVIEW_IMAGE = "marca_preliminar.png";

    // Extensiones de imagenes para utilizar en generacion de archivo
    public static final String EXTENSION_TIFF = ".tif";
    public static final String EXTENSION_PNG = "png";
    public static final String EXTENSION_JPG = "jpg";

    // Constante de presentacion
    public static final String PRESENTACION_COMO_LETRAS = "Letras";
    public static final String MINUSCULA = "Minuscula";
    public static final String PRESENTACION_COMO_MONEDA = "Valor moneda";
    public static final String IMAGEN_VARIABLE_FIRMA = "Firma";

    // Corresponden a los formatos de numeros negativos
    public static final int FORMATO_NEGATIVOS_SIGNO_NEGATIVO = 1;
    public static final int FORMATO_NEGATIVOS_COLOR_ROJO = 2;
    public static final int FORMATO_NEGATIVOS_PARENTESIS = 3;
    public static final int FORMATO_NEGATIVOS_COLOR_ROJO_PARENTESIS = 4;

    /**
     * Nombre del bundle de mensajes
     */
    public static final String NOMBRE_BUNDLE_PLANTILLAS = "labelPlantillas";

    public static final String CARPETA_JASPER_PLANTILLAS = "/plantillas/jasper/";

    // Patrones de verificacion de variables
    public static final String PATRON_VERIFICACION_FORMATO_IMAGEN = "[0-9]+[,][0-9]+";
    public static final String PATRON_VERIFICACION_FORMATO_NUMERO = "[0-9]+([.][0-9]*)?";
    public static final String PATRON_VERIFICACION_VALOR_DEFECTO_NUMERO = "[0-9]+([.][0-9]*)?";
    public static final String PATRON_VERIFICACION_VALOR_DEFECTO_FECHA = "yyyy-MM-dd HH:mm:ss";
    public static final String PATRON_VERIFICACION_VALOR_DEFECTO_TEXTO = "[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\_\\&\\@\\'\\;\\.\\,\\:\\*\\+\\-\\/\\?\\�\\�\\!\\%\\(\\)\\$\\�\\=\\#\n\\s\\\\ ]+";
    public static final String PATRON_VERIFICACION_VALOR_DEFECTO_URL = "[a-zA-Z0-9\\_\\&\\@\\'\\;\\.\\,\\:\\*\\+\\-\\/\\?\\�\\�\\!\\%\\(\\)\\$\\�\\=\\#\n\\s\\\\ ]+";
    public static final String PATRON_VERIFICACION_VALOR_DEFECTO_URL_IMAGEN = "[a-zA-Z0-9\\_\\&\\@\\'\\;\\.\\,\\:\\*\\+\\-\\/\\?\\�\\�\\!\\%\\(\\)\\$\\�\\=\\#\n\\s\\\\ ]+";

}
