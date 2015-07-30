package org.projecte.egradus.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
import org.projecte.egradus.service.AlumneService;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.service.PreguntaService;
import org.projecte.egradus.service.RespostaPreguntaService;
import org.projecte.egradus.service.RespostaQuestionariService;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/contestacio/")
public class ContestacioController {
	
	@Autowired
	private RespostaQuestionariService respostaQuestionariService;
	
	@Autowired
	private RespostaPreguntaService respostaPreguntaService;
	
	@Autowired
	private AssignaturaService assignaturaService;
	
	@Autowired
	private PreguntaService preguntaService;
	
	@Autowired
	private AlumneService alumneService;
	
	
	@RequestMapping(value = "getPreguntesAlumne.xml", method = RequestMethod.GET)
	public String getPreguntesAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaPregunta> respostesPregunta = respostaPreguntaService.getPreguntesPerRespondre(alumne);
		model.addAttribute("respostesPregunta", respostesPregunta);
		return "xml/pregunta/resposta/respostesPreguntaXml";
	}
	
	@RequestMapping(value = "getPreguntesPendentsCorregirAlumne.xml", method = RequestMethod.GET)
	public String getPreguntesPendentsCorregirAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaPregunta> respostesPregunta = respostaPreguntaService.getPreguntesPerCorregir(alumne);
		model.addAttribute("respostesPregunta", respostesPregunta);
		return "xml/pregunta/resposta/respostesPreguntaXml";
	}
	
	@RequestMapping(value = "getPreguntesPendentsRevisarAlumne.xml", method = RequestMethod.GET)
	public String getPreguntesPendentsRevisarAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaPregunta> respostesPregunta = respostaPreguntaService.getPreguntesPerRevisar(alumne);
		model.addAttribute("respostesPregunta", respostesPregunta);
		return "xml/pregunta/resposta/respostesPreguntaXml";
	}
	
	@RequestMapping(value = "getPreguntesContestadesAlumne.xml", method = RequestMethod.GET)
	public String getPreguntesContestadesAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaPregunta> respostesPregunta = respostaPreguntaService.getPreguntesContestades(alumne);
		model.addAttribute("respostesPregunta", respostesPregunta);
		return "xml/pregunta/resposta/respostesPreguntaXml";
	}
	
	@RequestMapping(value = "consultaResposta.do", method = RequestMethod.POST)
	public String consultarResposta(@RequestParam Long codiRespostaPregunta, Model model) {
		model.addAttribute("rsp", respostaPreguntaService.getRespostaPregunta(codiRespostaPregunta.intValue()));
		return "xml/pregunta/resposta/respostaPreguntaXml";
	}
	
	@RequestMapping(value = "contestaPreguntaInici.do", method = RequestMethod.POST)
	public String iniciarContestacioPregunta(@RequestParam Long codiRespostaPregunta, Model model) {
		RespostaPregunta rp = respostaPreguntaService.getRespostaPregunta(codiRespostaPregunta.intValue());
		model.addAttribute("rsp", respostaPreguntaService.iniciarContestacioPregunta(rp));
		return "xml/pregunta/resposta/respostaPreguntaXml";
	}
	
	@RequestMapping(value = "contestaPreguntaFi.do", method = RequestMethod.POST)
	public String finalitzarContestacioPregunta(@RequestParam Long codiRespostaPregunta, 
												@RequestParam (required = false, value = "idopcionsmarcades") List<String> idOpcionsMarcades,
												@RequestParam (required = false) String respostaREC,
												@RequestParam (required = false) String raonarresposta,
											    Model model) 
	{	
		RespostaPregunta rp = respostaPreguntaService.getRespostaPregunta(codiRespostaPregunta.intValue());
		List<OpcioResposta> opcionsMarcades = new ArrayList<OpcioResposta>();
		if (idOpcionsMarcades != null) {
			for (int i = 0; i < idOpcionsMarcades.size(); i++) {
				opcionsMarcades.add(Util.inserirOpcioResposta(preguntaService.getOpcio(Integer.parseInt(idOpcionsMarcades.get(i))), rp));
			}
		}
		rp = respostaPreguntaService.finalitzarContestacioPregunta(rp, raonarresposta, respostaREC, opcionsMarcades);
		model.addAttribute("rsp", rp);
		return "xml/pregunta/resposta/respostaPreguntaXml";
	}
	
	@RequestMapping(value = "getQuestionarisAlumne.xml", method = RequestMethod.GET)
	public String getQuestionarisAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaQuestionari> respostesQuestionari = respostaQuestionariService.getQuestionarisPerRespondre(alumne);
		model.addAttribute("respostesQuestionari", respostesQuestionari);
		return "xml/questionari/resposta/respostesQuestionariXml";
	}
	
	@RequestMapping(value = "getQuestionarisPendentsCorregirAlumne.xml", method = RequestMethod.GET)
	public String getQuestionarisPendentsCorregirAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaQuestionari> respostesQuestionari = respostaQuestionariService.getQuestionarisPerCorregir(alumne);
		model.addAttribute("respostesQuestionari", respostesQuestionari);
		return "xml/questionari/resposta/respostesQuestionariXml";
	}
	
	@RequestMapping(value = "getQuestionarisPendentsRevisarAlumne.xml", method = RequestMethod.GET)
	public String getQuestionarisPendentsRevisarAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaQuestionari> respostesQuestionari = respostaQuestionariService.getQuestionarisPerRevisar(alumne);
		model.addAttribute("respostesQuestionari", respostesQuestionari);
		return "xml/questionari/resposta/respostesQuestionariXml";
	}
	
	@RequestMapping(value = "getQuestionarisContestatsAlumne.xml", method = RequestMethod.GET)
	public String getQuestionarisContestatsAlumneXml(@RequestParam Long codiAssignatura, HttpSession session, Model model) {
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Alumne alumne = alumneService.getAlumne(persona, assignatura);
		List<RespostaQuestionari> respostesQuestionari = respostaQuestionariService.getQuestionarisContestats(alumne);
		model.addAttribute("respostesQuestionari", respostesQuestionari);
		return "xml/questionari/resposta/respostesQuestionariXml";
	}
	
	@RequestMapping(value = "consultaRespostaQuestionari.do", method = RequestMethod.POST)
	public String consultarRespostaQuestionari(@RequestParam Long codiRespostaQuestionari, Model model) {
		RespostaQuestionari rq = respostaQuestionariService.getRespostaQuestionari(codiRespostaQuestionari.intValue());
		model.addAttribute("rsp", rq);
		model.addAttribute("numRec", rq.getQuestionari().getNumPreguntesRec());
		model.addAttribute("numRr", rq.getQuestionari().getNumPreguntesRaonarResposta());
		return "xml/questionari/resposta/respostaQuestionariXml";
	}
	
	@RequestMapping(value = "contestaQuestionariInici.do", method = RequestMethod.POST)
	public String iniciarContestacioQuestionari(@RequestParam Long codiRespostaQuestionari, Model model) {
		RespostaQuestionari rq = respostaQuestionariService.getRespostaQuestionari(codiRespostaQuestionari.intValue());
		rq = respostaQuestionariService.iniciarContestacioQuestionari(rq);
		model.addAttribute("rsp", rq);
		return "xml/questionari/resposta/respostaQuestionariXml";
	}
	
	@RequestMapping(value = "contestaQuestionariFi.do", method = RequestMethod.POST)
	public String finalitzarContestacioQuestionari(@RequestParam Long codiRespostaQuestionari, 
												   @RequestParam (required = false, value = "idopcionsmarcades") List<String> idOpcionsMarcades,
												   @RequestParam (required = false, value = "respostaREC") List<String> respostaREC,
												   @RequestParam (required = false, value = "raonarresposta") List<String> raonarresposta,
											       Model model) 
	{
		RespostaQuestionari rq = respostaQuestionariService.getRespostaQuestionari(codiRespostaQuestionari.intValue());
		
		// Obtenim les preguntes associades al qüestionari de la resposta-questionari.
		// De cada pregunta que haguem obtingut, hem de generar una resposta-pregunta.
		// Les resposta-preguntes generades hauran d'assignar-se a la resposta-questionari.
		List<RespostaPregunta> llistaRespostaPreguntes = new ArrayList<RespostaPregunta>();
		List<OpcioResposta> opcionsMarcades = null;
		int idOpcions = 0;
		int idRaonarResposta = 0;
		int idRespostaRec = 0;
		for (RespostaPregunta rp : rq.getRespostaPreguntes()) {
			Pregunta p = rp.getPregunta();
			if (p.getTipus().equals(Pregunta.TIPUS_REC)) {
				// Preguntes REC: Necessita del text de la resposta
				if (respostaREC != null && respostaREC.size() > 0)
					rp.setTextResposta(respostaREC.get(idRespostaRec++));
			} else {
				// Preguntes ES1, ESN i VOF: Necessiten de les opcions
				if (idOpcionsMarcades != null && idOpcionsMarcades.size() > 0) {
					opcionsMarcades = new ArrayList<OpcioResposta>();
					Opcio opcio = null;
					Boolean sortida = Boolean.FALSE;
					// La resposta-pregunta requereix de les opcio-respostes que s'hagin marcat. Per això les
					// recorrem i les afegim a la resposta-pregunta
					while (!sortida && idOpcions < idOpcionsMarcades.size()) {
						opcio = preguntaService.getOpcio(Integer.parseInt(idOpcionsMarcades.get(idOpcions)));
						if (p.getCodi().equals(opcio.getPregunta().getCodi())) {
							OpcioResposta or = Util.inserirOpcioResposta(opcio, rp);
							opcionsMarcades.add(or);
							idOpcions++;
						} else sortida = Boolean.TRUE;
					}
					rp.setOpcionsMarcades(opcionsMarcades);
				}
			}
			
			// Raonar resposta (Sempre i quan la pregunta ho requereixi)
			if (p.getRaonarResposta() && raonarresposta != null && raonarresposta.size() > 0) 
				rp.setTextRaonarResposta(raonarresposta.get(idRaonarResposta++));
			
			// Afegim la resposta-pregunta que acabam de generar
			llistaRespostaPreguntes.add(rp);
		}
		
		rq = respostaQuestionariService.finalitzarContestacioQuestionari(rq, llistaRespostaPreguntes);
		model.addAttribute("rsp", rq);
		return "xml/questionari/resposta/respostaQuestionariXml";
	}
	
}
