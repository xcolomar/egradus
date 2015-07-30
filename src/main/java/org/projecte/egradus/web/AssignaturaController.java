package org.projecte.egradus.web;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.service.AlumneService;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.service.ProfessorService;
import org.projecte.egradus.utilities.Format;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

@Controller
@RequestMapping(value = "/assignatura/")
public class AssignaturaController {
	
	@Autowired
	private AssignaturaService assignaturaService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private AlumneService alumneService;
	
	
	// Només feim ús d'aquest mètode quan utilitzam c:import
	// Si empram jsp:include estam diguent directament que vagi a la vista "X" sense que faci falta
	// passar per Controlador
	@RequestMapping(value = "creacio", method = RequestMethod.GET)
	public String creaAssignatura(){
		return "assignatura/creacio";
	}
	
	
	@RequestMapping(value = "crea.do", method = RequestMethod.POST)
	public String formCreaAssignaturaNou(@RequestParam (required = false) String codiReferencia,
										 @RequestParam (required = false) String nom,
										 @RequestParam (required = false) String anyAcademic,
		    			   			     @RequestParam (required = false) String descripcio,
		    			   			     @RequestParam (required = false) String clauProfessor,
		    			   			     @RequestParam (required = false) String clauAlumne,
		    			   			     HttpSession session,  
		      							 Locale locale, 
		      							 Model model)
	{	
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		
		if (codiReferencia == null || codiReferencia.isEmpty()) {
			model.addAttribute("errorAssignatura", rb.getString("assignatura.creacio.mis.error.codi.referencia.buit"));
		} else if (nom == null || nom.isEmpty()) {
			model.addAttribute("errorAssignatura", rb.getString("assignatura.creacio.mis.error.nom.buit"));
		} else if (anyAcademic == null || anyAcademic.isEmpty()) {
			model.addAttribute("errorAssignatura", rb.getString("assignatura.creacio.mis.error.any.academic.buit"));
		} else if (!clauProfessor.isEmpty() && !clauAlumne.isEmpty() && clauProfessor.equals(clauAlumne)) {
			model.addAttribute("errorAssignatura", rb.getString("assignatura.creacio.mis.error.claus.iguals"));
		} else {
			// En aquest punt, l'any Acadèmic (així com la resta de camps obligatoris) no està buit, però pot venir
			// amb dos possibles errors més: 
			// - Error de sintaxi -> 2f49vks4 (no respecta el format YYYY-YY)
			// - Error semàntic -> 2014-13 (per exemple)
			if (!anyAcademic.matches(Format.FORMAT_ANY_ACADEMIC)) {
				model.addAttribute("errorAssignatura", rb.getString("assignatura.creacio.mis.error.any.academic.format"));
				return "xml/assignatura/descripcioXml";
			}
			if (!Format.anyAcademicCorrecte(anyAcademic)) {
				model.addAttribute("errorAssignatura", rb.getString("assignatura.creacio.mis.error.any.academic.semantica"));
				return "xml/assignatura/descripcioXml";
			}
			
			Persona persona = (Persona) session.getAttribute("persona");
			Assignatura assignatura = Util.inserirAssignatura(persona, codiReferencia, nom, anyAcademic, descripcio, Strings.emptyToNull(clauProfessor), Strings.emptyToNull(clauAlumne), new Date());
			assignaturaService.insereixAssignatura(assignatura);
			model.addAttribute("assignatura", assignatura);
			model.addAttribute("professors", professorService.getProfessors(assignatura));
		}
		return "xml/assignatura/descripcioXml";
	}
	
	// Només feim ús d'aquest mètode quan utilitzam c:import
	// Si empram jsp:include estam diguent directament que vagi a la vista "X" sense que faci falta
	// passar per Controlador
	@RequestMapping(value = "cerca", method = RequestMethod.GET)
	public String cercaAssignatura(Model model, Locale locale){
		model.addAttribute("idioma",locale.getLanguage());
		return "assignatura/cerca";
	}
	
	@RequestMapping(value = "cercaAssignatures.xml", method = RequestMethod.GET)
	public String cercaAssignaturesXml(Model model) {
		model.addAttribute("assignatures", assignaturaService.getAssignatures());
		return "xml/assignatura/cercaXml";
	}
	
