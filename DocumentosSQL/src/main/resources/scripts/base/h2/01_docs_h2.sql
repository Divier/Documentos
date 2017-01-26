DROP TABLE IF EXISTS cargo CASCADE
;
DROP TABLE IF EXISTS cargo_proceso CASCADE
;
DROP TABLE IF EXISTS contexto_aplicacion_variable CASCADE
;
DROP TABLE IF EXISTS documento CASCADE
;
DROP TABLE IF EXISTS firma_digital_documento CASCADE
;
DROP TABLE IF EXISTS firma_plantilla CASCADE
;
DROP TABLE IF EXISTS formato CASCADE
;
DROP TABLE IF EXISTS funcionario CASCADE
;
DROP TABLE IF EXISTS funcionario_cargo CASCADE
;
DROP TABLE IF EXISTS organizacion CASCADE
;
DROP TABLE IF EXISTS parametro_organizacion CASCADE
;
DROP TABLE IF EXISTS plantilla CASCADE
;
DROP TABLE IF EXISTS proceso CASCADE
;
DROP TABLE IF EXISTS tipo_fecha_referencia CASCADE
;
DROP TABLE IF EXISTS tipo_firma_plantilla CASCADE
;
DROP TABLE IF EXISTS tipo_variable CASCADE
;
DROP TABLE IF EXISTS usuario_organizacion CASCADE
;
DROP TABLE IF EXISTS variable CASCADE
;
DROP TABLE IF EXISTS variable_plantilla CASCADE
;
DROP TABLE IF EXISTS xml_documento CASCADE
;
DROP TABLE IF EXISTS xml_plantilla CASCADE
;

CREATE TABLE cargo
(
	id_cargo INTEGER NOT NULL,
	nombre_cargo VARCHAR(150) NOT NULL,
	id_organizacion INTEGER NOT NULL,
	PRIMARY KEY (id_cargo),
	KEY (id_organizacion)

) 
;


CREATE TABLE cargo_proceso
(
	id_cargo INTEGER NOT NULL,
	id_proceso BIGINT NOT NULL,
	PRIMARY KEY (id_cargo, id_proceso),
	KEY (id_proceso),
	KEY (id_cargo)

) 
;


CREATE TABLE contexto_aplicacion_variable
(
	id_contexto_aplicacion INTEGER NOT NULL,
	nombre_contexto_aplicacion VARCHAR(70) NOT NULL,
	PRIMARY KEY (id_contexto_aplicacion)

) 
;


CREATE TABLE documento
(
	id_documento BIGINT NOT NULL AUTO_INCREMENT,
	nombre_documento VARCHAR(70) NOT NULL,
	consecutivo_documento BIGINT NOT NULL,
	version_documento INTEGER NOT NULL DEFAULT 1,
	id_plantilla INTEGER NOT NULL,
	path_documento VARCHAR(255) NOT NULL,
	fecha_generacion DATETIME NOT NULL,
	id_usuario BIGINT NOT NULL,
	id_documento_origen BIGINT DEFAULT null,
	descripcion_documento VARCHAR(50),
	firmado TINYINT DEFAULT 0,
	id_xml_documento VARCHAR(100) NOT NULL,
	PRIMARY KEY (id_documento),
	UNIQUE UQ_docum_01(consecutivo_documento, version_documento),
	UNIQUE UQ_docum_02(id_xml_documento),
	KEY (id_usuario),
	KEY (id_xml_documento),
	KEY (id_documento_origen),
	KEY (id_plantilla)

) 
;


CREATE TABLE firma_digital_documento
(
	id_firma_digital_documento INTEGER NOT NULL AUTO_INCREMENT,
	id_documento BIGINT NOT NULL,
	id_funcionario BIGINT NOT NULL,
	fecha_firma DATETIME NOT NULL,
	path_documento_firmado VARCHAR(256) NOT NULL,
	PRIMARY KEY (id_firma_digital_documento),
	KEY (id_funcionario),
	KEY (id_documento)

) 
;


