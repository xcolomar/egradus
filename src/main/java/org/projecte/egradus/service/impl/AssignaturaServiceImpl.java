package org.projecte.egradus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.repository.AlumneRepository;
import org.projecte.egradus.repository.AssignaturaRepository;
import org.projecte.egradus.repository.ProfessorRepository;
import org.projecte.egradus.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AssignaturaServiceImpl {

	@Autowired
	private AssignaturaRepository assignaturaRepositori;

	@Autowired
	private ProfessorRepository professorDao;

	@Autowired
	private AlumneRepository alumneRepositori;

	/**
	 * Insereix l'Assignatura i la persona que l'ha creada com a professor. Abans de
	 * fer-ho, enllaça el professor a l'assignatura.
	 * 
	 * @param assignatura
	 */
	public void insereixAssignatura(Assignatura assignatura) {
		Professor professor = Util.getProfessor(assignatura.getCreador(), assignatura);
		List<Professor> professors = new ArrayList<Professor>();
		professors.add(professor);
		assignatura.setProfessors(professors);
		assignaturaRepositori.saveAndFlush(assignatura);
		professorDao.persistProfessor(professor);
	}

	/**
	 * Agafa l'assignatura amb el codi (PK) que es passa com a paràmetre
	 * 
	 * @param codi
	 * @return
	 */
	public Assignatura getAssignatura(Integer codi) {
		Assignatura assignatura = null;
		Optional<Assignatura> assignaturaOpt = assignaturaRepositori.findById(codi);
		if (assignaturaOpt.isPresent()) {
			assignatura = assignaturaOpt.get();
			assignatura.setProfessors(professorDao.getProfessors(assignatura));
			assignatura.setAlumnes(alumneRepositori.findByAssignatura(assignatura));
		}
		return assignatura;
	}

	/**
	 * Agafa les assignatures en les quals la persona que es passa com a paràmetre
	 * és professor o alumne, és a dir, totes les assignatures en les que intervé la
	 * persona.
	 * 
	 * @param persona
	 * @return
	 */
	public List<Assignatura> getAssignatures(Persona persona) {
		if (persona == null)
			return null;
		List<Assignatura> assignatures = new ArrayList<Assignatura>();

		// Assignatures en les que és professor
		List<Professor> professors = professorDao.getProfessors(persona);
		if (!professors.isEmpty()) {
			for (int i = 0; i < professors.size(); i++) {
				assignatures.add(i, professors.get(i).getAssignatura());
			}
		}

		// Assignatures en les que és alumne
		List<Alumne> alumnes = alumneRepositori.findByPersona(persona);
		int indexAssignatures = assignatures.size();
		if (!alumnes.isEmpty()) {
			for (int i = 0; i < alumnes.size(); i++) {
				assignatures.add(indexAssignatures, alumnes.get(i).getAssignatura());
				indexAssignatures++;
			}
		}

		if (!assignatures.isEmpty())
			return assignatures;
		return null;
	}

	/**
	 * Agafa les assignatures amb possibilitat de filtrar per els següents
	 * paràmetres:
	 * 
	 * @param dataInici
	 * @param dataFi
	 * @param clauProfessor
	 * @param clauAlumne
	 * @param codiReferencia
	 * @param nomAssignatura
	 * @param anyAcademic
	 * @param creador
	 * @return
	 */
	public List<Assignatura> getAssignatures(Date dataInici, Date dataFi, String clauProfessor, String clauAlumne,
			String codiReferencia, String nomAssignatura, String anyAcademic, String creador) {
		if (codiReferencia == null)
			System.out.println("codiReferencia és nul");
		else
			System.out.println("codiReferencia no es nul: " + codiReferencia.length() + ", " + codiReferencia);
		List<Assignatura> llista = assignaturaRepositori.getLlista(dataInici, dataFi, clauProfessor, clauAlumne,
				codiReferencia, nomAssignatura, anyAcademic, creador);
		if (!llista.isEmpty())
			return llista;
		return null;
	}

	/**
	 * Agafa totes les assignatures
	 * 
	 * @return
	 */
	public List<Assignatura> getAssignatures() {
		return assignaturaRepositori.findAll();
	}
}
