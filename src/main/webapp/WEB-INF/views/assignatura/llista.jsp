<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<ul class="nav nav-sidebar" id="llistaAssignatures">
	<li class="sidebar-brand">
		<h4 style="display:inline;"><fmt:message bundle="${missatge}" key="lateral.llista.assignatures"/></h4>
		<a style="display:inline;" href="javascript: habilitaCerca();"><span class="glyphicon glyphicon-search"></span></a>
		<a style="display:inline;" href="javascript: habilitaCreacio();"><span class="glyphicon glyphicon-plus"></span></a>
	</li>
	<c:forEach items="${assignatures}" var="ass">
		<li id="assignatura${ass.codi}" class="sidebar-brand egradus-llistat-assignatures">
			<a href="javascript: habilitaMaterial(${ass.codi});">${ass.nom}</a>
		</li>
	</c:forEach>
</ul>