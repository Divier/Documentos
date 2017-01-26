package co.com.datatools.documentos.negocio.helper;

import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.entidades.Funcionario;

/**
 * 
 * @author Pedro.Moncada
 * @version 2.0
 **/
public class FuncionarioHelper {

    public static FuncionarioDTO toFuncionarioDTO(Funcionario funcionario) {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        funcionarioDTO.setIdFuncionario(funcionario.getIdFuncionario());
        funcionarioDTO.setFechaFinalFuncionario(funcionario.getFechaFinalFuncionario());
        funcionarioDTO.setFechaInicialFuncionario(funcionario.getFechaInicialFuncionario());
        funcionarioDTO.setNombreFuncionario(funcionario.getNombreFuncionario());
        funcionarioDTO.setNumeroDocumIdent(funcionario.getNumeroDocumIdent());
        funcionarioDTO.setFirma(funcionario.getFirma());
        funcionarioDTO.setSiglaTipoIdentificacion(funcionario.getSiglaTipoIdentificacion());
        funcionarioDTO.setNombreTipoIdentificacion(funcionario.getNombreTipoIdentificacion());
        return funcionarioDTO;
    }

    public static Funcionario toFuncionario(FuncionarioDTO funcionarioDTO, Funcionario funcionario) {
        if (null == funcionario) {
            funcionario = new Funcionario();
        }
        funcionario.setIdFuncionario(funcionarioDTO.getIdFuncionario());
        funcionario.setFechaFinalFuncionario(funcionarioDTO.getFechaFinalFuncionario());
        funcionario.setFechaInicialFuncionario(funcionarioDTO.getFechaInicialFuncionario());
        funcionario.setNombreFuncionario(funcionarioDTO.getNombreFuncionario());
        funcionario.setNumeroDocumIdent(funcionarioDTO.getNumeroDocumIdent());
        funcionario.setFirma(funcionarioDTO.getFirma());
        funcionario.setSiglaTipoIdentificacion(funcionarioDTO.getSiglaTipoIdentificacion());
        funcionario.setNombreTipoIdentificacion(funcionarioDTO.getNombreTipoIdentificacion());
        return funcionario;
    }

}