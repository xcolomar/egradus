package org.projecte.egradus.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe de domini Persona. Engloba les característiques bàsiques d'una
 * persona. (nom, llinatges, correu, alies intern, etc). Dins l'aplicatiu,
 * existirà un compte per cada persona, independentment de les assignatures en
 * les què participi.
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_persona", uniqueConstraints = { @UniqueConstraint(name = "UK_ALIES", columnNames = { "alies" }) })
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;

	@NotEmpty
	private String nom;

	@NotEmpty
	private String primerLlinatge;

	private String segonLlinatge;

	private String identificador;

	@Email
	@NotEmpty
	private String correu;

	@NotEmpty
	private String alies;

	@NotEmpty
	private String clau;

	private Date dataNaixament;

	private Date dataAlta;

	// Constructor
	public Persona() {
	}

	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrimerLlinatge() {
		return primerLlinatge;
	}

	public void setPrimerLlinatge(String primerLlinatge) {
		this.primerLlinatge = primerLlinatge;
	}

	public String getSegonLlinatge() {
		return segonLlinatge;
	}

	public void setSegonLlinatge(String segonLlinatge) {
		this.segonLlinatge = segonLlinatge;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getCorreu() {
		return correu;
	}

	public void setCorreu(String correu) {
		this.correu = correu;
	}

	public String getAlies() {
		return alies;
	}

	public void setAlies(String alies) {
		this.alies = alies;
	}

	public String getClau() {
		return clau;
	}

	public void setClau(String clau) {
		this.clau = clau;
	}

	public Date getDataNaixament() {
		return dataNaixament;
	}

	public void setDataNaixament(Date dataNaixament) {
		this.dataNaixament = dataNaixament;
	}

	public Date getDataAlta() {
		return dataAlta;
	}

	public void setDataAlta(Date dataAlta) {
		this.dataAlta = dataAlta;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("  codi: " + codi);
		buffer.append(", nom: " + nom);
		buffer.append(", primer llinatge: " + primerLlinatge);
		buffer.append(", segon llinatge: " + segonLlinatge);
		buffer.append(", identificador: " + identificador);
		buffer.append(", correu: " + correu);
		buffer.append(", àlies: " + alies);
		buffer.append(", clau: " + clau);
		buffer.append(", data naixament: " + dataNaixament);
		buffer.append(", data d'alta: " + dataAlta);
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codi == null) ? 0 : codi.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		return true;
	}

}