<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="questionari.contestacio.titol"/></h3>
<h4><fmt:message bundle="${missatge}" key="questionari.contestacio.desc"/></h4>

<div id="contestarQuestionariCodiRespostaQuestionari"></div>

<form id="contestarQuestionariForm">
	<div class="row">
		<div id="contestarQuestionariDataInici" class="col-lg-12"></div>
	</div>
	<div class="row">
		<div id="contestarQuestionariAnonim" class="col-lg-12"></div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div id="contestarQuestionariNom" class="panel-body text-center"></div>
			</div>
		</div>
	</div>
	
	<!-- PANELL CENTRAL DE CONTESTACIÓ DE PREGUNTES -->
	<div class="row">
		<div id="contestarQuestionariPreguntes" class="col-md-offset-1 col-lg-offset-2 col-sm-12 col-md-10 col-lg-8 text-center"></div>
	</div>
	
	<div class="row">
    	<div class="col-lg-2">
    		<div class="form-group">
    			<a class="btn btn-default" href="javascript: finalitzaContestacioQuestionari();"><fmt:message bundle="${missatge}" key="questionari.contestacio.boto.contesta.questionari"/></a>
    		</div>
    	</div>
    </div>
	
</form>

<div id="contestarQuestionariModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="contestarQuestionariModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="contestarQuestionariModalLabel"><fmt:message bundle="${missatge}" key="questionari.contestacio.modal.titol"/></h4>
			</div>
			<div id="contestarQuestionariModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="contestarQuestionariModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.contestacio.modal.tornar"/></button>
			</div>
		</div>
	</div>
</div>