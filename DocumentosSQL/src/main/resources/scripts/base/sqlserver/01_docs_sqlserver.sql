--  Drop Foreign Key Constraints 
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_jasper_plantilla_plantilla') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE jasper_plantilla DROP CONSTRAINT FK_jasper_plantilla_plantilla
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_param_siste_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE parametro_sistema DROP CONSTRAINT FK_param_siste_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_usuar_rol_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE usuario_rol DROP CONSTRAINT FK_usuar_rol_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_usuar_rol_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE usuario_rol DROP CONSTRAINT FK_usuar_rol_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_xml_plant_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE xml_plantilla DROP CONSTRAINT FK_xml_plant_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_xml_plant_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE xml_plantilla DROP CONSTRAINT FK_xml_plant_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_xml_docum_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE xml_documento DROP CONSTRAINT FK_xml_docum_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_varia_plant_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE variable_plantilla DROP CONSTRAINT FK_varia_plant_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_varia_plant_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE variable_plantilla DROP CONSTRAINT FK_varia_plant_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_varia_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE variable DROP CONSTRAINT FK_varia_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_varia_03') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE variable DROP CONSTRAINT FK_varia_03
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_varia_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE variable DROP CONSTRAINT FK_varia_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_usuar_organ_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE usuario_organizacion DROP CONSTRAINT FK_usuar_organ_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_proce_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE proceso DROP CONSTRAINT FK_proce_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_plant_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE plantilla DROP CONSTRAINT FK_plant_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_plant_03') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE plantilla DROP CONSTRAINT FK_plant_03
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_plant_04') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE plantilla DROP CONSTRAINT FK_plant_04
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_funci_cargo_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE funcionario_cargo DROP CONSTRAINT FK_funci_cargo_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_funci_cargo_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE funcionario_cargo DROP CONSTRAINT FK_funci_cargo_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_forma_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE formato DROP CONSTRAINT FK_forma_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_plant_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_plantilla DROP CONSTRAINT FK_firma_plant_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_plant_03') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_plantilla DROP CONSTRAINT FK_firma_plant_03
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_plant_05') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_plantilla DROP CONSTRAINT FK_firma_plant_05
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_plant_06') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_plantilla DROP CONSTRAINT FK_firma_plant_06
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_plant_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_plantilla DROP CONSTRAINT FK_firma_plant_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_plant_04') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_plantilla DROP CONSTRAINT FK_firma_plant_04
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_digit_docum_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_digital_documento DROP CONSTRAINT FK_firma_digit_docum_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_firma_digit_docum_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE firma_digital_documento DROP CONSTRAINT FK_firma_digit_docum_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_docum_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE documento DROP CONSTRAINT FK_docum_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_docum_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE documento DROP CONSTRAINT FK_docum_02
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_docum_03') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE documento DROP CONSTRAINT FK_docum_03
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_cargo_proce_01') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE cargo_proceso DROP CONSTRAINT FK_cargo_proce_01
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('FK_cargo_proce_02') AND OBJECTPROPERTY(id, 'IsForeignKey') = 1)
ALTER TABLE cargo_proceso DROP CONSTRAINT FK_cargo_proce_02
;


--  Drop Tables, Stored Procedures and Views 

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('tipo_dato') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE tipo_dato
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('jasper_plantilla') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE jasper_plantilla
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('parametro_sistema') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE parametro_sistema
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('rol') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE rol
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('usuario_rol') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE usuario_rol
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('xml_plantilla') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE xml_plantilla
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('xml_documento') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE xml_documento
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('variable_plantilla') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE variable_plantilla
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('variable') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE variable
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('usuario_organizacion') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE usuario_organizacion
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('tipo_variable') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE tipo_variable
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('tipo_fecha_referencia') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE tipo_fecha_referencia
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('proceso') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE proceso
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('plantilla') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE plantilla
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('funcionario_cargo') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE funcionario_cargo
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('funcionario') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE funcionario
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('formato') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE formato
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('firma_plantilla') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE firma_plantilla
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('firma_digital_documento') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE firma_digital_documento
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('documento') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE documento
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('contexto_aplicacion_variable') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE contexto_aplicacion_variable
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('cargo_proceso') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE cargo_proceso
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('cargo') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE cargo
;

IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id('tipo_firma_plantilla') AND  OBJECTPROPERTY(id, 'IsUserTable') = 1)
DROP TABLE tipo_firma_plantilla
;


--  Create Tables 
CREATE TABLE tipo_dato ( 
	id_tipo_dato int NOT NULL,    --  Identificador unico de cada tipo de dato 
	nombre_tipo_dato varchar(25) NOT NULL    --  Indica el nombre del tipo de dato 
)
;

CREATE TABLE jasper_plantilla ( 
	id_jasper_plantilla int identity(1,1)  NOT NULL,    --  identificador único autoincremental 
	id_plantilla int NOT NULL,    --  id de la plantilla a la que corresponden los archivos jasper 
	codigo_documento varchar(255)    --  Indica la cadena de identificación del documento que contiene el arreglo de bytes del .jasper 
)
;

CREATE TABLE parametro_sistema ( 
	id_parametro_sistema int identity(1,1)  NOT NULL,    --  Identificador único autoincremental 
	id_tipo_dato int NOT NULL,    --  Indica el tipo de dato asignado al paarametro 
	nombre_parametro varchar(80) NOT NULL,    --  Indica el nombre único de cada parametro del sistema 
	valor_parametro varchar(max),    --  Valor del parametro indicado 
	editable tinyint DEFAULT 1 NOT NULL    --  Indica si el parámetro de sistema es editable para el usuario. 0= FALSE y 1 = TRUE 
)
;

CREATE TABLE rol ( 
	id_rol int NOT NULL,    --  Identificador único de cada rol del sistema 
	nombre varchar(50) NOT NULL,    --  Nombre del rol en el sistema 
	descripcion varchar(250)    --  Breve descripcion del rol   
)
;

CREATE TABLE usuario_rol ( 
	id_usuario bigint NOT NULL,    --  Usuario de la relacion 
	id_rol int NOT NULL    --  Rol asignado al usuario 
)
;

CREATE TABLE xml_plantilla ( 
	id_xml_plantilla int NOT NULL,    --  identificador único de cada documento es el asignado por la base de datos no relacional Mongo 
--  @author SergioTorres cambio en tipo de datos id para dejar una relación de 1:1 entre plantilla y xml_plantilla ya no se va a manejar MONGO el xml se guarda en la base de datos 
	nombre_xml_plantilla varchar(70) NOT NULL,    --  indica el nombre de la plantilla 
	contenido_xml_plantilla varbinary(max) NOT NULL,    --  contenido del archivo jxml generado para la plantilla 
	id_xml_plantilla_padre int,    --  indica la plantilla padre que contiene el "la sub-plantilla" la plantilla padre indica en concepto de i-report el padre de un subreporte 
	codigo_documento_html varchar(256)    --  codigo del documento en el repositorio correspondiente al HTML 
)
;

