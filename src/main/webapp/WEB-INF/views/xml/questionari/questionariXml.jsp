<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<questionaris>
	<questionari codi="${questionari.codi}" nom="${questionari.nom}" dataAlta="${questionari.dataAlta}" descripcio="${questionari.descripcio}" estat="${questionari.estat}" dificultatTeorica="${questionari.dificultatTeorica}" dificultatPractica="${questionari.dificultatPractica}">
		<c:if test="${not empty  errorQuestionari}"><errorQuestionari>${errorQuestionari}</errorQuestionari></c:if>
		<c:if test="${not empty  numAlumnes}"><numAlumnes>${numAlumnes}</numAlumnes></c:if>
		<creador nom="${questionari.creador.persona.nom}" llinatge1="${questionari.creador.persona.primerLlinatge}" llinatge2="${questionari.creador.persona.segonLlinatge}" alies="${questionari.creador.persona.alies}"/>
		<preguntes>
			<c:forEach items="${questionari.preguntes}" var="pregs">
				<pregunta codi="${pregs.pregunta.codi}" enunciat="${pregs.pregunta.enunciat}" estat="${pregs.pregunta.estat}" tipus="${pregs.pregunta.tipus}" dataAlta="${pregs.pregunta.dataAlta}" raonarResposta="${pregs.pregunta.raonarResposta}" dificultatTeorica="${pregs.pregunta.dificultatTeorica}" dificultatPractica="${pregs.pregunta.dificultatPractica}" pes="${pregs.pes}">
					<creador nom="${pregs.pregunta.creador.persona.nom}" llinatge1="${pregs.pregunta.creador.persona.primerLlinatge}" llinatge2="${pregs.pregunta.creador.persona.segonLlinatge}" alies="${pregs.pregunta.creador.persona.alies}"/>
					<opcions>
						<c:forEach items="${pregs.pregunta.opcions}" var="opc">
							<c:if test="${opc.correcta}">
								<opcio codi="${opc.codi}" text="${opc.text}" correcta="S"/>
							</c:if>
							<c:if test="${not opc.correcta}">
								<opcio codi="${opc.codi}" text="${opc.text}" correcta="N"/>
							</c:if>
						</c:forEach>
					</opcions>
				</pregunta>
			</c:forEach>
		</preguntes>
	</questionari>
</questionaris>