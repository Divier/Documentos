/**
 * 
 */
package co.com.datatools.documentos.html;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.jasper.JasperUtil;

import com.lowagie.text.pdf.codec.Base64;

/**
 * Clase utilitaria encargada de funcionalidades utilitarias para manejar el html de edicion de plantilla
 * 
 * @author julio.pinzon
 * 
 */
public class HtmlUtil {

    /**
     * Logger
     */
    private final static Logger LOGGER = Logger.getLogger(HtmlUtil.class.getName());

    private static final String WORKAREA = "workArea";
    private static final String TIME_DEFAULT = " 00:00:00";
    private static final String EDITABLE_ATTRIBUTE = "contenteditable";

    /**
     * Texto para embeber imagenes
     */
    public static final String BASE64 = "data:application/octet-stream;base64,";

    /**
     * Path de imagenes estatico
     */
    private static String pathImagenes = null;

    /**
     * Metodo encargado de obtener las variables utilizadas en una plantilla a partir del HTML
     * 
     * @return Mapa con las variables y su valor por defecto
     */
    public static Map<String, Object> obtenerVariables(String htmlPlantilla) {
        LOGGER.debug("HtmlUtil:obtenerVariables");
        Map<String, Object> mapaVariables = new HashMap<String, Object>();
        // Obtiene documento inicial
        Document doc = Jsoup.parse(htmlPlantilla);

        // Obtiene el area de trabajo
        Element workArea = doc.getElementsByClass(WORKAREA).first();

        mapaVariables.putAll(obtenerVariablesElementos(workArea));
        mapaVariables.putAll(obtenerGruposElementos(workArea));
        return mapaVariables;
    }

    /**
     * Metodo encargado de obtener los grupos de una plantilla para extraer las variables
     * 
     * @return Mapa con las variables y su valor por defecto
     */
    private static Map<String, Object> obtenerGruposElementos(Element elemento) {
        LOGGER.debug("HtmlUtil::obtenerGruposElementos");
        Map<String, Object> mapaGrupos = new HashMap<String, Object>();

        // Lista de grupos
        Elements groups = elemento.getElementsByClass(JasperUtil.GROUP_CLASS);
        for (Element group : groups) {
            String idGrupo = group.attr("id");
            List<Map<String, Object>> listaGrupos = new ArrayList<Map<String, Object>>();
            listaGrupos.add(new HashMap<String, Object>());
            String contenidoGrupo = group.getElementsByClass("grupo-content").first().attr("html_grupo");
            if (contenidoGrupo != null && !contenidoGrupo.isEmpty()) {
                listaGrupos.get(0).putAll(obtenerVariables(contenidoGrupo));
            }
            mapaGrupos.put(idGrupo, listaGrupos);
        }
        return mapaGrupos;
    }

    /**
     * Metodo encargado de obtener las variables de una plantilla o grupo
     * 
     * @return Mapa con las variables y su valor por defecto
     */
    private static Map<String, Object> obtenerVariablesElementos(Element elemento) {
        LOGGER.debug("HtmlUtil::obtenerVariablesElementos");
        Map<String, Object> mapaVariables = new HashMap<String, Object>();

        // Lista de variables
        Elements variables = elemento.getElementsByClass(JasperUtil.ITEM_VARIABLE);
        if (!variables.isEmpty()) {
            for (Element variable : variables) {
                String nombreVariable = variable.attr(JasperUtil.NOMBRE_VARIABLE);
                int tipoVariable = Integer.valueOf(variable.attr(JasperUtil.TIPO_VARIABLE));
                String valorDefecto = variable.attr(JasperUtil.VALOR_DEFECTO);
                // String valorDefectoFecha = variable.attr(VALOR_DEFECTO_FECHA);
                // TODO: INCLUIR TIPO

                // Verifica si ya existe el parametro
                if (ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL != tipoVariable
                        && !mapaVariables.containsKey(nombreVariable)) {
                    // if(ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == tipoVariable && !valorDefectoFecha.isEmpty()) {
                    // mapaVariables.put(nombreVariable, valorDefectoFecha);
                    // } else {
                    if (ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == tipoVariable) {
                        mapaVariables.put(nombreVariable, valorDefecto + TIME_DEFAULT);
                    } else {
                        mapaVariables.put(nombreVariable, valorDefecto);
                    }
                }
            }
        }
        return mapaVariables;
    }

