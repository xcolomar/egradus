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
 * Classe de domini Pregunta.
 * Engloba les característiques bàsiques d'una pregunta.
 * (enunciat, grau de dificultat, tipus de pregunta, etc)
 * 
 * @author Xavier
 *
 */
@Entity
@Table(name = "t_pregunta")
public class Pregunta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String ESTAT_EDITABLE = "editable";
	public static final String ESTAT_PUBLIC = "public";
	
	public static final String TIPUS_ES1 = "ES1";
	public static final String TIPUS_ESN = "ESN";
	public static final String TIPUS_VOF = "VOF";
	public static final String TIPUS_REC = "REC";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codi;
	
	@NotEmpty
	@Length(max = 4000)
	@Column(length = 4000)
	private String enunciat;
	
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
	 * actualitzam el Temps de Resposta Mig (TRM) de la pregunta.
	 * 
	 * Per a actualitzar el TRM no ens podem basar en l'atribut
	 * numTotalActualitzacionsNota anterior, ja que quan la pregunta 
	 * que es contesta vé d'una RQ, NO actualitzam el TRM i, 
	 * per tant, si només actualitzam el numTotalActualitzacionsNota,
	 * perdem el coneixement de quantes vegades hem actualitzat
	 * el TRM.
	 */
	@Min(value = 0)
	private int numTotalActualitzacionsTRM;
	
	/**
	 * nota mitja de totes les resposta-preguntes associades a 
	 * la pregunta
	 */
	@Min(value = 0)
	@Max(value = 10)
	private Float notaMitja;
	
	/**
	 * temps de resposta (de contestació) mig de totes les
	 * resposta-preguntes  associades a la pregunta.
	 * Estarà en milisegons.
	 */
	@Min(value = 0)
	private Long tempsRespostaMig;
	
	@NotNull
	private Boolean raonarResposta;
	
	private Date dataAlta;
	
	@NotEmpty
	@Pattern(regexp = TIPUS_ES1 + "|" + TIPUS_ESN + "|" + TIPUS_VOF + "|" + TIPUS_REC)
	private String tipus;
	
	/**
	 * atribut que només s'emmagatzema quan el tipus de
	 * pregunta és VOF, i defineix formalment si la 
	 * pregunta és vertadera o falsa.
	 */
	private Boolean vofVertader;
	
	@NotEmpty
	@Pattern(regexp = ESTAT_EDITABLE + "|" + ESTAT_PUBLIC)
	private String estat;
	
	@ManyToOne
    @NotNull
	private Professor creador;
	
	@OneToMany(mappedBy = "pregunta")
	private List<Opcio> opcions;
	
	@OneToMany(mappedBy = "pregunta")
	private List<PreguntaQuestionari> questionaris;
	
	@OneToMany(mappedBy = "pregunta")
	private List<RespostaPregunta> respostaPreguntes;
	
	
	// Constructors
	public Pregunta(){
		this.numTotalActualitzacionsNotaMitja = 0;
		this.numTotalActualitzacionsTRM = 0;
	}
	
	public Pregunta(String enunciat, Float dificultatTeorica, Float dificultatPractica, Boolean raonarResposta, Date dataAlta) {
		this.enunciat = enunciat;
		this.dificultatTeorica = dificultatTeorica;
		this.dificultatPractica = dificultatPractica;
		this.raonarResposta = raonarResposta;
		this.dataAlta = dataAlta;
		this.numTotalActualitzacionsNotaMitja = 0;
		this.numTotalActualitzacionsTRM = 0;
	}

	public Integer getCodi() {
		return codi;
	}

	public void setCodi(Integer codi) {
		this.codi = codi;
	}

	public String getEnunciat() {
		return enunciat;
	}

	public void setEnunciat(String enunciat) {
		this.enunciat = enunciat;
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

	public Boolean getRaonarResposta() {
		return raonarResposta;
	}

	public void setRaonarResposta(Boolean raonarResposta) {
		this.raonarResposta = raonarResposta;
	}

	public Date getDataAlta() {
		return dataAlta;
	}

	public void setDataAlta(Date dataAlta) {
		this.dataAlta = dataAlta;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public Boolean getVofVertader() {
		return vofVertader;
	}

	public void setVofVertader(Boolean vofVertader) {
		this.vofVertader = vofVertader;
	}

	public Professor getCreador() {
		return creador;
	}

	public void setCreador(Professor creador) {
		this.creador = creador;
	}
	
	public List<Opcio> getOpcions() {
		return opcions;
	}

	public void setOpcions(List<Opcio> opcions) {
		this.opcions = opcions;
	}

	public List<PreguntaQuestionari> getQuestionaris() {
		return questionaris;
	}

	public void setQuestionaris(List<PreguntaQuestionari> questionaris) {
		this.questionaris = questionaris;
	}

	public List<RespostaPregunta> getRespostaPreguntes() {
		return respostaPreguntes;
	}

	public void setRespostaPreguntes(List<RespostaPregunta> respostaPreguntes) {
		this.respostaPreguntes = respostaPreguntes;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}
	
	/**
	 * <p>Actualitza el temps de resposta mig de contestació de la pregunta.</p>
	 * <p>precondició: l'atribut numTotalActualitzacionsTRM s'ha d'haver actualitzat prèviament</p>
	 * <p>S'actualitzarà el temps de resposta mig quan la pregunta s'hagi contestat
	 * i NO vengui d'una resposta-questionari</p>
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
	 * <p>Actualitza la nota mitja de la pregunta.</p>
	 * <p>precondició: l'atribut numTotalActualitzacionsNotaMitja s'ha d'haver actualitzat prèviament</p>
	 * <p>S'actualitzarà la nota mitja quan la pregunta s'hagi corregit</p>
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
	 * <p>Actualitza la nota mitja de la pregunta eliminant la 
	 * contribució de la notaAntiga i plasmant la de la nova nota.</p>
	 * <p>nota: l'atribut numTotalActualitzacionsNotaMitja no sofrirà canvis</p>
	 * @param notaAntiga
	 * 			nota que figura a la nota mitja de la pregunta. És la 
	 * 			nota a eliminar d'aquesta mitja
	 * @param nota
	 * 			nota que volem fer figurar a la nota mitja de la pregunta.
	 */
	public void actualitzaNotaMitja(Float notaAntiga, Float nota) {
		if (notaAntiga != null && nota != null) {
			int n = getNumTotalActualitzacionsNotaMitja();
			Float notaMitja = getNotaMitja();
			if (n > 0) setNotaMitja((notaMitja * n - notaAntiga + nota) / n);
		}
	}
	
	/**
	 * <p>Actualitza la dificultat pràctica de la pregunta.</p>
	 * <p>precondició: l'atribut numTotalActualitzacionsNotaMitja s'ha d'haver actualitzat prèviament</p>
	 * <p>S'actualitzarà la nota mitja quan la pregunta s'hagi corregit</p>
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
	 * <p>Actualitza la dificultat pràctica de la pregunta eliminant la 
	 * contribució de la notaAntiga i plasmant la de la nova nota.</p>
	 * <p>nota: l'atribut numTotalActualitzacionsNotaMitja no sofrirà canvis</p>
	 * @param notaAntiga
	 * 			nota que figura a la dificultat pràctica de la pregunta. És la 
	 * 			nota a eliminar d'aquesta mitja
	 * @param nota
	 * 			nota que volem fer figurar a la dificultat pràctica de la pregunta.
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
	 * de la pregunta.</p>
	 */
	public void incrementaNumTotalActualitzacionsNotaMitja() {
		setNumTotalActualitzacionsNotaMitja(getNumTotalActualitzacionsNotaMitja() + 1);
	}
	
	/**
	 * <p>Actualitza el número total d'actualitzacions del Temps de
	 * Resposta Mig (TRM).</p>
	 */
	public void incrementaNumTotalActualitzacionsTRM() {
		setNumTotalActualitzacionsTRM(getNumTotalActualitzacionsTRM() + 1);
	}
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  codi: "                  + codi);
        buffer.append(", enunciat: "              + enunciat);
        buffer.append(", dificultat teòrica: "    + dificultatTeorica);
        buffer.append(", dificultat pràctica: "   + dificultatPractica);
        buffer.append(", num. act. NM: "     	  + numTotalActualitzacionsNotaMitja);
        buffer.append(", num. act. TRM: "     	  + numTotalActualitzacionsTRM);
        if (notaMitja != null) buffer.append(", nota mitja: "            + notaMitja);
        if (tempsRespostaMig != null) buffer.append(", temps resposta mig: "    + tempsRespostaMig);
        buffer.append(", raonar resposta: "       + raonarResposta);
        buffer.append(", data d'alta: "           + dataAlta);
        buffer.append(", tipus: "                 + tipus);
        buffer.append(", vofVertader: "           + vofVertader);
        buffer.append(", estat: "                 + estat);
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
		Pregunta other = (Pregunta) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		return true;
	}
	
}