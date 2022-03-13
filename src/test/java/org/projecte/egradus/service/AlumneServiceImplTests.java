package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.utilities.UtilTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
public class AlumneServiceImplTests {

	@Autowired
	private PersonaService personaService;

	@Autowired
	private AlumneService alumneService;

	@Autowired
	private AssignaturaService assignaturaService;

	// Assignatures
	private static String NOM1 = "Intel·ligència Artificial";
	private static String DESCRIPCIO1 = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String CLAU_PROFESSOR1 = "clau_inteli_artifi";
	private static String CLAU_ALUMNE1 = "clau_intart";
	private static String DATA_ALTA1 = "22/12/2013";
	private static String ANY_ACADEMIC1 = "2013-14";
	private static String CODI_REF1 = "INTE_ART";

	private static String NOM2 = "Càlcul diferencial II";
	private static String DESCRIPCIO2 = null;
	private static String CLAU_PROFESSOR2 = "clau_calcul_dif2";
	private static String CLAU_ALUMNE2 = "clau_caldif2";
	private static String DATA_ALTA2 = "14/01/2014";
	private static String ANY_ACADEMIC2 = "2013-14";
	private static String CODI_REF2 = "CALC_DIF";

	// Persones
	private static String PER_NOM = "Miquel";
	private static String PER_PRIMER_LLINATGE = "Garcia";
	private static String PER_CORREU = "m.garcia@correu.es";
	private static String PER_ALIES = "miquelet";
	private static String PER_CLAU = "claumiquel";
	private static String PER_DATA_ALTA = "20/11/2013";

	private static String PER2_NOM = "Joan";
	private static String PER2_PRIMER_LLINATGE = "Savater";
	private static String PER2_CORREU = "j.savat85@correu.es";
	private static String PER2_ALIES = "johnny";
	private static String PER2_CLAU = "jonyclau";
	private static String PER2_DATA_ALTA = "18/02/2014";

	private Persona persona;
	private Assignatura assignatura;
	private Alumne alumne;

	@Before
	public void initialize() {
		persona = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU,
				UtilTest.getDate(PER_DATA_ALTA));
		personaService.insereixPersona(persona);
		assignatura = UtilTest.inserirAssignatura(persona, CODI_REF1, ANY_ACADEMIC1, NOM1, DESCRIPCIO1, CLAU_PROFESSOR1,
				CLAU_ALUMNE1, UtilTest.getDate(DATA_ALTA1));
		assignaturaService.insereixAssignatura(assignatura);
		alumne = UtilTest.getAlumne(persona, assignatura);
		alumneService.insereixAlumne(alumne);
	}

	@Test
	public void testSetAlumne() {
		assertEquals(assignatura.getCodi(), alumne.getAssignatura().getCodi());
		assertEquals(persona.getCodi(), alumne.getPersona().getCodi());
	}

	@Test
	public void testEsAlumneVertader() {
		assertTrue(alumneService.esAlumne(persona, assignatura));
	}

	@Test
	public void testEsAlumneFalsAssignatura() {
		Assignatura a = UtilTest.inserirAssignatura(persona, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2,
				CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
		assignaturaService.insereixAssignatura(a);
		assertFalse(alumneService.esAlumne(persona, a));
	}

	@Test
	public void testEsAlumneFalsPersona() {
		Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaService.insereixPersona(p);
		assertFalse(alumneService.esAlumne(p, assignatura));
	}

	@Test
	public void testEsAlumneParametresNul() {
		assertFalse(alumneService.esAlumne(null, null));
	}

	@Test
	public void testGetAlumnesPerAssignaturaOk() {
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		assertEquals(1, alumnes.size());
	}

	@Test
	public void testGetAlumnesPerAssignaturaNo() {
		Assignatura a = UtilTest.inserirAssignatura(persona, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2,
				CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
		assignaturaService.insereixAssignatura(a);
		List<Alumne> alumnes = alumneService.getAlumnes(a);
		assertNull(alumnes);
	}

	@Test
	public void testGetAlumneByCodiNoExistent() {
		assertNull(alumneService.getAlumne(0));
	}

	@Test
	public void testGetAlumneByCodi() {
		Alumne alumne = UtilTest.getAlumne(persona, assignatura);
		alumneService.insereixAlumne(alumne);

		// Definim un altre alumne
		Persona persona2 = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaService.insereixPersona(persona2);
		Alumne alumne2 = UtilTest.getAlumne(persona2, assignatura);
		alumneService.insereixAlumne(alumne2);

		// Llegim el codi del primer alumne
		int codiAlumne = alumne.getCodi();

		// Llegim el codi del segon alumne
		int codiAlumne2 = alumne2.getCodi();

		assertEquals(PER_ALIES, alumneService.getAlumne(codiAlumne).getPersona().getAlies());
		assertEquals(PER2_ALIES, alumneService.getAlumne(codiAlumne2).getPersona().getAlies());
	}

	@Test
	public void testGetAlumneNotNull() {
		Alumne a = alumneService.getAlumne(persona, assignatura);
		assertNotNull(a);
	}

	@Test
	public void testGetAlumneParametresNul() {
		Alumne alumne = alumneService.getAlumne(null, null);
		assertNull(alumne);
	}

	@Test
	public void testGetAlumnePersonaIncorrecta() {
		Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaService.insereixPersona(p);
		Alumne alu = alumneService.getAlumne(p, assignatura);
		assertNull(alu);
	}

	@Test
	public void testGetAlumneAssignaturaIncorrecta() {
		Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaService.insereixPersona(p);
		Assignatura a = UtilTest.inserirAssignatura(p, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2, CLAU_PROFESSOR2,
				CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
		assignaturaService.insereixAssignatura(a);
		Alumne alu = alumneService.getAlumne(persona, a);
		assertNull(alu);
	}
}
