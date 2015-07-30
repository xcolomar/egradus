package org.projecte.egradus.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.repository.AlumneDao;
import org.springframework.stereotype.Repository;

@Repository(value = "alumneDao")
public class JPAAlumneDao implements AlumneDao {
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	public void persistAlumne(Alumne alumne) {
		em.persist(alumne);
	}
	
	@SuppressWarnings("unchecked")
	public List<Alumne> getAlumne(Persona persona, Assignatura assignatura) {
		String queryString = "select a"
				           +  " from Alumne a"
				           + " where a.persona = :persona"
				           +   " and a.assignatura = :assignatura";
		Query q = em.createQuery(queryString);
		q.setParameter("persona", persona);
		q.setParameter("assignatura", assignatura);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Alumne> getAlumnes() {
		return em.createQuery("select a from Alumne a").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Alumne> getAlumnes(Persona persona) {
		String queryString = "select a"
						   +  " from Alumne a"
						   + " where a.persona = :persona";
		Query q = em.createQuery(queryString);
		q.setParameter("persona", persona);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Alumne> getAlumnes(Assignatura assignatura) {
		String queryString = "select a"
						   +  " from Alumne a"
						   + " where a.assignatura = :assignatura";
		Query q = em.createQuery(queryString);
		q.setParameter("assignatura", assignatura);
		return q.getResultList();
	}

	public Alumne getAlumneByCodi(int codi) {
		return em.find(Alumne.class, codi);
	}
}
