<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<script type="text/javascript">
	$(function () {
		$('#modificaQuestionariCercaPreguntaDataInici, #modificaQuestionariCercaPreguntaDataFi').datetimepicker({
			format: 'DD/MM/YYYY',
			pickTime: false,
		    language: '${idioma}'
		});
	});
</script>

<h3><fmt:message bundle="${missatge}" key="questionari.modificacio.mis.modifica.questionari"/></h3>
<h4><fmt:message bundle="${missatge}" key="questionari.modificacio.mis.modifica.questionari.desc"/></h4>

<div id="modificaQuestionariCodi"></div>

<form id="modificaQuestionariForm">
	<div class="row">
		<div class="col-sm-6 col-md-6 col-lg-5">
			<div class="form-group">
				<label for="modificaQuestionariNom"><fmt:message bundle="${missatge}" key="questionari.creacio.label.nom"/></label>
				<input class="form-control" id="modificaQuestionariNom" name="nom" type="text" placeholder="<fmt:message bundle="${missatge}" key="questionari.creacio.placeholder.nom"/>"/>
			</div>
		</div>
		<div class="col-sm-6 col-md-6 col-lg-4">
			<div class="form-group">
				<label for="modificaQuestionariDificultatTeorica"><fmt:message bundle="${missatge}" key="questionari.creacio.label.dificultat.teorica"/></label>
				<input class="form-control" id="modificaQuestionariDificultatTeorica" name="dificultatteorica" type="text" placeholder="<fmt:message bundle="${missatge}" key="questionari.creacio.placeholder.dificultat.teorica"/>"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<div class="form-group">
				<label for="modificaQuestionariDescripcio"><fmt:message bundle="${missatge}" key="questionari.creacio.label.descripcio"/></label>
				<textarea rows="3" class="form-control" id="modificaQuestionariDescripcio" name="descripcio" placeholder="<fmt:message bundle="${missatge}" key="questionari.creacio.placeholder.descripcio"/>"></textarea>
				<div id="modificaQuestionariError"></div>
			</div>
		</div>
	</div>
	
	<h4><fmt:message bundle="${missatge}" key="questionari.creacio.mis.preguntes.afegides"/></h4>
	<div class="row">
		<div class="col-md-9 col-lg-9">
			<div class="form-group" id="modificaQuestionariPreguntesAfegides">
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<a id="modificaQuestionariBotoAfegirPregunta" class="btn btn-default" href="javascript: habilitaAfegirPregunta('modifica');">
				<span class="glyphicon glyphicon-plus"></span>
				<fmt:message bundle="${missatge}" key="questionari.creacio.button.afegir.pregunta"/>
			</a>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 col-lg-9">
			<div class="form-group">
				<a class="btn btn-primary pull-right egradus-separacio-horitz-botons" href="javascript: modificacioQuestionari();"><fmt:message bundle="${missatge}" key="questionari.modificacio.button.modifica"/></a>
				<a class="btn btn-default pull-right" href="javascript: habilitaRepositoriQst();"><fmt:message bundle="${missatge}" key="questionari.modificacio.button.cancela"/></a>
			</div>
		</div>
	</div>
</form>

