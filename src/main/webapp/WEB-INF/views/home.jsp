<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setBundle basename="messages" var="missatge"/>
<fmt:setBundle basename="colorsGrafiques" var="color"/>
<fmt:setBundle basename="tipus_pregunta" var="pregunta"/>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<title><fmt:message bundle="${missatge}" key="home.titol"/></title>
	
	<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
	<link href="<c:url value="/resources/css/bootstrap-datetimepicker.css"/>" rel="stylesheet">
	<link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
	
	<script src="<c:url value="/resources/js/jquery-1.11.0.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/moment.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/bootstrap-datetimepicker.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/locales/bootstrap-datetimepicker.${idioma}.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/Chart.min.js"/>"></script>
	
	<script src="<c:url value="/resources/js/egradus/egradus-utilitats.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/egradus/egradus-assignatures.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/egradus/egradus-repositori.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/egradus/egradus-contestacions.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/egradus/egradus-correccions.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/resources/js/egradus/egradus-estadistiques.js"/>" type="text/javascript"></script>
	
	<!-- Errors produïts a les peticions AJAX dels fitxers .js -->
	<script>
		var ERROR_DESCRIPCIO_ASSIGNATURA ="<fmt:message bundle="${missatge}" key="assignatura.descripcio.ajax.error"/>";
		var ERROR_MATERIAL_ASSIGNATURA = "<fmt:message bundle="${missatge}" key="assignatura.material.ajax.error"/>";
		var ERROR_CERCA_ASSIGNATURA = "<fmt:message bundle="${missatge}" key="assignatura.cerca.ajax.error"/>";
		var ERROR_CREACIO_ASSIGNATURA = "<fmt:message bundle="${missatge}" key="assignatura.creacio.ajax.error"/>";
		var ERROR_UNIO_ASSIGNATURA = "<fmt:message bundle="${missatge}" key="assignatura.unirse.ajax.error"/>";
		
		var ERROR_REPOSITORI_PREGUNTES = "<fmt:message bundle="${missatge}" key="repositori.preguntes.ajax.error"/>";
		var ERROR_REPOSITORI_QUESTIONARIS = "<fmt:message bundle="${missatge}" key="repositori.questionaris.ajax.error"/>";
		
		var ERROR_CREACIO_PREGUNTA = "<fmt:message bundle="${missatge}" key="pregunta.creacio.ajax.error"/>";
		var ERROR_DESCRIPCIO_PREGUNTA = "<fmt:message bundle="${missatge}" key="pregunta.descripcio.ajax.error"/>";
		
		var ERROR_CREACIO_QUESTIONARI = "<fmt:message bundle="${missatge}" key="questionari.creacio.ajax.error"/>";
		var ERROR_DESCRIPCIO_QUESTIONARI = "<fmt:message bundle="${missatge}" key="questionari.descripcio.ajax.error"/>";
		
		var VOF_VERTADER = "<fmt:message bundle="${missatge}" key="pregunta.creacio.opcio.vertader"/>";
	</script>
	
	<!-- Missatges per pintar a les funcions dels fitxers .js -->
	<script>
		
		var TIPUS_PREGUNTA_ES1 = "<fmt:message bundle="${pregunta}" key="abr.escull.una.opcio"/>";
		var TIPUS_PREGUNTA_ESN = "<fmt:message bundle="${pregunta}" key="abr.escull.moltes.opcions"/>";
		var TIPUS_PREGUNTA_VOF = "<fmt:message bundle="${pregunta}" key="abr.vertader.o.fals"/>";
		var TIPUS_PREGUNTA_REC = "<fmt:message bundle="${pregunta}" key="abr.resposta.curta"/>";
		
		var MIS_DILLUNS = "<fmt:message bundle="${missatge}" key="util.temp.dilluns.abr"/>";
		var MIS_DIMARTS = "<fmt:message bundle="${missatge}" key="util.temp.dimarts.abr"/>";
		var MIS_DIMECRES = "<fmt:message bundle="${missatge}" key="util.temp.dimecres.abr"/>";
		var MIS_DIJOUS = "<fmt:message bundle="${missatge}" key="util.temp.dijous.abr"/>";
		var MIS_DIVENDRES = "<fmt:message bundle="${missatge}" key="util.temp.divendres.abr"/>";
		var MIS_DISSABTE = "<fmt:message bundle="${missatge}" key="util.temp.dissabte.abr"/>";
		var MIS_DIUMENGE = "<fmt:message bundle="${missatge}" key="util.temp.diumenge.abr"/>";
		
		var MIS_GENER = "<fmt:message bundle="${missatge}" key="util.temp.gener.abr"/>";
		var MIS_FEBRER = "<fmt:message bundle="${missatge}" key="util.temp.febrer.abr"/>";
		var MIS_MARC = "<fmt:message bundle="${missatge}" key="util.temp.marc.abr"/>";
		var MIS_ABRIL = "<fmt:message bundle="${missatge}" key="util.temp.abril.abr"/>";
		var MIS_MAIG = "<fmt:message bundle="${missatge}" key="util.temp.maig.abr"/>";
		var MIS_JUNY = "<fmt:message bundle="${missatge}" key="util.temp.juny.abr"/>";
		var MIS_JULIOL = "<fmt:message bundle="${missatge}" key="util.temp.juliol.abr"/>";
		var MIS_AGOST = "<fmt:message bundle="${missatge}" key="util.temp.agost.abr"/>";
		var MIS_SETEMBRE = "<fmt:message bundle="${missatge}" key="util.temp.setembre.abr"/>";
		var MIS_OCTUBRE = "<fmt:message bundle="${missatge}" key="util.temp.octubre.abr"/>";
		var MIS_NOVEMBRE = "<fmt:message bundle="${missatge}" key="util.temp.novembre.abr"/>";
		var MIS_DECEMBRE = "<fmt:message bundle="${missatge}" key="util.temp.decembre.abr"/>";
		
		var MIS_CREA_ASS_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="assignatura.creacio.modal.mis1"/>";
		var MIS_CREA_ASS_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="assignatura.creacio.modal.mis2"/>";
		
		var MIS_UNIO_ASS_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="assignatura.descripcio.modal.mis1"/>";
		var MIS_UNIO_ASS_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="assignatura.descripcio.modal.mis2"/>";
		
		var MIS_TAULA_CERCA_CODI = "<fmt:message bundle="${missatge}" key="assignatura.cerca.resultat.codi"/>";
		var MIS_TAULA_CERCA_CODI_REF = "<fmt:message bundle="${missatge}" key="assignatura.cerca.resultat.codi.referencia"/>";
		var MIS_TAULA_CERCA_NOM = "<fmt:message bundle="${missatge}" key="assignatura.cerca.resultat.nom"/>";
		var MIS_TAULA_CERCA_ANY_ACADEMIC = "<fmt:message bundle="${missatge}" key="assignatura.cerca.resultat.any.academic"/>";
		var MIS_TAULA_CERCA_CREADOR = "<fmt:message bundle="${missatge}" key="assignatura.cerca.resultat.creador"/>";
		
		var MIS_INFO_UNIO_CLAU_TOTS = "<fmt:message bundle="${missatge}" key="assignatura.descripcio.mis.clau.profe.i.alumne"/>";
		var MIS_INFO_UNIO_CLAU_PRO = "<fmt:message bundle="${missatge}" key="assignatura.descripcio.mis.clau.profe"/>";
		var MIS_INFO_UNIO_CLAU_ALU = "<fmt:message bundle="${missatge}" key="assignatura.descripcio.mis.clau.alumne"/>";
		
		var MIS_TAULA_REPO_PRE_CODI = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.codi"/>";
		var MIS_TAULA_REPO_PRE_ENUNCIAT = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.enunciat"/>";
		var MIS_TAULA_REPO_PRE_TIPUS = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.tipus"/>";
		var MIS_TAULA_REPO_PRE_CREADOR = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.creador"/>";
		var MIS_TAULA_REPO_PRE_DATA_ALTA = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.data.alta"/>";
		var MIS_TAULA_REPO_PRE_RR = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.raonar.resposta"/>";
		var MIS_TAULA_REPO_PRE_ESTAT = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.estat"/>";
		var MIS_TAULA_REPO_PRE_DT = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.dificultat.teorica"/>";
		var MIS_TAULA_REPO_PRE_DP = "<fmt:message bundle="${missatge}" key="repositori.preguntes.resultat.dificultat.practica"/>";
		var MIS_TAULA_REPO_PRE_TIPUS_ES1 = "<fmt:message bundle="${missatge}" key="pregunta.creacio.select.es1"/>";
		var MIS_TAULA_REPO_PRE_TIPUS_ESN = "<fmt:message bundle="${missatge}" key="pregunta.creacio.select.esn"/>";
		var MIS_TAULA_REPO_PRE_TIPUS_VOF = "<fmt:message bundle="${missatge}" key="pregunta.creacio.select.vof"/>";
		var MIS_TAULA_REPO_PRE_TIPUS_REC = "<fmt:message bundle="${missatge}" key="pregunta.creacio.select.rec"/>";
		var MIS_TAULA_REPO_PRE_ENVIA = "<fmt:message bundle="${missatge}" key="pregunta.creacio.select.envia"/>";
		var MIS_TAULA_REPO_PRE_ACCIONS = "<fmt:message bundle="${missatge}" key="pregunta.creacio.select.accions"/>";
		var MIS_TAULA_REPO_PRE_NO_ALU = "<fmt:message bundle="${missatge}" key="pregunta.creacio.select.envia.no.alumnes"/>";
		var MIS_TAULA_REPO_PRE_PUBLICA = "<fmt:message bundle="${missatge}" key="repositori.preguntes.boto.publica"/>";
		var MIS_TAULA_REPO_PRE_MODIFICA = "<fmt:message bundle="${missatge}" key="repositori.preguntes.boto.modifica"/>";
		var MIS_TAULA_REPO_PRE_ELIMINA = "<fmt:message bundle="${missatge}" key="repositori.preguntes.boto.elimina"/>";
		
		var MIS_PUBLICA_PRE_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicar.mis1"/>";
		var MIS_PUBLICA_PRE_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicar.mis2"/>";
		var MIS_PUBLICA_PRE_MODAL_MIS3 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicar.mis3"/>";
		var MIS_PUBLICA_PRE_MODAL_MIS4 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicar.mis4"/>";
		
		var MIS_PUBLICA_QST_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicar.mis1"/>";
		var MIS_PUBLICA_QST_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicar.mis2"/>";
		var MIS_PUBLICA_QST_MODAL_MIS3 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicar.mis3"/>";
		var MIS_PUBLICA_QST_MODAL_MIS4 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicar.mis4"/>";
		
		var MIS_ELIMINA_PRE_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminar.mis1"/>";
		var MIS_ELIMINA_PRE_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminar.mis2"/>";
		var MIS_ELIMINA_PRE_MODAL_MIS3 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminar.mis3"/>";
		var MIS_ELIMINA_PRE_MODAL_MIS4 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminar.mis4"/>";
		
		var MIS_ELIMINA_QST_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminar.mis1"/>";
		var MIS_ELIMINA_QST_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminar.mis2"/>";
		var MIS_ELIMINA_QST_MODAL_MIS3 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminar.mis3"/>";
		var MIS_ELIMINA_QST_MODAL_MIS4 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminar.mis4"/>";
		
		var MIS_PUBLICADA_PRE_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicada.mis1"/>";
		var MIS_PUBLICADA_PRE_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.publicada.mis2"/>";
		
		var MIS_PUBLICAT_QST_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicat.mis1"/>";
		var MIS_PUBLICAT_QST_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.publicat.mis2"/>";
		
		var MIS_ELIMINADA_PRE_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminada.mis1"/>";
		var MIS_ELIMINADA_PRE_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.preguntes.modal.eliminada.mis2"/>";
		
		var MIS_ELIMINAT_QST_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminat.mis1"/>";
		var MIS_ELIMINAT_QST_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="repositori.questionaris.modal.eliminat.mis2"/>";
		
		var DESCRIPCIO_PREGUNTA_SI_RR = "<fmt:message bundle="${missatge}" key="pregunta.descripcio.mis.si.raonar.resposta"/>";
		var DESCRIPCIO_PREGUNTA_NO_RR = "<fmt:message bundle="${missatge}" key="pregunta.descripcio.mis.no.raonar.resposta"/>";
		
		var CREA_PREGUNTA_AFEGIR_OPCIONS = "<fmt:message bundle="${missatge}" key="pregunta.creacio.button.afegir.opcio"/>";
		var CREA_PREGUNTA_AMAGAR_OPCIONS = "<fmt:message bundle="${missatge}" key="pregunta.creacio.button.amagar.opcio"/>";
		
		var CREA_PREGUNTA_ENUNCIAT_LLARG = "<fmt:message bundle="${missatge}" key="pregunta.creacio.error.enunciat.llarg"/>";
		var CREA_ASSIGNATURA_DESCRIPCIO_LLARGA = "<fmt:message bundle="${missatge}" key="assignatura.creacio.error.descripcio.llarga"/>";
		
		var CREA_PREGUNTA_OPCIO_TEXT = "<fmt:message bundle="${missatge}" key="pregunta.creacio.opcio.text"/>";
		var CREA_PREGUNTA_OPCIO_PLACEHOLDER = "<fmt:message bundle="${missatge}" key="pregunta.creacio.placeholder.opcio.text"/>";
		var CREA_PREGUNTA_OPCIO_CORRECTA = "<fmt:message bundle="${missatge}" key="pregunta.creacio.opcio.correcta"/>";
		
		var CREA_PREGUNTA_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="pregunta.creacio.modal.mis1"/>";
		var CREA_PREGUNTA_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="pregunta.creacio.modal.mis2"/>";
		
		var MODIFICA_PREGUNTA_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="pregunta.modificacio.modal.mis1"/>";
		var MODIFICA_PREGUNTA_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="pregunta.modificacio.modal.mis2"/>";
		
		var MODIFICA_QUESTIONARI_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="questionari.modificacio.modal.mis1"/>";
		var MODIFICA_QUESTIONARI_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="questionari.modificacio.modal.mis2"/>";
		
		var ENVIA_PREGUNTA_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="pregunta.enviament.modal.mis1"/>";
		var ENVIA_PREGUNTA_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="pregunta.enviament.modal.mis2"/>";
		var ENVIA_PREGUNTA_BOTO = "<fmt:message bundle="${missatge}" key="pregunta.enviament.boto"/>";
		
		var ENVIA_QUESTIONARI_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="questionari.enviament.modal.mis1"/>";
		var ENVIA_QUESTIONARI_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="questionari.enviament.modal.mis2"/>";
		var ENVIA_QUESTIONARI_BOTO = "<fmt:message bundle="${missatge}" key="questionari.enviament.boto"/>";
		
		var CNT_PREGUNTA_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="pregunta.contestacio.modal.mis1"/>";
		var CNT_PREGUNTA_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="pregunta.contestacio.modal.mis2"/>";
		
		var CNT_QUESTIONARI_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="questionari.contestacio.modal.mis1"/>";
		var CNT_QUESTIONARI_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="questionari.contestacio.modal.mis2"/>";
		
		var CRR_PREGUNTA_TITOL = "<fmt:message bundle="${missatge}" key="pregunta.correccio.titol"/>";
		var CRR_PREGUNTA_TITOL_DESC = "<fmt:message bundle="${missatge}" key="pregunta.correccio.desc"/>";
		var CRR_PREGUNTA_BOTO = "<fmt:message bundle="${missatge}" key="pregunta.correccio.boto.corregeix.pregunta"/>";
		var REV_PREGUNTA_TITOL = "<fmt:message bundle="${missatge}" key="pregunta.revisio.titol"/>";
		var REV_PREGUNTA_TITOL_DESC = "<fmt:message bundle="${missatge}" key="pregunta.revisio.desc"/>";
		var REV_PREGUNTA_BOTO = "<fmt:message bundle="${missatge}" key="pregunta.revisio.boto.revisa.pregunta"/>";
		
		var CRR_QUESTIONARI_TITOL = "<fmt:message bundle="${missatge}" key="questionari.correccio.titol"/>";
		var CRR_QUESTIONARI_TITOL_DESC = "<fmt:message bundle="${missatge}" key="questionari.correccio.desc"/>";
		var CRR_QUESTIONARI_BOTO = "<fmt:message bundle="${missatge}" key="questionari.correccio.boto.corregeix.questionari"/>";
		var REV_QUESTIONARI_TITOL = "<fmt:message bundle="${missatge}" key="questionari.revisio.titol"/>";
		var REV_QUESTIONARI_TITOL_DESC = "<fmt:message bundle="${missatge}" key="questionari.revisio.desc"/>";
		var REV_QUESTIONARI_BOTO = "<fmt:message bundle="${missatge}" key="questionari.revisio.boto.revisa.questionari"/>";
		
		var CRR_PREGUNTA_CRR = "<fmt:message bundle="${missatge}" key="pregunta.correccio.correccio"/>";
		var CRR_PREGUNTA_REV = "<fmt:message bundle="${missatge}" key="pregunta.correccio.revisio"/>";
		var CRR_PREGUNTA_NOTA = "<fmt:message bundle="${missatge}" key="pregunta.correccio.nota"/>";
		var CRR_PREGUNTA_CRR_PLACEHOLDER = "<fmt:message bundle="${missatge}" key="pregunta.correccio.placeholder.escriu.resposta"/>";
		var CRR_PREGUNTA_REV_PLACEHOLDER = "<fmt:message bundle="${missatge}" key="pregunta.correccio.placeholder.escriu.revisio"/>";
		var CRR_PREGUNTA_NOTA_PLACEHOLDER = "<fmt:message bundle="${missatge}" key="pregunta.correccio.placeholder.escriu.nota"/>";
		
		var CRR_PREGUNTA_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="pregunta.correccio.modal.mis1"/>";
		var CRR_PREGUNTA_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="pregunta.correccio.modal.mis2"/>";
		var CRR_PREGUNTA_MODAL_TITOL = "<fmt:message bundle="${missatge}" key="pregunta.correccio.modal.titol"/>";
		var REV_PREGUNTA_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="pregunta.revisio.modal.mis1"/>";
		var REV_PREGUNTA_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="pregunta.revisio.modal.mis2"/>";
		var REV_PREGUNTA_MODAL_TITOL = "<fmt:message bundle="${missatge}" key="pregunta.revisio.modal.titol"/>";
		
		var CRR_QUESTIONARI_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="questionari.correccio.modal.mis1"/>";
		var CRR_QUESTIONARI_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="questionari.correccio.modal.mis2"/>";
		var CRR_QUESTIONARI_MODAL_TITOL = "<fmt:message bundle="${missatge}" key="questionari.correccio.modal.titol"/>";
		var REV_QUESTIONARI_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="questionari.revisio.modal.mis1"/>";
		var REV_QUESTIONARI_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="questionari.revisio.modal.mis2"/>";
		var REV_QUESTIONARI_MODAL_TITOL = "<fmt:message bundle="${missatge}" key="questionari.revisio.modal.titol"/>";
		
		var MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="questionari.creacio.afegir.preguntes.row.dif.teo"/>";
		var MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="questionari.creacio.afegir.preguntes.row.dif.pra"/>";
		var MIS_QST_AFEGIR_PREGUNTES_ROW_CREADOR = "<fmt:message bundle="${missatge}" key="questionari.creacio.afegir.preguntes.row.creador"/>";
		var MIS_QST_AFEGIR_PREGUNTES_ROW_TIPUS = "<fmt:message bundle="${missatge}" key="questionari.creacio.afegir.preguntes.row.tipus"/>";
		var MIS_QST_AFEGIR_PREGUNTES_ROW_CREADA = "<fmt:message bundle="${missatge}" key="questionari.creacio.afegir.preguntes.row.creada"/>";
		var MIS_QST_AFEGIR_PREGUNTES_PRE_AFEGIDA = "<fmt:message bundle="${missatge}" key="questionari.creacio.afegir.preguntes.pregunta.afegida"/>";
		
		var MIS_PRE_PENDENTS_CONTESTAR = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.contestar"/>";
		var MIS_PRE_PENDENTS_CONTESTAR_BUIT = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.contestar.buit"/>";
		var MIS_PRE_PENDENTS_CONTESTAR_ROW_ASSIGNADA_PER = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.row.assignada.per"/>";
		var MIS_PRE_PENDENTS_CONTESTAR_ROW_TIPUS = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.row.tipus"/>";
		var MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.row.dif.teo"/>";
		var MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.row.dif.pra"/>";
		var MIS_PRE_PENDENTS_CONTESTAR_ROW_REBUDA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.row.rebuda"/>";
		var MIS_PRE_PENDENTS_SER_CRR = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.ser.corregides"/>";
		var MIS_PRE_PENDENTS_SER_REV = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.ser.revisades"/>";
		
		var MIS_PRE_ANONIMA = "<fmt:message bundle="${missatge}" key="pregunta.enviament.anonima.checkbox"/>";
		var MIS_QST_ANONIM = "<fmt:message bundle="${missatge}" key="questionari.enviament.anonim.checkbox"/>";
		var MIS_ALU_ANONIM = "<fmt:message bundle="${missatge}" key="pregunta.enviament.alumne.anonim"/>";
		
		var MIS_QST_PENDENTS_CONTESTAR = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.contestar"/>";
		var MIS_QST_PENDENTS_CONTESTAR_BUIT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.contestar.buit"/>";
		var MIS_QST_PENDENTS_CONTESTAR_ROW_ASSIGNAT_PER = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.row.assignat.per"/>";
		var MIS_QST_PENDENTS_CONTESTAR_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.row.dif.teo"/>";
		var MIS_QST_PENDENTS_CONTESTAR_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.row.dif.pra"/>";
		var MIS_QST_PENDENTS_CONTESTAR_ROW_REBUT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.row.rebut"/>";
		var MIS_QST_PENDENTS_SER_CRR = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.ser.corregits"/>";
		var MIS_QST_PENDENTS_SER_REV = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.ser.revisats"/>";
		
		var MIS_PRE_CONTESTADES = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades"/>";
		var MIS_PRE_CONTESTADES_BUIT = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.buit"/>";
		var MIS_PRE_CONTESTADES_ROW_ASSIGNADA_PER = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.assignada.per"/>";
		var MIS_PRE_CONTESTADES_ROW_TIPUS = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.tipus"/>";
		var MIS_PRE_CONTESTADES_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.dif.teo"/>";
		var MIS_PRE_CONTESTADES_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.dif.pra"/>";
		var MIS_PRE_CONTESTADES_ROW_CONTESTADA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.contestada"/>";
		var MIS_PRE_CONTESTADES_ROW_CORREGIDA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.corregida"/>";
		var MIS_PRE_CONTESTADES_ROW_CORREGIDA_AUT = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.corregida.automaticament"/>";
		var MIS_PRE_CONTESTADES_ROW_REVISADA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.contestades.row.revisada"/>";
		
		var MIS_QST_CONTESTATS = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats"/>";
		var MIS_QST_CONTESTATS_BUIT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.buit"/>";
		var MIS_QST_CONTESTATS_ROW_ASSIGNAT_PER = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.row.assignat.per"/>";
		var MIS_QST_CONTESTATS_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.row.dif.teo"/>";
		var MIS_QST_CONTESTATS_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.row.dif.pra"/>";
		var MIS_QST_CONTESTATS_ROW_CONTESTAT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.row.contestat"/>";
		var MIS_QST_CONTESTATS_ROW_CORREGIT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.row.corregit"/>";
		var MIS_QST_CONTESTATS_ROW_CORREGIT_AUT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.row.corregit.automaticament"/>";
		var MIS_QST_CONTESTATS_ROW_REVISAT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.contestats.row.revisat"/>";
		
		var MIS_PRE_PENDENTS_CRR_O_REV = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.corregir"/>";
		var MIS_PRE_PENDENTS_CRR_O_REV_BUIT = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.corregir.buit"/>";
		var MIS_PRE_PENDENTS_CORREGIR_ROW_CONTESTADA_PER = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.corregir.row.contestada.per"/>";
		var MIS_PRE_PENDENTS_CORREGIR_ROW_TIPUS = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.corregir.row.tipus"/>";
		var MIS_PRE_PENDENTS_CORREGIR_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.corregir.row.dif.teo"/>";
		var MIS_PRE_PENDENTS_CORREGIR_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.corregir.row.dif.pra"/>";
		var MIS_PRE_PENDENTS_CORREGIR_ROW_REBUDA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.pendents.corregir.row.rebuda"/>";
		
		var MIS_QST_PENDENTS_CRR_O_REV = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.corregir"/>";
		var MIS_QST_PENDENTS_CRR_O_REV_BUIT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.corregir.buit"/>";
		var MIS_QST_PENDENTS_CORREGIR_ROW_CONTESTAT_PER = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.corregir.row.contestat.per"/>";
		var MIS_QST_PENDENTS_CORREGIR_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.corregir.row.dif.teo"/>";
		var MIS_QST_PENDENTS_CORREGIR_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.corregir.row.dif.pra"/>";
		var MIS_QST_PENDENTS_CORREGIR_ROW_REBUT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.pendents.corregir.row.rebut"/>";
		
		var MIS_PRE_CRR_O_REV = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.corregides"/>";
		var MIS_PRE_CRR_O_REV_BUIT = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.corregides.buit"/>";
		var MIS_PRE_CORREGIDES_ROW_CONTESTADA_PER = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.corregides.row.contestada.per"/>";
		var MIS_PRE_CORREGIDES_ROW_TIPUS = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.corregides.row.tipus"/>";
		var MIS_PRE_CORREGIDES_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.corregides.row.dif.teo"/>";
		var MIS_PRE_CORREGIDES_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.corregides.row.dif.pra"/>";
		var MIS_PRE_CORREGIDES_ROW_CORREGIDA = "<fmt:message bundle="${missatge}" key="pregunta.material.panel.corregides.row.corregida"/>";
		
		var MIS_QST_CRR_O_REV = "<fmt:message bundle="${missatge}" key="questionari.material.panel.corregits"/>";
		var MIS_QST_CRR_O_REV_BUIT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.corregits.buit"/>";
		var MIS_QST_CORREGITS_ROW_CONTESTAT_PER = "<fmt:message bundle="${missatge}" key="questionari.material.panel.corregits.row.contestat.per"/>";
		var MIS_QST_CORREGITS_ROW_DIF_TEO = "<fmt:message bundle="${missatge}" key="questionari.material.panel.corregits.row.dif.teo"/>";
		var MIS_QST_CORREGITS_ROW_DIF_PRA = "<fmt:message bundle="${missatge}" key="questionari.material.panel.corregits.row.dif.pra"/>";
		var MIS_QST_CORREGITS_ROW_CORREGIT = "<fmt:message bundle="${missatge}" key="questionari.material.panel.corregits.row.corregit"/>";
		
		var MIS_PRE_CONS_RESPOSTA_DATFIN = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.datfin"/>";
		var MIS_PRE_CONS_RESPOSTA_CORREGIDA = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.data.correccio"/>";
		var MIS_PRE_CONS_RESPOSTA_REVISADA = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.data.revisio"/>";
		var MIS_PRE_CONS_RESPOSTA_TEMPS = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.temps"/>";
		var MIS_PRE_CONS_RESPOSTA_RESP_REC = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.resp.rec"/>";
		var MIS_PRE_CONS_RESPOSTA_REV = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.text.revisio"/>";
		var MIS_PRE_CONS_RESPOSTA_RESP_RR = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.resp.rr"/>";
		var MIS_PRE_CONS_RESPOSTA_NOTA = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.nota"/>";
		var MIS_PRE_CONS_RESPOSTA_NOTA_REV = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.nota.revisada"/>";
		var MIS_PRE_CONS_RESPOSTA_CRR_REC = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.text.correccio"/>";
		var MIS_PRE_CONS_RESPOSTA_ASSIG = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.assignada.per"/>";
		var MIS_PRE_CONS_RESPOSTA_ALUMNE = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.contestada.per"/>";
		var MIS_PRE_CONS_CRR_AUTOMATICA = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.correccio.automatica"/>";
		var MIS_PRE_CONS_PENDENT_CORRECCIO = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.pendent.correccio"/>";
		var MIS_PRE_CONS_PENDENT_REVISIO = "<fmt:message bundle="${missatge}" key="pregunta.material.consulta.resposta.pendent.revisio"/>";
		
		var MIS_QST_CONS_RESPOSTA_DATFIN = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.datfin"/>";
		var MIS_QST_CONS_RESPOSTA_TEMPS = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.temps"/>";
		var MIS_QST_CONS_RESPOSTA_ASSIG = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.assignat.per"/>";
		var MIS_QST_CONS_RESPOSTA_ALUMNE = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.contestat.per"/>";
		var MIS_QST_CONS_RESPOSTA_CORREGIDA = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.data.correccio"/>";
		var MIS_QST_CONS_RESPOSTA_REVISADA = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.data.revisio"/>";
		var MIS_QST_CONS_CRR_AUTOMATICA = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.correccio.automatica"/>";
		var MIS_QST_CONS_CRR_MANUAL_PENDENT = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.correccio.manual.pendent"/>";
		var MIS_QST_CONS_CRR_MANUAL_CORREGIDA = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.correccio.manual.corregida"/>";
		var MIS_QST_CONS_CRR_MANUAL_PENDENT_PROFE_ACTUAL = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.correccio.manual.pendent.profe.actual"/>";
		var MIS_QST_CONS_RESPOSTA_NOTA = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.nota"/>";
		var MIS_QST_CONS_RESPOSTA_NOTA_REV = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.nota.revisada"/>";
		var MIS_QST_CONS_PENDENT_CORRECCIO = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.pendent.correccio"/>";
		var MIS_QST_CONS_PENDENT_REVISIO = "<fmt:message bundle="${missatge}" key="questionari.material.consulta.resposta.pendent.revisio"/>";
		
		var MIS_PRE_INI_CNT_DATA = "<fmt:message bundle="${missatge}" key="pregunta.material.inici.contestacio.data"/>";
		var MIS_PRE_INI_CNT_REC = "<fmt:message bundle="${missatge}" key="pregunta.material.inici.contestacio.rec"/>";
		var MIS_PRE_INI_CNT_RR = "<fmt:message bundle="${missatge}" key="pregunta.material.inici.contestacio.rr"/>";
		var MIS_PRE_INI_CNT_REC_PLH = "<fmt:message bundle="${missatge}" key="pregunta.material.inici.contestacio.rec.placeholder"/>";
		var MIS_PRE_INI_CNT_RR_PLH = "<fmt:message bundle="${missatge}" key="pregunta.material.inici.contestacio.rr.placeholder"/>";
		
		var MIS_QST_INI_CNT_DATA = "<fmt:message bundle="${missatge}" key="questionari.material.inici.contestacio.data"/>";
		var MIS_QST_INI_CNT_PREGUNTA = "<fmt:message bundle="${missatge}" key="questionari.material.inici.contestacio.nom.pregunta"/>";
		var MIS_QST_INI_CNT_PREGUNTA_PES = "<fmt:message bundle="${missatge}" key="questionari.material.inici.contestacio.pes.pregunta"/>";
		
		var MIS_PRE_CRR_TEXT_LLARG = "<fmt:message bundle="${missatge}" key="pregunta.correccio.error.text.correccio.llarg"/>";
		var MIS_PRE_REV_TEXT_LLARG = "<fmt:message bundle="${missatge}" key="pregunta.revisio.error.text.revisio.llarg"/>";
		
		var MIS_QST_REV_TEXT_LLARG = "<fmt:message bundle="${missatge}" key="questionari.revisio.error.text.revisio.llarg"/>";
		
		var CREA_QUESTIONARI_DESCRIPCIO_LLARGA = "<fmt:message bundle="${missatge}" key="questionari.creacio.error.descripcio.llarga"/>";
		var CREA_QUESTIONARI_MODAL_MIS1 = "<fmt:message bundle="${missatge}" key="questionari.creacio.modal.mis1"/>";
		var CREA_QUESTIONARI_MODAL_MIS2 = "<fmt:message bundle="${missatge}" key="questionari.creacio.modal.mis2"/>";
		var CREA_QUESTIONARI_DSV_PRE_TOOLTIP = "<fmt:message bundle="${missatge}" key="questionari.creacio.tooltip.desvincular.pregunta"/>";
		var CREA_QUESTIONARI_PES_PREGUNTA = "<fmt:message bundle="${missatge}" key="questionari.creacio.pes.pregunta"/>";
		var CREA_QUESTIONARI_PLACEHOLDER_PES = "<fmt:message bundle="${missatge}" key="questionari.creacio.placeholder.pes.pregunta"/>";
		
		var MIS_TAULA_REPO_QST_NO_ALU = "<fmt:message bundle="${missatge}" key="questionari.creacio.select.envia.no.alumnes"/>";
		var MIS_TAULA_REPO_QST_CODI = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.codi"/>";
		var MIS_TAULA_REPO_QST_NOM = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.nom"/>";
		var MIS_TAULA_REPO_QST_DESCRIPCIO = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.descripcio"/>";
		var MIS_TAULA_REPO_QST_CREADOR = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.creador"/>";
		var MIS_TAULA_REPO_QST_DATA_ALTA = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.data.alta"/>";
		var MIS_TAULA_REPO_QST_ESTAT = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.estat"/>";
		var MIS_TAULA_REPO_QST_DT = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.dificultat.teorica"/>";
		var MIS_TAULA_REPO_QST_DP = "<fmt:message bundle="${missatge}" key="repositori.questionaris.resultat.dificultat.practica"/>";
		var MIS_TAULA_REPO_QST_ENVIA = "<fmt:message bundle="${missatge}" key="questionari.creacio.select.envia"/>";
		var MIS_TAULA_REPO_QST_ACCIONS = "<fmt:message bundle="${missatge}" key="questionari.creacio.select.accions"/>";
		var MIS_TAULA_REPO_QST_PUBLICA = "<fmt:message bundle="${missatge}" key="repositori.questionaris.boto.publica"/>";
		var MIS_TAULA_REPO_QST_MODIFICA = "<fmt:message bundle="${missatge}" key="repositori.questionaris.boto.modifica"/>";
		var MIS_TAULA_REPO_QST_ELIMINA = "<fmt:message bundle="${missatge}" key="repositori.questionaris.boto.elimina"/>";
		
		var EST_ALU_NO_CNT = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.no.contestacions"/>";
		var EST_ALU_PRE_NO_CNT_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.preguntes.no.contestacions.desc"/>";
		var EST_ALU_QST_NO_CNT_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.no.contestacions.desc"/>";
		var EST_ALU_LEGEND_TAULA = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.taula"/>";
		var EST_ALU_LEGEND_GRAF_TEMPS_RESP = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.temps.resposta"/>";
		var EST_ALU_LEGEND_GRAF_NOTES = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.notes"/>";
		var EST_ALU_LEGEND_GRAF_TEMPS_RESP_MITJOS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.temps.resposta.mitjos"/>";
		var EST_ALU_LEGEND_GRAF_NOTES_MITGES = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.notes.mitges"/>";
		var EST_ALU_TAULA_DATA_CNT = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.data.contestacio"/>";
		var EST_ALU_TAULA_DATA_CRR = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.data.correccio"/>";
		var EST_ALU_TAULA_NOTA = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.nota"/>";
		var EST_ALU_TAULA_NOTA_MITJA_ASS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.nota.mitja.assignatura"/>";
		var EST_ALU_TAULA_NOTA_MITJA_EGR = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.nota.mitja.egradus"/>";
		var EST_ALU_TAULA_TEMPS_RESP = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.temps.resposta"/>";
		var EST_ALU_TAULA_TEMPS_RESP_MIG_ASS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.temps.resposta.mig.assignatura"/>";
		var EST_ALU_TAULA_TEMPS_RESP_MIG_EGR = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.header.temps.resposta.mig.egradus"/>";
		var EST_ALU_TAULA_PES = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.taula.header.pes"/>";
		var EST_ALU_TAULA_PES_X_NOTA = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.taula.header.pes.x.nota"/>";
		var EST_ALU_TAULA_PES_X_NOTA_MITJA_ASS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.taula.header.pes.x.nota.mitja.assignatura"/>";
		var EST_ALU_TAULA_PES_X_NOTA_MITJA_EGR = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.taula.header.pes.x.nota.mitja.egradus"/>";
		
		var EST_ALU_PRE_INFO = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.info.pregunta"/>";
		var EST_ALU_QST_IND_INFO = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.info.questionari.individual"/>";
		var EST_ALU_QST_INFO = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.taula.info.questionari"/>";
		var EST_CJT_ALU_PRE_INFO = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.taula.info.pregunta"/>";
		var EST_CJT_ALU_QST_IND_INFO = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.taula.info.questionari.individual"/>";
		var EST_CJT_ALU_QST_INFO = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.taula.info.questionari"/>";
			
		var EST_ALU_LEGEND_GRAFICA_NOTES = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.grafica.notes"/>";
		var EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_ASS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.grafica.notes.mitjes.assignatura"/>";
		var EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_EGR = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.grafica.notes.mitjes.egradus"/>";
		var EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.grafica.temps.resposta"/>";
		var EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_ASS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.grafica.temps.resposta.mig.assignatura"/>";
		var EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_EGR = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.legend.grafica.temps.resposta.mig.egradus"/>";
		
		var EST_ALU_QST_LEGEND_SELECT_LABEL = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.taula.legend.select.label"/>";
		var EST_ALU_QST_LEGEND_TAULA_IND = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.taula.legend.taula.individual"/>";
		var EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.grafica.llegenda.pesos"/>";
		var EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.grafica.llegenda.pesos.x.notes"/>";
		var EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES_MITGES_ASS = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.grafica.llegenda.pesos.x.notes.mitges.assignatura"/>";
		var EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES_MITGES_EGR = "<fmt:message bundle="${missatge}" key="estadistiques.alumne.questionaris.grafica.llegenda.pesos.x.notes.mitges.egradus"/>";
		
		var EST_GRAFICA_LLEGENDA = "<fmt:message bundle="${missatge}" key="estadistiques.llegenda"/>";
		
		var EST_PRO_TAULA_NUM_CONTESTACIONS = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.conjunt.taula.header.num.contestacions"/>";
		var EST_PRO_TAULA_DETALL = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.conjunt.taula.header.detall"/>";
		
		var EST_PRO_NO_PRE = "<fmt:message bundle="${missatge}" key="estadistiques.professor.no.preguntes"/>";
		var EST_PRO_NO_PRE_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.professor.no.preguntes.desc"/>";
		
		var EST_PRO_NO_QST = "<fmt:message bundle="${missatge}" key="estadistiques.professor.no.questionaris"/>";
		var EST_PRO_NO_QST_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.professor.no.questionaris.desc"/>";
		
		var EST_PRO_ALU_NO_CNT = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.alumne.no.contestacions"/>";
		var EST_PRO_ALU_PRE_NO_CNT_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.alumne.preguntes.no.contestacions.desc"/>";
		var EST_PRO_ALU_QST_NO_CNT_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.alumne.questionaris.no.contestacions.desc"/>";
		
		var EST_PRO_CONJUNT_ALU_NO_CNT = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.conjunt.no.contestacions"/>";
		var EST_PRO_CONJUNT_ALU_PRE_NO_CNT_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.conjunt.preguntes.no.contestacions.desc"/>";
		var EST_PRO_CONJUNT_ALU_QST_NO_CNT_DESC = "<fmt:message bundle="${missatge}" key="estadistiques.professor.alumnes.conjunt.questionaris.no.contestacions.desc"/>";
		
		var BLANC = "<fmt:message bundle="${color}" key="blanc"/>";
		var BLAU_PRIM = "<fmt:message bundle="${color}" key="blau"/>";
		var BLAU_FORT = "<fmt:message bundle="${color}" key="blau.fort"/>";
		var BLAU_FONS = "<fmt:message bundle="${color}" key="blau.fons"/>";
		var GRIS_PRIM = "<fmt:message bundle="${color}" key="gris"/>";
		var GRIS_FORT = "<fmt:message bundle="${color}" key="gris.fort"/>";
		var GRIS_FONS = "<fmt:message bundle="${color}" key="gris.fons"/>";
		var VERD_PRIM = "<fmt:message bundle="${color}" key="verd"/>";
		var VERD_FORT = "<fmt:message bundle="${color}" key="verd.fort"/>";
		var VERD_FONS = "<fmt:message bundle="${color}" key="verd.fons"/>";
		var VERMELL_PRIM = "<fmt:message bundle="${color}" key="vermell"/>";
		var VERMELL_FORT = "<fmt:message bundle="${color}" key="vermell.fort"/>";
		var VERMELL_FONS = "<fmt:message bundle="${color}" key="vermell.fons"/>";
		var GROC_PRIM = "<fmt:message bundle="${color}" key="groc"/>";
		var GROC_FORT = "<fmt:message bundle="${color}" key="groc.fort"/>";
		var GROC_FONS = "<fmt:message bundle="${color}" key="groc.fons"/>";
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/capcalera.jsp"/>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-sm-3 col-md-2 col-lg-2 sidebar">
				<c:import url="/assignatura/llista"/>
			</div>
			<div class="col-sm-9 col-md-10 col-lg-9">
				<jsp:include page="/WEB-INF/views/central.jsp"/>
			</div>
		</div>
	</div>
</body>
</html>