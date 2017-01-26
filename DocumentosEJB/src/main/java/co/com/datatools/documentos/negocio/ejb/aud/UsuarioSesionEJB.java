package co.com.datatools.documentos.negocio.ejb.aud;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.documento.UtilSeguridad;
import co.com.datatools.documentos.negocio.interfaces.aud.ILUsuarioSesion;
import co.com.datatools.documentos.negocio.interfaces.aud.IRUsuarioSesion;

/**
 * @author rodrigo.cruz
 */
@Stateless(mappedName = "UsuarioSesionEJB")
@LocalBean
public class UsuarioSesionEJB implements ILUsuarioSesion, IRUsuarioSesion {

    @Resource
    private EJBContext ctx;

    public UsuarioSesionEJB() {
    }

    @Override
    public String getUsuario() {
        if (null == UtilSeguridad.getMapUsuarios(Thread.currentThread().getId())) {
            return ctx.getCallerPrincipal().getName();
        } else {
            return UtilSeguridad.getMapUsuarios(Thread.currentThread().getId());
        }
    }

    @Override
    public String getIP() {
        if (null == UtilSeguridad.getMapIp(Thread.currentThread().getId())) {
            return ConstantesGeneral.LOCALHOST; // TODO RC - GET SESSION IP (JAAS)
        } else {
            return UtilSeguridad.getMapIp(Thread.currentThread().getId());
        }
    }

    @Override
    public long almacenarUsuario(String loginUsuario, String ip) {
        long idThread = Thread.currentThread().getId();
        UtilSeguridad.setMapUsuarios(idThread, loginUsuario, ip);
        return idThread;
    }

    @Override
    public void removerUsuario() {
        UtilSeguridad.removerUsuario(Thread.currentThread().getId());
    }

}