    /**
     * Genera el archivo html para la posterior edicion
     * 
     * @param htmlDocumento
     *            Contenido html de la plantilla utilizada para generar el documento
     * @param pathImagenes
     * @param list
     */
    public static String generaHtmlDocumento(String htmlDocumento, Map<String, Object> jasperDatasource,
            String pathImagenes) {
        LOGGER.debug("JasperUtil::generaHtmlGenerado");

        HtmlUtil.pathImagenes = pathImagenes;

        // Obtiene documento inicial
        Document doc = Jsoup.parse(htmlDocumento);

        // Verifica todas las secciones

        Element encabezado = doc.getElementById(JasperUtil.ENCABEZADO);
        encabezado = reemplazarVariablesElementos(encabezado, jasperDatasource);

        Element piePagina = doc.getElementById(JasperUtil.PIE_PAGINA);
        piePagina = reemplazarVariablesElementos(piePagina, jasperDatasource);

        Element cuerpo = doc.getElementById(JasperUtil.CUERPO);

        cuerpo = reemplazarVariablesElementos(cuerpo, jasperDatasource);
        cuerpo = reemplazarGruposElementos(cuerpo, jasperDatasource);

        // embebemos las imagenes
        doc = embeddingImagesFromHtmlContent(doc, false);

        JasperUtil.saveTempFile(doc.outerHtml().getBytes(), "D:\\documentos_temp\\reportes\\temp\\EJEMPLO.html");
        return doc.outerHtml();
    }

    /**
     * Metodo encargado de obtener los grupos de una plantilla para extraer las variables
     * 
     * @param pathImagenes
     * 
     * @param jasperDatasourceList
     * @return Mapa con las variables y su valor por defecto
     */
    private static Element reemplazarGruposElementos(Element elemento, Map<String, Object> jasperDatasource) {
        LOGGER.debug("HtmlUtil::reemplazarGruposElementos");

        // Lista de grupos
        Elements groups = elemento.getElementsByClass(JasperUtil.GROUP_CLASS);
        for (Element group : groups) {
            Element detailGroup = new Element(Tag.valueOf("div"), "");
            String idGrupo = group.attr("id");
            String contenidoGrupo = group.getElementsByClass("grupo-content").first().attr("html_grupo");
            if (group.attr("editable").equalsIgnoreCase(ConstantesGeneral.VALOR_SI)) {
                detailGroup.attr(EDITABLE_ATTRIBUTE, "false");
            }
            if (contenidoGrupo != null && !contenidoGrupo.isEmpty()) {
                // Obtenemos la lista de los grupos
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> subGrupos = (List<Map<String, Object>>) jasperDatasource.get(idGrupo);
                for (Map<String, Object> mapGroup : subGrupos) {
                    // Obtiene documento inicial
                    Document docGroup = Jsoup.parse(contenidoGrupo);
                    Element cuerpoGroup = docGroup.getElementById(JasperUtil.CUERPO);

                    cuerpoGroup = reemplazarVariablesElementos(cuerpoGroup, mapGroup);
                    cuerpoGroup = reemplazarGruposElementos(cuerpoGroup, mapGroup);

                    // embebemos las imagenes
                    docGroup = embeddingImagesFromHtmlContent(docGroup, false);
                    detailGroup.append(cuerpoGroup.html());
                }
            }
            group.replaceWith(detailGroup);
        }
        return elemento;
    }

