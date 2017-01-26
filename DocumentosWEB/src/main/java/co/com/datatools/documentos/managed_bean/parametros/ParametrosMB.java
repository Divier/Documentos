package co.com.datatools.documentos.managed_bean.parametros;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.jboss.logging.Logger;

import co.com.datatools.c2.bundle.comun.EnumParametrosWeb;
import co.com.datatools.documentos.administracion.ParametroSistemaDTO;
import co.com.datatools.documentos.administracion.TipoDatoDTO;
import co.com.datatools.documentos.constantes.ConstantesGeneral;
import co.com.datatools.documentos.enumeraciones.EnumTipoDato;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRCatalogo;
import co.com.datatools.documentos.negocio.interfaces.administracion.IRParametroSistema;
import co.com.datatools.util.mail.EmailValidator;
import co.com.datatools.util.web.mb.AbstractSwfManagedBean;

/**
 * Session Bean para gestion del flujo de la administraci�n de par�metros
 * 
 * @author julio.pinzon
 * @author claudia.rodriguez
 * 
 */
@ManagedBean
@SessionScoped
public class ParametrosMB extends AbstractSwfManagedBean {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = Logger.getLogger(ParametrosMB.class.getName());

    private static final String NOMBRE_BUNDLE_PARAMETROS = "labelAdmin";

    private Pattern patronNumericoEntero = Pattern.compile("[0-9]+");
    private Pattern patronNumericoDecimal = Pattern.compile("\\d+(\\.\\d{1,2})?");

    @EJB
    private IRParametroSistema parametrosOrganizacionEjb;

    @EJB
    private IRCatalogo catalogosOrganizacionEjb;

    private List<SelectItem> tiposDato;

    /**
     * Lista de resultados
     */
    private List<ParametroSistemaDTO> resultadoConsulta;

    /**
     * Indica si se debe mostrar la tabla de resultados
     */
    private boolean consultaRealizada;

    private ParametroSistemaDTO parametroOrganizacionDTOConsulta;

    private Date fecha;
    private String texto;
    private String numero;
    private boolean valorSiNo;
    private String email;

    private List<SelectItem> lSiNo;

    private ParametroSistemaDTO parametroSeleccionado;

    private boolean valorTexto;
    private boolean valorNumeroDecimal;
    private boolean valorNumeroEntero;
    private boolean valorBooleano;
    private boolean valorHora;
    private boolean valorFechaHora;
    private boolean valorFecha;
    private boolean valorEmail;
    private SimpleDateFormat formatoFecha;
    private SimpleDateFormat formatoFechaHora;
    private SimpleDateFormat formatoHora;

    public ParametrosMB() {
        logger.debug("ParametrosMB::ParametrosMB");
        tiposDato = new ArrayList<SelectItem>();
        parametroOrganizacionDTOConsulta = new ParametroSistemaDTO();
    }

    @PostConstruct
    public void init() {
        logger.debug("ParametrosMB::cargarListaTiposParametro");
        List<TipoDatoDTO> tiposParametro = catalogosOrganizacionEjb.consultarTipoDato();
        for (TipoDatoDTO tipoDatoDTO : tiposParametro) {
            tiposDato.add(new SelectItem(tipoDatoDTO.getIdTipoDato(), tipoDatoDTO.getNombreTipoDato()));
        }
    }

    public void consultarParametros() {
        logger.debug("ParametrosMB::consultarParametros");
        resultadoConsulta = parametrosOrganizacionEjb.consultarParametroSistema(parametroOrganizacionDTOConsulta);

        for (ParametroSistemaDTO parametroSistemaDTO : resultadoConsulta) {
            try {
                parametroSistemaDTO.setDescripcionParametro(getBundle(NOMBRE_BUNDLE_PARAMETROS).getString(
                        parametroSistemaDTO.getNombreParametro()));
            } catch (MissingResourceException e) {
                logger.debug("Falta descipcion del parametro= " + parametroSistemaDTO.getNombreParametro()
                        + " en el archivo bundle: " + NOMBRE_BUNDLE_PARAMETROS);
            }
        }
        consultaRealizada = true;
    }

