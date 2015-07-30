package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class EstadistiquesServiceImplTests {

	@Autowired
	private EstadistiquesService estadistiquesService;
	
	@Autowired
	private RespostaPreguntaService respostaPreguntaService;
	
	@Autowired
	private RespostaQuestionariService respostaQuestionariService;
	
	@Autowired
	private PreguntaService preguntaService;
	
	@Autowired
	private QuestionariService questionariService;
	
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
    
//    private static String ALU3_NOM             = "Xavier";
//    private static String ALU3_PRIMER_LLINATGE = "Colomar";
//    private static String ALU3_CORREU          = "xcolomar@uib.es";
//    private static String ALU3_ALIES           = "xavi";
//    private static String ALU3_CLAU            = "xaviclau";
//    private static String ALU3_DATA_ALTA       = "01/02/2014";
//    
//    private static String ALU4_NOM             = "Elvira";
//    private static String ALU4_PRIMER_LLINATGE = "Sánchez";
//    private static String ALU4_CORREU          = "elvira89@uib.es";
//    private static String ALU4_ALIES           = "elvis";
//    private static String ALU4_CLAU            = "elvisclau";
//    private static String ALU4_DATA_ALTA       = "04/10/2014";
//    
//    private static String ALU5_NOM             = "Albert";
//    private static String ALU5_PRIMER_LLINATGE = "Colomar";
//    private static String ALU5_CORREU          = "acolomar@uib.es";
//    private static String ALU5_ALIES           = "alberco";
//    private static String ALU5_CLAU            = "alberco1";
//    private static String ALU5_DATA_ALTA       = "22/09/2014";
    
    // Dades de l'Assignatura
    // ------------------------------------------------------------------------------------------------------------
    private static String ASS_NOM              = "Intel·ligència Artificial";
	private static String ASS_DESCRIPCIO       = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR   = "clau_inteli_artifi";
	private static String ASS_CLAU_ALUMNE      = "clau_intart";
	private static String ASS_DATA_ALTA        = "01/10/2013";
	private static String ASS_ANY_ACADEMIC     = "2012-13";
	private static String ASS_CODI_REF         = "INTE_ART";
	
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
	
	// Dades de la contestació de la pregunta
	private static String  RP_TEXT_RR           = "Aquest és el raonament de la resposta";
	private static String  RP_TEXT_REC          = "Aquest és el text de resposta de pregunta REC";
	
	// Dades de la correcció de la pregunta REC
	private static String  RP_TEXT_CRR_REC      = "Aquest és el text de la correcció de la pregunta REC";
	private static Float   RP_NOTA_CRR_REC      = 4.6f;
	
	// Dades de la Pregunta REC
	// ------------------------------------------------------------------------------------------------------------
	private static String  PRE_REC_ENUNCIAT        = "Aquest és l'enunciat de la pregunta de Resposta Curta";
	private static Float   PRE_REC_DIFICULTAT_TEO  = 0.97f;
	private static Float   PRE_REC_DIFICULTAT_PRA  = null;
	private static Boolean PRE_REC_RAONAR_RESPOSTA = Boolean.FALSE;
	private static Date    PRE_REC_DATA_ALTA       = new Date();
	private static String  PRE_REC_TIPUS           = Pregunta.TIPUS_REC;
//	private static Boolean PRE_REC_VOF_VERTADER    = null;
	
	private static Float   PRE_REC_PES             = 0.4f;
	private static Float   PRE_REC_NOTA            = 2.25f;
	
	
	private Persona personaProfessor;
	private Persona personaAlumne1;
	private Persona personaAlumne2;
	private Assignatura assignatura;
	private Professor professor;
	private Alumne alumne1;
	private Alumne alumne2;
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
		// Inserim les persones (1 professor i 2 alumnes)
		personaProfessor = UtilTest.inserirPersona(PRO_NOM, PRO_PRIMER_LLINATGE, PRO_CORREU, PRO_ALIES, PRO_CLAU, UtilTest.getDate(PRO_DATA_ALTA));
		personaService.insereixPersona(personaProfessor);
		
		personaAlumne1 = UtilTest.inserirPersona(ALU_NOM, ALU_PRIMER_LLINATGE, ALU_CORREU, ALU_ALIES, ALU_CLAU, UtilTest.getDate(ALU_DATA_ALTA));
		personaAlumne2 = UtilTest.inserirPersona(ALU2_NOM, ALU2_PRIMER_LLINATGE, ALU2_CORREU, ALU2_ALIES, ALU2_CLAU, UtilTest.getDate(ALU2_DATA_ALTA));
		personaService.insereixPersona(personaAlumne1);
		personaService.insereixPersona(personaAlumne2);
		
		// Inserim l'assignatura (i l'associam a la persona 'personaProfessor').
		// NOTA: Aquesta acció també insereix el rol de professor per a la persona 
		// 'personaProfessor' a l'assignatura 'assignatura'. Encara que no haguem
		// d'inserir-lo ...
		assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaService.insereixAssignatura(assignatura);
		
		// ... l'hem d'agafar per a què el POJO professor estigui alimentat amb
		// la informació de BD
    	professor = professorService.getProfessor(personaProfessor, assignatura);
		
		// Inserim el rol d'alumne per a les 2 persones alumne 'personaAlumneX' a l'assignatura 'assignatura'
		alumne1 = UtilTest.getAlumne(personaAlumne1, assignatura);
		alumne2 = UtilTest.getAlumne(personaAlumne2, assignatura);
		alumneService.insereixAlumne(alumne1);
		alumneService.insereixAlumne(alumne2);
		
		// Definim la pregunta ES1
		preguntaEs1 = UtilTest.inserirPregunta(professor, PRE1_ENUNCIAT, PRE1_DIFICULTAT_TEO, PRE1_DIFICULTAT_PRA, PRE1_RAONAR_RESPOSTA, PRE1_DATA_ALTA, PRE1_TIPUS, PRE1_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		
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
		preguntaVof = UtilTest.inserirPregunta(professor, PRE2_ENUNCIAT, PRE2_DIFICULTAT_TEO, PRE2_DIFICULTAT_PRA, PRE2_RAONAR_RESPOSTA, PRE2_DATA_ALTA, PRE2_TIPUS, PRE2_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		
		// Definim les dues opcions que té la pregunta VOF
		opcioVertaderVof = UtilTest.inserirOpcio(preguntaVof, OPC1_PRE2_TEXT, OPC1_PRE2_CORRECTA, OPC1_PRE2_DATA_ALTA);
		opcioFalsVof     = UtilTest.inserirOpcio(preguntaVof, OPC2_PRE2_TEXT, OPC2_PRE2_CORRECTA, OPC2_PRE2_DATA_ALTA);
		
		// afegim les opcions a la pregunta VOF i, finalment, la inserim
		llistaOpcions = new ArrayList<Opcio>();
		llistaOpcions.add(opcioVertaderVof);
		llistaOpcions.add(opcioFalsVof);
		preguntaService.insereixPregunta(preguntaVof, llistaOpcions);
		
		// Definim el qüestionari
		questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, QST_DATA_ALTA);
		
		// afegim les preguntes ES1 i VOF al qüestionari, així com el Map amb els pesos de cada pregunta
		List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
		llistaPreguntes.add(preguntaEs1);
		llistaPreguntes.add(preguntaVof);
		Map<Integer, Float> pesos = new HashMap<Integer, Float>();
		pesos.put(preguntaEs1.getCodi(), QST_PES_ES1);
		pesos.put(preguntaVof.getCodi(), QST_PES_VOF);
		questionariService.insereixQuestionari(questionari, llistaPreguntes, pesos);
		
		// EN AQUEST PUNT, TENIM CINC ALUMNES, UN PROFESSOR I UN QUESTIONARI DE DUES PREGUNTES (UNA ES1 I UNA VOF)
	}
	
	@Test
	public void testGetRespostesPreguntaBuit() {
		List<Map<String, Object>> llistaRp = estadistiquesService.getPreguntesAlumne(alumne1);
		assertNull(llistaRp);
	}
	
	@Test
	public void testGetRespostesPregunta() {
		// enviam la pregunta a l'alumne (És de tipus VOF)
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(preguntaEs1.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		preguntaEs1 = rp.getPregunta();
		
		// mentre l'alumne contesta la pregunta, va marcant opcions:
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaEs1.getOpcions().get(1), rp));
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
		preguntaEs1 = rp.getPregunta();
		
		// L'alumne 1 té una pregunta a les estadístiques
		List<Map<String, Object>> estadistiques = estadistiquesService.getPreguntesAlumne(alumne1);
		assertEquals(1, llistaRp.size());
		
		// L'alumne 2 no
		estadistiques = estadistiquesService.getPreguntesAlumne(alumne2);
		assertNull(estadistiques);
	}
	
	@Test
	public void testGetRespostesPreguntaRec() {
		// Definim la pregunta REC
		preguntaRec = novaPreguntaREC(professor);
		
		// enviam la pregunta REC a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(preguntaRec.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// iniciam la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		preguntaRec = rp.getPregunta();
		
		// finalitzam la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		preguntaRec = rp.getPregunta();
		
		// L'alumne 1 NO té cap pregunta a les estadístiques, ja que la pregunta
		// que ha contestat encara no està corregida
		List<Map<String, Object>> estadistiques = estadistiquesService.getPreguntesAlumne(alumne1);
		assertNull(estadistiques);
	}
	
	@Test
	public void testGetRespostesPreguntaRecCorregida() {
		// Inserim una pregunta REC
		preguntaRec = novaPreguntaREC(professor);
		
		// enviam la pregunta a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		respostaPreguntaService.enviaPregunta(preguntaRec.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// agafam les resposta-preguntes que l'alumne té pendent de contestar
		List<RespostaPregunta> llistaRp = respostaPreguntaService.getPreguntesPerRespondre(alumne1);
		RespostaPregunta rp = llistaRp.get(0);
		
		// Iniciam la contestació d'aquesta RP
		rp = respostaPreguntaService.iniciaContestacio(rp);
		
		// Finalitzam la contestació d'aquesta RP 
		rp = respostaPreguntaService.finalitzaContestacio(rp, RP_TEXT_RR, RP_TEXT_REC, new ArrayList<OpcioResposta>());
		preguntaRec = rp.getPregunta();
		
		// iniciam la correcció de la pregunta
		rp = respostaPreguntaService.iniciarCorreccioPregunta(rp);
		preguntaRec = rp.getPregunta();
		
		// finalitzam la correcció de la pregunta
		rp = respostaPreguntaService.finalitzarCorreccioPregunta(rp, RP_TEXT_CRR_REC, null, RP_NOTA_CRR_REC);
		preguntaRec = rp.getPregunta();
		
		// L'alumne 1 té una pregunta a les estadístiques
		List<Map<String, Object>> estadistiques = estadistiquesService.getPreguntesAlumne(alumne1);
		assertEquals(1, estadistiques.size());
	}
	
	@Test
	public void testGetRespostesQuestionariBuit() {
		List<Map<String, Object>> llistaRp = estadistiquesService.getPreguntesAlumne(alumne1);
		assertNull(llistaRp);
	}
	
	@Test
	public void testGetRespostesQuestionari() {
		// enviam el qüestionari a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);
		
		// iniciam la contestació del qüestionari
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
		questionari = rq.getQuestionari();
		
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
		
		// L'alumne 1 té un qüestionari a les estadístiques
		List<Map<String, Object>> estadistiques = estadistiquesService.getQuestionarisAlumne(alumne1);
		assertEquals(1, estadistiques.size());
		
		// L'alumne 2 no
		estadistiques = estadistiquesService.getQuestionarisAlumne(alumne2);
		assertNull(estadistiques);
	}
	
	@Test
	public void testGetRespostesQuestionariRec() {
		// Generam un questionari que té 3 preguntes: ES1, VOF i REC
		questionariRec = afegirPreguntaRec(PRE_REC_PES);
		
		// enviam el qüestionari a l'alumne
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionariRec.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);
		
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
		
		// Contestam la pregunta REC (que no es pot corregir automàticament)
		rpRec.setTextResposta("resposta a la pregunta REC");
		rpRec.setTextRaonarResposta("text raonar resposta");
		
		// Encapsulam les tres resposta-preguntes en una llista
		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
		llistaRp.add(rpEs1);
		llistaRp.add(rpVof);
		llistaRp.add(rpRec);
		
		// finalitzam la contestació del qüestionari
		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
		questionariRec = rq.getQuestionari();
		
		// L'alumne 1 NO té cap qüestionari a les estadístiques, ja que el qüestionari que
		// ha contestat encara no està corregit
		List<Map<String, Object>> estadistiques = estadistiquesService.getQuestionarisAlumne(alumne1);
		assertNull(estadistiques);
	}
	
	@Test
	public void testGetRespostesQuestionariRecCorregit() {
		// Generam un questionari que té 3 preguntes: ES1, VOF i REC
		questionariRec = afegirPreguntaRec(PRE_REC_PES);
		
		// enviam el qüestionari a l'alumne 1
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionariRec.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaQuestionari rq = llistaRq.get(0);
		
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
		
		// finalitzam la contestació del qüestionari (Passant la llista de resposta-preguntes
		// que acabam de definir)
		respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
		
		// inicialitzam la correcció del qüestionari
		rq = respostaQuestionariService.iniciarCorreccioQuestionari(rq);
		questionariRec = rq.getQuestionari();
		
		// finalitzam la correcció del qüestionari (sense escrire cap text però assignant una nota
		List<String> listText = new ArrayList<String>();
		List<Float> listNota = new ArrayList<Float>();
		listNota.add(PRE_REC_NOTA);
		rq = respostaQuestionariService.finalitzarCorreccioQuestionari(rq, listText, null, listNota);
		questionariRec = rq.getQuestionari();
		
		// L'alumne 1 té un qüestionari a les estadístiques
		List<Map<String, Object>> estadistiques = estadistiquesService.getQuestionarisAlumne(alumne1);
		assertEquals(1, estadistiques.size());
	}
	
	/**
	 * Enviam la pregunta a 2 alumnes però només contesta 1
	 */
	@Test
	public void testEstadistiquesProfessorByPregunta() {
		// enviam la pregunta a l'alumne1 i alumne2
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		alumnes.add(alumne2);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(preguntaEs1.getCodi(), professor, alumnes, Boolean.FALSE);
		RespostaPregunta rp = llistaRp.get(0);
		
		// l'alumne1 inicia la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		preguntaEs1 = rp.getPregunta();
		
		// mentre l'alumne1 contesta la pregunta, va marcant opcions:
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaEs1.getOpcions().get(1), rp));
		
		// l'alumne1 finalitza la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
		preguntaEs1 = rp.getPregunta();
		
		// Obtenim les estadístiques de la preguntaEs1 i verificam cadascun dels seus camps
		Map<String, Object> estadistiques = estadistiquesService.getEstadistiquesProfessorPreguntaAssignacioDirecta(preguntaEs1.getCodi(), assignatura.getCodi());
		assertEquals(alumnes.size(), estadistiques.get(EstadistiquesService.PREGUNTA_ENVIADES_ASS));
		assertEquals(1, estadistiques.get(EstadistiquesService.PREGUNTA_CONTESTADES_ASS));
		assertEquals(1, estadistiques.get(EstadistiquesService.PREGUNTA_CORREGIDES_ASS));
		assertEquals(1, estadistiques.get(EstadistiquesService.PREGUNTA_APROVADES_ASS));
		assertEquals(preguntaEs1.getNotaMitja(), estadistiques.get(EstadistiquesService.PREGUNTA_NOTA_MITJA_ASS));
		assertEquals(new Float(preguntaEs1.getTempsRespostaMig()), estadistiques.get(EstadistiquesService.PREGUNTA_TEMPS_RESPOSTA_MIG_ASS));
	}
	
	/**
	 * Enviam la pregunta a 2 alumnes i la contesten tots 2
	 * però un d'ells no aprova
	 */
	@Test
	public void testEstadistiquesProfessorByPreguntaDosContesten() {
		// enviam la pregunta a l'alumne1 i alumne2
		List<Alumne> alumnes = new ArrayList<Alumne>();
		alumnes.add(alumne1);
		alumnes.add(alumne2);
		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(preguntaEs1.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// Obtenem la resposta-pregunta de l'alumne1
		RespostaPregunta rp = llistaRp.get(0);
		
		// l'alumne1 inicia la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		preguntaEs1 = rp.getPregunta();
		
		// mentre l'alumne1 contesta la pregunta, va marcant opcions:
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaEs1.getOpcions().get(0), rp));
		
		// l'alumne1 finalitza la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
		preguntaEs1 = rp.getPregunta();
		
		// Obtenem la resposta-pregunta de l'alumne2
		rp = llistaRp.get(1);
		
		// l'alumne2 inicia la contestació de la pregunta
		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
		preguntaEs1 = rp.getPregunta();
		
		// mentre l'alumne2 contesta la pregunta, va marcant opcions:
		opcionsMarcades = new ArrayList<OpcioResposta>();
		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaEs1.getOpcions().get(1), rp));
		
		// l'alumne2 finalitza la contestació de la pregunta
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
		preguntaEs1 = rp.getPregunta();
		
		// Enviam la pregunta VOF als alumnes alumne1 i alumne2
		llistaRp = respostaPreguntaService.enviaPregunta(preguntaVof.getCodi(), professor, alumnes, Boolean.FALSE);
		
		// Obtenim les estadístiques de la preguntaEs1 i verificam cadascun dels seus camps
		Map<String, Object> estadistiques = estadistiquesService.getEstadistiquesProfessorPreguntaAssignacioDirecta(preguntaEs1.getCodi(), assignatura.getCodi());
		assertEquals(alumnes.size(), estadistiques.get(EstadistiquesService.PREGUNTA_ENVIADES_ASS));
		assertEquals(2, estadistiques.get(EstadistiquesService.PREGUNTA_CONTESTADES_ASS));
		assertEquals(2, estadistiques.get(EstadistiquesService.PREGUNTA_CORREGIDES_ASS));
		assertEquals(1, estadistiques.get(EstadistiquesService.PREGUNTA_APROVADES_ASS));
		assertEquals(preguntaEs1.getNotaMitja(), estadistiques.get(EstadistiquesService.PREGUNTA_NOTA_MITJA_ASS));
		
		// definim un interval (ja que a cada execució del test, els temps poden canviar i no ser exactament iguals)
		Float tempsRespostaEsperat = (Float) estadistiques.get(EstadistiquesService.PREGUNTA_TEMPS_RESPOSTA_MIG_ASS);
		Float tempsRespostaObtingut = new Float(preguntaEs1.getTempsRespostaMig());
		assertTrue(tempsRespostaEsperat - tempsRespostaObtingut < 1f);
	}
	
