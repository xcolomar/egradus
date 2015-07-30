package org.projecte.egradus.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.service.ProfessorService;
import org.projecte.egradus.service.RespostaPreguntaService;
import org.projecte.egradus.service.RespostaQuestionariService;
import org.projecte.egradus.utilities.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/correccio/")
public class CorreccioController {
	
	@Autowired
	private AssignaturaService assignaturaService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private RespostaPreguntaService respostaPreguntaService;
	
	@Autowired
	private RespostaQuestionariService respostaQuestionariService;
	
	
	@RequestMapping(value = "getPreguntesPendents.xml", method = RequestMethod.GET)
	public String getPreguntesPendentsCorregirXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Professor professor = professorService.getProfessor(persona, assignatura);
		List<RespostaPregunta> respostesPregunta = respostaPreguntaService.getPreguntesPerCorregir(professor);
		model.addAttribute("respostesPregunta", respostesPregunta);
		return "xml/pregunta/resposta/respostesPreguntaXml";
	}
	
	@RequestMapping(value = "getPreguntesCorregidesProfessor.xml", method = RequestMethod.GET)
	public String getPreguntesCorregidesXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Professor professor = professorService.getProfessor(persona, assignatura);
		List<RespostaPregunta> respostesPregunta = respostaPreguntaService.getPreguntesCorregides(professor);
		model.addAttribute("respostesPregunta", respostesPregunta);
		return "xml/pregunta/resposta/respostesPreguntaXml";
	}
	
	@RequestMapping(value = "corregeixPreguntaInici.do", method = RequestMethod.POST)
	public String iniciarCorreccioPregunta(@RequestParam Long codiRespostaPregunta, Model model) {
		RespostaPregunta rp = respostaPreguntaService.getRespostaPregunta(codiRespostaPregunta.intValue());
		rp = respostaPreguntaService.iniciarCorreccioPregunta(rp);
		model.addAttribute("rsp", rp);
		return "xml/pregunta/resposta/respostaPreguntaXml";
	}
	
	@RequestMapping(value = "corregeixPreguntaFi.do", method = RequestMethod.POST)
	public String finalitzarCorreccioPregunta(@RequestParam Long codiRespostaPregunta, 
			                                  @RequestParam (required = false) String textCorreccio,
			                                  @RequestParam (required = false) String textRevisio,
			                                  @RequestParam String nota, 
			                                  Model model,
			                                  Locale locale) 
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		RespostaPregunta rp = respostaPreguntaService.getRespostaPregunta(codiRespostaPregunta.intValue());
		
		// nota
		Float floatNota = null;
		if (nota != null && nota.length() > 0) {
			if (!nota.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorCorreccio", rb.getString("pregunta.correccio.error.format.nota"));
				model.addAttribute("rsp", rp);
				return "xml/pregunta/resposta/respostaPreguntaXml";
			}
			floatNota = Format.converteixAFloat(nota);
			if (floatNota < 0 || floatNota > 10) {
				model.addAttribute("errorCorreccio", rb.getString("pregunta.correccio.error.limits.nota"));
				model.addAttribute("rsp", rp);
				return "xml/pregunta/resposta/respostaPreguntaXml";
			}
		} else {
			model.addAttribute("errorCorreccio", rb.getString("pregunta.correccio.error.nota.buida"));
			model.addAttribute("rsp", rp);
			return "xml/pregunta/resposta/respostaPreguntaXml";
		}
		
		rp = respostaPreguntaService.finalitzarCorreccioPregunta(rp, textCorreccio, textRevisio, floatNota);
		model.addAttribute("rsp", rp);
		return "xml/pregunta/resposta/respostaPreguntaXml";
	}
	
	@RequestMapping(value = "getQuestionarisPendentsCorregir.xml", method = RequestMethod.GET)
	public String getQuestionarisPendentsCorregirXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Professor professor = professorService.getProfessor(persona, assignatura);
		List<RespostaQuestionari> respostesQuestionari = respostaQuestionariService.getQuestionarisPerCorregir(professor);
		model.addAttribute("respostesQuestionari", respostesQuestionari);
		return "xml/questionari/resposta/respostesQuestionariXml";
	}
	
	@RequestMapping(value = "getQuestionarisCorregitsProfessor.xml", method = RequestMethod.GET)
	public String getQuestionarisCorregitsXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Professor professor = professorService.getProfessor(persona, assignatura);
		List<RespostaQuestionari> respostesQuestionari = respostaQuestionariService.getQuestionarisCorregits(professor);
		model.addAttribute("respostesQuestionari", respostesQuestionari);
		return "xml/questionari/resposta/respostesQuestionariXml";
	}
	
	@RequestMapping(value = "corregeixQuestionariInici.do", method = RequestMethod.POST)
	public String iniciarCorreccioQuestionari(@RequestParam Long codiRespostaQuestionari, Model model) {
		RespostaQuestionari rq = respostaQuestionariService.getRespostaQuestionari(codiRespostaQuestionari.intValue());
		rq = respostaQuestionariService.iniciarCorreccioQuestionari(rq);
		model.addAttribute("rsp", rq);
		return "xml/questionari/resposta/respostaQuestionariXml";
	}
	
	@RequestMapping(value = "corregeixQuestionariFi.do", method = RequestMethod.POST)
	public String finalitzarCorreccioQuestionari(@RequestParam Long codiRespostaQuestionari, 
			                                     @RequestParam (required = false, value = "textCorreccio") List<String> textCorreccio,
			                                     @RequestParam (required = false, value = "textRevisio") List<String> textRevisio,
			                                     @RequestParam (required = false, value = "nota") List<String> nota,
			                                     Model model,
			                                     Locale locale) 
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		RespostaQuestionari rq = respostaQuestionariService.getRespostaQuestionari(codiRespostaQuestionari.intValue());
		
		// Retornam error quan hi ha alguna nota buida
		for (String stringNota : nota) {
			if (nota == null || stringNota.length() == 0) {
				model.addAttribute("errorCorreccio", rb.getString("questionari.correccio.error.nota.buida"));
				model.addAttribute("rsp", rq);
				return "xml/questionari/resposta/respostaQuestionariXml";
			}
		}
		
		List<Float> listNota = new ArrayList<Float>();
		for (String stringNota : nota) {
			Float floatNota = null;
			if (!stringNota.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorCorreccio", rb.getString("questionari.correccio.error.format.nota"));
				model.addAttribute("rsp", rq);
				return "xml/questionari/resposta/respostaQuestionariXml";
			}
			floatNota = Format.converteixAFloat(stringNota);
			if (floatNota < 0 || floatNota > 10) {
				model.addAttribute("errorCorreccio", rb.getString("questionari.correccio.error.limits.nota"));
				model.addAttribute("rsp", rq);
				return "xml/questionari/resposta/respostaQuestionariXml";
			}
			listNota.add(floatNota);
		}
		
		rq = respostaQuestionariService.finalitzarCorreccioQuestionari(rq, textCorreccio, textRevisio, listNota);
		model.addAttribute("rsp", rq);
		model.addAttribute("numRec", rq.getQuestionari().getNumPreguntesRec());
		model.addAttribute("numRr", rq.getQuestionari().getNumPreguntesRaonarResposta());
		return "xml/questionari/resposta/respostaQuestionariXml";
	}
	
}
