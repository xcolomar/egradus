<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3 id="corregirPreguntaTitol"></h3>
<h4 id="corregirPreguntaTitolDescripcio"></h4>

<div id="corregirPreguntaCodiRespostaPregunta"></div>

<form id="corregirPreguntaForm">
	
	<div id="corregirPreguntaAlumneCnt" class="row"></div>
	<div id="corregirPreguntaDataFiCnt" class="row"></div>
	<div id="corregirPreguntaDuracioCnt" class="row"></div>
	<div id="corregirPreguntaNota" class="row"></div>
	<div id="corregirPreguntaDataCorreccio" class="row"></div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div id="corregirPreguntaEnunciat" class="panel-body text-center"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div id="corregirPreguntaOpcionsOREC" class="col-sm-6"></div>
		<div id="corregirPreguntaCorreccioORevisio" class="col-sm-6"></div>
	</div>
	<div class="row">
		<div id="corregirPreguntaError" class="col-sm-6 col-md-6 col-lg-6"></div>
	</div>
	<div class="row">
    	<div class="col-lg-2">
    		<div class="form-group">
    			<a id="corregirPreguntaBoto" class="btn btn-default" href="javascript: finalitzaCorreccioPregunta();"></a>
    		</div>
    	</div>
    </div>
	
</form>

<div id="corregirPreguntaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="corregirPreguntaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="corregirPreguntaModalLabel"></h4>
			</div>
			<div id="corregirPreguntaModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="corregirPreguntaModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="pregunta.correccio.modal.tornar"/></button>
			</div>
		</div>
	</div>
</div>