package org.projecte.egradus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.repository.AlumneDao;
import org.projecte.egradus.repository.AssignaturaDao;
import org.projecte.egradus.repository.ProfessorDao;
import org.projecte.egradus.service.AssignaturaService;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssignaturaServiceImpl implements AssignaturaService {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AssignaturaDao assignaturaDao;
	
	@Autowired
	private ProfessorDao professorDao;
	
	@Autowired
	private AlumneDao alumneDao;
	
	/**
	 * Insereix l'Assignatura i la persona que l'ha creada com a professor.
	 * Abans de fer-ho, enllaça el professor a l'assignatura.
	 */
	public void insereixAssignatura(Assignatura assignatura){
		Professor professor = Util.getProfessor(assignatura.getCreador(), assignatura);
		List<Professor> professors = new ArrayList<Professor>();
		professors.add(professor);
		assignatura.setProfessors(professors);
		assignaturaDao.persistAssignatura(assignatura);
		professorDao.persistProfessor(professor);
	}
	
	public Assignatura getAssignatura(int codi){
		Assignatura assignatura = assignaturaDao.getAssignaturaByCodi(codi);
		if (assignatura != null) {
			assignatura.setProfessors(professorDao.getProfessors(assignatura));
			assignatura.setAlumnes(alumneDao.getAlumnes(assignatura));
		}
		return assignatura;
	}
	
	public List<Assignatura> getAssignatures(Persona persona) {
		if (persona == null) return null;
		List<Assignatura> assignatures = new ArrayList<Assignatura>();
		
		// Assignatures en les que és professor
		List<Professor> professors = professorDao.getProfessors(persona);
		if (!professors.isEmpty()) {
			for (int i = 0; i < professors.size(); i++) {
				assignatures.add(i, professors.get(i).getAssignatura());
			}
		}
		
	    // Assignatures en les que és alumne
		List<Alumne> alumnes = alumneDao.getAlumnes(persona);
		int indexAssignatures = assignatures.size();
		if (!alumnes.isEmpty()) {
			for (int i = 0; i < alumnes.size(); i++) {
				assignatures.add(indexAssignatures, alumnes.get(i).getAssignatura());
				indexAssignatures++;
			}
		}
		
		if (!assignatures.isEmpty()) return assignatures;
		return null;
	}
	
	public List<Assignatura> getAssignatures(Date dataInici, Date dataFi, String clauProfessor, String clauAlumne, String codiReferencia, String nomAssignatura, String anyAcademic, String creador){
		if (codiReferencia == null) System.out.println("codiReferencia és nul");
		else System.out.println("codiReferencia no es nul: " + codiReferencia.length() + ", " + codiReferencia);
		List<Assignatura> llista = assignaturaDao.getLlistaAssignatures(dataInici, dataFi, clauProfessor, clauAlumne, codiReferencia, nomAssignatura, anyAcademic, creador);
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public List<Assignatura> getAssignatures(){
		List<Assignatura> llista = assignaturaDao.getLlistaAssignatures();
		if (!llista.isEmpty()) return llista;
		return null;
	}
	
	public void setAssignaturaDao(AssignaturaDao assignaturaDao) {
		this.assignaturaDao = assignaturaDao;
	}
	
	public void setProfessorDao(ProfessorDao professorDao) {
		this.professorDao = professorDao;
	}
}
