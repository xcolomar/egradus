package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
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
public class PreguntaServiceImplTests {
	
	@Autowired
	private PreguntaService preguntaService;
	
	@Autowired
	private RespostaPreguntaService respostaPreguntaService;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private AssignaturaService assignaturaService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private AlumneService alumneService;
	
	
	private static int PREGUNTA_COUNT = 2;
	
	// Dades de Persona
	private static String PER1_NOM               = "Joan";
    private static String PER1_PRIMER_LLINATGE   = "Savater";
    private static String PER1_CORREU            = "j.savat85@correu.es";
    private static String PER1_ALIES             = "johnny";
    private static String PER1_CLAU              = "jonyclau";
    private static String PER1_DATA_ALTA         = "18/02/2014";
    
    private static String PER2_NOM               = "Maria";
    private static String PER2_PRIMER_LLINATGE   = "Bennassar";
    private static String PER2_CORREU            = "bennassarmaria@correu.es";
    private static String PER2_ALIES             = "maria";
    private static String PER2_CLAU              = "maria";
    private static String PER2_DATA_ALTA         = "15/09/2013";
    
    // Dades d'Assignatura
    private static String ASS_NOM               = "Càlcul diferencial II";
	private static String ASS_DESCRIPCIO        = null;
	private static String ASS_CLAU_PROFESSOR    = "clau_calcul_dif2";
	private static String ASS_CLAU_ALUMNE       = "clau_caldif2";
	private static String ASS_DATA_ALTA         = "14/01/2014";
	private static String ASS_ANY_ACADEMIC      = "2014-15";
	private static String ASS_CODI_REF          = "CALCDIF";
	
	// Dades de les Preguntes
	private static String  PRE_ENUNCIAT1        = "Aquest és l'enunciat de la pregunta";
	private static Float   PRE_DIFICULTAT_TEO1  = 0.5f;
	private static Float   PRE_DIFICULTAT_PRA1  = 0.99f;
	private static Boolean PRE_RAONAR_RESPOSTA1 = Boolean.TRUE;
	private static Date    PRE_DATA_ALTA1       = new Date();
	private static String  PRE_TIPUS1           = Pregunta.TIPUS_ES1;
	private static Boolean PRE_VOF_VERTADER1    = null;
	private static String  PRE_ESTAT1           = Pregunta.ESTAT_EDITABLE;
	
	private static String  PRE_ENUNCIAT2        = "Aquest és l'enunciat de la pregunta 2";
	private static Float   PRE_DIFICULTAT_TEO2  = null;
	private static Float   PRE_DIFICULTAT_PRA2  = 0.9f;
	private static Boolean PRE_RAONAR_RESPOSTA2 = Boolean.FALSE;
	private static Date    PRE_DATA_ALTA2       = new Date();
	private static String  PRE_TIPUS2           = Pregunta.TIPUS_VOF;
	private static Boolean PRE_VOF_VERTADER2    = Boolean.TRUE;
	private static String  PRE_ESTAT2           = Pregunta.ESTAT_PUBLIC;
	// Data per a comparar amb els filtres temporals d'abaix
	private static String  PRE_DATA_ALTA_REF   = "15/07/2014";
	
	// Dades de les Opcions
	private static int     NUM_OPCIONS         = 2;
	
	private static String  OPC_TEXT1           = "Opció 1";
	private static Boolean OPC_CORRECTA1       = Boolean.TRUE;
	private static Date    OPC_DATA_ALTA1      = new Date();
	
	private static String  OPC_TEXT2           = "Opció 2";
	private static Boolean OPC_CORRECTA2       = Boolean.FALSE;
	private static Date    OPC_DATA_ALTA2      = new Date();
	
	// Filtres per a cercar Preguntes
	private static String  PRE_DATA_INICI_OK   = "01/07/2014";
	private static String  PRE_DATA_INICI_NO   = "16/07/2014";
	private static String  PRE_DATA_FI_OK      = "31/07/2014";
	private static String  PRE_DATA_FI_NO      = "14/07/2014";
	
	private static String  PRE_ENUNCIAT_OK     = "enunciat de la pre";
	private static String  PRE_ENUNCIAT_NO     = "aquesta";
	
