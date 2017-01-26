package co.com.datatools.documentos.negocio.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.ws.DocumentoGeneradoVO;
import co.com.datatools.documentos.ws.DocumentoVO;
import co.com.datatools.documentos.ws.PlantillaConfiguracionVO;
import co.com.datatools.documentos.ws.PlantillaVO;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

@Remote
public interface IRIntegracionDocumentos {

    /**
     * importa los datos de la vista TIPO_IDENTIFICACION_VISTA
     * 
     * @return Lista TipoIdentificacionDTO
     * @author dixon.alvarez
     */
    @Deprecated
    public List<TipoIdentificacionDTO> importarTipoIdentificacion();

    /**
     * importa los datos de la vista CARGO_VISTA
     * 
     * @param cargoDTO
     *            Contiene el Id del cargo por el que se va filtrar la consulta
     * @param idOrganizacion
     *            Filtro Id organizaci�n a la que pertenece el cargo
     * @return Lista CargoDTO
     * @author dixon.alvarez
     */
    @Deprecated
    public List<CargoDTO> importarCargo(CargoDTO cargoDTO, int idOrganizacion);

    /**
     * importa los datos de la vista FUNCIONARIO_VISTA
     * 
     * @param funcionarioDTO
     *            Contiene los datos del funcionario que van a filtrar la consulta
     * @param idOrganizacion
     *            Filtro Id de la organizacion a la que pertence el funcionario
     * @return List FuncionarioDTO
     * @author dixon.alvarez
     */
    @Deprecated
    public List<FuncionarioDTO> importarFuncionario(FuncionarioDTO funcionarioDTO, int idOrganizacion);

    /**
     * importa los datos de la vista PROCESO_VISTA
     * 
     * @param procesoDTO
     *            Contiene los datos del Proceso que se van a tener en cuenta para filtrar la consulta
     * @param idOrganizacion
     *            Filtro Id organizacion a la que pertenece el proceso
     * @return List ProcesoDTO
     * @author dixon.alvarez
     */
    @Deprecated
    public List<ProcesoDTO> importarProceso(ProcesoDTO procesoDTO, int idOrganizacion);

    /**
     * importa los datos de la vista PROCESO_CARGO_VISTA
     * 
     * @param procesoDTO
     *            Contiene los datos del Proceso que se van a tener en cuenta para filtrar la consulta
     * @return List ProcesoDTO
     * @author dixon.alvarez
     */
    @Deprecated
    public List<ProcesoDTO> importarProcesoCargos(ProcesoDTO procesoDTO);

    /**
     * Servicio encargado de generar un documento apartir de datos enviados
     * 
     * @param documentoVO
     *            Objeto utilizado para la generacion del documento
     * @return Documento generado
     * @author julio.pinzon
     * @throws DocumentosWebException
     *             <br>
     *             1001: La fecha de generacion es mayor a la actual <br>
     *             1002: No se encontro el usuario <br>
     *             1003: No se pudo guardar el documento en el repositorio<br>
     *             1004: Error interno al generar documento<br>
     *             1005: No se encontro la plantilla asociada al codigo<br>
     *             1012: Problemas cargando parametros de configuracion<br>
     */
    public DocumentoGeneradoVO generarDocumento(DocumentoVO documentoVO) throws DocumentosWebException;

    /**
     * Obtiene el documento a partir del codigo unico de documento
     * 
     * @param codigoDocumento
     *            Codigo unico del documento
     * @return Documento obtenido
     * @author julio.pinzon
     * @throws DocumentosWebException
     *             <br>
     *             1012: Problemas cargando parametros de configuracion<br>
     */
    public DocumentoGeneradoVO consultarDocumento(Long codigoDocumento) throws DocumentosWebException;

