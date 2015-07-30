<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="pregunta.creacio.mis.crea.pregunta"/></h3>
<h4><fmt:message bundle="${missatge}" key="pregunta.creacio.mis.crea.pregunta.desc"/></h4>

<form id="creaPreguntaForm">
	<div class="row">
		<div class="col-sm-6 col-md-6 col-lg-5">
			<div class="form-group">
				<label for="creaPreguntaTipus"><fmt:message bundle="${missatge}" key="pregunta.creacio.label.tipus"/></label>
				<select id="creaPreguntaTipus" class="form-control" name="tipus" onchange="determinarTipusPregunta()">
					<option id="creaPreguntaOpcioES1" value="ES1"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.es1"/>
					<option value="ESN"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.esn"/>
					<option value="VOF"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.vof"/>
					<option value="REC"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.rec"/>
				</select>
			</div>
		</div>
		<div class="col-sm-6 col-md-6 col-lg-4">
			<div class="form-group">
				<label for="creaPreguntaDificultatTeorica"><fmt:message bundle="${missatge}" key="pregunta.creacio.label.dificultat.teorica"/></label>
				<input class="form-control" id="creaPreguntaDificultatTeorica" name="dificultatteorica" type="text" placeholder="<fmt:message bundle="${missatge}" key="pregunta.creacio.placeholder.dificultat.teorica"/>"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<div class="form-group">
				<label for="creaPreguntaEnunciat"><fmt:message bundle="${missatge}" key="pregunta.creacio.label.enunciat"/></label>
				<textarea rows="3" class="form-control" id="creaPreguntaEnunciat" name="enunciat" placeholder="<fmt:message bundle="${missatge}" key="pregunta.creacio.placeholder.enunciat"/>"></textarea>
				<div id="creaPreguntaError"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<div id="creaPreguntaRaonarResposta" class="form-group">
				<input id="creaPreguntaCheckBoxRaonarResposta" type="checkbox" name="raonarresposta" value="true">
				<fmt:message bundle="${missatge}" key="pregunta.creacio.checkbox.raonar.resposta"/>
			</div>
		</div>
	</div>
	
	
	<div id="creaPreguntaDivVeureOpcionsVOF">
		<div class="row">
			<div class="col-md-6 col-lg-5">
				<div class="radio">
					<label>
						<input type="radio" name="opciovertaderfals" id="creaPreguntaOpcioVertader" value="V" checked>
						<fmt:message bundle="${missatge}" key="pregunta.creacio.opcio.vertader"/>
					</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="opciovertaderfals" id="creaPreguntaOpcioFals" value="F">
						<fmt:message bundle="${missatge}" key="pregunta.creacio.opcio.fals"/>
					</label>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- En aquest div s'aniràn afegint totes les opcions per als casos ES1 i ESN -->
	<div class="egradus-opcions">
		<div class="row">
			<div class="col-md-12 col-lg-12">
				<div id="creaPreguntaDivVeureOpcionsES1"></div>
				<div id="creaPreguntaDivVeureOpcionsESN"></div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div id="creaPreguntaBotonsHabilitaOpcioES1">
			<div class="col-md-6 col-lg-5">
				<a id="creaPreguntaBotoAfegirOpcioES1" class="btn btn-default" href="javascript: habilitaOpcioES1();">
					<span class="glyphicon glyphicon-plus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.afegir.opcio"/>
				</a>
				<a id="creaPreguntaBotoEliminarOpcioES1" class="btn btn-default" href="javascript: eliminaOpcioES1();">
					<span class="glyphicon glyphicon-minus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.eliminar.opcio"/>
				</a>
			</div>
		</div>
		<div id="creaPreguntaBotonsHabilitaOpcioESN">
			<div class="col-md-6 col-lg-5">
				<a id="creaPreguntaBotoAfegirOpcioESN" class="btn btn-default" href="javascript: habilitaOpcioESN();">
					<span class="glyphicon glyphicon-plus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.afegir.opcio"/>
				</a>
				<a id="creaPreguntaBotoEliminarOpcioESN" class="btn btn-default" href="javascript: eliminaOpcioESN();">
					<span class="glyphicon glyphicon-minus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.eliminar.opcio"/>
				</a>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-lg-9">
			<div class="form-group">
				<a class="btn btn-default pull-right" href="javascript: creacioPregunta();"><fmt:message bundle="${missatge}" key="pregunta.creacio.button.crea"/></a>
			</div>
		</div>
	</div>
</form>

<div id="creaPreguntaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="creaPreguntaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="creaPreguntaModalLabel"><fmt:message bundle="${missatge}" key="pregunta.creacio.modal.title.pregunta.creada"/></h4>
			</div>
			<div id="creaPreguntaModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="pregunta.creacio.modal.tanca"/></button>
			</div>
		</div>
	</div>
</div>