	@RequestMapping(value = "cerca.xml", method = RequestMethod.POST) // ho he canviat de GET a POST
	public String formCercaAssignaturaXml(@RequestParam (required = false) String codiReferencia,
										  @RequestParam (required = false) String nomAssignatura,
										  @RequestParam (required = false) String anyAcademic,
										  @RequestParam (required = false) String creador,
			  							  @RequestParam (required = false) String dataInici,
									      @RequestParam (required = false) String dataFi,
		    			   			      @RequestParam (required = false, value = "clauprosi") String clauProfessorSi,
		    			   			      @RequestParam (required = false, value = "claualusi") String clauAlumneSi,
		    			   			      @RequestParam (required = false, value = "clauprono") String clauProfessorNo,
		    			   			      @RequestParam (required = false, value = "claualuno") String clauAlumneNo,
		    			   			      Model model)
	{
		Date datini;
		Date datfin;
		if (dataInici.length() == 0) {datini = null;} else {datini = Util.getDate(dataInici);}
		if (dataFi.length() == 0)    {datfin = null;} else {datfin = Util.getDate(dataFi);}
		
		// L'objectiu d'aquest troç de codi és que: clauProfessor i clauAlumne prenguin el
		// valor de "S", "N" o null per saber si s'ha de filtrar "Amb clau", "Sense clau" o
		// no s'ha de filtrar respectivament:
		String clauProfessor = null;
		String clauAlumne = null;
		if (clauProfessorSi == null && clauProfessorNo != null) clauProfessor = "N";
		if (clauProfessorSi != null && clauProfessorNo == null) clauProfessor = "S";
		if (clauProfessorSi != null && clauProfessorNo != null) clauProfessor = "S";
		if (clauAlumneSi == null && clauAlumneNo != null) clauAlumne = "N";
		if (clauAlumneSi != null && clauAlumneNo == null) clauAlumne = "S";
		if (clauAlumneSi != null && clauAlumneNo != null) clauAlumne = "S";
		
		model.addAttribute("assignatures", assignaturaService.getAssignatures(datini, datfin, clauProfessor, clauAlumne, codiReferencia, nomAssignatura, anyAcademic, creador));
		return "xml/assignatura/cercaXml";
	}
	
	@RequestMapping(value = "llista", method = RequestMethod.GET)
	public String lateral(Model model, HttpSession session){
		Persona persona = (Persona) session.getAttribute("persona");
		if (persona != null) {
			model.addAttribute("assignatures", assignaturaService.getAssignatures(persona));
		}
		return "assignatura/llista";
	}
	
	@RequestMapping(value = "descripcio.xml", method = RequestMethod.GET)
	public String descripcioXml(@RequestParam  Long codi, Model model){
		Assignatura assignatura = assignaturaService.getAssignatura(codi.intValue());
		// Dins 'assignatura' tenim, també, el llistat de professors
		model.addAttribute("assignatura", assignatura);
		return "xml/assignatura/descripcioXml";
	}
	
