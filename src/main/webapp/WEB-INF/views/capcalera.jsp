<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<div class="navbar navbar-inverse navbar-static-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/egradus"><fmt:message bundle="${missatge}" key="capcalera.mis.capcalera"/></a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li class="capcalera-item">${sessionScope.data}</li>
				<li class="capcalera-item">${sessionScope.persona.alies} (${sessionScope.persona.nom} ${sessionScope.persona.primerLlinatge} ${sessionScope.persona.segonLlinatge})</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message bundle="${missatge}" key="capcalera.lang.idioma"/><b class="caret"></b>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/canviaIdioma"/>?idioma=ca"><img src="<c:url value="/resources/img/flag_ca.png"/>" alt="catala" style="width:20px;height:20px"> <fmt:message bundle="${missatge}" key="capcalera.lang.catala"/></a></li>
							<li><a href="<c:url value="/canviaIdioma"/>?idioma=es"><img src="<c:url value="/resources/img/flag_es.png"/>" alt="castella" style="width:20px;height:20px"> <fmt:message bundle="${missatge}" key="capcalera.lang.castella"/></a></li>
							<li><a href="<c:url value="/canviaIdioma"/>?idioma=en"><img src="<c:url value="/resources/img/flag_en.png"/>" alt="angles" style="width:20px;height:20px"> <fmt:message bundle="${missatge}" key="capcalera.lang.angles"/></a></li>
						</ul>
					</a>
				</li>
				<li><a href="<c:url value="/logout" />"><fmt:message bundle="${missatge}" key="capcalera.tancasessio"/></a></li>
			</ul>
		</div>
	</div>
</div>