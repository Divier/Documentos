package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;

@Remote
public interface IRVariable {

    /**
     * Se encarga de consultar las variables de �mbito de organizaci�n, asociadas a una organizaci�n espec�fica
     * 
     * @return Lista de VariableDTO
     * @author dixon.alvarez<br>
     *         claudia.rodriguez(mod 2014-09-19)
     */
    public List<VariableDTO> consultarVariableOrganizacion();

    /**
     * Se encarga de consultar las variables asociadas a una plantilla
     * 
     * @param plantillaDTO
     *            Contiene los datos de la plantilla
     * @return Lista de VariableDTO
     * @author dixon.alvarez<br>
     *         claudia.rodriguez(mod 2014-09-19)
     */
    public List<VariableDTO> consultarVariablePlantilla(PlantillaDTO plantillaDTO);

    /**
     * Se encarga de consultar las variables de contexto de proceso, asociadas a un proceso espec�fico
     * 
     * @param procesoDTO
     *            Contiene los datos del proceso
     * @return Lista de VariableDTO
     * @author dixon.alvarez<br>
     *         claudia.rodriguez(mod 2014-09-19)
     */
    public List<VariableDTO> consultarVariableProceso(ProcesoDTO procesoDTO);

    /**
     * Se encarga de registrar un nuevo registro de variable
     * 
     * @param variableDTO
     *            Contiene los datos de la entidad a persistir
     * @return Variable persistida
     * @author dixon.alvarez
     */
    public VariableDTO registrarVariable(VariableDTO variableDTO);

    /**
     * Consulta si una variable ya existe en el sistema
     * 
     * @param variableDTO
     *            Contiene los datos de la variable a validar
     * @return true si la variable ya existe, de lo contrario false
     * @author dixon.alvarez<br>
     *         claudia.rodriguez(mod 2014-09-12)
     */
    public boolean validarExistenciaVariable(VariableDTO variableDTO);

    /**
     * Actualiza los datos de la variable
     * 
     * @param variableDTO
     *            DTO con los datos de la variable a actualizar
     * @author claudia.rodriguez
     */
    public void actualizarVariable(VariableDTO variableDTO);

    /**
     * Consultar variable por id
     * @param idVariable
     * @return Variable encontrada o null en caso contrario
     */
    public VariableDTO consultarVariableId(int idVariable);
}
