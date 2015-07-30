/*
 * egradus-assignatures.js
 * ----------------------------------------------------------------------------------------
 * 
 * Funcionalitats de les assignatures, com ara:
 * 1) Crear una nova assignatura
 * 2) Cercar una assignatura existent
 * 3) Consultar la descripció d'una assignatura
 * 4) Unir-se com alumne o com professor a una assignatura existent
 */

/**
 * habilita vista de cerca d'assignatura
 */
function habilitaCerca(){
	// ocultam totes les vistes del panell central i mostram la vista per cercar assignatures
	$(".central").hide();
	$("#centralCercaAssignatura").show();
	
	// esborram el color de fons de totes les assignatures, per donar a entendre que estam
	// fora de l'àmbit de cap assignatura en concret: estam cercant assignatures
	$('.egradus-llistat-assignatures').removeClass("egradus-color-llista");
	
	$.ajax({
        url: "/egradus/assignatura/cercaAssignatures.xml", // si vull paràmetres, afegir "?" + values
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	$("#cercaAssignaturaTaulaResultats").empty(); // esborram el contingut abans de cada cerca
        	$("#cercaAssignaturaTaulaResultats").append("<thead><tr><th>" + MIS_TAULA_CERCA_CODI + "</th><th>" + MIS_TAULA_CERCA_CODI_REF + "</th><th>" + MIS_TAULA_CERCA_NOM + "</th><th>" + MIS_TAULA_CERCA_ANY_ACADEMIC + "</th><th>" + MIS_TAULA_CERCA_CREADOR + "</th></tr></thead>");
        	ompleCerca(xml);
        },
        error:function(){
            alert(ERROR_REPOSITORI_PREGUNTES);
        }
    });
}

/**
 * habilita vista de creació d'assignatura
 */
function habilitaCreacio(){
	// ocultam totes les vistes del panell central i mostram la vista per crear assignatures
	$(".central").hide();
	$("#centralCreaAssignatura").show();
	
	// esborram el color de fons de totes les assignatures, per donar a entendre que estam
	// fora de l'àmbit de cap assignatura en concret: estam creant una nova assignatura
	$('.egradus-llistat-assignatures').removeClass("egradus-color-llista");
	
	// neteja el possible error que hi hagi hagut anteriorment
	// en la creació d'una assignatura
	$("#creaAssignaturaError").empty();
	
	// esborram el contingut dels camps del formulari de creació
	// que es pugui haver quedat d'una creació d'assinatura anterior
	$("#creaAssignaturaNom").val("");
	$("#creaAssignaturaDescripcio").val("");
	$("#creaAssignaturaClauProfessor").val("");
	$("#creaAssignaturaClauAlumne").val("");
}

/**
 * habilita vista de descripció d'assignatura
 * @param codi
 */
function habilitaDescripcio(codi){
	// ocultam totes les vistes del panell central i mostram la vista per mostrar la descripció d'una assignatura
	$(".central").hide();
	$("#centralDescripcioAssignatura").show();
	
	$.ajax({
        url: "/egradus/assignatura/descripcio.xml?codi=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	ompleDescripcio(xml);
        },
        error:function(){
            alert(ERROR_DESCRIPCIO_ASSIGNATURA);
        }
    });
}

/**
 * habilita vista de material de l'assignatura
 * @param codi
 */
