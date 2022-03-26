package org.projecte.egradus.service.impl;

import java.util.List;
import java.util.Optional;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfessorServiceImpl {

	@Autowired
	private ProfessorRepository professorRepositori;

	/**
	 * @deprecated resoldre-ho des del model, a nivell d'assignatura.
	 * 
	 *             Procedir així: 1) obtenir assignatura per codi 2) add del
	 *             professor (indicar paràmetres que identifiquen el professor) 3)
	 *             persistir l'assignatura
	 * 
	 *             Per tant el mètode hauria de ser d'assignaturaService.
	 * 
	 * @param professor
	 */
	@Deprecated
	public void insereixProfessor(Professor professor) {
		Assignatura assignatura = professor.getAssignatura();
		List<Professor> professors = assignatura.getProfessors();
		professors.add(professor); // obtenim els professors de l'assignatura del professor passat per paràmetre i
									// l'afegim a ell
		assignatura.setProfessors(professors); // afegim aquesta llista de professors actualitzada a l'assignatura
		professor.setAssignatura(assignatura); // assignam l'assignatura al professor
		professorRepositori.saveAndFlush(professor);
	}

	/**
	 * 
	 * @param personaCodi
	 * @param assignaturaCodi
	 * @return
	 */
	public Optional<Professor> getProfessor(Integer personaCodi, Integer assignaturaCodi) {
		return professorRepositori.findByPersonaCodiAndAssignaturaCodi(personaCodi, assignaturaCodi);
	}

	/**
	 * 
	 * @param personaCodi
	 * @param assignaturaCodi
	 * @return
	 */
	public boolean isProfessor(Integer personaCodi, Integer assignaturaCodi) {
		return professorRepositori.findByPersonaCodiAndAssignaturaCodi(personaCodi, assignaturaCodi).isPresent();
	}

	/**
	 * 
	 * @param assignaturaCodi
	 * @return
	 */
	public List<Professor> getProfessors(Integer assignaturaCodi) {
		List<Professor> professors = professorRepositori.findByAssignaturaCodi(assignaturaCodi);
		if (!professors.isEmpty())
			return professors;
		return null;
	}

}
