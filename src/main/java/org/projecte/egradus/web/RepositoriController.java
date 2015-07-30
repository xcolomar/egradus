package org.projecte.egradus.web;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.service.AlumneService;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.service.PreguntaService;
import org.projecte.egradus.service.ProfessorService;
import org.projecte.egradus.service.QuestionariService;
import org.projecte.egradus.service.RespostaPreguntaService;
import org.projecte.egradus.service.RespostaQuestionariService;
import org.projecte.egradus.utilities.Format;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/repositori/")
public class RepositoriController {
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private AlumneService alumneService;
	
	@Autowired
	private PreguntaService preguntaService;
	
	@Autowired
	private QuestionariService questionariService;
	
	@Autowired
	private RespostaPreguntaService respostaPreguntaService;
	
	@Autowired
	private RespostaQuestionariService respostaQuestionariService;
	
	@Autowired
	private AssignaturaService assignaturaService;
	
	
	/**
	 * Retorna les opcions preparades per afegir a la pregunta que s'ha de crear
	 * 
	 * @param pregunta				POJO pregunta
	 * @param opciotextESN			llistat dels textos de les opcions ESN
	 * @param opciocorrectaESN		llistat de Strings que són els índexos de les opcions ESN correctes
	 * @param opciotextES1 			llistat dels textos de les opcions ES1
	 * @param opciocorrectaES1		llistat de Strings d'una posició (un sol string) que és l'index de la opció ES1 correcta
	 * @param opciovertaderfals		String que val "V" si la pregunta VOF és Vertadera, i "F" si és falsa
	 * @param tipus					Tipus de pregunta (ES1, ESN, VOF o REC)
	 * @param rb                    ResourceBundle per agafar missatges dels fitxers idiomàtics
	 * @return
	 */
	private List<Opcio> afegirOpcions(Pregunta pregunta, List<String> opciotextESN, List<String> opciocorrectaESN, List<String> opciotextES1, List<String> opciocorrectaES1, String opciovertaderfals, String tipus, ResourceBundle rb) 
	{
		List<Opcio> opcions = new ArrayList<Opcio>();
		if (tipus.equals(Pregunta.TIPUS_ES1)) {
			for (int i = 0; i < opciotextES1.size(); i++) opcions.add(Util.inserirOpcio(pregunta, opciotextES1.get(i), Integer.parseInt(opciocorrectaES1.get(0)) == i, new Date()));
		} else if (tipus.equals(Pregunta.TIPUS_ESN)) {
			int j;
			for (int i = 0; i < opciotextESN.size(); i++) {
				j = 0;
				while (j < opciocorrectaESN.size() && Integer.parseInt(opciocorrectaESN.get(j)) != i) j++;
				opcions.add(Util.inserirOpcio(pregunta, opciotextESN.get(i), j < opciocorrectaESN.size() && Integer.parseInt(opciocorrectaESN.get(j)) == i, new Date()));
			}
		} else if (tipus.equals(Pregunta.TIPUS_VOF)) {
			opcions.add(Util.inserirOpcio(pregunta, rb.getString("pregunta.creacio.opcio.vertader"), opciovertaderfals.equals("V"), new Date()));
			opcions.add(Util.inserirOpcio(pregunta, rb.getString("pregunta.creacio.opcio.fals"), opciovertaderfals.equals("F"), new Date()));
		}
		return opcions;
	}
	
	/**
	 * Mètode privat que obté un llistat de POJO's Pregunta
	 * donada la llista de codis passada per paràmetre
	 * 
	 * @param codipreguntes
	 * @return
	 */
	private List<Pregunta> obtenirPreguntes(List<String> codipreguntes) {
		List<Pregunta> llistaPreguntes = new ArrayList<Pregunta>();
		if (codipreguntes != null && codipreguntes.size() > 0) {
			for (int i = 0; i < codipreguntes.size(); i++) {
				llistaPreguntes.add(preguntaService.getPregunta(Integer.parseInt(codipreguntes.get(i))));
			}
		}
		return llistaPreguntes;
	}
	