//	/**
//	 * Enviam la preguntaEs1 a 2 alumnes i la contesten tots 2
//	 * però un d'ells no aprova.
//	 * 
//	 * Enviam, també, la preguntaVof als dos alumnes d'abans, però
//	 * no la contesten
//	 */
//	@Test
//	public void testEstadistiquesProfessorPreguntaByAssignatura() {
//		// enviam la pregunta a l'alumne1 i alumne2
//		List<Alumne> alumnes = new ArrayList<Alumne>();
//		alumnes.add(alumne1);
//		alumnes.add(alumne2);
//		
//		// -------------------------------------------------------------------------------------------------------
//		List<RespostaPregunta> llistaRp = respostaPreguntaService.enviaPregunta(preguntaEs1.getCodi(), professor, alumnes);
//		
//		// Obtenem la resposta-pregunta de l'alumne1
//		RespostaPregunta rp = llistaRp.get(0);
//		
//		// l'alumne1 inicia la contestació de la preguntaEs1
//		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
//		preguntaEs1 = rp.getPregunta();
//		
//		// mentre l'alumne1 contesta la preguntaEs1, va marcant opcions:
//		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
//		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaEs1.getOpcions().get(0), rp));
//		
//		// l'alumne1 finalitza la contestació de la preguntaEs1
//		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
//		preguntaEs1 = rp.getPregunta();
//		
//		// Obtenem la resposta-pregunta de l'alumne2
//		rp = llistaRp.get(1);
//		
//		// l'alumne2 inicia la contestació de la preguntaEs1
//		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
//		preguntaEs1 = rp.getPregunta();
//		
//		// mentre l'alumne2 contesta la preguntaEs1, va marcant opcions:
//		opcionsMarcades = new ArrayList<OpcioResposta>();
//		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaEs1.getOpcions().get(1), rp));
//		
//		// l'alumne2 finalitza la contestació de la preguntaEs1
//		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
//		preguntaEs1 = rp.getPregunta();
//		
//		// -------------------------------------------------------------------------------------------------------
//		// Enviam la pregunta VOF als alumnes alumne1 i alumne2
//		llistaRp = respostaPreguntaService.enviaPregunta(preguntaVof.getCodi(), professor, alumnes);
//		
//		// Obtenem la resposta-pregunta de l'alumne1
//		rp = llistaRp.get(0);
//		
//		// l'alumne1 inicia la contestació de la preguntaVof
//		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
//		preguntaVof = rp.getPregunta();
//		
//		// mentre l'alumne1 contesta la preguntaVof, va marcant opcions:
//		opcionsMarcades = new ArrayList<OpcioResposta>();
//		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaVof.getOpcions().get(1), rp));
//		
//		// l'alumne1 finalitza la contestació de la preguntaVof
//		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
//		preguntaVof = rp.getPregunta();
//		
//		// Obtenem la resposta-pregunta de l'alumne2
//		rp = llistaRp.get(1);
//		
//		// l'alumne2 inicia la contestació de la preguntaVof
//		rp = respostaPreguntaService.iniciarContestacioPregunta(rp);
//		preguntaVof = rp.getPregunta();
//		
//		// mentre l'alumne2 contesta la preguntaVof, va marcant opcions:
//		opcionsMarcades = new ArrayList<OpcioResposta>();
//		opcionsMarcades.add(UtilTest.inserirOpcioResposta(preguntaVof.getOpcions().get(0), rp));
//		
//		// l'alumne2 finalitza la contestació de la preguntaVof
//		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, RP_TEXT_RR, RP_TEXT_REC, opcionsMarcades);
//		preguntaVof = rp.getPregunta();
//		
//		// -------------------------------------------------------------------------------------------------------
//		
//		// Obtenim les estadístiques de les preguntes que trobem a l'assignatura actual
//		List<Map<String, Object>> estadistiques = estadistiquesService.getEstadistiquesProfessorPreguntes(assignatura.getCodi());
//		
//		// Preparam la nota mitja, el temps de resposta mig i la dificultat pràctica de les preguntes
//		// preguntaEs1 i preguntaVof per evitar possibles valors nulls (i feim que valguin 0) 
//		preguntaEs1.setNotaMitja(preguntaEs1.getNotaMitja() != null ? preguntaEs1.getNotaMitja() : 0f);
//		preguntaVof.setNotaMitja(preguntaVof.getNotaMitja() != null ? preguntaVof.getNotaMitja() : 0f);
//		preguntaEs1.setTempsRespostaMig(preguntaEs1.getTempsRespostaMig() != null ? preguntaEs1.getTempsRespostaMig() : 0L);
//		preguntaVof.setTempsRespostaMig(preguntaVof.getTempsRespostaMig() != null ? preguntaVof.getTempsRespostaMig() : 0L);
//		preguntaEs1.setDificultatPractica(preguntaEs1.getDificultatPractica() != null ? preguntaEs1.getDificultatPractica() : 0f);
//		preguntaVof.setDificultatPractica(preguntaVof.getDificultatPractica() != null ? preguntaVof.getDificultatPractica() : 0f);
//		
//		// Calculam els valors de la nota mitja, del temps de resposta mig i de la dificultat pràctica
//		// que esperam obtenir (les mitjes aritmètiques entre preguntaEs1 i preguntaVof)
//		Float expectedNotaMitja = (preguntaEs1.getNotaMitja() + preguntaVof.getNotaMitja()) / 2;
//		Long expectedTempsRespostaMig = (preguntaEs1.getTempsRespostaMig() + preguntaVof.getTempsRespostaMig()) / 2;
//		Float expectedDificultatTeorica = (preguntaEs1.getDificultatTeorica() + preguntaVof.getDificultatTeorica()) / 2;
//		Float expectedDificultatPractica = (preguntaEs1.getDificultatPractica() + preguntaVof.getDificultatPractica()) / 2;
//		
//		// Verificam que el Map ha duit els valors que esperam
//		assertEquals(((Integer) alumnes.size()).floatValue(), estadistiques.get(EstadistiquesService.ALUMNES_MIG_PREGUNTA_REBUDA));
//		assertEquals(2f, estadistiques.get(EstadistiquesService.ALUMNES_MIG_PREGUNTA_CONTESTADA));
//		assertEquals(2f, estadistiques.get(EstadistiquesService.ALUMNES_MIG_PREGUNTA_CORREGIDA));
//		assertEquals(1f, estadistiques.get(EstadistiquesService.ALUMNES_MIG_PREGUNTA_APROVADA));
//		assertEquals(expectedNotaMitja, estadistiques.get(EstadistiquesService.ASSIGNATURA_NOTA_MITJA));
//		// NOTA: aquest assertTrue pot fallar perquè depèn de lo ràpid o lent que es contesti la pregunta en temps
//		// d'execució del test. (Està marcat a que peti si la diferència és major a 2 segons)
//		assertTrue(((new Float(expectedTempsRespostaMig)) - ((Float) estadistiques.get(EstadistiquesService.ASSIGNATURA_TEMPS_RESPOSTA_MIG))) < 2f);
//		assertEquals(expectedDificultatTeorica, estadistiques.get(EstadistiquesService.ASSIGNATURA_DIFICULTAT_TEORICA));
//		assertEquals(expectedDificultatPractica, estadistiques.get(EstadistiquesService.ASSIGNATURA_DIFICULTAT_PRACTICA));
//	}
	
