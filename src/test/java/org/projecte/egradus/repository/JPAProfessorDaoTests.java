package org.projecte.egradus.repository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.repository.AssignaturaDao;
import org.projecte.egradus.repository.PersonaDao;
import org.projecte.egradus.repository.ProfessorDao;
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
public class JPAProfessorDaoTests {
	
	@Autowired
	private ProfessorDao professorDao;
	
	@Autowired
	private PersonaDao personaDao;
	
	@Autowired
	private AssignaturaDao assignaturaDao;
	
	private static String ASS_NOM              = "Estadística III";
	private static String ASS_DESCRIPCIO       = "Descripció de l'Assignatura de 5º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String ASS_CLAU_PROFESSOR   = "clau_pro_estad";
	private static String ASS_CLAU_ALUMNE      = "clau_estad";
	private static String ASS_DATA_ALTA        = "28/10/2013";
	private static String ASS_ANY_ACADEMIC     = "2013-14";
	private static String ASS_CODI_REF         = "ESTA3";
	
	private static String ASS2_NOM             = "Llengua Castellana i Literatura";
	private static String ASS2_DESCRIPCIO      = "Descripció de l'Assignatura Segon de Secundària etc.";
	private static String ASS2_CLAU_PROFESSOR  = "llenguacast";
	private static String ASS2_CLAU_ALUMNE     = null;
	private static String ASS2_DATA_ALTA       = "09/01/2014";
	private static String ASS2_ANY_ACADEMIC    = "2013-14";
	private static String ASS2_CODI_REF        = "LLENGUACASTLIT";
	
	private static String PER_NOM              = "Miquel";
    private static String PER_PRIMER_LLINATGE  = "Garcia";
    private static String PER_CORREU           = "m.garcia@correu.es";
    private static String PER_ALIES            = "miquelet";
    private static String PER_CLAU             = "claumiquel";
    private static String PER_DATA_ALTA        = "20/11/2013";
    
    private static String PER2_NOM             = "Rosa";
    private static String PER2_PRIMER_LLINATGE = "Estarelles";
    private static String PER2_CORREU          = "restarelles@correu.es";
    private static String PER2_ALIES           = "rossi";
    private static String PER2_CLAU            = "claurosa";
    private static String PER2_DATA_ALTA       = "05/02/2014";
    
    private Persona persona;
    private Assignatura assignatura;
	
    @Before
    public void initialize() {
    	persona = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU, UtilTest.getDate(PER_DATA_ALTA));
		personaDao.persistPersona(persona);
		assignatura = UtilTest.inserirAssignatura(persona, ASS_CODI_REF, ASS_ANY_ACADEMIC, ASS_NOM, ASS_DESCRIPCIO, ASS_CLAU_PROFESSOR, ASS_CLAU_ALUMNE, UtilTest.getDate(ASS_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
    }
    
    
    @Test
    public void testGetProfessorsBuit() {
    	assertEquals(0, professorDao.getProfessors().size());
    }
    
    @Test
    public void testGetProfessorPersistProfessor() {
		Professor professor = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(professor);
		assertEquals(1, professorDao.getProfessors().size());
    }
    
    
    @Test
    public void testGetProfessorTotCorrecteNotNull() {
		Professor professor = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(professor);
		professor = professorDao.getProfessor(persona, assignatura).get(0);
		// Miram l'atribut persona del professor per comprovar que 
		// efectivament s'ha retornat una persona
		assertNotNull(professor.getPersona());
    }
    
    @Test
    public void testGetProfessorTotCorrecteValor() {
		Professor professor = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(professor);
		Professor p = professorDao.getProfessor(persona, assignatura).get(0);
		// Comprovam els àlies del professor inserit i el retornat pel
		// getProfessor()
		assertEquals(professor.getPersona().getAlies(), p.getPersona().getAlies());
    }
    
    @Test
    public void testGetProfessorPersonaMalament() {
		Professor professor = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(professor);
		
		// Definim una persona distinta
		persona = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
		personaDao.persistPersona(persona);
		
		List<Professor> professors = professorDao.getProfessor(persona, assignatura);
		assertEquals(0, professors.size());
    }
    
    @Test
    public void testGetProfessorAssignaturaMalament() {
		Professor professor = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(professor);
		
		// Definim una assignatura distinta
		assignatura = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO, ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura);
		
		List<Professor> professors = professorDao.getProfessor(persona, assignatura);
		assertEquals(0, professors.size());
    }
    
    @Test
    public void testGetProfessorsPersonaBuit() {
		List<Professor> professors = professorDao.getProfessors(persona);
		assertEquals(0, professors.size());
    }
    
    @Test
    public void testGetProfessorsPersona() {
		Assignatura assignatura2 = UtilTest.inserirAssignatura(persona, ASS2_CODI_REF, ASS2_ANY_ACADEMIC, ASS2_NOM, ASS2_DESCRIPCIO, ASS2_CLAU_PROFESSOR, ASS2_CLAU_ALUMNE, UtilTest.getDate(ASS2_DATA_ALTA));
		assignaturaDao.persistAssignatura(assignatura2);
		Professor p = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(p);
		Professor p2 = UtilTest.getProfessor(persona, assignatura2);
		professorDao.persistProfessor(p2);
		List<Professor> professors = professorDao.getProfessors(persona);
		assertEquals(2, professors.size());
    }
    
    @Test
    public void testGetProfessorsAssignaturaBuit() {
		List<Professor> professors = professorDao.getProfessors(assignatura);
		assertEquals(0, professors.size());
    }
    
    @Test
    public void testGetProfessorsAssignatura() {
		Persona persona2 = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
		personaDao.persistPersona(persona2);
		Professor p = UtilTest.getProfessor(persona, assignatura);
		professorDao.persistProfessor(p);
		Professor p2 = UtilTest.getProfessor(persona2, assignatura);
		professorDao.persistProfessor(p2);
		List<Professor> professors = professorDao.getProfessors(assignatura);
		assertEquals(2, professors.size());
    }
	
}
