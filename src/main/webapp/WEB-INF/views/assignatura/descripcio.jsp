<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3 id="descripcioAssignaturaNom"></h3>

<fieldset class="scheduler-border">
	<legend class="scheduler-border"><fmt:message bundle="${missatge}" key="assignatura.descripcio.legend.data.alta"/></legend>
	<div class="container">
		<div class="row">
			<div id="descripcioAssignaturaDataAlta" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>
		</div>
	</div>
</fieldset>

<fieldset class="scheduler-border">
	<legend class="scheduler-border"><fmt:message bundle="${missatge}" key="assignatura.descripcio.legend.any.academic"/></legend>
	<div class="container">
		<div class="row">
			<div id="descripcioAssignaturaAnyAcademic" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>
		</div>
	</div>
</fieldset>

<fieldset class="scheduler-border">
	<legend class="scheduler-border"><fmt:message bundle="${missatge}" key="assignatura.descripcio.legend.descripcio"/></legend>
	<div class="container">
		<div class="row">
			<div id="descripcioAssignaturaDescripcio" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>
		</div>
	</div>
</fieldset>

<fieldset class="scheduler-border">
	<legend class="scheduler-border"><fmt:message bundle="${missatge}" key="assignatura.descripcio.legend.creador"/></legend>
	<div class="container">
		<div class="row">
			<div id="descripcioAssignaturaCreador" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>
		</div>
	</div>
</fieldset>

<fieldset class="scheduler-border">
	<legend class="scheduler-border"><fmt:message bundle="${missatge}" key="assignatura.descripcio.legend.professors"/></legend>
	<div class="container">
		<div class="row">
			<div id="descripcioAssignaturaProfessors" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>
		</div>
	</div>
</fieldset>

<fieldset class="scheduler-border">
	<legend class="scheduler-border"><fmt:message bundle="${missatge}" key="assignatura.descripcio.legend.uneixme"/></legend>
	<div class="container">
		
		<div class="row">
			<div id="descripcioAssignaturaInfoUnio" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>
		</div>
			
		<form id="descripcioAssignaturaFormUnirse" class="form-horizontal">
			<input id="descripcioAssignaturaCodi" name="codiAssignatura" type="hidden">
			
			<div class="row" id="descripcioAssignaturaUnioUnBoto">
				<div class="form-group">
					<label for="descripcioAssignaturaUnioClau" class="col-sm-2 col-md-2 col-lg-1 control-label"><fmt:message bundle="${missatge}" key="assignatura.descripcio.label.clau"/></label>
					<div class="col-sm-5 col-md-5">
						<input class="form-control" id="descripcioAssignaturaUnioClau" name="clau" type="password" placeholder="<fmt:message bundle="${missatge}" key="assignatura.descripcio.placeholder.clau"/>"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-md-offset-2 col-lg-offset-1 col-sm-5 col-md-3">
						<a class="btn btn-default" href="javascript: unirseUnBoto();"><fmt:message bundle="${missatge}" key="assignatura.descripcio.label.uneixme"/></a>
					</div>
				</div>
			</div>
			
			<div class="row" id="descripcioAssignaturaUnioDosBotons">
				<div class="form-group">
					<div class="col-xs-offset-2 col-sm-offset-3 col-md-offset-4 col-lg-offset-5 col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<a class="btn btn-default" href="javascript: unirseDosBotonsAlu();"><fmt:message bundle="${missatge}" key="assignatura.descripcio.label.uneixme.alumne"/></a>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-offset-2 col-sm-offset-3 col-md-offset-4 col-lg-offset-5 col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<a class="btn btn-default" href="javascript: unirseDosBotonsPro();"><fmt:message bundle="${missatge}" key="assignatura.descripcio.label.uneixme.professor"/></a>
					</div>
				</div>
			</div>
			
		</form>
	</div>
</fieldset>

<div id="descripcioAssignaturaModalUnioOk" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="descripcioAssignaturaModalLabelUnioOk" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="descripcioAssignaturaModalLabelUnioOk"><fmt:message bundle="${missatge}" key="assignatura.descripcio.modal.unio.titol"/></h4>
			</div>
			<div id="descripcioAssignaturaModalMissatgeUnioOk" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="assignatura.descripcio.modal.tancar"/></button>
				<button id="descripcioAssignaturaModalBotoMaterial" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="assignatura.descripcio.modal.entrar.material"/></button>
			</div>
		</div>
	</div>
</div>

<div id="descripcioAssignaturaModalUnioError" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="descripcioAssignaturaModalLabelUnioError" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="descripcioAssignaturaModalLabelUnioError"><fmt:message bundle="${missatge}" key="assignatura.descripcio.modal.error.titol"/></h4>
			</div>
			<div id="descripcioAssignaturaModalMissatgeUnioError" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="assignatura.descripcio.modal.tancar"/></button>
			</div>
		</div>
	</div>
</div>