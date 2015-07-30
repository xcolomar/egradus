package org.projecte.egradus.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Assignatura;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Professor;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.domain.RespostaPregunta;
import org.projecte.egradus.domain.RespostaQuestionari;
import org.projecte.egradus.repository.RespostaQuestionariDao;
import org.springframework.stereotype.Repository;

@Repository(value = "respostaQuestionariDao")
public class JPARespostaQuestionariDao implements RespostaQuestionariDao {
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
	
	public void persistRespostaQuestionari(RespostaQuestionari respostaQuestionari) {
		em.persist(respostaQuestionari);
	}

	public RespostaQuestionari getRespostaQuestionariByCodi(int codi) {
		return em.find(RespostaQuestionari.class, codi);
	}

	@SuppressWarnings("unchecked")
	public List<RespostaQuestionari> getRespostaQuestionari(Questionari questionari, Professor assignador, Alumne contestador) {
		Query q = em.createQuery("select rq "
				                +  "from RespostaQuestionari rq "
				                + "where rq.alumne = :alumne "
				                +   "and rq.questionari = :questionari "
				                +   "and rq.assignador = :assignador");
		q.setParameter("alumne", contestador);
		q.setParameter("questionari", questionari);
		q.setParameter("assignador", assignador);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaQuestionari> getRespostesQuestionari(Alumne alumne, Boolean contestades, Boolean corregides, Boolean revisades) {
		String strQuery = "select rq "
		                 +  "from RespostaQuestionari rq "
		                 +  	    "inner join rq.questionari qst "
		                 + "where rq.alumne = :alumne "
		                 +   "and rq.contestat = :contestat "
		                 +   "and rq.corregit = :corregit ";
		
		if (corregides) {
			if (revisades) {
				strQuery += "and ("
						  +          "(rq.revisat = :true and exists ("
			              +                                            "select pq "
			              +                                              "from PreguntaQuestionari pq "
			              +                                             "where pq.pregunta.raonarResposta = :true "
			              +                                               "and pq.questionari.codi = qst.codi"
			              +                                         ")"
			              +          ") "
						  +       "or (rq.revisat = :false and not exists ("
						  +                                                 "select pq "
			              +                                                   "from PreguntaQuestionari pq "
			              +                                                  "where pq.pregunta.raonarResposta = :true "
			              +                                                    "and pq.questionari.codi = qst.codi"
			              +                                              ")"
			              +          ")"
						  +     ") ";
			} else {
				strQuery += "and rq.revisat = :false "
						  + "and exists ("
			              +                "select pq "
			              +                  "from PreguntaQuestionari pq "
			              +                 "where pq.pregunta.raonarResposta = :true "
			              +                   "and pq.questionari.codi = qst.codi"
			              +            ") ";
			}
		}
		
		if (corregides) 		strQuery += "order by rq.dataCorreccio desc";
		else if (contestades)   strQuery += "order by rq.dataContestacioFi desc";
		else 					strQuery += "order by rq.dataAlta desc";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("alumne", alumne);
		q.setParameter("contestat", contestades);
		q.setParameter("corregit", corregides);
		if (corregides) {
			q.setParameter("true", Boolean.TRUE);
			q.setParameter("false", Boolean.FALSE);
		}
		return q.getResultList();
	}

	public RespostaQuestionari updateRespostaQuestionari(RespostaQuestionari respostaQuestionari) {
		return em.merge(respostaQuestionari);
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaQuestionari> getRespostesQuestionariPendentsCorregir(Professor professor) {
		String strQuery = "select rq "
		                 +  "from RespostaQuestionari rq "
		                 + 			"inner join rq.questionari qst "
		                 + "where rq.assignador = :assignador "
		                 +   "and rq.contestat = :true "
		                 +   "and ("
		                 +            "(rq.corregit = :false and exists ("
			             +                                                "select pq "
			             +                                                  "from PreguntaQuestionari pq "
			             +                                                 "where pq.pregunta.tipus = :tipusPregunta "
			             +                                                   "and pq.questionari.codi = qst.codi"
			             +                                             ")"
			             +            ") "
			             +         "or (rq.revisat = :false  and exists ("
			             +                                                "select pq "
			             +                                                  "from PreguntaQuestionari pq "
			             +                                                 "where pq.pregunta.raonarResposta = :true "
			             +                                                   "and pq.questionari.codi = qst.codi"
			             +                                             ")"
			             +            ")"
			             +       ") "
			             + "order by rq.dataContestacioFi desc";
		
		
		Query q = em.createQuery(strQuery);
		q.setParameter("assignador", professor);
		q.setParameter("tipusPregunta", Pregunta.TIPUS_REC);
		q.setParameter("true", Boolean.TRUE);
		q.setParameter("false", Boolean.FALSE);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaQuestionari> getRespostesQuestionariCorregides(Professor professor) {
		String strQuery = "select rq "
		                 +  "from RespostaQuestionari rq "
		                 + 			"inner join rq.questionari qst "
		                 + "where rq.assignador = :assignador "
		                 +   "and rq.contestat = :true "
		                 +   "and ("
		                 +            "(rq.corregit = :true and exists ("
			             +                                               "select pq "
			             +                                                 "from PreguntaQuestionari pq "
			             +                                                "where pq.pregunta.tipus = :tipusPregunta "
			             +                                                  "and pq.questionari.codi = qst.codi"
			             +                                            ")"
			             +            ") "
			             +         "or (rq.revisat = :true  and exists ("
			             +                                               "select pq "
			             +                                                 "from PreguntaQuestionari pq "
			             +                                                "where pq.pregunta.raonarResposta = :true "
			             +                                                  "and pq.questionari.codi = qst.codi"
			             +                                            ")"
			             +            ")"
			             +       ") "
			             + "order by rq.dataContestacioFi desc";
		
		
		Query q = em.createQuery(strQuery);
		q.setParameter("assignador", professor);
		q.setParameter("tipusPregunta", Pregunta.TIPUS_REC);
		q.setParameter("true", Boolean.TRUE);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaQuestionari> getRespostesQuestionari(Professor professor, Boolean corregides, Boolean revisades) {
		String strQuery = "select rq "
		                 +  "from RespostaQuestionari rq "
		                 + 			"inner join rq.questionari qst "
		                 + "where rq.assignador = :assignador "
		                 +   "and rq.contestat = :contestat "
		                 +   "and rq.corregit = :corregit ";
		
		if (corregides) {
			if (revisades) {
				strQuery += "and ("
						  +          "(rq.revisat = :true and exists ("
			              +                                            "select pq "
			              +                                              "from PreguntaQuestionari pq "
			              +                                             "where pq.pregunta.raonarResposta = :true "
			              +                                               "and pq.questionari.codi = qst.codi"
			              +                                         ")"
			              +          ") "
						  +       "or (rq.revisat = :false and not exists ("
						  +                                                 "select pq "
			              +                                                   "from PreguntaQuestionari pq "
			              +                                                  "where pq.pregunta.raonarResposta = :true "
			              +                                                    "and pq.questionari.codi = qst.codi"
			              +                                              ") "
			              +                               "and exists ("
			              +                                             "select pq "
			              +                                               "from PreguntaQuestionari pq "
			              +                                              "where pq.pregunta.tipus = :tipusPregunta "
			              +                                                "and pq.questionari.codi = qst.codi"
			              +                                          ")"
			              +          ")"
						  +     ") ";
			} else {
				strQuery += "and rq.revisat = :false "
						  + "and exists ("
						  +                "select pq "
			              +                  "from PreguntaQuestionari pq "
			              +                 "where pq.pregunta.raonarResposta = :true "
			              +                   "and pq.questionari.codi = qst.codi"
			              +            ") ";
			}
		} else {
			strQuery += "and exists ("
	                  +               "select pq "
	                  +                 "from PreguntaQuestionari pq "
	                  +                "where pq.pregunta.tipus = :tipusPregunta "
	                  +                  "and pq.questionari.codi = qst.codi "
	                  +            ") ";
		}
		
		if (corregides) strQuery += "order by rq.dataCorreccio desc";
		else            strQuery += "order by rq.dataContestacioFi desc";
		
		Query q = em.createQuery(strQuery);

		q.setParameter("assignador", professor);
		q.setParameter("contestat", Boolean.TRUE);
		q.setParameter("corregit", corregides);
		
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
	public List<RespostaPregunta> getRespostaPreguntes(RespostaQuestionari respostaQuestionari) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 + "where rp.respostaQuestionari = :respostaQuestionari";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("respostaQuestionari", respostaQuestionari);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RespostaQuestionari> getRespostesQuestionari(Questionari questionari, Assignatura assignatura, Boolean contestats, Boolean corregits, Boolean assignacioDirecta) {
		String strQuery = "select rq "
		                +   "from RespostaQuestionari rq "
		                +  "where 1 = 1 ";
		
		if (questionari != null) strQuery += "and rq.questionari = :questionari ";
		else {
			if (assignatura != null) {
				strQuery += "and rq.questionari in ("
		                  +                           "select rq2.questionari "
		                  +                             "from RespostaQuestionari rq2 "
		                  +                            "where rq2.assignador in ("
		                  +                                                       "select pro "
		                  +                                                         "from Professor pro "
		                  +                                                        "where pro.assignatura = :assignatura"
		                  +                                                    ")"
		                  +                        ") ";
			}
		}
		
		if (assignatura != null) {
			strQuery += "and rq.assignador in ("
					  +                         "select pro "
					  +                           "from Professor pro "
					  +                          "where pro.assignatura = :assignatura"
					  +                      ") ";
		}
		
		if (contestats != null) strQuery += "and rq.contestat = :contestat ";
		if (corregits != null)  strQuery += "and rq.corregit = :corregit ";
		
		strQuery += "order by rq.dataCorreccio desc";
		
		Query q = em.createQuery(strQuery);
		if (questionari != null) q.setParameter("questionari", questionari);
		else if (assignatura != null) q.setParameter("assignatura", assignatura);
		if (assignatura != null) q.setParameter("assignatura", assignatura);
		if (contestats  != null) q.setParameter("contestat", contestats);
		if (corregits   != null) q.setParameter("corregit", corregits);
		
		return q.getResultList();
	}

}
