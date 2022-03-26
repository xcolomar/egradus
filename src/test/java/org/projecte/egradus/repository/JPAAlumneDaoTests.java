package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.springframework.transaction.annotation.Transactional;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.utilities.UtilTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
public class JPAAlumneDaoTests {

	@Autowired
	private AlumneRepository alumneDao;

	@Autowired
	private PersonaRepository personaDao;

	@Autowired
	private AssignaturaRepository assignaturaDao;

	private static String ASS_NOM = "Estadística III";
	private static String ASS_DESCRIPCIO = "Descripció de l'Assignatura de 5è Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR = "clau_pro_estad";
	private static String ASS_CLAU_ALUMNE = "clau_estad";
	private static String ASS_DATA_ALTA = "28/10/2013";
	private static String ASS_ANY_ACADEMIC = "2013-14";
	private static String ASS_CODI_REF = "ESTA3";

	private static String ASS2_NOM = "Llengua Castellana i Literatura";
	private static String ASS2_DESCRIPCIO = "Descripció de l'Assignatura Segon de Secundària etc.";
	private static String ASS2_CLAU_PROFESSOR = "llenguacast";
	private static String ASS2_CLAU_ALUMNE = null;
	private static String ASS2_DATA_ALTA = "09/01/2014";
	private static String ASS2_ANY_ACADEMIC = "2013-14";
	private static String ASS2_CODI_REF = "LLENGCASTLIT";

	private static String PER_NOM = "Miquel";
	private static String PER_PRIMER_LLINATGE = "Garcia";
	private static String PER_CORREU = "m.garcia@correu.es";
	private static String PER_ALIES = "miquelet";
	private static String PER_CLAU = "claumiquel";
	private static String PER_DATA_ALTA = "20/11/2013";

	private static String PER2_NOM = "Rosa";
	private static String PER2_PRIMER_LLINATGE = "Estarelles";
	private static String PER2_CORREU = "restarelles@correu.es";
	private static String PER2_ALIES = "rossi";
	private static String PER2_CLAU = "claurosa";
	private static String PER2_DATA_ALTA = "05/02/2014";

	private Persona persona;
	private Assignatura assignatura;

	@Before
	public void initialize() {
		persona = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU,
				UtilTest.getDate(PER_DATA_ALTA));
		personaDao.persistPersona(persona);
		assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO,
				ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
	}

	@Test
	public void testGetAlumnesBuit() {
		assertEquals(0, alumneDao.getAlumnes().size());
	}

	@Test
	public void testGetAlumnePersistAlumne() {
		Alumne alumne = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(alumne);
		assertEquals(1, alumneDao.getAlumnes().size());
	}

	@Test
	public void testGetAlumneTotCorrecteNotNull() {
		Alumne alumne = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(alumne);
		alumne = alumneDao.getAlumne(persona, assignatura).get(0);
		// Miram l'atribut persona de l'alumne per comprovar que
		// efectivament s'ha retornat una persona
		assertNotNull(alumne.getCodi());
	}

	@Test
	public void testGetAlumneTotCorrecteValor() {
		Alumne alumne = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(alumne);
		Alumne a = alumneDao.getAlumne(persona, assignatura).get(0);
		// Comprovam els àlies de l'alumne inserit i el retornat pel
		// getAlumne()
		assertEquals(alumne.getPersona().getAlies(), a.getPersona().getAlies());
	}

	@Test
	public void testGetAlumnePersonaMalament() {
		Alumne alumne = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(alumne);

		// Definim una persona distinta
		persona = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaDao.persistPersona(persona);

		List<Alumne> alumnes = alumneDao.getAlumne(persona, assignatura);
		assertEquals(0, alumnes.size());
	}

	@Test
	public void testGetAlumneAssignaturaMalament() {
		Alumne alumne = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(alumne);

		// Definim una assignatura distinta
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		List<Alumne> alumnes = alumneDao.getAlumne(persona, assignatura);
		assertEquals(0, alumnes.size());
	}

	@Test
	public void testGetAlumnesPersonaBuit() {
		List<Alumne> alumnes = alumneDao.getAlumnes(persona);
		assertEquals(0, alumnes.size());
	}

	@Test
	public void testGetAlumnesPersona() {
		Assignatura assignatura2 = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM,
				ASS2_DESCRIPCIO, ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura2);
		Alumne a = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(a);
		Alumne a2 = UtilTest.getAlumne(persona, assignatura2);
		alumneDao.persistAlumne(a2);
		List<Alumne> alumnes = alumneDao.getAlumnes(persona);
		assertEquals(2, alumnes.size());
	}

	@Test
	public void testGetAlumnesAssignaturaBuit() {
		List<Alumne> alumnes = alumneDao.getAlumnes(assignatura);
		assertEquals(0, alumnes.size());
	}

	@Test
	public void testGetAlumnesAssignatura() {
		Persona persona2 = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaDao.persistPersona(persona2);
		Alumne a = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(a);
		Alumne a2 = UtilTest.getAlumne(persona2, assignatura);
		alumneDao.persistAlumne(a2);
		List<Alumne> alumnes = alumneDao.getAlumnes(assignatura);
		assertEquals(2, alumnes.size());
	}

	@Test
	public void testGetAlumneByCodiNoExistent() {
		assertNull(alumneDao.getAlumneByCodi(0));
	}

	@Test
	public void testGetAlumneByCodi() {
		Alumne alumne = UtilTest.getAlumne(persona, assignatura);
		alumneDao.persistAlumne(alumne);

		// Definim un altre alumne
		Persona persona2 = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaDao.persistPersona(persona2);
		Alumne alumne2 = UtilTest.getAlumne(persona2, assignatura);
		alumneDao.persistAlumne(alumne2);

		// Llegim el codi del primer alumne
		int codiAlumne = alumne.getCodi();

		// Llegim el codi del segon alumne
		int codiAlumne2 = alumne2.getCodi();

		assertEquals(PER_ALIES, alumneDao.getAlumneByCodi(codiAlumne).getPersona().getAlies());
		assertEquals(PER2_ALIES, alumneDao.getAlumneByCodi(codiAlumne2).getPersona().getAlies());
	}

}
