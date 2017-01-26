package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.Cargo;

import co.com.datatools.documentos.administracion.CargoDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class CargoHelper {

	public static CargoDTO toCargoDTO(Cargo cargo){
		CargoDTO cargoDTO = new CargoDTO();
		cargoDTO.setIdCargo(cargo.getIdCargo());
		cargoDTO.setNombreCargo(cargo.getNombreCargo());
		return cargoDTO;
	}

	public static Cargo toCargo(CargoDTO cargoDTO, Cargo cargo){
		if(null==cargo){
			cargo = new Cargo();
		}
		cargo.setIdCargo(cargoDTO.getIdCargo());
		cargo.setNombreCargo(cargoDTO.getNombreCargo());
		return cargo;
	}

}