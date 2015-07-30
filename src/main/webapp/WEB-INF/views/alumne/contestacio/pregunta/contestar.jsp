<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="pregunta.contestacio.titol"/></h3>
<h4><fmt:message bundle="${missatge}" key="pregunta.contestacio.desc"/></h4>

<div id="contestarPreguntaCodiRespostaPregunta"></div>

<form id="contestarPreguntaForm">
	<div class="row">
		<div id="contestarPreguntaDataInici" class="col-lg-12"></div>
	</div>
	<div class="row">
		<div id="contestarPreguntaAnonima" class="col-lg-12"></div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div id="contestarPreguntaEnunciat" class="panel-body text-center"></div>
			</div>
		</div>
	</div>
	
	<!-- PANELL CENTRAL DE CONTESTACIÓ DE PREGUNTES -->
	<div class="row">
		<div id="contestarPreguntaOpcionsOREC" class="col-sm-6 col-md-6 col-lg-6"></div>
		<div id="contestarPreguntaRaonarResposta" class="col-sm-6 col-md-6 col-lg-6"></div>
	</div>
	
	<div class="row">
    	<div class="col-lg-2">
    		<div class="form-group">
    			<a class="btn btn-default" href="javascript: finalitzaContestacioPregunta();"><fmt:message bundle="${missatge}" key="pregunta.contestacio.boto.contesta.pregunta"/></a>
    		</div>
    	</div>
    </div>
	
</form>

<div id="contestarPreguntaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="contestarPreguntaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="contestarPreguntaModalLabel"><fmt:message bundle="${missatge}" key="pregunta.contestacio.modal.titol"/></h4>
			</div>
			<div id="contestarPreguntaModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="contestarPreguntaModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="pregunta.contestacio.modal.tornar"/></button>
			</div>
		</div>
	</div>
</div>