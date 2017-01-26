package co.com.datatools.documentos.negocio.helper;

import java.util.ArrayList;
import java.util.List;

import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.entidades.Funcionario;
import co.com.datatools.documentos.entidades.UsuarioOrganizacion;
import co.com.datatools.documentos.entidades.tmp.seguridad.Rol;

/**
 * 
 * @author Pedro.Moncada
 * @version 2.0
 **/
public class UsuarioOrganizacionHelper {

    public static UsuarioOrganizacionDTO toUsuarioOrganizacionDTO(UsuarioOrganizacion usuarioOrganizacion) {
        UsuarioOrganizacionDTO usuarioOrganizacionDTO = new UsuarioOrganizacionDTO();
        usuarioOrganizacionDTO.setIdUsuario(usuarioOrganizacion.getIdUsuario());
        usuarioOrganizacionDTO.setLoginUsuario(usuarioOrganizacion.getLoginUsuario());
        if (usuarioOrganizacion.getFuncionario() != null) {
            Funcionario funcionario = new Funcionario();
            funcionario.setIdFuncionario(usuarioOrganizacion.getFuncionario().getIdFuncionario());
            usuarioOrganizacion.setFuncionario(funcionario);
        }
        return usuarioOrganizacionDTO;
    }

    public static UsuarioOrganizacion toUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            UsuarioOrganizacion usuarioOrganizacion) {
        if (null == usuarioOrganizacion) {
            usuarioOrganizacion = new UsuarioOrganizacion();
        }
        usuarioOrganizacion.setIdUsuario(usuarioOrganizacionDTO.getIdUsuario());
        usuarioOrganizacion.setLoginUsuario(usuarioOrganizacionDTO.getLoginUsuario());
        usuarioOrganizacion.setContrasena(usuarioOrganizacionDTO.getContrasena());
        return usuarioOrganizacion;
    }

    public static RolDTO toRolDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombre(rol.getNombre());
        dto.setDescripcion(rol.getDescripcion());
        return dto;
    }

    public static Rol toRol(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setIdRol(rolDTO.getIdRol());
        rol.setNombre(rolDTO.getNombre());
        rol.setDescripcion(rolDTO.getDescripcion());
        return rol;
    }

    public static UsuarioOrganizacion toUsuarioOrganizacion(UsuarioOrganizacionDTO usuarioOrganizacionDTO,
            List<RolDTO> rolesDTO) {
        UsuarioOrganizacion usuarioOrganizacion = toUsuarioOrganizacion(usuarioOrganizacionDTO,
                new UsuarioOrganizacion());
        if (usuarioOrganizacionDTO.getFuncionarioDTO() != null) {
            usuarioOrganizacion.setFuncionario(FuncionarioHelper.toFuncionario(
                    usuarioOrganizacionDTO.getFuncionarioDTO(), new Funcionario()));
        }
        List<Rol> rolesUsuario = new ArrayList<>();
        if (rolesDTO != null) {
            for (RolDTO rolDto : rolesDTO) {
                Rol rol = toRol(rolDto);
                rolesUsuario.add(rol);
            }
            usuarioOrganizacion.setRols(rolesUsuario);
        }
        return usuarioOrganizacion;
    }

}