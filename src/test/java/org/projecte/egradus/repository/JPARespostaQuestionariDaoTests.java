package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
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
public class JPARespostaQuestionariDaoTests {

	@Autowired
	private RespostaQuestionariDao respostaQuestionariDao;

	@Autowired
	private RespostaPreguntaDao respostaPreguntaDao;

	@Autowired
	private QuestionariDao questionariDao;

	@Autowired
	private PreguntaQuestionariDao pqDao;

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

	// Dades de les Persones
	// ------------------------------------------------------------------------------------------------------------
	private static String PRO_NOM = "Aina";
	private static String PRO_PRIMER_LLINATGE = "González";
	private static String PRO_CORREU = "ainagonzalez@correu.es";
	private static String PRO_ALIES = "aina";
	private static String PRO_CLAU = "ainagonzalez";
	private static String PRO_DATA_ALTA = "20/09/2014";

	private static String ALU_NOM = "Miquel";
	private static String ALU_PRIMER_LLINATGE = "Garcia";
	private static String ALU_CORREU = "m.garcia@correu.es";
	private static String ALU_ALIES = "miquelet";
	private static String ALU_CLAU = "claumiquel";
	private static String ALU_DATA_ALTA = "20/11/2013";

	private static String ALU2_NOM = "Juan";
	private static String ALU2_PRIMER_LLINATGE = "Estarelles";
	private static String ALU2_CORREU = "estarelles@yahoo.es";
	private static String ALU2_ALIES = "juanest";
	private static String ALU2_CLAU = "juanest";
	private static String ALU2_DATA_ALTA = "01/03/2014";

	// Dades de l'Assignatura
	// ------------------------------------------------------------------------------------------------------------
	private static String ASS_NOM = "Intel·ligència Artificial";
	private static String ASS_DESCRIPCIO = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR = "clau_inteli_artifi";
	private static String ASS_CLAU_ALUMNE = "clau_intart";
	private static String ASS_DATA_ALTA = "01/10/2013";
	private static String ASS_ANY_ACADEMIC = "2013-14";
	private static String ASS_CODI_REF = "INTELIART";

	// Dades del Qüestionari
	// ------------------------------------------------------------------------------------------------------------
	private static String QST_NOM = "Nom del qüestionari";
	private static String QST_DESCRIPCIO = "Descripció del qüestionari";
	private static Float QST_DIFICULTAT_TEO = 0.342f;
	private static Float QST_DIFICULTAT_PRA = null;
	private static Date QST_DATA_ALTA = new Date();
	private static Float QST_PES_ES1 = 0.65f;
	private static Float QST_PES_VOF = 0.35f;

	// Dades de la Pregunta 1
	// ------------------------------------------------------------------------------------------------------------
	private static String PRE1_ENUNCIAT = "Aquest és l'enunciat de la pregunta d'escollir 1 opció entre 3";
	private static Float PRE1_DIFICULTAT_TEO = 0.5f;
	private static Float PRE1_DIFICULTAT_PRA = 0.99f;
	private static Boolean PRE1_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date PRE1_DATA_ALTA = new Date();
	private static String PRE1_TIPUS = Pregunta.TIPUS_ES1;
	private static Boolean PRE1_VOF_VERTADER = null;
	// Dades de les Opcions de la Pregunta 1
	private static String OPC1_PRE1_TEXT = "Text de la opció 1";
	private static Boolean OPC1_PRE1_CORRECTA = Boolean.FALSE;
	private static Date OPC1_PRE1_DATA_ALTA = UtilTest.getDate("11/10/2014");
	private static String OPC2_PRE1_TEXT = "Text de la opció 2";
	private static Boolean OPC2_PRE1_CORRECTA = Boolean.TRUE;
	private static Date OPC2_PRE1_DATA_ALTA = UtilTest.getDate("11/10/2014");
	private static String OPC3_PRE1_TEXT = "Text de la opció 3";
	private static Boolean OPC3_PRE1_CORRECTA = Boolean.FALSE;
	private static Date OPC3_PRE1_DATA_ALTA = UtilTest.getDate("11/10/2014");

