package co.com.datatools.documentos.entidades.aud;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;

import org.hibernate.envers.RevisionListener;

import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.excepcion.DocumentosRuntimeException;
import co.com.datatools.documentos.negocio.interfaces.aud.IRUsuarioSesion;

/**
 * @author rodrigo.cruz
 */
public class RevisionDocumentosListener implements RevisionListener {

    private static final String USUARIO_SESION_JNDI_NAME = "java:global/DocumentosEAR-"+ ConstantesGeneral.VERSION_DOCUMENTOS + "/DocumentosEJB/UsuarioSesionEJB!co.com.datatools.documentos.negocio.interfaces.aud.IRUsuarioSesion";

    @Override
    public void newRevision(Object revisionEntity) {
        try {

            final Hashtable<String, String> props = new Hashtable<String, String>();
            // setup the ejb: namespace URL factory
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            // create the InitialContext
            final Context context = new javax.naming.InitialContext(props);
            // lookup the bean
            IRUsuarioSesion usuarioSesionEJB = (IRUsuarioSesion) context.lookup(USUARIO_SESION_JNDI_NAME);

            RevisionDocumentos entity = (RevisionDocumentos) revisionEntity;
            entity.setUsuario(usuarioSesionEJB.getUsuario());
            entity.setIp(usuarioSesionEJB.getIP());

            usuarioSesionEJB.removerUsuario();
        } catch (NamingException ex) {
            throw new DocumentosRuntimeException(ex);
        }
    }

}
