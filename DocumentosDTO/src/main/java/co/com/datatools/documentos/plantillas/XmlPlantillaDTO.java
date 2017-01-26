package co.com.datatools.documentos.plantillas;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class XmlPlantillaDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idXmlPlantilla;
    private byte[] contenidoXmlPlantilla;
    private String nombreXmlPlantilla;
    private XmlPlantillaDTO xmlPlantillaDTO;
    private List<XmlPlantillaDTO> listXmlPlantillasDTO;
    private PlantillaDTO plantillaDTO;
    private String codigoDocumentoHtml;

    // Constructors Declaration

    public XmlPlantillaDTO() {

    }

    // Start Methods Declaration

    public int getIdXmlPlantilla() {
        return idXmlPlantilla;
    }

    public void setIdXmlPlantilla(int idXmlPlantilla) {
        this.idXmlPlantilla = idXmlPlantilla;
    }

    public byte[] getContenidoXmlPlantilla() {
        return contenidoXmlPlantilla;
    }

    public void setContenidoXmlPlantilla(byte[] contenidoXmlPlantilla) {
        this.contenidoXmlPlantilla = contenidoXmlPlantilla;
    }

    public String getNombreXmlPlantilla() {
        return nombreXmlPlantilla;
    }

    public void setNombreXmlPlantilla(String nombreXmlPlantilla) {
        this.nombreXmlPlantilla = nombreXmlPlantilla;
    }

    public XmlPlantillaDTO getXmlPlantillaDTO() {
        return xmlPlantillaDTO;
    }

    public void setXmlPlantillaDTO(XmlPlantillaDTO xmlPlantillaDTO) {
        this.xmlPlantillaDTO = xmlPlantillaDTO;
    }

    public List<XmlPlantillaDTO> getListXmlPlantillasDTO() {
        return listXmlPlantillasDTO;
    }

    public void setListXmlPlantillasDTO(List<XmlPlantillaDTO> listXmlPlantillasDTO) {
        this.listXmlPlantillasDTO = listXmlPlantillasDTO;
    }

    public PlantillaDTO getPlantillaDTO() {
        return plantillaDTO;
    }

    public void setPlantillaDTO(PlantillaDTO plantillaDTO) {
        this.plantillaDTO = plantillaDTO;
    }

    public String getCodigoDocumentoHtml() {
        return codigoDocumentoHtml;
    }

    public void setCodigoDocumentoHtml(String codigoDocumentoHtml) {
        this.codigoDocumentoHtml = codigoDocumentoHtml;
    }

    // Finish the class
}