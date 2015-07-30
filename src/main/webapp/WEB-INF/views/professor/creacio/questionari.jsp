<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<script type="text/javascript">
	$(function () {
		$('#creaQuestionariCercaPreguntaDataInici, #creaQuestionariCercaPreguntaDataFi').datetimepicker({
			format: 'DD/MM/YYYY',
			pickTime: false,
		    language: '${idioma}'
		});
	});
</script>

<h3><fmt:message bundle="${missatge}" key="questionari.creacio.mis.crea.questionari"/></h3>
<h4><fmt:message bundle="${missatge}" key="questionari.creacio.mis.crea.questionari.desc"/></h4>

<form id="creaQuestionariForm">
	<div class="row">
		<div class="col-sm-6 col-md-6 col-lg-5">
			<div class="form-group">
				<label for="creaQuestionariNom"><fmt:message bundle="${missatge}" key="questionari.creacio.label.nom"/></label>
				<input class="form-control" id="creaQuestionariNom" name="nom" type="text" placeholder="<fmt:message bundle="${missatge}" key="questionari.creacio.placeholder.nom"/>"/>
			</div>
		</div>
		<div class="col-sm-6 col-md-6 col-lg-4">
			<div class="form-group">
				<label for="creaQuestionariDificultatTeorica"><fmt:message bundle="${missatge}" key="questionari.creacio.label.dificultat.teorica"/></label>
				<input class="form-control" id="creaQuestionariDificultatTeorica" name="dificultatteorica" type="text" placeholder="<fmt:message bundle="${missatge}" key="questionari.creacio.placeholder.dificultat.teorica"/>"/>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<div class="form-group">
				<label for="creaQuestionariDescripcio"><fmt:message bundle="${missatge}" key="questionari.creacio.label.descripcio"/></label>
				<textarea rows="3" class="form-control" id="creaQuestionariDescripcio" name="descripcio" placeholder="<fmt:message bundle="${missatge}" key="questionari.creacio.placeholder.descripcio"/>"></textarea>
				<div id="creaQuestionariError"></div>
			</div>
		</div>
	</div>
	
	<h4><fmt:message bundle="${missatge}" key="questionari.creacio.mis.preguntes.afegides"/></h4>
	<div class="row">
		<div class="col-md-9 col-lg-9">
			<div class="form-group" id="creaQuestionariPreguntesAfegides">
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-6 col-lg-5">
			<a id="creaQuestionariBotoAfegirPregunta" class="btn btn-default" href="javascript: habilitaAfegirPregunta('crea');">
				<span class="glyphicon glyphicon-plus"></span>
				<fmt:message bundle="${missatge}" key="questionari.creacio.button.afegir.pregunta"/>
			</a>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 col-lg-9">
			<div class="form-group">
				<a class="btn btn-default pull-right" href="javascript: creacioQuestionari();"><fmt:message bundle="${missatge}" key="questionari.creacio.button.crea"/></a>
			</div>
		</div>
	</div>
</form>

