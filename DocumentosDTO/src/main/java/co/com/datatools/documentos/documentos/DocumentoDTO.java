package co.com.datatools.documentos.documentos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;

/**
 * 
 * @author divier.casas
 **/
public class DocumentoDTO implements Serializable, Cloneable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private long idDocumento;
    private String descripcionDocumento;
    private Date fechaGeneracion;
    private boolean firmado;
    private String nombreDocumento;
    private Long consecutivoDocumento;
    private String pathDocumento;
    private int versionDocumento;
    private String codigoDocumento;
    private int versionDocumentoCm;
    private Date fechaCreacion;
    private byte[] contenido;
    private PlantillaDTO plantillaDTO;
    private UsuarioOrganizacionDTO usuarioOrganizacionDTO;
    private DocumentoDTO documentoOrigenDTO;
    private XmlDocumentoDTO xmlDocumentoDTO;
    private List<FirmaDigitalDocumentoDTO> listFirmaDigitalDocumentosDTO;
    private Date fechaCreacionDesde;
    private Date fechaCreacionHasta;
    
    // Constructors Declaration

    public DocumentoDTO() {

    }

    // Start Methods Declaration

    public long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public boolean isFirmado() {
        return firmado;
    }

    public void setFirmado(boolean firmado) {
        this.firmado = firmado;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getPathDocumento() {
        return pathDocumento;
    }

    public void setPathDocumento(String pathDocumento) {
        this.pathDocumento = pathDocumento;
    }

    public int getVersionDocumento() {
        return versionDocumento;
    }

    public void setVersionDocumento(int versionDocumento) {
        this.versionDocumento = versionDocumento;
    }

    public PlantillaDTO getPlantillaDTO() {
        return plantillaDTO;
    }

    public void setPlantillaDTO(PlantillaDTO plantillaDTO) {
        this.plantillaDTO = plantillaDTO;
    }

    public DocumentoDTO getDocumentoOrigenDTO() {
        return documentoOrigenDTO;
    }

    public void setDocumentoOrigenDTO(DocumentoDTO documentoOrigenDTO) {
        this.documentoOrigenDTO = documentoOrigenDTO;
    }

    public XmlDocumentoDTO getXmlDocumentoDTO() {
        return xmlDocumentoDTO;
    }

    public void setXmlDocumentoDTO(XmlDocumentoDTO xmlDocumentoDTO) {
        this.xmlDocumentoDTO = xmlDocumentoDTO;
    }

    public List<FirmaDigitalDocumentoDTO> getListFirmaDigitalDocumentosDTO() {
        return listFirmaDigitalDocumentosDTO;
    }

    public void setListFirmaDigitalDocumentosDTO(List<FirmaDigitalDocumentoDTO> listFirmaDigitalDocumentosDTO) {
        this.listFirmaDigitalDocumentosDTO = listFirmaDigitalDocumentosDTO;
    }

    public UsuarioOrganizacionDTO getUsuarioOrganizacionDTO() {
        return usuarioOrganizacionDTO;
    }

    public void setUsuarioOrganizacionDTO(UsuarioOrganizacionDTO usuarioOrganizacionDTO) {
        this.usuarioOrganizacionDTO = usuarioOrganizacionDTO;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public int getVersionDocumentoCm() {
        return versionDocumentoCm;
    }

    public void setVersionDocumentoCm(int versionDocumentoCm) {
        this.versionDocumentoCm = versionDocumentoCm;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public Long getConsecutivoDocumento() {
        return consecutivoDocumento;
    }

    public void setConsecutivoDocumento(Long consecutivoDocumento) {
        this.consecutivoDocumento = consecutivoDocumento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCreacionDesde() {
		return fechaCreacionDesde;
	}

	public void setFechaCreacionDesde(Date fechaCreacionDesde) {
		this.fechaCreacionDesde = fechaCreacionDesde;
	}

	public Date getFechaCreacionHasta() {
		return fechaCreacionHasta;
	}

	public void setFechaCreacionHasta(Date fechaCreacionHasta) {
		this.fechaCreacionHasta = fechaCreacionHasta;
	}

	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Finish the class
}