CREATE TABLE firma_plantilla
(
	id_firma_plantilla INTEGER NOT NULL AUTO_INCREMENT,
	id_funcionario BIGINT,
	id_tipo_fecha INTEGER,
	id_fecha_referencia INTEGER,
	id_cargo INTEGER,
	id_plantilla INTEGER NOT NULL,
	id_tipo_firma_plantilla INTEGER NOT NULL,
	mostrar_nombre_funcionario TINYINT NOT NULL,
	PRIMARY KEY (id_firma_plantilla),
	KEY (id_cargo),
	KEY (id_fecha_referencia),
	KEY (id_funcionario),
	KEY (id_tipo_fecha),
	KEY (id_plantilla),
	KEY (id_tipo_firma_plantilla)

) 
;


CREATE TABLE formato
(
	id_formato INTEGER NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	formato VARCHAR(50),
	id_tipo_variable INTEGER NOT NULL,
	PRIMARY KEY (id_formato),
	KEY (id_tipo_variable)

) 
;


CREATE TABLE funcionario
(
	id_funcionario BIGINT NOT NULL AUTO_INCREMENT,
	sigla_tipo_identificacion VARCHAR(6) NOT NULL,
	nombre_tipo_identificacion VARCHAR(40) NOT NULL,
	numero_docum_ident VARCHAR(50) NOT NULL,
	nombre_funcionario VARCHAR(60) NOT NULL,
	fecha_inicial_funcionario DATETIME NOT NULL,
	fecha_final_funcionario DATETIME,
	firma BLOB,
	PRIMARY KEY (id_funcionario),
	UNIQUE UQ_funci_01(numero_docum_ident)

) 
;


CREATE TABLE funcionario_cargo
(
	id_funcionario_cargo BIGINT NOT NULL AUTO_INCREMENT,
	id_funcionario BIGINT NOT NULL,
	id_cargo INTEGER NOT NULL,
	fecha_inicio DATETIME NOT NULL,
	fecha_fin DATETIME,
	PRIMARY KEY (id_funcionario_cargo),
	KEY (id_funcionario),
	KEY (id_cargo)

) 
;


CREATE TABLE organizacion
(
	id_organizacion INTEGER NOT NULL,
	nombre_organizacion VARCHAR(80) NOT NULL,
	PRIMARY KEY (id_organizacion)

) 
;


CREATE TABLE parametro_organizacion
(
	id_param_organizacion INTEGER NOT NULL AUTO_INCREMENT,
	nombre_param_organ VARCHAR(64) NOT NULL,
	id_organizacion INTEGER NOT NULL,
	id_tipo_variable INTEGER NOT NULL,
	valor_param_organ VARCHAR(1024) NOT NULL,
	PRIMARY KEY (id_param_organizacion),
	UNIQUE UQ_param_organ_02(nombre_param_organ),
	UNIQUE UQ_param_organ_01(id_organizacion),
	KEY (id_organizacion),
	KEY (id_tipo_variable)

) 
;


CREATE TABLE plantilla
(
	id_plantilla INTEGER NOT NULL AUTO_INCREMENT,
	id_proceso BIGINT NOT NULL,
	nombre_plantilla VARCHAR(100) NOT NULL,
	version_plantilla INTEGER NOT NULL,
	fecha_inicio DATETIME NOT NULL,
	fecha_fin DATETIME,
	fecha_creacion DATETIME NOT NULL,
	estado_plantilla TINYINT NOT NULL DEFAULT 1,
	id_usuario BIGINT NOT NULL,
	plantilla_bloqueada TINYINT DEFAULT 0,
	id_plantilla_origen INTEGER,
	id_xml_plantilla VARCHAR(100) NOT NULL,
	PRIMARY KEY (id_plantilla),
	UNIQUE UQ_plant_01(version_plantilla, nombre_plantilla),
	UNIQUE UQ_plant_02(id_xml_plantilla),
	KEY (id_proceso),
	KEY (id_plantilla_origen),
	KEY (id_xml_plantilla),
	KEY (id_usuario)

) 
;


