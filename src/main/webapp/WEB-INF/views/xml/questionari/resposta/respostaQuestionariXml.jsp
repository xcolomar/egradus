<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<respostesQuestionari>
	<respostaQuestionari 	codi="${rsp.codi}"
							contestat="${rsp.contestat}"
							corregit="${rsp.corregit}"
							revisat="${rsp.revisat}"
							nota="${rsp.nota}"
							notaAntiga="${rsp.notaAntiga}"
							dataAlta="${rsp.dataAlta}"
							dataContestacioInici="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${rsp.dataContestacioInici}"/>"
							dataContestacioFi="${rsp.dataContestacioFi}"
							dataCorreccio="${rsp.dataCorreccio}"
							dataRevisio="${rsp.dataRevisio}"
							anonim="${rsp.anonim}">
		<questionari 	codi="${rsp.questionari.codi}" 
						nom="${rsp.questionari.nom}" 
						descripcio="${rsp.questionari.descripcio}" 
						dataAlta="${rsp.questionari.dataAlta}" 
						dificultatTeorica="${rsp.questionari.dificultatTeorica}" 
						dificultatPractica="${rsp.questionari.dificultatPractica}">
			<creador nom="${rsp.questionari.creador.persona.nom}" 
			         llinatge1="${rsp.questionari.creador.persona.primerLlinatge}" 
			         llinatge2="${rsp.questionari.creador.persona.segonLlinatge}" 
			         alies="${rsp.questionari.creador.persona.alies}"/>
			<c:if test="${not empty numRec}">
				<numRec>${numRec}</numRec>
			</c:if>
			<c:if test="${not empty numRr}">
				<numRr>${numRr}</numRr>
			</c:if>
			<c:if test="${not empty rsp.questionari.preguntes}">
				<preguntes>
					<c:forEach items="${rsp.questionari.preguntes}" var="pre">
						<pregunta codi="${pre.pregunta.codi}" 
						          enunciat="${pre.pregunta.enunciat}" 
						          tipus="${pre.pregunta.tipus}" 
						          raonarResposta="${pre.pregunta.raonarResposta}" 
						          pes="${pre.pes}">
							<c:if test="${not empty pre.pregunta.opcions}">
								<opcions>
									<c:forEach items="${pre.pregunta.opcions}" var="opc">
										<opcio codi="${opc.codi}" text="${opc.text}" correcta="${opc.correcta}"/>
									</c:forEach>
								</opcions>
							</c:if>
						</pregunta>
					</c:forEach>
				</preguntes>
			</c:if>
		</questionari>
		<assignador nom="${rsp.assignador.persona.nom}" llinatge1="${rsp.assignador.persona.primerLlinatge}" llinatge2="${rsp.assignador.persona.segonLlinatge}" alies="${rsp.assignador.persona.alies}"/>
		<corrector nom="${rsp.corrector.persona.nom}" llinatge1="${rsp.corrector.persona.primerLlinatge}" llinatge2="${rsp.corrector.persona.segonLlinatge}" alies="${rsp.corrector.persona.alies}"/>
		<c:if test="${rsp.anonim == false}">
			<alumne nom="${rsp.alumne.persona.nom}" llinatge1="${rsp.alumne.persona.primerLlinatge}" llinatge2="${rsp.alumne.persona.segonLlinatge}" alies="${rsp.alumne.persona.alies}"/>
		</c:if>
		<c:if test="${not empty errorCorreccio}">
			<errorCorreccio>${errorCorreccio}</errorCorreccio>
		</c:if>
		<c:if test="${not empty rsp.respostaPreguntes}">
			<respostaPreguntes>
				<c:forEach items="${rsp.respostaPreguntes}" var="rspre">
					<respostaPregunta codi="${rspre.codi}" 
					                  contestada="${rspre.contestada}" 
					                  corregida="${rspre.corregida}" 
					                  revisada="${rspre.revisada}"
					                  nota="${rspre.nota}" 
					                  notaAntiga="${rspre.notaAntiga}" 
					                  dataAlta="${rspre.dataAlta}" 
					                  dataContestacioInici="${rspre.dataContestacioInici}" 
					                  dataContestacioFi="${rspre.dataContestacioFi}" 
					                  dataCorreccio="${rspre.dataCorreccio}" 
					                  dataRevisio="${rspre.dataRevisio}" 
					                  textResposta="${rspre.textResposta}" 
					                  textRaonarResposta="${rspre.textRaonarResposta}" 
					                  textCorreccio="${rspre.textCorreccio}"
					                  textRevisio="${rspre.textRevisio}"
					                  anonima="${rspre.anonima}">
						<pregunta codi="${rspre.pregunta.codi}" 
						          enunciat="${rspre.pregunta.enunciat}" 
						          tipus="${rspre.pregunta.tipus}" 
						          notaMitja="${rspre.pregunta.notaMitja}" 
						          tempsRespostaMig="${rspre.pregunta.tempsRespostaMig}" 
						          dataAlta="${rspre.pregunta.dataAlta}" 
						          raonarResposta="${rspre.pregunta.raonarResposta}" 
						          dificultatTeorica="${rspre.pregunta.dificultatTeorica}" 
						          dificultatPractica="${rspre.pregunta.dificultatPractica}"/>
						<c:if test="${not empty rspre.opcionsMarcades}">
							<opcionsMarcades>
								<c:forEach items="${rspre.opcionsMarcades}" var="opm">
									<opcioMarcada codiOpcio="${opm.opcio.codi}" text="${opm.opcio.text}" correcta="${opm.opcio.correcta}"/>
								</c:forEach>
							</opcionsMarcades>
						</c:if>
					</respostaPregunta>
				</c:forEach>
			</respostaPreguntes>
		</c:if>
	</respostaQuestionari>
</respostesQuestionari>