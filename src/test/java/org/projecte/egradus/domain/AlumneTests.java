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
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.utilities.UtilTest;

public class AlumneTests {
	
	private Validator validador;
	
	
	private Persona persona;
	private Assignatura assignatura;
	private Alumne alumne;
	
	
	@Before
	public void inicialitza() {
		alumne = new Alumne();
		persona = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		assignatura = UtilTest.inserirAssignatura(persona, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null, "claualu", new Date());
		
		ValidatorFactory f = Validation.buildDefaultValidatorFactory();
		validador = f.getValidator();
	}
	
	@Test
	public void testAlumnePersonaNotNull() {
		alumne.setPersona(persona);
		alumne.setAssignatura(null);
		Set<ConstraintViolation<Alumne>> errors = validador.validate(alumne);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testAlumneAssignaturaNotNull() {
		alumne.setPersona(null);
		alumne.setAssignatura(assignatura);
		Set<ConstraintViolation<Alumne>> errors = validador.validate(alumne);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testAlumneOk() {
		alumne.setPersona(persona);
		alumne.setAssignatura(assignatura);
		Set<ConstraintViolation<Alumne>> errors = validador.validate(alumne);
		assertEquals(0, errors.size());
	}
	
}