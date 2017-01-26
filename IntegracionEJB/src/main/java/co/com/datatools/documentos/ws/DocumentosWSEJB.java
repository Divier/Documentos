package co.com.datatools.documentos.ws;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.constantes.ConstantesDocumentos;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.interfaces.IDocumentosWS;
import co.com.datatools.documentos.negocio.interfaces.ILIntegracionDocumentos;
import co.com.datatools.documentos.negocio.interfaces.ILIntegracionDocumentosADM;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILUsuarioOrganizacion;
import co.com.datatools.documentos.negocio.interfaces.aud.ILUsuarioSesion;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

/**
 * EJB de integracion por medio de WS
 * 
 * @author julio.pinzon
 * 
 */
@Stateless(name = "DocumentosWSEJB")
@WebService(
        targetNamespace = "http://ws.documentos.datatools.com.co/",
        endpointInterface = "co.com.datatools.documentos.negocio.interfaces.IDocumentosWS",
        portName = "DocumentosWSEJBPort",
        serviceName = "DocumentosWSEJBService")
public class DocumentosWSEJB implements IDocumentosWS {

    /**
     * Codigo de operacion exitosa
     */
    private static final String OPERACION_EXITOSA = "0000";

    /**
     * Logger
     */
    static final Logger logger = Logger.getLogger(DocumentosWSEJB.class.getName());

    @EJB
    private ILIntegracionDocumentos ilIntegracionDocumentos;

    @EJB
    private ILUsuarioOrganizacion ilUsuarioOrganizacion;

    @EJB
    private ILIntegracionDocumentosADM integracionDocumentosADMEJB;

    @EJB
    private ILUsuarioSesion usuarioSesionEJB;

    @Resource
    private WebServiceContext wsContext;

    /**
     * Realiza la autenticacion del usuario.
     * 
     * @param usuario
     *            contiene el usuario y clave para ser autenticados, la clave debe venir cifrada.
     * @return Id del hilo que se esta ejecutando
     * @throws DocumentosWebException
     *             1011: Error de autenticacion de usuario, si la autenticacion del usuario no es exitosa.
     */
    private void autenticarUsuario(UsuarioVO usuario) throws DocumentosWebException {
        try {
            this.ilUsuarioOrganizacion.autenticarUsuarioOrganizacionDTO(usuario.getUsuario(), usuario.getClave());
            String ipWS = null;

            try {
                // Obtiene la ip si viene por WS con el contexto
                if (wsContext != null) {
                    MessageContext mc = wsContext.getMessageContext();
                    HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
                    ipWS = req.getRemoteAddr();
                }
            } catch (IllegalStateException e) {
                ipWS = ConstantesGeneral.LOCALHOST;
            }
            usuarioSesionEJB.almacenarUsuario(usuario.getUsuario(), ipWS);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001011);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public DocumentoGeneradoVO generarDocumento(DocumentoWSVO documentoWSVO, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::generarDocumento");
        DocumentoVO documentoVO = null;
        if (documentoWSVO == null) {
            logger.info("Parametro nulo");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (documentoWSVO.getCodigoPlantilla() == null || documentoWSVO.getCodigoPlantilla().isEmpty()) {
            logger.info("Codigo de plantilla es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (documentoWSVO.getFechaGeneracion() == null || documentoWSVO.getFechaGeneracion().isEmpty()) {
            logger.info("Fecha de generacion es nula o vacia");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (documentoWSVO.getFormato() == null || documentoWSVO.getFormato().isEmpty()) {
            logger.info("Formato es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (documentoWSVO.getValoresPlantilla() == null || documentoWSVO.getValoresPlantilla().isEmpty()) {
            logger.info("ValoresPlantilla es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (documentoWSVO.getDescripcion() != null && !documentoWSVO.getDescripcion().isEmpty()
                && documentoWSVO.getDescripcion().length() > ConstantesDocumentos.TAMANO_MAXIMO_DESCRIPCION) {
            logger.info("Descripcion excede tamano");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001014);
        }

        if (documentoWSVO.getUbicacion() != null && !documentoWSVO.getUbicacion().isEmpty()) {
            if (documentoWSVO.getUbicacion().length() > ConstantesDocumentos.TAMANO_MAXIMO_UBICACION) {
                logger.info("Ubicacion excede tamano");
                throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001015);
            }
            // Validamos el path de la ubicacion
            Pattern pattern = Pattern.compile(ConstantesDocumentos.EXPRESION_REGULAR_UBICACION);
            Matcher matcher = pattern.matcher(documentoWSVO.getUbicacion());
            if (!matcher.matches()) {
                logger.info("Ubicacion formato invalido");
                throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001016);
            }
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);

        try {
            // Objeto para conversion JSON
            Gson gson = new GsonBuilder().setDateFormat(ConstantesGeneral.DATE_FORMAT).create();
            Date fechaGeneracion = DocumentosVOUtil.stringToDate(documentoWSVO.getFechaGeneracion());

            documentoVO = new DocumentoVO();
            documentoVO.setCodigoPlantilla(documentoWSVO.getCodigoPlantilla());
            documentoVO.setDescripcion(documentoWSVO.getDescripcion());
            documentoVO.setFechaGeneracion(fechaGeneracion);
            try {
                documentoVO.setFormato(EnumFormatoDocumento.valueOf(documentoWSVO.getFormato()));
            } catch (IllegalArgumentException e) {
                logger.info("Formato invalido");
                throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001013);
            }
            documentoVO.setUbicacion(documentoWSVO.getUbicacion());
            documentoVO.setUsuario(documentoWSVO.getUsuario());
            documentoVO.setValoresPlantilla(gson.fromJson(documentoWSVO.getValoresPlantilla(), HashMap.class));
            documentoVO.setUsuario(documentoWSVO.getUsuario());

            DocumentoGeneradoVO generarDocumento = ilIntegracionDocumentos.generarDocumento(documentoVO);
            return generarDocumento;
        } catch (JsonSyntaxException e) {
            logger.error("Error en formato de variables de plantilla", e);
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001009);
        } catch (ParseException e) {
            logger.error("Error en formato de fecha de generacion", e);
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001010);
        }

    }

