package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.TipoDatoDTO;
import co.com.datatools.documentos.administracion.TipoFechaReferenciaDTO;
import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;
import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.administracion.TipoVariableDTO;

@Local
public interface ILCatalogo {

    /**
     * @see IRCatalogo#consultarContextoAplicacionVariable()
     */
    public List<ContextoAplicacionVariableDTO> consultarContextoAplicacionVariable();

    /**
     * @see IRCatalogo#consultarContextAplicacionVariableId(int)
     */
    public ContextoAplicacionVariableDTO consultarContextAplicacionVariableId(int idContextoAplicacionVariable);

    /**
     * @see IRCatalogo#consultarTipoVariable()
     */
    public List<TipoVariableDTO> consultarTipoVariable();

    /**
     * @see IRCatalogo#consultarTipoFirmaPlantilla()
     */
    public List<TipoFirmaPlantillaDTO> consultarTipoFirmaPlantilla();

    /**
     * @see IRCatalogo#consultarTipoFechaReferencia()
     */
    public List<TipoFechaReferenciaDTO> consultarTipoFechaReferencia();

    /**
     * @see IRCatalogo#consultarTipoDato()
     */
    public List<TipoDatoDTO> consultarTipoDato();

    /**
     * @see IRCatalogo#consultarTipoIdentificacion()
     */
    public List<TipoIdentificacionDTO> consultarTipoIdentificacion();

}
