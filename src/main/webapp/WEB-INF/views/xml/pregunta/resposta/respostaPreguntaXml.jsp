<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<respostesPregunta>
	<respostaPregunta 	codi="${rsp.codi}" 
						contestada="${rsp.contestada}"
						corregida="${rsp.corregida}"
						revisada="${rsp.revisada}"
						nota="${rsp.nota}"
						notaAntiga="${rsp.notaAntiga}"
						dataAlta="${rsp.dataAlta}"
						dataContestacioInici="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${rsp.dataContestacioInici}"/>"
						dataContestacioFi="${rsp.dataContestacioFi}"
						dataCorreccio="${rsp.dataCorreccio}" 
						dataRevisio="${rsp.dataRevisio}" 
						textResposta="${rsp.textResposta}" 
						textRaonarResposta="${rsp.textRaonarResposta}" 
						textCorreccio="${rsp.textCorreccio}"
						textRevisio="${rsp.textRevisio}"
						anonima="${rsp.anonima}">
		<pregunta 	codi="${rsp.pregunta.codi}" 
					enunciat="${rsp.pregunta.enunciat}" 
					tipus="${rsp.pregunta.tipus}" 
					dataAlta="${rsp.pregunta.dataAlta}" 
					raonarResposta="${rsp.pregunta.raonarResposta}" 
					dificultatTeorica="${rsp.pregunta.dificultatTeorica}" 
					dificultatPractica="${rsp.pregunta.dificultatPractica}">
			<creador nom="${rsp.pregunta.creador.persona.nom}" llinatge1="${rsp.pregunta.creador.persona.primerLlinatge}" llinatge2="${rsp.pregunta.creador.persona.segonLlinatge}" alies="${rsp.pregunta.creador.persona.alies}"/>
			<c:if test="${not empty rsp.pregunta.opcions}">
				<opcions>
					<c:forEach items="${rsp.pregunta.opcions}" var="opc">
						<opcio codiOpcio="${opc.codi}" text="${opc.text}" correcta="${opc.correcta}"/>
					</c:forEach>
				</opcions>
			</c:if>
		</pregunta>
		<assignador nom="${rsp.assignador.persona.nom}" llinatge1="${rsp.assignador.persona.primerLlinatge}" llinatge2="${rsp.assignador.persona.segonLlinatge}" alies="${rsp.assignador.persona.alies}" />
		<corrector nom="${rsp.corrector.persona.nom}" llinatge1="${rsp.corrector.persona.primerLlinatge}" llinatge2="${rsp.corrector.persona.segonLlinatge}" alies="${rsp.corrector.persona.alies}"/>
		<c:if test="${rsp.anonima == false}">
			<alumne nom="${rsp.alumne.persona.nom}" llinatge1="${rsp.alumne.persona.primerLlinatge}" llinatge2="${rsp.alumne.persona.segonLlinatge}" alies="${rsp.alumne.persona.alies}"/>
		</c:if>
		<c:if test="${not empty rsp.opcionsMarcades}">
			<opcionsMarcades>
				<c:forEach items="${rsp.opcionsMarcades}" var="opm">
					<opcioMarcada codiOpcio="${opm.opcio.codi}" text="${opm.opcio.text}" correcta="${opm.opcio.correcta}"/>
				</c:forEach>
			</opcionsMarcades>
		</c:if>
		<c:if test="${not empty errorCorreccio}">
			<errorCorreccio>${errorCorreccio}</errorCorreccio>
		</c:if>
	</respostaPregunta>
</respostesPregunta>