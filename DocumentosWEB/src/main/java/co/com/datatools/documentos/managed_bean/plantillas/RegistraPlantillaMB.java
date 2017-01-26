/**
 * 
 */
package co.com.datatools.documentos.managed_bean.plantillas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import co.com.datatools.c2.bundle.comun.EnumParametrosWeb;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.jasper.JasperUtil;
import co.com.datatools.documentos.managed_bean.documentos.VisualizarDocumentoMB;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRProceso;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRDocumento;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRVariable;
import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;
import co.com.datatools.documentos.plantillas.GrupoPlantillaDTO;
import co.com.datatools.documentos.plantillas.JasperPlantillaDTO;
import co.com.datatools.documentos.plantillas.PlantillaConsultaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;
import co.com.datatools.documentos.plantillas.XmlPlantillaDTO;
import co.com.datatools.documentos.util.ConstantesManagedBean;
import co.com.datatools.documentos.util.VariableDTOComparator;
import co.com.datatools.util.web.mb.AbstractManagedBean;

import com.google.gson.Gson;

/**
 * Bean de registro de plantillas
 * 
 * @author julio.pinzon
 * 
 */
@ManagedBean
@SessionScoped
public class RegistraPlantillaMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(RegistraPlantillaMB.class.getName());

    @EJB
    private IRPlantilla irPlantilla;

    @EJB
    private IRProceso irProceso;

    @EJB
    private IRVariable irVariable;

    @EJB
    private IRDocumento irDocumento;

    /**
     * Indica si se esta creando una nueva version
     */
    private boolean nuevaVersion;

    /**
     * Indica si se esta editando
     */
    private boolean editar;

    /**
     * DTO de la plantilla
     */
    private PlantillaDTO plantillaDTO;

    /**
     * DTO de la ultima version de la plantilla
     */
    private PlantillaDTO ultimaVersionPlantilla;

    /**
     * UsuarioOrganizacion del sistema
     */
    private UsuarioOrganizacionDTO usuarioOrganizacionSesion;

    /**
     * Fecha minima de planilla
     */
    private Date fecMinPlantilla;

    /**
     * Fecha maxima de planilla
     */
    private String xmlPlantilla;

    /**
     * Variables
     */
    private String variables;

    /**
     * Lista de variables
     */
    private List<VariableDTO> lVariableDTOs;

    /**
     * Instancia de GSON para convertir objetos a JSON
     */
    private Gson gson = new Gson();

    /**
     * Url de contexto
     */
    private String contextURL;

    /**
     * Lista de procesos
     */
    private List<ProcesoDTO> lProcesoDTOs;

    /**
     * Lista de procesos
     */
    private List<SelectItem> lstProceso;

    /**
     * Bean de generar documento
     */
    @ManagedProperty(value = "#{visualizarDocumentoMB}")
    private VisualizarDocumentoMB visualizaDocumento;

    /**
     * Objeto que representa la pesata�as de los diferentes reportes
     */
    private List<GrupoPlantillaDTO> tabs;

    /**
     * Indica el numero de grupos creados
     */
    private int numeroGrupo;

    /**
     * Boton cancelar
     */
    private String botonCancelar;

    public RegistraPlantillaMB() {
        tabs = new ArrayList<GrupoPlantillaDTO>();
    }

    @PostConstruct
    public void init() {
        LOGGER.debug("RegistraPlantillaMB::init");

        // Context url
        contextURL = "";
        if (getFacesContext() != null) {
            contextURL = getHttpRequest().getContextPath();
        }
        
        // Colocar usuario logueado
        usuarioOrganizacionSesion = findSessionObject(ConstantesManagedBean.CLASE_OBJ_USUARIO_ORGANIZACION,
                ConstantesManagedBean.NOMBRE_OBJ_USUARIO_ORGANIZACION);

        // Procesos
        lProcesoDTOs = irProceso.consultarProceso(new ProcesoDTO());
        cargarProcesos();

        plantillaDTO = findSessionObject(ConstantesManagedBean.CLASE_OBJ_PLANTILLA_DOCUMENTOS,
                ConstantesManagedBean.NOMBRE_OBJ_PLANTILLA_DOCUMENTOS);

        // Fecha inicial minima de la plantilla
        Calendar calMinPlantilla = Calendar.getInstance();
        calMinPlantilla.add(Calendar.DAY_OF_MONTH, 1);
        calMinPlantilla.set(Calendar.HOUR_OF_DAY, 0);
        calMinPlantilla.set(Calendar.MINUTE, 0);
        calMinPlantilla.set(Calendar.SECOND, 0);
        fecMinPlantilla = calMinPlantilla.getTime();

        // Creamos un objeto para adicionarlo a la lista de pestanias
        GrupoPlantillaDTO newTab = new GrupoPlantillaDTO();

        if (plantillaDTO == null) {
            plantillaDTO = new PlantillaDTO();
            ProcesoDTO procesoDTO = new ProcesoDTO();
            plantillaDTO.setProcesoDTO(procesoDTO);
            plantillaDTO.setVersionPlantilla(1);
            plantillaDTO.setListVariablesDTO(new ArrayList<VariableDTO>());

            // Agregamos un objeto a la lista de tabs para que se genere la vista principal
            newTab.setContenidoXmlPlantilla(null);
            newTab.setNombreTab(plantillaDTO.getNombrePlantilla());
            newTab.setIdTab(0);
            newTab.setDisabled(false);
            tabs.add(newTab);

            botonCancelar = getBundle("lbComun").getString("btnCancelar");
        } else {
            // Carga la plantilla desde sesion
            cargarPlantilla();

            // Agregamos un objeto a la lista de tabs para que se genere la vista principal
            newTab.setContenidoXmlPlantilla(new String(plantillaDTO.getXmlPlantillaDTO().getContenidoXmlPlantilla(),
                    StandardCharsets.UTF_8));
            newTab.setNombreTab(plantillaDTO.getNombrePlantilla());
            newTab.setIdTab(0);
            newTab.setDisabled(false);
            tabs.add(newTab);

            // Obtener los grupos para generar las pestanias desde la edicion
            generarGrupos(newTab.getContenidoXmlPlantilla());

            // Consulta variables
            plantillaDTO.setListVariablesDTO(irVariable.consultarVariablePlantilla(plantillaDTO));

            botonCancelar = getBundle("lbComun").getString("btnVolver");
        }
        // Completa el drop down de procesos
        cargarProcesos();
    }

    /**
     * Carga la plantilla en el formulario con todos sus valores
     */
    private void cargarPlantilla() {

        editar = plantillaDTO.isEditar();
        nuevaVersion = !plantillaDTO.isEditar();

        // Si se va a generar una nueva version
        if (nuevaVersion) {
            // Verifica si es una version avanzada
            PlantillaDTO plantillaOrigen = plantillaDTO.getPlantillaOrigenDTO();
            if (plantillaOrigen == null) {
                plantillaOrigen = new PlantillaDTO();
                plantillaOrigen.setIdPlantilla(plantillaDTO.getIdPlantilla());
                plantillaDTO.setPlantillaOrigenDTO(plantillaOrigen);
            }

            // Verificar version
            PlantillaDTO plantillaTemp = new PlantillaDTO();
            plantillaTemp.setPlantillaOrigenDTO(plantillaOrigen);
            PlantillaConsultaDTO plantillaConsulta = new PlantillaConsultaDTO();
            plantillaConsulta.setPlantillaDTO(plantillaTemp);
            plantillaConsulta.setValidaOrigen(true);
            plantillaConsulta.setUltimaVersion(false);

            // Consulta para obtener la ultima version
            List<PlantillaDTO> plantillasVersiones = irPlantilla.consultarPlantilla(plantillaConsulta);
            try {
                if (!plantillasVersiones.isEmpty()) {
                    // Si hay mas versiones
                    ultimaVersionPlantilla = (PlantillaDTO) plantillasVersiones.get(plantillasVersiones.size() - 1)
                            .clone();
                } else {
                    // Si hay solo una version
                    ultimaVersionPlantilla = (PlantillaDTO) plantillaDTO.clone();
                }
                // TODO:Bloquea la plantilla
                // ultimaVersionPlantilla.setPlantillaBloqueada(true);
                // irPlantilla.modificarPlantilla(ultimaVersionPlantilla);
            } catch (CloneNotSupportedException e) {
                LOGGER.error("Error al clonar plantilla", e);
                addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
                return;
            }

            // Si es edicion verificamos la version que debe llevar
            // Identifica la version
            int version = ultimaVersionPlantilla.getVersionPlantilla() + 1;
            plantillaDTO.setVersionPlantilla(version);
            plantillaDTO.setFechaFin(null);

        } else {
            // Halla la version anterior a la que se esta editando
            if (plantillaDTO.getPlantillaOrigenDTO() != null) {
                // LLena objeto de consulta
                PlantillaConsultaDTO plantillaConsulta = new PlantillaConsultaDTO();
                PlantillaDTO plantillaTemp = new PlantillaDTO();
                plantillaTemp.setPlantillaOrigenDTO(plantillaDTO.getPlantillaOrigenDTO());
                plantillaConsulta.setPlantillaDTO(plantillaTemp);
                plantillaConsulta.setValidaOrigen(true);
                plantillaConsulta.setUltimaVersion(false);
                for (PlantillaDTO plantillaDTOTemp : irPlantilla.consultarPlantilla(plantillaConsulta)) {
                    if (plantillaDTOTemp.getIdPlantilla() == plantillaDTO.getIdPlantilla()) {
                        break;
                    }
                    ultimaVersionPlantilla = plantillaDTOTemp;
                }
            }
        }
    }

    /**
     * Consulta variables para adicionarlos a la plantilla
     */
    private void consultarVariables() {
        LOGGER.debug("RegistraPlantillaMB::consultarVariables");
        // Variables disponibles
        // convert java object to JSON format,
        // and returned as JSON formatted string
        lVariableDTOs = irVariable.consultarVariableOrganizacion();
        lVariableDTOs.addAll(irVariable.consultarVariableProceso(plantillaDTO.getProcesoDTO()));
        if(plantillaDTO.getProcesoDTO().getProcesoDTO() != null) {
            lVariableDTOs.addAll(irVariable.consultarVariableProceso(plantillaDTO.getProcesoDTO().getProcesoDTO()));            
        }
        plantillaDTO.setListVariablesDTO(irVariable.consultarVariablePlantilla(plantillaDTO));
        lVariableDTOs.addAll(plantillaDTO.getListVariablesDTO());

        // Se quitan los elementos duplicados
        HashSet<VariableDTO> hs = new HashSet<>();
        hs.addAll(lVariableDTOs);
        lVariableDTOs.clear();
        lVariableDTOs.addAll(hs);
        Collections.sort(lVariableDTOs, new VariableDTOComparator());

        // Formatos de fecha
        SimpleDateFormat myFormat = new SimpleDateFormat(getBundle("webPrm").getString("lab_calendar_pattern"));
        for (VariableDTO variableDTO : lVariableDTOs) {
            if (variableDTO.getImagen() != null) {
//              variableDTO.setEncodedImagen("data:application/octet-stream;base64,"
//                      + Base64.encodeBytes(variableDTO.getImagen()));
                variableDTO.setEncodedImagen(contextURL + ConstantesGeneral.SERVLET_IMAGENES_VARIABLES + variableDTO.getIdVariable());
                variableDTO.setImagen(null);
            }
            if (variableDTO.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA
                    && variableDTO.getValorDefecto() != null) {
                SimpleDateFormat fromUser = new SimpleDateFormat(variableDTO.getFormatoVariable());
                String valorDefecto = variableDTO.getValorDefecto();
                try {
                    valorDefecto = myFormat.format(fromUser.parse(variableDTO.getValorDefecto()));
                } catch (ParseException e) {
                    LOGGER.info("Error al parsear formato de fecha de variables : " + variableDTO.getValorDefecto());
                }
                variableDTO.setValorDefecto(valorDefecto);
            }
        }
        // Se pasan a formato JSON
        variables = gson.toJson(lVariableDTOs);
    }

    /**
     * Genera las pestanias de grupo
     * 
     * @param htmlPlantilla
     */
    private void generarGrupos(String htmlPlantilla) {
        LOGGER.debug("RegistraPlantillaMB::generarGrupos");

        // Obtiene documento inicial
        Document doc = Jsoup.parse(htmlPlantilla);

        // Obtiene el area de trabajo
        Element workArea = doc.getElementsByClass("workArea").first();
         // TODO:Modificar para que siempre ponga la ruta del servidor correcta  
          Elements variables = workArea.getElementsByClass(JasperUtil.ITEM_VARIABLE);        
          for (Element variable : variables) {
              VariableDTO variableDTO = gson.fromJson(variable.attr(JasperUtil.ITEM_VARIABLE), VariableDTO.class);
              if (variableDTO.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL) {
                  variable.attr("src", contextURL + ConstantesGeneral.SERVLET_IMAGENES_VARIABLES + variableDTO.getIdVariable());
              }
          }
        // Verifica los grupos para generarlos como subreportes independientes
        Elements groups = workArea.getElementsByClass(JasperUtil.GROUP_CLASS);
        for (Element group : groups) {
            // Creamos un objeto para adicionarlo a la lista de pestanias
            GrupoPlantillaDTO newTabGroup = new GrupoPlantillaDTO();
            newTabGroup.setNombreTab(group.attr("id"));
            if (!tabs.contains(newTabGroup)) {
                newTabGroup.setIdTab(Integer.valueOf(group.attr("tab")));
                newTabGroup.setContenidoXmlPlantilla(group.getElementsByClass("grupo-content").first()
                        .attr("html_grupo"));
                tabs.add(newTabGroup);
                // Busca dentro del grupo si existen mas para generar las otras pestanias
                generarGrupos(newTabGroup.getContenidoXmlPlantilla());
            }
        }
    }

    /**
     * Genera una pesta?a para visualizar el contenido del grupo
     */
    public void verGrupo() {
        LOGGER.debug("RegistraPlantillaMB::verGrupo");
        RequestContext context = RequestContext.getCurrentInstance();
        // Agregamos un objeto a la lista de tabs para que se genere la vista del grupo
        GrupoPlantillaDTO newTab = new GrupoPlantillaDTO();
        newTab.setNombreTab("grupo_" + this.numeroGrupo);
        newTab.setIdTab(this.numeroGrupo);
        newTab.setContenidoXmlPlantilla(null);
        newTab.setDisabled(false);
        this.tabs.add(newTab);

        // Muestra la pestania creada
        context.execute("PF('tabView').select(" + (this.tabs.size() - 1) + ")");
    }

    /**
     * Genera vista preliminar
     */
    public String preliminar() {
        LOGGER.debug("RegistraPlantillaMB::preliminar");
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            // Inicializa el valor de los atributos del MB que muestra la previsualizacion
            visualizaDocumento.setRutaArchivo(null);
            visualizaDocumento.setPathRepositorio(null);
            visualizaDocumento.setErrorGeneracionDocumento(false);
            // Se toma la primera pestania que es la que tiene la plantilla completa
            xmlPlantilla = tabs.get(0).getContenidoXmlPlantilla();
            if (xmlPlantilla != null) {

                String filename = "preliminar";
                if (plantillaDTO.getCodigoPlantilla() != null && !plantillaDTO.getCodigoPlantilla().isEmpty()) {
                    filename = plantillaDTO.getCodigoPlantilla();
                }
                // Inicializa utilitario para generar el documento
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
                    // Se actualiza el dialogo para visualizar el documento generado
                    visualizaDocumento.setRutaArchivo(contextURL + "/ReporteServlet");
                    visualizaDocumento.setPathRepositorio(documentoGenerado.getPathDocumento());
                    context.update("frmVisualizaDoc:documento");
                    context.update("frmVisualizaDoc:errores");
                    context.execute("PF('popupVisualizaDoc').show();jQuery('#iframeVisualiza').css('display', 'block');");
                } else {
                    // En caso de erro al generar el documento se muestra mensaje de error
                    visualizaDocumento.setErrorGeneracionDocumento(true);
                    context.update("frmVisualizaDoc:documento");
                    context.update("frmVisualizaDoc:errores");
                    context.execute("PF('popupVisualizaDoc').show()");
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error al generar vista preliminar", e);
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
        }
        return null;
    }

    /**
     * Guardar Plantilla
     */
    public void guardar() {
        LOGGER.debug("RegistraPlantillaMB::guardar");
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
            // Validacion de rango de fechas
            if (!validarFechasPlantilla()) {
                context.execute("Documentos.Plantillas.onBack()");
                return;
            }

            // Plantilla temporal para la validacion
            PlantillaDTO plantillaDTOTemp = new PlantillaDTO();
            plantillaDTOTemp.setIdPlantilla(plantillaDTO.getIdPlantilla());
            plantillaDTOTemp.setCodigoPlantilla(plantillaDTO.getCodigoPlantilla());
            plantillaDTOTemp.setVersionPlantilla(plantillaDTO.getVersionPlantilla());
            // Valida si existe otra plantilla con el mismo codigo
            if (!irPlantilla.validarPlantilla(plantillaDTOTemp)) {
                plantillaDTOTemp.setCodigoPlantilla(null);
                plantillaDTOTemp.setNombrePlantilla(plantillaDTO.getNombrePlantilla());
                // Valida si existe otra plantilla con el mismo nombre
                if (!irPlantilla.validarPlantilla(plantillaDTOTemp)) {
                    plantillaDTO.setFechaCreacion(new Date());
                    plantillaDTO.setUsuarioOrganizacionDTO(usuarioOrganizacionSesion);
                    // cambia el valor de la fecha de inicio para que quede con hora en 0
                    Calendar calFechaInicio = Calendar.getInstance();
                    calFechaInicio.setTime(plantillaDTO.getFechaInicio());
                    calFechaInicio.set(Calendar.HOUR_OF_DAY, 0);
                    calFechaInicio.set(Calendar.MINUTE, 0);
                    calFechaInicio.set(Calendar.SECOND, 0);
                    // Actualiza valores en la plantilla
                    plantillaDTO.setFechaInicio(calFechaInicio.getTime());

                    XmlPlantillaDTO xmlPlantillaDTO = new XmlPlantillaDTO();
                    xmlPlantillaDTO.setContenidoXmlPlantilla(tabs.get(0).getContenidoXmlPlantilla()
                            .getBytes(StandardCharsets.UTF_8));
                    xmlPlantillaDTO.setNombreXmlPlantilla(plantillaDTO.getNombrePlantilla());
                    plantillaDTO.setXmlPlantillaDTO(xmlPlantillaDTO);

                    // Obtienen las firmas que se van a colocar en la plantilla
                    List<JasperPlantillaDTO> jaspers = new ArrayList<JasperPlantillaDTO>();
                    // Obtienen las firmas que se van a colocar en la plantilla
                    List<FirmaPlantillaDTO> firmas = new ArrayList<FirmaPlantillaDTO>();
                    // Obtienen las firmas que se van a colocar en la plantilla
                    List<VariableDTO> variablesLst = new ArrayList<VariableDTO>();
                    for (GrupoPlantillaDTO grupoPlantilla : tabs) {
                        // Verifica si se encuentra activa la pesta�a
                        if (!grupoPlantilla.isDisabled()) {
                            // Obtiene documento inicial para verificar las firmas
                            Document doc = Jsoup.parse(grupoPlantilla.getContenidoXmlPlantilla());
                            // Recorre las firmas
                            Elements signs = doc.getElementsByClass("firmamecanica");
                            for (Element sign : signs) {
                                FirmaPlantillaDTO firmaPlantillaDTO = gson.fromJson(sign.attr("firma"),
                                        FirmaPlantillaDTO.class);
                                firmaPlantillaDTO.setCodigoFirmaPlantilla(Integer.parseInt(sign.attr("item")));
                                firmas.add(firmaPlantillaDTO);
                            }

                            // Se agregan para generar los archivos jasper
                            JasperPlantillaDTO jasperPlantillaDTO = new JasperPlantillaDTO();
                            jasperPlantillaDTO.setContenidoJasper(grupoPlantilla.getContenidoXmlPlantilla());
                            jasperPlantillaDTO.setGrupo(grupoPlantilla.getIdTab() > 0);
                            if (grupoPlantilla.getIdTab() > 0) {
                                jasperPlantillaDTO.setNombreJasper(plantillaDTO.getCodigoPlantilla() + "_"
                                        + grupoPlantilla.getNombreTab());
                            } else {
                                jasperPlantillaDTO.setNombreJasper(plantillaDTO.getCodigoPlantilla());
                            }
                            jaspers.add(jasperPlantillaDTO);
                            
                            // Recorre las variables
                            Elements variables = doc.getElementsByClass("variable");
                            for (Element variable : variables) {
                                VariableDTO variableDTO = (VariableDTO) gson.fromJson(variable.attr("variable"),
                                        VariableDTO.class);
                                variablesLst.add(variableDTO);
                                // Se agrega como un subgrupo la imagen para los casos en que vienen tiffs multipagina
                                if (variableDTO.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
                                    // Cuando es una imagen variable
                                    String contenidoJasper = JasperUtil.generateContentImageVariable(variable.clone(), doc
                                            .getElementsByClass("workArea").first().clone());
                                    // Se agregan para generar los archivos jasper
                                    jasperPlantillaDTO = new JasperPlantillaDTO();
                                    jasperPlantillaDTO.setContenidoJasper(contenidoJasper);
                                    jasperPlantillaDTO.setGrupo(true);

                                    String nombre = plantillaDTO.getCodigoPlantilla() + "_"
                                            + variableDTO.getNombreVariable();
                                    jasperPlantillaDTO.setNombreJasper(nombre);
                                    jaspers.add(jasperPlantillaDTO);
                                }
                            }
                            variablesLst.addAll(plantillaDTO.getListVariablesDTO());
                            // Se quitan los elementos duplicados
                            HashSet<VariableDTO> hs = new HashSet<>();
                            hs.addAll(variablesLst);
                            variablesLst.clear();
                            variablesLst.addAll(hs);
                        }
                    }
                    plantillaDTO.setListVariablesDTO(variablesLst);
                    plantillaDTO.setListFirmasPlantillaDTO(firmas);
                    plantillaDTO.setListJasperPlantillaDTO(jaspers);
                    // Si se va a crear una nueva plantilla o una nueva version
                    if (!editar) {
                        // Cambia datos basicos a su valor por defecto
                        plantillaDTO.setIdPlantilla(0);
                        plantillaDTO.setFechaFin(null);
                        // Metodo de guardar plantilla
                        plantillaDTO = irPlantilla.registrarPlantilla(plantillaDTO);
                    } else {
                        // Metodo de actualizar plantilla
                        irPlantilla.actualizarPlantilla(plantillaDTO);
                    }
                    // Si es edicion verificamos la version que debe llevar
                    if (nuevaVersion && ultimaVersionPlantilla != null && ultimaVersionPlantilla.getFechaFin() == null) {
                        // La fecha fin de la version anterior es la fecha de inicio de la actual - 1 dia
                        Calendar calFechaFin = Calendar.getInstance();
                        calFechaFin.setTime(plantillaDTO.getFechaInicio());
                        calFechaFin.add(Calendar.DAY_OF_YEAR, -1);
                        // Actualiza valores de la ultima version de la plantilla
                        ultimaVersionPlantilla.setFechaFin(calFechaFin.getTime());
                        // TODO:Desbloquea la plantilla
                        // ultimaVersionPlantilla.setPlantillaBloqueada(false);
                        irPlantilla.actualizarPlantilla(ultimaVersionPlantilla);
                    }
                    // Mensaje de exito al guardar
                    addInfoMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, "msg_exito_guardar");

                    if (editar || nuevaVersion) {
                        plantillaDTO.setEditar(true);
                        cargarPlantilla();

                        context.execute("Documentos.Plantillas.onBack()");
                    } else {
                        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_REGISTRA_PLANTILLA);
                        getFacesContext().getExternalContext().redirect(ConstantesManagedBean.RUTA_CONSULTA_PLANTILLA);
                    }

                } else {
                    addErrorMessage(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS,
                            ConstantesPlantillas.LLAVE_MENSAJE_ERROR_NOMBRE_EXISTE);
                    context.execute("Documentos.Plantillas.onBack()");
                }
            } else {
                addErrorMessage(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS,
                        ConstantesPlantillas.LLAVE_MENSAJE_ERROR_CODIGO_EXISTE);
                context.execute("Documentos.Plantillas.onBack()");
            }

        } catch (Exception e) {
            LOGGER.error("Error al guardar plantilla", e);
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
            context.execute("Documentos.Plantillas.onBack()");
        }
    }

    /**
     * Se encarga de cambiar las fechas de la plantilla almacenada en la variable global @this.plantillaSeleccionada
     * 
     * @return true si el rango de fechas es correcto
     */
    private boolean validarFechasPlantilla() {
        LOGGER.debug("RegistraPlantillaMB::validarFechasPlantilla");

        // Pone el formato que debe tener la fecha para mostrar en mensajes
        ResourceBundle bundle = getBundle(EnumParametrosWeb.getBundleName());
        // Formato por defecto para fechas
        SimpleDateFormat formatoFecha = new SimpleDateFormat(bundle.getString(EnumParametrosWeb.lab_calendar_pattern
                .toString()));

        // Valida que la fecha inicio no sea menor a una fecha fin de la ultima version de la plantilla
        // al igual que con la de inicio
        if (ultimaVersionPlantilla != null
                && ((ultimaVersionPlantilla.getFechaFin() != null && plantillaDTO.getFechaInicio().before(
                        ultimaVersionPlantilla.getFechaFin())) || (ultimaVersionPlantilla.getFechaInicio().compareTo(
                        plantillaDTO.getFechaInicio()) >= 0))) {
            String llave = ConstantesPlantillas.LLAVE_MENSAJE_ERROR_FECHA_INICIO_MENOR_FINAL;
            Date fechaParametro = ultimaVersionPlantilla.getFechaFin();
            if (ultimaVersionPlantilla.getFechaFin() == null) {
                llave = ConstantesPlantillas.LLAVE_MSG_ERROR_FECHA_INICIO_IGUAL;
                fechaParametro = ultimaVersionPlantilla.getFechaInicio();
            }
            getFacesContext().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                            getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(llave),
                            formatoFecha.format(fechaParametro))));
            return false;
        }

        // La fecha final siempre es superior a la inicial.
        if (this.plantillaDTO.getFechaFin() != null
                && this.plantillaDTO.getFechaInicio().after(this.plantillaDTO.getFechaFin())) {
            getFacesContext().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                            getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                    "msg_error_fecha_inicio_mayor_fecha_fin"),
                            formatoFecha.format(this.plantillaDTO.getFechaFin()))));
            return false;
        }

        // Validacion unicamente para edicion de plantillas no para nuevas versiones
        if (this.editar && plantillaDTO.getPlantillaOrigenDTO() != null) {
            PlantillaConsultaDTO plantillaConsulta = new PlantillaConsultaDTO();
            PlantillaDTO plantillaTemp = new PlantillaDTO();
            plantillaTemp.setPlantillaOrigenDTO(plantillaDTO.getPlantillaOrigenDTO());
            plantillaConsulta.setPlantillaDTO(plantillaTemp);
            plantillaConsulta.setValidaOrigen(true);
            plantillaConsulta.setUltimaVersion(false);
            List<PlantillaDTO> plantillas = irPlantilla.consultarPlantilla(plantillaConsulta);
            for (PlantillaDTO plantillaConsultadaDTO : plantillas) {
                // Verificacion con plantillas diferentes a la actual
                if (plantillaConsultadaDTO.getIdPlantilla() != this.plantillaDTO.getIdPlantilla()) {
                    // Permite modificar las fechas inicial y final, siempre y cuando no se crucen con la vigencia de otra versión de la misma
                    // plantilla.
                    boolean hayCruceVigencias = (
                            // Fecha inicio actual entre fecha inicio y fecha fin de otra plantilla
                            (plantillaDTO.getFechaInicio().compareTo(plantillaConsultadaDTO.getFechaInicio()) >= 0 && 
                            (plantillaConsultadaDTO.getFechaFin() == null || plantillaDTO.getFechaInicio().compareTo(
                            plantillaConsultadaDTO.getFechaFin()) <= 0))
                            // Fecha fin actual no es nula y entre fecha inicio y fecha fin de otra plantilla
                            || (plantillaDTO.getFechaFin() != null
                                    && plantillaDTO.getFechaFin().compareTo(plantillaConsultadaDTO.getFechaInicio()) >= 0 && 
                                    (plantillaConsultadaDTO.getFechaFin() == null || plantillaDTO.getFechaFin().compareTo(
                                    plantillaConsultadaDTO.getFechaFin()) <= 0))
                            // Fechas actuales cubren la vigencia de otra plantilla
                            || (plantillaDTO.getFechaFin() != null
                                    && plantillaConsultadaDTO.getFechaFin() != null
                                    && plantillaDTO.getFechaInicio()
                                            .compareTo(plantillaConsultadaDTO.getFechaInicio()) <= 0 && plantillaDTO
                                    .getFechaFin().compareTo(plantillaConsultadaDTO.getFechaFin()) >= 0)
                            // Fecha fin actual es nula y hay una plantilla con fecha de inicio de vigencia mayor
                            || (plantillaDTO.getFechaFin() == null && plantillaConsultadaDTO.getFechaInicio().compareTo(
                                    plantillaDTO.getFechaInicio()) >= 0));
                    
                    if (hayCruceVigencias) {
                        if(plantillaConsultadaDTO.getFechaFin() == null) {
                            getFacesContext().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                                            getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                    ConstantesPlantillas.LLAVE_MSG_ERROR_CRUCE_VIGENCIA_SIN_FECHA_FIN),
                                            plantillaConsultadaDTO.getVersionPlantilla(),
                                            formatoFecha.format(plantillaConsultadaDTO.getFechaInicio()))));                      
                        } else {
                            getFacesContext().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", MessageFormat.format(
                                            getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                    ConstantesPlantillas.LLAVE_MSG_ERROR_CRUCE_VIGENCIA),
                                            plantillaConsultadaDTO.getVersionPlantilla(),
                                            formatoFecha.format(plantillaConsultadaDTO.getFechaInicio()),
                                            formatoFecha.format(plantillaConsultadaDTO.getFechaFin()))));                    
                        }
                        return false;
                    }

                    // El sistema revisa si en el momento de cambiar la fecha inicial o final está quedando un "hueco" entre las versiones y presenta un
                    // mensaje
                    // informativo "Los cambios fueron guardados. Existe una versión posterior de la plantilla que empieza el FECHA, por favor verifique"
                    // o
                    // "Los cambios fueron guardados. Existe una versión anterior de la plantilla que termina el FECHA, por favor verifique".
                    if (plantillaConsultadaDTO.getVersionPlantilla() == (plantillaDTO.getVersionPlantilla() - 1)
                            && plantillaConsultadaDTO.getFechaFin() != null) {
                        // Verifica huecos por debajo de la fecha de iniciocon la version anterior a la actual
                        Calendar calFechaFinal = Calendar.getInstance();
                        calFechaFinal.setTime(plantillaConsultadaDTO.getFechaFin());
                        calFechaFinal.add(Calendar.DAY_OF_MONTH, 1);
                        if (calFechaFinal.getTime().before(plantillaDTO.getFechaInicio())) {
                            getFacesContext().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN, "", MessageFormat.format(
                                            getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                    ConstantesPlantillas.LLAVE_MSG_ERROR_HUECO_ANTERIOR),
                                            formatoFecha.format(plantillaConsultadaDTO.getFechaFin()))));
                        }
                    } else if (plantillaDTO.getFechaFin() != null
                            && plantillaConsultadaDTO.getVersionPlantilla() == (plantillaDTO.getVersionPlantilla() + 1)) {
                        // Verifica huecos por encima de la fecha final con la version siguiente a la actual si existe
                        Calendar calFechaFinal = Calendar.getInstance();
                        calFechaFinal.setTime(plantillaDTO.getFechaFin());
                        calFechaFinal.add(Calendar.DAY_OF_MONTH, 1);
                        if (calFechaFinal.getTime().before(plantillaConsultadaDTO.getFechaInicio())) {
                            getFacesContext().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN, "", MessageFormat.format(
                                            getBundle(ConstantesPlantillas.NOMBRE_BUNDLE_PLANTILLAS).getString(
                                                    ConstantesPlantillas.LLAVE_MSG_ERROR_HUECO_POSTERIOR),
                                            formatoFecha.format(plantillaConsultadaDTO.getFechaInicio()))));
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Continuar
     */
    public void continuar() {
        LOGGER.debug("RegistraPlantillaMB::continuar");
        RequestContext context = RequestContext.getCurrentInstance();
        consultarVariables();
        // Si es nueva plantilla
        if (!this.editar && !this.nuevaVersion) {
            // Cambiamos el nombre de la pestania de acuerdo al nombre de la plantilla
            tabs.get(0).setNombreTab(plantillaDTO.getNombrePlantilla());
            context.update("areaTrabajoForm:tabs");
        }
        context.update("areaTrabajoForm:variables");
        context.execute("Documentos.Plantillas.onContinue()");
    }

    /**
     * Cargar Procesos
     */
    public void cargarProcesos() {
        LOGGER.debug("RegistraPlantillaMB::cargarProcesos");
        lstProceso = new ArrayList<SelectItem>();
        for (ProcesoDTO procesoDTO : lProcesoDTOs) {
            lstProceso.add(new SelectItem(Long.valueOf(procesoDTO.getIdProceso()), procesoDTO.getNombreProceso()));
        }
    }

    /**
     * Volver a consultar plantilla
     */
    public void irConsultarPlantilla() {
        LOGGER.debug("RegistraPlantillaMB::irConsultarPlantilla");
        // TODO:Desbloquea la plantilla
        // ultimaVersionPlantilla.setPlantillaBloqueada(false);
        // irPlantilla.modificarPlantilla(ultimaVersionPlantilla);
        try {
            removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_PLANTILLA_DOCUMENTOS);
            removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_REGISTRA_PLANTILLA);
            removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_FIRMA_PLANTILLA);
            // Obtiene el bean de consulta para recargar la consulta
            ConsultaPlantillaMB consultaPlantillaMB = findSessionObject(ConsultaPlantillaMB.class,
                    ConstantesManagedBean.NOMBRE_OBJ_BEAN_CONSULTA_PLANTILLA);
            if (consultaPlantillaMB != null) {
                consultaPlantillaMB.consultar();
            }
            getFacesContext().getExternalContext().redirect(ConstantesManagedBean.RUTA_CONSULTA_PLANTILLA);
        } catch (IOException e) {
            LOGGER.error("Error al redirigir pagina", e);
            addErrorMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
        }
    }

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        getFacesContext().addMessage(null, msg);
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public PlantillaDTO getPlantillaDTO() {
        return plantillaDTO;
    }

    public void setPlantillaDTO(PlantillaDTO plantillaDTO) {
        this.plantillaDTO = plantillaDTO;
    }

    public Date getFecMinPlantilla() {
        return fecMinPlantilla;
    }

    public String getXmlPlantilla() {
        return xmlPlantilla;
    }

    public void setXmlPlantilla(String xmlPlantilla) {
        this.xmlPlantilla = xmlPlantilla;
    }

    public VisualizarDocumentoMB getVisualizaDocumento() {
        return visualizaDocumento;
    }

    public void setVisualizaDocumento(VisualizarDocumentoMB visualizaDocumento) {
        this.visualizaDocumento = visualizaDocumento;
    }

    public List<SelectItem> getLstProceso() {
        return lstProceso;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public List<VariableDTO> getlVariableDTOs() {
        return lVariableDTOs;
    }

    public List<GrupoPlantillaDTO> getTabs() {
        return tabs;
    }

    public void setTabs(List<GrupoPlantillaDTO> tabs) {
        this.tabs = tabs;
    }

    public int getNumeroGrupo() {
        return numeroGrupo;
    }

    public void setNumeroGrupo(int numeroGrupo) {
        this.numeroGrupo = numeroGrupo;
    }

    public boolean isNuevaVersion() {
        return nuevaVersion;
    }

    public String getBotonCancelar() {
        return botonCancelar;
    }

}