CREATE TABLE proceso
(
	id_proceso BIGINT NOT NULL,
	nombre_proceso VARCHAR(70) NOT NULL,
	id_organizacion INTEGER NOT NULL,
	descripcion_proceso VARCHAR(250) NOT NULL,
	id_proceso_padre BIGINT,
	PRIMARY KEY (id_proceso),
	UNIQUE UQ_proce_01(id_organizacion, id_proceso),
	KEY (id_proceso_padre),
	KEY (id_organizacion)

) 
;


CREATE TABLE tipo_fecha_referencia
(
	id_tipo_fecha INTEGER NOT NULL,
	nombre_tipo_fecha VARCHAR(50) NOT NULL,
	PRIMARY KEY (id_tipo_fecha)

) 
;


CREATE TABLE tipo_firma_plantilla
(
	id_tipo_firma_plantilla INTEGER NOT NULL,
	nombre VARCHAR(70) NOT NULL,
	PRIMARY KEY (id_tipo_firma_plantilla)

) 
;


CREATE TABLE tipo_variable
(
	id_tipo_variable INTEGER NOT NULL AUTO_INCREMENT,
	nombre_tipo_variable VARCHAR(70) NOT NULL,
	PRIMARY KEY (id_tipo_variable)

) 
;


CREATE TABLE usuario_organizacion
(
	id_usuario BIGINT NOT NULL,
	id_organizacion INTEGER NOT NULL,
	login_usuario VARCHAR(60) NOT NULL,
	id_funcionario BIGINT,
	PRIMARY KEY (id_usuario),
	KEY (id_funcionario),
	KEY (id_organizacion)

) 
;


CREATE TABLE variable
(
	id_variable INTEGER NOT NULL AUTO_INCREMENT,
	nombre_variable VARCHAR(255) NOT NULL,
	descripcion_variable VARCHAR(255) NOT NULL,
	id_tipo_variable INTEGER NOT NULL,
	valor_defecto VARCHAR(255) NOT NULL,
	longitud_variable INTEGER,
	formato_variable VARCHAR(250),
	id_contexto_aplicacion INTEGER NOT NULL,
	id_proceso BIGINT,
	id_organizacion INTEGER,
	imagen BLOB,
	tags TEXT,
	fecha_creacion DATETIME NOT NULL,
	PRIMARY KEY (id_variable),
	KEY (id_tipo_variable),
	KEY (id_organizacion),
	KEY (id_proceso),
	KEY (id_contexto_aplicacion)

) 
;


CREATE TABLE variable_plantilla
(
	id_plantilla INTEGER NOT NULL,
	id_variable INTEGER NOT NULL,
	PRIMARY KEY (id_plantilla, id_variable),
	KEY (id_variable),
	KEY (id_plantilla)

) 
;


CREATE TABLE xml_documento
(
	id_xml_documento VARCHAR(100) NOT NULL,
	nombre_xml_doc VARCHAR(70) NOT NULL,
	contenido_xml TEXT NOT NULL,
	parametros_documento TEXT,
	PRIMARY KEY (id_xml_documento)

) 
;


CREATE TABLE xml_plantilla
(
	id_xml_plantilla VARCHAR(100) NOT NULL,
	nombre_xml_plantilla VARCHAR(70) NOT NULL,
	contenido_xml_plantilla TEXT NOT NULL,
	id_xml_plantilla_padre VARCHAR(100),
	PRIMARY KEY (id_xml_plantilla),
	KEY (id_xml_plantilla_padre)

) 
;


ALTER TABLE cargo ADD CONSTRAINT FK_cargo_01 
	FOREIGN KEY (id_organizacion) REFERENCES organizacion (id_organizacion)
;

ALTER TABLE cargo_proceso ADD CONSTRAINT FK_cargo_proce_01 
	FOREIGN KEY (id_proceso) REFERENCES proceso (id_proceso)
;

ALTER TABLE cargo_proceso ADD CONSTRAINT FK_cargo_proce_02 
	FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
;

ALTER TABLE documento ADD CONSTRAINT FK_docum_01 
	FOREIGN KEY (id_usuario) REFERENCES usuario_organizacion (id_usuario)
;

