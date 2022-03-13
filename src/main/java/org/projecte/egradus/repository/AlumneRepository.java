package org.projecte.egradus.repository;

import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumneRepository extends JpaRepository<Alumne, Integer> {

	public List<Alumne> findByPersonaAndAssignatura(Persona persona, Assignatura assignatura);

	public List<Alumne> findByPersona(Persona persona);

	public List<Alumne> findByAssignatura(Assignatura assignatura);

}
