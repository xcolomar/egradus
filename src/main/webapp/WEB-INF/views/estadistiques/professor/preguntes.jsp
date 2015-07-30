<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<div class="row">
	<div class="col-xs-12"><h3 class="estadistiques-titol"><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.titol.una.pregunta"/></h3></div>
</div>
<div class="row">
	<div id="estProPreguntesNoEstadistiquesUnaPregunta" class="col-xs-12"></div>
</div>
<div id="estProPreguntesUnaPregunta">
	<div class="row">
		<div class="form-group col-xs-12 col-md-6 col-lg-4">
			<label for="estProPreguntesSelectUnaPregunta"><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.label.pregunta"/></label>
			<select id="estProPreguntesSelectUnaPregunta" class="form-control" onchange="determinarEstProfessorPregunta()"></select>
		</div>
	</div>
	<div class="row">
		<div id="estProPreguntesAssignacioDirecta" class="col-xs-12">
			<fieldset>
				<legend><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.directa"/></legend>
				<p><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.directa.desc"/></p>
				<table id="estProPreguntesTaulaAssignacioDirecta" class="table table-striped table-bordered table-condensed table-hover">
					<tr>
						<th></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.rebuda"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.contestada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.corregida"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.aprovada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.nota.mitja"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.temps.resposta.mig"/></th>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignatura.actual"/></th>
						<td id="estProPreguntesTaulaRebudesAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.egradus"/></th>
						<td id="estProPreguntesTaulaRebudesEgradusAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesEgradusAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesEgradusAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesEgradusAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaEgradusAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigEgradusAssignacioDirecta" class="estProPreguntesTaulaValor"></td>
					</tr>
				</table>
			</fieldset>
			<fieldset>
				<legend><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.questionari"/></legend>
				<p><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.questionari.desc"/></p>
				<table id="estProPreguntesTaulaAssignacioQuestionari" class="table table-striped table-bordered table-condensed table-hover">
					<tr>
						<th></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.rebuda"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.contestada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.corregida"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.aprovada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.nota.mitja"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.temps.resposta.mig"/></th>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignatura.actual"/></th>
						<td id="estProPreguntesTaulaRebudesAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.egradus"/></th>
						<td id="estProPreguntesTaulaRebudesEgradusAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesEgradusAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesEgradusAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesEgradusAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaEgradusAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigEgradusAssignacioQuestionari" class="estProPreguntesTaulaValor"></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12"><h3 class="estadistiques-titol"><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.titol.conjunt.preguntes"/></h3></div>
</div>
<div class="row">
	<div id="estProPreguntesNoEstadistiquesConjunt" class="col-xs-12"></div>
</div>
<div id="estProPreguntesConjuntPreguntes">
	<div class="row">
		<div id="estProPreguntesAssignacioDirectaConjunt" class="col-xs-12">
			<fieldset>
				<legend><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.directa"/></legend>
				<p><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.directa.conjunt.desc"/></p>
				<table id="estProPreguntesTaulaAssignacioDirectaConjunt" class="table table-striped table-bordered table-condensed table-hover">
					<tr>
						<th></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.rebuda"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.contestada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.corregida"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.aprovada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.nota.mitja"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.temps.resposta.mig"/></th>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignatura.actual"/></th>
						<td id="estProPreguntesTaulaRebudesAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.egradus"/></th>
						<td id="estProPreguntesTaulaRebudesEgradusAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesEgradusAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesEgradusAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesEgradusAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaEgradusAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigEgradusAssignacioDirectaConjunt" class="estProPreguntesTaulaValor"></td>
					</tr>
				</table>
			</fieldset>
			<fieldset>
				<legend><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.questionari"/></legend>
				<p><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignacio.questionari.conjunt.desc"/></p>
				<table id="estProPreguntesTaulaAssignacioQuestionariConjunt" class="table table-striped table-bordered table-condensed table-hover">
					<tr>
						<th></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.rebuda"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.contestada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.corregida"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.aprovada"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.nota.mitja"/></th>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.taula.pregunta.temps.resposta.mig"/></th>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.assignatura.actual"/></th>
						<td id="estProPreguntesTaulaRebudesAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
					</tr>
					<tr>
						<th><fmt:message bundle="${missatge}" key="estadistiques.professor.preguntes.egradus"/></th>
						<td id="estProPreguntesTaulaRebudesEgradusAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaContestadesEgradusAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaCorregidesEgradusAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaAprovadesEgradusAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaNotaMitjaEgradusAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
						<td id="estProPreguntesTaulaTempsRespostaMigEgradusAssignacioQuestionariConjunt" class="estProPreguntesTaulaValor"></td>
					</tr>
				</table>
			</fieldset>
		</div>
	</div>
</div>