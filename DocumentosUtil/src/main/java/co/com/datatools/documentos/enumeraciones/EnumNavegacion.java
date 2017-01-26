package co.com.datatools.documentos.enumeraciones;

public enum EnumNavegacion {

    inicio("/protected/inicio?faces-redirect=true", "inicio"), //
    usuarios("/protected/usuarios/consultar-usuarios?faces-redirect=true", "usuarios/administracion"), //
    parametros("/protected/parametros/consultar-parametros?faces-redirect=true", "parametros/administracion"), //
    historico_documentos("/protected/documentos/consultarHistoricoDocumentos?faces-redirect=true",
            "documentos/historico"), //
    plantillas("/protected/plantilla/consultarPlantilla?faces-redirect=true", "plantillas/administracion"), //
    salida("/protected/index?faces-redirect=true", ""), //
    ;

    private String ruta;
    private String recurso;

    private EnumNavegacion(String ruta, String recurso) {
        this.ruta = ruta;
        this.recurso = recurso;
    }

    /**
     * Se encarga de buscar el enum correspondiente por medio de la url para poder saber el recurso
     * 
     * @param url
     * @return
     * @author giovanni.velandia
     */
    public static EnumNavegacion buscarEnumNavegacion(String url) {
        for (EnumNavegacion enumNavegacion : EnumNavegacion.values()) {
            if (url.contains(enumNavegacion.normalizeURL())) {
                return enumNavegacion;
            }
        }
        return null;
    }

    /**
     * Se encarga de buscar el enum correspondiente por medio del recurso para poder saber la url
     * 
     * @param url
     * @return
     * @author giovanni.velandia
     */
    public static EnumNavegacion buscarEnumNavegacionRecurso(String recurso) {
        for (EnumNavegacion enumNavegacion : EnumNavegacion.values()) {
            if (recurso.equals(enumNavegacion.getRecurso())) {
                return enumNavegacion;
            }
        }
        return null;
    }

    private String normalizeURL() {
        return this.ruta.substring(0, this.ruta.indexOf("?"));
    }

    public String getRuta() {
        return ruta;
    }

    public String getRecurso() {
        return recurso;
    }
}
