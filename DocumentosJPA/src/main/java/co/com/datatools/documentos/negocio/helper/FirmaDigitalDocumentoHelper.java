package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.documentos.FirmaDigitalDocumentoDTO;
import co.com.datatools.documentos.entidades.FirmaDigitalDocumento;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class FirmaDigitalDocumentoHelper {

	public static FirmaDigitalDocumentoDTO toFirmaDigitalDocumentoDTO(FirmaDigitalDocumento firmaDigitalDocumento){
		FirmaDigitalDocumentoDTO firmaDigitalDocumentoDTO = new FirmaDigitalDocumentoDTO();
		firmaDigitalDocumentoDTO.setIdFirmaDigitalDocumento(firmaDigitalDocumento.getIdFirmaDigitalDocumento());
		firmaDigitalDocumentoDTO.setFechaFirma(firmaDigitalDocumento.getFechaFirma());
		firmaDigitalDocumentoDTO.setPathDocumentoFirmado(firmaDigitalDocumento.getPathDocumentoFirmado());
		return firmaDigitalDocumentoDTO;
	}

	public static FirmaDigitalDocumento toFirmaDigitalDocumento(FirmaDigitalDocumentoDTO firmaDigitalDocumentoDTO, FirmaDigitalDocumento firmaDigitalDocumento){
		if(null==firmaDigitalDocumento){
			firmaDigitalDocumento = new FirmaDigitalDocumento();
		}
		firmaDigitalDocumento.setIdFirmaDigitalDocumento(firmaDigitalDocumentoDTO.getIdFirmaDigitalDocumento());
		firmaDigitalDocumento.setFechaFirma(firmaDigitalDocumentoDTO.getFechaFirma());
		firmaDigitalDocumento.setPathDocumentoFirmado(firmaDigitalDocumentoDTO.getPathDocumentoFirmado());
		return firmaDigitalDocumento;
	}

}