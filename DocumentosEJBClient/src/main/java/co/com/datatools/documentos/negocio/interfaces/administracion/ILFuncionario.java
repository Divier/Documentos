package co.com.datatools.documentos.negocio.interfaces.administracion;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.ConsultaCargoFuncionarioDTO;
import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;

@Local
public interface ILFuncionario {

    /**
     * @see IRFuncionario#registrarFuncionario(FuncionarioDTO)
     */
    public FuncionarioDTO registrarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosException;

    /**
     * @see IRFuncionario#consultarFuncionario(ConsultaFuncionarioDTO)
     */
    public List<FuncionarioDTO> consultarFuncionario(ConsultaFuncionarioDTO consultaFuncionarioDTO);

    /**
     * @see IRFuncionario#actualizarFuncionario(FuncionarioDTO)
     */
    public void actualizarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosException;

    /**
     * @see IRFuncionario#consultarFuncionarioId(long)
     */
    public FuncionarioDTO consultarFuncionarioId(long idFuncionario);

    /**
     * @see IRFuncionario#registrarFuncionarioCargo(FuncionarioCargoDTO)
     */
    public FuncionarioCargoDTO registrarFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO)
            throws DocumentosException;

    /**
     * @see IRFuncionario#consultarFuncionarioCargo(FuncionarioCargoDTO)
     */
    public List<FuncionarioCargoDTO> consultarFuncionarioCargo(FuncionarioCargoDTO funcionarioCargoDTO);

    /**
     * @see IRFuncionario#consultarFirmaFuncionarioCargo(consultaCargoFuncionarioDTO)
     */
    public FuncionarioCargoDTO consultarFirmaFuncionarioCargo(ConsultaCargoFuncionarioDTO consultaCargoFuncionarioDTO);

}
