package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.TipoDatoDTO;
import co.com.datatools.documentos.administracion.TipoFechaReferenciaDTO;
import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.administracion.TipoVariableDTO;

@Remote
public interface IRCatalogo {

    /**
     * Consulta los contextos de las variables
     * 
     * @return Lista ContextoAplicacionVariableDTO
     * @author dixon.alvarez
     */
    public List<ContextoAplicacionVariableDTO> consultarContextoAplicacionVariable();

    /**
     * Consulta un ContextoAplicacionVariable por su id en el sistema
     * 
     * @param idContextoAplicacionVariable
     *            Id del ContextoAplicacionVariable a buscar
     * @return ContextoAplicacionVariableDTO
     */
    public ContextoAplicacionVariableDTO consultarContextAplicacionVariableId(int idContextoAplicacionVariable);

    /**
     * consulta los tipos de variable del sistema
     * 
     * @return Lista de TipoVariableDTO
     */
    public List<TipoVariableDTO> consultarTipoVariable();

    /**
     * Se encarga de consultar las firmas de plantilla definiddas en el sistema
     * 
     * @return Lista de TipoFirmaPlantillaDTOs
     */
    public List<TipoFirmaPlantillaDTO> consultarTipoFirmaPlantilla();

    /**
     * Consulta los tipos de fecha de referencia
     * 
     * @return Lista TipoFechaReferenciaDTOs
     * @author dixon.alvarez
     */
    public List<TipoFechaReferenciaDTO> consultarTipoFechaReferencia();

    /**
     * consulta los tipos de variable del sistema
     * 
     * @return Lista de TipoVariableDTO
     */
    public List<TipoDatoDTO> consultarTipoDato();

    /**
     * Consulta los tipos de identificacion
     * 
     * @return Lista de TipoIdentificacionDTO
     */
    public List<TipoIdentificacionDTO> consultarTipoIdentificacion();

}
