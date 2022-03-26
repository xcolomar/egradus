package org.projecte.egradus.service.impl;

import java.util.Optional;

import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonaServiceImpl {

	@Autowired
	private PersonaRepository personaRepositori;

	/**
	 * 
	 * @param persona
	 */
	public void insereixPersona(Persona persona) {
		personaRepositori.saveAndFlush(persona);
	}

	/**
	 * 
	 * @param alies
	 * @return
	 */
	public Optional<Persona> getPersona(String alies) {
		return personaRepositori.findByAlies(alies);
	}

}