    /**
     * Metodo encargado de obtener las variables de una plantilla o grupo
     * 
     * @param pathImagenes
     * 
     * @param jasperDatasourceList
     * @return Mapa con las variables y su valor por defecto
     */
    private static Element reemplazarVariablesElementos(Element section, Map<String, Object> jasperDatasource) {
        LOGGER.debug("HtmlUtil::reemplazarVariablesElementos");

        // Lista de no editables
        // Elements noEditables = section.getElementsByClass("noEditable");
        // for (Element noEditable : noEditables) {
        // Element noEditableClone = new Element(Tag.valueOf("span"), "");
        // noEditableClone.attr(EDITABLE_ATTRIBUTE, "false");
        // noEditableClone.attr("title", noEditable.attr("title"));
        // // noEditableClone.addClass("seccionNoEditable");
        // noEditableClone.html(noEditable.html());
        // noEditableClone.attr("style", "background-color: #F1F5F9;border-collapse: collapse;outline: 1px solid #c7cfd5;");
        // noEditable.replaceWith(noEditableClone);
        // }
        // TODO:Marca los no editables
        section.getElementsByClass("noEditable").attr(EDITABLE_ATTRIBUTE, "false")
//                .attr("sne", "true")
//                .attr("style", "background-color: #F1F5F9;border-collapse: collapse;outline: 1px solid #c7cfd5;")
//                .removeAttr("class")
                ;
        // Lista de variables
        Elements variables = section.getElementsByClass(JasperUtil.ITEM_VARIABLE);
        for (Element variable : variables) {
            String nombreVariable = variable.attr(JasperUtil.NOMBRE_VARIABLE);
            int tipoVariable = Integer.valueOf(variable.attr(JasperUtil.TIPO_VARIABLE));

            // Verifica si ya existe el parametro
            if (ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL != tipoVariable
                    && ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE != tipoVariable) {
                // variable.getElementsByClass(JasperUtil.HANDLER_CLASS).remove();
                // TextNode text = new TextNode(jasperDatasource.get(nombreVariable).toString(), "");
                // variable.replaceWith(text);
                // variable.removeClass(JasperUtil.ITEM_VARIABLE);
                variable.tagName("span");
                variable.attr("title", nombreVariable);
                variable.text(jasperDatasource.get(nombreVariable).toString());
                variable.removeClass(JasperUtil.ITEM_VARIABLE);
                //Quitamos imagenes utilizadas en el editor de plantillas
                variable.parent().getElementsByClass(JasperUtil.HANDLER_CLASS).remove();
            } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL == tipoVariable) {
                variable.parent().tagName("span");
                variable.attr("title", nombreVariable);
                variable.removeClass(JasperUtil.ITEM_VARIABLE);
                //Quitamos imagenes utilizadas en el editor de plantillas
                variable.parent().parent().getElementsByClass(JasperUtil.HANDLER_CLASS).remove();
            }
        }

        // Agrega las firmas
        Elements signs = section.getElementsByClass(JasperUtil.SIGN_CLASS);
        for (Element sign : signs) {
            for (Element item : sign.children()) {
                if (item.tagName().equals("img")) {
                    item.attr("src", jasperDatasource.get(item.attr("field")).toString());
                    item.attr("height", item.attr(JasperUtil.ALTO_ITEM) + "px");
                    item.attr("extension", FilenameUtils.getExtension(jasperDatasource.get(item.attr("field")).toString()));
                } else if (item.tagName().equals("hr")) {
                    item.attr("width", item.attr(JasperUtil.ANCHO_ITEM) + "px");
                    item.attr("style", "margin-left: 0;text-align: left;");
                } else if (item.tagName().equals("p")) {
                    if (!item.getElementsByClass("campofirma").isEmpty()) {
                        Element campoFirma = item.getElementsByClass("campofirma").get(0);
                        campoFirma.text(jasperDatasource.get(item.attr("field")).toString());
                    }
                }
            }
            //Quitamos imagenes utilizadas en el editor de plantillas
            sign.parent().getElementsByClass(JasperUtil.HANDLER_CLASS).remove();
            sign.removeClass(JasperUtil.SIGN_CLASS);
        }

