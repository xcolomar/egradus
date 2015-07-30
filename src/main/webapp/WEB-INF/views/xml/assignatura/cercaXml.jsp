<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<assignatures>
	<c:forEach items="${assignatures}" var="ass">
		<assignatura codiReferencia="${ass.codiReferencia}" codi="${ass.codi}" anyAcademic="${ass.anyAcademic}" nom="${ass.nom}">
			<creador nom="${ass.creador.nom}" llinatge1="${ass.creador.primerLlinatge}" llinatge2="${ass.creador.segonLlinatge}" alies="${ass.creador.alies}"/>
			<descripcio>${ass.descripcio}</descripcio>
		</assignatura>
	</c:forEach>
</assignatures>