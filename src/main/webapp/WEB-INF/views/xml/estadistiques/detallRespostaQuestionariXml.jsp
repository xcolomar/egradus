<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<detallEstadistiquesRespostaQuestionari>
	<c:forEach items="${estadistiques}" var="est">
		<detallRespostaQuestionari>
			<pregunta 	codi="${est.preguntaCodi}" 
						enunciat="${est.preguntaEnunciat}" 
						tipus="${est.preguntaTipus}"
						pes="${est.preguntaPes}"/>
			<nota 	nota="${est.nota}" 
					mitjaAssignatura="${est.notaMitjaAssignatura}" 
					mitjaEgradus="${est.notaMitjaEgradus}"/>
		</detallRespostaQuestionari>
	</c:forEach>
</detallEstadistiquesRespostaQuestionari>