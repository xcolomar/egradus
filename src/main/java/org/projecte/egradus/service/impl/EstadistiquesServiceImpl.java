package org.projecte.egradus.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
import org.projecte.egradus.repository.AssignaturaRepository;
import org.projecte.egradus.repository.EstadistiquesDao;
import org.projecte.egradus.repository.PreguntaDao;
import org.projecte.egradus.repository.QuestionariDao;
import org.projecte.egradus.repository.RespostaPreguntaDao;
import org.projecte.egradus.repository.RespostaQuestionariDao;
import org.projecte.egradus.service.EstadistiquesService;
import org.projecte.egradus.service.RespostaQuestionariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EstadistiquesServiceImpl implements EstadistiquesService {

	private static final long serialVersionUID = 1L;

	@Autowired
	EstadistiquesDao estadistiquesDao;
	
	@Autowired
	RespostaPreguntaDao respostaPreguntaDao;
	
	@Autowired
	RespostaQuestionariDao respostaQuestionariDao;
	
	@Autowired
	PreguntaDao preguntaDao;
	
	@Autowired
	QuestionariDao questionariDao;
	
	@Autowired
	AssignaturaRepository assignaturaDao;
	
	@Autowired
	RespostaQuestionariService respostaQuestionariService;
	
	
	public List<Map<String, Object>> getPreguntesAlumne(Alumne alumne) {
		List<Map<String, Object>> llista = estadistiquesDao.getRespostesPregunta(alumne);
		if (!llista.isEmpty()) return llista;
		return null;
	}

	public List<Map<String, Object>> getQuestionarisAlumne(Alumne alumne) {
		List<Map<String, Object>> llista = estadistiquesDao.getRespostesQuestionari(alumne);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public List<Map<String, Object>> getEstadistiquesDetallRespostaQuestionari(int codiRespostaQuestionari) {
		List<Map<String, Object>> llista = estadistiquesDao.getDetallRespostaQuestionari(codiRespostaQuestionari);
		if (!llista.isEmpty()) return llista;
		return null;
	}

	public Map<String, Object> getEstadistiquesProfessorPreguntaAssignacioDirecta(int codiPregunta, int codiAssignatura) {
		Pregunta pregunta = preguntaDao.getPreguntaByCodi(codiPregunta);
		Assignatura assignatura = assignaturaDao.getAssignaturaByCodi(codiAssignatura);
		
		List<RespostaPregunta> rebudesAss = respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, null, null, Boolean.TRUE);
		List<RespostaPregunta> contestadesAss = respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaPregunta> corregidesAss = respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		List<RespostaPregunta> rebudesEgr = respostaPreguntaDao.getRespostesPregunta(pregunta, null, null, null, Boolean.TRUE);
		List<RespostaPregunta> contestadesEgr = respostaPreguntaDao.getRespostesPregunta(pregunta, null, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaPregunta> corregidesEgr = respostaPreguntaDao.getRespostesPregunta(pregunta, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		
		return ompleEstadistiquesProfessorPregunta(rebudesAss, contestadesAss, corregidesAss, rebudesEgr, contestadesEgr, corregidesEgr);
	}
	
	public Map<String, Object> getEstadistiquesProfessorPreguntaAssignacioQuestionari(int codiPregunta, int codiAssignatura) {
		Pregunta pregunta = preguntaDao.getPreguntaByCodi(codiPregunta);
		Assignatura assignatura = assignaturaDao.getAssignaturaByCodi(codiAssignatura);
		
		List<RespostaPregunta> rebudesAss = respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, null, null, Boolean.FALSE);
		List<RespostaPregunta> contestadesAss = respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, null, Boolean.FALSE);
		List<RespostaPregunta> corregidesAss = respostaPreguntaDao.getRespostesPregunta(pregunta, assignatura, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		List<RespostaPregunta> rebudesEgr = respostaPreguntaDao.getRespostesPregunta(pregunta, null, null, null, Boolean.FALSE);
		List<RespostaPregunta> contestadesEgr = respostaPreguntaDao.getRespostesPregunta(pregunta, null, Boolean.TRUE, null, Boolean.FALSE);
		List<RespostaPregunta> corregidesEgr = respostaPreguntaDao.getRespostesPregunta(pregunta, null, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		
		return ompleEstadistiquesProfessorPregunta(rebudesAss, contestadesAss, corregidesAss, rebudesEgr, contestadesEgr, corregidesEgr);
	}
	
	public Map<String, Object> getEstadistiquesProfessorConjuntPreguntesAssignacioDirecta(int codiAssignatura) {
		Assignatura assignatura = assignaturaDao.getAssignaturaByCodi(codiAssignatura);
		
		List<RespostaPregunta> rebudesAss = respostaPreguntaDao.getRespostesPregunta(null, assignatura, null, null, Boolean.TRUE);
		List<RespostaPregunta> contestadesAss = respostaPreguntaDao.getRespostesPregunta(null, assignatura, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaPregunta> corregidesAss = respostaPreguntaDao.getRespostesPregunta(null, assignatura, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		List<RespostaPregunta> rebudesEgr = respostaPreguntaDao.getRespostesPregunta(null, null, null, null, Boolean.TRUE);
		List<RespostaPregunta> contestadesEgr = respostaPreguntaDao.getRespostesPregunta(null, null, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaPregunta> corregidesEgr = respostaPreguntaDao.getRespostesPregunta(null, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		
		return ompleEstadistiquesProfessorPregunta(rebudesAss, contestadesAss, corregidesAss, rebudesEgr, contestadesEgr, corregidesEgr);
	}
	
	public Map<String, Object> getEstadistiquesProfessorConjuntPreguntesAssignacioQuestionari(int codiAssignatura) {
		Assignatura assignatura = assignaturaDao.getAssignaturaByCodi(codiAssignatura);
		
		List<RespostaPregunta> rebudesAss = respostaPreguntaDao.getRespostesPregunta(null, assignatura, null, null, Boolean.FALSE);
		List<RespostaPregunta> contestadesAss = respostaPreguntaDao.getRespostesPregunta(null, assignatura, Boolean.TRUE, null, Boolean.FALSE);
		List<RespostaPregunta> corregidesAss = respostaPreguntaDao.getRespostesPregunta(null, assignatura, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		List<RespostaPregunta> rebudesEgr = respostaPreguntaDao.getRespostesPregunta(null, null, null, null, Boolean.FALSE);
		List<RespostaPregunta> contestadesEgr = respostaPreguntaDao.getRespostesPregunta(null, null, Boolean.TRUE, null, Boolean.FALSE);
		List<RespostaPregunta> corregidesEgr = respostaPreguntaDao.getRespostesPregunta(null, null, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		
		return ompleEstadistiquesProfessorPregunta(rebudesAss, contestadesAss, corregidesAss, rebudesEgr, contestadesEgr, corregidesEgr);
	}
	
	public Map<String, Object> getEstadistiquesProfessorQuestionari(int codiQuestionari, int codiAssignatura) {
		Questionari questionari = questionariDao.getQuestionariByCodi(codiQuestionari);
		Assignatura assignatura = assignaturaDao.getAssignaturaByCodi(codiAssignatura);
		
		List<RespostaQuestionari> rebutsAss = respostaQuestionariDao.getRespostesQuestionari(questionari, assignatura, null, null, Boolean.TRUE);
		List<RespostaQuestionari> contestatsAss = respostaQuestionariDao.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaQuestionari> corregitsAss = respostaQuestionariDao.getRespostesQuestionari(questionari, assignatura, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		List<RespostaQuestionari> rebutsEgr = respostaQuestionariDao.getRespostesQuestionari(questionari, null, null, null, Boolean.TRUE);
		List<RespostaQuestionari> contestatsEgr = respostaQuestionariDao.getRespostesQuestionari(questionari, null, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaQuestionari> corregitsEgr = respostaQuestionariDao.getRespostesQuestionari(questionari, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		
		return ompleEstadistiquesProfessorQuestionari(rebutsAss, contestatsAss, corregitsAss, rebutsEgr, contestatsEgr, corregitsEgr);
	}
	
	public Map<String, Object> getEstadistiquesProfessorConjuntQuestionaris(int codiAssignatura) {
		Assignatura assignatura = assignaturaDao.getAssignaturaByCodi(codiAssignatura);
		
		List<RespostaQuestionari> rebutsAss = respostaQuestionariDao.getRespostesQuestionari(null, assignatura, null, null, Boolean.TRUE);
		List<RespostaQuestionari> contestatsAss = respostaQuestionariDao.getRespostesQuestionari(null, assignatura, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaQuestionari> corregitsAss = respostaQuestionariDao.getRespostesQuestionari(null, assignatura, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		List<RespostaQuestionari> rebutsEgr = respostaQuestionariDao.getRespostesQuestionari(null, null, null, null, Boolean.TRUE);
		List<RespostaQuestionari> contestatsEgr = respostaQuestionariDao.getRespostesQuestionari(null, null, Boolean.TRUE, null, Boolean.TRUE);
		List<RespostaQuestionari> corregitsEgr = respostaQuestionariDao.getRespostesQuestionari(null, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		
		return ompleEstadistiquesProfessorQuestionari(rebutsAss, contestatsAss, corregitsAss, rebutsEgr, contestatsEgr, corregitsEgr);
	}

	public List<Map<String, Object>> getEstadistiquesProfessorPreguntes(int codiAssignatura, Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		String alumneAnonim = rb.getString("estadistiques.alumne.anonim");
		List<Map<String, Object>> estadistiques = estadistiquesDao.getEstadistiquesProfessorPreguntesByAssignatura(codiAssignatura);
		for (Map<String, Object> map : estadistiques) {
			Integer codiPregunta = (Integer) map.get(EstadistiquesDao.PREGUNTA_CODI);
			Integer numContestacions = (Integer) map.get(EstadistiquesDao.NUM_CONTESTACIONS);
			List<Map<String, Object>> detallEstadistiques = estadistiquesDao.getDetallEstadistiquesProfessorPreguntes(codiAssignatura, codiPregunta.intValue(), numContestacions.intValue(), alumneAnonim);
			map.put(EstadistiquesDao.DETALL, detallEstadistiques);
		}
		
//		imprimirMap(estadistiques, "");
		
		return estadistiques;
	}
	
	public List<Map<String, Object>> getEstadistiquesProfessorQuestionaris(int codiAssignatura, Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
		String alumneAnonim = rb.getString("estadistiques.alumne.anonim");
		List<Map<String, Object>> estadistiques = estadistiquesDao.getEstadistiquesProfessorQuestionarisByAssignatura(codiAssignatura);
		for (Map<String, Object> map : estadistiques) {
			Integer codiQuestionari = (Integer) map.get(EstadistiquesDao.QUESTIONARI_CODI);
			Integer numContestacions = (Integer) map.get(EstadistiquesDao.NUM_CONTESTACIONS);
			List<Map<String, Object>> detallEstadistiques = estadistiquesDao.getDetallEstadistiquesProfessorQuestionaris(codiAssignatura, codiQuestionari.intValue(), numContestacions.intValue(), alumneAnonim);
			map.put(EstadistiquesDao.DETALL, detallEstadistiques);
		}
		
//		imprimirMap(estadistiques, "");
		
		return estadistiques;
	}
	
	public List<Map<String, Object>> getEstadistiquesConjuntAlumnesDetallQuestionari(int codiQuestionari) {
		List<Map<String, Object>> llista = estadistiquesDao.getDetallQuestionari(codiQuestionari);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	private Map<String, Object> ompleEstadistiquesProfessorPregunta(List<RespostaPregunta> rebudesAss, 
			List<RespostaPregunta> contestadesAss, List<RespostaPregunta> corregidesAss, List<RespostaPregunta> rebudesEgr, 
			List<RespostaPregunta> contestadesEgr, List<RespostaPregunta> corregidesEgr) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// Temps de Resposta Mig de l'Assignatura actual
		Float tempsRespostaMigAss = 0f;
		if (contestadesAss.size() > 0) {
			for (RespostaPregunta rp : contestadesAss) tempsRespostaMigAss += rp.getTempsResposta();
			tempsRespostaMigAss = tempsRespostaMigAss / contestadesAss.size();
		}
		
		// Nota mitja i num d'aprovats de l'Assignatura actual 
		int respostaPreguntesAprovadesAss = 0;
		Float notaMitjaAss = 0f;
		if (corregidesAss.size() > 0) {
			for (RespostaPregunta rp : corregidesAss) {
				notaMitjaAss += rp.getNota();
				if (rp.getNota() >= 5) respostaPreguntesAprovadesAss++;
			}
			notaMitjaAss = notaMitjaAss / corregidesAss.size();
		}
		
		// Temps de Resposta Mig de tot Egradus
		Float tempsRespostaMigEgr = 0f;
		if (contestadesEgr.size() > 0) {
			for (RespostaPregunta rp : contestadesEgr) tempsRespostaMigEgr += rp.getTempsResposta();
			tempsRespostaMigEgr = tempsRespostaMigEgr / contestadesEgr.size();
		}
		
		// Nota mitja de tot Egradus i num d'aprovats de tot Egradus
		int respostaPreguntesAprovadesEgr = 0;
		Float notaMitjaEgr = 0f;
		if (corregidesEgr.size() > 0) {
			for (RespostaPregunta rp : corregidesEgr) {
				notaMitjaEgr += rp.getNota();
				if (rp.getNota() >= 5) respostaPreguntesAprovadesEgr++;
			}
			notaMitjaEgr = notaMitjaEgr / corregidesEgr.size();
		}
		
		// omplim els camps del Map
		map.put(EstadistiquesService.PREGUNTA_ENVIADES_ASS, new Integer(rebudesAss.size()));
		map.put(EstadistiquesService.PREGUNTA_CONTESTADES_ASS, new Integer(contestadesAss.size()));
		map.put(EstadistiquesService.PREGUNTA_CORREGIDES_ASS, new Integer(corregidesAss.size()));
		map.put(EstadistiquesService.PREGUNTA_APROVADES_ASS, new Integer(respostaPreguntesAprovadesAss));
		map.put(EstadistiquesService.PREGUNTA_NOTA_MITJA_ASS, notaMitjaAss);
		map.put(EstadistiquesService.PREGUNTA_TEMPS_RESPOSTA_MIG_ASS, tempsRespostaMigAss);
		
		map.put(EstadistiquesService.PREGUNTA_ENVIADES_EGR, new Integer(rebudesEgr.size()));
		map.put(EstadistiquesService.PREGUNTA_CONTESTADES_EGR, new Integer(contestadesEgr.size()));
		map.put(EstadistiquesService.PREGUNTA_CORREGIDES_EGR, new Integer(corregidesEgr.size()));
		map.put(EstadistiquesService.PREGUNTA_APROVADES_EGR, new Integer(respostaPreguntesAprovadesEgr));
		map.put(EstadistiquesService.PREGUNTA_NOTA_MITJA_EGR, notaMitjaEgr);
		map.put(EstadistiquesService.PREGUNTA_TEMPS_RESPOSTA_MIG_EGR, tempsRespostaMigEgr);
		return map;
	}
	
	private Map<String, Object> ompleEstadistiquesProfessorQuestionari(List<RespostaQuestionari> rebutsAss, 
			List<RespostaQuestionari> contestatsAss, List<RespostaQuestionari> corregitsAss, List<RespostaQuestionari> rebutsEgr, 
			List<RespostaQuestionari> contestatsEgr, List<RespostaQuestionari> corregitsEgr) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// Temps de Resposta Mig de l'Assignatura actual
		Float tempsRespostaMigAss = 0f;
		if (contestatsAss.size() > 0) {
			for (RespostaQuestionari rq : contestatsAss) tempsRespostaMigAss += rq.getTempsResposta();
			tempsRespostaMigAss = tempsRespostaMigAss / contestatsAss.size();
		}
		
		// Nota mitja i num d'aprovats de l'Assignatura actual
		int respostaQuestionarisAprovatsAss = 0;
		Float notaMitjaAss = 0f;
		if (corregitsAss.size() > 0) {
			for (RespostaQuestionari rq : corregitsAss) {
				notaMitjaAss += rq.getNota();
				if (rq.getNota() >= 5) respostaQuestionarisAprovatsAss++;
			}
			notaMitjaAss = notaMitjaAss / corregitsAss.size();
		}
		
		// Temps de Resposta Mig de tot Egradus
		Float tempsRespostaMigEgr = 0f;
		if (contestatsEgr.size() > 0) {
			for (RespostaQuestionari rq : contestatsEgr) tempsRespostaMigEgr += rq.getTempsResposta();
			tempsRespostaMigEgr = tempsRespostaMigEgr / contestatsEgr.size();
		}
		
		// Nota mitja de tot Egradus i num d'aprovats de tot Egradus
		int respostaQuestionarisAprovatsEgr = 0;
		Float notaMitjaEgr = 0f;
		if (corregitsEgr.size() > 0) {
			for (RespostaQuestionari rq : corregitsEgr) {
				notaMitjaEgr += rq.getNota();
				if (rq.getNota() >= 5) respostaQuestionarisAprovatsEgr++;
			}
			notaMitjaEgr = notaMitjaEgr / corregitsEgr.size();
		}
		
		// omplim els camps del Map
		map.put(EstadistiquesService.QUESTIONARI_ENVIATS_ASS, new Integer(rebutsAss.size()));
		map.put(EstadistiquesService.QUESTIONARI_CONTESTATS_ASS, new Integer(contestatsAss.size()));
		map.put(EstadistiquesService.QUESTIONARI_CORREGITS_ASS, new Integer(corregitsAss.size()));
		map.put(EstadistiquesService.QUESTIONARI_APROVATS_ASS, new Integer(respostaQuestionarisAprovatsAss));
		map.put(EstadistiquesService.QUESTIONARI_NOTA_MITJA_ASS, notaMitjaAss);
		map.put(EstadistiquesService.QUESTIONARI_TEMPS_RESPOSTA_MIG_ASS, tempsRespostaMigAss);
		
		map.put(EstadistiquesService.QUESTIONARI_ENVIATS_EGR, new Integer(rebutsEgr.size()));
		map.put(EstadistiquesService.QUESTIONARI_CONTESTATS_EGR, new Integer(contestatsEgr.size()));
		map.put(EstadistiquesService.QUESTIONARI_CORREGITS_EGR, new Integer(corregitsEgr.size()));
		map.put(EstadistiquesService.QUESTIONARI_APROVATS_EGR, new Integer(respostaQuestionarisAprovatsEgr));
		map.put(EstadistiquesService.QUESTIONARI_NOTA_MITJA_EGR, notaMitjaEgr);
		map.put(EstadistiquesService.QUESTIONARI_TEMPS_RESPOSTA_MIG_EGR, tempsRespostaMigEgr);
		return map;
	}
	
	/**
	 * Imprimeix el contingut de les estadístiques que veuen el professors
	 * sobre el conjunt d'alumnes de la seva assignatura
	 * (en referència a les preguntes)
	 * 
	 * @param llistaEstadistiques
	 * @param despl
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private void imprimirMap(List<Map<String, Object>> llistaEstadistiques, String despl) {
		for (Map<String, Object> mapEstadistiques : llistaEstadistiques) {
			for (Map.Entry<String, Object> entry : mapEstadistiques.entrySet()) {
				if (entry.getKey().equals(EstadistiquesDao.DETALL)) {
					System.out.println(despl + entry.getKey());
					imprimirMap((List<Map<String, Object>>) entry.getValue(), despl + "    ");
				} else {
					System.out.println(despl + entry.getKey() + " - " + entry.getValue());
				}
			}
			System.out.println(" ");
		}
	}
	
}
