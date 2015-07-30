<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<respostesQuestionari>
	<c:forEach items="${respostesQuestionari}" var="rsp">
		<respostaQuestionari 	codi="${rsp.codi}" 
								contestat="${rsp.contestat}" 
								corregit="${rsp.corregit}" 
								revisat="${rsp.revisat}" 
								nota="${rsp.nota}" 
								dataAlta="${rsp.dataAlta}" 
								dataContestacioInici="${rsp.dataContestacioInici}" 
								dataContestacioFi="${rsp.dataContestacioFi}" 
								dataCorreccio="${rsp.dataCorreccio}"
								dataRevisio="${rsp.dataRevisio}"
								anonim="${rsp.anonim}">
			<questionari 	codi="${rsp.questionari.codi}" 
							nom="${rsp.questionari.nom}" 
							descripcio="${rsp.questionari.descripcio}" 
							dataAlta="${rsp.questionari.dataAlta}" 
							dificultatTeorica="${rsp.questionari.dificultatTeorica}" 
							dificultatPractica="${rsp.questionari.dificultatPractica}"
							numTotalActualitzacionsNotaMitja="${rsp.questionari.numTotalActualitzacionsNotaMitja}"
							numTotalActualitzacionsTRM="${rsp.questionari.numTotalActualitzacionsTRM}"
							tempsRespostaMig="${rsp.questionari.tempsRespostaMig}"
							notaMitja="${rsp.questionari.notaMitja}">
				<creador nom="${rsp.questionari.creador.persona.nom}" llinatge1="${rsp.questionari.creador.persona.primerLlinatge}" llinatge2="${rsp.questionari.creador.persona.segonLlinatge}" alies="${rsp.questionari.creador.persona.alies}"/>
				<c:if test="${not empty rsp.questionari.preguntes}">
					<preguntes>
						<c:forEach items="${rsp.questionari.preguntes}" var="pre">
							<pregunta codi="${pre.pregunta.codi}" 
							          enunciat="${pre.pregunta.enunciat}" 
							          tipus="${pre.pregunta.tipus}" 
							          raonarResposta="${pre.pregunta.raonarResposta}" 
							          pes="${pre.pes}"/>
						</c:forEach>
					</preguntes>
				</c:if>
			</questionari>
			<assignador nom="${rsp.assignador.persona.nom}" llinatge1="${rsp.assignador.persona.primerLlinatge}" llinatge2="${rsp.assignador.persona.segonLlinatge}" alies="${rsp.assignador.persona.alies}"/>
			<corrector nom="${rsp.corrector.persona.nom}" llinatge1="${rsp.corrector.persona.primerLlinatge}" llinatge2="${rsp.corrector.persona.segonLlinatge}" alies="${rsp.corrector.persona.alies}"/>
			<c:if test="${rsp.anonim == false}">
				<alumne nom="${rsp.alumne.persona.nom}" llinatge1="${rsp.alumne.persona.primerLlinatge}" llinatge2="${rsp.alumne.persona.segonLlinatge}" alies="${rsp.alumne.persona.alies}"/>
			</c:if>
		</respostaQuestionari>
	</c:forEach>
</respostesQuestionari>