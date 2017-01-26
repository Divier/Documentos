package co.com.datatools.documentos.test.ejb;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.administracion.ConsultaFuncionarioDTO;
import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.documentos.test.BaseDocumentosTest;

@RunWith(Arquillian.class)
public class AdministracionEJBTest extends BaseDocumentosTest {

    private static final Logger logger = Logger.getLogger(AdministracionEJBTest.class.getName());

    @EJB
    private IRFuncionario interfazFuncionario;

    @Test
    public void consultarFuncionario() {
        logger.debug("AdministracionEJBTest::consultarFuncionario");
        ConsultaFuncionarioDTO consultaFuncionarioDTO = new ConsultaFuncionarioDTO();
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
        logger.debug("FORMULARIO: " + funcionarioDTO.getNombreFuncionario());
        consultaFuncionarioDTO.setFuncionarioDTO(funcionarioDTO);
        List<FuncionarioDTO> resutados = interfazFuncionario.consultarFuncionario(consultaFuncionarioDTO);
        logger.info("******* Prueba unitaria consultarFuncionario, consultó: " + resutados.size() + " funcionarios");

    }

}
