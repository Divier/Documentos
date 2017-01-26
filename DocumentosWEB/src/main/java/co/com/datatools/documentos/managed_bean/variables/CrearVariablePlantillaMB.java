package co.com.datatools.documentos.managed_bean.variables;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoVariableDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCatalogo;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRProceso;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRVariable;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;
import co.com.datatools.util.web.mb.AbstractManagedBean;

/**
 * Clase que gestiona la pagina de creacion de variables de plantilla
 * 
 * @author dixon.alvarez
 * 
 */

@ManagedBean
@SessionScoped
public class CrearVariablePlantillaMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(CrearVariablePlantillaMB.class.getName());

    private VariableDTO variableRegistrar;
    private List<SelectItem> lstOrdenJerarquico;
    private List<SelectItem> lstTiposVariable;
    private List<SelectItem> lstProcesos;
    private boolean habilitarProceso;
    private String pattern;
    private String mensajeAyudaFormato;
    private boolean habilitarLongitud;
    private boolean habilitarValorFecha;
    private boolean habilitarFormato;
    private String longitud;

    private Date valorDefectoFecha;
    private PlantillaDTO plantillaSesion;

    private UploadedFile file;
    private String nombreArchivo;
    private String extensionesPermitidas = "/(\\.|\\/)(gif|jpe?g|png|bmp)$/";

    private static final String NOMBRE_BUNDLE_VARIABLES = "labelVariables";

    /**
     * Bundle de mensajes
     */
    private ResourceBundle bundleVariables;

    @EJB
    private IRVariable irVariable;

    @EJB
    private IRProceso irProceso;

    @EJB
    private IRCatalogo irCatalogo;

    @EJB
    private IRPlantilla irPlantilla;

    private boolean permiteCargarImagen;

    private int anchoImagen;

    private int altoImagen;

    public CrearVariablePlantillaMB() {
        logger.debug("CrearVariablePlantillaMB::CrearVariablePlantillaMB");
        bundleVariables = getBundle(NOMBRE_BUNDLE_VARIABLES);
    }

    @PostConstruct
    public void init() {
        logger.debug("CrearVariablePlantillaMB::init");
        // Reinicia valores del formulario
        reiniciarValores();

        // Tipos variable
        lstTiposVariable = new ArrayList<SelectItem>();
        List<TipoVariableDTO> lTipoVariableDTOs = irCatalogo.consultarTipoVariable();
        if (lTipoVariableDTOs != null && !lTipoVariableDTOs.isEmpty()) {
            for (TipoVariableDTO tipoVariableDTO : lTipoVariableDTOs) {
                lstTiposVariable.add(new SelectItem(Integer.valueOf(tipoVariableDTO.getIdTipoVariable()), tipoVariableDTO
                        .getNombreTipoVariable()));
            }
        }

        // Conextos variable
        lstOrdenJerarquico = new ArrayList<SelectItem>();
        List<ContextoAplicacionVariableDTO> lContextoAplicacionVariableDTOs = irCatalogo
                .consultarContextoAplicacionVariable();
        if (lContextoAplicacionVariableDTOs != null && !lContextoAplicacionVariableDTOs.isEmpty()) {
            for (ContextoAplicacionVariableDTO contextoAplicacionVariableDTO : lContextoAplicacionVariableDTOs) {
                lstOrdenJerarquico.add(new SelectItem(Integer.valueOf(contextoAplicacionVariableDTO
                        .getIdContextoAplicacion()), contextoAplicacionVariableDTO.getNombreContextoAplicacion()));
            }
        }

        // Procesos
        lstProcesos = new ArrayList<SelectItem>();
        ProcesoDTO procesoFiltro = new ProcesoDTO();
        List<ProcesoDTO> lProcesoDTOs = irProceso.consultarProceso(procesoFiltro);
        if (lProcesoDTOs != null && !lProcesoDTOs.isEmpty()) {
            for (ProcesoDTO procesoDTO : lProcesoDTOs) {
                lstProcesos.add(new SelectItem(Long.valueOf(procesoDTO.getIdProceso()), procesoDTO.getNombreProceso()));

            }
        }
    }

    /**
     * Reinicia valores del formulario
     */
    private void reiniciarValores() {
        logger.debug("CrearVariablePlantillaMB::reiniciarValores");
        // Reinicia valores del formulario
        variableRegistrar = new VariableDTO();
        variableRegistrar.setContextoAplicacionVariableDTO(new ContextoAplicacionVariableDTO());
        variableRegistrar.setProcesoDTO(new ProcesoDTO());
        variableRegistrar.setTipoVariableDTO(new TipoVariableDTO());
        if (lstTiposVariable != null && !lstTiposVariable.isEmpty()) {
            variableRegistrar.getTipoVariableDTO().setIdTipoVariable(
                    Integer.valueOf(lstTiposVariable.get(0).getValue().toString()));
        }
    }

    /**
     * Craga la plantilla que debe tomar de sesion
     */
    public void cargarPlantilla() {
        logger.debug("CrearVariablePlantillaMB::cargarPlantilla");
        // Reinicia valores del formulario
        reiniciarValores();
        // Extraer objeto sesion plantilla
        if (plantillaSesion != null) {
            // asigna plantilla a variable a registrar
            List<PlantillaDTO> lPlantillaDTOs = new ArrayList<PlantillaDTO>();
            lPlantillaDTOs.add(plantillaSesion);
            variableRegistrar.setPlantillas(lPlantillaDTOs);
        }
        verificaTipoVariable();

    }

    /**
     * Registra la variable ingresada por el usuarioOrganizacion
     * 
     * @return
     * @author dixon.alvarez
     */
    public String crearVariablePlantilla() {
        logger.debug("CrearVariablePlantillaMB::crearVariablePlantilla");

        // Si la variable es de tipo imagen: Validar que se haya cargado el archivo
        if (ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL == variableRegistrar.getTipoVariableDTO()
                .getIdTipoVariable()) {
            variableRegistrar.setValorDefecto(nombreArchivo);
            if (file == null) {
                getFacesContext().addMessage(
                        "form-ingreso:uploadFile",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, bundleVariables
                                .getString("msg_error_archivo_requerido")));
                return null;
            }
        }
        // Si la variable es de tipo url: Validar que sea valida la url
        if (ConstantesPlantillas.ID_TIPO_VARIABLE_URL == variableRegistrar.getTipoVariableDTO().getIdTipoVariable()
                || ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE == variableRegistrar.getTipoVariableDTO()
                        .getIdTipoVariable()) {
            try {
                URL url = new URL(variableRegistrar.getValorDefecto());
                url = null;
            } catch (MalformedURLException e) {
                getFacesContext().addMessage(
                        "form-ingreso:valorDefecto",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, bundleVariables
                                .getString("msg_tipo_variable_invalido")));
                return null;
            }
        }

        variableRegistrar.setLongitudVariable(null);
        if (longitud != null && !longitud.isEmpty()) {
            try {
                variableRegistrar.setLongitudVariable(Integer.valueOf(longitud));
            } catch (NumberFormatException e) {
                getFacesContext().addMessage(
                        "form-ingreso:longitud",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundleVariables.getString(
                                "msg_error_formato_numero")));
                return null;
            }
        }
        if (validarLongitudVariable()) {
            if (validarFormatoVariable()) {
                // Validar que el nombre de la variable no exista en el sistema
                VariableDTO variableValidar = new VariableDTO();
                variableValidar.setNombreVariable(variableRegistrar.getNombreVariable());
                variableValidar.setProcesoDTO(variableRegistrar.getProcesoDTO());
                variableValidar.setPlantillas(variableRegistrar.getPlantillas());
                if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_ORGANIZACION) {
                    variableValidar.setPlantillas(null);
                    variableValidar.setProcesoDTO(null);
                }
                if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PROCESO) {
                    variableValidar.setPlantillas(null);
                }
                if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PLANTILLA) {
                    variableValidar.setProcesoDTO(null);
                }
                if (!irVariable.validarExistenciaVariable(variableValidar)
                        && validarVariablesPlantilla(variableRegistrar)) {
                    // Pone un valor por defecto al formato en caso de ser necesario
                    if (habilitarFormato
                            && (variableRegistrar.getFormatoVariable() == null || variableRegistrar
                                    .getFormatoVariable().isEmpty())) {
                        if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA) {
                            variableRegistrar.setFormatoVariable(ConstantesGeneral.DATE_FORMAT);
                            SimpleDateFormat sdf = new SimpleDateFormat(variableRegistrar.getFormatoVariable());
                            variableRegistrar.setValorDefecto(sdf.format(valorDefectoFecha));
                        } else if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO) {
                            variableRegistrar.setFormatoVariable("0");
                        }
                    }

                    if (file != null) {
                        variableRegistrar.setImagen(file.getContents());
                    }
                    if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_ORGANIZACION) {
                        variableRegistrar.setProcesoDTO(null);
                        variableRegistrar.setPlantillas(null);
                    } else if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PROCESO) {
                        variableRegistrar.setPlantillas(null);
                    } else if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PLANTILLA) {
                        variableRegistrar.setProcesoDTO(null);
                    }
                    VariableDTO variableRegistrada = irVariable.registrarVariable(variableRegistrar);
                    if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PLANTILLA) {
                        // Guarda relacion de la variable asociada a la plantilla
                        plantillaSesion.getListVariablesDTO().add(variableRegistrada);
                        irPlantilla.actualizarPlantilla(plantillaSesion);
                    }

                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('popupCrearVariable').hide()");
                    String msg = getBundle("labelGeneral").getString("msg_exito_guardar");
                    getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
                } else {
                    String msgExisteVariable = bundleVariables.getString("msg_existe_varible");
                    getFacesContext().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msgExisteVariable));
                }
            } else {
                String msgErrorFormato = bundleVariables.getString("msg_error_formato");
                getFacesContext().addMessage("form-ingreso:valorDefecto",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msgErrorFormato));
            }
        } else {
            String msgErrorLongitud = bundleVariables.getString("msg_error_longitud");
            getFacesContext().addMessage("form-ingreso:valorDefecto",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msgErrorLongitud));
        }
        return null;
    }

    /**
     * Validar que la cantidad de caracteres del valor por defecto de la variable no sea mayor al indicado en la longitud
     * 
     * @return True, si la longitud corresponde a la cantidad de caracteres
     * @author dixon.alvarez
     */
    private boolean validarLongitudVariable() {
        logger.debug("CrearVariablePlantillaMB::validarLongitudVariable");
        boolean longitudValidada = false;
        if (variableRegistrar.getLongitudVariable() != null) {
            if (habilitarLongitud) {
                if (variableRegistrar.getValorDefecto().length() > variableRegistrar.getLongitudVariable()) {
                    longitudValidada = false;
                } else {
                    longitudValidada = true;
                }
            } else {
                variableRegistrar.setLongitudVariable(null);
                longitudValidada = true;
            }
        } else {
            if (habilitarLongitud) {
                variableRegistrar.setLongitudVariable(0);
            }
            longitudValidada = true;
        }
        return longitudValidada;
    }

    /**
     * Validar que el valor por defecto ingresado corresponde al formato asignado a la variable
     * 
     * @return
     * @author dixon.alvarez
     */
    private boolean validarFormatoVariable() {
        logger.debug("CrearVariablePlantillaMB::validarFormatoVariable");
        boolean validar = false;
        if (habilitarFormato && variableRegistrar.getFormatoVariable() != null
                && !variableRegistrar.getFormatoVariable().isEmpty()) {
            if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA) {

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(variableRegistrar.getFormatoVariable());
                    variableRegistrar.setValorDefecto(sdf.format(valorDefectoFecha));
                    validar = true;
                    /*
                     * if (variableRegistrar.getValorDefecto().trim().length() != sdf.toPattern().length()) { validar = false; } else {
                     * sdf.setLenient(false); sdf.parse(variableRegistrar.getValorDefecto().trim()); validar = true; }
                     */
                } catch (IllegalArgumentException e) {
                    validar = false;
                }
            } else if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO) {
                try {
                    BigDecimal numero = new BigDecimal(variableRegistrar.getValorDefecto());
                    if (Integer.valueOf(variableRegistrar.getFormatoVariable()) != numero.scale()) {
                        validar = false;
                    } else {
                        validar = true;
                    }
                } catch (NumberFormatException e) {
                    validar = false;
                }
            } else {
                validar = true;
            }
        } else {
            validar = true;
        }
        return validar;
    }

    /**
     * Habilita la opci�n de seleccionar un proceso
     * 
     * @param variableDTO
     *            Contiene los datos del ContextoAplicacionVariable
     * @author dixon.alvarez
     */
    public void habilitarProcesos() {
        logger.debug("CrearVariablePlantillaMB::habilitarProcesos");
        habilitarProceso = false;
        if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PROCESO) {
            habilitarProceso = true;
            if (variableRegistrar.getProcesoDTO() == null) {
                variableRegistrar.setProcesoDTO(new ProcesoDTO());
            }
        } else {
            variableRegistrar.setProcesoDTO(new ProcesoDTO());
        }
    }

    /**
     * Verifica si el formato ingresado es correcto con respecto al tipo de variable seleccionada, es invocado en el evento change del campo de
     * formato de variable
     * 
     * @author claudia.rodriguez
     */
    public void verificaFormatoTipoVariable() {
        logger.debug("CrearVariablePlantillaMB::verificaFormatoTipoVariable");
        if (variableRegistrar.getTipoVariableDTO() != null
                && StringUtils.isNotBlank(variableRegistrar.getFormatoVariable())) {
            String patronFormato = "";
            if (ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == variableRegistrar.getTipoVariableDTO()
                    .getIdTipoVariable()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(variableRegistrar.getFormatoVariable());
                    pattern = variableRegistrar.getFormatoVariable();
                    sdf = null;
                } catch (IllegalArgumentException e) {
                    getFacesContext().addMessage(
                            "form-ingreso:formato",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, bundleVariables
                                    .getString("msg_error_formato_invalido")));
                }
            } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL == variableRegistrar.getTipoVariableDTO()
                    .getIdTipoVariable()
                    || ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE == variableRegistrar.getTipoVariableDTO()
                            .getIdTipoVariable()) {
                patronFormato = ConstantesPlantillas.PATRON_VERIFICACION_FORMATO_IMAGEN;
            } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO == variableRegistrar.getTipoVariableDTO()
                    .getIdTipoVariable()) {
                patronFormato = ConstantesPlantillas.PATRON_VERIFICACION_FORMATO_NUMERO;
            }

            if (!patronFormato.equals("")) {
                Pattern pattern = Pattern.compile(patronFormato);
                Matcher matcher = pattern.matcher(variableRegistrar.getFormatoVariable());
                if (!matcher.find()) {
                    getFacesContext().addMessage(
                            "form-ingreso:formato",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, bundleVariables
                                    .getString("msg_error_formato_variable")));
                }
            }
        }
    }

    /**
     * Cambia el patron para el campo de valor por defecto, dependiendo del tipo de variable seleccionado
     * 
     * @author dixon.alvarez
     */
    public void verificaTipoVariable() {
        logger.debug("CrearVariablePlantillaMB::verificaTipoVariable");
        verificarAyudaFormato();
        habilitarLongitud = false;
        habilitarValorFecha = false;
        habilitarFormato = false;
        permiteCargarImagen = false;
        // Al cambiar el tipo de variable debe limpiar los valores de formato y valor por defecto
        variableRegistrar.setFormatoVariable(null);
        variableRegistrar.setValorDefecto(null);
        file = null;
        if (ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO == variableRegistrar.getTipoVariableDTO().getIdTipoVariable()) {
            pattern = ConstantesPlantillas.PATRON_VERIFICACION_VALOR_DEFECTO_NUMERO;
            habilitarLongitud = false;
            habilitarFormato = true;
        } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == variableRegistrar.getTipoVariableDTO()
                .getIdTipoVariable()) {
            valorDefectoFecha = new Date();
            pattern = ConstantesPlantillas.PATRON_VERIFICACION_VALOR_DEFECTO_FECHA;
            if (StringUtils.isNotBlank(variableRegistrar.getFormatoVariable())) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(variableRegistrar.getFormatoVariable());
                    pattern = variableRegistrar.getFormatoVariable();
                    sdf = null;
                } catch (IllegalArgumentException e) {
                    getFacesContext().addMessage(
                            "form-ingreso:formato",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, null, bundleVariables
                                    .getString("msg_error_formato_invalido")));
                }

            } else {
                variableRegistrar.setFormatoVariable(pattern);
            }
            habilitarValorFecha = true;
            habilitarFormato = true;
        } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_TEXTO == variableRegistrar.getTipoVariableDTO()
                .getIdTipoVariable()) {
            pattern = ConstantesPlantillas.PATRON_VERIFICACION_VALOR_DEFECTO_TEXTO;
            habilitarLongitud = true;
        } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE == variableRegistrar.getTipoVariableDTO()
                .getIdTipoVariable()) {
            pattern = ConstantesPlantillas.PATRON_VERIFICACION_VALOR_DEFECTO_URL_IMAGEN;
            habilitarLongitud = true;
            habilitarFormato = true;
        } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL == variableRegistrar.getTipoVariableDTO()
                .getIdTipoVariable()) {
            permiteCargarImagen = true;
            habilitarFormato = true;
        } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_URL == variableRegistrar.getTipoVariableDTO()
                .getIdTipoVariable()) {
            pattern = ConstantesPlantillas.PATRON_VERIFICACION_VALOR_DEFECTO_URL;
            habilitarLongitud = true;
        }
    }

    /**
     * Modifica el mensaje del tooltip de ayuda para diligenciar el campo formato
     */
    private void verificarAyudaFormato() {
        logger.debug("CrearVariablePlantillaMB::verificarAyudaFormato");
        mensajeAyudaFormato = "Texto";
        if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO) {
            mensajeAyudaFormato = bundleVariables.getString("mensajeAyudaFormato_numero");
        } else if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL) {
            mensajeAyudaFormato = bundleVariables.getString("mensajeAyudaFormato_imagen");
        } else if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
            mensajeAyudaFormato = bundleVariables.getString("mensajeAyudaFormato_imagen_variable");
        } else if (variableRegistrar.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA) {
            mensajeAyudaFormato = bundleVariables.getString("mensajeAyudaFormato_fecha");
        }
    }

    /**
     * Valida si en las plantillas que contiene la variable existen variables con el mismo nombre
     * 
     * @param variableDTO
     *            Contiene los datos de la variable a validar
     * @return
     */
    private boolean validarVariablesPlantilla(VariableDTO variableDTO) {
        logger.debug("CrearVariablePlantillaMB::validarVariablesPlantilla");
        if (variableRegistrar.getContextoAplicacionVariableDTO().getIdContextoAplicacion() == ConstantesPlantillas.ID_CONTEXTO_APLICACION_VARIABLE_PLANTILLA) {
            if (plantillaSesion.getListVariablesDTO() != null && !plantillaSesion.getListVariablesDTO().isEmpty()) {
                for (VariableDTO var : plantillaSesion.getListVariablesDTO()) {
                    if (variableDTO.getNombreVariable().equals(var.getNombreVariable())) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * Maneja el evento de cargar el archivo para una variable de tipo imagen
     * 
     * @param event
     *            Evento de cargue del archivo
     * @author claudia.rodriguez
     */
    public void cargarArchivo(FileUploadEvent event) {
        logger.info("CrearVariablePlantillaMB::cargarArchivo");
        BufferedImage bi;
        try {
            bi = ImageIO.read(new ByteArrayInputStream(event.getFile().getContents()));
            anchoImagen = bi.getWidth();
            altoImagen = bi.getHeight();
            if (StringUtils.isBlank(variableRegistrar.getFormatoVariable()) || file != null)
                variableRegistrar.setFormatoVariable(altoImagen + "," + anchoImagen);

            setFile(event.getFile());
            nombreArchivo = file.getFileName();
        } catch (IOException e) {
           logger.error("Error al cargar imagen de variables ", e);
        }

    }

    public VariableDTO getVariableRegistrar() {
        return variableRegistrar;
    }

    public void setVariableRegistrar(VariableDTO variableRegistrar) {
        this.variableRegistrar = variableRegistrar;
    }

    public List<SelectItem> getLstOrdenJerarquico() {
        return lstOrdenJerarquico;
    }

    public void setLstOrdenJerarquico(List<SelectItem> lstOrdenJerarquico) {
        this.lstOrdenJerarquico = lstOrdenJerarquico;
    }

    public List<SelectItem> getLstTiposVariable() {
        return lstTiposVariable;
    }

    public void setLstTiposVariable(List<SelectItem> lstTiposVariable) {
        this.lstTiposVariable = lstTiposVariable;
    }

    public List<SelectItem> getLstProcesos() {
        return lstProcesos;
    }

    public void setLstProcesos(List<SelectItem> lstProcesos) {
        this.lstProcesos = lstProcesos;
    }

    public boolean isHabilitarProceso() {
        return habilitarProceso;
    }

    public void setHabilitarProceso(boolean habilitarProceso) {
        this.habilitarProceso = habilitarProceso;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getMensajeAyudaFormato() {
        return mensajeAyudaFormato;
    }

    public void setMensajeAyudaFormato(String mensajeAyudaFormato) {
        this.mensajeAyudaFormato = mensajeAyudaFormato;
    }

    public boolean isHabilitarLongitud() {
        return habilitarLongitud;
    }

    public void setHabilitarLongitud(boolean habilitarLongitud) {
        this.habilitarLongitud = habilitarLongitud;
    }

    public boolean isHabilitarValorFecha() {
        return habilitarValorFecha;
    }

    public void setHabilitarValorFecha(boolean habilitarValorFecha) {
        this.habilitarValorFecha = habilitarValorFecha;
    }

    public Date getValorDefectoFecha() {
        return valorDefectoFecha;
    }

    public void setValorDefectoFecha(Date valorDefectoFecha) {
        this.valorDefectoFecha = valorDefectoFecha;
    }

    public PlantillaDTO getPlantillaSesion() {
        return plantillaSesion;
    }

    public void setPlantillaSesion(PlantillaDTO plantillaSesion) {
        this.plantillaSesion = plantillaSesion;
    }

    public boolean isPermiteCargarImagen() {
        return permiteCargarImagen;
    }

    public void setPermiteCargarImagen(boolean permiteCargarImagen) {
        this.permiteCargarImagen = permiteCargarImagen;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getExtensionesPermitidas() {
        return extensionesPermitidas;
    }

    public void setExtensionesPermitidas(String extensionesPermitidas) {
        this.extensionesPermitidas = extensionesPermitidas;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public boolean isHabilitarFormato() {
        return habilitarFormato;
    }

}
