<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<preguntes>
	<pregunta 	codi="${pregunta.codi}" 
				tipus="${pregunta.tipus}" 
				estat="${pregunta.estat}" 
				enunciat="${pregunta.enunciat}" 
				dataAlta="${pregunta.dataAlta}" 
				raonarResposta="${pregunta.raonarResposta}" 
				dificultatTeorica="${pregunta.dificultatTeorica}" 
				dificultatPractica="${pregunta.dificultatPractica}">
		<c:if test="${not empty  errorPregunta}"><errorPregunta>${errorPregunta}</errorPregunta></c:if>
		<c:if test="${not empty  numAlumnes}"><numAlumnes>${numAlumnes}</numAlumnes></c:if>
		<creador 	nom="${pregunta.creador.persona.nom}" 
					llinatge1="${pregunta.creador.persona.primerLlinatge}" 
					llinatge2="${pregunta.creador.persona.segonLlinatge}" 
					alies="${pregunta.creador.persona.alies}"/>
		<opcions>
			<c:forEach items="${pregunta.opcions}" var="opc">
				<c:if test="${opc.correcta}">
					<opcio codi="${opc.codi}" text="${opc.text}" correcta="S"/>
				</c:if>
				<c:if test="${not opc.correcta}">
					<opcio codi="${opc.codi}" text="${opc.text}" correcta="N"/>
				</c:if>
			</c:forEach>
		</opcions>
	</pregunta>
</preguntes>