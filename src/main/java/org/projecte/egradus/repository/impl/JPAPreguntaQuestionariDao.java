package org.projecte.egradus.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.PreguntaQuestionari;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.repository.PreguntaQuestionariDao;
import org.springframework.stereotype.Repository;

@Repository(value = "preguntaQuestionariDao")
public class JPAPreguntaQuestionariDao implements PreguntaQuestionariDao {
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	public void persistPreguntaQuestionari(PreguntaQuestionari preguntaQuestionari) {
		em.persist(preguntaQuestionari);
	}

	@SuppressWarnings("unchecked")
	public List<PreguntaQuestionari> getRelacionsPreguntaQuestionari() {
		return em.createQuery("select pq from PreguntaQuestionari pq").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PreguntaQuestionari> getPreguntaQuestionari(Pregunta pregunta, Questionari questionari) {
		String queryString = "select pq"
				           +  " from PreguntaQuestionari pq"
				           + " where pq.pregunta = :pregunta"
				           +   " and pq.questionari = :questionari";
		Query q = em.createQuery(queryString);
		q.setParameter("pregunta", pregunta);
		q.setParameter("questionari", questionari);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PreguntaQuestionari> getRelacionsPreguntaQuestionari(Pregunta pregunta) {
		String queryString = "select pq"
				           +  " from PreguntaQuestionari pq"
				           + " where pq.pregunta = :pregunta";
		Query q = em.createQuery(queryString);
		q.setParameter("pregunta", pregunta);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PreguntaQuestionari> getRelacionsPreguntaQuestionari(Questionari questionari) {
		String queryString = "select pq"
				           +  " from PreguntaQuestionari pq"
				           + " where pq.questionari = :questionari";
		Query q = em.createQuery(queryString);
		q.setParameter("questionari", questionari);
		return q.getResultList();
	}

	public PreguntaQuestionari getPreguntaQuestionariByCodi(int codi) {
		return em.find(PreguntaQuestionari.class, codi);
	}

	public PreguntaQuestionari updatePreguntaQuestionari(PreguntaQuestionari preguntaQuestionari) {
		return em.merge(preguntaQuestionari);
	}

	public void removePreguntaQuestionari(PreguntaQuestionari preguntaQuestionari) {
		em.remove(preguntaQuestionari);
	}

}
