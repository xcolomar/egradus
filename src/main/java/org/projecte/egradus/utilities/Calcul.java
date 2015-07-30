package org.projecte.egradus.utilities;

import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;

/**
 * Utilitats de càlcul com ara:
 * <ul>
 * <li>Determinar la nota corresponent a una resposta-pregunta</li>
 * </ul>
 * 
 * @author Xavier
 *
 */
public class Calcul {
	
	/**
	 * <p>retorna la nota corresponent a la resposta-pregunta acabada de
	 * contestar.<br>
	 * <i>AVIS: No es calcula nota per preguntes de tipus 'REC'</i></p>
	 * <p>Pel càlcul de la nota fa falta informació sobre:</p>
	 * <ul>
	 * 		<li><b>NT = Num. total d'opcions</b> que té la pregunta</li>
	 * 		<li><b>NC = Num. d'opcions correctes</b> que s'han definit per la pregunta</li>
	 * 		<li><b>NA = Num. d'opcions acertades</b> per l'alumne en acabar la contestació de la pregunta</li>
	 * 		<li><b>NF = Num. d'opcions fallades</b> per l'alumne en acabar la contestació de la pregunta</li>
	 * </ul>
	 * <p>La nota es calcula com: </p>
	 * <h3>Max(0, N)</h3>
	 * <p>on <b>N = (10/NC)· NA - (10/NT) · NF</b></p>
	 * @param rp
	 * @return
	 */
	public static Float notaRespostaPregunta(RespostaPregunta rp) {
		int NT = rp.getPregunta().getOpcions().size();
		int NC = 0;
		int NA = 0;
		int NF = 0;
		
		for (OpcioResposta or : rp.getOpcionsMarcades()) if (or.getOpcio().getCorrecta()) NA++;
		NF = rp.getOpcionsMarcades().size() - NA;
		for (Opcio o : rp.getPregunta().getOpcions()) if (o.getCorrecta()) NC++;
		return Math.max(0, 10f * NA/NC - 10f * NF/NT);
	}
	
	/**
	 * <p>retorna la nota corresponent a la resposta-questionari acabada de
	 * contestar<br>
	 * <i>AVIS: Només es calcularà quan s'hagin pogut calcular automàticament les
	 * notes de totes les preguntes que contenia el qüestionari contestat</i></p>
	 * <p>Pel càlcul de la nota es realitzarà una mitja ponderada en funció
	 * dels valors indicats a l'atribut 'pes' del POJO resposta-questionari</p>
	 * 
	 * @param rq
	 * @return nota ponderada (en Float) o null en cas d'existir alguna pregunta que
	 * 		no s'hagi pogut corregir automàticament
	 */
	public static Float notaRespostaQuestionari(RespostaQuestionari rq) {
		Float nota = null;
		nota = 0f;
		for (RespostaPregunta rp : rq.getRespostaPreguntes()) {
			if (rp.getNota() != null) nota += rp.getNota() * rq.getPesByPregunta(rp.getPregunta());
		}
		return nota;
	}
}
