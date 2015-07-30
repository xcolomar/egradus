<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<questionaris>
	<c:if test="${not empty  errorQuestionari}"><errorQuestionari>${errorQuestionari}</errorQuestionari></c:if>
	<c:if test="${not empty  numAlumnes}"><numAlumnes>${numAlumnes}</numAlumnes></c:if>
	<c:forEach items="${questionaris}" var="qst">
		<questionari codi="${qst.codi}" nom="${qst.nom}" descripcio="${qst.descripcio}" dataAlta="${qst.dataAlta}" estat="${qst.estat}" dificultatTeorica="${qst.dificultatTeorica}" dificultatPractica="${qst.dificultatPractica}">
			<creador nom="${qst.creador.persona.nom}" llinatge1="${qst.creador.persona.primerLlinatge}" llinatge2="${qst.creador.persona.segonLlinatge}" alies="${qst.creador.persona.alies}"/>
		</questionari>
	</c:forEach>
</questionaris>