//	/**
//	 * Enviam el qüestionari a 2 alumnes però només contesta 1
//	 */
//	@Test
//	public void testEstadistiquesProfessorByQuestionari() {
//		// enviam el qüestionari a l'alumne1 i alumne2
//		List<Alumne> alumnes = new ArrayList<Alumne>();
//		alumnes.add(alumne1);
//		alumnes.add(alumne2);
//		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(), professor, alumnes);
//		RespostaQuestionari rq = llistaRq.get(0);
//		
//		// l'alumne1 inicia la contestació del qüestionari
//		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
//		questionari = rq.getQuestionari();
//		
//		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();
//		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
//		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);
//		
//		// Contestam les pregunta ES1
//		Pregunta pEs1 = rpEs1.getPregunta();
//		OpcioResposta orPEs1 = new OpcioResposta();
//		orPEs1.setRespostaPregunta(rpEs1);
//		orPEs1.setOpcio(pEs1.getOpcions().get(2)); // seleccionam la 3º opció de la pregunta ES1
//		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
//		llistaOrPEs1.add(orPEs1);
//		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
//		rpEs1.setOpcionsMarcades(llistaOrPEs1);
//		
//		// Contestam la pregunta VOF
//		Pregunta pVof = rq.getQuestionari().getPreguntes().get(1).getPregunta();
//		OpcioResposta orPVof = new OpcioResposta();
//		orPVof.setRespostaPregunta(rq.getRespostaPreguntes().get(1));
//		orPVof.setOpcio(pVof.getOpcions().get(0)); // seleccionam la 1º opció de la pregunta VOF
//		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
//		llistaOrPVof.add(orPVof);
//		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
//		rpVof.setOpcionsMarcades(llistaOrPVof);
//		
//		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
//		llistaRp.add(rpEs1);
//		llistaRp.add(rpVof);
//		
//		// l'alumne1 finalitza la contestació del qüestionari
//		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
//		questionari = rq.getQuestionari();
//		
//		// Obtenim les estadístiques del qüestionari i verificam cadascun dels seus camps
//		Map<String, Object> estadistiques = estadistiquesService.getEstadistiquesProfessorByQuestionari(questionari.getCodi(), assignatura.getCodi());
//		assertEquals(alumnes.size(), estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_REBUT));
//		assertEquals(1, estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_CONTESTAT));
//		assertEquals(1, estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_CORREGIT));
//		assertEquals(0, estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_APROVAT));
//		assertEquals(questionari.getNotaMitja(), estadistiques.get(EstadistiquesService.QUESTIONARI_NOTA_MITJA));
//		assertEquals(questionari.getTempsRespostaMig(), estadistiques.get(EstadistiquesService.QUESTIONARI_TEMPS_RESPOSTA_MIG));
//		assertEquals(questionari.getDificultatTeorica(), estadistiques.get(EstadistiquesService.QUESTIONARI_DIFICULTAT_TEORICA));
//		assertEquals(questionari.getDificultatPractica(), estadistiques.get(EstadistiquesService.QUESTIONARI_DIFICULTAT_PRACTICA));
//	}
	
