<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<script type="text/javascript">
	$(function () {
		$('#repositoriPreguntesDataInici, #repositoriPreguntesDataFi').datetimepicker({
			format: 'DD/MM/YYYY',
			pickTime: false,
		    language: '${idioma}'
		});
	});
</script>

<h3><fmt:message bundle="${missatge}" key="repositori.preguntes.mis.repositori.preguntes"/></h3>
<h4><fmt:message bundle="${missatge}" key="repositori.preguntes.mis.repositori.preguntes.desc"/></h4>

<div class="row">
	<div class="col-md-2 col-md-offset-10">
		<a class="btn btn-default pull-right" href="javascript: habilitaCreacioPregunta();">
			<span class="glyphicon glyphicon-plus"></span>
			<fmt:message bundle="${missatge}" key="repositori.preguntes.boto.crea.pregunta"/>
		</a>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<form id="cercaPreguntaForm">
			<fieldset>
				<legend><fmt:message bundle="${missatge}" key="repositori.preguntes.legend.filtre.cerca"/></legend>
				<div>
					<div class="row">
						<div class="col-sm-6 col-md-4 col-lg-3">
							<div class="form-group">
								<label for="repositoriPreguntesEnunciat"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.enunciat"/></label>
								<input class="form-control" id="repositoriPreguntesEnunciat" name="enunciat" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.enunciat"/>"/>
							</div>
						</div>
						<div class="col-sm-6 col-md-4 col-lg-3">
							<div class="form-group">
								<label for="repositoriPreguntesCreador"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.creador"/></label>
								<input class="form-control" id="repositoriPreguntesCreador" name="creador" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.creador"/>"/>
							</div>
						</div>
						<div class="col-sm-4 col-md-4 col-lg-2">
							<div class="form-group">
								<label for="repositoriPreguntesTipus"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.tipus"/></label>
								<select id="repositoriPreguntesTipus" class="form-control" name="tipus">
									<option value="NUL"> </option>
									<option value="ES1"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.es1"/></option>
									<option value="ESN"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.esn"/></option>
									<option value="VOF"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.vof"/></option>
									<option value="REC"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.rec"/></option>
								</select>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
							<div class="form-group">
								<label for="repositoriPreguntesDifTeoMin"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.teorica.min"/></label>
								<input class="form-control" id="repositoriPreguntesDifTeoMin" name="difTeoMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.teorica.min"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
							<div class="form-group">
								<label for="repositoriPreguntesDifTeoMax"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.teorica.max"/></label>
								<input class="form-control" id="repositoriPreguntesDifTeoMax" name="difTeoMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.teorica.max"/>"/>
							</div>
						</div>
						<div class="col-sm-4 col-md-3 col-lg-4">
					    	<div class="form-group">
					    		<div class="row">
					    			<div class="col-sm-12 padding-chbx-rdb-20">
					    				<input id="checkBoxAmbRaonarResposta" type="checkbox" name="ambrr" value="ambrr">
					    				<fmt:message bundle="${missatge}" key="repositori.preguntes.checkbox.amb.raonar.resposta"/>
					    			</div>
					    		</div>
					    		<div class="row">
					    			<div class="col-sm-12">
					    				<input id="checkBoxSenseRaonarResposta" type="checkbox" name="senserr" value="senserr">
					    				<fmt:message bundle="${missatge}" key="repositori.preguntes.checkbox.sense.raonar.resposta"/>
					    			</div>
					    		</div>
					    	</div>
					    </div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					    	<div class="form-group">
					    		<label for="repositoriPreguntesDataInici"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.despres.de"/></label>
					        	<div class="input-group date" id="repositoriPreguntesDataInici">
					            	<input id="repositoriPreguntesDataIniciInput" type="text" name="dataInici" class="form-control" />
					                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
					        </div>
					    </div>
					    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					    	<div class="form-group">
					    		<label for="repositoriPreguntesDataFi"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.abans.de"/></label>
					        	<div class="input-group date" id="repositoriPreguntesDataFi">
					            	<input id="repositoriPreguntesDataFiInput" type="text" name="dataFi" class="form-control" />
					                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
					        </div>
					    </div>
					    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
							<div class="form-group">
								<label for="repositoriPreguntesDifPraMin"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.practica.min"/></label>
								<input class="form-control" id="repositoriPreguntesDifPraMin" name="difPraMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.practica.min"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
							<div class="form-group">
								<label for="repositoriPreguntesDifPraMax"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.practica.max"/></label>
								<input class="form-control" id="repositoriPreguntesDifPraMax" name="difPraMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.practica.max"/>"/>
							</div>
						</div>
						<div class="col-xs-6 col-sm-4 col-md-4 col-lg-2">
							<div class="form-group">
								<label for="repositoriPreguntesEstat"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.estat"/></label>
								<select id="repositoriPreguntesEstat" class="form-control" name="estat">
									<option value="nul"> </option>
									<option value="editable"><fmt:message bundle="${missatge}" key="repositori.preguntes.select.editable"/></option>
									<option value="public"><fmt:message bundle="${missatge}" key="repositori.preguntes.select.public"/></option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				    		<div id="cercaPreguntaError"></div>
				    	</div>
					</div>
					<div class="row">
				    	<div class="col-lg-2">
				    		<div class="form-group">
				    			<a class="btn btn-default" href="javascript: cercaPreguntes();"><fmt:message bundle="${missatge}" key="repositori.preguntes.button.cerca"/></a>
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
			<legend><fmt:message bundle="${missatge}" key="repositori.preguntes.legend.resultats"/></legend>
			<table id="repositoriPreguntesTaula" class="table table-striped table-bordered table-condensed table-hover">
			</table>
		</fieldset>
	</div>
</div>

<!-- modal window per avisar que estam a punt de publicar la pregunta -->
<div id="repositoriPreguntesModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriPreguntesModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriPreguntesModalLabel"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.title.publicar.pregunta"/></h4>
			</div>
			<div id="repositoriPreguntesModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicar.cancelar"/></button>
				<button id="repositoriPreguntesModalBotoPublicar" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicar.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- modal window per avisar que estam a punt d'eliminar la pregunta -->
<div id="repositoriPreguntesModalElimina" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriPreguntesModalEliminaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriPreguntesModalEliminaLabel"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.title.eliminar.pregunta"/></h4>
			</div>
			<div id="repositoriPreguntesModalEliminaMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminar.cancelar"/></button>
				<button id="repositoriPreguntesModalBotoEliminar" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminar.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- modal window per avisar que acabam de publicar la pregunta -->
<div id="repositoriPreguntesModalPublicada" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriPreguntesModalPublicadaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriPreguntesModalPublicadaLabel"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.title.pregunta.publicada"/></h4>
			</div>
			<div id="repositoriPreguntesModalPublicadaMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.boto.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- modal window per avisar que acabam d'eliminar la pregunta -->
<div id="repositoriPreguntesModalEliminada" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="repositoriPreguntesModalEliminadaLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="repositoriPreguntesModalEliminadaLabel"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.title.pregunta.eliminada"/></h4>
			</div>
			<div id="repositoriPreguntesModalEliminadaMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="repositori.preguntes.modal.boto.acceptar"/></button>
			</div>
		</div>
	</div>
</div>