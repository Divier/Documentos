package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.TipoFechaReferencia;

import co.com.datatools.documentos.administracion.TipoFechaReferenciaDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class TipoFechaReferenciaHelper {

	public static TipoFechaReferenciaDTO toTipoFechaReferenciaDTO(TipoFechaReferencia tipoFechaReferencia){
		TipoFechaReferenciaDTO tipoFechaReferenciaDTO = new TipoFechaReferenciaDTO();
		tipoFechaReferenciaDTO.setIdTipoFecha(tipoFechaReferencia.getIdTipoFecha());
		tipoFechaReferenciaDTO.setNombreTipoFecha(tipoFechaReferencia.getNombreTipoFecha());
		return tipoFechaReferenciaDTO;
	}

	public static TipoFechaReferencia toTipoFechaReferencia(TipoFechaReferenciaDTO tipoFechaReferenciaDTO, TipoFechaReferencia tipoFechaReferencia){
		if(null==tipoFechaReferencia){
			tipoFechaReferencia = new TipoFechaReferencia();
		}
		tipoFechaReferencia.setIdTipoFecha(tipoFechaReferenciaDTO.getIdTipoFecha());
		tipoFechaReferencia.setNombreTipoFecha(tipoFechaReferenciaDTO.getNombreTipoFecha());
		return tipoFechaReferencia;
	}

}