package org.projecte.egradus.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;

public class UtilTest {
	
	/**
	 * Funció local que retorna una relació pregunta-questionari
	 * 
	 * @param pregunta
	 * @param questionari
	 * @param pes
	 * @return
	 */
	public static PreguntaQuestionari inserirPreguntaQuestionari(Pregunta pregunta, Questionari questionari, Float pes) {
		PreguntaQuestionari pq = new PreguntaQuestionari();
		pq.setPregunta(pregunta);
		pq.setQuestionari(questionari);
		pq.setPes(pes);
		return pq;
	}
	
	/**
	 * Funció local que retorna un qüestionari
	 * 
	 * @param creador
	 * @param nom
	 * @param descripcio
	 * @param dificultatTeorica
	 * @param dificultatPractica
	 * @param estat
	 * @param dataAlta
	 * @return
	 */
	public static Questionari inserirQuestionari(Professor creador, String nom, String descripcio, Float dificultatTeorica, Float dificultatPractica, String estat, Date dataAlta) {
		Questionari q = new Questionari();
		q.setNom(nom);
		q.setDescripcio(descripcio);
		q.setDificultatTeorica(dificultatTeorica);
		q.setDificultatPractica(dificultatPractica);
		q.setDataAlta(dataAlta);
		q.setEstat(estat);
		q.setCreador(creador);
		return q;
	}
	
	/**
	 * Funció local que retorna una opcio-resposta
	 * 
	 * @param opcio
	 * @param respostaPregunta
	 * @return org.projecte.egradus.domain.OpcioResposta
	 */
	public static OpcioResposta inserirOpcioResposta(Opcio opcio, RespostaPregunta respostaPregunta) {
		OpcioResposta opcioResposta = new OpcioResposta();
		opcioResposta.setOpcio(opcio);
		opcioResposta.setRespostaPregunta(respostaPregunta);
		return opcioResposta;
	}
	
	/**
	 * Funció local que retorna una opció
	 * 
	 * @param pregunta
	 * @param text
	 * @param correcta
	 * @param dataAlta
	 * @return org.projecte.egradus.domain.Opcio
	 */
	public static Opcio inserirOpcio(Pregunta pregunta, String text, Boolean correcta, Date dataAlta) {
		Opcio opcio = new Opcio();
		opcio.setText(text);
		opcio.setCorrecta(correcta);
		opcio.setDataAlta(dataAlta);
		opcio.setPregunta(pregunta);
		return opcio;
	}
	
	/**
	 * Funció local que retorna una pregunta
	 * 
	 * @param professor
	 * @param enunciat
	 * @param dificultatTeorica
	 * @param dificultatPractica
	 * @param raonarResposta
	 * @param dataAlta
	 * @param tipus
	 * @param vofVertader
	 * @param estat
	 * @return org.projecte.egradus.domain.Pregunta
	 */
	public static Pregunta inserirPregunta(Professor professor, String enunciat, Float dificultatTeorica, Float dificultatPractica, Boolean raonarResposta, Date dataAlta, String tipus, Boolean vofVertader, String estat) {
		Pregunta pregunta = new Pregunta();
		pregunta.setEnunciat(enunciat);
		pregunta.setDificultatTeorica(dificultatTeorica);
		pregunta.setDificultatPractica(dificultatPractica);
		
		if (tipus.equals(Pregunta.TIPUS_REC)) pregunta.setRaonarResposta(Boolean.FALSE);
		else pregunta.setRaonarResposta(raonarResposta);
		
		pregunta.setDataAlta(dataAlta);
		pregunta.setTipus(tipus);
		if (vofVertader != null) pregunta.setVofVertader(vofVertader); 
		pregunta.setCreador(professor);
		pregunta.setEstat(estat);
		return pregunta;
	}
	
