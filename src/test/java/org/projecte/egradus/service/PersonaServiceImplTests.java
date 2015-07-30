package org.projecte.egradus.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.service.PersonaService;
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
public class PersonaServiceImplTests {
	
	@Autowired
	private PersonaService personaService;
	
	private static String PERSONA1_NOM             = "Miquel";
    private static String PERSONA1_PRIMER_LLINATGE = "Garcia";
    private static String PERSONA1_SEGON_LLINATGE  = "Balaguer";
    private static String PERSONA1_IDENTIFICADOR   = "X366459J";
    private static String PERSONA1_CORREU          = "m.garcia@correu.es";
    private static String PERSONA1_ALIES           = "miquelet";
    private static String PERSONA1_CLAU            = "claumiquel";
    private static String PERSONA1_DATA_NAIXAMENT  = "22/05/1985";
    private static String PERSONA1_DATA_ALTA       = "20/11/2013";
    
    private static String PERSONA2_NOM             = "Maria del Mar";
    private static String PERSONA2_PRIMER_LLINATGE = "Gual de Torrella";
    private static String PERSONA2_SEGON_LLINATGE  = "Pons";
    private static String PERSONA2_IDENTIFICADOR   = "41932268T";
    private static String PERSONA2_CORREU          = "mdm2013_5@correu.com";
    private static String PERSONA2_ALIES           = "mar_gual_pons";
    private static String PERSONA2_CLAU            = "claumar";
    private static String PERSONA2_DATA_NAIXAMENT  = "06/01/1990";
    private static String PERSONA2_DATA_ALTA       = "30/06/2013";
    
    
    private Persona persona;
    
    
    @Before
    public void initialize() {
    	persona = UtilTest.inserirPersona(PERSONA1_NOM, PERSONA1_PRIMER_LLINATGE, PERSONA1_SEGON_LLINATGE, PERSONA1_IDENTIFICADOR, PERSONA1_CORREU, PERSONA1_ALIES, PERSONA1_CLAU, UtilTest.getDate(PERSONA1_DATA_NAIXAMENT), UtilTest.getDate(PERSONA1_DATA_ALTA));
    	personaService.insereixPersona(persona);
    }
    
    @Test
    public void testInsereixPersona() {
    	assertNotNull(persona.getCodi());
    }
    
    @Test
    public void testGetPersonaPerAliesSensePersones() {
    	Persona persona = personaService.getPersona(PERSONA2_ALIES);
    	assertNull(persona);
    }
    
    @Test
    public void testGetPersonaPerAliesNul() {
    	Persona persona = personaService.getPersona(null);
    	assertNull(persona);
    }
    
    @Test
    public void testGetPersonaPerAliesIncorrecte() {
    	persona = personaService.getPersona(PERSONA2_ALIES);
    	assertNull(persona);
    }
    
    @Test
    public void testGetPersonaPerAliesCorrecte() {
    	Persona p = personaService.getPersona(PERSONA1_ALIES);
    	assertEquals(p.getCodi(), persona.getCodi());
    }
    
    @Test
    public void testGetPersonaPerAliesCorrecteMesDunaPersona() {
    	Persona persona2 = UtilTest.inserirPersona(PERSONA2_NOM, PERSONA2_PRIMER_LLINATGE, PERSONA2_SEGON_LLINATGE, PERSONA2_IDENTIFICADOR, PERSONA2_CORREU, PERSONA2_ALIES, PERSONA2_CLAU, UtilTest.getDate(PERSONA2_DATA_NAIXAMENT), UtilTest.getDate(PERSONA2_DATA_ALTA));
    	personaService.insereixPersona(persona2);
    	Persona p = personaService.getPersona(PERSONA2_ALIES);
    	assertEquals(p.getCodi(), persona2.getCodi());
    }
	
}
