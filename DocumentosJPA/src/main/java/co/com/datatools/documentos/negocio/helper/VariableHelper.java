package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.administracion.TipoVariableDTO;
import co.com.datatools.documentos.entidades.TipoVariable;
import co.com.datatools.documentos.entidades.Variable;
import co.com.datatools.documentos.plantillas.VariableDTO;

/**
 * Clase que permite realizar las transformaciones entre DTO y entidad para una variable
 * 
 **/
public class VariableHelper {

    /**
     * Transforma la entidad en DTO
     * 
     * @param variable
     *            entidad a transformar
     * @return DTO con los datos de la entidad
     */
    public static VariableDTO toVariableDTO(Variable variable) {
        VariableDTO variableDTO = new VariableDTO();
        variableDTO.setIdVariable(variable.getIdVariable());
        variableDTO.setDescripcionVariable(variable.getDescripcionVariable());
        variableDTO.setFormatoVariable(variable.getFormatoVariable());
        variableDTO.setLongitudVariable(variable.getLongitudVariable());
        variableDTO.setNombreVariable(variable.getNombreVariable());
        variableDTO.setValorDefecto(variable.getValorDefecto());
        if (variable.getTipoVariable() != null) {
            TipoVariableDTO tipoVariableDTO = new TipoVariableDTO();
            tipoVariableDTO.setIdTipoVariable(variable.getTipoVariable().getIdTipoVariable());
            variableDTO.setTipoVariableDTO(tipoVariableDTO);
        }
        variableDTO.setFechaCreacion(variable.getFechaCreacion());
        variableDTO.setPalabraClave(variable.getPalabraClave());
        variableDTO.setImagen(variable.getImagen());
        return variableDTO;
    }

    /**
     * Transforma un DTO a una entidad
     * 
     * @param variableDTO
     *            Dto con los datos a asignar a la entidad
     * @param variable
     *            Entidad base para asignar los datos
     * @return Entidad con los datos del DTO
     */
    public static Variable toVariable(VariableDTO variableDTO, Variable variable) {
        if (null == variable) {
            variable = new Variable();
        }
        variable.setIdVariable(variableDTO.getIdVariable());
        variable.setDescripcionVariable(variableDTO.getDescripcionVariable());
        variable.setFormatoVariable(variableDTO.getFormatoVariable());
        if (variableDTO.getLongitudVariable() != null) {
            variable.setLongitudVariable(variableDTO.getLongitudVariable());            
        }
        variable.setNombreVariable(variableDTO.getNombreVariable());
        variable.setValorDefecto(variableDTO.getValorDefecto());
        if (variableDTO.getTipoVariableDTO() != null) {
            TipoVariable tipoVariable = new TipoVariable();
            tipoVariable.setIdTipoVariable(variableDTO.getTipoVariableDTO().getIdTipoVariable());
            variable.setTipoVariable(tipoVariable);
        }
        variable.setFechaCreacion(variableDTO.getFechaCreacion());
        variable.setPalabraClave(variableDTO.getPalabraClave());
        if (variableDTO.getImagen() != null) {
            variable.setImagen(variableDTO.getImagen());            
        }
        return variable;
    }

}