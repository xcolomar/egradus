package org.projecte.egradus.repository;

import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;

public interface RespostaQuestionariDao {
	
	/**
	 * Insereix una resposta d'un qüestionari
	 * 
	 * @param respostaQuestionari
	 */
	public void persistRespostaQuestionari(RespostaQuestionari respostaQuestionari);
	
	/**
	 * Retorna la resposta-qüestionari identificada pel codi passat 
	 * per paràmetre.
	 * 
	 * @param codi
	 * @return
	 */
	public RespostaQuestionari getRespostaQuestionariByCodi(int codi);
	
	/**
	 * Retorna la resposta-questionari corresponent als paràmetres següents:
	 * 
	 * @param questionari
	 * @param assignador
	 * @param contestador
	 * @return
	 */
	public List<RespostaQuestionari> getRespostaQuestionari(Questionari questionari, Professor assignador, Alumne contestador);
	
	/**
	 * Retorna les resposta-questionaris que té un alumne, poguent-les filtrar per si estan
	 * o no contestades, per si estan o no corregides i per si estan o no revisades.
	 * 
	 * @param alumne
	 * @param contestades
	 * @param corregides
	 * @param revisades
	 * @return
	 */
	public List<RespostaQuestionari> getRespostesQuestionari(Alumne alumne, Boolean contestades, Boolean corregides, Boolean revisades);
	
	/**
	 * Actualitza una respostaQuestionari
	 * 
	 * @param respostaQuestionari
	 * @return
	 */
	public RespostaQuestionari updateRespostaQuestionari(RespostaQuestionari respostaQuestionari);
	
	/**
	 * Retorna les resposta-questionaris que té pendents
	 * de corregir i revisar un professor
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaQuestionari> getRespostesQuestionariPendentsCorregir(Professor professor);
	
	/**
	 * Retorna les resposta-questionaris que té corregides
	 * i revisades un professor
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaQuestionari> getRespostesQuestionariCorregides(Professor professor);
	
	/**
	 * Retorna les resposta-questionaris que té un professor, poguent-les filtrar per si estan
	 * o no corregides i per si estan o no revisades.
	 * 
	 * @param professor
	 * @param corregides
	 * @param revisades
	 * @return
	 */
	public List<RespostaQuestionari> getRespostesQuestionari(Professor professor, Boolean corregides, Boolean revisades);
	
	/**
	 * Retorna el llistat de resposta-preguntes que té vinculada la 
	 * resposta-questionari passada per paràmetre
	 * 
	 * @param respostaQuestionari
	 * @return
	 */
	public List<RespostaPregunta> getRespostaPreguntes(RespostaQuestionari respostaQuestionari);
	
	/**
	 * Retorna les respostes que té un qüestionari dins l'àmbit de l'assignatura
	 * que es passa com a paràmetre
	 * 
	 * @param questionari
	 * @param assignatura
	 * @param contestats
	 * 				Boolean que indica si les resposta-questionari han d'estar contestades
	 * 				Si és null, no filtra
	 * @param corregits
	 * 				Boolean que indica si les resposta-questionaris han d'estar corregides
	 * 				Si és null, no filtra
	 * @param assignacioDirecta
	 * 				Paràmetre que no aporta res, però permet distingir del mètode getRespostesQuestionari(Alumne alumne, ...)
	 *  			amb 4 paràmetres per quan s'hagin de fer crides amb els 4 paràmetres a null 
	 *  			volguent-mos referir a n'aquest mètode i que no es confongui. Aquest 5º paràmetre 
	 *  			sempre estarà alimentat, i no es confondrà de mètode
	 * @return
	 */
	public List<RespostaQuestionari> getRespostesQuestionari(Questionari questionari, Assignatura assignatura, Boolean contestats, Boolean corregits, Boolean assignacioDirecta);
}
