package org.projecte.egradus.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.projecte.egradus.utilities.UtilTest;

public class RespostaQuestionariTests {
	
	private static final Float  NOTA_OK       = 8.5f;
	private static final Float  NOTA_BAIXA    = -1f;
	private static final Float  NOTA_ALTA     = 12.2f;
	
	private static Validator validador;
	
	private Persona personaPro;
	private Persona personaAlu;
	private Assignatura assignatura;
	private Professor professorAssignador;
	private Alumne alumne;
	private Questionari questionari;
	private RespostaQuestionari respostaQuestionari;
	
	@Before
	public void inicialitza() {
		personaPro = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		personaAlu = UtilTest.inserirPersona("nomalu", "prillinatgealu", "correu@correu.es", "aliesalu", "claualu", new Date());
		assignatura = UtilTest.inserirAssignatura(personaPro, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null, "claualu", new Date());
		professorAssignador = UtilTest.getProfessor(personaPro, assignatura);
		alumne = UtilTest.getAlumne(personaAlu, assignatura);
		questionari = UtilTest.inserirQuestionari(professorAssignador, "nom qüestionari", "descripció", 1f, null, Questionari.ESTAT_PUBLIC, new Date());
		respostaQuestionari = new RespostaQuestionari();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validador = factory.getValidator();
	}
	