CREATE TABLE xml_documento ( 
	id_xml_documento bigint NOT NULL,    --  identificador único de cada documento xml generado 
	nombre_xml_doc varchar(70) NOT NULL,    --  nombre del xml del documento 
	contenido_xml varbinary(max) NOT NULL,    --  contenido del documento xml 
	parametros_documento varchar(max),    --  Parámetros con los que fué generado el documento  
	codigo_documento_html varchar(256)    --  codigo donde esta almacenado el documento en el repositorio, sirve para recuperar el binario del documento para ser editado 
)
;

CREATE TABLE variable_plantilla ( 
	id_plantilla int NOT NULL,    --  identificaro unico de la plantilla en el sistema 
	id_variable int NOT NULL    --  Identificador unico de la variable en el sistema  
)
;

CREATE TABLE variable ( 
	id_variable int identity(1,1)  NOT NULL,    --  Identificador unico de la variable en el sistema 
	nombre_variable varchar(255) NOT NULL,    --  Nombre de la variable asignada por el organismo de transito.  No se permite repetir nombre de las variables por organismo de transito. 
	descripcion_variable varchar(255) NOT NULL,    --  Descripcion de la variable 
	id_tipo_variable int NOT NULL,    --  Codigo del tipo de variable asociada a la variable 
	valor_defecto varchar(255) NOT NULL,    --  Valor por defecto de la variable 
	longitud_variable int,    --  Longitud o tamaño de de la variable 
	formato_variable varchar(250),    --  Formato de la variable.  Ejemplo para fechas seria dd/mm/aaaa, mm-dd-aa 
	id_contexto_aplicacion int NOT NULL,    --  contexto en el cual tiene aplicacion la variable.  Si el contexto es a nivel de organismo de transito la variable puede ser utilizada por cualquier plantilla.  Si el contexto de aplicación es de plantilla, la variable solo es visible a nivel de la plantilla. 
	id_proceso bigint,    --  Indica el proceso al que está asociado la variable 
	palabra_clave varchar(max) NOT NULL,    --  Contiene las palabras clave que permiten marcar una variable para buscarla desde cualquier punto de la aplicación 
	fecha_creacion datetime2(7) NOT NULL,    --  Indica la fecha de creación de la variable, para facilitar la búsqueda y ordenamiento 
	imagen varbinary(max)    --  Cuando la variable es de tipo imagen se debe guardar el contenido binario en este campo 
)
;

CREATE TABLE usuario_organizacion ( 
	id_usuario bigint identity(1,1)  NOT NULL,    --  Identificador único de cada registro de usuarios, coincide y es administrado por el componente de seguridad. 
	login_usuario varchar(60) NOT NULL,    --  Dato replicado de la tabla de seguridad por rendimiento para obtener la información del login del usuario sin encesidad de consultar sobre otra BD 
	id_funcionario bigint,    --  Indica el funcionario al que puede pertenecer el usuario 
	contrasena varchar(512)    --  Contraseña codificada del usuario 
)
;

CREATE TABLE tipo_variable ( 
	id_tipo_variable int NOT NULL,    --  Identificador unico de cada tipo de variable 
	nombre_tipo_variable varchar(70) NOT NULL    --  nombre del tipo de variable 
)
;

CREATE TABLE tipo_fecha_referencia ( 
	id_tipo_fecha int NOT NULL,    --  codigo de la fecha de referencia. 
	nombre_tipo_fecha varchar(50) NOT NULL    --  nombre del tipo de fecha  
)
;

CREATE TABLE proceso ( 
	id_proceso bigint NOT NULL,    --  Identificador único de cada registro no autoincremental 
	nombre_proceso varchar(70) NOT NULL,    --  Indica el nombre del proceso 
	descripcion_proceso varchar(250) NOT NULL,    --  Descripción breve del proceso 
	id_proceso_padre bigint    --  Proceso padre en la jerarquía de procesos 
)
;

CREATE TABLE plantilla ( 
	id_plantilla int identity(1,1)  NOT NULL,    --  Identificador único de la plantilla en el sistema 
	codigo_plantilla varchar(20) NOT NULL,    --  Indica el código único de la plantilla, atributo que permite identificar una plantilla desde otros sistemas (hace parte integral de la integración). Constituye una forma normal con reglas estrictas: separadores con "_" todas las letras en mayuscula e identifica  la versión a la que corresponde y no tiene carácteres especiales 
	id_proceso bigint NOT NULL,    --  Indica el proceso al cual está asociada la plantilla 
	nombre_plantilla varchar(100) NOT NULL,    --  Nombre que se le asigna a la plantilla 
	version_plantilla int NOT NULL,    --  Versión de la plantilla 
	fecha_inicio date NOT NULL,    --  Fecha de inicio de la plantilla. 
	fecha_fin date,    --  Fecha final de la plantilla. Si la fecha final es null, indica que es la plantilla actual. 
	fecha_creacion datetime2(7) NOT NULL,    --  Fecha en la cual fue creada la plantilla. 
	id_usuario bigint NOT NULL,    --  Indica el usuario que crea la plantilla 
	plantilla_bloqueada tinyint DEFAULT 0,    --  Indica si la plantilla está siendo editada por otro usuario  
	id_plantilla_origen int    --  Indica la plantilla que dio origen a está nueva plantilla (la versión anterior en caso de no ser nueva) 
)
;

CREATE TABLE funcionario_cargo ( 
	id_funcionario_cargo bigint identity(1,1)  NOT NULL,    --  Código único de cada registro de la relación 
	id_funcionario bigint NOT NULL,    --  funcionario asociado al cargo 
	id_cargo int NOT NULL,    --  cargio asignado al funcionario 
	fecha_inicio datetime2(7) NOT NULL,    --  Indica la fecha de creación de la relación. Fecha de referencia para saber si se debe aplicar una firma u otra de acuerdo a la fecha de generación del documento. 
	fecha_fin datetime2(7)    --  Fecha que indica cuando termina la asignación de un funcionario en un cargo. Sigue quedando disponible para la generación de documentos con su firma de acuerdo al requerimiento. 
)
;

