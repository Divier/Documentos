package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.ParametroSistemaDTO;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;

@Local
public interface ILParametroSistema {

    /**
     * @see IRParametroSistema#actualizarParametroSistema(ParametroSistemaDTO)
     */
    public void actualizarParametroSistema(
            ParametroSistemaDTO parametroSistemaDTO);
    
    /**
     * @see IRParametroSistema#consultarParametroSistema(ParametroSistemaDTO)
     */
    public List<ParametroSistemaDTO> consultarParametroSistema(
            ParametroSistemaDTO parametroSistemaDTO);
    
    /**
     * @see IRParametroSistema#consultarParametroSistema(EnumParametrosSistema)
     */
    public String consultarValorParametroSistema(EnumParametrosSistema parametro);
}
