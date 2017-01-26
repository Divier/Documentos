package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.documentos.XmlDocumentoDTO;
import co.com.datatools.documentos.entidades.XmlDocumento;



/** 
 *
 * @author Pedro.Moncada
 * @version 2.0
 **/ 
public class XmlDocumentoHelper {

	public static XmlDocumentoDTO toXmlDocumentoDTO(XmlDocumento xmlDocumento){
		XmlDocumentoDTO xmlDocumentoDTO = new XmlDocumentoDTO();
		xmlDocumentoDTO.setIdXmlDocumento(xmlDocumento.getIdXmlDocumento());
		xmlDocumentoDTO.setNombreXmlDoc(xmlDocumento.getNombreXmlDoc());
		xmlDocumentoDTO.setContenidoXml(xmlDocumento.getContenidoXml());
		xmlDocumentoDTO.setCodigoDocumentoHtml(xmlDocumento.getCodigoDocumentoHtml());
        xmlDocumentoDTO.setParametrosDocumento(xmlDocumento.getParametrosDocumento());
		return xmlDocumentoDTO;
	}

	public static XmlDocumento toXmlDocumento(XmlDocumentoDTO xmlDocumentoDTO, XmlDocumento xmlDocumento){
		if(null==xmlDocumento){
			xmlDocumento = new XmlDocumento();
		}
		xmlDocumento.setIdXmlDocumento(xmlDocumentoDTO.getIdXmlDocumento());
		xmlDocumento.setNombreXmlDoc(xmlDocumentoDTO.getNombreXmlDoc());
		xmlDocumento.setContenidoXml(xmlDocumentoDTO.getContenidoXml());
		xmlDocumento.setCodigoDocumentoHtml(xmlDocumentoDTO.getCodigoDocumentoHtml());
		xmlDocumento.setParametrosDocumento(xmlDocumentoDTO.getParametrosDocumento());
		return xmlDocumento;
	}

}