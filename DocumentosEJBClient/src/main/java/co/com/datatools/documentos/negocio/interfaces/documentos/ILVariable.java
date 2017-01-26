package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;

@Local
public interface ILVariable {

    /**
     * @see IRVariable#consultarVariableOrganizacion()
     */
    public List<VariableDTO> consultarVariableOrganizacion();

    /**
     * @see IRVariable#consultarVariablePlantilla(PlantillaDTO)
     */
    public List<VariableDTO> consultarVariablePlantilla(PlantillaDTO plantillaDTO);

    /**
     * @see IRVariable#consultarVariableProceso(ProcesoDTO)
     */
    public List<VariableDTO> consultarVariableProceso(ProcesoDTO procesoDTO);

    /**
     * @see IRVariable#registrarVariable(VariableDTO)
     */
    public VariableDTO registrarVariable(VariableDTO variableDTO);

    /**
     * @see IRVariable#validarVariable(VariableDTO)
     */
    public boolean validarExistenciaVariable(VariableDTO variableDTO);

    /**
     * @see IRVariable#actualizarVariable(VariableDTO)
     */
    public void actualizarVariable(VariableDTO variableDTO);

    /**
     * @see IRVariable#consultarVariableId(int)
     */
    public VariableDTO consultarVariableId(int idVariable);
}
