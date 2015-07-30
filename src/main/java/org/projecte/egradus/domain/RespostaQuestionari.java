package org.projecte.egradus.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

/**
 * Classe de domini RespostaQuestionari.
 * Aquesta entitat identifica el qüestionari concret que s'envia a
 * un alumne per a que el contesti.
 * D'aquesta manera, es podria entendre que la classe de domini
 * Questionari és, en realitat, un qüestionari genèric, mentre que
 * RespostaQuestionari és el qüestionari concret que l'alumne contesta.
 * Per tant, un Questionari pot tenir moltes RespostaQuestionaris, 
 * mentre que una RespostaQuestionari està associada només a un
 * Questionari (i sempre a un).
 * La RespostaQuestionari és, per tant, un 'paquet' que duu la
 * informació de quin qüestionari s'ha de contestar, quin professor
 * l'ha enviat, quin alumne l'ha de contestar. I, a més, en cas 
 * que sigui necessari, el professor rebrà aquest 'paquet' per
 * a corregir aquelles preguntes del qüestionari que ho requereixin.
 * Donat que contestar un qüestionari significa contestar les preguntes
 * que conté aquell qüestionari, un 'paquet' RespostaQuestionari estarà
 * vinculat amb molts 'paquets' RespostaPregunta, que faran referència
 * a les contestacions de les preguntes del qüestionari.
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_resposta_questionari")
public class RespostaQuestionari implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	@ManyToOne
	@NotNull
	private Questionari questionari;
	
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
	private Boolean contestat;
	
	@NotNull
	private Boolean corregit;
	
	/**
	 * informa de si la resposta-questionari s'ha
	 * revisat o no, degut a la possibilitat de què
	 * el professor pugui canviar la nota de la 
	 * resposta-questionari si el qüestionari associat
	 * tenia flag raonarResposta a true
	 */
	@NotNull
	private Boolean revisat;
	
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
	
	@NotNull
	private Boolean anonim;
	
//	@Length(max = 4000)
//	@Column(length = 4000)
//	private String textCorreccio;
//	
//	@Length(max = 4000)
//	@Column(length = 4000)
//	private String textRevisio;
	
	@OneToMany(mappedBy = "respostaQuestionari")
    private List<RespostaPregunta> respostaPreguntes;
	
	
	// constructors
	public RespostaQuestionari() {}
	
	public RespostaQuestionari(Questionari questionari, Professor assignador, Alumne alumne) {
		this.questionari = questionari;
		this.assignador = assignador;
		this.alumne = alumne;
		this.contestat = Boolean.FALSE;
		this.corregit = Boolean.FALSE;
		this.revisat = Boolean.FALSE;
		this.anonim = Boolean.FALSE;
		this.dataAlta = new Date();
	}
	
	public RespostaQuestionari(Questionari questionari, Professor assignador, Alumne alumne, Boolean anonim) {
		this.questionari = questionari;
		this.assignador = assignador;
		this.alumne = alumne;
		this.contestat = Boolean.FALSE;
		this.corregit = Boolean.FALSE;
		this.revisat = Boolean.FALSE;
		this.anonim = anonim;
		this.dataAlta = new Date();
	}
	
	/**
	 * Retorna el pès associat a la pregunta del qüestionari actual
	 * passada per paràmetre.
	 * Si aquesta no es troba, és nula, o bé el qüestionari
	 * no té preguntes associades retornam null.
	 * 
	 * @param pregunta
	 * @return
	 */
	public Float getPesByPregunta(Pregunta pregunta) {
		if (pregunta != null && questionari.getPreguntes() != null) {
			for (PreguntaQuestionari pq : questionari.getPreguntes()) {
				if (pq.getPregunta().equals(pregunta)) return pq.getPes();
			}
		}
		return null;
	}
	
	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public Questionari getQuestionari() {
		return questionari;
	}

	public void setQuestionari(Questionari questionari) {
		this.questionari = questionari;
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

	public Boolean getContestat() {
		return contestat;
	}

	public void setContestat(Boolean contestat) {
		this.contestat = contestat;
	}

	public Boolean getCorregit() {
		return corregit;
	}

	public void setCorregit(Boolean corregit) {
		this.corregit = corregit;
	}

	public Boolean getRevisat() {
		return revisat;
	}

	public void setRevisat(Boolean revisat) {
		this.revisat = revisat;
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

//	public String getTextCorreccio() {
//		return textCorreccio;
//	}
//
//	public void setTextCorreccio(String textCorreccio) {
//		this.textCorreccio = textCorreccio;
//	}
//
//	public String getTextRevisio() {
//		return textRevisio;
//	}
//
//	public void setTextRevisio(String textRevisio) {
//		this.textRevisio = textRevisio;
//	}

	public Boolean getAnonim() {
		return anonim;
	}

	public void setAnonim(Boolean anonim) {
		this.anonim = anonim;
	}

	public List<RespostaPregunta> getRespostaPreguntes() {
		return respostaPreguntes;
	}

	public void setRespostaPreguntes(List<RespostaPregunta> respostaPreguntes) {
		this.respostaPreguntes = respostaPreguntes;
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
        buffer.append(", questionari: "              		 + questionari.toString());
        buffer.append(", assignador: "    					 + assignador.toString());
        if (corrector != null) buffer.append(", corrector: " + corrector.toString());
        buffer.append(", alumne: "       					 + alumne.toString());
        buffer.append(", nota: "           					 + nota);
        buffer.append(", nota antiga: " 					 + notaAntiga);
        buffer.append(", contestat: "                 		 + contestat);
        buffer.append(", corregit: "                 		 + corregit);
        buffer.append(", revisat: "                 		 + revisat);
        buffer.append(", anonim: "                 		 	 + anonim);
        buffer.append(", data d'alta: "        			 	 + dataAlta);
        buffer.append(", data de contestació inicial: "  	 + dataContestacioInici);
        buffer.append(", data de contestació final: "    	 + dataContestacioFi);
        buffer.append(", data de correcció: "		    	 + dataCorreccio);
        buffer.append(", data de revisió: "		    		 + dataRevisio);
//        buffer.append(", text correcció: "    				 + textCorreccio);
//        buffer.append(", text revisió: "    				 + textRevisio);
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
		RespostaQuestionari other = (RespostaQuestionari) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		return true;
	}
	
}