CREATE TABLE funcionario ( 
	id_funcionario bigint identity(1,1)  NOT NULL,    --  Identificador único de cada registro de funcionario 
	numero_docum_ident varchar(20) NOT NULL,    --  Número de documento de identidad del funcionario 
	nombre_funcionario varchar(125) NOT NULL,    --  Indica el nombre del funcionario que aparecerá en el pie de la firma 
	fecha_inicial_funcionario datetime2(7) NOT NULL,    --  Indica la fecha inicial del funcionario en el sistema 
	fecha_final_funcionario datetime2(7),    --  Indica la fecha final del funcionario en el sistema. NULL indica que se encuentra activo 
	sigla_tipo_identificacion varchar(6) NOT NULL,    --  Indica la sigla del tipo de identificación asociado al funcionario 
	nombre_tipo_identificacion varchar(40) NOT NULL,    --  Nombre del tipo de identificacion del funcionario 
	firma varbinary(max)    --  Binario que contiene la imagen de la firma del funcionario 
)
;

CREATE TABLE formato ( 
	id_formato int identity(1,1)  NOT NULL,    --  identificador unico autoincremental del formato 
	nombre varchar(50) NOT NULL,    --  n ombre que identifica el formato en que se va a presentar la variable 
	formato varchar(50),    --  formato que identifica en el sistema la presentacion del formato, cuando es fecha se permite java_pattern 
	id_tipo_variable int NOT NULL    --  Tipo de variable a la cual esta asociado el formato 
)
;

CREATE TABLE firma_plantilla ( 
	id_firma_plantilla int identity(1,1)  NOT NULL,
	id_funcionario bigint,    --  Indica el funcionario cuando se hace explicito que firma el documento 
	id_tipo_fecha int,    --  indica el tipo de fecha que aplica.  Puede ser fecha actual, fecha generacion o fecha definida como variable. 
	id_fecha_referencia int,    --  Indica la variable de la plantilla que determina quien firma el documento.  Hace referencia especificamente a una variable de tipo fecha "fecha de referencia en el Caso de Uso" que determina quien firma según la fecha de la plantilla.  En caso de ser null la fecha de referencia es la fecha de generación del documento. 
	id_cargo int,    --  Indica el cargo que firma el documento en caso de que la firma este asociada a un funcionario de un cargo de acuerdo a la fecha  
	id_plantilla int NOT NULL,
	id_tipo_firma_plantilla int NOT NULL,    --  tipo de firma que aplica.  Dependiendo del tipo de firma los campos de la entidad contienen datos.  Por ejemplo, para el tipo de firma de plantilla "funcionario" el campo id_funcionario tendría un valor especifico relacionado con la entidad funcionario. 
	mostrar_nombre_funcionario tinyint NOT NULL,    --  Indica si se debe visualizar el nombre del funcionario debajo de la firma 
	codigo_firma_plantilla int,    --  Permite identificar las caracteristicas de la firma que debe ir a la plantilla en el HTML generado 
	mostrar_cargo_funcionario tinyint DEFAULT 1 NOT NULL    --  permite configurar si se debe mostrar el cargo en la firma de la plantilla 1= ACTIVO 0 = INACTIVO 
)
;

CREATE TABLE firma_digital_documento ( 
	id_firma_digital_documento int identity(1,1)  NOT NULL,    --  Identificador único de la firma digital del documento 
	id_documento bigint NOT NULL,    --  Documento al que está asociado la firma digital 
	id_funcionario bigint NOT NULL,    --  Funcionario al que está asociada la firma digital 
	fecha_firma datetime2(7) NOT NULL,    --  Indica la fecha y hora en la cual es firmado el documento. 
	path_documento_firmado varchar(256) NOT NULL    --  Indica la ruta donde se almacena el archivo físico del documento firmado digitalmente 
)
;

CREATE TABLE documento ( 
	id_documento bigint identity(1,1)  NOT NULL,    --  Consecutivo Interno del documento que es manejado por el sistema 
	nombre_documento varchar(70) NOT NULL,    --  Indica el nombre que se le da al documento 
	consecutivo_documento bigint NOT NULL,    --  Es el consecutivo que se asigna al documento de acuerdo al proceso. 
	version_documento int DEFAULT 1 NOT NULL,    --  version del documento 
	id_plantilla int NOT NULL,    --  plantilla con la cual fue creado el documento. 
	path_documento varchar(255) NOT NULL,    --  Ruta donde se encuentra almacenado el documento. 
	fecha_generacion datetime2(7) NOT NULL,    --  fecha en la cual fue generado el documento. Se envia como fecha de referencia para la selección de la plantilla 
	id_usuario bigint NOT NULL,    --  Usuario que genero el documento 
	id_documento_origen bigint DEFAULT null,    --  Documento que dio origen al documento actual.  Esto aplica cuando un documento fue editado. 
	descripcion_documento varchar(300),    --  Descripción del documento 
	firmado tinyint DEFAULT 0,    --  indica si el documento esta firmado o no. 
	codigo_documento varchar(256),    --  Código del registro del binario almacenado correspondiente al documento 
	version_documento_cm int NOT NULL,    --  Contiene el número de la versión del documento entregada por el gestor documental. Es un fk NO físico, pues no se puede asegurar la integridad referencial contra el gestor. 
	fecha_creacion datetime2(7) NOT NULL    --  Fecha de creación del documento. 
)
;

CREATE TABLE contexto_aplicacion_variable ( 
	id_contexto_aplicacion int NOT NULL,    --  codigo del contexto de aplicacion de la variable 
	nombre_contexto_aplicacion varchar(70) NOT NULL    --  nombre del contexto de aplicación de la variable 
)
;

CREATE TABLE cargo_proceso ( 
	id_cargo int NOT NULL,    --  cargo asociado al proceso 
	id_proceso bigint NOT NULL    --  Proceso al que está asociado el cargo 
)
;

CREATE TABLE cargo ( 
	id_cargo int NOT NULL,    --  Identificador único de cada registro de cargo de la organización 
	nombre_cargo varchar(150) NOT NULL    --  Nombre del cargo registrado 
)
;

CREATE TABLE tipo_firma_plantilla ( 
	id_tipo_firma_plantilla int NOT NULL,    --  identificador único en el sistema para los tipo de firma que pueden existir. 
	nombre varchar(70) NOT NULL
)
;


--  Create Indexes 
ALTER TABLE tipo_dato
	ADD CONSTRAINT UQ_tipo_dato_01 UNIQUE (nombre_tipo_dato)
;

ALTER TABLE parametro_sistema
	ADD CONSTRAINT UQ_param_siste_01 UNIQUE (nombre_parametro)
;

ALTER TABLE plantilla
	ADD CONSTRAINT UQ_plant_01 UNIQUE (nombre_plantilla, version_plantilla)
;

ALTER TABLE plantilla
	ADD CONSTRAINT UQ_plant_02 UNIQUE (codigo_plantilla, version_plantilla)
;

ALTER TABLE plantilla
	ADD CONSTRAINT CK_plant_01 CHECK (plantilla_bloqueada = 0 OR plantilla_bloqueada = 1)
