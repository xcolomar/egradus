<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<alumnes>
	<c:forEach items="${alumnes}" var="alu">
		<alumne codi="${alu.codi}" nom="${alu.persona.nom}" llinatge1="${alu.persona.primerLlinatge}" llinatge2="${alu.persona.segonLlinatge}" alies="${alu.persona.alies}"/>
	</c:forEach>
</alumnes>