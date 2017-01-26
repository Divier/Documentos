package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.FirmaDigitalDocumentoDTO;

@Local
public interface ILFirmaDigitalDocumento {

    /**
     * @see IRFirmaDigitalDocumento#consultarFirmaDigitalDocumento(DocumentoDTO)
     */
    public List<FirmaDigitalDocumentoDTO> consultarFirmaDigitalDocumento(
            DocumentoDTO documentoDTO);
    
    /**
     * @see IRFirmaDigitalDocumento#registrarFirmaDigitalDocumento(FirmaDigitalDocumentoDTO)
     */
    public FirmaDigitalDocumentoDTO registrarFirmaDigitalDocumento(
            FirmaDigitalDocumentoDTO firmaDigitalDocumentoDTO);
}
