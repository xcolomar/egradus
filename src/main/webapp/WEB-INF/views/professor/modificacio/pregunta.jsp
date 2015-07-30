<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="pregunta.modificacio.mis.modifica.pregunta"/></h3>
<h4><fmt:message bundle="${missatge}" key="pregunta.modificacio.mis.modifica.pregunta.desc"/></h4>

<div id="modificaPreguntaCodi"></div>

<form id="modificaPreguntaForm">
	<div class="row">
		<div class="col-sm-6 col-md-6 col-lg-5">
			<div class="form-group">
				<label for="modificaPreguntaTipus"><fmt:message bundle="${missatge}" key="pregunta.creacio.label.tipus"/></label>
				<select id="modificaPreguntaTipus" class="form-control" name="tipus" onchange="determinarTipusPreguntaModifica()">
					<option id="modificaPreguntaOpcioES1" value="ES1"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.es1"/>
					<option id="modificaPreguntaOpcioESN" value="ESN"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.esn"/>
					<option id="modificaPreguntaOpcioVOF" value="VOF"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.vof"/>
					<option id="modificaPreguntaOpcioREC" value="REC"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.rec"/>
				</select>
			</div>
		</div>
		<div class="col-sm-6 col-md-6 col-lg-4">
			<div class="form-group">
				<label for="modificaPreguntaDificultatTeorica"><fmt:message bundle="${missatge}" key="pregunta.creacio.label.dificultat.teorica"/></label>
				<input class="form-control" id="modificaPreguntaDificultatTeorica" name="dificultatteorica" type="text" placeholder="<fmt:message bundle="${missatge}" key="pregunta.creacio.placeholder.dificultat.teorica"/>"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<div class="form-group">
				<label for="modificaPreguntaEnunciat"><fmt:message bundle="${missatge}" key="pregunta.creacio.label.enunciat"/></label>
				<textarea rows="3" class="form-control" id="modificaPreguntaEnunciat" name="enunciat" placeholder="<fmt:message bundle="${missatge}" key="pregunta.creacio.placeholder.enunciat"/>"></textarea>
				<div id="modificaPreguntaError"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<div class="form-group">
				<input id="modificaPreguntaCheckBoxRaonarResposta" type="checkbox" name="raonarresposta" value="true">
				<fmt:message bundle="${missatge}" key="pregunta.creacio.checkbox.raonar.resposta"/>
			</div>
		</div>
	</div>
	
	<!-- En aquest div es mostraran les opcions de les preguntes VOF -->
	<div id="modificaPreguntaDivVeureOpcionsVOF">
		<div class="row">
			<div class="col-md-6 col-lg-5">
				<div class="radio">
					<label>
						<input type="radio" name="opciovertaderfals" id="modificaPreguntaOpcioVertader" value="V" checked>
						<fmt:message bundle="${missatge}" key="pregunta.creacio.opcio.vertader"/>
					</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="opciovertaderfals" id="modificaPreguntaOpcioFals" value="F">
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
				<div id="modificaPreguntaDivVeureOpcionsES1"></div>
				<div id="modificaPreguntaDivVeureOpcionsESN"></div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div id="modificaPreguntaBotonsHabilitaOpcioES1">
			<div class="col-md-6 col-lg-5">
				<a id="modificaPreguntaBotoAfegirOpcioES1" class="btn btn-default" href="javascript: habilitaOpcioES1modifica();">
					<span class="glyphicon glyphicon-plus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.afegir.opcio"/>
				</a>
				<a id="modificaPreguntaBotoEliminarOpcioES1" class="btn btn-default" href="javascript: eliminaOpcioES1modifica();">
					<span class="glyphicon glyphicon-minus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.eliminar.opcio"/>
				</a>
			</div>
		</div>
		<div id="modificaPreguntaBotonsHabilitaOpcioESN">
			<div class="col-md-6 col-lg-5">
				<a id="modificaPreguntaBotoAfegirOpcioESN" class="btn btn-default" href="javascript: habilitaOpcioESNmodifica();">
					<span class="glyphicon glyphicon-plus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.afegir.opcio"/>
				</a>
				<a id="modificaPreguntaBotoEliminarOpcioESN" class="btn btn-default" href="javascript: eliminaOpcioESNmodifica();">
					<span class="glyphicon glyphicon-minus"></span>
					<fmt:message bundle="${missatge}" key="pregunta.creacio.button.eliminar.opcio"/>
				</a>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-lg-9">
			<div class="form-group">
				<a class="btn btn-primary pull-right egradus-separacio-horitz-botons" href="javascript: modificacioPregunta();"><fmt:message bundle="${missatge}" key="pregunta.modificacio.button.modifica"/></a>
				<a class="btn btn-default pull-right" href="javascript: habilitaRepositoriPre();"><fmt:message bundle="${missatge}" key="pregunta.modificacio.button.cancela"/></a>
			</div>
		</div>
	</div>
</form>

<div id="modificaPreguntaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modificaPreguntaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="modificaPreguntaModalLabel"><fmt:message bundle="${missatge}" key="pregunta.modificacio.modal.title.pregunta.modificada"/></h4>
			</div>
			<div id="modificaPreguntaModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="modificaPreguntaModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="pregunta.modificacio.modal.button.acceptar"/></button>
			</div>
		</div>
	</div>
</div>