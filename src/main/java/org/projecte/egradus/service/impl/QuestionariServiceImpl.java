package org.projecte.egradus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.repository.PreguntaQuestionariDao;
import org.projecte.egradus.repository.QuestionariDao;
import org.projecte.egradus.service.PreguntaService;
import org.projecte.egradus.service.QuestionariService;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionariServiceImpl implements QuestionariService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private QuestionariDao questionariDao;
	
	@Autowired
	private PreguntaQuestionariDao pqDao;
	
	@Autowired
	private PreguntaService preguntaService;
	
	
	/**
	 * Procés local que completa proporcionalment el pès de cada tupla
	 * d'aquelles que no tenen d'inici un pès indicat. Per exemple:
	 * 
	 * <ul>
	 * <li>pesos(1, null) -> pesos(1, 0.15)</li>
	 * <li>pesos(2, 0.3)  -> pesos(2, 0.3)</li>
	 * <li>pesos(3, 0.15) -> pesos(3, 0.15)</li>
	 * <li>pesos(4, null) -> pesos(4, 0.15)</li>
	 * <li>pesos(5, null) -> pesos(5, 0.15)</li>
	 * <li>pesos(6, 0.1)  -> pesos(6, 0.1)</li>
	 * </ul>
	 * 
	 * En cas de passar una llista buida de tuples, el procés en calcularà
	 * una amb l'ajut de les preguntes passades per paràmetre
	 * 
	 * @param preguntes
	 * @param pesos
	 * @return
	 */
	private Map<Integer, Float> completarPesos(List<Pregunta> preguntes, Map<Integer, Float> pesos) {
		// si no s'ha indicat llista de pesos o bé s'ha indicat però és una llista buida:
		if (pesos == null) pesos = new HashMap<Integer, Float>();
		if (pesos.size() == 0)
			for (Pregunta pregunta : preguntes) pesos.put(new Integer(pregunta.getCodi()), 1f / preguntes.size());
		
		// si s'ha indicat una llista de pesos no buida, que conté algún null (algún pès sense determinar)
		// entrarem aquí per omplir-lo amb un pès proporcional
		if (pesos.containsValue(null)) {
			// aquest és la suma dels pesos de totes les preguntes que han vengut inicialment amb un pès indicat.
			Float pesInicial = 0f;
			
			// aquest és el núm. total de preguntes que han vengut inicialment sense pès indicat.
			int numNulls = 0;
			
			// recorrem el map<preg, pes> per omplir aquestes dues variables definides
			for (Map.Entry<Integer, Float> map : pesos.entrySet())
				if (map.getValue() == null) numNulls++; else pesInicial += map.getValue();
			
			// aquest és el pès que ha de tenir cada pregunta que ha vengut inicialment sense pès indicat.
			Float pes = (1f - pesInicial) / (new Float(numNulls));
			
			// recorrem, de nou, el map<preg, pes> per omplir amb el pès calculat
			// aquelles preguntes que venien inicialment amb pès null
			for (Map.Entry<Integer, Float> map : pesos.entrySet())
				if (map.getValue() == null) map.setValue(pes);
		}
		return pesos;
	}
	
	public void setQuestionariDao(QuestionariDao questionariDao) {
		this.questionariDao = questionariDao;
	}

	public void insereixQuestionari(Questionari questionari, List<Pregunta> preguntes, Map<Integer, Float> pesos) {
		questionariDao.persistQuestionari(questionari);
		
		if (preguntes != null && preguntes.size() > 0) {
			pesos = completarPesos(preguntes, pesos);
			List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
			for (Pregunta pregunta : preguntes){
				PreguntaQuestionari pq = Util.inserirPreguntaQuestionari(pregunta, questionari, pesos.get(new Integer(pregunta.getCodi())));
				pqDao.persistPreguntaQuestionari(pq);
				llistaPQ.add(pq);
			}
			questionari.setPreguntes(llistaPQ);
			questionariDao.updateQuestionari(questionari);
		}
	}

	public List<Questionari> getQuestionaris() {
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris();
		if (!llistaQuestionaris.isEmpty()) return llistaQuestionaris;
		return null;
	}

	public Questionari getQuestionari(int codi) {
		Questionari questionari = questionariDao.getQuestionariByCodi(codi);
		List<PreguntaQuestionari> llistaPQ = pqDao.getRelacionsPreguntaQuestionari(questionari);
		for (PreguntaQuestionari pq : llistaPQ) {
			pq.setPregunta(preguntaService.getPregunta(pq.getPregunta().getCodi()));
		}
		questionari.setPreguntes(llistaPQ);
		return questionari;
	}
	
	public List<Questionari> getQuestionaris(Assignatura assignatura) {
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionarisByAssignatura(assignatura.getCodi());
		if (!llistaQuestionaris.isEmpty()) return llistaQuestionaris;
		return null;
	}

	public List<Questionari> getQuestionaris(Date dataInici, Date dataFi, String nom, String descripcio, String creador, String estat, Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2, Integer numPreguntes, int codiProfessor) {
		List<Questionari> llistaQuestionaris = questionariDao.getLlistaQuestionaris(dataInici, dataFi, nom, descripcio, creador, estat, dificultatTeorica1, dificultatTeorica2, dificultatPractica1, dificultatPractica2, numPreguntes, codiProfessor);
		if (!llistaQuestionaris.isEmpty()) return llistaQuestionaris;
		return null;
	}

	public Questionari publicaQuestionari(int codi) {
		// empram el servei getQuestionari per obtenir tota la info sobre el qüestinari amb aquest codi
		Questionari q = getQuestionari(codi);
		
		q.setEstat(Questionari.ESTAT_PUBLIC);
		return questionariDao.updateQuestionari(q);
	}
	
	public Questionari eliminaQuestionari(int codi) {
		// empram el servei getQuestionari per obtenir tota la info sobre el qüestionari amb aquest codi
		Questionari q = getQuestionari(codi);
		
		// Eliminam les preguntes associades al qüestionari que volem eliminar
		for (PreguntaQuestionari pq : q.getPreguntes()) pqDao.removePreguntaQuestionari(pq); 
		
		// Finalment, eliminam el qüestionari
		questionariDao.removeQuestionari(q);
		return q;
	}

	public Questionari modificaQuestionari(int codiQuestionari, Questionari questionari, List<Pregunta> preguntes, Map<Integer, Float> pesos) {
		// Obtenim la pregunta de BD donat el codi de pregunta passat per paràmetre
		Questionari qBd = getQuestionari(codiQuestionari);
		
		// Actualitzam els camps simples
		if (!qBd.getNom().equals(questionari.getNom())) 
			qBd.setNom(questionari.getNom()); 
		
		if (qBd.getDescripcio() == null || !qBd.getDescripcio().equals(questionari.getDescripcio())) 	
			qBd.setDescripcio(questionari.getDescripcio());
		
		if (qBd.getDificultatTeorica() == null || !qBd.getDificultatTeorica().equals(questionari.getDificultatTeorica())) 
			qBd.setDificultatTeorica(questionari.getDificultatTeorica());
		
		// Eliminam les relacions pregunta-questionari que té la pregunta de BD
		// (És a dir, estam desvinculant les preguntes del qüestionari, NO eliminant les preguntes)
		for (PreguntaQuestionari pq : qBd.getPreguntes()) pqDao.removePreguntaQuestionari(pq);
		
		// Agafam les noves preguntes que ens venen per paràmetre, així com els pesos. Afegim aquestes noves
		// preguntes al qüestionari nou
		// (És a dir, estam generant i creant a BD noves relacions pregunta-questionari. Finalment, les assignam al POJO questionari)
		List<PreguntaQuestionari> llistaPQ = new ArrayList<PreguntaQuestionari>();
		if (preguntes != null && preguntes.size() > 0) {
			pesos = completarPesos(preguntes, pesos);
			for (Pregunta pregunta : preguntes){
				PreguntaQuestionari pq = Util.inserirPreguntaQuestionari(pregunta, qBd, pesos.get(new Integer(pregunta.getCodi())));
				pqDao.persistPreguntaQuestionari(pq);
				llistaPQ.add(pq);
			}
			qBd.setPreguntes(llistaPQ);
		}
		
		// realitzam la modificació del qüestionari
		Questionari qMod = questionariDao.updateQuestionari(qBd);
		
		// Tot i que el qüestionari ja s'ha modificat correctament a BD, el POJO que retornam no està actualitzat.
		// Feim un getQuestionari, que refresqui el POJO:
		return getQuestionari(qMod.getCodi().intValue());
	}
	
}
