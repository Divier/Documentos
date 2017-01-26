package co.com.datatools.documentos.documentos;

import java.io.Serializable;

public class DocumentoRepositorioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DocumentoDTO documentoDTO;
    private String rutaArchivoDocumento;
    private String tipoArchivoDocumento;

    public DocumentoDTO getDocumentoDTO() {
        return documentoDTO;
    }

    public void setDocumentoDTO(DocumentoDTO documentoDTO) {
        this.documentoDTO = documentoDTO;
    }

    public String getRutaArchivoDocumento() {
        return rutaArchivoDocumento;
    }

    public void setRutaArchivoDocumento(String rutaArchivoDocumento) {
        this.rutaArchivoDocumento = rutaArchivoDocumento;
    }

    public String getTipoArchivoDocumento() {
        return tipoArchivoDocumento;
    }

    public void setTipoArchivoDocumento(String tipoArchivoDocumento) {
        this.tipoArchivoDocumento = tipoArchivoDocumento;
    }
}