<!-- Modal window que permet cercar i seleccionar preguntes per dur-les de volta al formulari de modificació del qüestionari -->
<div id="modificaQuestionariAfegirPreguntaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modificaQuestionariAfegirPreguntaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="modificaQuestionariAfegirPreguntaModalLabel"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.title.afegir.pregunta"/></h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form id="modificaQuestionariCercaPreguntaForm">
							<fieldset>
								<legend><fmt:message bundle="${missatge}" key="repositori.preguntes.legend.filtre.cerca"/></legend>
								<div>
									<div class="row">
										<div class="col-sm-6 col-md-4 col-lg-6">
											<div class="form-group">
												<label for="modificaQuestionariCercaPreguntaEnunciat"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.enunciat"/></label>
												<input class="form-control" id="modificaQuestionariCercaPreguntaEnunciat" name="enunciat" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.enunciat"/>"/>
											</div>
										</div>
										<div class="col-sm-6 col-md-4 col-lg-6">
											<div class="form-group">
												<label for="modificaQuestionariCercaPreguntaCreador"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.creador"/></label>
												<input class="form-control" id="modificaQuestionariCercaPreguntaCreador" name="creador" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.creador"/>"/>
											</div>
										</div>
										<div class="col-sm-4 col-md-4 col-lg-4">
											<div class="form-group">
												<label for="modificaQuestionariCercaPreguntaTipus"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.tipus"/></label>
												<select id="modificaQuestionariCercaPreguntaTipus" class="form-control" name="tipus">
													<option value="NUL"> </option>
													<option value="ES1"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.es1"/></option>
													<option value="ESN"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.esn"/></option>
													<option value="VOF"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.vof"/></option>
													<option value="REC"><fmt:message bundle="${missatge}" key="pregunta.creacio.select.rec"/></option>
												</select>
											</div>
										</div>
										<div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
											<div class="form-group">
												<label for="modificaQuestionariCercaPreguntaDifTeoMin"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.teorica.min"/></label>
												<input class="form-control" id="modificaQuestionariCercaPreguntaDifTeoMin" name="difTeoMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.teorica.min"/>"/>
											</div>
										</div>
										<div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
											<div class="form-group">
												<label for="modificaQuestionariCercaPreguntaDifTeoMax"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.teorica.max"/></label>
												<input class="form-control" id="modificaQuestionariCercaPreguntaDifTeoMax" name="difTeoMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.teorica.max"/>"/>
											</div>
										</div>
										<div class="col-sm-4 col-md-3 col-lg-4">
									    	<div class="form-group">
									    		<div class="row">
									    			<div class="col-sm-12 padding-chbx-rdb-20">
									    				<input id="modificaQuestionariCercaPreguntaCheckBoxAmbRr" type="checkbox" name="ambrr" value="ambrr">
									    				<fmt:message bundle="${missatge}" key="repositori.preguntes.checkbox.amb.raonar.resposta"/>
									    			</div>
									    		</div>
									    		<div class="row">
									    			<div class="col-sm-12">
									    				<input id="modificaQuestionariCercaPreguntaCheckBoxSenseRr" type="checkbox" name="senserr" value="senserr">
									    				<fmt:message bundle="${missatge}" key="repositori.preguntes.checkbox.sense.raonar.resposta"/>
									    			</div>
									    		</div>
									    	</div>
									    </div>
										<div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
									    	<div class="form-group">
									    		<label for="modificaQuestionariCercaPreguntaDataInici"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.despres.de"/></label>
									        	<div class="input-group date" id="modificaQuestionariCercaPreguntaDataInici">
									            	<input id="modificaQuestionariCercaPreguntaDataIniciInput" type="text" name="dataInici" class="form-control" />
									                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									            </div>
									        </div>
									    </div>
									    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
									    	<div class="form-group">
									    		<label for="modificaQuestionariCercaPreguntaDataFi"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.abans.de"/></label>
									        	<div class="input-group date" id="modificaQuestionariCercaPreguntaDataFi">
									            	<input id="modificaQuestionariCercaPreguntaDataFiInput" type="text" name="dataFi" class="form-control" />
									                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									            </div>
									        </div>
									    </div>
									    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
											<div class="form-group">
												<label for="modificaQuestionariCercaPreguntaDifPraMin"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.practica.min"/></label>
												<input class="form-control" id="modificaQuestionariCercaPreguntaDifPraMin" name="difPraMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.practica.min"/>"/>
											</div>
										</div>
										<div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
											<div class="form-group">
												<label for="modificaQuestionariCercaPreguntaDifPraMax"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.practica.max"/></label>
												<input class="form-control" id="modificaQuestionariCercaPreguntaDifPraMax" name="difPraMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.practica.max"/>"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								    		<div id="modificaQuestionariCercaPreguntaError"></div>
								    	</div>
									</div>
									<div class="row">
								    	<div class="col-lg-2">
								    		<div class="form-group">
								    			<a class="btn btn-default" href="javascript: cercaPreguntaDinsCreaQuestionari('modifica');"><fmt:message bundle="${missatge}" key="repositori.preguntes.button.cerca"/></a>
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
							<div id="modificaQuestionariCercaPreguntaTaula"></div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.afegir.pregunta.cancelar"/></button>
				<button id="modificaQuestionariAfegirPreguntaModalBotoAcceptar" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.afegir.pregunta.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<div id="modificaQuestionariModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modificaQuestionariModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="modificaQuestionariModalLabel"><fmt:message bundle="${missatge}" key="questionari.modificacio.modal.title.questionari.modificat"/></h4>
			</div>
			<div id="modificaQuestionariModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="modificaQuestionariModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.modificacio.modal.button.acceptar"/></button>
			</div>
		</div>
	</div>
</div>