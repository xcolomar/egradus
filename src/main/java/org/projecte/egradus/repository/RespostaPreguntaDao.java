package org.projecte.egradus.repository;

import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;

public interface RespostaPreguntaDao {
	
	/**
	 * Insereix una resposta d'una pregunta
	 * 
	 * @param respostaPregunta
	 */
	public void persistRespostaPregunta(RespostaPregunta respostaPregunta);
	
	/**
	 * Insereix la opció que es marca quan l'alumne contesta
	 * la resposta-pregunta: la opcio-resposta
	 * 
	 * @param opcioResposta
	 */
	public void persistOpcioResposta(OpcioResposta opcioResposta);
	
	/**
	 * Retorna la resposta-pregunta identificada pel codi passat 
	 * per paràmetre.
	 * 
	 * @param codi
	 * @return
	 */
	public RespostaPregunta getRespostaPreguntaByCodi(int codi);
	
	/**
	 * Retorna la resposta-pregunta corresponent als paràmetres següents:
	 * 
	 * @param pregunta
	 * @param assignador
	 * @param contestador
	 * @return
	 */
	public List<RespostaPregunta> getRespostaPregunta(Pregunta pregunta, Professor assignador, Alumne contestador);
	
	/**
	 * Retorna les resposta-preguntes que té un alumne, poguent-les filtrar per si estan
	 * o no contestades, per si estan o no corregides i per si estan o no revisades.
	 * 
	 * @param alumne
	 * @param contestades
	 * @param corregides
	 * @param revisades
	 * @return
	 */
	public List<RespostaPregunta> getRespostesPregunta(Alumne alumne, Boolean contestades, Boolean corregides, Boolean revisades);
	
	/**
	 * Retorna les opcions contestades en la resposta-pregunta que es passa
	 * com a paràmetre
	 * 
	 * @param respostaPregunta
	 * @return
	 */
	public List<OpcioResposta> getOpcionsContestades(RespostaPregunta respostaPregunta);
	
	/**
	 * Actualitza una respostaPregunta
	 * 
	 * @param respostaPregunta
	 * @return
	 */
	public RespostaPregunta updateRespostaPregunta(RespostaPregunta respostaPregunta);
	
	/**
	 * Retorna les resposta-preguntes que té pendents
	 * de corregir i revisar un professor
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaPregunta> getRespostesPreguntaPendentsCorregir(Professor professor);
	
	/**
	 * Retorna les resposta-preguntes que té corregides
	 * i revisades un professor
	 * 
	 * @param professor
	 * @return
	 */
	public List<RespostaPregunta> getRespostesPreguntaCorregides(Professor professor);
	
	/**
	 * Retorna les resposta-preguntes que té un professor, poguent-les filtrar per si estan
	 * o no corregides i per si estan o no revisades.
	 * 
	 * @param professor
	 * @param corregides
	 * @param revisades
	 * @return
	 */
	public List<RespostaPregunta> getRespostesPregunta(Professor professor, Boolean corregides, Boolean revisades);
	
	/**
	 * Retorna les respostes que té una pregunta dins l'àmbit de l'assignatura
	 * que es passa com a paràmetre
	 * 
	 * @param pregunta
	 * @param assignatura
	 * @param contestades
	 * 				Boolean que indica si les resposta-preguntes han d'estar contestades
	 * 				Si és null, no filtra
	 * @param corregides
	 * 				Boolean que indica si les resposta-preguntes han d'estar corregides
	 * 				Si és null, no filtra
	 * @param assignacioDirecta
	 * 				Boolean que indica si les resposta-preguntes han de pertànyer a una
	 * 				resposta-questionari o no. (TRUE si no pertany, FALSE si sí)
	 * 				Si és null, no filtra
	 * @return
	 */
	public List<RespostaPregunta> getRespostesPregunta(Pregunta pregunta, Assignatura assignatura, Boolean contestades, Boolean corregides, Boolean assignacioDirecta);
	
}
