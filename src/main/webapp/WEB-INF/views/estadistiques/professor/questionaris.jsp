<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<div class="row">
	<div class="col-xs-12"><h3 class="estadistiques-titol"><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.titol.un.questionari"/></h3></div>
</div>
<div class="row">
	<div id="estProQuestionarisNoEstadistiquesUnQuestionari" class="col-xs-12"></div>
</div>
<div id="estProQuestionarisUnQuestionari">
	<div class="row">
		<div class="form-group col-xs-12 col-md-6 col-lg-4">
			<label for="estProQuestionarisSelectUnQuestionari"><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.label.questionari"/></label>
			<select id="estProQuestionarisSelectUnQuestionari" class="form-control" onchange="determinarEstProfessorQuestionari()"></select>
		</div>
	</div>
	<div class="row">
		<div id="estProQuestionaris" class="col-xs-12">
			<p><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.desc"/></p>
			<table id="estProQuestionarisTaula" class="table table-striped table-bordered table-condensed table-hover">
				<tr>
					<th></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.rebut"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.contestat"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.corregit"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.aprovat"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.nota.mitja"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.temps.resposta.mig"/></th>
				</tr>
				<tr>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.assignatura.actual"/></th>
					<td id="estProQuestionarisTaulaRebuts" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaContestats" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaCorregits" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaAprovats" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaNotaMitja" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaTempsRespostaMig" class="estProQuestionarisTaulaValor"></td>
				</tr>
				<tr>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.egradus"/></th>
					<td id="estProQuestionarisTaulaRebutsEgradus" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaContestatsEgradus" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaCorregitsEgradus" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaAprovatsEgradus" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaNotaMitjaEgradus" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaTempsRespostaMigEgradus" class="estProQuestionarisTaulaValor"></td>
				</tr>
			</table>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12"><h3 class="estadistiques-titol"><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.titol.conjunt.questionaris"/></h3></div>
</div>
<div class="row">
	<div id="estProQuestionarisNoEstadistiquesConjunt" class="col-xs-12"></div>
</div>
<div id="estProQuestionarisConjuntQuestionaris">
	<div class="row">
		<div id="estProQuestionarisConjunt" class="col-xs-12">
			<p><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.conjunt.desc"/></p>
			<table id="estProQuestionarisTaulaConjunt" class="table table-striped table-bordered table-condensed table-hover">
				<tr>
					<th></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.rebut"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.contestat"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.corregit"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.aprovat"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.nota.mitja"/></th>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.taula.questionari.temps.resposta.mig"/></th>
				</tr>
				<tr>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.assignatura.actual"/></th>
					<td id="estProQuestionarisTaulaRebutsConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaContestatsConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaCorregitsConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaAprovatsConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaNotaMitjaConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaTempsRespostaMigConjunt" class="estProQuestionarisTaulaValor"></td>
				</tr>
				<tr>
					<th><fmt:message bundle="${missatge}" key="estadistiques.professor.questionaris.egradus"/></th>
					<td id="estProQuestionarisTaulaRebutsEgradusConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaContestatsEgradusConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaCorregitsEgradusConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaAprovatsEgradusConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaNotaMitjaEgradusConjunt" class="estProQuestionarisTaulaValor"></td>
					<td id="estProQuestionarisTaulaTempsRespostaMigEgradusConjunt" class="estProQuestionarisTaulaValor"></td>
				</tr>
			</table>
		</div>
	</div>
</div>