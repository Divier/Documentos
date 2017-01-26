/* 
 * Nombre del archivo: ManejadorSesion.java
 * Descripci�n: Clase encargada de manejar todo lo concerniente a la sesi�n de 
 * un usuario. Abre, cierra y extrae objetos de la sesi�n 
 * 
 */
package co.com.datatools.documentos.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Clase encargada de manejar todo lo concerniente a la sesi�n de un usuario.
 * Abre, cierra y extrae objetos de la sesi�n
 */
public class ManejadorSesion {
	/**
	 * Si ya existe una sesion la invalida y crea una nueva
	 * 
	 * @param unRequest
	 *            Objeto de la peticion
	 * @return Un objeto HttpSession
	 */
	public HttpSession crearSesion(HttpServletRequest unRequest) {
		HttpSession miSesion = unRequest.getSession(false);
		if (miSesion != null) {
			miSesion.invalidate();
		}
		miSesion = unRequest.getSession(true);
		return miSesion;
	}

	/**
	 * Se encarga de invalidar la sesion actual
	 * 
	 * @param unRequest
	 */
	public void cerrarSesion(HttpServletRequest unRequest) {
		HttpSession miSesion = unRequest.getSession(false);
		if (miSesion != null) {
			miSesion.invalidate();
		}
	}

	/**
	 * Verifica que exista una sesion
	 * 
	 * @param req
	 *            Objeto Peticion
	 * @return si existe una sesion retorna un objeto HttpSession con los datos
	 *         de la sesion, si no existe la sesion el objeto es null.
	 * 
	 */
	public HttpSession obtenerSesion(HttpServletRequest unRequest) {
		HttpSession miSesion = unRequest.getSession(false);
		return miSesion;
	}

	/**
	 * Metodo utilizado para agregar un objeto a la sesion del usuario
	 * 
	 * @param req
	 *            Objeto HttpServletRequest
	 * @param nombreAtrib
	 *            Nombre con el que se identificar� el objeto dentro de la
	 *            sesi�n
	 * @param objeto
	 *            Objeto que se desea agregar a la sesion
	 * 
	 */
	public void agregarObjetoSesion(HttpServletRequest unRequest,
			String unNombre, Object unObjeto) {
		HttpSession miSesion = obtenerSesion(unRequest);
		miSesion.setAttribute(unNombre, unObjeto);
	}

	/**
	 * Metodo para sacar objetos de la sesion
	 * 
	 * @param unNombreObjeto
	 *            Nombre del objeto en la sesion
	 * @param unRequest
	 * @return Objeto solicitado que se encuentra almacenado en la sesi�n
	 */
	public Object extraerObjetoSesion(String unNombreObjeto,
			HttpServletRequest unRequest) {
		HttpSession miSesion = unRequest.getSession(false);
		Object miObjeto = miSesion.getAttribute(unNombreObjeto);
		return miObjeto;
	}

	/**
	 * Metodo para eliminar objetos de la sesion
	 * 
	 * @param unNombreObjeto
	 *            Nombre del objeto en la sesion
	 * @param unRequest
	 */
	public void removerObjetoSesion(String unNombreObjeto,
			HttpServletRequest unRequest) {
		HttpSession sesion = unRequest.getSession(false);
		sesion.removeAttribute(unNombreObjeto);
	}

	/**
	 * Obtener los nombres de las llaves de los objetos en sesion
	 * 
	 * @param unRequest
	 * @return lista de llaves
	 */
	public Enumeration<String> obtenerLlavesObjetos(HttpServletRequest unRequest) {
		HttpSession sesion = unRequest.getSession(false);
		return sesion.getAttributeNames();
	}

	/**
	 * Remover los MBs de la sesi�n para que al finalizar una acci�n en forma
	 * exitosa se vuelva al estado inicial de los MBs
	 * 
	 * @param unRequest
	 */
	public void removerManagedBeansSesion(HttpServletRequest unRequest) {
		Enumeration<String> e = obtenerLlavesObjetos(unRequest);

		// Sacar de la sesion todos los MB que corresponda
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			if (key.endsWith("MB") && !key.contains("principalMB")) {
				removerObjetoSesion(key, unRequest);
			}
		}
	}

	public void removerTodoSesion(HttpServletRequest unRequest) {
		Enumeration<String> e = obtenerLlavesObjetos(unRequest);

		// Sacar de la sesion todos los MB que corresponda
		while (e.hasMoreElements()) {
			String key = e.nextElement();

			removerObjetoSesion(key, unRequest);

		}
	}
}
