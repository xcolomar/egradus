<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<estadistiquesAlumnesQuestionaris>
	<c:forEach items="${estadistiques}" var="est">
		<estadistiquesQuestionari>
			<questionari 	codiRq="${est.questionariCodiRq}" 
							codi="${est.questionariCodi}" 
							nom="${est.questionariNom}" 
							descripcio="${est.questionariDescripcio}"/>
			<nota 	nota="${est.nota}" 
					mitjaAssignatura="${est.notaMitjaAssignatura}" 
					mitjaEgradus="${est.notaMitjaEgradus}"/>
			<tempsResposta 	tempsResposta="${est.tempsResposta}" 
							mitjaAssignatura="${est.tempsRespostaMigAssignatura}" 
							mitjaEgradus="${est.tempsRespostaMigEgradus}"/>
			<dates 	dataContestacioFi="${est.dataContestacioFi}"
					dataCorreccio="${est.dataCorreccio}"/>
		</estadistiquesQuestionari>
	</c:forEach>
</estadistiquesAlumnesQuestionaris>