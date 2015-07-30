package org.projecte.egradus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.repository.PreguntaDao;
import org.projecte.egradus.repository.RespostaPreguntaDao;
import org.projecte.egradus.service.PreguntaService;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PreguntaServiceImpl implements PreguntaService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired (required = true)
	private PreguntaDao preguntaDao;
	
	@Autowired (required = true)
	private RespostaPreguntaDao respostaPreguntaDao;
	
	
	
	public void insereixPregunta(Pregunta pregunta, List<Opcio> opcions) {
		preguntaDao.persistPregunta(pregunta);
		for (Opcio opcio : opcions){
			opcio.setPregunta(pregunta);
			preguntaDao.persistOpcio(opcio);
		}
	}
	
	public List<Pregunta> getPreguntes() {
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes();
		if (!llistaPreguntes.isEmpty()) return llistaPreguntes;
		return null;
	}
	
	public List<Pregunta> getPreguntes(int codiProfessor) {
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(codiProfessor);
		if (!llistaPreguntes.isEmpty()) return llistaPreguntes;
		return null;
	}
	
	public Pregunta getPregunta(int codi) {
		Pregunta pregunta = preguntaDao.getPreguntaByCodi(codi);
		List<Opcio> opcions = preguntaDao.getOpcions(pregunta);
		if (opcions != null) pregunta.setOpcions(opcions);
		return pregunta;
	}
	
	public List<Pregunta> getPreguntes(Date dataInici, Date dataFi, String enunciat, String creador, Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2, Boolean raonarResposta, String tipus, String estat, int codiProfessor) {
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntes(dataInici, dataFi, enunciat, creador, dificultatTeorica1, dificultatTeorica2, dificultatPractica1, dificultatPractica2, raonarResposta, tipus, estat, codiProfessor);
		if (!llistaPreguntes.isEmpty()) return llistaPreguntes;
		return null;
	}
	
	public List<Pregunta> getPreguntes(Assignatura assignatura) {
		List<Pregunta> llistaPreguntes = preguntaDao.getLlistaPreguntesByAssignatura(assignatura.getCodi());
		if (!llistaPreguntes.isEmpty()) return llistaPreguntes;
		return null;
	}
	
	public Opcio getOpcio(int codi) {
		return preguntaDao.getOpcioByCodi(codi);
	}

	public Pregunta publicaPregunta(int codi) {
		// empram el servei getPregunta per obtenir tota la info sobre la pregunta amb aquest codi
		Pregunta p = getPregunta(codi);
		
		p.setEstat(Pregunta.ESTAT_PUBLIC);
		return preguntaDao.updatePregunta(p);
	}

	public Pregunta eliminaPregunta(int codi) {
		// empram el servei getPregunta per obtenir tota la info sobre la pregunta amb aquest codi
		Pregunta p = getPregunta(codi);
		
		// Eliminam les opcions associades a la pregunta que volem eliminar
		for (Opcio opcio : p.getOpcions()) preguntaDao.removeOpcio(opcio); 
		
		// Finalment, eliminam la pregunta
		preguntaDao.removePregunta(p);
		return p;
	}
	
	/**
	 * 
	 * @param codiPregunta
	 * 				codi de la pregunta guardada a BD que hem de modificar
	 * @param pregunta
	 * 				dades de pregunta que hem d'escriure a BD
	 * @param opcions
	 * 				dades de pregunta (les opcions) que hem d'escriure a BD
	 * @return
	 */
	public Pregunta modificaPregunta(int codiPregunta, Pregunta pregunta, List<Opcio> opcions) {
		// Obtenim la pregunta de BD donat el codi de pregunta passat per paràmetre
		Pregunta pBd = getPregunta(codiPregunta);
		
		// Actualitzam els camps simples
		if (!pBd.getEnunciat().equals(pregunta.getEnunciat())) 
			pBd.setEnunciat(pregunta.getEnunciat()); 
		
		if (pBd.getDificultatTeorica() == null || !pBd.getDificultatTeorica().equals(pregunta.getDificultatTeorica())) 
			pBd.setDificultatTeorica(pregunta.getDificultatTeorica());
		
		if (!pBd.getRaonarResposta().equals(pregunta.getRaonarResposta())) 
			pBd.setRaonarResposta(pregunta.getRaonarResposta());
		
		// Actualitzam el camp vofVertader
		if (pregunta.getVofVertader() != null) 		pBd.setVofVertader(pregunta.getVofVertader());
		else if (pregunta.getVofVertader() == null) pBd.setVofVertader(null);
		
		// Si el tipus no varia, i la pregunta té opcions
		if (pBd.getTipus().equals(pregunta.getTipus()) && !pBd.getTipus().equals(Pregunta.TIPUS_REC)) {
			int i = 0;
			Opcio oBd;
			Opcio oPojo;
			while ((i < pBd.getOpcions().size()) && (i < opcions.size())) {
				oBd = pBd.getOpcions().get(i);
				oPojo = opcions.get(i);
				if (!oBd.getText().equals(oPojo.getText()))  		oBd.setText(oPojo.getText());
				if (!oBd.getCorrecta().equals(oPojo.getCorrecta())) oBd.setCorrecta(oPojo.getCorrecta());
				preguntaDao.updateOpcio(oBd);
				i++;
			}
			// Si queden opcions per afegir:
			if ((i == pBd.getOpcions().size()) && (i < opcions.size())) {
				Opcio oBdNova;
				while (i < opcions.size()) {
					oPojo = opcions.get(i);
					oBdNova = Util.inserirOpcio(pBd, oPojo.getText(), oPojo.getCorrecta(), new Date());
					preguntaDao.persistOpcio(oBdNova);
					i++;
				}
			// Si s'han d'eliminar opcions:
			} else if ((i < pBd.getOpcions().size()) && (i == opcions.size())) {
				List<Opcio> opcionsBdPerEliminar = new ArrayList<Opcio>();
				while (i < pBd.getOpcions().size()) {
					oBd = pBd.getOpcions().get(i);
					opcionsBdPerEliminar.add(oBd);
					preguntaDao.removeOpcio(oBd);
					i++;
				}
				pBd.getOpcions().removeAll(opcionsBdPerEliminar);
			}
		}
		
		// Si els tipus són distints, actualitzam el tipus, eliminam les opcions que hi han a BD i afegim les noves
		if (!pBd.getTipus().equals(pregunta.getTipus())) {
			pBd.setTipus(pregunta.getTipus());
			// Eliminam les opcions que hi ha a BD
			if (pBd.getOpcions() != null) for (Opcio oBd : pBd.getOpcions()) preguntaDao.removeOpcio(oBd);
			// Afegim les noves opcions
			pBd.setOpcions(opcions);
			for (Opcio oPojo : opcions) {
				oPojo.setPregunta(pBd);
				preguntaDao.persistOpcio(oPojo);
			}
		}
		
		// realitzam la modificació de la pregunta
		Pregunta pMod = preguntaDao.updatePregunta(pBd);
		
		// Tot i que la pregunta ja s'ha modificat correctament a BD, el POJO que retornam no està actualitzat.
		// Feim un getPregunta, que refresqui el POJO:
		return getPregunta(pMod.getCodi().intValue());
	}
	
}
