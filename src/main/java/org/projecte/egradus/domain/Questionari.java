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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Classe de domini qüestionari.
 * Un qüestionari, es defineix com una col·lecció de
 * preguntes. Així i tot, té identidad per sí mateix,
 * per això se l'ha dotat de nom, descripció, graus
 * de dificultat (com la pregunta).
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_questionari")
public class Questionari implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ESTAT_EDITABLE = "editable";
	public static final String ESTAT_PUBLIC = "public";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	@NotEmpty
	private String nom;
	
	@Length(max = 4000)
	@Column(length = 4000)
	private String descripcio;
	
	@OneToMany(mappedBy = "questionari")
    private List<PreguntaQuestionari> preguntes;
	
	/**
	 * grau de dificultat definit pel professor
	 */
	@Min(value = 0)
	@Max(value = 1)
	private Float dificultatTeorica;
	
	/**
	 * grau de dificultat obtingut a través de l'històric
	 * de contestacions que han acertat/fallat els alumnes
	 */
	@Min(value = 0)
	@Max(value = 1)
	private Float dificultatPractica;
	
	/**
	 * atribut que emmagatzema la quantitat de vegades que
	 * actualitzam la Nota Mitja de la pregunta.
	 * 
	 * Amb aquest atribut actualitzarem:
	 * 	· Dificultat pràctica
	 * 	· Nota mitja
	 */
	@Min(value = 0)
	private int numTotalActualitzacionsNotaMitja;
	
	/**
	 * atribut que emmagatzema la quantitat de vegades que
	 * actualitzam la Nota Mitja de la pregunta.
	 * 
	 * Amb aquest atribut actualitzarem:
	 *  · Temps de Resposta Mig
	 */
	@Min(value = 0)
	private int numTotalActualitzacionsTRM;
	
	/**
	 * nota mitja de totes les resposta-questionaris associades al
	 * qüestionari
	 */
	@Min(value = 0)
	@Max(value = 10)
	private Float notaMitja;
	
	/**
	 * temps de resposta (de contestació) mig de totes les
	 * resposta-preguntes  associades al qüestionari.
	 * Estarà en milisegons.
	 */
	@Min(value = 0)
	private Long tempsRespostaMig;
	
	private Date dataAlta;
	
	@NotEmpty
	@Pattern(regexp = ESTAT_EDITABLE + "|" + ESTAT_PUBLIC)
	private String estat;
	
	@ManyToOne
    @NotNull
	private Professor creador;
	
	@OneToMany(mappedBy = "questionari")
    private List<RespostaQuestionari> respostaQuestionaris;
	
	// Constructors
	public Questionari(){
		this.numTotalActualitzacionsNotaMitja = 0;
		this.numTotalActualitzacionsTRM = 0;
	}
	
	public Questionari(String nom, Float dificultatTeorica, Float dificultatPractica, Date dataAlta) {
		this.nom = nom;
		this.dificultatTeorica = dificultatTeorica;
		this.dificultatPractica = dificultatPractica;
		this.numTotalActualitzacionsNotaMitja = 0;
		this.numTotalActualitzacionsTRM = 0;
		this.dataAlta = dataAlta;
	}
	
	/**
	 * <p>retorna el número de preguntes REC que té el qüestionari</p>
	 * <p>si el qüestionari no té preguntes, retorna 0</p>
	 * @return
	 */
	public int getNumPreguntesRec() {
		int numPreguntesRec = 0;
		if (preguntes != null) {
			for (PreguntaQuestionari pq : preguntes) {
				if (pq.getPregunta().getTipus().equals(Pregunta.TIPUS_REC)) numPreguntesRec++;
			}
		}
		return numPreguntesRec;
	}
	
	/**
	 * <p>retorna el número de preguntes que té el qüestionari 
	 * on s'ha de raonar la resposta</p><p>si el qüestionari no 
	 * té preguntes, retorna 0</p>
	 * @return
	 */
	public int getNumPreguntesRaonarResposta() {
		int numPreguntesRaonarResposta = 0;
		if (preguntes != null) {
			for (PreguntaQuestionari pq : preguntes) {
				if (pq.getPregunta().getRaonarResposta().equals(Boolean.TRUE)) numPreguntesRaonarResposta++;
			}
		}
		return numPreguntesRaonarResposta;
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

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public List<PreguntaQuestionari> getPreguntes() {
		return preguntes;
	}

	public void setPreguntes(List<PreguntaQuestionari> preguntes) {
		this.preguntes = preguntes;
	}

	public Float getDificultatTeorica() {
		return dificultatTeorica;
	}

	public void setDificultatTeorica(Float dificultatTeorica) {
		this.dificultatTeorica = dificultatTeorica;
	}

	public Float getDificultatPractica() {
		return dificultatPractica;
	}

	public void setDificultatPractica(Float dificultatPractica) {
		this.dificultatPractica = dificultatPractica;
	}

	public int getNumTotalActualitzacionsNotaMitja() {
		return numTotalActualitzacionsNotaMitja;
	}

	public void setNumTotalActualitzacionsNotaMitja(int numTotalActualitzacionsNotaMitja) {
		this.numTotalActualitzacionsNotaMitja = numTotalActualitzacionsNotaMitja;
	}

	public int getNumTotalActualitzacionsTRM() {
		return numTotalActualitzacionsTRM;
	}

	public void setNumTotalActualitzacionsTRM(int numTotalActualitzacionsTRM) {
		this.numTotalActualitzacionsTRM = numTotalActualitzacionsTRM;
	}

	public Float getNotaMitja() {
		return notaMitja;
	}

	public void setNotaMitja(Float notaMitja) {
		this.notaMitja = notaMitja;
	}

	public Long getTempsRespostaMig() {
		return tempsRespostaMig;
	}

	public void setTempsRespostaMig(Long tempsRespostaMig) {
		this.tempsRespostaMig = tempsRespostaMig;
	}

	public Date getDataAlta() {
		return dataAlta;
	}

	public void setDataAlta(Date dataAlta) {
		this.dataAlta = dataAlta;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public Professor getCreador() {
		return creador;
	}

	public void setCreador(Professor creador) {
		this.creador = creador;
	}
	
	public List<RespostaQuestionari> getRespostaQuestionaris() {
		return respostaQuestionaris;
	}

	public void setRespostaQuestionaris(
			List<RespostaQuestionari> respostaQuestionaris) {
		this.respostaQuestionaris = respostaQuestionaris;
	}
	
	/**
	 * <p>Actualitza el temps de resposta mig de contestació del qüestionari.</p>
	 * <p>precondició: l'atribut numTotalActualitzacionsTRM s'ha d'haver actualitzat prèviament</p>
	 * <p>S'actualitzarà el temps de resposta mig quan el qüestionari s'hagi contestat</p>
	 * @param tempsResposta
	 */
	public void actualitzaTempsRespostaMig(Long tempsResposta) {
		if (tempsResposta != null) {
			int n = getNumTotalActualitzacionsTRM();
			Long tempsRespostaMig = getTempsRespostaMig();
			if (n > 1) setTempsRespostaMig((tempsRespostaMig * (n - 1) + tempsResposta) / n);
			else setTempsRespostaMig(tempsResposta);
		}
	}
	
	/**
	 * <p>Actualitza la nota mitja del qüestionari.</p>
	 * <p>precondició: l'atribut numTotalActualitzacionsNotaMitja s'ha d'haver actualitzat prèviament</p>
	 * <p>S'actualitzarà la nota mitja quan el qüestionari s'hagi corregit</p>
	 * @param nota
	 */
	public void actualitzaNotaMitja(Float nota) {
		if (nota != null) {
			int n = getNumTotalActualitzacionsNotaMitja();
			Float notaMitja = getNotaMitja();
			if (n > 1) setNotaMitja((notaMitja * (n - 1) + nota) / n);
			else setNotaMitja(nota);
		}
	}
	
	/**
	 * <p>Actualitza la nota mitja del qüestionari eliminant la 
	 * contribució de la notaAntiga i plasmant la de la nova nota.</p>
	 * <p>nota: l'atribut numTotalActualitzacionsNotaMitja no sofrirà canvis</p>
	 * @param notaAntiga
	 * 			nota que figura a la nota mitja del qüestionari. És la 
	 * 			nota a eliminar d'aquesta mitja
	 * @param nota
	 * 			nota que volem fer figurar a la nota mitja del qüestionari.
	 */
	public void actualitzaNotaMitja(Float notaAntiga, Float nota) {
		if (notaAntiga != null && nota != null) {
			int n = getNumTotalActualitzacionsNotaMitja();
			Float notaMitja = getNotaMitja();
			if (n > 0) setNotaMitja((notaMitja * n - notaAntiga + nota) / n);
		}
	}
	
	/**
	 * <p>Actualitza la dificultat pràctica del qüestionari.</p>
	 * <p>precondició: l'atribut numTotalActualitzacionsNota s'ha d'haver actualitzat prèviament</p>
	 * <p>S'actualitzarà la nota mitja quan el qüestionari s'hagi corregit</p>
	 * @param nota
	 */
	public void actualitzaDificultatPractica(Float nota) {
		if (nota != null) {
			int n = getNumTotalActualitzacionsNotaMitja();
			Float dp = getDificultatPractica();
			if (n > 1) setDificultatPractica((dp * (n - 1) + (1f - nota/10)) / n);
			else setDificultatPractica(1f - nota/10);
		}
	}
	
	/**
	 * <p>Actualitza la dificultat pràctica del qüestionari eliminant la 
	 * contribució de la notaAntiga i plasmant la de la nova nota.</p>
	 * <p>nota: l'atribut numTotalActualitzacionsNotaMitja no sofrirà canvis</p>
	 * @param notaAntiga
	 * 			nota que figura a la dificultat pràctica del qüestionari. És la 
	 * 			nota a eliminar d'aquesta mitja
	 * @param nota
	 * 			nota que volem fer figurar a la dificultat pràctica del qüestionari.
	 */
	public void actualitzaDificultatPractica(Float notaAntiga, Float nota) {
		if (notaAntiga != null && nota != null) {
			int n = getNumTotalActualitzacionsNotaMitja();
			Float dp = getDificultatPractica();
			if (n > 0) setDificultatPractica((dp * n + notaAntiga/10 - nota/10) / n);
		}
	}
	
	/**
	 * <p>Actualitza el número total d'actualitzacions de la Nota Mitja
	 * del qüestionari.</p>
	 */
	public void incrementaNumTotalActualitzacionsNotaMitja() {
		setNumTotalActualitzacionsNotaMitja(getNumTotalActualitzacionsNotaMitja() + 1);
	}
	
	/**
	 * <p>Actualitza el número total d'actualitzacions del Temps de 
	 * Resposta Mig del qüestionari.</p>
	 */
	public void incrementaNumTotalActualitzacionsTRM() {
		setNumTotalActualitzacionsTRM(getNumTotalActualitzacionsTRM() + 1);
	}
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  codi: "                  + codi);
        buffer.append(", nom: "            		  + nom);
        buffer.append(", descripcio: " 			  + descripcio);
        buffer.append(", dificultat teòrica: "    + dificultatTeorica);
        buffer.append(", dificultat pràctica: "   + dificultatPractica);
        buffer.append(", num. act. NM: "     	  + numTotalActualitzacionsNotaMitja);
        buffer.append(", num. act. TRM: "     	  + numTotalActualitzacionsTRM);
        if (notaMitja != null) buffer.append(", nota mitja: "            + notaMitja);
        if (tempsRespostaMig != null) buffer.append(", temps reposta mig: "     + tempsRespostaMig);
        buffer.append(", data d'alta: "           + dataAlta);
        buffer.append(", estat: "		          + estat);
        buffer.append(", creador: "        		  + creador.toString());
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
		Questionari other = (Questionari) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		return true;
	}
	
}
