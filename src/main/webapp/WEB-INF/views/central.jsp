<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<div id="centralDefault" class="central">
	<p>Missatge per defecte</p>
	<p>En aquest moment, si l'usuari és nou, es veuran indicacions de com moure-se per EGRADUS. Si no és nou, es mostraran les novetats que té, (per exemple si li han assignat alguna pregunta o qüestionari)</p>
</div>

<div id="centralDescripcioAssignatura" class="central">
	<jsp:include page="/WEB-INF/views/assignatura/descripcio.jsp" />
</div>

<div id="centralCreaAssignatura" class="central">
	<!-- <c:import url="/assignatura/creacio"/> -->
	<jsp:include page="/WEB-INF/views/assignatura/creacio.jsp" />
</div>

<div id="centralCercaAssignatura" class="central">
	<!-- <c:import url="/assignatura/cerca"/> -->
	<jsp:include page="/WEB-INF/views/assignatura/cerca.jsp" />
</div>

<div id="centralMaterial" class="central">
	<!-- <c:import url="/assignatura/material"/> -->
	<jsp:include page="/WEB-INF/views/assignatura/material.jsp" />
</div>