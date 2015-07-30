package org.projecte.egradus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
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
@TransactionConfiguration(defaultRollback=true)
public class JPAQuestionariDaoTests {
	
	@Autowired
	private PersonaDao personaDao;
	
	@Autowired
	private AssignaturaDao assignaturaDao;
	
	@Autowired
	private ProfessorDao professorDao;
	
	@Autowired
	private AlumneDao alumneDao;
	
	@Autowired
	private QuestionariDao questionariDao;
	
	@Autowired
	private RespostaQuestionariDao respostaQuestionariDao;
	
	// Dades de la Persona
	private static String  PER1_NOM             = "Miquel";
    private static String  PER1_PRIMER_LLINATGE = "Garcia";
    private static String  PER1_CORREU          = "m.garcia@correu.es";
    private static String  PER1_ALIES           = "miquelet";
    private static String  PER1_CLAU            = "claumiquel";
    private static String  PER1_DATA_ALTA       = "20/11/2013";
    
    private static String  PER2_NOM             = "Jordi";
    private static String  PER2_PRIMER_LLINATGE = "Bonet";
    private static String  PER2_CORREU          = "bonetjordi@correu.es";
    private static String  PER2_ALIES           = "jordi";
    private static String  PER2_CLAU            = "claujordi";
    private static String  PER2_DATA_ALTA       = "20/11/2013";
    
    // Dades de l'Assignatura
    private static String  ASS_NOM             = "Intel·ligència Artificial";
	private static String  ASS_DESCRIPCIO      = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String  ASS_CLAU_PROFESSOR  = "clau_inteli_artifi";
	private static String  ASS_CLAU_ALUMNE     = "clau_intart";
	private static String  ASS_DATA_ALTA       = "01/10/2013";
	private static String  ASS_ANY_ACADEMIC    = "2013-14";
	private static String  ASS_CODI_REF        = "INTELIART";
	
	// Dades del Questionari
	private static String  QST_NOM             = "Nom del qüestionari";
	private static String  QST_NOM_DISTINT     = "Nom distint"; // per comprovar el merge
	private static String  QST_DESCRIPCIO      = "Descripció del qüestionari";
	private static Float   QST_DIFICULTAT_TEO  = 0.5f;
	private static Float   QST_DIFICULTAT_PRA  = 0.99f;
	private static String  QST_ESTAT           = Questionari.ESTAT_EDITABLE;
	private static String  QST_DATA_ALTA_REF   = "15/11/2014";
	
	private static String  QST2_NOM             = "Nom del qüestionari";
	private static String  QST2_DESCRIPCIO      = "Descripció del qüestionari";
	private static Float   QST2_DIFICULTAT_TEO  = 0.5f;
	private static Float   QST2_DIFICULTAT_PRA  = 0.99f;
	private static String  QST2_ESTAT           = Questionari.ESTAT_EDITABLE;
	private static String  QST2_DATA_ALTA_REF   = "15/11/2014";
	
	// Filtres per a cercar Questionaris
	private static String  QST_DATA_INICI_OK   = "01/07/2014";
	private static String  QST_DATA_INICI_NO   = "16/11/2014";
	private static String  QST_DATA_FI_OK      = "05/01/2015";
	private static String  QST_DATA_FI_NO      = "14/07/2014";
	
	private static String  QST_NOM_OK          = "üest";
	private static String  QST_NOM_NO          = "aaa";
	private static String  QST_DESCRIPCIO_OK   = "desc";
	private static String  QST_DESCRIPCIO_NO   = "aaa";
	
	private static String  QST_CRE_ALIES_OK    = "quelet";
	private static String  QST_CRE_NOM_OK      = "Miq";
	private static String  QST_CRE_LLINATGE_OK = "GarcI";
	private static String  QST_CREADOR_NO      = "mico";
	
	private static Float   QST_DIF_TEO1_OK     = 0.201f;
	private static Float   QST_DIF_TEO1_NO     = 0.774f;
	private static Float   QST_DIF_TEO2_OK     = QST_DIFICULTAT_TEO;
	private static Float   QST_DIF_TEO2_NO     = 0f;
	
	private static Float   QST_DIF_PRA1_OK     = QST_DIFICULTAT_PRA;
	private static Float   QST_DIF_PRA1_NO     = 1.0f;
	private static Float   QST_DIF_PRA2_OK     = 0.991f;
	private static Float   QST_DIF_PRA2_NO     = 0.9f;
	
	
	private Persona personaProfessor;
	private Persona personaAlumne;
	private Persona persona2;
	private Assignatura assignatura;
	private Professor professor;
	private Professor professor2;
	private Alumne alumne;
	
