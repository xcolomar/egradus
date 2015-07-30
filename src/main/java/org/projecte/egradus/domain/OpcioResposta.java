package org.projecte.egradus.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe de domini OpcioResposta.
 * Entitat que emmagatzema la informació referent a
 * les opcions que l'alumne ha marcat en el procés de
 * contestar una pregunta. Si l'alumne està contestant
 * una pregunta, és que té assignada una RespostaPregunta
 * associada a una Pregunta, per tant, és obvi deduir que,
 * si la Pregunta té opcions, la RespostaPregunta haurà
 * de guardar quines opcions de totes les que té la Pregunta
 * ha marcat l'alumne. Així, una RespostaPregunta pot tenir
 * opcionalment varies OpcioRespostes, i una OpcioResposta 
 * és únicament d'una RespostaPregunta (i sempre d'una).
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_opcio_resposta")
public class OpcioResposta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	/**
	 * Aquesta és la opció que l'alumne ha marcat com a 
	 * correcta en la contestació de la RespostaPregunta
	 */
	@ManyToOne
	@NotNull
	private Opcio opcio;
	
	/**
	 * la RespostaPregunta actual que l'alumne està
	 * contestant és aquesta
	 */
	@ManyToOne
	@NotNull
	private RespostaPregunta respostaPregunta;
	
	// constructor
	public OpcioResposta() {}

	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public Opcio getOpcio() {
		return opcio;
	}

	public void setOpcio(Opcio opcio) {
		this.opcio = opcio;
	}

	public RespostaPregunta getRespostaPregunta() {
		return respostaPregunta;
	}

	public void setRespostaPregunta(RespostaPregunta respostaPregunta) {
		this.respostaPregunta = respostaPregunta;
	}

	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  codi: "               + codi);
        buffer.append(", opcio: "              + opcio.toString());
        buffer.append(", respostaPregunta: "   + respostaPregunta.toString());
        return buffer.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((opcio == null) ? 0 : opcio.hashCode());
		result = prime
				* result
				+ ((respostaPregunta == null) ? 0 : respostaPregunta.hashCode());
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
		OpcioResposta other = (OpcioResposta) obj;
		if (opcio == null) {
			if (other.opcio != null)
				return false;
		} else if (!opcio.equals(other.opcio))
			return false;
		if (respostaPregunta == null) {
			if (other.respostaPregunta != null)
				return false;
		} else if (!respostaPregunta.equals(other.respostaPregunta))
			return false;
		return true;
	}
	
}
