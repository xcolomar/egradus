<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<div id="materialCodiAssignatura"></div>

<div class="row">
	<div class="col-sm-12">
		<img id="materialIconaAlumne" class="materialIcona" src="<c:url value="/resources/img/alumne.png"/>" alt="alumne" style="width:50px;height:50px;display:inline;">
		<img id="materialIconaProfessor" class="materialIcona" src="<c:url value="/resources/img/professor.png"/>" alt="professor" style="width:50px;height:50px;display:inline;">
		<h2 id="materialNomAssignatura" style="display:inline"></h2>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<i><h4 id="materialAnyAcademic" style="display:inline"></h4></i>
	</div>
</div>

<br />

<div class="row">
	<div id="materialAlumne" class="col-sm-12">
		<ul class="nav nav-pills">
			<li><a class="assignaturaMaterial" id="assignaturaMaterialPreguntes" href="javascript: habilitaPreguntes();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.preguntes"/></a></li>
			<li><a class="assignaturaMaterial" id="assignaturaMaterialQuestionaris" href="javascript: habilitaQuestionaris();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.questionaris"/></a></li>
			<li class="dropdown">
				<a id="assignaturaMaterialEstadistiquesAlu" class="dropdown-toggle assignaturaMaterial" data-toggle="dropdown" href="#"><fmt:message bundle="${missatge}" key="assignatura.material.nav.estadistiques"/> <span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="javascript: habilitaEstadistiquesPreAlu();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.estadistiques.preguntes"/></a></li>
					<li><a href="javascript: habilitaEstadistiquesQstAlu();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.estadistiques.questionaris"/></a></li>
				</ul>
			</li>
		</ul>
	</div>
</div>

<div class="row">
	<div id="materialProfessor" class="col-sm-12">
		<ul class="nav nav-pills">
			<li class="dropdown">
				<a id="assignaturaMaterialCorreccio" class="dropdown-toggle assignaturaMaterial" data-toggle="dropdown" href="#"><fmt:message bundle="${missatge}" key="assignatura.material.nav.correccio"/> <span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="javascript: habilitaCorreccioPre();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.correccio.preguntes"/></a></li>
					<li><a href="javascript: habilitaCorreccioQst();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.correccio.questionaris"/></a></li>
				</ul>
			</li>
			<li class="dropdown">
				<a id="assignaturaMaterialRepositori" class="dropdown-toggle assignaturaMaterial" data-toggle="dropdown" href="#"><fmt:message bundle="${missatge}" key="assignatura.material.nav.repositori"/> <span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="javascript: habilitaRepositoriPre();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.repositori.preguntes"/></a></li>
					<li><a href="javascript: habilitaRepositoriQst();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.repositori.questionaris"/></a></li>
				</ul>
			</li>
			<li class="dropdown">
				<a id="assignaturaMaterialEstadistiquesPro" class="dropdown-toggle assignaturaMaterial" data-toggle="dropdown" href="#"><fmt:message bundle="${missatge}" key="assignatura.material.nav.estadistiques"/> <span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="javascript: habilitaEstadistiquesPrePro();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.estadistiques.preguntes"/></a></li>
					<li><a href="javascript: habilitaEstadistiquesQstPro();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.estadistiques.questionaris"/></a></li>
					<li><a href="javascript: habilitaEstadistiquesAlu();"><fmt:message bundle="${missatge}" key="assignatura.material.nav.estadistiques.alumnes"/></a></li>
				</ul>
			</li>
		</ul>
	</div>
</div>


<!-- VISTES EXCLUSIVES DELS PROFESSORS -->

<div id="materialRepositoriPreguntes" class="egradus-material">
	<jsp:include page="/WEB-INF/views/repositori/preguntes.jsp" />
</div>

<div id="materialCreacioPregunta" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/creacio/pregunta.jsp" />
</div>

<div id="materialModificacioPregunta" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/modificacio/pregunta.jsp" />
</div>

<div id="materialModificacioQuestionari" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/modificacio/questionari.jsp" />
</div>

<div id="materialEnviamentPregunta" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/enviament/pregunta.jsp" />
</div>

