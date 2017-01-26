package co.com.datatools.documentos.test.ejb;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.administracion.ContextoAplicacionVariableDTO;
import co.com.datatools.documentos.administracion.ProcesoDTO;
import co.com.datatools.documentos.administracion.TipoVariableDTO;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRVariable;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.plantillas.VariableDTO;
import co.com.datatools.documentos.test.BaseDocumentosTest;

@RunWith(Arquillian.class)
public class VariableEJBTest extends BaseDocumentosTest {

    private final static Logger logger = Logger.getLogger(VariableEJBTest.class.getName());

    @EJB
    private IRVariable interfazVariable;

    @Test
    public void crearVariable() {
        logger.debug("VariableEJBTest::crearVariable");
        VariableDTO variableDTO = new VariableDTO();
        ContextoAplicacionVariableDTO contextoAplicacionVariableDTO = new ContextoAplicacionVariableDTO();
        contextoAplicacionVariableDTO.setIdContextoAplicacion(2);
        variableDTO.setContextoAplicacionVariableDTO(contextoAplicacionVariableDTO);
        variableDTO.setDescripcionVariable("Variable tipo texto prueba unitaria");
        variableDTO.setLongitudVariable(250);
        variableDTO.setNombreVariable("Variable texto prueba");
        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setIdProceso(1);
        variableDTO.setProcesoDTO(procesoDTO);
        variableDTO.setPalabraClave("prueba, texto");
        TipoVariableDTO tipoVariableDTO = new TipoVariableDTO();
        tipoVariableDTO.setIdTipoVariable(3);
        variableDTO.setTipoVariableDTO(tipoVariableDTO);
        variableDTO.setValorDefecto("Texto defecto");
        VariableDTO variablePersistida = interfazVariable.registrarVariable(variableDTO);
        Assert.assertTrue(variablePersistida.getIdVariable() > 0);
        logger.info("******* Prueba unitaria crearVariable, persisti� variable con ID= "
                + variablePersistida.getIdVariable());

    }

    @Test
    public void consultarVariableOrganizacion() {
        logger.debug("VariableEJBTest::consultarVariableOrganizacion");
        List<VariableDTO> resultados = interfazVariable.consultarVariableOrganizacion();
        Assert.assertFalse(resultados.isEmpty());
        logger.info("******* Prueba unitaria consultarVariableOrganizacion, consultó: " + resultados.size()
                + " variables para la organización");
    }

    @Test
    public void consultarVariablePlantilla() {
        logger.debug("VariableEJBTest::consultarVariablePlantilla");
        PlantillaDTO plantillaDTO = new PlantillaDTO();
        plantillaDTO.setIdPlantilla(1);
        List<VariableDTO> resultados = interfazVariable.consultarVariablePlantilla(plantillaDTO);
        Assert.assertFalse(resultados.isEmpty());
        logger.info("******* Prueba unitaria consultarVariablePlantilla, consultó: " + resultados.size()
                + " variables para la plantilla de id=" + plantillaDTO.getIdPlantilla());
    }

    @Test
    public void consultarVariableProceso() {
        logger.debug("VariableEJBTest::consultarVariableProceso");
        ProcesoDTO procesoDTO = new ProcesoDTO();
        procesoDTO.setIdProceso(1);
        List<VariableDTO> resultados = interfazVariable.consultarVariableProceso(procesoDTO);
        Assert.assertFalse(resultados.isEmpty());
        logger.info("******* Prueba unitaria consultarVariableProceso, consultó: " + resultados.size()
                + " variables para el proceso de id=" + procesoDTO.getIdProceso());
    }

    

}
