package co.com.datatools.documentos.negocio.ejb.administracion;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.entidades.Funcionario;
import co.com.datatools.documentos.entidades.UsuarioOrganizacion;
import co.com.datatools.documentos.entidades.tmp.seguridad.Rol;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.helper.FuncionarioHelper;
import co.com.datatools.documentos.negocio.helper.UsuarioOrganizacionHelper;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILUsuarioOrganizacion;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRUsuarioOrganizacion;
import co.com.datatools.util.GenericDao;
import co.com.datatools.util.cifrado.Digester;
import co.com.datatools.util.cifrado.Digester.EnumAlgoritmo;

@Stateless(name = "UsuarioOrganizacionEJB")
@LocalBean
public class UsuarioOrganizacionEJB implements ILUsuarioOrganizacion, IRUsuarioOrganizacion {

    private static final Logger logger = Logger.getLogger(UsuarioOrganizacionEJB.class.getName());

    @PersistenceContext(unitName = "DocumentosJPA")
    EntityManager em;

    @EJB
    private IRFuncionario iRFuncionario;

    public UsuarioOrganizacionEJB() {

    }

    // INICIO METODOS USUARIO

    @Override
    public UsuarioOrganizacionDTO consultarUsuarioOrganizacion(long idUsuario) {
        logger.debug("UsuarioOrganizacionEJB::consultarUsuarioId");
        UsuarioOrganizacionDTO usuarioOrganizacionDTO = null;

        UsuarioOrganizacion usuarioOrganizacion = em.find(UsuarioOrganizacion.class, idUsuario);
        if (usuarioOrganizacion != null) {
            usuarioOrganizacionDTO = UsuarioOrganizacionHelper.toUsuarioOrganizacionDTO(usuarioOrganizacion);

            if (usuarioOrganizacion.getFuncionario() != null) {
                usuarioOrganizacionDTO.setFuncionarioDTO(FuncionarioHelper.toFuncionarioDTO(usuarioOrganizacion
                        .getFuncionario()));
            }
        }

        return usuarioOrganizacionDTO;
    }

    @Override
    public UsuarioOrganizacionDTO consultarUsuarioOrganizacion(String loginUsuario, boolean modoSeguro)
            throws DocumentosException {
        logger.debug("UsuarioOrganizacionEJB::consultarUsuarioOrganizacionLogin");
        TypedQuery<UsuarioOrganizacion> consulta = em.createNamedQuery(UsuarioOrganizacion.SQ_USUARIO_POR_LOGIN,
                UsuarioOrganizacion.class);
        consulta.setParameter("loginUsuario", loginUsuario);
        List<UsuarioOrganizacion> lUsuarioOrganizacions = consulta.getResultList();
        if (!lUsuarioOrganizacions.isEmpty()) {
            UsuarioOrganizacion usuarioOrganizacion = lUsuarioOrganizacions.get(0);
            UsuarioOrganizacionDTO usuarioOrganizacionDTO = UsuarioOrganizacionHelper
                    .toUsuarioOrganizacionDTO(usuarioOrganizacion);

            if (usuarioOrganizacion.getFuncionario() != null) {
                FuncionarioDTO funcDto = FuncionarioHelper.toFuncionarioDTO(usuarioOrganizacion.getFuncionario());
                usuarioOrganizacionDTO.setFuncionarioDTO(funcDto);
            }
            return usuarioOrganizacionDTO;
        } else if (modoSeguro) {
            UsuarioOrganizacionDTO usuarioOrganizacionPersistir = new UsuarioOrganizacionDTO();
            usuarioOrganizacionPersistir.setLoginUsuario(loginUsuario);
            usuarioOrganizacionPersistir = registrarUsuarioOrganizacion(usuarioOrganizacionPersistir, null);
            return usuarioOrganizacionPersistir;
        }
        return null;
    }

    // FIN METODOS USUARIO