	/**
	 * Mètode privat que obté un Map de (codipregunta, pespregunta)
	 * donada les llistes de codis i de pesos de pregunta passats
	 * per paràmetre
	 * 
	 * @param codipreguntes
	 * @param pespreguntes
	 * @return
	 */
	private Map<Integer, Float> obtenirPesos(List<String> codipreguntes, List<String> pespreguntes) {
		Map<Integer, Float> pesos = new HashMap<Integer, Float>();
		if (codipreguntes != null && codipreguntes.size() > 0) {
			Integer key;
			Float value;
			for (int i = 0; i < codipreguntes.size(); i++) {
				// key: obtenim el codi de la pregunta (en Integer)
				key = new Integer(Integer.parseInt(codipreguntes.get(i)));
				
				// value: obtenim el pès de la pregunta en el qüestionari (en Float)
				value = null;
				if (pespreguntes.get(i) != null && pespreguntes.get(i).length() > 0)
					value = Format.converteixAFloat(pespreguntes.get(i));
				
				// inserim la tupla
				pesos.put(key, value);
			}
		}
		return pesos;
	}
	
	/**
	 * <p>Mètode privat que utilitzen els mètodes formCreaQuestionari() i formModificaQuestionari().</p>
	 * <p>Avalua si existeix alguna de les següents incompatibilitats entre els pesos que l'usuari ha 
	 * indicat per les preguntes:</p>
	 * <ol>
	 * 		<li>La suma de pesos és més gran que 1</li>
	 * 		<li>La suma de pesos és igual a 1 i el núm. de pesos és inferior al núm. de preguntes</li>
	 * 		<li>La suma de pesos és més petit que 1 i el núm. de pesos és igual al núm. de preguntes</li>
	 * 		<li>Algún dels pesos és més petit que 0</li>
	 * </ol>
	 * <p>En els casos 1 i 2 l'error a retornar és que la suma de pesos se passa d'1, mentre que en el cas 3
	 * la suma de pesos no arriba a 1. En el cas 4, l'error és que hi ha algún pès amb valor negatiu.</p>
	 * 
	 * @param codipreguntes
	 * @param pespreguntes
	 * @param rb
	 * @return
	 */
	private String errorPesPreguntes(List<String> codipreguntes, List<String> pespreguntes, ResourceBundle rb) {
		int numPreguntes = codipreguntes.size();
		int numPesos = 0;
		Float pesMaxim = 1f;
		Float sumaPesos = 0f;
		if (codipreguntes != null && codipreguntes.size() > 0) {
			Float pes;
			for (int i = 0; i < codipreguntes.size(); i++) {
				pes = 0f;
				if (pespreguntes.get(i) != null && pespreguntes.get(i).length() > 0) {
					if (!pespreguntes.get(i).matches(Format.FORMAT_DECIMAL)) return rb.getString("questionari.creacio.mis.error.pesos.format");
					pes = Format.converteixAFloat(pespreguntes.get(i));
					numPesos++;
				}
				if (pes < 0f) return MessageFormat.format(rb.getString("questionari.creacio.mis.error.pesos.negatiu"), codipreguntes.get(i));
				sumaPesos += pes;
			}
		}
		
		if (sumaPesos > pesMaxim) return rb.getString("questionari.creacio.mis.error.pesos.se.passa");
		if (sumaPesos.equals(pesMaxim) && numPesos < numPreguntes) return rb.getString("questionari.creacio.mis.error.pesos.se.passa.autocompletar");
		if (sumaPesos < pesMaxim && numPesos == numPreguntes) return rb.getString("questionari.creacio.mis.error.pesos.no.arriba"); 
		return null;
	}
	
	
	@RequestMapping(value = "creaPregunta.do", method = RequestMethod.POST)
	public String formCreaPregunta(@RequestParam int codiAssignatura,
								   @RequestParam String tipus,
								   @RequestParam (required = false) String enunciat,
								   @RequestParam (required = false) String dificultatteorica,
								   @RequestParam (required = false) String opciovertaderfals,
			                       @RequestParam (required = false, value = "raonarresposta", defaultValue="false") Boolean raonarResposta,
			                       @RequestParam (required = false, value = "opciotextES1")     List<String> opciotextES1,
			                       @RequestParam (required = false, value = "opciotextESN")     List<String> opciotextESN,
			                       @RequestParam (required = false, value = "opciocorrectaES1") List<String> opciocorrectaES1,
			                       @RequestParam (required = false, value = "opciocorrectaESN") List<String> opciocorrectaESN,
		    			   		   HttpSession session,
		      					   Locale locale,
		      					   Model model)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura);
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		
		// la pregunta ha de tenir enunciat
		if (enunciat == null || enunciat.isEmpty()) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error"));
			return "xml/pregunta/preguntaXml";
		}
		
		// el tipus de pregunta seleccionat ha de ser coherent amb si se duen opcions o no
		if ((tipus.equals(Pregunta.TIPUS_ES1) && (opciotextES1 == null || opciotextES1.size() == 0)) || 
			(tipus.equals(Pregunta.TIPUS_ESN) && (opciotextESN == null || opciotextESN.size() == 0))) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.opcions"));
			return "xml/pregunta/preguntaXml";
		}
		
		// alguna de les opcions que duu la pregunta ha de ser correcta
		if (tipus.equals(Pregunta.TIPUS_ES1) && (opciocorrectaES1 == null || opciocorrectaES1.size() == 0)) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.opcions.correctes.es1"));
			return "xml/pregunta/preguntaXml";
		}
		if (tipus.equals(Pregunta.TIPUS_ESN) && (opciocorrectaESN == null || opciocorrectaESN.size() == 0)) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.opcions.correctes.esn"));
			return "xml/pregunta/preguntaXml";
		}
		
		// dificultat teòrica
		Float dt = null;
		if (dificultatteorica != null && dificultatteorica.length() > 0) {
			if (!dificultatteorica.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.dificultat.teorica.format"));
				return "xml/pregunta/preguntaXml";
			}
			dt = Format.converteixAFloat(dificultatteorica);
			if (dt < 0 || dt > 1) {
				model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.dificultat.teorica"));
				return "xml/pregunta/preguntaXml";
			}
		}
		
		Persona persona = (Persona) session.getAttribute("persona");
		Professor professor = professorService.getProfessor(persona, assignatura);
		Pregunta pregunta = Util.inserirPregunta(professor, enunciat, dt, null, raonarResposta, new Date(), tipus, opciovertaderfals.equals("V"), Pregunta.ESTAT_EDITABLE);
		List<Opcio> opcions = afegirOpcions(pregunta, opciotextESN, opciocorrectaESN, opciotextES1, opciocorrectaES1, opciovertaderfals, tipus, rb);
		preguntaService.insereixPregunta(pregunta, opcions);
		model.addAttribute("pregunta", preguntaService.getPregunta(pregunta.getCodi()));
		return "xml/pregunta/preguntaXml";
	}
	
	@RequestMapping(value = "modificaPregunta.do", method = RequestMethod.POST)
	public String formModificaPregunta(@RequestParam int codiAssignatura,
									   @RequestParam int codiPregunta,
								   	   @RequestParam String tipus,
								   	   @RequestParam (required = false) String enunciat,
								   	   @RequestParam (required = false) String dificultatteorica,
								   	   @RequestParam (required = false, defaultValue = "valordefecte") String opciovertaderfals,
								   	   @RequestParam (required = false, value = "raonarresposta", defaultValue="false") Boolean raonarResposta,
								   	   @RequestParam (required = false, value = "opciotextES1modifica")     List<String> opciotextES1modifica,
								   	   @RequestParam (required = false, value = "opciotextESNmodifica")     List<String> opciotextESNmodifica,
								   	   @RequestParam (required = false, value = "opciocorrectaES1modifica") List<String> opciocorrectaES1modifica,
								   	   @RequestParam (required = false, value = "opciocorrectaESNmodifica") List<String> opciocorrectaESNmodifica,
								   	   HttpSession session,
								   	   Locale locale,
								   	   Model model)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura);
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		
		// la pregunta ha de tenir enunciat
		if (enunciat == null || enunciat.isEmpty()) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error"));
			return "xml/pregunta/preguntaXml";
		}
		
		// el tipus de pregunta seleccionat ha de ser coherent amb si se duen opcions o no
		if ((tipus.equals(Pregunta.TIPUS_ES1) && (opciotextES1modifica == null || opciotextES1modifica.size() == 0)) || 
			(tipus.equals(Pregunta.TIPUS_ESN) && (opciotextESNmodifica == null || opciotextESNmodifica.size() == 0))) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.opcions"));
			return "xml/pregunta/preguntaXml";
		}
		
		// alguna de les opcions que duu la pregunta ha de ser correcta
		if (tipus.equals(Pregunta.TIPUS_ES1) && (opciocorrectaES1modifica == null || opciocorrectaES1modifica.size() == 0)) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.opcions.correctes.es1"));
			return "xml/pregunta/preguntaXml";
		}
		if (tipus.equals(Pregunta.TIPUS_ESN) && (opciocorrectaESNmodifica == null || opciocorrectaESNmodifica.size() == 0)) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.opcions.correctes.esn"));
			return "xml/pregunta/preguntaXml";
		}
		
		// dificultat teòrica
		Float dt = null;
		if (dificultatteorica != null && dificultatteorica.length() > 0) {
			if (!dificultatteorica.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.dificultat.teorica.format"));
				return "xml/pregunta/preguntaXml";
			}
			dt = Format.converteixAFloat(dificultatteorica);
			if (dt < 0 || dt > 1) {
				model.addAttribute("errorPregunta", rb.getString("pregunta.creacio.mis.error.dificultat.teorica"));
				return "xml/pregunta/preguntaXml";
			}
		}
		
		Boolean vof = null;
		if (tipus.equals(Pregunta.TIPUS_VOF)) {
			if (opciovertaderfals.equals("V"))  vof = Boolean.TRUE;
			else								vof = Boolean.FALSE;
		}
		
		Persona persona = (Persona) session.getAttribute("persona");
		Professor professor = professorService.getProfessor(persona, assignatura);
		Pregunta pregunta = Util.inserirPregunta(professor, enunciat, dt, null, raonarResposta, new Date(), tipus, vof, Pregunta.ESTAT_EDITABLE);
		
		List<Opcio> opcions = new ArrayList<Opcio>();
		if (!tipus.equals(Pregunta.TIPUS_REC)) {
			opcions = afegirOpcions(pregunta, opciotextESNmodifica, opciocorrectaESNmodifica, opciotextES1modifica, opciocorrectaES1modifica, opciovertaderfals, tipus, rb);
		}
		pregunta = preguntaService.modificaPregunta(codiPregunta, pregunta, opcions);
		model.addAttribute("pregunta", preguntaService.getPregunta(pregunta.getCodi()));
		return "xml/pregunta/preguntaXml";
	}
	
	@RequestMapping(value = "pregunta.xml", method = RequestMethod.GET)
	public String getPreguntaXml(@RequestParam Long codiPregunta, Model model) {
		model.addAttribute("pregunta", preguntaService.getPregunta(codiPregunta.intValue()));
		return "xml/pregunta/preguntaXml";
	}
	
	@RequestMapping(value = "publicaPregunta.do", method = RequestMethod.POST)
	public String publicarPregunta(@RequestParam Long codiPregunta, Model model) {
		model.addAttribute("pregunta", preguntaService.publicaPregunta(codiPregunta.intValue()));
		return "xml/pregunta/preguntaXml";
	}
	
	@RequestMapping(value = "eliminaPregunta.do", method = RequestMethod.POST)
	public String eliminarPregunta(@RequestParam Long codiPregunta, Model model) {
		model.addAttribute("pregunta", preguntaService.eliminaPregunta(codiPregunta.intValue()));
		return "xml/pregunta/preguntaXml";
	}
	
	@RequestMapping(value = "cercaPreguntesFiltre.xml", method = RequestMethod.GET)
	public String formCercaPreguntaFiltresXml(@RequestParam Long codiAssignatura,
											  @RequestParam (required = false) String enunciat,
											  @RequestParam (required = false) String creador,
											  @RequestParam (required = false) String tipus,
											  @RequestParam (required = false) String estat,
											  @RequestParam (required = false) String difTeoMin,
											  @RequestParam (required = false) String difTeoMax,
											  @RequestParam (required = false, value = "ambrr") String ambrr,
											  @RequestParam (required = false, value = "senserr") String senserr,
											  @RequestParam (required = false) String dataInici,
											  @RequestParam (required = false) String dataFi,
											  @RequestParam (required = false) String difPraMin,
											  @RequestParam (required = false) String difPraMax,
											  Locale locale,
		    			   			          Model model,
		    			   			          HttpSession session)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Persona persona = (Persona) session.getAttribute("persona");
		Professor professor = professorService.getProfessor(persona, assignatura);
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		
		// dates d'inici i de fi
		Date datini;
		Date datfin;
		if (dataInici.length() == 0) {datini = null;} else {datini = Util.getDate(dataInici);}
		if (dataFi.length() == 0)    {datfin = null;} else {datfin = Util.getDate(dataFi);}
		
		// dificultat teòrica mínima
		Float dtmin = null;
		if (difTeoMin.length() > 0) {
			if (!difTeoMin.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorPregunta", rb.getString("repositori.preguntes.mis.error.format.dificultat.teorica.minima"));
				model.addAttribute("preguntes", preguntaService.getPreguntes());
				return "xml/pregunta/preguntesXml";
			}
			dtmin = Format.converteixAFloat(difTeoMin);
		}
		
		// dificultat teòrica màxima
		Float dtmax = null;
		if (difTeoMax.length() > 0) {
			if (!difTeoMax.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorPregunta", rb.getString("repositori.preguntes.mis.error.format.dificultat.teorica.maxima"));
				model.addAttribute("preguntes", preguntaService.getPreguntes());
				return "xml/pregunta/preguntesXml";
			}
			dtmax = Format.converteixAFloat(difTeoMax);
		}
		
		// dificultat pràctica mínima
		Float dpmin = null;
		if (difPraMin.length() > 0) {
			if (!difPraMin.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorPregunta", rb.getString("repositori.preguntes.mis.error.format.dificultat.practica.minima"));
				model.addAttribute("preguntes", preguntaService.getPreguntes());
				return "xml/pregunta/preguntesXml";
			}
			dpmin = Format.converteixAFloat(difPraMin);
		}
		
		// dificultat pràctica màxima
		Float dpmax = null;
		if (difPraMax.length() > 0) {
			if (!difPraMax.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorPregunta", rb.getString("repositori.preguntes.mis.error.format.dificultat.practica.maxima"));
				model.addAttribute("preguntes", preguntaService.getPreguntes());
				return "xml/pregunta/preguntesXml";
			}
			dpmax = Format.converteixAFloat(difPraMax);
		}
		
		// raonar resposta
		Boolean rr = null;
		if (ambrr != null && senserr != null) {
			model.addAttribute("errorPregunta", rb.getString("repositori.preguntes.mis.error.raonar.resposta"));
			model.addAttribute("preguntes", preguntaService.getPreguntes());
			return "xml/pregunta/preguntesXml";
		}
		if (ambrr != null && senserr == null) rr = Boolean.TRUE;
		if (ambrr == null && senserr != null) rr = Boolean.FALSE;
		
		
		// tipus
		String tip = null;
		if (tipus.equals(Pregunta.TIPUS_ES1) || tipus.equals(Pregunta.TIPUS_ESN) || 
				tipus.equals(Pregunta.TIPUS_VOF) || tipus.equals(Pregunta.TIPUS_REC)) tip = tipus;
		
		// estat
		String est = null;
		if (estat.equals(Pregunta.ESTAT_EDITABLE) || estat.equals(Pregunta.ESTAT_PUBLIC)) est = estat;
		
		model.addAttribute("preguntes", preguntaService.getPreguntes(datini, datfin, enunciat, creador, dtmin, dtmax, dpmin, dpmax, rr, tip, est, professor.getCodi().intValue()));
		return "xml/pregunta/preguntesXml";
	}
	
	@RequestMapping(value = "descripcioPregunta.xml", method = RequestMethod.GET)
	public String descripcioPreguntaXml(@RequestParam Long codiPregunta, @RequestParam Long codiAssignatura, Model model) {
		model.addAttribute("pregunta", preguntaService.getPregunta(codiPregunta.intValue()));
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		return "xml/pregunta/preguntaXml";
	}
	
	@RequestMapping(value = "enviaPregunta.do", method = RequestMethod.POST)
	public String formEnviaPregunta(@RequestParam Long codiPregunta,
								    @RequestParam Long codiAssignatura,
			                        @RequestParam (required = false, value = "alumnes") List<String> alumnes,
			                        @RequestParam (required = false, value = "anonima") String anonima,
			                        Model model,
		    			   		    HttpSession session,
		    			   		    Locale locale)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Professor assignador = professorService.getProfessor(persona, assignatura);
		
		// Si s'ha seleccionat algún alumne, cream una llista amb els alumnes. Sino, mostram missatge d'error
		if (alumnes == null) {
			model.addAttribute("errorPregunta", rb.getString("pregunta.enviament.error.no.alumnes"));
		} else {
			List<Alumne> llistaAlumnes = new ArrayList<Alumne>();
			for (int i = 0; i<alumnes.size(); i++) llistaAlumnes.add(alumneService.getAlumne(Integer.parseInt(alumnes.get(i))));
			
			Boolean booleanAnonima = Boolean.TRUE;
			if (anonima == null) booleanAnonima = Boolean.FALSE;
			
			respostaPreguntaService.enviaPregunta(codiPregunta.intValue(), assignador, llistaAlumnes, booleanAnonima);
		}
		model.addAttribute("pregunta", preguntaService.getPregunta(codiPregunta.intValue()));
		return "xml/pregunta/preguntaXml";
	}
	
	@RequestMapping(value = "modificaQuestionari.do", method = RequestMethod.POST)
	public String formModificaQuestionari(@RequestParam int codiAssignatura,
										  @RequestParam int codiQuestionari,
									      @RequestParam (required = false) String nom,
									      @RequestParam (required = false) String dificultatteorica,
									      @RequestParam (required = false) String descripcio,
									      @RequestParam (required = false, value = "codipreguntes") List<String> codipreguntes,
									      @RequestParam (required = false, value = "pespreguntes")  List<String> pespreguntes,
							 		      HttpSession session,
									      Locale locale,
									      Model model)
	{	
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura);
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		
		// el qüestionari ha de tenir nom
		if (nom == null || nom.isEmpty()) {
			model.addAttribute("errorQuestionari", rb.getString("questionari.creacio.mis.error"));
			return "xml/questionari/questionariXml";
		}
		
		// dificultat teòrica
		Float dt = null;
		if (dificultatteorica != null && dificultatteorica.length() > 0) {
			if (!dificultatteorica.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorQuestionari", rb.getString("questionari.creacio.mis.error.dificultat.teorica.format"));
				return "xml/questionari/questionariXml";
			}
			dt = Format.converteixAFloat(dificultatteorica);
			if (dt < 0 || dt > 1) {
				model.addAttribute("errorQuestionari", rb.getString("questionari.creacio.mis.error.dificultat.teorica"));
				return "xml/questionari/questionariXml";
			}
		}
		
		// Avaluam si és necessari retornar algún error d'incompatibilitat entre els pesos indicats
		String errorPesos = null;
		if (pespreguntes != null && pespreguntes.size() > 0) errorPesos = errorPesPreguntes(codipreguntes, pespreguntes, rb);
		if (errorPesos != null) {
			model.addAttribute("errorQuestionari", errorPesos);
			return "xml/questionari/questionariXml";
		}
		
		List<Pregunta> preguntes = obtenirPreguntes(codipreguntes);
		
		Map<Integer, Float> pesos = null;
		if (pespreguntes != null && pespreguntes.size() > 0) pesos = obtenirPesos(codipreguntes, pespreguntes);
		
		Persona persona = (Persona) session.getAttribute("persona");
		Professor professor = professorService.getProfessor(persona, assignatura);
		Questionari questionari = Util.inserirQuestionari(professor, nom, descripcio, Questionari.ESTAT_EDITABLE, dt, new Date());
		questionari = questionariService.modificaQuestionari(codiQuestionari, questionari, preguntes, pesos);
		model.addAttribute("questionari", questionari);
		
		return "xml/questionari/questionariXml";
	}
	
	@RequestMapping(value = "questionari.xml", method = RequestMethod.GET)
	public String getQuestionariXml(@RequestParam Long codiQuestionari, Model model) {
		model.addAttribute("questionari", questionariService.getQuestionari(codiQuestionari.intValue()));
		return "xml/questionari/questionariXml";
	}
	
	@RequestMapping(value = "creaQuestionari.do", method = RequestMethod.POST)
	public String formCreaQuestionari(@RequestParam int codiAssignatura,
								      @RequestParam (required = false) String nom,
								      @RequestParam (required = false) String dificultatteorica,
								      @RequestParam (required = false) String descripcio,
								      @RequestParam (required = false, value = "codipreguntes") List<String> codipreguntes,
								      @RequestParam (required = false, value = "pespreguntes")  List<String> pespreguntes,
		    			   		      HttpSession session,
		      					      Locale locale,
		      					      Model model)
	{	
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura);
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		
		// el qüestionari ha de tenir nom
		if (nom == null || nom.isEmpty()) {
			model.addAttribute("errorQuestionari", rb.getString("questionari.creacio.mis.error"));
			return "xml/questionari/questionariXml";
		}
		
		// dificultat teòrica
		Float dt = null;
		if (dificultatteorica != null && dificultatteorica.length() > 0) {
			if (!dificultatteorica.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorQuestionari", rb.getString("questionari.creacio.mis.error.dificultat.teorica.format"));
				return "xml/questionari/questionariXml";
			}
			dt = Format.converteixAFloat(dificultatteorica);
			if (dt < 0 || dt > 1) {
				model.addAttribute("errorQuestionari", rb.getString("questionari.creacio.mis.error.dificultat.teorica"));
				return "xml/questionari/questionariXml";
			}
		}
		
		// Avaluam si és necessari retornar algún error d'incompatibilitat entre els pesos indicats
		String errorPesos = null;
		if (pespreguntes != null && pespreguntes.size() > 0) errorPesos = errorPesPreguntes(codipreguntes, pespreguntes, rb);
		
		if (errorPesos != null) {
			model.addAttribute("errorQuestionari", errorPesos);
			return "xml/questionari/questionariXml";
		}
				
		List<Pregunta> preguntes = obtenirPreguntes(codipreguntes);
		
		Map<Integer, Float> pesos = null;
		if (pespreguntes != null && pespreguntes.size() > 0) pesos = obtenirPesos(codipreguntes, pespreguntes);
		
		Persona persona = (Persona) session.getAttribute("persona");
		Professor professor = professorService.getProfessor(persona, assignatura);
		Questionari questionari = Util.inserirQuestionari(professor, nom, descripcio, Questionari.ESTAT_EDITABLE, dt, new Date());
		questionariService.insereixQuestionari(questionari, preguntes, pesos);
		model.addAttribute("questionari", questionariService.getQuestionari(questionari.getCodi()));
		
		return "xml/questionari/questionariXml";
	}
	
	@RequestMapping(value = "cercaQuestionarisFiltre.xml", method = RequestMethod.GET)
	public String formCercaQuestionariFiltresXml(@RequestParam Long codiAssignatura,
										   	     @RequestParam (required = false) String nom,
										   	     @RequestParam (required = false) String descripcio,
										   	     @RequestParam (required = false) String creador,
										   	     @RequestParam (required = false) String difTeoMin,
										   	     @RequestParam (required = false) String difTeoMax,
										   	     @RequestParam (required = false) String dataInici,
										   	     @RequestParam (required = false) String dataFi,
										   	     @RequestParam (required = false) String difPraMin,
										   	     @RequestParam (required = false) String difPraMax,
										   	     @RequestParam (required = false) String estat,
										   	     Locale locale,
										   	     Model model,
										   	     HttpSession session)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Professor professor = professorService.getProfessor(persona, assignatura);
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		
		// dates d'inici i de fi
		Date datini;
		Date datfin;
		if (dataInici.length() == 0) {datini = null;} else {datini = Util.getDate(dataInici);}
		if (dataFi.length() == 0)    {datfin = null;} else {datfin = Util.getDate(dataFi);}
		
		// dificultat teòrica mínima
		Float dtmin = null;
		if (difTeoMin.length() > 0) {
			if (!difTeoMin.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorQuestionari", rb.getString("repositori.questionaris.mis.error.format.dificultat.teorica.minima"));
				model.addAttribute("questionaris", questionariService.getQuestionaris());
				return "xml/questionari/questionarisXml";
			}
			dtmin = Format.converteixAFloat(difTeoMin);
		}
		
		// dificultat teòrica màxima
		Float dtmax = null;
		if (difTeoMax.length() > 0) {
			if (!difTeoMax.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorQuestionari", rb.getString("repositori.questionaris.mis.error.format.dificultat.teorica.maxima"));
				model.addAttribute("questionaris", questionariService.getQuestionaris());
				return "xml/questionari/questionarisXml";
			}
			dtmax = Format.converteixAFloat(difTeoMax);
		}
		
		// dificultat pràctica mínima
		Float dpmin = null;
		if (difPraMin.length() > 0) {
			if (!difPraMin.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorQuestionari", rb.getString("repositori.questionaris.mis.error.format.dificultat.practica.minima"));
				model.addAttribute("questionaris", questionariService.getQuestionaris());
				return "xml/questionari/questionarisXml";
			}
			dpmin = Format.converteixAFloat(difPraMin);
		}
		
		// dificultat pràctica màxima
		Float dpmax = null;
		if (difPraMax.length() > 0) {
			if (!difPraMax.matches(Format.FORMAT_DECIMAL)) {
				model.addAttribute("errorQuestionari", rb.getString("repositori.questionaris.mis.error.format.dificultat.practica.maxima"));
				model.addAttribute("questionaris", questionariService.getQuestionaris());
				return "xml/questionari/questionarisXml";
			}
			dpmax = Format.converteixAFloat(difPraMax);
		}
		
		// estat
		String est = null;
		if (estat.equals(Questionari.ESTAT_EDITABLE) || estat.equals(Questionari.ESTAT_PUBLIC)) est = estat;
		
		// numero de preguntes
		Integer numPreguntes = null;
		
		model.addAttribute("questionaris", questionariService.getQuestionaris(datini, datfin, nom, descripcio, creador, est, dtmin, dtmax, dpmin, dpmax, numPreguntes, professor.getCodi().intValue()));
		return "xml/questionari/questionarisXml";
	}
	
	@RequestMapping(value = "descripcioQuestionari.xml", method = RequestMethod.GET)
	public String descripcioQuestionariXml(@RequestParam Long codiQuestionari, @RequestParam Long codiAssignatura, Model model) {
		model.addAttribute("questionari", questionariService.getQuestionari(codiQuestionari.intValue()));
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		List<Alumne> alumnes = alumneService.getAlumnes(assignatura);
		if (alumnes == null) model.addAttribute("numAlumnes", 0);
		else model.addAttribute("numAlumnes", alumnes.size());
		return "xml/questionari/questionariXml";
	}
	
	@RequestMapping(value = "enviaQuestionari.do", method = RequestMethod.POST)
	public String formEnviaQuestionari(@RequestParam Long codiQuestionari,
								       @RequestParam Long codiAssignatura,
			                           @RequestParam (required = false, value = "alumnes") List<String> alumnes,
			                           @RequestParam (required = false, value = "anonim") String anonim,
			                           Model model,
		    			   		       HttpSession session,
		    			   		       Locale locale)
	{	
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura.intValue());
		Professor assignador = professorService.getProfessor(persona, assignatura);
		
		// Si s'ha seleccionat algún alumne, cream una llista amb els alumnes. Sino, mostram missatge d'error
		if (alumnes == null) {
			model.addAttribute("errorQuestionari", rb.getString("questionari.enviament.error.no.alumnes"));
		} else {
			List<Alumne> llistaAlumnes = new ArrayList<Alumne>();
			for (int i = 0; i<alumnes.size(); i++) llistaAlumnes.add(alumneService.getAlumne(Integer.parseInt(alumnes.get(i))));
			
			Boolean booleanAnonim = Boolean.TRUE;
			if (anonim == null) booleanAnonim = Boolean.FALSE;
			
			respostaQuestionariService.enviaQuestionari(codiQuestionari.intValue(), assignador, llistaAlumnes, booleanAnonim);
		}
		model.addAttribute("questionari", questionariService.getQuestionari(codiQuestionari.intValue()));
		return "xml/questionari/questionariXml";
	}
	
	@RequestMapping(value = "publicaQuestionari.do", method = RequestMethod.POST)
	public String publicarQuestionari(@RequestParam Long codiQuestionari, Model model) {
		model.addAttribute("questionari", questionariService.publicaQuestionari(codiQuestionari.intValue()));
		return "xml/questionari/questionariXml";
	}
	
	@RequestMapping(value = "eliminaQuestionari.do", method = RequestMethod.POST)
	public String eliminarQuestionari(@RequestParam Long codiQuestionari, Model model) {
		model.addAttribute("questionari", questionariService.eliminaQuestionari(codiQuestionari.intValue()));
		return "xml/questionari/questionariXml";
	}
	
}
