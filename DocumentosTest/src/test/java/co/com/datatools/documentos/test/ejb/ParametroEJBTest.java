package co.com.datatools.documentos.test.ejb;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.administracion.ParametroSistemaDTO;
import co.com.datatools.documentos.administracion.TipoDatoDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRParametroSistema;
import co.com.datatools.documentos.test.BaseDocumentosTest;

@RunWith(Arquillian.class)
public class ParametroEJBTest extends BaseDocumentosTest {

    private final static Logger logger = Logger.getLogger(ParametroEJBTest.class.getName());

    @EJB
    IRParametroSistema parametrosSistemaEjb;

    @Test
    public void consultarParametroSistemas() {
        logger.debug("ParametroEJBTest::consultarParametroSistema");
        ParametroSistemaDTO parametroDto = new ParametroSistemaDTO();
        parametroDto.setNombreParametro("PATH_DOCUMENTOS");
        List<ParametroSistemaDTO> resultado = parametrosSistemaEjb.consultarParametroSistema(parametroDto);
        Assert.assertTrue(!resultado.isEmpty());
    }

    @Test
    public void consultarValorParametroSistema() {
        logger.debug("ParametroEJBTest::consultarValorParametroSistema");
        String resultado = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM;
        Assert.assertTrue(!resultado.equals(null));
    }

    @Test
    public void actualizarParametroSistema() {
        logger.debug("ParametroEJBTest::actualizarParametroSistema");
        ParametroSistemaDTO parametroDto = new ParametroSistemaDTO();
        parametroDto.setIdParametroSistema(4);
        parametroDto.setNombreParametro("PATH_IMAGENES");
        parametroDto.setValorParametro("/Path");
        TipoDatoDTO tipoDatoDTO = new TipoDatoDTO();
        tipoDatoDTO.setIdTipoDato(4);
        parametroDto.setTipoDatoDTO(tipoDatoDTO);
        
        parametrosSistemaEjb.actualizarParametroSistema(parametroDto);
    }

}
