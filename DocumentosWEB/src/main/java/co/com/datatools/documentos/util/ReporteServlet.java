package co.com.datatools.documentos.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import co.com.datatools.documentos.managed_bean.documentos.VisualizarDocumentoMB;

/**
 * Servlet implementation class ReporteServlet
 */
public class ReporteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ReporteServlet.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("ReporteServlet::doGet");
        render(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext application = this.getServletContext();
        ManejadorSesion maneja = new ManejadorSesion();
        VisualizarDocumentoMB visualizaDocumento = (VisualizarDocumentoMB) maneja.extraerObjetoSesion(
                "visualizarDocumentoMB", request);
        String pathArchivo = visualizaDocumento.getPathRepositorio();
        File archivoCargado = new File(pathArchivo);
        if (archivoCargado.exists()) {
            try(
                    FileInputStream fis1 = new FileInputStream(archivoCargado);
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivoCargado))) {
                int len1 = (int) (archivoCargado.length());
                byte buffer[] = new byte[len1];
                fis1.read(buffer);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int inputStreamLength = 0;
                int length = 0;
                while ((length = in.read(buffer)) > 0) {
                    inputStreamLength += length;
                    baos.write(buffer, 0, length);
                }

                String disposition = "attachment";
                if (pathArchivo.endsWith(".pdf")) {
                    disposition = "inline";
                }
                String contentType = application.getMimeType(pathArchivo);
                response.reset();
                response.setHeader("Content-Type", contentType);
                response.setHeader("Content-Length", String.valueOf(inputStreamLength));
                if (!archivoCargado.getName().isEmpty()) {
                    response.setHeader("Content-Disposition", disposition + "; filename=\"" + archivoCargado.getName());
                } else {
                    response.setHeader("Content-Disposition", disposition + "; filename=\"reporte.pdf");
                }

                response.getOutputStream().write(baos.toByteArray(), 0, inputStreamLength);

                // finally
                response.getOutputStream().flush();

                // clear
                baos = null;
            } finally {
                close(response.getOutputStream());
            }
        }
    }

    private void close(Closeable resource) throws IOException {
        if (resource != null) {
            resource.close();
        }
    }
}
