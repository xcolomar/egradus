package org.projecte.egradus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
import org.projecte.egradus.repository.PreguntaDao;
import org.projecte.egradus.repository.PreguntaQuestionariDao;
import org.projecte.egradus.repository.QuestionariDao;
import org.projecte.egradus.repository.RespostaPreguntaDao;
import org.projecte.egradus.repository.RespostaQuestionariDao;
import org.projecte.egradus.service.RespostaPreguntaService;
import org.projecte.egradus.service.RespostaQuestionariService;
import org.projecte.egradus.utilities.Calcul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RespostaQuestionariServiceImpl implements RespostaQuestionariService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private QuestionariDao questionariDao;
	
	@Autowired
	private PreguntaDao preguntaDao;
	
	@Autowired
	private PreguntaQuestionariDao pqDao;
	
	@Autowired
	private RespostaQuestionariDao respostaQuestionariDao;
	
	@Autowired
	private RespostaPreguntaDao respostaPreguntaDao;
	
	@Autowired (required = true)
	private RespostaPreguntaService respostaPreguntaService;
	
	/**
	 * recuperam la pregunta (i les seves opcions) associada a aquest codi
	 * 
	 * @param codiPregunta
	 * @return
	 */
	private Pregunta getPreguntaAssociada(int codiPregunta) {
		Pregunta pregunta = preguntaDao.getPreguntaByCodi(codiPregunta);
		List<Opcio> opcions = preguntaDao.getOpcions(pregunta);
		if (opcions != null) pregunta.setOpcions(opcions);
		return pregunta;
	}
	
	/**
	 * recuperam la llista de resposta-preguntes actual de la resposta-questionari
	 * 
	 * @param rq
	 * @return
	 */
	private List<RespostaPregunta> getRespostaPreguntesAssociades(RespostaQuestionari rq) {
		List<RespostaPregunta> llistaRp = respostaQuestionariDao.getRespostaPreguntes(rq);
		
		for (RespostaPregunta rp : llistaRp) {
			rp.setRespostaQuestionari(rq);
			rp.setPregunta(getPreguntaAssociada(rp.getPregunta().getCodi()));
			rp.setOpcionsMarcades(respostaPreguntaDao.getOpcionsContestades(rp));
		}
		
		return llistaRp;
	}
	
	public Questionari getQuestionariAssociat(RespostaQuestionari rq) {
		Questionari questionari = questionariDao.getQuestionariByCodi(rq.getQuestionari().getCodi());
		
		// Obtenim la llista de relacions pregunta-questionari, però no obtenim tota la informació
		// de les preguntes (opcions, etc) que necessitam.
		List<PreguntaQuestionari> llistaPQ = pqDao.getRelacionsPreguntaQuestionari(questionari);
		
		// Definim una nova llista de relacions pregunta-questionari que l'omplirem amb totes les
		// preguntes de la llista anterior amb la informació associada de cada pregunta
		List<PreguntaQuestionari> llistaPQcompleta = new ArrayList<PreguntaQuestionari>();
		
		if (llistaPQ != null) {
			PreguntaQuestionari pq = null;
			for (PreguntaQuestionari pquest : llistaPQ) {
				pq = new PreguntaQuestionari();
				pq.setPregunta(getPreguntaAssociada(pquest.getPregunta().getCodi()));
				pq.setQuestionari(questionari);
				pq.setPes(pquest.getPes());
				llistaPQcompleta.add(pq);
			}
			questionari.setPreguntes(llistaPQcompleta);
		}
			
		return questionari;
	}
	
	public List<RespostaQuestionari> enviaQuestionari(int codi, Professor assignador, List<Alumne> alumnes, Boolean anonim) {
		Questionari questionari = questionariDao.getQuestionariByCodi(codi);
		RespostaQuestionari rq = null;
		List<RespostaQuestionari> llistaRq = new ArrayList<RespostaQuestionari>();
		for (Alumne alumne : alumnes) {
			rq = new RespostaQuestionari(questionari, assignador, alumne, anonim);
			respostaQuestionariDao.persistRespostaQuestionari(rq);
			
			List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
			RespostaPregunta rp = null;
			for (PreguntaQuestionari pq : questionari.getPreguntes()) {
				rp = new RespostaPregunta(rq, pq.getPregunta(), anonim);
				respostaPreguntaDao.persistRespostaPregunta(rp);
				
				// la recuperam de BD per assignar-la a la llista
				rp = respostaPreguntaDao.getRespostaPreguntaByCodi(rp.getCodi());
				
				// afegim la resposta-pregunta a la llista
				llistaRp.add(rp);
			}
			
			// assignam la llista de resposta-preguntes que tendrà la resposta-questionari
			rq.setRespostaPreguntes(llistaRp);
			
			// afegim la resposta-questionari a la llista
			llistaRq.add(rq);
		}
		return llistaRq;
	}

	public RespostaQuestionari getRespostaQuestionari(int codi) {
		RespostaQuestionari rq = respostaQuestionariDao.getRespostaQuestionariByCodi(codi);
		
		// assignam el qüestionari a l'estructura rq
		rq.setQuestionari(getQuestionariAssociat(rq));
		
		List<RespostaPregunta> llistaRp = new ArrayList<RespostaPregunta>();
		if (rq.getRespostaPreguntes() != null) {
			for (RespostaPregunta respostaPregunta : rq.getRespostaPreguntes()) {
				RespostaPregunta rp = respostaPreguntaDao.getRespostaPreguntaByCodi(respostaPregunta.getCodi());
				
				// assignam la pregunta a l'estructura rp
				rp.setPregunta(getPreguntaAssociada(rp.getPregunta().getCodi()));
				
				// assignam les opcions contestades (en cas que ja s'hagi contestat la resposta-pregunta)
				rp.setOpcionsMarcades(respostaPreguntaDao.getOpcionsContestades(rp));
				
				llistaRp.add(rp);
			}
		}
		
		// assignam les resposta-preguntes associades
		rq.setRespostaPreguntes(llistaRp);
		
		return rq;
	}

	public List<RespostaQuestionari> getQuestionarisPerRespondre(Alumne alumne) {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
		for (RespostaQuestionari rq : llista) rq.setQuestionari(getQuestionariAssociat(rq));
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public List<RespostaQuestionari> getQuestionarisPerCorregir(Alumne alumne) {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
		for (RespostaQuestionari rq : llista) rq.setQuestionari(getQuestionariAssociat(rq));
		if (!llista.isEmpty()) return llista;
		return null;
	}

	public List<RespostaQuestionari> getQuestionarisPerRevisar(Alumne alumne) {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
		for (RespostaQuestionari rq : llista) rq.setQuestionari(getQuestionariAssociat(rq));
		if (!llista.isEmpty()) return llista;
		return null;
	}

	public List<RespostaQuestionari> getQuestionarisContestats(Alumne alumne) {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionari(alumne, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		for (RespostaQuestionari rq : llista) rq.setQuestionari(getQuestionariAssociat(rq));
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public RespostaQuestionari iniciarContestacioQuestionari(RespostaQuestionari respostaQuestionari) {
		RespostaQuestionari rq = respostaQuestionariDao.getRespostaQuestionariByCodi(respostaQuestionari.getCodi());
		
		// assignam el qüestionari a l'estructura rq
		rq.setQuestionari(getQuestionariAssociat(rq));
		
		// assignam la data d'inici de contestació del qüestionari
		rq.setDataContestacioInici(new Date());
		
		// generam les resposta-preguntes associades a cada pregunta del qüestionari
		List<RespostaPregunta> llistaRespostaPregunta = new ArrayList<RespostaPregunta>();
		for (RespostaPregunta rp : respostaQuestionariDao.getRespostaPreguntes(rq)) {
			rp = respostaPreguntaService.iniciaContestacio(rp);
			llistaRespostaPregunta.add(rp);
		}
		
		// assignam les preguntes contestades
		rq.setRespostaPreguntes(llistaRespostaPregunta);
		
		// Actualitzam la resposta-questionari i la retornam
		return respostaQuestionariDao.updateRespostaQuestionari(rq);
	}
	
	public RespostaQuestionari finalitzarContestacioQuestionari(RespostaQuestionari respostaQuestionari, List<RespostaPregunta> llistaRespostaPreguntes) {
		RespostaQuestionari rq = respostaQuestionariDao.getRespostaQuestionariByCodi(respostaQuestionari.getCodi());
		Questionari questionari = getQuestionariAssociat(rq);
		
		// Actualitzam els camps simples de RQ
		rq.setDataContestacioFi(new Date());
		
		// Donat que ja tenim alimentades les dates d'inici i de fi de contestació, podem actualitar
		// el temps de resposta d'aquesta resposta-questionari
		rq.actualitzaTempsResposta();
		
		// Indicam que s'ha contestat
		rq.setContestat(Boolean.TRUE);
		
		// Si acabam de contestar un qüestionari, ja podem actualitzar el TRM, per tant, incrementam
		// el comptador de TRM i actualitzam el TRM
		questionari.incrementaNumTotalActualitzacionsTRM();
		questionari.actualitzaTempsRespostaMig(rq.getTempsResposta());
		
		if (llistaRespostaPreguntes != null) {
			// Actualitzam les resposta-preguntes de RQ amb les de la llista que rebem per paràmetre
			// (De fet, també corregeix les preguntes de tipus no REC)
			List<RespostaPregunta> llistaRpNoves = new ArrayList<RespostaPregunta>();
			for (int i = 0; i < rq.getRespostaPreguntes().size(); i++) {
				
				// Si no hi han opcions marcades, assignam per defecte un ArrayList buit
				if (llistaRespostaPreguntes.get(i).getOpcionsMarcades() == null)
					llistaRespostaPreguntes.get(i).setOpcionsMarcades(new ArrayList<OpcioResposta>());
				
				RespostaPregunta rpNova = respostaPreguntaService.finalitzaContestacio(
												rq.getRespostaPreguntes().get(i),
												llistaRespostaPreguntes.get(i).getTextRaonarResposta(),
												llistaRespostaPreguntes.get(i).getTextResposta(),
												llistaRespostaPreguntes.get(i).getOpcionsMarcades());
				
				llistaRpNoves.add(rpNova);
			}
			rq.setRespostaPreguntes(llistaRpNoves);
			
			// Assignam la nota al qüestionari (Sempre i quan
			// no tengui cap pregunta que requereixi correcció manual)
			if (questionari.getNumPreguntesRec() == 0) {
				// Si corregim el qüestionari, hem d'incrementar abans el comptador d'actualitzacions
				// de la nota mitja
				questionari.incrementaNumTotalActualitzacionsNotaMitja();
				
				Float nota = Calcul.notaRespostaQuestionari(rq);
				rq.setNota(nota);
				rq.setCorregit(Boolean.TRUE);
				rq.setDataCorreccio(new Date());
				
				// Actualitzam la dificultat pràctica i la nota mitja del qüestionari
				questionari.actualitzaDificultatPractica(nota);
				questionari.actualitzaNotaMitja(nota);
			}
		}
		
		// Finalment, actualitzam la estructura RQ amb el qüestionari, actualitzam la BD i la retornam
		rq.setQuestionari(questionari);
		return respostaQuestionariDao.updateRespostaQuestionari(rq);
	}
	
	public List<RespostaQuestionari> getQuestionarisPerCorregir(Professor professor) {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionariPendentsCorregir(professor);
		for (RespostaQuestionari rq : llista) rq.setQuestionari(getQuestionariAssociat(rq));
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public List<RespostaQuestionari> getQuestionarisCorregits(Professor professor) {
		List<RespostaQuestionari> llista = respostaQuestionariDao.getRespostesQuestionariCorregides(professor);
		for (RespostaQuestionari rq : llista) rq.setQuestionari(getQuestionariAssociat(rq));
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public RespostaQuestionari iniciarCorreccioQuestionari(RespostaQuestionari respostaQuestionari) {
		RespostaQuestionari rq = respostaQuestionariDao.getRespostaQuestionariByCodi(respostaQuestionari.getCodi());
		
		// assignam el qüestionari a l'estructura rq
		rq.setQuestionari(getQuestionariAssociat(rq));
		
		// assignam les resposta-preguntes associades a aquesta resposta-questionari
		rq.setRespostaPreguntes(getRespostaPreguntesAssociades(rq));
		
		return respostaQuestionariDao.updateRespostaQuestionari(rq);
	}
	
	public RespostaQuestionari finalitzarCorreccioQuestionari(RespostaQuestionari respostaQuestionari, List<String> listTextCorreccio, List<String> listTextRevisio, List<Float> listNota) {		
		RespostaQuestionari rq = respostaQuestionariDao.getRespostaQuestionariByCodi(respostaQuestionari.getCodi());
		
		// obtenim el qüestionari associat
		Questionari questionari = getQuestionariAssociat(rq);
		
		int indexNota = 0;
		int indexTextCorreccio = 0;
		int indexTextRevisio = 0;
		for (RespostaPregunta rp : rq.getRespostaPreguntes()) {
			Pregunta pregunta = rp.getPregunta();
			Float notaPregunta = null;
			if (pregunta.getTipus().equals(Pregunta.TIPUS_REC)) {
				String textCorreccio = null;
				if (listNota != null && indexNota < listNota.size()) notaPregunta = listNota.get(indexNota++);
				if (listTextCorreccio != null && indexTextCorreccio < listTextCorreccio.size()) textCorreccio = listTextCorreccio.get(indexTextCorreccio++);
				respostaPreguntaService.finalitzarCorreccioPregunta(rp, textCorreccio, null, notaPregunta);
			}
			if (pregunta.getRaonarResposta().equals(Boolean.TRUE)) {
				String textRevisio = null;
				if (listNota != null && indexNota < listNota.size()) notaPregunta = listNota.get(indexNota++);
				if (listTextRevisio != null && indexTextRevisio < listTextRevisio.size()) textRevisio = listTextRevisio.get(indexTextRevisio++);
				respostaPreguntaService.finalitzarCorreccioPregunta(rp, null, textRevisio, notaPregunta);
			}
		}
		
		// Obtenim les resposta-preguntes que trobem associades a la reposta-questionari rq
		// (Són les resposta-preguntes que acabam d'actualitzar a BD)
		rq.setRespostaPreguntes(getRespostaPreguntesAssociades(rq));
		
		// obtenim la nota nova calculada en funció de les notes de les seves resposta-preguntes
		Float notaQuestionari = Calcul.notaRespostaQuestionari(rq);
		
		if (rq.getQuestionari().getNumPreguntesRec() > 0 && !rq.getCorregit()) {
			if (notaQuestionari != null) {
				// Si corregim el qüestionari, hem d'incrementar abans el comptador d'actualitzacions
				// de la nota mitja
				questionari.incrementaNumTotalActualitzacionsNotaMitja();
				
				// Assignam la nota al qüestionari
				rq.setNota(notaQuestionari);
				rq.setCorregit(Boolean.TRUE);
				rq.setDataCorreccio(new Date());
				
				// Actualitzam la dificultat pràctica del qüestionari, la nota mitja, 
				// el temps de resposta mig i el num total de contestacions
				questionari.actualitzaDificultatPractica(notaQuestionari);
				questionari.actualitzaNotaMitja(notaQuestionari);
			}
		}
		
		if (rq.getQuestionari().getNumPreguntesRaonarResposta() > 0 && !rq.getRevisat()) {
			// obtenim la nota antiga perquè haurem de desfer la dificultat pràctica i la nota mitja
			Float notaAntiga = rq.getNota();
			
			// Assignam la nota al qüestionari
			if (notaAntiga != null && notaQuestionari != null) {
				// Actualitzam la nota revisada (guardant la nota antiga)
				rq.setNotaAntiga(notaAntiga);
				rq.setNota(notaQuestionari);
				
				rq.setRevisat(Boolean.TRUE);
				rq.setDataRevisio(new Date());
				
				// Actualitzam la dificultat pràctica del qüestionari i la nota mitja
				questionari.actualitzaDificultatPractica(notaAntiga, notaQuestionari);
				questionari.actualitzaNotaMitja(notaAntiga, notaQuestionari);
			}
		}
		
		// Finalment, actualitzam la estructura RQ amb el qüestionari, actualitzam la BD i retornam
		rq.setQuestionari(questionari);
		return respostaQuestionariDao.updateRespostaQuestionari(rq);
	}

}
