package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;

public interface ProfessorService extends Serializable {
	
	/**
	 * insereix el Professor que es passa 
	 * com a paràmetre
	 * @param professor
	 */
	public void insereixProfessor(Professor professor);
	
	/**
	 * agafa el perfil de Professor de la Persona
	 * i Assignatura que es passen com a paràmetres
	 * @param persona
	 * @param assignatura
	 * @return
	 */
	public Professor getProfessor(Persona persona, Assignatura assignatura);
	
	/**
	 * consulta si la Persona té un perfil de Professor
	 * a l'Assignatura
	 * @param persona
	 * @param assignatura
	 * @return
	 */
	public boolean esProfessor(Persona persona, Assignatura assignatura);
	
	/**
	 * agafa tots els perfils de Professor de l'Assignatura
	 * que es passa com a paràmetre
	 * @param assignatura
	 * @return
	 */
	public List<Professor> getProfessors(Assignatura assignatura);
	
}
