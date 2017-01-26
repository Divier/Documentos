package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.JasperPlantilla;
import co.com.datatools.documentos.plantillas.JasperPlantillaDTO;




/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class JasperPlantillaHelper {

	public static JasperPlantillaDTO toJasperPlantillaDTO(JasperPlantilla jasperPlantilla){
		JasperPlantillaDTO jasperPlantillaDTO = new JasperPlantillaDTO();
		jasperPlantillaDTO.setIdJasperPlantilla(jasperPlantilla.getIdJasperPlantilla());
		jasperPlantillaDTO.setCodigoDocumento(jasperPlantilla.getCodigoDocumento());
		return jasperPlantillaDTO;
	}

	public static JasperPlantilla toJasperPlantilla(JasperPlantillaDTO jasperPlantillaDTO, JasperPlantilla jasperPlantilla){
		if(null==jasperPlantilla){
			jasperPlantilla = new JasperPlantilla();
		}
		jasperPlantilla.setIdJasperPlantilla(jasperPlantillaDTO.getIdJasperPlantilla());
		jasperPlantilla.setCodigoDocumento(jasperPlantillaDTO.getCodigoDocumento());
		return jasperPlantilla;
	}

}