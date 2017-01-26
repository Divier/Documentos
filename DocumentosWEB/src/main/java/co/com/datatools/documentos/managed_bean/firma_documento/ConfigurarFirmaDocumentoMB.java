package co.com.datatools.documentos.managed_bean.firma_documento;

/**
 * ManagedBean que gestiona la pagina de configuracion de firma mecanica para la plantilla
 * 
 * @author dixon.alvarez
 */
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoFechaReferenciaDTO;
import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.managed_bean.variables.CrearVariablePlantillaMB;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCargo;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCatalogo;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRProceso;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRFirmaPlantilla;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRVariable;
import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;
import co.com.datatools.documentos.util.ConstantesManagedBean;
import co.com.datatools.util.web.mb.AbstractManagedBean;

import com.google.gson.Gson;

@ManagedBean
@SessionScoped
public class ConfigurarFirmaDocumentoMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(CrearVariablePlantillaMB.class.getName());
    private FacesContext fc;
    private FirmaPlantillaDTO firmaConfigurar;
    private List<SelectItem> lstProcesoDTOs;
    private ProcesoDTO procesoSeleccionado;
    private List<SelectItem> lstCargosProceso;
    private boolean seleccionarProceso;
    private List<SelectItem> lstTipoFirmaPlantillaDTOs;
    private List<SelectItem> lstTiposFechaReferencia;
    private FuncionarioDTO funcionarioBuscar;
    private List<SelectItem> lstTipoIdentificacion;
    private List<FuncionarioDTO> lstFuncionarioDTOs;
    private List<CargoDTO> lstFuncionarioCargoDTOs;
    private PlantillaDTO plantillaSesion;
    private List<SelectItem> lVariablesFecha;
    private FuncionarioDTO funcionarioSeleccionado;
    private String variablePlantillaSeleccionada;
    private boolean verVariablesPlantilla;
    private boolean verTiposFechaReferencia;
    private boolean verOpcionesFuncionario;
    private boolean funcionariosConsultados;
    private List<VariableDTO> lVariableDTOs;

    /*
     * Instancia de GSON para convertir objetos a JSON
     */
    private Gson gson = new Gson();

    // private String ruta_pagina_pruebas = "/protected/pruebas/pruebas.xhtml";
    private static final String NOMBRE_BUNDLE_DOCUMENTOS = "labelDocumentos";

    @EJB
    private IRProceso irProceso;

    @EJB
    private IRCatalogo irCatalogo;

    @EJB
    private IRFuncionario irFuncionario;

    @EJB
    private IRCargo irCargo;

    @EJB
    private IRVariable irVariable;

    @EJB
    private IRFirmaPlantilla irFirmaPlantilla;

    /**
     * Bundle de mensajes
     */
    private ResourceBundle bundle;

    public ConfigurarFirmaDocumentoMB() {
        logger.debug("ConfigurarFirmaDocumentoMB::ConfigurarFirmaDocumentoMB");
        bundle = getBundle(NOMBRE_BUNDLE_DOCUMENTOS);
        if (firmaConfigurar == null) {
            firmaConfigurar = new FirmaPlantillaDTO();
            firmaConfigurar.setTipoFechaReferenciaDTO(new TipoFechaReferenciaDTO());
            firmaConfigurar.setCargoDTO(new CargoDTO());
        }
        if (funcionarioBuscar == null) {
            funcionarioBuscar = new FuncionarioDTO();
        }
    }

    /**
     * Inicializa los valores del Bean
     */
    @PostConstruct
    public void init() {
        logger.debug("ConfigurarFirmaDocumentoMB::init");
        fc = FacesContext.getCurrentInstance();

        // TipoFirmaPlantilla
        List<TipoFirmaPlantillaDTO> lTipoFirmaPlantillaDTOs = irCatalogo.consultarTipoFirmaPlantilla();
        lstTipoFirmaPlantillaDTOs = new ArrayList<SelectItem>();
        if (lTipoFirmaPlantillaDTOs != null && !lTipoFirmaPlantillaDTOs.isEmpty()) {
            for (TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO : lTipoFirmaPlantillaDTOs) {
                lstTipoFirmaPlantillaDTOs.add(new SelectItem(Integer.valueOf(tipoFirmaPlantillaDTO
                        .getIdTipoFirmaPlantilla()), tipoFirmaPlantillaDTO.getNombre()));
            }
            firmaConfigurar.setTipoFirmaPlantillaDTO(lTipoFirmaPlantillaDTOs.get(0));
        }
        // Procesos
        procesoSeleccionado = new ProcesoDTO();
        ProcesoDTO procesoDTO = new ProcesoDTO();
        List<ProcesoDTO> lProcesoDTOs = irProceso.consultarProceso(procesoDTO);
        lstProcesoDTOs = new ArrayList<SelectItem>();
        if (lProcesoDTOs != null && !lProcesoDTOs.isEmpty()) {
            for (ProcesoDTO procesoDTO2 : lProcesoDTOs) {
                lstProcesoDTOs
                        .add(new SelectItem(Long.valueOf(procesoDTO2.getIdProceso()), procesoDTO2.getNombreProceso()));
            }
        }

        // Tipos de fecha referencia
        List<TipoFechaReferenciaDTO> lTipoFechaReferenciaDTOs = irCatalogo.consultarTipoFechaReferencia();
        if (lTipoFechaReferenciaDTOs != null && !lTipoFechaReferenciaDTOs.isEmpty()) {
            lstTiposFechaReferencia = new ArrayList<SelectItem>();
            for (TipoFechaReferenciaDTO tipoFechaReferenciaDTO : lTipoFechaReferenciaDTOs) {
                lstTiposFechaReferencia.add(new SelectItem(Integer.valueOf(tipoFechaReferenciaDTO.getIdTipoFecha()),
                        tipoFechaReferenciaDTO.getNombreTipoFecha()));
            }
        }

        // Tipos de identificacion
        List<TipoIdentificacionDTO> lTipoIdentificacionDTOs = irCatalogo.consultarTipoIdentificacion();
        if (lTipoIdentificacionDTOs != null && !lTipoIdentificacionDTOs.isEmpty()) {
            lstTipoIdentificacion = new ArrayList<SelectItem>();
            for (TipoIdentificacionDTO tipoIdentificacionDTO : lTipoIdentificacionDTOs) {
                lstTipoIdentificacion.add(new SelectItem(tipoIdentificacionDTO.getSiglaTipoIdentificacion(),
                        tipoIdentificacionDTO.getNombreTipoIdentificacion()));
            }
        }
        validarTipoFirma();
    }

    /**
     * Obtiene los cargos relacionados al proceso seleccionado
     * 
     * @author dixon.alvarez
     */
    public void obtenerCargosProceso() {
        logger.debug("ConfigurarFirmaDocumentoMB::obtenerCargosProceso");
        lstCargosProceso = new ArrayList<SelectItem>();
        firmaConfigurar.setCargoDTO(new CargoDTO());
        List<CargoDTO> lCargoDTOs = (irProceso.consultarProcesoId(procesoSeleccionado.getIdProceso()))
                .getListCargosDTO();
        if (lCargoDTOs != null && !lCargoDTOs.isEmpty()) {
            for (CargoDTO cargoDTO : lCargoDTOs) {
                lstCargosProceso.add(new SelectItem(Integer.valueOf(cargoDTO.getIdCargo()), cargoDTO.getNombreCargo()));
            }
        }
    }

    /**
     * Habilita las opciones a diligenciar en el formulario
     * 
     * @author dixon.alvarez
     */
    public void validarTipoFirma() {
        logger.debug("ConfigurarFirmaDocumentoMB::habilitarTipoFirma");
        seleccionarProceso = false;
        verTiposFechaReferencia = false;
        verOpcionesFuncionario = false;
        verVariablesPlantilla = false;
        if (firmaConfigurar.getTipoFirmaPlantillaDTO() != null
                && firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() != 0
                && firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_CARGO) {
            seleccionarProceso = true;
            verTiposFechaReferencia = true;
            funcionarioSeleccionado = null;
        } else if (firmaConfigurar.getTipoFirmaPlantillaDTO() != null
                && firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() != 0
                && firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_FUNCIONARIO) {
            verOpcionesFuncionario = true;
            firmaConfigurar.getTipoFechaReferenciaDTO().setIdTipoFecha(ConstantesPlantillas.ID_TIPO_FECHA_REFERENCIA_FECHA_ACTUAL);
            consultarVariablesFecha();
            funcionariosConsultados = false;
        } else if (firmaConfigurar.getTipoFirmaPlantillaDTO() != null
                && firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() != 0
                && firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_FUNCIONARIO_GENERA_DOCUMENTO) {
            verTiposFechaReferencia = false;
            seleccionarProceso = false;
            verOpcionesFuncionario = false;
            funcionarioSeleccionado = null;
            firmaConfigurar.getTipoFechaReferenciaDTO().setIdTipoFecha(ConstantesPlantillas.ID_TIPO_FECHA_REFERENCIA_FECHA_ACTUAL);
            consultarVariablesFecha();
        }
    }

    /**
     * Consulta las variables de tipo fecha, relacionadas a la plantilla en sesion
     * 
     * @author dixon.alvarez
     */
    public void consultarVariablesFecha() {
        logger.debug("ConfigurarFirmaDocumentoMB::consultarVariablesFecha");
        verVariablesPlantilla = false;
        variablePlantillaSeleccionada = "";
        if (firmaConfigurar.getTipoFechaReferenciaDTO().getIdTipoFecha() == ConstantesPlantillas.ID_TIPO_FECHA_REFERENCIA_FECHA_VARIABLE_PLANTILLA) {
            verVariablesPlantilla = true;
            lVariablesFecha = new ArrayList<SelectItem>();
            if (plantillaSesion != null) {
                if (lVariableDTOs != null && !lVariableDTOs.isEmpty()) {
                    for (VariableDTO variableDTO : lVariableDTOs) {
                        if (variableDTO.getTipoVariableDTO().getIdTipoVariable() == ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA) {
                            lVariablesFecha.add(new SelectItem(variableDTO.getNombreVariable(), variableDTO
                                    .getNombreVariable()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Agrega de nuevo a la sesion el objeto plantilla, ya con la firma cargada
     * 
     * @author dixon.alvarez
     */
    public void modificarPlantillaSesion() {
        logger.debug("ConfigurarFirmaDocumentoMB::modificarPlantillaSesion");
        fc = FacesContext.getCurrentInstance();
        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_PLANTILLA_DOCUMENTOS);
        addSessionObject(ConstantesManagedBean.NOMBRE_OBJ_PLANTILLA_DOCUMENTOS, this.plantillaSesion);
    }

    /**
     * Guarda la configuraci�n de la firma para una plantilla
     * 
     * @return
     * @author dixon.alvarez
     */
    public void guardarConfiguracionFirma() {
        logger.debug("ConfigurarFirmaDocumentoMB::guardarConfiguracionFirma");
        fc = FacesContext.getCurrentInstance();
        FirmaPlantillaDTO firmaPlantillaDTO = new FirmaPlantillaDTO();
        
        //Completa el objeto con el nombre
        for (SelectItem tipoFirmaPlantilla : lstTipoFirmaPlantillaDTOs) {
            if (tipoFirmaPlantilla.getValue().equals(
                    firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla())) {
                firmaConfigurar.getTipoFirmaPlantillaDTO().setNombre(tipoFirmaPlantilla.getLabel());
                break;
            }
        }

        firmaPlantillaDTO.setTipoFirmaPlantillaDTO(firmaConfigurar.getTipoFirmaPlantillaDTO());
        firmaPlantillaDTO.setMostrarNombreFuncionario(firmaConfigurar.isMostrarNombreFuncionario());
        firmaPlantillaDTO.setMostrarCargoFuncionario(firmaConfigurar.isMostrarCargoFuncionario());

        // Tipo firma por cargo
        if (firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_CARGO) {
            
            //Completa el objeto con el nombre
            for (SelectItem cargoProceso : lstCargosProceso) {
                if (cargoProceso.getValue().equals(
                        firmaConfigurar.getCargoDTO().getIdCargo())) {
                    firmaConfigurar.getCargoDTO().setNombreCargo(cargoProceso.getLabel());
                    break;
                }
            }
            firmaPlantillaDTO.setCargoDTO(firmaConfigurar.getCargoDTO());
        }
        // Tipo firma por funcionario
        else if (firmaConfigurar.getTipoFirmaPlantillaDTO().getIdTipoFirmaPlantilla() == ConstantesPlantillas.ID_TIPO_FIRMA_PLANTILLA_FUNCIONARIO) {
            if (funcionarioSeleccionado != null) {
                
                //Completa el objeto con el nombre
                for (FuncionarioDTO funcionario : lstFuncionarioDTOs) {
                    if (funcionario.getIdFuncionario() == 
                            funcionarioSeleccionado.getIdFuncionario()) {
                        funcionarioSeleccionado.setNombreFuncionario(funcionario.getNombreFuncionario());
                        break;
                    }
                }
                firmaPlantillaDTO.setFuncionarioDTO(funcionarioSeleccionado);
            } else {
                fc.addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
                                .getString("msg_validacion_funcionario")));
                return;
            }
        }
        
        //Asigna la fecha de referencia
        if(firmaConfigurar.getTipoFechaReferenciaDTO() != null) {
            //Completa el objeto con el nombre
            for (SelectItem tipoReferencia : lstTiposFechaReferencia) {
                if (tipoReferencia.getValue().equals(
                        firmaConfigurar.getTipoFechaReferenciaDTO().getIdTipoFecha())) {
                    firmaConfigurar.getTipoFechaReferenciaDTO().setNombreTipoFecha(tipoReferencia.getLabel());
                    break;
                }
            }
            firmaPlantillaDTO.setTipoFechaReferenciaDTO(firmaConfigurar.getTipoFechaReferenciaDTO());            
        }
        
        // Variables para tipo fecha referencia plantilla
        if (firmaPlantillaDTO.getTipoFechaReferenciaDTO() != null
                && firmaPlantillaDTO.getTipoFechaReferenciaDTO().getIdTipoFecha() == ConstantesPlantillas.ID_TIPO_FECHA_REFERENCIA_FECHA_VARIABLE_PLANTILLA) {

            for (VariableDTO var : lVariableDTOs) {
                if (variablePlantillaSeleccionada.equals(var.getNombreVariable())) {
                    firmaPlantillaDTO.setVariableDTO(var);
                    break;
                }
            }
        }
        // Se adiciona la firma configurada a la plantilla en sesion
        if (this.plantillaSesion.getListFirmasPlantillaDTO() == null) {
            this.plantillaSesion.setListFirmasPlantillaDTO(new ArrayList<FirmaPlantillaDTO>());
        }

        // Generar el popup de confirmacion de guardado
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("Documentos.Plantillas.addFirmaMecanica(" + gson.toJson(firmaPlantillaDTO) + ")");
        context.execute("PF('popupConfigurarFirmaDocumento').hide()");
        // removeSessionObject("configurarFirmaDocumentoMB");
        // context.update(":form-configura");
        // return null;
    }

    /**
     * Consulta los funcionarios que tienen firma teniendo en cuenta el filtro diligenciado
     * 
     * @author dixon.alvarez
     */
    public void consultarFuncionario() {
        logger.debug("ConfigurarFirmaDocumentoMB::consultarFuncionario");
        fc = FacesContext.getCurrentInstance();
        funcionarioSeleccionado = null;
        if(StringUtils.isNotBlank(funcionarioBuscar.getNumeroDocumIdent()) && 
                !StringUtils.isNotBlank(funcionarioBuscar.getSiglaTipoIdentificacion())) {
            String msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
            getFacesContext().addMessage("form-configura:selOneMenTipoDocumento",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            return;
        } else if(!StringUtils.isNotBlank(funcionarioBuscar.getNumeroDocumIdent()) && 
                        StringUtils.isNotBlank(funcionarioBuscar.getSiglaTipoIdentificacion())) {
            String msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("requerido");
            getFacesContext().addMessage("form-configura:txtNumeroDocumento",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            return;            
        } else if (!StringUtils.isNotBlank(funcionarioBuscar.getNombreFuncionario())
                && !StringUtils.isNotBlank(funcionarioBuscar.getNumeroDocumIdent())) {
            String mensajeValidacion = bundle.getString("mensaje_validacion_filtro_funcionario");
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", mensajeValidacion));
            return;
        }
        
        ConsultaFuncionarioDTO consultaFuncionarioDTO = new ConsultaFuncionarioDTO();
        consultaFuncionarioDTO.setFuncionarioDTO(funcionarioBuscar);
        consultaFuncionarioDTO.setTieneFirma(true);
        List<FuncionarioDTO> lstFuncDTOs = irFuncionario.consultarFuncionario(consultaFuncionarioDTO);
        lstFuncionarioDTOs = new ArrayList<FuncionarioDTO>();
        for (FuncionarioDTO funcionarioDTO : lstFuncDTOs) {
            lstFuncionarioDTOs.add(funcionarioDTO);
        }
        funcionariosConsultados = true;
    }

    /**
     * Consultar Cargos relacionados al funcionario enviado como parametro
     * 
     * @param funcionarioDTO
     *            FuncionarioDTO a filtrar
     * @author dixon.alvarez
     */
    public void consultarFuncionarioCargos() {
        logger.debug("ConfigurarFirmaDocumentoMB::consultarFuncionarioCargos");
        FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
        funcionarioCargoDTO.setFuncionarioDTO(funcionarioSeleccionado);
        List<FuncionarioCargoDTO> lFuncionarioCargos = new ArrayList<FuncionarioCargoDTO>();
        lFuncionarioCargos = irFuncionario.consultarFuncionarioCargo(funcionarioCargoDTO);
        lstFuncionarioCargoDTOs = new ArrayList<CargoDTO>();
        if (lFuncionarioCargos != null && !lFuncionarioCargos.isEmpty()) {
            for (FuncionarioCargoDTO funCargoDTO : lFuncionarioCargos) {
                if (funCargoDTO.getCargoDTO() != null && funCargoDTO.getCargoDTO().getIdCargo() != 0) {
                    CargoDTO cargoDTO = irCargo.consultarCargoId(funCargoDTO.getCargoDTO().getIdCargo());
                    lstFuncionarioCargoDTOs.add(cargoDTO);
                }
            }
        }
        // Mostrar el popup de detalle de cargos
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('popupCargosFuncionario').show()");
    }

    public void handleClose(CloseEvent event) {
        removeSessionObject(ConstantesManagedBean.NOMBRE_OBJ_BEAN_FIRMA_PLANTILLA);
    }

    public FirmaPlantillaDTO getFirmaConfigurar() {
        return firmaConfigurar;
    }

    public void setFirmaConfigurar(FirmaPlantillaDTO firmaConfigurar) {
        this.firmaConfigurar = firmaConfigurar;
    }

    public ProcesoDTO getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(ProcesoDTO procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public List<SelectItem> getLstProcesoDTOs() {
        return lstProcesoDTOs;
    }

    public void setLstProcesoDTOs(List<SelectItem> lstProcesoDTOs) {
        this.lstProcesoDTOs = lstProcesoDTOs;
    }

    public List<SelectItem> getLstCargosProceso() {
        return lstCargosProceso;
    }

    public void setLstCargosProceso(List<SelectItem> lstCargosProceso) {
        this.lstCargosProceso = lstCargosProceso;
    }

    public boolean isSeleccionarProceso() {
        return seleccionarProceso;
    }

    public void setSeleccionarProceso(boolean seleccionarProceso) {
        this.seleccionarProceso = seleccionarProceso;
    }

    public List<SelectItem> getLstTipoFirmaPlantillaDTOs() {
        return lstTipoFirmaPlantillaDTOs;
    }

    public void setLstTipoFirmaPlantillaDTOs(List<SelectItem> lstTipoFirmaPlantillaDTOs) {
        this.lstTipoFirmaPlantillaDTOs = lstTipoFirmaPlantillaDTOs;
    }

    public List<SelectItem> getLstTiposFechaReferencia() {
        return lstTiposFechaReferencia;
    }

    public void setLstTiposFechaReferencia(List<SelectItem> lstTiposFechaReferencia) {
        this.lstTiposFechaReferencia = lstTiposFechaReferencia;
    }

    public FuncionarioDTO getFuncionarioBuscar() {
        return funcionarioBuscar;
    }

    public void setFuncionarioBuscar(FuncionarioDTO funcionarioBuscar) {
        this.funcionarioBuscar = funcionarioBuscar;
    }

    public List<SelectItem> getLstTipoIdentificacion() {
        return lstTipoIdentificacion;
    }

    public void setLstTipoIdentificacion(List<SelectItem> lstTipoIdentificacion) {
        this.lstTipoIdentificacion = lstTipoIdentificacion;
    }

    public List<FuncionarioDTO> getLstFuncionarioDTOs() {
        return lstFuncionarioDTOs;
    }

    public void setLstFuncionarioDTOs(List<FuncionarioDTO> lstFuncionarioDTOs) {
        this.lstFuncionarioDTOs = lstFuncionarioDTOs;
    }

    public List<CargoDTO> getLstFuncionarioCargoDTOs() {
        return lstFuncionarioCargoDTOs;
    }

    public void setLstFuncionarioCargoDTOs(List<CargoDTO> lstFuncionarioCargoDTOs) {
        this.lstFuncionarioCargoDTOs = lstFuncionarioCargoDTOs;
    }

    public PlantillaDTO getPlantillaSesion() {
        return plantillaSesion;
    }

    public void setPlantillaSesion(PlantillaDTO plantillaSesion) {
        this.plantillaSesion = plantillaSesion;
    }

    public List<SelectItem> getlVariablesFecha() {
        return lVariablesFecha;
    }

    public void setlVariablesFecha(List<SelectItem> lVariablesFecha) {
        this.lVariablesFecha = lVariablesFecha;
    }

    public FuncionarioDTO getFuncionarioSeleccionado() {
        return funcionarioSeleccionado;
    }

    public void setFuncionarioSeleccionado(FuncionarioDTO funcionarioSeleccionado) {
        this.funcionarioSeleccionado = funcionarioSeleccionado;
    }

    public String getVariablePlantillaSeleccionada() {
        return variablePlantillaSeleccionada;
    }

    public void setVariablePlantillaSeleccionada(String variablePlantillaSeleccionada) {
        this.variablePlantillaSeleccionada = variablePlantillaSeleccionada;
    }

    public boolean isVerVariablesPlantilla() {
        return verVariablesPlantilla;
    }

    public void setVerVariablesPlantilla(boolean verVariablesPlantilla) {
        this.verVariablesPlantilla = verVariablesPlantilla;
    }

    public boolean isVerTiposFechaReferencia() {
        return verTiposFechaReferencia;
    }

    public void setVerTiposFechaReferencia(boolean verTiposFechaReferencia) {
        this.verTiposFechaReferencia = verTiposFechaReferencia;
    }

    public boolean isVerOpcionesFuncionario() {
        return verOpcionesFuncionario;
    }

    public void setVerOpcionesFuncionario(boolean verOpcionesFuncionario) {
        this.verOpcionesFuncionario = verOpcionesFuncionario;
    }

    public boolean isFuncionariosConsultados() {
        return funcionariosConsultados;
    }

    public void setFuncionariosConsultados(boolean funcionariosConsultados) {
        this.funcionariosConsultados = funcionariosConsultados;
    }

    public List<VariableDTO> getlVariableDTOs() {
        return lVariableDTOs;
    }

    public void setlVariableDTOs(List<VariableDTO> lVariableDTOs) {
        this.lVariableDTOs = lVariableDTOs;
    }

}
