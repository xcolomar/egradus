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

public class OpcioTests {
	
	private Validator validador;
	
	
	private Persona persona;
	private Assignatura assignatura;
	private Professor professor;
	private Pregunta pregunta;
	private Opcio opcio;
	
	
	@Before
	public void inicialitza() {
		opcio = new Opcio();
		persona = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		assignatura = UtilTest.inserirAssignatura(persona, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null, "claualu", new Date());
		professor = UtilTest.getProfessor(persona, assignatura);
		pregunta = UtilTest.inserirPregunta(professor, "enunciat", 0f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_ES1, null, Pregunta.ESTAT_PUBLIC);
		
		ValidatorFactory f = Validation.buildDefaultValidatorFactory();
		validador = f.getValidator();
	}
	
	@Test
	public void testOpcioTextNotEmpty() {
		opcio.setText(null);
		opcio.setCorrecta(Boolean.TRUE);
		opcio.setPregunta(pregunta);
		Set<ConstraintViolation<Opcio>> errors = validador.validate(opcio);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testOpcioCorrectaNotNull() {
		opcio.setText("text de la opció");
		opcio.setCorrecta(null);
		opcio.setPregunta(pregunta);
		Set<ConstraintViolation<Opcio>> errors = validador.validate(opcio);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testOpcioPreguntaNotNull() {
		opcio.setText("text de la opció");
		opcio.setCorrecta(Boolean.FALSE);
		opcio.setPregunta(null);
		Set<ConstraintViolation<Opcio>> errors = validador.validate(opcio);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testOpcioOk() {
		opcio.setText("text de la opció");
		opcio.setCorrecta(Boolean.TRUE);
		opcio.setPregunta(pregunta);
		Set<ConstraintViolation<Opcio>> errors = validador.validate(opcio);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testOpcioOkTotsParametres() {
		opcio.setText("text de la opció");
		opcio.setCorrecta(Boolean.TRUE);
		opcio.setDataAlta(new Date());
		opcio.setPregunta(pregunta);
		Set<ConstraintViolation<Opcio>> errors = validador.validate(opcio);
		assertEquals(0, errors.size());
	}
	
}