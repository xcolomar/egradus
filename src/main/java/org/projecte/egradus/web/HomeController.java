package org.projecte.egradus.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.service.PersonaService;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class HomeController {
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private AssignaturaService assignaturaService;
	
	@Autowired
	@Qualifier("validator")
	private Validator validador;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, HttpSession session, Locale locale) {
		Persona persona = (Persona) session.getAttribute("persona");
		if (persona == null) {
			return "index";
		}
		model.addAttribute("idioma", locale.getLanguage());
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String alies, 
			            @RequestParam String clau, 
			            HttpSession session, 
			            Locale locale, 
			            Model model)
	{
		Persona persona = personaService.getPersona(alies);
		if (persona != null && persona.getClau().equals(clau)) {
			session.setAttribute("persona", persona);
			session.setAttribute("data", obtenirData(locale));
			return "redirect:/";
		} else {
			ResourceBundle rb = ResourceBundle.getBundle("messages",locale);	
            model.addAttribute("errorLogin",rb.getString("index.label.error.login"));
			return "index";
		}
	}
	
	@RequestMapping(value = "/registre", method = RequestMethod.POST)
	public String registre(@RequestParam String nom,
		    			   @RequestParam String primerLlinatge,
		    			   @RequestParam String segonLlinatge,
		    			   @RequestParam String correu,
		    			   @RequestParam String alies,
		    			   @RequestParam String clau,
		    			   HttpSession session,  
		    			   Locale locale, 
		    			   Model model)
	{
		
		ResourceBundle rb = ResourceBundle.getBundle("messages",locale);
		boolean hiHaError = false;
		if (nom == null || nom.isEmpty()){	
            model.addAttribute("errorNom", rb.getString("index.label.error.nom"));
			hiHaError = true;
		}
		
		if (primerLlinatge == null || primerLlinatge.isEmpty()){
            model.addAttribute("errorPrimerLlinatge", rb.getString("index.label.error.primerLlinatge"));
            hiHaError = true;
		}
		
		if (correu == null || correu.isEmpty()){
            model.addAttribute("errorCorreu", rb.getString("index.label.error.correu"));
            hiHaError = true;
		}
		
		if (alies == null || alies.isEmpty()){
            model.addAttribute("errorAlies", rb.getString("index.label.error.alies"));
            hiHaError = true;
		}
		
		if (clau == null || clau.isEmpty()){
            model.addAttribute("errorClau", rb.getString("index.label.error.clau"));
            hiHaError = true;
		}
		
		Persona p = personaService.getPersona(alies);
		if (p != null) {
            model.addAttribute("errorRegistre", rb.getString("index.label.error.registre"));
            hiHaError = true;
		}
		if (hiHaError){
			return "index";
		}
		
		Persona persona = Util.inserirPersona(nom, primerLlinatge, segonLlinatge, null, correu, alies, clau, null, new Date());
		
		Set<ConstraintViolation<Persona>> errors = validador.validateProperty(persona, "correu");
		if (errors.isEmpty()){
			personaService.insereixPersona(persona);
			session.setAttribute("persona", persona);
	        session.setAttribute("data", obtenirData(locale));
			return "redirect:/";
		}
		model.addAttribute("errorCorreu", rb.getString("index.label.error.correu.format"));
		return "index";
        
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/canviaIdioma", method = RequestMethod.GET)
	public String canviaIdioma(@RequestParam String idioma, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		localeResolver.setLocale(request, response, new Locale(idioma));
		session.setAttribute("data", obtenirData(localeResolver.resolveLocale(request)));
		return "redirect:/";
	}
	
	@RequestMapping(value = "/capcalera", method = RequestMethod.GET)
	public String capcalera() {
		return "capcalera";
	}
	
	@RequestMapping(value = "/central", method = RequestMethod.GET)
	public String central() {
		return "central"; 
	}
	
//	private String obtenirData(Locale locale) {
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		Date date = new Date();
//		return dateFormat.format(date);
//	}
	
	private String obtenirData(Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		
		SimpleDateFormat formatAny   = new SimpleDateFormat("yyyy");
		SimpleDateFormat formatMes   = new SimpleDateFormat("MM");
		SimpleDateFormat formatDia   = new SimpleDateFormat("dd");
		SimpleDateFormat formatHora  = new SimpleDateFormat("HH");
		SimpleDateFormat formatMinut = new SimpleDateFormat("mm");
		SimpleDateFormat formatSegon = new SimpleDateFormat("ss");
		
		Date dateAny   = new Date();
		Date dateMes   = new Date();
		Date dateDia   = new Date();
		Date dateHora  = new Date();
		Date dateMinut = new Date();
		Date dateSegon = new Date();
		
		int any   = Integer.parseInt(formatAny.format(dateAny));
		int mes   = Integer.parseInt(formatMes.format(dateMes));
		int dia   = Integer.parseInt(formatDia.format(dateDia));
		int hora  = Integer.parseInt(formatHora.format(dateHora));
		int minut = Integer.parseInt(formatMinut.format(dateMinut));
		int segon = Integer.parseInt(formatSegon.format(dateSegon));	
		
		String[] setmana = new String[] {
							rb.getString("util.temp.diumenge.abr"), 
						 	rb.getString("util.temp.dilluns.abr"), 
						 	rb.getString("util.temp.dimarts.abr"), 
						 	rb.getString("util.temp.dimecres.abr"), 
						 	rb.getString("util.temp.dijous.abr"), 
						 	rb.getString("util.temp.divendres.abr"),
						 	rb.getString("util.temp.dissabte.abr")};
		
		String[] mesos   = new String[] {
							rb.getString("util.temp.gener.abr"), 
			 				rb.getString("util.temp.febrer.abr"), 
			 				rb.getString("util.temp.marc.abr"), 
			 				rb.getString("util.temp.abril.abr"), 
			 				rb.getString("util.temp.maig.abr"), 
			 				rb.getString("util.temp.juny.abr"),
			 				rb.getString("util.temp.juliol.abr"),
			 				rb.getString("util.temp.agost.abr"),
			 				rb.getString("util.temp.setembre.abr"),
			 				rb.getString("util.temp.octubre.abr"),
			 				rb.getString("util.temp.novembre.abr"),
			 				rb.getString("util.temp.decembre.abr")};
		
		Calendar calendar = new GregorianCalendar(any, mes-1, dia);
		return setmana[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + completaTemps(dia) + "-" + mesos[mes-1] + "-" + any + " " + completaTemps(hora) + ":" + completaTemps(minut) + ":" + completaTemps(segon);
	}
	
	private String completaTemps(int temps) {
		if (temps<10) return "0" + Integer.toString(temps); 
		else     	  return Integer.toString(temps);
	}
	
}