        Elements items = section.getElementsByClass(JasperUtil.ITEM_CLASS);
        for (Element item : items) {
            if (item.attr(JasperUtil.TIPO).equals(JasperUtil.ITEM_IMAGEN)) {
                int tipoVariable = Integer.valueOf(item.attr(JasperUtil.TIPO_VARIABLE));
                String nombreVariable = item.attr(JasperUtil.NOMBRE_VARIABLE);

                if (tipoVariable == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
                    if (!item.attr(JasperUtil.ID_GRUPO).isEmpty()) {
                        // Hallamos el elemento padre para adicionarle las imagenes extraidas
                        Element parentImage = item.parent();
                        // Obtenemos la lista de rutas de las imagenes
                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> imagenes = (List<Map<String, Object>>) jasperDatasource
                                .get(nombreVariable);
                        for (Map<String, Object> mapImagen : imagenes) {

                            Element image = item.clone();
                            image.attr("src", mapImagen.get(nombreVariable).toString());
                            // Asigna el valor de la variable
                            image.attr("height", item.attr(JasperUtil.ALTO_ITEM) + "px");
                            image.attr("width", item.attr(JasperUtil.ANCHO_ITEM) + "px");
                            image.attr("title", nombreVariable);

                            // codifica la imagen
                            String imagenTemp = encodeImage(image);
                            if (imagenTemp != null && !imagenTemp.isEmpty()) {
                                image.attr("src", BASE64 + imagenTemp);
                            }
                            // Adicionamos imagenes
                            parentImage.appendElement("br");
                            parentImage.appendChild(image);
                        }
                    }
                    //Quitamos imagenes utilizadas en el editor de plantillas
                    item.parent().parent().getElementsByClass(JasperUtil.HANDLER_CLASS).remove();
                    //Quitamos la imagen por defecto
                    item.remove();
                }
            }
        }
        return section;
    }

    /**
     * Embebe las imagenes en el archivo html generado
     * 
     * @param pathHtml
     *            Path del archivo html
     * @param pathImagenes
     *            Path temporal de imagenes
     */
    public static void embeddingImagesFromHtmlFile(Path pathHtml, String pathImagenes) {
        LOGGER.debug("JasperUtil::embeddingImagesHtml");
        try {
            HtmlUtil.pathImagenes = pathImagenes;
            byte[] encodedHtml = Files.readAllBytes(pathHtml);
            String contentHtml = new String(encodedHtml, StandardCharsets.UTF_8);

            // Obtiene documento inicial y embebe las imagenes
            Document doc = Jsoup.parse(contentHtml);
            doc = embeddingImagesFromHtmlContent(doc, true);

            // Sobreescribe archivo Html
            Files.write(pathHtml, doc.outerHtml().getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("Error al embeber imagenes en documento HTM generado", e);
        }
    }

    /**
     * Embebe las imagenes en el contenido de html
     * 
     * @param contentHtml
     *            Contenido archivo html
     * @param htmlDocument
     *            Indica si se esta generando un documento en formato html
     */
    public static Document embeddingImagesFromHtmlContent(Document doc, boolean htmlDocument) {
        LOGGER.debug("HtmlUtil::embeddingImagesFromHtmlContent");
        Elements images = doc.getElementsByTag("img");
        for (Element image : images) {
            if (!image.attr("src").startsWith("data:") && !image.hasClass("cke_widget_drag_handler")
                    && (htmlDocument || !image.attr("src").contains(ConstantesGeneral.SERVLET_IMAGENES_VARIABLES))) {
                // codifica la imagen
                String imagenTemp = encodeImage(image);
                if (imagenTemp != null && !imagenTemp.isEmpty()) {
                    image.attr("src", BASE64 + imagenTemp);
                }
            }
        }
        return doc;
    }

    /**
     * Se encarga de codificar una imagen para embeberla en html
     * 
     * @param image
     * @param pathImagenes
     * @return Devuelve la imagen codificada en base64
     */
    private static String encodeImage(Element image) {
        // codifica la imagen
        String imagenCodificada = Base64.encodeFromFile(image.attr("src"));
        if (imagenCodificada != null && !imagenCodificada.isEmpty()) {
            image.attr("src", BASE64 + imagenCodificada);
        } else {
            // Puede ser una url
            // String ext = FilenameUtils.getExtension(image.attr("src"));
            File archivoImagen = new File(pathImagenes + new Date().getTime());
            try {
                // Guarda la imagen temporalmente
                FileUtils.copyURLToFile(new URL(image.attr("src")), archivoImagen);
                // codifica la imagen
                imagenCodificada = Base64.encodeFromFile(archivoImagen.getAbsolutePath());
            } catch (IOException e) {
                LOGGER.error("No pudo obtener imagen de la url : " + image.attr("src") + " :: " + e.getMessage(), e);
            }
        }
        return imagenCodificada;
    }

}
