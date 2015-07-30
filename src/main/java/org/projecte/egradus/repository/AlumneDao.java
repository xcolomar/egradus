package org.projecte.egradus.repository;

import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;

public interface AlumneDao {
	
	/**
	 * Insereix un alumne
	 * 
	 * @param alumne
	 */
	public void persistAlumne(Alumne alumne);
	
	/**
	 * Agafa l'alumne corresponent a la persona i
	 * assignatura que es passen com a paràmetre
	 * 
	 * @param persona
	 * @param assignatura
	 * @return
	 */
	public List<Alumne> getAlumne(Persona persona, Assignatura assignatura);
	
	/**
	 * Agafa tots els Alumnes
	 * 
	 * @return
	 */
	public List<Alumne> getAlumnes();
	
	/**
	 * Agafa tots els perfils d'Alumne (de l'assignatura
	 * que sigui) que té la persona que es passa com a
	 * paràmetre
	 * 
	 * @param persona
	 * @return
	 */
	public List<Alumne> getAlumnes(Persona persona);
	
	/**
	 * Agafa tots els Alumnes que té l'Assignatura que
	 * es passa com a paràmetre
	 * 
	 * @param assignatura
	 * @return
	 */
	public List<Alumne> getAlumnes(Assignatura assignatura);
	
	/**
	 * Agafa l'alumne identificat amb el codi passat per paràmetre
	 * @param codi
	 * 
	 * @return
	 */
	public Alumne getAlumneByCodi(int codi);
	
}
