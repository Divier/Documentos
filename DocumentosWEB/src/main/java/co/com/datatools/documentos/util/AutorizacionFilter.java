package co.com.datatools.documentos.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.com.datatools.documentos.managed_bean.comun.MenuMB;

/**
 * Se encarga de verificar si los recursos caracterizados tiene permiso en la aplicacion
 * 
 * @author giovanni.velandia
 * 
 */
@WebFilter(filterName = "AutorizacionFilter", urlPatterns = { "/protected/*" })
public class AutorizacionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        boolean esPermitido = false;
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getRequestURL().toString();
        // Validar que no sea la pagina de inicio ya que esta validacion la realiza JAAS
        if (!url.endsWith("inicio.xhtml") && !url.endsWith("index.xhtml")) {
            MenuMB menuMB = (MenuMB) httpServletRequest.getSession().getAttribute("menuMB");
            esPermitido = menuMB.validarNavegacion(httpServletRequest.getRequestURL().toString(), menuMB
                    .getAdminCuentaUsuarioMB().getLogin());
            if (!esPermitido) {
                httpServletResponse.setStatus(500);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
