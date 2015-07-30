package org.projecte.egradus.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.projecte.egradus.utilities.UtilTest;

public class PreguntaQuestionariTests {
	
	private Validator validador;
	
	
	private Persona persona;
	private Assignatura assignatura;
	private Professor professor;
	private Pregunta pregunta;
	private Questionari questionari;
	private PreguntaQuestionari pq;
	
	@Before
	public void inicialitza() {
		persona = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		assignatura = UtilTest.inserirAssignatura(persona, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null, "claualu", new Date());
		professor = UtilTest.getProfessor(persona, assignatura);
		pregunta = UtilTest.inserirPregunta(professor, "enunciat", 0.5f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_REC, null, Pregunta.ESTAT_PUBLIC);
		questionari = UtilTest.inserirQuestionari(professor, "nom qüestionari", "descripció qüestionari", 0.5f, null, Questionari.ESTAT_EDITABLE, new Date());
		pq = new PreguntaQuestionari();
		
		ValidatorFactory f = Validation.buildDefaultValidatorFactory();
		validador = f.getValidator();
	}
	
	@Test
	public void testPQQuestionariNotNull() {
		pq.setPregunta(pregunta);
		pq.setQuestionari(null);
		Set<ConstraintViolation<PreguntaQuestionari>> errors = validador.validate(pq);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testPQPreguntaNotNull() {
		pq.setPregunta(null);
		pq.setQuestionari(questionari);
		Set<ConstraintViolation<PreguntaQuestionari>> errors = validador.validate(pq);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testPQOk() {
		pq.setPregunta(pregunta);
		pq.setQuestionari(questionari);
		Set<ConstraintViolation<PreguntaQuestionari>> errors = validador.validate(pq);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testPQCompletOk() {
		pq.setPregunta(pregunta);
		pq.setQuestionari(questionari);
		pq.setPes(0.5f);
		Set<ConstraintViolation<PreguntaQuestionari>> errors = validador.validate(pq);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testPQPesMax(){
		pq.setPregunta(pregunta);
		pq.setQuestionari(questionari);
		pq.setPes(2f);
		Set<ConstraintViolation<PreguntaQuestionari>> errors = validador.validateProperty(pq, "pes");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testPQPesMin(){
		pq.setPregunta(pregunta);
		pq.setQuestionari(questionari);
		pq.setPes(-1f);
		Set<ConstraintViolation<PreguntaQuestionari>> errors = validador.validateProperty(pq, "pes");
		assertEquals(1, errors.size());
	}
}
