package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
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
public class RespostaQuestionariServiceImplTests {

	@Autowired
	private RespostaQuestionariService respostaQuestionariService;

	@Autowired
	private QuestionariService questionariService;

	@Autowired
	private PreguntaService preguntaService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private AssignaturaService assignaturaService;

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private AlumneService alumneService;

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

	private static String ALU3_NOM = "Xavier";
	private static String ALU3_PRIMER_LLINATGE = "Colomar";
	private static String ALU3_CORREU = "xcolomar@uib.es";
	private static String ALU3_ALIES = "xavi";
	private static String ALU3_CLAU = "xaviclau";
	private static String ALU3_DATA_ALTA = "01/02/2014";

	private static String ALU4_NOM = "Elvira";
	private static String ALU4_PRIMER_LLINATGE = "Sánchez";
	private static String ALU4_CORREU = "elvira89@uib.es";
	private static String ALU4_ALIES = "elvis";
	private static String ALU4_CLAU = "elvisclau";
	private static String ALU4_DATA_ALTA = "04/10/2014";

	private static String ALU5_NOM = "Albert";
	private static String ALU5_PRIMER_LLINATGE = "Colomar";
	private static String ALU5_CORREU = "acolomar@uib.es";
	private static String ALU5_ALIES = "alberco";
	private static String ALU5_CLAU = "alberco1";
	private static String ALU5_DATA_ALTA = "22/09/2014";

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

	private static Float PRE_REC_PES = 0.4f;
//	private static Float PRE_REC_NOTA = 2.25f;

	private Persona personaProfessor;
	private Persona personaAlumne1;
	private Persona personaAlumne2;
	private Persona personaAlumne3;
	private Persona personaAlumne4;
	private Persona personaAlumne5;
	private Assignatura assignatura;
	private Professor professor;
	private Alumne alumne1;
	private Alumne alumne2;
	private Alumne alumne3;
	private Alumne alumne4;
	private Alumne alumne5;
	private Pregunta preguntaEs1;
	private Pregunta preguntaVof;
	private Pregunta preguntaRec;
	private Opcio opcio1Es1;
	private Opcio opcio2Es1;
	private Opcio opcio3Es1;
	private Opcio opcioVertaderVof;
	private Opcio opcioFalsVof;
	private Questionari questionari;
	private Questionari questionariRec;

