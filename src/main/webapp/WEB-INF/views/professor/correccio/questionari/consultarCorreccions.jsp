<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge" />

<h3><fmt:message bundle="${missatge}" key="consultar.correccio.questionari.titol"/></h3>
<h4><fmt:message bundle="${missatge}" key="consultar.correccio.questionari.desc"/></h4>

<div id="consultaCorreccionsQuestionarisLlistatPendents"></div>
<div id="consultaCorreccionsQuestionarisLlistatCorregits"></div>