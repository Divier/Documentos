/**
 * 
 */
package co.com.datatools.documentos.jasper;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.lowagie.text.pdf.codec.Base64;

import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.constantes.ConstantesPlantillas;
import co.com.datatools.documentos.documento.NumeroUtil;
import co.com.datatools.documentos.enumeraciones.EnumFormatoDocumento;
import co.com.datatools.documentos.html.HtmlUtil;
import co.com.datatools.documentos.html.ImagenUtil;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRParagraph;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignBreak;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JRDesignTextElement;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.type.BreakTypeEnum;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.FillEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.LineSpacingEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;
import net.sf.jasperreports.engine.type.OverflowType;
import net.sf.jasperreports.engine.type.ResetTypeEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.PdfReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Clase utilitaria encargada de generar los documentos utilizando la libreria jasper
 * 
 * @author julio.pinzon
 * 
 */
@SuppressWarnings("deprecation")
public class JasperUtil {

    /**
     * Logger
     */
    private final static Logger LOGGER = Logger.getLogger(JasperUtil.class.getName());

    /**
     * Ancho de columna por defecto
     */
    private int columnWidth = 555;

    /**
     * Ruta archivo
     */
    private String pathArchivo;

    /**
     * valor del centimetro en pixeles
     */
    // html
    // private final double CENTIMETRO = 37.795276;
    // ireport
    private static final double CENTIMETRO = 28.35;
    private final float SCALA = 0.74f;
    private final float SPACE_PIXEL = 3.03f;
    private static final Integer DEFAULT_HEIGHT = 35;

    /**
     * Estilos de fuentes
     */
    private JRDesignStyle normalStyle;
    private JRDesignStyle boldStyle;
    private JRDesignStyle italicStyle;
    private JRDesignStyle arialStyle;
    private JRDesignStyle arialBoldStyle;

    /**
     * Objeto para generar el reporte
     */
    private JasperDesign jasperDesign;

    /**
     * Llaves para los atributos del html
     */
    private static final String Y = "y";
    private static final String X = "x";
    private static final String ANCHO = "ancho";
    private static final String ALTO = "alto";
    public static final String ANCHO_ITEM = "ancho_item";
    public static final String ALTO_ITEM = "alto_item";
    public static final String CKITEM_CLASS = "ckeditorItem";
    public static final String ITEM_CLASS = "item";
    public static final String ITEM_VARIABLE = "variable";
    public static final String ITEM_IMAGEN = "imagen";
    public static final String TIPO = "tipo";
    private static final String ID_VARIABLE = "idVariable";
    public static final String NOMBRE_VARIABLE = "nombreVariable";
    public static final String VALOR_DEFECTO = "valorDefecto";
    public static final String VALOR_DEFECTO_FECHA = "valorDefectoFecha";
    public static final String TIPO_VARIABLE = "tipoVariable";
    private static final String PRESENTACION_VARIABLE = "presentacionVariable";
    private static final String TIPO_IMAGEN = "tipoImagen";
    private static final String FORMATO_PRESENTACION = "formatoPresentacionVariable";
    private static final String SEPARADOR_MILES = "separadorMiles";
    private static final String FORMATO_NUMEROS_NEGATIVOS = "formatoNumerosNegativos";
    private static final String FORMATO_VARIABLE = "formatoVariable";
    private static final String LONGITUD_VARIABLE = "longitud";
    private static final String DECIMALES_VARIABLE = "decimales";
    private static final String NUMERO = "numero";
    private static final String BREAK_CLASS = "cke_pagebreak";
    public static final String GROUP_CLASS = "grupo";
    public static final String SIGN_CLASS = "firmamecanica";
    private static final String SIGN_IMAGE = "firma_defecto.png";
    public static final String ID_GRUPO = "id_grupo";
    public static final String HANDLER_CLASS = "cke_widget_drag_handler_container";
    private static final String BORDER_ATTR = "border";
    private static final String DONE_CLASS = "done";
    private static final String TD_TAG = "td";
    private static final String TH_TAG = "th";
    private static final String P_TAG = "p";
    private static final String CAPTION_TAG = "caption";
    private static final String FIELD_ATTR = "field";
    private static final String CELLPADDING_ATTR = "cellpadding";
    private static final String CELLSPACING_ATTR = "cellspacing";
    private static final String ZERO = "0";
    private static final String NUMTOTPAG_CLASS = "numeroTotalPagina";
    private static final String NUMPAG_CLASS = "numeroPagina";
    private static final String TR_TAG = "tr";
    private static final String TABLE_TAG = "table";
    private static final String HR = "hr";
    private static final Integer ESPACIO_LINEA = 15;

    /**
     * Llaves de las secciones
     */
    private static final String TITULO = "titulo";
    public static final String ENCABEZADO = "encabezado";
    public static final String PIE_PAGINA = "piePagina";
    private static final String ULTIMO_PIE_PAGINA = "ultimoPiePagina";
    public static final String CUERPO = "cuerpo";

    /**
     * Key del parametro de sub reporte
     */
    private static final String SUBREPORT_DIR_KEY = "SUBREPORT_DIR_KEY";

    /**
     * Key del parametro de imagenes
     */
    private static final String IMAGE_DIR_KEY = "IMAGE_DIR_KEY";

    /**
     * Key del parametro de preliminar
     */
    private static final String PREVIEW_KEY = "PREVIEW_KEY";

    /**
     * Patron para identificar las variables en el html
     */
    // private String patternVariable = "(?i)(<strong class=\"variable.*?>)([$]+[F]+[{]+(\\w+)?[}]+)(</strong>)";

    /**
     * Nombre del campo que se crea por defecto
     */
    private static final String CAMPO_DEFECTO = "-DEFAULT";

    /**
     * Constante con nombre de variable jasper para el numero de la pagina
     */
    private static final String CURRENT_PAGE_NUMBER = "CURRENT_PAGE_NUMBER";

    /**
     * Constante con nombre de variable jasper para el numero total de la pagina
     */
    private static final String PAGE_NUMBER = "$V{PAGE_NUMBER}";

    /**
     * Objeto que tiene los valores de instancia de jasper util
     */
    private JasperUtilDTO jasperUtilDTO;

    /**
     * Constructor
     * 
     * @param jasperUtilDTO
     */
    public JasperUtil(JasperUtilDTO jasperUtilDTO) {
        this.jasperUtilDTO = jasperUtilDTO;
        if (this.jasperUtilDTO.getGroupNames() == null) {
            this.jasperUtilDTO.setGroupNames(new HashMap<String, Object>());
        }
    }

    /**
     * Genera el documento. De lo contrario lanza excepcion.
     * 
     * @throws Exception
     *             Si se presenta algun error inesperado generando el documento
     */
    public void generateDocument() throws Exception {
        LOGGER.debug("JasperUtil::generateDocument");
        String mensajeExc = null;

        try {
            // Inicializa las variables locales
            String fileName = jasperUtilDTO.getNombreDocumento();
            EnumFormatoDocumento tipoArchivo = jasperUtilDTO.getFormato();
            String xmlDocumento = jasperUtilDTO.getXmlDocumento();

            // Obtiene el datasource por defecto
            if (jasperUtilDTO.getJasperDatasourceList() == null) {
                jasperUtilDTO.setJasperDatasourceList(new ArrayList<Map<String, Object>>());
            }

            // Ingresa un elemento por defecto
            if (jasperUtilDTO.getJasperDatasourceList().isEmpty() && jasperUtilDTO.isPreliminar()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(CAMPO_DEFECTO, CAMPO_DEFECTO);
                jasperUtilDTO.getJasperDatasourceList().add(map);
            }

            if (!jasperUtilDTO.isSubreporte()) {
                // Verifica que las rutas existan, si no existen, las crea
                verifyFolders(jasperUtilDTO.getPathDocumentos(), jasperUtilDTO.getPathReportes(),
                        jasperUtilDTO.getPathTemporalGuardaReporte(), jasperUtilDTO.getPathImagenes());
            }

            // jrxml compiling process
            long start = System.currentTimeMillis();
            generateJasperDesign(xmlDocumento, fileName);

            JasperCompileManager.compileReportToFile(jasperDesign,
                    jasperUtilDTO.getPathReportes() + fileName + EnumFormatoDocumento.JASPER.getExtension());
            LOGGER.info("Compile time : " + (System.currentTimeMillis() - start));
            // Verifica si el tipo de archivo es nulo, si es nulo quiere decir que se manneja como un grupo o subreporte
            if (EnumFormatoDocumento.JASPER == tipoArchivo) {
                this.pathArchivo = jasperUtilDTO.getPathReportes() + fileName
                        + EnumFormatoDocumento.JASPER.getExtension();
            } else if (tipoArchivo != null) {
                this.jasperUtilDTO.setJasperPath(
                        jasperUtilDTO.getPathReportes() + fileName + EnumFormatoDocumento.JASPER.getExtension());
                this.exportDocument();
            }

        } catch (JRException e) {
            LOGGER.error("JasperUtil::generarDocumento Error de jasper", e);
            mensajeExc = e.getMessage();
        } catch (Exception e) {
            LOGGER.error("JasperUtil::generarDocumento", e);
            mensajeExc = e.getMessage();
        }

        if (mensajeExc != null) {
            throw new Exception("Problemas generando el documento. " + mensajeExc);
        }

    }

    /**
     * Exporta el documento al formato solicitado
     * 
     * @throws IOException
     *             Excepcion en caso de error manejo de archivos
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    public void exportDocument() throws IOException, JRException {
        LOGGER.debug("JasperUtil::exportDocument");
        // Iniciaaliza las variables locales
        String fileName = jasperUtilDTO.getNombreDocumento();

        // Formato del archivo
        EnumFormatoDocumento tipoArchivo = jasperUtilDTO.getFormato();

        /*
         * Inicializa los parametros de jasper
         */
        Map<String, Object> jasperParameter = new ConcurrentHashMap<String, Object>();

        // exporting process
        jasperParameter.put("DIR", jasperUtilDTO.getPathReportes());
        jasperParameter.put(SUBREPORT_DIR_KEY, jasperUtilDTO.getPathReportes());
        jasperParameter.put(IMAGE_DIR_KEY, jasperUtilDTO.getPathImagenes());
        jasperParameter.put(PREVIEW_KEY, Boolean.valueOf(jasperUtilDTO.isPreliminar()));
        if (EnumFormatoDocumento.XLS == tipoArchivo || EnumFormatoDocumento.CSV == tipoArchivo) {
            jasperParameter.put("IS_IGNORE_PAGINATION", Boolean.TRUE);
        }

