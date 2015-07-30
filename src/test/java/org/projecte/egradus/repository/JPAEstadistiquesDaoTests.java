package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
import org.projecte.egradus.utilities.UtilTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class JPAEstadistiquesDaoTests {

	@Autowired
	private EstadistiquesDao estadistiquesDao;
	
	@Autowired
	private RespostaPreguntaDao respostaPreguntaDao;
	
	@Autowired
	private RespostaQuestionariDao respostaQuestionariDao;
	
	@Autowired
	private PreguntaDao preguntaDao;
	
	@Autowired
	private QuestionariDao questionariDao;
	
	@Autowired
	private PreguntaQuestionariDao pqDao;
	
	@Autowired
	private PersonaDao personaDao;
	
	@Autowired
	private AssignaturaDao assignaturaDao;
	
	@Autowired
	private ProfessorDao professorDao;
	
	@Autowired
	private AlumneDao alumneDao;
	
	// Dades de les Persones
	// ------------------------------------------------------------------------------------------------------------
	private static String PRO_NOM               = "Aina";
    private static String PRO_PRIMER_LLINATGE   = "González";
    private static String PRO_CORREU            = "ainagonzalez@correu.es";
    private static String PRO_ALIES             = "aina";
    private static String PRO_CLAU              = "ainagonzalez";
    private static String PRO_DATA_ALTA         = "20/09/2014";
    
    private static String ALU_NOM               = "Miquel";
    private static String ALU_PRIMER_LLINATGE   = "Garcia";
    private static String ALU_CORREU            = "m.garcia@correu.es";
    private static String ALU_ALIES             = "miquelet";
    private static String ALU_CLAU              = "claumiquel";
    private static String ALU_DATA_ALTA         = "20/11/2013";
    
    private static String ALU2_NOM              = "Juan";
    private static String ALU2_PRIMER_LLINATGE  = "Estarelles";
    private static String ALU2_CORREU           = "estarelles@yahoo.es";
    private static String ALU2_ALIES            = "juanest";
    private static String ALU2_CLAU             = "juanest";
    private static String ALU2_DATA_ALTA        = "01/03/2014";
    
    // Dades de l'Assignatura
    // ------------------------------------------------------------------------------------------------------------
    private static String ASS_NOM               = "Intel·ligència Artificial";
	private static String ASS_DESCRIPCIO        = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR    = "clau_inteli_artifi";
	private static String ASS_CLAU_ALUMNE       = "clau_intart";
	private static String ASS_DATA_ALTA         = "01/10/2013";
	private static String ASS_ANY_ACADEMIC      = "2013-14";
	private static String ASS_CODI_REF          = "INTELIART";
	
	// Dades del Qüestionari
	// ------------------------------------------------------------------------------------------------------------
	private static String  QST_NOM              = "Nom del qüestionari";
	private static String  QST_DESCRIPCIO       = "Descripció del qüestionari";
	private static Float   QST_DIFICULTAT_TEO   = 0.342f;
	private static Float   QST_DIFICULTAT_PRA   = null;
	private static Date    QST_DATA_ALTA        = new Date();
	private static Float   QST_PES_ES1          = 0.65f;
	private static Float   QST_PES_VOF          = 0.35f;
	
	// Dades de la Pregunta 1
	// ------------------------------------------------------------------------------------------------------------
	private static String  PRE1_ENUNCIAT        = "Aquest és l'enunciat de la pregunta d'escollir 1 opció entre 3";
	private static Float   PRE1_DIFICULTAT_TEO  = 0.5f;
	private static Float   PRE1_DIFICULTAT_PRA  = 0.99f;
	private static Boolean PRE1_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date    PRE1_DATA_ALTA       = new Date();
	private static String  PRE1_TIPUS           = Pregunta.TIPUS_ES1;
	private static Boolean PRE1_VOF_VERTADER    = null;
	// Dades de les Opcions de la Pregunta 1
	private static String  OPC1_PRE1_TEXT       = "Text de la opció 1";
	private static Boolean OPC1_PRE1_CORRECTA   = Boolean.FALSE;
	private static Date    OPC1_PRE1_DATA_ALTA  = UtilTest.getDate("11/10/2014");
	private static String  OPC2_PRE1_TEXT       = "Text de la opció 2";
	private static Boolean OPC2_PRE1_CORRECTA   = Boolean.TRUE;
	private static Date    OPC2_PRE1_DATA_ALTA  = UtilTest.getDate("11/10/2014");
	private static String  OPC3_PRE1_TEXT       = "Text de la opció 3";
	private static Boolean OPC3_PRE1_CORRECTA   = Boolean.FALSE;
	private static Date    OPC3_PRE1_DATA_ALTA  = UtilTest.getDate("11/10/2014");
	
	// Dades de la Pregunta 2
	// ------------------------------------------------------------------------------------------------------------
	private static String  PRE2_ENUNCIAT        = "Aquest és l'enunciat de la pregunta Vertader o Fals";
	private static Float   PRE2_DIFICULTAT_TEO  = 0.23f;
	private static Float   PRE2_DIFICULTAT_PRA  = null;
	private static Boolean PRE2_RAONAR_RESPOSTA = Boolean.TRUE;
	private static Date    PRE2_DATA_ALTA       = new Date();
	private static String  PRE2_TIPUS           = Pregunta.TIPUS_VOF;
	private static Boolean PRE2_VOF_VERTADER    = Boolean.TRUE;
	// Dades de les Opcions de la Pregunta 2
	private static String  OPC1_PRE2_TEXT       = "Vertader";
	private static Boolean OPC1_PRE2_CORRECTA   = Boolean.TRUE;
	private static Date    OPC1_PRE2_DATA_ALTA  = UtilTest.getDate("11/10/2014");
	private static String  OPC2_PRE2_TEXT       = "Fals";
	private static Boolean OPC2_PRE2_CORRECTA   = Boolean.FALSE;
	private static Date    OPC2_PRE2_DATA_ALTA  = UtilTest.getDate("11/10/2014");
	
	
	private Persona personaProfessor;
	private Persona personaAlumne1;
	private Persona personaAlumne2;
	private Assignatura assignatura;
	private Professor professor;
	private Alumne alumne1;
	private Alumne alumne2;
	private Pregunta preguntaEs1;
	private Pregunta preguntaVof;
	private Opcio opcio1Es1;
	private Opcio opcio2Es1;
	private Opcio opcio3Es1;
	private Opcio opcioVertaderVof;
	private Opcio opcioFalsVof;
	private Questionari questionari;
	
	@Before
	public void initialize() {
		// Inserim les persones
		personaProfessor = UtilTest.inserirPersona(PRO_NOM, PRO_PRIMER_LLINATGE, PRO_CORREU, PRO_ALIES, PRO_CLAU, UtilTest.getDate(PRO_DATA_ALTA));
		personaDao.persistPersona(personaProfessor);
		personaAlumne1 = UtilTest.inserirPersona(ALU_NOM, ALU_PRIMER_LLINATGE, ALU_CORREU, ALU_ALIES, ALU_CLAU, UtilTest.getDate(ALU_DATA_ALTA));
		personaDao.persistPersona(personaAlumne1);
		personaAlumne2 = UtilTest.inserirPersona(ALU2_NOM, ALU2_PRIMER_LLINATGE, ALU2_CORREU, ALU2_ALIES, ALU2_CLAU, UtilTest.getDate(ALU2_DATA_ALTA));
		personaDao.persistPersona(personaAlumne2);
		
		// Inserim l'assignatura (i l'associam a la persona 'personaProfessor')
		assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		
		// Inserim el rol de professor per a la persona 'personaProfessor' a l'assignatura 'assignatura'
		professor = UtilTest.getProfessor(personaProfessor, assignatura);
		professorDao.persistProfessor(professor);
		
		// Inserim els rols d'alumne per a les persones 'personaAlumne1' i 'personaAlumne2' a 
    	// l'assignatura 'assignatura'
		alumne1 = UtilTest.getAlumne(personaAlumne1, assignatura);
		alumneDao.persistAlumne(alumne1);
		alumne2 = UtilTest.getAlumne(personaAlumne2, assignatura);
		alumneDao.persistAlumne(alumne2);
		
		// Inserim la pregunta ES1
		preguntaEs1 = UtilTest.inserirPregunta(professor, PRE1_ENUNCIAT, PRE1_DIFICULTAT_TEO, PRE1_DIFICULTAT_PRA, PRE1_RAONAR_RESPOSTA, PRE1_DATA_ALTA, PRE1_TIPUS, PRE1_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaEs1);
		
		// Definim les tres opcions que té la pregunta ES1
		opcio1Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC1_PRE1_TEXT, OPC1_PRE1_CORRECTA, OPC1_PRE1_DATA_ALTA);
		opcio2Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC2_PRE1_TEXT, OPC2_PRE1_CORRECTA, OPC2_PRE1_DATA_ALTA);
		opcio3Es1 = UtilTest.inserirOpcio(preguntaEs1, OPC3_PRE1_TEXT, OPC3_PRE1_CORRECTA, OPC3_PRE1_DATA_ALTA);
		
		// inserim les opcions a BD
		preguntaDao.persistOpcio(opcio1Es1);
		preguntaDao.persistOpcio(opcio2Es1);
		preguntaDao.persistOpcio(opcio3Es1);
		
		// les tres opcions estan enllaçades amb la pregunta ES1, però a partir de la pregunta ES1 no podrem
		// obtenir les opcions si no enllaçam la pregunta a les opcions! Per tant:
		List<Opcio> opcions = new ArrayList<Opcio>();
		opcions.add(opcio1Es1);
		opcions.add(opcio2Es1);
		opcions.add(opcio3Es1);
		preguntaEs1.setOpcions(opcions);
		
		// Inserim la pregunta VOF
		preguntaVof = UtilTest.inserirPregunta(professor, PRE2_ENUNCIAT, PRE2_DIFICULTAT_TEO, PRE2_DIFICULTAT_PRA, PRE2_RAONAR_RESPOSTA, PRE2_DATA_ALTA, PRE2_TIPUS, PRE2_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaVof);
		
		// Definim les dues opcions que té la pregunta VOF
		opcioVertaderVof = UtilTest.inserirOpcio(preguntaVof, OPC1_PRE2_TEXT, OPC1_PRE2_CORRECTA, OPC1_PRE2_DATA_ALTA);
		opcioFalsVof     = UtilTest.inserirOpcio(preguntaVof, OPC2_PRE2_TEXT, OPC2_PRE2_CORRECTA, OPC2_PRE2_DATA_ALTA);
		
		// inserim les opcions a BD
		preguntaDao.persistOpcio(opcioVertaderVof);
		preguntaDao.persistOpcio(opcioFalsVof);
		
		// les dues opcions estan enllaçades amb la pregunta VOF, però a partir de la pregunta VOF no podrem
		// obtenir les opcions si no enllaçam la pregunta a les opcions! Per tant:
		opcions = new ArrayList<Opcio>();
		opcions.add(opcioVertaderVof);
		opcions.add(opcioFalsVof);
		preguntaVof.setOpcions(opcions);
		
		// Definim el qüestionari
		questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, QST_DATA_ALTA);
		questionariDao.persistQuestionari(questionari);
		
		// Definim una relació pregunta-questionari per cada pregunta que tendrà el qüestionari i les
		// inserim a BD
		PreguntaQuestionari pqEs1 = UtilTest.inserirPreguntaQuestionari(preguntaEs1, questionari, QST_PES_ES1);
		PreguntaQuestionari pqVof = UtilTest.inserirPreguntaQuestionari(preguntaVof, questionari, QST_PES_VOF);
		pqDao.persistPreguntaQuestionari(pqEs1);
		pqDao.persistPreguntaQuestionari(pqVof);
		
		// Afegim les dues relacions al qüestionari, per a tenir accés des del POJO questionari
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ.add(pqEs1);
		llistaPQ.add(pqVof);
		questionari.setPreguntes(llistaPQ);
		
		// EN AQUEST PUNT, TENIM UN ALUMNE, UN PROFESSOR I UN QUESTIONARI DE DUES PREGUNTES (UNA ES1 I UNA VOF)
	}
	
	@Test
	public void testGetRespostesPreguntaBuit() {
		List<Map<String, Object>> llistaRp = estadistiquesDao.getRespostesPregunta(alumne1);
		assertEquals(0, llistaRp.size());
	}
	
	/**
	 * En aquest test generam una resposta-pregunta contestada i corregida, però 
	 * NO representa el procés 'natural' de contestació i correcció de preguntes
	 */
	@Test
	public void testGetRespostesPregunta() {
		RespostaPregunta rp = new RespostaPregunta(preguntaEs1, professor, alumne1);
		
		// marcam la resposta-pregunta com a contestada
		rp.setContestada(Boolean.TRUE);
		rp.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
		rp.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setTextRaonarResposta("raonament de la resposta");
		rp.setTempsResposta(56000L);
		rp.getPregunta().incrementaNumTotalActualitzacionsTRM();
		rp.getPregunta().actualitzaTempsRespostaMig(rp.getTempsResposta());
		
		// es corregeix automàticament
		rp.setCorregida(Boolean.TRUE);
		rp.setCorrector(professor);
		rp.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setNota(5.5f);
		rp.setTextCorreccio("text de la correcció");
		rp.getPregunta().incrementaNumTotalActualitzacionsNotaMitja();
		rp.getPregunta().actualitzaNotaMitja(rp.getNota());
		
		// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
		// ja està suficientment preparada com per a ser trobada pel mètode:
		respostaPreguntaDao.persistRespostaPregunta(rp);
		
		// L'alumne 1 té una pregunta a les estadístiques
		List<Map<String, Object>> llistaRp = estadistiquesDao.getRespostesPregunta(alumne1);
		assertEquals(1, llistaRp.size());
		
		// L'alumne 2 no
		llistaRp = estadistiquesDao.getRespostesPregunta(alumne2);
		assertEquals(0, llistaRp.size());
	}
	
	@Test
	public void testGetRespostesQuestionariBuit() {
		List<Map<String, Object>> llistaRq = estadistiquesDao.getRespostesQuestionari(alumne1);
		assertEquals(0, llistaRq.size());
	}
	
	/**
	 * En aquest test generam una resposta-questionari contestada i corregida, 
	 * (així com totes les seves resposta-preguntes associades) però NO representa
	 * el procés 'natural' de contestació i correcció de qüestionaris
	 */
	@Test
	public void testGetRespostesQuestionari() {
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne1);
		
		for (PreguntaQuestionari pq : rq.getQuestionari().getPreguntes()) {
			RespostaPregunta rp = new RespostaPregunta(pq.getPregunta(), professor, alumne1);
			
			// marcam la resposta-pregunta com a contestada
			rp.setContestada(Boolean.TRUE);
			rp.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
			rp.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:36:56"));
			rp.setTextRaonarResposta("raonament de la resposta");
			
			// correcció de la pregunta
			rp.setCorregida(Boolean.TRUE);
			rp.setCorrector(professor);
			rp.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:06"));
			rp.setNota(5.5f);
			rp.setTextCorreccio("text de la correcció");
			
			// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
			// ja està suficientment preparada com per a ser trobada pel mètode:
			respostaPreguntaDao.persistRespostaPregunta(rp);
		}
		
		// marcam la resposta-qüestionari com a contestada
		rq.setContestat(Boolean.TRUE);
		rq.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
		rq.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:39:21"));
		rq.setTempsResposta(11000L);
		
		rq.getQuestionari().incrementaNumTotalActualitzacionsTRM();
		rq.getQuestionari().actualitzaTempsRespostaMig(rq.getTempsResposta());
		
		// es corregeix automàticament
		rq.setCorregit(Boolean.TRUE);
		rq.setCorrector(professor);
		rq.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rq.setNota(5.5f);
		
		respostaQuestionariDao.persistRespostaQuestionari(rq);
		
		// L'alumne 1 té un qüestionari a les estadístiques
		List<Map<String, Object>> llistaRq = estadistiquesDao.getRespostesQuestionari(alumne1);
		assertEquals(1, llistaRq.size());
		
		// L'alumne 2 no
		llistaRq = estadistiquesDao.getRespostesQuestionari(alumne2);
		assertEquals(0, llistaRq.size());
	}
	
	@Test
	public void testNumAlumnesCorregits() {
		RespostaPregunta rp = new RespostaPregunta(preguntaEs1, professor, alumne1);
		
		// marcam la resposta-pregunta com a contestada
		rp.setContestada(Boolean.TRUE);
		rp.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
		rp.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setTextRaonarResposta("raonament de la resposta");
		
		// Comprovam que la pregunta no té cap alumne corregit
		assertEquals(0, estadistiquesDao.getNumAlumnesCorregitsByPregunta(preguntaEs1));
		
		// es corregeix automàticament (indicam una nota suspesa)
		rp.setCorregida(Boolean.TRUE);
		rp.setCorrector(professor);
		rp.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setNota(4.9f);
		rp.setTextCorreccio("text de la correcció");
		
		// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
		// ja està suficientment preparada com per a ser trobada pel mètode:
		respostaPreguntaDao.persistRespostaPregunta(rp);
		
		// Comprovam que la pregunta, per molt que la nota estigui suspesa, té un alumne corregit
		assertEquals(1, estadistiquesDao.getNumAlumnesCorregitsByPregunta(preguntaEs1));
	}
	
	@Test
	public void testNumAlumnesAprovats() {
		RespostaPregunta rp = new RespostaPregunta(preguntaEs1, professor, alumne1);
		
		// marcam la resposta-pregunta com a contestada
		rp.setContestada(Boolean.TRUE);
		rp.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
		rp.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setTextRaonarResposta("raonament de la resposta");
		
		// Comprovam que la pregunta no té cap alumne aprovat
		assertEquals(0, estadistiquesDao.getNumAlumnesAprovatsByPregunta(preguntaEs1));
		
		// es corregeix automàticament
		rp.setCorregida(Boolean.TRUE);
		rp.setCorrector(professor);
		rp.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setNota(5.5f);
		rp.setTextCorreccio("text de la correcció");
		
		// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
		// ja està suficientment preparada com per a ser trobada pel mètode:
		respostaPreguntaDao.persistRespostaPregunta(rp);
		
		// Comprovam que la pregunta ja té un alumne aprovat
		assertEquals(1, estadistiquesDao.getNumAlumnesAprovatsByPregunta(preguntaEs1));
	}
	
	@Test
	public void testNumAlumnesAprovatsFals() {
		RespostaPregunta rp = new RespostaPregunta(preguntaEs1, professor, alumne1);
		
		// marcam la resposta-pregunta com a contestada
		rp.setContestada(Boolean.TRUE);
		rp.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
		rp.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setTextRaonarResposta("raonament de la resposta");
		
		// es corregeix automàticament (Nota suspesa!)
		rp.setCorregida(Boolean.TRUE);
		rp.setCorrector(professor);
		rp.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:06"));
		rp.setNota(4.9f);
		rp.setTextCorreccio("text de la correcció");
		
		// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
		// ja està suficientment preparada com per a ser trobada pel mètode:
		respostaPreguntaDao.persistRespostaPregunta(rp);
		
		// Comprovam que la pregunta segueix sense tenir cap alumne aprovat
		assertEquals(0, estadistiquesDao.getNumAlumnesAprovatsByPregunta(preguntaEs1));
	}
	