	private static String  PRE_CRE_ALIES_OK    = "OHnN";
	private static String  PRE_CRE_NOM_OK      = "joan";
	private static String  PRE_CRE_LLINATGE_OK = "savA";
	private static String  PRE_CREADOR_NO      = "johny";
	
	private static Boolean PRE_RAONAR_RES_OK   = Boolean.TRUE;
	private static Boolean PRE_RAONAR_RES_NO   = Boolean.FALSE;
	private static Boolean PRE_RAONAR_RES_NULL = null;
	
	private static Float   PRE1_DIF_TEO1_OK     = 0.201f;
	private static Float   PRE1_DIF_TEO1_NO     = 0.774f;
	private static Float   PRE1_DIF_TEO2_OK     = PRE_DIFICULTAT_TEO1;
	private static Float   PRE1_DIF_TEO2_NO     = 0f;
	
	private static Float   PRE1_DIF_PRA1_OK     = PRE_DIFICULTAT_PRA1;
	private static Float   PRE1_DIF_PRA1_NO     = 1.0f;
	private static Float   PRE1_DIF_PRA2_OK     = 0.991f;
	private static Float   PRE1_DIF_PRA2_NO     = 0.9f;
	
	private static String  PRE_TIPUS_OK         = PRE_TIPUS1;
	private static String  PRE_TIPUS_NO         = Pregunta.TIPUS_REC;
	
//	private static String  PRE1_ESTAT_OK        = PRE_ESTAT1;
//	private static String  PRE2_ESTAT_OK        = PRE_ESTAT2;
	
	
	private Persona personaProfessor;
	private Persona personaProfessor2;
	private Persona personaAlumne;
	private Assignatura assignatura;
	private Professor professor;
	private Professor professor2;
	private Alumne alumne;
	
	@Before
	public void initialize() {
		personaProfessor = UtilTest.inserirPersona(PER1_NOM, PER1_PRIMER_LLINATGE, PER1_CORREU, PER1_ALIES, PER1_CLAU, UtilTest.getDate(PER1_DATA_ALTA));
    	personaService.insereixPersona(personaProfessor);
    	personaAlumne = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(personaAlumne);
    	assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
    	assignaturaService.insereixAssignatura(assignatura);
    	alumne = UtilTest.getAlumne(personaAlumne, assignatura);
    	alumneService.insereixAlumne(alumne);
    	
    	// Quan inserim una assignatura, automàticament la persona s'insereix com a professor
    	// i per tant només l'hem d'agafar:
    	professor = professorService.getProfessor(personaProfessor, assignatura);
	}
	
	@Test
	public void testGetPreguntesBuit() {
    	List<Pregunta> llistaPreguntes = preguntaService.getPreguntes();
    	assertNull(llistaPreguntes);
	}
	
	@Test
	public void testInserirPreguntaSenseOpcions() {
    	Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
    	preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
    	assertNotNull(pregunta.getCodi());
	}
	
	@Test
	public void testInserirPreguntaAmbOpcions() {
    	Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
    	Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, OPC_CORRECTA2, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	preguntaService.insereixPregunta(pregunta, opcions);
    	assertNotNull(pregunta.getCodi());
	}
	
	@Test
	public void testInserirPreguntaAmbOpcionsComptarOpcions() {
    	Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
    	Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, OPC_CORRECTA2, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
    	preguntaService.insereixPregunta(pregunta, opcions);
    	
    	// agafam la pregunta que acabam d'inserir
    	pregunta = preguntaService.getPregunta(pregunta.getCodi());
    	assertEquals(NUM_OPCIONS, pregunta.getOpcions().size());
	}
	
