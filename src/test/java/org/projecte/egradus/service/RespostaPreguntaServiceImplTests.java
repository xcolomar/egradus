package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;
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
public class RespostaPreguntaServiceImplTests {
	
	@Autowired
	private RespostaPreguntaService respostaPreguntaService;
	
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
	private static String PRO_NOM              = "Aina";
    private static String PRO_PRIMER_LLINATGE  = "González";
    private static String PRO_CORREU           = "ainagonzalez@correu.es";
    private static String PRO_ALIES            = "aina";
    private static String PRO_CLAU             = "ainagonzalez";
    private static String PRO_DATA_ALTA        = "20/09/2014";
    
    private static String ALU_NOM              = "Miquel";
    private static String ALU_PRIMER_LLINATGE  = "Garcia";
    private static String ALU_CORREU           = "m.garcia@correu.es";
    private static String ALU_ALIES            = "miquelet";
    private static String ALU_CLAU             = "claumiquel";
    private static String ALU_DATA_ALTA        = "20/11/2013";
    
    private static String ALU2_NOM             = "Juan";
    private static String ALU2_PRIMER_LLINATGE = "Estarelles";
    private static String ALU2_CORREU          = "estarelles@yahoo.es";
    private static String ALU2_ALIES           = "juanest";
    private static String ALU2_CLAU            = "juanest";
    private static String ALU2_DATA_ALTA       = "01/03/2014";
    
    private static String ALU3_NOM             = "Xavier";
    private static String ALU3_PRIMER_LLINATGE = "Colomar";
    private static String ALU3_CORREU          = "xcolomar@uib.es";
    private static String ALU3_ALIES           = "xavi";
    private static String ALU3_CLAU            = "xaviclau";
    private static String ALU3_DATA_ALTA       = "01/02/2014";
    
    private static String ALU4_NOM             = "Elvira";
    private static String ALU4_PRIMER_LLINATGE = "Sánchez";
    private static String ALU4_CORREU          = "elvira89@uib.es";
    private static String ALU4_ALIES           = "elvis";
    private static String ALU4_CLAU            = "elvisclau";
    private static String ALU4_DATA_ALTA       = "04/10/2014";
    
    private static String ALU5_NOM             = "Albert";
    private static String ALU5_PRIMER_LLINATGE = "Colomar";
    private static String ALU5_CORREU          = "acolomar@uib.es";
    private static String ALU5_ALIES           = "alberco";
    private static String ALU5_CLAU            = "alberco1";
    private static String ALU5_DATA_ALTA       = "22/09/2014";
    
    // Dades de l'Assignatura
    private static String ASS_NOM              = "Intel·ligència Artificial";
	private static String ASS_DESCRIPCIO       = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR   = "clau_inteli_artifi";
	private static String ASS_CLAU_ALUMNE      = "clau_intart";
	private static String ASS_DATA_ALTA        = "01/10/2013";
	private static String ASS_ANY_ACADEMIC     = "2013-14";
	private static String ASS_CODI_REF         = "INTELIART";
	
	// Dades de la Pregunta
	private static String  PRE_ENUNCIAT        = "Aquest és l'enunciat de la pregunta";
	private static Float   PRE_DIFICULTAT_TEO  = 0.5f;
	private static Float   PRE_DIFICULTAT_PRA  = null;
	private static Boolean PRE_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date    PRE_DATA_ALTA       = new Date();
	private static String  PRE_TIPUS           = Pregunta.TIPUS_VOF;
	private static Boolean PRE_VOF_VERTADER    = Boolean.FALSE;
	// per a comprovar la correcció manual de preguntes
	private static String  PRE_TIPUS_REC       = Pregunta.TIPUS_REC;
	
	// Dades de les Opcions de la Pregunta
	private static String  OPC1_TEXT           = "Text de la opció 1";
	private static Boolean OPC1_CORRECTA       = Boolean.FALSE;
	private static Date    OPC1_DATA_ALTA      = UtilTest.getDate("11/10/2014");
	
	private static String  OPC2_TEXT           = "Text de la opció 2";
	private static Boolean OPC2_CORRECTA       = Boolean.TRUE;
	private static Date    OPC2_DATA_ALTA      = UtilTest.getDate("11/10/2014");
	
	// Dades de la contestació de la pregunta
	private static String  RP_TEXT_RR          = "Aquest és el raonament de la resposta";
	private static String  RP_TEXT_REC         = "Aquest és el text de resposta de pregunta REC";
	
