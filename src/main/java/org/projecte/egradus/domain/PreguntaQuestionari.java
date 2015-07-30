package org.projecte.egradus.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Classe de domini PreguntaQuestionari.
 * Emmagatzema la relació de quina pregunta pertany a
 * quin qüestionari i quin pès (ponderació) té aquesta
 * pregunta en ell.
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_pregunta_questionari")
public class PreguntaQuestionari implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	@ManyToOne
	@NotNull
	private Pregunta pregunta;
	
	@ManyToOne
	@NotNull
	private Questionari questionari;
	
	/**
	 * pès que te la pregunta en el qüestionari
	 */
	@Min(value = 0)
	@Max(value = 1)
	private Float pes;
	
	
	// Constructor
	public PreguntaQuestionari() {}

	
	
	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public Questionari getQuestionari() {
		return questionari;
	}

	public void setQuestionari(Questionari questionari) {
		this.questionari = questionari;
	}

	public Float getPes() {
		return pes;
	}

	public void setPes(Float pes) {
		this.pes = pes;
	}
	

	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  codi: "        + codi);
        buffer.append(", pes: "         + pes);
        buffer.append(", pregunta: "    + pregunta.toString());
        buffer.append(", questionari: " + questionari.toString());
        return buffer.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((pregunta == null) ? 0 : pregunta.hashCode());
		result = prime * result
				+ ((questionari == null) ? 0 : questionari.hashCode());
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
		PreguntaQuestionari other = (PreguntaQuestionari) obj;
		if (pregunta == null) {
			if (other.pregunta != null)
				return false;
		} else if (!pregunta.equals(other.pregunta))
			return false;
		if (questionari == null) {
			if (other.questionari != null)
				return false;
		} else if (!questionari.equals(other.questionari))
			return false;
		return true;
	}
	
}
