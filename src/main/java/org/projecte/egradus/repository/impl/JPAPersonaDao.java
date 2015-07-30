package org.projecte.egradus.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.repository.PersonaDao;
import org.springframework.stereotype.Repository;

@Repository(value = "personaDao")
public class JPAPersonaDao implements PersonaDao {
	
	private EntityManager em = null;
	
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@SuppressWarnings("unchecked")
	public List<Persona> getPersones(){
    	return em.createQuery("select p from Persona p").getResultList();
    }
    
	public void persistPersona(Persona persona) {
		em.persist(persona);
	}
	
	@SuppressWarnings("unchecked")
	public List<Persona> getPersonesByAlies(String alies){
		Query q = (Query) em.createQuery("select p from Persona p where p.alies = :alies");
		q.setParameter("alies", alies);
		return q.getResultList();
	}
	
	public Persona getPersona(Integer codi) {
		return em.find(Persona.class, codi);
	}

}
