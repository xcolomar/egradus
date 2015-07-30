<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="consultar.contestacio.pregunta.titol"/></h3>
<h4><fmt:message bundle="${missatge}" key="consultar.contestacio.pregunta.desc"/></h4>

<div id="consultarRespostaAnonima" class="row"></div>
<div id="consultarRespostaAssignadaPer" class="row"></div>
<div id="consultarRespostaContestadaPer" class="row"></div>
<div id="consultarRespostaDataInici" class="row"></div>
<div id="consultarRespostaDataTemps" class="row"></div>
<div id="consultarRespostaDataCorreccio" class="row"></div>
<div id="consultarRespostaNota" class="row"></div>
<div id="consultarRespostaDataRevisio" class="row"></div>
<div id="consultarRespostaNotaRevisada" class="row"></div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div id="consultarRespostaEnunciat" class="panel-body text-center"></div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-6 col-md-6 col-lg-6">
		<div class="row">
			<div id="consultarRespostaOpcionsOREC" class="col-sm-12"></div>
		</div>
		<div class="row">
			<div id="consultarRespostaTextCorreccioREC" class="col-sm-12"></div>
		</div>
	</div>
	<div class="col-sm-6 col-md-6 col-lg-6">
		<div class="row">
			<div id="consultarRespostaRaonarResposta" class="col-sm-12"></div>
		</div>
		<div class="row">
			<div id="consultarRespostaTextRevisio" class="col-sm-12"></div>
		</div>
	</div>
</div>