	// Dades de la correcció de la pregunta REC
	private static String  RP_TEXT_CRR_REC     = "Aquest és el text de la correcció de la pregunta REC";
	private static Float   RP_NOTA_CRR_REC     = 4.6f;
	
	private static String  CNT_TEXT_RESPOSTA   = "Text de resposta en la contestació de la pregunta";
	private static String  CNT_TEXT_RR         = "Text de raonament de la resposta en la contestació de la pregunta";
	
	private Persona personaProfessor;
	private Persona personaAlumne;
	private Assignatura assignatura;
	private Professor professor;
	private Alumne alumne;
	private Pregunta pregunta;
	
	
	/**
	 * Mètode local que retorna un Alumne amb els següents paràmetres:
	 * @param nom
	 * @param primerLlinatge
	 * @param correu
	 * @param alies
	 * @param clau
	 * @param dataAlta
	 * @param assignatura
	 * @return
	 */
	private Alumne nouAlumne(String nom, String primerLlinatge, String correu, String alies, String clau, Date dataAlta, Assignatura assignatura) {
		Persona persona = UtilTest.inserirPersona(nom, primerLlinatge, correu, alies, clau, dataAlta);
		personaService.insereixPersona(persona);
		return UtilTest.getAlumne(persona, assignatura);
	}
	
