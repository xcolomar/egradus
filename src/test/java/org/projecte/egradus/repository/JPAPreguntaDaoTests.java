package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.utilities.Util;
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
public class JPAPreguntaDaoTests {

	@Autowired
	private PreguntaRepository preguntaDao;

	@Autowired
	private PersonaRepository personaDao;

	@Autowired
	private AssignaturaRepository assignaturaDao;

	@Autowired
	private ProfessorRepository professorDao;

	@Autowired
	private AlumneRepository alumneDao;

	@Autowired
	private RespostaPreguntaDao respostaPreguntaDao;

	// Dades de la Persona
	private static String PER_NOM = "Miquel";
	private static String PER_PRIMER_LLINATGE = "Garcia";
	private static String PER_CORREU = "m.garcia@correu.es";
	private static String PER_ALIES = "miquelet";
	private static String PER_CLAU = "claumiquel";
	private static String PER_DATA_ALTA = "20/11/2013";

	private static String PER2_NOM = "Toni";
	private static String PER2_PRIMER_LLINATGE = "Salom";
	private static String PER2_CORREU = "correutoni@correu.es";
	private static String PER2_ALIES = "toniet";
	private static String PER2_CLAU = "clautoni";
	private static String PER2_DATA_ALTA = "14/02/2014";

	private static String PER3_NOM = "Maria";
	private static String PER3_PRIMER_LLINATGE = "Salvà";
	private static String PER3_CORREU = "mariasalva@correu.es";
	private static String PER3_ALIES = "salva";
	private static String PER3_CLAU = "marisalva";
	private static String PER3_DATA_ALTA = "07/03/2015";

	// Dades de l'Assignatura
	private static String ASS_NOM = "Intel·ligència Artificial";
	private static String ASS_DESCRIPCIO = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR = "clau_inteli_artifi";
	private static String ASS_CLAU_ALUMNE = "clau_intart";
	private static String ASS_DATA_ALTA = "01/10/2013";
	private static String ASS_ANY_ACADEMIC = "2013-14";
	private static String ASS_CODI_REF = "INTELIART";

	// Dades de la Pregunta
	private static String PRE_ENUNCIAT = "Aquest és l'enunciat de la pregunta";
	private static Float PRE_DIFICULTAT_TEO = 0.5f;
	private static Float PRE_DIFICULTAT_PRA = 0.99f;
	private static Boolean PRE_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date PRE_DATA_ALTA = new Date();
	private static String PRE_TIPUS = Pregunta.TIPUS_VOF;
	private static Boolean PRE_VOF_VERTADER = Boolean.TRUE;
	private static String PRE_ESTAT = Pregunta.ESTAT_EDITABLE;
	private static String PRE_ESTAT_NO = Pregunta.ESTAT_PUBLIC;
	// Data per a comparar amb els filtres temporals d'abaix
	private static String PRE_DATA_ALTA_REF = "15/07/2014";

	private static String PRE2_ENUNCIAT = "Aquest és l'enunciat de la pregunta 2";
	private static Float PRE2_DIFICULTAT_TEO = 0.1f;
	private static Float PRE2_DIFICULTAT_PRA = null;
	private static Boolean PRE2_RAONAR_RESPOSTA = Boolean.TRUE;
	private static Date PRE2_DATA_ALTA = new Date();
	private static String PRE2_TIPUS = Pregunta.TIPUS_REC;
	private static Boolean PRE2_VOF_VERTADER = null;
	private static String PRE2_ESTAT = Pregunta.ESTAT_PUBLIC;

	// Dades de les Opcions
	private static int NUM_OPCIONS = 2;

	private static String OPC_TEXT1 = "Opció 1";
	private static Boolean OPC_CORRECTA1 = Boolean.TRUE;
	private static Date OPC_DATA_ALTA1 = new Date();

	private static String OPC_TEXT2 = "Opció 2";
	private static Boolean OPC_CORRECTA2 = Boolean.FALSE;
	private static Date OPC_DATA_ALTA2 = new Date();

	// Filtres per a cercar Preguntes
	private static String PRE_DATA_INICI_OK = "01/07/2014";
	private static String PRE_DATA_INICI_NO = "16/07/2014";
	private static String PRE_DATA_FI_OK = "31/07/2014";
	private static String PRE_DATA_FI_NO = "14/07/2014";

