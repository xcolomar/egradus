package org.projecte.egradus.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe de domini Opcio.
 * Una opció forma part obligatòriament d'una pregunta,
 * mentre que una pregunta pot tenir cero o moltes opcions.
 * Les opcions no es reutilitzen entre les preguntes, de
 * manera que donada una Opció és trivial saber a quina
 * pregunta pertany.
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_opcio")
public class Opcio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	@NotEmpty
	private String text;
	
	@NotNull
	private Boolean correcta;
	
	private Date dataAlta;
	
	@ManyToOne
	@NotNull
	private Pregunta pregunta;
	
	// constructor
	public Opcio() {}
	
	
	public Integer getCodi() {
		return codi;
	}
	
	public void setCodi(Integer codi) {
		this.codi = codi;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Boolean getCorrecta() {
		return correcta;
	}
	
	public void setCorrecta(Boolean correcta) {
		this.correcta = correcta;
	}
	
	public Date getDataAlta() {
		return dataAlta;
	}
	
	public void setDataAlta(Date dataAlta) {
		this.dataAlta = dataAlta;
	}
	
	public Pregunta getPregunta() {
		return pregunta;
	}
	
	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  codi: "              + codi);
        buffer.append(", text: "              + text);
        buffer.append(", opcio correcta: "    + correcta);
        buffer.append(", data d'alta: "       + dataAlta);
        buffer.append(", pregunta (codi): "   + pregunta.getCodi());
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
		Opcio other = (Opcio) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		return true;
	}
	
}