package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Remote;

import co.com.datatools.documentos.administracion.ConsultaCargoFuncionarioDTO;
import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Remote
public interface IRFuncionario {

    /**
     * Registra el funcionario enviado como parametro
     * 
     * @param funcionarioDTO
     *            Contiene la entidad a persistir
     * @return FuncionarioDTO persistida
     * @exception DocumentosException
     */
    public FuncionarioDTO registrarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosException;

    /**
     * Consulta los funcionarios teniendo en cuenta los parametros enviados como filtro, incluyendo filtro por cargo
     * 
     * @param consultaFuncionarioDTO
     *            Contiene los filtros de la consulta
     * 
     * @cargoVigente Si se incluye el filtro por cargo, este parametro indica si el funcionario debe tener dicho cargo actualmente asignado(fecha
     *               fin==null), si es false consulta todas las asignaciones del cargo sin importar las fechas
     * @return Lista de FuncionarioDTO consultados, sino encuentra resultados retorna la lista vacia
     * 
     * @author dixon.alvarez <br>
     *         claudia.rodriguez(mod 2014-09-18)
     */
    public List<FuncionarioDTO> consultarFuncionario(ConsultaFuncionarioDTO consultaFuncionarioDTO);

    /**
     * Actualiza el funcionario enviado como parametro
     * 
     * @param funcionarioDTO
     *            Contiene los datos a actualizar
     * @exception DocumentosException
     */
    public void actualizarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosException;

    /**
     * Consulta un Funcionario por Id
     * 
     * @param idFuncionario
     *            Id del funcionario a buscar
     * @return FuncionarioDTO
     */
    public FuncionarioDTO consultarFuncionarioId(long idFuncionario);

    /**
     * Registra el funcionario-cargo enviado como parametro
     * 
     * @param funcionarioCargoDTO
     *            Contiene los datos de la entidad a persistir
     * @return FuncionarioCargoDTO persistido
     * @exception DocumentosException
     */
    public FuncionarioCargoDTO registrarFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO)
            throws DocumentosException;

    /**
     * Consulta los funcionarioCargo teniendo en cuenta los parametros enviados
     * 
     * @param funcionarioCargoDTO
     *            Contiene los datos a filtrar en la consulta
     * @return Lista funcionarioCargoDTO
     * @author dixon.alvarez <br>
     *         claudia.rodriguez(mod 2014-09-22)
     */
    public List<FuncionarioCargoDTO> consultarFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO);

    /**
     * Se encarga de consultar el funcionario cargo con la firma que aplica a un cargo en una fecha determinada. Si existe mas de un funcionario que
     * aplica al cargo se debe tomar el mas antiguo
     * 
     * @param consultaCargoFuncionarioDTO
     *            Filtros para la consulta
     * @return Funcionario consultado
     */
    public FuncionarioCargoDTO consultarFirmaFuncionarioCargo(ConsultaCargoFuncionarioDTO consultaCargoFuncionarioDTO);

}