//	/**
//	 * Estadístiques que veu el professor sobre el conjunt d'alumnes
//	 * de la seva assignatura centrades en una pregunta
//	 */
//	@Test
//	public void testGetEstadistiquesProfessorPregunta() {
//		RespostaPregunta rp = new RespostaPregunta(preguntaEs1, professor, alumne1);
//		
//		// marcam la resposta-pregunta com a contestada
//		rp.setContestada(Boolean.TRUE);
//		rp.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
//		rp.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:37:06"));
//		rp.setTextRaonarResposta("raonament de la resposta");
//		rp.setTempsResposta(56000L);
//		rp.getPregunta().incrementaNumTotalActualitzacionsTRM();
//		rp.getPregunta().actualitzaTempsRespostaMig(rp.getTempsResposta());
//		
//		// es corregeix automàticament (Nota suspesa!)
//		rp.setCorregida(Boolean.TRUE);
//		rp.setCorrector(professor);
//		rp.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:06"));
//		rp.setNota(4.9f);
//		rp.setTextCorreccio("text de la correcció");
//		rp.getPregunta().incrementaNumTotalActualitzacionsNotaMitja();
//		rp.getPregunta().actualitzaNotaMitja(rp.getNota());
//		
//		// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
//		// ja està suficientment preparada com per a ser trobada pel mètode:
//		respostaPreguntaDao.persistRespostaPregunta(rp);
//		
//		List<Map<String, Object>> estadistiques = estadistiquesDao.getDetallEstadistiquesProfessorPreguntes(assignatura.getCodi(), preguntaEs1.getCodi(), 2, "alumneAnonim");
//		
//		for (Map<String, Object> mapEstadistiques : estadistiques) {
////			assertTrue(mapEstadistiques.containsKey(EstadistiquesDao.ALUMNE_NOM));
////			assertTrue(mapEstadistiques.containsKey(EstadistiquesDao.ALUMNE_PRIMER_LLINATGE));
////			assertTrue(mapEstadistiques.containsKey(EstadistiquesDao.ALUMNE_SEGON_LLINATGE));
//			assertTrue(mapEstadistiques.containsKey(EstadistiquesDao.ALUMNE_ALIES));
////			assertTrue(mapEstadistiques.containsKey(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA));
//			assertTrue(mapEstadistiques.containsKey(EstadistiquesDao.NUM_CONTESTACIONS));
//			
//			String aliesAlumne = (String) mapEstadistiques.get(EstadistiquesDao.ALUMNE_ALIES);
//			Float notaMitja = (Float) mapEstadistiques.get(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA);
//			Integer numContestacions = (Integer) mapEstadistiques.get(EstadistiquesDao.NUM_CONTESTACIONS);
//			
//			assertEquals(alumne1.getPersona().getAlies(), aliesAlumne);
//			assertEquals(new Float(4.9f), notaMitja);
//			assertEquals(new Integer(1), numContestacions);
//		}
//	}
	
	/**
	 * Estadístiques que veu el professor sobre el conjunt d'alumnes
	 * de la seva assignatura de totes les preguntes que hagin participat
	 * en aquella assignatura
	 * 
	 * TODO: En aquest test no soc capaç de fer els asserts pel temps de resposta!
	 * (Estic segur que és que el test no fica o no agafa les dates de contestació
	 * d'inici i de fi correctament, perquè la funcionalitat va bé fora de l'àmbit 
	 * del test.
	 * 
	 */
	@Test
	public void testGetEstadistiquesProfessorConjuntPreguntes() {
		//
		// pregunta ES1
		//
		RespostaPregunta rp = new RespostaPregunta(preguntaEs1, professor, alumne1);
		
		// marcam la resposta-pregunta com a contestada
		rp.setContestada(Boolean.TRUE);
		rp.setDataContestacioInici(UtilTest.getDateTime("06/03/2015 09:36:10"));
		rp.setDataContestacioFi(UtilTest.getDateTime("06/03/2015 09:37:10"));
		rp.setTextRaonarResposta("raonament de la resposta");
		rp.setTempsResposta(60000L);
		rp.getPregunta().incrementaNumTotalActualitzacionsTRM();
		rp.getPregunta().actualitzaTempsRespostaMig(rp.getTempsResposta());
		
		// es corregeix automàticament (Nota suspesa!)
		rp.setCorregida(Boolean.TRUE);
		rp.setCorrector(professor);
		rp.setDataCorreccio(UtilTest.getDateTime("06/03/2015 09:37:10"));
		rp.setNota(4.9f);
		rp.setTextCorreccio("text de la correcció");
		rp.getPregunta().incrementaNumTotalActualitzacionsNotaMitja();
		rp.getPregunta().actualitzaNotaMitja(rp.getNota());
		
		// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
		// ja està suficientment preparada com per a ser trobada pel mètode:
		respostaPreguntaDao.persistRespostaPregunta(rp);
		
		//
		// pregunta VOF
		//
		rp = new RespostaPregunta(preguntaVof, professor, alumne2);
		
		// marcam la resposta-pregunta com a contestada
		rp.setContestada(Boolean.TRUE);
		rp.setDataContestacioInici(UtilTest.getDateTime("08/03/2015 12:07:10"));
		rp.setDataContestacioFi(UtilTest.getDateTime("08/03/2015 12:09:10"));
		rp.setTextRaonarResposta("raonament de la resposta");
		rp.setTempsResposta(120000L);
		rp.getPregunta().incrementaNumTotalActualitzacionsTRM();
		rp.getPregunta().actualitzaTempsRespostaMig(rp.getTempsResposta());
		
		// es corregeix automàticament (Nota suspesa!)
		rp.setCorregida(Boolean.TRUE);
		rp.setCorrector(professor);
		rp.setDataCorreccio(UtilTest.getDateTime("08/03/2015 12:09:10"));
		rp.setNota(2f);
		rp.setTextCorreccio("text de la correcció");
		rp.getPregunta().incrementaNumTotalActualitzacionsNotaMitja();
		rp.getPregunta().actualitzaNotaMitja(rp.getNota());
		
		// falten les opcio-resposta que l'alumne hagi marcat. Així i tot, la resposta-pregunta
		// ja està suficientment preparada com per a ser trobada pel mètode:
		respostaPreguntaDao.persistRespostaPregunta(rp);
		
		List<Map<String, Object>> llistaEstadistiques = estadistiquesDao.getEstadistiquesProfessorPreguntesByAssignatura(assignatura.getCodi());
		
		assertEquals(2, llistaEstadistiques.size());
		for (Map<String, Object> estadistiques : llistaEstadistiques) {
			assertTrue(estadistiques.containsKey(EstadistiquesDao.PREGUNTA_CODI));
			assertTrue(estadistiques.containsKey(EstadistiquesDao.PREGUNTA_ENUNCIAT));
			assertTrue(estadistiques.containsKey(EstadistiquesDao.PREGUNTA_TIPUS));
			assertTrue(estadistiques.containsKey(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA));
			assertTrue(estadistiques.containsKey(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA));
			assertTrue(estadistiques.containsKey(EstadistiquesDao.NUM_CONTESTACIONS));
		}
		Pregunta pregunta = preguntaDao.getPreguntaByCodi(((Integer) llistaEstadistiques.get(0).get(EstadistiquesDao.PREGUNTA_CODI)).intValue());
		Float notaMitja = (Float) llistaEstadistiques.get(0).get(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA);
		Float tempsRespostaMig = (Float) llistaEstadistiques.get(0).get(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA);
		Float tempsRespostaMigEgradus = (Float) llistaEstadistiques.get(0).get(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS);
		Integer numContestacions = (Integer) llistaEstadistiques.get(0).get(EstadistiquesDao.NUM_CONTESTACIONS);
		
		assertEquals(preguntaEs1, pregunta);
		assertEquals(new Float(4.9f), notaMitja);
		assertTrue(tempsRespostaMig.equals(60f));
		assertTrue(tempsRespostaMigEgradus.equals(60f));
		assertEquals(new Integer(1), numContestacions);
		
		pregunta = preguntaDao.getPreguntaByCodi(((Integer) llistaEstadistiques.get(1).get(EstadistiquesDao.PREGUNTA_CODI)).intValue());
		notaMitja = (Float) llistaEstadistiques.get(1).get(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA);
		tempsRespostaMig = (Float) llistaEstadistiques.get(1).get(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA);
		tempsRespostaMigEgradus = (Float) llistaEstadistiques.get(1).get(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS);
		numContestacions = (Integer) llistaEstadistiques.get(1).get(EstadistiquesDao.NUM_CONTESTACIONS);
		
		assertEquals(preguntaVof, pregunta);
		assertEquals(new Float(2f), notaMitja);
		assertTrue(tempsRespostaMig.equals(120f));
		assertTrue(tempsRespostaMigEgradus.equals(120f));
		assertEquals(new Integer(1), numContestacions);
	}
	
}
