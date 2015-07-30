<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<preguntes>
	<c:if test="${not empty  errorPregunta}"><errorPregunta>${errorPregunta}</errorPregunta></c:if>
	<c:if test="${not empty  numAlumnes}"><numAlumnes>${numAlumnes}</numAlumnes></c:if>
	<c:forEach items="${preguntes}" var="pre">
		<pregunta 	codi="${pre.codi}" 
					enunciat="${pre.enunciat}" 
					estat="${pre.estat}" 
					tipus="${pre.tipus}" 
					dataAlta="${pre.dataAlta}" 
					raonarResposta="${pre.raonarResposta}" 
					dificultatTeorica="${pre.dificultatTeorica}" 
					dificultatPractica="${pre.dificultatPractica}">
			<creador 	nom="${pre.creador.persona.nom}" 
						llinatge1="${pre.creador.persona.primerLlinatge}" 
						llinatge2="${pre.creador.persona.segonLlinatge}" 
						alies="${pre.creador.persona.alies}"/>
		</pregunta>
	</c:forEach>
</preguntes>