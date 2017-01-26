package co.com.datatools.documentos.administracion;

import java.io.Serializable;
import java.util.List;

import co.com.datatools.documentos.plantillas.PlantillaDTO;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class ProcesoDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private long idProceso;
    private String descripcionProceso;
    private String nombreProceso;
    private String codigoProceso;
    private List<CargoDTO> listCargosDTO;
    private List<PlantillaDTO> listPlantillasDTO;
    private ProcesoDTO procesoDTO;
    private List<ProcesoDTO> listProcesosDTO;

    // Constructors Declaration

    public ProcesoDTO() {

    }

    // Start Methods Declaration

    public long getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(long idProceso) {
        this.idProceso = idProceso;
    }

    public String getDescripcionProceso() {
        return descripcionProceso;
    }

    public void setDescripcionProceso(String descripcionProceso) {
        this.descripcionProceso = descripcionProceso;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public List<CargoDTO> getListCargosDTO() {
        return listCargosDTO;
    }

    public void setListCargosDTO(List<CargoDTO> listCargosDTO) {
        this.listCargosDTO = listCargosDTO;
    }

    public List<PlantillaDTO> getListPlantillasDTO() {
        return listPlantillasDTO;
    }

    public void setListPlantillasDTO(List<PlantillaDTO> listPlantillasDTO) {
        this.listPlantillasDTO = listPlantillasDTO;
    }

    public ProcesoDTO getProcesoDTO() {
        return procesoDTO;
    }

    public void setProcesoDTO(ProcesoDTO procesoDTO) {
        this.procesoDTO = procesoDTO;
    }

    public List<ProcesoDTO> getListProcesosDTO() {
        return listProcesosDTO;
    }

    public void setListProcesosDTO(List<ProcesoDTO> listProcesosDTO) {
        this.listProcesosDTO = listProcesosDTO;
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    // Finish the class
}