//	/**
//	 * Enviam el qüestionari a 2 alumnes i el contesten tots 2.
//	 * Un d'ells l'aprova
//	 */
//	@Test
//	public void testEstadistiquesProfessorByQuestionariDosContesten() {
//		// enviam el qúestionari a l'alumne1 i alumne2
//		List<Alumne> alumnes = new ArrayList<Alumne>();
//		alumnes.add(alumne1);
//		alumnes.add(alumne2);
//		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(), professor, alumnes);
//		
//		// Obtenem la resposta-questionari de l'alumne1
//		RespostaQuestionari rq = llistaRq.get(0);
//		
//		// l'alumne1 inicia la contestació del qüestionari
//		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
//		questionari = rq.getQuestionari();
//		
//		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();
//		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
//		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);
//		
//		// L'alumne 1 contesta la pregunta ES1
//		Pregunta pEs1 = rpEs1.getPregunta();
//		OpcioResposta orPEs1 = new OpcioResposta();
//		orPEs1.setRespostaPregunta(rpEs1);
//		orPEs1.setOpcio(pEs1.getOpcions().get(2)); // seleccionam la 3º opció de la pregunta ES1
//		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
//		llistaOrPEs1.add(orPEs1);
//		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
//		rpEs1.setOpcionsMarcades(llistaOrPEs1);
//		
//		// L'alumne 1 contesta la pregunta VOF
//		Pregunta pVof = rq.getQuestionari().getPreguntes().get(1).getPregunta();
//		OpcioResposta orPVof = new OpcioResposta();
//		orPVof.setRespostaPregunta(rq.getRespostaPreguntes().get(1));
//		orPVof.setOpcio(pVof.getOpcions().get(0)); // seleccionam la 1º opció de la pregunta VOF
//		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
//		llistaOrPVof.add(orPVof);
//		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
//		rpVof.setOpcionsMarcades(llistaOrPVof);
//		
//		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
//		llistaRp.add(rpEs1);
//		llistaRp.add(rpVof);
//		
//		// l'alumne1 finalitza la contestació del qüestionari
//		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
//		questionari = rq.getQuestionari();
//		
//		// Obtenem la resposta-pregunta de l'alumne2
//		rq = llistaRq.get(1);
//		
//		// l'alumne2 inicia la contestació de la pregunta
//		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
//		questionari = rq.getQuestionari();
//		
//		llistaRespostesPregunta = rq.getRespostaPreguntes();
//		rpEs1 = llistaRespostesPregunta.get(0);
//		rpVof = llistaRespostesPregunta.get(1);
//		
//		// L'alumne 2 contesta la pregunta ES1
//		pEs1 = rpEs1.getPregunta();
//		orPEs1 = new OpcioResposta();
//		orPEs1.setRespostaPregunta(rpEs1);
//		orPEs1.setOpcio(pEs1.getOpcions().get(1)); // seleccionam la 2º opció de la pregunta ES1
//		llistaOrPEs1 = new ArrayList<OpcioResposta>();
//		llistaOrPEs1.add(orPEs1);
//		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
//		rpEs1.setOpcionsMarcades(llistaOrPEs1);
//		
//		// L'alumne 1 contesta la pregunta VOF
//		pVof = rq.getQuestionari().getPreguntes().get(1).getPregunta();
//		orPVof = new OpcioResposta();
//		orPVof.setRespostaPregunta(rq.getRespostaPreguntes().get(1));
//		orPVof.setOpcio(pVof.getOpcions().get(0)); // seleccionam la 1º opció de la pregunta VOF
//		llistaOrPVof = new ArrayList<OpcioResposta>();
//		llistaOrPVof.add(orPVof);
//		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
//		rpVof.setOpcionsMarcades(llistaOrPVof);
//		
//		llistaRp = new ArrayList<RespostaPregunta>();
//		llistaRp.add(rpEs1);
//		llistaRp.add(rpVof);
//		
//		// l'alumne2 finalitza la contestació del qüestionari
//		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
//		questionari = rq.getQuestionari();
//		
//		// Obtenim les estadístiques del qüestionari i verificam cadascun dels seus camps
//		Map<String, Object> estadistiques = estadistiquesService.getEstadistiquesProfessorByQuestionari(questionari.getCodi(), assignatura.getCodi());
//		assertEquals(alumnes.size(), estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_REBUT));
//		assertEquals(2, estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_CONTESTAT));
//		assertEquals(2, estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_CORREGIT));
//		assertEquals(1, estadistiques.get(EstadistiquesService.ALUMNES_QUESTIONARI_APROVAT));
//		assertEquals(questionari.getNotaMitja(), estadistiques.get(EstadistiquesService.QUESTIONARI_NOTA_MITJA));
//		assertEquals(questionari.getTempsRespostaMig(), estadistiques.get(EstadistiquesService.QUESTIONARI_TEMPS_RESPOSTA_MIG));
//		assertEquals(questionari.getDificultatTeorica(), estadistiques.get(EstadistiquesService.QUESTIONARI_DIFICULTAT_TEORICA));
//		assertEquals(questionari.getDificultatPractica(), estadistiques.get(EstadistiquesService.QUESTIONARI_DIFICULTAT_PRACTICA));
//	}
	
