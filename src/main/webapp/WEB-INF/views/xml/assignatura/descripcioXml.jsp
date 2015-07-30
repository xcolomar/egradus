<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<assignatures>
	<assignatura codi="${assignatura.codi}" codiReferencia="${assignatura.codiReferencia}" nom="${assignatura.nom}" anyAcademic="${assignatura.anyAcademic}" dataAlta="${assignatura.dataAlta}" clauProfessor="${assignatura.clauProfessor}" clauAlumne="${assignatura.clauAlumne}">
		<descripcio>${assignatura.descripcio}</descripcio>
		<c:if test="${not empty  errorAssignatura}"><errorAssignatura>${errorAssignatura}</errorAssignatura></c:if>
		<c:if test="${not empty  errorUnio}"><errorUnio>${errorUnio}</errorUnio></c:if>
		<c:if test="${not empty  esAlumne}"><esAlumne codi="${esAlumne}"/></c:if>
		<creador nom="${assignatura.creador.nom}" llinatge1="${assignatura.creador.primerLlinatge}" llinatge2="${assignatura.creador.segonLlinatge}" alies="${assignatura.creador.alies}"/>
		<professors>
			<c:forEach items="${assignatura.professors}" var="pro">
				<professor nom="${pro.persona.nom}" llinatge1="${pro.persona.primerLlinatge}" llinatge2="${pro.persona.segonLlinatge}" alies="${pro.persona.alies}"/>
			</c:forEach>
		</professors>
	</assignatura>
</assignatures>