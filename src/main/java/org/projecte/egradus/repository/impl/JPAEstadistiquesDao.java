package org.projecte.egradus.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.projecte.egradus.domain.Alumne;
import org.projecte.egradus.domain.Pregunta;
import org.projecte.egradus.domain.Questionari;
import org.projecte.egradus.repository.EstadistiquesDao;
import org.springframework.stereotype.Repository;

@Repository(value = "estadistiquesDao")
public class JPAEstadistiquesDao  implements EstadistiquesDao {

	private static final long serialVersionUID = 1L;
	
	private EntityManager em = null;
	
	@PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRespostesPregunta(Alumne alumne) {
		String strQuery = "select pre "
				         +     ", rsp.nota "
				         +     ", rsp.tempsResposta "
				         +     ", rsp.dataContestacioFi "
				         +     ", rsp.dataCorreccio "
				         +     ", (" // nota mitja de tots els alumnes de l'assignatura actual
				         + 		    "select avg(rp.nota) "
				         +            "from RespostaPregunta rp "
				         +           "where rp.respostaQuestionari.codi is null "
				         +             "and rp.alumne.codi in ("
				         +                                      "select alu.codi "
				         +                                        "from Alumne alu "
				         +                                       "where alu.assignatura.codi = ("
				         +                                                                        "select alu2.assignatura.codi "
				         +                                                                          "from Alumne alu2 "
				         +                                                                         "where alu2.codi = rsp.alumne.codi"
				         +																	  ")"
				         +                                   ") "
				         +             "and rp.pregunta.codi = rsp.pregunta.codi"
				         +       ") "
				         +     ", (" // nota mitja de tots els alumnes, de qualsevol assignatura d'egradus
				         + 		    "select avg(rp.nota) "
				         +            "from RespostaPregunta rp "
				         +           "where rp.respostaQuestionari.codi is null "
				         +             "and rp.pregunta.codi = rsp.pregunta.codi"
				         +       ") "
				         +     ", ((" // temps de resposta mig de tots els alumnes de l'assignatura actual
				         + 		    "select avg(rp.tempsResposta) "
				         +            "from RespostaPregunta rp "
				         +           "where rp.respostaQuestionari.codi is null "
				         +             "and rp.alumne.codi in ("
				         +                                      "select alu.codi "
				         +                                        "from Alumne alu "
				         +                                       "where alu.assignatura.codi = ("
				         +                                                                        "select alu2.assignatura.codi "
				         +                                                                          "from Alumne alu2 "
				         +                                                                         "where alu2.codi = rsp.alumne.codi"
				         +																	  ")"
				         +                                   ") "
				         +             "and rp.pregunta.codi = rsp.pregunta.codi"
				         +       ")/1000) "
		                 +  "from RespostaPregunta rsp "
		                 +		    "inner join rsp.pregunta pre "
		                 + "where rsp.contestada = :contestada "
		                 +   "and rsp.corregida = :corregida "
		                 +   "and rsp.anonima = :anonima "
		                 +   "and rsp.alumne = :alumne "
		                 +   "and rsp.respostaQuestionari.codi is null "
		                 + "order by rsp.dataCorreccio";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestada", Boolean.TRUE);
		q.setParameter("corregida", Boolean.TRUE);
		q.setParameter("anonima", Boolean.FALSE);
		q.setParameter("alumne", alumne);
		
		List<Object[]> llistaArrayObjectes = q.getResultList();
		
		List<Map<String, Object>> estadistiquesAlumne = new ArrayList<Map<String, Object>>(llistaArrayObjectes.size());
		for (Object[] arrayObjectes : llistaArrayObjectes) {
			Pregunta pregunta = (Pregunta) arrayObjectes[0];
			Float nota = parsejaFloat(arrayObjectes[1]);
			Float tempsResposta = (Float) (parsejaLong(arrayObjectes[2]).floatValue() / 1000);
			Date dataContestacioFi = (Date) arrayObjectes[3];
			Date dataCorreccio = (Date) arrayObjectes[4];
			Float notaMitjaAss = parsejaFloat(arrayObjectes[5]);
			Float notaMitjaEgr = parsejaFloat(arrayObjectes[6]);
			Float tempsRespostaMigAss = parsejaFloat(arrayObjectes[7]);
			// El temps de resposta mig de tot Egradus el tenim directament a pregunta.getTempsRespostaMig().
			// Això no passa amb la nota mitja de tot Egradus ja que quan contestam Questionaris, s'actualitza
			// la nota mitja de les preguntes que hi pertanyen, però no el temps de resposta mig.
			
			Map<String, Object> liniaEstadistiquesAlumne = new HashMap<String, Object>();
			liniaEstadistiquesAlumne.put(EstadistiquesDao.PREGUNTA_CODI, pregunta.getCodi());
			liniaEstadistiquesAlumne.put(EstadistiquesDao.PREGUNTA_ENUNCIAT, pregunta.getEnunciat());
			liniaEstadistiquesAlumne.put(EstadistiquesDao.PREGUNTA_TIPUS, pregunta.getTipus());
			liniaEstadistiquesAlumne.put(EstadistiquesDao.NOTA, nota);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA, notaMitjaAss);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.NOTA_MITJA_EGRADUS, notaMitjaEgr);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.TEMPS_RESPOSTA, tempsResposta);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA, tempsRespostaMigAss);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS, (Float) (pregunta.getTempsRespostaMig().floatValue() / 1000));
			liniaEstadistiquesAlumne.put(EstadistiquesDao.DATA_CONTESTACIO_FI, dataContestacioFi);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.DATA_CORRECCIO, dataCorreccio);
			
			estadistiquesAlumne.add(liniaEstadistiquesAlumne);
		}
		
		return estadistiquesAlumne;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRespostesQuestionari(Alumne alumne) {
		String strQuery = "select qst "
						 +     ", rsq.codi "
				         +     ", rsq.nota "
				         +     ", rsq.tempsResposta "
				         +     ", rsq.dataContestacioFi "
				         +     ", rsq.dataCorreccio "
				         +     ", ("
				         + 		    "select avg(rq.nota) "
				         +            "from RespostaQuestionari rq "
				         +           "where rq.alumne.codi in ("
				         +                                      "select alu.codi "
				         +                                        "from Alumne alu "
				         +                                       "where alu.assignatura.codi = ("
				         +                                                                        "select alu2.assignatura.codi "
				         +                                                                          "from Alumne alu2 "
				         +                                                                         "where alu2.codi = rsq.alumne.codi"
				         +																	  ")"
				         +                                   ") "
				         +             "and rq.questionari.codi = rsq.questionari.codi"
				         +       ") "
				         +     ", (("
				         + 		    "select avg(rq.tempsResposta) "
				         +            "from RespostaQuestionari rq "
				         +           "where rq.alumne.codi in ("
				         +                                      "select alu.codi "
				         +                                        "from Alumne alu "
				         +                                       "where alu.assignatura.codi = ("
				         +                                                                        "select alu2.assignatura.codi "
				         +                                                                          "from Alumne alu2 "
				         +                                                                         "where alu2.codi = rsq.alumne.codi"
				         +																	  ")"
				         +                                   ") "
				         +             "and rq.questionari.codi = rsq.questionari.codi"
				         +       ")/1000) "
		                +  "from RespostaQuestionari rsq "
		                +		    "inner join rsq.questionari qst "
		                + "where rsq.contestat = :contestat "
		                +   "and rsq.corregit = :corregit "
		                +   "and rsq.anonim = :anonim "
		                +   "and rsq.alumne = :alumne "
		                + "order by rsq.dataCorreccio";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestat", Boolean.TRUE);
		q.setParameter("corregit", Boolean.TRUE);
		q.setParameter("anonim", Boolean.FALSE);
		q.setParameter("alumne", alumne);
		
		List<Object[]> llistaArrayObjectes = q.getResultList();
		
		List<Map<String, Object>> estadistiquesAlumne = new ArrayList<Map<String, Object>>(llistaArrayObjectes.size());
		for (Object[] arrayObjectes : llistaArrayObjectes) {
			Questionari questionari = (Questionari) arrayObjectes[0];
			Integer codiRq = (Integer) arrayObjectes[1];
			Float nota = parsejaFloat(arrayObjectes[2]);
			Float tempsResposta = (Float) (parsejaLong(arrayObjectes[3]).floatValue() / 1000);
			Date dataContestacioFi = (Date) arrayObjectes[4];
			Date dataCorreccio = (Date) arrayObjectes[5];
			Float notaMitjaAss = parsejaFloat(arrayObjectes[6]);
			Float tempsRespostaMigAss = parsejaFloat(arrayObjectes[7]);
			
			Map<String, Object> liniaEstadistiquesAlumne = new HashMap<String, Object>();
			liniaEstadistiquesAlumne.put(EstadistiquesDao.QUESTIONARI_CODI_RQ, codiRq);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.QUESTIONARI_CODI, questionari.getCodi());
			liniaEstadistiquesAlumne.put(EstadistiquesDao.QUESTIONARI_NOM, questionari.getNom());
			liniaEstadistiquesAlumne.put(EstadistiquesDao.QUESTIONARI_DESCRIPCIO, questionari.getDescripcio());
			liniaEstadistiquesAlumne.put(EstadistiquesDao.NOTA, nota);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA, notaMitjaAss);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.NOTA_MITJA_EGRADUS, questionari.getNotaMitja());
			liniaEstadistiquesAlumne.put(EstadistiquesDao.TEMPS_RESPOSTA, tempsResposta);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA, tempsRespostaMigAss);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS, (Float) (questionari.getTempsRespostaMig().floatValue() / 1000));
			liniaEstadistiquesAlumne.put(EstadistiquesDao.DATA_CONTESTACIO_FI, dataContestacioFi);
			liniaEstadistiquesAlumne.put(EstadistiquesDao.DATA_CORRECCIO, dataCorreccio);
			
			estadistiquesAlumne.add(liniaEstadistiquesAlumne);
		}
		
		return estadistiquesAlumne;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDetallRespostaQuestionari(int codiRespostaQuestionari) {
		String strQuery = "select pre "
				        +      ", prq.pes "
				        +      ", rsp.nota "
				        +	   ", ("
				        + 			"select avg(rsp2.nota) "
				        +			  "from RespostaPregunta rsp2 "
				        +						"inner join rsp2.respostaQuestionari rsq2 "
				        +			 "where rsq2.alumne.codi in (" // Alumnes de l'assignatura on està l'alumne de la resposta-questionari
				        +										   "select alu2.codi "
				        +											 "from Alumne alu2 "
				        +											"where alu2.assignatura.codi = ("
				        +																			  "select alu3.assignatura.codi "
				        +																				"from Alumne alu3 "
				        +																			   "where alu3.codi = alu.codi"
				        + 																		  ")"
				        + 									   ") "
				        +			   "and rsq2.questionari.codi = qst.codi "
				        +              "and rsp2.pregunta.codi = pre.codi"
				        +        ")"
				        +      ", ("
				        +			"select avg(rsp2.nota) "
				        +			  "from RespostaPregunta rsp2 "
				        +						"inner join rsp2.respostaQuestionari rsq2 "
				        +			 "where rsq2.questionari.codi = qst.codi "
				        +              "and rsp2.pregunta.codi = pre.codi"
				        + 		 ") "
				        +   "from RespostaPregunta rsp "
				        +			"inner join rsp.respostaQuestionari rsq "
				        +			"inner join rsq.alumne alu "
				        +			"inner join rsq.questionari qst "
				        +			"inner join rsp.pregunta pre "
				        +			"inner join qst.preguntes prq "
				        +  "where prq.pregunta.codi = pre.codi "
				        +    "and prq.questionari.codi = qst.codi "
				        +    "and rsq.codi = :codiRq";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("codiRq", codiRespostaQuestionari);
		
		List<Object[]> llistaArrayObjectes = q.getResultList();
		
		List<Map<String, Object>> estadistiquesDetallRq = new ArrayList<Map<String, Object>>(llistaArrayObjectes.size());
		for (Object[] arrayObjectes : llistaArrayObjectes) {
			Pregunta pregunta = (Pregunta) arrayObjectes[0];
			Float pes = parsejaFloat(arrayObjectes[1]);
			Float nota = parsejaFloat(arrayObjectes[2]);
			Float notaMitjaAss = parsejaFloat(arrayObjectes[3]);
			Float notaMitjaEgr = parsejaFloat(arrayObjectes[4]);
			
			Map<String, Object> liniaEstadistiquesDetallRq = new HashMap<String, Object>();
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_CODI, pregunta.getCodi());
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_ENUNCIAT, pregunta.getEnunciat());
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_TIPUS, pregunta.getTipus());
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_PES, pes);
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.NOTA, nota);
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA, notaMitjaAss);
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.NOTA_MITJA_EGRADUS, notaMitjaEgr);
			
			estadistiquesDetallRq.add(liniaEstadistiquesDetallRq);
		}
		
		return estadistiquesDetallRq;
	}
	
	public int getNumAlumnesAprovatsByPregunta(Pregunta pregunta) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 + "where rp.contestada = :contestada "
		                 +   "and rp.corregida = :corregida "
		                 +   "and rp.nota >= :nota "
		                 +   "and rp.pregunta = :pregunta";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestada", Boolean.TRUE);
		q.setParameter("corregida", Boolean.TRUE);
		q.setParameter("nota", 5f);
		q.setParameter("pregunta", pregunta);
		return q.getResultList().size();
	}

	public int getNumAlumnesCorregitsByPregunta(Pregunta pregunta) {
		String strQuery = "select rp "
		                 +  "from RespostaPregunta rp "
		                 + "where rp.contestada = :contestada "
		                 +   "and rp.corregida = :corregida "
		                 +   "and rp.pregunta = :pregunta";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestada", Boolean.TRUE);
		q.setParameter("corregida", Boolean.TRUE);
		q.setParameter("pregunta", pregunta);
		return q.getResultList().size();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getEstadistiquesProfessorPreguntesByAssignatura(int codiAssignatura) {
		String strQuery = "select pre "
						+      ", (" // nota mitja de tots els alumnes de l'assignatura actual
				        +  		    "select avg(rp.nota) "
				        +             "from RespostaPregunta rp "
				        +            "where rp.respostaQuestionari.codi is null "
				        +              "and rp.alumne.codi in ("
				        +                                       "select alu.codi "
				        +                                         "from Alumne alu "
				        +                                        "where alu.assignatura.codi = ass.codi"
				        +                                    ") "
				        +              "and rp.pregunta.codi = pre.codi"
				        +        ") "
				        +      ", (" // nota mitja de tots els alumnes, de qualsevol assignatura d'egradus
				        +  		    "select avg(rp.nota) "
				        +             "from RespostaPregunta rp "
				        +            "where rp.respostaQuestionari.codi is null "
				        +              "and rp.pregunta.codi = pre.codi"
				        +        ") "
				        +      ", ((" // temps de resposta mig de tots els alumnes de l'assignatura actual
				        +  		    "select avg(rp.tempsResposta) "
				        +             "from RespostaPregunta rp "
				        +            "where rp.respostaQuestionari.codi is null "
				        +              "and rp.alumne.codi in ("
				        +                                       "select alu.codi "
				        +                                         "from Alumne alu "
				        +                                        "where alu.assignatura.codi = ass.codi"
				        +                                    ") "
				        +              "and rp.pregunta.codi = pre.codi"
				        +        ")/1000) "
						+	"from RespostaPregunta rsp " 
						+           "inner join rsp.pregunta pre "
						+		    "inner join rsp.assignador.assignatura ass "
						+  "where rsp.contestada = :contestada "
						+    "and rsp.corregida = :corregida "
						+    "and rsp.respostaQuestionari.codi is null "
						+    "and ass.codi = :assignatura "
						+  "order by pre";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestada", Boolean.TRUE);
		q.setParameter("corregida", Boolean.TRUE);
		q.setParameter("assignatura", codiAssignatura);
		List<Object[]> llistaArrayObjectes = q.getResultList();
		
		List<Map<String, Object>> estadistiquesProfessor = new ArrayList<Map<String, Object>>(llistaArrayObjectes.size());
		for (Object[] arrayObjectes : llistaArrayObjectes) {
			Pregunta pregunta = (Pregunta) arrayObjectes[0];
			Float notaMitjaAss = parsejaFloat(arrayObjectes[1]);
			Float notaMitjaEgr = parsejaFloat(arrayObjectes[2]);
			Float tempsRespostaMigAss = parsejaFloat(arrayObjectes[3]);
//			Integer numContestacions = (Integer) ((Long) arrayObjectes[4]).intValue();
			// El temps de resposta mig de tot Egradus el tenim directament a pregunta.getTempsRespostaMig().
			// Això no passa amb la nota mitja de tot Egradus ja que quan contestam Questionaris, s'actualitza
			// la nota mitja de les preguntes que hi pertanyen, però no el temps de resposta mig.
			
			Map<String, Object> liniaEstadistiquesProfessor = new HashMap<String, Object>();
			liniaEstadistiquesProfessor.put(EstadistiquesDao.PREGUNTA_CODI, pregunta.getCodi());
			liniaEstadistiquesProfessor.put(EstadistiquesDao.PREGUNTA_ENUNCIAT, pregunta.getEnunciat());
			liniaEstadistiquesProfessor.put(EstadistiquesDao.PREGUNTA_TIPUS, pregunta.getTipus());
			liniaEstadistiquesProfessor.put(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA, notaMitjaAss);
			liniaEstadistiquesProfessor.put(EstadistiquesDao.NOTA_MITJA_EGRADUS, notaMitjaEgr);
			liniaEstadistiquesProfessor.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA, tempsRespostaMigAss);
			liniaEstadistiquesProfessor.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS, (Float) (pregunta.getTempsRespostaMig().floatValue() / 1000));
			
			estadistiquesProfessor.add(liniaEstadistiquesProfessor);
		}
		
		// Agrupam les estadístiques anteriors per Pregunta de manera explícita, (ja que JPQL no dóna
		// suport a realitzar funcions d'agrupació sobre subqueries). Per això ens ajudarem d'un mètode
		// privat 'agrupaEstadistiquesPer()'
		Set<String> keysPerAgrupar = new HashSet<String>();
		keysPerAgrupar.add(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA);
		keysPerAgrupar.add(EstadistiquesDao.NOTA_MITJA_EGRADUS);
		keysPerAgrupar.add(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA);
		keysPerAgrupar.add(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS);
		estadistiquesProfessor = agrupaEstadistiquesPer(estadistiquesProfessor, EstadistiquesDao.PREGUNTA_CODI, EstadistiquesDao.NUM_CONTESTACIONS, keysPerAgrupar);
		
		return estadistiquesProfessor;
	}
	
	public List<Map<String, Object>> getDetallEstadistiquesProfessorPreguntes(int codiAssignatura, int codiPregunta, int numContestacions, String alumneAnonim) {
		String strQuery = "select alu "
						+	   ", avg(rsp.nota) "
						+	   ", count(pre.codi) "
						+	"from RespostaPregunta rsp " 
						+		    "inner join rsp.pregunta pre "
						+			"inner join rsp.alumne alu "
						+		    "inner join rsp.assignador.assignatura ass "
						+  "where rsp.respostaQuestionari.codi is null "
						+    "and rsp.contestada = :contestada "
						+    "and rsp.corregida = :corregida "
						+    "and rsp.anonima = :anonima "
						+    "and ass.codi = :assignatura "
						+    "and pre.codi = :pregunta "
						+  "group by alu "
						+  "order by count(pre.codi) desc";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestada", Boolean.TRUE);
		q.setParameter("corregida", Boolean.TRUE);
		q.setParameter("anonima", Boolean.FALSE);
		q.setParameter("assignatura", codiAssignatura);
		q.setParameter("pregunta", codiPregunta);
		return llistaEstadistiquesDetall(q, numContestacions, alumneAnonim);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getEstadistiquesProfessorQuestionarisByAssignatura(int codiAssignatura) {
		String strQuery = "select qst "
						+      ", (" // nota mitja de tots els alumnes de l'assignatura actual
				        +  		    "select avg(rq.nota) "
				        +             "from RespostaQuestionari rq "
				        +            "where rq.alumne.codi in ("
				        +                                       "select alu.codi "
				        +                                         "from Alumne alu "
				        +                                        "where alu.assignatura.codi = ass.codi"
				        +                                    ") "
				        +              "and rq.questionari.codi = qst.codi"
				        +        ") "
				        +      ", ((" // temps de resposta mig de tots els alumnes de l'assignatura actual
				        +  		    "select avg(rq.tempsResposta) "
				        +             "from RespostaQuestionari rq "
				        +            "where rq.alumne.codi in ("
				        +                                       "select alu.codi "
				        +                                         "from Alumne alu "
				        +                                        "where alu.assignatura.codi = ass.codi"
				        +                                    ") "
				        +              "and rq.questionari.codi = qst.codi"
				        +        ")/1000) "
						+	"from RespostaQuestionari rsq " 
						+           "inner join rsq.questionari qst "
						+		    "inner join rsq.assignador.assignatura ass "
						+  "where rsq.contestat = :contestat "
						+    "and rsq.corregit = :corregit "
						+    "and ass.codi = :assignatura "
						+  "order by qst";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestat", Boolean.TRUE);
		q.setParameter("corregit", Boolean.TRUE);
		q.setParameter("assignatura", codiAssignatura);
		List<Object[]> llistaArrayObjectes = q.getResultList();
		
		List<Map<String, Object>> estadistiquesProfessor = new ArrayList<Map<String, Object>>(llistaArrayObjectes.size());
		for (Object[] arrayObjectes : llistaArrayObjectes) {
			Questionari questionari = (Questionari) arrayObjectes[0];
			Float notaMitjaAss = parsejaFloat(arrayObjectes[1]);
			Float tempsRespostaMigAss = parsejaFloat(arrayObjectes[2]);
			
			// El temps de resposta mig i la nota mitja del qüestionari a nivell de tot Egradus
			// el trobarem directament a questionari.getNotaMitja() i questionari.getTempsRespostaMig();
			
			Map<String, Object> liniaEstadistiquesProfessor = new HashMap<String, Object>();
			liniaEstadistiquesProfessor.put(EstadistiquesDao.QUESTIONARI_CODI, questionari.getCodi());
			liniaEstadistiquesProfessor.put(EstadistiquesDao.QUESTIONARI_NOM, questionari.getNom());
			liniaEstadistiquesProfessor.put(EstadistiquesDao.QUESTIONARI_DESCRIPCIO, questionari.getDescripcio());
			liniaEstadistiquesProfessor.put(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA, notaMitjaAss);
			liniaEstadistiquesProfessor.put(EstadistiquesDao.NOTA_MITJA_EGRADUS, questionari.getNotaMitja());
			liniaEstadistiquesProfessor.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA, tempsRespostaMigAss);
			liniaEstadistiquesProfessor.put(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS, (Float) (questionari.getTempsRespostaMig().floatValue() / 1000));
			
			estadistiquesProfessor.add(liniaEstadistiquesProfessor);
		}
		
		// Agrupam les estadístiques anteriors per Questionari de manera explícita, (ja que JPQL no dóna
		// suport a realitzar funcions d'agrupació sobre subqueries). Per això ens ajudarem d'un mètode
		// privat 'agrupaEstadistiquesPer()'
		Set<String> keysPerAgrupar = new HashSet<String>();
		keysPerAgrupar.add(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA);
		keysPerAgrupar.add(EstadistiquesDao.NOTA_MITJA_EGRADUS);
		keysPerAgrupar.add(EstadistiquesDao.TEMPS_RESPOSTA_MIG_ASSIGNATURA);
		keysPerAgrupar.add(EstadistiquesDao.TEMPS_RESPOSTA_MIG_EGRADUS);
		estadistiquesProfessor = agrupaEstadistiquesPer(estadistiquesProfessor, EstadistiquesDao.QUESTIONARI_CODI, EstadistiquesDao.NUM_CONTESTACIONS, keysPerAgrupar);
		
		return estadistiquesProfessor;
	}
	
	public List<Map<String, Object>> getDetallEstadistiquesProfessorQuestionaris(int codiAssignatura, int codiQuestionari, int numContestacions, String alumneAnonim) {
		String strQuery = "select alu "
						+	   ", avg(rsq.nota) "
						+	   ", count(qst.codi) "
						+	"from RespostaQuestionari rsq " 
						+		    "inner join rsq.questionari qst "
						+			"inner join rsq.alumne alu "
						+			"inner join rsq.assignador.assignatura ass "
						+  "where rsq.contestat = :contestat "
						+    "and rsq.corregit = :corregit "
						+    "and rsq.anonim = :anonim "
						+    "and ass.codi = :assignatura "
						+    "and qst.codi = :questionari "
						+  "group by alu "
						+  "order by count(qst.codi) desc";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("contestat", Boolean.TRUE);
		q.setParameter("corregit", Boolean.TRUE);
		q.setParameter("anonim", Boolean.FALSE);
		q.setParameter("assignatura", codiAssignatura);
		q.setParameter("questionari", codiQuestionari);
		return llistaEstadistiquesDetall(q, numContestacions, alumneAnonim);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDetallQuestionari(int codiQuestionari) {
		String strQuery = "select pre "
				        +      ", prq.pes "
				        +	   ", ("
				        + 			"select avg(rsp2.nota) "
				        +			  "from RespostaPregunta rsp2 "
				        +						"inner join rsp2.respostaQuestionari rsq2 "
				        +			 "where rsq2.alumne.codi in (" // Alumnes de l'assignatura on està l'alumne de la resposta-questionari
				        +										   "select alu2.codi "
				        +											 "from Alumne alu2 "
				        +											"where alu2.assignatura.codi = ("
				        +																			  "select alu3.assignatura.codi "
				        +																				"from Alumne alu3 "
				        +																			   "where alu3.codi = alu.codi"
				        + 																		  ")"
				        + 									   ") "
				        +			   "and rsq2.questionari.codi = qst.codi "
				        +              "and rsp2.pregunta.codi = pre.codi"
				        +        ")"
				        +      ", ("
				        +			"select avg(rsp2.nota) "
				        +			  "from RespostaPregunta rsp2 "
				        +						"inner join rsp2.respostaQuestionari rsq2 "
				        +			 "where rsq2.questionari.codi = qst.codi "
				        +              "and rsp2.pregunta.codi = pre.codi"
				        + 		 ") "
				        +   "from RespostaPregunta rsp "
				        +			"inner join rsp.respostaQuestionari rsq "
				        +			"inner join rsq.alumne alu "
				        +			"inner join rsq.questionari qst "
				        +			"inner join rsp.pregunta pre "
				        +			"inner join qst.preguntes prq "
				        +  "where prq.pregunta.codi = pre.codi "
				        +    "and prq.questionari.codi = qst.codi "
				        +    "and qst.codi = :codiQuestionari "
				        +  "order by pre";
		
		Query q = em.createQuery(strQuery);
		q.setParameter("codiQuestionari", codiQuestionari);
		
		List<Object[]> llistaArrayObjectes = q.getResultList();
		
		List<Map<String, Object>> estadistiquesDetallRq = new ArrayList<Map<String, Object>>(llistaArrayObjectes.size());
		for (Object[] arrayObjectes : llistaArrayObjectes) {
			Pregunta pregunta = (Pregunta) arrayObjectes[0];
			Float pes = parsejaFloat(arrayObjectes[1]);
			Float notaMitjaAss = parsejaFloat(arrayObjectes[2]);
			Float notaMitjaEgr = parsejaFloat(arrayObjectes[3]);
			
			Map<String, Object> liniaEstadistiquesDetallRq = new HashMap<String, Object>();
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_CODI, pregunta.getCodi());
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_ENUNCIAT, pregunta.getEnunciat());
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_TIPUS, pregunta.getTipus());
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.PREGUNTA_PES, pes);
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA, notaMitjaAss);
			liniaEstadistiquesDetallRq.put(EstadistiquesDao.NOTA_MITJA_EGRADUS, notaMitjaEgr);
			
			estadistiquesDetallRq.add(liniaEstadistiquesDetallRq);
		}
		
		// Agrupam les estadístiques anteriors per Pregunta de manera explícita, (ja que JPQL no dóna
		// suport a realitzar funcions d'agrupació sobre subqueries). Per això ens ajudarem d'un mètode
		// privat 'agrupaEstadistiquesPer()'
		Set<String> keysPerAgrupar = new HashSet<String>();
		keysPerAgrupar.add(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA);
		keysPerAgrupar.add(EstadistiquesDao.NOTA_MITJA_EGRADUS);
		estadistiquesDetallRq = agrupaEstadistiquesPer(estadistiquesDetallRq, EstadistiquesDao.PREGUNTA_CODI, null, keysPerAgrupar);
		
		return estadistiquesDetallRq;
	}
	
	/**
	 * Processa la query passada per paràmetre i defineix
	 * un llistat de Maps <String, <Object> amb les estadístiques
	 * detall del conjunt d'alumnes, indicant:
	 * <ul>
	 * <li>alumne (alies, nom, primer llinatge i segon llinatge)</li>
	 * <li>nota mitja a l'assignatura en aquella pregunta o qüestionari (depenent del cas)</li>
	 * <li>nota mitja de tot Egradus en aquella pregunta o qüestionari (depenent del cas)</li>
	 * <li>número de contestacions en aquella pregunta o qüestionari (depenent del cas)</li>
	 * </ul>
	 * 
	 * Finalment, retorna la llista d'aquests Maps.
	 * 
	 * Aquest mètode s'emplea tant per a les estadístiques detall de les preguntes
	 * com les estadístiques detall dels qüestionaris
	 * 
	 * @param q
	 * 			Query
	 * @param numTotalContestacions
	 * 			número total de contestacions de la pregunta.
	 * 			Si el núm de files extretes en la query no arriba a 
	 * 			aquest total, significarà que la resta són anònimes
	 * 			i les haurem d'afegir manualment.
	 * @param alumneAnonim
	 * 			nom de l'alumne que es visualitzarà, pensat per a quan
	 * 			l'alumne sigui anònim
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> llistaEstadistiquesDetall(Query q, int numTotalContestacions, String alumneAnonim) {
		List<Object[]> llistaArrayObjectes = q.getResultList();
		
		/**
		 * Comptador on tendrem el núm total de contestacions del detall.
		 * L'emplearem per averiguar quantes contestacions anònimes hi ha hagut d'aquella
		 * pregunta o qüestionari.
		 * 
		 * (serà el num total de contestacions d'aquella pregunta o qüestionari menos
		 * aquest acumulador).
		 */
		int numCntDetallAcumulat = 0;
		
		List<Map<String, Object>> llistaDetall = new ArrayList<Map<String, Object>>(numTotalContestacions);
		for (Object[] arrayObjectes : llistaArrayObjectes) {
			Alumne alumne = (Alumne) arrayObjectes[0];
			Float notaMitjaAss = parsejaFloat(arrayObjectes[1]);
			Integer numContestacions = (Integer) ((Long) arrayObjectes[2]).intValue();
			
			Map<String, Object> detall = new HashMap<String, Object>();
			detall.put(EstadistiquesDao.ALUMNE_ALIES, alumne.getPersona().getAlies());
			detall.put(EstadistiquesDao.ALUMNE_NOM, alumne.getPersona().getNom());
			detall.put(EstadistiquesDao.ALUMNE_PRIMER_LLINATGE, alumne.getPersona().getPrimerLlinatge());
			detall.put(EstadistiquesDao.ALUMNE_SEGON_LLINATGE, alumne.getPersona().getSegonLlinatge());
			detall.put(EstadistiquesDao.NOTA_MITJA_ASSIGNATURA, notaMitjaAss);
			detall.put(EstadistiquesDao.NUM_CONTESTACIONS, numContestacions);
			
			llistaDetall.add(detall);
			
			// Afegim el número de contestacions del detall actual
			numCntDetallAcumulat += numContestacions.intValue();
		}
		
		// afegir les contestacions anònimes
		if (numTotalContestacions > numCntDetallAcumulat) {
			Map<String, Object> detall = new HashMap<String, Object>();
			// no incluim ni el nom, ni llinatges de l'alumne ni la seva nota mitja
			detall.put(EstadistiquesDao.ALUMNE_ALIES, alumneAnonim);
			detall.put(EstadistiquesDao.NUM_CONTESTACIONS, numTotalContestacions - numCntDetallAcumulat);
			llistaDetall.add(detall);
		}
		
		return llistaDetall;
	}
	
	private Float parsejaFloat(Object objecte) {
		Float f = 0f;
		if (objecte instanceof Double) 	f = ((Double) objecte).floatValue();
		if (objecte instanceof Float) 	f = (Float) objecte;
		return f;
	}
	
	private Long parsejaLong(Object objecte) {
		Long l = 0L;
		if (objecte instanceof Long) 	l = (Long) objecte;
		return l;
	}
	
	/**
	 * Agrupa les estadístiques realitzant
	 * mitjes aritmètiques (AVG)
	 * 
	 * PRECONDICIÓ: És necessari que la llista estigui
	 * ordenada per 'keyAgrupadora'.
	 * 
	 * @param llistaEstadistiques
	 * 			List de Maps a agrupar
	 * @param keyAgrupadora
	 * 			key per la qual s'aplicarà el Group By (típicament el codi de pregunta, o de qüestionari)
	 * @param keyTotalAgrupacions
	 * 			key que s'afegirà a cada Map resultant indicant la quantitat de Maps agrupats (COUNT)
	 * @param keysAgrupades
	 * 			conjunt de keys a agrupar (AVG)
	 * @return
	 */
	private List<Map<String, Object>> agrupaEstadistiquesPer(List<Map<String, Object>> llistaEstadistiques, String keyAgrupadora, String keyTotalAgrupacions, Set<String> keysAgrupades) {
		List<Map<String, Object>> llistaEstadistiquesAgrupat = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapAux = new HashMap<String, Object>();
		
		// index acumulador (per saber quants Maps iguals hi ha hagut, és a dir, per saber entre quant dividir)
		int n = 1;
		
		for (Map<String, Object> map : llistaEstadistiques) {
			if (mapAux.isEmpty()) mapAux = new HashMap<String, Object>(map);
			else {
				// comparam els maps, per veure si els hem de juntar.
				if ((map.get(keyAgrupadora)).equals(mapAux.get(keyAgrupadora))) {
					// Hem d'agrupar map amb mapAux i deixar el resultat a mapAux
					for (String clau : keysAgrupades) mapAux.put(clau, ((Float) mapAux.get(clau)) + ((Float) map.get(clau)));
					n++;
				} else {
					// Dividim entre el total d'instàncies i enviam a la llista d'agrupats
					for (String clau : keysAgrupades) mapAux.replace(clau, ((Float) mapAux.get(clau)) / n);
					
					// Afegim el num total d'agrupacions trobades
					if (keyTotalAgrupacions != null) mapAux.put(keyTotalAgrupacions, new Integer(n));
					llistaEstadistiquesAgrupat.add(mapAux);
					
					// Actualitzam el nou mapAux i l'index acumulador
					mapAux = new HashMap<String, Object>(map);
					n = 1;
				}
			}
		}
		
		// inserim el darrer mapAux del procés
		if (!mapAux.isEmpty()) {
			for (String clau : keysAgrupades) mapAux.replace(clau, ((Float) mapAux.get(clau)) / n);
			if (keyTotalAgrupacions != null) mapAux.put(keyTotalAgrupacions, new Integer(n));
			llistaEstadistiquesAgrupat.add(mapAux);
		}
		
		return llistaEstadistiquesAgrupat;
	}

}