	@Before
	public void initialize() {
		personaProfessor = UtilTest.inserirPersona(PER1_NOM, PER1_PRIMER_LLINATGE, PER1_CORREU, PER1_ALIES, PER1_CLAU, UtilTest.getDate(PER1_DATA_ALTA));
		personaDao.persistPersona(personaProfessor);
		personaAlumne = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
		personaDao.persistPersona(personaAlumne);
		assignatura = UtilTest.inserirAssignatura(personaProfessor, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		professor = UtilTest.getProfessor(personaProfessor, assignatura);
		professorDao.persistProfessor(professor);
		alumne = UtilTest.getAlumne(personaAlumne, assignatura);
		alumneDao.persistAlumne(alumne);
	}
	
	@Test
	public void testGetLlistaQuestionaris() {
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris();
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testPersistQuestionari() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		assertNotNull(questionari.getCodi());
	}
	
	@Test
	public void testUpdateQuestionari() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		
		// comprobam que el nom del qüestionari no encaixa amb el nom distint
		assertNotEquals(QST_NOM_DISTINT, questionari.getNom());
		
		// modificam el nom del qüestionari
		questionari.setNom(QST_NOM_DISTINT);
		Questionari questionari2 = questionariDao.updateQuestionari(questionari);
		
		// comprobam que el nom del qüestionari ja és el nom distint, però els codis coincideixen
		assertEquals(questionari.getCodi(), questionari2.getCodi());
		assertEquals(QST_NOM_DISTINT, questionari2.getNom());
	}
	
	@Test
	public void testEliminaQuestionari() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		
		// Consultam quants qüestionaris tenim inserits a BD
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris();
		assertEquals(1, llistaQuestionaris.size());
		
