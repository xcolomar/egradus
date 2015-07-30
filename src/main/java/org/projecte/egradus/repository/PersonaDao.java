package org.projecte.egradus.repository;

import java.util.List;

import org.projecte.egradus.domain.Persona;

public interface PersonaDao {
	
	/**
	 * Agafa totes les Persones
	 * 
	 * @return
	 */
	public List<Persona> getPersones();
	
	/**
	 * Insereix una persona
	 * 
	 * @param persona
	 */
	public void persistPersona(Persona persona);
	
	/**
	 * Agafa les persones amb l'àlies que es
	 * passa com a paràmetre, tot i que en cap
	 * cas mai no n'hi haurà més d'una
	 * 
	 * @param alies
	 * @return
	 */
	public List<Persona> getPersonesByAlies(String alies);
	
	/**
	 * Agafa la persona identificada amb el
	 * codi passat per paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Persona getPersona(Integer codi);
	
}