	/**
	 * Funció local que retorna una assignatura i reb
	 * com a paràmetre la persona que l'ha creada
	 * @param persona
	 * @param codiReferencia
	 * @param anyAcademic
	 * @param nom
	 * @param descripcio
	 * @param clauProfessor
	 * @param clauAlumne
	 * @param dataAlta
	 * @return org.projecte.egradus.domain.Assignatura
	 */
	public static Assignatura inserirAssignatura(Persona persona, String codiReferencia, String anyAcademic, String nom, String descripcio, String clauProfessor, String clauAlumne, Date dataAlta){
		Assignatura assignatura = new Assignatura();
		assignatura.setCodiReferencia(codiReferencia);
		assignatura.setAnyAcademic(anyAcademic);
		assignatura.setNom(nom);
		assignatura.setDescripcio(descripcio);
		assignatura.setClauProfessor(clauProfessor);
		assignatura.setClauAlumne(clauAlumne);
		assignatura.setDataAlta(dataAlta);
		assignatura.setCreador(persona);
		return assignatura;
	}
	
	/**
     * Funció local que retorna un Professor
     * @param persona
     * @param assignatura
     * @return org.projecte.egradus.domain.Professor
     */
    public static Professor getProfessor(Persona persona, Assignatura assignatura) {
    	Professor professor = new Professor();
		professor.setPersona(persona);
		professor.setAssignatura(assignatura);
		return professor;
    }
    
    /**
     * Funció local que retorna un Alumne
     * @param persona
     * @param assignatura
     * @return org.projecte.egradus.domain.Alumne
     */
    public static Alumne getAlumne(Persona persona, Assignatura assignatura) {
		Alumne alumne = new Alumne();
		alumne.setPersona(persona);
		alumne.setAssignatura(assignatura);
		return alumne;
	}
    
	/**
	 * Funció local que converteix una cadena de text
	 * en una data tipus java.util.Date
	 * @param stringDate
	 * 				format "dd/MM/yyyy"
	 * @return java.util.Date
	 */
	public static Date getDate(String stringDate){
		Date date = null;
		try {
//        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        	date = format.parse(stringDate);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
		return date;
	}
	
	/**
	 * Funció local que converteix una cadena de text
	 * en una data tipus java.util.Date
	 * @param stringDate
	 * 				format "dd/MM/yyyy hh:mm:ss"
	 * @return java.util.Date
	 */
	public static Date getDateTime(String stringDate){
		Date date = null;
		try {
        	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        	date = format.parse(stringDate);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
		return date;
	}
	
	/**
	 * Funció local que retorna una persona 
	 * (utilitzant tots els paràmetres)
	 * @param nom
	 * @param primerLlinatge
	 * @param segonLlinatge
	 * @param identificador
	 * @param correu
	 * @param alies
	 * @param clau
	 * @param dataNaixament
	 * @param dataAlta
	 * @return org.projecte.egradus.domain.Persona
	 */
	public static Persona inserirPersona(String nom, String primerLlinatge, String segonLlinatge, String identificador, String correu, String alies, String clau, Date dataNaixament, Date dataAlta){
		Persona persona = new Persona();
		persona.setNom(nom);
		persona.setPrimerLlinatge(primerLlinatge);
		persona.setSegonLlinatge(segonLlinatge);
		persona.setIdentificador(identificador);
		persona.setCorreu(correu);
		persona.setAlies(alies);
		persona.setClau(clau);
		persona.setDataNaixament(dataNaixament);
		persona.setDataAlta(dataAlta);
		return persona;
	}
	
	/**
	 * Funció local que retorna una persona 
	 * (utilitzant menys paràmetres)
	 * @param nom
	 * @param primerLlinatge
	 * @param correu
	 * @param alies
	 * @param clau
	 * @param dataAlta
	 * @return org.projecte.egradus.domain.Persona
	 */
	public static Persona inserirPersona(String nom, String primerLlinatge, String correu, String alies, String clau, Date dataAlta){
		Persona persona = new Persona();
		persona.setNom(nom);
		persona.setPrimerLlinatge(primerLlinatge);
		persona.setCorreu(correu);
		persona.setAlies(alies);
		persona.setClau(clau);
		persona.setDataAlta(dataAlta);
		return persona;
	}
}
