package co.com.datatools.documentos.managed_bean.plantillas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;

import co.com.datatools.c2.bundle.comun.EnumParametrosWeb;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.managed_bean.documentos.VisualizarDocumentoMB;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRPlantilla;
import co.com.datatools.documentos.plantillas.PlantillaConsultaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.util.ConstantesManagedBean;
import co.com.datatools.util.web.mb.AbstractManagedBean;

@ManagedBean
@SessionScoped
public class ConsultaPlantillaMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(ConsultaPlantillaMB.class.getName());

    /**
     * Lista de plantillas
     */
    private List<PlantillaDTO> plantillas;

    /**
     * Objecto utilizado para realizar la consulta de plantillas
     */
    private PlantillaConsultaDTO plantillaConsulta;

    /**
     * Representa la plantilla seleccionada
     */
    private PlantillaDTO plantillaSeleccionadaFechas;

    /**
     * Representa la plantilla seleccionada
     */
    private PlantillaDTO plantillaSeleccionadaGeneral;

    /**
     * Representa la plantilla seleccionada del historico
     */
    private PlantillaDTO plantillaSeleccionadaHistorico;

    /**
     * Lista de plantillas historico de versiones
     */
    private List<PlantillaDTO> plantillasHistorico;

    /**
     * Url de contexto
     */
    private String contextURL;

    /**
     * Utilizado para mostrar la tabla de resultados
     */
    private boolean mostrarTabla;

    /**
     * Fecha minima de plantilla
     */
    private Date fecMinPlantilla;

    /**
     * Formato de fecha
     */
    private SimpleDateFormat formatoFecha;

    /**
     * UsuarioOrganizacion del sistema
     */
    private UsuarioOrganizacionDTO usuarioOrganizacionSesion;

    @EJB
    private IRPlantilla irPlantilla;

    @EJB
    private IRDocumento irDocumento;

    /**
     * Bean de generar documento
     */
    @ManagedProperty(value = "#{visualizarDocumentoMB}")
    private VisualizarDocumentoMB visualizaDocumento;

    @PostConstruct
    public void init() {
        LOGGER.debug("ConsultaPlantillaMB::init");

        // Colocar usuario logueado
        usuarioOrganizacionSesion = findSessionObject(ConstantesManagedBean.CLASE_OBJ_USUARIO_ORGANIZACION,
                ConstantesManagedBean.NOMBRE_OBJ_USUARIO_ORGANIZACION);

        PlantillaDTO plantillaDTO = new PlantillaDTO();
        ProcesoDTO procesoDTO = new ProcesoDTO();
        plantillaDTO.setProcesoDTO(procesoDTO);

        this.plantillaConsulta = new PlantillaConsultaDTO();
        this.plantillaConsulta.setPlantillaDTO(plantillaDTO);
        // Quitar la plantilla de la sesion
        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_PLANTILLA_DOCUMENTOS);

        // No pueden existir fechas anteriores a la actual
        // Fecha inicial minima de la plantilla
        Calendar calMinPlantilla = Calendar.getInstance();
        calMinPlantilla.add(Calendar.DAY_OF_MONTH, 1);
        fecMinPlantilla = calMinPlantilla.getTime();

        // Pone el formato que debe tener la fecha para mostrar en mensajes
        ResourceBundle bundle = getBundle(EnumParametrosWeb.getBundleName());
        formatoFecha = new SimpleDateFormat(bundle.getString(EnumParametrosWeb.lab_calendar_pattern.toString()));

    }

    public void consultar() {
        LOGGER.debug("ConsultaPlantillaMB::consultar");
        plantillas = new ArrayList<PlantillaDTO>();
        mostrarTabla = false;
        plantillaSeleccionadaGeneral = null;
        String msg;
        if (plantillaConsulta.getFechaInicioDesde() != null && plantillaConsulta.getFechaInicioHasta() == null) {
            msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
            getFacesContext().addMessage("form-contenido:calfechaInicialHasta",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            return;
        } else if (plantillaConsulta.getFechaInicioDesde() == null && plantillaConsulta.getFechaInicioHasta() != null) {
            msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
            getFacesContext().addMessage("form-contenido:calfechaInicialDesde",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            return;
        } else if (plantillaConsulta.getFechaInicioDesde() != null && plantillaConsulta.getFechaInicioHasta() != null) {
            // VALIDAR FECHAS
            Calendar fechaInicio = Calendar.getInstance();
            fechaInicio.setTime(plantillaConsulta.getFechaInicioDesde());
            Calendar fechaFin = Calendar.getInstance();
            fechaFin.setTime(plantillaConsulta.getFechaInicioHasta());
            if (fechaFin.before(fechaInicio)) {
                msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("rango_fecha_mayor");
                getFacesContext().addMessage("form-contenido:calfechaInicialHasta",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
                return;
            }
        }

        if (plantillaConsulta.getFechaFinDesde() != null && plantillaConsulta.getFechaFinHasta() == null) {
            msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
            getFacesContext().addMessage("form-contenido:calfechaFinHasta",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            return;
        } else if (plantillaConsulta.getFechaFinDesde() == null && plantillaConsulta.getFechaFinHasta() != null) {
            msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
            getFacesContext().addMessage("form-contenido:calfechaFinDesde",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            return;
        } else if (plantillaConsulta.getFechaFinDesde() != null && plantillaConsulta.getFechaFinHasta() != null) {
            // VALIDAR FECHAS
            Calendar fechaInicio = Calendar.getInstance();
            fechaInicio.setTime(plantillaConsulta.getFechaFinDesde());
            Calendar fechaFin = Calendar.getInstance();
            fechaFin.setTime(plantillaConsulta.getFechaFinHasta());
            if (fechaFin.before(fechaInicio)) {
                msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("rango_fecha_mayor");
                getFacesContext().addMessage("form-contenido:calfechaFinHasta",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
                return;
            }
        }
        plantillaConsulta.setValidaOrigen(false);
        plantillaConsulta.setUltimaVersion(true);
        plantillas = irPlantilla.consultarPlantilla(plantillaConsulta);
        mostrarTabla = true;

    }

    public String estadoPlantilla(boolean estado) {
        LOGGER.debug("ConsultaPlantillaMB::estadoPlantilla");
        String textoEstado = getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                "estado_activo_plantilla");
        if (!estado) {
            textoEstado = getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                    "estado_inactivo_plantilla");
        }
        return textoEstado;
    }

    public void editarPlantilla(PlantillaDTO plantillaEditar, boolean editar) {
        LOGGER.debug("ConsultaPlantillaMB::editarPlantilla");
        if(!editar || !deshabilitarEditar(plantillaEditar)) {
            plantillaEditar.setEditar(editar);
            addSessionObject(ConstantesManagedBean.NOMBRE_OBJ_PLANTILLA_DOCUMENTOS, plantillaEditar);

            removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_REGISTRA_PLANTILLA);
            removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_FIRMA_PLANTILLA);
            try {
                getFacesContext().getExternalContext().redirect(ConstantesManagedBean.RUTA_REGISTRA_PLANTILLA);
                plantillaSeleccionadaGeneral = null;
                plantillaSeleccionadaHistorico = null;
            } catch (IOException e) {
                LOGGER.error("Error al redirigir pagina", e);
                addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
            }            
        } else {
            addErrorMessage(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS, ConstantesPlantillas.LLAVE_MSG_ERROR_FECHA_INICIO_EDITAR);            
        }
    }

    public void registrarPlantilla() {
        LOGGER.debug("ConsultaPlantillaMB::editarPlantilla");

        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_PLANTILLA_DOCUMENTOS);
        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_REGISTRA_PLANTILLA);
        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_CONSULTA_PLANTILLA);
        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_FIRMA_PLANTILLA);
        try {
            getFacesContext().getExternalContext().redirect(ConstantesManagedBean.RUTA_REGISTRA_PLANTILLA);
            plantillaSeleccionadaGeneral = null;
            plantillaSeleccionadaHistorico = null;
        } catch (IOException e) {
            LOGGER.error("Error al redirigir pagina", e);
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
        }
    }

    public void verHistorico() {
        LOGGER.debug("ConsultaPlantillaMB::verHistorico");
        plantillaSeleccionadaHistorico = null;
        plantillasHistorico = new ArrayList<PlantillaDTO>();
        RequestContext context = RequestContext.getCurrentInstance();
        // LLena objeto de consulta
        if (plantillaSeleccionadaGeneral.getPlantillaOrigenDTO() != null) {
            PlantillaConsultaDTO plantillaConsulta = new PlantillaConsultaDTO();
            PlantillaDTO plantillaTemp = new PlantillaDTO();
            plantillaTemp.setPlantillaOrigenDTO(plantillaSeleccionadaGeneral.getPlantillaOrigenDTO());
            plantillaConsulta.setPlantillaDTO(plantillaTemp);
            plantillaConsulta.setValidaOrigen(true);
            plantillaConsulta.setUltimaVersion(false);
            plantillasHistorico = irPlantilla.consultarPlantilla(plantillaConsulta);
        } else {
            // coloca el objeto final
            plantillasHistorico.add(plantillaSeleccionadaGeneral);
        }
        context.execute("PF('historicoDialog').show()");
    }

    /**
     * Se encarga de mostrar el popup de detalle de cambio fechas
     * 
     * @param plantillaSeleccionada
     * @param historico
     *            indica si se va a eliminar una plantilla del historico
     */
    public void eliminar(PlantillaDTO plantillaSeleccionada, boolean historico) {
        LOGGER.debug("ConsultaPlantillaMB::eliminar");
        if(!deshabilitarEliminar(plantillaSeleccionada)) {
            irPlantilla.eliminarPlantilla(new Integer(plantillaSeleccionada.getIdPlantilla()));
            plantillaSeleccionadaHistorico = null;
            plantillaSeleccionadaGeneral = null;
            plantillas = irPlantilla.consultarPlantilla(plantillaConsulta);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form-resultado:tabla-resultados");
            context.update("form-popup-historico-plantilla:tabla-resultados");
            context.update("form-resultado:acciones-top");
            context.update("form-popup-historico-plantilla:acciones-top");
            context.execute("PF('historicoDialog').hide()");
            // Mensaje de exito al guardar
            addInfoMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, "msg_exito_eliminar");          
        } else {
            addErrorMessage(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS, ConstantesPlantillas.LLAVE_MSG_ERROR_FECHA_INICIO_ELIMINAR);            
        }
    }

    /**
     * Se encarga de mostrar el popup de detalle de cambio fechas
     * 
     */
    public void verPopupCambioFechas(PlantillaDTO plantillaSeleccionada) {
        LOGGER.debug("ConsultaPlantillaMB::verPopupCambioFechas");

        if(!deshabilitarCambioFechasVigencia(plantillaSeleccionada)) {
            plantillaSeleccionadaFechas = plantillaSeleccionada;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("cambioFechasPopup");
            context.execute("PF('cambioFechasPopup').show()");        
        } else {
            addErrorMessage(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS, ConstantesPlantillas.LLAVE_MSG_ERROR_FECHA_INICIO_FECHAS);            
        }
    }

    /**
     * Se encarga de validar si se habilita o no la opcion de cambiar la fechas de vigencia
     * 
     */
    private boolean deshabilitarCambioFechasVigencia(PlantillaDTO plantillaSeleccionada) {
        LOGGER.debug("ConsultaPlantillaMB::deshabilitarCambioFechasVigencia");
        // Valida si alguna fecha del rango de fechas se puede modificar
        if (plantillaSeleccionada == null
                || (plantillaSeleccionada.getFechaFin() != null && plantillaSeleccionada.getFechaFin().before(
                        new Date()))) {
            return true;
        }
        return false;

    }

    /**
     * Se encarga de validar si se habilita o no la opcion de editar
     * 
     */
    private boolean deshabilitarEditar(PlantillaDTO plantillaSeleccionada) {
        LOGGER.debug("ConsultaPlantillaMB::deshabilitarEditar");
        // Valida si la fecha de inicio de la plantilla es superior a la actual
        if (plantillaSeleccionada == null
                || (plantillaSeleccionada.getFechaInicio() != null && plantillaSeleccionada.getFechaInicio().before(
                        new Date()))) {
            return true;
        }
        return false;

    }

    /**
     * Se encarga de validar si se habilita o no la opcion de eliminar plantilla
     * 
     */
    private boolean deshabilitarEliminar(PlantillaDTO plantillaSeleccionada) {
        LOGGER.debug("ConsultaPlantillaMB::deshabilitarEliminar");
        // Valida si se puede eliminar una plantilla
        if (plantillaSeleccionada == null
                || (plantillaSeleccionada.getFechaInicio() != null && plantillaSeleccionada.getFechaInicio().before(
                        new Date()))) {
            return true;
        }
        return false;

    }

    /**
     * Se encarga de validar si se habilita o no el campo fechaa
     * 
     */
    public boolean deshabilitarCampoFecha(Date fecha) {
        LOGGER.debug("ConsultaPlantillaMB::deshabilitarCampoFecha");
        // Valida si alguna fecha del rango de fechas se puede modificar
        if (fecha != null && fecha.before(new Date())) {
            return true;
        }
        return false;

    }

    /**
     * Se encarga de cambiar las fechas de la plantilla almacenada en la variable global @this.plantillaSeleccionada
     * 
     * @return
     */
    public String cambiarFechasPlantilla() {
        LOGGER.debug("ConsultaPlantillaMB::cambiarFechasPlantilla");
        // La fecha final siempre es superior a la inicial.
        if (this.plantillaSeleccionadaFechas.getFechaFin() != null
                && this.plantillaSeleccionadaFechas.getFechaInicio().after(
                        this.plantillaSeleccionadaFechas.getFechaFin())) {
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, "rango_fecha_mayor");
            return null;
        }

        PlantillaConsultaDTO plantillaConsulta = new PlantillaConsultaDTO();
        PlantillaDTO plantillaTemp = new PlantillaDTO();
        plantillaTemp.setPlantillaOrigenDTO(plantillaSeleccionadaFechas.getPlantillaOrigenDTO());
        plantillaConsulta.setPlantillaDTO(plantillaTemp);
        plantillaConsulta.setValidaOrigen(true);
        plantillaConsulta.setUltimaVersion(false);
        List<PlantillaDTO> plantillas = irPlantilla.consultarPlantilla(plantillaConsulta);
        for (PlantillaDTO plantillaDTO : plantillas) {
            // Verificacion con plantillas diferentes a la actual
            if (plantillaDTO.getIdPlantilla() != plantillaSeleccionadaFechas.getIdPlantilla()) {
                // Permite modificar las fechas inicial y final, siempre y cuando no se crucen con la vigencia de otra versión de la misma plantilla.

                boolean hayCruceVigencias = (
                        // Fecha inicio actual entre fecha inicio y fecha fin de otra plantilla
                        (plantillaSeleccionadaFechas.getFechaInicio().compareTo(plantillaDTO.getFechaInicio()) >= 0 && 
                        (plantillaDTO.getFechaFin() == null || plantillaSeleccionadaFechas.getFechaInicio().compareTo(
                        plantillaDTO.getFechaFin()) <= 0))
                        // Fecha fin actual no es nula y entre fecha inicio y fecha fin de otra plantilla
                        || (plantillaSeleccionadaFechas.getFechaFin() != null
                                && plantillaSeleccionadaFechas.getFechaFin().compareTo(plantillaDTO.getFechaInicio()) >= 0 && 
                                (plantillaDTO.getFechaFin() == null || plantillaSeleccionadaFechas.getFechaFin().compareTo(
                                plantillaDTO.getFechaFin()) <= 0))
                        // Fechas actuales cubren la vigencia de otra plantilla
                        || (plantillaSeleccionadaFechas.getFechaFin() != null
                                && plantillaDTO.getFechaFin() != null
                                && plantillaSeleccionadaFechas.getFechaInicio()
                                        .compareTo(plantillaDTO.getFechaInicio()) <= 0 && plantillaSeleccionadaFechas
                                .getFechaFin().compareTo(plantillaDTO.getFechaFin()) >= 0)
                        // Fecha fin actual es nula y hay una plantilla con fecha de inicio de vigencia mayor
                        || (plantillaSeleccionadaFechas.getFechaFin() == null && plantillaDTO.getFechaInicio().compareTo(
                                plantillaSeleccionadaFechas.getFechaInicio()) >= 0));
                
                if (hayCruceVigencias) {
                    if(plantillaDTO.getFechaFin() == null) {
                        getFacesContext().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                                        getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                ConstantesPlantillas.LLAVE_MSG_ERROR_CRUCE_VIGENCIA_SIN_FECHA_FIN),
                                        plantillaDTO.getVersionPlantilla(),
                                        formatoFecha.format(plantillaDTO.getFechaInicio()))));                        
                    } else {
                        getFacesContext().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                                        getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                ConstantesPlantillas.LLAVE_MSG_ERROR_CRUCE_VIGENCIA),
                                        plantillaDTO.getVersionPlantilla(),
                                        formatoFecha.format(plantillaDTO.getFechaInicio()),
                                        formatoFecha.format(plantillaDTO.getFechaFin()))));                        
                    }
                    
                    return null;
                }

                // El sistema revisa si en el momento de cambiar la fecha inicial o final está quedando un "hueco" entre las versiones y presenta un
                // mensaje
                // informativo "Los cambios fueron guardados. Existe una versión posterior de la plantilla que empieza el FECHA, por favor verifique"
                // o
                // "Los cambios fueron guardados. Existe una versión anterior de la plantilla que termina el FECHA, por favor verifique".
                if (plantillaDTO.getVersionPlantilla() == (plantillaSeleccionadaFechas.getVersionPlantilla() - 1)
                        && plantillaDTO.getFechaFin() != null) {
                    // Verifica huecos por debajo de la fecha de iniciocon la version anterior a la actual
                    Calendar calFechaFinal = Calendar.getInstance();
                    calFechaFinal.setTime(plantillaDTO.getFechaFin());
                    calFechaFinal.add(Calendar.DAY_OF_MONTH, 1);
                    if (calFechaFinal.getTime().before(plantillaSeleccionadaFechas.getFechaInicio())) {
                        getFacesContext().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "", MessageFormat.format(
                                        getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                ConstantesPlantillas.LLAVE_MSG_ERROR_HUECO_ANTERIOR),
                                        formatoFecha.format(plantillaDTO.getFechaFin()))));
                    }
                } else if (plantillaSeleccionadaFechas.getFechaFin() != null
                        && plantillaDTO.getVersionPlantilla() == (plantillaSeleccionadaFechas.getVersionPlantilla() + 1)) {
                    // Verifica huecos por encima de la fecha final con la version siguiente a la actual si existe
                    Calendar calFechaFinal = Calendar.getInstance();
                    calFechaFinal.setTime(plantillaSeleccionadaFechas.getFechaFin());
                    calFechaFinal.add(Calendar.DAY_OF_MONTH, 1);
                    if (calFechaFinal.getTime().before(plantillaDTO.getFechaInicio())) {
                        getFacesContext().addMessage(
                                null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "", MessageFormat.format(
                                        getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                ConstantesPlantillas.LLAVE_MSG_ERROR_HUECO_POSTERIOR),
                                        formatoFecha.format(plantillaDTO.getFechaInicio()))));
                    }
                }
            }
        }

        irPlantilla.actualizarPlantilla(plantillaSeleccionadaFechas);
        addInfoMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, "msg_exito_guardar");
        return null;
    }

    public void cerrarPopupFechas() {
        RequestContext context = RequestContext.getCurrentInstance();
        plantillaSeleccionadaFechas = null;
        if (plantillaSeleccionadaHistorico == null) {
            plantillaSeleccionadaGeneral = null;
            context.update("form-resultado:tabla-resultados");
            context.update("form-resultado:acciones-top");
            consultar();
        } else {
            plantillaSeleccionadaHistorico = null;
            context.update("form-popup-historico-plantilla:tabla-resultados");
            context.update("form-popup-historico-plantilla:acciones-top");
            verHistorico();
        }
        context.execute("PF('cambioFechasPopup').hide()");
    }

    /**
     * Genera vista preliminar
     */
    public void visualizar(PlantillaDTO plantillaDTO) {
        LOGGER.debug("ConsultaPlantillaMB::preliminar");
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (contextURL == null) {
                // Context url
                contextURL = "";
                if (getFacesContext() != null) {
                    contextURL = getHttpRequest().getRequestURL().toString()
                            .replace(getHttpRequest().getRequestURI(), "")
                            + getHttpRequest().getContextPath();
                }
            }

            visualizaDocumento.setRutaArchivo(null);
            visualizaDocumento.setPathRepositorio(null);
            visualizaDocumento.setErrorGeneracionDocumento(false);

            String xmlPlantilla = new String(plantillaDTO.getXmlPlantillaDTO().getContenidoXmlPlantilla(),
                    StandardCharsets.UTF_8);
            if (xmlPlantilla != null) {

                String filename = "preliminar";
                if (plantillaDTO.getNombrePlantilla() != null && !plantillaDTO.getNombrePlantilla().isEmpty()) {
                    filename = plantillaDTO.getNombrePlantilla();
                }

                GeneraDocumentoDTO generaDocumentoDTO = new GeneraDocumentoDTO();
                generaDocumentoDTO.setFormato(EnumFormatoDocumento.PDF);
                generaDocumentoDTO.setNombreDocumento(filename);
                generaDocumentoDTO.setXmlDocumento(xmlPlantilla);
                generaDocumentoDTO.setPreliminar(true);
                generaDocumentoDTO.setUsuario(usuarioOrganizacionSesion.getLoginUsuario());
                // Context url
                if (getFacesContext() != null) {
                    generaDocumentoDTO.setContextPath(getHttpRequest().getRequestURL().toString().replace(getHttpRequest().getRequestURI(), ""));
                }
                DocumentoDTO documentoGenerado = irDocumento.generarDocumentoSinGuardar(generaDocumentoDTO);
                xmlPlantilla = null;// reporte creado
                if (documentoGenerado != null) {
                    visualizaDocumento.setRutaArchivo(contextURL + "/ReporteServlet");
                    visualizaDocumento.setPathRepositorio(documentoGenerado.getPathDocumento());
                    context.update("frmVisualizaDoc:documento");
                    context.update("frmVisualizaDoc:popupVisualizaDocId");
                    context.execute("PF('popupVisualizaDoc').show();jQuery('#iframeVisualiza').css('display', 'block');");
                } else {
                    visualizaDocumento.setErrorGeneracionDocumento(true);
                    context.update("frmVisualizaDoc:documento");
                    context.update("frmVisualizaDoc:popupVisualizaDocId");
                    context.execute("PF('popupVisualizaDoc').show()");
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error al visualizar preliminar de plantilla", e);
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
        }
    }

    public List<PlantillaDTO> getPlantillas() {
        return plantillas;
    }

    public void setPlantillas(List<PlantillaDTO> plantillas) {
        this.plantillas = plantillas;
    }

    public PlantillaConsultaDTO getPlantillaConsulta() {
        return plantillaConsulta;
    }

    public void setPlantillaConsulta(PlantillaConsultaDTO plantillaConsulta) {
        this.plantillaConsulta = plantillaConsulta;
    }

    public boolean isMostrarTabla() {
        return mostrarTabla;
    }

    public List<PlantillaDTO> getPlantillasHistorico() {
        return plantillasHistorico;
    }

    public void setPlantillasHistorico(List<PlantillaDTO> plantillasHistorico) {
        this.plantillasHistorico = plantillasHistorico;
    }

    public PlantillaDTO getPlantillaSeleccionadaGeneral() {
        return plantillaSeleccionadaGeneral;
    }

    public void setPlantillaSeleccionadaGeneral(PlantillaDTO plantillaSeleccionadaGeneral) {
        this.plantillaSeleccionadaGeneral = plantillaSeleccionadaGeneral;
    }

    public PlantillaDTO getPlantillaSeleccionadaHistorico() {
        return plantillaSeleccionadaHistorico;
    }

    public void setPlantillaSeleccionadaHistorico(PlantillaDTO plantillaSeleccionadaHistorico) {
        this.plantillaSeleccionadaHistorico = plantillaSeleccionadaHistorico;
    }

    public VisualizarDocumentoMB getVisualizaDocumento() {
        return visualizaDocumento;
    }

    public void setVisualizaDocumento(VisualizarDocumentoMB visualizaDocumento) {
        this.visualizaDocumento = visualizaDocumento;
    }

    public PlantillaDTO getPlantillaSeleccionadaFechas() {
        return plantillaSeleccionadaFechas;
    }

    public void setPlantillaSeleccionadaFechas(PlantillaDTO plantillaSeleccionadaFechas) {
        this.plantillaSeleccionadaFechas = plantillaSeleccionadaFechas;
    }

    public Date getFecMinPlantilla() {
        return fecMinPlantilla;
    }

    public Date getFecMinFinalPlantilla() {
        if (plantillaSeleccionadaFechas != null && plantillaSeleccionadaFechas.getFechaInicio() != null
                && fecMinPlantilla.before(plantillaSeleccionadaFechas.getFechaInicio())) {
            return plantillaSeleccionadaFechas.getFechaInicio();
        }
        return fecMinPlantilla;
    }

}
