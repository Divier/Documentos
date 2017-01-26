package co.com.datatools.documentos.administracion;

import java.io.Serializable;

/**
 * DTO creado para enviar los filtros a la consulta de funcionarios
 * 
 * @author claudia.rodriguez
 * 
 */
public class ConsultaFuncionarioDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * DTO con los filtros de datos del funcionario
     */
    private FuncionarioDTO funcionarioDTO;

    /**
     * Si se incluye el filtro por cargo en el funcionarioDTO, este atributo indica si el funcionario debe tener dicho cargo actualmente
     * asignado(fecha fin==null), si es false consulta todas las asignaciones del cargo sin importar las fechas
     */
    private boolean cargoVigente;

    /**
     * Indica si debe buscar unicamente los funcionarios que tengan firma mecanica configurada, de lo contrario busca todos los funcionarios que
     * cumplan con los demas criterios sin importar si tengan firma mecanica o no
     */
    private boolean tieneFirma;

    public FuncionarioDTO getFuncionarioDTO() {
        return funcionarioDTO;
    }

    public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
        this.funcionarioDTO = funcionarioDTO;
    }

    public boolean isCargoVigente() {
        return cargoVigente;
    }

    public void setCargoVigente(boolean cargoVigente) {
        this.cargoVigente = cargoVigente;
    }

    public boolean isTieneFirma() {
        return tieneFirma;
    }

    public void setTieneFirma(boolean tieneFirma) {
        this.tieneFirma = tieneFirma;
    }

}