;

ALTER TABLE funcionario
	ADD CONSTRAINT UQ_funci_01 UNIQUE (numero_docum_ident)
;

ALTER TABLE firma_plantilla
	ADD CONSTRAINT CK_firma_plant_01 CHECK (mostrar_cargo_funcionario = 0 OR mostrar_cargo_funcionario = 1)
;

ALTER TABLE documento
	ADD CONSTRAINT UQ_docum_01 UNIQUE (consecutivo_documento, version_documento)
;

--  Create Primary Key Constraints 
ALTER TABLE tipo_dato ADD CONSTRAINT PK_tipo_dato 
	PRIMARY KEY CLUSTERED (id_tipo_dato)
;

ALTER TABLE jasper_plantilla ADD CONSTRAINT PK_jasper_plantilla 
	PRIMARY KEY CLUSTERED (id_jasper_plantilla)
;

ALTER TABLE parametro_sistema ADD CONSTRAINT PK_parametro_sistema 
	PRIMARY KEY CLUSTERED (id_parametro_sistema)
;

ALTER TABLE rol ADD CONSTRAINT PK_rol 
	PRIMARY KEY CLUSTERED (id_rol)
;

ALTER TABLE usuario_rol ADD CONSTRAINT PK_usuario_rol 
	PRIMARY KEY CLUSTERED (id_usuario, id_rol)
;

ALTER TABLE xml_plantilla ADD CONSTRAINT PK_xml_plantilla 
	PRIMARY KEY CLUSTERED (id_xml_plantilla)
;

ALTER TABLE xml_documento ADD CONSTRAINT PK_xml_documento 
	PRIMARY KEY CLUSTERED (id_xml_documento)
;

ALTER TABLE variable_plantilla ADD CONSTRAINT PK_variable_plantilla 
	PRIMARY KEY CLUSTERED (id_plantilla, id_variable)
;

ALTER TABLE variable ADD CONSTRAINT PK_variable 
	PRIMARY KEY CLUSTERED (id_variable)
;

ALTER TABLE usuario_organizacion ADD CONSTRAINT PK_usuario 
	PRIMARY KEY CLUSTERED (id_usuario)
;

ALTER TABLE tipo_variable ADD CONSTRAINT PK_tipo_variable 
	PRIMARY KEY CLUSTERED (id_tipo_variable)
;

ALTER TABLE tipo_fecha_referencia ADD CONSTRAINT PK_tipo_fecha_referencia 
	PRIMARY KEY CLUSTERED (id_tipo_fecha)
;

ALTER TABLE proceso ADD CONSTRAINT PK_proceso 
	PRIMARY KEY CLUSTERED (id_proceso)
;

ALTER TABLE plantilla ADD CONSTRAINT PK_Plantilla 
	PRIMARY KEY CLUSTERED (id_plantilla)
;

ALTER TABLE funcionario_cargo ADD CONSTRAINT PK_funcionario_cargo 
	PRIMARY KEY CLUSTERED (id_funcionario_cargo)
;

ALTER TABLE funcionario ADD CONSTRAINT PK_funcionario 
	PRIMARY KEY CLUSTERED (id_funcionario)
;

ALTER TABLE formato ADD CONSTRAINT PK_formato 
	PRIMARY KEY CLUSTERED (id_formato)
;

ALTER TABLE firma_plantilla ADD CONSTRAINT PK_firma_plantilla 
	PRIMARY KEY CLUSTERED (id_firma_plantilla)
;

ALTER TABLE firma_digital_documento ADD CONSTRAINT PK_firma_digital_documento 
	PRIMARY KEY CLUSTERED (id_firma_digital_documento)
;

ALTER TABLE documento ADD CONSTRAINT PK_Documento 
	PRIMARY KEY CLUSTERED (id_documento)
;

ALTER TABLE contexto_aplicacion_variable ADD CONSTRAINT PK_conte_aplic_varia 
	PRIMARY KEY CLUSTERED (id_contexto_aplicacion)
;

ALTER TABLE cargo_proceso ADD CONSTRAINT PK_cargo_proceso 
	PRIMARY KEY CLUSTERED (id_cargo, id_proceso)
;

ALTER TABLE cargo ADD CONSTRAINT PK_cargo 
	PRIMARY KEY CLUSTERED (id_cargo)
;

ALTER TABLE tipo_firma_plantilla ADD CONSTRAINT PK_tipo_firma_planitlla 
	PRIMARY KEY CLUSTERED (id_tipo_firma_plantilla)
;



--  Create Foreign Key Constraints 
ALTER TABLE jasper_plantilla ADD CONSTRAINT FK_jasper_plantilla_plantilla 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE parametro_sistema ADD CONSTRAINT FK_param_siste_01 
	FOREIGN KEY (id_tipo_dato) REFERENCES tipo_dato (id_tipo_dato)
;

ALTER TABLE usuario_rol ADD CONSTRAINT FK_usuar_rol_01 
	FOREIGN KEY (id_usuario) REFERENCES usuario_organizacion (id_usuario)
;

ALTER TABLE usuario_rol ADD CONSTRAINT FK_usuar_rol_02 
	FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
;

ALTER TABLE xml_plantilla ADD CONSTRAINT FK_xml_plant_01 
	FOREIGN KEY (id_xml_plantilla_padre) REFERENCES xml_plantilla (id_xml_plantilla)
;