    /**
     * Combina los documentos pdf en uno solo
     * 
     * @param codigosDocumentos
     *            Lista de codigos de documentos que se van a combinar en un PDF
     * @return Contenido del documento combinado
     * @author julio.pinzon
     * @throws DocumentosWebException
     *             <br>
     *             1006: Error al combinar archivos PDF<br>
     *             1007: La lista de codigos enviados no contiene documentos en formato PDF <br>
     *             1012: Problemas cargando parametros de configuracion
     */
    public byte[] consultarDocumentosPDF(List<Long> codigosDocumentos) throws DocumentosWebException;

    /**
     * Combina los archivos de los codigos de documento existentes en un archivo .zip
     * 
     * @param codigosDocumentos
     *            Lista de codigos de documentos que se van a comprimir en un ZIP
     * @return Contenido del documento comprimido
     * @author sergio.torres
     * @throws DocumentosWebException
     *             <br>
     *             1007: La lista de codigos enviados no contiene documentos en formato PDF <br>
     *             1008: Error al comprimir documentos <br>
     *             1012: Problemas cargando parametros de configuracion
     */
    public byte[] consultarDocumentosComprimidos(List<Long> codigosDocumentos) throws DocumentosWebException;

    /**
     * Obtiene las variables de la plantilla asociada al codigo enviado que se encuentra vigente de acuerdo a la fecha enviada.<br/>
     * La respuesta se encuentra en formato JSON
     * 
     * @param codigoPlantilla
     *            Codigo unico de una plantilla
     * @param fechaGeneracion
     *            Fecha de generacion con la que se va a generar el documento para identificar la versión de la plantilla - formato <strong>yyyy-MM-dd
     *            HH:mm:ss</strong>
     * @return JSON de variables de la plantilla
     * @author julio.pinzon
     * @throws DocumentosWebException
     *             <br>
     *             1005: No se encontro la plantilla asociada al codigo<br>
     *             1010: Error en formato de fecha de generacion <br>
     */
    public String consultarVariablesPlantilla(String codigoPlantilla, Date fechaGeneracion)
            throws DocumentosWebException;

    /**
     * Retorna la ubicacion donde se encuentra ubicado un archivo en formato PDF que contiene todos los archivos asociados a los ids indicados.
     * 
     * @param ids
     *            identificadores de los documentos que se desean consultar
     * @return Cadena con la ubicaci�n del archivo resultado
     * @throws DocumentosWebException
     *             1006: Error al combinar archivos PDF<br>
     *             1007: La lista de codigos enviados no contiene documentos en formato PDF <br>
     *             1012: Problemas cargando parametros de configuracion
     * @author robert.bautista
     */
    public String consultarURLDocumentos(List<Long> ids) throws DocumentosWebException;

    /**
     * Servicio encargado de actualizar la información de las variables contenidas en el documento. Es decir, solo se actualizan los valores de las
     * variables de la plantilla.
     * 
     * @param documentoVO
     *            Objeto utilizado para la generacion del documento
     * @return Documento generado
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
    public DocumentoGeneradoVO actualizarDocumento(DocumentoVO documentoVO) throws DocumentosWebException;

    /**
     * Consulta las plantillas a partir de el codigo del proceso
     * 
     * @param codigoProceso
     * @return Devuelve una lista de plantillas
     * @author julio.pinzon 2016-09-06
     * @throws DocumentosWebException
     *             1002: No se encontro el usuario <br>
     */
    public List<PlantillaVO> consultarPlantillas(String codigoProceso) throws DocumentosWebException;

    /**
     * Consulta la configuracion de plantilla a partir del codigo del plantilla
     * 
     * @param codigoPlantilla
     * @param fechaReferencia
     * @return Devuelve la configuracion de la plantilla {@link PlantillaConfiguracionVO}
     * @author julio.pinzon 2016-09-08
     * @throws DocumentosWebException
     *             1021: No existe un plantilla con ese codigo <br>
     *             1022: No existe una configuracion para la plantilla<br>
     */
    public PlantillaConfiguracionVO consultarConfiguracionPlantilla(String codigoPlantilla, Date fechaReferencia)
            throws DocumentosWebException;

}
