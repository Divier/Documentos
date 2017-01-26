package co.com.datatools.documentos.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import co.com.datatools.util.jdbc.ScriptRunner;

@ArquillianSuiteDeployment
public abstract class BaseDocumentosTest {

    private final static Logger logger = Logger.getLogger(BaseDocumentosTest.class.getName());

    public static final String NOMBRE_PERSISTENCE_CTX = "DocumentosJPA";

    @PersistenceContext(unitName = NOMBRE_PERSISTENCE_CTX)
    private EntityManager em;

    private static boolean bdIniciada = false;

    /**
     * Listado de scripts q inicializan la bd en cada prueba, estos scripts se encuentran en <br>
     * ./DocumentosSQL/src/main/resources/scripts/
     */
    private static final String[] scriptsInit = { "/scripts/base/mysql/01_docs_mysql.sql",
            "/scripts/datos/app/docs/comun/01_datos_catalogos.sql",//
            "/scripts/datos/app/docs/comun/03_datos_catalogos_seguridad.sql"
    // , "/scripts/datos/app/docs/comun/02_datos_pruebas_iniciales.sql"
    };

    /**
     * Listado de scripts q pueblan la bd con datos mock para cada prueba, estos scripts deben estar en
     * ./DocumentosTest/src/test/resources/scripts/pruebas
     */
    private static final String[] scriptsPruebas = {
            //
            "scripts/pruebas/01_datos_pruebas_variables.sql", "scripts/pruebas/02_datos_pruebas_firmaplantilla.sql",
            "scripts/pruebas/03_datos_pruebas_administracion.sql", "scripts/pruebas/04_datos_pruebas_plantilla.sql",
            "scripts/pruebas/05_datos_pruebas_documento.sql" };

    @Deployment
    public static Archive<?> createDeployment() {

        final String versionTest = Maven.resolver().loadPomFromFile("pom.xml")
                .resolve("co.com.datatools.documentos:DocumentosSQL").withoutTransitivity().asSingleResolvedArtifact()
                .getResolvedVersion();

        final File mavenDocSql = Maven.resolver().offline()
                .resolve("co.com.datatools.documentos:DocumentosSQL:jar:" + versionTest).withoutTransitivity()
                .asSingleFile();

        final File mavenEar = Maven.resolver().offline()
                .resolve("co.com.datatools.documentos:DocumentosEAR:ear:" + versionTest).withoutTransitivity()
                .asSingleFile();

        final EnterpriseArchive archivo = ShrinkWrap.createFromZipFile(EnterpriseArchive.class, mavenEar);
        // Jar con Clases de prueba
        JavaArchive test = ShrinkWrap.create(JavaArchive.class, "DocumentosTest.jar")
                .addPackages(true, "co/com/datatools/documentos/test").addAsResource(EmptyAsset.INSTANCE, "beans.xml");
        for (String script : scriptsDisponibles()) {
            test.addAsResource(script);
        }
        logger.info(test.toString(true));
        archivo.addAsLibraries(test);
        archivo.addAsLibraries(mavenDocSql);
        logger.info(archivo.toString(true));
        return archivo;
    }

    private static List<String> scriptsDisponibles() {
        List<String> scripts = new ArrayList<>(20);
        scripts.addAll(new Reflections("scripts", new ResourcesScanner()).getResources(Pattern.compile(".*\\.sql")));
        Collections.sort(scripts);
        return scripts;
    }

    @Before
    public void crearBaseDatos() {
        if (bdIniciada) {
            return;
        }
        logger.debug("BaseDocumentosTest::crearBaseDatos");
        Session session = em.unwrap(Session.class);
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
        ConnectionProvider cp = sfi.getConnectionProvider();
        final StringWriter writerInfo = new StringWriter();
        final StringWriter writerError = new StringWriter();
        try {
            Connection conn = cp.getConnection();
            ScriptRunner runner = new ScriptRunner(conn, false, true);
            runner.setLogWriter(new PrintWriter(writerInfo));
            runner.setErrorLogWriter(new PrintWriter(writerError));
            URL resource = null;
            for (String script : scriptsInit) {
                logger.debug("BaseDocumentosTest::scriptsInit: " + script);
                resource = Thread.currentThread().getContextClassLoader().getResource(script);
                logger.debug("BaseDocumentosTest::resource: " + resource);
                runner.runScript(new InputStreamReader(resource.openStream()));
                logger.debug("BaseDocumentosTest::scriptsInit: fin");
            }
            for (String script : scriptsPruebas) {
                logger.debug("BaseDocumentosTest::scriptsPruebas: " + script);
                resource = Thread.currentThread().getContextClassLoader().getResource(script);
                logger.debug("BaseDocumentosTest::resource: " + resource);
                runner.runScript(new InputStreamReader(resource.openStream()));
                logger.debug("BaseDocumentosTest::scriptsPruebas: fin");
            }
            bdIniciada = true;
        } catch (SQLException | IOException e) {
            logger.error("\n" + writerError.toString());
            throw new RuntimeException(e);
        } finally {
            logger.debug("\n" + writerInfo.toString());
        }
    }

}
