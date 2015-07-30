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
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.utilities.UtilTest;

public class ProfessorTests {

	private Validator validador;
	
	private Persona persona;
	private Assignatura assignatura;
	private Professor professor;
	
	
	@Before
	public void inicialitza() {
		persona = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		assignatura = UtilTest.inserirAssignatura(persona, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null, "claualu", new Date());
		professor = new Professor();
		
		ValidatorFactory f = Validation.buildDefaultValidatorFactory();
		validador = f.getValidator();
	}
	
	@Test
	public void testProfessorPersonaNotNull() {
		professor.setPersona(persona);
		professor.setAssignatura(null);
		Set<ConstraintViolation<Professor>> errors = validador.validate(professor);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testProfessorAssignaturaNotNull() {
		professor.setPersona(null);
		professor.setAssignatura(assignatura);
		Set<ConstraintViolation<Professor>> errors = validador.validate(professor);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testProfessorOk() {
		professor.setPersona(persona);
		professor.setAssignatura(assignatura);
		Set<ConstraintViolation<Professor>> errors = validador.validate(professor);
		assertEquals(0, errors.size());
	}
	
}
