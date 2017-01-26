SET FOREIGN_KEY_CHECKS=0;



DROP TABLE IF EXISTS data_documento CASCADE
;
DROP TABLE IF EXISTS version_data_documento CASCADE
;
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
DROP TABLE IF EXISTS jasper_plantilla CASCADE
;
DROP TABLE IF EXISTS parametro_sistema CASCADE
;
DROP TABLE IF EXISTS plantilla CASCADE
;
DROP TABLE IF EXISTS proceso CASCADE
;
DROP TABLE IF EXISTS tipo_dato CASCADE
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

CREATE TABLE data_documento
(
	codigo_documento INTEGER NOT NULL AUTO_INCREMENT,
	descripcion VARCHAR(512),
	nombre VARCHAR(100),
	folder VARCHAR(256),
	extension VARCHAR(20),
	PRIMARY KEY (codigo_documento)

) 
;


CREATE TABLE version_data_documento
(
	id_version INTEGER NOT NULL AUTO_INCREMENT,
	codigo_documento INTEGER NOT NULL,
	contenido_binario LONGBLOB NOT NULL,
	fecha DATETIME,
	version INTEGER NOT NULL,
	PRIMARY KEY (id_version),
	UNIQUE UQ_version_data_documento_codigo_documento(codigo_documento, version)

) 
;


CREATE TABLE cargo
(
	id_cargo INTEGER NOT NULL,
	nombre_cargo VARCHAR(150) NOT NULL,
	PRIMARY KEY (id_cargo)

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
	id_contexto_aplicacion INTEGER NOT NULL AUTO_INCREMENT,
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
	fecha_creacion DATETIME NOT NULL,
	id_usuario BIGINT NOT NULL,
	id_documento_origen BIGINT DEFAULT null,
	descripcion_documento VARCHAR(300),
	firmado TINYINT DEFAULT 0,
	codigo_documento VARCHAR(256),
  	version_documento_cm INT(11) NOT NULL DEFAULT 0,
	PRIMARY KEY (id_documento),
	UNIQUE UQ_docum_01(consecutivo_documento, version_documento),
	KEY (id_usuario),
	KEY (id_plantilla),
	KEY (id_documento_origen)
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
	codigo_firma_plantilla INTEGER,
	mostrar_cargo_funcionario TINYINT NOT NULL DEFAULT 1,
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
	numero_docum_ident VARCHAR(50) NOT NULL,
	nombre_funcionario VARCHAR(60) NOT NULL,
	fecha_inicial_funcionario DATETIME NOT NULL,
	fecha_final_funcionario DATETIME,
	sigla_tipo_identificacion VARCHAR(6) NOT NULL,
	nombre_tipo_identificacion VARCHAR(40) NOT NULL,
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


CREATE TABLE jasper_plantilla
(
	id_jasper_plantilla INTEGER NOT NULL AUTO_INCREMENT,
	id_plantilla INTEGER NOT NULL,
	codigo_documento VARCHAR(255),
	PRIMARY KEY (id_jasper_plantilla),
	KEY (id_plantilla)

) 
;


CREATE TABLE parametro_sistema
(
	id_parametro_sistema INTEGER NOT NULL AUTO_INCREMENT,
	id_tipo_dato INTEGER NOT NULL,
	nombre_parametro VARCHAR(80) NOT NULL,
	valor_parametro TEXT,
	editable TINYINT NOT NULL DEFAULT 1,
	PRIMARY KEY (id_parametro_sistema),
	UNIQUE UQ_param_siste_01(nombre_parametro),
	KEY (id_tipo_dato)

) 
;


CREATE TABLE plantilla
(
	id_plantilla INTEGER NOT NULL AUTO_INCREMENT,
	codigo_plantilla VARCHAR(20) NOT NULL,
	id_proceso BIGINT NOT NULL,
	nombre_plantilla VARCHAR(100) NOT NULL,
	version_plantilla INTEGER NOT NULL,
	fecha_inicio DATE NOT NULL,
	fecha_fin DATE,
	fecha_creacion DATETIME NOT NULL,
	id_usuario BIGINT NOT NULL,
	plantilla_bloqueada TINYINT DEFAULT 0,
	id_plantilla_origen INTEGER,
	PRIMARY KEY (id_plantilla),
	UNIQUE UQ_plant_01(nombre_plantilla, version_plantilla),
	UNIQUE UQ_plant_02(codigo_plantilla, version_plantilla),
	KEY (id_proceso),
	KEY (id_usuario),
	KEY (id_plantilla_origen)
) 
;


CREATE TABLE proceso
(
	id_proceso BIGINT NOT NULL,
	nombre_proceso VARCHAR(70) NOT NULL,
	descripcion_proceso VARCHAR(250) NOT NULL,
	id_proceso_padre BIGINT,
	PRIMARY KEY (id_proceso),
	KEY (id_proceso_padre)

) 
;


CREATE TABLE tipo_dato
(
	id_tipo_dato INTEGER NOT NULL AUTO_INCREMENT,
	nombre_tipo_dato VARCHAR(25) NOT NULL,
	PRIMARY KEY (id_tipo_dato),
	UNIQUE UQ_tipo_dato_01(nombre_tipo_dato)

) 
;


CREATE TABLE tipo_fecha_referencia
(
	id_tipo_fecha INTEGER NOT NULL AUTO_INCREMENT,
	nombre_tipo_fecha VARCHAR(50) NOT NULL,
	PRIMARY KEY (id_tipo_fecha)

) 
;


CREATE TABLE tipo_firma_plantilla
(
	id_tipo_firma_plantilla INTEGER NOT NULL AUTO_INCREMENT,
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
	id_usuario BIGINT NOT NULL  AUTO_INCREMENT,
	login_usuario VARCHAR(60) NOT NULL,
	id_funcionario BIGINT,
  	contrasena VARCHAR(512) DEFAULT NULL,
	PRIMARY KEY (id_usuario),
	KEY (id_funcionario)

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
	palabra_clave TEXT NOT NULL,
	fecha_creacion DATETIME NOT NULL,
	imagen BLOB,
	PRIMARY KEY (id_variable),
	KEY (id_tipo_variable),
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
	id_xml_documento BIGINT NOT NULL,
	nombre_xml_doc VARCHAR(70) NOT NULL,
	contenido_xml LONGBLOB NOT NULL,
	parametros_documento TEXT,
	codigo_documento_html VARCHAR(256),
	PRIMARY KEY (id_xml_documento)

) 
;


CREATE TABLE xml_plantilla
(
	id_xml_plantilla INTEGER NOT NULL,
	nombre_xml_plantilla VARCHAR(70) NOT NULL,
	contenido_xml_plantilla LONGBLOB NOT NULL,
	id_xml_plantilla_padre INTEGER,
	codigo_documento_html VARCHAR(256),
	PRIMARY KEY (id_xml_plantilla),
	KEY (id_xml_plantilla_padre)

) 
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE cargo_proceso ADD CONSTRAINT FK_cargo_proce_01 
	FOREIGN KEY (id_proceso) REFERENCES proceso (id_proceso)
;

ALTER TABLE cargo_proceso ADD CONSTRAINT FK_cargo_proce_02 
	FOREIGN KEY (id_cargo) REFERENCES cargo (id_cargo)
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

ALTER TABLE jasper_plantilla ADD CONSTRAINT FK_jasper_plantilla_plantilla 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE parametro_sistema ADD CONSTRAINT FK_param_siste_01 
	FOREIGN KEY (id_tipo_dato) REFERENCES tipo_dato (id_tipo_dato)
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

ALTER TABLE proceso ADD CONSTRAINT FK_proce_01 
	FOREIGN KEY (id_proceso_padre) REFERENCES proceso (id_proceso)
;

ALTER TABLE usuario_organizacion ADD CONSTRAINT FK_usuar_organ_01 
	FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario)
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

