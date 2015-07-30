<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<detallEstadistiquesQuestionari>
	<c:forEach items="${estadistiques}" var="est">
		<detallQuestionari>
			<pregunta 	codi="${est.preguntaCodi}" 
						enunciat="${est.preguntaEnunciat}" 
						tipus="${est.preguntaTipus}"
						pes="${est.preguntaPes}"/>
			<nota 	mitjaAssignatura="${est.notaMitjaAssignatura}" 
					mitjaEgradus="${est.notaMitjaEgradus}"/>
		</detallQuestionari>
	</c:forEach>
</detallEstadistiquesQuestionari>