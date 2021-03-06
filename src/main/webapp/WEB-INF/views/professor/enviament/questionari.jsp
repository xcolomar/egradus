<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="questionari.enviament.mis.envia"/></h3>
<h4><fmt:message bundle="${missatge}" key="questionari.enviament.mis.envia.desc"/></h4>

<div class="row">
	<div class="col-md-12">
		<form id="enviaQuestionariForm">
			<div class="row">
				<div class="col-md-12 col-lg-9">
					<fieldset>
						<legend><fmt:message bundle="${missatge}" key="questionari.enviament.descripcio.questionari"/></legend>
						<div id="enviaQuestionariDescripcioQuestionari" class="form-group"></div>
					</fieldset>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-lg-9">
					<fieldset>
						<legend><fmt:message bundle="${missatge}" key="questionari.enviament.legend.seleccionar.alumnes"/></legend>
						<div id="enviaQuestionariLlistatAlumnes" class="form-group"></div>
					</fieldset>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-lg-9">
					<fieldset>
						<legend><fmt:message bundle="${missatge}" key="questionari.enviament.legend.anonim"/></legend>
						<div class="alert alert-info" role="alert">
							<span class="glyphicon glyphicon-info-sign"></span> 
							<fmt:message bundle="${missatge}" key="questionari.enviament.anonim.desc"/>
						</div>
						<div class="row form-group padding-chbx-rdb-10">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="row hover-chbx">
									<div class="col-xs-9 col-sm-8 col-md-6 col-lg-4">
										<fmt:message bundle="${missatge}" key="questionari.enviament.anonim.checkbox"/>
									</div>
									<div class="col-xs-3 col-sm-4 col-md-6 col-lg-8\">
										<input id="enviaQuestionariAnonim" type="checkbox" name="anonim" value="S">
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-lg-9">
					<fieldset>
						<legend><fmt:message bundle="${missatge}" key="questionari.enviament.legend.enviament"/></legend>
						<div class="form-group">
							<div id="enviaQuestionariBotoEnviament"></div>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-lg-9">
					<div class="pull-right" id="enviaQuestionariError"></div>
				</div>
			</div>
		</form>
	</div>
</div>

<div id="enviaQuestionariModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="enviaQuestionariModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="enviaQuestionariModalLabel"><fmt:message bundle="${missatge}" key="questionari.enviament.modal.titol"/></h4>
			</div>
			<div id="enviaQuestionariModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="enviaQuestionariModalBotoCreacio" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.enviament.modal.creacio"/></button>
				<button id="enviaQuestionariModalBotoRepositori" type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message bundle="${missatge}" key="questionari.enviament.modal.repositori"/></button>
			</div>
		</div>
	</div>
</div>