package org.projecte.egradus.domain;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.projecte.egradus.utilities.UtilTest;

public class PreguntaTests {

	private static Validator validador;

	private static String ENUNCIAT_MAX_LENGTH = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore mag";

	private static Float PRE_NOTA1 = 8f;
	private static Float PRE_NOTA2 = 5.5f;
	private static String DATA_CNT_INICI1 = "2015-01-18 15:08:10";
	private static String DATA_CNT_FI1 = "2015-01-18 15:11:38";
	private static String DATA_CNT_INICI2 = "2015-01-18 15:08:10";
	private static String DATA_CNT_FI2 = "2015-01-18 15:11:38";
	private static String FORMAT_DATA = "yyyy-MM-dd HH:mm:ss";

	private Persona persona;
	private Assignatura assignatura;
	private Professor professor;
	private Pregunta pregunta;

	@Before
	public void inicialitza() {
		persona = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		assignatura = UtilTest.inserirAssignatura(persona, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null,
				"claualu", new Date());
		professor = UtilTest.getProfessor(persona, assignatura);
		pregunta = new Pregunta();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validador = factory.getValidator();
	}

	@Test
	public void testPreguntaEnunciatNotEmpty() {
		pregunta.setEnunciat("");
		pregunta.setRaonarResposta(Boolean.TRUE);
		pregunta.setTipus(Pregunta.TIPUS_ESN);
		pregunta.setCreador(professor);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaRaonarRespostaNotNull() {
		pregunta.setEnunciat("enunciat");
		pregunta.setRaonarResposta(null);
		pregunta.setTipus(Pregunta.TIPUS_ESN);
		pregunta.setCreador(professor);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaCreadorNotNull() {
		pregunta.setEnunciat("enunciat");
		pregunta.setRaonarResposta(Boolean.FALSE);
		pregunta.setTipus(Pregunta.TIPUS_ESN);
		pregunta.setCreador(null);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaTipusNotEmpty() {
		pregunta.setEnunciat("enunciat");
		pregunta.setRaonarResposta(Boolean.FALSE);
		pregunta.setTipus("");
		pregunta.setCreador(professor);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);

		// obtendrem 2 errors: un per l'anotació NotEmpty i l'altre per el Pattern
		// que l'obliga a seguir el patró "ES1|ESN|VOF|REC"
		assertEquals(2, errors.size());
	}

	@Test
	public void testPreguntaEstatNotEmpty() {
		pregunta.setEnunciat("enunciat");
		pregunta.setRaonarResposta(Boolean.FALSE);
		pregunta.setTipus(Pregunta.TIPUS_ESN);
		pregunta.setCreador(professor);
		pregunta.setEstat("");
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);

		// obtendrem 2 errors: un per l'anotació NotEmpty i l'altre per el Pattern
		// que l'obliga a seguir el patró "editable|public"
		assertEquals(2, errors.size());
	}

	@Test
	public void testPreguntaOk() {
		pregunta.setEnunciat("enunciat");
		pregunta.setRaonarResposta(Boolean.FALSE);
		pregunta.setTipus(Pregunta.TIPUS_ESN);
		pregunta.setCreador(professor);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);
		assertEquals(0, errors.size());
	}

