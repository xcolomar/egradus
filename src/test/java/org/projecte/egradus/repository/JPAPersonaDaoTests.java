package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.utilities.UtilTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
public class JPAPersonaDaoTests {

	private static String ALIES_FALSE = "alies_false";
	private static String ALIES_TRUE = "alies_true";

	@Autowired
	private PersonaRepository personaDao;

	@Test
	public void testGetLlistaPersonaBuit() {
		List<Persona> persones = personaDao.getPersones();
		assertEquals(0, persones.size());
	}

	@Test
	public void testSetPersona() {
		Persona persona = UtilTest.inserirPersona("Xavi", "Colomar", "Rodríguez", "43195777Y", "xcolomar@egradus.com",
				"xcolomar", "password", UtilTest.getDate("28/07/1988"), UtilTest.getDate("26/01/2014"));
		personaDao.persistPersona(persona);
		assertNotNull(persona.getCodi());
	}

	@Test
	public void testGetLlistaPersona() {
		Persona persona = UtilTest.inserirPersona("Nomtest", "Llinatge1test", null, null, "correu@test.es", ALIES_TRUE,
				"clautest", null, new Date());
		personaDao.persistPersona(persona);
		List<Persona> persones = personaDao.getPersones();
		assertEquals(1, persones.size());
	}

	@Test
	public void testGetPersonaByAlies() {
		Persona persona = UtilTest.inserirPersona("Nomtest", "Llinatge1test", null, null, "correu@test.es", ALIES_TRUE,
				"clautest", null, new Date());
		personaDao.persistPersona(persona);
		List<Persona> p = personaDao.getPersonesByAlies(ALIES_TRUE);
		assertEquals(1, p.size());
	}

	@Test
	public void testGetPersonaByAliesNoExistent() {
		Persona persona = UtilTest.inserirPersona("Nomtest", "Llinatge1test", null, null, "correu@test.es", ALIES_TRUE,
				"clautest", null, new Date());
		personaDao.persistPersona(persona);
		List<Persona> p = personaDao.getPersonesByAlies(ALIES_FALSE);
		assertEquals(0, p.size());
	}

	@Test
	public void testGetPersonaByAliesNull() {
		List<Persona> p = personaDao.getPersonesByAlies(null);
		assertEquals(0, p.size());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstraintSetPersonaNom() {
		Persona persona = UtilTest.inserirPersona(null, "Llinatge1test", null, null, "correu@test.es", ALIES_TRUE,
				"clautest", null, new Date());
		personaDao.persistPersona(persona);
		fail("no hauria de guardar nom null");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstraintSetPersonaPrimerLlinatge() {
		Persona persona = UtilTest.inserirPersona("nomtest", null, null, null, "correu@test.es", ALIES_TRUE, "clautest",
				null, new Date());
		personaDao.persistPersona(persona);
		fail("no hauria de guardar primer llinatge null");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstraintSetPersonaCorreu() {
		Persona persona = UtilTest.inserirPersona("nomtest", "Llinatge1test", null, null, null, ALIES_TRUE, "clautest",
				null, new Date());
		personaDao.persistPersona(persona);
		fail("no hauria de guardar correu null");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstraintSetPersonaAlies() {
		Persona persona = UtilTest.inserirPersona("Nomtest", "Llinatge1test", null, null, "correu@test.es", null,
				"clautest", null, new Date());
		personaDao.persistPersona(persona);
		fail("no hauria de guardar alies null");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstraintSetPersonaClau() {
		Persona persona = UtilTest.inserirPersona("Nomtest", "Llinatge1test", null, null, "correu@test.es",
				"correu@test.es", null, null, new Date());
		personaDao.persistPersona(persona);
		fail("no hauria de guardar clau null");
	}

}