	/**
	 * Mètode local que retorna una Pregunta REC creada pel professor passat per
	 * paràmetre
	 * @param professor
	 * @return
	 */
	private Pregunta novaPreguntaREC(Professor professor) {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA, PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS_REC, null, Pregunta.ESTAT_PUBLIC);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		return pregunta;
	}
	
	@Before
	public void initialize() {
		// Inserim les persones
		personaProfessor = UtilTest.inserirPersona(PRO_NOM, PRO_PRIMER_LLINATGE, PRO_CORREU, PRO_ALIES, PRO_CLAU, UtilTest.getDate(PRO_DATA_ALTA));
		personaService.insereixPersona(personaProfessor);
		personaAlumne = UtilTest.inserirPersona(ALU_NOM, ALU_PRIMER_LLINATGE, ALU_CORREU, ALU_ALIES, ALU_CLAU, UtilTest.getDate(ALU_DATA_ALTA));
		personaService.insereixPersona(personaAlumne);
		
		// Inserim l'assignatura (i l'associam a la persona 'personaProfessor')
		assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaService.insereixAssignatura(assignatura);
		
		// Quan inserim una assignatura, automàticament la persona s'insereix com a professor
    	// i per tant només l'hem d'agafar:
    	professor = professorService.getProfessor(personaProfessor, assignatura);
		
		// Inserim el rol d'alumne per a la persona 'personaAlumne' a l'assignatura 'assignatura'
		alumne = UtilTest.getAlumne(personaAlumne, assignatura);
		alumneService.insereixAlumne(alumne);
		
		// Definim la pregunta
		pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA, PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		
		// Definim les dues opcions que té la pregunta
		List<Opcio> opcions = new ArrayList<Opcio>();
		opcions.add(UtilTest.inserirOpcio(pregunta, OPC1_TEXT, OPC1_CORRECTA, OPC1_DATA_ALTA));
		opcions.add(UtilTest.inserirOpcio(pregunta, OPC2_TEXT, OPC2_CORRECTA, OPC2_DATA_ALTA));
		pregunta.setOpcions(opcions);
		
		// Inserim la pregunta (i la enllaçam amb les opcions)
		preguntaService.insereixPregunta(pregunta, opcions);
	}
	
	@Test
	public void testGetPreguntesPerRespondreNull() {
		assertNull(respostaPreguntaService.getPreguntesPerRespondre(alumne));
	}
	
	@Test
	public void testEnviaPregunta() {
		// Afegim un alumne més a l'assignatura
		Alumne alumne2 = nouAlumne(ALU2_NOM, ALU2_PRIMER_LLINATGE, ALU2_CORREU, ALU2_ALIES, ALU2_CLAU, UtilTest.getDate(ALU2_DATA_ALTA), assignatura);
		alumneService.insereixAlumne(alumne2);
		
		// enviam la pregunta als dos alumnes (nou i antic)
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne2);
		alumnes.add(alumne);
		respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// comprovam que els alumnes l'han rebuda
		assertEquals(1, respostaPreguntaService.getPreguntesPerRespondre(alumne).size());
		assertEquals(1, respostaPreguntaService.getPreguntesPerRespondre(alumne2).size());
	}
	
	@Test
	public void testEnviaPreguntaAMoltsAlumnes() {
		// Afegim 4 alumnes més a l'assignatura
		Alumne alumne2 = nouAlumne(ALU2_NOM, ALU2_PRIMER_LLINATGE, ALU2_CORREU, ALU2_ALIES, ALU2_CLAU, UtilTest.getDate(ALU2_DATA_ALTA), assignatura);
		Alumne alumne3 = nouAlumne(ALU3_NOM, ALU3_PRIMER_LLINATGE, ALU3_CORREU, ALU3_ALIES, ALU3_CLAU, UtilTest.getDate(ALU3_DATA_ALTA), assignatura);
		Alumne alumne4 = nouAlumne(ALU4_NOM, ALU4_PRIMER_LLINATGE, ALU4_CORREU, ALU4_ALIES, ALU4_CLAU, UtilTest.getDate(ALU4_DATA_ALTA), assignatura);
		Alumne alumne5 = nouAlumne(ALU5_NOM, ALU5_PRIMER_LLINATGE, ALU5_CORREU, ALU5_ALIES, ALU5_CLAU, UtilTest.getDate(ALU5_DATA_ALTA), assignatura);
		alumneService.insereixAlumne(alumne2);
		alumneService.insereixAlumne(alumne3);
		alumneService.insereixAlumne(alumne4);
		alumneService.insereixAlumne(alumne5);
		
		// enviam la pregunta a 3 alumnes (el primer de tot i els 2 darrers)
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		alumnes.add(alumne4);
		alumnes.add(alumne5);
		respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// comprovam que els alumnes 1, 4 i 5 l'han rebuda
		assertEquals(1, respostaPreguntaService.getPreguntesPerRespondre(alumne).size());
		assertEquals(1, respostaPreguntaService.getPreguntesPerRespondre(alumne4).size());
		assertEquals(1, respostaPreguntaService.getPreguntesPerRespondre(alumne5).size());
		
		// comprovam que els alumnes 2 i 3 no l'han rebuda
		assertNull(respostaPreguntaService.getPreguntesPerRespondre(alumne2));
		assertNull(respostaPreguntaService.getPreguntesPerRespondre(alumne3));
	}
	
	@Test
	public void testGetRespostaPregunta() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// ens guardam el codi per la comprovació final
		int codi = rp.getCodi();
		
		rp = respostaPreguntaService.getRespostaPregunta(codi);
		
		// comprovam que són la mateixa resposta-pregunta
		assertEquals(codi, rp.getCodi(), 0);
	}
	
	@Test 
	public void testIniciaContestacioPreguntaFals() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// com que no feim una crida a 'iniciarContestacioPregunta(...);', la pregunta no s'ha
		// començat a contestar:
		assertNull(rp.getDataContestacioInici());
	}
	
	@Test
	public void testIniciaContestacioPreguntaVertader() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// com que s'ha iniciat la contestació de la pregunta, ja existirà data d'inici de contestació
		assertNotNull(rp.getDataContestacioInici());
	}
	
	@Test
	public void testGetOpcionsContestadesBuit() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// com que no s'ha finalitzat la contestació de la pregunta, no hi poden haver opcions marcades.
		// De totes maneres, iniciarContestacioPregunta crea un array buit, per tant la comprovació no és
		// si rp.getOpcionsMarcades és o no nul, sino si està buit o no
		assertEquals(0, rp.getOpcionsMarcades().size());
	}
	
	@Test
	public void testGetOpcionsContestades() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// mentre l'alumne contesta la pregunta, va marcant opcions:
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		opcionsMarcades.add(UtilTest.inserirOpcioResposta(pregunta.getOpcions().get(1), rp));
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
		
		// com que s'ha finalitzat la contestació de la pregunta, hi poden haver opcions marcades.
		assertEquals(1, rp.getOpcionsMarcades().size());
	}
	
	@Test
	public void testFinalitzaContestacioPreguntaSenseOpcionsVertader() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		pregunta = rp.getPregunta();
		
		// Obtenim el num total de contestacions en aquest moment per a fer comprovacions després
		int numTotalActualitzacionsNotaMitja = pregunta.getNumTotalActualitzacionsNotaMitja();
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		pregunta = rp.getPregunta();
		
		// com que s'ha iniciat i finalitzat la contestació de la pregunta, ja existirà data de fi de contestació
		// i estarà marcada com a contestada
		assertNotNull(rp.getDataContestacioInici());
		assertTrue(rp.getContestada());
		
		// Comprovam que l'atribut numTotalContestacions s'ha actualitzat
		assertEquals(numTotalActualitzacionsNotaMitja + 1, pregunta.getNumTotalActualitzacionsNotaMitja());
	}
	
	@Test
	public void testFinalitzaContestacioPreguntaAmbOpcionsVertader() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// mentre l'alumne contesta la pregunta, va marcant opcions:
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		opcionsMarcades.add(UtilTest.inserirOpcioResposta(pregunta.getOpcions().get(1), rp));
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
		pregunta = rp.getPregunta();
		
		// com que s'ha iniciat i finalitzat la contestació de la pregunta, ja existirà data de fi de contestació
		// i estarà marcada com a contestada
		assertNotNull(rp.getDataContestacioInici());
		assertTrue(rp.getContestada());
	}
	
	@Test
	public void testFinalitzaContestacioPreguntaFals() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// com que no feim una crida a 'finalitzarContestacioPregunta(...);', la pregunta no s'ha
		// acabat de contestar:
		assertNotNull(rp.getDataContestacioInici());
		assertNull(rp.getDataContestacioFi());
		assertFalse(rp.getContestada());
	}
	
	@Test
	public void testFinalitzaContestacioPreguntaCorreccioAutomatica() {
		// enviam la pregunta a l'alumne (És de tipus VOF)
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// abans de contestar la pregunta, agafam l'estat actual de la dificultat pràctica i del total
		// de persones que han contestat la pregunta:
		Float dp = pregunta.getDificultatPractica();
		int numContestacions = pregunta.getNumTotalActualitzacionsNotaMitja();
		
		// comprovam que no hi ha cap contestació i que la dificultat pràctica encara no està definida
		assertNull(dp);
		assertEquals(0, numContestacions);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		pregunta = rp.getPregunta();
		
		// En aquest punt la nota mitja i la dificultat pràctica encara no s'han assignat
		assertNull(pregunta.getDificultatPractica());
		assertNull(pregunta.getNotaMitja());
		
		// mentre l'alumne contesta la pregunta, va marcant opcions:
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		opcionsMarcades.add(UtilTest.inserirOpcioResposta(pregunta.getOpcions().get(1), rp));
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
		pregunta = rp.getPregunta();
		
		// comprovam que la nota mitja i la dificultat pràctica ja està definida i que el num de 
		// contestacions s'ha incrementat en 1
		assertNotNull(pregunta.getDificultatPractica());
		assertNotNull(pregunta.getNotaMitja());
		assertEquals(numContestacions + 1, pregunta.getNumTotalActualitzacionsNotaMitja());
	}
	
	@Test
	public void testGetPreguntesContestades() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// comprovam que l'alumne NO té, encara, preguntes contestades
		List<RespostaPregunta> llista = respostaPreguntaService.getPreguntesContestades(alumne);
		assertNull(llista);
		
		// finalitzam la contestació de la pregunta
		respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		
		// comprovam que efectivament l'alumne té preguntes contestades
		llista = respostaPreguntaService.getPreguntesContestades(alumne);
		assertEquals(1, llista.size());
	}
	
	@Test
	public void testGetPreguntesPerCorregirPreguntaREC() {
		// Definim una pregunta REC
		Pregunta preguntaREC = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA, PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS_REC, null, Pregunta.ESTAT_PUBLIC);
		
		// Inserim la pregunta (amb un llistat d'opcions buit)
		preguntaService.insereixPregunta(preguntaREC, new ArrayList<Opcio>());
		
		// enviam a l'alumne la pregunta que acabam de definir
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(preguntaREC.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		pregunta = rp.getPregunta();
		
		// comprovam que no hi ha cap contestació i que la dificultat pràctica i la nota mitja encara no estan definides
		assertNull(pregunta.getDificultatPractica());
		assertNull(pregunta.getNotaMitja());
		assertNull(pregunta.getTempsRespostaMig());
		assertEquals(0, pregunta.getNumTotalActualitzacionsNotaMitja());
		
		// comprovam que el professor NO té, encara, preguntes pendents de corregir
		List<RespostaPregunta> llista = respostaPreguntaService.getPreguntesPerCorregir(professor);
		assertNull(llista);
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		pregunta = rp.getPregunta();
		
		// comprovam que efectivament el professor ja té una pregunta pendent de corregir, degut a que
		// aquesta pregunta és REC, i requereix correcció manual del professor
		llista = respostaPreguntaService.getPreguntesPerCorregir(professor);
		assertEquals(1, llista.size());
		
		// comprovam que la dificultat pràctica i la nota mitja segueixen sense estar definides
		assertNull(pregunta.getDificultatPractica());
		assertNull(pregunta.getNotaMitja());
		
		// el temps de resposta mig s'han actualitzat (ja que la pregunta ha sigut contestada)
		// però la nota mitja no (ja que la pregunta encara no ha sigut corregida, degut a que és REC i requereix
		// correcció manual per part del professor)
		assertEquals(0, pregunta.getNumTotalActualitzacionsNotaMitja());
		assertNotNull(pregunta.getTempsRespostaMig());
	}
	
	@Test
	public void testGetPreguntesPerCorregirPreguntaNoREC() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// comprovam que el professor NO té, encara, preguntes pendents de corregir
		List<RespostaPregunta> llista = respostaPreguntaService.getPreguntesPerCorregir(professor);
		assertNull(llista);
		
		// finalitzam la contestació de la pregunta
		respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		
		// comprovam que, així i tot, el professor segueix sense tenir cap pregunta per corregir,
		// ja que la pregunta és de tipus VOF
		llista = respostaPreguntaService.getPreguntesPerCorregir(professor);
		assertNull(llista);
	}
	
	@Test
	public void testCorreccioPreguntaREC() {
		// cream una pregunta REC
		Pregunta p = novaPreguntaREC(professor);
		
		// enviam aquesta pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(p.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// abans de contestar la pregunta, agafam l'estat actual de la dificultat pràctica i del total
		// de persones que han contestat la pregunta:
		Float dp = pregunta.getDificultatPractica();
		int numActualitzacionsNotaMitja = pregunta.getNumTotalActualitzacionsNotaMitja();
		
		// comprovam que no hi ha cap contestació i que la dificultat pràctica encara no està definida
		assertNull(dp);
		assertEquals(0, numActualitzacionsNotaMitja);
		
		// iniciam la contestació de la pregunta
		respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		pregunta = rp.getPregunta();
		
		// comprovam que la pregunta encara no ha vist actualitzada la seva nota mitja
		assertEquals(numActualitzacionsNotaMitja, pregunta.getNumTotalActualitzacionsNotaMitja());
		
		// iniciam la correcció de la pregunta
		rp = respostaPreguntaService.iniciarCorreccioPregunta(rp);
		pregunta = rp.getPregunta();
		
		// ens asseguram de què encara no està corregida
		assertFalse(rp.getCorregida());
		
		// comprovam que la dificultat pràctica i la nota mitja encara no estan definides
		assertNull(pregunta.getDificultatPractica());
		assertNull(pregunta.getNotaMitja());
		
		// finalitzam la correcció de la pregunta
		rp = respostaPreguntaService.finalitzarCorreccioPregunta(rp, RP_TEXT_CRR_REC, null, RP_NOTA_CRR_REC);
		pregunta = rp.getPregunta();
		
		// la resposta-pregunta ja hauria de figurar com a corregida
		assertTrue(rp.getCorregida());
		
		// comprovam que ha s'ha actualitzat la nota mitja de la pregunta
		assertEquals(numActualitzacionsNotaMitja + 1, pregunta.getNumTotalActualitzacionsNotaMitja());
		
		// comprovam que la dificultat pràctica i la nota mitja ja estan definides
		assertNotNull(pregunta.getDificultatPractica());
		assertNotNull(pregunta.getNotaMitja());
	}
	
	@Test
	public void testGetPreguntesCorregidesPreguntaREC() {
		// Definim una pregunta REC
		Pregunta preguntaREC = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA, PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS_REC, null, Pregunta.ESTAT_PUBLIC);
		
		// Inserim la pregunta (amb un llistat d'opcions buit)
		preguntaService.insereixPregunta(preguntaREC, new ArrayList<Opcio>());
		
		// enviam a l'alumne la pregunta que acabam de definir
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(preguntaREC.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		respostaPreguntaService.iniciarContestacioPregunta(rp);
		
		// finalitzam la contestació de la pregunta
		respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		
		// iniciam la correcció de la pregunta
		rp = respostaPreguntaService.iniciarCorreccioPregunta(rp);
		
		// comprovam que el professor NO té, encara, preguntes corregides
		List<RespostaPregunta> llista = respostaPreguntaService.getPreguntesCorregides(professor);
		assertNull(llista);
		
		// finalitzam la correcció de la pregunta
		rp = respostaPreguntaService.finalitzarCorreccioPregunta(rp, RP_TEXT_CRR_REC, null, RP_NOTA_CRR_REC);
		
		// comprovam que efectivament el professor ja té una pregunta corregida
		llista = respostaPreguntaService.getPreguntesCorregides(professor);
		assertEquals(1, llista.size());
	}
	
	@Test
	public void testIniciaContestacioPregunta() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// agafam les resposta-preguntes que l'alumne té pendent de contestar
		List<RespostaPregunta> llistaRp = respostaPreguntaService.getPreguntesPerRespondre(alumne);
		
		RespostaPregunta rp = llistaRp.get(0);
		
		// abans d'iniciar la contestació de la RP comprovam que no existeix cap llista
		// d'opcions marcades i que no s'ha definit cap data d'inici de contestacio
		assertNull(rp.getOpcionsMarcades());
		assertNull(rp.getDataContestacioInici());
		
		// Iniciam la contestació d'aquesta RP
		rp = respostaPreguntaService.iniciaContestacio(rp);
		
		// Obtenim la pregunta per a poder verificar que és la que hem assignat al principi
		Pregunta p = rp.getPregunta();
		assertEquals(pregunta.getCodi(), p.getCodi());
		assertEquals(pregunta.getEnunciat(), p.getEnunciat());
		assertEquals(pregunta.getTipus(), p.getTipus());
		assertEquals(pregunta.getDataAlta(), p.getDataAlta());
		assertEquals(pregunta.getCreador().getCodi(), p.getCreador().getCodi());
		assertEquals(pregunta.getDificultatTeorica(), p.getDificultatTeorica());
		assertEquals(pregunta.getDificultatPractica(), p.getDificultatPractica());
		assertEquals(pregunta.getEstat(), p.getEstat());
		assertEquals(pregunta.getRaonarResposta(), p.getRaonarResposta());
		assertEquals(pregunta.getNumTotalActualitzacionsNotaMitja(), p.getNumTotalActualitzacionsNotaMitja());
		assertEquals(pregunta.getVofVertader(), p.getVofVertader());
		assertEquals(pregunta.getOpcions().size(), p.getOpcions().size());
		Opcio opcioOriginal = null;
		Opcio opcioTest = null;
		for (int i = 0; i < pregunta.getOpcions().size(); i++) {
			opcioOriginal = pregunta.getOpcions().get(i);
			opcioTest = p.getOpcions().get(i);
			assertEquals(opcioOriginal.getCodi(), opcioTest.getCodi());
			assertEquals(opcioOriginal.getText(), opcioTest.getText());
			assertEquals(opcioOriginal.getCorrecta(), opcioTest.getCorrecta());
		}
		if (pregunta.getQuestionaris() != null) {
			assertEquals(pregunta.getQuestionaris().size(), p.getQuestionaris().size());
		} else {
			assertEquals(pregunta.getQuestionaris(), p.getQuestionaris());
		}
		
		// Comprovam que no hi ha cap opció marcada (És a dir, existeix una llista, sense cap opció)
		// i que la data d'inici de contestació ja s'ha assignat
		assertEquals(0, rp.getOpcionsMarcades().size());
		assertNotNull(rp.getDataContestacioInici());
	}
	
	@Test
	public void testFinalitzaContestacioPregunta() {
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		respostaPreguntaService.enviaPregunta(pregunta.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// agafam les resposta-preguntes que l'alumne té pendent de contestar
		List<RespostaPregunta> llistaRp = respostaPreguntaService.getPreguntesPerRespondre(alumne);
		
		RespostaPregunta rp = llistaRp.get(0);
		
		// Obtenim el numero de contestacions total que ha tengut la pregunta
		int numContestacions = rp.getPregunta().getNumTotalActualitzacionsNotaMitja();
		
		// Iniciam la contestació d'aquesta RP
		rp = respostaPreguntaService.iniciaContestacio(rp);
		
		// Abans de finalitzar la contestació, ens asseguram que no hi ha definida una data de finalització
		// de la contestació, que el flag contestada està a fals, i que no s'ha inserit cap text de RR ni que
		// s'ha seleccionat cap opció
		assertNull(rp.getDataContestacioFi());
		assertFalse(rp.getContestada());
		assertNull(rp.getTextRaonarResposta());
		assertEquals(0, rp.getOpcionsMarcades().size());
		
		// comprovam que tampoc s'ha assignat cap nota, ni s'ha actualitzat el camp de dificultat pràctica 
		// de la pregunta, ni el total de contestacions, ni es mostra com a corregida ni existeix data de correcció
		assertNull(rp.getNota());
		assertNull(rp.getPregunta().getDificultatPractica());
		assertEquals(numContestacions, rp.getPregunta().getNumTotalActualitzacionsNotaMitja());
		assertFalse(rp.getCorregida());
		assertNull(rp.getDataCorreccio());
		
		// Finalitzam la contestació d'aquesta RP 
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		OpcioResposta opcioResposta = new OpcioResposta();
		Opcio opcioSeleccionada = rp.getPregunta().getOpcions().get(1); 
		opcioResposta.setOpcio(opcioSeleccionada); // seleccionam la segona opció (i acertam!!)
		opcioResposta.setRespostaPregunta(rp);
		opcionsMarcades.add(opcioResposta);
		rp = respostaPreguntaService.finalitzaContestacio(rp, CNT_TEXT_RR, null, opcionsMarcades);
		
		// Obtenim la pregunta per a poder verificar que és la que hem assignat al principi
		Pregunta p = rp.getPregunta();
		assertEquals(pregunta.getCodi(), p.getCodi());
		assertEquals(pregunta.getEnunciat(), p.getEnunciat());
		assertEquals(pregunta.getTipus(), p.getTipus());
		assertEquals(pregunta.getDataAlta(), p.getDataAlta());
		assertEquals(pregunta.getCreador().getCodi(), p.getCreador().getCodi());
		assertEquals(pregunta.getDificultatTeorica(), p.getDificultatTeorica());
		assertEquals(pregunta.getDificultatPractica(), p.getDificultatPractica());
		assertEquals(pregunta.getEstat(), p.getEstat());
		assertEquals(pregunta.getRaonarResposta(), p.getRaonarResposta());
		assertEquals(pregunta.getNumTotalActualitzacionsNotaMitja(), p.getNumTotalActualitzacionsNotaMitja());
		assertEquals(pregunta.getVofVertader(), p.getVofVertader());
		assertEquals(pregunta.getOpcions().size(), p.getOpcions().size());
		Opcio opcioOriginal = null;
		Opcio opcioTest = null;
		for (int i = 0; i < pregunta.getOpcions().size(); i++) {
			opcioOriginal = pregunta.getOpcions().get(i);
			opcioTest = p.getOpcions().get(i);
			assertEquals(opcioOriginal.getCodi(), opcioTest.getCodi());
			assertEquals(opcioOriginal.getText(), opcioTest.getText());
			assertEquals(opcioOriginal.getCorrecta(), opcioTest.getCorrecta());
		}
		if (pregunta.getQuestionaris() != null) {
			assertEquals(pregunta.getQuestionaris().size(), p.getQuestionaris().size());
		} else {
			assertEquals(pregunta.getQuestionaris(), p.getQuestionaris());
		}
		
		// Comprovam que la pregunta s'ha contestat
		assertNotNull(rp.getDataContestacioFi());
		assertTrue(rp.getContestada());
		assertEquals(CNT_TEXT_RR, rp.getTextRaonarResposta());
		
		// Comprovam que, donat que la pregunta és de tipus VOF, s'ha pogut corregir automàticament i, per tant,
		// té nota, la dificultat pràctica s'ha actualitzat, el num de contestacions també, apareix com a
		// corregida i té assignada una data de correcció
		assertNotNull(rp.getNota());
		assertNotNull(rp.getPregunta().getDificultatPractica());
		assertEquals(numContestacions + 1, rp.getPregunta().getNumTotalActualitzacionsNotaMitja());
		assertTrue(rp.getCorregida());
		assertNotNull(rp.getDataCorreccio());
		
		// Ens asseguram que té vinculada una opcio-resposta
		for (int i = 0; i < rp.getOpcionsMarcades().size(); i++) {
			OpcioResposta or = rp.getOpcionsMarcades().get(i);
			assertNotNull(or.getCodi());
			assertEquals(rp, or.getRespostaPregunta());
			assertEquals(opcioSeleccionada.getCodi(), or.getOpcio().getCodi());
			assertEquals(opcioSeleccionada.getText(), or.getOpcio().getText());
			assertEquals(opcioSeleccionada.getCorrecta(), or.getOpcio().getCorrecta());
		}
	}
	
	@Test
	public void testFinalitzaContestacioPreguntaREC() {
		// Inserim una pregunta REC
		Pregunta preguntaRec = novaPreguntaREC(professor);
		
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne);
		respostaPreguntaService.enviaPregunta(preguntaRec.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// agafam les resposta-preguntes que l'alumne té pendent de contestar
		List<RespostaPregunta> llistaRp = respostaPreguntaService.getPreguntesPerRespondre(alumne);
		
		RespostaPregunta rp = llistaRp.get(0);
		
		// Obtenim el numero de contestacions total que ha tengut la pregunta
		int numContestacions = rp.getPregunta().getNumTotalActualitzacionsNotaMitja();
		
		// Iniciam la contestació d'aquesta RP
		rp = respostaPreguntaService.iniciaContestacio(rp);
		
		// Abans de finalitzar la contestació, ens asseguram que no hi ha definida una data de finalització
		// de la contestació, que el flag contestada està a fals, i que no s'ha inserit cap text de RR ni que
		// s'ha seleccionat cap opció, ni hi ha cap text de resposta (en cas que sigui una pregunta REC)
		assertNull(rp.getDataContestacioFi());
		assertFalse(rp.getContestada());
		assertNull(rp.getTextRaonarResposta());
		assertNull(rp.getTextResposta());
		assertEquals(0, rp.getOpcionsMarcades().size());
		
		// comprovam que tampoc s'ha assignat cap nota, ni s'ha actualitzat el camp de dificultat pràctica 
		// de la pregunta, ni la nota mitja, ni el total de contestacions, ni el temps de resposta mig, ni 
		// es mostra com a corregida ni existeix data de correcció
		assertNull(rp.getNota());
		assertNull(rp.getPregunta().getDificultatPractica());
		assertNull(rp.getPregunta().getNotaMitja());
		assertEquals(numContestacions, rp.getPregunta().getNumTotalActualitzacionsNotaMitja());
		assertNull(rp.getPregunta().getTempsRespostaMig());
		assertFalse(rp.getCorregida());
		assertNull(rp.getDataCorreccio());
		
		// Finalitzam la contestació d'aquesta RP 
		rp = respostaPreguntaService.finalitzaContestacio(rp, CNT_TEXT_RR, CNT_TEXT_RESPOSTA, new ArrayList<OpcioResposta>());
		
		// Obtenim la pregunta per a poder verificar que és la que hem assignat al principi
		Pregunta p = rp.getPregunta();
		assertEquals(preguntaRec.getCodi(), p.getCodi());
		assertEquals(preguntaRec.getEnunciat(), p.getEnunciat());
		assertEquals(preguntaRec.getTipus(), p.getTipus());
		assertEquals(preguntaRec.getDataAlta(), p.getDataAlta());
		assertEquals(preguntaRec.getCreador().getCodi(), p.getCreador().getCodi());
		assertEquals(preguntaRec.getDificultatTeorica(), p.getDificultatTeorica());
		assertEquals(preguntaRec.getDificultatPractica(), p.getDificultatPractica());
		assertEquals(preguntaRec.getEstat(), p.getEstat());
		assertEquals(preguntaRec.getRaonarResposta(), p.getRaonarResposta());
		assertEquals(preguntaRec.getNumTotalActualitzacionsNotaMitja(), p.getNumTotalActualitzacionsNotaMitja());
		assertEquals(preguntaRec.getVofVertader(), p.getVofVertader());
		assertEquals(0, p.getOpcions().size());
		if (preguntaRec.getQuestionaris() != null) {
			assertEquals(preguntaRec.getQuestionaris().size(), p.getQuestionaris().size());
		} else {
			assertEquals(preguntaRec.getQuestionaris(), p.getQuestionaris());
		}
		
		// Comprovam que la pregunta s'ha contestat
		assertNotNull(rp.getDataContestacioFi());
		assertTrue(rp.getContestada());
		assertEquals(CNT_TEXT_RR, rp.getTextRaonarResposta());
		assertEquals(CNT_TEXT_RESPOSTA, rp.getTextResposta());
		assertNotNull(rp.getPregunta().getTempsRespostaMig());
		
		// Comprovam que, donat que la pregunta és de tipus REC, NO s'ha pogut corregir automàticament i, per tant,
		// no té nota, ni s'ha actualitzat la dificultat pràctica, ni la nota mitja, no apareix com a corregida i no 
		// té assignada una data de correcció
		assertNull(rp.getNota());
		assertEquals(numContestacions, rp.getPregunta().getNumTotalActualitzacionsNotaMitja());
		assertNull(rp.getPregunta().getDificultatPractica());
		assertNull(rp.getPregunta().getNotaMitja());
		assertFalse(rp.getCorregida());
		assertNull(rp.getDataCorreccio());
		
		// Ens asseguram que segueix sense tenir vinculada una opcio-resposta
		assertEquals(0, rp.getOpcionsMarcades().size());
	}
	
}