//	/**
//	 * Enviam el qüestionari a 2 alumnes i la contesten tots 2
//	 * però un d'ells no aprova. Treim les estadístiques de l'assignatura
//	 */
//	@Test
//	public void testEstadistiquesProfessorQuestionariByAssignatura() {
//		// enviam el qúestionari a l'alumne1 i alumne2
//		List<Alumne> alumnes = new ArrayList<Alumne>();
//		alumnes.add(alumne1);
//		alumnes.add(alumne2);
//		List<RespostaQuestionari> llistaRq = respostaQuestionariService.enviaQuestionari(questionari.getCodi(), professor, alumnes);
//		
//		// Obtenem la resposta-questionari de l'alumne1
//		RespostaQuestionari rq = llistaRq.get(0);
//		
//		// l'alumne1 inicia la contestació del qüestionari
//		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
//		questionari = rq.getQuestionari();
//		
//		List<RespostaPregunta> llistaRespostesPregunta = rq.getRespostaPreguntes();
//		RespostaPregunta rpEs1 = llistaRespostesPregunta.get(0);
//		RespostaPregunta rpVof = llistaRespostesPregunta.get(1);
//		
//		// L'alumne 1 contesta la pregunta ES1
//		Pregunta pEs1 = rpEs1.getPregunta();
//		OpcioResposta orPEs1 = new OpcioResposta();
//		orPEs1.setRespostaPregunta(rpEs1);
//		orPEs1.setOpcio(pEs1.getOpcions().get(2)); // seleccionam la 3º opció de la pregunta ES1
//		List<OpcioResposta> llistaOrPEs1 = new ArrayList<OpcioResposta>();
//		llistaOrPEs1.add(orPEs1);
//		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
//		rpEs1.setOpcionsMarcades(llistaOrPEs1);
//		
//		// L'alumne 1 contesta la pregunta VOF
//		Pregunta pVof = rq.getQuestionari().getPreguntes().get(1).getPregunta();
//		OpcioResposta orPVof = new OpcioResposta();
//		orPVof.setRespostaPregunta(rq.getRespostaPreguntes().get(1));
//		orPVof.setOpcio(pVof.getOpcions().get(0)); // seleccionam la 1º opció de la pregunta VOF
//		List<OpcioResposta> llistaOrPVof = new ArrayList<OpcioResposta>();
//		llistaOrPVof.add(orPVof);
//		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
//		rpVof.setOpcionsMarcades(llistaOrPVof);
//		
//		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
//		llistaRp.add(rpEs1);
//		llistaRp.add(rpVof);
//		
//		// l'alumne1 finalitza la contestació del qüestionari
//		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
//		questionari = rq.getQuestionari();
//		
//		// Obtenem la resposta-pregunta de l'alumne2
//		rq = llistaRq.get(1);
//		
//		// l'alumne2 inicia la contestació de la pregunta
//		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
//		questionari = rq.getQuestionari();
//		
//		llistaRespostesPregunta = rq.getRespostaPreguntes();
//		rpEs1 = llistaRespostesPregunta.get(0);
//		rpVof = llistaRespostesPregunta.get(1);
//		
//		// L'alumne 2 contesta la pregunta ES1
//		pEs1 = rpEs1.getPregunta();
//		orPEs1 = new OpcioResposta();
//		orPEs1.setRespostaPregunta(rpEs1);
//		orPEs1.setOpcio(pEs1.getOpcions().get(1)); // seleccionam la 2º opció de la pregunta ES1
//		llistaOrPEs1 = new ArrayList<OpcioResposta>();
//		llistaOrPEs1.add(orPEs1);
//		rpEs1.setTextRaonarResposta("Raonament de la resposta a la pregunta Es1");
//		rpEs1.setOpcionsMarcades(llistaOrPEs1);
//		
//		// L'alumne 1 contesta la pregunta VOF
//		pVof = rq.getQuestionari().getPreguntes().get(1).getPregunta();
//		orPVof = new OpcioResposta();
//		orPVof.setRespostaPregunta(rq.getRespostaPreguntes().get(1));
//		orPVof.setOpcio(pVof.getOpcions().get(0)); // seleccionam la 1º opció de la pregunta VOF
//		llistaOrPVof = new ArrayList<OpcioResposta>();
//		llistaOrPVof.add(orPVof);
//		rpVof.setTextRaonarResposta("Raonament de la resposta a la pregunta Vof");
//		rpVof.setOpcionsMarcades(llistaOrPVof);
//		
//		llistaRp = new ArrayList<RespostaPregunta>();
//		llistaRp.add(rpEs1);
//		llistaRp.add(rpVof);
//		
//		// l'alumne2 finalitza la contestació del qüestionari
//		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRp);
//		questionari = rq.getQuestionari();
//		
//		// Obtenim les estadístiques dels qüestionaris que trobem a l'assignatura actual
//		Map<String, Object> estadistiques = estadistiquesService.getEstadistiquesProfessorQuestionarisByAssignatura(assignatura.getCodi());
//		
//		// Preparam la nota mitja, el temps de resposta mig i la dificultat pràctica de les preguntes
//		// preguntaEs1 i preguntaVof per evitar possibles valors nulls (i feim que valguin 0) 
//		questionari.setNotaMitja(questionari.getNotaMitja() != null ? questionari.getNotaMitja() : 0f);
//		questionari.setTempsRespostaMig(questionari.getTempsRespostaMig() != null ? questionari.getTempsRespostaMig() : 0L);
//		questionari.setDificultatPractica(questionari.getDificultatPractica() != null ? questionari.getDificultatPractica() : 0f);
//		
//		// Verificam que el Map ha duit els valors que esperam
//		assertEquals(((Integer) alumnes.size()).floatValue(), estadistiques.get(EstadistiquesService.ALUMNES_MIG_QUESTIONARI_REBUT));
//		assertEquals(2f, estadistiques.get(EstadistiquesService.ALUMNES_MIG_QUESTIONARI_CONTESTAT));
//		assertEquals(2f, estadistiques.get(EstadistiquesService.ALUMNES_MIG_QUESTIONARI_CORREGIT));
//		assertEquals(1f, estadistiques.get(EstadistiquesService.ALUMNES_MIG_QUESTIONARI_APROVAT));
//		assertEquals(questionari.getNotaMitja(), estadistiques.get(EstadistiquesService.ASSIGNATURA_NOTA_MITJA));
//		// NOTA: aquest assertTrue pot fallar perquè depèn de lo ràpid o lent que es contesti la pregunta en temps
//		// d'execució del test. (Està marcat a que peti si la diferència és major a 2 segons)
//		assertTrue(((new Float(questionari.getTempsRespostaMig())) - ((Float) estadistiques.get(EstadistiquesService.ASSIGNATURA_TEMPS_RESPOSTA_MIG))) < 2f);
//		assertEquals(questionari.getDificultatTeorica(), estadistiques.get(EstadistiquesService.ASSIGNATURA_DIFICULTAT_TEORICA));
//		assertEquals(questionari.getDificultatPractica(), estadistiques.get(EstadistiquesService.ASSIGNATURA_DIFICULTAT_PRACTICA));
//	}
	
