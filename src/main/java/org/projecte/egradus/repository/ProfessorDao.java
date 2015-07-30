package org.projecte.egradus.repository;

import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;

public interface ProfessorDao {
	
	/**
	 * Insereix un professor
	 * @param professor
	 */
	public void persistProfessor(Professor professor);
	
	/**
	 * Agafa el Professor corresponent a la persona i 
	 * assignatura que es passen com a paràmetres
	 * @param persona
	 * @param assinatura
	 * @return
	 */
	public List<Professor> getProfessor(Persona persona, Assignatura assignatura);
	
	/**
	 * Agafa tots els Professors
	 * @return
	 */
	public List<Professor> getProfessors();
	
	/**
	 * Agafa tots els perfils de Professor (de l'assignatura
	 * que sigui) que té la persona que es passa com a
	 * paràmetre
	 * @param persona
	 * @return
	 */
	public List<Professor> getProfessors(Persona persona);
	
	/**
	 * Agafa tots els Professors de l'Assignatura que
	 * es passa com a paràmetre
	 * @param assignatura
	 * @return
	 */
	public List<Professor> getProfessors(Assignatura assignatura);
}
