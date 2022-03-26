package org.projecte.egradus.repository;

import java.util.List;
import java.util.Optional;

import org.projecte.egradus.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

	/**
	 * Agafa el Professor corresponent a la persona i assignatura que es passen com
	 * a paràmetres
	 * 
	 * @param personaCodi
	 * @param assinaturaCodi
	 * @return
	 */
	public Optional<Professor> findByPersonaCodiAndAssignaturaCodi(Integer personaCodi, Integer assignaturaCodi);

	/**
	 * Agafa tots els perfils de Professor (de l'assignatura que sigui) que té la
	 * persona que es passa com a paràmetre
	 * 
	 * @param persona
	 * @return
	 */
	public List<Professor> findByPersonaCodi(Integer personaCodi);

	/**
	 * Agafa tots els Professors de l'Assignatura que es passa com a paràmetre
	 * 
	 * @param assignatura
	 * @return
	 */
	public List<Professor> findByAssignaturaCodi(Integer assignaturaCodi);
}
