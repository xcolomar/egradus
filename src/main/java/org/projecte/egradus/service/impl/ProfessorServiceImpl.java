package org.projecte.egradus.service.impl;

import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.repository.ProfessorDao;
import org.projecte.egradus.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProfessorDao professorDao;
	
	// ALTERAT!!!
	public void insereixProfessor(Professor professor) {
		Assignatura assignatura = professor.getAssignatura();
		List<Professor> professors = assignatura.getProfessors();
		professors.add(professor); // obtenim els professors de l'assignatura del professor passat per paràmetre i l'afegim a ell
		assignatura.setProfessors(professors); // afegim aquesta llista de professors actualitzada a l'assignatura
		professor.setAssignatura(assignatura); // assignam l'assignatura al professor
		professorDao.persistProfessor(professor);
	}
	
	public Professor getProfessor(Persona persona, Assignatura assignatura) {
		List<Professor> professors = professorDao.getProfessor(persona, assignatura);
		if (!professors.isEmpty()) return professors.get(0);
		return null;
	}
	
	public boolean esProfessor(Persona persona, Assignatura assignatura) {
		List<Professor> professors = professorDao.getProfessor(persona, assignatura);
		return !professors.isEmpty();
	}
	
	public List<Professor> getProfessors(Assignatura assignatura) {
		List<Professor> professors = professorDao.getProfessors(assignatura);
		if (!professors.isEmpty()) return professors;
		return null;
	}

	public void setProfessorDao(ProfessorDao professorDao) {
		this.professorDao = professorDao;
	}

}