ALTER TABLE variable_plantilla ADD CONSTRAINT FK_variable_plantilla_variable 
	FOREIGN KEY (id_variable) REFERENCES variable (id_variable)
;

ALTER TABLE variable_plantilla ADD CONSTRAINT FK_varia_plant_02 
	FOREIGN KEY (id_plantilla) REFERENCES plantilla (id_plantilla)
;

ALTER TABLE xml_documento ADD CONSTRAINT FK_xml_docum_01 
	FOREIGN KEY (id_xml_documento) REFERENCES documento (id_documento)
;

ALTER TABLE xml_plantilla ADD CONSTRAINT FK_xml_plant_01 
	FOREIGN KEY (id_xml_plantilla_padre) REFERENCES xml_plantilla (id_xml_plantilla)
;

ALTER TABLE xml_plantilla ADD CONSTRAINT FK_xml_plant_02 
	FOREIGN KEY (id_xml_plantilla) REFERENCES plantilla (id_plantilla)
;

--
-- 2015-05-22 Tablas de seguridad para el módulo de documentos. Implementación básica.
--

DROP TABLE IF EXISTS usuario_rol CASCADE;
DROP TABLE IF EXISTS rol CASCADE;

CREATE TABLE usuario_rol
(
	id_usuario BIGINT NOT NULL,
	id_rol INTEGER NOT NULL,
	CONSTRAINT PK_usuario_rol PRIMARY KEY (id_usuario, id_rol),
	KEY (id_usuario),
	KEY (id_rol)
);

CREATE TABLE rol
(
	id_rol INTEGER NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	descripcion VARCHAR(100),
	CONSTRAINT PK_rol PRIMARY KEY (id_rol)
);

ALTER TABLE usuario_rol ADD CONSTRAINT FK_usuar_rol_01 FOREIGN KEY (id_usuario) REFERENCES usuario_organizacion (id_usuario);

ALTER TABLE usuario_rol ADD CONSTRAINT FK_usuar_rol_02 FOREIGN KEY (id_rol) REFERENCES rol (id_rol);