<div id="materialEnviamentQuestionari" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/enviament/questionari.jsp" />
</div>

<div id="materialRepositoriQuestionaris" class="egradus-material">
	<jsp:include page="/WEB-INF/views/repositori/questionaris.jsp" />
</div>

<div id="materialCreacioQuestionari" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/creacio/questionari.jsp" />
</div>

<div id="materialConsultaCorreccioPregunta" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/correccio/pregunta/consultarCorreccions.jsp" />
</div>

<div id="materialConsultaCorreccioQuestionari" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/correccio/questionari/consultarCorreccions.jsp" />
</div>

<div id="materialCorreccioPregunta" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/correccio/pregunta/corregir.jsp" />
</div>

<div id="materialCorreccioQuestionari" class="egradus-material">
	<jsp:include page="/WEB-INF/views/professor/correccio/questionari/corregir.jsp" />
</div>

<div id="materialEstadistiquesPrePro" class="egradus-material">
	<jsp:include page="/WEB-INF/views/estadistiques/professor/preguntes.jsp" />
</div>

<div id="materialEstadistiquesQstPro" class="egradus-material">
	<jsp:include page="/WEB-INF/views/estadistiques/professor/questionaris.jsp" />
</div>

<div id="materialEstadistiquesAlu" class="egradus-material">
	<jsp:include page="/WEB-INF/views/estadistiques/professor/alumne.jsp" />
</div>


<!-- VISTES EXCLUSIVES DELS ALUMNES -->

<div id="materialPreguntes" class="egradus-material">
	<jsp:include page="/WEB-INF/views/alumne/contestacio/pregunta/consultarMaterial.jsp" />
</div>

<div id="materialContestarPregunta" class="egradus-material">
	<jsp:include page="/WEB-INF/views/alumne/contestacio/pregunta/contestar.jsp" />
</div>

<div id="materialContestarQuestionari" class="egradus-material">
	<jsp:include page="/WEB-INF/views/alumne/contestacio/questionari/contestar.jsp" />
</div>

<div id="materialConsultarResposta" class="egradus-material">
	<jsp:include page="/WEB-INF/views/alumne/contestacio/pregunta/consultarResposta.jsp" />
</div>

<div id="materialConsultarRespostaQuestionari" class="egradus-material">
	<jsp:include page="/WEB-INF/views/alumne/contestacio/questionari/consultarResposta.jsp" />
</div>

<div id="materialQuestionaris" class="egradus-material">
	<jsp:include page="/WEB-INF/views/alumne/contestacio/questionari/consultarMaterial.jsp" />
</div>

<div id="materialEstadistiquesPreAlu" class="egradus-material">
	<jsp:include page="/WEB-INF/views/estadistiques/alumne/preguntesContestades.jsp" />
</div>

<div id="materialEstadistiquesQstAlu" class="egradus-material">
	<jsp:include page="/WEB-INF/views/estadistiques/alumne/questionarisContestats.jsp" />
</div>

<!-- modal window per mostrar la descripció d'una pregunta -->
<div id="descripcioPreguntaModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="descripcioPreguntaModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="descripcioPreguntaModalLabel"><fmt:message bundle="${missatge}" key="pregunta.descripcio.mis.descripcio.pregunta"/></h4>
			</div>
			<div id="descripcioPreguntaModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="descripcioPreguntaModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="pregunta.descripcio.modal.button.acceptar"/></button>
			</div>
		</div>
	</div>
</div>

<!-- modal window per mostrar la descripció d'un qüestionari -->
<div id="descripcioQuestionariModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="descripcioQuestionariModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
				<h4 class="modal-title" id="descripcioQuestionariModalLabel"><fmt:message bundle="${missatge}" key="questionari.descripcio.mis.descripcio.questionari"/></h4>
			</div>
			<div id="descripcioQuestionariModalMissatge" class="modal-body"></div>
			<div class="modal-footer">
				<button id="descripcioQuestionariModalBoto" type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${missatge}" key="pregunta.descripcio.modal.button.acceptar"/></button>
			</div>
		</div>
	</div>
</div>