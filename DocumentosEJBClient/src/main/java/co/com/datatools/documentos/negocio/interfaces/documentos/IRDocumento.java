package co.com.datatools.documentos.negocio.interfaces.documentos;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.documentos.DocumentoDTO;
import co.com.datatools.documentos.documentos.GeneraDocumentoDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Remote
public interface IRDocumento {

    /**
     * Consulta los documentos que se hayan generado teniendo en cuenta los filtros ingresados
     * 
     * @param documentoDTO
     *            Contiene los datos a filtrar
     * @return Lista de DocumentoDTOs
     * @author julio.pinzon
     */
    public List<DocumentoDTO> consultarDocumento(DocumentoDTO documentoDTO);

    /**
     * Guarda un nuevo documento en BD
     * 
     * @param documentoDTO
     *            Contiene los datos de la entidad a persistir
     * @return DocumentoDTO guardado
     * @author julio.pinzon
     */
    public DocumentoDTO registrarDocumento(DocumentoDTO documentoDTO);

    /**
     * Se encarga de consultar la �ltima version del documento que cumple con los par�metros recibidos
     * 
     * @param documentoDTO
     *            Contiene los datos que se tienen en cuenta para filtrar la consulta
     * @return Ultima version de un documento, teniendo en cuenta el consecutivo que tiene
     * @author dixon.alvarez
     */
    public DocumentoDTO consultarUltimaVersionDocumento(DocumentoDTO documentoDTO);

    /**
     * Se encarga de consultar un Documento por Id
     * 
     * @param idDocumento
     *            Id del documento a buscar
     * @return DocumentoDTO
     * @author dixon.alvarez
     */
    public DocumentoDTO consultarDocumentoId(long idDocumento);

    /**
     * Se encarga de generar un documento
     * 
     * @param generaDocumentoDTO
     * @return Documento generado
     * @author julio.pinzon
     */
    public DocumentoDTO generarDocumento(GeneraDocumentoDTO generaDocumentoDTO) throws DocumentosException;

    /**
     * Se encarga de generar version preliminar del documento
     * 
     * @param generaDocumentoDTOList
     * @return true si genera exitosamente el documento
     * @author julio.pinzon
     */
    public DocumentoDTO generarDocumentoSinGuardar(GeneraDocumentoDTO generaDocumentoDTO) throws DocumentosException;

    /**
     * Guarda el documento utilizando en content manager
     * 
     * @param GeneraDocumentoDTO
     * @return Documento guardado
     * @throws DocumentosException
     * @author julio.pinzon
     */
    public DocumentoDTO crearDocumento(GeneraDocumentoDTO generaDocumentoDTO);

    /**
     * Retorna el documento asociado al consecutivo indicado.
     * 
     * @param consecutivoDocumento
     * @return Documento encontrado
     * @author julio.pinzon
     */
    public DocumentoDTO obtenerDocumento(long consecutivoDocumento);

    /**
     * Retorna el documento asociado al consecutivo y la version indicada.
     * 
     * @param consecutivoDocumento
     * @param version
     * @return Documento encontrado
     * @author julio.pinzon
     */
    public DocumentoDTO obtenerDocumento(long consecutivoDocumento, int version);

    /**
     * Combina los documentos pdf en uno solo
     * 
     * @param Lista
     *            de codigosDocumentos
     * @return Documento combinado
     * @author julio.pinzon
     */
    public DocumentoDTO consultarDocumentosPDF(List<Long> codigosDocumentos) throws DocumentosException;

    /**
     * Retorna la ubicaci�n donde se encuentra ubicado un archivo en formato PDF que contiene todos los archivos asociados a los ids indicados. El
     * usuario indicado debe contener el nombre del usuario que realiza el uso del servicio.
     * 
     * @param ids
     *            identificadores de los documentos que se desean consultar
     * @return Cadena con la ubicaci�n del archivo resultado
     * @throws DocumentosException
     *             si se presentan problemas cargando los parametros de configuracion o creando el archivo PDF resultado
     * @author robert.bautista
     */
    public String consultarURLDocumentos(List<Long> ids) throws DocumentosException;
}
