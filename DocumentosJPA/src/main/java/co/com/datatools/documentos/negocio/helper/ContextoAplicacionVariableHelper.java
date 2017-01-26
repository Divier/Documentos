package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.ContextoAplicacionVariable;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class ContextoAplicacionVariableHelper {

	public static ContextoAplicacionVariableDTO toContextoAplicacionVariableDTO(ContextoAplicacionVariable contextoAplicacionVariable){
		ContextoAplicacionVariableDTO contextoAplicacionVariableDTO = new ContextoAplicacionVariableDTO();
		contextoAplicacionVariableDTO.setIdContextoAplicacion(contextoAplicacionVariable.getIdContextoAplicacion());
		contextoAplicacionVariableDTO.setNombreContextoAplicacion(contextoAplicacionVariable.getNombreContextoAplicacion());
		return contextoAplicacionVariableDTO;
	}

	public static ContextoAplicacionVariable toContextoAplicacionVariable(ContextoAplicacionVariableDTO contextoAplicacionVariableDTO, ContextoAplicacionVariable contextoAplicacionVariable){
		if(null==contextoAplicacionVariable){
			contextoAplicacionVariable = new ContextoAplicacionVariable();
		}
		contextoAplicacionVariable.setIdContextoAplicacion(contextoAplicacionVariableDTO.getIdContextoAplicacion());
		contextoAplicacionVariable.setNombreContextoAplicacion(contextoAplicacionVariableDTO.getNombreContextoAplicacion());
		return contextoAplicacionVariable;
	}

}