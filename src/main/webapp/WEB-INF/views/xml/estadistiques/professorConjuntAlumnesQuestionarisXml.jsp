<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<estadistiquesConjuntAlumnesQuestionaris>
	<c:forEach items="${estadistiques}" var="est">
		<estadistiquesQuestionari>
			<questionari 	codi="${est.questionariCodi}" 
							nom="${est.questionariNom}" 
							descripcio="${est.questionariDescripcio}"
							numContestacions="${est.numContestacions}"/>
			<nota	mitjaAssignatura="${est.notaMitjaAssignatura}"
					mitjaEgradus="${est.notaMitjaEgradus}"/>
			<tempsResposta	mitjaAssignatura="${est.tempsRespostaMigAssignatura}"
							mitjaEgradus="${est.tempsRespostaMigEgradus}"/>
			<llistaDetall>
				<c:forEach items="${est.detall}" var="det">
					<detall>
						<alumne 	nom="${det.alumneNom}" 
									primerLlinatge="${det.alumnePrimerLlinatge}" 
									segonLlinatge="${det.alumneSegonLlinatge}" 
									alies="${det.alumneAlies}"
									numContestacions="${det.numContestacions}"/>
						<nota	mitjaAssignatura="${det.notaMitjaAssignatura}"/>
					</detall>
				</c:forEach>
			</llistaDetall>
		</estadistiquesQuestionari>
	</c:forEach>
</estadistiquesConjuntAlumnesQuestionaris>