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

public class PersonaTests {
	
	private Validator validador;
	
	
	private Persona persona;
	
	
    @Before
    public void inicialitza() {
    	persona = new Persona();
    	
    	ValidatorFactory f = Validation.buildDefaultValidatorFactory();
    	validador = f.getValidator();
    }
    
    @Test
    public void testPersonaNomNotEmpty() {
    	persona.setNom(null);
    	persona.setPrimerLlinatge("llinatge1");
    	persona.setCorreu("correu@correu.es");
    	persona.setAlies("alies");
    	persona.setClau("clau");
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(1, errors.size());
    }
    
    @Test
    public void testPersonaPrimerLlinatgeNotEmpty() {
    	persona.setNom("nom");
    	persona.setPrimerLlinatge(null);
    	persona.setCorreu("correu@correu.es");
    	persona.setAlies("alies");
    	persona.setClau("clau");
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(1, errors.size());
    }
    
    @Test
    public void testPersonaCorreuNotEmpty() {
    	persona.setNom("nom");
    	persona.setPrimerLlinatge("llinatge1");
    	persona.setCorreu(null);
    	persona.setAlies("alies");
    	persona.setClau("clau");
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(1, errors.size());
    }
    
    @Test
    public void testPersonaAliesNotEmpty() {
    	persona.setNom("nom");
    	persona.setPrimerLlinatge("llinatge1");
    	persona.setCorreu("correu@correu.es");
    	persona.setAlies(null);
    	persona.setClau("clau");
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(1, errors.size());
    }
    
    @Test
    public void testPersonaClauNotEmpty() {
    	persona.setNom("nom");
    	persona.setPrimerLlinatge("llinatge1");
    	persona.setCorreu("correu@correu.es");
    	persona.setAlies("alies");
    	persona.setClau(null);
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(1, errors.size());
    }
    
    @Test
    public void testPersonaOk() {
    	persona.setNom("nom");
    	persona.setPrimerLlinatge("llinatge1");
    	persona.setCorreu("correu@correu.es");
    	persona.setAlies("alies");
    	persona.setClau("clau");
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(0, errors.size());
    }
    
    @Test
    public void testPersonaTotsElsParametresOk() {
    	persona.setNom("nom");
    	persona.setPrimerLlinatge("llinatge1");
    	persona.setSegonLlinatge("llinatge2");
    	persona.setIdentificador("identificador");
    	persona.setCorreu("correu@correu.es");
    	persona.setAlies("alies");
    	persona.setClau("clau");
    	persona.setDataNaixament(UtilTest.getDate("01/01/1991"));
    	persona.setDataAlta(new Date());
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(0, errors.size());
    }
    
    @Test
    public void testPersonaFormatCorreuIncorrecte() {
    	persona.setNom("nom");
    	persona.setPrimerLlinatge("llinatge1");
    	persona.setCorreu("correu.es");
    	persona.setAlies("alies");
    	persona.setClau("clau");
    	Set<ConstraintViolation<Persona>> errors = validador.validate(persona);
    	assertEquals(1, errors.size());
    }

}