package org.projecte.egradus.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.service.AlumneService;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.service.EstadistiquesService;
import org.projecte.egradus.service.PreguntaService;
import org.projecte.egradus.service.ProfessorService;
import org.projecte.egradus.service.QuestionariService;
import org.projecte.egradus.service.RespostaQuestionariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/estadistiques/")
public class EstadistiquesController {

	@Autowired
	AssignaturaService assignaturaService;
	
	@Autowired
	AlumneService alumneService;
	
	@Autowired
	PreguntaService preguntaService;
	
	@Autowired
	QuestionariService questionariService;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	EstadistiquesService estadistiquesService;
	
	@Autowired
	RespostaQuestionariService respostaQuestionariService;
	
	/**
	 * Estadístiques dels alumnes sobre les preguntes
	 * Agafa les resposta-preguntes d'un alumne
	 * 
	 * @param codiAssignatura
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getPreguntes.xml", method = RequestMethod.GET)
	public String getPreguntesXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<Map<String, Object>> estadistiques = estadistiquesService.getPreguntesAlumne(alumne);
		model.addAttribute("estadistiques", estadistiques);
		return "xml/estadistiques/alumnePreguntesXml";
	}
	
	/**
	 * Estadístiques dels alumnes sobre els qüestionaris
	 * Agafa les resposta-questionaris d'un alumne
	 * 
	 * @param codiAssignatura
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getQuestionaris.xml", method = RequestMethod.GET)
	public String getQuestionarisXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<Map<String, Object>> estadistiques = estadistiquesService.getQuestionarisAlumne(alumne);
		model.addAttribute("estadistiques", estadistiques);
		return "xml/estadistiques/alumneQuestionarisXml";
	}
	
	/**
	 * Estadístiques dels alumnes sobre els qüestionaris
	 * Agafa una resposta-questionari amb el detall de totes
	 * les seves resposta-preguntes, per veure les estadístiques
	 * detallades d'un qüestionari.
	 * 
	 * @param codiRq
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getDetallRespostaQuestionari.xml", method = RequestMethod.GET)
	public String getDetallRespostaQuestionariXml(@RequestParam Long codiRq, Model model) {
		List<Map<String, Object>> estadistiques = estadistiquesService.getEstadistiquesDetallRespostaQuestionari(codiRq.intValue());
		model.addAttribute("estadistiques", estadistiques);
		return "xml/estadistiques/detallRespostaQuestionariXml";
	}
	
	/**
	 * Estadístiques dels professors sobre una pregunta
	 * 
	 * Agafa el HashMap d'estadístiques d'una pregunta en una 
	 * assignatura i a tot Egradus en referència a assignacions
	 * directes que hagi tengut aquella pregunta.
	 * 
	 * Agafa, també, el HashMap d'estadístiques d'una pregunta 
	 * en una assignatura i a tot Egradus en referència a assignacions
	 * a través de qüestionaris que hagi tengut aquella pregunta.
	 * 
	 * @param codiAssignatura
	 * @param codiPregunta
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/pregunta.xml", method = RequestMethod.GET)
	public String getPreguntesProfessorXml(@RequestParam Long codiAssignatura, @RequestParam Long codiPregunta, HttpSession session, Model model) {
		Map<String, Object> assignacioDirecta = estadistiquesService.getEstadistiquesProfessorPreguntaAssignacioDirecta(codiPregunta.intValue(), codiAssignatura.intValue());
		Map<String, Object> assignacioQuestionari = estadistiquesService.getEstadistiquesProfessorPreguntaAssignacioQuestionari(codiPregunta.intValue(), codiAssignatura.intValue());
		model.addAttribute("estDir", assignacioDirecta);
		model.addAttribute("estQst", assignacioQuestionari);
		return "xml/estadistiques/preguntaXml";
	}
	
	/**
	 * Estadístiques dels professors sobre el conjunt de les preguntes
	 * que han participat en l'assignatura
	 * 
	 * Agafa el HashMap d'estadístiques del conjunt de preguntes en una 
	 * assignatura i a tot Egradus en referència a assignacions
	 * directes que hagin tengut aquelles preguntes.
	 * 
	 * Agafa, també, el HashMap d'estadístiques del conjunt de preguntes
	 * en una assignatura i a tot Egradus en referència a assignacions
	 * a través de qüestionaris que hagin tengut aquella preguntes.
	 * 
	 * @param codiAssignatura
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/conjuntPreguntes.xml", method = RequestMethod.GET)
	public String getPreguntesAssignaturaProfessorXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Map<String, Object> assignacioDirecta = estadistiquesService.getEstadistiquesProfessorConjuntPreguntesAssignacioDirecta(codiAssignatura.intValue());
		Map<String, Object> assignacioQuestionari = estadistiquesService.getEstadistiquesProfessorConjuntPreguntesAssignacioQuestionari(codiAssignatura.intValue());
		model.addAttribute("estDir", assignacioDirecta);
		model.addAttribute("estQst", assignacioQuestionari);
		return "xml/estadistiques/preguntaXml";
	}
	
	/**
	 * Estadístiques dels professors sobre les preguntes
	 * Agafa les preguntes que han participat a l'assignatura
	 * 
	 * @param codiAssignatura
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getPreguntesDisponibles.xml", method = RequestMethod.GET)
	public String getPreguntesDisponiblesXml(@RequestParam Long codiAssignatura, Model model) {
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		model.addAttribute("preguntes", preguntaService.getPreguntes(assignatura));
		return "xml/pregunta/preguntesXml";
	}
	
	/**
	 * Estadístiques dels professors sobre un qüestionari
	 * 
	 * Agafa el HashMap d'estadístiques d'una pregunta en una 
	 * assignatura i a tot Egradus.
	 * 
	 * @param codiAssignatura
	 * @param codiQuestionari
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/questionari.xml", method = RequestMethod.GET)
	public String getQuestionarisProfessorXml(@RequestParam Long codiAssignatura, @RequestParam Long codiQuestionari, HttpSession session, Model model) {
		Map<String, Object> estadistiques = estadistiquesService.getEstadistiquesProfessorQuestionari(codiQuestionari.intValue(), codiAssignatura.intValue());
		model.addAttribute("est", estadistiques);
		return "xml/estadistiques/questionariXml";
	}
	
	/**
	 * Estadístiques dels professors sobre els qüestionaris
	 * Agafa el HashMap d'estadístiques de tots els qüestionaris 
	 * que han participat a una assignatura
	 * 
	 * @param codiAssignatura
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/conjuntQuestionaris.xml", method = RequestMethod.GET)
	public String getQuestionarisAssignaturaProfessorXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Map<String, Object> estadistiques = estadistiquesService.getEstadistiquesProfessorConjuntQuestionaris(codiAssignatura.intValue());
		model.addAttribute("est", estadistiques);
		return "xml/estadistiques/questionariXml";
	}
	
	/**
	 * Estadístiques dels professors sobre els qüestionaris
	 * Agafa els qüestionaris que han participat a l'assignatura
	 * 
	 * @param codiAssignatura
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getQuestionarisDisponibles.xml", method = RequestMethod.GET)
	public String getQuestionarisDisponiblesXml(@RequestParam Long codiAssignatura, Model model) {
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		model.addAttribute("questionaris", questionariService.getQuestionaris(assignatura));
		return "xml/questionari/questionarisXml";
	}
	
	/**
	 * Estadístiques dels professors sobre els alumnes
	 * Agafa els alumnes de l'assignatura
	 * 
	 * @param codiAssignatura
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getAlumnes.xml", method = RequestMethod.GET)
	public String getAlumnesXml(@RequestParam Long codiAssignatura, Model model) {
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		model.addAttribute("alumnes", alumneService.getAlumnes(assignatura));
		return "xml/assignatura/alumnesXml";
	}
	
	/**
	 * Estadístiques dels professors sobre els alumnes
	 * Agafa les resposta-preguntes de un alumne
	 * 
	 * @param codiAlumne
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getPreguntesAlumne.xml", method = RequestMethod.GET)
	public String getPreguntesAlumneProfessorXml(@RequestParam Long codiAlumne, HttpSession session, Model model) {
		Alumne alumne = alumneService.getAlumne(codiAlumne.intValue());
		List<Map<String, Object>> estadistiques = estadistiquesService.getPreguntesAlumne(alumne);
		model.addAttribute("estadistiques", estadistiques);
		return "xml/estadistiques/alumnePreguntesXml";
	}
	
	/**
	 * Estadístiques dels professors sobre els alumnes
	 * Agafa les resposta-questionaris de un alumne
	 * 
	 * @param codiAlumne
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getQuestionarisAlumne.xml", method = RequestMethod.GET)
	public String getQuestionarisAlumneProfessorXml(@RequestParam Long codiAlumne, HttpSession session, Model model) {
		Alumne alumne = alumneService.getAlumne(codiAlumne.intValue());
		List<Map<String, Object>> estadistiques = estadistiquesService.getQuestionarisAlumne(alumne);
		model.addAttribute("estadistiques", estadistiques);
		return "xml/estadistiques/alumneQuestionarisXml";
	}
	
	/**
	 * Estadístiques dels professors sobre els alumnes
	 * Agafa les estadístiques de les preguntes sobre
	 * el conjunt d'alumnes de l'assignatura
	 * 
	 * @param codiAssignatura
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getPreguntesConjuntAlumnes.xml", method = RequestMethod.GET)
	public String getPreguntesConjuntAlumnesXml(@RequestParam Long codiAssignatura, HttpSession session, Model model, Locale locale) {
		List<Map<String, Object>> estadistiques = estadistiquesService.getEstadistiquesProfessorPreguntes(codiAssignatura.intValue(), locale);
		model.addAttribute("estadistiques", estadistiques);
		return "xml/estadistiques/professorConjuntAlumnesPreguntesXml";
	}
	
	/**
	 * Estadístiques dels professors sobre els alumnes
	 * Agafa les estadístiques dels qüestionaris sobre
	 * el conjunt d'alumnes de l'assignatura
	 * 
	 * @param codiAssignatura
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getQuestionarisConjuntAlumnes.xml", method = RequestMethod.GET)
	public String getQuestionarisConjuntAlumnesXml(@RequestParam Long codiAssignatura, HttpSession session, Model model, Locale locale) {
		List<Map<String, Object>> estadistiques = estadistiquesService.getEstadistiquesProfessorQuestionaris(codiAssignatura.intValue(), locale);
		model.addAttribute("estadistiques", estadistiques);
		return "xml/estadistiques/professorConjuntAlumnesQuestionarisXml";
	}
	
	/**
	 * Estadístiques dels professors sobre el conjunt d'alumnes
	 * en referència a un qüestionari
	 * Agafa un qüestionari amb les estadístiques detallades
	 * de les seves preguntes.
	 * 
	 * @param codiQuestionari
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "professor/getEstadistiquesQuestionari.xml", method = RequestMethod.GET)
	public String getEstadistiquesQuestionariXml(@RequestParam Long codiQuestionari, Model model) {
		model.addAttribute("estadistiques", estadistiquesService.getEstadistiquesConjuntAlumnesDetallQuestionari(codiQuestionari.intValue()));
		return "xml/estadistiques/detallQuestionariXml";
	}
	
}
