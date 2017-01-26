package co.com.datatools.documentos.test.ejb;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.administracion.TipoFirmaPlantillaDTO;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRFirmaPlantilla;
import co.com.datatools.documentos.plantillas.FirmaPlantillaDTO;
import co.com.datatools.documentos.plantillas.PlantillaDTO;
import co.com.datatools.documentos.test.BaseDocumentosTest;

@RunWith(Arquillian.class)
public class FirmaPlantillaEJBTest extends BaseDocumentosTest {

    private final static Logger logger = Logger.getLogger(FirmaPlantillaEJBTest.class.getName());

    @EJB
    private IRFirmaPlantilla interfazFirmaPlantilla;

    @Test
    public void consultarFirmaPlantilla() {
        logger.debug("FirmaPlantillaEJBTest::consultarFirmaPlantilla");
        FirmaPlantillaDTO firmaPlantillaDTO = new FirmaPlantillaDTO();
        PlantillaDTO plantillaDTO = new PlantillaDTO();
        plantillaDTO.setIdPlantilla(2);
        firmaPlantillaDTO.setPlantillaDTO(plantillaDTO);
        List<FirmaPlantillaDTO> resultados = interfazFirmaPlantilla.consultarFirmaPlantilla(firmaPlantillaDTO);
        Assert.assertTrue(!resultados.isEmpty());
        logger.info("******* Prueba unitaria consultarFirmaPlantilla, consultó: " + resultados.size()
                + " firmas para la plantilla de id=" + plantillaDTO.getIdPlantilla());

    }

    @Test
    public void registrarFirmaPlantilla() {
        logger.debug("FirmaPlantillaEJBTest::registrarFirmaPlantilla");
        FirmaPlantillaDTO firmaPlantillaDTO = new FirmaPlantillaDTO();
        TipoFirmaPlantillaDTO tipoFirmaPlantillaDTO = new TipoFirmaPlantillaDTO();
        tipoFirmaPlantillaDTO.setIdTipoFirmaPlantilla(3);
        firmaPlantillaDTO.setTipoFirmaPlantillaDTO(tipoFirmaPlantillaDTO);
        PlantillaDTO plantillaDTO = new PlantillaDTO();
        plantillaDTO.setIdPlantilla(2);
        firmaPlantillaDTO.setPlantillaDTO(plantillaDTO);
        firmaPlantillaDTO.setMostrarNombreFuncionario(true);
        FirmaPlantillaDTO firmaPersistida = interfazFirmaPlantilla.registrarFirmaPlantilla(firmaPlantillaDTO);
        logger.info("******* Prueba unitaria registrarFirmaPlantilla,insertó la firma  plantilla de id=: "
                + firmaPersistida.getIdFirmaPlantilla());
    }

}
