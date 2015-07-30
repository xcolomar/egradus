package org.projecte.egradus.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.repository.QuestionariDao;
import org.springframework.stereotype.Repository;

@Repository(value = "questionariDao")
public class JPAQuestionariDao implements QuestionariDao {
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@SuppressWarnings("unchecked")
	public List<Questionari> getLlistaQuestionaris() {
		return em.createQuery("select q from Questionari q").getResultList();
	}

	public void persistQuestionari(Questionari questionari) {
		em.persist(questionari);
	}
	
	public Questionari updateQuestionari(Questionari questionari) {
		return em.merge(questionari);
	}
	
	public void removeQuestionari(Questionari questionari) {
		em.remove(questionari);
	}

	public Questionari getQuestionariByCodi(int codi) {
		return em.find(Questionari.class, codi);
	}
	
	@SuppressWarnings("unchecked")
	public List<Questionari> getLlistaQuestionarisByAssignatura(int codiAssignatura) {
		String stringQuery = "select q "
		                   +   "from Questionari q "
		                   +  "where q.codi in ("
		                   +                     "select rq.questionari.codi "
		                   +                       "from RespostaQuestionari rq "
		                   +                      "where rq.assignador.codi in ("
		                   +                                                     "select pro.codi "
		                   +                                                       "from Professor pro "
		                   +                                                      "where pro.assignatura.codi = :codiAssignatura"
		                   +                                                  ")"
		                   +                  ")";
		
		Query q = em.createQuery(stringQuery);
		q.setParameter("codiAssignatura", codiAssignatura);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Questionari> getLlistaQuestionaris(Date dataInici, Date dataFi, String nom, String descripcio, String creador, String estat, Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2, Integer numPreguntes, int codiProfessor) {
		String stringQuery = "select q "
		                   +   "from Questionari q "
			               +  "where (q.dataAlta >= :dataInici or :dataInici is null) "
			               +    "and (q.dataAlta <= :dataFi or :dataFi is null) "
			               +    "and (lower(q.nom) like :nom or :nom is null) "
			               +    "and (lower(q.descripcio) like :descripcio or :descripcio is null) "
			               +    "and ("
			               +               "lower(q.creador.persona.nom) like :creador "
			               +            "or lower(q.creador.persona.primerLlinatge) like :creador "
			               +            "or lower(q.creador.persona.segonLlinatge) like :creador "
			               +            "or lower(q.creador.persona.alies) like :creador "
			               +            "or :creador is null"
			               +         ") ";
		
		// si l'estat és EDITABLE, hem de veure només els qüestionaris editables creades pel professor actual
		// si l'estat és PUBLIC, hem de veure els qüestionaris que hagi creat qualsevol professor
		if (estat != null) {
			stringQuery += "and q.estat = :estat ";
			if (estat.equals(Questionari.ESTAT_EDITABLE)) stringQuery += "and q.creador.codi = :codiProfessor ";
		} else {
			stringQuery += "and (q.estat = :estat_public or (q.estat = :estat_editable and q.creador.codi = :codiProfessor)) ";
		}
		
		if (dificultatTeorica1 != null) stringQuery += "and ifnull(q.dificultatTeorica, 0) >= :dificultatTeorica1 ";
		if (dificultatTeorica2 != null) stringQuery += "and ifnull(q.dificultatTeorica, 0) <= :dificultatTeorica2 ";
		
		if (dificultatPractica1 != null) stringQuery += "and ifnull(q.dificultatPractica, 0) >= :dificultatPractica1 ";
		if (dificultatPractica2 != null) stringQuery += "and ifnull(q.dificultatPractica, 0) <= :dificultatPractica2 ";
		
		Query q = em.createQuery(stringQuery);
		
		q.setParameter("dataInici", dataInici);
		q.setParameter("dataFi", dataFi);
		
		if (estat != null) {
			q.setParameter("estat", estat);
			if (estat.equals(Questionari.ESTAT_EDITABLE)) q.setParameter("codiProfessor", codiProfessor);
		} else {
			q.setParameter("estat_public", Questionari.ESTAT_PUBLIC);
			q.setParameter("estat_editable", Questionari.ESTAT_EDITABLE);
			q.setParameter("codiProfessor", codiProfessor);
		}
		
		if (dificultatTeorica1 != null) q.setParameter("dificultatTeorica1", dificultatTeorica1);
		if (dificultatTeorica2 != null) q.setParameter("dificultatTeorica2", dificultatTeorica2);
		if (dificultatPractica1 != null) q.setParameter("dificultatPractica1", dificultatPractica1);
		if (dificultatPractica2 != null) q.setParameter("dificultatPractica2", dificultatPractica2);
		
		if (nom == null) q.setParameter("nom", nom);
		else q.setParameter("nom", "%" + nom.toLowerCase() + "%");
		
		if (descripcio == null) q.setParameter("descripcio", descripcio);
		else q.setParameter("descripcio", "%" + descripcio.toLowerCase() + "%");
		
		if (creador == null) q.setParameter("creador", creador);
		else q.setParameter("creador", "%" + creador.toLowerCase() + "%");
		
		return q.getResultList();
	}

}
