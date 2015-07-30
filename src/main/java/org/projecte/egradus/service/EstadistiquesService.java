package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.projecte.egradus.domain.Alumne;

public interface EstadistiquesService extends Serializable {

	public static final String PREGUNTA_ENVIADES_ASS = "preguntaEnviadesAss";
	public static final String PREGUNTA_ENVIADES_EGR = "preguntaEnviadesEgr";
	public static final String PREGUNTA_CONTESTADES_ASS = "preguntaContestadesAss";
	public static final String PREGUNTA_CONTESTADES_EGR = "preguntaContestadesEgr";
	public static final String PREGUNTA_CORREGIDES_ASS = "preguntaCorregidesAss";
	public static final String PREGUNTA_CORREGIDES_EGR = "preguntaCorregidesEgr";
	public static final String PREGUNTA_APROVADES_ASS = "preguntaAprovadesAss";
	public static final String PREGUNTA_APROVADES_EGR = "preguntaAprovadesEgr";
	public static final String PREGUNTA_NOTA_MITJA_ASS = "preguntaNotaMitjaAss";
	public static final String PREGUNTA_NOTA_MITJA_EGR = "preguntaNotaMitjaEgr";
	public static final String PREGUNTA_TEMPS_RESPOSTA_MIG_ASS = "preguntaTempsRespostaMigAss";
	public static final String PREGUNTA_TEMPS_RESPOSTA_MIG_EGR = "preguntaTempsRespostaMigEgr";
	
	public static final String QUESTIONARI_ENVIATS_ASS = "questionariEnviatsAss";
	public static final String QUESTIONARI_ENVIATS_EGR = "questionariEnviatsEgr";
	public static final String QUESTIONARI_CONTESTATS_ASS = "questionariContestatsAss";
	public static final String QUESTIONARI_CONTESTATS_EGR = "questionariContestatsEgr";
	public static final String QUESTIONARI_CORREGITS_ASS = "questionariCorregitsAss";
	public static final String QUESTIONARI_CORREGITS_EGR = "questionariCorregitsEgr";
	public static final String QUESTIONARI_APROVATS_ASS = "questionariAprovatsAss";
	public static final String QUESTIONARI_APROVATS_EGR = "questionariAprovatsEgr";
	public static final String QUESTIONARI_NOTA_MITJA_ASS = "questionariNotaMitjaAss";
	public static final String QUESTIONARI_NOTA_MITJA_EGR = "questionariNotaMitjaEgr";
	public static final String QUESTIONARI_TEMPS_RESPOSTA_MIG_ASS = "questionariTempsRespostaMigAss";
	public static final String QUESTIONARI_TEMPS_RESPOSTA_MIG_EGR = "questionariTempsRespostaMigEgr";
	
	/**
	 * Estadístiques que veu l'alumne sobre les preguntes
	 * que ha contestat (i que estiguin corregides)
	 * 
	 * NOTA: No es retornaran aquelles preguntes que l'alumne
	 * hagi contestat de manera anònima
	 * 
	 * @param alumne
	 * @return
	 */
	public List<Map<String, Object>> getPreguntesAlumne(Alumne alumne);
	
	/**
	 * Estadístiques que veu l'alumne sobre els qüestionaris
	 * que ha contestat (i que estiguin corregits)
	 * 
	 * NOTA: No es retornaran aquells qüestionaris que l'alumne
	 * hagi contestat de manera anònima
	 * 
	 * @param alumne
	 * @return
	 */
	public List<Map<String, Object>> getQuestionarisAlumne(Alumne alumne);
	
	/**
	 * Estadístiques detallades que veu l'alumne sobre un
	 * dels qüestionaris que ha contestat
	 * 
	 * @param codiRespostaQuestionari
	 * @return
	 */
	public List<Map<String, Object>> getEstadistiquesDetallRespostaQuestionari(int codiRespostaQuestionari);
	
