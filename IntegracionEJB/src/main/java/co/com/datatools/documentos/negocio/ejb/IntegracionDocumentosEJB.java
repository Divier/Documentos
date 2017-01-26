package co.com.datatools.documentos.negocio.ejb;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.html.HtmlUtil;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.helper.DocumentoHelper;
import co.com.datatools.documentos.negocio.helper.PlantillaConfiguracionHelper;
import co.com.datatools.documentos.negocio.interfaces.ILIntegracionDocumentos;
import co.com.datatools.documentos.negocio.interfaces.IRIntegracionDocumentos;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRParametroSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRProceso;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRUsuarioOrganizacion;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRPlantillaConfiguracion;
import co.com.datatools.documentos.negocio.util.Conexion;
import co.com.datatools.documentos.plantillas.PlantillaConfiguracionDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.ws.DocumentoGeneradoVO;
import co.com.datatools.documentos.ws.DocumentoVO;
import co.com.datatools.documentos.ws.PlantillaConfiguracionVO;
import co.com.datatools.documentos.ws.PlantillaVO;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;
import co.com.datatools.util.file.CompresorArchivos;

/**
 * Clase que contiene los metodos que consultan las diferentes vistas creadas en el negocio
 * 
 * @author dixon.alvarez
 * 
 */
@Stateless(name = "IntegracionDocumentosEJB")
@LocalBean
public class IntegracionDocumentosEJB implements ILIntegracionDocumentos, IRIntegracionDocumentos {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(IntegracionDocumentosEJB.class.getName());

    @EJB
    private IRDocumento iRDocumento;

    @EJB
    private IRPlantilla iRPlantilla;

    @EJB
    private IRPlantillaConfiguracion iRPlantillaConfiguracion;

    @EJB
    private IRProceso procesoEJB;

    @EJB
    private IRUsuarioOrganizacion iRUsuarioOrganizacion;

    @EJB
    private IRParametroSistema iRParametroSistema;

    /**
     * Realiza la autenticacion del usuario.
     * 
     * @param usuario
     *            contiene el usuario y clave para ser autenticados, la clave debe venir cifrada.
     * @throws DocumentosWebException
     *             1011: Error de autenticacion de usuario, si la autenticacion del usuario no es exitosa.
     */
    // private void autenticarUsuario(UsuarioVO usuario) throws DocumentosWebException {
    // try {
    // ilUsuarioOrganizacion.autenticarUsuarioOrganizacionDTO(usuario.getUsuario(), usuario.getClave());
    // } catch (DocumentosException e) {
    // throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001011);
    // }
    //
    // }

