package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Local
public interface ILDocumento {

    /**
     * @see IRDocumento#consultarDocumento(DocumentoDTO)
     */
    public List<DocumentoDTO> consultarDocumento(DocumentoDTO documentoDTO);

    /**
     * @see IRDocumento#registrarDocumento(DocumentoDTO)
     */
    public DocumentoDTO registrarDocumento(DocumentoDTO documentoDTO);

    /**
     * @see IRDocumento#consultarUltimaVersionDocumento(DocumentoDTO)
     */
    public DocumentoDTO consultarUltimaVersionDocumento(DocumentoDTO documentoDTO);

    /**
     * @see IRDocumento#consultarDocumentoId(BigInteger)
     */
    public DocumentoDTO consultarDocumentoId(long idDocumento);

    /**
     * @see IRDocumento#generarDocumento(GeneraDocumentoDTO)
     */
    public DocumentoDTO generarDocumento(GeneraDocumentoDTO generaDocumentoDTO) throws DocumentosException;

    /**
     * @see IRDocumento#generarDocumentoSinGuardar(GeneraDocumentoDTO)
     */
    public DocumentoDTO generarDocumentoSinGuardar(GeneraDocumentoDTO generaDocumentoDTO) throws DocumentosException;

    /**
     * @see IRDocumento#crearDocumento(GeneraDocumentoDTO)
     */
    public DocumentoDTO crearDocumento(GeneraDocumentoDTO generaDocumentoDTO);

    /**
     * @see IRDocumento#obtenerDocumento(long)
     */
    public DocumentoDTO obtenerDocumento(long consecutivoDocumento);

    /**
     * @see IRDocumento#obtenerDocumento(long, int)
     */
    public DocumentoDTO obtenerDocumento(long consecutivoDocumento, int version);

    /**
     * @see IRDocumento#consultarDocumentosPDF(List<Long>)
     */
    public DocumentoDTO consultarDocumentosPDF(List<Long> codigosDocumentos) throws DocumentosException;

    /**
     * @see IRDocumento#consultarURLDocumentos(List)
     */
    public String consultarURLDocumentos(List<Long> ids) throws DocumentosException;
}
