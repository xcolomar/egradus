<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<script type="text/javascript">
	$(function () {
		$('#cercaAssignaturaDataInici, #cercaAssignaturaDataFi').datetimepicker({
			format: 'DD/MM/YYYY',
			pickTime: false,
		    language: '${idioma}'
		});
	});
</script>

<h3><fmt:message bundle="${missatge}" key="assignatura.cerca.mis.cerca.asignatura"/></h3>
<form id="cercaAssignaturaForm">
	<fieldset>
		<legend><fmt:message bundle="${missatge}" key="assignatura.cerca.legend.filtre.cerca"/></legend>
		<div>
			<div class="row">
				<div class="col-sm-6 col-lg-4">
					<div class="form-group">
						<label for="cercaAssignaturaCodiReferencia"><fmt:message bundle="${missatge}" key="assignatura.cerca.label.codi.referencia"/></label>
						<input class="form-control" id="cercaAssignaturaCodiReferencia" name="codiReferencia" type="text" placeholder="<fmt:message bundle="${missatge}" key="assignatura.cerca.placeholder.codi.referencia"/>"/>
					</div>
				</div>
				<div class="col-sm-4 col-md-3 col-lg-2">
					<div class="form-group">
						<label for="cercaAssignaturaAnyAcademic"><fmt:message bundle="${missatge}" key="assignatura.cerca.label.any.academic"/></label>
						<input class="form-control" id="cercaAssignaturaAnyAcademic" name="anyAcademic" type="text" placeholder="<fmt:message bundle="${missatge}" key="assignatura.cerca.placeholder.any.academic"/>"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 col-lg-4">
					<div class="form-group">
						<label for="cercaAssignaturaNom"><fmt:message bundle="${missatge}" key="assignatura.cerca.label.nom.assignatura"/></label>
						<input class="form-control" id="cercaAssignaturaNom" name="nomAssignatura" type="text" placeholder="<fmt:message bundle="${missatge}" key="assignatura.cerca.placeholder.nom.assignatura"/>"/>
					</div>
				</div>
				<div class="col-md-6 col-lg-4">
					<div class="form-group">
						<label for="cercaAssignaturaCreador"><fmt:message bundle="${missatge}" key="assignatura.cerca.label.creador"/></label>
						<input class="form-control" id="cercaAssignaturaCreador" name="creador" type="text" placeholder="<fmt:message bundle="${missatge}" key="assignatura.cerca.placeholder.creador"/>"/>
					</div>
				</div>
				<div class="col-sm-6 col-md-3 col-lg-2">
			    	<div class="form-group">
			    		<label for="cercaAssignaturaDataInici"><fmt:message bundle="${missatge}" key="assignatura.cerca.label.despres.de"/></label>
			        	<div class="input-group date" id="cercaAssignaturaDataInici">
			            	<input id="cercaAssignaturaDataIniciInput" type="text" name="dataInici" class="form-control" />
			                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			            </div>
			        </div>
			    </div>
			    <div class="col-sm-6 col-md-3 col-lg-2">
			    	<div class="form-group">
			    		<label for="cercaAssignaturaDataFi"><fmt:message bundle="${missatge}" key="assignatura.cerca.label.abans.de"/></label>
			        	<div class="input-group date" id="cercaAssignaturaDataFi">
			            	<input id="cercaAssignaturaDataFiInput" type="text" name="dataFi" class="form-control" />
			                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
			            </div>
			        </div>
			    </div>
			    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-12">
			    	<div class="form-group">
			    		<div class="row">
			    			<div class="col-xs-6 col-sm-6 col-md-6 col-lg-3 padding-chbx-rdb-20">
			    				<input id="clauprosi" type="checkbox" name="clauprosi" value="clauprosi">
			    				<fmt:message bundle="${missatge}" key="assignatura.cerca.checkbox.amb.claupro"/>
			    			</div>
			    			<div class="col-xs-6 col-sm-6 col-md-6 col-lg-3 padding-chbx-rdb-20">
			    				<input id="claualusi" type="checkbox" name="claualusi" value="claualusi">
			    				<fmt:message bundle="${missatge}" key="assignatura.cerca.checkbox.amb.claualu"/>
			    			</div>
			    		</div>
			    		<div class="row">
			    			<div class="col-xs-6 col-sm-6 col-md-6 col-lg-3">
			    				<input id="clauprono" type="checkbox" name="clauprono" value="clauprono">
			    				<fmt:message bundle="${missatge}" key="assignatura.cerca.checkbox.sense.claupro"/>
			    			</div>
			    			<div class="col-xs-6 col-sm-6 col-md-6 col-lg-3">
			    				<input id="claualuno" type="checkbox" name="claualuno" value="claualuno">
			    				<fmt:message bundle="${missatge}" key="assignatura.cerca.checkbox.sense.claualu"/>
			    			</div>
			    		</div>
			    	</div>
			    </div>
			</div>
			<div class="row">
		    	<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
		    		<div class="form-group">
		    			<a class="btn btn-default" href="javascript: cerca();"><fmt:message bundle="${missatge}" key="assignatura.cerca.button.cerca"/></a>
		    		</div>
		    	</div>
		    </div>
		</div>
	</fieldset>
</form>

<fieldset>
	<legend><fmt:message bundle="${missatge}" key="assignatura.cerca.legend.resultats"/></legend>
	<table id="cercaAssignaturaTaulaResultats" class="table table-striped table-bordered table-condensed table-hover">
	</table>
</fieldset>