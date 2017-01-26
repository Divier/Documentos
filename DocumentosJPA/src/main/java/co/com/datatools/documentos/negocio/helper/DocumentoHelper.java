package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.entidades.Documento;
import co.com.datatools.documentos.ws.DocumentoGeneradoVO;
import co.com.datatools.documentos.ws.DocumentoVO;

/**
 * 
 * @author datatools
 * @version 2.0
 **/
public class DocumentoHelper {

    public static DocumentoDTO toDocumentoDTO(Documento documento) {
        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setIdDocumento(documento.getIdDocumento());
        documentoDTO.setConsecutivoDocumento(documento.getConsecutivoDocumento());
        documentoDTO.setVersionDocumentoCm(documento.getVersionDocumentoCm());
        documentoDTO.setDescripcionDocumento(documento.getDescripcionDocumento());
        documentoDTO.setFechaGeneracion(documento.getFechaGeneracion());
        documentoDTO.setFechaCreacion(documento.getFechaCreacion());
        documentoDTO.setFirmado(documento.getFirmado());
        documentoDTO.setNombreDocumento(documento.getNombreDocumento());
        documentoDTO.setPathDocumento(documento.getPathDocumento());
        documentoDTO.setVersionDocumento(documento.getVersionDocumento());
        documentoDTO.setCodigoDocumento(documento.getCodigoDocumento());
        return documentoDTO;
    }

    public static Documento toDocumento(DocumentoDTO documentoDTO, Documento documento) {
        if (null == documento) {
            documento = new Documento();
        }
        documento.setIdDocumento(documentoDTO.getIdDocumento());
        documento.setConsecutivoDocumento(documentoDTO.getConsecutivoDocumento());
        documento.setVersionDocumentoCm(documentoDTO.getVersionDocumentoCm());
        documento.setDescripcionDocumento(documentoDTO.getDescripcionDocumento());
        documento.setFechaGeneracion(documentoDTO.getFechaGeneracion());
        documento.setFechaCreacion(documentoDTO.getFechaCreacion());
		documento.setFirmado(documentoDTO.isFirmado());
        documento.setNombreDocumento(documentoDTO.getNombreDocumento());
        documento.setPathDocumento(documentoDTO.getPathDocumento());
        documento.setVersionDocumento(documentoDTO.getVersionDocumento());
        documento.setCodigoDocumento(documentoDTO.getCodigoDocumento());
        return documento;
	}

    /**
     * Utilizado para el servicio de integracion para convertir objeto de entrada
     * 
     * @param documentoVO
     * @return Objeto utilizado en la generacion de documento
     */
    public static GeneraDocumentoDTO toGeneraDocumento(DocumentoVO documentoVO) {
        GeneraDocumentoDTO generaDocumentoDTO = new GeneraDocumentoDTO();
        generaDocumentoDTO.setCodigoDocumento(documentoVO.getCodigoDocumento());
        generaDocumentoDTO.setCodigoPlantilla(documentoVO.getCodigoPlantilla());
        generaDocumentoDTO.setData(documentoVO.getValoresPlantilla());
        generaDocumentoDTO.setFechaGeneracion(documentoVO.getFechaGeneracion());
        generaDocumentoDTO.setDescripcionDocumento(documentoVO.getDescripcion());
        generaDocumentoDTO.setFormato(documentoVO.getFormato());
        generaDocumentoDTO.setUsuario(documentoVO.getUsuario());
        generaDocumentoDTO.setCarpeta(documentoVO.getUbicacion());
        return generaDocumentoDTO;
    }

    /**
     * Utilizado para el servicio de integracion para convertir objeto de salida
     * 
     * @param documentoVO
     * @return Objeto utilizado en la generacion de documento
     */
    public static DocumentoGeneradoVO toDocumentoGenerado(DocumentoDTO documento) {
        DocumentoGeneradoVO documentoGeneradoVO = new DocumentoGeneradoVO();
        documentoGeneradoVO.setCodigoDocumento(documento.getConsecutivoDocumento());
        documentoGeneradoVO.setVersion(documento.getVersionDocumento());
        documentoGeneradoVO.setNombre(documento.getNombreDocumento());
        documentoGeneradoVO.setContenido(documento.getContenido());
        return documentoGeneradoVO;
    }

}