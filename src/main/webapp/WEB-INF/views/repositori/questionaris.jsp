<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<script type="text/javascript">
	$(function () {
		$('#repositoriQuestionarisDataInici, #repositoriQuestionarisDataFi').datetimepicker({
			format: 'DD/MM/YYYY',
			pickTime: false,
		    language: '${idioma}'
		});
	});
</script>

<h3><fmt:message bundle="${missatge}" key="repositori.questionaris.mis.repositori.questionaris"/></h3>
<h4><fmt:message bundle="${missatge}" key="repositori.questionaris.mis.repositori.questionaris.desc"/></h4>

<div class="row">
	<div class="col-md-2 col-md-offset-10">
		<a class="btn btn-default pull-right" href="javascript: habilitaCreacioQuestionari();">
			<span class="glyphicon glyphicon-plus"></span>
			<fmt:message bundle="${missatge}" key="repositori.questionaris.boto.crea.questionari"/>
		</a>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<form id="cercaQuestionariForm">
			<fieldset>
				<legend><fmt:message bundle="${missatge}" key="repositori.questionaris.legend.filtre.cerca"/></legend>
				<div>
					<div class="row">
						<div class="col-sm-6 col-md-4 col-lg-3">
							<div class="form-group">
								<label for="repositoriQuestionarisNom"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.nom"/></label>
								<input class="form-control" id="repositoriQuestionarisNom" name="nom" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.questionaris.placeholder.nom"/>"/>
							</div>
						</div>
						<div class="col-sm-6 col-md-4 col-lg-5">
							<div class="form-group">
								<label for="repositoriQuestionarisDescripcio"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.descripcio"/></label>
								<input class="form-control" id="repositoriQuestionarisDescripcio" name="descripcio" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.questionaris.placeholder.descripcio"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
							<div class="form-group">
								<label for="repositoriQuestionarisDifTeoMin"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.dificultat.teorica.min"/></label>
								<input class="form-control" id="repositoriQuestionarisDifTeoMin" name="difTeoMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.questionaris.placeholder.dificultat.teorica.min"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
							<div class="form-group">
								<label for="repositoriQuestionarisDifTeoMax"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.dificultat.teorica.max"/></label>
								<input class="form-control" id="repositoriQuestionarisDifTeoMax" name="difTeoMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.questionaris.placeholder.dificultat.teorica.max"/>"/>
							</div>
						</div>
						<div class="col-sm-6 col-md-4 col-lg-3">
							<div class="form-group">
								<label for="repositoriQuestionariCreador"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.creador"/></label>
								<input class="form-control" id="repositoriQuestionariCreador" name="creador" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.questionaris.placeholder.creador"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					    	<div class="form-group">
					    		<label for="repositoriQuestionarisDataInici"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.despres.de"/></label>
					        	<div class="input-group date" id="repositoriQuestionarisDataInici">
					            	<input id="repositoriQuestionarisDataIniciInput" type="text" name="dataInici" class="form-control" />
					                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
					        </div>
					    </div>
					    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					    	<div class="form-group">
					    		<label for="repositoriQuestionarisDataFi"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.abans.de"/></label>
					        	<div class="input-group date" id="repositoriQuestionarisDataFi">
					            	<input id="repositoriQuestionarisDataFiInput" type="text" name="dataFi" class="form-control" />
					                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
					        </div>
					    </div>
					    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-offset-1 col-lg-2">
							<div class="form-group">
								<label for="repositoriQuestionarisDifPraMin"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.dificultat.practica.min"/></label>
								<input class="form-control" id="repositoriQuestionarisDifPraMin" name="difPraMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.questionaris.placeholder.dificultat.practica.min"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
							<div class="form-group">
								<label for="repositoriQuestionarisDifPraMax"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.dificultat.practica.max"/></label>
								<input class="form-control" id="repositoriQuestionarisDifPraMax" name="difPraMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.questionaris.placeholder.dificultat.practica.max"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-4 col-lg-2">
							<div class="form-group">
								<label for="repositoriQuestionarisEstat"><fmt:message bundle="${missatge}" key="repositori.questionaris.label.estat"/></label>
								<select id="repositoriQuestionarisEstat" class="form-control" name="estat">
									<option value="nul"> </option>
									<option value="editable"><fmt:message bundle="${missatge}" key="repositori.questionaris.select.editable"/></option>
									<option value="public"><fmt:message bundle="${missatge}" key="repositori.questionaris.select.public"/></option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				    		<div id="cercaQuestionariError"></div>
				    	</div>
					</div>
					<div class="row">
				    	<div class="col-lg-2">
				    		<div class="form-group">
				    			<a class="btn btn-default" href="javascript: cercaQuestionaris();"><fmt:message bundle="${missatge}" key="repositori.questionaris.button.cerca"/></a>
				    		</div>
				    	</div>
				    </div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<fieldset>
			<legend><fmt:message bundle="${missatge}" key="repositori.questionaris.legend.resultats"/></legend>
			<table id="repositoriQuestionarisTaula" class="table table-striped table-bordered table-condensed table-hover">
			</table>
		</fieldset>
	</div>
</div>

<!-- modal window per avisar que estam a punt de publicar el qüestionari -->
<div id="repositoriQuestionarisModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriQuestionarisModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriQuestionarisModalLabel"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.title.publicar.questionari"/></h4>
			</div>
			<div id="repositoriQuestionarisModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicar.cancelar"/></button>
				<button id="repositoriQuestionarisModalBotoPublicar" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicar.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- modal window per avisar que estam a punt d'eliminar el qüestionari -->
<div id="repositoriQuestionarisModalElimina" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriQuestionarisModalEliminaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriQuestionarisModalEliminaLabel"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.title.eliminar.questionari"/></h4>
			</div>
			<div id="repositoriQuestionarisModalEliminaMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminar.cancelar"/></button>
				<button id="repositoriQuestionarisModalBotoEliminar" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminar.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- modal window per avisar que acabam de publicar el qüestionari -->
<div id="repositoriQuestionarisModalPublicat" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriQuestionarisModalPublicatLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriQuestionarisModalPublicatLabel"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.title.questionari.publicat"/></h4>
			</div>
			<div id="repositoriQuestionarisModalPublicatMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.boto.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- modal window per avisar que acabam d'eliminar el qüestionari -->
<div id="repositoriQuestionarisModalEliminat" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriQuestionarisModalEliminatLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriQuestionarisModalEliminatLabel"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.title.questionari.eliminat"/></h4>
			</div>
			<div id="repositoriQuestionarisModalEliminatMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.questionaris.modal.boto.acceptar"/></button>
			</div>
		</div>
	</div>
</div>