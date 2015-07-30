package org.projecte.egradus.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Persona;
import org.projecte.egradus.repository.AssignaturaDao;
import org.springframework.stereotype.Repository;

@Repository(value = "assignaturaDao")
public class JPAAssignaturaDao implements AssignaturaDao {

	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	@SuppressWarnings("unchecked")
	public List<Assignatura> getLlistaAssignatures(){
		return em.createQuery("select a from Assignatura a").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Assignatura> getLlistaAssignaturesCreador(Persona persona){
		Query q = em.createQuery("select a from Assignatura a where a.creador = :creador");
		q.setParameter("creador", persona);
		return q.getResultList();
	}
	
	/**
	 * clauProfessor pot valer ('S', 'N', null) <br>
	 * clauAlumne    pot valer ('S', 'N', null) <br>
	 * <br>
	 * 'S' : Avaluam que tengui clau <br>
	 * 'N' : Avaluam que NO tengui clau <br>
	 * null: NO avaluam <br>
	 */
	@SuppressWarnings("unchecked")
	public List<Assignatura> getLlistaAssignatures(Date dataInici, Date dataFi, String clauProfessor, String clauAlumne, String codiReferencia, String nomAssignatura, String anyAcademic, String creador){
		String stringQuery = "select a "
			               +   "from Assignatura a "
			               +  "where (a.dataAlta >= :dataInici or :dataInici is null) "
			               +    "and (a.dataAlta <= :dataFi or :dataFi is null) "
			               +    "and (a.anyAcademic = :anyAcademic or :anyAcademic is null) "
			               +    "and lower(a.codiReferencia) like :codiReferencia "
			               +    "and lower(a.nom) like :nomAssignatura "
			               +    "and ("
			               +               "lower(a.creador.nom) like :creador "
			               +            "or lower(a.creador.primerLlinatge) like :creador "
			               +            "or lower(a.creador.segonLlinatge) like :creador "
			               +            "or lower(a.creador.alies) like :creador "
			               +        ") ";
		
		if (clauAlumne != null) {
			stringQuery += "and (a.clauAlumne is not null or :clauAlumne != 'S') "
                        +  "and (a.clauAlumne is null or :clauAlumne != 'N') ";
		}
		
		if (clauProfessor != null) {
			stringQuery += "and (a.clauProfessor is not null or :clauProfessor != 'S') " 
                        +  "and (a.clauProfessor is null or :clauProfessor != 'N') ";
		}
		
		Query q = em.createQuery(stringQuery);
		
		q.setParameter("dataInici", dataInici);
		q.setParameter("dataFi", dataFi);
		
		// codiReferencia, nomAssignatura i creador no vendran com a nulls, en tot cas com a strings buits.
		// Donat que només volem fer 'like' del seu valor i no un '=' estricte, en cas de venir com a strings buits
		// el paràmetre assignat a la Query serà, en el pitjor cas, '%%' que anirà bé igualment a la consulta
		if (codiReferencia != null) q.setParameter("codiReferencia", "%" + codiReferencia.toLowerCase() + "%");
		else q.setParameter("codiReferencia", "%");
		if (nomAssignatura != null) q.setParameter("nomAssignatura", "%" + nomAssignatura.toLowerCase() + "%");
		else q.setParameter("nomAssignatura", "%");
		if (creador != null) q.setParameter("creador", "%" + creador.toLowerCase() + "%");
		else q.setParameter("creador", "%");
		
		// en canvi, a 'anyAcademic', volem realitzar un '=' estricte, per tant hem de forçar
		// el paràmetre a ser 'null' en cas que el string 'anyAcademic' vengui buit.
		if (anyAcademic == null || anyAcademic.length() == 0) q.setParameter("anyAcademic", null);
		else q.setParameter("anyAcademic", anyAcademic);
		
		if (clauAlumne != null) q.setParameter("clauAlumne", clauAlumne);
		if (clauProfessor != null) q.setParameter("clauProfessor", clauProfessor);
		
		return q.getResultList();
	}
	
	public void persistAssignatura(Assignatura assignatura){
		em.persist(assignatura);
	}
	
	public Assignatura getAssignaturaByCodi(int codi){	
		return em.find(Assignatura.class, codi);
	}
	
}