function habilitaMaterial(codi) {
	// ocultam totes les vistes del panell central i mostram la vista per mostrar el material d'una assignatura:
	// (l'espai on poder manejar preguntes, qüestionaris, etc)
	$(".central").hide();
	$("#centralMaterial").show();
	
	// ocultam, també, tots els divs de material
	$(".egradus-material").hide();
	
	// Passam el codi d'assignatura a la vista material, per a poder disposar d'ell quan faci falta
	$("#materialCodiAssignatura").val(codi);
	
	// canviam el color de l'assignatura en el llistat lateral
	$(".egradus-llistat-assignatures").removeClass("egradus-color-llista");
	$("#assignatura" + codi).addClass("egradus-color-llista");
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// agafam informació sobre l'assignatura de la que extreim el material (per exemple
	// si la persona prèn un rol d'alumne o de professor)
	$.ajax({
        url: "/egradus/assignatura/material.xml?codi=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var codass = $(xml).find("assignatura").attr("codi");
        	var codref = $(xml).find("assignatura").attr("codiReferencia");
        	var assnom = $(xml).find("assignatura").attr("nom");
        	var anyaca = $(xml).find("assignatura").attr("anyAcademic");
        	var esAlumne = $(xml).find("esAlumne").attr("codi");
        	
        	$(".materialIcona").hide();
        	if (esAlumne == 'S') {
        		$("#materialAlumne").show();
        		$("#materialProfessor").hide();
        		$("#materialIconaAlumne").show();
        	}
        	if (esAlumne == 'N') {
        		$("#materialAlumne").hide();
        		$("#materialProfessor").show();
        		$("#materialIconaProfessor").show();
        	}
        	
        	$("#materialNomAssignatura").html(codref + " - " + assnom);
        	$("#materialAnyAcademic").html(anyaca);
        	$("#materialEsAlumne").html(esAlumne);
        	$("#materialCodiAssignatura").val(codass);
        },
        error:function(){
            alert(ERROR_MATERIAL_ASSIGNATURA);
        }
    });
}

//cerca assignatura manera antiga
//function cerca(){
//	var values = $("#cercaAssignaturaForm").serialize();
//	$.ajax({
//        url: "/egradus/assignatura/cerca.xml?" + values,
//        type: "get",
//        dataType: "xml",
//        success: function(xml) {
//        	$("#cercaAssignaturaTaulaResultats").empty(); // esborram el contingut abans de cada cerca
//        	$("#cercaAssignaturaTaulaResultats").append("<thead><tr><th>" + MIS_TAULA_CERCA_CODI + "</th><th>" + MIS_TAULA_CERCA_NOM + "</th><th>" + MIS_TAULA_CERCA_CREADOR + "</th></tr></thead>");
//            $(xml).find("assignatura").each(function(){
//                var nom = $(this).attr("nom");
//                var codi = $(this).attr("codi");
//                var creadorNom = $(this).find("creador").attr("nom");
//                var creadorLli1 = $(this).find("creador").attr("llinatge1");
//                var creadorLli2 = $(this).find("creador").attr("llinatge2");
//                var creador = creadorNom + " " + creadorLli1 + " " + creadorLli2;
//                $("#cercaAssignaturaTaulaResultats").append("<tr><td>"+ codi +"</td><td><a href='javascript: habilitaDescripcio(" + codi + ");'>" + nom +"</a></td><td>" + creador + "</td></tr>");
//            });
//        },
//        error:function(){
//            alert(ERROR_CERCA_ASSIGNATURA);
//        }
//    });
//}
	
/**
 * cerca assignatura manera nova: necessària per a que la cerca de preguntes 
 * tengui en compte els accents modificació respecte la funció de cerca antiga: 
 * POST enlloc de GET i el serializeArray() enlloc del serialize()
 */
function cerca(){
	$.ajax({
        url:  "/egradus/assignatura/cerca.xml",
        type: "post",
        data :  $('#cercaAssignaturaForm').serializeArray(),
        dataType: "xml",
        success: function(xml) {
        	$("#cercaAssignaturaTaulaResultats").empty(); // esborram el contingut abans de cada cerca
        	$("#cercaAssignaturaTaulaResultats").append("<thead><tr><th>" + MIS_TAULA_CERCA_CODI + "</th><th>" + MIS_TAULA_CERCA_NOM + "</th><th>" + MIS_TAULA_CERCA_CREADOR + "</th></tr></thead>");
        	ompleCerca(xml);
        },
        error:function(){
            alert(ERROR_CERCA_ASSIGNATURA);
        }
    });
}

/**
 * funció auxiliar que omple d'assignatures la taula de cerca d'assignatures
 * (ja sigui just en entrar o després d'haver filtrat per algún paràmetre)
 * 
 * @param xml
 */
