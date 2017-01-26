package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.ParametroSistemaDTO;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;

/**
 * Interfaz que permite exponer los metodos de accion sobre 
 * los parametros definidos para la organizacion
 * @author dixon.alvarez
 *
 */
@Remote
public interface IRParametroSistema {

    /**
     * Se encarga de actualizar un parametro de organizaci�n 
     * existente en la base de datos
     * @param parametroSistemaDTO
     *      Contiene los datos del parametro a actualizar
     * @author dixon.alvarez
     */
    public void actualizarParametroSistema(
            ParametroSistemaDTO parametroSistemaDTO);
    
    /**
     * Se encarga de consultar un parametro Organizacion 
     * de acuerdo a los par�metros
     * @param parametroSistemaDTO
     *      Contiene los datos a filtrar en la consulta
     * @return
     *      Lista de ParametroSistemaDTO
     * @author dixon.alvarez
     */
    public List<ParametroSistemaDTO> consultarParametroSistema(
            ParametroSistemaDTO parametroSistemaDTO);
    
    /**
     * Se encarga de consultar un ParametroSistema 
     * teniendo en cuenta el nombre enviado como parametro
     * @param enumParametrosSistema
     *      Nombre de un parametro de organizacion 
     * @return
     *      Null, si no encuentra ningun parametro con
     *      el nombre enviado como parametro
     * @author julio.pinzon
     */
    public String consultarValorParametroSistema(
            EnumParametrosSistema enumParametrosSistema);
}
