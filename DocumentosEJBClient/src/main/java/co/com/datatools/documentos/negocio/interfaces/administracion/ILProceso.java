package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Local
public interface ILProceso {

    /**
     * @see IRProceso#consultarProceso(ProcesoDTO)
     */
    public List<ProcesoDTO> consultarProceso(ProcesoDTO procesoDTO);

    /**
     * @see IRProceso#registrarProceso(ProcesoDTO)
     */
    public ProcesoDTO registrarProceso(ProcesoDTO procesoDTO) throws DocumentosException;

    /**
     * @see IRProceso#actualizarProceso(ProcesoDTO)
     */
    public void actualizarProceso(ProcesoDTO procesoDTO);

    /**
     * @see IRProceso#consultarProcesoId(long)
     */
    public ProcesoDTO consultarProcesoId(long idProceso);

    /**
     * @see IRProceso#consultarProceso(String)
     */
    public ProcesoDTO consultarProceso(String codigoProceso);

}
