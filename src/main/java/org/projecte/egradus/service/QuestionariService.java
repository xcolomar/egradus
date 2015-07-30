package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Questionari;

public interface QuestionariService extends Serializable {
	
	/**
	 * Insereix el qüestionari indicat, assignant-li les preguntes
	 * que es passen com a paràmetre
	 * 
	 * @param questionari
	 * @param preguntes
	 * @param pesos
	 * 			pesos és un array de tuples (codiPregunta, pesEnElQuestionari)
	 */
	public void insereixQuestionari(Questionari questionari, List<Pregunta> preguntes, Map<Integer, Float> pesos);
	
	/**
	 * Agafa tots els qüestionaris
	 * 
	 * @return
	 */
	public List<Questionari> getQuestionaris();
	
	/**
	 * Agafa el qüestionari amb el codi que
	 * es passa com a paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Questionari getQuestionari(int codi);
	
	/**
	 * Agafa els qüestionaris associats a resposta-questionaris
	 * assignades per qualsevol Professor de l'Assignatura
	 * passada com a paràmetre
	 * 
	 * @param assignatura
	 * @return
	 */
	public List<Questionari> getQuestionaris(Assignatura assignatura);
	
	/**
	 * Agafa els qüestionaris amb la possibilitat de
	 * filtrar per els següents paràmetres:
	 * 
	 * @param dataInici
	 * @param dataFi
	 * @param nom
	 * @param descripcio
	 * @param creador
	 * @param estat
	 * @param dificultatTeorica1
	 * @param dificultatTeorica2
	 * @param dificultatPractica1
	 * @param dificultatPractica2
	 * @param numPreguntes
	 * @param codiProfessor
	 * @return
	 */
	public List<Questionari> getQuestionaris(Date dataInici, Date dataFi, String nom, String descripcio, String creador, String estat, Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2, Integer numPreguntes, int codiProfessor);
	
	/**
	 * Actualitza l'estat Editable del qüestionari en
	 * estat Públic
	 * 
	 * @param codi
	 * @return
	 */
	public Questionari publicaQuestionari(int codi);
	
	/**
	 * Elimina el qüestionari.
	 * 
	 * (Retorna l'objecte Java questionari, però el qüestionari
	 * ja s'haurà eliminat de la BD)
	 * 
	 * @param codi
	 * @return
	 */
	public Questionari eliminaQuestionari(int codi);
	
	/**
	 * Actualitza un qüestionari (qualsevol dels seus camps excepte l'estat, la data
	 * de creació i el professor creador del qüestionari).
	 * 
	 * @param codiQuestionari
	 * 			El codi de qüestionari passat per paràmetre és el codi del qüestionari dins BD que hem de substituir
	 * @param questionari
	 * 			El POJO questionari passat per paràmetre conté els valors nous que haurà de tenir el qüestionari.
	 * @param preguntes
	 * 			El llistat de preguntes són les noves preguntes que ha de tenir el qüestionari (pot venir buit)
	 * @param pesos
	 * 			El HashMap de pesos són les noves tuples <codiPregunta, pes> que indicaran quin pès té cada pregunta
	 * @return
	 */
	public Questionari modificaQuestionari(int codiQuestionari, Questionari questionari, List<Pregunta> preguntes, Map<Integer, Float> pesos);
	
}
