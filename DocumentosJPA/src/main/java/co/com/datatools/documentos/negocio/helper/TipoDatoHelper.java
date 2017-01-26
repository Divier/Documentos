package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.administracion.TipoDatoDTO;
import co.com.datatools.documentos.entidades.TipoDato;




/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class TipoDatoHelper {

	public static TipoDatoDTO toTipoDatoDTO(TipoDato tipoDato){
		TipoDatoDTO tipoDatoDTO = new TipoDatoDTO();
		tipoDatoDTO.setIdTipoDato(tipoDato.getIdTipoDato());
		tipoDatoDTO.setNombreTipoDato(tipoDato.getNombreTipoDato());
		return tipoDatoDTO;
	}

	public static TipoDato toTipoDato(TipoDatoDTO tipoDatoDTO, TipoDato tipoDato){
		if(null==tipoDato){
			tipoDato = new TipoDato();
		}
		tipoDato.setIdTipoDato(tipoDatoDTO.getIdTipoDato());
		tipoDato.setNombreTipoDato(tipoDatoDTO.getNombreTipoDato());
		return tipoDato;
	}

}