function ompleCerca(xml) {
	$(xml).find("assignatura").each(function(){
		var codi = $(this).attr("codi");
		var codiRef = $(this).attr("codiReferencia");
        var nom = $(this).attr("nom");
        var anyAca = $(this).attr("anyAcademic");
        var creadorNom = $(this).find("creador").attr("nom");
        var creadorLli1 = $(this).find("creador").attr("llinatge1");
        var creadorLli2 = $(this).find("creador").attr("llinatge2");
        var creadorAli  = $(this).find("creador").attr("alies");
        var creador = creadorAli + " (" + creadorNom + " " + creadorLli1 + " " + creadorLli2 + ")";
        $("#cercaAssignaturaTaulaResultats").append("<tr><td>"+ codi +"</td><td>"+ codiRef +"</td><td><a href='javascript: habilitaDescripcio(" + codi + ");'>" + nom +"</a></td><td>"+ anyAca +"</td><td>" + creador + "</td></tr>");
    });
}

/**
 * crea assignatura
 */
function creacio(){
	// validar en client que la descripció de l'assignatura no excedeixi els 4000 caràcters que pot tenir.
	if ($("#creaAssignaturaDescripcio").val().length > 4000){
		alert(CREA_ASSIGNATURA_DESCRIPCIO_LLARGA);
		return;
	}
	
	var values = $("#creaAssignaturaForm").serialize();
	$.ajax({
        url: "/egradus/assignatura/crea.do",
        type: "post",
        data: values,
        success: function(xml) {
        	var error = $(xml).find("errorAssignatura").text();
        	if (error.length == 0) {
        		var codi  = $(xml).find("assignatura").attr("codi");
            	var nom   = $(xml).find("assignatura").attr("nom");
            	
            	afegeixAssignaturaLLista(codi, nom);
            	
            	$("#creaAssignaturaModalMissatge").html(MIS_CREA_ASS_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + MIS_CREA_ASS_MODAL_MIS2);
            	$("#creaAssignaturaModal").modal("show");
            	
            	// esborram el contingut després de la creació
            	$("#creaAssignaturaCodiReferencia").val("");
            	$("#creaAssignaturaNom").val("");
            	$("#creaAssignaturaAnyAcademic").val("");
            	$("#creaAssignaturaDescripcio").val("");
            	$("#creaAssignaturaClauProfessor").val("");
            	$("#creaAssignaturaClauAlumne").val("");
            	
            	// neteja el possible error que hi hagi hagut anteriorment
            	// en la creació d'una assignatura
            	$("#creaAssignaturaError").empty();
        	} else {
        		$("#creaAssignaturaError").html(error);
        	}
        },
        error:function(){
        	alert(ERROR_CREACIO_ASSIGNATURA);
        }
    });
}

// unir assignatura antiga
//function unir(url){
//	var values = $("#descripcioAssignaturaFormUnirse").serialize();
//	$.ajax({
//        url: url,
//        type: "post",
//        data: values,
//        success: function(xml) {
//        	ompleDescripcio(xml);
//        	var errorUnio = $(xml).find("errorUnio");
//        	if (errorUnio.length == 0){
//        		var codi  = $(xml).find("assignatura").attr("codi");
//            	var nom   = $(xml).find("assignatura").attr("nom");
//            	afegeixAssignaturaLLista(codi, nom);
//        	}
//        },
//        error:function(){
//            alert(ERROR_UNIO_ASSIGNATURA);
//        }
//    });
//}

/**
 * unir assignatura utilitzant AssignaturaException
 * @param url
 */
function unir(url){
	var values = $("#descripcioAssignaturaFormUnirse").serialize();
	$.ajax({
        url: url,
        type: "post",
        data: values,
        success: function(xml) {
        	error = ompleDescripcio(xml);
            if (error.length == 0) {
        		var codi  = $(xml).find("assignatura").attr("codi");
            	var nom   = $(xml).find("assignatura").attr("nom");
            	afegeixAssignaturaLLista(codi, nom);
            	$("#descripcioAssignaturaModalMissatgeUnioOk").html(MIS_UNIO_ASS_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + MIS_UNIO_ASS_MODAL_MIS2);
            	$("#descripcioAssignaturaModalBotoMaterial").attr("onclick", "habilitaMaterial(" + codi + ");");
            	$("#descripcioAssignaturaModalUnioOk").modal("show");
        	} else {
        		$("#descripcioAssignaturaModalMissatgeUnioError").html(error);
            	$("#descripcioAssignaturaModalUnioError").modal("show");
        	}
        },
        error:function(xhr){
            $("#descripcioAssignaturaModalMissatgeUnioError").html($.trim(xhr.responseText));
        	$("#descripcioAssignaturaModalUnioError").modal("show");
        }
    });
}

