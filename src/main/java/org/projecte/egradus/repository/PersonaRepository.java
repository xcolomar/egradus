package org.projecte.egradus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.projecte.egradus.domain.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

	/**
	 * Agafa les persones amb l'àlies que es passa com a paràmetre, tot i que en cap
	 * cas mai no n'hi haurà més d'una
	 * 
	 * @param alies
	 * @return
	 */
	public Optional<Persona> findByAlies(String alies);

}