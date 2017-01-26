package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Local
public interface ILCargo {

    /**
     * @see IRCargo#registrarCargo(CargoDTO)
     */
    public CargoDTO registrarCargo(CargoDTO cargoDTO) throws DocumentosException;

    /**
     * @see IRCargo#consultarCargoId(int)
     */
    public CargoDTO consultarCargoId(int idCargo);

    /**
     * @see IRCargo#actualizarCargo(CargoDTO)
     */
    public void actualizarCargo(CargoDTO cargoDTO) throws DocumentosException;

    /**
     * @see IRCargo#consultarCargo(CargoDTO)
     */
    public List<CargoDTO> consultarCargo(CargoDTO cargoDTO);
}
