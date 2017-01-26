package co.com.datatools.documentos.negocio.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.ws.CargoVO;
import co.com.datatools.documentos.ws.DocumentoEditadoVO;
import co.com.datatools.documentos.ws.DocumentoGeneradoVO;
import co.com.datatools.documentos.ws.DocumentoWSVO;
import co.com.datatools.documentos.ws.FirmaVO;
import co.com.datatools.documentos.ws.FuncionarioVO;
import co.com.datatools.documentos.ws.PlantillaConfiguracionVO;
import co.com.datatools.documentos.ws.PlantillaVO;
import co.com.datatools.documentos.ws.ProcesoVO;
import co.com.datatools.documentos.ws.UsuarioOrganizacionVO;
import co.com.datatools.documentos.ws.UsuarioVO;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

@WebService(targetNamespace = "http://interfaces.negocio.documentos.datatools.com.co/", name = "IDocumentosWS")
@Remote
public interface IDocumentosWS {

    /**
     * Servicio encargado de generar un documento a partir de datos enviados
     * 
     * @param {@link
     *            DocumentoWSVO}
     * @param {@link
     *            UsuarioVO}
     * @return {@link DocumentoGeneradoVO}
     * @author julio.pinzon
     *         <p>
     *         <strong>sergio.torres - 13-may-2015</strong><br/>
     *         Cambio en el manejo diferenciado del usuario que genera el documento y el usuario que se autentica contra el sistema de documentos
     *         </p>
     * @throws DocumentosWebException
     *             <br>
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1001: La fecha de generacion es mayor a la actual <br>
     *             1002: No se encontro el usuario <br>
     *             1003: No se pudo guardar el documento en el repositorio<br>
     *             1004: Error interno al generar documento<br>
     *             1005: No se encontro la plantilla asociada al codigo<br>
     *             1009: Error en formato de variables de plantilla<br>
     *             1010: Error en formato de fecha de generacion<br>
     *             1011: Error de autenticacion de usuario<br>
     *             1012: Problemas cargando parametros de configuracion<br>
     *             1013: Formato invalido de documento<br>
     *             1014: Excede longitud de descripcion<br>
     *             1015: Excede longitud de ubicacion<br>
     *             1016: Error en formato de ubicacion
     */
    public DocumentoGeneradoVO generarDocumento(@WebParam(name = "documentoWSVO") DocumentoWSVO documentoWSVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Edita el documento con el código de documento indicado
     * 
     * @param {@link
     *            DocumentoEditadoVO}
     * @param {@link
     *            UsuarioVO}
     * @return {@link DocumentoGeneradoVO}
     * @author julio.pinzon
     * @throws DocumentosWebException
     *             <br>
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1002: No se encontro el usuario <br>
     *             1003: No se pudo guardar el documento en el repositorio<br>
     *             1004: Error interno al generar documento<br>
     *             1009: Error en formato de variables de plantilla<br>
     *             1011: Error de autenticacion de usuario<br>
     *             1012: Problemas cargando parametros de configuracion<br>
     *             1018: Codigo del documento no está asociado a ningún documento existente<br>
     *             1019: El documento está firmado digitalmente y NO es posible editarlo
     */
    public DocumentoGeneradoVO actualizarDocumento(
            @WebParam(name = "documentoEditadoVO") DocumentoEditadoVO documentoEditadoVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Servicio encargado de obtener el documento a partir de su codigo
     * 
     * @param codigoDocumento
     *            Codigo unico del documento a consultar
     * @param {@link
     *            UsuarioVO}
     * @return {@link DocumentoGeneradoVO}
     * @author julio.pinzon
     * @throws DocumentosWebException
     *             <br>
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1011: Error de autenticacion de usuario<br>
     *             1012: Problemas cargando parametros de configuracion<br>
     *             1018: Codigo del documento no está asociado a ningún documento existente
     */
    public DocumentoGeneradoVO consultarDocumento(@WebParam(name = "codigoDocumento") Long codigoDocumento,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Servicio encargado de combinar los documentos pdf en uno solo
     * 
     * @param codigosDocumentos
     *            Lista de codigos de documentos que se van a combinar en un PDF
     * @param {@link
     *            UsuarioVO}
     * @return Contenido del documento combinado
     * @author julio.pinzon
     * @throws DocumentosException
     *             <br>
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1006: Error al combinar archivos PDF<br>
     *             1007: La lista de codigos enviados no contiene documentos en formato PDF <br>
     *             1011: Error de autenticacion de usuario<br>
     *             1012: Problemas cargando parametros de configuracion
     */
    public byte[] consultarDocumentosPDF(@WebParam(name = "codigosDocumentos") List<Long> codigosDocumentos,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Servicio encargado de combinar los archivos de los codigos de documento existentes en un archivo .zip
     * 
     * @param codigosDocumentos
     *            Lista de codigos de documentos que se van a comprimir en un ZIP
     * @param {@link
     *            UsuarioVO}
     * @return Contenido del documento comprimido
     * @author sergio.torres
     * @throws DocumentosWebException
     *             <br>
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1007: La lista de codigos enviados no contiene documentos en formato PDF <br>
     *             1008: Error al comprimir documentos <br>
     *             1011: Error de autenticacion de usuario<br>
     *             1012: Problemas cargando parametros de configuracion
     */
    public byte[] consultarDocumentosComprimidos(@WebParam(name = "codigosDocumentos") List<Long> codigosDocumentos,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Servicio encargado de obtener las variables de la plantilla asociada al codigo enviado que se encuentra vigente de acuerdo a la fecha enviada.
     * <br/>
     * La respuesta se encuentra en formato JSON
     * 
     * @param codigoPlantilla
     *            Codigo unico de una plantilla
     * @param fechaGeneracion
     *            Fecha de generacion con la que se va a generar el documento para identificar la versión de la plantilla - formato <strong>yyyy-MM-dd
     *            HH:mm:ss</strong>
     * @param {@link
     *            UsuarioVO}
     * @return JSON de variables de la plantilla
     * @author julio.pinzon
     * @throws DocumentosWebException
     *             <br>
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1005: No se encontro la plantilla asociada al codigo<br>
     *             1010: Error en formato de fecha de generacion <br>
     *             1011: Error de autenticacion de usuario
     */
    public String consultarVariablesPlantilla(@WebParam(name = "codigoPlantilla") String codigoPlantilla,
            @WebParam(name = "fechaGeneracion") String fechaGeneracion,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Retorna la ubicacion donde se encuentra ubicado un archivo en formato PDF que contiene todos los archivos asociados a los ids indicados. El
     * usuario indicado debe contener el nombre del usuario que realiza el uso del servicio.
     * 
     * @param consecutivos
     *            identificadores de los documentos que se desean consultar
     * @param {@link
     *            UsuarioVO}
     * @return Cadena con la ubicacion del archivo resultado
     * @throws DocumentosWebException
     *             <br>
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1006: Error al combinar archivos PDF<br>
     *             1007: La lista de codigos enviados no contiene documentos en formato PDF <br>
     *             1011: Error de autenticacion de usuario<br>
     *             1012: Problemas cargando parametros de configuracion
     * @author robert.bautista
     */
    public String consultarURLDocumentos(@WebParam(name = "codigosDocumentos") List<Long> codigosDocumentos,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Registra el cargo indicado en el sistema de documentos. Valida que no exista otro cargo con el mismo nombre, sin tener en cuenta mayúsculas o
     * minusculas
     * 
     * @param cargoVO
     *            cargo a registrar
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001050: "Existe un cargo con el mismo nombre", (Codigo:1050)<br>
     *             DOC_001051: "Existe un cargo con el mismo identificador 'id' ", (Codigo:1051)<br>
     *             DOC_001054: "No existe el proceso asociado al cargo", (Codigo:1054)<br>
     *             DOC_001077: "Nombre del cargo obligatorio", (Codigo:1077), <br>
     *             DOC_001078: "Longitud maxima del Nombre del Cargo 150", (Codigo:1077)<br>
     * @author
     */
    public String registrarCargo(@WebParam(name = "cargo") CargoVO cargoVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Adiciona los procesos al cargo indicado
     * 
     * @param idCargo
     *            id del cargo al cual se adicionan los procesos
     * @param procesos
     *            id de los procesos a adicionar
     * @param {@link
     *            UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001054: "No existe el proceso asociado al cargo", (Codigo:1054)<br>
     *             DOC_001071: "El cargo no se encuentra registrado en el sistema", (Codigo:1071)<br>
     *             DOC_001072: "El proceso asociado al cargo ya se encuentra asociado", (Codigo:1072)<br>
     *             DOC_001079: "Procesos no ingresados", (Codigo:1079)<br>
     * @author
     */
    public String adicionarProcesosCargo(@WebParam(name = "idCargo") int idCargo,
            @WebParam(name = "idProcesos") List<Long> procesos, @WebParam(name = "usuarioVO") UsuarioVO usuarioVO)
            throws DocumentosWebException;

    /**
     * Remueve los procesos del cargo indicado
     * 
     * @param idCargo
     *            id del cargo al cual se le removeran los procesos
     * @param procesos
     *            id de los procesos a adicionar
     * @param {@link
     *            UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001054: "No existe el proceso asociado al cargo", (Codigo:1054)<br>
     *             DOC_001071: "El cargo no se encuentra registrado en el sistema", (Codigo:1071)<br>
     *             DOC_001079: "Procesos no ingresados", (Codigo:1079)<br>
     * @author luis.forero(2015-08-05)
     */
    public String removerProcesosCargo(@WebParam(name = "idCargo") int idCargo,
            @WebParam(name = "idProcesos") List<Long> procesos, @WebParam(name = "usuarioVO") UsuarioVO usuarioVO)
            throws DocumentosWebException;

    /**
     * Registra el proceso indicado en el sistema. Valida que no exista otro proceso con el mismo nombre, sin tener en cuenta mayúsculas o minúsculas.
     * Valida que el proceso padre exista en caso de traerlo.
     * 
     * @param procesoVO
     *            {@link ProcesoVO}
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001052: "Existe un proceso con el mismo identificador 'id' ", (Codigo:1052)<br>
     *             DOC_001053: "Existe un proceso con el mismo nombre", (Codigo:1053)<br>
     *             DOC_001055: "No existe el proceso padre asociado con el ingresado", (Codigo:1055)<br>
     *             DOC_001073: "El nombre del proceso es obligatorio", (Codigo:1073),<br>
     *             DOC_001074: "Longitud del nombre del proceso invalida, max 70", (Codigo:1074), <br>
     *             DOC_001075: "La descripcion del proceso es obligatoria", (Codigo:1075),<br>
     *             DOC_001076: "Longitud de la descripcion del proceso invalida, max 250", (Codigo:1076), <br>
     * @author
     */
    public String registrarProceso(@WebParam(name = "proceso") ProcesoVO procesoVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Registra el usuario indicado. Verifica que no exista otro usuario con el mismo login sin tener en cuenta mayúsculas o minúsculas. En caso de
     * ser necesario verifica que el funcionario exista.
     * 
     * @param usuarioOrganizacionVO
     *            {@link UsuarioOrganizacionVO}
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001056: "Funcionario asociado al usuario no existente en el sistema", (Codigo:1056)<br>
     *             DOC_001059: "El funcionario no contiene el numero de identificacion", (Codigo:1059)<br>
     *             DOC_001060: "El funcionario no contiene el nombre del tipo de identificacion", (Codigo:1060)<br>
     *             DOC_001080: "Login del usuario es obligatorio", (Codigo:1080) <br>
     *             DOC_001081: "Maxima longitud del login es 60", (Codigo:1081) <br>
     *             DOC_001082: "Maxima longitud del nombre del tipo de documento del funcionario es 40", (Codigo:1082) <br>
     *             DOC_001083: "Maxima longitud del numero de documento del funcionario es 20", (Codigo:1083) <br>
     *             DOC_001088: "Contrasena obligatoria", (Codigo:1088), <br>
     *             DOC_001089: "Longitud maxima de la Contrasena excedida", (Codigo:1089), <br>
     *             DOC_SEG_002: "El usuario ya existe", (Codigo:2)
     * @author
     */
    public String registrarUsuario(@WebParam(name = "usuario") UsuarioOrganizacionVO usuarioOrganizacionVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Registra el funcionario indicado. Verifica que no exista otro funcionario con el tipo y numero de documento de identidad. Verifica que el cargo
     * del funcionario exista y que las fechas de labor del funcionario sean ordenadas cronologicamente. Además verifica que las fechas del cargo se
     * encuentren dentro del rango de las fechas de labor del funcionario.
     * 
     * @param funcionarioVO
     *            {@link FuncionarioVO}
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001059: "El funcionario no contiene el numero de identificacion", (Codigo:1059)<br>
     *             DOC_001060: "El funcionario no contiene el nombre del tipo de identificacion", (Codigo:1060)<br>
     *             DOC_001061: "Error en formato de fecha inicial del funcionario", (Codigo:1061)<br>
     *             DOC_001062: "Error en formato de fecha final del funcionario", (Codigo:1062)<br>
     *             DOC_001063: "Error en formato de fecha inicial del cargo del funcionario", (Codigo:1063)<br>
     *             DOC_001064: "Error en formato de fecha final del cargo del funcionario", (Codigo:1064)<br>
     *             DOC_001065:
     *             "El funcionario con el numero de documento y el nombre de identificacion ingresado ya se encuentra registrado en el sistema"
     *             ,(Codigo:1065)<br>
     *             DOC_001066: "El cargo a asociar no se encuentra registrado en el sistema", (Codigo:1066)<br>
     *             DOC_001067: "Los cargos asociados al funcionario no se encuentran ordenados cronologicamente", (Codigo:1067)<br>
     *             DOC_001082: "Maxima longitud del nombre del tipo de documento del funcionario es 40", (Codigo:1082) <br>
     *             DOC_001083: "Maxima longitud del numero de documento del funcionario es 20", (Codigo:1083) <br>
     *             DOC_001084: "Nombre del funcionario es obligatorio", (Codigo:1084) <br>
     *             DOC_001085: "Maxima longitud del nombre del funcionario es 125", (Codigo:1085) <br>
     *             DOC_001086: "Sigla tipo de identificacion es obligatorio", (Codigo:1086) <br>
     *             DOC_001087: "Maxima longitud Sigla tipo de identificacion es 6", (Codigo:1087) <br>
     * @author luis.forero
     */
    public String registrarFuncionario(@WebParam(name = "funcionario") FuncionarioVO funcionarioVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Actualiza la firma indicada al funcionario relacionado a esta. Verifica que el funcionario exista por su tipo y número de documento.
     * 
     * @param firmaVO
     *            la firma a actualizar
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001059: "El funcionario no contiene el numero de identificacion", (Codigo:1059)<br>
     *             DOC_001060: "El funcionario no contiene el nombre del tipo de identificacion", (Codigo:1060)<br>
     *             DOC_001066: "El cargo a asociar no se encuentra registrado en el sistema", (Codigo:1066)<br>
     *             DOC_001067: "Los cargos asociados al funcionario no se encuentran ordenados cronologicamente", (Codigo:1067)<br>
     * @author
     */
    public String actualizarFirma(@WebParam(name = "firmaVO") FirmaVO firmaVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Actualiza la clave del usuario indicado. Solo se actualiza el funcionario si y solo si el usuario no tenía funcionario asociado.
     * 
     * @param usuarioVO
     *            {@link UsuarioOrganizacionVO}
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001056: "Funcionario asociado al usuario no existente en el sistema", (Codigo:1056)<br>
     *             DOC_001057: "Usuario no existente en el sistema", (Codigo:1057)<br>
     *             DOC_001058: "El funcionario asociado al usuario a actualizar no corresponde con el asociado en el sistema", (Codigo:1058)<br>
     *             DOC_001059: "El funcionario no contiene el numero de identificacion", (Codigo:1059)<br>
     *             DOC_001060: "El funcionario no contiene el nombre del tipo de identificacion", (Codigo:1060)<br>
     *             DOC_001080: "Login del usuario es obligatorio", (Codigo:1080) <br>
     *             DOC_001081: "Maxima longitud del login es 60", (Codigo:1081) <br>
     *             DOC_001082: "Maxima longitud del nombre del tipo de documento del funcionario es 40", (Codigo:1082) <br>
     *             DOC_001083: "Maxima longitud del numero de documento del funcionario es 20", (Codigo:1083) <br>
     *             DOC_001088: "Contrasena obligatoria", (Codigo:1088), <br>
     *             DOC_001089: "Longitud maxima de la Contrasena excedida", (Codigo:1089), <br>
     * @author luis.forero
     */
    public String actualizarUsuario(@WebParam(name = "usuario") UsuarioOrganizacionVO usuarioOrganizacionVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Actualiza el funcionario indicado. No actualiza el nombre del funcionario. Verifica que el cargo del funcionario exista y que las fechas de
     * labor del funcionario sean ordenadas cronologicamente. Ademas verifica que las fechas del cargo se encuentren dentro del rango de las fechas de
     * labor del funcionario.
     * 
     * @param funcionarioVO
     *            {@link FuncionarioVO}
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001061: "Error en formato de fecha inicial del funcionario", (Codigo:1061)<br>
     *             DOC_001062: "Error en formato de fecha final del funcionario", (Codigo:1062)<br>
     *             DOC_001063: "Error en formato de fecha inicial del cargo del funcionario", (Codigo:1063)<br>
     *             DOC_001064: "Error en formato de fecha final del cargo del funcionario", (Codigo:1064)<br>
     *             DOC_001066: "El cargo a asociar no se encuentra registrado en el sistema", (Codigo:1066)<br>
     *             DOC_001067: "Los cargos asociados al funcionario no se encuentran ordenados cronologicamente", (Codigo:1067)<br>
     *             DOC_001069: "El cargo del funcionario actualmente se encuentra sin cerrar", (Codigo:1069)<br>
     *             DOC_001070: "El cargo ingresado del funcionario se cruza con respecto al anterior cargo asociado a el en el sistema",(Codigo:1070)
     *             <br>
     * @author luis.forero
     */
    public String actualizarFuncionario(@WebParam(name = "funcionario") FuncionarioVO funcionarioVO,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Consulta las plantillas a partir de el codigo del proceso
     * 
     * @param codigoProceso
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @return Devuelve una lista de plantillas
     * @author julio.pinzon 2016-09-06
     * @throws DocumentosWebException
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1002: No se encontro el usuario <br>
     *             1020: No existe un proceso con ese codigo <br>
     */
    public List<PlantillaVO> consultarPlantillas(@WebParam(name = "codigoProceso") String codigoProceso,
            @WebParam(name = "usuarioVO") UsuarioVO usuarioVO) throws DocumentosWebException;

    /**
     * Consulta la configuracion de plantilla a partir del codigo del plantilla
     * 
     * @param codigoPlantilla
     * @param fechaReferencia
     * @param usuarioVO
     *            {@link UsuarioVO}
     * @return Devuelve la configuracion de la plantilla {@link PlantillaConfiguracionVO}
     * @author julio.pinzon 2016-09-08
     * @throws DocumentosWebException
     *             1000: Se recibio al menos un parametro invalido <br>
     *             1002: No se encontro el usuario <br>
     *             1021: No existe un plantilla con ese codigo <br>
     *             1022: No existe una configuracion para la plantilla<br>
     */
    public PlantillaConfiguracionVO consultarConfiguracionPlantilla(
            @WebParam(name = "codigoPlantilla") String codigoPlantilla,
            @WebParam(name = "fechaReferencia") Date fechaReferencia, @WebParam(name = "usuarioVO") UsuarioVO usuarioVO)
            throws DocumentosWebException;

}
