package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
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
public class AssignaturaServiceImplTests {

	@Autowired
	private PersonaService personaService;

	@Autowired
	private AlumneService alumneService;

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private AssignaturaService assignaturaService;

	private static int ASSIGNATURA_COUNT = 3;

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

	private static String NOM3 = "Enginyeria del Software III";
	private static String DESCRIPCIO3 = "Darrera Assignatura troncal de la carrera";
	private static String CLAU_PROFESSOR3 = "clau_enginy_software_3";
	private static String CLAU_ALUMNE3 = "clau_engisoft3";
	private static String DATA_ALTA3 = "10/01/2014";
	private static String ANY_ACADEMIC3 = "2013-14";
	private static String CODI_REF3 = "ENGISOFT";

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

	@Before
	public void initialize() {
		persona = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU,
				UtilTest.getDate(PER_DATA_ALTA));
		personaService.insereixPersona(persona);
		assignatura = UtilTest.inserirAssignatura(persona, CODI_REF1, ANY_ACADEMIC1, NOM1, DESCRIPCIO1, CLAU_PROFESSOR1,
				CLAU_ALUMNE1, UtilTest.getDate(DATA_ALTA1));
		assignaturaService.insereixAssignatura(assignatura);
	}

	@Test
	public void testSetAssignaturaNotNull() {
		assertNotNull(assignatura.getCodi());
	}

	@Test
	public void testSetAssignaturaCodiPersona() {
		assertEquals(persona.getCodi(), assignatura.getCreador().getCodi());
	}

	@Test
	public void testGetAssignaturaPerCodiZero() {
		Assignatura assignatura = assignaturaService.getAssignatura(0);
		assertNull(assignatura);
	}

	@Test
	public void testGetAssignaturaPerCodiNegatiu() {
		Assignatura assignatura = assignaturaService.getAssignatura(-5);
		assertNull(assignatura);
	}

	@Test
	public void testGetAssignaturaPerCodiNoExistent() {
		Assignatura a = assignaturaService.getAssignatura(assignatura.getCodi() + 1);
		assertNull(a);
	}

	@Test
	public void testGetAssignaturaPerCodiExistentNotNull() {
		Assignatura a = assignaturaService.getAssignatura(assignatura.getCodi());
		assertNotNull(a);
	}

	@Test
	public void testGetAssignaturaPerCodiExistent() {
		Assignatura a = assignaturaService.getAssignatura(assignatura.getCodi());
		assertEquals(a.getCodi(), assignatura.getCodi());
	}

	@Test
	public void testGetAssignaturesPle() {
		// assignam dues assignatures més a la persona
		Assignatura assignatura2 = UtilTest.inserirAssignatura(persona, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2,
				CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
		assignaturaService.insereixAssignatura(assignatura2);
		Assignatura assignatura3 = UtilTest.inserirAssignatura(persona, CODI_REF3, ANY_ACADEMIC3, NOM3, DESCRIPCIO3,
				CLAU_PROFESSOR3, CLAU_ALUMNE3, UtilTest.getDate(DATA_ALTA3));
		assignaturaService.insereixAssignatura(assignatura3);
		List<Assignatura> assignatures = assignaturaService.getAssignatures();
		assertEquals(ASSIGNATURA_COUNT, assignatures.size());
	}

	@Test
	public void testGetAssignaturesDunaPersonaNull() {
		List<Assignatura> assignatures = assignaturaService.getAssignatures(null);
		assertNull(assignatures);
	}

	@Test
	public void testGetAssignaturesDunaPersonaTotesProfessor() {
		// assignam dues assignatures més a la persona
		Assignatura assignatura2 = UtilTest.inserirAssignatura(persona, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2,
				CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
		assignaturaService.insereixAssignatura(assignatura2);
		Assignatura assignatura3 = UtilTest.inserirAssignatura(persona, CODI_REF3, ANY_ACADEMIC3, NOM3, DESCRIPCIO3,
				CLAU_PROFESSOR3, CLAU_ALUMNE3, UtilTest.getDate(DATA_ALTA3));
		assignaturaService.insereixAssignatura(assignatura3);
		List<Assignatura> assignatures = assignaturaService.getAssignatures(persona);
		assertEquals(ASSIGNATURA_COUNT, assignatures.size());
	}

	@Test
	public void testGetAssignaturesDunaPersonaAlumne() {
		Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaService.insereixPersona(p);
		Alumne alumne = UtilTest.getAlumne(p, assignatura);
		alumneService.insereixAlumne(alumne);
		List<Assignatura> assignatures = assignaturaService.getAssignatures(p);
		assertEquals(1, assignatures.size());
	}

	@Test
	public void testGetAssignaturesDunaPersonaProfessor() {
		Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaService.insereixPersona(p);
		Professor professor = UtilTest.getProfessor(p, assignatura);
		professorService.insereixProfessor(professor);
		List<Assignatura> assignatures = assignaturaService.getAssignatures(p);
		assertEquals(1, assignatures.size());
	}

	@Test
	public void testGetAssignaturesDunaPersonaAlumneIProfessor() {
		Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaService.insereixPersona(p);
		Assignatura assignatura2 = UtilTest.inserirAssignatura(persona, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2,
				CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
		assignaturaService.insereixAssignatura(assignatura2);
		Assignatura assignatura3 = UtilTest.inserirAssignatura(persona, CODI_REF3, ANY_ACADEMIC3, NOM3, DESCRIPCIO3,
				CLAU_PROFESSOR3, CLAU_ALUMNE3, UtilTest.getDate(DATA_ALTA3));
		assignaturaService.insereixAssignatura(assignatura3);
		Professor professor = UtilTest.getProfessor(p, assignatura);
		professorService.insereixProfessor(professor);
		Alumne alumne = UtilTest.getAlumne(p, assignatura2);
		alumneService.insereixAlumne(alumne);
		Alumne alumne2 = UtilTest.getAlumne(p, assignatura3);
		alumneService.insereixAlumne(alumne2);
		List<Assignatura> assignatures = assignaturaService.getAssignatures(p);
		assertEquals(ASSIGNATURA_COUNT, assignatures.size());
	}

}
