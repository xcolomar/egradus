package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;

public interface AlumneService extends Serializable {
	
	/**
	 * insereix l'alumne que es passa
	 * com a paràmetre
	 * @param alumne
	 */
	public void insereixAlumne(Alumne alumne);
	
	/**
	 * consulta si la Persona té un perfil d'Alumne
	 * a l'Assignatura
	 * @param persona
	 * @param assignatura
	 * @return
	 */
	public boolean esAlumne(Persona persona, Assignatura assignatura);
	
	/**
	 * retorna els alumnes de l'assignatura que es
	 * passa com a paràmetre
	 * @param assignatura
	 * @return
	 */
	public List<Alumne> getAlumnes(Assignatura assignatura);
	
	/**
	 * retorna l'alumne amb codi passat per paràmetre
	 * @param codi
	 * @return
	 */
	public Alumne getAlumne(int codi);
	
	/**
	 * retorna l'alumne identificat per la persona i assignatura
	 * que es passen com a paràmetre
	 * @param persona
	 * @param assignatura
	 * @return
	 */
	public Alumne getAlumne(Persona persona, Assignatura assignatura);
	
}