	/**
	 * Estadístiques que el professor veu sobre una pregunta
	 * d'assignació directa (que no venien de qüestionaris).
	 * 
	 * @param codiPregunta
	 * @param codiAssignatura
	 * @return
	 */
	public Map<String, Object> getEstadistiquesProfessorPreguntaAssignacioDirecta(int codiPregunta, int codiAssignatura);
	
	/**
	 * Estadístiques que el professor veu sobre una pregunta
	 * d'assignació a través de qüestionari.
	 * 
	 * @param codiPregunta
	 * @param codiAssignatura
	 * @return
	 */
	public Map<String, Object> getEstadistiquesProfessorPreguntaAssignacioQuestionari(int codiPregunta, int codiAssignatura);
	
	/**
	 * Estadístiques que el professor veu sobre el conjunt
	 * de les preguntes d'assignació directa (que no venien 
	 * de qüestionaris) que han participat en l'assignatura.
	 * 
	 * @param codiAssignatura
	 * @return
	 */
	public Map<String, Object> getEstadistiquesProfessorConjuntPreguntesAssignacioDirecta(int codiAssignatura);
	
	/**
	 * Estadístiques que el professor veu sobre el conjunt
	 * de les preugntes d'assignació a través de qüestionari
	 * que han participat en l'assignatura.
	 * 
	 * @param codiAssignatura
	 * @return
	 */
	public Map<String, Object> getEstadistiquesProfessorConjuntPreguntesAssignacioQuestionari(int codiAssignatura);
	
	/**
	 * Estadístiques que el professor veu sobre un qüestionari
	 * 
	 * @param codiQuestionari
	 * @param codiAssignatura
	 * @return
	 */
	public Map<String, Object> getEstadistiquesProfessorQuestionari(int codiQuestionari, int codiAssignatura);
	
	/**
	 * Estadístiques que el professor veu sobre el conjunt
	 * dels qüestionaris que han participat en l'assignatura.
	 * 
	 * @param codiAssignatura
	 * @return
	 */
	public Map<String, Object> getEstadistiquesProfessorConjuntQuestionaris(int codiAssignatura);
	
	/**
	 * Estadístiques que veu el professor sobre els alumnes
	 * en relació a les preguntes
	 * (Valors mitjos de l'assignatura)
	 * 
	 * Obté Un List de Maps amb les estadístiques que veu el professor
	 * sobre el conjunt d'alumnes de la seva assignatura
	 * 
	 * @param codiAssignatura
	 * @param locale
	 * 			utilitzat per a recollir el missatge idiomàtic (segons el Locale)
	 * 			que ens permetrà etiquetar els alumnes anònims amb l'idioma
	 * 			actual
	 * @return
	 */
	public List<Map<String, Object>> getEstadistiquesProfessorPreguntes(int codiAssignatura, Locale locale);
	
	/**
	 * Estadístiques que veu el professor sobre els alumnes
	 * en relació als qüestionaris
	 * (Valors mitjos de l'assignatura)
	 * 
	 * Obté Un List de Maps amb les estadístiques que veu el professor
	 * sobre el conjunt d'alumnes de la seva assignatura
	 * 
	 * @param codiAssignatura
	 * @param locale
	 * 			utilitzat per a recollir el missatge idiomàtic (segons el Locale)
	 * 			que ens permetrà etiquetar els alumnes anònims amb l'idioma
	 * 			actual
	 * @return
	 */
	public List<Map<String, Object>> getEstadistiquesProfessorQuestionaris(int codiAssignatura, Locale locale);
	
	/**
	 * Estadístiques que veu el professor sobre els alumnes
	 * en relació a UN qüestionari
	 * (Valors mitjos de l'assignatura)
	 * 
	 * Obté un List de Maps amb les estadístiques que veu el professor
	 * sobre el conjunt d'alumnes de la seva assignatura centrades
	 * en les preguntes d'UN qüestionari
	 * 
	 * @param codiQuestionari
	 * @return
	 */
	public List<Map<String, Object>> getEstadistiquesConjuntAlumnesDetallQuestionari(int codiQuestionari);
	
}
