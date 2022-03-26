package org.projecte.egradus.repository;

import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {


	/**
	 * Agafa totes les preguntes en estat públic i les preguntes en estat editable
	 * que hagi creat el professor passat per paràmetre
	 * 
	 * @param codiProfessor
	 * @return
	 */
	public List<Pregunta> getLlistaPreguntes(int codiProfessor);

	/**
	 * Agafa totes les preguntes associades a resposta-preguntes assignades per
	 * qualsevol professor de l'assignatura que es passa per paràmetre
	 * 
	 * @param codiAssignatura
	 * @return
	 */
	public List<Pregunta> getLlistaPreguntesByAssignatura(int codiAssignatura);

	/**
	 * Insereix una pregunta
	 * 
	 * @param pregunta
	 */
	public void persistPregunta(Pregunta pregunta);

	/**
	 * Insereix una opció
	 * 
	 * @param opcio
	 */
	public void persistOpcio(Opcio opcio);

	/**
	 * Agafa la pregunta donat el seu codi
	 * 
	 * @param codi
	 * @return
	 */
	public Pregunta getPreguntaByCodi(int codi);

	/**
	 * Agafa les preguntes filtrades per els següents paràmetres tots ells
	 * opcionals:
	 * 
	 * @param dataInici
	 * @param dataFi
	 * @param enunciat
	 * @param creador
	 * @param dificultatTeorica1
	 * @param dificultatTeorica2
	 * @param dificultatPractica1
	 * @param dificultatPractica2
	 * @param raonarResposta
	 * @param tipus
	 * @param estat
	 * @param codiProfessor
	 * @return
	 */
	public List<Pregunta> getLlistaPreguntes(Date dataInici, Date dataFi, String enunciat, String creador,
			Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2,
			Boolean raonarResposta, String tipus, String estat, int codiProfessor);

	/**
	 * Agafa la llista d'opcions que té la pregunta
	 * 
	 * @param pregunta
	 * @return
	 */
	public List<Opcio> getOpcions(Pregunta pregunta);

	/**
	 * Agafa la opcio identificada pel codi passat per paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Opcio getOpcioByCodi(int codi);

	/**
	 * Actualitza una pregunta
	 * 
	 * @param pregunta
	 * @return
	 */
	public Pregunta updatePregunta(Pregunta pregunta);

	/**
	 * Elimina una pregunta
	 * 
	 * (Donam per suposat que la pregunta passada per paràmetre s'ha extret
	 * anteriorment de BD)
	 * 
	 * @param pregunta
	 */
	public void removePregunta(Pregunta pregunta);

	/**
	 * Elimina una opció
	 * 
	 * @param opcio
	 */
	public void removeOpcio(Opcio opcio);

	/**
	 * Actualitza una opció
	 * 
	 * @param opcio
	 * @return
	 */
	public Opcio updateOpcio(Opcio opcio);

}
