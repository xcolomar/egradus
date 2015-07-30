package org.projecte.egradus.domain;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class QuestionariTests {
	
	private static Validator validador;
	
	private static String DESCRIPCIO_MAX_LENGTH = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore mag";
	
	private static Float  QST_NOTA1           = 8f;
	private static Float  QST_NOTA2           = 5.5f;
	private static String DATA_CNT_INICI1     = "2015-01-18 15:08:10";
	private static String DATA_CNT_FI1        = "2015-01-18 15:11:38";
	private static String DATA_CNT_INICI2     = "2015-01-18 15:08:10";
	private static String DATA_CNT_FI2        = "2015-01-18 15:11:38";
	private static String FORMAT_DATA         = "yyyy-MM-dd HH:mm:ss";
	
	private Persona persona;
	private Assignatura assignatura;
	private Professor professor;
	private Questionari questionari;
	
	@Before
	public void inicialitza() {
		persona = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		assignatura = UtilTest.inserirAssignatura(persona, "CODIREF", "2013-14", "nomAssignatura", "descripcio", null, "claualu", new Date());
		professor = UtilTest.getProfessor(persona, assignatura);
		questionari = new Questionari();
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validador = factory.getValidator();
	}
	
	@Test
	public void testQuestionariNomNotEmpty() {
		questionari.setNom("");
		questionari.setCreador(professor);
		questionari.setEstat(Questionari.ESTAT_EDITABLE);
		Set<ConstraintViolation<Questionari>> errors = validador.validate(questionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariCreadorNotNull() {
		questionari.setNom("nom questionari");
		questionari.setCreador(null);
		questionari.setEstat(Questionari.ESTAT_EDITABLE);
		Set<ConstraintViolation<Questionari>> errors = validador.validate(questionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariOk() {
		questionari.setNom("nom");
		questionari.setCreador(professor);
		questionari.setEstat(Questionari.ESTAT_EDITABLE);
		Set<ConstraintViolation<Questionari>> errors = validador.validate(questionari);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testQuestionariTotsElsParametresOk() {
		questionari.setNom("nom");
		questionari.setDescripcio("descripcio");
		questionari.setPreguntes(new ArrayList<PreguntaQuestionari>());
		questionari.setDificultatTeorica(0.8f);
		questionari.setDificultatPractica(0.34f);
		questionari.setNumTotalActualitzacionsNotaMitja(25);
		questionari.setNumTotalActualitzacionsTRM(25);
		questionari.setNotaMitja(5.25f);
		questionari.setTempsRespostaMig(462L);
		questionari.setDataAlta(new Date());
		questionari.setCreador(professor);
		questionari.setEstat(Questionari.ESTAT_EDITABLE);
		Set<ConstraintViolation<Questionari>> errors = validador.validate(questionari);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testQuestionariDificultatPracticaMax(){
		Questionari q = new Questionari();
		q.setDificultatPractica(2f);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "dificultatPractica");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariDificultatTeoricaMax(){
		Questionari q = new Questionari();
		q.setDificultatTeorica(2f);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "dificultatTeorica");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariDificultatPracticaMin(){
		Questionari q = new Questionari();
		q.setDificultatPractica(-2f);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "dificultatPractica");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariDificultatTeoricaMin(){
		Questionari q = new Questionari();
		q.setDificultatTeorica(-1f);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "dificultatTeorica");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariNumTotalActualitzacionsNotaMitjaMin(){
		Questionari q = new Questionari();
		q.setNumTotalActualitzacionsNotaMitja(-1);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "numTotalActualitzacionsNotaMitja");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariNumTotalActualitzacionsTRMMin(){
		Questionari q = new Questionari();
		q.setNumTotalActualitzacionsTRM(-1);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "numTotalActualitzacionsTRM");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariNotaMitjaMin(){
		Questionari q = new Questionari();
		q.setNotaMitja(-1f);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "notaMitja");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariNotaMitjaMax(){
		Questionari q = new Questionari();
		q.setNotaMitja(11f);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "notaMitja");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariNotaMitjaOk(){
		Questionari q = new Questionari();
		q.setNotaMitja(6.3f);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "notaMitja");
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testQuestionariTempsRespostaMigMin(){
		Questionari q = new Questionari();
		q.setTempsRespostaMig(-1L);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "tempsRespostaMig");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariTempsRespostaMigOk(){
		Questionari q = new Questionari();
		q.setTempsRespostaMig(154L);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(q, "tempsRespostaMig");
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testQuestionariDescripcioMaxLength() {
		questionari.setNom("nom");
		questionari.setDescripcio(DESCRIPCIO_MAX_LENGTH);
		questionari.setDificultatTeorica(0.8f);
		questionari.setDificultatPractica(0.34f);
		questionari.setDataAlta(new Date());
		questionari.setPreguntes(new ArrayList<PreguntaQuestionari>());
		questionari.setCreador(professor);
		questionari.setEstat(Questionari.ESTAT_EDITABLE);
		Set<ConstraintViolation<Questionari>> errors = validador.validate(questionari);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testQuestionariDescripcioMaxLengthSobrepassat() {
		questionari.setNom("nom");
		questionari.setDescripcio(DESCRIPCIO_MAX_LENGTH + ".");
		questionari.setDificultatTeorica(0.8f);
		questionari.setDificultatPractica(0.34f);
		questionari.setDataAlta(new Date());
		questionari.setPreguntes(new ArrayList<PreguntaQuestionari>());
		questionari.setCreador(professor);
		questionari.setEstat(Questionari.ESTAT_EDITABLE);
		Set<ConstraintViolation<Questionari>> errors = validador.validate(questionari);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariSetEstatIncorrecte() {
		questionari.setEstat("AAA");
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(questionari, "estat");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testQuestionariSetEstatCorrecte() {
		questionari.setEstat(Questionari.ESTAT_PUBLIC);
		Set<ConstraintViolation<Questionari>> errors = validador.validateProperty(questionari, "estat");
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testGetNumPreguntesRecNull() {
		questionari.setPreguntes(null);
		assertEquals(0, questionari.getNumPreguntesRec());
	}
	
	@Test
	public void testGetNumPreguntesRecBuit() {
		questionari.setPreguntes(new ArrayList<PreguntaQuestionari>());
		assertEquals(0, questionari.getNumPreguntesRec());
	}
	
	@Test
	public void testGetNumPreguntesRecAmbUnaPreguntaRec() {
		// Inserim tres preguntes (una d'elles REC, per tant, NO es podrà corregir automàticament)
		Pregunta preguntaEs1 = UtilTest.inserirPregunta(professor, "enunciat1", 0.43f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_ES1, null, Pregunta.ESTAT_PUBLIC);
		Pregunta preguntaRec = UtilTest.inserirPregunta(professor, "enunciat2", 0.88f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_REC, null, Pregunta.ESTAT_PUBLIC);
		Pregunta preguntaVof = UtilTest.inserirPregunta(professor, "enunciat3", 0.2f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_VOF, Boolean.FALSE, Pregunta.ESTAT_PUBLIC);
		
		PreguntaQuestionari pqEs1 = new PreguntaQuestionari();
		pqEs1.setPregunta(preguntaEs1);
		pqEs1.setQuestionari(questionari);
		pqEs1.setPes(0.7f);
		
		PreguntaQuestionari pqRec = new PreguntaQuestionari();
		pqRec.setPregunta(preguntaRec);
		pqRec.setQuestionari(questionari);
		pqRec.setPes(0.2f);
		
		PreguntaQuestionari pqVof = new PreguntaQuestionari();
		pqVof.setPregunta(preguntaVof);
		pqVof.setQuestionari(questionari);
		pqVof.setPes(0.1f);
		
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ.add(pqEs1);
		llistaPQ.add(pqRec);
		llistaPQ.add(pqVof);
		
		questionari.setPreguntes(llistaPQ);
		assertEquals(1, questionari.getNumPreguntesRec());
	}
	
	@Test
	public void testGetNumPreguntesRecSenseCapPreguntaRec() {
		// Inserim tres preguntes (cap d'elles REC, per tant, es podrà corregir automàticament)
		Pregunta preguntaEs1 = UtilTest.inserirPregunta(professor, "enunciat1", 0.43f, null, Boolean.TRUE, new Date(), Pregunta.TIPUS_ES1, null, Pregunta.ESTAT_PUBLIC);
		Pregunta preguntaEsN = UtilTest.inserirPregunta(professor, "enunciat2", 0.88f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_ESN, null, Pregunta.ESTAT_PUBLIC);
		Pregunta preguntaVof = UtilTest.inserirPregunta(professor, "enunciat3", 0.2f, null, Boolean.FALSE, new Date(), Pregunta.TIPUS_VOF, Boolean.TRUE, Pregunta.ESTAT_PUBLIC);
		
		PreguntaQuestionari pqEs1 = new PreguntaQuestionari();
		pqEs1.setPregunta(preguntaEs1);
		pqEs1.setQuestionari(questionari);
		pqEs1.setPes(0.3f);
		
		PreguntaQuestionari pqEsN = new PreguntaQuestionari();
		pqEsN.setPregunta(preguntaEsN);
		pqEsN.setQuestionari(questionari);
		pqEsN.setPes(0.3f);
		
		PreguntaQuestionari pqVof = new PreguntaQuestionari();
		pqVof.setPregunta(preguntaVof);
		pqVof.setQuestionari(questionari);
		pqVof.setPes(0.4f);
		
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		llistaPQ.add(pqEs1);
		llistaPQ.add(pqEsN);
		llistaPQ.add(pqVof);
		
		questionari.setPreguntes(llistaPQ);
		assertEquals(0, questionari.getNumPreguntesRec());
	}
	
	@Test
	public void testIncrementaNumTotalActualitzacionsNotaMitja() {
		int numTotalActualitzacionsNotaMitja = questionari.getNumTotalActualitzacionsNotaMitja();
		questionari.incrementaNumTotalActualitzacionsNotaMitja();
		assertEquals(numTotalActualitzacionsNotaMitja + 1, questionari.getNumTotalActualitzacionsNotaMitja());
	}
	
	@Test
	public void testIncrementaNumTotalActualitzacionsTRM() {
		int numTotalActualitzacionsTRM = questionari.getNumTotalActualitzacionsTRM();
		questionari.incrementaNumTotalActualitzacionsTRM();
		assertEquals(numTotalActualitzacionsTRM + 1, questionari.getNumTotalActualitzacionsTRM());
	}
	
	@Test
	public void testActualitzaDificultatPracticaPrimeraContestacio() {
		// Abans d'actualitzar la dificultat pràctica, hem d'haver actualitzat el núm total
		// de contestacions. Se suposa que si actualitzam la dificultat pràctica és degut a 
		// una nova contestació.
		questionari.incrementaNumTotalActualitzacionsNotaMitja();
		
		questionari.actualitzaDificultatPractica(QST_NOTA1);
		assertEquals(new Float(1f - QST_NOTA1/10), questionari.getDificultatPractica());
	}
	
	@Test
	public void testActualitzaDificultatPractica() {
		// Primera contestació
		questionari.incrementaNumTotalActualitzacionsNotaMitja();
		questionari.actualitzaDificultatPractica(QST_NOTA1);
		
		// Segona contestació
		questionari.incrementaNumTotalActualitzacionsNotaMitja();
		questionari.actualitzaDificultatPractica(QST_NOTA2);
		
		assertEquals(new Float(((1f - QST_NOTA1/10) + (1f - QST_NOTA2/10)) / 2), questionari.getDificultatPractica());
	}
	
	@Test
	public void testActualitzaNotaMitjaPrimeraContestacio() {
		// Abans d'actualitzar la nota mitja, hem d'haver actualitzat el núm total
		// de contestacions. Se suposa que si actualitzam la nota mitja és degut a 
		// una nova contestació.
		questionari.incrementaNumTotalActualitzacionsNotaMitja();
		
		questionari.actualitzaNotaMitja(QST_NOTA1);
		assertEquals(QST_NOTA1, questionari.getNotaMitja());
	}
	
	@Test
	public void testActualitzaNotaMitja() {
		// Primera contestació
		questionari.incrementaNumTotalActualitzacionsNotaMitja();
		questionari.actualitzaNotaMitja(QST_NOTA1);
		
		// Primera contestació
		questionari.incrementaNumTotalActualitzacionsNotaMitja();
		questionari.actualitzaNotaMitja(QST_NOTA2);
		
		assertEquals(new Float((QST_NOTA1 + QST_NOTA2) / 2), questionari.getNotaMitja());
	}
	
	@Test
	public void testActualitzaTempsRespostaMigPrimeraContestacio() throws ParseException {
		Date dataCntInici = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_INICI1);
		Date dataCntFi = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_FI1);
		Long tempsResposta = dataCntFi.getTime() - dataCntInici.getTime();
		
		// Abans d'actualitzar el temps de resposta mig, hem d'haver actualitzat el núm total
		// de contestacions. Se suposa que si actualitzam el temps de resposta mig és degut a 
		// una nova contestació.
		questionari.incrementaNumTotalActualitzacionsTRM();
		questionari.actualitzaTempsRespostaMig(dataCntFi.getTime() - dataCntInici.getTime());
		
		assertEquals(new Long(tempsResposta), questionari.getTempsRespostaMig());
	}
	
	@Test
	public void testActualitzaTempsRespostaMig() throws ParseException {
		Date dataCntInici1 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_INICI1);
		Date dataCntFi1 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_FI1);
		Date dataCntInici2 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_INICI2);
		Date dataCntFi2 = new SimpleDateFormat(FORMAT_DATA).parse(DATA_CNT_FI2);
		Long tempsResposta1 = dataCntFi1.getTime() - dataCntInici1.getTime();
		Long tempsResposta2 = dataCntFi2.getTime() - dataCntInici2.getTime();
		
		// Primera contestació
		questionari.incrementaNumTotalActualitzacionsTRM();
		questionari.actualitzaTempsRespostaMig(dataCntFi1.getTime() - dataCntInici1.getTime());
		
		// Segona contestació
		questionari.incrementaNumTotalActualitzacionsTRM();
		questionari.actualitzaTempsRespostaMig(dataCntFi2.getTime() - dataCntInici2.getTime());
		
		assertEquals(new Long((tempsResposta1 + tempsResposta2) / 2), questionari.getTempsRespostaMig());
	}
	
}
