package co.com.datatools.documentos.test.ejb;

import static org.junit.Assert.*;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.administracion.TipoIdentificacionDTO;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCatalogo;
import co.com.datatools.documentos.test.BaseDocumentosTest;
	
@RunWith(Arquillian.class)
public class CatalogoEJBTest extends BaseDocumentosTest {

	private static final Logger logger = Logger.getLogger(CatalogoEJBTest.class.getName());
	
	@EJB
    private IRCatalogo irCatalogo;
	
	@Test
	public void testConsultarTipoIdentificacion() {
		logger.debug("CatalogoEJBTest::consultarTipoIdentificacion");
		List<TipoIdentificacionDTO> tipodIdentificacion = irCatalogo.consultarTipoIdentificacion();
        Assert.assertTrue(!tipodIdentificacion.isEmpty());
        logger.info("******* Prueba unitaria consultarTipoIdentificacion, consultó: " + tipodIdentificacion.size());
	}

}
