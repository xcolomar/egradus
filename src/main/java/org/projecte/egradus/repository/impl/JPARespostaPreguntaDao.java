package org.projecte.egradus.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.OpcioResposta;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.repository.RespostaPreguntaDao;
import org.springframework.stereotype.Repository;

@Repository(value = "respostaPreguntaDao")
public class JPARespostaPreguntaDao implements RespostaPreguntaDao {
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	public void persistRespostaPregunta(RespostaPregunta respostaPregunta) {
		em.persist(respostaPregunta);
	}
	
	public void persistOpcioResposta(OpcioResposta opcioResposta) {
		em.persist(opcioResposta);
	}
	
	public RespostaPregunta getRespostaPreguntaByCodi(int codi) {
		return em.find(RespostaPregunta.class, codi);
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaPregunta> getRespostaPregunta(Pregunta pregunta, Professor assignador, Alumne contestador) {
		Query q = em.createQuery("select rp "
	                            +  "from RespostaPregunta rp "
	                            + "where rp.alumne = :alumne "
	                            +   "and rp.pregunta = :pregunta "
	                            +   "and rp.assignador = :assignador "
	                            +   "and rp.respostaQuestionari.codi is null");
		q.setParameter("alumne", contestador);
		q.setParameter("pregunta", pregunta);
		q.setParameter("assignador", assignador);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaPregunta> getRespostesPregunta(Alumne alumne, Boolean contestades, Boolean corregides, Boolean revisades) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 +  	    "inner join rp.pregunta pre "
		                 + "where rp.respostaQuestionari.codi is null "
		                 +   "and rp.alumne = :alumne "
		                 +   "and rp.contestada = :contestada "
		                 +   "and rp.corregida = :corregida ";
		
		if (corregides) {
			if (revisades) {
				strQuery += "and ("
						  +          "(rp.revisada = :true and pre.raonarResposta = :true) "
						  +       "or (rp.revisada = :false and pre.raonarResposta = :false)"
						  +     ") ";
			} else {
				strQuery += "and rp.revisada = :false "
						  + "and pre.raonarResposta = :true ";
			}
		}
		
		if (corregides) 		strQuery += "order by rp.dataCorreccio desc";
		else if (contestades)   strQuery += "order by rp.dataContestacioFi desc";
		else 					strQuery += "order by rp.dataAlta desc";
		
		Query q = em.createQuery(strQuery);
		
		q.setParameter("alumne", alumne);
		q.setParameter("contestada", contestades);
		q.setParameter("corregida", corregides);
		
		if (corregides) {
			q.setParameter("true", Boolean.TRUE);
			q.setParameter("false", Boolean.FALSE);
		}
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<OpcioResposta> getOpcionsContestades(RespostaPregunta respostaPregunta) {
		Query q = em.createQuery("select o "
				                +  "from OpcioResposta o "
				                + "where o.respostaPregunta = :respostaPregunta");
		q.setParameter("respostaPregunta", respostaPregunta);
		return q.getResultList();
	}
	
	public RespostaPregunta updateRespostaPregunta(RespostaPregunta respostaPregunta) {
		return em.merge(respostaPregunta);
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaPregunta> getRespostesPreguntaPendentsCorregir(Professor professor) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 +  	    "inner join rp.pregunta pre "
		                 + "where rp.respostaQuestionari.codi is null "
		                 +   "and rp.assignador = :assignador "
		                 +   "and rp.contestada = :true "
		                 +   "and ("
		                 +            "(pre.tipus = :tipusPregunta and rp.corregida = :false) "
		                 +         "or (pre.raonarResposta = :true and rp.revisada  = :false)"
		                 +       ") "
		                 + "order by rp.dataContestacioFi desc";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("assignador", professor);
		q.setParameter("tipusPregunta", Pregunta.TIPUS_REC);
		q.setParameter("true", Boolean.TRUE);
		q.setParameter("false", Boolean.FALSE);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaPregunta> getRespostesPreguntaCorregides(Professor professor) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 +  	    "inner join rp.pregunta pre "
		                 + "where rp.respostaQuestionari.codi is null "
		                 +   "and rp.assignador = :assignador "
		                 +   "and rp.contestada = :true "
		                 +   "and ("
		                 +            "(pre.tipus = :tipusPregunta and rp.corregida = :true) "
		                 +         "or (pre.raonarResposta = :true and rp.revisada  = :true)"
		                 +       ") "
		                 + "order by rp.dataContestacioFi desc";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("assignador", professor);
		q.setParameter("tipusPregunta", Pregunta.TIPUS_REC);
		q.setParameter("true", Boolean.TRUE);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaPregunta> getRespostesPregunta(Professor professor, Boolean corregides, Boolean revisades) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 +  	    "inner join rp.pregunta pre "
		                 + "where rp.respostaQuestionari.codi is null "
		                 +   "and rp.assignador = :assignador "
		                 +   "and rp.contestada = :contestada "
		                 +   "and rp.corregida = :corregida ";
		
		if (corregides) {
			if (revisades) {
				strQuery += "and ("
						  +          "(rp.revisada = :true and pre.raonarResposta = :true) "
						  +       "or (rp.revisada = :false and pre.raonarResposta = :false and pre.tipus = :tipusPregunta)"
						  +     ") ";
			} else {
				strQuery += "and rp.revisada = :false "
						  + "and pre.raonarResposta = :true ";
			}
		} else {
			strQuery += "and pre.tipus = :tipusPregunta ";
		}
		
		// NO afegir ordenació per revisió, perque no totes se revisen!
		if (corregides) strQuery += "order by rp.dataCorreccio desc";
		else            strQuery += "order by rp.dataContestacioFi desc";
		
		Query q = em.createQuery(strQuery);
		
		q.setParameter("assignador", professor);
		q.setParameter("contestada", Boolean.TRUE);
		q.setParameter("corregida", corregides);
		
		if (corregides) {
			q.setParameter("true", Boolean.TRUE);
			q.setParameter("false", Boolean.FALSE);
			if (revisades) q.setParameter("tipusPregunta", Pregunta.TIPUS_REC);
		} else {
			q.setParameter("tipusPregunta", Pregunta.TIPUS_REC);
		}
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaPregunta> getRespostesPregunta(Pregunta pregunta, Assignatura assignatura, Boolean contestades, Boolean corregides, Boolean assignacioDirecta) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 + "where 1 = 1 ";
		
		if (pregunta != null) strQuery += "and rp.pregunta = :pregunta ";
		else {
			if (assignatura != null) {
				strQuery += "and rp.pregunta in ("
		                   +                      "select rp2.pregunta "
		                   +                        "from RespostaPregunta rp2 "
		                   +                       "where rp2.assignador in ("
		                   +                                                  "select pro "
		                   +                                                    "from Professor pro "
		                   +                                                   "where pro.assignatura = :assignatura"
		                   +                                               ")"
		                   +                   ") ";
			}
		}
		
		if (assignatura != null) {
			strQuery += "and rp.assignador in ("
					  +                         "select pro "
					  +                           "from Professor pro "
					  +                          "where pro.assignatura = :assignatura"
					  +                      ") ";
		}
		
		if (assignacioDirecta != null) {
			if (assignacioDirecta.equals(Boolean.TRUE)) strQuery += "and rp.respostaQuestionari.codi is null ";
			else strQuery += "and rp.respostaQuestionari.codi is not null ";
		}
		
		if (contestades != null) strQuery += "and rp.contestada = :contestada ";
		if (corregides != null)  strQuery += "and rp.corregida = :corregida ";
		
		strQuery += "order by rp.dataCorreccio desc";
		
		Query q = em.createQuery(strQuery);
		if (pregunta    != null) q.setParameter("pregunta", pregunta);
		else if (assignatura != null) q.setParameter("assignatura", assignatura);
		if (assignatura != null) q.setParameter("assignatura", assignatura);
		if (contestades != null) q.setParameter("contestada", contestades);
		if (corregides  != null) q.setParameter("corregida", corregides);
		
		return q.getResultList();
	}
	
}
