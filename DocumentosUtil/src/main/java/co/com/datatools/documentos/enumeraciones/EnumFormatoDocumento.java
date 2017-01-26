/**
 * 
 */
package co.com.datatools.documentos.enumeraciones;

/**
 * Enumeracion de tipos de archivos permitidos
 * @author julio.pinzon
 *
 */
public enum EnumFormatoDocumento implements Cloneable  {
    CSV(".csv"),
    XLS(".xls"),
    PDF(".pdf"), 
    HTML(".html"), 
    XML(".xml"), 
    DOCX(".docx"), 
    RTF(".rtf"), 
    JASPER(".jasper"), 
    PDF_PREVIEW(".pdf_preview");    
    
    private EnumFormatoDocumento(String extension) {
        this.extension = extension;
    }

    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
