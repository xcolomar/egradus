package org.projecte.egradus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
import org.projecte.egradus.repository.PreguntaDao;
import org.projecte.egradus.repository.RespostaPreguntaDao;
import org.projecte.egradus.service.RespostaPreguntaService;
import org.projecte.egradus.utilities.Calcul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RespostaPreguntaServiceImpl implements RespostaPreguntaService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PreguntaDao preguntaDao;
	
	@Autowired
	private RespostaPreguntaDao respostaPreguntaDao;
	
	/**
	 * recuperam la pregunta (i les seves opcions) associada a aquesta resposta-pregunta
	 * 
	 * @param rp
	 * @return
	 */
	private Pregunta getPreguntaAssociada(RespostaPregunta rp) {
		Pregunta pregunta = preguntaDao.getPreguntaByCodi(rp.getPregunta().getCodi());
		List<Opcio> opcions = preguntaDao.getOpcions(pregunta);
		if (opcions != null) pregunta.setOpcions(opcions);
		return pregunta;
	}
	
	public RespostaPregunta iniciaContestacio(RespostaPregunta rp) {
		// assignam la pregunta a l'estructura rp
		rp.setPregunta(getPreguntaAssociada(rp));
		
		// assignam les opcions contestades (llistat buit de opcions respostes, 
		// ja que acabam d'iniciar la contestació de la pregunta)
		rp.setOpcionsMarcades(new ArrayList<OpcioResposta>());
		
		rp.setDataContestacioInici(new Date());
		
		return respostaPreguntaDao.updateRespostaPregunta(rp);
	}
	
	public RespostaPregunta finalitzaContestacio(RespostaPregunta rp, String textRaonarResposta, String textResposta, List<OpcioResposta> opcionsMarcades) {
		Pregunta pregunta = getPreguntaAssociada(rp);
		
		// Data de fi de contestació + actualitzar el temps de resposta mig de la pregunta
		rp.setDataContestacioFi(new Date());
		
		// Donat que ja tenim alimentades les dates d'inici i de fi de contestació, podem actualitar
		// el temps de resposta d'aquesta resposta-pregunta
		rp.actualitzaTempsResposta();
		
		// Només actualitzam el temps de resposta mig de la pregunta quan la resposta-pregunta NO ve
		// d'una resposta-questionari, ja que en aquest darrer cas, el temps de contestació d'una
		// pregunta no es pot calcular. Només es calcula el temps de contestació del qüestionari en global,
		// per tant, si intentam actualitzar el temps de resposta mig de la pregunta amb aquest valor, 
		// quedarà inflat (ja que haurem considerat el temps de contestació del qüestionari sencer!)
		if (rp.getRespostaQuestionari() == null) {
			pregunta.incrementaNumTotalActualitzacionsTRM();
			pregunta.actualitzaTempsRespostaMig(rp.getTempsResposta());
		}
		
		rp.setContestada(Boolean.TRUE);
		rp.setTextRaonarResposta(textRaonarResposta);
		rp.setTextResposta(textResposta);
		
		for (OpcioResposta or : opcionsMarcades) {
			or.setRespostaPregunta(rp);
			respostaPreguntaDao.persistOpcioResposta(or);
		}
		rp.setOpcionsMarcades(opcionsMarcades);
		
		// correcció implícita de les preguntes que no siguin de Resposta Curta (REC)
		if (!pregunta.getTipus().equals(Pregunta.TIPUS_REC)) {
			// Si corregim la pregunta, hem d'incrementar abans el comptador d'actualitzacions
			// de la nota mitja
			pregunta.incrementaNumTotalActualitzacionsNotaMitja();
			
			Float nota = Calcul.notaRespostaPregunta(rp);
			rp.setNota(nota);
			
			// Actualitzar la dificultat pràctica i la nota mitja de la pregunta
			pregunta.actualitzaDificultatPractica(nota);
			pregunta.actualitzaNotaMitja(nota);
			
			rp.setCorregida(Boolean.TRUE);
			rp.setDataCorreccio(new Date());
			// per defecte, no definim professor corrector, ja que s'ha corregit automàticament
		}
		
		rp.setPregunta(pregunta);
		return respostaPreguntaDao.updateRespostaPregunta(rp);
	}
	
	public List<RespostaPregunta> enviaPregunta(int codi, Professor assignador, List<Alumne> alumnes, Boolean anonima) {
		Pregunta pregunta = preguntaDao.getPreguntaByCodi(codi);
		RespostaPregunta rp = null;
		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
		for (Alumne alumne : alumnes) {
			rp = new RespostaPregunta(pregunta, assignador, alumne, anonima);
			respostaPreguntaDao.persistRespostaPregunta(rp);
			llistaRp.add(rp);
		}
		return llistaRp;
	}
	
	public RespostaPregunta getRespostaPregunta(int codi) {
		RespostaPregunta rp = respostaPreguntaDao.getRespostaPreguntaByCodi(codi);
		
		// assignam la pregunta a l'estructura rp
		rp.setPregunta(getPreguntaAssociada(rp));
		
		// assignam les opcions contestades (en cas que ja s'hagi contestat la resposta-pregunta)
		rp.setOpcionsMarcades(respostaPreguntaDao.getOpcionsContestades(rp));
		
		return rp;
	}
	
	public List<RespostaPregunta> getPreguntesPerRespondre(Alumne alumne) {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public List<RespostaPregunta> getPreguntesPerCorregir(Alumne alumne) {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
		if (!llista.isEmpty()) return llista;
		return null;
	}

	public List<RespostaPregunta> getPreguntesPerRevisar(Alumne alumne) {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public List<RespostaPregunta> getPreguntesContestades(Alumne alumne) {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPregunta(alumne, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public RespostaPregunta iniciarContestacioPregunta(RespostaPregunta respostaPregunta) {
		RespostaPregunta rp = respostaPreguntaDao.getRespostaPreguntaByCodi(respostaPregunta.getCodi());
		return iniciaContestacio(rp);
	}
	
	public RespostaPregunta iniciarContestacioPregunta(RespostaQuestionari respostaQuestionari, Pregunta pregunta) {
		// Generam la resposta-pregunta i la inserim a BD
		RespostaPregunta rp = new RespostaPregunta(pregunta, respostaQuestionari.getAssignador(), respostaQuestionari.getAlumne());
		respostaPreguntaDao.persistRespostaPregunta(rp);
		
		// Recuperam la resposta-pregunta de BD
		rp = respostaPreguntaDao.getRespostaPreguntaByCodi(rp.getCodi());
		
		return iniciaContestacio(rp);
	}
	
	public RespostaPregunta finalitzarContestacioPregunta(RespostaPregunta respostaPregunta, String textRaonarResposta, String textResposta, List<OpcioResposta> opcionsMarcades) {
		RespostaPregunta rp = respostaPreguntaDao.getRespostaPreguntaByCodi(respostaPregunta.getCodi());
		return finalitzaContestacio(rp, textRaonarResposta, textResposta, opcionsMarcades);
	}

	public List<RespostaPregunta> getPreguntesPerCorregir(Professor professor) {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPreguntaPendentsCorregir(professor);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public List<RespostaPregunta> getPreguntesCorregides(Professor professor) {
		List<RespostaPregunta> llista = respostaPreguntaDao.getRespostesPreguntaCorregides(professor);
		if (!llista.isEmpty()) return llista;
		return null;
	}

	public RespostaPregunta iniciarCorreccioPregunta(RespostaPregunta respostaPregunta) {
		RespostaPregunta rp = respostaPreguntaDao.getRespostaPreguntaByCodi(respostaPregunta.getCodi());
		
		Pregunta pregunta = getPreguntaAssociada(rp);
		
		// assignam la pregunta a l'estructura rp
		rp.setPregunta(pregunta);
		
		// Si la pregunta associada és REC, assignam un conjunt buit d'opcions contestades 
		// perquè l'XML que retornam requereix almenys una llista buida d'opcions marcades.
		// En cas contrari, assignam les opcions marcades a l'estructura rp
		if (pregunta.getTipus().equals(Pregunta.TIPUS_REC)) rp.setOpcionsMarcades(new ArrayList<OpcioResposta>());
		else rp.setOpcionsMarcades(respostaPreguntaDao.getOpcionsContestades(rp));
		
		return respostaPreguntaDao.updateRespostaPregunta(rp);
	}

	public RespostaPregunta finalitzarCorreccioPregunta(RespostaPregunta respostaPregunta, String textCorreccio, String textRevisio, Float nota) {
		RespostaPregunta rp = respostaPreguntaDao.getRespostaPreguntaByCodi(respostaPregunta.getCodi());
		Pregunta pregunta = getPreguntaAssociada(rp);
		
//		if (textCorreccio != null) {
		if (pregunta.getTipus().equals(Pregunta.TIPUS_REC)) {
			rp.setDataCorreccio(new Date());
			rp.setCorregida(Boolean.TRUE);
			rp.setTextCorreccio(textCorreccio);
			rp.setCorrector(rp.getAssignador()); // de moment, informam de què és el mateix assignador, qui corregeix
			rp.setNota(nota);
			
			// no té opcions marcades, però l'XML que retornam requereix almenys una llista buida d'opcions marcades
			rp.setOpcionsMarcades(new ArrayList<OpcioResposta>());
			
			// Actualitzar la dificultat pràctica i la nota mitja de la pregunta
			pregunta.incrementaNumTotalActualitzacionsNotaMitja();
			pregunta.actualitzaDificultatPractica(nota);
			pregunta.actualitzaNotaMitja(nota);
		}
		
//		if (textRevisio != null) {
		if (pregunta.getRaonarResposta().equals(Boolean.TRUE)) {
			rp.setRevisada(Boolean.TRUE);
			rp.setDataRevisio(new Date());
			rp.setTextRevisio(textRevisio);
			
			// obtenim la nota antiga perquè haurem de desfer la dificultat pràctica i la nota mitja
			Float notaAntiga = rp.getNota();
			
			// Actualitzam la nota revisada (guardant la nota antiga)
			rp.setNotaAntiga(notaAntiga);
			rp.setNota(nota);
			
			// Sabem que les preguntes revisables mai no seran REC, per tant,
			// no hi ha perill de què el mètode Dao retorni NULL (sempre tendrà
			// opcions que retornar)
			rp.setOpcionsMarcades(respostaPreguntaDao.getOpcionsContestades(rp));
			
			// Actualitzar la dificultat pràctica i la nota mitja de la pregunta
			pregunta.actualitzaDificultatPractica(notaAntiga, nota);
			pregunta.actualitzaNotaMitja(notaAntiga, nota);
		}
		
		rp.setPregunta(pregunta);
		return respostaPreguntaDao.updateRespostaPregunta(rp);
	}
	
}