ALTER TABLE xml_plantilla ADD CONSTRAINT FK_xml_plant_02 
	FOREIGN KEY (id_xml_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE xml_documento ADD CONSTRAINT FK_xml_docum_01 
	FOREIGN KEY (id_xml_documento) REFERENCES documento (id_documento)
;

ALTER TABLE variable_plantilla ADD CONSTRAINT FK_varia_plant_01 
	FOREIGN KEY (id_variable) REFERENCES variable (id_variable)
;

ALTER TABLE variable_plantilla ADD CONSTRAINT FK_varia_plant_02 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE variable ADD CONSTRAINT FK_varia_01 
	FOREIGN KEY (id_tipo_variable) REFERENCES tipo_variable (id_tipo_variable)
;

ALTER TABLE variable ADD CONSTRAINT FK_varia_03 
	FOREIGN KEY (id_proceso) REFERENCES proceso (id_proceso)
;

ALTER TABLE variable ADD CONSTRAINT FK_varia_02 
	FOREIGN KEY (id_contexto_aplicacion) REFERENCES contexto_aplicacion_variable (id_contexto_aplicacion)
;

ALTER TABLE usuario_organizacion ADD CONSTRAINT FK_usuar_organ_01 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
;

ALTER TABLE proceso ADD CONSTRAINT FK_proce_01 
	FOREIGN KEY (id_proceso_padre) REFERENCES proceso (id_proceso)
;

ALTER TABLE plantilla ADD CONSTRAINT FK_plant_02 
	FOREIGN KEY (id_proceso) REFERENCES proceso (id_proceso)
;

ALTER TABLE plantilla ADD CONSTRAINT FK_plant_03 
	FOREIGN KEY (id_usuario) REFERENCES usuario_organizacion (id_usuario)
;

ALTER TABLE plantilla ADD CONSTRAINT FK_plant_04 
	FOREIGN KEY (id_plantilla_origen) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE funcionario_cargo ADD CONSTRAINT FK_funci_cargo_01 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
;

ALTER TABLE funcionario_cargo ADD CONSTRAINT FK_funci_cargo_02 
	FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
;

ALTER TABLE formato ADD CONSTRAINT FK_forma_01 
	FOREIGN KEY (id_tipo_variable) REFERENCES tipo_variable (id_tipo_variable)
;

ALTER TABLE firma_plantilla ADD CONSTRAINT FK_firma_plant_02 
	FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
;

ALTER TABLE firma_plantilla ADD CONSTRAINT FK_firma_plant_03 
	FOREIGN KEY (id_fecha_referencia) REFERENCES variable (id_variable)
;

ALTER TABLE firma_plantilla ADD CONSTRAINT FK_firma_plant_05 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
;

ALTER TABLE firma_plantilla ADD CONSTRAINT FK_firma_plant_06 
	FOREIGN KEY (id_tipo_fecha) REFERENCES tipo_fecha_referencia (id_tipo_fecha)
;

ALTER TABLE firma_plantilla ADD CONSTRAINT FK_firma_plant_01 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE firma_plantilla ADD CONSTRAINT FK_firma_plant_04 
	FOREIGN KEY (id_tipo_firma_plantilla) REFERENCES tipo_firma_plantilla (id_tipo_firma_plantilla)
;

ALTER TABLE firma_digital_documento ADD CONSTRAINT FK_firma_digit_docum_01 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
;

ALTER TABLE firma_digital_documento ADD CONSTRAINT FK_firma_digit_docum_02 
	FOREIGN KEY (id_documento) REFERENCES documento (id_documento)
;

ALTER TABLE documento ADD CONSTRAINT FK_docum_01 
	FOREIGN KEY (id_usuario) REFERENCES usuario_organizacion (id_usuario)
;

ALTER TABLE documento ADD CONSTRAINT FK_docum_02 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE documento ADD CONSTRAINT FK_docum_03 
	FOREIGN KEY (id_documento_origen) REFERENCES documento (id_documento)
;

ALTER TABLE cargo_proceso ADD CONSTRAINT FK_cargo_proce_01 
	FOREIGN KEY (id_proceso) REFERENCES proceso (id_proceso)
;

ALTER TABLE cargo_proceso ADD CONSTRAINT FK_cargo_proce_02 
	FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
;









EXEC sp_addextendedproperty 'MS_Description', 'Almacena los posible de tipo de dato de los parámetros de sistema

-Entero
-Decimal
-Texto
-Fecha
-Hora
-Fecha y Hora
-SI/NO
-Email', 'Schema', dbo, 'table', tipo_dato
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador unico de cada tipo de dato', 'Schema', dbo, 'table', tipo_dato, 'column', id_tipo_dato
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el nombre del tipo de dato', 'Schema', dbo, 'table', tipo_dato, 'column', nombre_tipo_dato
;

EXEC sp_addextendedproperty 'MS_Description', 'se encarga de guardar los diferentes jasper que conforman una plantilla', 'Schema', dbo, 'table', jasper_plantilla
;
EXEC sp_addextendedproperty 'MS_Description', 'identificador único autoincremental', 'Schema', dbo, 'table', jasper_plantilla, 'column', id_jasper_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'id de la plantilla a la que corresponden los archivos jasper', 'Schema', dbo, 'table', jasper_plantilla, 'column', id_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la cadena de identificación del documento que contiene el arreglo de bytes del .jasper', 'Schema', dbo, 'table', jasper_plantilla, 'column', codigo_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'Almacena los parametros del sistema', 'Schema', dbo, 'table', parametro_sistema
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único autoincremental', 'Schema', dbo, 'table', parametro_sistema, 'column', id_parametro_sistema
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el tipo de dato asignado al paarametro', 'Schema', dbo, 'table', parametro_sistema, 'column', id_tipo_dato
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el nombre único de cada parametro del sistema', 'Schema', dbo, 'table', parametro_sistema, 'column', nombre_parametro
;

EXEC sp_addextendedproperty 'MS_Description', 'Valor del parametro indicado', 'Schema', dbo, 'table', parametro_sistema, 'column', valor_parametro
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica si el parámetro de sistema es editable para el usuario.
0= FALSE y 1 = TRUE', 'Schema', dbo, 'table', parametro_sistema, 'column', editable
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla que almacena los roles de la aplicación', 'Schema', dbo, 'table', rol
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único de cada rol del sistema', 'Schema', dbo, 'table', rol, 'column', id_rol
;

EXEC sp_addextendedproperty 'MS_Description', 'Nombre del rol en el sistema', 'Schema', dbo, 'table', rol, 'column', nombre
;

EXEC sp_addextendedproperty 'MS_Description', 'Breve descripcion del rol  ', 'Schema', dbo, 'table', rol, 'column', descripcion
;

EXEC sp_addextendedproperty 'MS_Description', 'Relación de los roles asignados a un usuario', 'Schema', dbo, 'table', usuario_rol
;
EXEC sp_addextendedproperty 'MS_Description', 'Usuario de la relacion', 'Schema', dbo, 'table', usuario_rol, 'column', id_usuario
;

EXEC sp_addextendedproperty 'MS_Description', 'Rol asignado al usuario', 'Schema', dbo, 'table', usuario_rol, 'column', id_rol
;

EXEC sp_addextendedproperty 'MS_Description', 'se extrae el xml de la plantilla ya que se plantea almacenar los documentos en una base de datos externa.

Se genera la tabla xml_plantilla para resolver la relacion envolvente de plantilla y para controlar de acuerdo a las pruebas de desarrollo que el documento se guarde en otro motor de base de datos', 'Schema', dbo, 'table', xml_plantilla
;
EXEC sp_addextendedproperty 'MS_Description', 'identificador único de cada documento es el asignado por la base de datos no relacional Mongo

@author SergioTorres cambio en tipo de datos id para dejar una relación de 1:1 entre plantilla y xml_plantilla ya no se va a manejar MONGO el xml se guarda en la base de datos', 'Schema', dbo, 'table', xml_plantilla, 'column', id_xml_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'indica el nombre de la plantilla', 'Schema', dbo, 'table', xml_plantilla, 'column', nombre_xml_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'contenido del archivo jxml generado para la plantilla', 'Schema', dbo, 'table', xml_plantilla, 'column', contenido_xml_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'indica la plantilla padre que contiene el "la sub-plantilla" la plantilla padre indica en concepto de i-report el padre de un subreporte', 'Schema', dbo, 'table', xml_plantilla, 'column', id_xml_plantilla_padre
;

EXEC sp_addextendedproperty 'MS_Description', 'codigo del documento en el repositorio correspondiente al HTML', 'Schema', dbo, 'table', xml_plantilla, 'column', codigo_documento_html
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla que permite almacenar el documento generado con la metadata de cada una de sus secciones', 'Schema', dbo, 'table', xml_documento
;
EXEC sp_addextendedproperty 'MS_Description', 'identificador único de cada documento xml generado', 'Schema', dbo, 'table', xml_documento, 'column', id_xml_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'nombre del xml del documento', 'Schema', dbo, 'table', xml_documento, 'column', nombre_xml_doc
;

EXEC sp_addextendedproperty 'MS_Description', 'contenido del documento xml', 'Schema', dbo, 'table', xml_documento, 'column', contenido_xml
;

EXEC sp_addextendedproperty 'MS_Description', 'Parámetros con los que fué generado el documento ', 'Schema', dbo, 'table', xml_documento, 'column', parametros_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'codigo donde esta almacenado el documento en el repositorio, sirve para recuperar el binario del documento para ser editado', 'Schema', dbo, 'table', xml_documento, 'column', codigo_documento_html
;

EXEC sp_addextendedproperty 'MS_Description', 'Contiene la relacion de variables y plantillas.  es una relacion muchos a muchos.', 'Schema', dbo, 'table', variable_plantilla
;
EXEC sp_addextendedproperty 'MS_Description', 'identificaro unico de la plantilla en el sistema', 'Schema', dbo, 'table', variable_plantilla, 'column', id_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Identificador unico de la variable en el sistema ', 'Schema', dbo, 'table', variable_plantilla, 'column', id_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'Contiene las variables definidas para el sistema. Que pueden ser utilizadas en distinto nivel jerárquico y en distintas plantillas.
Conservando nombre, tipo, longitud, descripción, palabras clave, formato y proceso al cual pertenecen si es necesario.', 'Schema', dbo, 'table', variable
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador unico de la variable en el sistema', 'Schema', dbo, 'table', variable, 'column', id_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'Nombre de la variable asignada por el organismo de transito.  No se permite repetir nombre de las variables por organismo de transito.', 'Schema', dbo, 'table', variable, 'column', nombre_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'Descripcion de la variable', 'Schema', dbo, 'table', variable, 'column', descripcion_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'Codigo del tipo de variable asociada a la variable', 'Schema', dbo, 'table', variable, 'column', id_tipo_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'Valor por defecto de la variable', 'Schema', dbo, 'table', variable, 'column', valor_defecto
;

EXEC sp_addextendedproperty 'MS_Description', 'Longitud o tamaño de de la variable', 'Schema', dbo, 'table', variable, 'column', longitud_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'Formato de la variable.  Ejemplo para fechas seria dd/mm/aaaa, mm-dd-aa', 'Schema', dbo, 'table', variable, 'column', formato_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'contexto en el cual tiene aplicacion la variable.  Si el contexto es a nivel de organismo de transito la variable puede ser utilizada por cualquier plantilla.  Si el contexto de aplicación es de plantilla, la variable solo es visible a nivel de la plantilla.', 'Schema', dbo, 'table', variable, 'column', id_contexto_aplicacion
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el proceso al que está asociado la variable', 'Schema', dbo, 'table', variable, 'column', id_proceso
;

EXEC sp_addextendedproperty 'MS_Description', 'Contiene las palabras clave que permiten marcar una variable para buscarla desde cualquier punto de la aplicación', 'Schema', dbo, 'table', variable, 'column', palabras_clave
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la fecha de creación de la variable, para facilitar la búsqueda y ordenamiento', 'Schema', dbo, 'table', variable, 'column', fecha_creacion
;

EXEC sp_addextendedproperty 'MS_Description', 'Cuando la variable es de tipo imagen se debe guardar el contenido binario en este campo', 'Schema', dbo, 'table', variable, 'column', imagen
;

EXEC sp_addextendedproperty 'MS_Description', 'Es la tabla que representa el usuario que es manejado por el aplicativo de seguridad pero utilizado de manera independiente por el subsistema de documentos', 'Schema', dbo, 'table', usuario_organizacion
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único de cada registro de usuarios, coincide y es administrado por el componente de seguridad.', 'Schema', dbo, 'table', usuario_organizacion, 'column', id_usuario
;

EXEC sp_addextendedproperty 'MS_Description', 'Dato replicado de la tabla de seguridad por rendimiento para obtener la información del login del usuario sin encesidad de consultar sobre otra BD', 'Schema', dbo, 'table', usuario_organizacion, 'column', login_usuario
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el funcionario al que puede pertenecer el usuario', 'Schema', dbo, 'table', usuario_organizacion, 'column', id_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Contraseña codificada del usuario', 'Schema', dbo, 'table', usuario_organizacion, 'column', contrasena
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla que almacena los posibles tipos de variable del sistema

-Number
-Date
-String
- URL
- Imagen', 'Schema', dbo, 'table', tipo_variable
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador unico de cada tipo de variable', 'Schema', dbo, 'table', tipo_variable, 'column', id_tipo_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'nombre del tipo de variable', 'Schema', dbo, 'table', tipo_variable, 'column', nombre_tipo_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'catalogo del sistema que indica el tipo de fecha de referencia


	1. Fecha actual
	2. Fecha generacion
	3. Fecha definida en las variables de la plantilla.  En este caso el campo id_fecha_referencia en la tabla firma_plantilla es diferente de null.', 'Schema', dbo, 'table', tipo_fecha_referencia
;
EXEC sp_addextendedproperty 'MS_Description', 'codigo de la fecha de referencia.', 'Schema', dbo, 'table', tipo_fecha_referencia, 'column', id_tipo_fecha
;

EXEC sp_addextendedproperty 'MS_Description', 'nombre del tipo de fecha ', 'Schema', dbo, 'table', tipo_fecha_referencia, 'column', nombre_tipo_fecha
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla de procesos del modelo de documentos', 'Schema', dbo, 'table', proceso
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único de cada registro no autoincremental', 'Schema', dbo, 'table', proceso, 'column', id_proceso
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el nombre del proceso', 'Schema', dbo, 'table', proceso, 'column', nombre_proceso
;

EXEC sp_addextendedproperty 'MS_Description', 'Descripción breve del proceso', 'Schema', dbo, 'table', proceso, 'column', descripcion_proceso
;

EXEC sp_addextendedproperty 'MS_Description', 'Proceso padre en la jerarquía de procesos', 'Schema', dbo, 'table', proceso, 'column', id_proceso_padre
;

EXEC sp_addextendedproperty 'MS_Description', 'Guarda la plantilla que puede ser aplicada para crear un documento.', 'Schema', dbo, 'table', plantilla
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único de la plantilla en el sistema', 'Schema', dbo, 'table', plantilla, 'column', id_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el código único de la plantilla, atributo que permite identificar una plantilla desde otros sistemas (hace parte integral de la integración). Constituye una forma normal con reglas estrictas: separadores con "_" todas las letras en mayuscula e identifica  la versión a la que corresponde y no tiene carácteres especiales', 'Schema', dbo, 'table', plantilla, 'column', codigo_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el proceso al cual está asociada la plantilla', 'Schema', dbo, 'table', plantilla, 'column', id_proceso
;

EXEC sp_addextendedproperty 'MS_Description', 'Nombre que se le asigna a la plantilla', 'Schema', dbo, 'table', plantilla, 'column', nombre_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Versión de la plantilla', 'Schema', dbo, 'table', plantilla, 'column', version_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Fecha de inicio de la plantilla.', 'Schema', dbo, 'table', plantilla, 'column', fecha_inicio
;

EXEC sp_addextendedproperty 'MS_Description', 'Fecha final de la plantilla. Si la fecha final es null, indica que es la plantilla actual.', 'Schema', dbo, 'table', plantilla, 'column', fecha_fin
;

EXEC sp_addextendedproperty 'MS_Description', 'Fecha en la cual fue creada la plantilla.', 'Schema', dbo, 'table', plantilla, 'column', fecha_creacion
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el usuario que crea la plantilla', 'Schema', dbo, 'table', plantilla, 'column', id_usuario
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica si la plantilla está siendo editada por otro usuario ', 'Schema', dbo, 'table', plantilla, 'column', plantilla_bloqueada
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la plantilla que dio origen a está nueva plantilla (la versión anterior en caso de no ser nueva)', 'Schema', dbo, 'table', plantilla, 'column', id_plantilla_origen
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla que almacena la relación de los cargos del empleado', 'Schema', dbo, 'table', funcionario_cargo
;
EXEC sp_addextendedproperty 'MS_Description', 'Código único de cada registro de la relación', 'Schema', dbo, 'table', funcionario_cargo, 'column', id_funcionario_cargo
;

EXEC sp_addextendedproperty 'MS_Description', 'funcionario asociado al cargo', 'Schema', dbo, 'table', funcionario_cargo, 'column', id_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'cargio asignado al funcionario', 'Schema', dbo, 'table', funcionario_cargo, 'column', id_cargo
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la fecha de creación de la relación. Fecha de referencia para saber si se debe aplicar una firma u otra de acuerdo a la fecha de generación del documento.', 'Schema', dbo, 'table', funcionario_cargo, 'column', fecha_inicio
;

EXEC sp_addextendedproperty 'MS_Description', 'Fecha que indica cuando termina la asignación de un funcionario en un cargo. Sigue quedando disponible para la generación de documentos con su firma de acuerdo al requerimiento.', 'Schema', dbo, 'table', funcionario_cargo, 'column', fecha_fin
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla que permite almacenar los funcionarios alos que se les asociará la firma', 'Schema', dbo, 'table', funcionario
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único de cada registro de funcionario', 'Schema', dbo, 'table', funcionario, 'column', id_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Número de documento de identidad del funcionario', 'Schema', dbo, 'table', funcionario, 'column', numero_docum_ident
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el nombre del funcionario que aparecerá en el pie de la firma', 'Schema', dbo, 'table', funcionario, 'column', nombre_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la fecha inicial del funcionario en el sistema', 'Schema', dbo, 'table', funcionario, 'column', fecha_inicial_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la fecha final del funcionario en el sistema. NULL indica que se encuentra activo', 'Schema', dbo, 'table', funcionario, 'column', fecha_final_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la sigla del tipo de identificación asociado al funcionario', 'Schema', dbo, 'table', funcionario, 'column', sigla_tipo_identificacion
;

EXEC sp_addextendedproperty 'MS_Description', 'Nombre del tipo de identificacion del funcionario', 'Schema', dbo, 'table', funcionario, 'column', nombre_tipo_identificacion
;

EXEC sp_addextendedproperty 'MS_Description', 'Binario que contiene la imagen de la firma del funcionario', 'Schema', dbo, 'table', funcionario, 'column', firma
;

EXEC sp_addextendedproperty 'MS_Description', 'formato en el que puede ser presentada una variable de un tipo determinado', 'Schema', dbo, 'table', formato
;
EXEC sp_addextendedproperty 'MS_Description', 'identificador unico autoincremental del formato', 'Schema', dbo, 'table', formato, 'column', id_formato
;

EXEC sp_addextendedproperty 'MS_Description', 'n ombre que identifica el formato en que se va a presentar la variable', 'Schema', dbo, 'table', formato, 'column', nombre
;

EXEC sp_addextendedproperty 'MS_Description', 'formato que identifica en el sistema la presentacion del formato, cuando es fecha se permite java_pattern', 'Schema', dbo, 'table', formato, 'column', formato
;

EXEC sp_addextendedproperty 'MS_Description', 'Tipo de variable a la cual esta asociado el formato', 'Schema', dbo, 'table', formato, 'column', id_tipo_variable
;

EXEC sp_addextendedproperty 'MS_Description', 'Permite asociar firma mecánica a un documento, proceso o subproceso', 'Schema', dbo, 'table', firma_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el funcionario cuando se hace explicito que firma el documento', 'Schema', dbo, 'table', firma_plantilla, 'column', id_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'indica el tipo de fecha que aplica.  Puede ser fecha actual, fecha generacion o fecha definida como variable.', 'Schema', dbo, 'table', firma_plantilla, 'column', id_tipo_fecha
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la variable de la plantilla que determina quien firma el documento.  Hace referencia especificamente a una variable de tipo fecha "fecha de referencia en el Caso de Uso" que determina quien firma según la fecha de la plantilla.  En caso de ser null la fecha de referencia es la fecha de generación del documento.', 'Schema', dbo, 'table', firma_plantilla, 'column', id_fecha_referencia
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el cargo que firma el documento en caso de que la firma este asociada a un funcionario de un cargo de acuerdo a la fecha ', 'Schema', dbo, 'table', firma_plantilla, 'column', id_cargo
;


EXEC sp_addextendedproperty 'MS_Description', 'tipo de firma que aplica.  Dependiendo del tipo de firma los campos de la entidad contienen datos.  Por ejemplo, para el tipo de firma de plantilla "funcionario" el campo id_funcionario tendría un valor especifico relacionado con la entidad funcionario.', 'Schema', dbo, 'table', firma_plantilla, 'column', id_tipo_firma_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica si se debe visualizar el nombre del funcionario debajo de la firma', 'Schema', dbo, 'table', firma_plantilla, 'column', mostrar_nombre_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Permite identificar las caracteristicas de la firma que debe ir a la plantilla en el HTML generado', 'Schema', dbo, 'table', firma_plantilla, 'column', codigo_firma_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'permite configurar si se debe mostrar el cargo en la firma de la plantilla
1= ACTIVO
0 = INACTIVO', 'Schema', dbo, 'table', firma_plantilla, 'column', mostrar_cargo_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla que almacena la versión del documento firmado digitalmente', 'Schema', dbo, 'table', firma_digital_documento
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único de la firma digital del documento', 'Schema', dbo, 'table', firma_digital_documento, 'column', id_firma_digital_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'Documento al que está asociado la firma digital', 'Schema', dbo, 'table', firma_digital_documento, 'column', id_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'Funcionario al que está asociada la firma digital', 'Schema', dbo, 'table', firma_digital_documento, 'column', id_funcionario
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la fecha y hora en la cual es firmado el documento.', 'Schema', dbo, 'table', firma_digital_documento, 'column', fecha_firma
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica la ruta donde se almacena el archivo físico del documento firmado digitalmente', 'Schema', dbo, 'table', firma_digital_documento, 'column', path_documento_firmado
;

EXEC sp_addextendedproperty 'MS_Description', 'El CIDocumentoOrigen indica el documento que dio origen al documento actual. Cuando el documento es nuevo este campo es null.  
Cuando un documento es editado, el nuevo documento generado a partir de la edición, tiene un nuevo CIDocumento y el CIDocumentoOrigen es el documento original.

Es importante garantizar que no existan bubles en la asignacion de codigos.
Por ejemplo
CIDocumento    CIDocumentoOrigen
         2                                    4
         4                                    2', 'Schema', dbo, 'table', documento
;
EXEC sp_addextendedproperty 'MS_Description', 'Consecutivo Interno del documento que es manejado por el sistema', 'Schema', dbo, 'table', documento, 'column', id_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'Indica el nombre que se le da al documento', 'Schema', dbo, 'table', documento, 'column', nombre_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'Es el consecutivo que se asigna al documento de acuerdo al proceso.', 'Schema', dbo, 'table', documento, 'column', consecutivo_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'version del documento', 'Schema', dbo, 'table', documento, 'column', version_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'plantilla con la cual fue creado el documento.', 'Schema', dbo, 'table', documento, 'column', id_plantilla
;

EXEC sp_addextendedproperty 'MS_Description', 'Ruta donde se encuentra almacenado el documento.', 'Schema', dbo, 'table', documento, 'column', path_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'fecha en la cual fue generado el documento. Se envia como fecha de referencia para la selección de la plantilla', 'Schema', dbo, 'table', documento, 'column', fecha_generacion
;

EXEC sp_addextendedproperty 'MS_Description', 'Usuario que genero el documento', 'Schema', dbo, 'table', documento, 'column', id_usuario
;

EXEC sp_addextendedproperty 'MS_Description', 'Documento que dio origen al documento actual.  Esto aplica cuando un documento fue editado.', 'Schema', dbo, 'table', documento, 'column', id_documento_origen
;

EXEC sp_addextendedproperty 'MS_Description', 'Descripción del documento', 'Schema', dbo, 'table', documento, 'column', descripcion_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'indica si el documento esta firmado o no.', 'Schema', dbo, 'table', documento, 'column', firmado
;

EXEC sp_addextendedproperty 'MS_Description', 'Código del registro del binario almacenado correspondiente al documento', 'Schema', dbo, 'table', documento, 'column', codigo_documento
;

EXEC sp_addextendedproperty 'MS_Description', 'Contiene el número de la versión del documento entregada por el gestor documental. Es un fk NO físico, pues no se puede asegurar la integridad referencial contra el gestor.', 'Schema', dbo, 'table', documento, 'column', version_documento_cm
;

EXEC sp_addextendedproperty 'MS_Description', 'Fecha de creación del documento.', 'Schema', dbo, 'table', documento, 'column', fecha_creacion
;

EXEC sp_addextendedproperty 'MS_Description', '
Identifica en donde se puede aplicar la variable creada.  Este catalogo de contexto de aplicación de la variable es a nivel de sistema.


Puede tomar valor de:

	- Organización (corresponde al organismo de tránsito )
	- Proceso
	- Plantillas', 'Schema', dbo, 'table', contexto_aplicacion_variable
;
EXEC sp_addextendedproperty 'MS_Description', 'codigo del contexto de aplicacion de la variable', 'Schema', dbo, 'table', contexto_aplicacion_variable, 'column', id_contexto_aplicacion
;

EXEC sp_addextendedproperty 'MS_Description', 'nombre del contexto de aplicación de la variable', 'Schema', dbo, 'table', contexto_aplicacion_variable, 'column', nombre_contexto_aplicacion
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla que resuelve la relación de muchos a muchos de los cargos relacionados con un proceso', 'Schema', dbo, 'table', cargo_proceso
;
EXEC sp_addextendedproperty 'MS_Description', 'cargo asociado al proceso', 'Schema', dbo, 'table', cargo_proceso, 'column', id_cargo
;

EXEC sp_addextendedproperty 'MS_Description', 'Proceso al que está asociado el cargo', 'Schema', dbo, 'table', cargo_proceso, 'column', id_proceso
;

EXEC sp_addextendedproperty 'MS_Description', 'Tabla de parametrización que lista los cargos que tiene definidos una organización', 'Schema', dbo, 'table', cargo
;
EXEC sp_addextendedproperty 'MS_Description', 'Identificador único de cada registro de cargo de la organización', 'Schema', dbo, 'table', cargo, 'column', id_cargo
;

EXEC sp_addextendedproperty 'MS_Description', 'Nombre del cargo registrado', 'Schema', dbo, 'table', cargo, 'column', nombre_cargo
;

EXEC sp_addextendedproperty 'MS_Description', 'tipos de firma que pueden configurarse para las plantillas.

Son:
- funcionario
- Cargo
- Usuario que Genera el documento', 'Schema', dbo, 'table', tipo_firma_plantilla
;
EXEC sp_addextendedproperty 'MS_Description', 'identificador único en el sistema para los tipo de firma que pueden existir.', 'Schema', dbo, 'table', tipo_firma_plantilla, 'column', id_tipo_firma_plantilla
;