	@Before
	public void initialize() {
		// Inserim les persones (1 professor i 5 alumnes)
		personaProfessor = UtilTest.inserirPersona(PRO_NOM, PRO_PRIMER_LLINATGE, PRO_CORREU, PRO_ALIES, PRO_CLAU,
				UtilTest.getDate(PRO_DATA_ALTA));
		personaService.insereixPersona(personaProfessor);

		personaAlumne1 = UtilTest.inserirPersona(ALU_NOM, ALU_PRIMER_LLINATGE, ALU_CORREU, ALU_ALIES, ALU_CLAU,
				UtilTest.getDate(ALU_DATA_ALTA));
		personaAlumne2 = UtilTest.inserirPersona(ALU2_NOM, ALU2_PRIMER_LLINATGE, ALU2_CORREU, ALU2_ALIES, ALU2_CLAU,
				UtilTest.getDate(ALU2_DATA_ALTA));
		personaAlumne3 = UtilTest.inserirPersona(ALU3_NOM, ALU3_PRIMER_LLINATGE, ALU3_CORREU, ALU3_ALIES, ALU3_CLAU,
				UtilTest.getDate(ALU3_DATA_ALTA));
		personaAlumne4 = UtilTest.inserirPersona(ALU4_NOM, ALU4_PRIMER_LLINATGE, ALU4_CORREU, ALU4_ALIES, ALU4_CLAU,
				UtilTest.getDate(ALU4_DATA_ALTA));
		personaAlumne5 = UtilTest.inserirPersona(ALU5_NOM, ALU5_PRIMER_LLINATGE, ALU5_CORREU, ALU5_ALIES, ALU5_CLAU,
				UtilTest.getDate(ALU5_DATA_ALTA));
		personaService.insereixPersona(personaAlumne1);
		personaService.insereixPersona(personaAlumne2);
		personaService.insereixPersona(personaAlumne3);
		personaService.insereixPersona(personaAlumne4);
		personaService.insereixPersona(personaAlumne5);

		// Inserim l'assignatura (i l'associam a la persona 'personaProfessor').
		// NOTA: Aquesta acció també insereix el rol de professor per a la persona
		// 'personaProfessor' a l'assignatura 'assignatura'. Encara que no haguem
		// d'inserir-lo ...
		assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaService.insereixAssignatura(assignatura);

		// ... l'hem d'agafar per a què el POJO professor estigui alimentat amb
		// la informació de BD
		professor = professorService.getProfessor(personaProfessor, assignatura);

		// Inserim el rol d'alumne per a les 5 persones alumne 'personaAlumneX' a
		// l'assignatura 'assignatura'
		alumne1 = UtilTest.getAlumne(personaAlumne1, assignatura);
		alumne2 = UtilTest.getAlumne(personaAlumne2, assignatura);
		alumne3 = UtilTest.getAlumne(personaAlumne3, assignatura);
		alumne4 = UtilTest.getAlumne(personaAlumne4, assignatura);
		alumne5 = UtilTest.getAlumne(personaAlumne5, assignatura);
		alumneService.insereixAlumne(alumne1);
		alumneService.insereixAlumne(alumne2);
		alumneService.insereixAlumne(alumne3);
		alumneService.insereixAlumne(alumne4);
		alumneService.insereixAlumne(alumne5);

		// Definim la pregunta ES1
		preguntaEs1 = UtilTest.inserirPregunta(professor, PRE1_ENUNCIAT, PRE1_DIFICULTAT_TEO, PRE1_DIFICULTAT_PRA,
				PRE1_RAONAR_RESPOSTA, PRE1_DATA_ALTA, PRE1_TIPUS, PRE1_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);

		// Definim les tres opcions que té la pregunta ES1
		opcio1Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC1_PRE1_TEXT, OPC1_PRE1_CORRECTA, OPC1_PRE1_DATA_ALTA);
		opcio2Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC2_PRE1_TEXT, OPC2_PRE1_CORRECTA, OPC2_PRE1_DATA_ALTA);
		opcio3Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC3_PRE1_TEXT, OPC3_PRE1_CORRECTA, OPC3_PRE1_DATA_ALTA);

		// afegim les opcions a la pregunta ES1 i, finalment, inserim la pregunta
		List<Opcio> llistaOpcions = new ArrayList<Opcio>();
		llistaOpcions.add(opcio1Es1);
		llistaOpcions.add(opcio2Es1);
		llistaOpcions.add(opcio3Es1);
		preguntaService.insereixPregunta(preguntaEs1, llistaOpcions);

		// Definim la pregunta VOF
		preguntaVof = UtilTest.inserirPregunta(professor, PRE2_ENUNCIAT, PRE2_DIFICULTAT_TEO, PRE2_DIFICULTAT_PRA,
				PRE2_RAONAR_RESPOSTA, PRE2_DATA_ALTA, PRE2_TIPUS, PRE2_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);

		// Definim les dues opcions que té la pregunta VOF
		opcioVertaderVof = UtilTest.inserirOpcio(preguntaVof, OPC1_PRE2_TEXT, OPC1_PRE2_CORRECTA, OPC1_PRE2_DATA_ALTA);
		opcioFalsVof = UtilTest.inserirOpcio(preguntaVof, OPC2_PRE2_TEXT, OPC2_PRE2_CORRECTA, OPC2_PRE2_DATA_ALTA);

		// afegim les opcions a la pregunta VOF i, finalment, la inserim
		llistaOpcions = new ArrayList<Opcio>();
		llistaOpcions.add(opcioVertaderVof);
		llistaOpcions.add(opcioFalsVof);
		preguntaService.insereixPregunta(preguntaVof, llistaOpcions);

		// Definim el qüestionari
		questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO,
				QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, QST_DATA_ALTA);

		// afegim les preguntes ES1 i VOF al qüestionari, així com el Map amb els pesos
		// de cada pregunta
		List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
		llistaPreguntes.add(preguntaEs1);
		llistaPreguntes.add(preguntaVof);
		Map<Integer, Float> pesos = new HashMap<Integer, Float>();
		pesos.put(preguntaEs1.getCodi(), QST_PES_ES1);
		pesos.put(preguntaVof.getCodi(), QST_PES_VOF);
		questionariService.insereixQuestionari(questionari, llistaPreguntes, pesos);

		// EN AQUEST PUNT, TENIM CINC ALUMNES, UN PROFESSOR I UN QUESTIONARI DE DUES
		// PREGUNTES (UNA ES1 I UNA VOF)
	}

	@Test
	public void testGetQuestionarisPerRespondreNull() {
		// Cap dels alumnes té un Qüestionari pendent de respondre
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne1));
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne2));
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne3));
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne4));
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne5));
	}

	@Test
	public void testEnviaQuestionari() {
		// enviam la pregunta als alumnes 1 i 2
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		alumnes.add(alumne2);
		respostaQuestionariService.enviaQuestionari(questionari.getCodi(), professor, alumnes, Boolean.FALSE);

		// comprovam que els alumnes a qui se'ls ha enviat el qüestionari
		// efectivament l'han rebut
		List<RespostaQuestionari> llistaRqAlumne1 = respostaQuestionariService.getQuestionarisPerRespondre(alumne1);
		List<RespostaQuestionari> llistaRqAlumne2 = respostaQuestionariService.getQuestionarisPerRespondre(alumne2);
		assertEquals(1, llistaRqAlumne1.size());
		assertEquals(1, llistaRqAlumne2.size());

		// dels alumnes que han rebut el qüestionari, hem de comprovar que aquestes
		// resposta-questionaris
		// duen vinculades les resposta-preguntes esperades
		assertEquals(questionari.getPreguntes().size(), llistaRqAlumne1.get(0).getRespostaPreguntes().size());
		int index = 0;
		for (RespostaPregunta rp1 : llistaRqAlumne1.get(0).getRespostaPreguntes()) {
			assertEquals(questionari.getPreguntes().get(index++).getPregunta(), rp1.getPregunta());
			assertNotNull(rp1.getDataAlta());
			assertNull(rp1.getDataContestacioInici());
			assertNull(rp1.getDataContestacioFi());
			assertNull(rp1.getDataCorreccio());
			assertEquals(alumne1, rp1.getAlumne());
			assertEquals(professor, rp1.getAssignador());
			assertFalse(rp1.getContestada());
			assertFalse(rp1.getCorregida());
			assertNull(rp1.getNota());
			assertNotNull(rp1.getRespostaQuestionari());
		}

		assertEquals(questionari.getPreguntes().size(), llistaRqAlumne2.get(0).getRespostaPreguntes().size());
		index = 0;
		for (RespostaPregunta rp2 : llistaRqAlumne2.get(0).getRespostaPreguntes()) {
			assertEquals(questionari.getPreguntes().get(index++).getPregunta(), rp2.getPregunta());
			assertNotNull(rp2.getDataAlta());
			assertNull(rp2.getDataContestacioInici());
			assertNull(rp2.getDataContestacioFi());
			assertNull(rp2.getDataCorreccio());
			assertEquals(alumne2, rp2.getAlumne());
			assertEquals(professor, rp2.getAssignador());
			assertFalse(rp2.getContestada());
			assertFalse(rp2.getCorregida());
			assertNull(rp2.getNota());
			assertNotNull(rp2.getRespostaQuestionari());
		}

		// comprovam que els damés alumnes segueixen tenint cap qüestionari
		// pendent de contestar
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne3));
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne4));
		assertNull(respostaQuestionariService.getQuestionarisPerRespondre(alumne5));
	}

	@Test
	public void testIniciaContestacioQuestionariFals() {
		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// com que no feim una crida a 'iniciarContestacioQuestionari(...);', el
		// qüestionari no s'ha
		// començat a contestar ...
		assertNull(rq.getDataContestacioInici());

		// ... però sí s'hauran generat les resposta-preguntes associades
		assertNotNull(rq.getRespostaPreguntes());
	}

	@Test
	public void testIniciaContestacioQuestionariVertader() {
		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// iniciam la contestació del qüestionari
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);

		// com que s'ha iniciat la contestació del qüestionari, ja existirà data d'inici
		// de contestació ...
		assertNotNull(rq.getDataContestacioInici());
		assertNull(rq.getDataContestacioFi());
		assertFalse(rq.getContestat());

		// Comprovam que les dates d'inici de contestació s'han assignat també a les
		// resposta-preguntes
		// però encara no hi ha cap opció marcada (ja que no hem començat a marcar
		// opcions)
		for (RespostaPregunta rp : rq.getRespostaPreguntes()) {
			assertNotNull(rp.getDataContestacioInici());
			assertEquals(0, rp.getOpcionsMarcades().size());
		}
	}

	@Test
	public void testGetQuestionarisContestats() {
		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// iniciam la contestació del qüestionari
		respostaQuestionariService.iniciarContestacioQuestionari(rq);

		// comprovam que l'alumne NO té, encara, qüestionaris contestats
		List<RespostaQuestionari> llista = respostaQuestionariService.getQuestionarisContestats(alumne1);
		assertNull(llista);

		// finalitzam la contestació del qüestionari
		respostaQuestionariService.finalitzarContestacioQuestionari(rq, null);

		// comprovam que efectivament l'alumne 1 té qüestionaris contestats
		llista = respostaQuestionariService.getQuestionarisPerCorregir(alumne1);
		assertEquals(1, llista.size());

		// comprovam que l'alumne 4, per exemple, NO té qüestionaris contestats
		llista = respostaQuestionariService.getQuestionarisContestats(alumne4);
		assertNull(llista);
	}

	@Test
	public void testFinalitzaContestacioQuestionariFals() {
		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// iniciam la contestació del qüestionari
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);

		// com que no feim una crida a 'finalitzarContestacioQuestionari(...)', el
		// qüestionari no
		// s'ha acabat de contestar:
		assertNotNull(rq.getDataContestacioInici());
		assertNull(rq.getDataContestacioFi());
		assertFalse(rq.getContestat());
	}

	@Test
	public void testFinalitzaContestacioQuestionariAmbCorreccioAutomatica() {
		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// iniciam la contestació del qüestionari
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
		questionari = rq.getQuestionari();

		// comprovam que no l'ha contestat ningú, i que no s'han definit encara temps de
		// resposta mig,
		// dificultat pràctica ni nota mitja
		assertEquals(0, questionari.getNumTotalActualitzacionsNotaMitja());
		assertNull(questionari.getTempsRespostaMig());
		assertNull(questionari.getDificultatPractica());
		assertNull(questionari.getNotaMitja());

		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();
		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);

		// Contestam les pregunta ES1 (i l'acertam)
		Pregunta pEs1 = rpEs1.getPregunta();
		OpcioResposta orPEs1 = new OpcioResposta();
		orPEs1.setRespostaPregunta(rpEs1);
		orPEs1.setOpcio(pEs1.getOpcions().get(2)); // seleccionam la 3º opció de la pregunta ES1
		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
		llistaOrPEs1.add(orPEs1);
		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
		rpEs1.setOpcionsMarcades(llistaOrPEs1);

		// Contestam la pregunta VOF (i la fallam)
		Pregunta pVof = rq.getQuestionari().getPreguntes().get(1).getPregunta();
		OpcioResposta orPVof = new OpcioResposta();
		orPVof.setRespostaPregunta(rq.getRespostaPreguntes().get(1));
		orPVof.setOpcio(pVof.getOpcions().get(0)); // seleccionam la 1º opció de la pregunta VOF
		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
		llistaOrPVof.add(orPVof);
		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
		rpVof.setOpcionsMarcades(llistaOrPVof);

		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
		llistaRp.add(rpEs1);
		llistaRp.add(rpVof);

		// finalitzam la contestació del qüestionari
		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
		questionari = rq.getQuestionari();

		// com que s'ha finalitzat la contestació del qüestionari, ja existirà data de
		// fi de contestació
		assertNotNull(rq.getDataContestacioInici());
		assertNotNull(rq.getDataContestacioFi());
		assertTrue(rq.getContestat());

		// comprovam que s'han contestat les resposta-preguntes de la
		// resposta-questionari
		for (RespostaPregunta rp : rq.getRespostaPreguntes()) {
			assertNotNull(rp.getDataContestacioInici());
			assertTrue(rp.getContestada());
			assertNotNull(rp.getDataContestacioFi());
			assertNotNull(rp.getDataCorreccio());

			// comprovam, a més, que s'han pogut corregir ja que no hi havia cap pregunta
			// REC
			assertTrue(rp.getCorregida());
			assertNotNull(rp.getNota());

			// comprovam que s'ha actualitzat el num total de contestacions, la dificultat
			// pràctica,
			// la nota mitja i el temps de resposta mig la pregunta de cada
			// resposta-pregunta
			Pregunta pregunta = rp.getPregunta();
			assertTrue(pregunta.getNumTotalActualitzacionsNotaMitja() > 0);
			assertNull(pregunta.getTempsRespostaMig()); // el temps de resposta mig NO s'actualitza quan les RP venen de
														// RQs
			assertNotNull(pregunta.getDificultatPractica());
			assertNotNull(pregunta.getNotaMitja());
		}

		// comprovam que el qüestionari s'ha corregit (ja que no hi havia cap pregunta
		// REC) i
		// que la nota de la resposta-questionari és la esperada (tenint en compte que
		// la 1º
		// pregunta ha sigut acertada i la 2º no)
		assertTrue(rq.getCorregit());
		assertNotNull(rq.getDataCorreccio());

		Float notaQuestionari = QST_PES_ES1 * 0f + QST_PES_VOF * 10f;
		assertEquals(notaQuestionari, rq.getNota());

		// comprovam que la dificultat pràctica ja està definida i que el num de
		// contestacions s'ha incrementat en 1
		assertTrue(questionari.getNumTotalActualitzacionsNotaMitja() > 0);
		assertNotNull(questionari.getTempsRespostaMig());
		assertNotNull(questionari.getDificultatPractica());
		assertNotNull(questionari.getNotaMitja());
	}

	@Test
	public void testFinalitzaContestacioQuestionariSenseCorreccioAutomatica() {
		// Generam un questionari que té 3 preguntes: ES1, VOF i REC
		questionariRec = afegirPreguntaRec(PRE_REC_PES);

		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionariRec.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// abans de contestar el qüestionari, comprovam que no hi ha cap contestació i
		// que la dificultat pràctica
		// encara no està definida
		assertNull(questionari.getDificultatPractica());
		assertEquals(0, questionari.getNumTotalActualitzacionsNotaMitja());

		// iniciam la contestació del qüestionari
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
		questionariRec = rq.getQuestionari();

		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();

		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);
		RespostaPregunta rpRec = llistaRespostesPregunta.get(2);

		// Contestam les pregunta ES1 (i l'acertam)
		Pregunta pEs1 = rpEs1.getPregunta();
		OpcioResposta orPEs1 = new OpcioResposta();
		orPEs1.setRespostaPregunta(rpEs1);
		orPEs1.setOpcio(pEs1.getOpcions().get(1)); // seleccionam la 2º opció de la pregunta ES1
		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
		llistaOrPEs1.add(orPEs1);
		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
		rpEs1.setOpcionsMarcades(llistaOrPEs1);

		// Contestam la pregunta VOF (i la fallam)
		Pregunta pVof = rpVof.getPregunta();
		OpcioResposta orPVof = new OpcioResposta();
		orPVof.setRespostaPregunta(rpVof);
		orPVof.setOpcio(pVof.getOpcions().get(1)); // seleccionam la 2º opció de la pregunta VOF
		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
		llistaOrPVof.add(orPVof);
		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
		rpVof.setOpcionsMarcades(llistaOrPVof);

		// Contestam la pregunta REC (que no es pot corregir automàticament)
		rpRec.setTextResposta("resposta a la pregunta REC");
		rpRec.setTextRaonarResposta("text raonar resposta");

		// Encapsulam les tres resposta-preguntes en una llista
		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
		llistaRp.add(rpEs1);
		llistaRp.add(rpVof);
		llistaRp.add(rpRec);

		// finalitzam la contestació del qüestionari (Passant la llista de
		// resposta-preguntes
		// que acabam de definir)
		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
		questionariRec = rq.getQuestionari();

		// com que s'ha finalitzat la contestació del qüestionari, ja existirà data de
		// fi de contestació
		assertNotNull(rq.getDataContestacioInici());
		assertNotNull(rq.getDataContestacioFi());
		assertTrue(rq.getContestat());

		// si la resposta-questionari s'ha contestat, s'ha d'haver actualitzat el temps
		// de
		// resposta mig del qüestionari
		assertNotNull(questionariRec.getTempsRespostaMig());

		// comprovam que s'han contestat les resposta-preguntes de la
		// resposta-questionari
		for (RespostaPregunta rp : rq.getRespostaPreguntes()) {
			Pregunta pregunta = rp.getPregunta();
			assertNotNull(rp.getDataContestacioInici());
			assertTrue(rp.getContestada());
			assertNotNull(rp.getDataContestacioFi());
			assertNull(pregunta.getTempsRespostaMig()); // les resposta-preguntes de la resposta-questionari no tenen
														// temps assignat

			if (pregunta.getTipus().equals(Pregunta.TIPUS_REC)) {
				// comprovam que la pregunta REC no s'ha corregit
				assertEquals(0, pregunta.getNumTotalActualitzacionsNotaMitja());
				assertFalse(rp.getCorregida());
				assertNull(rp.getDataCorreccio());
				assertNull(rp.getNota());

				// NO s'han d'haver actualitzat la dificultat pràctica i la nota mitja de la
				// pregunta
				assertNull(pregunta.getDificultatPractica());
				assertNull(pregunta.getNotaMitja());
			} else {
				// comprovam que les preguntes ES1 i VOF s'han pogut corregir
				assertTrue(rp.getCorregida());
				assertNotNull(rp.getDataCorreccio());
				assertNotNull(rp.getNota());
				assertTrue(pregunta.getNumTotalActualitzacionsNotaMitja() > 0);

				// s'han d'haver actualitzat la dificultat pràctica i la nota mitja de la
				// pregunta
				assertNotNull(pregunta.getDificultatPractica());
				assertNotNull(pregunta.getNotaMitja());
			}
		}

		// comprovam que el qüestionari NO s'ha corregit (ja que hi havia una pregunta
		// REC) i,
		// per tant, no existeix nota per a la resposta-questionari
		assertEquals(0, questionariRec.getNumTotalActualitzacionsNotaMitja());
		assertFalse(rq.getCorregit());
		assertNull(rq.getDataCorreccio());
		assertNull(rq.getNota());

		// Si la resposta-questionari no s'ha corregit, no s'han d'haver actualitzat la
		// dificultat
		// pràctica ni la nota mitja del qüestionari
		assertNull(questionariRec.getDificultatPractica());
		assertNull(questionariRec.getNotaMitja());
	}

	@Test
	public void testGetQuestionarisPerCorregirNull() {
		// D'inici, el professor no té cap qüestionari pendent de corregir
		assertNull(respostaQuestionariService.getQuestionarisPerCorregir(professor));
	}

	@Test
	public void testGetQuestionarisPerCorregir() {
		// Generam un questionari que té 3 preguntes: ES1, VOF i REC
		questionariRec = afegirPreguntaRec(PRE_REC_PES);

		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionariRec.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// D'inici, el professor no té cap qüestionari pendent de corregir ni corregit
		assertNull(respostaQuestionariService.getQuestionarisPerCorregir(professor));
		assertNull(respostaQuestionariService.getQuestionarisCorregits(professor));

		// iniciam la contestació del qüestionari
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);

		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();

		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);
		RespostaPregunta rpRec = llistaRespostesPregunta.get(2);

		// Contestam les pregunta ES1 (i l'acertam)
		Pregunta pEs1 = rpEs1.getPregunta();
		OpcioResposta orPEs1 = new OpcioResposta();
		orPEs1.setRespostaPregunta(rpEs1);
		orPEs1.setOpcio(preguntaService.getOpcio(pEs1.getOpcions().get(1).getCodi())); // seleccionam la 2º opció de la
																						// pregunta ES1
		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
		llistaOrPEs1.add(orPEs1);
		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
		rpEs1.setOpcionsMarcades(llistaOrPEs1);

		// Contestam la pregunta VOF (i la fallam)
		Pregunta pVof = rpVof.getPregunta();
		OpcioResposta orPVof = new OpcioResposta();
		orPVof.setRespostaPregunta(rpVof);
		orPVof.setOpcio(preguntaService.getOpcio(pVof.getOpcions().get(1).getCodi())); // seleccionam la 2º opció de la
																						// pregunta VOF
		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
		llistaOrPVof.add(orPVof);
		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
		rpVof.setOpcionsMarcades(llistaOrPVof);

		// Contestam la pregunta REC (que no es pot corregir automàticament)
		rpRec.setTextResposta("resposta a la pregunta REC");
		rpRec.setTextRaonarResposta("text raonar resposta");

		// Encapsulam les tres resposta-preguntes en una llista
		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
		llistaRp.add(rpEs1);
		llistaRp.add(rpVof);
		llistaRp.add(rpRec);

		// finalitzam la contestació del qüestionari (Passant la llista de
		// resposta-preguntes
		// que acabam de definir)
		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);

		// En aquest punt, el professor té un qüestionari pendent de corregir, però no
		// en
		// té cap de corregit
		assertEquals(1, respostaQuestionariService.getQuestionarisPerCorregir(professor).size());
		assertNull(respostaQuestionariService.getQuestionarisCorregits(professor));
	}

	@Test
	public void testGetQuestionarisPerCorregirFals() {
		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(),
				professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);

		// D'inici, el professor no té cap qüestionari pendent de corregir ni corregit
		assertNull(respostaQuestionariService.getQuestionarisPerCorregir(professor));
		assertNull(respostaQuestionariService.getQuestionarisCorregits(professor));

		// iniciam la contestació del qüestionari
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);

		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();

		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);

		// Contestam les pregunta ES1 (i l'acertam)
		Pregunta pEs1 = rpEs1.getPregunta();
		OpcioResposta orPEs1 = new OpcioResposta();
		orPEs1.setRespostaPregunta(rpEs1);
		orPEs1.setOpcio(pEs1.getOpcions().get(2)); // seleccionam la 3º opció de la pregunta ES1
		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
		llistaOrPEs1.add(orPEs1);
		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
		rpEs1.setOpcionsMarcades(llistaOrPEs1);

		// Contestam la pregunta VOF (i la fallam)
		Pregunta pVof = rpVof.getPregunta();
		OpcioResposta orPVof = new OpcioResposta();
		orPVof.setRespostaPregunta(rpVof);
		orPVof.setOpcio(pVof.getOpcions().get(0)); // seleccionam la 1º opció de la pregunta VOF
		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
		llistaOrPVof.add(orPVof);
		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
		rpVof.setOpcionsMarcades(llistaOrPVof);

		// Encapsulam les tres resposta-preguntes en una llista
		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
		llistaRp.add(rpEs1);
		llistaRp.add(rpVof);

		// finalitzam la contestació del qüestionari (Passant la llista de
		// resposta-preguntes
		// que acabam de definir)
		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);

		// Finalment, el professor no té cap qüestionari pendent de corregir ni corregit
		// ja que el qüestionari s'ha pogut corregir automàticament
		if (PRE1_RAONAR_RESPOSTA || PRE2_RAONAR_RESPOSTA)
			assertNotNull(respostaQuestionariService.getQuestionarisPerCorregir(professor));
		else
			assertNull(respostaQuestionariService.getQuestionarisPerCorregir(professor));

		assertNull(respostaQuestionariService.getQuestionarisCorregits(professor));
	}

	/**
	 * Verifica l'inici i el fi de correcció del qüestionari, així com el get
	 * questionaris corregits
	 */