    public void validarModificacion() {
        if (!parametroSeleccionado.isEditable()) {
            addErrorMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_error_parametro_no_editable");
            return;
        }
        validarTipoDato();

        try {
            getFacesContext().getExternalContext().redirect(
                    "/DocumentosWEB/protected/parametros/modificar-parametro.xhtml");
        } catch (IOException e) {
            String msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString(
                    ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
            getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            logger.error(e);
        }
        return;
    }

    public void actualizarParametro() {
        logger.debug("ParametrosMB::actualizarParametro");

        // Validar tipo de dato si el parametro es numerico
        if (isValorNumeroEntero()) {
            Matcher matcher = patronNumericoEntero.matcher(getNumero());
            if (!matcher.matches()) {
                addErrorMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_error_tipo_dato");
                return;
            }
        } else if (isValorNumeroDecimal()) {
            Matcher matcher = patronNumericoDecimal.matcher(getNumero());
            if (!matcher.matches()) {
                addErrorMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_error_tipo_dato");
                return;
            }
        } else if (isValorEmail()) {
            if(!EmailValidator.validate(email)) {
                addErrorMessage(NOMBRE_BUNDLE_PARAMETROS, "msg_error_tipo_dato");
                return;
            }
        }
        // Transformar el valor del parametro a string
        transformarValorParametro();
        // Actualizar el parametro
        parametrosOrganizacionEjb.actualizarParametroSistema(parametroSeleccionado);

        // Mensaje de exito al guardar
        addInfoMessage(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL, "msg_exito_guardar");

    }

    public void cargarListaSiNo() {
        logger.debug("ModificarParametroFL::cargarListaSiNo");
        lSiNo = new ArrayList<>();
        lSiNo.add(new SelectItem(true, getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("label_si")));
        lSiNo.add(new SelectItem(false, getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString("label_no")));
    }

    public void validarTipoDato() {
        logger.debug("ModificarParametroFL::validarTipoDato");
        valorTexto = false;
        valorNumeroEntero = false;
        valorNumeroDecimal = false;
        valorBooleano = false;
        valorHora = false;
        valorFechaHora = false;
        valorFecha = false;
        valorEmail = false;

        valorSiNo = false;

        ResourceBundle bundle = getBundle(EnumParametrosWeb.getBundleName());

        formatoFecha = new SimpleDateFormat(bundle.getString(EnumParametrosWeb.lab_calendar_pattern.toString()));
        formatoFechaHora = new SimpleDateFormat(
                bundle.getString(EnumParametrosWeb.lab_calendar_pattern_full.toString()));
        formatoHora = new SimpleDateFormat(bundle.getString(EnumParametrosWeb.lab_calendar_hour_pattern.toString()));

        int idTipoParSeleccionado = parametroSeleccionado.getTipoDatoDTO().getIdTipoDato();
        try {
            if (idTipoParSeleccionado == EnumTipoDato.DECIMAL.getId()) {
                valorNumeroDecimal = true;
                numero = parametroSeleccionado.getValorParametro();
            } else if (idTipoParSeleccionado == EnumTipoDato.ENTERO.getId()) {
                valorNumeroEntero = true;
                numero = parametroSeleccionado.getValorParametro();
            } else if (idTipoParSeleccionado == EnumTipoDato.SI_NO.getId()
                    || idTipoParSeleccionado == EnumTipoDato.SI_NO.getId()) {
                valorBooleano = true;
                cargarListaSiNo();
                if (parametroSeleccionado.getValorParametro().equalsIgnoreCase(ConstantesGeneral.VALOR_SI))
                    valorSiNo = true;
            } else if (idTipoParSeleccionado == EnumTipoDato.HORA.getId()
                    || idTipoParSeleccionado == EnumTipoDato.HORA.getId()) {
                valorHora = true;
                fecha = formatoHora.parse(parametroSeleccionado.getValorParametro());
            } else if (idTipoParSeleccionado == EnumTipoDato.FECHA_HORA.getId()
                    || idTipoParSeleccionado == EnumTipoDato.FECHA_HORA.getId()) {
                valorFechaHora = true;
                fecha = formatoFechaHora.parse(parametroSeleccionado.getValorParametro());
            } else if (idTipoParSeleccionado == EnumTipoDato.FECHA.getId()
                    || idTipoParSeleccionado == EnumTipoDato.FECHA.getId()) {
                valorFecha = true;
                fecha = formatoFecha.parse(parametroSeleccionado.getValorParametro());
            } else if (idTipoParSeleccionado == EnumTipoDato.EMAIL.getId()) {
                valorEmail = true;
                email = parametroSeleccionado.getValorParametro();
            } else {
                valorTexto = true;
                texto = parametroSeleccionado.getValorParametro();
            }
        } catch (ParseException e) {
            logger.error("Error convirtiendo el valor del parametro: " + parametroSeleccionado.getValorParametro()
                    + " - " + e.getMessage());
        }
    }

    public void transformarValorParametro() {
        if (valorFecha) {
            parametroSeleccionado.setValorParametro(formatoFecha.format(fecha));
        } else if (valorFechaHora) {
            parametroSeleccionado.setValorParametro(formatoFechaHora.format(fecha));
        } else if (valorHora) {
            parametroSeleccionado.setValorParametro(formatoHora.format(fecha));
        } else if (valorBooleano) {
            if (valorSiNo) {
                parametroSeleccionado.setValorParametro(ConstantesGeneral.VALOR_SI);
            } else {
                parametroSeleccionado.setValorParametro(ConstantesGeneral.VALOR_NO);
            }
        } else if (valorNumeroDecimal || valorNumeroEntero) {
            parametroSeleccionado.setValorParametro(numero);
        } else if (valorEmail) {
            parametroSeleccionado.setValorParametro(email);
        } else {
            parametroSeleccionado.setValorParametro(texto);
        }
    }

    public boolean volver() {
        logger.debug("ParametrosMB::volver");
        parametroSeleccionado = null;
        try {
            getFacesContext().getExternalContext().redirect(
                    "/DocumentosWEB/protected/parametros/consultar-parametros.xhtml");
        } catch (IOException e) {
            String msg = getBundle(ConstantesGeneral.NOMBRE_BUNDLE_GENERAL).getString(
                    ConstantesGeneral.LLAVE_MENSAJE_ERROR_SISTEMA);
            getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
            logger.error(e);
        }
        return true;

    }

    public ParametroSistemaDTO getParametroSeleccionado() {
        return parametroSeleccionado;
    }

    public void setParametroSeleccionado(ParametroSistemaDTO parametroSeleccionado) {
        this.parametroSeleccionado = parametroSeleccionado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isValorSiNo() {
        return valorSiNo;
    }

    public void setValorSiNo(boolean valorSiNo) {
        this.valorSiNo = valorSiNo;
    }

    public List<SelectItem> getlSiNo() {
        return lSiNo;
    }

    public void setlSiNo(List<SelectItem> lSiNo) {
        this.lSiNo = lSiNo;
    }

    public boolean isValorTexto() {
        return valorTexto;
    }

    public void setValorTexto(boolean valorTexto) {
        this.valorTexto = valorTexto;
    }

    public boolean isValorBooleano() {
        return valorBooleano;
    }

    public void setValorBooleano(boolean valorBooleano) {
        this.valorBooleano = valorBooleano;
    }

    public boolean isValorHora() {
        return valorHora;
    }

    public void setValorHora(boolean valorHora) {
        this.valorHora = valorHora;
    }

    public boolean isValorFechaHora() {
        return valorFechaHora;
    }

    public void setValorFechaHora(boolean valorFechaHora) {
        this.valorFechaHora = valorFechaHora;
    }

    public boolean isValorFecha() {
        return valorFecha;
    }

    public void setValorFecha(boolean valorFecha) {
        this.valorFecha = valorFecha;
    }

    public boolean isValorNumeroDecimal() {
        return valorNumeroDecimal;
    }

    public void setValorNumeroDecimal(boolean valorNumeroDecimal) {
        this.valorNumeroDecimal = valorNumeroDecimal;
    }

    public boolean isValorNumeroEntero() {
        return valorNumeroEntero;
    }

    public void setValorNumeroEntero(boolean valorNumeroEntero) {
        this.valorNumeroEntero = valorNumeroEntero;
    }

    public List<SelectItem> getTiposDato() {
        return tiposDato;
    }

    public void setTiposDato(List<SelectItem> tiposDato) {
        this.tiposDato = tiposDato;
    }

    public boolean isConsultaRealizada() {
        return consultaRealizada;
    }

    public List<ParametroSistemaDTO> getResultadoConsulta() {
        return resultadoConsulta;
    }

    public ParametroSistemaDTO getParametroSistemaDTOConsulta() {
        return parametroOrganizacionDTOConsulta;
    }

    public void setParametroSistemaDTOConsulta(ParametroSistemaDTO parametroOrganizacionDTOConsulta) {
        this.parametroOrganizacionDTOConsulta = parametroOrganizacionDTOConsulta;
    }

    public boolean isValorEmail() {
        return valorEmail;
    }

    public void setValorEmail(boolean valorEmail) {
        this.valorEmail = valorEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