function afegeixAssignaturaLLista(codi, nom){
	$("#llistaAssignatures").append('<li id="assignatura' + codi + '" class="sidebar-brand egradus-llistat-assignatures"><a href="javascript: habilitaMaterial(' + codi + ');">'+ nom + '</a></li>');
}

function unirseUnBoto(){
	unir("/egradus/assignatura/unirse.do");
}

function unirseDosBotonsAlu(){
	unir("/egradus/assignatura/unirseAlumne.do");
}

function unirseDosBotonsPro(){
	unir("/egradus/assignatura/unirseProfessor.do");
}

function ompleDescripcio(xml) {
	// Esborram el contingut abans de cada cerca:
	//  · De la Clau que s'hagi pogut escriure
	//  · Del llistat de Professors de l'assignatura
	$("#descripcioAssignaturaUnioClau").val("");
	$("#descripcioAssignaturaProfessors").empty();
	
	var codass      = $(xml).find("assignatura").attr("codi");
	var codRefAss   = $(xml).find("assignatura").attr("codiReferencia");
	var assnom      = $(xml).find("assignatura").attr("nom");
	var anyAca      = $(xml).find("assignatura").attr("anyAcademic");
	var datalt      = new Date($(xml).find("assignatura").attr("dataAlta"));
	var descripcio  = $(xml).find("descripcio");
	var crenom      = $(xml).find("creador").attr("nom");
	var crelli1     = $(xml).find("creador").attr("llinatge1");
	var crelli2     = $(xml).find("creador").attr("llinatge2");
	var crealies    = $(xml).find("creador").attr("alies");
	var creador     = crealies + " (" + crenom + " " + crelli1 + " " + crelli2 + ")";
	var errorUnio   = $(xml).find("errorUnio");
	var claupro     = $(xml).find("assignatura").attr("clauProfessor");
	var claualu     = $(xml).find("assignatura").attr("clauAlumne");
	
	$(xml).find("professor").each(function(){
        var pronom  = $(this).attr("nom");
        var prolli1 = $(this).attr("llinatge1");
        var prolli2 = $(this).attr("llinatge2");
        var proali  = $(this).attr("alies");
        $("#descripcioAssignaturaProfessors").append(proali + " (" + pronom + " " + prolli1 + " " + prolli2 + ")<br>");
    });
	
	// Deixam un espai entre el text d'informació de la unió i el formulari d'unió en sí
	$("#descripcioAssignaturaFormUnirse").css({'padding-top': '15px'});
	
	// finalment, omplim els camps amb la informació que hem agafat de l'XML
	$("#descripcioAssignaturaNom").html(codRefAss + " - " + assnom);
	$("#descripcioAssignaturaAnyAcademic").html(anyAca);
	$("#descripcioAssignaturaDataAlta").html(formatData(datalt));
	$("#descripcioAssignaturaDescripcio").html(descripcio);
	$("#descripcioAssignaturaCreador").html(creador);
	
	if (claupro.length == 0 && claualu.length == 0) {
		$("#descripcioAssignaturaUnioDosBotons").show(); 
		$("#descripcioAssignaturaUnioUnBoto").hide();
		$("#descripcioAssignaturaInfoUnio").empty();
	}
	if (claupro.length == 0 && claualu.length != 0) {
		$("#descripcioAssignaturaUnioUnBoto").show(); 
		$("#descripcioAssignaturaUnioDosBotons").hide();
		$("#descripcioAssignaturaInfoUnio").html(MIS_INFO_UNIO_CLAU_ALU);
	}
	if (claupro.length != 0 && claualu.length == 0) {
		$("#descripcioAssignaturaUnioUnBoto").show(); 
		$("#descripcioAssignaturaUnioDosBotons").hide();
		$("#descripcioAssignaturaInfoUnio").html(MIS_INFO_UNIO_CLAU_PRO);
	}
	if (claupro.length != 0 && claualu.length != 0) {
		$("#descripcioAssignaturaUnioUnBoto").show(); 
		$("#descripcioAssignaturaUnioDosBotons").hide();
		$("#descripcioAssignaturaInfoUnio").html(MIS_INFO_UNIO_CLAU_TOTS);
	}
	
	$("#descripcioAssignaturaCodi").val(codass);
	
	return errorUnio;
}