	@RequestMapping(value = "material.xml", method = RequestMethod.GET)
	public String materialXml(@RequestParam  Long codi, Model model, HttpSession session){
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codi.intValue());
		model.addAttribute("assignatura", assignatura);
		if (alumneService.esAlumne(persona, assignatura)) model.addAttribute("esAlumne", "S");
		if (professorService.esProfessor(persona, assignatura)) model.addAttribute("esAlumne", "N");
		return "xml/assignatura/descripcioXml";
	}
	
	@RequestMapping(value = "alumnes.xml", method = RequestMethod.GET)
	public String retornaAlumnes(@RequestParam Long codi, Model model) {
		Assignatura assignatura = assignaturaService.getAssignatura(codi.intValue());
		model.addAttribute("alumnes", assignatura.getAlumnes());
		return "xml/assignatura/alumnesXml";
	}
	
	/**
	 * <p>Mètode per unir-se a una assignatura, opció en la que tenim:
	 * 2 Botons de Submit i seleccionam el Submit de professor</p>
	 * <p>és un cas trivial: tractam la inserció (o no) d'un nou professor</p>
	 * @param codiAssignatura
	 * @param clau
	 * @param session
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "unirseProfessor.do", method = RequestMethod.POST)
	public String formUnirseAssignaturaPro(@RequestParam int codiAssignatura,
			      						   @RequestParam (required = false) String clau,
			      						   HttpSession session,  
			      						   Locale locale, 
			      						   Model model)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura);
		
		// Intentam unir-mos com a professor
		unirProfessorAssignatura(persona, assignatura, rb, model);
		
		model.addAttribute("assignatura", assignatura);
		model.addAttribute("professors", professorService.getProfessors(assignatura));
		return "xml/assignatura/descripcioXml";
	}
	
	/**
	 * <p>Mètode per unir-se a una assignatura, opció en la que tenim:
	 * 2 Botons de Submit i seleccionam el Submit de alumne</p>
	 * <p>és un cas trivial: tractam la inserció (o no) d'un nou alumne</p>
	 * @param codiAssignatura
	 * @param session
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "unirseAlumne.do", method = RequestMethod.POST)
	public String formUnirseAssignaturaAluDo(@RequestParam int codiAssignatura,
			      							 @RequestParam (required = false) String clau,
			      							 HttpSession session,  
			      							 Locale locale, 
			      							 Model model)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura);
		
		// Intentam unir-mos com a alumne
		unirAlumneAssignatura(persona, assignatura, rb, model);
		
		model.addAttribute("assignatura", assignatura);
		model.addAttribute("professors", professorService.getProfessors(assignatura));
		return "xml/assignatura/descripcioXml";
	}
	
	/**
	 * Mètode per unir-se a una assignatura, opció en la que tenim:
	 * 1 Botó de Submit
	 * Casos que es poden donar:
	 *   · (1) Pot ser que requereixi clau tant per professors com per alumnes
	 *   · (2) Pot ser que només requereixi clau per professors
	 *   · (3) Pot ser que només requereixi clau per a alumnes
	 * Aquestes tres opcions són tractades dins el mètode.
	 * @param codiAssignatura
	 * @param clau
	 * @param session
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "unirse.do", method = RequestMethod.POST)
	public String formUnirseAssignaturaDo(@RequestParam int codiAssignatura,
		    			   			      @RequestParam (required = false) String clau,
		    			   			      HttpSession session,  
		    			   			      Locale locale, 
		    			   			      Model model)
	{
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		Persona persona = (Persona) session.getAttribute("persona");
		Assignatura assignatura = assignaturaService.getAssignatura(codiAssignatura);
		
		// Agafam les claus d'Alumne i de Professor de l'Assignatura actual
		String clauAlumne = assignatura.getClauAlumne();
		String clauProfessor = assignatura.getClauProfessor();
		
		// Les avaluam cas per cas, comparant-les amb la 'clau' rebuda per paràmetre:
		
		// --------------------------------------------------------------------------------- //
		// (1) // L'assignatura actual requereix clau per si volem unir-mos tant per         //
		//     // professor com per alumne                                                   //
		// --------------------------------------------------------------------------------- //
		if ( clauAlumne != null && clauProfessor != null ) {
			if (!clau.isEmpty() && clau.equals(assignatura.getClauAlumne())) {
				// Intentam unir-mos com a alumne
				unirAlumneAssignatura(persona, assignatura, rb, model);
			} else if (!clau.isEmpty() && clau.equals(assignatura.getClauProfessor())) {
				// Intentam unir-mos com a professor
				unirProfessorAssignatura(persona, assignatura, rb, model);
			} else {
				model.addAttribute("errorUnio", rb.getString("assignatura.unirse.mis.clau.erronia"));
			}
		}
		
		// --------------------------------------------------------------------------------- //
		// (2) // L'assignatura actual només requereix clau per a unir-mos com a professors  //
		// --------------------------------------------------------------------------------- //
		if ( clauAlumne == null && clauProfessor != null ) {
			if (clau.isEmpty()) {
				// Intentam unir-mos com a alumne
				unirAlumneAssignatura(persona, assignatura, rb, model);
			} else if (!clau.isEmpty() && clau.equals(assignatura.getClauProfessor())) {
				// Intentam unir-mos com a professor
				unirProfessorAssignatura(persona, assignatura, rb, model);
			} else {
				model.addAttribute("errorUnio", rb.getString("assignatura.unirse.mis.clau.erronia"));
			}
		}
		
		// --------------------------------------------------------------------------------- //
		// (3) // L'assignatura actual només requereix clau per a unir-mos com a alumnes     //
		// --------------------------------------------------------------------------------- //
		if ( clauAlumne != null && clauProfessor == null ) {
			if (!clau.isEmpty() && clau.equals(assignatura.getClauAlumne())) {
				// Intentam unir-mos com a alumne
				unirAlumneAssignatura(persona, assignatura, rb, model);
			} else if (clau.isEmpty()) {
				// Intentam unir-mos com a professor
				unirProfessorAssignatura(persona, assignatura, rb, model);
			} else {
				model.addAttribute("errorUnio", rb.getString("assignatura.unirse.mis.clau.erronia"));
			}
		}
		
		model.addAttribute("assignatura", assignatura);
		model.addAttribute("professors", professorService.getProfessors(assignatura));
		return "xml/assignatura/descripcioXml";
	}
	
	@ExceptionHandler(AssignaturaException.class)
	@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
	public ModelAndView interceptaAssignaturaException(AssignaturaException ex) {
		ModelAndView model = new ModelAndView("error");
	    model.addObject("error", ex.getMessage());
	    return model;
	}
	
	/**
	 * Mètode que tracta els casos que poden ocòrrer quan intentam unir una
	 * Persona com a Alumne de l'Assignatura:
	 *   · Que ja sigui alumne
	 *   · Que ja sigui professor (per lo qual no podrà ser alumne)
	 *   · Que no sigui, d'inici, ni alumne ni professor i, per tant, podrà ser alumne
	 * @param persona
	 * @param assignatura
	 * @param rb
	 * @param model
	 */
	private void unirAlumneAssignatura(Persona persona, Assignatura assignatura, ResourceBundle rb, Model model) {
		if (alumneService.esAlumne(persona, assignatura)) {
//			model.addAttribute("errorUnio", rb.getString("assignatura.unirse.mis.ja.ets.alumne"));
			throw new AssignaturaException(rb.getString("assignatura.unirse.mis.ja.ets.alumne"));
		} else if (professorService.esProfessor(persona, assignatura)) {
//			model.addAttribute("errorUnio", rb.getString("assignatura.unirse.mis.no.pots.alumne.profe"));
			throw new AssignaturaException(rb.getString("assignatura.unirse.mis.no.pots.alumne.profe"));
		} else {
			Alumne alumne = Util.getAlumne(persona, assignatura);
			alumneService.insereixAlumne(alumne);
			model.addAttribute("refresh", "refresh");
		}
	}
	
	/**
	 * Mètode que tracta els casos que poden ocòrrer quan intentam unir una
	 * Persona com a Professor de l'Assignatura:
	 *   · Que ja sigui professor
	 *   · Que ja sigui alumne (per lo qual no podrà ser professor)
	 *   · Que no sigui, d'inici, ni professor ni alumne i, per tant, podrà ser professor
	 * @param persona
	 * @param assignatura
	 * @param rb
	 * @param model
	 */
	private void unirProfessorAssignatura(Persona persona, Assignatura assignatura, ResourceBundle rb, Model model) {
		if (professorService.esProfessor(persona, assignatura)) {
//			model.addAttribute("errorUnio", rb.getString("assignatura.unirse.mis.ja.ets.profe"));
			throw new AssignaturaException(rb.getString("assignatura.unirse.mis.ja.ets.profe"));
		} else if (alumneService.esAlumne(persona, assignatura)) {
//			model.addAttribute("errorUnio", rb.getString("assignatura.unirse.mis.no.pots.profe.alumne"));
			throw new AssignaturaException(rb.getString("assignatura.unirse.mis.no.pots.profe.alumne"));
		} else {
			Professor professor = Util.getProfessor(persona, assignatura);
			professorService.insereixProfessor(professor);
			model.addAttribute("refresh", "refresh");
		}
	}
	
}
