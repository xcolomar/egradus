package org.projecte.egradus.service.impl;

import java.util.List;
import java.util.Optional;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.repository.AlumneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlumneServiceImpl {

	@Autowired
	private AlumneRepository alumneRepositori;

	/**
	 * insereix l'alumne que es passa com a paràmetre
	 * 
	 * @param alumne
	 */
	public void insereixAlumne(Alumne alumne) {
		alumneRepositori.saveAndFlush(alumne);
	}

	/**
	 * consulta si la Persona té un perfil d'Alumne a l'Assignatura
	 * 
	 * @param persona
	 * @param assignatura
	 * @return
	 */
	public boolean esAlumne(Persona persona, Assignatura assignatura) {
		List<Alumne> alumnes = alumneRepositori.findByPersonaAndAssignatura(persona, assignatura);
		return !alumnes.isEmpty();
	}

	/**
	 * retorna els alumnes de l'assignatura que es passa com a paràmetre
	 * 
	 * @param assignatura
	 * @return
	 */
	public List<Alumne> getAlumnes(Assignatura assignatura) {
		List<Alumne> llista = alumneRepositori.findByAssignatura(assignatura);
		if (!llista.isEmpty())
			return llista;
		return null;
	}

	public void setAlumneDao(AlumneRepository alumneDao) {
		this.alumneRepositori = alumneDao;
	}

	/**
	 * retorna l'alumne amb codi passat per paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Optional<Alumne> getAlumne(Integer codi) {
		return alumneRepositori.findById(codi);
	}

	/**
	 * retorna l'alumne identificat per la persona i assignatura que es passen com a
	 * paràmetre
	 * 
	 * @param persona
	 * @param assignatura
	 * @return
	 */
	public Optional<Alumne> getAlumne(Persona persona, Assignatura assignatura) {
		List<Alumne> alumnes = alumneRepositori.findByPersonaAndAssignatura(persona, assignatura);
		if (!alumnes.isEmpty())
			return Optional.of(alumnes.get(0));
		return Optional.empty();
	}

}
