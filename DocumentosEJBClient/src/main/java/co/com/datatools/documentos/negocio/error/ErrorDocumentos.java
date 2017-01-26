package co.com.datatools.documentos.negocio.error;

import co.com.datatools.documentos.error.ErrorInfo;

/**
 * Catalogo de errores para Documentos
 * 
 * @author julio.pinzon
 * 
 */
public final class ErrorDocumentos {

    private ErrorDocumentos() {
    }

    /**
     * Catalogo errores de negocio
     */
    public enum GenerarDocumento implements ErrorInfo {
        DOC_001000("DOC_001000", "Se recibio al menos un parametro invalido (Es nulo o vacio)", 1000), //
        DOC_001001("DOC_001001", "La fecha de generacion es mayor a la actual", 1001), //
        DOC_001002("DOC_001002", "No se encontro el usuario", 1002), //
        DOC_001003("DOC_001003", "No se pudo guardar el documento en el repositorio", 1003), //
        DOC_001004("DOC_001004", "Error interno al generar documento", 1004), //
        DOC_001005("DOC_001005", "No se encontro la plantilla asociada al codigo", 1005), //
        DOC_001006("DOC_001006", "Error al combinar archivos PDF", 1006), //
        DOC_001007("DOC_001007", "La lista de codigos enviados no contiene documentos en formato PDF", 1007), //
        DOC_001008("DOC_001008", "Error al comprimir documentos", 1008), //
        DOC_001009("DOC_001009", "Error en formato de variables de plantilla", 1009), //
        DOC_001010("DOC_001010", "Error en formato de fecha de generacion", 1010), //
        DOC_001011("DOC_001011", "Error de autenticacion de usuario", 1011), //
        DOC_001012("DOC_001012", "Problemas cargando parametros de configuracion", 1012), //
        DOC_001013("DOC_001013", "Formato invalido de documento", 1013), //
        DOC_001014("DOC_001014", "Excede longitud de descripcion", 1014), //
        DOC_001015("DOC_001015", "Excede longitud de ubicacion", 1015), //
        DOC_001016("DOC_001016", "Error en formato de ubicacion", 1016), //
        DOC_001017("DOC_001017", "Error cargando el archivo al servidor FTP", 1017), //
        DOC_001018("DOC_001018", "Codigo del documento no esta asociado a ningun documento existente", 1018), //
        DOC_001019("DOC_001019", "El documento esta firmado digitalmente y NO es posible editarlo", 1019), //
        DOC_001020("DOC_001020", "No existe un proceso con ese codigo", 1020), //
        DOC_001021("DOC_001021", "No existe un plantilla con ese codigo", 1021), //
        DOC_001022("DOC_001022", "No existe una configuracion para la plantilla", 1022), //
        ;

        /**
         * Contiene el codigo del error
         */
        private final String codigoError;

        /**
         * Contiene la descripcion del error
         */
        private final String descError;

        /**
         * Contiene el codigo del error numerico
         */
        private final Integer codigoNumericoError;

        /**
         * Constructor con codigo y descripcion
         * 
         * @param codigoError
         * @param descError
         * @param codigoNumericoError
         */
        private GenerarDocumento(String codigoError, String descError, Integer codigoNumericoError) {
            this.codigoError = codigoError;
            this.descError = descError;
            this.codigoNumericoError = codigoNumericoError;
        }

        @Override
        public String getCodigoError() {
            return this.codigoError;
        }

        @Override
        public String getDescError() {
            return this.descError;
        }

        @Override
        public Integer getCodigoNumericoError() {
            return this.codigoNumericoError;
        }

    }

