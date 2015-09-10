<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<div id="centralDefault" class="central">
	<p><fmt:message bundle="${missatge}" key="home.missatge.defecte"/></p>
	<p><fmt:message bundle="${missatge}" key="home.missatge.defecte.desc"/></p>
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