    @Override
    public DocumentoGeneradoVO consultarDocumento(Long codigoDocumento, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::consultarDocumento");

        if (codigoDocumento == null) {
            logger.info("Codigo de documento nulo");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        // Valida usuario
        validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        DocumentoGeneradoVO consultarDocumento = null;
        consultarDocumento = ilIntegracionDocumentos.consultarDocumento(codigoDocumento);
        return consultarDocumento;
    }

    @Override
    public byte[] consultarDocumentosPDF(List<Long> codigosDocumentos, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::consultarDocumentosPDF");

        if (codigosDocumentos == null || codigosDocumentos.isEmpty()) {
            logger.info("Lista de codigosDocumentos es nula o vacia");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        // Valida usuario
        validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        return ilIntegracionDocumentos.consultarDocumentosPDF(codigosDocumentos);
    }

    @Override
    public byte[] consultarDocumentosComprimidos(List<Long> codigosDocumentos, UsuarioVO usuario)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::consultarDocumentosComprimidos");

        if (codigosDocumentos == null || codigosDocumentos.isEmpty()) {
            logger.info("Lista de codigosDocumentos es nula o vacia");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        // Valida usuario
        validarUsuario(usuario);
        this.autenticarUsuario(usuario);
        return ilIntegracionDocumentos.consultarDocumentosComprimidos(codigosDocumentos);
    }

    @Override
    public String consultarVariablesPlantilla(String codigoPlantilla, String fechaGeneracionString, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::consultarVariablesPlantilla");
        if (codigoPlantilla == null || codigoPlantilla.isEmpty()) {
            logger.info("Codigo plantilla es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (fechaGeneracionString == null || fechaGeneracionString.isEmpty()) {
            logger.info("Fecha de generacion es nula o vacia");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        // Valida usuario
        validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ConstantesGeneral.DATE_FORMAT);
            Date fechaGeneracion = sdf.parse(fechaGeneracionString);

            String consultarVariablesPlantilla = ilIntegracionDocumentos.consultarVariablesPlantilla(codigoPlantilla,
                    fechaGeneracion);

            return consultarVariablesPlantilla;
        } catch (ParseException e) {
            logger.error("Error en formato de fecha", e);
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001010);
        }
    }

    @Override
    public String consultarURLDocumentos(List<Long> codigosDocumentos, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::consultarURLDocumentos");

        if (codigosDocumentos == null || codigosDocumentos.isEmpty()) {
            logger.info("Lista de ids es nula o vacia");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        return this.ilIntegracionDocumentos.consultarURLDocumentos(codigosDocumentos);
    }

    /**
     * Valida la informacion del usuario
     * 
     * @throws DocumentosWebException
     *             si es nula o vacia
     */
    private void validarUsuario(UsuarioVO usuario) throws DocumentosWebException {

        if (usuario == null) {
            logger.info("El usuario es nulo");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if ((usuario.getUsuario() == null) || (usuario.getClave() == null) || usuario.getUsuario().isEmpty()
                || usuario.getClave().isEmpty()) {
            logger.info("El usuario invalido, clave o usuario es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

    }

    @Override
    public String registrarCargo(CargoVO cargoVO, UsuarioVO usuarioVO) throws DocumentosWebException {
        // Validar datos de entrada del cargo
        if (StringUtils.isBlank(cargoVO.getNombre())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001077);
        } else if (cargoVO.getNombre().length() > 150) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001078);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        CargoDTO cargoDTO = DocumentosVOUtil.toCargoDTO(cargoVO);

        integracionDocumentosADMEJB.registrarCargo(cargoDTO);
        return OPERACION_EXITOSA;
    }

    @Override
    public String adicionarProcesosCargo(int idCargo, List<Long> procesos, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        // Validar datos de entrada
        if (procesos == null || procesos.isEmpty()) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001079);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        integracionDocumentosADMEJB.adicionarProcesosCargo(idCargo, procesos);
        return OPERACION_EXITOSA;
    }

    @Override
    public String removerProcesosCargo(int idCargo, List<Long> procesos, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        // Validar datos de entrada
        if (procesos == null || procesos.isEmpty()) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001079);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        integracionDocumentosADMEJB.removerProcesosCargo(idCargo, procesos);

        return OPERACION_EXITOSA;
    }

    @Override
    public String registrarProceso(ProcesoVO procesoVO, UsuarioVO usuarioVO) throws DocumentosWebException {

        // Validar datos entrada del proceso
        if (StringUtils.isBlank(procesoVO.getNombreProceso())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001073);
        } else if (procesoVO.getNombreProceso().length() > 70) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001074);
        }
        if (StringUtils.isBlank(procesoVO.getDescripcionProceso())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001075);
        } else if (procesoVO.getDescripcionProceso().length() > 250) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001076);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        ProcesoDTO procesoDTO = DocumentosVOUtil.toProcesoDTO(procesoVO);
        integracionDocumentosADMEJB.registrarProceso(procesoDTO);
        return OPERACION_EXITOSA;
    }

    @Override
    public String registrarUsuario(UsuarioOrganizacionVO usuarioOrganizacionVO, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        // Valida datos del usuario
        if (StringUtils.isBlank(usuarioOrganizacionVO.getLogin())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001080);
        } else if (usuarioOrganizacionVO.getLogin().length() > 60) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001081);
        }
        if (StringUtils.isBlank(usuarioOrganizacionVO.getContrasena())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001088);
        } else if (usuarioOrganizacionVO.getContrasena().length() > 512) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001089);
        }
        // Valida datos de funcionario entrante.
        if (StringUtils.isNotBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())
                && StringUtils.isBlank(usuarioOrganizacionVO.getNumeroDocumentoFuncionario())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001059);
        }
        if (StringUtils.isBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())
                && StringUtils.isNotBlank(usuarioOrganizacionVO.getNumeroDocumentoFuncionario())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001060);
        }
        if (StringUtils.isNotBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())
                && usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario().length() > 40) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001082);
        }
        if (StringUtils.isNotBlank(usuarioOrganizacionVO.getNumeroDocumentoFuncionario())
                && usuarioOrganizacionVO.getNumeroDocumentoFuncionario().length() > 20) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001083);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);

        UsuarioOrganizacionDTO usuarioDTO = DocumentosVOUtil.toUsuarioOrganizacionDTO(usuarioOrganizacionVO);

        integracionDocumentosADMEJB.registrarUsuario(usuarioDTO);
        return OPERACION_EXITOSA;
    }

    @Override
    public String registrarFuncionario(FuncionarioVO funcionarioVO, UsuarioVO usuarioVO) throws DocumentosWebException {
        // Validar datos del funcionario
        if (StringUtils.isBlank(funcionarioVO.getNombreFuncionario())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001084);
        } else if (funcionarioVO.getNombreFuncionario().length() > 125) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001085);
        }
        if (StringUtils.isBlank(funcionarioVO.getSiglaTipoIdentificacion())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001086);
        } else if (funcionarioVO.getSiglaTipoIdentificacion().length() > 6) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001087);
        }
        // Valida datos de funcionario entrante.
        if (StringUtils.isNotBlank(funcionarioVO.getNombreTipoIdentificacion())
                && StringUtils.isBlank(funcionarioVO.getNumeroDocumIdent())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001059);
        }
        if (StringUtils.isBlank(funcionarioVO.getNombreTipoIdentificacion())
                && StringUtils.isNotBlank(funcionarioVO.getNumeroDocumIdent())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001060);
        }
        if (StringUtils.isNotBlank(funcionarioVO.getNombreTipoIdentificacion())
                && funcionarioVO.getNombreTipoIdentificacion().length() > 40) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001082);
        }
        if (StringUtils.isNotBlank(funcionarioVO.getNumeroDocumIdent())
                && funcionarioVO.getNumeroDocumIdent().length() > 20) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001083);
        }
        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        FuncionarioDTO funcionarioDTO = DocumentosVOUtil.toFuncionarioDTO(funcionarioVO);

        integracionDocumentosADMEJB.registrarFuncionario(funcionarioDTO);
        return OPERACION_EXITOSA;
    }

    @Override
    public String actualizarFirma(FirmaVO firmaVO, UsuarioVO usuarioVO) throws DocumentosWebException {
        // Valida usuario
        if (StringUtils.isBlank(firmaVO.getNumeroDocumento())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001059);
        }
        if (StringUtils.isBlank(firmaVO.getTipoDocumento())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001060);
        }
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);

        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setNumeroDocumIdent(firmaVO.getNumeroDocumento());
        funcionarioDTO.setNombreTipoIdentificacion(firmaVO.getTipoDocumento());
        funcionarioDTO.setFirma(firmaVO.getFirma());

        integracionDocumentosADMEJB.actualizarFirma(funcionarioDTO);
        return OPERACION_EXITOSA;
    }

    @Override
    public String actualizarUsuario(UsuarioOrganizacionVO usuarioOrganizacionVO, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        // Valida datos del usuario
        if (StringUtils.isBlank(usuarioOrganizacionVO.getLogin())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001080);
        } else if (usuarioOrganizacionVO.getLogin().length() > 60) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001081);
        }
        if (StringUtils.isBlank(usuarioOrganizacionVO.getContrasena())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001088);
        } else if (usuarioOrganizacionVO.getContrasena().length() > 512) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001089);
        }
        // Valida datos de funcionario entrante.
        if (StringUtils.isNotBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())
                && StringUtils.isBlank(usuarioOrganizacionVO.getNumeroDocumentoFuncionario())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001059);
        }
        if (StringUtils.isBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())
                && StringUtils.isNotBlank(usuarioOrganizacionVO.getNumeroDocumentoFuncionario())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001060);
        }
        if (StringUtils.isNotBlank(usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario())
                && usuarioOrganizacionVO.getNombreTipoDocumentoFuncionario().length() > 40) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001082);
        }
        if (StringUtils.isNotBlank(usuarioOrganizacionVO.getNumeroDocumentoFuncionario())
                && usuarioOrganizacionVO.getNumeroDocumentoFuncionario().length() > 20) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001083);
        }
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        integracionDocumentosADMEJB.actualizarUsuario(DocumentosVOUtil.toUsuarioOrganizacionDTO(usuarioOrganizacionVO));
        return OPERACION_EXITOSA;
    }

    @Override
    public String actualizarFuncionario(FuncionarioVO funcionarioVO, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        // Valida datos de funcionario entrante.
        if (StringUtils.isBlank(funcionarioVO.getNumeroDocumIdent())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001059);
        }
        if (StringUtils.isBlank(funcionarioVO.getNombreTipoIdentificacion())) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001060);
        }
        if (funcionarioVO.getNombreTipoIdentificacion().length() > 40) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001082);
        }
        if (funcionarioVO.getNumeroDocumIdent().length() > 20) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001083);
        }
        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);
        FuncionarioDTO funcionarioDTO = DocumentosVOUtil.toFuncionarioDTO(funcionarioVO);
        integracionDocumentosADMEJB.actualizarFuncionario(funcionarioDTO);

        return OPERACION_EXITOSA;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DocumentoGeneradoVO actualizarDocumento(DocumentoEditadoVO documentoEditadoVO, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::actualizarDocumento");
        DocumentoVO documentoVO = null;
        if (documentoEditadoVO == null) {
            logger.info("Parametro nulo");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (documentoEditadoVO.getCodigoDocumento() == null) {
            logger.info("Codigo de documento es nulo");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (documentoEditadoVO.getValoresPlantilla() == null || documentoEditadoVO.getValoresPlantilla().isEmpty()) {
            logger.info("ValoresPlantilla es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);

        try {
            // Objeto para conversion JSON
            Gson gson = new GsonBuilder().setDateFormat(ConstantesGeneral.DATE_FORMAT).create();

            documentoVO = new DocumentoVO();
            documentoVO.setCodigoDocumento(documentoEditadoVO.getCodigoDocumento());
            documentoVO.setUsuario(documentoEditadoVO.getUsuario());
            documentoVO.setValoresPlantilla(gson.fromJson(documentoEditadoVO.getValoresPlantilla(), HashMap.class));

            DocumentoGeneradoVO actualizarDocumento = ilIntegracionDocumentos.actualizarDocumento(documentoVO);

            return actualizarDocumento;
        } catch (JsonSyntaxException e) {
            logger.error("Error en formato de variables de plantilla", e);
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001009);
        }
    }

    @Override
    public List<PlantillaVO> consultarPlantillas(String codigoProceso, UsuarioVO usuarioVO)
            throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::consultarPlantillas(String, UsuarioVO)");

        if (StringUtils.isBlank(codigoProceso)) {
            logger.info("Codigo del proceso es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);

        return ilIntegracionDocumentos.consultarPlantillas(codigoProceso);
    }

    @Override
    public PlantillaConfiguracionVO consultarConfiguracionPlantilla(String codigoPlantilla, Date fechaReferencia,
            UsuarioVO usuarioVO) throws DocumentosWebException {
        logger.debug("DocumentosWSEJB::consultarConfiguracionPlantilla(String, Date, UsuarioVO)");

        if (StringUtils.isBlank(codigoPlantilla)) {
            logger.info("Codigo de la plantilla es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if (fechaReferencia == null) {
            logger.info("Fecha de referencia es nula");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        // Valida usuario
        this.validarUsuario(usuarioVO);
        this.autenticarUsuario(usuarioVO);

        return ilIntegracionDocumentos.consultarConfiguracionPlantilla(codigoPlantilla, fechaReferencia);
    }

}
