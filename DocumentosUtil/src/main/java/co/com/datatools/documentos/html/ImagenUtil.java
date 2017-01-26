/**
 * 
 */
package co.com.datatools.documentos.html;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Logger;

import co.com.datatools.documentos.constantes.ConstantesPlantillas;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;

/**
 * Clase utilitaria encargada de funcionalidades para manejar las imagenes
 * 
 * @author julio.pinzon
 * 
 */
public class ImagenUtil {

    /**
     * Logger
     */
    private final static Logger LOGGER = Logger.getLogger(ImagenUtil.class.getName());

    /**
     * Genera los parametros de las imagenes variables creando en directorio temporal las tiffs
     * para manejarlas como subreportes
     * @param imageUrl Url de la imagen
     * @param nombreVariable Nombre de la variable con que esta nombrada la imagen
     * @param valorDefecto Valor por defecto en caso que no se encuentre el valor
     * @param pathImagenes Path temporal donde se guardan las imagenes
     * @return Lista de mapa de parametros 
     */
    public static synchronized List<Map<String, Object>> generateImageParameters(String imageUrl, String nombreVariable,
            String valorDefecto, String pathImagenes) {
        List<Map<String, Object>> imageParameters = new ArrayList<Map<String, Object>>();
        // Tratamiento especial para las imagenes .tiff que pueden ser multipagina
        String name = FilenameUtils.getName(imageUrl);
        if (name.contains(ConstantesPlantillas.EXTENSION_TIFF)) {
            File archivoImagen = new File(pathImagenes + FilenameUtils.getBaseName(imageUrl) + ConstantesPlantillas.EXTENSION_TIFF);
            try {
                // Guarda la imagen temporalmente
                FileUtils.copyURLToFile(new URL(imageUrl), archivoImagen);
                List<String> pathImages = extractMultiPageTiff(archivoImagen.getAbsolutePath(),
                        ConstantesPlantillas.EXTENSION_PNG);
                for (String rutaImagen : pathImages) {
                    Map<String, Object> mapaimagen = new HashMap<String, Object>();
                    mapaimagen.put(nombreVariable, rutaImagen);
                    // La imagen no es tiff por lo tanto se maneja como una lista de una imagen
                    imageParameters.add(mapaimagen);
                }
            } catch (IOException e) {
                LOGGER.error("No pudo obtener imagen de la url : " + imageUrl + " :: " + e.getMessage(), e);
                Map<String, Object> mapaimagen = new HashMap<String, Object>();
                mapaimagen.put(nombreVariable, valorDefecto);
                // La imagen no es tiff por lo tanto se maneja como una lista de una imagen
                imageParameters.add(mapaimagen);
            }
        } else {
            // La imagen no es tiff por lo tanto se maneja como una lista de una imagen con la url
            Map<String, Object> mapaimagen = new HashMap<String, Object>();
            mapaimagen.put(nombreVariable, imageUrl);
            imageParameters.add(mapaimagen);
        }
        return imageParameters;
    }

    /**
     * Metodo para extraer las imagenes contenidas dentro de una imagen tiff
     * 
     * @param tiffFilePath
     *            : Ruta donde se encuentra la imagen tiff
     * @param outputFileType
     *            : Tipo de las imagenes de salida (para imagen a color “jpeg” y para imagen de mas calidad “png”)
     * @return Lista de rutas temporales de los archivos generados
     * @throws IOException
     *             En caso de fallo al guardar las imagenes
     * @author julio.pinzon
     */
    public static synchronized List<String> extractMultiPageTiff(String tiffFilePath, String outputFileType) throws IOException {
        LOGGER.debug("ImagenUtil::extractMultiPageTiff");
        List<String> rutasImagenes = new ArrayList<String>();

        /*
         * create object of RenderedIamge to produce image data in form of Rasters
         */
        RenderedImage renderedImage[], page;
        File file = new File(tiffFilePath);
        /*
         * SeekabaleStream is use for taking input from file. FileSeekableStream is not committed part of JAI API.
         */
        SeekableStream seekableStream = new FileSeekableStream(file);
        ImageDecoder imageDecoder = ImageCodec.createImageDecoder("tiff", seekableStream, null);
        renderedImage = new RenderedImage[imageDecoder.getNumPages()];

        /* count no. of pages available inside input tiff file */
        int count = 0;
        for (int i = 0; i < imageDecoder.getNumPages(); i++) {
            renderedImage[i] = imageDecoder.decodeAsRenderedImage(i);
            count++;
        }

        /* set output folder path */
        String outputFolderName;
        String[] temp = null;
        temp = tiffFilePath.split("\\.");
        outputFolderName = temp[0];
        /*
         * create file object of output folder and make a directory
         */
        File fileObjForOPFolder = new File(outputFolderName);
        fileObjForOPFolder.mkdirs();

        /*
         * extract no. of image available inside the input tiff file
         */
        for (int i = 0; i < count; i++) {
            page = imageDecoder.decodeAsRenderedImage(i);
            File fileObj = new File(outputFolderName + "/" + (i + 1) + "." + outputFileType);
            /*
             * ParameterBlock create a generic interface for parameter passing
             */
            ParameterBlock parameterBlock = new ParameterBlock();
            /* add source of page */
            parameterBlock.addSource(page);
            /* add o/p file path */
            parameterBlock.add(fileObj.toString());
            /* add o/p file type */
            parameterBlock.add(outputFileType);
            /* create output image using JAI filestore */
            RenderedOp renderedOp = JAI.create("filestore", parameterBlock);
            renderedOp.dispose();

            rutasImagenes.add(fileObj.getAbsolutePath());
        }
        return rutasImagenes;
    }

}
