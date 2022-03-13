package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Questionari;
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
public class JPAPreguntaQuestionariDaoTests {

	@Autowired
	private PersonaDao personaDao;

	@Autowired
	private AssignaturaRepository assignaturaDao;

	@Autowired
	private ProfessorDao professorDao;

	@Autowired
	private PreguntaDao preguntaDao;

	@Autowired
	private QuestionariDao questionariDao;

	@Autowired
	private PreguntaQuestionariDao pqDao;

	// Dades de la Persona
	private static String PER_NOM = "Miquel";
	private static String PER_PRIMER_LLINATGE = "Garcia";
	private static String PER_CORREU = "m.garcia@correu.es";
	private static String PER_ALIES = "miquelet";
	private static String PER_CLAU = "claumiquel";
	private static String PER_DATA_ALTA = "20/11/2013";

	// Dades de l'Assignatura
	private static String ASS_NOM = "Intel·ligència Artificial";
	private static String ASS_DESCRIPCIO = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR = "clau_inteli_artifi";
	private static String ASS_CLAU_ALUMNE = "clau_intart";
	private static String ASS_DATA_ALTA = "01/10/2013";
	private static String ASS_ANY_ACADEMIC = "2013-14";
	private static String ASS_CODI_REF = "INTELIART";

	// Dades de la Pregunta 1
	private static String PRE1_ENUNCIAT = "Aquest és l'enunciat de la pregunta 1";
	private static Float PRE1_DIFICULTAT_TEO = 0.5f;
	private static Float PRE1_DIFICULTAT_PRA = 0.99f;
	private static Boolean PRE1_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date PRE1_DATA_ALTA = new Date();
	private static String PRE1_TIPUS = Pregunta.TIPUS_VOF;
	private static Boolean PRE1_VOF_VERTADER = Boolean.FALSE;

	// Dades de la Pregunta 2
	private static String PRE2_ENUNCIAT = "Aquest és l'enunciat de la pregunta 2";
	private static Float PRE2_DIFICULTAT_TEO = 0.8f;
	private static Float PRE2_DIFICULTAT_PRA = null;
	private static Boolean PRE2_RAONAR_RESPOSTA = Boolean.TRUE;
	private static Date PRE2_DATA_ALTA = new Date();
	private static String PRE2_TIPUS = Pregunta.TIPUS_REC;
	private static Boolean PRE2_VOF_VERTADER = null;

	// Dades del Questionari 1
	private static String QST1_NOM = "Nom del qüestionari 1";
	private static String QST1_DESCRIPCIO = "Descripció del qüestionari 1";
	private static Float QST1_DIFICULTAT_TEO = 0.5f;
	private static Float QST1_DIFICULTAT_PRA = 0.99f;
	private static String QST1_DATA_ALTA_REF = "15/11/2014";
	private static String QST1_ESTAT = Questionari.ESTAT_EDITABLE;

	// Dades del Questionari 1
	private static String QST2_NOM = "Nom del qüestionari 2";
	private static String QST2_DESCRIPCIO = "Descripció del qüestionari 2";
	private static Float QST2_DIFICULTAT_TEO = 0.2f;
	private static Float QST2_DIFICULTAT_PRA = 0.21f;
	private static String QST2_DATA_ALTA_REF = "10/11/2014";
	private static String QST2_ESTAT = Questionari.ESTAT_EDITABLE;

	// Dades de la relació Pregunta-Questionari
	private static Float PQ_PES_1 = 0.45f;
	private static Float PQ_PES_2 = 0.55f;

	private Persona persona;
	private Assignatura assignatura;
	private Professor professor;
	private Pregunta pregunta1;
	private Pregunta pregunta2;
	private Questionari questionari1;
	private Questionari questionari2;
	private PreguntaQuestionari pq;

	@Before
	public void initialize() {
		persona = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU,
				UtilTest.getDate(PER_DATA_ALTA));
		personaDao.persistPersona(persona);
		assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO,
				ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		professor = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(professor);

		// cream dos preguntes
		pregunta1 = UtilTest.inserirPregunta(professor, PRE1_ENUNCIAT, PRE1_DIFICULTAT_TEO, PRE1_DIFICULTAT_PRA,
				PRE1_RAONAR_RESPOSTA, PRE1_DATA_ALTA, PRE1_TIPUS, PRE1_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(pregunta1);
		pregunta2 = UtilTest.inserirPregunta(professor, PRE2_ENUNCIAT, PRE2_DIFICULTAT_TEO, PRE2_DIFICULTAT_PRA,
				PRE2_RAONAR_RESPOSTA, PRE2_DATA_ALTA, PRE2_TIPUS, PRE2_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(pregunta2);

		// cream dos questionaris
		questionari1 = UtilTest.inserirQuestionari(professor, QST1_NOM, QST1_DESCRIPCIO, QST1_DIFICULTAT_TEO,
				QST1_DIFICULTAT_PRA, QST1_ESTAT, UtilTest.getDate(QST1_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari1);
		questionari2 = UtilTest.inserirQuestionari(professor, QST2_NOM, QST2_DESCRIPCIO, QST2_DIFICULTAT_TEO,
				QST2_DIFICULTAT_PRA, QST2_ESTAT, UtilTest.getDate(QST2_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari2);
	}

	@Test
	public void testGetRelacionsPQBuit() {
		assertEquals(0, pqDao.getRelacionsPreguntaQuestionari().size());
	}

	@Test
	public void testGetPQPersistPQ() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);
		assertEquals(1, pqDao.getRelacionsPreguntaQuestionari().size());
	}

	@Test
	public void testGetPQTotCorrecteNotNull() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);

