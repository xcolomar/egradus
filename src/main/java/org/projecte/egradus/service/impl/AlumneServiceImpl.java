package org.projecte.egradus.service.impl;

import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.repository.AlumneDao;
import org.projecte.egradus.repository.AssignaturaDao;
import org.projecte.egradus.repository.PersonaDao;
import org.projecte.egradus.service.AlumneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlumneServiceImpl implements AlumneService {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AlumneDao alumneDao;
	
	@Autowired
	private PersonaDao personaDao;
	
	@Autowired
	private AssignaturaDao assignaturaDao;
	
	public void insereixAlumne(Alumne alumne) {
		alumneDao.persistAlumne(alumne);
	}

	public boolean esAlumne(Persona persona, Assignatura assignatura) {
		List<Alumne> alumnes = alumneDao.getAlumne(persona, assignatura);
		return !alumnes.isEmpty();
	}
	
	public List<Alumne> getAlumnes(Assignatura assignatura) {
		List<Alumne> llista = alumneDao.getAlumnes(assignatura);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public void setAlumneDao(AlumneDao alumneDao) {
		this.alumneDao = alumneDao;
	}

	public Alumne getAlumne(int codi) {
		return alumneDao.getAlumneByCodi(codi);
	}
	
	public Alumne getAlumne(Persona persona, Assignatura assignatura) {
		List<Alumne> alumnes = alumneDao.getAlumne(persona, assignatura);
		if (!alumnes.isEmpty()) return alumnes.get(0);
		return null;
	}
	
}
