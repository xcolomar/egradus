package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
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
public class JPAAssignaturaDaoTests {

	@Autowired
	private AssignaturaDao assignaturaDao;

	@Autowired
	private PersonaDao personaDao;

	private static String ASS_NOM = "Intel·ligència Artificial";
	private static String ASS_DESCRIPCIO = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR = "clau_inteli_artifi";
	private static String ASS_CLAU_ALUMNE = "clau_intart";
	private static String ASS_DATA_ALTA = "01/10/2013";
	private static String ASS_ANY_ACADEMIC = "2013-14";
	private static String ASS_CODI_REF = "INTELIART";

	private static String ASS2_NOM = "Càlcul Numèric";
	private static String ASS2_DESCRIPCIO = "Descripció de l'Assignatura de 2º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS2_CLAU_PROFESSOR = null;
	private static String ASS2_CLAU_ALUMNE = null;
	private static String ASS2_DATA_ALTA = "15/11/2013";
	private static String ASS2_ANY_ACADEMIC = "2013-14";
	private static String ASS2_CODI_REF = "CALCNUM";

	private static String ASS3_NOM = "Estadística III";
	private static String ASS3_DESCRIPCIO = "Descripció de l'Assignatura de 5º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS3_CLAU_PROFESSOR = "clau_estad";
	private static String ASS3_CLAU_ALUMNE = null;
	private static String ASS3_DATA_ALTA = "28/10/2013";
	private static String ASS3_ANY_ACADEMIC = "2013-14";
	private static String ASS3_CODI_REF = "ESTAD3";

	private static String PER_NOM = "Miquel";
	private static String PER_PRIMER_LLINATGE = "Garcia";
	private static String PER_CORREU = "m.garcia@correu.es";
	private static String PER_ALIES = "miquelet";
	private static String PER_CLAU = "claumiquel";
	private static String PER_DATA_ALTA = "20/11/2013";

	// Dates per comparar
	private static String DATA_INICI_TOTES = "01/10/2013";
	private static String DATA_FI_TOTES = "30/11/2013";
	private static String DATA_INICI_CAP = "01/09/2013";
	private static String DATA_FI_CAP = "10/09/2013";
	private static String DATA_INICI_DUES = "02/10/2013";
	private static String DATA_FI_DUES = "15/11/2013";

	// Claus professor i alumne per comparar
	private static String CLAU_PROFESSOR_NULL = null;
	private static String CLAU_PROFESSOR_SI = "S";
	private static String CLAU_PROFESSOR_NO = "N";
	private static String CLAU_ALUMNE_NULL = null;
	private static String CLAU_ALUMNE_SI = "S";
	private static String CLAU_ALUMNE_NO = "N";

	// Noms d'assignatura per comparar
	private static String NOM_ASS_OK = "artific"; // Coincideix amb "Intel·ligència Artificial"
	private static String NOM_ASS_NO = "meric"; // per coincidir amb "Càlcul Numèric" hauria de tenir accent a la 'e'

	// Noms de persones per comparar
	private static String ALIES_OK = "quele"; // Coincideix amb l'àlies "miquelet"
	private static String ALIES_NO = "ca"; // No coincideix ni amb nom, llinatges ni àlies
	private static String LLINATGE_OK = "Garci"; // Coincideix amb el llinatge "Garcia"
	private static String LLINATGE_NO = "garcies"; // No coincideix ni amb nom, llinatges ni àlies

	private Persona persona;

