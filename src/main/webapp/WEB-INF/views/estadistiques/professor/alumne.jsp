<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.mis"/></h3>
<h4><fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.mis.desc"/></h4>

<form id="estProAlumnesForm">
	<div class="row">
		<div class="col-md-6">
			<div class="radio">
				<label>
					<input type="radio" name="estproalumnesradio" id="estProAlumnesRadioConjuntAlumnes" onchange="adaptaRadioButtonConjuntAlumnes()" value="conjuntAlumnes" checked>
					<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.radio.conjunt.alumnes"/>
				</label>
			</div>
			<div class="radio">
				<label>
					<input type="radio" name="estproalumnesradio" id="estProAlumnesRadioUnAlumne" onchange="adaptaRadioButtonUnAlumne()" value="unAlumne">
					<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.radio.un.alumne"/>
				</label>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<label for="estProAlumnesSelectAlumne"><fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.select.alumne"/></label>
				<select id="estProAlumnesSelectAlumne" class="form-control" name="tipus" disabled></select>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-2">
			<div class="form-group">
				<a class="btn btn-default pull-left" href="javascript: determinarEstAlumne();"><fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.button.mostra"/></a>
			</div>
		</div>
	</div>
</form>

<!-- En aquests divs es mostraran les estadístiques del conjunt dels alumnes o d'un alumne -->
<div id="estProAlumnesPreguntes">
	<div class="row">
		<div class="col-xs-12"><h3 class="estadistiques-titol"><fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.titol.preguntes"/></h3></div>
	</div>
	<div class="row">
		<div id="estProAlumnesPreguntesNoEstadistiques" class="col-xs-12 estProAlumnes"></div>
	</div>
	<div class="row">
		<div id="estProAlumnesPreguntesDivTaula" class="col-xs-12 estProAlumnes"></div>
	</div>
	<div class="row">
		<div id="estProAlumnesPreguntesGraficaNotes" class="col-xs-12 col-md-6 estProAlumnes"></div>
		<div id="estProAlumnesPreguntesGraficaTempsResposta" class="col-xs-12 col-md-6 estProAlumnes"></div>
	</div>
</div>
<div id="estProAlumnesQuestionaris">
	<div class="row">
		<div class="col-xs-12"><h3 class="estadistiques-titol"><fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.titol.questionaris"/></h3></div>
	</div>
	<div class="row">
		<div id="estProAlumnesQuestionarisNoEstadistiques" class="col-xs-12 estProAlumnes"></div>
	</div>
	<div class="row">
		<div id="estProAlumnesQuestionarisDivTaula" class="col-xs-12 estProAlumnes"></div>
	</div>
	<div class="row">
		<div id="estProAlumnesQuestionarisGraficaNotes" class="col-xs-12 col-md-6 estProAlumnes"></div>
		<div id="estProAlumnesQuestionarisGraficaTempsResposta"class="col-xs-12 col-md-6 estProAlumnes"></div>
	</div>
	<div class="row">
		<div id="estProAlumnesQuestionarisDivUnQuestionari" class="col-xs-12 estProAlumnes"></div>
	</div>
</div>
