package co.com.datatools.documentos.managed_bean.documentos;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import co.com.datatools.documentos.constantes.ConstantesDocumentos;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRParametroSistema;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRDocumento;
import co.com.datatools.documentos.util.ConstantesManagedBean;
import co.com.datatools.util.web.mb.AbstractManagedBean;

/**
 * 
 * @author dixon.alvarez
 * 
 */

@ManagedBean
@SessionScoped
public class ConsultarHistoricoDocumentosMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(ConsultarHistoricoDocumentosMB.class.getName());

    private DocumentoDTO documentoBuscar;
    private boolean tablaVisible;
    private List<DocumentoDTO> lstDocumentoDTOs;
    private String consecutivo;
    private String descripcionDocumento;
    private Date fechaCreaDocInicial;
    private Date fechaCreaDocFinal;
    private Boolean estadoValidacionFiltros;
    private Long diasPermitidosRangoFecha;

    /**
     * Utilizado para tomar el documento seleccionado
     */
    private DocumentoDTO documentoSeleccionado;

    @EJB
    private IRDocumento irDocumento;
    @EJB
    private IRParametroSistema ilParametroSistema;

    public ConsultarHistoricoDocumentosMB() {
        if (documentoBuscar == null) {
            documentoBuscar = new DocumentoDTO();
        }
    }

    /**
     * Consulta los documentos que existen en el sistema
     * 
     * @author dixon.alvarez
     */
    public void consultar() {
        LOGGER.debug("ConsultarHistoricoDocumentosMB::consultar");
        estadoValidacionFiltros = null;
        documentoBuscar.setConsecutivoDocumento(null);
        documentoBuscar.setDescripcionDocumento(null);
        documentoBuscar.setFechaCreacionDesde(null);
        documentoBuscar.setFechaCreacionHasta(null);

        tablaVisible = false;
        documentoSeleccionado = null;
        lstDocumentoDTOs = null;
        if (!validarFiltros()) {
            return;
        }
        lstDocumentoDTOs = irDocumento.consultarDocumento(documentoBuscar);
        tablaVisible = true;
    }

    /**
     * Metodo que permite validar los filtros de la consulta
     * 
     * @return Boolean true si los valores en los filtros ingresados son validos, false en caso contrario
     */
    private Boolean validarFiltros() {
        LOGGER.debug("ConsultarHistoricoDocumentosMB::validarFiltros");
        validarConsecutivoDocumento();
        validarDescripcionDocumento();
        validarFechas();

        if (estadoValidacionFiltros == null) {
            addErrorMessage(ConstantesDocumentos.NOMBRE_BUNDLE_DOCUMENTOS, "msg_filtros_requeridos");
        } else if (estadoValidacionFiltros) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que permite validar que el numero de consecutivo del documento ingresado sea valido
     */
    private void validarConsecutivoDocumento() {
        if (StringUtils.isNotBlank(consecutivo)) {
            consecutivo = consecutivo.trim();
            try {
                documentoBuscar.setConsecutivoDocumento(Long.valueOf(consecutivo));
                if (documentoBuscar.getConsecutivoDocumento() <= 0) {
                    documentoBuscar.setConsecutivoDocumento(null);
                    getFacesContext()
                            .addMessage(
                                    "form-contenido:txtConsecutivo",
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                                            getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL)
                                                    .getString("numero_mayor"), "0")));
                    estadoValidacionFiltros = false;
                } else {
                    estadoValidacionFiltros = true;
                }
            } catch (NumberFormatException e) {
                getFacesContext().addMessage(
                        "form-contenido:txtConsecutivo",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getBundle(
                                ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("msg_convertir_numero_error")));
                estadoValidacionFiltros = false;
            }
        }
    }

    /**
     * Metodo que permite validar si fue ingresada la descripcion del documento
     */
    private void validarDescripcionDocumento() {
        int minimoCaracteres = 3;
        String msg;
        if (verificarEstadoValidacion()) {
            if (StringUtils.isNotBlank(descripcionDocumento)) {
                descripcionDocumento = descripcionDocumento.trim();
                if (descripcionDocumento.length() < minimoCaracteres) {
                    msg = getBundle(ConstantesDocumentos.NOMBRE_BUNDLE_DOCUMENTOS).getString(
                            "min_caracteres_descripcion_documento");
                    getFacesContext().addMessage("form-contenido:txtDescripcionDocumento",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
                    estadoValidacionFiltros = false;
                } else {
                    documentoBuscar.setDescripcionDocumento(descripcionDocumento);
                    estadoValidacionFiltros = true;
                }
            }
        }
    }

    /**
     * Metodo que permite validar si las fecha ingresadas son correctas y se encuentra el rango completamente identificado
     */
    private void validarFechas() {
        String msg;
        if (verificarEstadoValidacion()) {
            if (fechaCreaDocInicial != null && fechaCreaDocFinal != null) {
                Calendar fechaInicio = Calendar.getInstance();
                fechaInicio.setTime(fechaCreaDocInicial);
                Calendar fechaFin = Calendar.getInstance();
                fechaFin.setTime(fechaCreaDocFinal);
                if (fechaFin.before(fechaInicio)) {
                    msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("rango_fecha_mayor");
                    getFacesContext().addMessage("form-contenido:calFechaCreaDocFinal",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
                    estadoValidacionFiltros = false;
                } else if (!verificarNumeroDiasRangoFechas()) {
                    String msgResaltado = getBundle(ConstantesDocumentos.NOMBRE_BUNDLE_DOCUMENTOS).getString(
                            "max_dias_permitidos_consulta");
                    msg = getBundle(ConstantesDocumentos.NOMBRE_BUNDLE_DOCUMENTOS).getString("max_dias_complemento");
                    msg = String.format(msg, diasPermitidosRangoFecha);
                    getFacesContext()
                            .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgResaltado, msg));
                    estadoValidacionFiltros = false;
                } else {
                    documentoBuscar.setFechaCreacionDesde(fechaCreaDocInicial);
                    documentoBuscar.setFechaCreacionHasta(fechaCreaDocFinal);
                    estadoValidacionFiltros = true;
                }
            } else if (fechaCreaDocInicial == null && fechaCreaDocFinal != null) {
                msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
                getFacesContext().addMessage("form-contenido:calFechaCreaDocInicial",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
                estadoValidacionFiltros = false;
            } else if (fechaCreaDocFinal == null && fechaCreaDocInicial != null) {
                msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
                getFacesContext().addMessage("form-contenido:calFechaCreaDocFinal",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
                estadoValidacionFiltros = false;
            }
        }
    }

    /**
     * Metodo que permite verificar si algun filtro no ha sido ingresado, o si lo fue, su valor sea valido
     * 
     * @return Boolean true si ningun filtro ha sido ingresado o si ya han sido ingresados, sus valores sean correctos, false en caso que no se de lo
     *         anterior.
     */
    private Boolean verificarEstadoValidacion() {
        if (estadoValidacionFiltros == null || estadoValidacionFiltros == true) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que permite validar si el rango de fechas ingresado es valido para realizar la consulta
     * 
     * @return Boolean true si el rango de fechas se encuentra dentro de los dias permitidos, false en caso contarrio
     */
    private Boolean verificarNumeroDiasRangoFechas() {
        diasPermitidosRangoFecha = Long.valueOf(ilParametroSistema
                .consultarValorParametroSistema(EnumParametrosSistema.DIAS_PERMITIDOS_RANGO_FECHA));
        long diferencia = fechaCreaDocFinal.getTime() - fechaCreaDocInicial.getTime();
        diferencia = TimeUnit.DAYS.convert(diferencia, TimeUnit.MILLISECONDS);
        if (diferencia <= diasPermitidosRangoFecha) {
            return true;
        }
        return false;
    }

    /**
     * Permite preparar los datos para mostrar la pantalla de edicion de documentos
     * 
     * @return navegacion a la pagina solicitada
     * 
     */
    public void irEditarDocumento() {
        LOGGER.debug("ConsultarHistoricoDocumentosMB::irEditarDocumento");

        // Verificamos si es la ultima version
        DocumentoDTO ultimoDocumento = irDocumento.consultarUltimaVersionDocumento(documentoSeleccionado);
        if (ultimoDocumento.getIdDocumento() != documentoSeleccionado.getIdDocumento()) {
            addErrorMessage(ConstantesDocumentos.NOMBRE_BUNDLE_DOCUMENTOS,
                    ConstantesDocumentos.LLAVE_MSG_ERROR_ULTIMA_VERSION);
            return;
        }
        addSessionObject(ConstantesManagedBean.NOMBRE_OBJ_DOCUMENTO, documentoSeleccionado);
        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_REGISTRA_DOCUMENTO);
        // return ConstantesManagedBean.RUTA_EDITA_DOCUMENTO;

        // addSessionObject(ConstantesManagedBean.NOMBRE_OBJ_DOCUMENTO, documentoVisualizar);
        //
        // removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_REGISTRA_DOCUMENTO);

        try {

            getFacesContext().getExternalContext().redirect(ConstantesManagedBean.RUTA_EDITA_DOCUMENTO);
        } catch (IOException e) {
            LOGGER.error("Error al redirigir pagina", e);
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);

        }

    }

    public DocumentoDTO getDocumentoBuscar() {
        return documentoBuscar;
    }

    public void setDocumentoBuscar(DocumentoDTO documentoBuscar) {
        this.documentoBuscar = documentoBuscar;
    }

    public boolean isTablaVisible() {
        return tablaVisible;
    }

    public void setTablaVisible(boolean tablaVisible) {
        this.tablaVisible = tablaVisible;
    }

    public List<DocumentoDTO> getLstDocumentoDTOs() {
        return lstDocumentoDTOs;
    }

    public void setLstDocumentoDTOs(List<DocumentoDTO> lstDocumentoDTOs) {
        this.lstDocumentoDTOs = lstDocumentoDTOs;
    }

    public DocumentoDTO getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(DocumentoDTO documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    public void setDescripcionDocumento(String descripcionConsecutivo) {
        this.descripcionDocumento = descripcionConsecutivo;
    }

    public Date getFechaCreaDocInicial() {
        return fechaCreaDocInicial;
    }

    public void setFechaCreaDocInicial(Date fechaCreaDocInicial) {
        this.fechaCreaDocInicial = fechaCreaDocInicial;
    }

    public Date getFechaCreaDocFinal() {
        return fechaCreaDocFinal;
    }

    public void setFechaCreaDocFinal(Date fechaCreaDocFinal) {
        this.fechaCreaDocFinal = fechaCreaDocFinal;
    }
}
