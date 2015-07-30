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
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.utilities.UtilTest;

public class AssignaturaTests {

	private Validator validador;
	
	private static String DESCRIPCIO_MAX_LENGTH = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore mag";
	
	
	private Persona persona;
	private Assignatura assignatura;
	private String anyAcademic;
	private String anyAcademicErroni;
	private String codiReferencia;
	
	
	@Before
	public void inicialitza() {
		assignatura = new Assignatura();
		persona = UtilTest.inserirPersona("nom", "primerLlinatge", "correu@correu.es", "alies", "clau", new Date());
		
		codiReferencia = "REFERENCIA";
		anyAcademic = "2014-15";
		anyAcademicErroni = "2014-152";
		
		ValidatorFactory f = Validation.buildDefaultValidatorFactory();
		validador = f.getValidator();
	}
	
	@Test
	public void testAssignaturaCodiReferenciaNotEmpty() {
		assignatura.setCodiReferencia(null);
		assignatura.setNom("nom");
		assignatura.setCreador(persona);
		assignatura.setAnyAcademic(anyAcademic);
		Set<ConstraintViolation<Assignatura>> errors = validador.validate(assignatura);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testAssignaturaNomNotEmpty() {
		assignatura.setNom(null);
		assignatura.setCreador(persona);
		assignatura.setAnyAcademic(anyAcademic);
		assignatura.setCodiReferencia(codiReferencia);
		Set<ConstraintViolation<Assignatura>> errors = validador.validate(assignatura);
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testAssignaturaCreadorNotNull() {
		assignatura.setNom("nom");
		assignatura.setCreador(null);
		assignatura.setAnyAcademic(anyAcademic);
		assignatura.setCodiReferencia(codiReferencia);
		Set<ConstraintViolation<Assignatura>> errors = validador.validate(assignatura);
		assertEquals(1, errors.size());
	}
	
	
	@Test
	public void testAssignaturaAnyAcademicIncorrecte() {
		assignatura.setNom("nom");
		assignatura.setCreador(persona);
		assignatura.setAnyAcademic(anyAcademicErroni);
		assignatura.setCodiReferencia(codiReferencia);
		Set<ConstraintViolation<Assignatura>> errors = validador.validateProperty(assignatura, "anyAcademic");
		assertEquals(1, errors.size());
	}
	
	@Test
	public void testAssignaturaOk() {
		assignatura.setNom("nom");
		assignatura.setCreador(persona);
		assignatura.setAnyAcademic(anyAcademic);
		assignatura.setCodiReferencia(codiReferencia);
		Set<ConstraintViolation<Assignatura>> errors = validador.validate(assignatura);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testAssignaturaTotsElsParametresOk() {
		assignatura.setNom("nom");
		assignatura.setDescripcio("descripcio");
		assignatura.setClauAlumne("claualu");
		assignatura.setClauProfessor("claupro");
		assignatura.setDataAlta(new Date());
		assignatura.setCreador(persona);
		assignatura.setAnyAcademic(anyAcademic);
		assignatura.setCodiReferencia(codiReferencia);
		assignatura.setProfessors(null);
		assignatura.setAlumnes(null);
		Set<ConstraintViolation<Assignatura>> errors = validador.validate(assignatura);
		assertEquals(0, errors.size());
	}

	@Test
	public void testAssignaturaEnunciatMaxLength() {
		assignatura.setNom("nom");
		assignatura.setDescripcio(DESCRIPCIO_MAX_LENGTH);
		assignatura.setClauAlumne("claualu");
		assignatura.setClauProfessor("claupro");
		assignatura.setDataAlta(new Date());
		assignatura.setCreador(persona);
		assignatura.setAnyAcademic(anyAcademic);
		assignatura.setCodiReferencia(codiReferencia);
		assignatura.setProfessors(null);
		assignatura.setAlumnes(null);
		Set<ConstraintViolation<Assignatura>> errors = validador.validate(assignatura);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void testAssignaturaEnunciatMaxLengthSobrepassat() {
		assignatura.setNom("nom");
		assignatura.setDescripcio(DESCRIPCIO_MAX_LENGTH + ".");
		assignatura.setClauAlumne("claualu");
		assignatura.setClauProfessor("claupro");
		assignatura.setDataAlta(new Date());
		assignatura.setCreador(persona);
		assignatura.setAnyAcademic(anyAcademic);
		assignatura.setCodiReferencia(codiReferencia);
		assignatura.setProfessors(null);
		assignatura.setAlumnes(null);
		Set<ConstraintViolation<Assignatura>> errors = validador.validate(assignatura);
		assertEquals(1, errors.size());
	}
	
}