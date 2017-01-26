-- 
--  operacion
-- 
-- 
--  aplicacion
-- 
INSERT INTO aplicacion (ID_APLICACION, NOMBRE_APLICACION) VALUES ('3', 'DOCUMENTOS');
-- 
--  rol
-- 
SET IDENTITY_INSERT rol ON;
INSERT INTO rol (ID_ROL, NOMBRE, DESCRIPCION, ACTIVO, ID_ROL_PADRE, ID_APLICACION) VALUES ('-101', 'Administrador Documentos', 'Administrador de la aplicación', '1', NULL, '3');
SET IDENTITY_INSERT rol OFF;
-- 
--  recurso
-- 
SET IDENTITY_INSERT recurso ON;
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-3000', 'Menú Documentos', 'Menú Documentos', NULL, NULL, '3');
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-3010', 'Plantillas', 'Plantillas', NULL, '-3000', '3');
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-3020', 'Documentos', 'Documentos', NULL, '-3000', '3');
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-3030', 'Parámetros', 'Parámetros', NULL, '-3000', '3');
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-2001', 'Administrar Plantillas', 'plantillas/administracion', NULL, '-3010', '3');
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-2002', 'Consultar Histórico de documentos', 'documentos/historico', NULL, '-3020', '3');
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-2003', 'Administrar Parámetros', 'parametros/administracion', NULL, '-3030', '3');
INSERT INTO recurso (ID_RECURSO, DESCRIPCION, NOMBRE, ID_TIPO_RECURSO, ID_RECURSO_PADRE, ID_APLICACION) VALUES ('-2004', 'Administrar Usuarios', 'usuarios/administracion', NULL, '-3030', '3');
SET IDENTITY_INSERT recurso OFF;
-- 
--  recurso_operacion
-- 
SET IDENTITY_INSERT recurso_operacion ON;
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1000', '-2001', '1');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1001', '-2001', '2');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1002', '-2001', '3');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1003', '-2001', '4');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1004', '-2001', '5');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1005', '-2002', '1');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1006', '-2002', '3');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1007', '-2002', '5');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1008', '-2003', '1');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1009', '-2003', '3');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1010', '-2003', '5');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1011', '-2004', '1');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1012', '-2004', '2');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1013', '-2004', '3');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1014', '-3030', '1');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1015', '-3020', '1');
INSERT INTO recurso_operacion (ID_RECURSO_OPERACION, ID_RECURSO, ID_OPERACION) VALUES ('-1016', '-3010', '1');
SET IDENTITY_INSERT recurso_operacion OFF;
-- 
--  permiso_recurso_operacion
-- 
SET IDENTITY_INSERT permiso_recurso_operacion ON;
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2000', '-1000', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2001', '-1001', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2002', '-1002', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2003', '-1003', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2004', '-1004', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2005', '-1005', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2006', '-1006', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2007', '-1007', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2008', '-1008', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2009', '-1009', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2010', '-1010', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2011', '-1011', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2012', '-1012', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2013', '-1013', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2014', '-1014', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2015', '-1015', '-101');
INSERT INTO permiso_recurso_operacion (ID_PERMISO_RECURSO_OPERACION, ID_RECURSO_OPERACION, ID_ROL) VALUES ('-2016', '-1016', '-101');
SET IDENTITY_INSERT permiso_recurso_operacion OFF;
-- 
--  usuario
-- 
SET IDENTITY_INSERT usuario ON;
INSERT INTO usuario (ID_USUARIO, NOMBRE, APELLIDO, EMAIL, ID_ESTADO_USUARIO, ID_ESTADO_PASSWORD, LOGIN, PASSWORD, LDAP, HUELLA, FECHA_MODIFICA_PASSWORD, FECHA_MODIFICA_USUARIO, FECHA_INICIO_USUARIO, FECHA_FIN_USUARIO) VALUES ('-51', 'Administrador Documentos', 'Administrador Documentos', 'admdocs@g.com', '1', '1', 'admin-docs', 'WLVETPG2JTpDF/4S2v9BGni9oKlSebHVdo6/XKYIKeeNqUToqRYKC21CjLIT6BNSWnJlDaxnuIh5OU/2JNpILw==', '0', NULL, GETDATE(), GETDATE(), GETDATE(), NULL);
SET IDENTITY_INSERT usuario OFF;
-- 
--  rol_usuario
-- 
INSERT INTO rol_usuario (ID_ROL, ID_USUARIO) VALUES ('-101', '-51');
-- 
--  recurso_menu
-- 
SET IDENTITY_INSERT recurso_menu ON;
INSERT INTO recurso_menu (ID_RECURSO_MENU, ID_RECURSO, ORDEN, RECURSO_DATA, ID_MENU_PADRE) VALUES ('-1', '-3010', '1', '<map>
   <entry>
     <string>label</string>
     <string>Plantillas</string>
   </entry>
 </map>', NULL);
INSERT INTO recurso_menu (ID_RECURSO_MENU, ID_RECURSO, ORDEN, RECURSO_DATA, ID_MENU_PADRE) VALUES ('-2', '-2001', '1', '<map>
   <entry>
     <string>label</string>
     <string>Administrar Plantillas</string>
   </entry>
 </map>', '-1');
INSERT INTO recurso_menu (ID_RECURSO_MENU, ID_RECURSO, ORDEN, RECURSO_DATA, ID_MENU_PADRE) VALUES ('-3', '-3020', '2', '<map>
   <entry>
     <string>label</string>
     <string>Documentos</string>
   </entry>
 </map>', NULL);
INSERT INTO recurso_menu (ID_RECURSO_MENU, ID_RECURSO, ORDEN, RECURSO_DATA, ID_MENU_PADRE) VALUES ('-4', '-2002', '1', '<map>
   <entry>
     <string>label</string>
     <string>Consultar histórico de documentos</string>
   </entry>
 </map>', '-3');
INSERT INTO recurso_menu (ID_RECURSO_MENU, ID_RECURSO, ORDEN, RECURSO_DATA, ID_MENU_PADRE) VALUES ('-5', '-3030', '3', '<map>
   <entry>
     <string>label</string>
     <string>Parámetros</string>
   </entry>
 </map>', NULL);
INSERT INTO recurso_menu (ID_RECURSO_MENU, ID_RECURSO, ORDEN, RECURSO_DATA, ID_MENU_PADRE) VALUES ('-6', '-2003', '1', '<map>
   <entry>
     <string>label</string>
     <string>Administrar Parámetros</string>
   </entry>
 </map>', '-5');
INSERT INTO recurso_menu (ID_RECURSO_MENU, ID_RECURSO, ORDEN, RECURSO_DATA, ID_MENU_PADRE) VALUES ('-7', '-2004', '2', '<map>
   <entry>
     <string>label</string>
     <string>Administrar Usuarios</string>
   </entry>
 </map>', '-5');
SET IDENTITY_INSERT recurso_menu OFF;

-- --------------------------------------------------------------------------------------------
-- 2.0.4-SNAPSHOT