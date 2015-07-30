package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.utilities.UtilTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class QuestionariServiceImplTests {
	
	@Autowired
	private QuestionariService questionariService;
	
	@Autowired
	private RespostaQuestionariService respostaQuestionariService;
	
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
	
	
	// Dades de la Persona
	private static String  PER1_NOM             = "Miquel";
    private static String  PER1_PRIMER_LLINATGE = "Garcia";
    private static String  PER1_CORREU          = "m.garcia@correu.es";
    private static String  PER1_ALIES           = "miquelet";
    private static String  PER1_CLAU            = "claumiquel";
    private static String  PER1_DATA_ALTA       = "20/11/2013";
    
    private static String  PER2_NOM             = "Aina";
    private static String  PER2_PRIMER_LLINATGE = "Fernández";
    private static String  PER2_CORREU          = "aineta@correu.es";
    private static String  PER2_ALIES           = "aina";
    private static String  PER2_CLAU            = "fernandez";
    private static String  PER2_DATA_ALTA       = "20/01/2014";
    
    // Dades de l'Assignatura
    private static String  ASS_NOM             = "Intel·ligència Artificial";
	private static String  ASS_DESCRIPCIO      = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String  ASS_CLAU_PROFESSOR  = "clau_inteli_artifi";
	private static String  ASS_CLAU_ALUMNE     = "clau_intart";
	private static String  ASS_DATA_ALTA       = "01/10/2013";
	private static String  ASS_ANY_ACADEMIC    = "2013-14";
	private static String  ASS_CODI_REF        = "INTELIART";
	
	// Dades del Questionari 1
	private static String  QST1_NOM             = "Nom del qüestionari";
	private static String  QST1_DESCRIPCIO      = "Descripció del qüestionari";
	private static Float   QST1_DIFICULTAT_TEO  = 0.5f;
	private static Float   QST1_DIFICULTAT_PRA  = 0.99f;
	private static Date    QST1_DATA_ALTA_REF   = UtilTest.getDate("15/11/2014");
	private static String  QST1_ESTAT           = Questionari.ESTAT_EDITABLE;
	private static Float   QST1_PES_PREG_VOF    = 0.4f;
	private static Float   QST1_PES_PREG_REC    = 0.6f;
	
	// Dades del Questionari 2
	private static String  QST2_NOM             = "Nom del qüestionari";
	private static String  QST2_DESCRIPCIO      = "Descripció del qüestionari";
	private static Float   QST2_DIFICULTAT_TEO  = 0.5f;
	private static Float   QST2_DIFICULTAT_PRA  = 0.99f;
	private static Date    QST2_DATA_ALTA_REF   = UtilTest.getDate("15/11/2014");
	private static String  QST2_ESTAT           = Questionari.ESTAT_EDITABLE;
//	private static Float   QST2_PES_PREG_VOF    = 0.75f;
//	private static Float   QST2_PES_PREG_REC    = 0.25f;
	
	// Dades de la Pregunta 1
	private static String  PRE1_ENUNCIAT        = "Aquest és l'enunciat de la pregunta 1";
	private static Float   PRE1_DIFICULTAT_TEO  = 0.5f;
	private static Float   PRE1_DIFICULTAT_PRA  = 0.99f;
	private static Boolean PRE1_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date    PRE1_DATA_ALTA       = new Date();
	private static String  PRE1_TIPUS           = Pregunta.TIPUS_VOF;
	private static Boolean PRE1_VOF_VERTADER    = Boolean.FALSE;
	
	private static String  OPC_TEXT1           = "Vertader";
	private static Boolean OPC_CORRECTA1       = Boolean.FALSE;
	private static Date    OPC_DATA_ALTA1      = new Date();
	private static String  OPC_TEXT2           = "Fals";
	private static Boolean OPC_CORRECTA2       = Boolean.TRUE;
	private static Date    OPC_DATA_ALTA2      = new Date();
	
	// Dades de la Pregunta 2
	private static String  PRE2_ENUNCIAT        = "Aquest és l'enunciat de la pregunta 2";
	private static Float   PRE2_DIFICULTAT_TEO  = 0.8f;
	private static Float   PRE2_DIFICULTAT_PRA  = null;
	private static Boolean PRE2_RAONAR_RESPOSTA = Boolean.TRUE;
	private static Date    PRE2_DATA_ALTA       = new Date();
	private static String  PRE2_TIPUS           = Pregunta.TIPUS_REC;
	
	// Filtres per a cercar Questionaris
	private static String  QST_DATA_INICI_OK   = "01/07/2014";
	private static String  QST_DATA_INICI_NO   = "16/11/2014";
	private static String  QST_DATA_FI_OK      = "05/01/2015";
	private static String  QST_DATA_FI_NO      = "14/07/2014";
	
	private static String  QST_NOM_OK          = "üest";
	private static String  QST_NOM_NO          = "aaa";
	private static String  QST_DESCRIPCIO_OK   = "desc";
	private static String  QST_DESCRIPCIO_NO   = "aaa";
	
	private static String  QST_CRE_ALIES_OK    = "quelet";
	private static String  QST_CRE_NOM_OK      = "Miq";
	private static String  QST_CRE_LLINATGE_OK = "GarcI";
	private static String  QST_CREADOR_NO      = "mico";
	
	private static Float   QST_DIF_TEO1_OK     = 0.201f;
	private static Float   QST_DIF_TEO1_NO     = 0.774f;
	private static Float   QST_DIF_TEO2_OK     = QST1_DIFICULTAT_TEO;
	private static Float   QST_DIF_TEO2_NO     = 0f;
	
	private static Float   QST_DIF_PRA1_OK     = QST1_DIFICULTAT_PRA;
	private static Float   QST_DIF_PRA1_NO     = 1.0f;
	private static Float   QST_DIF_PRA2_OK     = 0.991f;
	private static Float   QST_DIF_PRA2_NO     = 0.9f;
	
	private static int     NUM_PREGUNTES        = 2;
	private static int     QUESTIONARI_COUNT    = 2;
	
	
	private Persona personaProfessor;
	private Persona personaProfessor2;
	private Persona personaAlumne;
	private Assignatura assignatura;
	private Professor professor;
	private Professor professor2;
	private Alumne alumne;
	private Questionari questionari1;
	private Questionari questionari2;
	private Pregunta preguntaVof;
	private Pregunta preguntaRec;
	private List<Opcio> opcionsVof;
	
	/**
	 * mètode privat que insereix un qüestionari
	 * amb una pregunta VOF i una REC, i un pès indicat
	 * explícitament per a la pregunta VOF, de manera que
	 * el pès de la pregunta REC es calcularà en proporció
	 */
	private void insereixQuestionari1() {
		// inserim les dues preguntes
		preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	
    	// les preparam per a que formin part dels 2 qüestionaris
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// cream una llista de pesos de les preguntes pel qüestionari 1
    	Map<Integer, Float> pesosQuestionari1 = new HashMap<Integer, Float>();
    	pesosQuestionari1.put(new Integer(preguntaVof.getCodi()), QST1_PES_PREG_VOF);
    	pesosQuestionari1.put(new Integer(preguntaRec.getCodi()), null);
    	
    	// inserim el qüestionari 1
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, pesosQuestionari1);
	}
	
	/**
	 * mètode privat que insereix un qüestionari
	 * amb una pregunta VOF i una REC sense indicar quin pès
	 * tendran dins ell (significant que cada pregunta valdrà
	 * lo mateix, és a dir, que cada pregunta tendrà el mateix pès)
	 */
	private void insereixQuestionari2() {
		// inserim les dues preguntes
		preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	
    	// les preparam per a que formin part dels 2 qüestionaris
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// inserim el qüestionari 2, sense llista de pesos
    	questionariService.insereixQuestionari(questionari2, llistaPreguntes, null);
	}
	
	@Before
	public void initialize() {
		personaProfessor = UtilTest.inserirPersona(PER1_NOM, PER1_PRIMER_LLINATGE, PER1_CORREU, PER1_ALIES, PER1_CLAU, UtilTest.getDate(PER1_DATA_ALTA));
    	personaService.insereixPersona(personaProfessor);
    	personaAlumne = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(personaAlumne);
    	assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
    	assignaturaService.insereixAssignatura(assignatura);
    	
    	// Quan inserim una assignatura, automàticament la persona s'insereix com a professor
    	// i per tant només l'hem d'agafar:
    	professor = professorService.getProfessor(personaProfessor, assignatura);
    	
    	alumne = UtilTest.getAlumne(personaAlumne, assignatura);
    	alumneService.insereixAlumne(alumne);
    	
    	// Cream un qüestionari (sense preguntes, només la informació bàsica)
    	questionari1 = UtilTest.inserirQuestionari(professor, QST1_NOM, QST1_DESCRIPCIO, QST1_DIFICULTAT_TEO, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
    	
    	// Cream un altre qüestionari (també sense preguntes)
    	questionari2 = UtilTest.inserirQuestionari(professor, QST2_NOM, QST2_DESCRIPCIO, QST2_DIFICULTAT_TEO, QST2_DIFICULTAT_PRA, QST2_ESTAT, QST2_DATA_ALTA_REF);
    	
    	// Cream una pregunta VOF
    	preguntaVof = UtilTest.inserirPregunta(professor, PRE1_ENUNCIAT, PRE1_DIFICULTAT_TEO, PRE1_DIFICULTAT_PRA, PRE1_RAONAR_RESPOSTA, PRE1_DATA_ALTA, PRE1_TIPUS, PRE1_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
    	opcionsVof = new ArrayList<Opcio>();
    	opcionsVof.add(UtilTest.inserirOpcio(preguntaVof, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1));
    	opcionsVof.add(UtilTest.inserirOpcio(preguntaVof, OPC_TEXT2, OPC_CORRECTA2, OPC_DATA_ALTA2));
    	
    	// Cream una pregunta REC
    	preguntaRec = UtilTest.inserirPregunta(professor, PRE2_ENUNCIAT, PRE2_DIFICULTAT_TEO, PRE2_DIFICULTAT_PRA, PRE2_RAONAR_RESPOSTA, PRE2_DATA_ALTA, PRE2_TIPUS, PRE1_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
	}
	
	@Test
	public void testGetQuestionarisBuit() {
    	List<Questionari> llistaQuestionaris = questionariService.getQuestionaris();
    	assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testInserirQuestionariSensePreguntes() {
    	questionariService.insereixQuestionari(questionari1, new ArrayList<Pregunta>(), new HashMap<Integer, Float>());
    	assertNotNull(questionari1.getCodi());
	}
	
	@Test
	public void testInserirQuestionariSensePreguntesLlistaPreguntesNula() {
    	questionariService.insereixQuestionari(questionari1, null, new HashMap<Integer, Float>());
    	assertNotNull(questionari1.getCodi());
	}
	
	@Test
	public void testInserirQuestionariSensePreguntesLlistaPesosNula() {
    	questionariService.insereixQuestionari(questionari1, new ArrayList<Pregunta>(), null);
    	assertNotNull(questionari1.getCodi());
	}
	
	@Test
	public void testInserirQuestionariAmbPreguntesSensePes() {
    	preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, null);
    	assertNotNull(questionari1.getCodi());
	}
	
	@Test
	public void testInserirQuestionariAmbPreguntesIPes() {
    	preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// llista de pesos de les preguntes
    	Map<Integer, Float> pesos = new HashMap<Integer, Float>();
    	pesos.put(new Integer(preguntaVof.getCodi()), QST1_PES_PREG_VOF);
    	pesos.put(new Integer(preguntaRec.getCodi()), QST1_PES_PREG_REC);
    	
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, pesos);
    	assertNotNull(questionari1.getCodi());
	}
	
	@Test
	public void testInserirQuestionariAmbPreguntesComptarPreguntes() {
		preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, null);
    	assertNotNull(questionari1.getCodi());
    	
    	// agafam el qüestionari que acabam d'inserir
    	Questionari q = questionariService.getQuestionari(questionari1.getCodi());
    	assertEquals(NUM_PREGUNTES, q.getPreguntes().size());
	}
	
	@Test
	public void testInserirQuestionariComprovacioPesosSensePasarliPesos() {
    	preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// inserim un qüestionari amb dues preguntes i sense indicar-li quins pesos tendrà
    	// cada pregunta dins el qüestionari
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, null);
    	
    	// recollim les preguntes i pesos d'aquest questionari
    	Pregunta p1 = questionari1.getPreguntes().get(0).getPregunta();
    	Float pes1 = questionari1.getPreguntes().get(0).getPes();
    	Pregunta p2 = questionari1.getPreguntes().get(1).getPregunta();
    	Float pes2 = questionari1.getPreguntes().get(1).getPes();
    	
    	// realitzam les comprovacions
    	assertEquals(preguntaVof.getCodi(), p1.getCodi());
    	assertEquals(preguntaRec.getCodi(), p2.getCodi());
    	assertEquals(new Float(1f/NUM_PREGUNTES), pes1);
    	assertEquals(new Float(1f/NUM_PREGUNTES), pes2);
	}
	
	@Test
	public void testInserirQuestionariComprovacioPesosAmbLlistaPesosBuida() {
    	preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// inserim un qüestionari amb dues preguntes i passant-li una llista de pesos buida
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, new HashMap<Integer, Float>());
    	
    	// recollim les preguntes i pesos d'aquest questionari
    	Pregunta p1 = questionari1.getPreguntes().get(0).getPregunta();
    	Float pes1 = questionari1.getPreguntes().get(0).getPes();
    	Pregunta p2 = questionari1.getPreguntes().get(1).getPregunta();
    	Float pes2 = questionari1.getPreguntes().get(1).getPes();
    	
    	// realitzam les comprovacions
    	assertEquals(preguntaVof.getCodi(), p1.getCodi());
    	assertEquals(preguntaRec.getCodi(), p2.getCodi());
    	assertEquals(new Float(1f/NUM_PREGUNTES), pes1);
    	assertEquals(new Float(1f/NUM_PREGUNTES), pes2);
	}
	
	@Test
	public void testInserirQuestionariComprovacioPesosLlistaPesosCompleta() {
    	preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// llista de pesos de les preguntes
    	Map<Integer, Float> pesos = new HashMap<Integer, Float>();
    	pesos.put(new Integer(preguntaVof.getCodi()), QST1_PES_PREG_VOF);
    	pesos.put(new Integer(preguntaRec.getCodi()), QST1_PES_PREG_REC);
    	
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, pesos);
    	
    	// recollim les preguntes i pesos d'aquest questionari
    	Pregunta p1 = questionari1.getPreguntes().get(0).getPregunta();
    	Float pes1 = questionari1.getPreguntes().get(0).getPes();
    	Pregunta p2 = questionari1.getPreguntes().get(1).getPregunta();
    	Float pes2 = questionari1.getPreguntes().get(1).getPes();
    	
    	// realitzam les comprovacions
    	assertEquals(preguntaVof.getCodi(), p1.getCodi());
    	assertEquals(preguntaRec.getCodi(), p2.getCodi());
    	assertEquals(QST1_PES_PREG_VOF, pes1);
    	assertEquals(QST1_PES_PREG_REC, pes2);
	}
	
	@Test
	public void testInserirQuestionariComprovacioPesosLlistaPesosIncompleta() {
    	preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// llista de pesos de les preguntes (només n'indicam 1)
    	Map<Integer, Float> pesos = new HashMap<Integer, Float>();
    	pesos.put(new Integer(preguntaVof.getCodi()), QST1_PES_PREG_VOF);
    	pesos.put(new Integer(preguntaRec.getCodi()), null);
    	
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, pesos);
    	
    	// recollim les preguntes i pesos d'aquest questionari
    	Pregunta p1 = questionari1.getPreguntes().get(0).getPregunta();
    	Float pes1 = questionari1.getPreguntes().get(0).getPes();
    	Pregunta p2 = questionari1.getPreguntes().get(1).getPregunta();
    	Float pes2 = questionari1.getPreguntes().get(1).getPes();
    	
    	// calculam el pès que hauria d'haver-se calculat per la pregunta REC
    	// en el qüestionari
    	Float pesRec = new Float(1f - QST1_PES_PREG_VOF);
    	
    	// realitzam les comprovacions
    	assertEquals(preguntaVof.getCodi(), p1.getCodi());
    	assertEquals(preguntaRec.getCodi(), p2.getCodi());
    	assertEquals(QST1_PES_PREG_VOF, pes1);
    	assertEquals(pesRec, pes2);
	}
	
	@Test
	public void testInserirQuestionariSensePreguntesNullAmbComprovacio() {
		// inserim un qüestionari passant-li una llista de preguntes Nul·la
		// i comprovam que, efectivament, no té llista de preguntes
		// NOTA: (no comprovam si s'ha creat el qüestionari, sino que no té preguntes)
    	questionariService.insereixQuestionari(questionari1, null, new HashMap<Integer, Float>());
    	assertNull(questionari1.getPreguntes());
	}
	
	@Test
	public void testInserirQuestionariSensePreguntesBuidaAmbComprovacio() {
		// inserim un qüestionari passant-li una llista de preguntes Nul·la
		// i comprovam que, efectivament, no té llista de preguntes
		// NOTA: (no comprovam si s'ha creat el qüestionari, sino que no té preguntes)
    	questionariService.insereixQuestionari(questionari1, new ArrayList<Pregunta>(), null);
    	assertNull(questionari1.getPreguntes());
	}
	
	@Test
	public void testGetQuestionaris() {
		insereixQuestionari1();
		insereixQuestionari2();
		
    	List<Questionari> llistaQuestionaris = questionariService.getQuestionaris();
    	assertEquals(QUESTIONARI_COUNT, llistaQuestionaris.size());
	}
	
	/**
	 * agafa tots els qüestionaris associats a resposta-questionaris que 
	 * hagin sigut enviades per algún professor de l'assignatura
	 */
	@Test
	public void testGetQuestionarisByAssignatura() {
		insereixQuestionari1();
		
    	// En aquest punt, la cerca de qüestionaris by assignatura no ens hauria de retornar cap qüestionari
    	assertNull(questionariService.getQuestionaris(assignatura));
    	
		// enviam el qüestionari a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		respostaQuestionariService.enviaQuestionari(questionari1.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// En aquest punt, la cerca de qüestionaris by assignatura ens hauria de retornar 1 qüestionari
		assertEquals(1, questionariService.getQuestionaris(assignatura).size());
		
		// Afegim un altre professor a l'assignatura: professor2
		personaProfessor2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaService.insereixPersona(personaProfessor2);
		professor2 = UtilTest.getProfessor(personaProfessor2, assignatura);
		professorService.insereixProfessor(professor2);
		
		// Aquest segon professor envia la pregunta
		respostaQuestionariService.enviaQuestionari(questionari1.getCodi(), professor2, alumnes, Boolean.FALSE);
		
		// En aquest punt, la cerca de qüestionaris by assignatura ens hauria de retornar, també, 1 qüestionari
		// (ja que ambdós professors han enviat el mateix qüestionari, i a l'assignatura, per tant, només
		// hi ha participat un qüestionari).
		assertEquals(1, questionariService.getQuestionaris(assignatura).size());
		
		// Si un dels professors envia el qüestionari 2, la cerca de qüestionaris by assignatura
		// ens hauria de retornar 2 qüestionaris:
		insereixQuestionari2();
		respostaQuestionariService.enviaQuestionari(questionari2.getCodi(), professor, alumnes, Boolean.FALSE);
		assertEquals(2, questionariService.getQuestionaris(assignatura).size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDataIniciOk() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(UtilTest.getDate(QST_DATA_INICI_OK), null, null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDataIniciNo() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(UtilTest.getDate(QST_DATA_INICI_NO), null, null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreDataFiOk() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, UtilTest.getDate(QST_DATA_FI_OK), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDataFiNo() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, UtilTest.getDate(QST_DATA_FI_NO), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreBetweenOk() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(UtilTest.getDate(QST_DATA_INICI_OK), UtilTest.getDate(QST_DATA_FI_OK), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreBetweenDadesGirades() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(UtilTest.getDate(QST_DATA_INICI_NO), UtilTest.getDate(QST_DATA_FI_NO), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreNomOk() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, QST_NOM_OK, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreNomNo() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, QST_NOM_NO, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreDescripcioOk() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, QST_DESCRIPCIO_OK, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDescripcioNo() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, QST_DESCRIPCIO_NO, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorNo() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, QST_CREADOR_NO, null, null, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorAlies() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, QST_CRE_ALIES_OK, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorNom() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, QST_CRE_NOM_OK, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorLlinatge() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, QST_CRE_LLINATGE_OK, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo1Ok() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_OK, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo1No() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_NO, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo2Ok() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, QST_DIF_TEO2_OK, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo2No() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, QST_DIF_TEO2_NO, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeoBetweenOk() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_OK, QST_DIF_TEO2_OK, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeoBetweenNo() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_NO, QST_DIF_TEO2_NO, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra1Ok() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_OK, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra1No() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_NO, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra2Ok() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, null, null, QST_DIF_PRA2_OK, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra2No() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, null, null, QST_DIF_PRA2_NO, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPraBetweenOk() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_OK, QST_DIF_PRA2_OK, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPraBetweenNo() {
		insereixQuestionari1();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_NO, QST_DIF_PRA2_NO, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreEstatPublic() {
		insereixQuestionari1();
		insereixQuestionari2();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, Questionari.ESTAT_PUBLIC, null, null, null, null, null, professor.getCodi().intValue());
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testGetQuestionarisFiltreEstatEditable() {
		insereixQuestionari1();
		insereixQuestionari2();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, Questionari.ESTAT_EDITABLE, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(2, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreEstatSenseIndicar() {
		insereixQuestionari1();
		insereixQuestionari2();
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris(null, null, null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(2, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetPregunta() {
		insereixQuestionari1();
		Questionari q = questionariService.getQuestionari(questionari1.getCodi());
		assertEquals(QST1_NOM, q.getNom());
	}
	
	@Test
	public void testPublicaQuestionari() {
		insereixQuestionari1();
		
		assertEquals(QST1_ESTAT, questionari1.getEstat());
		
		// modificam l'estat al qüestionari
		Questionari q = questionariService.publicaQuestionari(questionari1.getCodi());
		
		// comprovam que s'ha modificat correctament, però que segueix sent el mateix qüestionari
		assertEquals(questionari1.getCodi(), q.getCodi());
		assertEquals(Questionari.ESTAT_PUBLIC, q.getEstat());
	}
	
	@Test
	public void testEliminaQuestionari() {
		insereixQuestionari1();
		
		// Consultam quantes preguntes tenim inserides a BD
		List<Questionari> llistaQuestionaris = questionariService.getQuestionaris();
		assertEquals(1, llistaQuestionaris.size());
		
		// Eliminam el qüestionari, i tornam a consultar
		questionariService.eliminaQuestionari(questionari1.getCodi().intValue());
		llistaQuestionaris = questionariService.getQuestionaris();
		assertNull(llistaQuestionaris);
	}
	
	@Test
	public void testModificaQuestionariNom() {
		insereixQuestionari1();
		
		// Definim un enunciat distint
		String nomDistint = "nom distint";
		
		// Comprovam que, abans de realitzar la modificació, el nom no coincideix
		assertNotEquals(nomDistint, questionari1.getNom());
		
		// Definim el nou qüestionari
		Questionari questionari = UtilTest.inserirQuestionari(professor, nomDistint, QST1_DESCRIPCIO, QST1_DIFICULTAT_TEO, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
		
		// modificam el qüestionari original, amb les dades del nou qüestionari
		questionari1 = questionariService.modificaQuestionari(questionari1.getCodi(), questionari, new ArrayList<Pregunta>(), new HashMap<Integer, Float>());
		
		// comprovam que, efectivament, el nom s'ha modificat correctament
		assertEquals(nomDistint, questionari1.getNom());
	}
	
	@Test
	public void testModificaQuestionariDescripcio() {
		insereixQuestionari1();
		
		// Definim un enunciat distint
		String descripcioDistinta = "descripció distinta";
		
		// Comprovam que, abans de realitzar la modificació, la descripció no coincideix
		assertNotEquals(descripcioDistinta, questionari1.getDescripcio());
		
		// Definim el nou qüestionari
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST1_NOM, descripcioDistinta, QST1_DIFICULTAT_TEO, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
		
		// modificam el qüestionari original, amb les dades del nou qüestionari
		questionari1 = questionariService.modificaQuestionari(questionari1.getCodi(), questionari, new ArrayList<Pregunta>(), new HashMap<Integer, Float>());
		
		// comprovam que, efectivament, la descripció s'ha modificat correctament
		assertEquals(descripcioDistinta, questionari1.getDescripcio());
	}
	
	@Test
	public void testModificaQuestionariDificultatTeorica() {
		insereixQuestionari1();
		
		// Definim una dificultat teòrica distinta
		Float dificultatTeoricaDistinta = QST1_DIFICULTAT_TEO / 2f;
		
		// Comprovam que, abans de realitzar la modificació, la dificultat teòrica no coincideix
		assertNotEquals(dificultatTeoricaDistinta, questionari1.getDificultatTeorica());
		
		// Definim el nou qüestionari
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST1_NOM, QST1_DESCRIPCIO, dificultatTeoricaDistinta, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
		
		// modificam el qüestionari original, amb les dades del nou qüestionari
		questionari1 = questionariService.modificaQuestionari(questionari1.getCodi(), questionari, new ArrayList<Pregunta>(), new HashMap<Integer, Float>());
		
		// comprovam que, efectivament, el nom s'ha modificat correctament
		assertEquals(dificultatTeoricaDistinta, questionari1.getDificultatTeorica());
	}
	
	@Test
	public void testModificaQuestionariPreguntesAbansSiDespresNo() {
		insereixQuestionari1();
		
		// Comprovam que el questionari1 té preguntes
		assertNotEquals(0, questionari1.getPreguntes().size());
		
		// Definim el nou qüestionari (sense preguntes)
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST1_NOM, QST1_DESCRIPCIO, QST1_DIFICULTAT_TEO, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
		
		// modificam el qüestionari original, amb les dades del nou qüestionari
		// ens asseguram de que no li passam preguntes
		questionari1 = questionariService.modificaQuestionari(questionari1.getCodi(), questionari, null, null);
		
		// comprovam que, efectivament, el qüestionari ja no té preguntes
		assertEquals(0, questionari1.getPreguntes().size());
	}
	
	@Test
	public void testModificaQuestionariPreguntesAbansNoDespresSi() {
		// Inserim el qüestionari sense preguntes
		questionariService.insereixQuestionari(questionari1, null, null);
		
		// Comprovam que el questionari1 no té preguntes
		assertNull(questionari1.getPreguntes());
		
		// Definim el nou qüestionari (amb preguntes)
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST1_NOM, QST1_DESCRIPCIO, QST1_DIFICULTAT_TEO, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
		
		// modificam el qüestionari original, amb les dades del nou qüestionari
		// ens asseguram de que li passam preguntes (no lis passam pesos)
		preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	questionariService.insereixQuestionari(questionari, llistaPreguntes, null);
    	
		questionari1 = questionariService.modificaQuestionari(questionari1.getCodi(), questionari, llistaPreguntes, null);
		
		// comprovam que, efectivament, el qüestionari ara té preguntes
		assertEquals(llistaPreguntes.size(), questionari1.getPreguntes().size());
	}
	
	@Test
	public void testModificaQuestionariPreguntesDiferents() {
		// inserim la pregunta que tendrà el qüestionari 1
		preguntaService.insereixPregunta(preguntaVof, opcionsVof);
    	
    	// la preparam per a que formi part del qüestionari
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	
    	// inserim el qüestionari 1
    	questionariService.insereixQuestionari(questionari1, llistaPreguntes, null);
    	
    	// Comprovam que la pregunta que té el qüestionari és de tipus VOF
    	assertEquals(1, questionari1.getPreguntes().size());
    	assertEquals(PRE1_TIPUS, questionari1.getPreguntes().get(0).getPregunta().getTipus());
    	
    	// Inserim una nova pregunta
    	preguntaService.insereixPregunta(preguntaRec, new ArrayList<Opcio>());
    	
    	// la preparam per a que formi part d'un nou qüestionari
    	llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaRec);
    	
    	// Definim el nou qüestionari
    	Questionari questionari = UtilTest.inserirQuestionari(professor, QST1_NOM, QST1_DESCRIPCIO, QST1_DIFICULTAT_TEO, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
    	
    	// Afegim la nova pregunta
    	questionariService.insereixQuestionari(questionari, llistaPreguntes, null);
    	
    	// Modificam el nou qüestionari 
		questionari1 = questionariService.modificaQuestionari(questionari1.getCodi(), questionari, llistaPreguntes, null);
    	
		// Comprovam que la pregunta que té el qüestionari ja no és de tipus VOF, sino de tipus REC
		assertEquals(1, questionari1.getPreguntes().size());
    	assertEquals(PRE2_TIPUS, questionari1.getPreguntes().get(0).getPregunta().getTipus());
	}
	
	@Test
	public void testModificaQuestionariPreguntesAmbDiferentsPesos() {
		insereixQuestionari1();
		// NOTA: el mètode privat 'insereixQuestionari1()' no assigna explícitament pès per la segona
		// pregunta, però aquest és calculat dins el mètode de servei insereixQuestionari()
    	
    	// Comprovam els pesos de les dues preguntes
    	assertEquals(2, questionari1.getPreguntes().size());
    	assertEquals(QST1_PES_PREG_VOF, questionari1.getPreguntes().get(0).getPes());
    	assertEquals(QST1_PES_PREG_REC, questionari1.getPreguntes().get(1).getPes()); // podem comprovar que s'ha calculat bé, tot i no haver-lo indicat explícitament
    	
    	// Definim un nou qüestionari
    	Questionari questionari = UtilTest.inserirQuestionari(professor, QST1_NOM, QST1_DESCRIPCIO, QST1_DIFICULTAT_TEO, QST1_DIFICULTAT_PRA, QST1_ESTAT, QST1_DATA_ALTA_REF);
    	
    	// les preguntes preguntaVof i preguntaRec ja estan inserides a BD pel mètode privat
    	// insereixQuestionari1(), però les emmagatzemam a llistaPreguntes per a afegir-les al
    	// nou qüestionari
    	List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
    	llistaPreguntes.add(preguntaVof);
    	llistaPreguntes.add(preguntaRec);
    	
    	// cream una llista nova de pesos de les preguntes pel nou qüestionari
    	Map<Integer, Float> pesos = new HashMap<Integer, Float>();
    	pesos.put(new Integer(preguntaVof.getCodi()), null);
    	pesos.put(new Integer(preguntaRec.getCodi()), QST1_PES_PREG_REC / 2f);
    	
    	// inserim el nou qüestionari (amb les mateixes preguntes que el qüestionari1 però amb un llistat de pesos distint)
    	questionariService.insereixQuestionari(questionari, llistaPreguntes, pesos);
    	
    	// Modificam el nou qüestionari 
    	questionari1 = questionariService.modificaQuestionari(questionari1.getCodi(), questionari, llistaPreguntes, pesos);
    	
    	// Comprovam que hi segueixen havent dues preguntes, però els pesos ja no són els mateixos
    	assertEquals(2, questionari1.getPreguntes().size());
    	assertEquals(new Float(1f - QST1_PES_PREG_REC / 2f), questionari1.getPreguntes().get(0).getPes());
    	assertEquals(new Float(QST1_PES_PREG_REC / 2f),      questionari1.getPreguntes().get(1).getPes());
	}
	
}
