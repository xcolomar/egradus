package org.projecte.egradus.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Opcio;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.repository.PreguntaDao;
import org.springframework.stereotype.Repository;

@Repository(value = "preguntaDao")
public class JPAPreguntaDao implements PreguntaDao {
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getLlistaPreguntes() {
		return em.createQuery("select p from Pregunta p").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getLlistaPreguntes(int codiProfessor) {
		
		String stringQuery = "select p "
                           +   "from Pregunta p "
                           +  "where p.estat = :estat_public "
                           +     "or (p.estat = :estat_editable and p.creador.codi = :codiProfessor)";
        
		Query q = em.createQuery(stringQuery);
		
		q.setParameter("estat_public", Pregunta.ESTAT_PUBLIC);
		q.setParameter("estat_editable", Pregunta.ESTAT_EDITABLE);
		q.setParameter("codiProfessor", codiProfessor);
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getLlistaPreguntesByAssignatura(int codiAssignatura) {
		String stringQuery = "select p "
		                   +   "from Pregunta p "
		                   +  "where p.codi in ("
		                   +                     "select rp.pregunta.codi "
		                   +                       "from RespostaPregunta rp "
		                   +                      "where rp.assignador.codi in ("
		                   +                                                     "select pro.codi "
		                   +                                                       "from Professor pro "
		                   +                                                      "where pro.assignatura.codi = :codiAssignatura"
		                   +                                                  ")"
		                   +                  ")";
		
		Query q = em.createQuery(stringQuery);
		q.setParameter("codiAssignatura", codiAssignatura);
		return q.getResultList();
	}
	
	public void persistPregunta(Pregunta pregunta) {
		em.persist(pregunta);
	}
	
	public void persistOpcio(Opcio opcio) {
		em.persist(opcio);
	}
	
	public Pregunta getPreguntaByCodi(int codi) {
		return em.find(Pregunta.class, codi);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pregunta> getLlistaPreguntes(Date dataInici, Date dataFi, String enunciat, String creador, Float dificultatTeorica1, Float dificultatTeorica2, Float dificultatPractica1, Float dificultatPractica2, Boolean raonarResposta, String tipus, String estat, int codiProfessor) {
		
		String stringQuery = "select p "
	                       +   "from Pregunta p "
			               +  "where (p.dataAlta >= :dataInici or :dataInici is null) "
			               +    "and (p.dataAlta <= :dataFi or :dataFi is null) "
			               +    "and (lower(p.enunciat) like :enunciat or :enunciat is null) "
			               +    "and ("
			               +               "lower(p.creador.persona.nom) like :creador "
			               +            "or lower(p.creador.persona.primerLlinatge) like :creador "
			               +            "or lower(p.creador.persona.segonLlinatge) like :creador "
			               +            "or lower(p.creador.persona.alies) like :creador "
			               +            "or :creador is null"
			               +         ") "
			               +    "and (p.tipus = :tipus or :tipus is null) ";
		
		// si l'estat és EDITABLE, hem de veure només les preguntes editables creades pel professor actual
		// si l'estat és PUBLIC, hem de veure les preguntes que hagi creat qualsevol professor
		if (estat != null) {
			stringQuery += "and p.estat = :estat ";
			if (estat.equals(Pregunta.ESTAT_EDITABLE)) stringQuery += "and p.creador.codi = :codiProfessor ";
		} else {
			stringQuery += "and (p.estat = :estat_public or (p.estat = :estat_editable and p.creador.codi = :codiProfessor)) ";
		}
		
		if (dificultatTeorica1 != null) stringQuery += "and ifnull(p.dificultatTeorica, 0) >= :dificultatTeorica1 ";
		if (dificultatTeorica2 != null) stringQuery += "and ifnull(p.dificultatTeorica, 0) <= :dificultatTeorica2 ";
		
		if (dificultatPractica1 != null) stringQuery += "and ifnull(p.dificultatPractica, 0) >= :dificultatPractica1 ";
		if (dificultatPractica2 != null) stringQuery += "and ifnull(p.dificultatPractica, 0) <= :dificultatPractica2 ";
		
		if (raonarResposta != null) {
			if (raonarResposta == Boolean.TRUE) stringQuery += "and p.raonarResposta = 1 ";
			 else stringQuery += "and p.raonarResposta = 0 ";
		}
		
		Query q = em.createQuery(stringQuery);
		
		q.setParameter("dataInici", dataInici);
		q.setParameter("dataFi", dataFi);
		q.setParameter("tipus", tipus);
		
		if (estat != null) {
			q.setParameter("estat", estat);
			if (estat.equals(Pregunta.ESTAT_EDITABLE)) q.setParameter("codiProfessor", codiProfessor);
		} else {
			q.setParameter("estat_public", Pregunta.ESTAT_PUBLIC);
			q.setParameter("estat_editable", Pregunta.ESTAT_EDITABLE);
			q.setParameter("codiProfessor", codiProfessor);
		}
		
		if (dificultatTeorica1 != null) q.setParameter("dificultatTeorica1", dificultatTeorica1);
		if (dificultatTeorica2 != null) q.setParameter("dificultatTeorica2", dificultatTeorica2);
		if (dificultatPractica1 != null) q.setParameter("dificultatPractica1", dificultatPractica1);
		if (dificultatPractica2 != null) q.setParameter("dificultatPractica2", dificultatPractica2);
		
		if (enunciat == null) q.setParameter("enunciat", enunciat);
		 else q.setParameter("enunciat", "%" + enunciat.toLowerCase() + "%");
		
		if (creador == null) q.setParameter("creador", creador);
		 else q.setParameter("creador", "%" + creador.toLowerCase() + "%");
		
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Opcio> getOpcions(Pregunta pregunta) {
		String stringQuery =  "select o "
                            +   "from Opcio o "
                            +  "where o.pregunta.codi = :pregunta";
		
		Query q = em.createQuery(stringQuery);
		q.setParameter("pregunta", pregunta.getCodi());
		return q.getResultList();
	}
	
	public Opcio getOpcioByCodi(int codi) {
		return em.find(Opcio.class, codi);
	}

	public Pregunta updatePregunta(Pregunta pregunta) {
		return em.merge(pregunta);
	}

	public void removePregunta(Pregunta pregunta) {
		em.remove(pregunta);
	}

	public void removeOpcio(Opcio opcio) {
		em.remove(opcio);
	}

	public Opcio updateOpcio(Opcio opcio) {
		return em.merge(opcio);
		
	}

}
