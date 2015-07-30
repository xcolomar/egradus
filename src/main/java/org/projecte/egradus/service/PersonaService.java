package org.projecte.egradus.service;

import java.io.Serializable;

import org.projecte.egradus.domain.Persona;

public interface PersonaService extends Serializable {
	
	/**
	 * insereix la Persona que es passa
	 * com a paràmetre
	 * @param persona
	 */
	public void insereixPersona(Persona persona);
	
	/**
	 * agafa la Persona amb l'àlies que es
	 * passa com a paràmetre
	 * @param alies
	 * @return
	 */
	public Persona getPersona(String alies);
	
}