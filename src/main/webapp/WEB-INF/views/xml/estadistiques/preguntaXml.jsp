<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<estadistiquesPregunta>
	<assignacioDirecta>
		<assignatura>
			<rebudes>${estDir.preguntaEnviadesAss}</rebudes>
			<contestades>${estDir.preguntaContestadesAss}</contestades>
			<corregides>${estDir.preguntaCorregidesAss}</corregides>
			<aprovades>${estDir.preguntaAprovadesAss}</aprovades>
			<notaMitja>${estDir.preguntaNotaMitjaAss}</notaMitja>
			<tempsRespostaMig>${estDir.preguntaTempsRespostaMigAss}</tempsRespostaMig>
		</assignatura>
		<egradus>
			<rebudes>${estDir.preguntaEnviadesEgr}</rebudes>
			<contestades>${estDir.preguntaContestadesEgr}</contestades>
			<corregides>${estDir.preguntaCorregidesEgr}</corregides>
			<aprovades>${estDir.preguntaAprovadesEgr}</aprovades>
			<notaMitja>${estDir.preguntaNotaMitjaEgr}</notaMitja>
			<tempsRespostaMig>${estDir.preguntaTempsRespostaMigEgr}</tempsRespostaMig>
		</egradus>
	</assignacioDirecta>
	<assignacioQuestionari>
		<assignatura>
			<rebudes>${estQst.preguntaEnviadesAss}</rebudes>
			<contestades>${estQst.preguntaContestadesAss}</contestades>
			<corregides>${estQst.preguntaCorregidesAss}</corregides>
			<aprovades>${estQst.preguntaAprovadesAss}</aprovades>
			<notaMitja>${estQst.preguntaNotaMitjaAss}</notaMitja>
			<tempsRespostaMig>${estQst.preguntaTempsRespostaMigAss}</tempsRespostaMig>
		</assignatura>
		<egradus>
			<rebudes>${estQst.preguntaEnviadesEgr}</rebudes>
			<contestades>${estQst.preguntaContestadesEgr}</contestades>
			<corregides>${estQst.preguntaCorregidesEgr}</corregides>
			<aprovades>${estQst.preguntaAprovadesEgr}</aprovades>
			<notaMitja>${estQst.preguntaNotaMitjaEgr}</notaMitja>
			<tempsRespostaMig>${estQst.preguntaTempsRespostaMigEgr}</tempsRespostaMig>
		</egradus>
	</assignacioQuestionari>
</estadistiquesPregunta>