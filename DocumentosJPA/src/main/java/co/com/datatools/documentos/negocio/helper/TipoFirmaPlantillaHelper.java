package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.TipoFirmaPlantilla;

import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class TipoFirmaPlantillaHelper {

	public static TipoFirmaPlantillaDTO toTipoFirmaPlantillaDTO(TipoFirmaPlantilla tipoFirmaPlantilla){
		TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO = new TipoFirmaPlantillaDTO();
		tipoFirmaPlantillaDTO.setIdTipoFirmaPlantilla(tipoFirmaPlantilla.getIdTipoFirmaPlantilla());
		tipoFirmaPlantillaDTO.setNombre(tipoFirmaPlantilla.getNombre());
		return tipoFirmaPlantillaDTO;
	}

	public static TipoFirmaPlantilla toTipoFirmaPlantilla(TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO, TipoFirmaPlantilla tipoFirmaPlantilla){
		if(null==tipoFirmaPlantilla){
			tipoFirmaPlantilla = new TipoFirmaPlantilla();
		}
		tipoFirmaPlantilla.setIdTipoFirmaPlantilla(tipoFirmaPlantillaDTO.getIdTipoFirmaPlantilla());
		tipoFirmaPlantilla.setNombre(tipoFirmaPlantillaDTO.getNombre());
		return tipoFirmaPlantilla;
	}

}