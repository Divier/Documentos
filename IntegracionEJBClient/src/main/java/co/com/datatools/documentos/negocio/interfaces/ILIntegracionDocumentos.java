package co.com.datatools.documentos.negocio.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.ws.DocumentoGeneradoVO;
import co.com.datatools.documentos.ws.DocumentoVO;
import co.com.datatools.documentos.ws.PlantillaConfiguracionVO;
import co.com.datatools.documentos.ws.PlantillaVO;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

@Local
public interface ILIntegracionDocumentos {

    /**
     * @see IRIntegracionDocumentos#importarTipoIdentificacion()
     */
    @Deprecated
    public List<TipoIdentificacionDTO> importarTipoIdentificacion();

    /**
     * @see IRIntegracionDocumentos#importarCargo(CargoDTO, int)
     */
    @Deprecated
    public List<CargoDTO> importarCargo(CargoDTO cargoDTO, int idOrganizacion);

    /**
     * @see IRIntegracionDocumentos#importarFuncionario(FuncionarioDTO, int)
     */
    @Deprecated
    public List<FuncionarioDTO> importarFuncionario(FuncionarioDTO funcionarioDTO, int idOrganizacion);

    /**
     * @see IRIntegracionDocumentos#importarProceso(ProcesoDTO, int)
     */
    @Deprecated
    public List<ProcesoDTO> importarProceso(ProcesoDTO procesoDTO, int idOrganizacion);

    /**
     * @see IRIntegracionDocumentos#importarProcesoCargos(ProcesoDTO)
     */
    @Deprecated
    public List<ProcesoDTO> importarProcesoCargos(ProcesoDTO procesoDTO);

    /**
     * @see IRIntegracionDocumentos#generarDocumento(DocumentoVO)
     */
    public DocumentoGeneradoVO generarDocumento(DocumentoVO documentoVO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#consultarDocumento(long)
     */
    public DocumentoGeneradoVO consultarDocumento(Long codigoDocumento) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#consultarDocumentosPDF(List<Long> )
     */
    public byte[] consultarDocumentosPDF(List<Long> codigosDocumentos) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#consultarDocumentosComprimidos(List)
     */
    public byte[] consultarDocumentosComprimidos(List<Long> codigosDocumentos) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#consultarVariablesPlantilla(String, Date)
     */
    public String consultarVariablesPlantilla(String codigoPlantilla, Date fechaGeneracion)
            throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#consultarURLDocumentos(List)
     */
    public String consultarURLDocumentos(List<Long> ids) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#actualizarDocumento(DocumentoVO)
     */
    public DocumentoGeneradoVO actualizarDocumento(DocumentoVO documentoVO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#consultarPlantillas(String)
     */
    public List<PlantillaVO> consultarPlantillas(String codigoProceso) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentos#consultarConfiguracionPlantilla(String, Date)
     */
    public PlantillaConfiguracionVO consultarConfiguracionPlantilla(String codigoPlantilla, Date fechaReferencia)
            throws DocumentosWebException;
}
