package org.projecte.egradus.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.repository.ProfessorDao;
import org.springframework.stereotype.Repository;

@Repository(value = "professorDao")
public class JPAProfessorDao implements ProfessorDao {
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	public void persistProfessor(Professor professor) {
		em.persist(professor);
	}
	
	@SuppressWarnings("unchecked")
	public List<Professor> getProfessor(Persona persona, Assignatura assignatura) {
		String queryString = "select p"
				           +  " from Professor p"
				           + " where p.persona = :persona"
				           +   " and p.assignatura = :assignatura";
		Query q = em.createQuery(queryString);
		q.setParameter("persona", persona);
		q.setParameter("assignatura", assignatura);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Professor> getProfessors() {
		return em.createQuery("select p from Professor p").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Professor> getProfessors(Persona persona) {
		String queryString = "select p"
		                   +  " from Professor p"
		                   + " where p.persona = :persona";
		Query q = em.createQuery(queryString);
		q.setParameter("persona", persona);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Professor> getProfessors(Assignatura assignatura) {
		String queryString = "select p"
		                   +  " from Professor p"
		                   + " where p.assignatura = :assignatura";
		Query q = em.createQuery(queryString);
		q.setParameter("assignatura", assignatura);
		return q.getResultList();
	}
	
}