	// Dades de la Pregunta 2
	// ------------------------------------------------------------------------------------------------------------
	private static String PRE2_ENUNCIAT = "Aquest és l'enunciat de la pregunta Vertader o Fals";
	private static Float PRE2_DIFICULTAT_TEO = 0.23f;
	private static Float PRE2_DIFICULTAT_PRA = null;
	private static Boolean PRE2_RAONAR_RESPOSTA = Boolean.TRUE;
	private static Date PRE2_DATA_ALTA = new Date();
	private static String PRE2_TIPUS = Pregunta.TIPUS_VOF;
	private static Boolean PRE2_VOF_VERTADER = Boolean.TRUE;
	// Dades de les Opcions de la Pregunta 2
	private static String OPC1_PRE2_TEXT = "Vertader";
	private static Boolean OPC1_PRE2_CORRECTA = Boolean.TRUE;
	private static Date OPC1_PRE2_DATA_ALTA = UtilTest.getDate("11/10/2014");
	private static String OPC2_PRE2_TEXT = "Fals";
	private static Boolean OPC2_PRE2_CORRECTA = Boolean.FALSE;
	private static Date OPC2_PRE2_DATA_ALTA = UtilTest.getDate("11/10/2014");

	// Dades de la Pregunta REC
	// ------------------------------------------------------------------------------------------------------------
	private static String PRE_REC_ENUNCIAT = "Aquest és l'enunciat de la pregunta de Resposta Curta";
	private static Float PRE_REC_DIFICULTAT_TEO = 0.97f;
	private static Float PRE_REC_DIFICULTAT_PRA = null;
	private static Boolean PRE_REC_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date PRE_REC_DATA_ALTA = new Date();
	private static String PRE_REC_TIPUS = Pregunta.TIPUS_REC;
	private static Boolean PRE_REC_VOF_VERTADER = null;

	// Dades pròpies de Resposta-Questionari
	private static String RQ_DATA_INICI_CNT = "10/10/2014";

	private Persona personaProfessor;
	private Persona personaAlumne;
	private Assignatura assignatura;
	private Professor professor;
	private Alumne alumne;
	private Pregunta preguntaEs1;
	private Pregunta preguntaVof;
	private Pregunta preguntaRec;
	private Opcio opcio1Es1;
	private Opcio opcio2Es1;
	private Opcio opcio3Es1;
	private Opcio opcioVertaderVof;
	private Opcio opcioFalsVof;
	private Questionari questionari;

