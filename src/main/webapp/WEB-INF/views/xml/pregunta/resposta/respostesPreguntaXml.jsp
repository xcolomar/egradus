<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<respostesPregunta>
	<c:forEach items="${respostesPregunta}" var="rsp">
		<respostaPregunta 	codi="${rsp.codi}" 
							contestada="${rsp.contestada}" 
							corregida="${rsp.corregida}"
							revisada="${rsp.revisada}"  
							nota="${rsp.nota}" 
							dataAlta="${rsp.dataAlta}" 
							dataContestacioInici="${rsp.dataContestacioInici}" 
							dataContestacioFi="${rsp.dataContestacioFi}" 
							dataCorreccio="${rsp.dataCorreccio}"
							dataRevisio="${rsp.dataRevisio}"
							anonima="${rsp.anonima}">
			<pregunta 	codi="${rsp.pregunta.codi}" 
						enunciat="${rsp.pregunta.enunciat}" 
						tipus="${rsp.pregunta.tipus}" 
						dataAlta="${rsp.pregunta.dataAlta}" 
						raonarResposta="${rsp.pregunta.raonarResposta}" 
						dificultatTeorica="${rsp.pregunta.dificultatTeorica}" 
						dificultatPractica="${rsp.pregunta.dificultatPractica}"
						numTotalActualitzacionsNotaMitja="${rsp.pregunta.numTotalActualitzacionsNotaMitja}"
						numTotalActualitzacionsTRM="${rsp.pregunta.numTotalActualitzacionsTRM}"
						tempsRespostaMig="${rsp.pregunta.tempsRespostaMig}"
						notaMitja="${rsp.pregunta.notaMitja}">
				<creador nom="${rsp.pregunta.creador.persona.nom}" llinatge1="${rsp.pregunta.creador.persona.primerLlinatge}" llinatge2="${rsp.pregunta.creador.persona.segonLlinatge}" alies="${rsp.pregunta.creador.persona.alies}"/>
			</pregunta>
			<assignador nom="${rsp.assignador.persona.nom}" llinatge1="${rsp.assignador.persona.primerLlinatge}" llinatge2="${rsp.assignador.persona.segonLlinatge}" alies="${rsp.assignador.persona.alies}"/>
			<corrector nom="${rsp.corrector.persona.nom}" llinatge1="${rsp.corrector.persona.primerLlinatge}" llinatge2="${rsp.corrector.persona.segonLlinatge}" alies="${rsp.corrector.persona.alies}"/>
			<c:if test="${rsp.anonima == false}">
				<alumne nom="${rsp.alumne.persona.nom}" llinatge1="${rsp.alumne.persona.primerLlinatge}" llinatge2="${rsp.alumne.persona.segonLlinatge}" alies="${rsp.alumne.persona.alies}"/>
			</c:if>
		</respostaPregunta>
	</c:forEach>
</respostesPregunta>