        JRFileVirtualizer virtualizer = new JRFileVirtualizer(10000, jasperUtilDTO.getPathReportes());
        virtualizer.setReadOnly(false);
        jasperParameter.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        /*
         * JasperReport is the object that holds our compiled jrxml file Carga el archivo compilado
         */
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperUtilDTO.getJasperPath());

        List<Map<String, ?>> jasperDatasourceList = new ArrayList<Map<String, ?>>();
        jasperDatasourceList.addAll(jasperUtilDTO.getJasperDatasourceList());
        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(jasperDatasourceList);

        /*
         * JasperPrint is the object contains report after result filling process Llena reporte con los datos de la fuente
         */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, dataSource);

        if (EnumFormatoDocumento.PDF == tipoArchivo) {

            Exporter<ExporterInput, PdfReportConfiguration, PdfExporterConfiguration, OutputStreamExporterOutput> exporter = new JRPdfExporter();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
                    jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension()));
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.exportReport();
            // 1- export to PDF
            // JasperExportManager.exportReportToPdfFile(jasperPrint, jasperUtilDTO.getPathTemporalGuardaReporte()
            // + fileName + tipoArchivo.getExtension());
        } else if (EnumFormatoDocumento.HTML == tipoArchivo) {
            // 2- export to HTML
            JRHtmlExporter jrExporter = new JRHtmlExporter();
            // jrExporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR, new File(pathImagenes));
            // jrExporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
            jrExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
            jrExporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRHtmlExporterParameter.OUTPUT_FILE_NAME,
                    jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension());
            jrExporter.exportReport();
            // JasperExportManager.exportReportToHtmlFile(jasperPrint, PATH_TEMPORAL_GUARDA_REPORTE + fileName
            // + tipoArchivo.getExtension());
            // Embeber las imagenes
            HtmlUtil.embeddingImagesFromHtmlFile(
                    Paths.get(jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension()),
                    jasperUtilDTO.getPathImagenes());

        } else if (EnumFormatoDocumento.XLS == tipoArchivo) {
            // 3- export to Excel sheet
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension());
            exporter.exportReport();
        } else if (EnumFormatoDocumento.CSV == tipoArchivo) {
            // 4- export to Csv sheet
            JRCsvExporter exporterCSV = new JRCsvExporter();
            exporterCSV.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
            exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
            exporterCSV.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, true);
            exporterCSV.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,
                    jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension());
            exporterCSV.exportReport();
        } else if (EnumFormatoDocumento.XML == tipoArchivo) {
            // 5- export to XML
            JRXmlExporter jrExporter = new JRXmlExporter();
            jrExporter.setParameter(JRXmlExporterParameter.IS_EMBEDDING_IMAGES, Boolean.FALSE);
            jrExporter.setParameter(JRXmlExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRXmlExporterParameter.OUTPUT_FILE_NAME,
                    jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension());
            jrExporter.exportReport();
        } else if (EnumFormatoDocumento.RTF == tipoArchivo) {
            // 5- export to XML
            JRRtfExporter jrExporter = new JRRtfExporter();
            jrExporter.setParameter(JRXmlExporterParameter.IS_EMBEDDING_IMAGES, Boolean.FALSE);
            jrExporter.setParameter(JRXmlExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRXmlExporterParameter.OUTPUT_FILE_NAME,
                    jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension());
            jrExporter.exportReport();
        } else if (EnumFormatoDocumento.DOCX == tipoArchivo) {
            // 5- export to XML
            JRDocxExporter jrExporter = new JRDocxExporter();
            jrExporter.setParameter(JRXmlExporterParameter.IS_EMBEDDING_IMAGES, Boolean.FALSE);
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension());
            jrExporter.exportReport();
        }
        pathArchivo = jasperUtilDTO.getPathTemporalGuardaReporte() + fileName + tipoArchivo.getExtension();
    }

    /**
     * Verifica la creacion de folders temporales
     * 
     * @throws IOException
     *             Excepcion en caso de error en la creacion del directorio
     */
    public static void verifyFolders(String pathDocumentos, String pathReportes, String pathTemporalGuardaReporte,
            String pathImagenes) throws IOException {
        LOGGER.debug("JasperUtil::verifyFolders");
        // Verifica que las rutas existan, si no existen, las crea
        verifyFolder(pathDocumentos);
        verifyFolder(pathReportes);
        verifyFolder(pathTemporalGuardaReporte);
        verifyFolder(pathImagenes);
    }

    /**
     * Verifica la existencia de un directorio y lo crea en caso que no exista
     * 
     * @param directory
     *            Ruta del directorio
     * @throws IOException
     *             Excepcion en caso de error en la creacion del directorio
     */
    public static void verifyFolder(String directory) throws IOException {
        LOGGER.debug("JasperUtil::verifyFolder");
        // Verifica que las rutas existan, si no existen, las crea
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            createFolder(path);
        }
    }

    /**
     * Metodo para crear directorio y hacerlo editable
     * 
     * @param pathDirectory
     *            Ruta del directorio
     * @throws IOException
     *             Excepcion en caso de error en la creacion del directorio
     */
    private static void createFolder(Path pathDirectory) throws IOException {
        LOGGER.debug("JasperUtil::createFolder");
        Path directory = Files.createDirectory(pathDirectory);
        directory.toFile().setExecutable(true, false);
        directory.toFile().setReadable(true, false);
        directory.toFile().setWritable(true, false);
    }

    /**
     * Obtiene el objeto de generacion del reporte a partir del html del documento
     * 
     * @param htmlDocumento
     *            Contenido html de la plantilla utilizada para generar el documento
     * @param fileName
     *            Nombre que tendra el archivo a generar
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private void generateJasperDesign(String htmlDocumento, String fileName) throws JRException {
        LOGGER.debug("JasperUtil::generateJasperDesign");
        // JasperDesign
        initJasperDesign();

        // Obtiene documento inicial
        Document doc = Jsoup.parse(htmlDocumento);

        // Obtiene el area de trabajo
        Element workArea = doc.getElementsByClass("workArea").first();

        // Quita los nombres de las secciones para que no afecten la generacion
        workArea.getElementsByClass("bandName").remove();

        // Propiedades plantilla
        jasperDesign.setName(fileName);

        // Genera subreportes
        generateSubReports(workArea);

        // Si no es sub reporte se verifican todas las secciones,
        // en caso contrario solo se verifica y cuerpo y se da un trato especial
        if (!jasperUtilDTO.isSubreporte()) {
            jasperDesign.setPageWidth(convertCentimetrosToPixeles(Float.valueOf(workArea.attr(ANCHO))));
            jasperDesign.setLeftMargin(convertCentimetrosToPixeles(Float.valueOf(workArea.attr("margenIzquierda"))));
            jasperDesign.setRightMargin(convertCentimetrosToPixeles(Float.valueOf(workArea.attr("margenDerecha"))));
            jasperDesign.setTopMargin(convertCentimetrosToPixeles(Float.valueOf(workArea.attr("margenSuperior"))));
            jasperDesign.setBottomMargin(convertCentimetrosToPixeles(Float.valueOf(workArea.attr("margenInferior"))));
        } else {
            jasperDesign.setLeftMargin(0);
            jasperDesign.setRightMargin(0);
            jasperDesign.setTopMargin(0);
            jasperDesign.setBottomMargin(0);
            int pageWidth = convertCentimetrosToPixeles(Float.valueOf(workArea.attr(ANCHO)))
                    - (convertCentimetrosToPixeles(Float.valueOf(workArea.attr("margenIzquierda")))
                            + convertCentimetrosToPixeles(Float.valueOf(workArea.attr("margenDerecha"))));
            jasperDesign.setPageWidth(pageWidth);
        }
        jasperDesign.setPageHeight(convertCentimetrosToPixeles(Float.valueOf(workArea.attr(ALTO))));
        columnWidth = jasperDesign.getPageWidth() - (jasperDesign.getLeftMargin() + jasperDesign.getRightMargin());
        jasperDesign.setColumnCount(1);
        jasperDesign.setColumnWidth(columnWidth);

        // Genera parametros
        generateParameters(workArea);

        // Genera campos
        generateFields(workArea);

        // Si no es sub reporte se verifican todas las secciones,
        // en caso contrario solo se verifica y cuerpo y se da un trato especial
        if (!jasperUtilDTO.isSubreporte()) {
            // titulo
            Element titulo = doc.getElementById(TITULO);
            addItems(titulo);
            JRDesignBand band = getJRBand(titulo, false);

            // Adiciona la seccion de titulo
            jasperDesign.setTitle(band);

            Element encabezado = doc.getElementById(ENCABEZADO);
            addItems(encabezado);
            band = getJRBand(encabezado, false);

            // Adiciona la seccion de encabezado
            jasperDesign.setPageHeader(band);

            Element piePagina = doc.getElementById(PIE_PAGINA);
            addItems(piePagina);
            band = getJRBand(piePagina, false);

            // Adiciona la seccion de pie_pagina
            jasperDesign.setPageFooter(band);

            Element ultimoPiePagina = doc.getElementById(ULTIMO_PIE_PAGINA);
            addItems(ultimoPiePagina);
            band = getJRBand(ultimoPiePagina, false);

            // Adiciona la seccion de pie_pagina
            jasperDesign.setLastPageFooter(band);
        }

        // Genera el background que indica si es un preliminar
        if (!jasperUtilDTO.isSubreporte()) {
            generateBackground();
        }

        Element cuerpo = doc.getElementById(CUERPO);
        // Pueden ser varios detalles
        List<JRDesignBand> details = generateDetails(cuerpo);

        // Adiciona la seccion de detalle
        for (JRDesignBand jrDesignBand : details) {
            ((JRDesignSection) jasperDesign.getDetailSection()).addBand(jrDesignBand);
        }
    }

    /**
     * Genera el background cuando se genera una vista preliminar
     */
    private void generateBackground() {
        LOGGER.debug("JasperUtil::generateBackground");
        // Decodifica la imagen por defecto
        if (jasperUtilDTO.isPreliminar()
                && !Files.exists(Paths.get(jasperUtilDTO.getPathImagenes() + ConstantesPlantillas.PREVIEW_IMAGE))) {
            byte[] imagenTemp = Base64.decode(jasperUtilDTO.getPreliminarImage());
            saveTempFile(imagenTemp, jasperUtilDTO.getPathImagenes() + ConstantesPlantillas.PREVIEW_IMAGE);
        }

        JRDesignBand band = new JRDesignBand();

        // Expresion del imagen
        JRDesignExpression expression = new JRDesignExpression();
        expression.setText("$P{PREVIEW_KEY}");
        band.setPrintWhenExpression(expression);

        int pageHeight = jasperDesign.getPageHeight() - (jasperDesign.getTopMargin() + jasperDesign.getBottomMargin());
        band.setHeight(pageHeight);

        // Expresion del imagen
        JRDesignExpression imageExpression = new JRDesignExpression();
        imageExpression.setText("$P{IMAGE_DIR_KEY} + \"" + ConstantesPlantillas.PREVIEW_IMAGE + "\"");
        // Imagen en reporte generada
        JRDesignImage image = new JRDesignImage(this.jasperDesign);
        image.setPrintWhenDetailOverflows(true);
        image.setX(-jasperDesign.getLeftMargin());
        image.setY(0);
        image.setWidth(jasperDesign.getPageWidth());
        image.setHeight(pageHeight);
        image.setScaleImage(ScaleImageEnum.FILL_FRAME);
        image.setExpression(imageExpression);
        image.setLazy(true);
        image.setUsingCache(false);
        image.setOnErrorType(OnErrorTypeEnum.BLANK);
        // Adiciona la imagen
        band.addElement(image);
        // Adiciona la seccion de background
        jasperDesign.setBackground(band);
    }

    /**
     * Genera los subreportes en el caso de las vistas preliminares con valores por defecto
     * 
     * @param main
     *            Elemento principal html de la plantilla
     */
    @SuppressWarnings("unchecked")
    private void generateSubReports(Element main) {
        LOGGER.debug("JasperUtil::generateSubReports");
        // Verifica los grupos para generarlos como subreportes independientes
        Elements groups = main.getElementsByClass(GROUP_CLASS);
        // Adiciona las imagenes variables que se manejaran como grupos
        groups.addAll(main.getElementsByAttributeValue(TIPO_VARIABLE,
                String.valueOf(ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE)));
        for (Element group : groups) {
            String groupId = group.attr("id");
            // Atributo que tienen las imagenes variables para ser procesadas como grupos
            if (!group.attr(ID_GRUPO).isEmpty() && !group.attr(TIPO_VARIABLE).isEmpty()) {
                groupId = group.attr(ID_GRUPO);
            } else if (!group.attr(TIPO_VARIABLE).isEmpty()) {
                continue;
            }
            // Si no tiene parametros envia los que son por defecto
            if (this.jasperUtilDTO.getJasperDatasourceList().isEmpty()) {
                Map<String, Object> mapGroup = new HashMap<String, Object>();
                mapGroup.put(groupId, null);
                this.jasperUtilDTO.getJasperDatasourceList().add(mapGroup);
            } else if (!this.jasperUtilDTO.getJasperDatasourceList().get(0).containsKey(groupId)) {
                this.jasperUtilDTO.getJasperDatasourceList().get(0).put(groupId, null);
            }
            // Verifica si el subreporte ya fue generado
            if (!this.jasperUtilDTO.getGroupNames().containsKey(groupId)) {
                JasperUtilDTO jasperUtilDTO = new JasperUtilDTO();
                jasperUtilDTO.setSubreporte(true);
                jasperUtilDTO.setJasperDatasourceList(
                        (List<Map<String, Object>>) this.jasperUtilDTO.getJasperDatasourceList().get(0).get(groupId));
                jasperUtilDTO.setPreliminar(true);
                String xmlDocumento = null;
                if (!group.getElementsByClass("grupo-content").isEmpty()) {
                    xmlDocumento = group.getElementsByClass("grupo-content").first().attr("html_grupo");
                } else {
                    // Cuando es una imagen variable
                    xmlDocumento = generateContentImageVariable(group.clone(), main.clone());
                }
                jasperUtilDTO.setXmlDocumento(xmlDocumento);
                jasperUtilDTO.setNombreDocumento(jasperDesign.getName() + "_" + groupId);
                jasperUtilDTO.setFormato(null);
                jasperUtilDTO.setPathDocumentos(this.jasperUtilDTO.getPathDocumentos());
                jasperUtilDTO.setPathImagenes(this.jasperUtilDTO.getPathImagenes());
                jasperUtilDTO.setPathReportes(this.jasperUtilDTO.getPathReportes());
                jasperUtilDTO.setPathTemporalGuardaReporte(this.jasperUtilDTO.getPathTemporalGuardaReporte());
                // Genera una nueva instancia para generar el reporte del grupo
                JasperUtil jasperUtil = new JasperUtil(jasperUtilDTO);

                try {
                    jasperUtil.generateDocument();
                } catch (Exception e) {
                    LOGGER.error("JasperUtil::generateSubReports" + e.getMessage());
                    return;
                }
                // Coloca el grupo en el datasource
                this.jasperUtilDTO.getJasperDatasourceList().get(0).put(groupId, jasperUtil.getJasperDatasourceList());
                this.jasperUtilDTO.getGroupNames().put(groupId, jasperUtil.getJasperDatasourceList());
            } else {
                // Si ya fue generado coloca los mismos parametros
                this.jasperUtilDTO.getJasperDatasourceList().get(0).put(groupId,
                        this.jasperUtilDTO.getGroupNames().get(groupId));
            }

            group.getElementsByClass("grupo-content").remove();
        }
    }

    /**
     * Adiciona los items de imagenes independientes del texto
     * 
     * @param section
     *            Seccion html donde se adicionan los items de tipo imagen
     */
    private void addItems(Element section) {
        LOGGER.debug("JasperUtil::addItems");
        Elements ckItems = section.getElementsByClass(CKITEM_CLASS);
        for (Element ckItem : ckItems) {
            Elements items = ckItem.getElementsByClass(ITEM_CLASS);
            for (Element item : items) {
                // vERIFICAMOS QUE SEA UNA IMAGEN
                if (item.attr(TIPO).equals(ITEM_IMAGEN)) {
                    // Adiciona el item a la seccion de detalle
                    section.appendChild(item.clone());
                }
            }
            for (Element item : items) {
                // vERIFICAMOS QUE SEA UNA IMAGEN
                if (item.attr(TIPO).equals(ITEM_IMAGEN) && !ckItem.text().isEmpty()) {
                    // Reemplaza el item de imagen por espacios en blanco
                    StringBuilder spaces = new StringBuilder();
                    for (int i = 0; i < Integer.valueOf(item.attr(ANCHO_ITEM)) / SPACE_PIXEL; i++) {
                        spaces.append("&nbsp;");
                    }

                    Element itemSpace = new Element(Tag.valueOf("span"), "");
                    itemSpace.html(spaces.toString());
                    item.replaceWith(itemSpace);
                }
            }
            // quita los items del ck item
            ckItem.getElementsByClass(ITEM_CLASS).remove();
            ckItem.getElementsByClass(JasperUtil.HANDLER_CLASS).remove();
        }
    }

    /**
     * Genera los campos que se ingresaran al generar el documento
     * 
     * @param workArea
     *            Seccion html principal de la plantilla
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private void generateFields(Element workArea) throws JRException {
        LOGGER.debug("JasperUtil::generateFields");
        // Lista para identificar si un parametro ya fue creado
        List<String> fields = new ArrayList<>();
        Elements variables = workArea.getElementsByClass(ITEM_VARIABLE);

        if (variables.isEmpty()) {
            // Campo por defecto
            if (this.jasperUtilDTO.isPreliminar()) {
                generateField(CAMPO_DEFECTO, ConstantesPlantillas.ID_TIPO_VARIABLE_TEXTO);
            }
            fields.add(CAMPO_DEFECTO);
        }

        for (Element variable : variables) {
            String numero = variable.attr(NUMERO);
            String nombreVariable = variable.attr(NOMBRE_VARIABLE);
            int tipoVariable = Integer.valueOf(variable.attr(TIPO_VARIABLE));

            // No debe generar campos para las variables de tipo imagen variable ya que se manejaran como subreportes(grupos)
            if (tipoVariable != ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE
                    || variable.attr(ID_GRUPO).isEmpty()) {
                boolean renombrado = false;
                // Recorremos el mapa de parametros para identificar si los valores fueron enviados
                for (Map<String, Object> jasperParameter : jasperUtilDTO.getJasperDatasourceList()) {
                    nombreVariable = variable.attr(NOMBRE_VARIABLE);
                    // Verificar si existe el parametro para darle el formato
                    if (!jasperParameter.containsKey(nombreVariable) && this.jasperUtilDTO.isPreliminar()) {
                        // Revisamos si es una variable que esta repetida en la plantilla
                        if (numero != null && !numero.isEmpty()) {
                            nombreVariable = nombreVariable + "_" + numero;
                            renombrado = true;
                        }
                        if (!variable.attr(VALOR_DEFECTO_FECHA).isEmpty()
                                && ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == tipoVariable) {
                            jasperParameter.put(nombreVariable, variable.attr(VALOR_DEFECTO_FECHA));
                        } else {
                            jasperParameter.put(nombreVariable, variable.attr(VALOR_DEFECTO));
                        }
                    }
                }
                // Verifica si es una variable con varias instancias dentro de la plantilla
                if (numero != null && !numero.isEmpty() && !renombrado) {
                    nombreVariable = nombreVariable + "_" + numero;
                }
                // Verifica si ya existe el parametro
                if (!fields.contains(nombreVariable)) {
                    // Campo
                    generateField(nombreVariable, tipoVariable);

                    fields.add(nombreVariable);
                }
            }
        }
    }

    /**
     * Genera los campos que se ingresaran al generar el documento reemplazando los casos que no vienen por los valores por defecto
     * 
     * @param workArea
     *            Seccion html principal de la plantilla
     * @param jasperDatasourceList
     *            Seccion html del cuerpo de la plantilla
     * @param pathImagenes
     * @param simboloMoneda
     * @return Lista de mapas que contine los valores de las variables utilizadas en la generacion del documento
     */
    private static List<Map<String, Object>> generateDefaultValues(Element workArea,
            List<Map<String, Object>> jasperDatasourceList, String pathImagenes, String simboloMoneda) {
        LOGGER.debug("JasperUtil::generateDefaultValues");
        // Lista para identificar si un parametro ya fue creado
        List<String> fields = new ArrayList<>();
        Elements variables = workArea.getElementsByClass(ITEM_VARIABLE);
        List<Map<String, Object>> jasperDatasourceListDefecto = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> jasperDatasourceListFormatted = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantesGeneral.DATE_FORMAT);
        if (variables.isEmpty() && jasperDatasourceList.isEmpty()) {
            fields.add(CAMPO_DEFECTO);
            Map<String, Object> jasperDatasourceMap = new HashMap<String, Object>();
            jasperDatasourceMap.put(CAMPO_DEFECTO, CAMPO_DEFECTO);
            jasperDatasourceList.add(jasperDatasourceMap);
        } else {
            for (Element variable : variables) {
                String numero = variable.attr(NUMERO);
                String nombreVariable = variable.attr(NOMBRE_VARIABLE);
                int tipoVariable = Integer.valueOf(variable.attr(TIPO_VARIABLE));
                String formato = variable.attr(FORMATO_VARIABLE);
                // Formato de numero negativo seleccionado
                int formatoNumerosNegativos = 0;
                boolean renombrado = false;
                // Define el objeto format para dar formato correcto a la variable
                Format myFormatter = null;
                if (ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO == tipoVariable) {

                    // Formato de numero negativo seleccionado
                    try {
                        formatoNumerosNegativos = Integer.valueOf(variable.attr(FORMATO_NUMEROS_NEGATIVOS));
                    } catch (NumberFormatException e) {
                        LOGGER.info("Atributo de variable formato de numeros negativos no numerico: "
                                + variable.attr(FORMATO_NUMEROS_NEGATIVOS));
                    }
                    String presentacion = variable.attr(PRESENTACION_VARIABLE);
                    String patron = "###";
                    if (!presentacion.equalsIgnoreCase(ConstantesPlantillas.PRESENTACION_COMO_LETRAS)) {
                        String separadorMiles = variable.attr(SEPARADOR_MILES);
                        if (separadorMiles.equalsIgnoreCase(ConstantesGeneral.VALOR_SI)) {
                            patron = "###,###";
                        }
                        if (presentacion.equalsIgnoreCase(ConstantesPlantillas.PRESENTACION_COMO_MONEDA)) {
                            patron = simboloMoneda + patron;
                        }
                    }
                    StringBuilder pattern = new StringBuilder(patron);
                    int decimales = 0;
                    try {
                        decimales = Integer.valueOf(variable.attr(DECIMALES_VARIABLE));
                    } catch (NumberFormatException e) {
                        LOGGER.info("Atributo de variable decimales no numerico: " + variable.attr(DECIMALES_VARIABLE));
                    }
                    if (decimales > 0) {
                        pattern.append(".");
                        for (int i = 0; i < decimales; i++) {
                            pattern.append("#");
                        }
                        // myFormatter = new DecimalFormat(pattern.toString());
                        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());
                        decimalFormat.setDecimalSeparatorAlwaysShown(true);
                        decimalFormat.setMinimumFractionDigits(decimales);
                        myFormatter = decimalFormat;
                    } else {
                        myFormatter = new DecimalFormat(pattern.toString());
                    }
                } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == tipoVariable) {
                    if (!formato.isEmpty()) {
                        myFormatter = new SimpleDateFormat(formato);
                    }
                }
                int index = 0;
                // Recorremos el mapa de parametros para identificar si los valores fueron enviados
                for (Map<String, Object> jasperParameter : jasperDatasourceList) {
                    if (jasperDatasourceListDefecto.size() <= index) {
                        jasperDatasourceListDefecto.add(index, new HashMap<String, Object>());
                    }
                    if (jasperDatasourceListFormatted.size() <= index) {
                        jasperDatasourceListFormatted.add(index, new HashMap<String, Object>());
                    }
                    nombreVariable = variable.attr(NOMBRE_VARIABLE);
                    // Verificar si existe el parametro para darle el formato
                    if (jasperParameter.containsKey(nombreVariable)) {
                        Object valorParametro = jasperParameter.get(nombreVariable);

                        // Revisamos si es una variable que esta repetida en la plantilla
                        if (numero != null && !numero.isEmpty()) {
                            nombreVariable = nombreVariable + "_" + numero;
                            renombrado = true;
                        }
                        jasperDatasourceListFormatted.get(index).put(nombreVariable, valorParametro);
                        if (valorParametro != null) {
                            if (myFormatter != null) {
                                try {
                                    if (myFormatter instanceof DecimalFormat && valorParametro instanceof Number) {
                                        jasperDatasourceListFormatted.get(index).put(nombreVariable,
                                                myFormatter.format(Double.parseDouble(valorParametro.toString())));
                                    } else if (myFormatter instanceof SimpleDateFormat
                                            && valorParametro instanceof Date) {
                                        jasperDatasourceListFormatted.get(index).put(nombreVariable,
                                                myFormatter.format(valorParametro));
                                    } else if (myFormatter instanceof SimpleDateFormat
                                            && valorParametro instanceof String) {
                                        Date fecha = sdf.parse(valorParametro.toString());
                                        jasperDatasourceListFormatted.get(index).put(nombreVariable,
                                                myFormatter.format(fecha));
                                    } else if (myFormatter instanceof DecimalFormat
                                            && valorParametro instanceof String) {
                                        jasperDatasourceListFormatted.get(index).put(nombreVariable,
                                                myFormatter.format(new BigDecimal(valorParametro.toString())));
                                    }
                                } catch (ParseException | IllegalArgumentException e) {
                                    LOGGER.error(
                                            "Error al dar formato a los parametros del reporte: " + e.getMessage());
                                    if (!variable.attr(VALOR_DEFECTO_FECHA).isEmpty()
                                            && ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == tipoVariable) {
                                        jasperDatasourceListDefecto.get(index).put(nombreVariable,
                                                variable.attr(VALOR_DEFECTO_FECHA));
                                    } else {
                                        jasperDatasourceListDefecto.get(index).put(nombreVariable,
                                                variable.attr(VALOR_DEFECTO));
                                    }
                                }
                            } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_TEXTO == tipoVariable
                                    || ConstantesPlantillas.ID_TIPO_VARIABLE_URL == tipoVariable
                                    || ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE == tipoVariable) {
                                int longitud = 0;
                                try {
                                    longitud = Integer.valueOf(variable.attr(LONGITUD_VARIABLE));
                                } catch (NumberFormatException e) {
                                    LOGGER.info("Atributo de variable longitud no numerico: "
                                            + variable.attr(LONGITUD_VARIABLE));
                                }
                                if (longitud > 0 && valorParametro.toString().length() > longitud) {
                                    jasperDatasourceListFormatted.get(index).put(nombreVariable,
                                            valorParametro.toString().substring(0, longitud));
                                } else {
                                    jasperDatasourceListFormatted.get(index).put(nombreVariable,
                                            valorParametro.toString());
                                }
                                // En caso de imagenes variables debe validarse si son en formato tiff para el caso especial
                                if (tipoVariable == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
                                    jasperDatasourceListFormatted.get(index).put(nombreVariable,
                                            ImagenUtil.generateImageParameters(
                                                    jasperDatasourceListFormatted.get(index).get(nombreVariable)
                                                            .toString(),
                                                    nombreVariable, variable.attr(VALOR_DEFECTO), pathImagenes));
                                }
                            }
                            // Si es de tipo numero puede ser por letras o numeros
                            if (ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO == tipoVariable) {
                                try {
                                    String presentacion = variable.attr(PRESENTACION_VARIABLE);
                                    String formaPresentacion = variable.attr(FORMATO_PRESENTACION);
                                    if (presentacion.equals(ConstantesPlantillas.PRESENTACION_COMO_LETRAS)) {
                                        String valor = NumeroUtil.convertNumberToLetter(jasperDatasourceListFormatted
                                                .get(index).get(nombreVariable).toString());
                                        // Valida el formato de la presentacion(Mayus/Minus)
                                        if (formaPresentacion.equals(ConstantesPlantillas.MINUSCULA)) {
                                            valor = valor.toLowerCase();
                                        }
                                        jasperDatasourceListFormatted.get(index).put(nombreVariable, valor);
                                    } else {
                                        // Verifica el formato de un numero en caso que sea negativo
                                        BigDecimal valorParametroNumero = new BigDecimal(valorParametro.toString());
                                        jasperDatasourceListFormatted.get(index)
                                                .put(nombreVariable, formatoNumeroNegativo(formatoNumerosNegativos,
                                                        valorParametroNumero, jasperDatasourceListFormatted.get(index)
                                                                .get(nombreVariable)));
                                    }
                                } catch (NumberFormatException e) {
                                    LOGGER.info("Valor de la variable " + nombreVariable + " no numerico: "
                                            + valorParametro);
                                }
                            }
                        }
                    } else {
                        // Revisamos si es una variable que esta repetida en la plantilla
                        if (numero != null && !numero.isEmpty()) {
                            nombreVariable = nombreVariable + "_" + numero;
                            renombrado = true;
                        }
                        if (!jasperDatasourceListDefecto.get(index).containsKey(nombreVariable)) {
                            String valorParametro = variable.attr(VALOR_DEFECTO);
                            jasperDatasourceListDefecto.get(index).put(nombreVariable, valorParametro);
                            if (!variable.attr(VALOR_DEFECTO_FECHA).isEmpty()
                                    && ConstantesPlantillas.ID_TIPO_VARIABLE_FECHA == tipoVariable) {
                                jasperDatasourceListDefecto.get(index).put(nombreVariable,
                                        variable.attr(VALOR_DEFECTO_FECHA));
                            } else {
                                // En caso de imagenes variables debe validarse si son en formato tiff para el caso especial
                                if (tipoVariable == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
                                    jasperDatasourceListDefecto.get(index).put(nombreVariable,
                                            ImagenUtil.generateImageParameters(valorParametro, nombreVariable,
                                                    valorParametro, pathImagenes));
                                } else if (ConstantesPlantillas.ID_TIPO_VARIABLE_NUMERO == tipoVariable) {
                                    // Si es de tipo numero puede ser por letras o numeros
                                    String presentacion = variable.attr(PRESENTACION_VARIABLE);
                                    String formaPresentacion = variable.attr(FORMATO_PRESENTACION);
                                    if (presentacion.equals(ConstantesPlantillas.PRESENTACION_COMO_LETRAS)) {
                                        String valor = NumeroUtil.convertNumberToLetter(valorParametro);
                                        // Valida el formato de la presentacion(Mayus/Minus)
                                        if (formaPresentacion.equals(ConstantesPlantillas.MINUSCULA)) {
                                            valor = valor.toLowerCase();
                                        }
                                        jasperDatasourceListDefecto.get(index).put(nombreVariable, valor);
                                    } else {
                                        try {
                                            if (myFormatter != null) {
                                                if (myFormatter instanceof DecimalFormat) {
                                                    jasperDatasourceListDefecto.get(index).put(nombreVariable,
                                                            myFormatter.format(new BigDecimal(valorParametro)));
                                                }
                                            }
                                            // Verifica el formato de un numero en caso que sea negativo
                                            BigDecimal valorParametroNumero = new BigDecimal(valorParametro.toString());
                                            jasperDatasourceListDefecto.get(index).put(nombreVariable,
                                                    formatoNumeroNegativo(formatoNumerosNegativos, valorParametroNumero,
                                                            jasperDatasourceListDefecto.get(index)
                                                                    .get(nombreVariable)));
                                        } catch (IllegalArgumentException e) {
                                            LOGGER.error(
                                                    "Error al dar formato a los parametros por defecto del reporte: "
                                                            + e.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    index++;
                }
                // Verifica si es una variable con varias instancias dentro de la plantilla
                if (numero != null && !numero.isEmpty() && !renombrado) {
                    nombreVariable = nombreVariable + "_" + numero;
                }
                // Verifica si ya existe el parametro
                if (!fields.contains(nombreVariable)) {
                    fields.add(nombreVariable);
                }
            }

            // Adiciona las variables con valor por defecto
            // Recorremos el mapa de parametros para identificar si los valores fueron enviados
            int index = 0;
            for (Map<String, Object> jasperParameter : jasperDatasourceList) {
                if (jasperDatasourceListDefecto.size() > index) {
                    jasperParameter.putAll(jasperDatasourceListDefecto.get(index));
                }
                if (jasperDatasourceListFormatted.size() > index) {
                    jasperParameter.putAll(jasperDatasourceListFormatted.get(index));
                }
                index++;
            }
        }
        return jasperDatasourceList;
    }

    /**
     * Asigna el valor con el formato negativo si es numerico
     * 
     * @param formatoNumerosNegativos
     * @param valorParametroNumero
     * @param valorParametro
     * @return Valor en String de valor formateado
     */
    private static String formatoNumeroNegativo(int formatoNumerosNegativos, BigDecimal valorParametroNumero,
            Object valorParametro) {
        if (valorParametroNumero.doubleValue() < 0) {
            // Si el valor es negativo se evalua el formato de numero negativo seleccionado
            if (formatoNumerosNegativos == ConstantesPlantillas.FORMATO_NEGATIVOS_COLOR_ROJO) {
                return "<span style='color:red;'>" + valorParametro.toString().replaceAll("-", "") + "</span>";
            } else if (formatoNumerosNegativos == ConstantesPlantillas.FORMATO_NEGATIVOS_COLOR_ROJO_PARENTESIS) {
                return "<span style='color:red;'>(" + valorParametro.toString().replaceAll("-", "") + ")</span>";
            } else if (formatoNumerosNegativos == ConstantesPlantillas.FORMATO_NEGATIVOS_PARENTESIS) {
                return "(" + valorParametro.toString().replaceAll("-", "") + ")";
            }
        }
        return valorParametro.toString();

    }

    /**
     * Genera un campo para ingresarlo en el documento a partir de una variable
     * 
     * @param name
     *            Nombre del campoque se va agenerar
     * @param tipoVariable
     *            Tipo de la variable
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private void generateField(String name, int tipoVariable) throws JRException {
        LOGGER.debug("JasperUtil::generateField");
        if (!jasperDesign.getFieldsMap().containsKey(name)) {
            JRDesignField field = new JRDesignField();
            field.setName(name);
            field.setValueClass(java.lang.String.class);
            jasperDesign.addField(field);
        }

    }

    /**
     * Genera los parametros que se ingresaran al generar el documento
     * 
     * @param workArea
     *            Elemento html de la plantilla
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private void generateParameters(Element workArea) throws JRException {
        LOGGER.debug("JasperUtil::generateParameters");

        // Expresion del parametro
        JRDesignExpression expressionSub = new JRDesignExpression();
        expressionSub.setText("\"\"");
        // Parametro de la ubicacion de los subreportes
        JRDesignParameter parameterSub = new JRDesignParameter();
        parameterSub.setName(SUBREPORT_DIR_KEY);
        parameterSub.setValueClass(java.lang.String.class);
        parameterSub.setDefaultValueExpression(expressionSub);
        jasperDesign.addParameter(parameterSub);

        // Expresion del parametro
        expressionSub = new JRDesignExpression();
        expressionSub.setText("\"\"");
        // Parametro de la ubicacion de los subreportes
        JRDesignParameter parameterImage = new JRDesignParameter();
        parameterImage.setName(IMAGE_DIR_KEY);
        parameterImage.setValueClass(java.lang.String.class);
        parameterImage.setDefaultValueExpression(expressionSub);
        jasperDesign.addParameter(parameterImage);

        // Expresion del parametro
        expressionSub = new JRDesignExpression();
        expressionSub.setText("false");
        // Parametro indica si es vista preliminar
        JRDesignParameter parameterPreview = new JRDesignParameter();
        parameterPreview.setName(PREVIEW_KEY);
        parameterPreview.setValueClass(java.lang.Boolean.class);
        parameterPreview.setDefaultValueExpression(expressionSub);
        jasperDesign.addParameter(parameterPreview);

    }

    /**
     * Genera los detalles de acuerdo al tamano maximo de la pagina
     * 
     * @param detailElement
     *            Seccion html del cuerpo de la plantilla
     * @return Lista de elementos de seccion(banda) de detalle utilizado por JasperReports
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private List<JRDesignBand> generateDetails(Element detailElement) throws JRException {
        LOGGER.debug("JasperUtil::generateDetails");
        List<JRDesignBand> details = new ArrayList<JRDesignBand>();
        // Tamanio maximo de cada detalle calculado
        int maxHeight = jasperDesign.getPageHeight() - (jasperDesign.getBottomMargin() + jasperDesign.getTopMargin());
        if (jasperDesign.getPageHeader() != null) {
            maxHeight -= jasperDesign.getPageHeader().getHeight();
        }
        if (jasperDesign.getPageFooter() != null) {
            maxHeight -= jasperDesign.getPageFooter().getHeight();
        }
        // Maximo alto sin escala de jasper
        float maxHeightEscala = maxHeight / SCALA;

        // Convierte las filas de la tabla en items que iran en diferente seccion cada uno
        Elements tables = detailElement.getElementsByTag(TABLE_TAG);
        for (Element ckItem : tables) {
            // Tag que representa el titulo de la tabla
            Elements captions = ckItem.getElementsByTag(CAPTION_TAG);
            for (Element caption : captions) {
                caption.addClass(CKITEM_CLASS);
            }
            // REcorre las filas de la tabla
            Elements rows = ckItem.getElementsByTag(TR_TAG);
            for (Element row : rows) {
                row.attr(BORDER_ATTR, ckItem.attr(BORDER_ATTR));
                row.attr(CELLPADDING_ATTR, ckItem.attr(CELLPADDING_ATTR));
                row.attr(CELLSPACING_ATTR, ckItem.attr(CELLSPACING_ATTR));
                row.addClass(CKITEM_CLASS);
            }
            ckItem.removeClass(CKITEM_CLASS);
        }

        Elements ckItems = detailElement.getElementsByClass(CKITEM_CLASS);
        for (Element ckItem : ckItems) {
            Element detail = new Element(Tag.valueOf("div"), "");
            // Float y = Float.valueOf(ckItem.attr(Y));
            Float altoCkItem = Float.valueOf(ckItem.attr(ALTO_ITEM)) * SCALA;

            // Ajustamos el valor del alto de la seccion
            detail.attr(ALTO, String.valueOf(maxHeightEscala));
            // Se ajusta a la nueva posicion relativa a la subseccion
            ckItem.attr(Y, ZERO);
            if (altoCkItem > maxHeight) {
                ckItem.attr(ALTO_ITEM, String.valueOf(maxHeightEscala));
            } else {
                detail.attr(ALTO, ckItem.attr(ALTO_ITEM));
            }
            // TODO: Verificar si funciona en todos los casos
            Elements specialItems = ckItem.getElementsByClass(SIGN_CLASS);
            specialItems.addAll(ckItem.getElementsByClass(GROUP_CLASS));
            specialItems.addAll(ckItem.getElementsByClass(BREAK_CLASS));
            if (specialItems.isEmpty()) {
                detail.attr(ALTO, (DEFAULT_HEIGHT + 1) + "");
                ckItem.attr(ALTO_ITEM, DEFAULT_HEIGHT + "");
            }

            // Obtiene el alto con el que quedo la seccion de detalle
            Float altoDetalle = Float.valueOf(detail.attr(ALTO));

            Elements items = ckItem.getElementsByClass(ITEM_CLASS);
            // items.addAll(ckItem.getElementsByTag(TD_TAG));
            // items.addAll(ckItem.getElementsByTag(TH_TAG));
            // items.addAll(ckItem.getElementsByTag(CAPTION_TAG));
            for (Element item : items) {
                // vERIFICAMOS QUE SEA UNA IMAGEN
                if (item.attr(TIPO).equals(ITEM_IMAGEN)) {
                    Float itemY = Float.valueOf(item.attr(Y));
                    Float altoItem = Float.valueOf(item.attr(ALTO_ITEM));
                    float finalItem = itemY + altoItem;
                    // Se ajusta el tamano del item al de la seccion
                    if (finalItem > altoDetalle && finalItem >= maxHeightEscala) {
                        item.attr(ALTO_ITEM, String.valueOf(maxHeightEscala - itemY - 25));
                        // Ajustamos el valor del alto de la seccion
                        detail.attr(ALTO, String.valueOf(maxHeightEscala));
                    } else if (finalItem > altoDetalle) {
                        // Ajustamos el valor del alto de la seccion
                        detail.attr(ALTO, String.valueOf(finalItem));
                    }
                    // Adiciona el item a la seccion de detalle
                    detail.appendChild(item.clone());
                }
            }
            for (Element item : items) {
                // vERIFICAMOS QUE SEA UNA IMAGEN
                if (item.attr(TIPO).equals(ITEM_IMAGEN) && !ckItem.text().isEmpty()) {
                    // Reemplaza el item de imagen por espacios en blanco
                    StringBuilder spaces = new StringBuilder();
                    for (int i = 0; i < Double.valueOf(item.attr(ANCHO_ITEM)) / SPACE_PIXEL; i++) {
                        spaces.append("&nbsp;");
                    }

                    Element itemSpace = new Element(Tag.valueOf("span"), "");
                    itemSpace.html(spaces.toString());
                    item.replaceWith(itemSpace);
                }
            }
            // quita los items del ck item
            ckItem.getElementsByClass(ITEM_CLASS).remove();
            // Quita imagenes no utilizadas
            ckItem.getElementsByClass(JasperUtil.HANDLER_CLASS).remove();
            // Adiciona el ckitem a la seccion de detalle quitando los items
            detail.appendChild(ckItem);

            // Adiciona la banda como detalle
            JRDesignBand band = getJRBand(detail, true);
            if (band != null) {
                details.add(band);
            }
        }
        return details;
    }

    /**
     * Genera la seccion jasper que se adiciona al reporte
     * 
     * @param section
     *            Seccion html de la plantilla
     * @return Elemento de seccion(banda) utilizado por JasperReports
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private JRDesignBand getJRBand(Element section, boolean isDetail) throws JRException {
        LOGGER.debug("JasperUtil::getJRBand");
        JRDesignBand band = new JRDesignBand();
        // Se convierten los valores segun la escala
        Float alto = Float.valueOf(section.attr(ALTO)) * SCALA;
        band.setHeight(alto.intValue());

        // Si viene el numero total de pagina se debe ampliar el tamano del la seccion de pie de pagina
        if (!section.getElementsByClass(NUMTOTPAG_CLASS).isEmpty()
                || !section.getElementsByClass(NUMPAG_CLASS).isEmpty()) {
            // Se agreg� por problema con jasper en tiempo de evaluacion y strech
            // http://stackoverflow.com/a/12284040
            band.setHeight(band.getHeight() + 6);
        }

        // Adiciona los items a la seccion
        addItemsToBand(section, band, isDetail);

        // SI no tiene ningun elemento no se crea la seccion
        if (band.getElements().length == 0) {
            band = null;
        }
        return band;
    }

    /**
     * Adiciona los item de la seccion html a la seccion en jasper
     * 
     * @param section
     *            Seccion html de la plantilla
     * @param band
     *            Seccion del reporte en jasper
     * @param isDetail
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private void addItemsToBand(Element section, JRDesignBand band, boolean isDetail) throws JRException {
        LOGGER.debug("JasperUtil::addItemsToBand");

        Elements items = section.getElementsByClass(CKITEM_CLASS);
        for (Element item : items) {
            if (!item.attr(TIPO).equals(GROUP_CLASS) && !item.tagName().equals(TR_TAG)
                    && !item.tagName().equals(TABLE_TAG) && !item.hasClass("ckeditorItemFirma")) {
                if (canAddToBand(item, band, isDetail)) {
                    JRDesignTextField textField = getJRDesignTextField(item);
                    band.addElement(textField);
                }
            } else if (item.tagName().equals(TR_TAG) || item.tagName().equals(TABLE_TAG)) {

                if (!isDetail) {
                    // Tag que representa el titulo de la tabla
                    Elements captions = section.getElementsByTag("caption");
                    for (Element caption : captions) {
                        JRDesignTextField textField = getJRDesignTextField(caption);
                        band.addElement(textField);
                    }
                }

                // Color negro para el borde
                Color c = new Color(-16777216);

                // Determinamos si la tabla tiene borde
                boolean hasBorder = item.hasAttr(BORDER_ATTR) && !item.attr(BORDER_ATTR).equals(ZERO);

                Elements columns = item.getElementsByTag(TH_TAG);
                columns.addAll(item.getElementsByTag(TD_TAG));
                for (Element column : columns) {
                    if (canAddToBand(column, band, isDetail)) {
                        if (isDetail) {
                            column.attr(Y, item.attr(Y));
                        }

                        // Quita otros items que hay en la fila para agregarlos posteriormente
                        Element columnTemp = column.clone();
                        Elements removeItems = columnTemp.getElementsByClass(SIGN_CLASS);
                        removeItems.addAll(columnTemp.getElementsByClass(GROUP_CLASS));
                        removeItems.addAll(columnTemp.getElementsByClass(BREAK_CLASS));
                        removeItems.addAll(columnTemp.getElementsByClass(CKITEM_CLASS));
                        removeItems.remove();

                        JRDesignTextField textField = getJRDesignTextField(columnTemp);
                        if (isDetail) {
                            textField.setStretchType(StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT);
                            // Por defecto la alineacion del texto será justificado
                            if (column.tagName().equals(TD_TAG)) {
                                textField.setHorizontalAlignment(HorizontalAlignEnum.JUSTIFIED);
                            }
                        }

                        // Si tiene borde le colocamos a cada campo el borde
                        if (hasBorder) {
                            // Asigna el grosor del borde
                            float bw = .5f * Float.valueOf(item.attr(BORDER_ATTR));
                            // Las lineas del borde
                            JRLineBox box = textField.getLineBox();
                            box.getLeftPen().setLineWidth(bw);
                            box.getLeftPen().setLineColor(c);
                            box.getRightPen().setLineWidth(bw);
                            box.getRightPen().setLineColor(c);
                            box.getBottomPen().setLineWidth(bw);
                            box.getBottomPen().setLineColor(c);
                            box.getTopPen().setLineWidth(bw);
                            box.getTopPen().setLineColor(c);
                        }
                        // Asigna el padding de cada columna
                        Float padding = 1
                                * (item.hasAttr(CELLPADDING_ATTR) ? Float.valueOf(item.attr(CELLPADDING_ATTR)) : 1);
                        // cellpadding
                        JRParagraph paragraph = textField.getParagraph();
                        paragraph.setLeftIndent(padding.intValue());
                        paragraph.setRightIndent(padding.intValue());
                        // paragraph.setSpacingAfter(padding.intValue());
                        // paragraph.setSpacingBefore(padding.intValue());

                        // textField.setHeight(textField.getHeight() + padding.intValue());
                        textField.setWidth(textField.getWidth() + padding.intValue());
                        band.addElement(textField);
                        // Adiciona items en caso de tener algo en la columna de la tabla
                        // addItemsToBand(column, band, isDetail);
                    }
                }
            }
        }

        // Agrega las firmas
        Elements signs = section.getElementsByClass(SIGN_CLASS);
        for (Element sign : signs) {
            if (!sign.hasClass(DONE_CLASS)) {
                for (Element item : sign.children()) {
                    if (item.tagName().equals("img")) {
                        if (jasperUtilDTO.isPreliminar()) {
                            // Pone imagen por defecto
                            jasperUtilDTO.getJasperDatasourceList().get(0).put(item.attr(FIELD_ATTR),
                                    jasperUtilDTO.getPathImagenes() + SIGN_IMAGE);
                            // Crea la imagen temporalmente
                            if (!Files.exists(Paths.get(jasperUtilDTO.getPathImagenes() + SIGN_IMAGE))) {
                                // Decodifica la imagen de firma por defecto
                                File archivoImagen = new File(jasperUtilDTO.getPathImagenes() + SIGN_IMAGE);
                                try {
                                    // Guarda la imagen temporalmente
                                    FileUtils.copyURLToFile(new URL(jasperUtilDTO.getContextPath() + item.attr("src")),
                                            archivoImagen);
                                } catch (IOException e) {
                                    LOGGER.error("No pudo obtener imagen de la url : " + item.attr("src") + " :: "
                                            + e.getMessage(), e);
                                }
                            }
                        }
                        // Creamos el campo para el reporte verificando que se encuentre el atributo en el html
                        if (!item.attr(FIELD_ATTR).isEmpty()) {
                            generateField(item.attr(FIELD_ATTR), ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL);
                        }
                        // Se genera la imagen de la firma
                        JRDesignImage image = getJRDesignImage(item);
                        image.setScaleImage(ScaleImageEnum.RETAIN_SHAPE);
                        band.addElement(image);
                    } else if (item.tagName().equals(HR)) {
                        // Se genera la linea de la firma
                        JRDesignLine line = getJRDesignLine(item, true);
                        band.addElement(line);
                    } else if (item.tagName().equals(P_TAG)) {
                        // Si es preliminar se coloca un valor por defecto
                        if (jasperUtilDTO.isPreliminar()) {
                            if (!jasperUtilDTO.getJasperDatasourceList().get(0).containsKey(item.attr(FIELD_ATTR))) {
                                jasperUtilDTO.getJasperDatasourceList().get(0).put(item.attr(FIELD_ATTR),
                                        item.attr(FIELD_ATTR));
                            }
                        }
                        if (!item.getElementsByClass("campofirma").isEmpty()) {
                            Element campoFirma = item.getElementsByClass("campofirma").get(0);
                            campoFirma.text("  \" + String.valueOf($F" + campoFirma.text() + " ) + \" ");
                        }
                        // Creamos el campo para el reporte verificando que se encuentre el atributo en el html
                        if (!item.attr(FIELD_ATTR).isEmpty()) {
                            generateField(item.attr(FIELD_ATTR), ConstantesPlantillas.ID_TIPO_VARIABLE_TEXTO);
                        }
                        // Se genera el textfield de la firma
                        JRDesignTextField textField = getJRDesignTextField(item);
                        band.addElement(textField);
                    }
                }
            }
            sign.addClass(DONE_CLASS);
        }

        // Agrega los subreportes
        Elements groups = section.getElementsByClass(GROUP_CLASS);
        for (Element item : groups) {
            if (!item.hasClass(DONE_CLASS)) {
                JRDesignSubreport jRDesignSubreport = getJRDesignSubreport(item.parent());
                band.addElement(jRDesignSubreport);
            }
            item.addClass(DONE_CLASS);
        }

        // Agrega los BREAKS
        Elements breaks = section.getElementsByClass(BREAK_CLASS);
        for (Element item : breaks) {
            JRBreak jrBreak = getJRBreak(item);
            band.addElement(jrBreak);
        }

        items = section.getElementsByClass(ITEM_CLASS);
        for (Element item : items) {
            if (canAddToBand(item, band, isDetail)) {
                if (item.attr(TIPO).equals(ITEM_IMAGEN)) {
                    int tipoVariable = Integer.valueOf(item.attr(TIPO_VARIABLE));
                    // Gauarda la imagen local en un directorio temporal
                    if ((jasperUtilDTO.isPreliminar())
                            && tipoVariable == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL) {

                        File archivoImagen = new File(jasperUtilDTO.getPathImagenes() + item.attr(ID_VARIABLE) + "_"
                                + item.attr(VALOR_DEFECTO));
                        try {
                            // Guarda la imagen temporalmente
                            FileUtils.copyURLToFile(new URL(jasperUtilDTO.getContextPath() + item.attr("src")),
                                    archivoImagen);
                        } catch (IOException e) {
                            LOGGER.error(
                                    "No pudo obtener imagen de la url : " + item.attr("src") + " :: " + e.getMessage(),
                                    e);
                        }
                    }

                    if (tipoVariable == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE
                            && !item.attr(ID_GRUPO).isEmpty()) {
                        JRDesignSubreport jRDesignSubreport = getJRDesignSubreport(item);
                        band.addElement(jRDesignSubreport);
                    } else {
                        // Genera la imagen
                        JRDesignImage image = getJRDesignImage(item);
                        band.addElement(image);
                        String tipo = item.attr(TIPO_IMAGEN);
                        if (tipo.equals(ConstantesPlantillas.IMAGEN_VARIABLE_FIRMA)) {
                            // Ingresar linea debajo de la imagen
                            Element linea = new Element(Tag.valueOf(HR), "");
                            Float y = Float.valueOf(item.attr(Y)) + Float.valueOf(item.attr(ALTO_ITEM)) + ESPACIO_LINEA;
                            linea.attr(X, item.attr(X));
                            linea.attr(Y, String.valueOf(y));
                            linea.attr(ANCHO_ITEM, item.attr(ANCHO_ITEM));

                            JRDesignLine line = getJRDesignLine(linea, true);
                            band.addElement(line);
                            band.setHeight(band.getHeight() + line.getHeight() + ESPACIO_LINEA);
                        }
                    }
                }
            }
        }
    }

    /**
     * Verifica si se puede adicionar un item a una seccion
     * 
     * @param item
     *            Elemento de la plantilla html
     * @param band
     *            Seccion del reporte donde se insertara el item
     * @return true si se puede adicionar a la seccion, false en caso contrario
     */
    private boolean canAddToBand(Element item, JRDesignBand band, boolean modify) {
        LOGGER.debug("JasperUtil::canAddToBand");
        boolean canAdd = true;

        // Se convierten los valores segun la escala
        Float y = Float.valueOf(item.attr(Y)) * SCALA;
        Float altoItem = Float.valueOf(item.attr(ALTO_ITEM)) * SCALA;

        if (band.getHeight() < (y.intValue() + altoItem.intValue())) {
            canAdd = false;
            if (modify) {
                // Se ajusta tamanio de la columna para que quede se pueda mostrar
                item.attr(ALTO_ITEM, ((band.getHeight() - 1) / SCALA) + "");
                canAdd = true;
            }
        }
        return canAdd;
    }

    /**
     * Genera un subreporte
     * 
     * @param item
     *            Elemento de la plantilla html
     * @return Elemento de subreporte utilizado por JasperReports
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private JRDesignSubreport getJRDesignSubreport(Element item) throws JRException {
        LOGGER.debug("JasperUtil::getJRDesignSubreport");
        // Se convierten los valores segun la escala
        Float x = Float.valueOf(item.attr(X)) * SCALA;
        // Float y = Float.valueOf(item.attr(Y)) * SCALA;
        Float y = 0F;
        Float ancho = Float.valueOf(item.attr(ANCHO_ITEM)) * SCALA;
        Float altoItem = Float.valueOf(item.attr(ALTO_ITEM)) * SCALA;

        JRDesignSubreport jRDesignSubreport = new JRDesignSubreport(jasperDesign);
        jRDesignSubreport.setPrintWhenDetailOverflows(false);
        jRDesignSubreport.setOverflowType(OverflowType.STRETCH);
        // jRDesignSubreport.setRemoveLineWhenBlank(true);
        jRDesignSubreport.setPrintRepeatedValues(true);
        jRDesignSubreport.setX(x.intValue());
        jRDesignSubreport.setY(y.intValue());
        jRDesignSubreport.setMode(ModeEnum.TRANSPARENT);
        jRDesignSubreport.setWidth(ancho.intValue());
        jRDesignSubreport.setHeight(altoItem.intValue());
        jRDesignSubreport.setUsingCache(false);

        // Expresion del parametro
        JRDesignExpression expressionSub = new JRDesignExpression();
        expressionSub.setText("$P{SUBREPORT_DIR_KEY}");
        // Parametro de la ubicacion de los subreportes
        JRDesignSubreportParameter parameterSub = new JRDesignSubreportParameter();
        parameterSub.setName(SUBREPORT_DIR_KEY);
        parameterSub.setExpression(expressionSub);
        jRDesignSubreport.addParameter(parameterSub);

        // Expresion del parametro
        JRDesignExpression expressionImage = new JRDesignExpression();
        expressionImage.setText("$P{IMAGE_DIR_KEY}");
        // Parametro de la ubicacion de las imagenes
        JRDesignSubreportParameter parameterImage = new JRDesignSubreportParameter();
        parameterImage.setName(IMAGE_DIR_KEY);
        parameterImage.setExpression(expressionImage);
        jRDesignSubreport.addParameter(parameterImage);

        // Expresion del subreporte
        JRDesignExpression expression = new JRDesignExpression();
        expression.setText(
                "$P{SUBREPORT_DIR_KEY} + \"" + jasperDesign.getName() + "_" + item.attr(ID_GRUPO) + ".jasper\"");
        jRDesignSubreport.setExpression(expression);

        // Expresion de la conexion subreporte
        JRDesignExpression dataSrcExpression = new JRDesignExpression();
        dataSrcExpression.setText(
                "new net.sf.jasperreports.engine.data.JRMapCollectionDataSource($F{" + item.attr(ID_GRUPO) + "})");
        jRDesignSubreport.setDataSourceExpression(dataSrcExpression);

        if (!jasperDesign.getFieldsMap().containsKey(item.attr(ID_GRUPO))) {
            // Genera campo del Datasource
            JRDesignField field = new JRDesignField();
            field.setName(item.attr(ID_GRUPO));
            field.setValueClass(java.util.List.class);
            jasperDesign.addField(field);
        }

        return jRDesignSubreport;
    }

    /**
     * Genera un break
     * 
     * @param item
     *            Elemento de la plantilla html
     * @return Elemento de salto de linea utilizado por JasperReports
     */
    private JRBreak getJRBreak(Element item) {
        LOGGER.debug("JasperUtil::getJRBreak");
        // Se convierten los valores segun la escala
        Float x = Float.valueOf(item.attr(X)) * SCALA;
        Float ancho = Float.valueOf(item.attr(ANCHO_ITEM)) * SCALA;
        JRBreak jrBreak = new JRDesignBreak();
        jrBreak.setPrintWhenDetailOverflows(false);
        jrBreak.setX(x.intValue());
        jrBreak.setMode(ModeEnum.TRANSPARENT);
        jrBreak.setType(BreakTypeEnum.PAGE);
        jrBreak.setWidth(ancho.intValue());
        return jrBreak;
    }

    /**
     * Guarda archivo en un directorio temporal
     * 
     * @param content
     *            Contenido del archivo
     * @param path
     *            Path temporal del archivo
     * @return Path del archivo guardado
     */
    public static String saveTempFile(byte[] content, String path) {
        LOGGER.debug("JasperUtil::saveTempFile");
        // Verificamos que sea una imagen para guardarla temporalmente
        Path tempFilePath = Paths.get(path);
        try {
            Files.write(tempFilePath, content);
            return tempFilePath.toString();
        } catch (IOException e) {
            LOGGER.error("Error al crear temporal de la firma para generar el documento", e);
        }
        return "";
    }

    /**
     * Genera una linea
     * 
     * @param item
     *            Elemento de la plantilla html
     * @return Elemento de linea utilizado por JasperReports
     */
    private JRDesignLine getJRDesignLine(Element item, boolean horizontal) {
        LOGGER.debug("JasperUtil::getJRDesignLine");

        // Se convierten los valores segun la escala
        Float x = Float.valueOf(item.attr(X)) * SCALA;
        Float y = Float.valueOf(item.attr(Y)) * SCALA;

        Float alto = 0F;
        Float ancho = 0F;
        if (horizontal) {
            ancho = Float.valueOf(item.attr(ANCHO_ITEM)) * SCALA;
        } else {
            alto = Float.valueOf(item.attr(ALTO_ITEM)) * SCALA;
        }

        // Imagen en reporte generada
        JRDesignLine line = new JRDesignLine();
        line.setPrintWhenDetailOverflows(false);
        line.setFill(FillEnum.SOLID);
        line.setForecolor(Color.BLACK);
        line.setBackcolor(Color.WHITE);
        line.setX(x.intValue());
        line.setY(y.intValue());
        line.setWidth(ancho.intValue());
        line.setHeight(alto.intValue());

        return line;
    }

    /**
     * Genera una imagen
     * 
     * @param item
     *            Elemento de la plantilla html
     * @return Elemento de imagen utilizado por JasperReports
     * @throws JRException
     *             Excepcion enviada por JasperReports
     */
    private JRDesignImage getJRDesignImage(Element item) throws JRException {
        LOGGER.debug("JasperUtil::getJRDesignImage");

        // Se convierten los valores segun la escala
        Float x = Float.valueOf(item.attr(X)) * SCALA;
        Float y = Float.valueOf(item.attr(Y)) * SCALA;
        Float ancho = Float.valueOf(item.attr(ANCHO_ITEM)) * SCALA;
        Float altoItem = Float.valueOf(item.attr(ALTO_ITEM)) * SCALA;
        // Expresion del imagen
        JRDesignExpression expression = new JRDesignExpression();

        if (!item.attr("srcField").isEmpty()) {
            expression.setText(item.attr("srcField"));
        } else {
            int tipoVariable = Integer.valueOf(item.attr(TIPO_VARIABLE));
            if (!item.attr(VALOR_DEFECTO).isEmpty()
                    && tipoVariable == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_LOCAL) {
                expression.setText(
                        "$P{IMAGE_DIR_KEY} + \"" + item.attr(ID_VARIABLE) + "_" + item.attr(VALOR_DEFECTO) + "\"");
            } else if (!item.attr(NOMBRE_VARIABLE).isEmpty()
                    && tipoVariable == ConstantesPlantillas.ID_TIPO_VARIABLE_IMAGEN_VARIABLE) {
                expression.setText("$F{" + item.attr(NOMBRE_VARIABLE) + "}");
            }
        }

        // Imagen en reporte generada
        JRDesignImage image = new JRDesignImage(this.jasperDesign);
        image.setPrintWhenDetailOverflows(false);
        // image.setStretchType(StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT);
        image.setStretchType(StretchTypeEnum.NO_STRETCH);
        ;
        image.setX(x.intValue());
        image.setY(y.intValue());
        image.setWidth(ancho.intValue());
        image.setHeight(altoItem.intValue());
        image.setScaleImage(ScaleImageEnum.FILL_FRAME);
        image.setExpression(expression);
        image.setLazy(true);
        image.setUsingCache(false);
        image.setOnErrorType(OnErrorTypeEnum.BLANK);

        return image;
    }

    /**
     * Genera un elemento dinamico que se adiciona a la seccion
     * 
     * @param item
     *            Elemento de la plantilla html
     * @return Elemento de texto dinamico utilizado por JasperReports
     * @throws JRException
     *             Error en jasper
     */
    private JRDesignTextField getJRDesignTextField(Element item) throws JRException {
        LOGGER.debug("JasperUtil::getJRDesignTextField");
        JRDesignTextField textField = (JRDesignTextField) getJRDesignTextElement(item, new JRDesignTextField());
        textField.setStretchWithOverflow(true);
        textField.setPrintWhenDetailOverflows(false);
        textField.setBlankWhenNull(true);
        // textField.setStretchType(StretchTypeEnum.RELATIVE_TO_TALLEST_OBJECT);
        textField.setMarkup("html");
        // Expresion del textField
        JRDesignExpression expression = new JRDesignExpression();

        Elements variables = item.getElementsByClass(ITEM_VARIABLE);
        for (Element variable : variables) {
            String texto = variable.text();
            // Verificamos que no sea una imagen
            if (!variable.attr(TIPO).equals(ITEM_IMAGEN)) {
                variable.text("  \" + String.valueOf(" + texto + " ) + \" ");
            }
        }
        // Si viene el numero total de pagina se debe reemplazar por la jasperVariable adecuada
        Elements numerosTotalPagina = item.getElementsByClass(NUMTOTPAG_CLASS);
        for (Element numeroTotalPagina : numerosTotalPagina) {
            numeroTotalPagina.parent().replaceWith(new TextNode("\" + String.valueOf(" + PAGE_NUMBER + ") + \"", ""));
        }

        // Si viene el numero de pagina se debe reemplazar por la jasperVariable adecuada
        Elements numerosPagina = item.getElementsByClass(NUMPAG_CLASS);
        for (Element numeroPagina : numerosPagina) {
            numeroPagina.parent()
                    .replaceWith(new TextNode("\" + String.valueOf($V{" + CURRENT_PAGE_NUMBER + "}) + \"", ""));
        }

        if (!numerosTotalPagina.isEmpty() || !numerosPagina.isEmpty()) {
            if (!jasperDesign.getVariablesMap().containsKey(CURRENT_PAGE_NUMBER)) {
                // Expresion de la variable de numero de pagina
                JRDesignExpression pageNumberExpression = new JRDesignExpression();
                pageNumberExpression.setText("( $V{PAGE_COUNT} == 1 ? $V{PAGE_COUNT} : " + PAGE_NUMBER + " + 1 )");
                JRDesignVariable pageNumberVariable = new JRDesignVariable();
                pageNumberVariable.setValueClass(java.lang.Integer.class);
                pageNumberVariable.setExpression(pageNumberExpression);
                pageNumberVariable.setResetType(ResetTypeEnum.NONE);
                pageNumberVariable.setName(CURRENT_PAGE_NUMBER);
                jasperDesign.addVariable(pageNumberVariable);
            }
            // Se agreg� por problema con jasper en tiempo de evaluacion y strech
            // http://stackoverflow.com/a/12284040
            textField.setHeight(textField.getHeight() + 6);
            // Si viene el numero total depagina se debe cambiar el tiempo de evaluacion
            textField.setEvaluationTime(EvaluationTimeEnum.AUTO);
        }

        // Reemplaza los valores para que tome correctamente el html
        @SuppressWarnings("resource")
        Scanner contenido = new Scanner(item.outerHtml());
        StringBuilder itemHtml = new StringBuilder();
        while (contenido.hasNextLine()) {
            itemHtml.append(contenido.nextLine()
                    // .replaceAll(patternVariable, "$2")
                    // .replaceAll(patternNumeroPagina, " &quot; + \\$V{PAGE_NUMBER} + &quot; ")
                    .replaceAll("\\\"", "'").replaceAll("strong>", "b>").replaceAll("em>", "i>")
                    .replaceAll("s>", "strike>").replaceAll("&quot; \\+", "\" \\+").replaceAll("\\+ &quot;", "\\+ \""));
        }
        expression.setText("\"" + itemHtml + " \"");
        textField.setExpression(expression);

        return textField;
    }

    /**
     * Genera un elemento generico de texto con las propiedades basicas
     * 
     * @param item
     *            Elemento de la plantilla html
     * @return Elemento de texto utilizado por JasperReports
     */
    private JRDesignTextElement getJRDesignTextElement(Element item, JRDesignTextElement textElement) {
        LOGGER.debug("JasperUtil::getJRDesignTextElement");
        // Se convierten los valores segun la escala
        Float x = Float.valueOf(item.attr(X)) * SCALA;
        Float y = Float.valueOf(item.attr(Y)) * SCALA;
        Float ancho = Float.valueOf(item.attr(ANCHO_ITEM)) * SCALA;
        Float alto = Float.valueOf(item.attr(ALTO_ITEM)) * SCALA;
        HorizontalAlignEnum alineacion = HorizontalAlignEnum.LEFT;
        VerticalAlignEnum alineacionVertical = VerticalAlignEnum.TOP;

        textElement.setX(x.intValue());
        textElement.setY(y.intValue());
        textElement.setWidth(ancho.intValue());
        textElement.setHeight(alto.intValue());
        // textElement.setForecolor(Color.white);
        // textElement.setBackcolor(new Color(0x33, 0x33, 0x33));
        textElement.setForecolor(Color.BLACK);
        textElement.setBackcolor(Color.WHITE);

        textElement.setMode(ModeEnum.TRANSPARENT);
        // Tipo de letra por defecto arial
        textElement.setStyle(arialStyle);

        // Verificar si es caption o si es un th para centrarlo
        if (item.tagName().equals(TH_TAG) || item.tagName().equals(CAPTION_TAG)) {
            alineacionVertical = VerticalAlignEnum.MIDDLE;
            alineacion = HorizontalAlignEnum.CENTER;
            textElement.setStyle(arialBoldStyle);
            // Si es header le ponemos color de fondo
            if (item.tagName().equals(TH_TAG)) {
                textElement.setBackcolor(Color.LIGHT_GRAY);
            }
        } else if (item.tagName().equals(TD_TAG)) {
            alineacionVertical = VerticalAlignEnum.TOP;
            // textElement.setY(y.intValue() - 5);
        }

        // Espacio de interlineado
        JRParagraph paragraph = textElement.getParagraph();
        paragraph.setLineSpacing(LineSpacingEnum.SINGLE);

        // Obtiene los estilos del item para ponerlos en el reporte
        String styleAttr = item.attr("style");
        String[] styles = styleAttr.split(";");
        for (String estilo : styles) {
            String[] styleValue = estilo.split(":");
            if (styleValue[0].trim().equalsIgnoreCase("text-align")) {
                switch (styleValue[1].trim().toLowerCase()) {
                case "center":
                    alineacion = HorizontalAlignEnum.CENTER;
                    break;
                case "justify":
                    alineacion = HorizontalAlignEnum.JUSTIFIED;
                    break;
                case "right":
                    alineacion = HorizontalAlignEnum.RIGHT;
                    break;
                default:
                    alineacion = HorizontalAlignEnum.LEFT;
                }
            } else if (styleValue[0].trim().equalsIgnoreCase("vertical-align")) {
                String value = styleValue[1].trim().toLowerCase();
                if (value.contains("middle")) {
                    alineacionVertical = VerticalAlignEnum.MIDDLE;
                } else if (value.contains("top")) {
                    alineacionVertical = VerticalAlignEnum.TOP;
                } else if (value.contains("bottom")) {
                    alineacionVertical = VerticalAlignEnum.BOTTOM;
                }
            } else if (styleValue[0].trim().equalsIgnoreCase("margin-left")) {
                Float marginLeft = Float.valueOf(styleValue[1].replaceAll("px", "")) * SCALA;
                textElement.setX(x.intValue() + marginLeft.intValue());
                // JRParagraph paragraph = textElement.getParagraph();
                // paragraph.setLeftIndent(marginLeft.intValue());
            } else if (styleValue[0].trim().equalsIgnoreCase("line-height")) {
                Float lineHeight = 0F;
                if (styleValue[1].indexOf("px") != -1) {
                    lineHeight = Float.valueOf(styleValue[1].replaceAll("px", "")) / 12;
                } else {
                    lineHeight = Float.valueOf(styleValue[1]);
                }
                // Espacio de interlineado
                paragraph = textElement.getParagraph();
                if (lineHeight < 0.75) {
                    paragraph.setLineSpacing(LineSpacingEnum.AT_LEAST);
                } else if (lineHeight < 1.25) {
                    paragraph.setLineSpacing(LineSpacingEnum.SINGLE);
                } else if (lineHeight < 1.75) {
                    paragraph.setLineSpacing(LineSpacingEnum.ONE_AND_HALF);
                } else if (lineHeight < 2.25) {
                    paragraph.setLineSpacing(LineSpacingEnum.DOUBLE);
                } else {
                    paragraph.setLineSpacing(LineSpacingEnum.FIXED);
                    paragraph.setLineSpacingSize((lineHeight * 10) + 0.5f);
                }
            }
        }

        textElement.setHorizontalAlignment(alineacion);
        textElement.setVerticalAlignment(alineacionVertical);

        return textElement;
    }

    /**
     * Inicializa propiedades basicas del reporte
     */
    private void initJasperDesign() throws JRException {
        LOGGER.debug("JasperUtil::initJasperDesign");
        // JasperDesign
        jasperDesign = new JasperDesign();
        // Propiedad para que muestre todos los contenidos
        jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        // Fonts
        arialStyle = new JRDesignStyle();
        arialStyle.setName("Arial");
        arialStyle.setDefault(true);
        arialStyle.setFontName("Arial");
        arialStyle.setFontSize(12f);
        arialStyle.setPdfFontName("Helvetica");
        arialStyle.setPdfEncoding("Cp1252");
        arialStyle.setPdfEmbedded(false);
        jasperDesign.addStyle(arialStyle);

        arialBoldStyle = new JRDesignStyle();
        arialBoldStyle.setName("Arial Bold");
        arialBoldStyle.setFontName("Arial");
        arialBoldStyle.setFontSize(12f);
        arialBoldStyle.setBold(true);
        arialBoldStyle.setPdfFontName("Helvetica");
        arialBoldStyle.setPdfEncoding("Cp1252");
        arialBoldStyle.setPdfEmbedded(false);
        jasperDesign.addStyle(arialBoldStyle);

        normalStyle = new JRDesignStyle();
        normalStyle.setName("Sans_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("DejaVu Sans");
        normalStyle.setFontSize(12f);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        normalStyle.setPdfEmbedded(false);
        jasperDesign.addStyle(normalStyle);

        boldStyle = new JRDesignStyle();
        boldStyle.setName("Sans_Bold");
        boldStyle.setFontName("DejaVu Sans");
        boldStyle.setFontSize(12f);
        boldStyle.setBold(true);
        boldStyle.setPdfFontName("Helvetica-Bold");
        boldStyle.setPdfEncoding("Cp1252");
        boldStyle.setPdfEmbedded(false);
        jasperDesign.addStyle(boldStyle);

        italicStyle = new JRDesignStyle();
        italicStyle.setName("Sans_Italic");
        italicStyle.setFontName("DejaVu Sans");
        italicStyle.setFontSize(12f);
        italicStyle.setItalic(true);
        italicStyle.setPdfFontName("Helvetica-Oblique");
        italicStyle.setPdfEncoding("Cp1252");
        italicStyle.setPdfEmbedded(false);
        jasperDesign.addStyle(italicStyle);
    }

    /**
     * Completa los datos con defecto cuando se genera un documento
     * 
     * @param contenidoJasper
     *            Contenido html de la plantilla
     * @param jasperDatasourceList
     *            Lista de mapas de los valores de las variables enviados
     * @param simboloMoneda
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> completeData(String contenidoJasper,
            List<Map<String, Object>> jasperDatasourceList, String pathImagenes, String simboloMoneda) {
        LOGGER.debug("JasperUtil::");
        // Obtiene documento inicial
        Document doc = Jsoup.parse(contenidoJasper);

        // Obtiene el area de trabajo
        Element workArea = doc.getElementsByClass("workArea").first();

        // Quita los nombres de las secciones para que no afecten la generacion
        workArea.getElementsByClass("bandName").remove();

        if (jasperDatasourceList.isEmpty()) {
            Map<String, Object> jasperDatasourceMap = new HashMap<String, Object>();
            jasperDatasourceList.add(jasperDatasourceMap);
        }
        // Genera los campos por defecto para el documento principal
        jasperDatasourceList = generateDefaultValues(workArea, jasperDatasourceList, pathImagenes, simboloMoneda);

        // Verifica los grupos para generarlos como subreportes independientes
        Elements groups = workArea.getElementsByClass(GROUP_CLASS);
        for (Element group : groups) {
            String contenidoGrupo = group.getElementsByClass("grupo-content").first().attr("html_grupo");
            // Todos los registros de este reporte
            for (Map<String, Object> mapGroup : jasperDatasourceList) {
                // Si no tiene parametros envia los que son por defecto
                if (!mapGroup.containsKey(group.attr("id"))) {
                    mapGroup.put(group.attr("id"), new ArrayList<Map<String, Object>>());
                }
                // Genera los campos para el grupo
                mapGroup.put(group.attr("id"), completeData(contenidoGrupo,
                        (List<Map<String, Object>>) mapGroup.get(group.attr("id")), pathImagenes, simboloMoneda));
            }
            group.getElementsByClass("grupo-content").remove();
        }
        return jasperDatasourceList;
    }

    /**
     * Genera el html necesario para generar una imagen variable como un grupo(subreporte)
     * 
     * @param variable
     *            Elemento de la imagen variable
     * @param workArea
     *            Elemento del area principal del html
     * @return Contenido html utilizado para generar el subreporte
     */
    public static String generateContentImageVariable(Element variable, Element workArea) {
        variable.removeAttr(JasperUtil.ID_GRUPO);
        // Define atributos de la imagen que será utilizada como subreporte
        variable.attr(X, ZERO);
        variable.attr(Y, ZERO);
        String alto = String.valueOf(JasperUtil.convertPixelesToCentimetros(Float.valueOf(variable.attr(ALTO_ITEM))));
        String ancho = String.valueOf(JasperUtil.convertPixelesToCentimetros(Float.valueOf(variable.attr(ANCHO_ITEM))));
        // Crea elemento
        Element ckItem = new Element(Tag.valueOf(P_TAG), "");
        ckItem.addClass(CKITEM_CLASS);
        ckItem.attr(ANCHO_ITEM, ancho);
        ckItem.attr(ALTO_ITEM, alto);
        ckItem.attr(X, ZERO);
        ckItem.attr(Y, ZERO);
        ckItem.html(variable.outerHtml());
        // Elemento de cuerpo
        Element divCuerpo = new Element(Tag.valueOf("div"), "");
        divCuerpo.attr("id", JasperUtil.CUERPO);
        divCuerpo.attr(ALTO, alto);
        divCuerpo.html(ckItem.outerHtml());
        // Obtiene el area de trabajo para enviarla en el mismo formato de los grupos
        workArea.attr(ANCHO, ancho);
        workArea.attr(ALTO, alto);
        workArea.html(divCuerpo.outerHtml());

        return "<html><head><title>Documento</title></head><body>" + workArea.outerHtml() + "</body></html>";
    }

    /**
     * Convierte centimetros a pixeles
     */
    private int convertCentimetrosToPixeles(float centimetros) {
        return Long.valueOf(Math.round(centimetros * JasperUtil.CENTIMETRO)).intValue();
    }

    /**
     * Convierte pixeles a centimetros
     */
    public static int convertPixelesToCentimetros(float centimetros) {
        return Long.valueOf(Math.round(centimetros / JasperUtil.CENTIMETRO)).intValue();
    }

    public String getPathArchivo() {
        return pathArchivo;
    }

    public List<Map<String, Object>> getJasperDatasourceList() {
        return jasperUtilDTO.getJasperDatasourceList();
    }

    public void setJasperUtilDTO(JasperUtilDTO jasperUtilDTO) {
        this.jasperUtilDTO = jasperUtilDTO;
    }

}