    @Override
    public List<TipoIdentificacionDTO> importarTipoIdentificacion() {
        logger.debug("IntegracionDocumentosEJB::importarTipoIdentificacion");
        List<TipoIdentificacionDTO> lTipoIdentificacionDTOs = new ArrayList<TipoIdentificacionDTO>();
        Conexion conexion = new Conexion();
        Connection c = conexion.getConexion();
        try {
            String sql = "SELECT id_tipo_identificacion," + " nombre_tipo_identificacion"
                    + " FROM TIPO_IDENTIFICACION_VISTA";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
                tipoIdentificacionDTO.setIdTipoIdentificacion(rs.getInt("id_tipo_identificacion"));
                tipoIdentificacionDTO.setNombreTipoIdentificacion(rs.getString("nombre_tipo_identificacion"));
                lTipoIdentificacionDTOs.add(tipoIdentificacionDTO);
            }
            ps.close();
            rs.close();
        } catch (SQLException se) {
            logger.error(se);
        } finally {
            conexion.cerrarConexion(c);
        }
        return lTipoIdentificacionDTOs;
    }

    @Override
    public List<CargoDTO> importarCargo(CargoDTO cargoDTO, int idOrganizacion) {
        logger.debug("IntegracionDocumentosEJB::importarCargo");
        List<CargoDTO> lCargoDTOs = new ArrayList<CargoDTO>();
        Conexion conexion = new Conexion();
        Connection c = conexion.getConexion();
        try {
            String sql = "SELECT id_cargo," + " nombre_cargo," + " codigo_organizacion" + " FROM CARGO_VISTA";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CargoDTO cargo = new CargoDTO();
                cargo.setIdCargo(rs.getInt("id_cargo"));
                cargo.setNombreCargo(rs.getString("nombre_cargo"));
                lCargoDTOs.add(cargo);
            }
            ps.close();
            rs.close();
        } catch (SQLException se) {
            logger.error(se);
        } finally {
            conexion.cerrarConexion(c);
        }
        return lCargoDTOs;
    }

    @Override
    public List<FuncionarioDTO> importarFuncionario(FuncionarioDTO funcionarioDTO, int idOrganizacion) {
        logger.debug("IntegracionDocumentosEJB::importarFuncionario");
        List<FuncionarioDTO> lFuncionarioDTOs = new ArrayList<FuncionarioDTO>();
        Conexion conexion = new Conexion();
        Connection c = conexion.getConexion();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT numero_documento_identificacion,");
            sql.append(" nombre_funcionario,");
            sql.append(" ruta_firma_funcionario,");
            sql.append(" fecha_inicial_funcionario,");
            sql.append(" fecha_final_funcionario,");
            sql.append(" id_tipo_identificacion_funcionario,");
            sql.append(" id_cargo_funcionario");
            sql.append(" FROM FUNCIONARIO_VISTA");
            PreparedStatement ps = c.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FuncionarioDTO funcionario = new FuncionarioDTO();
                funcionario.setNumeroDocumIdent(rs.getString("numero_documento_identificacion"));
                funcionario.setNombreFuncionario(rs.getString("nombre_funcionario"));
                // funcionario.setRutaFirma(rs.getString("ruta_firma_funcionario"));//revisar
                funcionario.setFechaFinalFuncionario(rs.getDate("fecha_final_funcionario"));
                funcionario.setFechaInicialFuncionario(rs.getDate("fecha_inicial_funcionario"));
                /*
                 * TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
                 * tipoIdentificacionDTO.setIdTipoIdentificacion(rs.getInt("id_tipo_identificacion_funcionario"));
                 * funcionario.setTipoIdentificacionDTO(tipoIdentificacionDTO);//revisar
                 */
                List<FuncionarioCargoDTO> lFuncionarioCargosDTO = new ArrayList<FuncionarioCargoDTO>();
                FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
                CargoDTO cargoDTO = new CargoDTO();
                cargoDTO.setIdCargo(rs.getInt("id_cargo_funcionario"));
                funcionarioCargoDTO.setCargoDTO(cargoDTO);
                funcionarioCargoDTO.setFuncionarioDTO(funcionario);
                lFuncionarioCargosDTO.add(funcionarioCargoDTO);
                funcionario.setListFuncionarioCargosDTO(lFuncionarioCargosDTO);
                lFuncionarioDTOs.add(funcionario);
            }
            ps.close();
            rs.close();
        } catch (SQLException se) {
            logger.error(se);
        } finally {
            conexion.cerrarConexion(c);
        }
        return lFuncionarioDTOs;
    }

    @Override
    public List<ProcesoDTO> importarProceso(ProcesoDTO procesoDTO, int idOrganizacion) {
        logger.debug("IntegracionDocumentosEJB::importarProceso");
        List<ProcesoDTO> lProcesoDTOs = new ArrayList<ProcesoDTO>();
        Conexion conexion = new Conexion();
        Connection c = conexion.getConexion();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id_proceso,");
            sql.append(" nombre_proceso,");
            sql.append(" descripcion_proceso,");
            sql.append(" id_proceso_padre,");
            sql.append(" id_organizacion");
            sql.append(" FROM PROCESO_VISTA");
            PreparedStatement ps = c.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProcesoDTO proceso = new ProcesoDTO();
                proceso.setIdProceso(rs.getLong("id_proceso"));
                proceso.setNombreProceso(rs.getString("nombre_proceso"));
                proceso.setDescripcionProceso("descripcion_proceso");
                if (rs.getLong("id_proceso_padre") != 0) {
                    ProcesoDTO procesoPadre = new ProcesoDTO();
                    procesoPadre.setIdProceso(rs.getLong("id_proceso_padre"));
                    proceso.setProcesoDTO(procesoPadre);
                }
                lProcesoDTOs.add(proceso);
            }
            ps.close();
            rs.close();
        } catch (SQLException se) {
            logger.error(se);
        } finally {
            conexion.cerrarConexion(c);
        }
        return lProcesoDTOs;
    }

    @Override
    public List<ProcesoDTO> importarProcesoCargos(ProcesoDTO procesoDTO) {
        logger.debug("IntegracionDocumentosEJB::importarProcesoCargos");
        List<ProcesoDTO> lProcesoDTOs = new ArrayList<ProcesoDTO>();
        Conexion conexion = new Conexion();
        Connection c = conexion.getConexion();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id_proceso,");
            sql.append(" id_cargo");
            sql.append(" FROM PROCESO_CARGO_VISTA");
            sql.append(" WHERE id_proceso IS NOT NULL");
            if (procesoDTO != null && procesoDTO.getIdProceso() != 0) {
                sql.append(" AND id_proceso = " + procesoDTO.getIdProceso());
            }

            PreparedStatement ps = c.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            Map<Long, ProcesoDTO> lista = new HashMap<Long, ProcesoDTO>();
            while (rs.next()) {
                ProcesoDTO proceso = new ProcesoDTO();
                proceso.setIdProceso(rs.getLong("id_proceso"));
                proceso.setListCargosDTO(new ArrayList<CargoDTO>());
                CargoDTO cargoDTO = new CargoDTO();
                cargoDTO.setIdCargo(rs.getInt("id_cargo"));
                if (!lista.containsKey(proceso.getIdProceso())) {
                    lista.put(proceso.getIdProceso(), proceso);
                }
                lista.get(proceso.getIdProceso()).getListCargosDTO().add(cargoDTO);
            }
            ps.close();
            rs.close();
            for (ProcesoDTO procesoDTO2 : lista.values()) {
                lProcesoDTOs.add(procesoDTO2);
            }
        } catch (SQLException se) {
            logger.error(se);
        } finally {
            conexion.cerrarConexion(c);
        }
        return lProcesoDTOs;
    }

    @Override
    public DocumentoGeneradoVO generarDocumento(DocumentoVO documentoVO) throws DocumentosWebException {
        logger.debug("IntegracionDocumentosEJB::generarDocumento");

        // Verificamos parametro del sistema no valida fechas
        if (iRParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.VALIDA_FECHA_GENERA_DOCUMENTO)
                .equals(ConstantesGeneral.VALOR_SI)) {
            // Verifica que se esté generando con una fecha anterior o igual a la actual
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.set(Calendar.HOUR_OF_DAY, 23);
            calendarActual.set(Calendar.MINUTE, 59);
            calendarActual.set(Calendar.SECOND, 59);
            if (documentoVO.getFechaGeneracion().after(calendarActual.getTime())) {
                throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001001);
            }
        }

        DocumentoGeneradoVO documentoGeneradoVO = null;
        // Genera objeto de generacion a partir de entrada
        GeneraDocumentoDTO generaDocumentoDTO = DocumentoHelper.toGeneraDocumento(documentoVO);
        DocumentoDTO documento;

        try {
            if (documentoVO.getFormato().equals(EnumFormatoDocumento.PDF_PREVIEW)) {
                documento = this.iRDocumento.generarDocumentoSinGuardar(generaDocumentoDTO);
                documentoGeneradoVO = DocumentoHelper.toDocumentoGenerado(documento);
            } else {
                documento = iRDocumento.generarDocumento(generaDocumentoDTO);
                documentoGeneradoVO = DocumentoHelper.toDocumentoGenerado(documento);
                documentoGeneradoVO.setContenido(null);
            }

        } catch (DocumentosException de) {
            logger.error("Problemas invocando a generarDocumento.");
            throw new DocumentosWebException(de.getErrorInfo());
        }

        return documentoGeneradoVO;
    }

    @Override
    public DocumentoGeneradoVO consultarDocumento(Long codigoDocumento) throws DocumentosWebException {
        logger.debug("IntegracionDocumentosEJB::consultarDocumento");

        DocumentoGeneradoVO documentoGenerado = null;
        DocumentoDTO documento = iRDocumento.obtenerDocumento(codigoDocumento);
        if (documento != null && documento.getContenido() != null) {
            // Genera objeto de respuesta
            documentoGenerado = DocumentoHelper.toDocumentoGenerado(documento);
        } else {
            logger.error("Consecutivo del Documento NO está asociado a ningún documento existente");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001018);
        }
        return documentoGenerado;
    }

    @Override
    public byte[] consultarDocumentosPDF(List<Long> codigosDocumentos) throws DocumentosWebException {
        logger.debug("IntegracionDocumentosEJB::consultarDocumentosPDF");

        byte[] documentoGenerado = null;
        DocumentoDTO documento;

        try {
            documento = iRDocumento.consultarDocumentosPDF(codigosDocumentos);
        } catch (DocumentosException e) {
            logger.error("Problemas invocando a IDocumento.consultarDocumentosPDF");
            throw new DocumentosWebException(e.getErrorInfo());
        }

        if (documento != null && documento.getContenido() != null) {
            documentoGenerado = documento.getContenido();
        }
        return documentoGenerado;

    }

    @Override
    public byte[] consultarDocumentosComprimidos(List<Long> codigosDocumentos) throws DocumentosWebException {
        logger.debug("IntegracionDocumentosEJB::consultarDocumentosComprimidos");
        byte[] documentoGenerado = null;

        boolean tieneDocumentos = false;
        CompresorArchivos comprimido = new CompresorArchivos();
        for (Long codDocumento : codigosDocumentos) {
            DocumentoDTO documentoDTO = iRDocumento.obtenerDocumento(codDocumento);
            if (documentoDTO != null) {
                comprimido.agregarEntrada(documentoDTO.getNombreDocumento(), documentoDTO.getContenido());
                tieneDocumentos = true;
            }
        }
        // Si no encontro documentos en manda una excepcion
        if (!tieneDocumentos) {
            // TODO: poner error correcto, no solo PDF, esto es unicamente para la primera version
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001007);
        }
        try {
            documentoGenerado = comprimido.comprimir();
        } catch (IOException e) {
            logger.error("Error al comprimir documentos ", e);
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001008);
        }
        return documentoGenerado;
    }

    @Override
    public String consultarVariablesPlantilla(String codigoPlantilla, Date fechaGeneracion)
            throws DocumentosWebException {
        logger.debug("IntegracionDocumentosEJB::consultarVariablesPlantilla");

        PlantillaDTO plantillaDTO = new PlantillaDTO();
        plantillaDTO.setCodigoPlantilla(codigoPlantilla);
        List<PlantillaDTO> plantillas = iRPlantilla.consultarPlantilla(plantillaDTO, fechaGeneracion);
        if (!plantillas.isEmpty()) {
            // Objeto para conversion JSON
            Gson gson = new GsonBuilder().setDateFormat(ConstantesGeneral.DATE_FORMAT).create();
            plantillaDTO = plantillas.get(0);
            Map<String, Object> variables = HtmlUtil.obtenerVariables(
                    new String(plantillaDTO.getXmlPlantillaDTO().getContenidoXmlPlantilla(), StandardCharsets.UTF_8));
            return gson.toJson(variables);
        } else {
            logger.info("No se encontro la plantilla asociada al codigo: " + codigoPlantilla);
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001005);
        }
    }

    @Override
    public String consultarURLDocumentos(List<Long> ids) throws DocumentosWebException {
        String url;
        logger.debug("IntegracionDocumentosEJB::consultarURLDocumentos");

        try {
            url = this.iRDocumento.consultarURLDocumentos(ids);
        } catch (DocumentosException e) {
            logger.info("Problemas invocando ilDocumento.consultarURLDocumentos(ids)");
            throw new DocumentosWebException(e.getErrorInfo());
        }

        return url;
    }

    @Override
    public DocumentoGeneradoVO actualizarDocumento(DocumentoVO documentoVO) throws DocumentosWebException {
        logger.debug("IntegracionDocumentosEJB::actualizarDocumento");

        DocumentoGeneradoVO documentoGeneradoVO = null;
        // Genera objeto de generacion a partir de entrada
        GeneraDocumentoDTO generaDocumentoDTO = DocumentoHelper.toGeneraDocumento(documentoVO);
        generaDocumentoDTO.setActualizar(true);

        try {
            DocumentoDTO documento = iRDocumento.generarDocumento(generaDocumentoDTO);
            documentoGeneradoVO = DocumentoHelper.toDocumentoGenerado(documento);
            documentoGeneradoVO.setContenido(null);

        } catch (DocumentosException de) {
            logger.error("Problemas invocando a generarDocumento.");
            throw new DocumentosWebException(de.getErrorInfo());
        }

        return documentoGeneradoVO;
    }

    @Override
    public List<PlantillaVO> consultarPlantillas(String codigoProceso) throws DocumentosWebException {
        logger.debug("IntegracionDocumentosEJB::consultarPlantillas(String)");

        ProcesoDTO procesoDTO = procesoEJB.consultarProceso(codigoProceso);
        if (procesoDTO == null) {
            // Error no existe el proceso con ese codigo
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001020);
        }

        // Consulta las plantillas activas por medio del proceso

        PlantillaDTO plantillaDTO = new PlantillaDTO();
        plantillaDTO.setProcesoDTO(procesoDTO);
        List<PlantillaDTO> plantillas = iRPlantilla.consultarPlantilla(plantillaDTO, new Date());

        List<PlantillaVO> plantillasVOList = new ArrayList<>();
        for (PlantillaDTO plantilla : plantillas) {
            PlantillaVO plantillaVO = new PlantillaVO();
            plantillaVO.setCodigoPlantilla(plantilla.getCodigoPlantilla());
            plantillaVO.setNombrePlantilla(plantilla.getNombrePlantilla());
            plantillasVOList.add(plantillaVO);
        }
        return plantillasVOList;
    }

    @Override
    public PlantillaConfiguracionVO consultarConfiguracionPlantilla(String codigoPlantilla, Date fechaReferencia)
            throws DocumentosWebException {
        // Consulta las plantillas activas por codigo

        PlantillaDTO plantilla = new PlantillaDTO();
        plantilla.setCodigoPlantilla(codigoPlantilla);
        List<PlantillaDTO> plantillas = iRPlantilla.consultarPlantilla(plantilla, fechaReferencia);
        if (plantillas.isEmpty()) {
            // Error no existe la plantilla con ese codigo
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001021);
        }
        plantilla = plantillas.get(0);

        PlantillaConfiguracionDTO plantillaConfiguracionDTO = new PlantillaConfiguracionDTO();
        plantillaConfiguracionDTO.setPlantilla(plantilla);
        PlantillaConfiguracionDTO config = iRPlantillaConfiguracion
                .consultarConfiguracionPlantilla(plantillaConfiguracionDTO, false);

        if (config == null) {
            // busca otra plantilla del proceso y toma esa configuracion si existe
            config = iRPlantillaConfiguracion.consultarConfiguracionPlantilla(plantillaConfiguracionDTO, true);

            if (config == null) {
                // Error no existe la configuracion
                throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001022);
            }
        }
        return PlantillaConfiguracionHelper.toPlantillaConfiguracionVO(config);
    }
}
