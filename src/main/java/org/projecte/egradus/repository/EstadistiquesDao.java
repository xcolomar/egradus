package org.projecte.egradus.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Pregunta;

public interface EstadistiquesDao extends Serializable {
	
	public static final String PREGUNTA_CODI = "preguntaCodi";
	public static final String PREGUNTA_ENUNCIAT = "preguntaEnunciat";
	public static final String PREGUNTA_TIPUS = "preguntaTipus";
	public static final String PREGUNTA_PES = "preguntaPes";
	
	public static final String QUESTIONARI_CODI_RQ = "questionariCodiRq";
	public static final String QUESTIONARI_CODI = "questionariCodi";
	public static final String QUESTIONARI_NOM = "questionariNom";
	public static final String QUESTIONARI_DESCRIPCIO = "questionariDescripcio";
	
	public static final String DATA_CONTESTACIO_FI = "dataContestacioFi";
	public static final String DATA_CORRECCIO = "dataCorreccio";
	
	public static final String NOTA = "nota";
	public static final String NOTA_MITJA_ASSIGNATURA = "notaMitjaAssignatura";
	public static final String NOTA_MITJA_EGRADUS = "notaMitjaEgradus";
	
	public static final String TEMPS_RESPOSTA = "tempsResposta";
	public static final String TEMPS_RESPOSTA_MIG_ASSIGNATURA = "tempsRespostaMigAssignatura";
	public static final String TEMPS_RESPOSTA_MIG_EGRADUS = "tempsRespostaMigEgradus";
	
	public static final String DETALL = "detall";
	
	public static final String NUM_CONTESTACIONS = "numContestacions";
	
	public static final String ALUMNE_ALIES = "alumneAlies";
	public static final String ALUMNE_NOM = "alumneNom";
	public static final String ALUMNE_PRIMER_LLINATGE = "alumnePrimerLlinatge";
	public static final String ALUMNE_SEGON_LLINATGE = "alumneSegonLlinatge";
	
	/**
	 * obté les resposta-preguntes contestades per l'alumne que estiguin
	 * corregides.
	 * 
	 * NOTA: No es retornaran aquelles preguntes que l'alumne
	 * hagi contestat de manera anònima
	 * 
	 * @param alumne
	 * @return
	 */
	public List<Map<String, Object>> getRespostesPregunta(Alumne alumne);
	
	/**
	 * obté les resposta-questionaris contestades per l'alumne que estiguin
	 * corregides.
	 * 
	 * NOTA: No es retornaran aquells qüestionaris que l'alumne
	 * hagi contestat de manera anònima
	 * 
	 * @param alumne
	 * @return
	 */
	public List<Map<String, Object>> getRespostesQuestionari(Alumne alumne);
	
	/**
	 * obté les estadístiques detallades de les resposta-preguntes d'una
	 * resposta-questionari
	 * 
	 * @param codiRespostaQuestionari
	 * @return
	 */
	public List<Map<String, Object>> getDetallRespostaQuestionari(int codiRespostaQuestionari);
	
	/**
	 * obté el número d'alumnes que han tret més d'un 5 a la pregunta
	 * passada per paràmetre
	 * 
	 * @param pregunta
	 * @return
	 */
	public int getNumAlumnesAprovatsByPregunta(Pregunta pregunta);
	
	/**
	 * obté el número d'alumnes que tenen la pregunta passada per
	 * paràmetre corregida
	 * 
	 * @param pregunta
	 * @return
	 */
	public int getNumAlumnesCorregitsByPregunta(Pregunta pregunta);
	
	/**
	 * obté les estadístiques que veu el professor en referència
	 * a les preguntes sobre el conjunt d'alumnes que té a 
	 * l'assignatura de codi passat per paràmetre
	 * 
	 * @param codiAssignatura
	 * @return
	 */
	public List<Map<String, Object>> getEstadistiquesProfessorPreguntesByAssignatura(int codiAssignatura);
	
	/**
	 * obté les estadístiques 'detall' que veu el professor en referència
	 * a les preguntes sobre el conjunt d'alumnes que té a l'assignatura 
	 * de codi passat per paràmetre i de la pregunta de codi passat per 
	 * paràmetre
	 * 
	 * @param codiAssignatura
	 * @param codiPregunta
	 * @param numContestacions
	 * 			número total de contestacions a l'assignatura del 1º paràmetre
	 * 			i de la pregunta del 2º paràmetre. Ens ajudarà per a calcular
	 * 			quantes contestacions d'aquesta pregunta han sigut anònimes.
	 * @param alumneAnonim
	 * 			nom de l'alumne que es visualitzarà, pensat per a quan
	 * 			l'alumne sigui anònim
	 * @return
	 */
	public List<Map<String, Object>> getDetallEstadistiquesProfessorPreguntes(int codiAssignatura, int codiPregunta, int numContestacions, String alumneAnonim);
	
	/**
	 * obté les estadístiques que veu el professor en referència
	 * als qüestionaris sobre el conjunt d'alumnes que té a 
	 * l'assignatura de codi passat per paràmetre
	 * 
	 * @param codiAssignatura
	 * @return
	 */
	public List<Map<String, Object>> getEstadistiquesProfessorQuestionarisByAssignatura(int codiAssignatura);
	
	/**
	 * obté les estadístiques 'detall' que veu el professor en referència
	 * als qüestionaris sobre el conjunt d'alumnes que té a l'assignatura 
	 * de codi passat per paràmetre i de la pregunta de codi passat per 
	 * paràmetre
	 * 
	 * @param codiAssignatura
	 * @param codiQuestionari
	 * @param numContestacions
	 * 			número total de contestacions a l'assignatura del 1º paràmetre
	 * 			i del qüestionari del 2º paràmetre. Ens ajudarà per a calcular
	 * 			quantes contestacions d'aquest qüestionari han sigut anònims.
	 * @param alumneAnonim
	 * 			nom de l'alumne que es visualitzarà, pensat per a quan
	 * 			l'alumne sigui anònim
	 * @return
	 */
	public List<Map<String, Object>> getDetallEstadistiquesProfessorQuestionaris(int codiAssignatura, int codiQuestionari, int numContestacions, String alumneAnonim);
	
	/**
	 * obté les estadístiques que veu el professor en referència
	 * a UN qüestionari de codi passat per paràmetre sobre el conjunt 
	 * d'alumnes que té l'assignatura
	 * 
	 * @param codiQuestionari
	 * @return
	 */
	public List<Map<String, Object>> getDetallQuestionari(int codiQuestionari);
	
}
