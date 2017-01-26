/**
 * 
 */
package co.com.datatools.documentos.negocio.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.CargoDTO;
import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioCargoDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.enumeraciones.EnumRoles;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.interfaces.ILIntegracionDocumentosADM;
import co.com.datatools.documentos.negocio.interfaces.IRIntegracionDocumentosADM;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILCargo;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILProceso;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILUsuarioOrganizacion;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

/**
 * 
 * Clase que implementa los servicios expuestos por la interfaz IIntegracionDocumentosADM para la administracion de elementos no propios del
 * componente
 * 
 * @author
 * 
 */
@Stateless(name = "IntegracionDocumentosAdminEJB")
@LocalBean
public class IntegracionDocumentosAdminEJB implements ILIntegracionDocumentosADM, IRIntegracionDocumentosADM {

    private static final Logger logger = Logger.getLogger(IntegracionDocumentosAdminEJB.class.getName());

    @EJB
    private ILCargo cargoEJB;
    @EJB
    private ILProceso procesoEJB;
    @EJB
    private ILUsuarioOrganizacion usuarioOrganizacionEJB;
    @EJB
    private ILFuncionario funcionarioEJB;

    @Override
    public void registrarCargo(CargoDTO cargoDTO) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::registrarCargo");
        try {
            cargoEJB.registrarCargo(cargoDTO);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void registrarProceso(ProcesoDTO procesoDTO) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::registrarProceso");
        try {
            procesoEJB.registrarProceso(procesoDTO);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void adicionarProcesosCargo(int idCargo, List<Long> procesos) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::adicionarProcesosCargo");
        CargoDTO cargoDTO = cargoEJB.consultarCargoId(idCargo);
        if (cargoDTO == null) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001071);
        }
        if (cargoDTO.getListProcesosDTO() != null && !cargoDTO.getListProcesosDTO().isEmpty()) {
            for (ProcesoDTO procesoDTO : cargoDTO.getListProcesosDTO()) {
                for (Long idProcesos : procesos) {
                    if (idProcesos.equals(procesoDTO.getIdProceso())) {
                        throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001072);
                    }
                }
            }
        } else if (cargoDTO.getListProcesosDTO() == null) {
            cargoDTO.setListProcesosDTO(new ArrayList<ProcesoDTO>(0));
        }
        for (Long idProceso : procesos) {
            ProcesoDTO procesoDTO = new ProcesoDTO();
            procesoDTO.setIdProceso(idProceso);
            cargoDTO.getListProcesosDTO().add(procesoDTO);
        }

        try {
            cargoEJB.actualizarCargo(cargoDTO);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void removerProcesosCargo(int idCargo, List<Long> procesos) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::removerProcesosCargo");
        CargoDTO cargoDTO = cargoEJB.consultarCargoId(idCargo);
        if (cargoDTO == null) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001071);
        }
        if (cargoDTO.getListProcesosDTO() != null && !cargoDTO.getListProcesosDTO().isEmpty()) {
            for (Iterator<ProcesoDTO> iterator = cargoDTO.getListProcesosDTO().iterator(); iterator.hasNext();) {
                ProcesoDTO procesoDTO = iterator.next();
                for (Long idProcesos : procesos) {
                    if (idProcesos.equals(procesoDTO.getIdProceso())) {
                        iterator.remove();
                        procesos.remove(idProcesos);
                        break;
                    }
                }
            }
        }
        if (!procesos.isEmpty()) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001054);
        }

        try {
            cargoEJB.actualizarCargo(cargoDTO);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void registrarUsuario(UsuarioOrganizacionDTO usuarioOrganizacionDTO) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::registrarUsuario");
        List<RolDTO> roles = new ArrayList<RolDTO>();
        roles.add(new RolDTO(EnumRoles.EDITOR.getIdRol(), null, null));
        try {
            usuarioOrganizacionEJB.registrarUsuarioOrganizacionCifrado(usuarioOrganizacionDTO, roles);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void registrarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::registrarFuncionario");

        try {
            funcionarioEJB.registrarFuncionario(funcionarioDTO);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void actualizarFirma(FuncionarioDTO funcionarioDTO) throws DocumentosWebException {
        byte[] firma = funcionarioDTO.getFirma();
        funcionarioDTO = consultarFuncionario(funcionarioDTO.getNumeroDocumIdent(),
                funcionarioDTO.getNombreTipoIdentificacion());
        funcionarioDTO.setFirma(firma);
        try {
            funcionarioEJB.actualizarFuncionario(funcionarioDTO);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void actualizarUsuario(UsuarioOrganizacionDTO usuarioOrganizacionDTO) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::actualizarUsuario");
        List<RolDTO> roles = new ArrayList<RolDTO>();
        roles.add(new RolDTO(EnumRoles.EDITOR.getIdRol(), null, null));
        try {
            usuarioOrganizacionEJB.actualizarUsuarioOrganizacionCifrado(usuarioOrganizacionDTO, roles);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    @Override
    public void actualizarFuncionario(FuncionarioDTO funcionarioDTO) throws DocumentosWebException {
        logger.debug(IntegracionDocumentosAdminEJB.class.getName() + "::actualizarFuncionario");

        FuncionarioDTO funcionarioExistente = consultarFuncionario(funcionarioDTO.getNumeroDocumIdent(),
                funcionarioDTO.getNombreTipoIdentificacion());

        // Valida el cargo para identificar si es un nuevo cargo a asociar o para actualizar un funcionario cargo
        if (funcionarioDTO.getListFuncionarioCargosDTO() != null
                && !funcionarioDTO.getListFuncionarioCargosDTO().isEmpty()) {
            FuncionarioCargoDTO funcionarioCargoDTOEntrada = funcionarioDTO.getListFuncionarioCargosDTO().get(0);

            FuncionarioCargoDTO funcionarioCargoDTO = new FuncionarioCargoDTO();
            funcionarioCargoDTO.setFuncionarioDTO(new FuncionarioDTO());
            funcionarioCargoDTO.getFuncionarioDTO().setIdFuncionario(funcionarioExistente.getIdFuncionario());

            List<FuncionarioCargoDTO> lstCargosFuncionario = funcionarioEJB
                    .consultarFuncionarioCargo(funcionarioCargoDTO);

            if (!lstCargosFuncionario.isEmpty()) {
                funcionarioCargoDTO = lstCargosFuncionario.get(lstCargosFuncionario.size() - 1);

                if (funcionarioCargoDTO.getCargoDTO().getIdCargo() == funcionarioCargoDTOEntrada.getCargoDTO()
                        .getIdCargo()
                        && funcionarioCargoDTO.getFechaInicio()
                                .compareTo(funcionarioCargoDTOEntrada.getFechaInicio()) == 0) {
                    funcionarioCargoDTO.setFechaFin(funcionarioCargoDTOEntrada.getFechaFin());
                    funcionarioCargoDTOEntrada = funcionarioCargoDTO;
                    // } else if (funcionarioCargoDTO.getFechaFin() == null) {
                    // throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001069);
                    // } else if (UtilFecha.betweenDate(funcionarioCargoDTO.getFechaInicio(),
                    // funcionarioCargoDTO.getFechaFin(), funcionarioCargoDTOEntrada.getFechaInicio())
                    // || (funcionarioCargoDTOEntrada.getFechaFin() != null
                    // && UtilFecha.betweenDate(funcionarioCargoDTO.getFechaInicio(),
                    // funcionarioCargoDTO.getFechaFin(), funcionarioCargoDTOEntrada.getFechaFin()))) {
                    // throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001070);
                }
                funcionarioCargoDTOEntrada.setFuncionarioDTO(new FuncionarioDTO());
                funcionarioCargoDTOEntrada.getFuncionarioDTO()
                        .setIdFuncionario(funcionarioExistente.getIdFuncionario());

                lstCargosFuncionario.clear();
                lstCargosFuncionario.add(funcionarioCargoDTOEntrada);
            } else {
                lstCargosFuncionario = new ArrayList<>();
                funcionarioCargoDTO.setFechaInicio(funcionarioCargoDTOEntrada.getFechaInicio());
                funcionarioCargoDTO.setFechaFin(funcionarioCargoDTOEntrada.getFechaFin());
                lstCargosFuncionario.add(funcionarioCargoDTO);
            }
            funcionarioDTO.setListFuncionarioCargosDTO(lstCargosFuncionario);
        }

        funcionarioDTO.setIdFuncionario(funcionarioExistente.getIdFuncionario());
        funcionarioDTO.setSiglaTipoIdentificacion(funcionarioExistente.getSiglaTipoIdentificacion());
        funcionarioDTO.setNombreFuncionario(funcionarioExistente.getNombreFuncionario());
        funcionarioDTO.setFechaInicialFuncionario(funcionarioExistente.getFechaInicialFuncionario());
        try {
            funcionarioEJB.actualizarFuncionario(funcionarioDTO);
        } catch (DocumentosException e) {
            throw new DocumentosWebException(e.getErrorInfo());
        }
    }

    /**
     * Consulta el funcionario existente en el sistema con el numero de documento y nombre del documento del mismo.
     * 
     * @param numeroDocumIdent
     *            numero de documento de funcionario
     * @param nombreTipoIdentificacion
     *            nombre del tipo de identificacion
     * @return funcionario existente en el sistema
     * @throws DocumentosWebException
     *             <br>
     *             DOC_001068: El funcionario no se encuentra registrado en el sistema.(Codigo:1068)<br>
     * @author luis.forero (2015-08-03)
     */
    private FuncionarioDTO consultarFuncionario(String numeroDocumIdent, String nombreTipoIdentificacion)
            throws DocumentosWebException {
        ConsultaFuncionarioDTO consultaFuncionarioDTO = new ConsultaFuncionarioDTO();
        consultaFuncionarioDTO.setFuncionarioDTO(new FuncionarioDTO());
        consultaFuncionarioDTO.getFuncionarioDTO().setNumeroDocumIdent(numeroDocumIdent);
        consultaFuncionarioDTO.getFuncionarioDTO().setNombreTipoIdentificacion(nombreTipoIdentificacion);

        List<FuncionarioDTO> funcionarios = funcionarioEJB.consultarFuncionario(consultaFuncionarioDTO);
        if (funcionarios.isEmpty()) {
            throw new DocumentosWebException(ErrorDocumentos.Administracion.DOC_001068);
        }
        return funcionarios.get(0);
    }

}
