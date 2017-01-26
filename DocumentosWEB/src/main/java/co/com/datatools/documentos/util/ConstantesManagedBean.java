package co.com.datatools.documentos.util;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.managed_bean.comun.PrincipalMB;
import co.com.datatools.documentos.plantillas.PlantillaDTO;

public class ConstantesManagedBean {

    /**
     * Nombre del objeto en sesion q contiene la informacion del usuario que se encuentra logueado sobre la aplicacion de documentos
     */
    public static final String NOMBRE_OBJ_USUARIO_ORGANIZACION = "usuarioOrganizacionDocumentos";

    /**
     * Clase del objeto en sesion q contiene la informacion del usuario que se encuentra logueado sobre la aplicacion de documentos
     */
    public static final Class<UsuarioOrganizacionDTO> CLASE_OBJ_USUARIO_ORGANIZACION = UsuarioOrganizacionDTO.class;

    /**
     * Nombre del objeto en sesion q contiene la informacion de la plantilla que se esta configurando en sesion
     */
    public static final String NOMBRE_OBJ_PLANTILLA_DOCUMENTOS = "plantillaDocumentos";

    /**
     * Nombre del objeto en sesion q contiene la informacion de la plantilla que se esta configurando en sesion
     */
    public static final String NOMBRE_OBJ_DOCUMENTO = "documentoEditar";

    /**
     * Nombre del objeto en sesion q contiene la informacion de la plantilla que se esta configurando en sesion
     */
    public static final String NOMBRE_OBJ_PLANTILLA_DOCUMENTOS_ULTIMA_VERSION = "plantillaDocumentosUltimaVersion";

    /**
     * Clase del objeto en sesion que contiene la informacion de la plantilla que se esta configurando en sesion
     */
    public static final Class<PlantillaDTO> CLASE_OBJ_PLANTILLA_DOCUMENTOS = PlantillaDTO.class;

    /**
     * Clase del objeto en sesion que contiene la informacion de la plantilla que se esta configurando en sesion
     */
    public static final Class<DocumentoDTO> CLASE_OBJ_DOCUMENTO = DocumentoDTO.class;

    /**
     * Nombre del objeto en sesion q contiene la informacion del bean principal
     */
    public static final String NOMBRE_OBJ_BEAN_PRINCIPAL = "principalMB";

    /**
     * Nombres de objeto en sesion de beans de negocio
     */
    public static final String NOMBRE_OBJ_BEAN_CONSULTA_PLANTILLA = "consultaPlantillaMB";
    public static final String NOMBRE_OBJ_BEAN_REGISTRA_PLANTILLA = "registraPlantillaMB";
    public static final String NOMBRE_OBJ_BEAN_FIRMA_PLANTILLA = "configurarFirmaDocumentoMB";
    public static final String NOMBRE_OBJ_BEAN_VARIABLE_PLANTILLA = "crearVariablePlantillaMB";
    public static final String NOMBRE_OBJ_BEAN_REGISTRA_DOCUMENTO = "registraDocumentoMB";
    public static final String NOMBRE_OBJ_BEAN_CONSULTA_HISTORICO_DOCUMENTO = "consultarHistoricoDocumentosMB";

    /**
     * Rutas para redirigir a paginas
     */
    public static final String RUTA_CONSULTA_PLANTILLA = "/DocumentosWEB/protected/plantilla/consultarPlantilla.xhtml";
    public static final String RUTA_REGISTRA_PLANTILLA = "/DocumentosWEB/protected/plantilla/registraPlantilla.xhtml";
    public static final String RUTA_EDITA_DOCUMENTO = "/DocumentosWEB/protected/documentos/registraDocumento.xhtml";
    public static final String RUTA_INICIO = "/";
    public static final String RUTA_CONSULTA_HISTORICO_DOCUMENTO = "/DocumentosWEB/protected/documentos/consultarHistoricoDocumentos.xhtml";

    /**
     * Clase del objeto en sesion que contiene la informacion del bean principal
     */
    public static final Class<PrincipalMB> CLASE_OBJ_BEAN_PRINCIPAL = PrincipalMB.class;

    /**
     * Nombre del objeto en sesion que contiene la variable que se ha creado
     */
    public static final String NOMBRE_OBJ_VARIABLE_CREADA = "variablePlantilla";

    /**
     * Nombre del atributo en la sesion en el cual se va a almacenar la ruta de la pagina de inicio de la aplicacion, a la cual se realiza el
     * direccionamiento cuando no se tiene una sesion valida
     */
    public static final String URL_INICIAL = "URL_INICIAL";

    /**
     * Nombre del parametro contenido en el vinculo(URL) de recuperacion corresponde al codigo de verificacion del vinculo de recuperacion
     */
    public static final String NOMBRE_PARAMETRO_KEY_RECUPERACION = "key";

}
