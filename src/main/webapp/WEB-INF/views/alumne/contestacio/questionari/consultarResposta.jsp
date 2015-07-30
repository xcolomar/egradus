<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="consultar.contestacio.questionari.titol"/></h3>
<h4><fmt:message bundle="${missatge}" key="consultar.contestacio.questionari.desc"/></h4>

<div id="consultarRespostaQuestionariAnonim" class="row"></div>
<div id="consultarRespostaQuestionariAssignatPer" class="row"></div>
<div id="consultarRespostaQuestionariContestatPer" class="row"></div>
<div id="consultarRespostaQuestionariDataInici" class="row"></div>
<div id="consultarRespostaQuestionariDataTemps" class="row"></div>
<div id="consultarRespostaQuestionariDataCorreccio" class="row"></div>
<div id="consultarRespostaQuestionariNota" class="row"></div>
<div id="consultarRespostaQuestionariDataRevisio" class="row"></div>
<div id="consultarRespostaQuestionariNotaRevisada" class="row"></div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div id="consultarRespostaQuestionariNom" class="panel-body text-center"></div>
		</div>
	</div>
</div>

<!-- PANELL CENTRAL DE CONTESTACIÓ DE PREGUNTES -->
<div class="row">
	<div id="consultarRespostaQuestionariPreguntes" class="col-md-offset-1 col-lg-offset-2 col-sm-12 col-md-10 col-lg-8 text-center"></div>
</div>