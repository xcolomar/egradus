<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="assignatura.creacio.mis.crea.assignatura"/></h3>
<h4><fmt:message bundle="${missatge}" key="assignatura.creacio.mis.crea.assignatura.desc"/></h4>

<form id="creaAssignaturaForm" class="form-horizontal">
	<div class="form-group">
		<label for="creaAssignaturaCodiReferencia" class="col-sm-3 col-md-2 col-lg-2 control-label"><fmt:message bundle="${missatge}" key="assignatura.creacio.label.codi.referencia"/></label>
		<div class="col-sm-6 col-md-4 col-lg-4">
			<input class="form-control" id="creaAssignaturaCodiReferencia" name="codiReferencia" type="text" placeholder="<fmt:message bundle="${missatge}" key="assignatura.creacio.placeholder.codi.referencia"/>"/>
		</div>
	</div>
	<div class="form-group">
		<label for="creaAssignaturaNom" class="col-sm-3 col-md-2 col-lg-2 control-label"><fmt:message bundle="${missatge}" key="assignatura.creacio.label.nom"/></label>
		<div class="col-sm-9 col-md-8 col-lg-8">
			<input class="form-control" id="creaAssignaturaNom" name="nom" type="text" placeholder="<fmt:message bundle="${missatge}" key="assignatura.creacio.placeholder.nom"/>"/>
		</div>
	</div>
	<div class="form-group">
		<label for="creaAssignaturaAnyAcademic" class="col-sm-3 col-md-2 col-lg-2 control-label"><fmt:message bundle="${missatge}" key="assignatura.creacio.label.any.academic"/></label>
		<div class="col-sm-6 col-md-4 col-lg-4">
			<input class="form-control" id="creaAssignaturaAnyAcademic" name="anyAcademic" type="text" placeholder="<fmt:message bundle="${missatge}" key="assignatura.creacio.placeholder.any.academic"/>"/>
		</div>
	</div>
	<div class="form-group">
		<label for="creaAssignaturaDescripcio" class="col-sm-3 col-md-2 control-label"><fmt:message bundle="${missatge}" key="assignatura.creacio.label.descripcio"/></label>
		<div class="col-sm-9 col-md-8 col-lg-8">
			<textarea rows="3" class="form-control" id="creaAssignaturaDescripcio" name="descripcio" placeholder="<fmt:message bundle="${missatge}" key="assignatura.creacio.placeholder.descripcio"/>"></textarea>
		</div>
	</div>
	<div class="form-group">
		<label for="creaAssignaturaClauProfessor" class="col-sm-3 col-md-2 control-label"><fmt:message bundle="${missatge}" key="assignatura.creacio.label.clau.professor"/></label>
		<div class="col-sm-6 col-md-4 col-lg-4">
			<input class="form-control" id="creaAssignaturaClauProfessor" name="clauProfessor" type="password" placeholder="<fmt:message bundle="${missatge}" key="assignatura.creacio.placeholder.clau.professor"/>"/>
		</div>
	</div>
	<div class="form-group">
		<label for="creaAssignaturaClauAlumne" class="col-sm-3 col-md-2 control-label"><fmt:message bundle="${missatge}" key="assignatura.creacio.label.clau.alumne"/></label>
		<div class="col-sm-6 col-md-4 col-lg-4">
			<input class="form-control" id="creaAssignaturaClauAlumne" name="clauAlumne" type="password" placeholder="<fmt:message bundle="${missatge}" key="assignatura.creacio.placeholder.clau.alumne"/>"/>
		</div>
	</div>
	<div class="row">
		<div id="creaAssignaturaError" class="col-sm-offset-3 col-md-offset-2 col-lg-offset-2 col-sm-9 col-md-8 col-lg-8"></div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-3 col-md-offset-2 col-lg-offset-2 col-sm-3 col-md-2 col-lg-2">
			<a class="btn btn-default" href="javascript: creacio();"><fmt:message bundle="${missatge}" key="assignatura.creacio.button.crea"/></a>
		</div>
	</div>
</form>

<div id="creaAssignaturaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="creaAssignaturaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="creaAssignaturaModalLabel"><fmt:message bundle="${missatge}" key="assignatura.creacio.modal.title.asignatura.creada"/></h4>
			</div>
			<div id="creaAssignaturaModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="assignatura.creacio.modal.tanca"/></button>
			</div>
		</div>
	</div>
</div>