	@Before
	public void initialize() {
		// Inserim les persones
		personaProfessor = UtilTest.inserirPersona(PRO_NOM, PRO_PRIMER_LLINATGE, PRO_CORREU, PRO_ALIES, PRO_CLAU,
				UtilTest.getDate(PRO_DATA_ALTA));
		personaDao.persistPersona(personaProfessor);
		personaAlumne = UtilTest.inserirPersona(ALU_NOM, ALU_PRIMER_LLINATGE, ALU_CORREU, ALU_ALIES, ALU_CLAU,
				UtilTest.getDate(ALU_DATA_ALTA));
		personaDao.persistPersona(personaAlumne);

		// Inserim l'assignatura (i l'associam a la persona 'personaProfessor')
		assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Inserim el rol de professor per a la persona 'personaProfessor' a
		// l'assignatura 'assignatura'
		professor = UtilTest.getProfessor(personaProfessor, assignatura);
		professorDao.persistProfessor(professor);

		// Inserim el rol d'alumne per a la persona 'personaAlumne' a l'assignatura
		// 'assignatura'
		alumne = UtilTest.getAlumne(personaAlumne, assignatura);
		alumneDao.persistAlumne(alumne);

		// Inserim la pregunta ES1
		preguntaEs1 = UtilTest.inserirPregunta(professor, PRE1_ENUNCIAT, PRE1_DIFICULTAT_TEO, PRE1_DIFICULTAT_PRA,
				PRE1_RAONAR_RESPOSTA, PRE1_DATA_ALTA, PRE1_TIPUS, PRE1_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaEs1);

		// Definim les tres opcions que té la pregunta ES1
		opcio1Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC1_PRE1_TEXT, OPC1_PRE1_CORRECTA, OPC1_PRE1_DATA_ALTA);
		opcio2Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC2_PRE1_TEXT, OPC2_PRE1_CORRECTA, OPC2_PRE1_DATA_ALTA);
		opcio3Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC3_PRE1_TEXT, OPC3_PRE1_CORRECTA, OPC3_PRE1_DATA_ALTA);

		// inserim les opcions a BD
		preguntaDao.persistOpcio(opcio1Es1);
		preguntaDao.persistOpcio(opcio2Es1);
		preguntaDao.persistOpcio(opcio3Es1);

		// les tres opcions estan enllaçades amb la pregunta ES1, però a partir de la
		// pregunta ES1 no podrem
		// obtenir les opcions si no enllaçam la pregunta a les opcions! Per tant:
		List<Opcio> opcions = new ArrayList<Opcio>();
		opcions.add(opcio1Es1);
		opcions.add(opcio2Es1);
		opcions.add(opcio3Es1);
		preguntaEs1.setOpcions(opcions);

		// Inserim la pregunta VOF
		preguntaVof = UtilTest.inserirPregunta(professor, PRE2_ENUNCIAT, PRE2_DIFICULTAT_TEO, PRE2_DIFICULTAT_PRA,
				PRE2_RAONAR_RESPOSTA, PRE2_DATA_ALTA, PRE2_TIPUS, PRE2_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaVof);

		// Definim les dues opcions que té la pregunta VOF
		opcioVertaderVof = UtilTest.inserirOpcio(preguntaVof, OPC1_PRE2_TEXT, OPC1_PRE2_CORRECTA, OPC1_PRE2_DATA_ALTA);
		opcioFalsVof = UtilTest.inserirOpcio(preguntaVof, OPC2_PRE2_TEXT, OPC2_PRE2_CORRECTA, OPC2_PRE2_DATA_ALTA);

		// inserim les opcions a BD
		preguntaDao.persistOpcio(opcioVertaderVof);
		preguntaDao.persistOpcio(opcioFalsVof);

		// les dues opcions estan enllaçades amb la pregunta VOF, però a partir de la
		// pregunta VOF no podrem
		// obtenir les opcions si no enllaçam la pregunta a les opcions! Per tant:
		opcions = new ArrayList<Opcio>();
		opcions.add(opcioVertaderVof);
		opcions.add(opcioFalsVof);
		preguntaVof.setOpcions(opcions);

		// Definim el qüestionari
		questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO,
				QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, QST_DATA_ALTA);
		questionariDao.persistQuestionari(questionari);

		// Definim una relació pregunta-questionari per cada pregunta que tendrà el
		// qüestionari i les
		// inserim a BD
		PreguntaQuestionari pqEs1 = UtilTest.inserirPreguntaQuestionari(preguntaEs1, questionari, QST_PES_ES1);
		PreguntaQuestionari pqVof = UtilTest.inserirPreguntaQuestionari(preguntaVof, questionari, QST_PES_VOF);
		pqDao.persistPreguntaQuestionari(pqEs1);
		pqDao.persistPreguntaQuestionari(pqVof);

		// Afegim les dues relacions al qüestionari, per a tenir accés des del POJO
		// questionari
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ.add(pqEs1);
		llistaPQ.add(pqVof);
		questionari.setPreguntes(llistaPQ);

		// EN AQUEST PUNT, TENIM UN ALUMNE, UN PROFESSOR I UN QUESTIONARI DE DUES
		// PREGUNTES (UNA ES1 I UNA VOF)
	}

	@Test
	public void testSetRespostaQuestionari() {
		// 'professor' és qui assigna el qüestionari
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);
		assertNotNull(rq.getCodi());
	}

	@Test
	public void testGetRespostaQuestionariByCodi() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);
		rq = respostaQuestionariDao.getRespostaQuestionariByCodi(rq.getCodi());
		assertEquals(questionari, rq.getQuestionari());
		assertEquals(professor, rq.getAssignador());
		assertEquals(alumne, rq.getAlumne());
	}

	@Test
	public void testGetRespostesQuestionariAlumneBuida() {
		// Inserim un nou alumne a l'assignatura (que no tendrà com a pendent de
		// contestar el qüestionari)
		Persona personaAlumne = UtilTest.inserirPersona(ALU2_NOM, ALU2_PRIMER_LLINATGE, ALU2_CORREU, ALU2_ALIES,
				ALU2_CLAU, UtilTest.getDate(ALU2_DATA_ALTA));
		personaDao.persistPersona(personaAlumne);
		Alumne alumne = UtilTest.getAlumne(personaAlumne, assignatura);
		alumneDao.persistAlumne(alumne);
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostesQuestionariAlumne() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);
		assertEquals(1, llista.size());
	}

	@Test
	public void testGetQuestionarisPerCorregirSenseContestar() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.FALSE,
				Boolean.FALSE);

		// Com que el qüestionari encara no ha estat contestat, el professor encara no
		// té qüestionaris
		// pendents de corregir, per molt que contengui preguntes REC
		assertEquals(0, llista.size());
	}

	/**
	 * TODO aquest test de moment obté 1 resposta-questionari (a l'assert) ja que el
	 * getRespostesQuestionari() no filtra per si té preguntes REC o no. Quan
	 * actualitzi el mètode Dao, aquest test NO hauria de retornar cap
	 * respostaQuestionari.
	 */
	@Test
	public void testGetQuestionarisPerCorregirContestatsSensePreguntesREC() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// Contestam el qüestionari
		rq.setDataContestacioFi(new Date());
		rq.setContestat(Boolean.TRUE);
		rq.setRespostaPreguntes(getPreguntesContestades(rq));
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.FALSE,
				Boolean.FALSE);

		// El qüestionari ha sigut contestat, però així i tot, el professor no el rebrà
		// com a pendent per corregir
		// perquè no conté cap pregunta de tipus REC
		assertEquals(0, llista.size());
	}

	/**
	 * TODO aquest test de moment obté 1 resposta-questionari (a l'assert) ja que el
	 * getRespostesQuestionari() no filtra per si té preguntes REC o no. Quan
	 * actualitzi el mètode Dao, aquest test HAURIA DE SEGUIR retornant 1
	 * resposta-questionari.
	 */
	@Test
	public void testGetQuestionarisPerCorregirContestatsAmbPreguntaREC() {
		// Definim i inserim la pregunta REC
		preguntaRec = UtilTest.inserirPregunta(professor, PRE_REC_ENUNCIAT, PRE_REC_DIFICULTAT_TEO,
				PRE_REC_DIFICULTAT_PRA, PRE_REC_RAONAR_RESPOSTA, PRE_REC_DATA_ALTA, PRE_REC_TIPUS, PRE_REC_VOF_VERTADER,
				Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaRec);

		// Definim i inserim la relació pregunta-questionari
		// NOTA: La definim amb un pès de 0, ja que en l'àmbit d'aquest Test no afectarà
		// que hi hagi una
		// pregunta del qüestionari amb pès 0
		PreguntaQuestionari pqRec = UtilTest.inserirPreguntaQuestionari(preguntaRec, questionari, 0f);
		pqDao.persistPreguntaQuestionari(pqRec);

		// Obtenim la llista de preguntes del qüestionari (que segons el @Before són 2
		// preguntes)
		// i afegim aquesta nova pregunta REC
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ = questionari.getPreguntes();
		llistaPQ.add(pqRec);
		questionari.setPreguntes(llistaPQ);

		// Ara si, començam:
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// Contestam el qüestionari
		rq.setDataContestacioFi(new Date());
		rq.setContestat(Boolean.TRUE);
		rq.setRespostaPreguntes(getPreguntesContestadesAmbRec(rq));
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.FALSE,
				Boolean.FALSE);

		// El qüestionari ha sigut contestat. Donat que el qüestionari conté una
		// pregunta REC, el professor
		// la rebrà com a pendent de corregir
		assertEquals(1, llista.size());
	}

	@Test
	public void testMergeRespostaQuestionariDataContestacioInici() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// ens guardam el codi per la comprovació final
		int codi = rq.getCodi();

		// en aquest punt, l'atribut dataContestacioInici no val res:
		assertNull(rq.getDataContestacioInici());

		// assignam un valor a aquest atribut
		rq.setDataContestacioInici(UtilTest.getDate(RQ_DATA_INICI_CNT));
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		// llegim la respostaQuestionari de BD (per no consultar-la directament de rq,
		// ja que acabam
		// de fer el setDataContestacioInici() explícitament, i lo que volem comprovar,
		// en realitat,
		// és que s'ha inserit correctament a BD:
		List<RespostaQuestionari> respostesQuestionari = respostaQuestionariDao.getRespostesQuestionari(alumne,
				Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
		rq = respostesQuestionari.get(0);

		// comprovam que té el valor d'atribut que toca
		assertEquals(UtilTest.getDate(RQ_DATA_INICI_CNT), rq.getDataContestacioInici());

		// comprovam, també, que els codis de les resposta-questionari que estam
		// comparant coincideix
		assertEquals(codi, rq.getCodi(), 0);
	}

	@Test
	public void testGetRespostaQuestionariNoExistent() {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostaQuestionari(questionari, professor,
				alumne);

		// comprovam que no s'ha trobat cap resposta-pregunta
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostaQuestionariOk() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// ens guardam el codi per la comprovació final
		int codi = rq.getCodi();

		rq = respostaQuestionariDao.getRespostaQuestionari(questionari, professor, alumne).get(0);

		// comprovam que són la mateixa resposta-questionari
		assertEquals(codi, rq.getCodi(), 0);
	}

	@Test
	public void testGetRespostesQuestionariContestadesAlumneBuida() {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.FALSE,
				Boolean.FALSE, Boolean.TRUE);
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostesQuestionariContestadesAlumne() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// En aquest moment, l'alumne té un qüestionari assignat pendent per contestar.
		// Comprovam que no el té com a contestat
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);
		assertEquals(1, llista.size());

		// Contestam el qüestionari
		rq.setDataContestacioFi(new Date());
		rq.setContestat(Boolean.TRUE);
		rq.setRespostaPreguntes(getPreguntesContestades(rq));
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		// Comprovam que l'alumne ja té un qüestionari contestat ...
		llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
		assertEquals(1, llista.size());

		// ... i que no en té cap com a pendent de contestar
		llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostesPreguntaDunaRespostaQuestionari() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// Comprovam que, abans de contestar el qüestionari, aquest no té
		// resposta-preguntes
		// vinculades
		assertEquals(0, respostaQuestionariDao.getRespostaPreguntes(rq).size());

		// Contestam el qüestionari
		rq.setDataContestacioFi(new Date());
		rq.setContestat(Boolean.TRUE);
		rq.setRespostaPreguntes(getPreguntesContestades(rq));
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		// Comprovam que, després d'haver contestat el qüestionari, aquest té una
		// resposta-pregunta per cada una de les preguntes que tenia vinculades
		assertEquals(rq.getQuestionari().getPreguntes().size(), respostaQuestionariDao.getRespostaPreguntes(rq).size());
	}

	@Test
	public void testGetRespostesQuestionariCorregits() {
		// Definim i inserim la pregunta REC
		preguntaRec = UtilTest.inserirPregunta(professor, PRE_REC_ENUNCIAT, PRE_REC_DIFICULTAT_TEO,
				PRE_REC_DIFICULTAT_PRA, PRE_REC_RAONAR_RESPOSTA, PRE_REC_DATA_ALTA, PRE_REC_TIPUS, PRE_REC_VOF_VERTADER,
				Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaRec);

		// Definim i inserim la relació pregunta-questionari
		// NOTA: La definim amb un pès de 0, ja que en l'àmbit d'aquest Test no afectarà
		// que hi hagi una
		// pregunta del qüestionari amb pès 0
		PreguntaQuestionari pqRec = UtilTest.inserirPreguntaQuestionari(preguntaRec, questionari, 0f);
		pqDao.persistPreguntaQuestionari(pqRec);

		// Obtenim la llista de preguntes del qüestionari (que segons el @Before són 2
		// preguntes)
		// i afegim aquesta nova pregunta REC
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ = questionari.getPreguntes();
		llistaPQ.add(pqRec);
		questionari.setPreguntes(llistaPQ);

		// Ara si, començam:
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// Abans de contestar el qüestionari, el professor no té cap qüestionari pendent
		// de corregir ni corregit
		assertEquals(0, respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.FALSE, Boolean.FALSE).size());
		assertEquals(0, respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.TRUE, Boolean.FALSE).size());

		// Contestam el qüestionari
		rq.setDataContestacioFi(new Date());
		rq.setContestat(Boolean.TRUE);
		rq.setRespostaPreguntes(getPreguntesContestadesAmbRec(rq));
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		// El qüestionari té una ES1, una VOF i una REC, per tant, el professor tendrà
		// un
		// qüestionari pendent de corregir però no corregit
		assertEquals(1, respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.FALSE, Boolean.FALSE).size());
		assertEquals(0, respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.TRUE, Boolean.FALSE).size());

		// Corregim el qüestionari
		rq.setCorregit(Boolean.TRUE);
		rq.setCorrector(professor);
		rq.setDataCorreccio(new Date());
		rq.setNota(5.75f);

		// El professor, en aquest punt, no tendrà cap qüestionari pendent de corregir i
		// en tendrà
		// un corregit
		assertEquals(0, respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.FALSE, Boolean.FALSE).size());
		assertEquals(1, respostaQuestionariDao.getRespostesQuestionari(professor, Boolean.TRUE, Boolean.FALSE).size());
	}

	@Test
	public void testGetRespostesQuestionariByQuestionariIAssignatura() {
		// En aquest moment, cap qüestionari ha participat a l'assignatura
		assertEquals(0,
				respostaQuestionariDao.getRespostesQuestionari(questionari, assignatura, null, null, null).size());

		// Enviam una resposta-questionari
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);

		// En aquest moment, hi ha un qüestionari que ha participat a l'assignatura,
		// però no està contestat
		// ni corregit
		assertEquals(1,
				respostaQuestionariDao.getRespostesQuestionari(questionari, assignatura, null, null, null).size());
		assertEquals(1, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.FALSE, null, null).size());
		assertEquals(0, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, null, null).size());
		assertEquals(0, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, Boolean.TRUE, null).size());
		assertEquals(0, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, Boolean.FALSE, null).size());

		// Contestam el qüestionari
		rq.setDataContestacioFi(new Date());
		rq.setContestat(Boolean.TRUE);
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		// En aquest moment, la pregunta que ha participat a l'assignatura està
		// contestada però no corregit
		assertEquals(1,
				respostaQuestionariDao.getRespostesQuestionari(questionari, assignatura, null, null, null).size());
		assertEquals(0, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.FALSE, null, null).size());
		assertEquals(1, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, null, null).size());
		assertEquals(0, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, Boolean.TRUE, null).size());
		assertEquals(1, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, Boolean.FALSE, null).size());

		// Corregim la pregunta
		rq.setDataCorreccio(new Date());
		rq.setCorregit(Boolean.TRUE);
		rq.setCorrector(professor);
		rq.setNota(9.5f);
		respostaQuestionariDao.updateRespostaQuestionari(rq);

		// En aquest moment, el qüestionari que ha participat a l'assignatura està
		// contestat i corregit
		assertEquals(1,
				respostaQuestionariDao.getRespostesQuestionari(questionari, assignatura, null, null, null).size());
		assertEquals(0, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.FALSE, null, null).size());
		assertEquals(1, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, null, null).size());
		assertEquals(1, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, Boolean.TRUE, null).size());
		assertEquals(0, respostaQuestionariDao
				.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, Boolean.FALSE, null).size());
	}

	/**
	 * En aquest mètode privat contestarem les dues preguntes del qüestionari
	 * 
	 * @param rq
	 * @return llistat de resposta-preguntes resultant
	 */
	private List<RespostaPregunta> getPreguntesContestades(RespostaQuestionari rq) {
		List<RespostaPregunta> llistaRespostaPregunta = new ArrayList<RespostaPregunta>();
		List<PreguntaQuestionari> llistaPq = rq.getQuestionari().getPreguntes();
		return contestarPreguntes(llistaPq, llistaRespostaPregunta, rq);
	}

	/**
	 * En aquest mètode privat contestarem les 3 preguntes del qüestionari (les dues
	 * primeres són les que s'han definit per defecte en el @Before i la tercera és
	 * la pregunta REC)
	 * 
	 * @param rq
	 * @return llistat de resposta-preguntes resultant
	 */
	private List<RespostaPregunta> getPreguntesContestadesAmbRec(RespostaQuestionari rq) {
		List<RespostaPregunta> llistaRespostaPregunta = new ArrayList<RespostaPregunta>();

		List<PreguntaQuestionari> llistaPq = rq.getQuestionari().getPreguntes();
		PreguntaQuestionari pqRec = llistaPq.remove(2); // l'índex de la pregunta REC

		// Contestam la pregunta REC per separat
		RespostaPregunta rp = new RespostaPregunta(pqRec.getPregunta(), professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextResposta("resposta de la pregunta REC!");
		respostaPreguntaDao.updateRespostaPregunta(rp);

		// i després contestam les altres dues preguntes per defecte d'inici
		return contestarPreguntes(llistaPq, llistaRespostaPregunta, rq);
	}

	/**
	 * mètode que retorna la llistaRespostaPregunta amb les resposta-pregunta ja
	 * contestades segons la llista de relacions pregunta-questionari que li passam
	 * també per paràmetre
	 * 
	 * @param llistaPq
	 * @param llistaRespostaPregunta
	 * @return
	 */
	private List<RespostaPregunta> contestarPreguntes(List<PreguntaQuestionari> llistaPq,
			List<RespostaPregunta> llistaRespostaPregunta, RespostaQuestionari rq) {
		// Contestam la 1º pregunta (ES1)
		RespostaPregunta rp = new RespostaPregunta(llistaPq.get(0).getPregunta(), professor, alumne);
		rp.setRespostaQuestionari(rq); // vinculam aquesta resposta-pregunta a la resposta-questionari actual
		respostaPreguntaDao.persistRespostaPregunta(rp);
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setOpcionsMarcades(new ArrayList<OpcioResposta>()); // no ha contestat cap opció
		respostaPreguntaDao.updateRespostaPregunta(rp);

		llistaRespostaPregunta.add(rp);

		// Contestam la 2º pregunta (VOF)
		rp = new RespostaPregunta(llistaPq.get(1).getPregunta(), professor, alumne);
		rp.setRespostaQuestionari(rq); // vinculam aquesta resposta-pregunta a la resposta-questionari actual
		respostaPreguntaDao.persistRespostaPregunta(rp);
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta("raonament de la resposta");
		rp.setOpcionsMarcades(new ArrayList<OpcioResposta>()); // no ha contestat cap opció
		respostaPreguntaDao.updateRespostaPregunta(rp);

		llistaRespostaPregunta.add(rp);

		return llistaRespostaPregunta;
	}

}
