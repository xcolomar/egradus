<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3 id="corregirQuestionariTitol"></h3>
<h4 id="corregirQuestionariTitolDescripcio"></h4>

<div id="corregirQuestionariCodiRespostaQuestionari"></div>

<form id="corregirQuestionariForm">

	<div id="corregirQuestionariContestatPer" class="row"></div>
	<div id="corregirQuestionariDataFiCnt" class="row"></div>
	<div id="corregirQuestionariDataTemps" class="row"></div>
	<div id="corregirQuestionariNota" class="row"></div>
	<div id="corregirQuestionariDataCorreccio" class="row"></div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div id="corregirQuestionariNom" class="panel-body text-center"></div>
			</div>
		</div>
	</div>
	
	<!-- PANELL CENTRAL DE CORRECCIÓ DE PREGUNTES -->
	<div class="row">
		<div id="corregirQuestionariPreguntes" class="col-md-offset-1 col-lg-offset-2 col-sm-12 col-md-10 col-lg-8 text-center"></div>
	</div>
	
	<div class="row">
		<div id="corregirQuestionariError" class="col-sm-6 col-md-6 col-lg-6"></div>
	</div>
	
	<div class="row">
    	<div class="col-lg-2">
    		<div class="form-group">
    			<a id="corregirQuestionariBoto" class="btn btn-default" href="javascript: finalitzaCorreccioQuestionari();"></a>
    		</div>
    	</div>
    </div>

</form>

<div id="corregirQuestionariModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="corregirQuestionariModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="corregirQuestionariModalLabel"></h4>
			</div>
			<div id="corregirQuestionariModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="corregirQuestionariModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.correccio.modal.tornar"/></button>
			</div>
		</div>
	</div>
</div>