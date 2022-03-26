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
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;
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
public class JPARespostaPreguntaDaoTests {

	@Autowired
	private RespostaPreguntaDao respostaPreguntaDao;

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
	private static String PRE_TIPUS = Pregunta.TIPUS_ES1;
	private static Boolean PRE_VOF_VERTADER = null;
	// per comprovar la correcció manual de preguntes
	private static String PRE_TIPUS_REC = Pregunta.TIPUS_REC;

	// Dades de les Opcions de la Pregunta
	private static String OPC1_TEXT = "Text de la opció 1";
	private static Boolean OPC1_CORRECTA = Boolean.FALSE;
	private static Date OPC1_DATA_ALTA = UtilTest.getDate("11/10/2014");

	private static String OPC2_TEXT = "Text de la opció 2";
	private static Boolean OPC2_CORRECTA = Boolean.TRUE;
	private static Date OPC2_DATA_ALTA = UtilTest.getDate("11/10/2014");

	// Dades pròpies de Resposta-Pregunta
	private static String RP_DATA_INICI_CNT = "10/10/2014";

	private Persona personaProfessor;
	private Persona personaAlumne;
	private Assignatura assignatura;
	private Professor professor;
	private Alumne alumne;
	private Pregunta pregunta;
	private Opcio opcio1;
	private Opcio opcio2;

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

		// Inserim la pregunta
		pregunta = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS, PRE_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(pregunta);

		// Definim les dues opcions que té la pregunta
		opcio1 = UtilTest.inserirOpcio(pregunta, OPC1_TEXT, OPC1_CORRECTA, OPC1_DATA_ALTA);
		opcio2 = UtilTest.inserirOpcio(pregunta, OPC2_TEXT, OPC2_CORRECTA, OPC2_DATA_ALTA);

		// inserim les opcions a BD
		preguntaDao.persistOpcio(opcio1);
		preguntaDao.persistOpcio(opcio2);

