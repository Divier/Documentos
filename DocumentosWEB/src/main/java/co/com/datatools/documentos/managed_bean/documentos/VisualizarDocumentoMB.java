package co.com.datatools.documentos.managed_bean.documentos;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRDocumento;
import co.com.datatools.util.web.mb.AbstractManagedBean;

/**
 * 
 * @author dixon.alvarez
 * 
 */

@ManagedBean
@SessionScoped
public class VisualizarDocumentoMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(VisualizarDocumentoMB.class.getName());

    private boolean errorGeneracionDocumento;
    private DocumentoDTO documentoVisualizar;
    private String rutaArchivo;
    private String pathRepositorio;

    @EJB
    private IRDocumento irDocumento;

    /**
     * Consulta el documento en el repositorio y lo descarga para visualizarlo
     * 
     * @param documentoDTO
     *            Contiene los datos del documento a buscar
     */
    public void generarDocumento() {
        LOGGER.debug("VisualizarDocumentoMB::generarDocumento");
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            String contextURL = getHttpRequest().getRequestURL().toString()
                    .replace(getHttpRequest().getRequestURI(), "")
                    + getHttpRequest().getContextPath();
            if (documentoVisualizar != null) {
                DocumentoDTO documentoGenerado = irDocumento.obtenerDocumento(documentoVisualizar.getConsecutivoDocumento(), documentoVisualizar.getVersionDocumento());
                // Inicializa el valor de los atributos del MB que muestra la previsualizacion
                setRutaArchivo(null);
                setPathRepositorio(null);
                setErrorGeneracionDocumento(false);
                if (documentoGenerado != null) {
                    // Se actualiza el dialogo para visualizar el documento generado
                    setRutaArchivo(contextURL + "/ReporteServlet");
                    setPathRepositorio(documentoGenerado.getPathDocumento());
                    context.update("frmVisualizaDoc:documento");
                    context.update("frmVisualizaDoc:errores");
                    context.update("frmVisualizaDoc:botones");
                    //Si no es pdf solo se descarga el archivo
                    if(FilenameUtils.isExtension(documentoGenerado.getNombreDocumento(), EnumFormatoDocumento.PDF.getExtension().substring(1))) {
                        context.execute("PF('popupVisualizaDoc').show();jQuery('#iframeVisualiza').css('display', 'block');");                        
                    } 
                } else {
                    // En caso de erro al generar el documento se muestra mensaje de error
                    setErrorGeneracionDocumento(true);
                    context.update("frmVisualizaDoc:documento");
                    context.update("frmVisualizaDoc:errores");
                    context.update("frmVisualizaDoc:botones");
                    context.execute("PF('popupVisualizaDoc').show()");
                }               
            }
        } catch (EJBException e) {
            if (e.getMessage().contains("Object not found")) {
                addErrorMessage("labelDocumentos", "error_documento_no_encontrado");
            }
            LOGGER.error(e.getMessage());
        }

    }

    public boolean isErrorGeneracionDocumento() {
        return errorGeneracionDocumento;
    }

    public void setErrorGeneracionDocumento(boolean errorGeneracionDocumento) {
        this.errorGeneracionDocumento = errorGeneracionDocumento;
    }

    public DocumentoDTO getDocumentoVisualizar() {
        return documentoVisualizar;
    }

    public void setDocumentoVisualizar(DocumentoDTO documentoVisualizar) {
        this.documentoVisualizar = documentoVisualizar;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getPathRepositorio() {
        return pathRepositorio;
    }

    public void setPathRepositorio(String pathRepositorio) {
        this.pathRepositorio = pathRepositorio;
    }
}
