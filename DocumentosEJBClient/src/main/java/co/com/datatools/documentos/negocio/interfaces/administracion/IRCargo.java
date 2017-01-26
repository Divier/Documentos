package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Remote
public interface IRCargo {

    /**
     * Registra el cargo enviado como parametro
     * 
     * @param cargoDTO
     *            Contiene la entidad a persistir
     * @return CargoDTO persistido
     * @exception DocumentosException
     * <br>
     *                DOC_001050:"Existe un cargo con el mismo nombre"(Cod:1050)<br>
     *                DOC_001051:"Existe un cargo con el mismo identificador 'id' "(Cod:1051)<br>
     *                DOC_001054:"No existe el proceso asociado al cargo", (cod:1054)<br>
     *                
     * @author dixon.alvarez<br>
     *         luis.forero(mod 29-07-2015)
     */
    public CargoDTO registrarCargo(CargoDTO cargoDTO) throws DocumentosException;

    /**
     * Consultar cargo por Id
     * 
     * @param idCargo
     *            Id del cargo a buscar
     * @return CargoDTO
     * @author dixon.alvarez
     */
    public CargoDTO consultarCargoId(int idCargo);

    /**
     * Actualiza un cargo enviado como parametro
     * 
     * @param cargoDTO
     *            Contiene los datos a actualizar
     * @exception DocumentosException
     * @author dixon.alvarez<br>
     *         luis.forero (2015-08-04)
     */
    public void actualizarCargo(CargoDTO cargoDTO) throws DocumentosException;

    /**
     * Se encarga de consultar un cargo de acuerdo a los parametros recibidos
     * 
     * @param cargoDTO
     *            DTO con los filtros de consulta
     * @return DTOs consultados
     */
    public List<CargoDTO> consultarCargo(CargoDTO cargoDTO);
}
