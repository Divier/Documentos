package co.com.datatools.documentos.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jboss.logging.Logger;

/**
 * Clase que se conecta a un servidor ftp, sube y descarga archivos.
 * 
 * @author camilo.sierra
 * @version 1.0
 * @since 26-Agt-2013
 */

public class Ftp {

    private static final Logger logger = Logger.getLogger(Ftp.class.getName());

    private static final int BUFFER_SIZE = 4096;

    /**
     * 
     * Metodo para subir un archivo local a un servidor FTP
     * 
     * @param servidor
     *            servidor de FTP al cual se debe conectar
     * @param usuario
     *            usuario de conexion al servidor de FTP
     * @param clave
     *            clave del usuario para realizar la conexion al servidor de FTP
     * @param archivoLocal
     *            representa el archivo a cargar al servidor FTP
     * @param rutaDestino
     *            ubicacion en directorios a donde se va a cargar el archivo
     * @param nombreArchivoDestino
     *            nombre asignado al archivo que se va a cargar
     * 
     * @throws Exception
     *             Si se presenta algun error leyendo o cargando el archivo al FTP
     */

    public static String cargarArchivoFTP(String servidor, String usuario, String clave, File archivoLocal,
            String rutaDestino, String nombreArchivoDestino) throws Exception {
        URL url;
        URLConnection urlc;
        String rutaFinal, formatoFTP;
        File archivoDestino = new File(rutaDestino, nombreArchivoDestino);
        archivoDestino = new File(servidor, archivoDestino.getPath());

        rutaFinal = archivoDestino.getPath().replace("\\", "/");
        formatoFTP = "ftp://";
        try {
            url = new URL(formatoFTP + usuario + ":" + clave + "@" + rutaFinal + ";type=i");
            urlc = url.openConnection();
            try (OutputStream os = urlc.getOutputStream(); //
                    FileInputStream is = new FileInputStream(archivoLocal);) {

                byte[] buffer = new byte[Ftp.BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

            } catch (FileNotFoundException e) {
                logger.error("Ftp::cargarArchivoFTP problemas cargando el archivo local para ser leído.", e);
                throw new Exception("Problemas cargando el archivo al FTP.", e);
            } catch (IOException e) {
                logger.error("Ftp::cargarArchivoFTP problema leyendo / escribiendo el archivo.", e);
                throw new Exception("Problemas de lectura/escritura del archivo.", e);
            }

        } catch (MalformedURLException e) {
            logger.error("Ftp::cargarArchivoFTP problemas estableciendo la url de conexión al FTP.", e);
            throw new Exception("Problemas de conexión al servidor FTP.", e);
        } catch (IOException e) {
            logger.error("Ftp::cargarArchivoFTP problema cargando el archivo al servidor FTP.", e);
            throw new Exception("problema cargando el archivo al servidor FTP.", e);
        }

        return formatoFTP + rutaFinal;
    }

    /**
     * Método encargado de descargar los archivos encontrados en el servidor ftp indicado dejándolos en el directorio con la ruta local indicada.
     * 
     * @param rutaServidor
     *            ruta del servidor FTP a donde conectarse
     * 
     * @param usuario
     *            usuario para realizar la conexión
     * 
     * @param clave
     *            clave del usuario indicado para realizar la conexión
     * 
     * @param rutaLocal
     *            ruta local del directorio donde se descargarán los archivos
     * 
     * @return true si el proceso se realiza satisfactoriamente
     * 
     * @throws MalformedURLException
     *             si la url de la conexión ftp no es válida
     * 
     * @throws IOException
     *             si hay problemas leyendo/escribiendo la información del servidor
     */
    public static boolean downloadFileByFTP(String rutaServidor, String usuario, String clave, String rutaLocal)
            throws MalformedURLException, IOException {

        URL url = new URL("ftp://" + usuario + ":" + clave + "@" + rutaServidor + ";type=i");
        URLConnection urlc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        List<String> archivos = new ArrayList<String>();
        String inputLine;
        // TODO no creo que esta sea la mejor manera de obtener el listado de nombres de los archivos
        while ((inputLine = in.readLine()) != null) {
            archivos.add(inputLine.substring(56, (inputLine.length())));
        }

        in.close();
        Iterator<String> iter = archivos.iterator();
        while (iter.hasNext()) {
            String nombreArchivo = (String) iter.next();
            File dirFtp = new File(rutaServidor, nombreArchivo);
            URL url2 = new URL("ftp://" + usuario + ":" + clave + "@" + dirFtp.getPath().replace("\\", "/") + ";type=i");
            URLConnection urlc2 = url2.openConnection();
            InputStream is = urlc2.getInputStream();
            int aleatorio = (int) (Math.random() * 999999 + 1);
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(rutaLocal, aleatorio + ".txt")));
            int c;
            while ((c = is.read()) != -1) {
                bw.write(c);
            }

            is.close();
            bw.flush();
            bw.close();
        }

        return true;
    }

}
