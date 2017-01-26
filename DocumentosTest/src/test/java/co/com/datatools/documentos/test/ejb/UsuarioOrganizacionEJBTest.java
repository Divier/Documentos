package co.com.datatools.documentos.test.ejb;

import static org.junit.Assert.*;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.administracion.UsuarioOrganizacionDTO;
import co.com.datatools.documentos.administracion.tmp.RolDTO;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRUsuarioOrganizacion;
import co.com.datatools.documentos.test.BaseDocumentosTest;

@RunWith(Arquillian.class)
public class UsuarioOrganizacionEJBTest extends BaseDocumentosTest {

    private static final Logger logger = Logger.getLogger(UsuarioOrganizacionEJBTest.class.getName());

    @EJB
    private IRFuncionario iRFuncionario;
    
    @EJB
    private IRUsuarioOrganizacion iRUsuarioOrganizacion;
    
	@Test
	public void testConsultarUsuarioOrganizacion() {
		logger.debug("UsuarioOrganizacionEJBTest::consultarUsuarioOrganizacion");
		UsuarioOrganizacionDTO usuarioOrganizacionDTO = new UsuarioOrganizacionDTO();
		usuarioOrganizacionDTO.setLoginUsuario("dixon");
		List<UsuarioOrganizacionDTO> usuarios = iRUsuarioOrganizacion.consultarUsuarioOrganizacion(usuarioOrganizacionDTO);
        Assert.assertTrue(!usuarios.isEmpty());
        logger.info("******* Prueba unitaria consultarUsuarioOrganizacion, consultó: " + usuarios.size());
        
        usuarioOrganizacionDTO.setLoginUsuario(null);
		usuarios = iRUsuarioOrganizacion.consultarUsuarioOrganizacion(usuarioOrganizacionDTO);
        Assert.assertTrue(!usuarios.isEmpty());
        logger.info("******* Prueba unitaria consultarUsuarioOrganizacion, consultó: " + usuarios.size());
	}

	@Test
	public void testConsultarRol() {
		logger.debug("UsuarioOrganizacionEJBTest::consultarRol");
		List<RolDTO> roles = iRUsuarioOrganizacion.consultarRol();
		Assert.assertTrue(roles.isEmpty());
        logger.info("******* Prueba unitaria consultarRol, consultó: " + roles.size());
	}

	@Test
	public void testRegistrarUsuarioOrganizacion() throws DocumentosException {
		logger.debug("UsuarioOrganizacionEJBTest::registrarUsuarioOrganizacion");
		UsuarioOrganizacionDTO usuarioOrganizacionDTO = new UsuarioOrganizacionDTO();
		usuarioOrganizacionDTO.setLoginUsuario("nuevoUsuario1");
		usuarioOrganizacionDTO.setContrasena("123456");
		
		FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
		funcionarioDTO.setIdFuncionario(1L);
		funcionarioDTO.setNumeroDocumIdent("123456");
		funcionarioDTO.setNombreFuncionario("Funcionario");
		usuarioOrganizacionDTO.setFuncionarioDTO(funcionarioDTO);
		
		List<RolDTO> roles = iRUsuarioOrganizacion.consultarRol();
		UsuarioOrganizacionDTO resUsuarioOrganizacionDTO = iRUsuarioOrganizacion.registrarUsuarioOrganizacion(usuarioOrganizacionDTO, roles);
		Assert.assertNotNull(resUsuarioOrganizacionDTO);
		logger.info("******* Prueba unitaria registrarUsuarioOrganizacion, arrojo Id Usuario: " + resUsuarioOrganizacionDTO.getIdUsuario());
	}

	@Test
	public void testActualizarUsuarioOrganizacionCifrado() throws DocumentosException {
		logger.debug("UsuarioOrganizacionEJBTest::actualizarUsuarioOrganizacionCifrado");
		UsuarioOrganizacionDTO usuarioOrganizacionDTO = new UsuarioOrganizacionDTO();
		usuarioOrganizacionDTO.setLoginUsuario("nuevoUsuario1");
		usuarioOrganizacionDTO.setContrasena("123456");
		List<RolDTO> roles = iRUsuarioOrganizacion.consultarRol();
		UsuarioOrganizacionDTO resUsuarioOrganizacionDTO = iRUsuarioOrganizacion.actualizarUsuarioOrganizacionCifrado(usuarioOrganizacionDTO, roles);
		Assert.assertNotNull(resUsuarioOrganizacionDTO);
		logger.info("******* Prueba unitaria autenticarUsuarioOrganizacionDTO, arrojo Id Usuario: " + resUsuarioOrganizacionDTO.getIdUsuario());
	}
}
