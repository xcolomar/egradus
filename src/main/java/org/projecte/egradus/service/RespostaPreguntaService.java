package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;

public interface RespostaPreguntaService extends Serializable {
	
	/**
	 * El Professor "assignador" envia la pregunta amb codi 
	 * "codi" als alumnes que es passen com a paràmetre.
	 * 
	 * (Es retornen les resposta-preguntes generades)
	 * 
	 * @param codi
	 * @param assignador
	 * @param alumnes
	 * @param anonima
	 * 			True : la resposta-pregunta no emmagatzemarà
	 * 			l'alumne que la contesti. 
	 * 			False: la resposta-pregunta emmagatzemarà
	 * 			l'alumne que la contesti.
	 * @return
	 */
	public List<RespostaPregunta> enviaPregunta(int codi, Professor assignador, List<Alumne> alumnes, Boolean anonima);
	
	/**
	 * Mètode per inicialitzar la contestació d'una pregunta. Empleat tant per contestar una pregunta
	 * suelta com per contestar les preguntes d'un qüestionari. Per això encapsulam
	 * la problemàtica en un sol mètode, i el cridam quan faci falta.
	 * 
	 * @param rp
	 * @return
	 */
	public RespostaPregunta iniciaContestacio(RespostaPregunta rp);
	
	/**
	 * Mètode per finalitzar la contestació d'una pregunta. Empleat tant per contestar una pregunta
	 * suelta com per contestar les preguntes d'un qüestionari. Per això encapsulam
	 * la problemàtica en un sol mètode, i el cridam quan faci falta.
	 * 
	 * @param rp
	 * @param textRaonarResposta
	 * @param textResposta
	 * @param opcionsMarcades
	 * @return
	 */
	public RespostaPregunta finalitzaContestacio(RespostaPregunta rp, String textRaonarResposta, String textResposta, List<OpcioResposta> opcionsMarcades);
	
	/**
	 * Retorna la resposta-pregunta identificada pel codi passat
	 * per paràmetre.
	 * 
	 * @param codi
	 * @return
	 */
	public RespostaPregunta getRespostaPregunta(int codi);
	
	/**
	 * Retorna la llista de preguntes que té assignades l'alumne
	 * pendents per contestar.
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaPregunta> getPreguntesPerRespondre(Alumne alumne);
	
	/**
	 * Retorna la llista de preguntes que ja ha contestat l'alumne
	 * i que estan pendents de correcció manual per part del professor
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaPregunta> getPreguntesPerCorregir(Alumne alumne);
	
	/**
	 * Retorna la llista de preguntes que ja ha contestat l'alumne
	 * i que estan pendents de revisió per part del professor
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaPregunta> getPreguntesPerRevisar(Alumne alumne);
	
	/**
	 * Retorna la llista de preguntes que ja ha contestat l'alumne
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaPregunta> getPreguntesContestades(Alumne alumne);
	
	/**
	 * Actualitza la estructura resposta-pregunta per tal que quedi
	 * constància de que s'acaba d'iniciar la contestació de la pregunta
	 * 
	 * @param respostaPregunta
	 * @return
	 */
	public RespostaPregunta iniciarContestacioPregunta(RespostaPregunta respostaPregunta);
	
	/**
	 * Actualitza la estructura resposta-pregunta per tal que quedi
	 * constància de que s'acaba d'iniciar la contestació de la pregunta
	 * 
	 * NOTA: Aquesta versió del mètode fa referència a l'inici d'una
	 * resposta-pregunta que encara no s'ha creat, (ja que la pregunta no
	 * ha sigut enviada, sino el qüestionari que la conté), i per tant,
	 * en aquest mètode cream la resposta-pregunta, i procedim com el
	 * mètode anterior.
	 * 
	 * @param respostaQuestionari
	 * @param pregunta
	 * @return
	 */
	public RespostaPregunta iniciarContestacioPregunta(RespostaQuestionari respostaQuestionari, Pregunta pregunta);
	
	/**
	 * Actualitza la estructura resposta-pregunta per tal que quedi
	 * constància de que s'acaba de finalitzar la contestació de la pregunta
	 * 
	 * @param respostaPregunta
	 * @param textRaonarResposta
	 * @param textResposta
	 * @param opcionsMarcades
	 * @return
	 */
	public RespostaPregunta finalitzarContestacioPregunta(RespostaPregunta respostaPregunta, String textRaonarResposta, String textResposta, List<OpcioResposta> opcionsMarcades);
	
	/**
	 * Retorna la llista de preguntes pendents de ser corregides pel
	 * professor passat com a paràmetre així com aquelles preguntes
	 * corregides pendents de ser revisades.
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaPregunta> getPreguntesPerCorregir(Professor professor);
	
	/**
	 * Retorna la llista de preguntes corregides pel professor passat
	 * com a paràmetre
	 * 
	 * NOTA: Es retornaran només aquelles resposta-preguntes associades
	 * a preguntes de tipus REC o aquelles que hagin sigut revisades
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaPregunta> getPreguntesCorregides(Professor professor);
	
	/**
	 * Actualitza la estructura resposta-pregunta per tal que quedi
	 * constància de que s'acaba d'iniciar la correcció (o revisió) 
	 * de la pregunta
	 * 
	 * @param respostaPregunta
	 * @return
	 */
	public RespostaPregunta iniciarCorreccioPregunta(RespostaPregunta respostaPregunta);
	
	/**
	 * Actualitza la estructura resposta-pregunta per tal que quedi
	 * constància de que s'acaba de finalitzar la correcció (o revisió) 
	 * de la pregunta
	 * 
	 * @param respostaPregunta
	 * @param textCorreccio
	 * @param textRevisio
	 * @param nota
	 * @return
	 */
	public RespostaPregunta finalitzarCorreccioPregunta(RespostaPregunta respostaPregunta, String textCorreccio, String textRevisio, Float nota);
	
}
