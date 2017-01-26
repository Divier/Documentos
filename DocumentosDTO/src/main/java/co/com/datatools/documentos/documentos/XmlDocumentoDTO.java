package co.com.datatools.documentos.documentos;

import java.io.Serializable;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class XmlDocumentoDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private long idXmlDocumento;
    private byte[] contenidoXml;
    private String nombreXmlDoc;
    private String codigoDocumentoHtml;
    private String parametrosDocumento;
    private DocumentoDTO documentoDTO;

    // Constructors Declaration

    public XmlDocumentoDTO() {

    }

    // Start Methods Declaration

    public long getIdXmlDocumento() {
        return idXmlDocumento;
    }

    public void setIdXmlDocumento(long idXmlDocumento) {
        this.idXmlDocumento = idXmlDocumento;
    }

    public byte[] getContenidoXml() {
        return contenidoXml;
    }

    public void setContenidoXml(byte[] contenidoXml) {
        this.contenidoXml = contenidoXml;
    }

    public String getNombreXmlDoc() {
        return nombreXmlDoc;
    }

    public void setNombreXmlDoc(String nombreXmlDoc) {
        this.nombreXmlDoc = nombreXmlDoc;
    }

    public String getCodigoDocumentoHtml() {
        return codigoDocumentoHtml;
    }

    public void setCodigoDocumentoHtml(String codigoDocumentoHtml) {
        this.codigoDocumentoHtml = codigoDocumentoHtml;
    }

    public String getParametrosDocumento() {
        return parametrosDocumento;
    }

    public void setParametrosDocumento(String parametrosDocumento) {
        this.parametrosDocumento = parametrosDocumento;
    }

    public DocumentoDTO getDocumentoDTO() {
        return documentoDTO;
    }

    public void setDocumentoDTO(DocumentoDTO documentoDTO) {
        this.documentoDTO = documentoDTO;
    }

    // Finish the class
}