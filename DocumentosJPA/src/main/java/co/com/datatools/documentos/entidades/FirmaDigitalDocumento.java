package co.com.datatools.documentos.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the firma_digital_documento database table.
 * 
 */
@Entity
@Table(name="firma_digital_documento")
@NamedQuery(name="FirmaDigitalDocumento.findAll", query="SELECT f FROM FirmaDigitalDocumento f")
public class FirmaDigitalDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_firma_digital_documento")
	private int idFirmaDigitalDocumento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_firma")
	private Date fechaFirma;

	@Column(name="path_documento_firmado")
	private String pathDocumentoFirmado;

	//bi-directional many-to-one association to Documento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_documento")
	private Documento documento;

	//bi-directional many-to-one association to Funcionario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_funcionario")
	private Funcionario funcionario;

	public FirmaDigitalDocumento() {
	}

	public int getIdFirmaDigitalDocumento() {
		return this.idFirmaDigitalDocumento;
	}

	public void setIdFirmaDigitalDocumento(int idFirmaDigitalDocumento) {
		this.idFirmaDigitalDocumento = idFirmaDigitalDocumento;
	}

	public Date getFechaFirma() {
		return this.fechaFirma;
	}

	public void setFechaFirma(Date fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

	public String getPathDocumentoFirmado() {
		return this.pathDocumentoFirmado;
	}

	public void setPathDocumentoFirmado(String pathDocumentoFirmado) {
		this.pathDocumentoFirmado = pathDocumentoFirmado;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}