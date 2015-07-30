package org.projecte.egradus.repository;

import java.util.List;

import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Questionari;

public interface PreguntaQuestionariDao {
	
	/**
	 * Insereix una relació pregunta-questionari
	 * 
	 * @param preguntaQuestionari
	 */
	public void persistPreguntaQuestionari(PreguntaQuestionari preguntaQuestionari);
	
	/**
	 * Agafa totes les relacions pregunta-questionari
	 * 
	 * @return
	 */
	public List<PreguntaQuestionari> getRelacionsPreguntaQuestionari();
	
	/**
	 * Agafa la relació pregunta-questionari identificada
	 * per la pregunta i questionari passats per paràmetre
	 * 
	 * @param pregunta
	 * @param questionari
	 * @return
	 */
	public List<PreguntaQuestionari> getPreguntaQuestionari(Pregunta pregunta, Questionari questionari);
	
	/**
	 * Agafa totes les relacions pregunta-questionari de la
	 * pregunta passada per paràmetre
	 * 
	 * @param pregunta
	 * @return
	 */
	public List<PreguntaQuestionari> getRelacionsPreguntaQuestionari(Pregunta pregunta);
	
	/**
	 * Agafa totes les relacions pregunta-questionari del
	 * questionari passat per paràmetre
	 * 
	 * @param questionari
	 * @return
	 */
	public List<PreguntaQuestionari> getRelacionsPreguntaQuestionari(Questionari questionari);
	
	/**
	 * Agafa la relació pregunta-questionari identificada
	 * pel codi passat per paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public PreguntaQuestionari getPreguntaQuestionariByCodi(int codi);
	
	/**
	 * Actualitza la relació pregunta-questionari passada
	 * per paràmetre
	 * 
	 * @param preguntaQuestionari
	 * @return
	 */
	public PreguntaQuestionari updatePreguntaQuestionari(PreguntaQuestionari preguntaQuestionari);
	
	/**
	 * Elimina la relació pregunta-questionari passada
	 * per paràmetre
	 * 
	 * @param preguntaQuestionari
	 */
	public void removePreguntaQuestionari(PreguntaQuestionari preguntaQuestionari);
	
}
