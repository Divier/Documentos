package co.com.datatools.documentos.test.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.excepcion.DocumentosException;
import co.com.datatools.documentos.negocio.error.ErrorDocumentos;
import co.com.datatools.documentos.negocio.interfaces.ILIntegracionDocumentos;
import co.com.datatools.documentos.negocio.interfaces.administracion.ILUsuarioOrganizacion;
import co.com.datatools.documentos.test.BaseDocumentosTest;
import co.com.datatools.documentos.ws.DocumentoGeneradoVO;
import co.com.datatools.documentos.ws.DocumentoVO;
import co.com.datatools.documentos.ws.UsuarioVO;
import co.com.datatools.documentos.ws.exception.DocumentosWebException;

@RunWith(Arquillian.class)
public class IntegracionDocumentosEJBTest extends BaseDocumentosTest {

    private static final Logger logger = Logger.getLogger(IntegracionDocumentosEJBTest.class.getName());

    @EJB
    private ILIntegracionDocumentos ilIntegracionDocumentos;

    @EJB
    private ILUsuarioOrganizacion ilUsuarioOrganizacion;

    /**
     * Realiza la autenticacion del usuario.
     * 
     * @param usuario
     *            contiene el usuario y clave para ser autenticados, la clave debe venir cifrada.
     * @throws DocumentosWebException
     *             1011: Error de autenticacion de usuario, si la autenticacion del usuario no es exitosa.
     */
    private void autenticarUsuario(UsuarioVO usuario) throws DocumentosWebException {
        try {
            this.ilUsuarioOrganizacion.autenticarUsuarioOrganizacionDTO(usuario.getUsuario(), usuario.getClave());
        } catch (DocumentosException e) {
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001011);
        }

    }

    @Test
    public void generarDocumento() {
        logger.debug("IntegracionDocumentosEJBTest::generarDocumento");

        try {
            UsuarioVO usuarioVO = new UsuarioVO();
            usuarioVO.setUsuario("dixon");
            usuarioVO
                    .setClave("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

            this.validarUsuario(usuarioVO);
            this.autenticarUsuario(usuarioVO);

            Map<String, Object> valoresPlantillaMap = new HashMap<String, Object>(); 
            valoresPlantillaMap.put("Nombre Peticionario", "Julio Pinzon");
            valoresPlantillaMap.put("Numero Expediente", 1459);
            valoresPlantillaMap.put("Numero Comparendo", 1000);
            valoresPlantillaMap.put("placa", "DDR014");
            valoresPlantillaMap.put("Fecha comparendo", new Date());
            List<Map<String, Object>> grupo1 = new ArrayList<Map<String, Object>>();
            Map<String, Object> valoresGrupo1_1 = new HashMap<String, Object>();
            valoresGrupo1_1.put("infraccion", "88888888999999888778");
            valoresGrupo1_1.put("Fecha Actual", new Date());
            grupo1.add(valoresGrupo1_1);
            Map<String, Object> valoresGrupo1_2 = new HashMap<String, Object>();
            valoresGrupo1_2.put("infraccion", "6666666665555555");
            valoresGrupo1_1.put("Fecha Actual", new Date());
            grupo1.add(valoresGrupo1_2);
            valoresPlantillaMap.put("grupo_1", grupo1);
            
            //Verifica que se esté generando con una fecha anterior o igual a la actual
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.set(Calendar.YEAR, 2015);
            calendarActual.set(Calendar.MONTH, 4);
            calendarActual.set(Calendar.DAY_OF_MONTH, 10);

            DocumentoVO documentoVO = new DocumentoVO();
            documentoVO.setCodigoPlantilla("ABCDEF");
            documentoVO.setDescripcion("Descripcion");
            documentoVO.setFechaGeneracion(calendarActual.getTime());
            documentoVO.setFormato(EnumFormatoDocumento.PDF);
            documentoVO.setUbicacion("/documento");
            documentoVO.setUsuario("diana");
            documentoVO.setValoresPlantilla(valoresPlantillaMap);
            DocumentoGeneradoVO documento =  ilIntegracionDocumentos.generarDocumento(documentoVO);
            logger.info("******* Prueba unitaria consultarURLDocumentos,generó el documento con codigo "
                    + documento.getCodigoDocumento());
            Assert.assertTrue(true);
        } catch (DocumentosWebException e) {
            logger.error("Error en test de generarDocumento ", e);
            Assert.assertTrue(false);
        }

    }

    @Test
    public void consultarDocumento() {
        logger.debug("IntegracionDocumentosEJBTest::consultarDocumento");

        try {
            UsuarioVO usuarioVO = new UsuarioVO();
            usuarioVO.setUsuario("dixon");
            usuarioVO
                    .setClave("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

            this.validarUsuario(usuarioVO);
            this.autenticarUsuario(usuarioVO);

            Long codigoDocumento = 1L;
            logger.info("******* Prueba unitaria consultarDocumento, obtuvo el documento "
                    + this.ilIntegracionDocumentos.consultarDocumento(codigoDocumento));
            Assert.assertTrue(true);
        } catch (DocumentosWebException e) {
            logger.error("Error en test de consultarDocumento ", e);
            Assert.assertTrue(false);
        }
    }

    @Test
    public void consultarDocumentosPDF() {
        logger.debug("IntegracionDocumentosEJBTest::consultarDocumentosPDF");

        try {
            UsuarioVO usuarioVO = new UsuarioVO();
            usuarioVO.setUsuario("dixon");
            usuarioVO
                    .setClave("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

            this.validarUsuario(usuarioVO);
            this.autenticarUsuario(usuarioVO);

            List<Long> codigosDocumentos = new ArrayList<Long>();
            codigosDocumentos.add(1L);
            logger.info("******* Prueba unitaria consultarDocumentosPDF, obtuvo el documento "
                    + this.ilIntegracionDocumentos.consultarDocumentosPDF(codigosDocumentos));
            Assert.assertTrue(true);
        } catch (DocumentosWebException e) {
            logger.error("Error en test de consultarDocumentosPDF ", e);
            Assert.assertTrue(false);
        }
    }

    @Test
    public void consultarDocumentosComprimidos() {
        logger.debug("IntegracionDocumentosEJBTest::consultarDocumentosComprimidos");

        try {
            UsuarioVO usuarioVO = new UsuarioVO();
            usuarioVO.setUsuario("dixon");
            usuarioVO
                    .setClave("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

            this.validarUsuario(usuarioVO);
            this.autenticarUsuario(usuarioVO);

            List<Long> codigosDocumentos = new ArrayList<Long>();
            codigosDocumentos.add(1L);

            logger.info("******* Prueba unitaria consultarDocumentosComprimidos,obtuvo el documento "
                    + this.ilIntegracionDocumentos.consultarDocumentosComprimidos(codigosDocumentos));
            Assert.assertTrue(true);
        } catch (DocumentosWebException e) {
            logger.error("Error en test de consultarDocumentosComprimidos ", e);
            Assert.assertTrue(false);
        }
    }

    @Test
    public void consultarVariablesPlantilla() {
        logger.debug("IntegracionDocumentosEJBTest::consultarVariablesPlantilla");

        try {
            UsuarioVO usuarioVO = new UsuarioVO();
            usuarioVO.setUsuario("dixon");
            usuarioVO
                    .setClave("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

            this.validarUsuario(usuarioVO);
            this.autenticarUsuario(usuarioVO);

            //Verifica que se esté generando con una fecha anterior o igual a la actual
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.set(Calendar.YEAR, 2015);
            calendarActual.set(Calendar.MONTH, 4);
            calendarActual.set(Calendar.DAY_OF_MONTH, 10);
            
            String codigoPlantilla = "ABCDEF";
            Date fechaGeneracion = calendarActual.getTime();
            logger.info("******* Prueba unitaria consultarVariablesPlantilla,obtuvo las variablesplantilla "
                    + this.ilIntegracionDocumentos.consultarVariablesPlantilla(codigoPlantilla, fechaGeneracion ));
            Assert.assertTrue(true);
        } catch (DocumentosWebException e) {
            logger.error("Error en test de consultarVariablesPlantilla ", e);
            Assert.assertTrue(false);
        }
    }

    @Test
    public void consultarURLDocumentos() {
        logger.debug("IntegracionDocumentosEJBTest::consultarURLDocumentos");

        try {
            UsuarioVO usuarioVO = new UsuarioVO();
            usuarioVO.setUsuario("dixon");
            usuarioVO
                    .setClave("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

            this.validarUsuario(usuarioVO);
            this.autenticarUsuario(usuarioVO);

            List<Long> codigosDocumentos = new ArrayList<Long>();
            codigosDocumentos.add(1L);
            logger.info("******* Prueba unitaria consultarURLDocumentos,generó el path "
                    + this.ilIntegracionDocumentos.consultarURLDocumentos(codigosDocumentos));
            Assert.assertTrue(true);
        } catch (DocumentosWebException e) {
            logger.error("Error en test de consultarURLDocumentos ", e);
            Assert.assertTrue(false);
        }
    }

    /**
     * Valida la informacion del usuario
     * 
     * @throws DocumentosWebException
     *             si es nula o vacia
     */
    private void validarUsuario(UsuarioVO usuario) throws DocumentosWebException {

        if (usuario == null) {
            logger.info("El usuario es nulo");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

        if ((usuario.getUsuario() == null) || (usuario.getClave() == null) || usuario.getUsuario().isEmpty()
                || usuario.getClave().isEmpty()) {
            logger.info("El usuario invalido, clave o usuario es nulo o vacio");
            throw new DocumentosWebException(ErrorDocumentos.GenerarDocumento.DOC_001000);
        }

    }

    @Test
    public void actualizarDocumento() {
        logger.debug("IntegracionDocumentosEJBTest::actualizarDocumento");
        DocumentoVO documentoVO = null;
        try {
            UsuarioVO usuarioVO = new UsuarioVO();
            usuarioVO.setUsuario("dixon");
            usuarioVO
                    .setClave("c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec");

            Map<String, Object> valoresPlantillaMap = new HashMap<String, Object>();
            valoresPlantillaMap.put("Nombre Peticionario", "Julio Pinzon");
            valoresPlantillaMap.put("Numero Expediente", 1459);
            valoresPlantillaMap.put("Numero Comparendo", 1000);
            valoresPlantillaMap.put("placa", "DDR014");
            valoresPlantillaMap.put("Fecha comparendo", new Date());
            List<Map<String, Object>> grupo1 = new ArrayList<Map<String, Object>>();
            Map<String, Object> valoresGrupo1_1 = new HashMap<String, Object>();
            valoresGrupo1_1.put("infraccion", "88888888999999888778");
            valoresGrupo1_1.put("Fecha Actual", new Date());
            grupo1.add(valoresGrupo1_1);
            Map<String, Object> valoresGrupo1_2 = new HashMap<String, Object>();
            valoresGrupo1_2.put("infraccion", "6666666665555555");
            valoresGrupo1_1.put("Fecha Actual", new Date());
            grupo1.add(valoresGrupo1_2);
            valoresPlantillaMap.put("grupo_1", grupo1);
            
            // Valida usuario
            this.validarUsuario(usuarioVO);
            this.autenticarUsuario(usuarioVO);

            documentoVO = new DocumentoVO();
            documentoVO.setCodigoDocumento(1L);
            documentoVO.setUsuario("diana");
            documentoVO.setValoresPlantilla(valoresPlantillaMap);

            DocumentoGeneradoVO documento = ilIntegracionDocumentos.actualizarDocumento(documentoVO);
            logger.info("******* Prueba unitaria actualizarDocumento,generó el documento de consecutivo=: "
                    + documento.getCodigoDocumento());
            Assert.assertTrue(true);
        } catch (DocumentosWebException e) {
            logger.error("Error en test de actualizar documento", e);
            Assert.assertTrue(false);
        }
    }

}
