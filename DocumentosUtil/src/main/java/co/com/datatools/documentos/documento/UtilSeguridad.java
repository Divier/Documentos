package co.com.datatools.documentos.documento;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import co.com.datatools.documentos.excepcion.DocumentosRuntimeException;

public class UtilSeguridad {

    private static String loginUsuario;
    private static String ipUsuario;
    private static Map<Long, String> mapaUsuarios;
    private static Map<Long, String> mapaIps;

    public static String getIpUsuario() {
        return ipUsuario;
    }

    public static void setIpUsuario(String ipUsuario) {
        UtilSeguridad.ipUsuario = ipUsuario;
    }

    public static String getLoginUsuario() {
        return loginUsuario;
    }

    public static void setLoginUsuario(String loginUsuario) {
        UtilSeguridad.loginUsuario = loginUsuario;
    }

    public static void setMapUsuarios(Long idThread, String usuario, String ip) {
        if (null == mapaUsuarios) {
            mapaUsuarios = new HashMap<Long, String>();
        }
        if (null == mapaIps)
            mapaIps = new HashMap<Long, String>();
        mapaUsuarios.put(idThread, usuario);
        mapaIps.put(idThread, ip);
    }

    public static String getMapUsuarios(Long idThread) {
        String login = "";
        try {
            login = mapaUsuarios.get(idThread);
        } catch (Exception e) {
            return null;
        }
        return login;
    }

    public static String getMapIp(Long idThread) {
        String ip = "";
        try {
            ip = mapaIps.get(idThread);
        } catch (Exception e) {
            return null;
        }
        return ip;
    }

    /**
     * Quita el usuario del mapa estatico de usuarios
     * 
     * @param idThread
     * @author julio.pinzon
     */
    public static void removerUsuario(Long idThread) {
        if (null != mapaUsuarios) {
            mapaUsuarios.remove(idThread);
        }
        if (null != mapaIps) {
            mapaIps.remove(idThread);
        }
    }

    /**
     * Metodo para obtener la version de documentos
     * 
     * @author giovanni.velandia
     * @return
     */
    public static String cargarVersionArtefactoDoc() {
        Properties propiedades = new Properties();
        try {
            propiedades.load(UtilSeguridad.class.getResourceAsStream("/docs-artefacto.properties"));
            return propiedades.getProperty("version");
        } catch (IOException e) {
            throw new DocumentosRuntimeException(
                    "No se encuentra el archivo de propiedades de versiones docs-artefacto.properties");
        }
    }

}
