package co.com.datatools.documentos.constantes;

import co.com.datatools.documentos.documento.UtilSeguridad;

/**
 * Clase que encapsula los valores constantes para el m�dulo de Plantillas
 * 
 * @author julio.pinzon
 * 
 */
public class ConstantesGeneral {

    // Corresponden a llaves de errores que se mostraran al usuario
    public final static String LLAVE_MENSAJE_ERROR_CONSULTA = "error_consulta";
    public final static String LLAVE_MENSAJE_ERROR_OPERACION = "error_operacion";
    public final static String LLAVE_MENSAJE_ERROR_SISTEMA = "error_sistema";

    public final static String LLAVE_MENSAJE_NO_RESULTADOS_COSULTA = "No se presentaron resultados en la consulta";

    /**
     * Nombre del bundle de mensajes general
     */
    public static final String NOMBRE_BUNDLE_GENERAL = "labelGeneral";

    public static final String VALOR_SI = "SI";
    public static final String VALOR_NO = "NO";

    // date format
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Servlet imagenes para variables
    public static final String SERVLET_IMAGENES_VARIABLES = "/ImagenServlet?idVariable=";

    // Servlet imagenes para edicion documentos
    public static final String SERVLET_IMAGENES_DOCUMENTO = "/ImagenServlet?indexImagen=";

    // Valores auditoria
    public static final String LOCALHOST = "127.0.0.1";
    public static final String USUARIO = "AUTO";

    public static final String VERSION_DOCUMENTOS = UtilSeguridad.cargarVersionArtefactoDoc();
    public static final String PATH_DOCUMENTOS_FILE_SYSTEM = System.getProperty("PATH_DOCUMENTOS");

}