//	@Test
//	public void testCorreccioQuestionariREC() {
//		// Generam un questionari que té 3 preguntes: ES1, VOF i REC
//		questionariRec = afegirPreguntaRec(PRE_REC_PES);
//		
//		// enviam el qüestionari a l'alumne 1
//		List<Alumne> alumnes = new ArrayList<Alumne>();
//		alumnes.add(alumne1);
//		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionariRec.getCodi(), professor, alumnes, Boolean.FALSE);
//		RespostaQuestionari rq = llistaRq.get(0);
//		
//		// iniciam la contestació del qüestionari
//		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
//		questionari = rq.getQuestionari();
//		
//		assertEquals(0, questionari.getNumTotalActualitzacionsNotaMitja());
//		assertNull(questionari.getTempsRespostaMig());
//		assertNull(questionari.getDificultatPractica());
//		assertNull(questionari.getNotaMitja());
//		
//		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();
//		
//		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
//		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);
//		RespostaPregunta rpRec = llistaRespostesPregunta.get(2);
//		
//		// Contestam les pregunta ES1 (i l'acertam)
//		Pregunta pEs1 = rpEs1.getPregunta();
//		OpcioResposta orPEs1 = new OpcioResposta();
//		orPEs1.setRespostaPregunta(rpEs1);
//		orPEs1.setOpcio(pEs1.getOpcions().get(1)); // seleccionam la 2º opció de la pregunta ES1
//		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
//		llistaOrPEs1.add(orPEs1);
//		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
//		rpEs1.setOpcionsMarcades(llistaOrPEs1);
//		
//		// Contestam la pregunta VOF (i la fallam)
//		Pregunta pVof = rpVof.getPregunta();
//		OpcioResposta orPVof = new OpcioResposta();
//		orPVof.setRespostaPregunta(rpVof);
//		orPVof.setOpcio(pVof.getOpcions().get(1)); // seleccionam la 2º opció de la pregunta VOF
//		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
//		llistaOrPVof.add(orPVof);
//		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
//		rpVof.setOpcionsMarcades(llistaOrPVof);
//		
//		// Contestam la pregunta REC (que no es pot corregir automàticament)
//		rpRec.setTextResposta("resposta a la pregunta REC");
//		rpRec.setTextRaonarResposta("text raonar resposta");
//		
//		// Encapsulam les tres resposta-preguntes en una llista
//		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
//		llistaRp.add(rpEs1);
//		llistaRp.add(rpVof);
//		llistaRp.add(rpRec);
//		
//		// finalitzam la contestació del qüestionari (Passant la llista de resposta-preguntes
//		// que acabam de definir)
//		respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
//		
//		// En aquest punt, el professor té un qüestionari pendent de corregir però NO
//		// en té cap de corregit
//		assertEquals(1, respostaQuestionariService.getQuestionarisPerCorregir(professor).size());
//		assertNull(respostaQuestionariService.getQuestionarisCorregits(professor));
//		
//		// inicialitzam la correcció del qüestionari
//		rq = respostaQuestionariService.iniciarCorreccioQuestionari(rq);
//		questionari = rq.getQuestionari();
//		
//		// ens asseguram que el qüestionari no està corregit (ni té data de correcció assignada, ni nota)
//		assertFalse(rq.getCorregit());
//		assertNull(rq.getDataCorreccio());
//		assertNull(rq.getNota());
//		
//		// La resposta-questionari s'ha contestat (per tant s'han d'haver actualitzat el temps de resposta mig 
//		// però no la dificultat pràctica ni la nota mitja, perquè encara no s`ha corregit).
//		assertNotNull(questionari.getTempsRespostaMig());
//		assertNull(questionari.getDificultatPractica());
//		assertNull(questionari.getNotaMitja());
//		
//		// finalitzam la correcció del qüestionari (sense escrire cap text però assignant una nota
//		List<String> listText = new ArrayList<String>();
//		List<Float> listNota = new ArrayList<Float>();
//		listNota.add(PRE_REC_NOTA);
//		rq = respostaQuestionariService.finalitzarCorreccioQuestionari(rq, listText, new ArrayList<String>(), listNota);
//		questionari = rq.getQuestionari();
//		
//		// ens asseguram que el qüestionari està corregit, té data de correcció assignada i la nota
//		assertTrue(rq.getCorregit());
//		assertNotNull(rq.getDataCorreccio());
//		assertTrue(questionari.getNumTotalActualitzacionsNotaMitja() > 0);
//		assertNotNull(rq.getNota());
//		assertNotNull(questionari.getDificultatPractica());
//		assertNotNull(questionari.getNotaMitja());
//		
//		// comprovam que TOTES les preguntes estan corregides, tenen data de correcció i nota
//		for (RespostaPregunta rp : rq.getRespostaPreguntes()) {
//			assertTrue(rp.getCorregida());
//			assertNotNull(rp.getDataCorreccio());
//			assertNotNull(rp.getNota());
//			assertNotNull(rp.getPregunta().getDificultatPractica());
//			assertNotNull(rp.getPregunta().getNotaMitja());
//		}
//		
//		// Deduim el pès que tenen les preguntes ES1 i VOF sabent que la REC té un 
//		// pès de PRE_REC_PES:
//		Float pesNoRec = ((1f - PRE_REC_PES)/ 2f);
//		
//		// Calculam la nota del qüestionari i la comparam amb la nota obtinguda
//		Float notaQuestionari = pesNoRec * 10f + pesNoRec * 0f + PRE_REC_PES * PRE_REC_NOTA;
//		assertEquals(notaQuestionari, rq.getNota());
//		
//		// Finalment, el professor no té cap qüestionari pendent de corregir però
//		// en té un de corregit
//		assertNull(respostaQuestionariService.getQuestionarisPerCorregir(professor));
//		assertEquals(1, respostaQuestionariService.getQuestionarisCorregits(professor).size());
//	}

	/**
	 * mètode privat que crea un nou qüestionari amb les dues preguntes per defecte
	 * ES1 i VOF afegint-li una tercera pregunta REC.
	 * 
	 * En aquest cas, no s'indica pès per a les dues primeres preguntes. Només
	 * s'assigna el pès de la pregunta REC
	 * 
	 * @param pesRec
	 * @return questionariRec
	 */
	private Questionari afegirPreguntaRec(Float pesRec) {
		// Definim la pregunta REC
		preguntaRec = UtilTest.inserirPregunta(professor, PRE_REC_ENUNCIAT, PRE_REC_DIFICULTAT_TEO,
				PRE_REC_DIFICULTAT_PRA, PRE_REC_RAONAR_RESPOSTA, PRE_REC_DATA_ALTA, PRE_REC_TIPUS, PRE_REC_VOF_VERTADER,
				Pregunta.ESTAT_PUBLIC);

		// Inserim la pregunta a BD (amb una llista de preguntes buida)
		preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());

		// Definim el qüestionari
		Questionari q = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO,
				QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, QST_DATA_ALTA);

		// afegim les preguntes ES1 i VOF al qüestionari, així com el Map amb els pesos
		// de cada pregunta
		List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
		llistaPreguntes.add(preguntaEs1);
		llistaPreguntes.add(preguntaVof);
		llistaPreguntes.add(preguntaRec);
		Map<Integer, Float> pesos = new HashMap<Integer, Float>();
		pesos.put(preguntaEs1.getCodi(), null);
		pesos.put(preguntaVof.getCodi(), null);
		pesos.put(preguntaRec.getCodi(), pesRec);
		questionariService.insereixQuestionari(q, llistaPreguntes, pesos);

		return q;
	}

}
