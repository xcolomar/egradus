package org.projecte.egradus.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.projecte.egradus.utilities.UtilTest;

public class RespostaPreguntaTests {
	
	private static final String TEXT_RR_MAX_LENGTH 		  = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore mag";
	private static final Float  NOTA_OK            		  = 8.5f;
	private static final Float  NOTA_BAIXA         		  = -1f;
	private static final Float  NOTA_ALTA                 = 12.2f;
	private static final String TEXT_RESPOSTA_MAX_LENGTH  = TEXT_RR_MAX_LENGTH;
	private static final String TEXT_CORRECCIO_MAX_LENGTH = TEXT_RR_MAX_LENGTH;
	
	private static Validator validador;
	
	private Persona personaPro;
	private Persona personaAlu;
	private Assignatura assignatura;
	private Professor professorAssignador;
	private Alumne alumne;
	private Pregunta pregunta;
	private RespostaPregunta respostaPregunta;
	
	@Before
	public void inicialitza() {
		personaPro = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		personaAlu = UtilTest.inserirPersona("nomalu", "prillinatgealu", "correu@correu.es", "aliesalu", "claualu", new Date());
		assignatura = UtilTest.inserirAssignatura(personaPro, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null, "claualu", new Date());
		professorAssignador = UtilTest.getProfessor(personaPro, assignatura);
		alumne = UtilTest.getAlumne(personaAlu, assignatura);
		pregunta = UtilTest.inserirPregunta(professorAssignador, "enunciat", 1f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_REC, null, Pregunta.ESTAT_PUBLIC);
		respostaPregunta = new RespostaPregunta();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validador = factory.getValidator();
	}
	
	@Test
	public void testRespostaPreguntaPreguntaNotNull() {
		respostaPregunta.setPregunta(null);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaAssignadorNotNull() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(null);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaAlumneNotNull() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(null);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaContestadaNotNull() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(null);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaCorregidaNotNull() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(null);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaRevisadaNotNull() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(null);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaAnonimaNotNull() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(null);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaDataAltaNotNull() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(null);
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaMinimaInfoOk() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaNotaMassaBaixa() {
		respostaPregunta.setNota(NOTA_BAIXA);
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validateProperty(respostaPregunta, "nota");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaNotaMassaAlta() {
		respostaPregunta.setNota(NOTA_ALTA);
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validateProperty(respostaPregunta, "nota");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaTextRaonarRespostaLlarg() {
		respostaPregunta.setTextRaonarResposta(TEXT_RR_MAX_LENGTH + ".");
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validateProperty(respostaPregunta, "textRaonarResposta");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaTextRespostaLlarg() {
		respostaPregunta.setTextResposta(TEXT_RESPOSTA_MAX_LENGTH + ".");
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validateProperty(respostaPregunta, "textResposta");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaTextCorreccioLlarg() {
		respostaPregunta.setTextCorreccio(TEXT_CORRECCIO_MAX_LENGTH + ".");
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validateProperty(respostaPregunta, "textCorreccio");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testRespostaPreguntaCompletOk() {
		respostaPregunta.setPregunta(pregunta);
		respostaPregunta.setAssignador(professorAssignador);
		respostaPregunta.setAlumne(alumne);
		respostaPregunta.setContestada(Boolean.FALSE);
		respostaPregunta.setCorregida(Boolean.FALSE);
		respostaPregunta.setRevisada(Boolean.FALSE);
		respostaPregunta.setAnonima(Boolean.FALSE);
		respostaPregunta.setDataAlta(new Date());
		respostaPregunta.setCorrector(professorAssignador);
		respostaPregunta.setNota(NOTA_OK);
		respostaPregunta.setDataContestacioInici(new Date());
		respostaPregunta.setDataContestacioFi(new Date());
		respostaPregunta.setDataCorreccio(new Date());
		respostaPregunta.setTextRaonarResposta(TEXT_RR_MAX_LENGTH);
		respostaPregunta.setTextResposta(TEXT_RESPOSTA_MAX_LENGTH);
		respostaPregunta.setTextCorreccio(TEXT_CORRECCIO_MAX_LENGTH);
		respostaPregunta.setOpcionsMarcades(null);
		Set<ConstraintViolation<RespostaPregunta>> errors = validador.validate(respostaPregunta);
		assertEquals(0, errors.size());
	}
	
}