	private static String PRE_ENUNCIAT_OK = "PReg";
	private static String PRE_ENUNCIAT_NO = "aaa";

	private static String PRE_CRE_ALIES_OK = "quelet";
	private static String PRE_CRE_NOM_OK = "Miq";
	private static String PRE_CRE_LLINATGE_OK = "GarcI";
	private static String PRE_CREADOR_NO = "mico";

	private static Boolean PRE_RAONAR_RES_OK = Boolean.FALSE;
	private static Boolean PRE_RAONAR_RES_NO = Boolean.TRUE;
	private static Boolean PRE_RAONAR_RES_NULL = null;

	private static Float PRE_DIF_TEO1_OK = 0.201f;
	private static Float PRE_DIF_TEO1_NO = 0.774f;
	private static Float PRE_DIF_TEO2_OK = PRE_DIFICULTAT_TEO;
	private static Float PRE_DIF_TEO2_NO = 0f;

	private static Float PRE_DIF_PRA1_OK = PRE_DIFICULTAT_PRA;
	private static Float PRE_DIF_PRA1_NO = 1.0f;
	private static Float PRE_DIF_PRA2_OK = 0.991f;
	private static Float PRE_DIF_PRA2_NO = 0.9f;

	private static String PRE_TIPUS_OK = PRE_TIPUS;
	private static String PRE_TIPUS_NO = Pregunta.TIPUS_ESN;

	private Persona personaProfessor;
	private Persona personaProfessor2;
	private Persona personaAlumne;
	private Assignatura assignatura;
	private Professor professor;
	private Professor professor2;
	private Alumne alumne;