	@Test
	public void testRespostaQuestionariQuestionariNotNull() {
		respostaQuestionari.setQuestionari(null);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariAssignadorNotNull() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(null);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariAlumneNotNull() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(null);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariContestatNotNull() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(null);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariCorregitNotNull() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(null);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariRevisatNotNull() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(null);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariAnonimNotNull() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(null);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariDataAltaNotNull() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(null);
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariMinimaInfoOk() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariNotaMassaBaixa() {
		respostaQuestionari.setNota(NOTA_BAIXA);
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validateProperty(respostaQuestionari, "nota");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariNotaMassaAlta() {
		respostaQuestionari.setNota(NOTA_ALTA);
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validateProperty(respostaQuestionari, "nota");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaQuestionariCompletOk() {
		respostaQuestionari.setQuestionari(questionari);
		respostaQuestionari.setAssignador(professorAssignador);
		respostaQuestionari.setAlumne(alumne);
		respostaQuestionari.setContestat(Boolean.FALSE);
		respostaQuestionari.setCorregit(Boolean.FALSE);
		respostaQuestionari.setRevisat(Boolean.FALSE);
		respostaQuestionari.setAnonim(Boolean.FALSE);
		respostaQuestionari.setDataAlta(new Date());
		respostaQuestionari.setCorrector(professorAssignador);
		respostaQuestionari.setNota(NOTA_OK);
		respostaQuestionari.setDataContestacioInici(new Date());
		respostaQuestionari.setDataContestacioFi(new Date());
		respostaQuestionari.setDataCorreccio(new Date());
		respostaQuestionari.setRespostaPreguntes(null);
		Set<ConstraintViolation<RespostaQuestionari>> errors = validador.validate(respostaQuestionari);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testGetPesByPreguntaNull() {
		assertNull(respostaQuestionari.getPesByPregunta(null));
	}
	
	@Test
	public void testGetPesByPreguntaSenseLlistaPreguntes() {
		respostaQuestionari.setQuestionari(questionari);
		Pregunta pregunta = UtilTest.inserirPregunta(professorAssignador, "enunciat", 0.7f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_VOF, Boolean.TRUE, Pregunta.ESTAT_PUBLIC);
		assertNull(respostaQuestionari.getPesByPregunta(pregunta));
	}
	
	@Test
	public void testGetPesByPreguntaLlistaPreguntesBuida() {
		questionari.setPreguntes(new ArrayList<PreguntaQuestionari>());
		respostaQuestionari.setQuestionari(questionari);
		Pregunta pregunta = UtilTest.inserirPregunta(professorAssignador, "enunciat", 0.7f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_VOF, Boolean.TRUE, Pregunta.ESTAT_PUBLIC);
		assertNull(respostaQuestionari.getPesByPregunta(pregunta));
	}
	
	@Test
	public void testGetPesByPreguntaLlistaPreguntes() {
		// definim un qüestionari amb dues preguntes (VOF i ES1)
		Pregunta preguntaVof = UtilTest.inserirPregunta(professorAssignador, "enunciatVof", 0.7f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_VOF, Boolean.TRUE, Pregunta.ESTAT_PUBLIC);
		Pregunta preguntaEs1 = UtilTest.inserirPregunta(professorAssignador, "enunciatEs1", 0.643f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_ES1, null, Pregunta.ESTAT_PUBLIC);
		
		// donat el mètode d'Equals entre preguntes actúa sobre el codi, i el mètode
		// getPesByPregunta() emplea l'Equals, hem d'inserir un codi explícitament.
		// En un entorn fora de tests de Domini, no farà falta, ja que actuarem sempre
		// amb preguntes inserides a BD i, per tant, amb un codi inserit prèviament
		preguntaVof.setCodi(100);
		preguntaEs1.setCodi(200);
		
		PreguntaQuestionari pqVof = new PreguntaQuestionari();
		pqVof.setPregunta(preguntaVof);
		pqVof.setQuestionari(questionari);
		pqVof.setPes(0.65f);
		
		PreguntaQuestionari pqEs1 = new PreguntaQuestionari();
		pqEs1.setPregunta(preguntaEs1);
		pqEs1.setQuestionari(questionari);
		pqEs1.setPes(0.35f);
		
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ.add(pqEs1);
		llistaPQ.add(pqVof);
		questionari.setPreguntes(llistaPQ);
		
		// assignam el qüestionari a la resposta-questionari
		respostaQuestionari.setQuestionari(questionari);
		
		// comprovam que, si cercam el pès de la pregunta VOF ens torna el pès que hem definit per ella
		assertEquals(pqVof.getPes(), respostaQuestionari.getPesByPregunta(preguntaVof));
		
		// Igualment per la pregunta ES1
		assertEquals(pqEs1.getPes(), respostaQuestionari.getPesByPregunta(preguntaEs1));
	}
	
	@Test
	public void testGetPesByPreguntaLlistaPreguntesPreguntaNull() {
		// definim un qüestionari amb dues preguntes (VOF i ES1)
		Pregunta preguntaVof = UtilTest.inserirPregunta(professorAssignador, "enunciatVof", 0.7f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_VOF, Boolean.TRUE, Pregunta.ESTAT_PUBLIC);
		Pregunta preguntaEs1 = UtilTest.inserirPregunta(professorAssignador, "enunciatEs1", 0.643f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_ES1, null, Pregunta.ESTAT_PUBLIC);
		
		// definim una pregunta extra, amb la qual cercarem (ESN)
		Pregunta preguntaEsN = UtilTest.inserirPregunta(professorAssignador, "enunciatEsN", 0.29f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_ESN, null, Pregunta.ESTAT_PUBLIC);
		
		// donat el mètode d'Equals entre preguntes actúa sobre el codi, i el mètode
		// getPesByPregunta() emplea l'Equals, hem d'inserir un codi explícitament.
		// En un entorn fora de tests de Domini, no farà falta, ja que actuarem sempre
		// amb preguntes inserides a BD i, per tant, amb un codi inserit prèviament
		preguntaVof.setCodi(100);
		preguntaEs1.setCodi(200);
		preguntaEsN.setCodi(300);
		
		PreguntaQuestionari pqVof = new PreguntaQuestionari();
		pqVof.setPregunta(preguntaVof);
		pqVof.setQuestionari(questionari);
		pqVof.setPes(0.65f);
		
		PreguntaQuestionari pqEs1 = new PreguntaQuestionari();
		pqEs1.setPregunta(preguntaEs1);
		pqEs1.setQuestionari(questionari);
		pqEs1.setPes(0.35f);
		
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ.add(pqEs1);
		llistaPQ.add(pqVof);
		questionari.setPreguntes(llistaPQ);
		
		// assignam el qüestionari a la resposta-questionari
		respostaQuestionari.setQuestionari(questionari);
		
		// comprovam que, si cercam el pès de la pregunta ESN obtindrem NULL, ja que no l'haurà
		// trobada dins el qüestionari
		assertNull(respostaQuestionari.getPesByPregunta(preguntaEsN));
	}
}