    /**
     * Errores referentes a logica de validaciones de Cargo, Funcionario, Proceso o Usuario
     * 
     * @author luis.forero (2015-07-29)
     * 
     */
    public enum Administracion implements ErrorInfo {
        DOC_001050("DOC_001050", "Existe un cargo con el mismo nombre", 1050), //
        DOC_001051("DOC_001051", "Existe un cargo con el mismo identificador 'id' ", 1051), //
        DOC_001052("DOC_001052", "Existe un proceso con el mismo identificador 'id' ", 1052), //
        DOC_001053("DOC_001053", "Existe un proceso con el mismo nombre", 1053), //
        DOC_001054("DOC_001054", "No existe el proceso asociado al cargo", 1054), //
        DOC_001055("DOC_001055", "No existe el proceso padre asociado con el ingresado", 1055), //
        DOC_001056("DOC_001056", "Funcionario asociado al usuario no existente en el sistema", 1056), //
        DOC_001057("DOC_001057", "Usuario no existente en el sistema", 1057), //
        DOC_001058("DOC_001058",
                "El funcionario asociado al usuario a actulizar no corresponde con el asociado en el sistema", 1058), //
        DOC_001059("DOC_001059", "El funcionario no contiene el numero de identificacion", 1059), //
        DOC_001060("DOC_001060", "El funcionario no contiene el nombre del tipo de identificacion", 1060), //
        DOC_001061("DOC_001061", "Error en formato de fecha inicial del funcionario", 1061), //
        DOC_001062("DOC_001062", "Error en formato de fecha final del funcionario", 1062), //
        DOC_001063("DOC_001063", "Error en formato de fecha inicial del cargo del funcionario", 1063), //
        DOC_001064("DOC_001064", "Error en formato de fecha final del cargo del funcionario", 1064), //
        DOC_001065("DOC_001065",
                "El funcionario con el numero de documento y el nombre de identificacion ingresado ya se encuentra registrado en el sistema",
                1065), //
        DOC_001066("DOC_001066", "El cargo a asociar no se encuentra registrado en el sistema", 1066), //
        DOC_001067("DOC_001067", "Los cargos asociados al funcionario no se encuentran ordenados cronologicamente",
                1067), //
        DOC_001068("DOC_001068", "El funcionario no se encuentra registrado en el sistema", 1068), //
        DOC_001069("DOC_001069", "El cargo del funcionario actualmente se encuentra sin cerrar", 1069), //
        DOC_001070("DOC_001070",
                "El cargo ingresado del funcionario se cruza con respecto al anterior cargo asociado a el en el sistema",
                1070), //
        DOC_001071("DOC_001071", "El cargo no se encuentra registrado en el sistema", 1071), //
        DOC_001072("DOC_001072", "El proceso asociado al cargo ya se encuentra asociado", 1072), //
        DOC_001073("DOC_001073", "El nombre del proceso es obligatorio", 1073), //
        DOC_001074("DOC_001074", "Longitud del nombre del proceso invalida, max 70", 1074), //
        DOC_001075("DOC_001075", "La descripcion del proceso es obligatoria", 1075), //
        DOC_001076("DOC_001076", "Longitud de la descripcion del proceso invalida, max 250", 1076), //
        DOC_001077("DOC_001077", "Nombre del cargo obligatorio", 1077), //
        DOC_001078("DOC_001078", "Longitud maxima del Nombre del Cargo 150", 1078), //
        DOC_001079("DOC_001079", "Procesos no ingresados", 1079), //
        DOC_001080("DOC_001080", "Login del usuario es obligatorio", 1080), //
        DOC_001081("DOC_001081", "Maxima longitud del login es 60", 1081), //
        DOC_001082("DOC_001082", "Maxima longitud del nombre del tipo de documento del funcionario es 40", 1082), //
        DOC_001083("DOC_001083", "Maxima longitud del numero de documento del funcionario es 20", 1083), //
        DOC_001084("DOC_001084", "Nombre del funcionario es obligatorio", 1084), //
        DOC_001085("DOC_001085", "Maxima longitud del nombre del funcionario es 125", 1085), //
        DOC_001086("DOC_001086", "Sigla tipo de identificacion es obligatorio", 1086), //
        DOC_001087("DOC_001087", "Maxima longitud Sigla tipo de identificacion es 6", 1087), //
        DOC_001088("DOC_001088", "Contrasena obligatoria", 1088), //
        DOC_001089("DOC_001089", "Longitud maxima de la Contrasena excedida", 1089), //
        ;

        private String codigoError;
        private String descError;
        private Integer codigoNumericoError;

        private Administracion(String codigoError, String descError, Integer codigoNumericoError) {
            this.codigoError = codigoError;
            this.descError = descError;
            this.codigoNumericoError = codigoNumericoError;
        }

        @Override
        public String getCodigoError() {
            return codigoError;
        }

        @Override
        public String getDescError() {
            return descError;
        }

        @Override
        public Integer getCodigoNumericoError() {
            return codigoNumericoError;
        }

    }

    public enum Seguridad implements ErrorInfo {
        DOC_SEG_001("DOC_SEG_001", "Credenciales de acceso inválidas", 1), //
        DOC_SEG_002("DOC_SEG_002", "El usuario ya existe", 2), //
        ;

        /**
         * Contiene el codigo del error
         */
        private final String codigoError;

        /**
         * Contiene la descripcion del error
         */
        private final String descError;

        /**
         * Contiene el codigo del error numerico
         */
        private final Integer codigoNumericoError;

        /**
         * Constructor con codigo y descripcion
         * 
         * @param codigoError
         * @param descError
         * @param codigoNumericoError
         */
        private Seguridad(String codigoError, String descError, Integer codigoNumericoError) {
            this.codigoError = codigoError;
            this.descError = descError;
            this.codigoNumericoError = codigoNumericoError;
        }

        @Override
        public String getCodigoError() {
            return codigoError;
        }

        @Override
        public String getDescError() {
            return descError;
        }

        @Override
        public Integer getCodigoNumericoError() {
            return codigoNumericoError;
        }

    }
}