package co.com.datatools.documentos.managed_bean.firma_documento;

/**
 * @author dixon.alvarez
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.constantes.ConstantesDocumentos;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.DocumentoRepositorioDTO;
import co.com.datatools.documentos.documentos.FirmaDigitalDocumentoDTO;
import co.com.datatools.documentos.documentos.XmlDocumentoDTO;
import co.com.datatools.documentos.managed_bean.comun.PrincipalMB;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCatalogo;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRFirmaDigitalDocumento;
import co.com.datatools.documentos.util.ConstantesManagedBean;
import co.com.datatools.util.web.mb.AbstractManagedBean;

@ManagedBean
@SessionScoped
public class FirmarDigitalmenteDocumentoMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(FirmarDigitalmenteDocumentoMB.class.getName());

    private UsuarioOrganizacionDTO usuarioOrganizacionSesion;
    private FacesContext fc;
//    private ManejadorSesion manejadorSesion;
    private String usuarioCertificado;
    private String contrasenaCertificado;
    private UploadedFile certificadoDigital;
    private DocumentoDTO documentoFirmar;
    private String rutaArchivoFirmar;

    @EJB
    private IRCatalogo irCatalogo;

    @EJB
    private IRFuncionario irFuncionario;

    @EJB
    private IRDocumento irDocumento;

    @EJB
    private IRFirmaDigitalDocumento irFirmaDigitalDocumento;

    @ManagedProperty("#{principalMB}")
    private PrincipalMB principalMB;

    /**
     * Bundle de mensajes
     */
    private ResourceBundle bundle;

    public FirmarDigitalmenteDocumentoMB() {
    }

    /**
     * Inicializa los valores del Bean
     */
    @PostConstruct
    public void init() {
        logger.debug("ConfigurarFirmaDocumentoMB::init");
        // UsuarioOrganizacion sesion
        usuarioOrganizacionSesion = findSessionObject(
                ConstantesManagedBean.CLASE_OBJ_USUARIO_ORGANIZACION,
                    ConstantesManagedBean.NOMBRE_OBJ_USUARIO_ORGANIZACION);
    }

    /**
     * Inicializa los valores del documento que se va firmar
     * 
     * @param documentoDTO
     *            Contiene los datos del documento a firmar
     * @param rutaArchivoFirmar
     *            Ruta del archivo que se esta visualizando
     * @author dixon.alvarez
     */
    public void inicializarDatos(DocumentoDTO documentoDTO, String rutaArchivoFirmar) {
        logger.debug("ConfigurarFirmaDocumentoMB::inicializarDatos");
//        try {
            this.rutaArchivoFirmar = rutaArchivoFirmar;
            documentoFirmar = irDocumento.consultarDocumentoId(documentoDTO.getIdDocumento());
//        } catch (ExceptionEJB e) {
//            logger.error("Error: " + e.getMessage());
//        }
    }

    /**
     * Obtener el bundle de mensajes
     * 
     * @return archivo de propiedades
     * @author dixon.alvarez
     */
    public ResourceBundle getBundle() {
        fc = FacesContext.getCurrentInstance();
        if (bundle == null) {
            bundle = fc.getApplication().getResourceBundle(fc, "labelDocumentos");
        }
        return bundle;
    }

    /**
     * Funcion que cancela la operacion de crear plantilla y reinicializa los valores de firmarDigitalmenteDocumento
     * 
     * @author dixon.alvarez
     */
    public void cancelar() {
        logger.debug("ConfigurarFirmaDocumentoMB::cancelar");
        certificadoDigital = null;
        usuarioCertificado = null;
        contrasenaCertificado = null;
    }

    /**
     * Asigna el archivo seleccionado a la variable certificado digital
     * 
     * @param event
     * @author dixon.alvarez
     */
    public void handleFileUpload(FileUploadEvent event) {
        logger.debug("FirmarDigitalmenteDocumentoMB::handleFileUpload");
        certificadoDigital = event.getFile();
    }

    /**
     * Guarda el documento firmado digitalmente para una plantilla
     * 
     * @return
     * @author dixon.alvarez
     */
    public String guardarFirmaDigital() {
        logger.debug("FirmarDigitalmenteDocumentoMB::guardarFirmaDigital");
        fc = FacesContext.getCurrentInstance();
        FileInputStream archivoCertificado = null;
        FileOutputStream out = null;
        try {
            File archivoFirmar = new File(this.rutaArchivoFirmar);
            byte[] archivoFirmadoInBytes = null;
            // activar cuando se tenga certificado para probar
            if (usuarioCertificado != null && !usuarioCertificado.isEmpty() && contrasenaCertificado != null
                    && !contrasenaCertificado.isEmpty() && certificadoDigital != null) {
                // archivoFirmadoInBytes = FirmaDigitalUtil.firmarPdf(certificadoDigital.getContents(),
                // usuarioCertificado, contrasenaCertificado, archivoFirmar);
                // Quitar cuando se este probando con certificado
                archivoCertificado = new FileInputStream(this.rutaArchivoFirmar);
                archivoFirmadoInBytes = IOUtils.toByteArray(archivoCertificado);
                String rutaArchivoFirmado = archivoFirmar.getParent();
                // Documento a guardar
                DocumentoDTO documentoFirmado = new DocumentoDTO();
                documentoFirmado.setDescripcionDocumento(documentoFirmar.getDescripcionDocumento());
//                documentoFirmado.setConsecutivoDocumento(documentoFirmar.getConsecutivoDocumento());
                documentoFirmado.setDocumentoOrigenDTO(documentoFirmar);
                documentoFirmado.setFechaGeneracion(new Date());
                documentoFirmado.setFirmado(true);
                documentoFirmado.setPlantillaDTO(documentoFirmar.getPlantillaDTO());
                documentoFirmado.setUsuarioOrganizacionDTO(usuarioOrganizacionSesion);
                documentoFirmado.setVersionDocumento(documentoFirmar.getVersionDocumento() + 1);
//                documentoFirmado.setNombreDocumento(documentoFirmar.getConsecutivoDocumento() + "_v"
//                        + (documentoFirmar.getVersionDocumento() + 1) + ".pdf");
                File archivoFirmado = new File(rutaArchivoFirmado, documentoFirmado.getNombreDocumento());
                out = new FileOutputStream(archivoFirmado);
                out.write(archivoFirmadoInBytes);
                DocumentoRepositorioDTO documentoRepositorioDTO = new DocumentoRepositorioDTO();
                documentoRepositorioDTO.setDocumentoDTO(documentoFirmado);
                documentoRepositorioDTO.setTipoArchivoDocumento(ConstantesDocumentos.TIPO_ARCHIVO_DOCUMENTO_PDF);
                documentoRepositorioDTO.setRutaArchivoDocumento(archivoFirmado.getAbsolutePath());
//                String documentoId = irGestorArchivos.guardarDocumentoRepositorio(documentoRepositorioDTO);
//                documentoFirmado.setPathDocumento(documentoId);

                // Guardar documento firmado
                XmlDocumentoDTO xmlDocumentoDTO = new XmlDocumentoDTO();
                xmlDocumentoDTO.setContenidoXml(documentoFirmar.getXmlDocumentoDTO().getContenidoXml());
                xmlDocumentoDTO.setNombreXmlDoc(documentoFirmado.getNombreDocumento());
//                xmlDocumentoDTO.setIdXmlDocumento(documentoFirmado.getPathDocumento());
                documentoFirmado.setXmlDocumentoDTO(xmlDocumentoDTO);
                documentoFirmado = irDocumento.registrarDocumento(documentoFirmado);

                // Guardar firma digital
                FirmaDigitalDocumentoDTO firmaDigitalDocumentoDTO = new FirmaDigitalDocumentoDTO();
                firmaDigitalDocumentoDTO.setDocumentoDTO(documentoFirmado);
                firmaDigitalDocumentoDTO.setFechaFirma(new Date());
                firmaDigitalDocumentoDTO.setFuncionarioDTO(usuarioOrganizacionSesion.getFuncionarioDTO());
                firmaDigitalDocumentoDTO.setPathDocumentoFirmado(documentoFirmado.getPathDocumento());
                irFirmaDigitalDocumento.registrarFirmaDigitalDocumento(firmaDigitalDocumentoDTO);

                // Generar el popup de confirmacion de guardado
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("popupfirmaDigitalDocumento.hide()");
                context.execute("popupConfirmRegistraDatosFirmaDigital.show()");
                // archivoFirmado.delete();
                return null;
            } else {
                fc.addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", getBundle().getString(
                                "msg_error_archivo_certificado_obligatorio")));
                return null;
            }
        } catch (IOException e) {
            logger.error("Error: " + e.getMessage());
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        } finally {
            try {
                if(archivoCertificado != null) {
                    archivoCertificado.close();
                }
                if(out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("Error: " + e.getMessage());
            }
        }
        //catch (ExceptionEJB e) {
//            logger.error("Error: " + e.getMessage());
//            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
//        }
        return null;
    }

    public PrincipalMB getPrincipalMB() {
        return principalMB;
    }

    public void setPrincipalMB(PrincipalMB principalMB) {
        this.principalMB = principalMB;
    }

    public String getUsuarioCertificado() {
        return usuarioCertificado;
    }

    public void setUsuarioCertificado(String usuarioCertificado) {
        this.usuarioCertificado = usuarioCertificado;
    }

    public String getContrasenaCertificado() {
        return contrasenaCertificado;
    }

    public void setContrasenaCertificado(String contrasenaCertificado) {
        this.contrasenaCertificado = contrasenaCertificado;
    }

    public UploadedFile getCertificadoDigital() {
        return certificadoDigital;
    }

    public void setCertificadoDigital(UploadedFile certificadoDigital) {
        this.certificadoDigital = certificadoDigital;
    }

    public DocumentoDTO getDocumentoFirmar() {
        return documentoFirmar;
    }

    public void setDocumentoFirmar(DocumentoDTO documentoFirmar) {
        this.documentoFirmar = documentoFirmar;
    }

    public String getRutaArchivoFirmar() {
        return rutaArchivoFirmar;
    }

    public void setRutaArchivoFirmar(String rutaArchivoFirmar) {
        this.rutaArchivoFirmar = rutaArchivoFirmar;
    }

}
