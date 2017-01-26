package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.FuncionarioCargo;

import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class FuncionarioCargoHelper {

	public static FuncionarioCargoDTO toFuncionarioCargoDTO(FuncionarioCargo funcionarioCargo){
		FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
		funcionarioCargoDTO.setIdFuncionarioCargo(funcionarioCargo.getIdFuncionarioCargo());
		funcionarioCargoDTO.setFechaFin(funcionarioCargo.getFechaFin());
		funcionarioCargoDTO.setFechaInicio(funcionarioCargo.getFechaInicio());
		return funcionarioCargoDTO;
	}

	public static FuncionarioCargo toFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO, FuncionarioCargo funcionarioCargo){
		if(null==funcionarioCargo){
			funcionarioCargo = new FuncionarioCargo();
		}
		funcionarioCargo.setIdFuncionarioCargo(funcionarioCargoDTO.getIdFuncionarioCargo());
		funcionarioCargo.setFechaFin(funcionarioCargoDTO.getFechaFin());
		funcionarioCargo.setFechaInicio(funcionarioCargoDTO.getFechaInicio());
		return funcionarioCargo;
	}

}