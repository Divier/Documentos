package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Remote
public interface IRProceso {

    /**
     * Consulta los procesos que se hayan generado teniendo en cuenta los filtros ingresados
     * 
     * @param procesoDTO
     *            Contiene los datos a filtrar
     * @return Lista de ProcesoDTOs
     * 
     * @author dixon.alvarez<br>
     *         claudia.rodriguez(mod 2014-09-18)
     */
    public List<ProcesoDTO> consultarProceso(ProcesoDTO procesoDTO);

    /**
     * Registra el proceso enviado como parametro
     * 
     * @param procesoDTO
     *            Contiene la entidad a persistir
     * @return ProcesoDTO persistida
     * @exception DocumentosException
     *                <br>
     *                DOC_001052:"Existe un proceso con el mismo identificador 'id' ", (Codigo:1052)</br>
     *                DOC_001053:"Existe un proceso con el mismo nombre", (Codigo:1053)</br>
     *                DOC_001055:"No existe el proceso padre asociado con el ingresado", (Codigo:1055)</br>
     * 
     * @author dixon.alvarez<br>
     *         luis.forero (mod 2015-07-29)
     */
    public ProcesoDTO registrarProceso(ProcesoDTO procesoDTO) throws DocumentosException;

    /**
     * Actualiza el proceso enviado como parametro
     * 
     * @param procesoDTO
     *            Contiene los datos del proceso a actualizar
     * @author dixon.alvarez
     */
    public void actualizarProceso(ProcesoDTO procesoDTO);

    /**
     * Consulta un proceso por Id
     * 
     * @param idProceso
     *            Id del proceso a buscar
     * @return ProcesoDTO
     * @author dixon.alvarez
     */
    public ProcesoDTO consultarProcesoId(long idProceso);

    /**
     * Consulta un proceso por su codigo
     * 
     * @param codigoProceso
     * @return Proceso encontrado de lo contrario null
     * @author julio.pinzon 2016-09-06
     */
    public ProcesoDTO consultarProceso(String codigoProceso);
}
