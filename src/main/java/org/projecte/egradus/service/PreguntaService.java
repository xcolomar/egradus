package org.projecte.egradus.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.Pregunta;

public interface PreguntaService extends Serializable {

	/**
	 * Insereix la pregunta que es passa com a
	 * paràmetre
	 * 
	 * @param pregunta
	 * @param opcions
	 */
	public void insereixPregunta(Pregunta pregunta, List<Opcio> opcions);
	
	/**
	 * Agafa totes les preguntes
	 * 
	 * @return
	 */
	public List<Pregunta> getPreguntes();
	
	/**
	 * Agafa totes les preguntes en estat públic i
	 * les preguntes en estat editable que hagi creat
	 * el professor que es passa com a paràmetre
	 * 
	 * @param codiProfessor
	 * @return
	 */
	public List<Pregunta> getPreguntes(int codiProfessor);
	
	/**
	 * Agafa la pregunta amb el codi que
	 * es passa com a paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Pregunta getPregunta(int codi);
	
	/**
	 * Agafa les preguntes amb la possibilitat de
	 * filtrar per els següents paràmetres:
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
	public List<Pregunta> getPreguntes(Date dataInici, Date dataFi, String enunciat, String creador, Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2, Boolean raonarResposta, String tipus, String estat, int codiProfessor);
	
	/**
	 * Agafa les preguntes associades a resposta-preguntes
	 * assignades per qualsevol Professor de l'Assignatura
	 * passada com a paràmetre
	 * 
	 * @param assignatura
	 * @return
	 */
	public List<Pregunta> getPreguntes(Assignatura assignatura);
	
	/**
	 * Agafa la opció identificada amb el codi
	 * passat per paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Opcio getOpcio(int codi);
	
	/**
	 * Actualitza l'estat Editable de la pregunta en
	 * estat Públic
	 * 
	 * @param codi
	 * @return
	 */
	public Pregunta publicaPregunta(int codi);
	
	/**
	 * Elimina la pregunta.
	 * 
	 * (Retorna l'objecte Java pregunta, però la pregunta
	 * ja s'haurà eliminat de la BD)
	 * 
	 * @param codi
	 * @return
	 */
	public Pregunta eliminaPregunta(int codi);
	
	/**
	 * Actualitza una pregunta (qualsevol dels seus camps excepte l'estat, la data
	 * de creació i el professor creador de la pregunta).
	 * 
	 * @param codiPregunta
	 * 			El codi de pregunta passat per paràmetre és el codi de la pregunta dins BD que hem de substituir
	 * @param pregunta
	 * 			El POJO pregunta passat per paràmetre conté els valors nous que haurà de tenir la pregunta.
	 * @param opcions
	 * 			El llistat d'opcions són les noves opcions que ha de tenir la pregunta (pot venir buit)
	 * @return
	 */
	public Pregunta modificaPregunta(int codiPregunta, Pregunta pregunta, List<Opcio> opcions);
	
}