		// obtenim la relació pregunta-questionari acabada d'inserir i l'emmagatzemam
		// a una nova variable
		PreguntaQuestionari pq2 = pqDao.getPreguntaQuestionari(pregunta1, questionari1).get(0);
		assertNotNull(pq2.getCodi());
	}

	@Test
	public void testGetPQTotCorrecteValor() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);

		// obtenim la relació pregunta-questionari acabada d'inserir i l'emmagatzemam
		// a una nova variable
		PreguntaQuestionari pq2 = pqDao.getPreguntaQuestionari(pregunta1, questionari1).get(0);

		// realitzam la comprovació comparant els enunciats de les preguntes obtingudes
		assertEquals(pq.getPregunta().getEnunciat(), pq2.getPregunta().getEnunciat());
	}

	@Test
	public void testGetPQPreguntaMalament() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);

		// Cercam la relació Pregunta-Questionari amb una Pregunta distinta
		List<PreguntaQuestionari> llistaPQ = pqDao.getPreguntaQuestionari(pregunta2, questionari1);
		assertEquals(0, llistaPQ.size());
	}

	@Test
	public void testGetPQQuestionariMalament() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);

		// Cercam la relació Pregunta-Questionari amb un Questionari distint
		List<PreguntaQuestionari> llistaPQ = pqDao.getPreguntaQuestionari(pregunta1, questionari2);
		assertEquals(0, llistaPQ.size());
	}

	@Test
	public void testGetPQPreguntaBuit() {
		List<PreguntaQuestionari> llistaPQ = pqDao.getRelacionsPreguntaQuestionari(pregunta1);
		assertEquals(0, llistaPQ.size());
	}

	@Test
	public void testGetPQPregunta() {
		// feim que el questionari1 tengui les preguntes 1 i 2
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);
		PreguntaQuestionari pq2 = UtilTest.inserirPreguntaQuestionari(pregunta2, questionari1, PQ_PES_2);
		pqDao.persistPreguntaQuestionari(pq2);

		// consultam a quants questionaris apareix la pregunta1
		List<PreguntaQuestionari> llistaPQ = pqDao.getRelacionsPreguntaQuestionari(pregunta1);
		assertEquals(1, llistaPQ.size());
	}

	@Test
	public void testGetPQQuestionariBuit() {
		List<PreguntaQuestionari> llistaPQ = pqDao.getRelacionsPreguntaQuestionari(questionari1);
		assertEquals(0, llistaPQ.size());
	}

	@Test
	public void testGetPQQuestionari() {
		// feim que el questionari1 tengui les preguntes 1 i 2
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);
		PreguntaQuestionari pq2 = UtilTest.inserirPreguntaQuestionari(pregunta2, questionari1, PQ_PES_2);
		pqDao.persistPreguntaQuestionari(pq2);

		// consultam quantes preguntes té el questionari1
		List<PreguntaQuestionari> llistaPQ = pqDao.getRelacionsPreguntaQuestionari(questionari1);
		assertEquals(2, llistaPQ.size());
	}

	@Test
	public void testGetPQByCodiNoExistent() {
		assertNull(pqDao.getPreguntaQuestionariByCodi(0));
	}

	@Test
	public void testGetPQByCodi() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);

		// Definim una altre relació PQ
		PreguntaQuestionari pq2 = UtilTest.inserirPreguntaQuestionari(pregunta2, questionari1, PQ_PES_2);
		pqDao.persistPreguntaQuestionari(pq2);

		// Llegim el codi de la primera relació PQ
		int codiPQ1 = pq.getCodi();

		// Llegim el codi de la segona relació PQ
		int codiPQ2 = pq2.getCodi();

		assertEquals(PRE1_ENUNCIAT, pqDao.getPreguntaQuestionariByCodi(codiPQ1).getPregunta().getEnunciat());
		assertEquals(PRE2_ENUNCIAT, pqDao.getPreguntaQuestionariByCodi(codiPQ2).getPregunta().getEnunciat());
	}

	@Test
	public void testUpdatePQ() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);

		// Comprovam que el pès és el que acabam d'inserir
		assertEquals(PQ_PES_1, pq.getPes());

		// modificam el pès de la relació PQ
		Float nouPes = PQ_PES_1 / 2f;
		pq.setPes(nouPes);

		// Actualitzam la relació PQ i comprovam que el pès s'ha modificat
		pqDao.updatePreguntaQuestionari(pq);
		assertEquals(nouPes, pq.getPes());
	}

	@Test
	public void testRemovePQ() {
		pq = UtilTest.inserirPreguntaQuestionari(pregunta1, questionari1, PQ_PES_1);
		pqDao.persistPreguntaQuestionari(pq);

		// Comprovam que hi ha una relació PQ
		assertEquals(1, pqDao.getRelacionsPreguntaQuestionari(pregunta1).size());

		// eliminam la relació PQ i comprovam que ha no n'hi ha cap
		pqDao.removePreguntaQuestionari(pq);
		assertEquals(0, pqDao.getRelacionsPreguntaQuestionari(pregunta1).size());
	}

}