//	/**
//	 * Estadístiques que veuen els professors sobre el conjunt 
//	 * d'alumnes de la seva assignatura respecte a les preguntes
//	 * 
//	 * TODO: falta definir (quan estiguin els tests millor organitzats)
//	 */
//	@Test
//	public void testGetEstadistiquesProfessorByAssignatura() {
//		estadistiquesService.getEstadistiquesProfessorPreguntes(assignatura.getCodi());
//		assertNull(null);
//	}
	
	/**
	 * Mètode privat que retorna una Pregunta REC creada pel professor passat per
	 * paràmetre
	 * @param professor
	 * @return
	 */
	private Pregunta novaPreguntaREC(Professor professor) {
		Pregunta pregunta = UtilTest.inserirPregunta(professor, PRE_REC_ENUNCIAT, PRE_REC_DIFICULTAT_TEO, PRE_REC_DIFICULTAT_PRA, PRE_REC_RAONAR_RESPOSTA, PRE_REC_DATA_ALTA, PRE_REC_TIPUS, null, Pregunta.ESTAT_PUBLIC);
		preguntaService.insereixPregunta(pregunta, new ArrayList<Opcio>());
		return pregunta;
	}
	
	/**
	 * mètode privat que crea un nou qüestionari amb les dues preguntes per defecte
	 * ES1 i VOF afegint-li una tercera pregunta REC.
	 * 
	 * En aquest cas, no s'indica pès per a les dues primeres preguntes. Només s'assigna
	 * el pès de la pregunta REC
	 * 
	 * @param pesRec
	 * @return questionariRec
	 */
	private Questionari afegirPreguntaRec(Float pesRec) {
		// Definim la pregunta REC
		preguntaRec = novaPreguntaREC(professor);
		
		// Definim el qüestionari
		Questionari q = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, QST_DATA_ALTA);
		
		// afegim les preguntes ES1 i VOF al qüestionari, així com el Map amb els pesos de cada pregunta
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
