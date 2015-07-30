package org.projecte.egradus.service.impl;

import java.util.List;

import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.repository.PersonaDao;
import org.projecte.egradus.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonaServiceImpl implements PersonaService {
	
	private static final long serialVersionUID = 1L;
    
    @Autowired
    private PersonaDao personaDao;
	
    public void setPersonaDao(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }
    
	public void insereixPersona(Persona persona){
		personaDao.persistPersona(persona);
	}
	
	public Persona getPersona(String alies){
		List<Persona> llista = personaDao.getPersonesByAlies(alies);
		if (!llista.isEmpty()) return llista.get(0);
		return null;
	}
	
}