		// les dues opcions estan enllaçades amb la pregunta, però a partir de la
		// pregunta no podrem
		// obtenir les opcions si no enllaçam la pregunta a les opcions! Per tant:
		List<Opcio> opcions = new ArrayList<Opcio>();
		opcions.add(opcio1);
		opcions.add(opcio2);
		pregunta.setOpcions(opcions);
	}

	/**
	 * Persist resposta-pregunta
	 */
	@Test
	public void testSetRespostaPregunta() {
		// 'professor' és qui assigna la pregunta
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);
		assertNotNull(rp.getCodi());
	}

	/**
	 * Persist opcio-resposta
	 */
	@Test
	public void testSetOpcioResposta() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// definim una opcio-resposta de la resposta-pregunta 'rp' i de la primera opció
		// que
		// tenia la pregunta 'pregunta'
		OpcioResposta or = UtilTest.inserirOpcioResposta(pregunta.getOpcions().get(0), rp);
		respostaPreguntaDao.persistOpcioResposta(or);

		// comprovam que s'ha inserit a BD
		assertNotNull(or.getCodi());
	}

	@Test
	public void testGetRespostaPreguntaByCodi() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);
		rp = respostaPreguntaDao.getRespostaPreguntaByCodi(rp.getCodi());
		assertEquals(pregunta, rp.getPregunta());
		assertEquals(professor, rp.getAssignador());
		assertEquals(alumne, rp.getAlumne());
	}

	@Test
	public void testGetRespostesPreguntaAlumneBuida() {
		// Inserim un nou alumne a l'assignatura (a qui no se li enviarà la pregunta)
		Persona personaAlumne = UtilTest.inserirPersona(ALU2_NOM, ALU2_PRIMER_LLINATGE, ALU2_CORREU, ALU2_ALIES,
				ALU2_CLAU, UtilTest.getDate(ALU2_DATA_ALTA));
		personaDao.persistPersona(personaAlumne);
		Alumne alumne = UtilTest.getAlumne(personaAlumne, assignatura);
		alumneDao.persistAlumne(alumne);
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.FALSE, Boolean.FALSE,
				Boolean.FALSE);
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostesPreguntaAlumne() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.FALSE, Boolean.FALSE,
				Boolean.FALSE);
		assertEquals(1, llista.size());
	}

	@Test
	public void testGetPreguntesPerCorregirSenseContestarREC() {
		// Inserim una pregunta REC
		Pregunta preguntaREC = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS_REC, PRE_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaREC);

		RespostaPregunta rp = new RespostaPregunta(preguntaREC, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(professor, Boolean.FALSE,
				Boolean.FALSE);

		// Com que la pregunta encara no ha estat contestada, el professor encara no té
		// preguntes
		// pendents de corregir, per molt que el tipus de pregunta sigui REC
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetPreguntesPerCorregirSenseContestarNoREC() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(professor, Boolean.FALSE,
				Boolean.FALSE);

		// Com que la pregunta encara no ha estat contestada, el professor encara no té
		// preguntes
		// pendents de corregir
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetPreguntesPerCorregirContestadesPreguntaREC() {
		// Inserim una pregunta REC
		Pregunta preguntaREC = UtilTest.inserirPregunta(professor, PRE_ENUNCIAT, PRE_DIFICULTAT_TEO, PRE_DIFICULTAT_PRA,
				PRE_RAONAR_RESPOSTA, PRE_DATA_ALTA, PRE_TIPUS_REC, PRE_VOF_VERTADER, Pregunta.ESTAT_PUBLIC);
		preguntaDao.persistPregunta(preguntaREC);

		RespostaPregunta rp = new RespostaPregunta(preguntaREC, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// Contestam la pregunta
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta("raonament resposta");
		rp.setTextResposta("text de la resposta");
		respostaPreguntaDao.updateRespostaPregunta(rp);

		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(professor, Boolean.FALSE,
				Boolean.FALSE);

		// La pregunta REC ha estat contestada, per tant, el professor la rebrà com a
		// pendent per corregir
		assertEquals(1, llista.size());
	}

	@Test
	public void testGetPreguntesPerCorregirContestadesPreguntaNoREC() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// Contestam la pregunta
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta("raonament resposta");
		rp.setTextResposta("text de la resposta");
		respostaPreguntaDao.updateRespostaPregunta(rp);

		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(professor, Boolean.FALSE,
				Boolean.FALSE);

		// La pregunta ha estat contestada, però així i tot, el professor no la rebrà
		// com a pendent per corregir
		// perquè no és de tipus REC
		assertEquals(0, llista.size());
	}

	@Test
	public void testMergeRespostaPreguntaDataContestacioInici() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// ens guardam el codi per la comprovació final
		int codi = rp.getCodi();

		// en aquest punt, l'atribut dataContestacioInici no val res:
		assertNull(rp.getDataContestacioInici());

		// assignam un valor a aquest atribut
		rp.setDataContestacioInici(UtilTest.getDate(RP_DATA_INICI_CNT));
		respostaPreguntaDao.updateRespostaPregunta(rp);

		// llegim la respostaPregunta de BD (per no consultar-la directament de rp, ja
		// que acabam
		// de fer el setDataContestacioInici() explícitament, i lo que volem comprovar,
		// en realitat,
		// és que s'ha inserit correctament a BD:
		List<RespostaPregunta> respostesPregunta = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.FALSE,
				Boolean.FALSE, Boolean.FALSE);
		rp = respostesPregunta.get(0);

		// comprovam que té el valor d'atribut que toca
		assertEquals(UtilTest.getDate(RP_DATA_INICI_CNT), rp.getDataContestacioInici());

		// comprovam, també, que els codis de les resposta-pregunta que estam comparant
		// coincideix
		assertEquals(codi, rp.getCodi(), 0);
	}

	@Test
	public void testGetRespostaPreguntaNoExistent() {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostaPregunta(pregunta, professor, alumne);

		// comprovam que no s'ha trobat cap resposta-pregunta
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostaPreguntaOk() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// ens guardam el codi per la comprovació final
		int codi = rp.getCodi();

		rp = respostaPreguntaDao.getRespostaPregunta(pregunta, professor, alumne).get(0);

		// comprovam que són la mateixa resposta-pregunta
		assertEquals(codi, rp.getCodi(), 0);
	}

	@Test
	public void testGetRespostesPreguntaContestadesAlumneBuida() {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.FALSE, Boolean.FALSE,
				Boolean.TRUE);
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostesPreguntaContestadesAlumne() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// En aquest moment, l'alumne té una pregunta assignada pendent per contestar.
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.FALSE, Boolean.FALSE,
				Boolean.FALSE);
		assertEquals(1, llista.size());

		// Contestam la pregunta
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta("raonament resposta");
		rp.setTextResposta("text de la resposta");
		respostaPreguntaDao.updateRespostaPregunta(rp);

		// Comprovam que l'alumne ja té una pregunta contestada ...
		llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
		assertEquals(1, llista.size());

		// ... i que no la té com a pendent de contestar
		llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
		assertEquals(0, llista.size());
	}

	@Test
	public void testGetRespostesPreguntaByPreguntaIAssignatura() {
		// En aquest moment, cap pregunta ha participat a l'assignatura
		assertEquals(0, respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, null, null, null).size());

		// Enviam una resposta-pregunta
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// En aquest moment, hi ha una pregunta que ha participat a l'assignatura, però
		// no està contestada
		// ni corregida
		assertEquals(1, respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, null, null, null).size());
		assertEquals(1,
				respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.FALSE, null, null).size());
		assertEquals(0,
				respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, null, null).size());
		assertEquals(0, respostaPreguntaDao
				.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.TRUE, null).size());
		assertEquals(0, respostaPreguntaDao
				.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.FALSE, null).size());

		// Contestam la pregunta
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta("raonament resposta");
		rp.setTextResposta("text de la resposta");
		respostaPreguntaDao.updateRespostaPregunta(rp);

		// En aquest moment, la pregunta que ha participat a l'assignatura està
		// contestada però no corregida
		assertEquals(1, respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, null, null, null).size());
		assertEquals(0,
				respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.FALSE, null, null).size());
		assertEquals(1,
				respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, null, null).size());
		assertEquals(0, respostaPreguntaDao
				.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.TRUE, null).size());
		assertEquals(1, respostaPreguntaDao
				.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.FALSE, null).size());

		// Corregim la pregunta
		rp.setDataCorreccio(new Date());
		rp.setCorregida(Boolean.TRUE);
		rp.setTextCorreccio("correcció de la resposta");
		rp.setCorrector(professor);
		rp.setNota(9.5f);
		respostaPreguntaDao.updateRespostaPregunta(rp);

		// En aquest moment, la pregunta que ha participat a l'assignatura està
		// contestada i corregida
		assertEquals(1, respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, null, null, null).size());
		assertEquals(0,
				respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.FALSE, null, null).size());
		assertEquals(1,
				respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, null, null).size());
		assertEquals(1, respostaPreguntaDao
				.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.TRUE, null).size());
		assertEquals(0, respostaPreguntaDao
				.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.FALSE, null).size());
	}

	@Test
	public void testGetOpcionsRespostaCapOpcioMarcada() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// Contestam la pregunta
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta("raonament resposta");
		rp.setTextResposta("text de la resposta");
		// Però no marcam cap opció. Directament, actualitzam la estructura
		respostaPreguntaDao.updateRespostaPregunta(rp);

		// Comprovam que s'ha contestat la pregunta sense marcar cap opció
		assertEquals(0, respostaPreguntaDao.getOpcionsContestades(rp).size());
	}

	@Test
	public void testGetOpcionsRespostaOk() {
		RespostaPregunta rp = new RespostaPregunta(pregunta, professor, alumne);
		respostaPreguntaDao.persistRespostaPregunta(rp);

		// Contestam la pregunta
		rp.setDataContestacioFi(new Date());
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta("raonament resposta");
		rp.setTextResposta("text de la resposta");

		// Afegim una opció-resposta ...
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		OpcioResposta or = UtilTest.inserirOpcioResposta(opcio1, rp);
		opcionsMarcades.add(or);
		respostaPreguntaDao.persistOpcioResposta(or); // ... tant a la BD ...
		rp.setOpcionsMarcades(opcionsMarcades); // ... com al POJO resposta-pregunta

		// Actualitzam la resposta-pregunta
		respostaPreguntaDao.updateRespostaPregunta(rp);

		// Comprovam que s'ha contestat la pregunta marcant una opció de les dues que té
		// la pregunta
		assertEquals(1, respostaPreguntaDao.getOpcionsContestades(rp).size());
	}

}
