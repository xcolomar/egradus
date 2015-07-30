package org.projecte.egradus.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Classe de domini RespostaPregunta.
 * Aquesta entitat identifica la pregunta concreta que s'envia a
 * un alumne per a que la contesti.
 * D'aquesta manera, es podria entendre que la classe de domini
 * Pregunta és, en realitat, una pregunta genèrica, mentre que
 * RespostaPregunta és la pregunta concreta que l'alumne contesta.
 * Per tant, una Pregunta pot tenir moltes RespostaPreguntes, 
 * mentre que una RespostaPregunta està associada només a una
 * Pregunta (i sempre a una).
 * La RespostaPregunta és, per tant, un 'paquet' que duu la
 * informació de quina pregunta s'ha de contestar, quin professor
 * l'ha enviat, quin alumne l'ha de contestar. I, a més, en cas 
 * que sigui necessari, el professor rebrà aquest 'paquet' per
 * a corregir la resposta de l'alumne (només quan no es pugui
 * corregir automàticament la resposta).
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_resposta_pregunta")
public class RespostaPregunta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	@ManyToOne
	@NotNull
	private Pregunta pregunta;
	
	@ManyToOne
	@NotNull
	private Professor assignador;
	
	@ManyToOne
	private Professor corrector;
	
	@ManyToOne
	@NotNull
	private Alumne alumne;
	
	@Min(value = 0)
	@Max(value = 10)
	private Float nota;
	
	/**
	 * nota que tenia la resposta-pregunta
	 * abans de ser revisada
	 */
	@Min(value = 0)
	@Max(value = 10)
	private Float notaAntiga;
	
	@NotNull
	private Boolean contestada;
	
	@NotNull
	private Boolean corregida;
	
	/**
	 * informa de si la resposta-pregunta s'ha
	 * revisat o no, degut a la possibilitat de què
	 * el professor pugui canviar la nota de la 
	 * resposta-pregunta si la pregunta associada
	 * tenia flag raonarResposta a true
	 */
	@NotNull
	private Boolean revisada;
	
	@NotNull
	private Date dataAlta;
	
	private Date dataContestacioInici;
	
	private Date dataContestacioFi;
	
	/**
	 * Diferència entre dataContestacioInici
	 * i dataContestacioFi.
	 * 
	 * El temps de resposta s'expressarà en
	 * milisegons.
	 */
	private Long tempsResposta;
	
	private Date dataCorreccio;
	
	private Date dataRevisio;
	
	@Length(max = 4000)
	@Column(length = 4000)
	private String textResposta;
	
	@Length(max = 4000)
	@Column(length = 4000)
	private String textRaonarResposta;
	
	@Length(max = 4000)
	@Column(length = 4000)
	private String textCorreccio;
	
	@Length(max = 4000)
	@Column(length = 4000)
	private String textRevisio;
	
	@NotNull
	private Boolean anonima;
	
	@OneToMany(mappedBy = "respostaPregunta")
	private List<OpcioResposta> opcionsMarcades;
	
	/**
	 * la RespostaPregunta actual pot pertànyer a
	 * una RespostaQuestionari
	 */
	@ManyToOne
	private RespostaQuestionari respostaQuestionari;
	
	
	// constructors
	public RespostaPregunta() {}
	
	public RespostaPregunta(Pregunta pregunta, Professor assignador, Alumne alumne) {
		this.pregunta = pregunta;
		this.assignador = assignador;
		this.alumne = alumne;
		this.contestada = Boolean.FALSE;
		this.corregida = Boolean.FALSE;
		this.revisada = Boolean.FALSE;
		this.anonima = Boolean.FALSE;
		this.dataAlta = new Date();
	}
	
	public RespostaPregunta(Pregunta pregunta, Professor assignador, Alumne alumne, Boolean anonima) {
		this.pregunta = pregunta;
		this.assignador = assignador;
		this.alumne = alumne;
		this.contestada = Boolean.FALSE;
		this.corregida = Boolean.FALSE;
		this.revisada = Boolean.FALSE;
		this.anonima = anonima;
		this.dataAlta = new Date();
	}
	
	public RespostaPregunta(RespostaQuestionari respostaQuestionari, Pregunta pregunta, Boolean anonima) {
		this.respostaQuestionari = respostaQuestionari;
		this.pregunta = pregunta;
		this.assignador = respostaQuestionari.getAssignador();
		this.alumne = respostaQuestionari.getAlumne();
		this.contestada = Boolean.FALSE;
		this.corregida = Boolean.FALSE;
		this.revisada = Boolean.FALSE;
		this.anonima = anonima;
		this.dataAlta = new Date();
	}
	

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

	public Professor getAssignador() {
		return assignador;
	}

	public void setAssignador(Professor assignador) {
		this.assignador = assignador;
	}

	public Professor getCorrector() {
		return corrector;
	}

	public void setCorrector(Professor corrector) {
		this.corrector = corrector;
	}

	public Alumne getAlumne() {
		return alumne;
	}

	public void setAlumne(Alumne alumne) {
		this.alumne = alumne;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}

	public Float getNotaAntiga() {
		return notaAntiga;
	}

	public void setNotaAntiga(Float notaAntiga) {
		this.notaAntiga = notaAntiga;
	}

	public Boolean getContestada() {
		return contestada;
	}

	public void setContestada(Boolean contestada) {
		this.contestada = contestada;
	}

	public Boolean getCorregida() {
		return corregida;
	}

	public void setCorregida(Boolean corregida) {
		this.corregida = corregida;
	}

	public Boolean getRevisada() {
		return revisada;
	}

	public void setRevisada(Boolean revisada) {
		this.revisada = revisada;
	}

	public Date getDataAlta() {
		return dataAlta;
	}

	public void setDataAlta(Date dataAlta) {
		this.dataAlta = dataAlta;
	}

	public Date getDataContestacioInici() {
		return dataContestacioInici;
	}

	public void setDataContestacioInici(Date dataContestacioInici) {
		this.dataContestacioInici = dataContestacioInici;
	}

	public Date getDataContestacioFi() {
		return dataContestacioFi;
	}

	public void setDataContestacioFi(Date dataContestacioFi) {
		this.dataContestacioFi = dataContestacioFi;
	}
	
	public Long getTempsResposta() {
		return tempsResposta;
	}

	public void setTempsResposta(Long tempsResposta) {
		this.tempsResposta = tempsResposta;
	}

	public Date getDataCorreccio() {
		return dataCorreccio;
	}

	public void setDataCorreccio(Date dataCorreccio) {
		this.dataCorreccio = dataCorreccio;
	}

	public Date getDataRevisio() {
		return dataRevisio;
	}

	public void setDataRevisio(Date dataRevisio) {
		this.dataRevisio = dataRevisio;
	}

	public String getTextResposta() {
		return textResposta;
	}

	public void setTextResposta(String textResposta) {
		this.textResposta = textResposta;
	}

	public String getTextRaonarResposta() {
		return textRaonarResposta;
	}

	public void setTextRaonarResposta(String textRaonarResposta) {
		this.textRaonarResposta = textRaonarResposta;
	}

	public String getTextCorreccio() {
		return textCorreccio;
	}

	public void setTextCorreccio(String textCorreccio) {
		this.textCorreccio = textCorreccio;
	}

	public String getTextRevisio() {
		return textRevisio;
	}

	public void setTextRevisio(String textRevisio) {
		this.textRevisio = textRevisio;
	}

	public Boolean getAnonima() {
		return anonima;
	}

	public void setAnonima(Boolean anonima) {
		this.anonima = anonima;
	}

	public List<OpcioResposta> getOpcionsMarcades() {
		return opcionsMarcades;
	}

	public void setOpcionsMarcades(List<OpcioResposta> opcionsMarcades) {
		this.opcionsMarcades = opcionsMarcades;
	}

	public RespostaQuestionari getRespostaQuestionari() {
		return respostaQuestionari;
	}

	public void setRespostaQuestionari(RespostaQuestionari respostaQuestionari) {
		this.respostaQuestionari = respostaQuestionari;
	}
	
	/**
	 * Alimenta l'atribut 'tempsResposta' agafant com a referència les
	 * dates d'inici i de fi de contestació que suposam que estan omplits.
	 * 
	 * El 'tempsResposta' serà el resultat de restar les dues dates:
	 * datfin - datini
	 * 
	 * @param dataContestacioInici
	 * @param dataContestacioFi
	 */
	public void actualitzaTempsResposta() {
		tempsResposta = dataContestacioFi.getTime() - dataContestacioInici.getTime();
	}
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  codi: "                  			 + codi);
        buffer.append(", pregunta: "              			 + pregunta.toString());
        buffer.append(", assignador: "    					 + assignador.toString());
        if (corrector != null) buffer.append(", corrector: " + corrector.toString());
        buffer.append(", alumne: "       					 + alumne.toString());
        buffer.append(", nota: "           					 + nota);
        buffer.append(", nota antiga: "                      + notaAntiga);
        buffer.append(", contestada: "                 		 + contestada);
        buffer.append(", corregida: "                 		 + corregida);
        buffer.append(", revisada: "                 		 + revisada);
        buffer.append(", anonima: "                 		 + anonima);
        buffer.append(", data d'alta: "        			 	 + dataAlta);
        buffer.append(", data de contestació inicial: "  	 + dataContestacioInici);
        buffer.append(", data de contestació final: "    	 + dataContestacioFi);
        buffer.append(", data de correcció: "		    	 + dataCorreccio);
        buffer.append(", data de revisió: "		    		 + dataRevisio);
        buffer.append(", text resposta: "                    + textResposta);
        buffer.append(", raonament resposta: "    			 + textRaonarResposta);
        buffer.append(", text correcció: "    				 + textCorreccio);
        buffer.append(", text revisió: "    				 + textRevisio);
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
		RespostaPregunta other = (RespostaPregunta) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		return true;
	}
	
}
