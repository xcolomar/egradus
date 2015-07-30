package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;

public interface RespostaQuestionariService extends Serializable {
	
	/**
	 * Recuperam el qüestionari (i les seves preguntes) associat a 
	 * aquesta resposta-qüestionari
	 * 
	 * @param rq
	 * @return
	 */
	public Questionari getQuestionariAssociat(RespostaQuestionari rq);
	
	/**
	 * El Professor "assignador" envia el qüestionari amb codi 
	 * "codi" als alumnes que es passen com a paràmetre.
	 * 
	 * (Es retornen les resposta-questionaris generades)
	 * 
	 * @param codi
	 * @param assignador
	 * @param alumnes
	 * @param anonim
	 * 			True : la resposta-questionari no emmagatzemarà
	 * 			l'alumne que la contesti. 
	 * 			False: la resposta-questionari emmagatzemarà
	 * 			l'alumne que la contesti.
	 * @return
	 */
	public List<RespostaQuestionari> enviaQuestionari(int codi, Professor assignador, List<Alumne> alumnes, Boolean anonim);
	
	/**
	 * Retorna la resposta-questionari identificada pel codi passat
	 * per paràmetre.
	 * 
	 * @param codi
	 * @return
	 */
	public RespostaQuestionari getRespostaQuestionari(int codi);
	
	/**
	 * Retorna la llista de qüestionaris que té assignats l'alumne
	 * pendents per contestar.
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaQuestionari> getQuestionarisPerRespondre(Alumne alumne);
	
	/**
	 * Retorna la llista de qüestionaris que ja ha contestat l'alumne
	 * i que estan pendents de correcció manual per part del professor
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaQuestionari> getQuestionarisPerCorregir(Alumne alumne);
	
	/**
	 * Retorna la llista de qüestionaris que ja ha contestat l'alumne
	 * i que estan pendents de revisió per part del professor
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaQuestionari> getQuestionarisPerRevisar(Alumne alumne);
	
	/**
	 * Retorna la llista de qüestionaris que ja ha contestat l'alumne
	 * 
	 * @param alumne
	 * @return
	 */
	public List<RespostaQuestionari> getQuestionarisContestats(Alumne alumne);
	
	/**
	 * Actualitza la estructura resposta-questionari per tal que quedi
	 * constància de que s'acaba d'iniciar la contestació del qüestionari
	 * 
	 * @param respostaQuestionari
	 * @return
	 */
	public RespostaQuestionari iniciarContestacioQuestionari(RespostaQuestionari respostaQuestionari);
	
	/**
	 * Actualitza la estructura resposta-questionari per tal que quedi
	 * constància de que s'acaba de finalitzar la contestació del qüestionari.
	 * 
	 * @param respostaQuestionari
	 * @param llistaRespostaPreguntes
	 * 				resposta-preguntes pendents d'assignar a la resposta-questionari
	 * @return resposta-questionari amb el llistat de resposta-preguntes assignat al
	 * seu atribut 'respostaPreguntes'
	 */
	public RespostaQuestionari finalitzarContestacioQuestionari(RespostaQuestionari respostaQuestionari, List<RespostaPregunta> llistaRespostaPreguntes);
	
	/**
	 * Retorna la llista de qüestionaris pendents de ser corregits o revisats pel
	 * professor passat com a paràmetre
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaQuestionari> getQuestionarisPerCorregir(Professor professor);
	
	/**
	 * Retorna la llista de qüestionaris corregits pel professor passat
	 * com a paràmetre
	 * NOTA: Es retornaran només aquelles resposta-questionaris que contenguin
	 * alguna resposta-pregunta associada a preguntes de tipus REC o aquelles
	 * que hagin sigut revisades
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaQuestionari> getQuestionarisCorregits(Professor professor);
	
	/**
	 * Actualitza la estructura resposta-questionari per tal que quedi
	 * constància de que s'acaba d'iniciar la correcció manual del qüestionari
	 * 
	 * @param respostaQuestionari
	 * @return
	 */
	public RespostaQuestionari iniciarCorreccioQuestionari(RespostaQuestionari respostaQuestionari);
	
	/**
	 * Actualitza la estructura resposta-questionari per tal que quedi
	 * constància de que s'acaba de finalitzar la correcció manual del qüestionari
	 * 
	 * NOTA: La idea és que a aquest punt, les preguntes REC del qüestionari 
	 * seran corregides i les RR revisades.
	 * 
	 * @param respostaQuestionari
	 * @param listTextCorreccio
	 * @param listTextRevisio
	 * @param listNota
	 * @return
	 */
	public RespostaQuestionari finalitzarCorreccioQuestionari(RespostaQuestionari respostaQuestionari, List<String> listTextCorreccio, List<String> listTextRevisio, List<Float> listNota);
	
}