	@Test
	public void testPreguntaTotsElsParametresOk() {
		pregunta.setEnunciat("enunciat");
		pregunta.setDificultatTeorica(0.8f);
		pregunta.setDificultatPractica(0.34f);
		pregunta.setNumTotalActualitzacionsNotaMitja(25);
		pregunta.setNumTotalActualitzacionsTRM(25);
		pregunta.setRaonarResposta(Boolean.FALSE);
		pregunta.setNotaMitja(4f);
		pregunta.setTempsRespostaMig(45L);
		pregunta.setDataAlta(new Date());
		pregunta.setTipus(Pregunta.TIPUS_VOF);
		pregunta.setVofVertader(Boolean.FALSE);
		pregunta.setCreador(professor);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		pregunta.setOpcions(new ArrayList<Opcio>());
		pregunta.setQuestionaris(new ArrayList<PreguntaQuestionari>());
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);
		assertEquals(0, errors.size());
	}

	@Test
	public void testPreguntaDificultatPracticaMax() {
		Pregunta p = new Pregunta();
		p.setDificultatPractica(2f);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "dificultatPractica");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaDificultatTeoricaMax() {
		Pregunta p = new Pregunta();
		p.setDificultatTeorica(2f);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "dificultatTeorica");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaDificultatPracticaMin() {
		Pregunta p = new Pregunta();
		p.setDificultatPractica(-2f);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "dificultatPractica");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaDificultatTeoricaMin() {
		Pregunta p = new Pregunta();
		p.setDificultatTeorica(-1f);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "dificultatTeorica");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaNumTotalActualitzacionsNotaMitjaMin() {
		Pregunta p = new Pregunta();
		p.setNumTotalActualitzacionsNotaMitja(-1);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "numTotalActualitzacionsNotaMitja");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaNumTotalActualitzacionsTRMMin() {
		Pregunta p = new Pregunta();
		p.setNumTotalActualitzacionsTRM(-1);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "numTotalActualitzacionsTRM");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaNotaMitjaMin() {
		Pregunta p = new Pregunta();
		p.setNotaMitja(-1f);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "notaMitja");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaNotaMitjaMax() {
		Pregunta p = new Pregunta();
		p.setNotaMitja(11f);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "notaMitja");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaNotaMitjaOk() {
		Pregunta p = new Pregunta();
		p.setNotaMitja(6.3f);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "notaMitja");
		assertEquals(0, errors.size());
	}

	@Test
	public void testPreguntaTempsRespostaMigMin() {
		Pregunta p = new Pregunta();
		p.setTempsRespostaMig(-1L);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "tempsRespostaMig");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaTempsRespostaMigOk() {
		Pregunta p = new Pregunta();
		p.setTempsRespostaMig(154L);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "tempsRespostaMig");
		assertEquals(0, errors.size());
	}

	@Test
	public void testPreguntaEnunciatMaxLength() {
		pregunta.setEnunciat(ENUNCIAT_MAX_LENGTH);
		pregunta.setDificultatTeorica(0.8f);
		pregunta.setDificultatPractica(0.34f);
		pregunta.setRaonarResposta(Boolean.FALSE);
		pregunta.setDataAlta(new Date());
		pregunta.setTipus(Pregunta.TIPUS_ESN);
		pregunta.setCreador(professor);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);
		assertEquals(0, errors.size());
	}

	@Test
	public void testPreguntaEnunciatMaxLengthSobrepassat() {
		pregunta.setEnunciat(ENUNCIAT_MAX_LENGTH + ".");
		pregunta.setDificultatTeorica(0.8f);
		pregunta.setDificultatPractica(0.34f);
		pregunta.setRaonarResposta(Boolean.FALSE);
		pregunta.setDataAlta(new Date());
		pregunta.setTipus(Pregunta.TIPUS_ESN);
		pregunta.setCreador(professor);
		pregunta.setEstat(Pregunta.ESTAT_EDITABLE);
		Set<ConstraintViolation<Pregunta>> errors = validador.validate(pregunta);
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaSetTipusIncorrecte() {
		Pregunta p = new Pregunta();
		p.setTipus("AAA");
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "tipus");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaSetTipusCorrecte() {
		Pregunta p = new Pregunta();
		p.setTipus(Pregunta.TIPUS_VOF);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "tipus");
		assertEquals(0, errors.size());
	}

	@Test
	public void testPreguntaSetEstatIncorrecte() {
		Pregunta p = new Pregunta();
		p.setEstat("AAA");
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "estat");
		assertEquals(1, errors.size());
	}

	@Test
	public void testPreguntaSetEstatCorrecte() {
		Pregunta p = new Pregunta();
		p.setEstat(Pregunta.ESTAT_PUBLIC);
		Set<ConstraintViolation<Pregunta>> errors = validador.validateProperty(p, "estat");
		assertEquals(0, errors.size());
	}

	@Test
	public void testIncrementaNumTotalActualitzacionsNotaMitja() {
		Pregunta p = new Pregunta();
		int numTotalActualitzacionsNotaMitja = p.getNumTotalActualitzacionsNotaMitja();
		p.incrementaNumTotalActualitzacionsNotaMitja();
		assertEquals(numTotalActualitzacionsNotaMitja + 1, p.getNumTotalActualitzacionsNotaMitja());
	}

	@Test
	public void testIncrementaNumTotalActualitzacionsTRM() {
		Pregunta p = new Pregunta();
		int numTotalActualitzacionsTRM = p.getNumTotalActualitzacionsTRM();
		p.incrementaNumTotalActualitzacionsTRM();
		assertEquals(numTotalActualitzacionsTRM + 1, p.getNumTotalActualitzacionsTRM());
	}

	@Test
	public void testActualitzaDificultatPracticaPrimeraContestacio() {
		Pregunta p = new Pregunta();

		// Abans d'actualitzar la dificultat pràctica, hem d'haver actualitzat el núm
		// total
		// de contestacions. Se suposa que si actualitzam la dificultat pràctica és
		// degut a
		// una nova contestació.
		p.incrementaNumTotalActualitzacionsNotaMitja();

		p.actualitzaDificultatPractica(PRE_NOTA1);
		assertEquals(Float.valueOf(1f - PRE_NOTA1 / 10), p.getDificultatPractica());
	}

	@Test
	public void testActualitzaDificultatPractica() {
		Pregunta p = new Pregunta();

		// Primera contestació
		p.incrementaNumTotalActualitzacionsNotaMitja();
		p.actualitzaDificultatPractica(PRE_NOTA1);

		// Segona contestació
		p.incrementaNumTotalActualitzacionsNotaMitja();
		p.actualitzaDificultatPractica(PRE_NOTA2);

		assertEquals(Float.valueOf(((1f - PRE_NOTA1 / 10) + (1f - PRE_NOTA2 / 10)) / 2), p.getDificultatPractica());
	}

	@Test
	public void testActualitzaNotaMitjaPrimeraContestacio() {
		Pregunta p = new Pregunta();

		// Abans d'actualitzar la nota mitja, hem d'haver actualitzat el núm total
		// de contestacions. Se suposa que si actualitzam la nota mitja és degut a
		// una nova contestació.
		p.incrementaNumTotalActualitzacionsNotaMitja();

		p.actualitzaNotaMitja(PRE_NOTA1);
		assertEquals(PRE_NOTA1, p.getNotaMitja());
	}

	@Test
	public void testActualitzaNotaMitja() {
		Pregunta p = new Pregunta();

		// Primera contestació
		p.incrementaNumTotalActualitzacionsNotaMitja();
		p.actualitzaNotaMitja(PRE_NOTA1);

		// Primera contestació
		p.incrementaNumTotalActualitzacionsNotaMitja();
		p.actualitzaNotaMitja(PRE_NOTA2);

		assertEquals(Float.valueOf((PRE_NOTA1 + PRE_NOTA2) / 2), p.getNotaMitja());
	}

	@Test
	public void testActualitzaTempsRespostaMigPrimeraContestacio() throws ParseException {
		Pregunta p = new Pregunta();

		Date dataCntInici = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_INICI1);
		Date dataCntFi = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_FI1);
		Long tempsResposta = dataCntFi.getTime() - dataCntInici.getTime();

		// Abans d'actualitzar el temps de resposta mig, hem d'haver actualitzat el núm
		// total
		// de contestacions. Se suposa que si actualitzam el temps de resposta mig és
		// degut a
		// una nova contestació.
		p.incrementaNumTotalActualitzacionsTRM();
		p.actualitzaTempsRespostaMig(dataCntFi.getTime() - dataCntInici.getTime());

		assertEquals(Long.valueOf(tempsResposta), p.getTempsRespostaMig());
	}

	@Test
	public void testActualitzaTempsRespostaMig() throws ParseException {
		Pregunta p = new Pregunta();

		Date dataCntInici1 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_INICI1);
		Date dataCntFi1 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_FI1);
		Date dataCntInici2 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_INICI2);
		Date dataCntFi2 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_FI2);
		Long tempsResposta1 = dataCntFi1.getTime() - dataCntInici1.getTime();
		Long tempsResposta2 = dataCntFi2.getTime() - dataCntInici2.getTime();

		// Primera contestació
		p.incrementaNumTotalActualitzacionsTRM();
		p.actualitzaTempsRespostaMig(dataCntFi1.getTime() - dataCntInici1.getTime());

		// Segona contestació
		p.incrementaNumTotalActualitzacionsTRM();
		p.actualitzaTempsRespostaMig(dataCntFi2.getTime() - dataCntInici2.getTime());

		assertEquals(Long.valueOf((tempsResposta1 + tempsResposta2) / 2), p.getTempsRespostaMig());
	}

}
