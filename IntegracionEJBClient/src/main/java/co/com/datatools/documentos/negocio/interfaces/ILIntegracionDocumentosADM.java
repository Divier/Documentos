package co.com.datatools.documentos.negocio.interfaces;

import java.util.List;

import javax.ejb.Local;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

@Local
public interface ILIntegracionDocumentosADM {

    /**
     * @see IRIntegracionDocumentosADM#registrarCargo(CargoDTO)
     */
    public void registrarCargo(CargoDTO cargoDTO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#registrarProceso(ProcesoDTO)
     */
    public void registrarProceso(ProcesoDTO procesoDTO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#registrarUsuario(UsuarioOrganizacionDTO)
     */
    public void registrarUsuario(UsuarioOrganizacionDTO usuarioOrganizacionDTO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#registrarFuncionario(FuncionarioDTO)
     */
    public void registrarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#actualizarFirma(funcionarioDTO)
     */
    public void actualizarFirma(FuncionarioDTO funcionarioDTO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#actualizarUsuario(UsuarioOrganizacionDTO)
     */
    public void actualizarUsuario(UsuarioOrganizacionDTO usuarioOrganizacionDTO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#actualizarFuncionario(FuncionarioDTO)
     */
    public void actualizarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#adicionarProcesosCargo(int, List)
     */
    public void adicionarProcesosCargo(int idCargo, List<Long> procesos) throws DocumentosWebException;

    /**
     * @see IRIntegracionDocumentosADM#removerProcesosCargo(int, List)
     */
    public void removerProcesosCargo(int idCargo, List<Long> procesos) throws DocumentosWebException;
}