ALTER TABLE documento ADD CONSTRAINT FK_docum_04 
	FOREIGN KEY (id_xml_documento) REFERENCES xml_documento (id_xml_documento)
;

ALTER TABLE documento ADD CONSTRAINT FK_docum_03 
	FOREIGN KEY (id_documento_origen) REFERENCES documento (id_documento)
;

ALTER TABLE documento ADD CONSTRAINT FK_docum_02 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE firma_digital_documento ADD CONSTRAINT FK_firma_digit_docum_01 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
;

ALTER TABLE firma_digital_documento ADD CONSTRAINT FK_firma_digit_docum_02 
	FOREIGN KEY (id_documento) REFERENCES documento (id_documento)
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

ALTER TABLE formato ADD CONSTRAINT FK_forma_01 
	FOREIGN KEY (id_tipo_variable) REFERENCES tipo_variable (id_tipo_variable)
;

ALTER TABLE funcionario_cargo ADD CONSTRAINT FK_funci_cargo_01 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
;

ALTER TABLE funcionario_cargo ADD CONSTRAINT FK_funci_cargo_02 
	FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
;

ALTER TABLE parametro_organizacion ADD CONSTRAINT FK_param_org_01 
	FOREIGN KEY (id_organizacion) REFERENCES organizacion (id_organizacion)
;

ALTER TABLE parametro_organizacion ADD CONSTRAINT FK_param_org_02 
	FOREIGN KEY (id_tipo_variable) REFERENCES tipo_variable (id_tipo_variable)
;

ALTER TABLE plantilla ADD CONSTRAINT FK_plant_02 
	FOREIGN KEY (id_proceso) REFERENCES proceso (id_proceso)
;

ALTER TABLE plantilla ADD CONSTRAINT FK_plant_04 
	FOREIGN KEY (id_plantilla_origen) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE plantilla ADD CONSTRAINT FK_plant_05 
	FOREIGN KEY (id_xml_plantilla) REFERENCES xml_plantilla (id_xml_plantilla)
;

ALTER TABLE plantilla ADD CONSTRAINT FK_plant_03 
	FOREIGN KEY (id_usuario) REFERENCES usuario_organizacion (id_usuario)
;

ALTER TABLE proceso ADD CONSTRAINT FK_proce_01 
	FOREIGN KEY (id_proceso_padre) REFERENCES proceso (id_proceso)
;

ALTER TABLE proceso ADD CONSTRAINT FK_proce_02 
	FOREIGN KEY (id_organizacion) REFERENCES organizacion (id_organizacion)
;

ALTER TABLE usuario_organizacion ADD CONSTRAINT FK_usuar_organ_01 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
;

ALTER TABLE usuario_organizacion ADD CONSTRAINT FK_usuar_02 
	FOREIGN KEY (id_organizacion) REFERENCES organizacion (id_organizacion)
;

ALTER TABLE variable ADD CONSTRAINT FK_varia_01 
	FOREIGN KEY (id_tipo_variable) REFERENCES tipo_variable (id_tipo_variable)
;

ALTER TABLE variable ADD CONSTRAINT FK_varia_04 
	FOREIGN KEY (id_organizacion) REFERENCES organizacion (id_organizacion)
;

ALTER TABLE variable ADD CONSTRAINT FK_varia_03 
	FOREIGN KEY (id_proceso) REFERENCES proceso (id_proceso)
;

ALTER TABLE variable ADD CONSTRAINT FK_varia_02 
	FOREIGN KEY (id_contexto_aplicacion) REFERENCES contexto_aplicacion_variable (id_contexto_aplicacion)
;

ALTER TABLE variable_plantilla ADD CONSTRAINT FK_varia_plant_01 
	FOREIGN KEY (id_variable) REFERENCES variable (id_variable)
;

ALTER TABLE variable_plantilla ADD CONSTRAINT FK_varia_plant_02 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE xml_plantilla ADD CONSTRAINT FK_xml_plant_01 
	FOREIGN KEY (id_xml_plantilla_padre) REFERENCES xml_plantilla (id_xml_plantilla)
;