	@Before
	public void initialize() {
		personaProfessor = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU,
				UtilTest.getDate(PER_DATA_ALTA));
		personaDao.persistPersona(personaProfessor);
		personaAlumne = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU,
				UtilTest.getDate(PER2_DATA_ALTA));
		personaDao.persistPersona(personaAlumne);
		personaProfessor2 = UtilTest.inserirPersona(PER3_NOM, PER3_PRIMER_LLINATGE, PER3_CORREU, PER3_ALIES, PER3_CLAU,
				UtilTest.getDate(PER3_DATA_ALTA));
		personaDao.persistPersona(personaProfessor2);

		assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		professor = UtilTest.getProfessor(personaProfessor, assignatura);
		professorDao.persistProfessor(professor);
		professor2 = UtilTest.getProfessor(personaProfessor2, assignatura);
		professorDao.persistProfessor(professor2);

		alumne = Util.getAlumne(personaAlumne, assignatura);
		alumneDao.persistAlumne(alumne);
	}

	@Test
	public void testGetLlistaPreguntes() {
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes();
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testPersistPregunta() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		assertNotNull(pregunta.getCodi());
	}

	@Test
	public void testPersistAmbGetLlistaPreguntes() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes();
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetLlistaPreguntesEstatBuit() {
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(0);
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetLlistaPreguntesEstatOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetLlistaPreguntesByAssignaturaBuit() {
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntesByAssignatura(assignatura.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetLlistaPreguntesByAssignatura() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntesByAssignatura(assignatura.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetLlistaPreguntesByAssignaturaVaries() {
		Pregunta pregunta1 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta1);

		Pregunta pregunta2 = UtilTest.inserirPregunta(professor2, PRE2_ENUNCIAT, PRE2_DIFICULTAT_TEO,
				PRE2_DIFICULTAT_PRA, PRE2_RAONAR_RESPOSTA, PRE2_DATA_ALTA, PRE2_TIPUS, PRE2_VOF_VERTADER, PRE2_ESTAT);
		preguntaDao.persistPregunta(pregunta2);

		// El professor 1 li envia la pregunta1 a l'alumne
		RespostaPregunta rp1 = new RespostaPregunta(pregunta1, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp1);

		// El professor 2 li envia la pregunta1 a l'alumne
		RespostaPregunta rp2 = new RespostaPregunta(pregunta1, professor2, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp2);

		// El professor 2 li envia la pregunta2 a l'alumne2
		RespostaPregunta rp3 = new RespostaPregunta(pregunta2, professor2, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp3);

		// Comprovam que, en l'àmbit de l'assignatura actual, hi han 2 preguntes (per
		// molt que,
		// en total, hi hagin 3 resposta-preguntes).
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntesByAssignatura(assignatura.getCodi());
		assertEquals(2, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDataIniciOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(UtilTest.getDate(PRE_DATA_INICI_OK), null, null,
				null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDataIniciNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(UtilTest.getDate(PRE_DATA_INICI_NO), null, null,
				null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDataFiOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, UtilTest.getDate(PRE_DATA_FI_OK), null,
				null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDataFiNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, UtilTest.getDate(PRE_DATA_FI_NO), null,
				null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreBetweenOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(UtilTest.getDate(PRE_DATA_INICI_OK),
				UtilTest.getDate(PRE_DATA_FI_OK), null, null, null, null, null, null, null, null, null,
				professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreBetweenDadesGirades() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(UtilTest.getDate(PRE_DATA_INICI_NO),
				UtilTest.getDate(PRE_DATA_FI_NO), null, null, null, null, null, null, null, null, null,
				professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreEnunciatOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, PRE_ENUNCIAT_OK, null, null, null,
				null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreEnunciatNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, PRE_ENUNCIAT_NO, null, null, null,
				null, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreCreadorNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, PRE_CREADOR_NO, null, null,
				null, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreCreadorAlies() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, PRE_CRE_ALIES_OK, null, null,
				null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreCreadorNom() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, PRE_CRE_NOM_OK, null, null,
				null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreCreadorLlinatge() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, PRE_CRE_LLINATGE_OK, null,
				null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreRaonarRespostaOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				PRE_RAONAR_RES_OK, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreRaonarRespostaNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				PRE_RAONAR_RES_NO, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreRaonarRespostaNull() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				PRE_RAONAR_RES_NULL, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifTeo1Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, PRE_DIF_TEO1_OK, null,
				null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifTeo1No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, PRE_DIF_TEO1_NO, null,
				null, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifTeo2Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, PRE_DIF_TEO2_OK,
				null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifTeo2No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, PRE_DIF_TEO2_NO,
				null, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifTeoBetweenOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, PRE_DIF_TEO1_OK,
				PRE_DIF_TEO2_OK, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifTeoBetweenNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, PRE_DIF_TEO1_NO,
				PRE_DIF_TEO2_NO, null, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifPra1Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null,
				PRE_DIF_PRA1_OK, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifPra1No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null,
				PRE_DIF_PRA1_NO, null, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifPra2Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null,
				PRE_DIF_PRA2_OK, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifPra2No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null,
				PRE_DIF_PRA2_NO, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifPraBetweenOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null,
				PRE_DIF_PRA1_OK, PRE_DIF_PRA2_OK, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreDifPraBetweenNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null,
				PRE_DIF_PRA1_NO, PRE_DIF_PRA2_NO, null, null, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreTipusOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				null, PRE_TIPUS_OK, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreTipusNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				null, PRE_TIPUS_NO, null, professor.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreEstatPublic() {
		// La pregunta creada pel professor es troba en estat editable
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_EDITABLE);
		preguntaDao.persistPregunta(pregunta);

		// La pregunta creada pel professor2 es troba en estat públic
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor2, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(pregunta2);

		// Consultam les preguntes en estat públic que veu el professor 1
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				null, null, Pregunta.ESTAT_PUBLIC, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());

		// Consultam les preguntes en estat públic que veu el professor 2
		llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null, null, null,
				Pregunta.ESTAT_PUBLIC, professor2.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreEstatEditable() {
		// La pregunta creada pel professor es troba en estat editable
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_EDITABLE);
		preguntaDao.persistPregunta(pregunta);

		// La pregunta creada pel professor2 es troba en estat públic
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor2, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(pregunta2);

		// Consultam les preguntes en estat editable que veu el professor 1
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				null, null, Pregunta.ESTAT_EDITABLE, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());

		// Consultam les preguntes en estat públic que veu el professor 2
		llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null, null, null,
				Pregunta.ESTAT_EDITABLE, professor2.getCodi());
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntesFiltreEstatSenseIndicar() {
		// La pregunta creada pel professor es troba en estat editable
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_EDITABLE);
		preguntaDao.persistPregunta(pregunta);

		// La pregunta creada pel professor2 es troba en estat públic
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor2, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(pregunta2);

		// Consultam les preguntes que veu el professor 1 sense haver indicat estat
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null,
				null, null, null, professor.getCodi());
		assertEquals(2, llistaPreguntes.size());

		// Consultam les preguntes que veu el professor 2 sense haver indicat estat
		llistaPreguntes = preguntaDao.getLlistaPreguntes(null, null, null, null, null, null, null, null, null, null,
				null, professor2.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}

	@Test
	public void testGetPreguntaByCodi() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		// agafam la pregunta que acabam d'inserir cercant-la per codi
		Pregunta pregunta2 = preguntaDao.getPreguntaByCodi(pregunta.getCodi());
		assertEquals(PRE_ENUNCIAT, pregunta2.getEnunciat());
	}

	@Test
	public void testGetOpcionsBuit() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);
		assertTrue(preguntaDao.getOpcions(pregunta).isEmpty());
	}

	@Test
	public void testGetOpcionsPle() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		Opcio opc1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1);
		Opcio opc2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, OPC_CORRECTA2, OPC_DATA_ALTA2);
		preguntaDao.persistOpcio(opc1);
		preguntaDao.persistOpcio(opc2);

		assertEquals(NUM_OPCIONS, preguntaDao.getOpcions(pregunta).size());
	}

	@Test
	public void testGetOpcioByCodi() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		Opcio opcio = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1);
		preguntaDao.persistOpcio(opcio);

		opcio = preguntaDao.getOpcioByCodi(opcio.getCodi());

		assertEquals(pregunta, opcio.getPregunta());
		assertEquals(OPC_TEXT1, opcio.getText());
		assertEquals(OPC_CORRECTA1, opcio.getCorrecta());
		assertEquals(OPC_DATA_ALTA1, opcio.getDataAlta());
	}

	@Test
	public void testActualitzaPregunta() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		assertEquals(PRE_ESTAT, pregunta.getEstat());

		// modificam l'estat a la pregunta
		pregunta.setEstat(PRE_ESTAT_NO);
		Pregunta pregunta2 = preguntaDao.updatePregunta(pregunta);

		// comprovam que s'ha modificat correctament, però que segueix sent la mateixa
		// pregunta
		assertEquals(pregunta.getCodi(), pregunta2.getCodi());
		assertEquals(PRE_ESTAT_NO, pregunta2.getEstat());
	}

	@Test
	public void testEliminaPregunta() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		// Consultam quantes preguntes tenim inserides a BD
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes();
		assertEquals(1, llistaPreguntes.size());

		// Eliminam la pregunta, i tornam a consultar
		preguntaDao.removePregunta(pregunta);
		llistaPreguntes = preguntaDao.getLlistaPreguntes();
		assertEquals(0, llistaPreguntes.size());
	}

	@Test
	public void testEliminaOpcio() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		// vinculam dues opcions a la pregunta
		Opcio opc1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1);
		Opcio opc2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, OPC_CORRECTA2, OPC_DATA_ALTA2);
		preguntaDao.persistOpcio(opc1);
		preguntaDao.persistOpcio(opc2);

		// Consultam quantes opcions tenim inserides a BD
		assertEquals(NUM_OPCIONS, preguntaDao.getOpcions(pregunta).size());

		// Eliminam les opcions de la pregunta
		for (Opcio opcio : preguntaDao.getOpcions(pregunta))
			preguntaDao.removeOpcio(opcio);

		// Comprobam que la pregunta ja no té opcions
		assertEquals(0, preguntaDao.getOpcions(pregunta).size());
	}

	@Test
	public void testModificaOpcio() {
		String tipusVof = Pregunta.TIPUS_VOF;

		// Definim una pregunta VOF
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, tipusVof, PRE_VOF_VERTADER, PRE_ESTAT);
		preguntaDao.persistPregunta(pregunta);

		// vinculam dues opcions a la pregunta
		Opcio opc1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
		Opcio opc2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
		preguntaDao.persistOpcio(opc1);
		preguntaDao.persistOpcio(opc2);

		// Comprovam que la opció 1 és la correcta
		assertEquals(Boolean.TRUE, opc1.getCorrecta());
		assertEquals(Boolean.FALSE, opc2.getCorrecta());

		// Modificam el camp correcta només de la primera opció
		opc1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.FALSE, OPC_DATA_ALTA1);
		preguntaDao.updateOpcio(opc1);

		// Comprovam que la opció 1 ara és incorrecta, i la opció 2 roman igual
		assertEquals(Boolean.FALSE, opc1.getCorrecta());
		assertEquals(Boolean.FALSE, opc2.getCorrecta());
	}

}
