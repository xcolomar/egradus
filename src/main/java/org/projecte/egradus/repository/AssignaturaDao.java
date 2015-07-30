package org.projecte.egradus.repository;

import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;

public interface AssignaturaDao {
	
	/**
	 * Agafa totes les assignatures
	 * 
	 * @return
	 */
	public List<Assignatura> getLlistaAssignatures();
	
	/**
	 * Agafa les assignatures creades per la persona que es passa
	 * com a paràmetre
	 * 
	 * @param persona
	 * @return
	 */
	public List<Assignatura> getLlistaAssignaturesCreador(Persona persona);
	
	/**
	 * Agafa totes les assignatures amb els filtres que es passen
	 * com a paràmetres
	 * 
	 * @param dataInici
	 * @param dataFi
	 * @param clauProfessor
	 * @param clauAlumne
	 * @param codiReferencia
	 * @param nomAssignatura
	 * @param anyAcademic
	 * @param creador
	 * @return
	 */
	public List<Assignatura> getLlistaAssignatures(Date dataInici, Date dataFi, String clauProfessor, String clauAlumne, String codiReferencia, String nomAssignatura, String anyAcademic, String creador);
	
	/**
	 * Insereix una assignatura
	 * 
	 * @param assignatura
	 */
	public void persistAssignatura(Assignatura assignatura);
	
	/**
	 * Agafa l'Assignatura amb el codi que es passa
	 * com a paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Assignatura getAssignaturaByCodi(int codi);
	
}