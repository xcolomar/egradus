package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.service.PersonaService;
import org.projecte.egradus.service.ProfessorService;
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
public class ProfessorServiceImplTests {
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private AssignaturaService assignaturaService;
	
	// Assignatures
	private static String NOM1            = "Intel·ligència Artificial";
	private static String DESCRIPCIO1     = "Descripció de l'Assignatura de 4º Curs de l'Enginyeria Informàtica de la Universitat de les Illes Balears.";
	private static String CLAU_PROFESSOR1 = "clau_inteli_artifi";
	private static String CLAU_ALUMNE1    = "clau_intart";
	private static String DATA_ALTA1      = "22/12/2013";
	private static String ANY_ACADEMIC1   = "2013-14";
	private static String CODI_REF1       = "INTELIART";
	
	private static String NOM2            = "Càlcul diferencial II";
	private static String DESCRIPCIO2     = null;
	private static String CLAU_PROFESSOR2 = "clau_calcul_dif2";
	private static String CLAU_ALUMNE2    = "clau_caldif2";
	private static String DATA_ALTA2      = "14/01/2014";
	private static String ANY_ACADEMIC2   = "2013-14";
	private static String CODI_REF2       = "CALCDIF";
	
	// Persones
	private static String PER_NOM              = "Miquel";
    private static String PER_PRIMER_LLINATGE  = "Garcia";
    private static String PER_CORREU           = "m.garcia@correu.es";
    private static String PER_ALIES            = "miquelet";
    private static String PER_CLAU             = "claumiquel";
    private static String PER_DATA_ALTA        = "20/11/2013";
    
    private static String PER2_NOM             = "Joan";
    private static String PER2_PRIMER_LLINATGE = "Savater";
    private static String PER2_CORREU          = "j.savat85@correu.es";
    private static String PER2_ALIES           = "johnny";
    private static String PER2_CLAU            = "jonyclau";
    private static String PER2_DATA_ALTA       = "18/02/2014";
	
    
    private Persona persona;
    private Assignatura assignatura;
    
    @Before
    public void initialize() {
    	persona = UtilTest.inserirPersona(PER_NOM, PER_PRIMER_LLINATGE, PER_CORREU, PER_ALIES, PER_CLAU, UtilTest.getDate(PER_DATA_ALTA));
    	personaService.insereixPersona(persona);
    	assignatura = UtilTest.inserirAssignatura(persona, CODI_REF1, ANY_ACADEMIC1, NOM1, DESCRIPCIO1, CLAU_PROFESSOR1, CLAU_ALUMNE1, UtilTest.getDate(DATA_ALTA1));
    	assignaturaService.insereixAssignatura(assignatura);
    	// No fa falta crear un nou professor,
    	// per defecte, el mètode de servei de creació de l'Assignatura ja insereix la persona com a professor de assignatura
    }
    
    @Test
    public void testInsereixProfessor() {
    	// Assignam un professor adicional: persona2
    	Persona persona2 = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(persona2);
    	Professor professor = UtilTest.getProfessor(persona2, assignatura);
    	professorService.insereixProfessor(professor);
    	assertEquals(2, assignatura.getProfessors().size());
    }
    
    @Test
    public void testGetProfessorNotNull() {
    	Professor p = professorService.getProfessor(persona, assignatura);
    	assertNotNull(p);
    }
    
    @Test
    public void testGetProfessorParametresNul() {
    	Professor professor = professorService.getProfessor(null, null);
    	assertNull(professor);
    }
    
    @Test
    public void testGetProfessorPersonaIncorrecta() {
    	Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(p);
    	Professor pro = professorService.getProfessor(p, assignatura);
    	assertNull(pro);
    }
    
    @Test
    public void testGetProfessorAssignaturaIncorrecta() {
    	Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(p);
    	Assignatura a = UtilTest.inserirAssignatura(p, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2, CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
    	assignaturaService.insereixAssignatura(a);
    	Professor pro = professorService.getProfessor(persona, a);
    	assertNull(pro);
    }
    
    @Test
    public void testEsProfessorVertader() {
    	assertTrue(professorService.esProfessor(persona, assignatura));
    }
    
    @Test
    public void testEsProfessorFalsAssignatura() {
    	Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(p);
    	Assignatura a = UtilTest.inserirAssignatura(p, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2, CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
    	assignaturaService.insereixAssignatura(a);
    	assertFalse(professorService.esProfessor(persona, a));
    }
    
    @Test
    public void testEsProfessorFalsPersona() {
    	Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(p);
    	assertFalse(professorService.esProfessor(p, assignatura));
    }
    
    @Test
    public void testEsProfessorParametresNul() {
    	assertFalse(professorService.esProfessor(null, null));
    }
    
    @Test
    public void testGetProfessorsDunaAssignaturaNula() {
    	List<Professor> professors = professorService.getProfessors(null);
    	assertNull(professors);
    }
    
    @Test
    public void testGetProfessorsDunaAssignaturaPle() {
    	Persona p = UtilTest.inserirPersona(PER2_NOM, PER2_PRIMER_LLINATGE, PER2_CORREU, PER2_ALIES, PER2_CLAU, UtilTest.getDate(PER2_DATA_ALTA));
    	personaService.insereixPersona(p);
    	
    	// A part del professor "persona", s'afegeix un altre:
    	// professor "p"
    	Professor professor2 = UtilTest.getProfessor(p, assignatura);
    	professorService.insereixProfessor(professor2);
    	List<Professor> professors = professorService.getProfessors(assignatura);
    	assertEquals(2, professors.size());
    }
    
    @Test
    public void testGetProfessorsDunaAssignaturaErronia() {
    	// Afefim una altra assignatura "assignatura2"
    	Assignatura assignatura2 = UtilTest.inserirAssignatura(persona, CODI_REF2, ANY_ACADEMIC2, NOM2, DESCRIPCIO2, CLAU_PROFESSOR2, CLAU_ALUMNE2, UtilTest.getDate(DATA_ALTA2));
    	assignaturaService.insereixAssignatura(assignatura2);
    	List<Professor> professors = professorService.getProfessors(assignatura2);
    	assertEquals(1, professors.size());
    }
	
}