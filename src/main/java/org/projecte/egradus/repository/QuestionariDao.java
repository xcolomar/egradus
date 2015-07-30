package org.projecte.egradus.repository;

import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Questionari;

public interface QuestionariDao {
	
	/**
	 * Agafa tots els qüestionaris
	 * @return
	 */
	public List<Questionari> getLlistaQuestionaris();
	
	/**
	 * Insereix un qüestionari
	 * @param questionari
	 */
	public void persistQuestionari(Questionari questionari);
	
	/**
	 * Actualitza un qüestionari
	 * @param questionari
	 */
	public Questionari updateQuestionari(Questionari questionari);
	
	/**
	 * Elimina un qüestionari
	 * 
	 * (Donam per suposat que el qüestionari passat per
	 * paràmetre s'ha extret anteriorment de BD)
	 * 
	 * @param questionari
	 */
	public void removeQuestionari(Questionari questionari);
	
	/**
	 * Agafa el qüestionari donat el seu codi
	 * @param codi
	 * @return
	 */
	public Questionari getQuestionariByCodi(int codi);
	
	/**
	 * Agafa tots els qüestionaris associats a resposta-questionaris
	 * assignades per qualsevol professor de l'assignatura que 
	 * es passa per paràmetre
	 * 
	 * @param codiAssignatura
	 * @return
	 */
	public List<Questionari> getLlistaQuestionarisByAssignatura(int codiAssignatura);
	
	/**
	 * Agafa els qüestionaris filtrats per els següents
	 * paràmetres tots ells opcionals:
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
	public List<Questionari> getLlistaQuestionaris(Date dataInici, Date dataFi, String nom, String descripcio, String creador, String estat, Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2, Integer numPreguntes, int codiProfessor);
	
}