	@Test
	public void testGetLlistaPreguntesEstatBuit() {
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(0);
		assertNull(llistaPreguntes);
	}
	@Test
	public void testGetLlistaPreguntesEstatOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntes() {
    	Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
    	preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
    	pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT2, PRE_DIFICULTAT_TEO2, PRE_DIFICULTAT_PRA2, PRE_RAONAR_RESPOSTA2, PRE_DATA_ALTA2, PRE_TIPUS2, PRE_VOF_VERTADER2, PRE_ESTAT2);
    	preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
    	List<Pregunta> llistaPreguntes = preguntaService.getPreguntes();
    	assertEquals(PREGUNTA_COUNT, llistaPreguntes.size());
	}
	
	/**
	 * agafa totes les preguntes associades a resposta-preguntes que 
	 * hagin sigut enviades per algún professor de l'assignatura
	 */
	@Test
	public void testGetPreguntesByAssignatura() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		List<Opcio> opcions = new ArrayList<Opcio>();
		opcions.add(UtilTest.inserirOpcio(pregunta, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1));
		opcions.add(UtilTest.inserirOpcio(pregunta, OPC_TEXT2, OPC_CORRECTA2, OPC_DATA_ALTA2));
		pregunta.setOpcions(opcions);
    	preguntaService.insereixPregunta(pregunta, opcions);
		
    	// En aquest punt, la cerca de preguntes by assignatura no ens hauria de retornar cap pregunta
    	assertNull(preguntaService.getPreguntes(assignatura));
    	
		// enviam la pregunta a l'alumne (És de tipus VOF)
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// En aquest punt, la cerca de preguntes by assignatura ens hauria de retornar 1 pregunta
		assertEquals(1, preguntaService.getPreguntes(assignatura).size());
		
		// Afegim un altre professor a l'assignatura: professor2
		personaProfessor2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaService.insereixPersona(personaProfessor2);
		professor2 = UtilTest.getProfessor(personaProfessor2, assignatura);
		professorService.insereixProfessor(professor2);
		
		// Aquest segon professor envia la pregunta
		respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor2, alumnes, Boolean.FALSE);
		
		// En aquest punt, la cerca de preguntes by assignatura ens hauria de retornar, també, 1 pregunta
		// (ja que ambdós professors han enviat la mateixa pregunta, i a l'assignatura, per tant, només
		// hi ha participat una pregunta).
		assertEquals(1, preguntaService.getPreguntes(assignatura).size());
	}
	
	@Test
	public void testGetPreguntesFiltreDataIniciOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(UtilTest.getDate(PRE_DATA_INICI_OK), null, null, null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDataIniciNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(UtilTest.getDate(PRE_DATA_INICI_NO), null, null, null, null, null, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreDataFiOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, UtilTest.getDate(PRE_DATA_FI_OK), null, null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDataFiNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, UtilTest.getDate(PRE_DATA_FI_NO), null, null, null, null, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreBetweenOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(UtilTest.getDate(PRE_DATA_INICI_OK), UtilTest.getDate(PRE_DATA_FI_OK), null, null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreBetweenDadesGirades() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(UtilTest.getDate(PRE_DATA_INICI_NO), UtilTest.getDate(PRE_DATA_FI_NO), null, null, null, null, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreEnunciatOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, PRE_ENUNCIAT_OK, null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreEnunciatNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, PRE_ENUNCIAT_NO, null, null, null, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreCreadorNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, PRE_CREADOR_NO, null, null, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreCreadorAlies() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, PRE_CRE_ALIES_OK, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreCreadorNom() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, PRE_CRE_NOM_OK, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreCreadorLlinatge() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, PRE_CRE_LLINATGE_OK, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreRaonarRespostaOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, PRE_RAONAR_RES_OK, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreRaonarRespostaNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, PRE_RAONAR_RES_NO, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreRaonarRespostaNull() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, PRE_RAONAR_RES_NULL, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDifTeo1Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, PRE1_DIF_TEO1_OK, null, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDifTeo1No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, PRE1_DIF_TEO1_NO, null, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreDifTeo2Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, PRE1_DIF_TEO2_OK, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDifTeo2No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, PRE1_DIF_TEO2_NO, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreDifTeoBetweenOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, PRE1_DIF_TEO1_OK, PRE1_DIF_TEO2_OK, null, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDifTeoBetweenNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, PRE1_DIF_TEO1_NO, PRE1_DIF_TEO2_NO, null, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreDifPra1Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, PRE1_DIF_PRA1_OK, null, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDifPra1No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, PRE1_DIF_PRA1_NO, null, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreDifPra2Ok() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, PRE1_DIF_PRA2_OK, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDifPra2No() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, PRE1_DIF_PRA2_NO, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreDifPraBetweenOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, PRE1_DIF_PRA1_OK, PRE1_DIF_PRA2_OK, null, null, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreDifPraBetweenNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, PRE1_DIF_PRA1_NO, PRE1_DIF_PRA2_NO, null, null, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreTipusOk() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, PRE_TIPUS_OK, null, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreTipusNo() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, PRE_TIPUS_NO, null, professor.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreEstatPublic() {
		// La pregunta creada pel professor es troba en estat editable
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, Pregunta.ESTAT_EDITABLE);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		// Afegim un altre professor a l'assignatura: professor2
		personaProfessor2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaService.insereixPersona(personaProfessor2);
		professor2 = UtilTest.getProfessor(personaProfessor2, assignatura);
		professorService.insereixProfessor(professor2);
		
		// La pregunta creada pel professor2 es troba en estat públic
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor2, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, Pregunta.ESTAT_PUBLIC);
		preguntaService.insereixPregunta(pregunta2, new ArrayList<Opcio>());
		
		// Consultam les preguntes en estat públic que veu el professor 1
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, null, Pregunta.ESTAT_PUBLIC, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
		
		// Consultam les preguntes en estat públic que veu el professor 2
		llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, null, Pregunta.ESTAT_PUBLIC, professor2.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPreguntesFiltreEstatEditable() {
		// La pregunta creada pel professor es troba en estat editable
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, Pregunta.ESTAT_EDITABLE);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		// Afegim un altre professor a l'assignatura: professor2
		personaProfessor2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaService.insereixPersona(personaProfessor2);
		professor2 = UtilTest.getProfessor(personaProfessor2, assignatura);
		professorService.insereixProfessor(professor2);
		
		// La pregunta creada pel professor2 es troba en estat públic
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor2, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, Pregunta.ESTAT_PUBLIC);
		preguntaService.insereixPregunta(pregunta2, new ArrayList<Opcio>());
		
		// Consultam les preguntes en estat públic que veu el professor 1
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, null, Pregunta.ESTAT_EDITABLE, professor.getCodi());
		assertEquals(1, llistaPreguntes.size());
		
		// Consultam les preguntes en estat públic que veu el professor 2
		llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, null, Pregunta.ESTAT_EDITABLE, professor2.getCodi());
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testGetPreguntesFiltreEstatSenseIndicar() {
		// La pregunta creada pel professor es troba en estat editable
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, Pregunta.ESTAT_EDITABLE);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		// Afegim un altre professor a l'assignatura: professor2
		personaProfessor2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaService.insereixPersona(personaProfessor2);
		professor2 = UtilTest.getProfessor(personaProfessor2, assignatura);
		professorService.insereixProfessor(professor2);
		
		// La pregunta creada pel professor2 es troba en estat públic
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor2, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, Pregunta.ESTAT_PUBLIC);
		preguntaService.insereixPregunta(pregunta2, new ArrayList<Opcio>());
		
		// Consultam les preguntes en estat públic que veu el professor 1
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, null, null, professor.getCodi());
		assertEquals(2, llistaPreguntes.size());
		
		// Consultam les preguntes en estat públic que veu el professor 2
		llistaPreguntes = preguntaService.getPreguntes(null, null, null, null, null, null, null, null, null, null, null, professor2.getCodi());
		assertEquals(1, llistaPreguntes.size());
	}
	
	@Test
	public void testGetPregunta() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		Pregunta pregunta2 = preguntaService.getPregunta(pregunta.getCodi());
		assertEquals(PRE_ENUNCIAT1, pregunta2.getEnunciat());
	}
	
	@Test
	public void testGetOpcio() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		Opcio opcio = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, OPC_CORRECTA1, OPC_DATA_ALTA1);
		List<Opcio> opcions = new ArrayList<Opcio>();
		opcions.add(opcio);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		Opcio o = preguntaService.getOpcio(opcio.getCodi());
		
		assertEquals(opcio.getPregunta(), o.getPregunta());
		assertEquals(opcio.getText(), o.getText());
		assertEquals(opcio.getCorrecta(), o.getCorrecta());
		assertEquals(opcio.getDataAlta(), o.getDataAlta());
	}
	
	@Test
	public void testPublicaPregunta() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, UtilTest.getDate(PRE_DATA_ALTA_REF), PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		assertEquals(PRE_ESTAT1, pregunta.getEstat());
		
		// modificam l'estat a la pregunta
		pregunta.setEstat(PRE_ESTAT2);
		Pregunta pregunta2 = preguntaService.publicaPregunta(pregunta.getCodi());
		
		// comprovam que s'ha modificat correctament, però que segueix sent la mateixa pregunta
		assertEquals(pregunta.getCodi(), pregunta2.getCodi());
		assertEquals(PRE_ESTAT2, pregunta2.getEstat());
	}
	
	@Test
	public void testEliminaPregunta() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		// Consultam quantes preguntes tenim inserides a BD
		List<Pregunta> llistaPreguntes = preguntaService.getPreguntes();
		assertEquals(1, llistaPreguntes.size());
		
		// Eliminam la pregunta, i tornam a consultar
		preguntaService.eliminaPregunta(pregunta.getCodi().intValue());
		llistaPreguntes = preguntaService.getPreguntes();
		assertNull(llistaPreguntes);
	}
	
	@Test
	public void testModificaPreguntaEnunciat() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		// Definim un enunciat distint
		String enunciatDistint = "enunciat distint";
		
		// Comprovam que, abans de realitzar la modificació, l'enunciat no coincideix
		assertNotEquals(enunciatDistint, pregunta.getEnunciat());
		
		// Definim la pregunta nova
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, enunciatDistint, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		
		// modificam la pregunta original, amb les dades de la nova pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, new ArrayList<Opcio>());
		
		// comprovam que, efectivament, l'enunciat s'ha modificat correctament
		assertEquals(enunciatDistint, pregunta.getEnunciat());
	}
	
	@Test
	public void testModificaPreguntaRaonarResposta() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		
		// Definim un camp raonar resposta distint
		Boolean raonarRespostaDistint = PRE_RAONAR_RESPOSTA2;
		
		// Comprovam que, abans de realitzar la modificació, el camp de raonar resposta no coincideix
		assertNotEquals(raonarRespostaDistint, pregunta.getRaonarResposta());
		
		// Definim la pregunta nova
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, raonarRespostaDistint, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		
		// modificam la pregunta original, amb les dades de la nova pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, new ArrayList<Opcio>());
		
		// comprovam que, efectivament, el camp de raonar resposta s'ha modificat correctament
		assertEquals(raonarRespostaDistint, pregunta.getRaonarResposta());
	}
	
	@Test
	public void testModificaPreguntaDificultatTeorica() {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());

		// Definim una dificultat teòrica distinta
		Float dificultatTeoricaDistinta = PRE_DIFICULTAT_TEO1 / 2f;
		
		// Comprovam que, abans de realitzar la modificació, la dificultat teòrica no coincideix
		assertNotEquals(dificultatTeoricaDistinta, pregunta.getDificultatTeorica());
		
		// Definim la pregunta nova
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, dificultatTeoricaDistinta, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		
		// modificam la pregunta original, amb les dades de la nova pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, new ArrayList<Opcio>());
		
		// comprovam que, efectivament, la dificultat teòrica s'ha modificat correctament
		assertEquals(dificultatTeoricaDistinta, pregunta.getDificultatTeorica());
	}
	
	@Test
	public void testModificaPreguntaTipus() {
		String tipusEs1 = Pregunta.TIPUS_ES1;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEs1, PRE_VOF_VERTADER1, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());

		// Definim un tipus distint
		String tipusRec = Pregunta.TIPUS_REC;
		
		// Comprovam que, abans de realitzar la modificació, el tipus no coincideix
		assertNotEquals(tipusRec, pregunta.getTipus());
		
		// Definim la pregunta nova
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusRec, PRE_VOF_VERTADER1, PRE_ESTAT1);
		
		// modificam la pregunta original, amb les dades de la nova pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, new ArrayList<Opcio>());
		
		// comprovam que, efectivament, la dificultat teòrica s'ha modificat correctament
		assertEquals(tipusRec, pregunta.getTipus());
	}
	
	@Test
	public void testModificaPreguntaVofVertader() {
		Boolean vofVertaderTrue = Boolean.TRUE;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, vofVertaderTrue, PRE_ESTAT1);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());

		// Definim un vofVertader distint
		Boolean vofVertaderNull = null;
		
		// Comprovam que, abans de realitzar la modificació, el tipus no coincideix
		assertNotEquals(vofVertaderNull, pregunta.getVofVertader());
		
		// Definim la pregunta nova
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, PRE_TIPUS1, vofVertaderNull, PRE_ESTAT1);
		
		// modificam la pregunta original, amb les dades de la nova pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, new ArrayList<Opcio>());
		
		// comprovam que, efectivament, la dificultat teòrica s'ha modificat correctament
		assertEquals(vofVertaderNull, pregunta.getVofVertader());
	}
	
	@Test
	public void testModificaPreguntaPolaritatVOF() {
		// Definim i cream una pregunta VOF que és Vertadera (això és: opció vertadera correcta i opció falsa incorrecta)
		String tipusVof = Pregunta.TIPUS_VOF;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusVof, Boolean.TRUE, PRE_ESTAT1);
		Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		// Comprovam, prèviament, que la primera opció és Correcta i la segona incorrecta
		assertEquals(Boolean.TRUE,	pregunta.getOpcions().get(0).getCorrecta());
		assertEquals(Boolean.FALSE, pregunta.getOpcions().get(1).getCorrecta());
		
		// Definim una nova pregunta VOF igual que l'anterior, però amb la polaritat canviada
		// (Això és, la 1º opció falsa i la 2º vertadera)
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusVof, Boolean.TRUE, PRE_ESTAT1);
		opcio1 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT1, Boolean.FALSE, OPC_DATA_ALTA1);
    	opcio2 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT2, Boolean.TRUE, OPC_DATA_ALTA2);
    	opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	// pregunta2.setOpcions(opcions); <-- no fa falta:
    	// pregunta2 és el POJO amb què modificarem pregunta. Les opcions de pregunta2 no fa falta assignar-les a pregunta2.
    	// El mètode de Servei modificaPregunta està preparat per rebre una llista suelta d'opcions.
		
		// modificam la pregunta dient que volem que sigui Falsa. Això ho conseguim passant com a paràmetre: Boolean.FALSE
		// Això canviarà la polaritat de les opcions, de manera que, ara:
		// la opció vertadera serà incorrecta, mentre que la opció falsa serà correcta.
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, opcions);
		
		// Comprovam, després de la modificació, que la primera opció és incorrecta i la segona correcta
		assertEquals(Boolean.FALSE,	pregunta.getOpcions().get(0).getCorrecta());
		assertEquals(Boolean.TRUE, 	pregunta.getOpcions().get(1).getCorrecta());
	}
	
	@Test
	public void testModificaPreguntaES1afegirOpcio() {
		// Definim i cream una pregunta ES1
		String tipusEs1 = Pregunta.TIPUS_ES1;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEs1, null, PRE_ESTAT1);
		Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		// Comprovam, prèviament, hi han 2 opcions
		assertEquals(NUM_OPCIONS, pregunta.getOpcions().size());
		
		// Definim una nova pregunta ES1 igual que l'anterior, però amb una opció més
		// (Això és, que l'usuari haurà afegit una opció)
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEs1, null, PRE_ESTAT1);
		opcio1 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT1, Boolean.FALSE, OPC_DATA_ALTA1);
    	opcio2 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT2, Boolean.TRUE, OPC_DATA_ALTA2);
    	Opcio opcio3 = UtilTest.inserirOpcio(pregunta2, "text opcio 3", Boolean.FALSE, new Date());
    	opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	opcions.add(opcio3);
    	// pregunta2.setOpcions(opcions); <-- no fa falta:
    	// pregunta2 és el POJO amb què modificarem pregunta. Les opcions de pregunta2 no fa falta assignar-les a pregunta2.
    	// El mètode de Servei modificaPregunta està preparat per rebre una llista suelta d'opcions.
		
		// modificam la pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, opcions);
		
		// Comprovam, després de la modificació, que la primera opció és incorrecta i la segona correcta
		assertEquals(NUM_OPCIONS + 1, pregunta.getOpcions().size());
	}
	
	@Test
	public void testModificaPreguntaES1eliminarOpcio() {
		// Definim i cream una pregunta ES1
		String tipusEs1 = Pregunta.TIPUS_ES1;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEs1, null, PRE_ESTAT1);
		Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		// Comprovam, prèviament, hi han 2 opcions
		assertEquals(NUM_OPCIONS, pregunta.getOpcions().size());
		
		// Definim una nova pregunta ES1 igual que l'anterior, però amb una opció més
		// (Això és, que l'usuari haurà afegit una opció)
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEs1, null, PRE_ESTAT1);
		opcio1 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	// pregunta2.setOpcions(opcions); <-- no fa falta:
    	// pregunta2 és el POJO amb què modificarem pregunta. Les opcions de pregunta2 no fa falta assignar-les a pregunta2.
    	// El mètode de Servei modificaPregunta està preparat per rebre una llista suelta d'opcions.
		
		// modificam la pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, opcions);
		
		// Comprovam, després de la modificació, que la primera opció és incorrecta i la segona correcta
		assertEquals(NUM_OPCIONS - 1, pregunta.getOpcions().size());
	}
	
	@Test
	public void testModificaPreguntaES1modificarOpcio() {
		// Definim i cream una pregunta ES1
		String tipusEs1 = Pregunta.TIPUS_ES1;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEs1, null, PRE_ESTAT1);
		Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		// Comprovam, prèviament, que els texts i les correcteses coincideixen
		assertEquals(OPC_TEXT1, 	pregunta.getOpcions().get(0).getText());
		assertEquals(OPC_TEXT2, 	pregunta.getOpcions().get(1).getText());
		assertEquals(Boolean.TRUE, 	pregunta.getOpcions().get(0).getCorrecta());
		assertEquals(Boolean.FALSE, pregunta.getOpcions().get(1).getCorrecta());
		
		// Definim una nova pregunta ES1 igual que l'anterior, però amb les opcions
		// modificades (tant del text com de la correctesa)
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEs1, null, PRE_ESTAT1);
		String textOpcio1Nou = "Text d'opció 1 modificat";
		opcio1 = UtilTest.inserirOpcio(pregunta2, textOpcio1Nou, Boolean.FALSE, OPC_DATA_ALTA1);
    	opcio2 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT2, Boolean.TRUE, OPC_DATA_ALTA2);
    	opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	// pregunta2.setOpcions(opcions); <-- no fa falta:
    	// pregunta2 és el POJO amb què modificarem pregunta. Les opcions de pregunta2 no fa falta assignar-les a pregunta2.
    	// El mètode de Servei modificaPregunta està preparat per rebre una llista suelta d'opcions.
		
		// modificam la pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, opcions);
		
		// Comprovam, després de la modificació, que tant el text com la correctesa de les opcions s'ha modificat correctament
		assertEquals(textOpcio1Nou, pregunta.getOpcions().get(0).getText());
		assertEquals(OPC_TEXT2, 	pregunta.getOpcions().get(1).getText());
		assertEquals(Boolean.FALSE, pregunta.getOpcions().get(0).getCorrecta());
		assertEquals(Boolean.TRUE, 	pregunta.getOpcions().get(1).getCorrecta());
	}
	
	@Test
	public void testModificaPreguntaESNafegirOpcio() {
		// Definim i cream una pregunta ESN
		String tipusEsN = Pregunta.TIPUS_ESN;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEsN, null, PRE_ESTAT1);
		Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		// Comprovam, prèviament, hi han 2 opcions
		assertEquals(NUM_OPCIONS, pregunta.getOpcions().size());
		
		// Definim una nova pregunta ESN igual que l'anterior, però amb una opció més
		// (Això és, que l'usuari haurà afegit una opció)
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEsN, null, PRE_ESTAT1);
		opcio1 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT1, Boolean.FALSE, OPC_DATA_ALTA1);
    	opcio2 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT2, Boolean.TRUE, OPC_DATA_ALTA2);
    	Opcio opcio3 = UtilTest.inserirOpcio(pregunta2, "text opcio 3", Boolean.TRUE, new Date());
    	opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	opcions.add(opcio3);
    	// pregunta2.setOpcions(opcions); <-- no fa falta:
    	// pregunta2 és el POJO amb què modificarem pregunta. Les opcions de pregunta2 no fa falta assignar-les a pregunta2.
    	// El mètode de Servei modificaPregunta està preparat per rebre una llista suelta d'opcions.
		
		// modificam la pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, opcions);
		
		// Comprovam, després de la modificació, que la primera opció és incorrecta i la segona correcta
		assertEquals(NUM_OPCIONS + 1, pregunta.getOpcions().size());
	}
	
	@Test
	public void testModificaPreguntaESNeliminarOpcio() {
		// Definim i cream una pregunta ESN
		String tipusEsN = Pregunta.TIPUS_ESN;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEsN, null, PRE_ESTAT1);
		Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		// Comprovam, prèviament, hi han 2 opcions
		assertEquals(NUM_OPCIONS, pregunta.getOpcions().size());
		
		// Definim una nova pregunta ESN igual que l'anterior, però amb una opció més
		// (Això és, que l'usuari haurà afegit una opció)
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEsN, null, PRE_ESTAT1);
		opcio1 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	// pregunta2.setOpcions(opcions); <-- no fa falta:
    	// pregunta2 és el POJO amb què modificarem pregunta. Les opcions de pregunta2 no fa falta assignar-les a pregunta2.
    	// El mètode de Servei modificaPregunta està preparat per rebre una llista suelta d'opcions.
		
		// modificam la pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, opcions);
		
		// Comprovam, després de la modificació, que la primera opció és incorrecta i la segona correcta
		assertEquals(NUM_OPCIONS - 1, pregunta.getOpcions().size());
	}
	
	@Test
	public void testModificaPreguntaESNmodificarOpcio() {
		// Definim i cream una pregunta ESN
		String tipusEsN = Pregunta.TIPUS_ESN;
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEsN, null, PRE_ESTAT1);
		Opcio opcio1 = UtilTest.inserirOpcio(pregunta, OPC_TEXT1, Boolean.TRUE, OPC_DATA_ALTA1);
    	Opcio opcio2 = UtilTest.inserirOpcio(pregunta, OPC_TEXT2, Boolean.FALSE, OPC_DATA_ALTA2);
    	List<Opcio> opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	pregunta.setOpcions(opcions);
		preguntaService.insereixPregunta(pregunta, opcions);
		
		// Comprovam, prèviament, que els texts i les correcteses coincideixen
		assertEquals(OPC_TEXT1, 	pregunta.getOpcions().get(0).getText());
		assertEquals(OPC_TEXT2, 	pregunta.getOpcions().get(1).getText());
		assertEquals(Boolean.TRUE, 	pregunta.getOpcions().get(0).getCorrecta());
		assertEquals(Boolean.FALSE, pregunta.getOpcions().get(1).getCorrecta());
		
		// Definim una nova pregunta ES1 igual que l'anterior, però amb les opcions
		// modificades (tant del text com de la correctesa)
		Pregunta pregunta2 = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT1, PRE_DIFICULTAT_TEO1, PRE_DIFICULTAT_PRA1, PRE_RAONAR_RESPOSTA1, PRE_DATA_ALTA1, tipusEsN, null, PRE_ESTAT1);
		String textOpcio1Nou = "Text d'opció 1 modificat";
		opcio1 = UtilTest.inserirOpcio(pregunta2, textOpcio1Nou, Boolean.TRUE, OPC_DATA_ALTA1);
    	opcio2 = UtilTest.inserirOpcio(pregunta2, OPC_TEXT2, Boolean.TRUE, OPC_DATA_ALTA2);
    	opcions = new ArrayList<Opcio>();
    	opcions.add(opcio1);
    	opcions.add(opcio2);
    	// pregunta2.setOpcions(opcions); <-- no fa falta:
    	// pregunta2 és el POJO amb què modificarem pregunta. Les opcions de pregunta2 no fa falta assignar-les a pregunta2.
    	// El mètode de Servei modificaPregunta està preparat per rebre una llista suelta d'opcions.
		
		// modificam la pregunta
		pregunta = preguntaService.modificaPregunta(pregunta.getCodi(), pregunta2, opcions);
		
		// Comprovam, després de la modificació, que tant el text com la correctesa de les opcions s'ha modificat correctament
		assertEquals(textOpcio1Nou, pregunta.getOpcions().get(0).getText());
		assertEquals(OPC_TEXT2, 	pregunta.getOpcions().get(1).getText());
		assertEquals(Boolean.TRUE, 	pregunta.getOpcions().get(0).getCorrecta());
		assertEquals(Boolean.TRUE, 	pregunta.getOpcions().get(1).getCorrecta());
	}
	
}
