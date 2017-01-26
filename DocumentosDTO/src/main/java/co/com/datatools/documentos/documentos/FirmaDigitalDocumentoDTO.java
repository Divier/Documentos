package co.com.datatools.documentos.documentos;

import java.io.Serializable;
import java.util.Date;

import co.com.datatools.documentos.administracion.FuncionarioDTO;

/**
 * 
 * @author Generated
 * @version 2.0
 **/
public class FirmaDigitalDocumentoDTO implements Serializable {

    // Attributes Declaration

    private static final long serialVersionUID = 1L;
    private int idFirmaDigitalDocumento;
    private Date fechaFirma;
    private String pathDocumentoFirmado;
    private DocumentoDTO documentoDTO;
    private FuncionarioDTO funcionarioDTO;

    // Constructors Declaration

    public FirmaDigitalDocumentoDTO() {

    }

    // Start Methods Declaration

    public int getIdFirmaDigitalDocumento() {
        return idFirmaDigitalDocumento;
    }

    public void setIdFirmaDigitalDocumento(int idFirmaDigitalDocumento) {
        this.idFirmaDigitalDocumento = idFirmaDigitalDocumento;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public String getPathDocumentoFirmado() {
        return pathDocumentoFirmado;
    }

    public void setPathDocumentoFirmado(String pathDocumentoFirmado) {
        this.pathDocumentoFirmado = pathDocumentoFirmado;
    }

    public DocumentoDTO getDocumentoDTO() {
        return documentoDTO;
    }

    public void setDocumentoDTO(DocumentoDTO documentoDTO) {
        this.documentoDTO = documentoDTO;
    }

    public FuncionarioDTO getFuncionarioDTO() {
        return funcionarioDTO;
    }

    public void setFuncionarioDTO(FuncionarioDTO funcionarioDTO) {
        this.funcionarioDTO = funcionarioDTO;
    }

    // Finish the class
}