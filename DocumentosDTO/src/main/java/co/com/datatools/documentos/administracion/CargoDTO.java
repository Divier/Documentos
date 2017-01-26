package co.com.datatools.documentos.administracion;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class CargoDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idCargo;
    private String nombreCargo;
    private List<ProcesoDTO> listProcesosDTO;

    // Constructors Declaration

    public CargoDTO() {

    }

    // Start Methods Declaration

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public List<ProcesoDTO> getListProcesosDTO() {
        return listProcesosDTO;
    }

    public void setListProcesosDTO(List<ProcesoDTO> listProcesosDTO) {
        this.listProcesosDTO = listProcesosDTO;
    }

    // Finish the class
}