<!-- Modal window que permet cercar i seleccionar preguntes per dur-les de volta al formulari de creació del qüestionari -->
<div id="creaQuestionariAfegirPreguntaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="creaQuestionariAfegirPreguntaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="creaQuestionariAfegirPreguntaModalLabel"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.title.afegir.pregunta"/></h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<form id="creaQuestionariCercaPreguntaForm">
							<fieldset>
								<legend><fmt:message bundle="${missatge}" key="repositori.preguntes.legend.filtre.cerca"/></legend>
								<div>
									<div class="row">
										<div class="col-sm-6 col-md-4 col-lg-6">
											<div class="form-group">
												<label for="creaQuestionariCercaPreguntaEnunciat"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.enunciat"/></label>
												<input class="form-control" id="creaQuestionariCercaPreguntaEnunciat" name="enunciat" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.enunciat"/>"/>
											</div>
										</div>
										<div class="col-sm-6 col-md-4 col-lg-6">
											<div class="form-group">
												<label for="creaQuestionariCercaPreguntaCreador"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.creador"/></label>
												<input class="form-control" id="creaQuestionariCercaPreguntaCreador" name="creador" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.creador"/>"/>
											</div>
										</div>
										<div class="col-sm-4 col-md-4 col-lg-4">
											<div class="form-group">
												<label for="creaQuestionariCercaPreguntaTipus"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.tipus"/></label>
												<select id="creaQuestionariCercaPreguntaTipus" class="form-control" name="tipus">
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
												<label for="creaQuestionariCercaPreguntaDifTeoMin"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.teorica.min"/></label>
												<input class="form-control" id="creaQuestionariCercaPreguntaDifTeoMin" name="difTeoMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.teorica.min"/>"/>
											</div>
										</div>
										<div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
											<div class="form-group">
												<label for="creaQuestionariCercaPreguntaDifTeoMax"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.teorica.max"/></label>
												<input class="form-control" id="creaQuestionariCercaPreguntaDifTeoMax" name="difTeoMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.teorica.max"/>"/>
											</div>
										</div>
										<div class="col-sm-4 col-md-3 col-lg-4">
									    	<div class="form-group">
									    		<div class="row">
									    			<div class="col-sm-12 padding-chbx-rdb-20">
									    				<input id="creaQuestionariCercaPreguntaCheckBoxAmbRr" type="checkbox" name="ambrr" value="ambrr">
									    				<fmt:message bundle="${missatge}" key="repositori.preguntes.checkbox.amb.raonar.resposta"/>
									    			</div>
									    		</div>
									    		<div class="row">
									    			<div class="col-sm-12">
									    				<input id="creaQuestionariCercaPreguntaCheckBoxSenseRr" type="checkbox" name="senserr" value="senserr">
									    				<fmt:message bundle="${missatge}" key="repositori.preguntes.checkbox.sense.raonar.resposta"/>
									    			</div>
									    		</div>
									    	</div>
									    </div>
										<div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
									    	<div class="form-group">
									    		<label for="creaQuestionariCercaPreguntaDataInici"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.despres.de"/></label>
									        	<div class="input-group date" id="creaQuestionariCercaPreguntaDataInici">
									            	<input id="creaQuestionariCercaPreguntaDataIniciInput" type="text" name="dataInici" class="form-control" />
									                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									            </div>
									        </div>
									    </div>
									    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
									    	<div class="form-group">
									    		<label for="creaQuestionariCercaPreguntaDataFi"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.abans.de"/></label>
									        	<div class="input-group date" id="creaQuestionariCercaPreguntaDataFi">
									            	<input id="creaQuestionariCercaPreguntaDataFiInput" type="text" name="dataFi" class="form-control" />
									                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
									            </div>
									        </div>
									    </div>
									    <div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
											<div class="form-group">
												<label for="creaQuestionariCercaPreguntaDifPraMin"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.practica.min"/></label>
												<input class="form-control" id="creaQuestionariCercaPreguntaDifPraMin" name="difPraMin" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.practica.min"/>"/>
											</div>
										</div>
										<div class="col-xs-6 col-sm-4 col-md-3 col-lg-4">
											<div class="form-group">
												<label for="creaQuestionariCercaPreguntaDifPraMax"><fmt:message bundle="${missatge}" key="repositori.preguntes.label.dificultat.practica.max"/></label>
												<input class="form-control" id="creaQuestionariCercaPreguntaDifPraMax" name="difPraMax" type="text" placeholder="<fmt:message bundle="${missatge}" key="repositori.preguntes.placeholder.dificultat.practica.max"/>"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								    		<div id="creaQuestionariCercaPreguntaError"></div>
								    	</div>
									</div>
									<div class="row">
								    	<div class="col-lg-2">
								    		<div class="form-group">
								    			<a class="btn btn-default" href="javascript: cercaPreguntaDinsCreaQuestionari('crea');"><fmt:message bundle="${missatge}" key="repositori.preguntes.button.cerca"/></a>
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
							<div id="creaQuestionariCercaPreguntaTaula"></div>
						</fieldset>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.afegir.pregunta.cancelar"/></button>
				<button id="creaQuestionariAfegirPreguntaModalBotoAcceptar" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.afegir.pregunta.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- El qüestionari s'ha creat satisfactòriament -->
<div id="creaQuestionariModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="creaQuestionariModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="creaQuestionariModalLabel"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.title.questionari.creat"/></h4>
			</div>
			<div id="creaQuestionariModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.creacio.modal.tanca"/></button>
			</div>
		</div>
	</div>
</div>