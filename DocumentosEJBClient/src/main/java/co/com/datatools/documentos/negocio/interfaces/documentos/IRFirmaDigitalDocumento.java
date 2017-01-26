package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.FirmaDigitalDocumentoDTO;

@Remote
public interface IRFirmaDigitalDocumento {

    /**
     * Se encarga de consultar los registros de las 
     * firmas digitales asociadas a un documento
     * @param documentoDTO
     *      Contiene los datos del documento que 
     *      tiene firma digital
     * @return
     *      List<FirmaDigitalDocumentoDTO>
     * @author dixon.alvarez
     */
    public List<FirmaDigitalDocumentoDTO> consultarFirmaDigitalDocumento(
            DocumentoDTO documentoDTO);
    
    /**
     * Se encarga de guardar un nuevo registro 
     * de firma digital de un documento
     * @param firmaDigitalDocumentoDTO
     *      Contiene los datos a persistir
     * @return
     *      FirmaDigitalDocumentoDTO guardada
     * @author dixon.alvarez
     */
    public FirmaDigitalDocumentoDTO registrarFirmaDigitalDocumento(
            FirmaDigitalDocumentoDTO firmaDigitalDocumentoDTO);
    
}
