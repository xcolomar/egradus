<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<estadistiquesQuestionari>
	<assignatura>
		<rebuts>${est.questionariEnviatsAss}</rebuts>
		<contestats>${est.questionariContestatsAss}</contestats>
		<corregits>${est.questionariCorregitsAss}</corregits>
		<aprovats>${est.questionariAprovatsAss}</aprovats>
		<notaMitja>${est.questionariNotaMitjaAss}</notaMitja>
		<tempsRespostaMig>${est.questionariTempsRespostaMigAss}</tempsRespostaMig>
	</assignatura>
	<egradus>
		<rebuts>${est.questionariEnviatsEgr}</rebuts>
		<contestats>${est.questionariContestatsEgr}</contestats>
		<corregits>${est.questionariCorregitsEgr}</corregits>
		<aprovats>${est.questionariAprovatsEgr}</aprovats>
		<notaMitja>${est.questionariNotaMitjaEgr}</notaMitja>
		<tempsRespostaMig>${est.questionariTempsRespostaMigEgr}</tempsRespostaMig>
	</egradus>
</estadistiquesQuestionari>