package co.com.datatools.documentos.administracion;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class FuncionarioCargoDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private long idFuncionarioCargo;
    private Date fechaFin;
    private Date fechaInicio;
    private CargoDTO cargoDTO;
    private FuncionarioDTO funcionarioDTO;

    // Constructors Declaration

    public FuncionarioCargoDTO() {

    }

    // Start Methods Declaration

    public long getIdFuncionarioCargo() {
        return idFuncionarioCargo;
    }

    public void setIdFuncionarioCargo(long idFuncionarioCargo) {
        this.idFuncionarioCargo = idFuncionarioCargo;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public CargoDTO getCargoDTO() {
        return cargoDTO;
    }

    public void setCargoDTO(CargoDTO cargoDTO) {
        this.cargoDTO = cargoDTO;
    }

    public FuncionarioDTO getFuncionarioDTO() {
        return funcionarioDTO;
    }

    public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
        this.funcionarioDTO = funcionarioDTO;
    }

    // Finish the class
}