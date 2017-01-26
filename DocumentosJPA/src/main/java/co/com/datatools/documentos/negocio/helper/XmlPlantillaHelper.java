package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.entidades.XmlPlantilla;
import co.com.datatools.documentos.plantillas.XmlPlantillaDTO;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class XmlPlantillaHelper {

	public static XmlPlantillaDTO toXmlPlantillaDTO(XmlPlantilla xmlPlantilla){
		XmlPlantillaDTO xmlPlantillaDTO = new XmlPlantillaDTO();
		xmlPlantillaDTO.setIdXmlPlantilla(xmlPlantilla.getIdXmlPlantilla());
		xmlPlantillaDTO.setNombreXmlPlantilla(xmlPlantilla.getNombreXmlPlantilla());
		xmlPlantillaDTO.setContenidoXmlPlantilla(xmlPlantilla.getContenidoXmlPlantilla());
		xmlPlantillaDTO.setCodigoDocumentoHtml(xmlPlantilla.getCodigoDocumentoHtml());
		return xmlPlantillaDTO;
	}

	public static XmlPlantilla toXmlPlantilla(XmlPlantillaDTO xmlPlantillaDTO, XmlPlantilla xmlPlantilla){
		if(null==xmlPlantilla){
			xmlPlantilla = new XmlPlantilla();
		}
		xmlPlantilla.setIdXmlPlantilla(xmlPlantillaDTO.getIdXmlPlantilla());
		xmlPlantilla.setNombreXmlPlantilla(xmlPlantillaDTO.getNombreXmlPlantilla());
		xmlPlantilla.setContenidoXmlPlantilla(xmlPlantillaDTO.getContenidoXmlPlantilla());
        xmlPlantilla.setCodigoDocumentoHtml(xmlPlantillaDTO.getCodigoDocumentoHtml());
        return xmlPlantilla;
	}

}