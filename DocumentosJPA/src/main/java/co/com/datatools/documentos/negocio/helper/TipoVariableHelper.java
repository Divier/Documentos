package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.TipoVariable;

import co.com.datatools.documentos.administracion.TipoVariableDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class TipoVariableHelper {

	public static TipoVariableDTO toTipoVariableDTO(TipoVariable tipoVariable){
		TipoVariableDTO tipoVariableDTO = new TipoVariableDTO();
		tipoVariableDTO.setIdTipoVariable(tipoVariable.getIdTipoVariable());
		tipoVariableDTO.setNombreTipoVariable(tipoVariable.getNombreTipoVariable());
		return tipoVariableDTO;
	}

	public static TipoVariable toTipoVariable(TipoVariableDTO tipoVariableDTO, TipoVariable tipoVariable){
		if(null==tipoVariable){
			tipoVariable = new TipoVariable();
		}
		tipoVariable.setIdTipoVariable(tipoVariableDTO.getIdTipoVariable());
		tipoVariable.setNombreTipoVariable(tipoVariableDTO.getNombreTipoVariable());
		return tipoVariable;
	}

}