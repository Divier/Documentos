package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.administracion.ParametroSistemaDTO;
import co.com.datatools.documentos.entidades.ParametroSistema;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class ParametrosSistemaHelper {

	public static ParametroSistemaDTO toParametroSistemaDTO(ParametroSistema parametroSistema){
		ParametroSistemaDTO parametroSistemaDTO = new ParametroSistemaDTO();
		parametroSistemaDTO.setIdParametroSistema(parametroSistema.getIdParametroSistema());
		parametroSistemaDTO.setNombreParametro(parametroSistema.getNombreParametro());
		parametroSistemaDTO.setValorParametro(parametroSistema.getValorParametro());
        parametroSistemaDTO.setEditable(parametroSistema.isEditable());
		return parametroSistemaDTO;
	}

	public static ParametroSistema toParametroSistema(ParametroSistemaDTO parametroSistemaDTO, ParametroSistema parametroSistema){
		if(null==parametroSistema){
			parametroSistema = new ParametroSistema();
		}
		parametroSistema.setIdParametroSistema(parametroSistemaDTO.getIdParametroSistema());
		parametroSistema.setNombreParametro(parametroSistemaDTO.getNombreParametro());
		parametroSistema.setValorParametro(parametroSistemaDTO.getValorParametro());
		parametroSistema.setEditable(parametroSistemaDTO.isEditable());
		return parametroSistema;
	}

}