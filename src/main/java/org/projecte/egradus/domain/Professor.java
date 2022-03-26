package org.projecte.egradus.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Classe de domini Professor. Un professor no és una persona, és el rol d'una
 * persona a una assignatura o, dit d'una altra manera, és la relació que
 * vincula una persona amb una assignatura. Per tant, una persona pot tenir
 * múltiples instàncies 'Professor', significant que aquesta persona és
 * professora a múltiples assignatures.
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_professor", uniqueConstraints = {
		@UniqueConstraint(name = "UK_PERSONA_ASSIGNATURA", columnNames = { "persona", "assignatura" }) })
public class Professor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;

	@ManyToOne
	@NotNull
	private Persona persona;

	@ManyToOne
	@NotNull
	private Assignatura assignatura;

	// Constructor
	public Professor() {
	}

	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Assignatura getAssignatura() {
		return assignatura;
	}

	public void setAssignatura(Assignatura assignatura) {
		this.assignatura = assignatura;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("  codi: " + codi);
		buffer.append(", persona: " + persona.toString());
		buffer.append(", assignatura: " + assignatura.toString());
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignatura == null) ? 0 : assignatura.hashCode());
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
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
		Professor other = (Professor) obj;
		if (assignatura == null) {
			if (other.assignatura != null)
				return false;
		} else if (!assignatura.equals(other.assignatura))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		return true;
	}

}