	@Before
	public void initialize() {
		persona = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU,
				UtilTest.getDate(PER_DATA_ALTA));
		personaDao.persistPersona(persona);
	}

	@Test
	public void testGetLlistaAssignaturesBuit() {
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures();
		assertEquals(0, assignatures.size());
	}

	@Test
	public void testGetLlistaAssignatures() {
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures();
		assertEquals(1, assignatures.size());
	}

	@Test
	public void testSetAssignatura() {
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assertNotNull(assignatura.getCodi());
	}

	@Test
	public void testGetAssignaturesPerPersonaSize() {
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignaturesCreador(persona);
		assertEquals(1, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerPersonaCodi() {
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignaturesCreador(persona);
		assertEquals(assignatures.get(0).getCodi(), assignatura.getCodi());
	}

	@Test
	public void testGetAssignaturesPerPersonaNull() {
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignaturesCreador(null);
		assertEquals(0, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerDatesTotes() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per dates que agafen totes les assignatures
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(UtilTest.getDate(DATA_INICI_TOTES),
				UtilTest.getDate(DATA_FI_TOTES), CLAU_PROFESSOR_NULL, CLAU_ALUMNE_NULL, null, null, null, null);
		assertEquals(3, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerDatesCap() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per dates que NO agafen cap assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(UtilTest.getDate(DATA_INICI_CAP),
				UtilTest.getDate(DATA_FI_CAP), CLAU_PROFESSOR_NULL, CLAU_ALUMNE_NULL, null, null, null, null);
		assertEquals(0, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerDatesDues() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per dates que agafen només dues de les assignatures
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(UtilTest.getDate(DATA_INICI_DUES),
				UtilTest.getDate(DATA_FI_DUES), CLAU_PROFESSOR_NULL, CLAU_ALUMNE_NULL, null, null, null, null);
		assertEquals(2, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerClauProfessor() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per assignatures que tenen clau de professor i agafam dues
		// assignatures
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_SI,
				CLAU_ALUMNE_NULL, null, null, null, null);
		assertEquals(2, assignatures.size());
		assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NO, CLAU_ALUMNE_NULL, null, null,
				null, null);
		assertEquals(1, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerClauAlumne() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per assignatures que tenen clau d'alumne i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NULL,
				CLAU_ALUMNE_SI, null, null, null, null);
		assertEquals(1, assignatures.size());
		assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NULL, CLAU_ALUMNE_NO, null, null,
				null, null);
		assertEquals(2, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerTotsParametres() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per tots els paràmetres que té el mètode i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(UtilTest.getDate(DATA_INICI_TOTES),
				UtilTest.getDate(DATA_FI_TOTES), CLAU_PROFESSOR_SI, CLAU_ALUMNE_NO, null, null, null, null);
		assertEquals(1, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerNomAssignaturaOk() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per tots els paràmetres que té el mètode i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(UtilTest.getDate(DATA_INICI_TOTES),
				UtilTest.getDate(DATA_FI_TOTES), CLAU_PROFESSOR_NULL, CLAU_ALUMNE_NULL, null, NOM_ASS_OK, null, null);
		assertEquals(1, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerNomAssignaturaFalse() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per tots els paràmetres que té el mètode i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NULL,
				CLAU_ALUMNE_NULL, null, NOM_ASS_NO, null, null);
		assertEquals(0, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerLlinatgeCreadorOk() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per tots els paràmetres que té el mètode i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NULL,
				CLAU_ALUMNE_NULL, null, null, null, LLINATGE_OK);
		assertEquals(3, assignatures.size()); // Són 3 assignatures
	}

	@Test
	public void testGetAssignaturesPerLlinatgeCreadorFalse() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per tots els paràmetres que té el mètode i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NULL,
				CLAU_ALUMNE_NULL, null, null, null, LLINATGE_NO);
		assertEquals(0, assignatures.size());
	}

	@Test
	public void testGetAssignaturesPerAliesCreadorOk() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per tots els paràmetres que té el mètode i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NULL,
				CLAU_ALUMNE_NULL, null, null, null, ALIES_OK);
		assertEquals(3, assignatures.size()); // Són 3 assignatures
	}

	@Test
	public void testGetAssignaturesPerAliesCreadorFalse() {
		// Inserim les tres assignatures (les tres vinculades a la mateixa persona)
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO,
				ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = UtilTest.inserirAssignatura(persona, ASS3_CODI_REF, ASS3_ANY_ACADEMIC, ASS3_NOM, ASS3_DESCRIPCIO,
				ASS3_CLAU_PROFESSOR, ASS3_CLAU_ALUMNE, UtilTest.getDate(ASS3_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);

		// Filtram per tots els paràmetres que té el mètode i agafam una assignatura
		List<Assignatura> assignatures = assignaturaDao.getLlistaAssignatures(null, null, CLAU_PROFESSOR_NULL,
				CLAU_ALUMNE_NULL, null, null, null, ALIES_NO);
		assertEquals(0, assignatures.size());
	}

	@Test
	public void testGetAssignaturaPerCodiAssignaturaSize() {
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		assignatura = assignaturaDao.getAssignaturaByCodi(assignatura.getCodi());
		assertNotNull(assignatura.getCodi());
	}

	@Test
	public void testGetAssignaturaPerCodiAssignatura() {
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		Assignatura assignatura2 = assignaturaDao.getAssignaturaByCodi(assignatura.getCodi());
		assertEquals(assignatura2.getCodi(), assignatura.getCodi());
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstraintSetAssignaturaNom() {
		Assignatura assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, null,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		fail("no hauria de guardar una assignatura sense nom");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testConstraintSetAssignaturaPersona() {
		Assignatura assignatura = UtilTest.inserirAssignatura(null, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM,
				ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		fail("no hauria de guardar una assignatura sense persona");
	}

}
