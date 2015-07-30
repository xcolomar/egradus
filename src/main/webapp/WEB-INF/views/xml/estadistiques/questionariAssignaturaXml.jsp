<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<estadistiquesQuestionariAssignatura>
	<rebuts>${est.alumnesMigQuestionariRebut}</rebuts>
	<contestats>${est.alumnesMigQuestionariContestat}</contestats>
	<corregits>${est.alumnesMigQuestionariCorregit}</corregits>
	<aprovats>${est.alumnesMigQuestionariAprovat}</aprovats>
	<notaMitja>${est.assignaturaNotaMitja}</notaMitja>
	<tempsRespostaMig>${est.assignaturaTempsRespostaMig}</tempsRespostaMig>
	<dificultatTeorica>${est.assignaturaDificultatTeorica}</dificultatTeorica>
	<dificultatPractica>${est.assignaturaDificultatPractica}</dificultatPractica>
</estadistiquesQuestionariAssignatura>