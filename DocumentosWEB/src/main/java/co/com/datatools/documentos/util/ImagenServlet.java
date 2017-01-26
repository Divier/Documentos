package co.com.datatools.documentos.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import co.com.datatools.documentos.administracion.FuncionarioDTO;
import co.com.datatools.documentos.constantes.ConstantesDocumentos;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.enumeraciones.EnumParametrosSistema;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRParametroSistema;
import co.com.datatools.documentos.negocio.interfaces.documentos.IRVariable;
import co.com.datatools.documentos.plantillas.VariableDTO;

/**
 * Servlet implementation class ImagenServlet
 */
@WebServlet(description = "Servlet para mostrar imagenes locales", urlPatterns = { "/ImagenServlet" })
public class ImagenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger LOGGER = Logger.getLogger(ImagenServlet.class.getName());

    private final static String ID_VARIABLE = "idVariable";

    private final static String ID_FUNCIONARIO = "idFuncionario";

    private IRVariable irVariable;

    private IRFuncionario irFuncionario;

    private IRParametroSistema irParametroSistema;

    private static final String VARIABLE_JNDI_NAME = "java:app/DocumentosEJB/VariableEJB!co.com.datatools.documentos.negocio.interfaces.documentos.IRVariable";
    private static final String FUNCIONARIO_JNDI_NAME = "java:app/DocumentosEJB/AdministracionEJB!co.com.datatools.documentos.negocio.interfaces.administracion.IRFuncionario";
    private static final String PARAMETRO_JNDI_NAME = "java:app/DocumentosEJB/AdministracionEJB!co.com.datatools.documentos.negocio.interfaces.administracion.IRParametroSistema";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImagenServlet() {
        super();
        try {
            final Hashtable<String, String> props = new Hashtable<String, String>();
            // setup the ejb: namespace URL factory
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            // create the InitialContext
            final Context context = new javax.naming.InitialContext(props);
            // lookup the bean

            irVariable = (IRVariable) context
                    .lookup(VARIABLE_JNDI_NAME);

            irFuncionario = (IRFuncionario) context
                    .lookup(FUNCIONARIO_JNDI_NAME);

            irParametroSistema = (IRParametroSistema) context
                    .lookup(PARAMETRO_JNDI_NAME);
        } catch (NamingException e) {
            LOGGER.error("Error localizando Jndi EJB", e);
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("ImagenServlet::doGet");
        OutputStream out = null;
        BufferedImage bi = null;
        byte[] imagen = null;
        String nombreImagen = null;
        try {
            if (StringUtils.isNotBlank(request.getParameter(ID_VARIABLE))) {
                int idVariable = Integer.valueOf(request.getParameter(ID_VARIABLE));
                VariableDTO variable = irVariable.consultarVariableId(idVariable);
                if (variable != null) {
                    imagen = variable.getImagen();
                    nombreImagen = variable.getValorDefecto();
                } else {
                    LOGGER.error("No se encontro variable con id " + idVariable);
                }
            } else if (StringUtils.isNotBlank(request.getParameter(ID_FUNCIONARIO))) {
                int idFuncionario = Integer.valueOf(request.getParameter(ID_FUNCIONARIO));
                FuncionarioDTO funcionario = irFuncionario.consultarFuncionarioId(idFuncionario);
                if (funcionario != null) {
                    // Extension de la firma
                    String extension = irParametroSistema
                            .consultarValorParametroSistema(EnumParametrosSistema.EXTENSION_IMAGEN_FIRMA);
                    imagen = funcionario.getFirma();
                    nombreImagen = funcionario.getIdFuncionario() + extension;
                } else {
                    LOGGER.error("No se encontro funcionario con id " + idFuncionario);
                }
            } else if (StringUtils.isNotBlank(request.getParameter(ConstantesDocumentos.INDEX))) {
                int index = Integer.valueOf(request.getParameter(ConstantesDocumentos.INDEX));
                long consecutivo = Long.valueOf(request.getParameter(ConstantesDocumentos.CONSECUTIVO));

                // Obtiene parametros del sistema con las rutas donde se guardan los reportes temporalmente
                String pathImagenes = ConstantesGeneral.PATH_DOCUMENTOS_FILE_SYSTEM
                        + irParametroSistema.consultarValorParametroSistema(EnumParametrosSistema.PATH_IMAGENES);

                // Obtiene contenido de imagen temporal
                Path pathImage = Paths.get(pathImagenes + consecutivo + "/" + index + "."
                        + ConstantesPlantillas.EXTENSION_PNG);
                imagen = Files.readAllBytes(pathImage);
                nombreImagen = index + "." + ConstantesPlantillas.EXTENSION_PNG;
            }

            if (imagen != null && nombreImagen != null && !nombreImagen.isEmpty()) {
                ByteArrayInputStream bais = new ByteArrayInputStream(imagen);
                bi = ImageIO.read(bais);
                out = response.getOutputStream();

                // Extension del archivo
                String ext = FilenameUtils.getExtension(nombreImagen);
                if (ext == null) {
                    ext = ConstantesPlantillas.EXTENSION_JPG;
                }

                ImageIO.write(bi, ext, out);
                response.setContentType("image/" + ext);
            }
        } finally {
            close(out);
        }
    }

    private void close(Closeable resource) throws IOException {
        if (resource != null) {
            resource.close();
        }
    }

}
