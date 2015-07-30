package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;

public interface AssignaturaService extends Serializable {
	
	/**
	 * Insereix l'Assignatura que es passa com a
	 * paràmetre
	 * @param assignatura
	 */
	public void insereixAssignatura(Assignatura assignatura);
	
	/**
	 * Agafa l'assignatura amb el codi (PK) que
	 * es passa com a paràmetre
	 * @param codi
	 * @return
	 */
	public Assignatura getAssignatura(int codi);
	
	/**
	 * Agafa les assignatures en les quals la persona
	 * que es passa com a paràmetre és professor o
	 * alumne, és a dir, totes les assignatures en les
	 * que intervé la persona.
	 * @param persona
	 * @return
	 */
	public List<Assignatura> getAssignatures(Persona persona);
	
	/**
	 * Agafa les assignatures amb possibilitat de filtrar per
	 * els següents paràmetres:
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
	public List<Assignatura> getAssignatures(Date dataInici, Date dataFi, String clauProfessor, String clauAlumne, String codiReferencia, String nomAssignatura, String anyAcademic, String creador);
	
	/**
	 * Agafa totes les assignatures
	 * @return
	 */
	public List<Assignatura> getAssignatures();
	
}
