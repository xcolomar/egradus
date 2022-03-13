package org.projecte.egradus.repository;

import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignaturaRepository extends JpaRepository<Assignatura, Integer> {

	public List<Assignatura> findByPersona(Persona persona);

	/**
	 * Agafa totes les assignatures amb els filtres que es passen com a paràmetres
	 * 
	 * @param dataInici
	 * @param dataFi
	 * @param clauProfessor
	 * @param clauAlumne
	 * @param codiReferencia
	 * @param nomAssignatura
	 * @param anyAcademic
	 * @param creador
	 * @return
	 */
	@Query("")
	public List<Assignatura> getLlista(Date dataInici, Date dataFi, String clauProfessor, String clauAlumne,
			String codiReferencia, String nomAssignatura, String anyAcademic, String creador);

}