    @Override
    public List<UsuarioOrganizacionDTO> consultarUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO) {
        logger.debug("UsuarioOrganizacionEJB::consultarUsuarioOrganizacionFiltros");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u FROM UsuarioOrganizacion u");
        sql.append(" WHERE u.idUsuario IS NOT NULL");
        Map<String, Object> parametros = new HashMap<>();
        GenericDao<UsuarioOrganizacion> daoUsuarioOrganizacion = new GenericDao<>(UsuarioOrganizacion.class, em);
        if (usuarioOrganizacionDTO.getLoginUsuario() != null && !usuarioOrganizacionDTO.getLoginUsuario().equals("")) {
            sql.append(" AND UPPER(u.loginUsuario) LIKE :loginUsuario");
            parametros.put("loginUsuario", "%" + usuarioOrganizacionDTO.getLoginUsuario().toUpperCase() + "%");
        }
        List<UsuarioOrganizacion> resultados = daoUsuarioOrganizacion.buildAndExecuteQuery(sql, parametros);
        List<UsuarioOrganizacionDTO> retorno = new ArrayList<>();
        for (UsuarioOrganizacion usuario : resultados) {
            UsuarioOrganizacionDTO usuarioDTO = UsuarioOrganizacionHelper.toUsuarioOrganizacionDTO(usuario);

            if (usuario.getFuncionario() != null) {
                usuarioDTO.setFuncionarioDTO(iRFuncionario.consultarFuncionarioId(usuario.getFuncionario()
                        .getIdFuncionario()));
            }
            retorno.add(usuarioDTO);
        }
        return retorno;
    }

    @Override
    public List<RolDTO> consultarRol() {
        logger.debug("UsuarioOrganizacionEJB::consultarRol");
        String sql = "SELECT r FROM Rol r";
        TypedQuery<Rol> query = em.createQuery(sql, Rol.class);
        List<Rol> roles = query.getResultList();
        List<RolDTO> rolesDTO = new ArrayList<>();

        for (Rol rol : roles) {
            RolDTO rolDTO = UsuarioOrganizacionHelper.toRolDTO(rol);
            rolesDTO.add(rolDTO);
        }
        return rolesDTO;
    }

    @Override
    public UsuarioOrganizacionDTO registrarUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException {
        logger.debug("UsuarioOrganizacionEJB::registrarUsuarioOrganizacion");
        return registrarUsuario(usuarioOrganizacionDTO, rolesUsuario, false);
    }

    @Override
    public UsuarioOrganizacionDTO registrarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException {
        logger.debug("UsuarioOrganizacionEJB::registrarUsuarioOrganizacionCifrado");
        return registrarUsuario(usuarioOrganizacionDTO, rolesUsuario, true);
    }

    /**
     * LLeva a acabo la logica de registro de un usuario
     * 
     * @param usuarioOrganizacionDTO
     *            Datos del usuario a registrar
     * @param rolesUsuario
     *            roles con los cuales se registra
     * @param cifrado
     *            identifica si la contrasena ya se encuentra cifrada(true) o no (false)
     * @return retorna el usuario registrado
     * @throws DocumentosException
     * @author luis.forero (2015-07-30)
     */
    private UsuarioOrganizacionDTO registrarUsuario(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario, boolean cifrado) throws DocumentosException {
        // Validar si el mismo usuario ya existe en el sistema
        UsuarioOrganizacionDTO usuarioExistente = consultarUsuarioOrganizacion(
                usuarioOrganizacionDTO.getLoginUsuario(), false);
        if (usuarioExistente != null) {
            throw new DocumentosException(ErrorDocumentos.Seguridad.DOC_SEG_002);
        }

        // Asociar funcionario en caso de venir asociado
        if (usuarioOrganizacionDTO.getFuncionarioDTO() != null) {
            TypedQuery<Funcionario> query = em.createNamedQuery(Funcionario.SQ_BY_NUM_DOC_TIP_DOC, Funcionario.class);
            query.setParameter("pNumDoc", usuarioOrganizacionDTO.getFuncionarioDTO().getNumeroDocumIdent());
            query.setParameter("pNomTipIde", usuarioOrganizacionDTO.getFuncionarioDTO().getNombreTipoIdentificacion());
            List<Funcionario> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                usuarioOrganizacionDTO.setFuncionarioDTO(new FuncionarioDTO());
                usuarioOrganizacionDTO.getFuncionarioDTO().setIdFuncionario(resultList.get(0).getIdFuncionario());
            } else {
                throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001056);
            }
        }

        // Creacion de usuario
        UsuarioOrganizacion usuarioOrganizacion = UsuarioOrganizacionHelper.toUsuarioOrganizacion(
                usuarioOrganizacionDTO, rolesUsuario);
        if (!cifrado) {
            // Codificar el password del usuario
            try {

                if (usuarioOrganizacion.getContrasena() != null && !usuarioOrganizacion.getContrasena().equals(""))
                    usuarioOrganizacion.setContrasena(Digester.digestEncoderJboss(usuarioOrganizacion.getContrasena(),
                            EnumAlgoritmo.SHA512));
            } catch (NoSuchAlgorithmException | IOException e) {
                logger.error("Error al encriptar contrasena", e);
            }
        }
        em.persist(usuarioOrganizacion);
        em.flush();
        return UsuarioOrganizacionHelper.toUsuarioOrganizacionDTO(usuarioOrganizacion);
    }

    @Override
    public UsuarioOrganizacionDTO autenticarUsuarioOrganizacionDTO(String usuario, String contrasena)
            throws DocumentosException {
        logger.debug("UsuarioOrganizacionEJB::autenticarusuarioOrganizacionDTO");
        TypedQuery<UsuarioOrganizacion> query = em.createNamedQuery(UsuarioOrganizacion.SQ_USUARIO_POR_LOGIN,
                UsuarioOrganizacion.class);
        query.setParameter("loginUsuario", usuario);
        List<UsuarioOrganizacion> resultados = query.getResultList();
        UsuarioOrganizacionDTO usuarioDTO = null;
        if (resultados.isEmpty()) {
            throw new DocumentosException(ErrorDocumentos.Seguridad.DOC_SEG_001);
        } else {
            UsuarioOrganizacion usuarioOrganizacion = resultados.get(0);
            if (!usuarioOrganizacion.getContrasena().equals(contrasena)) {
                throw new DocumentosException(ErrorDocumentos.Seguridad.DOC_SEG_001);
            } else {
                usuarioDTO = UsuarioOrganizacionHelper.toUsuarioOrganizacionDTO(usuarioOrganizacion);
            }
        }
        return usuarioDTO;
    }

    @Override
    public UsuarioOrganizacionDTO actualizarUsuarioOrganizacionCifrado(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesUsuario) throws DocumentosException {
        logger.debug("UsuarioOrganizacionEJB::autenticarusuarioOrganizacionDTO");
        TypedQuery<UsuarioOrganizacion> query = em.createNamedQuery(UsuarioOrganizacion.SQ_USUARIO_POR_LOGIN,
                UsuarioOrganizacion.class);
        query.setParameter("loginUsuario", usuarioOrganizacionDTO.getLoginUsuario());
        List<UsuarioOrganizacion> resultado = query.getResultList();

        if (resultado.isEmpty()) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001057);
        }

        UsuarioOrganizacion usuarioExistente = resultado.get(0);
        if (usuarioExistente.getFuncionario() == null && usuarioOrganizacionDTO.getFuncionarioDTO() != null) {
            Funcionario funcionario = em.find(Funcionario.class, usuarioOrganizacionDTO.getFuncionarioDTO()
                    .getIdFuncionario());
            if (funcionario == null) {
                throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001056);
            }
            usuarioExistente.setFuncionario(funcionario);
        } else if (usuarioExistente.getFuncionario() != null
                && usuarioOrganizacionDTO.getFuncionarioDTO() != null
                && (!usuarioOrganizacionDTO.getFuncionarioDTO().getNumeroDocumIdent()
                        .equals(usuarioExistente.getFuncionario().getNumeroDocumIdent()) || !usuarioOrganizacionDTO
                        .getFuncionarioDTO().getNombreTipoIdentificacion()
                        .equals(usuarioExistente.getFuncionario().getNombreTipoIdentificacion()))) {
            throw new DocumentosException(ErrorDocumentos.Administracion.DOC_001058);
        }

        usuarioExistente.setContrasena(usuarioOrganizacionDTO.getContrasena());

        em.merge(usuarioExistente);
        em.flush();
        return UsuarioOrganizacionHelper.toUsuarioOrganizacionDTO(usuarioExistente);
    }
}