		// Eliminam el qüestionari, i tornam a consultar
		questionariDao.removeQuestionari(questionari);
		llistaQuestionaris = questionariDao.getLlistaQuestionaris();
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testPersistAmbGetLlistaQuestionaris() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris();
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetLlistaQuestionarisByAssignaturaBuit() {
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionarisByAssignatura(assignatura.getCodi());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetLlistaQuestionarisByAssignatura() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		
		RespostaQuestionari rq = new RespostaQuestionari(questionari, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq);
		
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionarisByAssignatura(assignatura.getCodi());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetLlistaQuestionarisByAssignaturaVarios() {
		// Afegim un altre professor a l'assignatura: professor2
		persona2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaDao.persistPersona(persona2);
		professor2 = UtilTest.getProfessor(persona2, assignatura);
		professorDao.persistProfessor(professor2);
		
		Questionari questionari1 = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari1);
		
		Questionari questionari2 = UtilTest.inserirQuestionari(professor, QST2_NOM, QST2_DESCRIPCIO, QST2_DIFICULTAT_TEO, QST2_DIFICULTAT_PRA, QST2_ESTAT, UtilTest.getDate(QST2_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari2);
		
		// El professor 1 li envia el qüestionari1 a l'alumne
		RespostaQuestionari rq1 = new RespostaQuestionari(questionari1, professor, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq1);
		
		// El professor 2 li envia la qüestionari1 a l'alumne
		RespostaQuestionari rq2 = new RespostaQuestionari(questionari1, professor2, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq2);
		
		// El professor 2 li envia la qüestionari2 a l'alumne2
		RespostaQuestionari rq3 = new RespostaQuestionari(questionari2, professor2, alumne);
		respostaQuestionariDao.persistRespostaQuestionari(rq3);
		
		// Comprovam que, en l'àmbit de l'assignatura actual, hi han 2 qüestionaris (per molt que,
		// en total, hi hagin 3 resposta-questionaris).
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionarisByAssignatura(assignatura.getCodi());
		assertEquals(2, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDataIniciOk() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(UtilTest.getDate(QST_DATA_INICI_OK), null, null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDataIniciNo() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(UtilTest.getDate(QST_DATA_INICI_NO), null, null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDataFiOk() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, UtilTest.getDate(QST_DATA_FI_OK), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDataFiNo() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, UtilTest.getDate(QST_DATA_FI_NO), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreBetweenOk() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(UtilTest.getDate(QST_DATA_INICI_OK), UtilTest.getDate(QST_DATA_FI_OK), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreBetweenDadesGirades() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(UtilTest.getDate(QST_DATA_INICI_NO), UtilTest.getDate(QST_DATA_FI_NO), null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreNomOk() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, QST_NOM_OK, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreNomNo() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, QST_NOM_NO, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDescripcioOk() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, QST_DESCRIPCIO_OK, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDescripcioNo() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, QST_DESCRIPCIO_NO, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorNo() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, QST_CREADOR_NO, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorAlies() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, QST_CRE_ALIES_OK, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorNom() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, QST_CRE_NOM_OK, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreCreadorLlinatge() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, QST_CRE_LLINATGE_OK, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo1Ok() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_OK, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo1No() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_NO, null, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo2Ok() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, QST_DIF_TEO2_OK, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeo2No() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, QST_DIF_TEO2_NO, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeoBetweenOk() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_OK, QST_DIF_TEO2_OK, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifTeoBetweenNo() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, QST_DIF_TEO1_NO, QST_DIF_TEO2_NO, null, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra1Ok() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_OK, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra1No() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_NO, null, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra2Ok() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, null, QST_DIF_PRA2_OK, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPra2No() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, null, QST_DIF_PRA2_NO, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPraBetweenOk() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_OK, QST_DIF_PRA2_OK, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreDifPraBetweenNo() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, QST_DIF_PRA1_NO, QST_DIF_PRA2_NO, null, professor.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreEstatPublic() {
		// El qüestionari creat pel professor es troba en estat editable
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_EDITABLE, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		
		// Afegim un altre professor a l'assignatura: professor2
		persona2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaDao.persistPersona(persona2);
		professor2 = UtilTest.getProfessor(persona2, assignatura);
		professorDao.persistProfessor(professor2);
		
		// El qüestionari creat pel professor2 es troba en estat públic
		Questionari questionari2 = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari2);
		
		// Consultam els qüestionaris en estat públic que veu el professor 1
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, Questionari.ESTAT_PUBLIC, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
		
		// Consultam els qüestionaris en estat públic que veu el professor 2
		llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, Questionari.ESTAT_PUBLIC, null, null, null, null, null, professor2.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreEstatEditable() {
		// El qüestionari creat pel professor es troba en estat editable
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_EDITABLE, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		
		// Afegim un altre professor a l'assignatura: professor2
		persona2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaDao.persistPersona(persona2);
		professor2 = UtilTest.getProfessor(persona2, assignatura);
		professorDao.persistProfessor(professor2);
		
		// El qüestionari creat pel professor2 es troba en estat públic
		Questionari questionari2 = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari2);
		
		// Consultam els qüestionaris en estat editable que veu el professor 1
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, Questionari.ESTAT_EDITABLE, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
		
		// Consultam els qüestionaris en estat editable que veu el professor 2
		llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, Questionari.ESTAT_EDITABLE, null, null, null, null, null, professor2.getCodi().intValue());
		assertEquals(0, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionarisFiltreEstatSenseIndicar() {
		// El qüestionari creat pel professor es troba en estat editable
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_EDITABLE, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		
		// Afegim un altre professor a l'assignatura: professor2
		persona2 = UtilTest.inserirPersona("nom", "llinatge1", "correu@correu.es", "alies", "clau", new Date());
		personaDao.persistPersona(persona2);
		professor2 = UtilTest.getProfessor(persona2, assignatura);
		professorDao.persistProfessor(professor2);
		
		// El qüestionari creat pel professor2 es troba en estat públic
		Questionari questionari2 = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, Questionari.ESTAT_PUBLIC, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari2);
		
		// Consultam els qüestionaris en estat editable que veu el professor 1
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, null, null, null, professor.getCodi().intValue());
		assertEquals(2, llistaQuestionaris.size());
		
		// Consultam els qüestionaris en estat editable que veu el professor 2
		llistaQuestionaris = questionariDao.getLlistaQuestionaris(null, null, null, null, null, null, null, null, null, null, null, professor2.getCodi().intValue());
		assertEquals(1, llistaQuestionaris.size());
	}
	
	@Test
	public void testGetQuestionariByCodi() {
		Questionari questionari = UtilTest.inserirQuestionari(professor, QST_NOM, QST_DESCRIPCIO, QST_DIFICULTAT_TEO, QST_DIFICULTAT_PRA, QST_ESTAT, UtilTest.getDate(QST_DATA_ALTA_REF));
		questionariDao.persistQuestionari(questionari);
		
		// agafam el qüestionari que acabam d'inserir cercant-lo per codi
		Questionari q = questionariDao.getQuestionariByCodi(questionari.getCodi());
		assertEquals(QST_NOM, q.getNom());
	}
	
}
