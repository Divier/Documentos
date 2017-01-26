package co.com.datatools.documentos.managed_bean.comun;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILProceso;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCatalogo;
import co.com.datatools.util.web.mb.AbstractManagedBean;

/**
 * Es el bean encargado de centralizar la carga de listas comunes y transversales a la aplicacion para reducir la cantidad de consultas sobre la base
 * de datos
 * 
 * @author julio.pinzon
 * 
 */
@ManagedBean
@SessionScoped
public class CatalogoMB extends AbstractManagedBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<SelectItem> lstProceso;
    private List<SelectItem> lstContextoAplicacionVariablePlantilla;
    private List<SelectItem> lstTipoFirmaPlantillaDTOs;
    private List<SelectItem> lstTipoIdentificacion;

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(CatalogoMB.class.getName());

    @EJB
    private ILProceso ilProceso;

    @EJB
    private IRCatalogo irCatalogo;

    public CatalogoMB() {
    }

    @PostConstruct
    public void inicializar() {
        logger.debug("CatalogoMB::inicializar");

        // Procesos
        List<ProcesoDTO> lProcesoDTOs = ilProceso.consultarProceso(new ProcesoDTO());

        lstProceso = new ArrayList<SelectItem>();
        for (ProcesoDTO procesoDTO : lProcesoDTOs) {
            lstProceso.add(new SelectItem(Long.valueOf(procesoDTO.getIdProceso()), procesoDTO.getNombreProceso()));
        }
        
        //Tipos de Documentos
        List<TipoIdentificacionDTO> lTipoIdentificacionDTOs = irCatalogo.consultarTipoIdentificacion();
        lstTipoIdentificacion = new ArrayList<SelectItem>();
        if(lTipoIdentificacionDTOs != null && !lTipoIdentificacionDTOs.isEmpty()) {
	        for (TipoIdentificacionDTO tipoIdentificacionDTO : lTipoIdentificacionDTOs) {
	        	lstTipoIdentificacion.add(new SelectItem(tipoIdentificacionDTO.getSiglaTipoIdentificacion(), tipoIdentificacionDTO.getNombreTipoIdentificacion()));
	        }
        }

        // ContextosAplicacionVariable
        List<ContextoAplicacionVariableDTO> lContextoAplicacionVariableDTOs = irCatalogo
                .consultarContextoAplicacionVariable();
        lstContextoAplicacionVariablePlantilla = new ArrayList<SelectItem>();
        if (lContextoAplicacionVariableDTOs != null && !lContextoAplicacionVariableDTOs.isEmpty()) {
            for (ContextoAplicacionVariableDTO contextoAplicacionVariableDTO : lContextoAplicacionVariableDTOs) {
                lstContextoAplicacionVariablePlantilla.add(new SelectItem(Integer.valueOf(contextoAplicacionVariableDTO
                        .getIdContextoAplicacion()), contextoAplicacionVariableDTO.getNombreContextoAplicacion()));
            }
        }

        // TipoFirmaPlantilla
        List<TipoFirmaPlantillaDTO> lTipoFirmaPlantillaDTOs = irCatalogo.consultarTipoFirmaPlantilla();
        lstTipoFirmaPlantillaDTOs = new ArrayList<SelectItem>();
        if (lTipoFirmaPlantillaDTOs != null && !lTipoFirmaPlantillaDTOs.isEmpty()) {
            for (TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO : lTipoFirmaPlantillaDTOs) {
                lstTipoFirmaPlantillaDTOs.add(new SelectItem(Integer.valueOf(tipoFirmaPlantillaDTO
                        .getIdTipoFirmaPlantilla()), tipoFirmaPlantillaDTO.getNombre()));
            }
        }
    }

    public List<SelectItem> getLstProceso() {
        return lstProceso;
    }

    public void setLstProceso(List<SelectItem> lstProceso) {
        this.lstProceso = lstProceso;
    }

    public List<SelectItem> getLstContextoAplicacionVariablePlantilla() {
        return lstContextoAplicacionVariablePlantilla;
    }

    public void setLstContextoAplicacionVariablePlantilla(List<SelectItem> lstContextoAplicacionVariablePlantilla) {
        this.lstContextoAplicacionVariablePlantilla = lstContextoAplicacionVariablePlantilla;
    }

    public List<SelectItem> getLstTipoFirmaPlantillaDTOs() {
        return lstTipoFirmaPlantillaDTOs;
    }

    public void setLstTipoFirmaPlantillaDTOs(List<SelectItem> lstTipoFirmaPlantillaDTOs) {
        this.lstTipoFirmaPlantillaDTOs = lstTipoFirmaPlantillaDTOs;
    }

	public List<SelectItem> getLstTipoIdentificacion() {
		return lstTipoIdentificacion;
	}

	public void setLstTipoIdentificacion(List<SelectItem> lstTipoIdentificacion) {
		this.lstTipoIdentificacion = lstTipoIdentificacion;
	}
}
