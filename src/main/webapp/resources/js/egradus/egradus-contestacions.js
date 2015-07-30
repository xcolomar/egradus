/*
 * egradus-contestacions.js
 * ----------------------------------------------------------------------------------------
 * 
 * Funcionalitats per a que els alumnes puguin contestar preguntes i qüestionaris
 * 
 */

/**
 * habilita la vista dels alumnes per a contestar preguntes
 */
function habilitaPreguntes() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialPreguntes").show();
	
	// netejam els llistats de preguntes
	$("#consultaMaterialPreguntesLlistatPendents").empty();
	$("#consultaMaterialPreguntesLlistatPendentsCorregir").empty();
	$("#consultaMaterialPreguntesLlistatPendentsRevisar").empty();
	$("#consultaMaterialPreguntesLlistatContestades").empty();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya de contestació de preguntes, per saber que ha
	// estat seleccionada per l'alumne
	$("#assignaturaMaterialPreguntes").addClass("egradus-color-pestanya");
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// obtenim els llistats de preguntes pendents de contestar, pendents de ser corregides,
	// pendents de ser revisades i preguntes contestades
	preguntesPendentsDeContestar(codiAssignatura);
	preguntesPendentsDeSerCorregides(codiAssignatura);
	preguntesPendentsDeSerRevisades(codiAssignatura);
	preguntesContestades(codiAssignatura);
}

/**
 * obtenim el llistat de preguntes pendents de contestar per l'alumne actual (el tenim a la sessió)
 * i actualitzam la vista 'consultarMaterialPreguntes.jsp' amb aquesta informació
 * 
 * @param codiAssignatura
 */
function preguntesPendentsDeContestar(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getPreguntesAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-success\">" +
								   "<div class=\"panel-heading\">" + MIS_PRE_PENDENTS_CONTESTAR + "</div>" +
					            "<ul class=\"list-group\">";
        	var preguntaDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var preguntes = $(xml).find("respostaPregunta");
        	if (preguntes.length > 0) {
        		preguntes.each(function(){
        			var codiRp   = $(this).attr("codi");
        			var anonima  = $(this).attr("anonima");
        			var dataAlta = new Date($(this).attr("dataAlta"));
	        		var codi     = $(this).find("pregunta").attr("codi");
	                var enunciat = $(this).find("pregunta").attr("enunciat");
	                var tipus    = $(this).find("pregunta").attr("tipus");
	                var rr       = $(this).find("pregunta").attr("raonarResposta");
	                var dt       = $(this).find("pregunta").attr("dificultatTeorica");
	                var dp       = $(this).find("pregunta").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";
	                
	                var rrLlapis;
	                if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
	                else              rrLlapis = "";
	                
	                var divAnonima;
	                if (anonima == "true") 	divAnonima = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_PRE_ANONIMA;
	                else 					divAnonima = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var infoPregunta = "<div class=\"row\">" +
		                			      "<div class=\"col-lg-3\">" + MIS_PRE_PENDENTS_CONTESTAR_ROW_ASSIGNADA_PER + ": " + assignador + "</div>" +
		                			      "<div class=\"col-lg-1\">" + MIS_PRE_PENDENTS_CONTESTAR_ROW_TIPUS + ": " + tipus + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dtText + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dpText + "</div>" +
		                			      "<div class=\"col-lg-2\">" + divAnonima + "</div>" +
		                			      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
		                			      "<div class=\"col-lg-3\">" + MIS_PRE_PENDENTS_CONTESTAR_ROW_REBUDA + ": " + formatData(dataAlta) + "</div>" +
		                			   "</div>";
	                
	                var textPregunta = "<div class=\"row\">" +
									      "<div class=\"col-lg-12\"><h4>" + codi + " - " + enunciat + "</h4></div>" +
									   "</div>";
	                
	                var preguntaBox = infoPregunta + textPregunta;
	                
	                preguntaDesc += "<li class=\"list-group-item\"><a href=\"javascript: iniciaContestacioPregunta(" + codiRp + ");\">" + preguntaBox + "</a></li>";
	        	});
        	} else {
        		preguntaDesc = "<div class=\"pregunta-buit\">" + MIS_PRE_PENDENTS_CONTESTAR_BUIT + "</div>";
        	}
        	$("#consultaMaterialPreguntesLlistatPendents").html(panelHeading + preguntaDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista per a contestar les preguntes");
        }
    });
}

/**
 * obtenim el llistat de preguntes que ha contestat l'alumne actual (el tenim a la sessió) 
 * pendents de ser corregides i actualitzam la vista 'consultarMaterialPreguntes.jsp' amb 
 * aquesta informació
 * 
 * @param codiAssignatura
 */
function preguntesPendentsDeSerCorregides(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getPreguntesPendentsCorregirAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-warning\">" +
								   "<div class=\"panel-heading\">" + MIS_PRE_PENDENTS_SER_CRR + "</div>" +
					            "<ul class=\"list-group\">";
        	var preguntaDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var preguntes = $(xml).find("respostaPregunta");
        	if (preguntes.length > 0) {
        		preguntes.each(function(){
        			var codiRp   = $(this).attr("codi");
        			var anonima  = $(this).attr("anonima");
        			var dataCnt  = new Date($(this).attr("dataContestacioFi"));
	        		var codi     = $(this).find("pregunta").attr("codi");
	                var enunciat = $(this).find("pregunta").attr("enunciat");
	                var tipus    = $(this).find("pregunta").attr("tipus");
	                var rr       = $(this).find("pregunta").attr("raonarResposta");
	                var dt       = $(this).find("pregunta").attr("dificultatTeorica");
	                var dp       = $(this).find("pregunta").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";
	                
	                var rrLlapis;
	                if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
	                else              rrLlapis = "";
	                
	                var divAnonima;
	                if (anonima == "true") 	divAnonima = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_PRE_ANONIMA;
	                else 					divAnonima = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var infoPregunta = "<div class=\"row\">" +
		                			      "<div class=\"col-lg-3\">" + MIS_PRE_CONTESTADES_ROW_ASSIGNADA_PER + ": " + assignador + "</div>" +
		                			      "<div class=\"col-lg-1\">" + MIS_PRE_CONTESTADES_ROW_TIPUS + ": " + tipus + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dtText + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dpText + "</div>" +
		                			      "<div class=\"col-lg-2\">" + divAnonima + "</div>" +
		                			      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
		                			      "<div class=\"col-lg-3\">" + MIS_PRE_CONTESTADES_ROW_CONTESTADA + ": " + formatData(dataCnt) + "</div>" +
		                			   "</div>";
	                
	                var textPregunta = "<div class=\"row\">" +
									      "<div class=\"col-lg-12\"><h4>" + codi + " - " + enunciat + "</h4></div>" +
									   "</div>";
	                
	                var preguntaBox = infoPregunta + textPregunta;
	                
	                preguntaDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaPreguntaContestada(" + codiRp + ", 1);\">" + preguntaBox + "</a></li>";
	        	});
        	}
        	
        	if (preguntaDesc.length > 0) 
        		$("#consultaMaterialPreguntesLlistatPendentsCorregir").html(panelHeading + preguntaDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista per a veure les preguntes pendents de ser corregides");
        }
    });
}

/**
 * obtenim el llistat de preguntes que ha contestat l'alumne actual (el tenim a la sessió) 
 * pendents de ser revisades i actualitzam la vista 'consultarMaterialPreguntes.jsp' amb 
 * aquesta informació
 * 
 * @param codiAssignatura
 */
function preguntesPendentsDeSerRevisades(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getPreguntesPendentsRevisarAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-warning\">" +
								   "<div class=\"panel-heading\">" + MIS_PRE_PENDENTS_SER_REV + "</div>" +
					            "<ul class=\"list-group\">";
        	var preguntaDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var preguntes = $(xml).find("respostaPregunta");
        	if (preguntes.length > 0) {
        		preguntes.each(function(){
        			var codiRp   = $(this).attr("codi");
        			var anonima  = $(this).attr("anonima");
        			var dataCrr  = new Date($(this).attr("dataCorreccio"));
	        		var codi     = $(this).find("pregunta").attr("codi");
	                var enunciat = $(this).find("pregunta").attr("enunciat");
	                var tipus    = $(this).find("pregunta").attr("tipus");
	                var rr       = $(this).find("pregunta").attr("raonarResposta");
	                var dt       = $(this).find("pregunta").attr("dificultatTeorica");
	                var dp       = $(this).find("pregunta").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";
	                
	                var rrLlapis;
	                if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
	                else              rrLlapis = "";
	                
	                var divAnonima;
	                if (anonima == "true") 	divAnonima = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_PRE_ANONIMA;
	                else 					divAnonima = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_PRE_PENDENTS_CONTESTAR_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var textCorregida = "";
	                if (tipus == TIPUS_PREGUNTA_REC) textCorregida = MIS_PRE_CONTESTADES_ROW_CORREGIDA;
	                else textCorregida = MIS_PRE_CONTESTADES_ROW_CORREGIDA_AUT;
	                
	                var infoPregunta = "<div class=\"row\">" +
		                			      "<div class=\"col-lg-3\">" + MIS_PRE_CONTESTADES_ROW_ASSIGNADA_PER + ": " + assignador + "</div>" +
		                			      "<div class=\"col-lg-1\">" + MIS_PRE_CONTESTADES_ROW_TIPUS + ": " + tipus + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dtText + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dpText + "</div>" +
		                			      "<div class=\"col-lg-2\">" + divAnonima + "</div>" +
		                			      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
		                			      "<div class=\"col-lg-3\">" + textCorregida + ": " + formatData(dataCrr) + "</div>" +
		                			   "</div>";
	                
	                var textPregunta = "<div class=\"row\">" +
									      "<div class=\"col-lg-12\"><h4>" + codi + " - " + enunciat + "</h4></div>" +
									   "</div>";
	                
	                var preguntaBox = infoPregunta + textPregunta;
	                
	                preguntaDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaPreguntaContestada(" + codiRp + ", 1);\">" + preguntaBox + "</a></li>";
	        	});
        	}
        	
        	if (preguntaDesc.length > 0) 
        		$("#consultaMaterialPreguntesLlistatPendentsRevisar").html(panelHeading + preguntaDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista per a veure les preguntes pendents de ser revisades");
        }
    });
}

/**
 * obtenim el llistat de preguntes contestades per l'alumne actual (el tenim a la sessió)
 * i actualitzam la vista 'consultarMaterialPreguntes.jsp' amb aquesta informació
 * 
 * @param codiAssignatura
 */
function preguntesContestades(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getPreguntesContestadesAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-danger\">" +
								   "<div class=\"panel-heading\">" + MIS_PRE_CONTESTADES + "</div>" +
					            "<ul class=\"list-group\">";
        	var preguntaDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var preguntes = $(xml).find("respostaPregunta");
        	if (preguntes.length > 0) {
        		preguntes.each(function(){
        			var codiRp   = $(this).attr("codi");
        			var anonima  = $(this).attr("anonima");
        			var dataCrr  = new Date($(this).attr("dataCorreccio"));
	                var dataRev  = new Date($(this).attr("dataRevisio"));
	        		var codi     = $(this).find("pregunta").attr("codi");
	                var enunciat = $(this).find("pregunta").attr("enunciat");
	                var tipus    = $(this).find("pregunta").attr("tipus");
	                var rr       = $(this).find("pregunta").attr("raonarResposta");
	                var dt       = $(this).find("pregunta").attr("dificultatTeorica");
	                var dp       = $(this).find("pregunta").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";
	                
	                var rrLlapis;
	                if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
	                else              rrLlapis = "";
	                
	                var divAnonima;
	                if (anonima == "true") 	divAnonima = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_PRE_ANONIMA;
	                else 					divAnonima = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_PRE_CONTESTADES_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_PRE_CONTESTADES_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var textCorregida = "";
	                if (tipus == TIPUS_PREGUNTA_REC) textCorregida = MIS_PRE_CONTESTADES_ROW_CORREGIDA;
	                else textCorregida = MIS_PRE_CONTESTADES_ROW_CORREGIDA_AUT;
	                
	                var textRevisada = "";
	                if (rr == "true") textRevisada = "<br /> " + MIS_PRE_CONTESTADES_ROW_REVISADA + ": " + formatData(dataRev);
	                
	                var infoPregunta = "<div class=\"row\">" +
							               "<div class=\"col-lg-3\">" + MIS_PRE_CONTESTADES_ROW_ASSIGNADA_PER + ": " + assignador + "</div>" +
						  			       "<div class=\"col-lg-1\">" + MIS_PRE_CONTESTADES_ROW_TIPUS + ": " + tipus + "</div>" +
						  			     "<div class=\"col-lg-1\">" + dtText + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dpText + "</div>" +
		                			      "<div class=\"col-lg-2\">" + divAnonima + "</div>" +
		                			      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
						  			       "<div class=\"col-lg-3\">" + textCorregida + ": " + formatData(dataCrr) + textRevisada + "</div>" +
		                			   "</div>";
	                
	                var textPregunta = "<div class=\"row\">" +
									      "<div class=\"col-lg-12\"><h4>" + codi + " - " + enunciat + "</h4></div>" +
									   "</div>";
	                
	                var preguntaBox = infoPregunta + textPregunta;
//	                var esAlumne = "S";
	                preguntaDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaPreguntaContestada(" + codiRp + ", 1);\">" + preguntaBox + "</a></li>";
	        	});
        	} else {
        		preguntaDesc = "<div class=\"pregunta-buit\">" + MIS_PRE_CONTESTADES_BUIT + "</div>";
        	}
        	$("#consultaMaterialPreguntesLlistatContestades").html(panelHeading + preguntaDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista per a contestar les preguntes");
        }
    });
}

/**
 * Mostra el detall d'una resposta-pregunta prèviament contestada.
 * 
 * Donat que aquesta funció és reutilitzada en cas d'alumne i professor, 
 * el paràmetre 'esAlumne' pretén discernir si qui crida la funció és un
 * alumne o un professor. 
 * 
 * @param codiRespostaPregunta
 * @param esAlumne prendrà per valor 1 o 0
 */
function mostraRespostaPreguntaContestada(codiRespostaPregunta, esAlumne) {
	
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialConsultarResposta").show();
	
	$.ajax({
        url: "/egradus/contestacio/consultaResposta.do",
        type: "post",
        // afegim el codi de la pregunta
        data: "codiRespostaPregunta=" + codiRespostaPregunta, 
        success: function(xml) {
        	var rspDataInici = $(xml).find("respostaPregunta").attr("dataContestacioInici");
        	var rspDataFi    = $(xml).find("respostaPregunta").attr("dataContestacioFi");
        	var rspDataCrr   = $(xml).find("respostaPregunta").attr("dataCorreccio");
        	var rspDataRev   = $(xml).find("respostaPregunta").attr("dataRevisio");
			var rspTextREC   = $(xml).find("respostaPregunta").attr("textResposta");
        	var rspTextRR    = $(xml).find("respostaPregunta").attr("textRaonarResposta");
        	var rspNota      = $(xml).find("respostaPregunta").attr("nota");
        	var rspNotaAnt   = $(xml).find("respostaPregunta").attr("notaAntiga");
        	var rspTextCrr   = $(xml).find("respostaPregunta").attr("textCorreccio");
        	var rspTextRev   = $(xml).find("respostaPregunta").attr("textRevisio");
        	var anonima      = $(xml).find("respostaPregunta").attr("anonima");
        	
//        	var preCodi 	 = $(xml).find("pregunta").attr("codi");
        	var preEnunciat  = $(xml).find("pregunta").attr("enunciat");
        	var preTipus 	 = $(xml).find("pregunta").attr("tipus");
        	var preRr 		 = $(xml).find("pregunta").attr("raonarResposta");
        	
        	var asiNom 		 = $(xml).find("assignador").attr("nom");
        	var asiLli1 	 = $(xml).find("assignador").attr("llinatge1");
        	var asiLli2 	 = $(xml).find("assignador").attr("llinatge2");
        	var asiAlies     = $(xml).find("assignador").attr("alies");
        	var assignador   = asiAlies + " (" + asiNom + " " + asiLli1 + " " + asiLli2 + ")";
        	
        	var aluNom 		 = $(xml).find("alumne").attr("nom");
        	var aluLli1 	 = $(xml).find("alumne").attr("llinatge1");
        	var aluLli2 	 = $(xml).find("alumne").attr("llinatge2");
        	var aluAlies     = $(xml).find("alumne").attr("alies");
        	var alumne       = aluAlies + " (" + aluNom + " " + aluLli1 + " " + aluLli2 + ")";
        	
        	var dataInici = new Date(rspDataInici);
        	var dataFi = new Date(rspDataFi);
        	
        	$("#consultarRespostaEnunciat").html(preEnunciat);
        	$("#consultarRespostaDataInici").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_DATFIN + ": </b>" + formatData(dataFi) + "</div>");
        	$("#consultarRespostaDataTemps").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_TEMPS + ": </b>" + diferenciaDates(dataFi, dataInici) + "</div>");
        	if (esAlumne == 1) { // ALUMNE
        		$("#consultarRespostaContestadaPer").empty();
        		$("#consultarRespostaAssignadaPer").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_ASSIG + ": </b>" + assignador + "</div>");
        		
        		// Pregunta anònima o no
            	var divAnonima;
                if (anonima == "true") 	divAnonima = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_PRE_ANONIMA;
                else 					divAnonima = "";
                
        		$("#consultarRespostaAnonima").html("<div class=\"col-lg-12\">" + divAnonima + "</div>");
        	} else if (esAlumne == 0) { // PROFESSOR
        		$("#consultarRespostaAssignadaPer").empty();
        		
        		// Si la resposta-pregunta és anònima, no hem de veure l'alumne
        		var divAnonimaOAlumne;
                if (anonima == "true") 	divAnonimaOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
                else 					divAnonimaOAlumne = alumne;
        		
        		$("#consultarRespostaContestadaPer").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_ALUMNE + ": </b>" + divAnonimaOAlumne + "</div>");
        	}
        	
        	// Data de correcció (en cas que s'hagi corregit)
        	if (rspDataCrr.length > 0) {
        		var dataCrr = new Date(rspDataCrr);
        		var textRespostaDataCorreccio = "<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_CORREGIDA + ": </b>" + formatData(dataCrr);
        		// si la pregunta NO és REC, indicarem que es tracta d'una correcció automàtica
        		if (preTipus != TIPUS_PREGUNTA_REC) textRespostaDataCorreccio += " <i>(" + MIS_PRE_CONS_CRR_AUTOMATICA + ")</i>";
        		textRespostaDataCorreccio += "</div>";
        		$("#consultarRespostaDataCorreccio").html(textRespostaDataCorreccio);
        	} else $("#consultarRespostaDataCorreccio").empty();
        	
        	// Text de la Correcció (en cas que la pregunta sigui REC i ja s'hagi corregit)
        	if (rspTextCrr.length > 0) {
        		$("#consultarRespostaTextCorreccioREC").html("<div class=\"form-group\">" +
																 "<label for=\"consultarRespostaTextCorreccioRECInput\">" + MIS_PRE_CONS_RESPOSTA_CRR_REC + "</label>" +
																 "<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaTextCorreccioRECInput\" name=\"correccioREC\" disabled>" + rspTextCrr + "</textarea>" +
															 "</div>");
        	} else $("#consultarRespostaTextCorreccioREC").empty();
        	
        	// si té notaAntiga:
        	if (rspNota.length > 0 && rspNotaAnt.length > 0) {
        		$("#consultarRespostaNotaRevisada").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_NOTA_REV + ": </b>" + rspNota + "</div>");
        		rspNota = rspNotaAnt;
        	} else $("#consultarRespostaNotaRevisada").empty();
        	
        	// Nota (en cas que s'hagi corregit)
        	if (rspNota.length > 0) {
        		// si la nota pot variar degut a que el professor la revisi en un futur, avisam!
        		var pendentRevisio = "";
        		if (preRr == "true" && rspDataRev.length == 0) pendentRevisio = " <i>(" + MIS_PRE_CONS_PENDENT_REVISIO + ")</i>";
        		$("#consultarRespostaNota").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_NOTA + ": </b>" + rspNota + pendentRevisio + "</div>");
        	} else {
        		if (esAlumne == 1 && preTipus == TIPUS_PREGUNTA_REC) $("#consultarRespostaNota").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_NOTA + ": </b><i>" + MIS_PRE_CONS_PENDENT_CORRECCIO + " " + assignador + "</i></div>");
        		else $("#consultarRespostaNota").empty();
        	}
        	
        	// Data de revisió (en cas que s'hagi revisat)
        	if (rspDataRev.length > 0) {
        		var dataRev = new Date(rspDataRev);
        		var textRespostaDataRevisio = "<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_REVISADA + ": </b>" + formatData(dataRev);
        		textRespostaDataRevisio += "</div>";
        		$("#consultarRespostaDataRevisio").html(textRespostaDataRevisio);
        	} else $("#consultarRespostaDataRevisio").empty();
        	
        	// Text de la Revisió (en cas que la pregunta estigui corregida i revisada)
        	if (rspTextRev.length > 0) {
        		$("#consultarRespostaTextRevisio").html("<div class=\"form-group\">" +
															"<label for=\"consultarRespostaTextRevisioInput\">" + MIS_PRE_CONS_RESPOSTA_REV + "</label>" +
															"<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaTextRevisioInput\" name=\"revisio\" disabled>" + rspTextRev + "</textarea>" +
														"</div>");
        	} else $("#consultarRespostaTextRevisio").empty();
        	
        	// buidam el camp de Raonar Resposta que pugui haver aparegut en una contestació de pregunta anterior
        	$("#consultarRespostaRaonarResposta").empty();
        	
        	// obtenim un llistat amb les opcions marcades per l'alumne
        	var opcionsMarcades = $(xml).find("opcioMarcada");
        	var lstOpcMarcades = [];
        	var i = 0;
        	if (opcionsMarcades.length > 0) {
        		opcionsMarcades.each(function(){
        			lstOpcMarcades[i++] = $(this).attr("codiOpcio");
            	});
        	}
        	
        	// obtenim les opcions i les pintam a la vista d'un determinat color en funció de si
        	// la opció s'ha acertat o fallat i de si s'ha contestat o no
        	var opcions = $(xml).find("opcio");
        	if (opcions.length > 0) {
        		var opcionsDesc = "";
        		var opcioResultatClass = "";
        		opcions.each(function(){
        			var codiOpcio = $(this).attr("codiOpcio");
            		var textOpcio = $(this).attr("text");
            		var correctaOpcio = $(this).attr("correcta");
            		
            		// la opció és correcta, i l'alumne l'ha marcada!
        			if (correctaOpcio == "true" && opcioContestada(codiOpcio, lstOpcMarcades)) 
        				opcioResultatClass = "egradus-opcio-contestada-acertada";
        			
        			// la opció és correcta, però l'alumne no l'ha marcada
        			else if (correctaOpcio == "true" && !opcioContestada(codiOpcio, lstOpcMarcades)) 
        				opcioResultatClass = "egradus-opcio-no-contestada-fallada";
        			
        			// la opció és falsa, i l'alumne l'ha marcada
        			else if (correctaOpcio == "false" && opcioContestada(codiOpcio, lstOpcMarcades)) 
        				opcioResultatClass = "egradus-opcio-contestada-fallada";
        			
        			// la opció és falsa, però l'alumne no l'ha marcada!
        			else if (correctaOpcio == "false" && !opcioContestada(codiOpcio, lstOpcMarcades)) 
        				opcioResultatClass = "egradus-opcio-no-contestada-acertada";
        			
        			var correctaIcon = "";
        			var correctaClass = "";
        	        if (correctaOpcio == "true") {
        	        	correctaIcon = "<span class=\"glyphicon glyphicon-ok\"></span>";
        	        	correctaClass = "egradus-opcio-correcta";
        	        } else {
        	        	correctaIcon = "<span class=\"glyphicon glyphicon-remove\"></span>";
        	        	correctaClass = "egradus-opcio-incorrecta";
        	        }
        			
        			opcionsDesc +=  "<div class=\"row\">" +
										"<div class=\"col-xs-9\">" +
											"<div class=\"panel panel-default\">" +
												"<div id=\"consultarRespostaCodiOpcio" + codiOpcio + "\" class=\"panel-body text-center " + opcioResultatClass + "\">" + textOpcio + "</div>" +
											"</div>" +
										"</div>" + 
										"<div class=\"col-xs-3\">" +
											"<div class=\"panel panel-default\">" +
												"<div id=\"consultarRespostaCodiOpcio" + codiOpcio + "correcta\" class=\"panel-body text-center " + correctaClass + "\">" + correctaIcon + "</div>" +
											"</div>" +
										"</div>" + 
									"</div>";
        		});
        		$("#consultarRespostaOpcionsOREC").html(opcionsDesc);
        	} else {
        		// Si és una pregunta de tipus REC hem d'habilitar un camp de text
        		// per a donar una resposta a la pregunta
        		if (preTipus == TIPUS_PREGUNTA_REC) {
            		$("#consultarRespostaOpcionsOREC").html("<div class=\"form-group\">" +
    															"<label for=\"consultarRespostaContestacioPreguntaRECInput\">" + MIS_PRE_CONS_RESPOSTA_RESP_REC + "</label>" +
    															"<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaContestacioPreguntaRECInput\" name=\"respostaREC\" disabled>" + rspTextREC + "</textarea>" +
    														"</div>");
            	}
        	}
        	
        	// Raonar resposta
        	if (preRr == "true") {
        		$("#consultarRespostaRaonarResposta").html("<div class=\"form-group\">" +
															   "<label for=\"consultarRespostaRaonarRespostaInput\">" + MIS_PRE_CONS_RESPOSTA_RESP_RR + "</label>" +
															   "<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaRaonarRespostaInput\" name=\"raonarresposta\" disabled>" + rspTextRR + "</textarea>" + 
														   "</div>");
        	}
        },
        error:function(){
        	alert("error mostraRespostaPreguntaContestada");
        }
    });
}

/**
 * funció local que ens permet conèixer si, de la llista d'opcions
 * que passam com a segon paràmetre, la opció amb codi 'codiOpcio'
 * està entre elles.
 * 
 * NOTA: Ojo, que feim referència a aquesta funció també a 
 * 'egradus-correccions.js', en la revisió de resposta-preguntes
 * 
 * @param codiOpcio
 * @param llistatOpcionsMarcades
 * @returns {Boolean}
 */
function opcioContestada(codiOpcio, llistatOpcionsMarcades) {
	var i = 0;
	while (i < llistatOpcionsMarcades.length && codiOpcio != llistatOpcionsMarcades[i]) i++;
	return i < llistatOpcionsMarcades.length && codiOpcio == llistatOpcionsMarcades[i];
}

/**
 * habilita la vista dels alumnes per a contestar qüestionaris
 */
function habilitaQuestionaris() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialQuestionaris").show();
	
	// netejam els llistats de qüestionaris
	$("#consultaMaterialQuestionarisLlistatPendents").empty();
	$("#consultaMaterialQuestionarisLlistatPendentsCorregir").empty();
	$("#consultaMaterialQuestionarisLlistatPendentsRevisar").empty();
	$("#consultaMaterialQuestionarisLlistatContestats").empty();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya de contestació de qüestionaris, per saber que ha
	// estat seleccionada per l'alumne
	$("#assignaturaMaterialQuestionaris").addClass("egradus-color-pestanya");
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// obtenim els llistats de qüestionaris pendents de contestar, pendents de ser corregits
	// pendents de ser revisats i qüestionaris contestats
	questionarisPendentsDeContestar(codiAssignatura);
	questionarisPendentsDeSerCorregits(codiAssignatura);
	questionarisPendentsDeSerRevisats(codiAssignatura);
	questionarisContestats(codiAssignatura);
}

/**
 * obtenim el llistat de qüestionaris pendents de contestar per l'alumne actual (el tenim a la sessió)
 * i actualitzam la vista 'consultarMaterialQuestionaris.jsp' amb aquesta informació
 * 
 * @param codiAssignatura
 */
function questionarisPendentsDeContestar(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getQuestionarisAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var panelHeading = "<div class=\"panel panel-success\">" +
								   "<div class=\"panel-heading\">" + MIS_QST_PENDENTS_CONTESTAR + "</div>" +
					            "<ul class=\"list-group\">";
        	var questionariDesc = "";
			var panelFooter = "</ul></div>";
			
        	var questionaris = $(xml).find("respostaQuestionari");
        	if (questionaris.length > 0) {
        		questionaris.each(function(){
        			var codiRq     = $(this).attr("codi");
        			var anonim     = $(this).attr("anonim");
        			var dataAlta   = new Date($(this).attr("dataAlta"));
	        		var codi       = $(this).find("questionari").attr("codi");
	                var nom        = $(this).find("questionari").attr("nom");
	                var descripcio = $(this).find("questionari").attr("descripcio");
	                var dt         = $(this).find("questionari").attr("dificultatTeorica");
	                var dp         = $(this).find("questionari").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";
	                
	                var divAnonim;
	                if (anonim == "true") 	divAnonim = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_QST_ANONIM;
	                else 					divAnonim = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_QST_PENDENTS_CONTESTAR_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_QST_PENDENTS_CONTESTAR_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var infoQuestionari =  "<div class=\"row\">" +
							  			      "<div class=\"col-lg-3\">" + MIS_QST_PENDENTS_CONTESTAR_ROW_ASSIGNAT_PER + ": " + assignador + "</div>" +
							  			      "<div class=\"col-lg-1\">" + dtText + "</div>" +
							  			      "<div class=\"col-lg-3\">" + dpText + "</div>" +
							  			      "<div class=\"col-lg-2\">" + divAnonim + "</div>" +
							  			      "<div class=\"col-lg-3\">" + MIS_QST_PENDENTS_CONTESTAR_ROW_REBUT + ": " + formatData(dataAlta) + "</div>" +
							  			   "</div>";
	                
	                var textQuestionari = "<div class=\"row\">" +
										      "<div class=\"col-lg-12\"><h4>" + codi + " - " + nom + "</h4></div>" +
										   "</div>" +
										   "<div class=\"row\">" +
										      "<div class=\"col-lg-12\">" + descripcio + "</div>" +
										   "</div>";
	                
	                var questionariBox = infoQuestionari + textQuestionari;
	                
	                questionariDesc += "<li class=\"list-group-item\"><a href=\"javascript: iniciaContestacioQuestionari(" + codiRq + ");\">" + questionariBox + "</a></li>";
	        	});
        	} else {
        		questionariDesc = "<div class=\"questionari-buit\">" + MIS_QST_PENDENTS_CONTESTAR_BUIT + "</div>";
        	}
        	$("#consultaMaterialQuestionarisLlistatPendents").html(panelHeading + questionariDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista per a contestar els qüestionaris");
        }
    });
}

/**
 * obtenim el llistat de qüestionaris que ha contestat l'alumne actual (el tenim a la sessió) 
 * pendents de ser corregits i actualitzam la vista 'consultarMaterialQuestionaris.jsp' amb 
 * aquesta informació
 * 
 * @param codiAssignatura
 */
function questionarisPendentsDeSerCorregits(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getQuestionarisPendentsCorregirAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var panelHeading = "<div class=\"panel panel-warning\">" +
								   "<div class=\"panel-heading\">" + MIS_QST_PENDENTS_SER_CRR + "</div>" +
					            "<ul class=\"list-group\">";
        	var questionariDesc = "";
			var panelFooter = "</ul></div>";
			
        	var questionaris = $(xml).find("respostaQuestionari");
        	if (questionaris.length > 0) {
        		questionaris.each(function(){
        			var codiRq     = $(this).attr("codi");
        			var anonim     = $(this).attr("anonim");
        			var dataCnt    = new Date($(this).attr("dataContestacioFi"));
	        		var codi       = $(this).find("questionari").attr("codi");
	                var nom        = $(this).find("questionari").attr("nom");
	                var descripcio = $(this).find("questionari").attr("descripcio");
	                var dt         = $(this).find("questionari").attr("dificultatTeorica");
	                var dp         = $(this).find("questionari").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";
	                
	                var divAnonim;
	                if (anonim == "true") 	divAnonim = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_QST_ANONIM;
	                else 					divAnonim = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_QST_CONTESTATS_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_QST_CONTESTATS_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var infoQuestionari = "<div class=\"row\">" +
									      	  "<div class=\"col-lg-3\">" + MIS_QST_CONTESTATS_ROW_ASSIGNAT_PER + ": " + assignador + "</div>" +
								  			  "<div class=\"col-lg-1\">" + dtText + "</div>" +
								  			  "<div class=\"col-lg-3\">" + dpText + "</div>" +
								  			  "<div class=\"col-lg-2\">" + divAnonim + "</div>" +
								  			  "<div class=\"col-lg-3\">" + MIS_QST_CONTESTATS_ROW_CONTESTAT + ": " + formatData(dataCnt) + "</div>" +
							 			  "</div>";
	                
	                var textQuestionari = "<div class=\"row\">" +
										     "<div class=\"col-lg-12\"><h4>" + codi + " - " + nom + "</h4></div>" +
										  "</div>" +
										  "<div class=\"row\">" +
										     "<div class=\"col-lg-12\">" + descripcio + "</div>" +
										  "</div>";
	                
	                var questionariBox = infoQuestionari + textQuestionari;
	                
	                questionariDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaQuestionariContestat(" + codiRq + ", 1);\">" + questionariBox + "</a></li>";
	                
	        	});
        	}
        	
        	if (questionariDesc.length > 0) 
        		$("#consultaMaterialQuestionarisLlistatPendentsCorregir").html(panelHeading + questionariDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista per a veure els qüestionaris pendents de ser corregits");
        }
    });
}

/**
 * obtenim el llistat de qüestionaris que ha contestat l'alumne actual (el tenim a la sessió) 
 * pendents de ser revisats i actualitzam la vista 'consultarMaterialQuestionaris.jsp' amb 
 * aquesta informació
 * 
 * @param codiAssignatura
 */
function questionarisPendentsDeSerRevisats(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getQuestionarisPendentsRevisarAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-warning\">" +
								   "<div class=\"panel-heading\">" + MIS_QST_PENDENTS_SER_REV + "</div>" +
					            "<ul class=\"list-group\">";
        	var questionariDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var questionaris = $(xml).find("respostaQuestionari");
        	if (questionaris.length > 0) {
        		questionaris.each(function(){
        			var codiRq     = $(this).attr("codi");
        			var anonim     = $(this).attr("anonim");
        			var dataCrr    = new Date($(this).attr("dataCorreccio"));
	        		var codi       = $(this).find("questionari").attr("codi");
	                var nom        = $(this).find("questionari").attr("nom");
	                var descripcio = $(this).find("questionari").attr("descripcio");
	                var dt         = $(this).find("questionari").attr("dificultatTeorica");
	                var dp         = $(this).find("questionari").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";
	                
	                var numRec = getNumPreguntesRec($(this).find("questionari"));
	                
	                var divAnonim;
	                if (anonim == "true") 	divAnonim = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_QST_ANONIM;
	                else 					divAnonim = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_QST_CONTESTATS_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_QST_CONTESTATS_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var textCorregit = "";
	                if (numRec > 0) textCorregit = MIS_QST_CONTESTATS_ROW_CORREGIT;
	                else textCorregit = MIS_QST_CONTESTATS_ROW_CORREGIT_AUT;
	                
	                var infoQuestionari = "<div class=\"row\">" +
									      	 "<div class=\"col-lg-3\">" + MIS_QST_CONTESTATS_ROW_ASSIGNAT_PER + ": " + assignador + "</div>" +
								  			 "<div class=\"col-lg-1\">" + dtText + "</div>" +
								  			 "<div class=\"col-lg-3\">" + dpText + "</div>" +
								  			 "<div class=\"col-lg-2\">" + divAnonim + "</div>" +
								  			 "<div class=\"col-lg-3\">" + textCorregit + ": " + formatData(dataCrr) + "</div>" +
							 			  "</div>";
	                
	                var textQuestionari = "<div class=\"row\">" +
										     "<div class=\"col-lg-12\"><h4>" + codi + " - " + nom + "</h4></div>" +
										  "</div>" +
										  "<div class=\"row\">" +
										     "<div class=\"col-lg-12\">" + descripcio + "</div>" +
										  "</div>";
	                
	                var questionariBox = infoQuestionari + textQuestionari;
	                
	                questionariDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaQuestionariContestat(" + codiRq + ", 1);\">" + questionariBox + "</a></li>";
	        	});
        	}
        	
        	if (questionariDesc.length > 0) 
        		$("#consultaMaterialQuestionarisLlistatPendentsRevisar").html(panelHeading + questionariDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista per a veure els qüestionaris pendents de ser revisats");
        }
    });
}

/**
 * obtenim el llistat de qüestionaris contestats per l'alumne actual (el tenim a la sessió)
 * i actualitzam la vista 'consultarMaterialQuestionaris.jsp' amb aquesta informació
 * 
 * @param codiAssignatura
 */
function questionarisContestats(codiAssignatura) {
	$.ajax({
        url: "/egradus/contestacio/getQuestionarisContestatsAlumne.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var panelHeading = "<div class=\"panel panel-danger\">" +
								   "<div class=\"panel-heading\">" + MIS_QST_CONTESTATS + "</div>" +
					            "<ul class=\"list-group\">";
        	var questionariDesc = "";
			var panelFooter = "</ul></div>";
			
        	var questionaris = $(xml).find("respostaQuestionari");
        	if (questionaris.length > 0) {
        		questionaris.each(function(){
        			var codiRq     = $(this).attr("codi");
        			var anonim     = $(this).attr("anonim");
        			var dataCrr    = new Date($(this).attr("dataCorreccio"));
	                var dataRev    = new Date($(this).attr("dataRevisio"));
	        		var codi       = $(this).find("questionari").attr("codi");
	                var nom        = $(this).find("questionari").attr("nom");
	                var descripcio = $(this).find("questionari").attr("descripcio");
	                var dt         = $(this).find("questionari").attr("dificultatTeorica");
	                var dp         = $(this).find("questionari").attr("dificultatPractica");
	                var assignadorNom   = $(this).find("assignador").attr("nom");
	                var assignadorLli1  = $(this).find("assignador").attr("llinatge1");
	                var assignadorLli2  = $(this).find("assignador").attr("llinatge2");
	                var assignadorAlies = $(this).find("assignador").attr("alies");
	                var assignador = "<i>" + assignadorAlies + " (" + assignadorNom + " " + assignadorLli1 + " " + assignadorLli2 + ")</i>";  
	                
	                var numRec = getNumPreguntesRec($(this).find("questionari"));
	                var numRr  = getNumPreguntesRr($(this).find("questionari"));
	                
	                var divAnonim;
	                if (anonim == "true") 	divAnonim = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_QST_ANONIM;
	                else 					divAnonim = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_QST_CONTESTATS_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_QST_CONTESTATS_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var textCorregit = "";
	                if (numRec > 0) textCorregit = MIS_QST_CONTESTATS_ROW_CORREGIT;
	                else textCorregit = MIS_QST_CONTESTATS_ROW_CORREGIT_AUT;
	                
	                var textRevisat = "";
	                if (numRr > 0) textRevisat = "<br /> " + MIS_QST_CONTESTATS_ROW_REVISAT + ": " + formatData(dataRev);
	                
	                var infoQuestionari = "<div class=\"row\">" +
								               "<div class=\"col-lg-3\">" + MIS_QST_CONTESTATS_ROW_ASSIGNAT_PER + ": " + assignador + "</div>" +
							  			       "<div class=\"col-lg-1\">" + dtText + "</div>" +
							  			       "<div class=\"col-lg-3\">" + dpText + "</div>" +
							  			       "<div class=\"col-lg-2\">" + divAnonim + "</div>" +
							  			       "<div class=\"col-lg-3\">" + textCorregit + ": " + formatData(dataCrr) + textRevisat + "</div>" +
			                			   "</div>";
	                
	                var textQuestionari = "<div class=\"row\">" +
										      "<div class=\"col-lg-12\"><h4>" + codi + " - " + nom + "</h4></div>" +
										   "</div>" +
										   "<div class=\"row\">" +
										      "<div class=\"col-lg-12\">" + descripcio + "</div>" +
										   "</div>";
	                
	                var questionariBox = infoQuestionari + textQuestionari;
//	                var esAlumne = "S";
	                questionariDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaQuestionariContestat(" + codiRq + ", 1);\">" + questionariBox + "</a></li>";
	        	});
        	} else {
        		questionariDesc = "<div class=\"questionari-buit\">" + MIS_QST_CONTESTATS_BUIT + "</div>";
        	}
        	$("#consultaMaterialQuestionarisLlistatContestats").html(panelHeading + questionariDesc + panelFooter);
        },
        error:function(){
            alert("error per mostrar la vista dels qüestionaris contestats, corregits i revisats");
        }
    });
}

/**
 * Donat el troç d'XML d'un qüestionari, 
 * obtenim el número de preguntes REC que té
 * 
 * @param xmlQuestionari
 * @return num de preguntes REC
 */
function getNumPreguntesRec(xmlQuestionari) {
	var numPreguntes = 0;
	$(xmlQuestionari).find("pregunta").each(function(){
		var tipus = $(this).attr("tipus");
		if (tipus == TIPUS_PREGUNTA_REC) numPreguntes++;
	});
	return numPreguntes;
}

/**
 * Donat el troç d'XML d'un qüestionari, 
 * obtenim el número de preguntes amb 
 * Raonar Resposta que té
 * 
 * @param xmlQuestionari
 * @return num de preguntes RR
 */
function getNumPreguntesRr(xmlQuestionari) {
	var numPreguntes = 0;
	$(xmlQuestionari).find("pregunta").each(function(){
		var rr = $(this).attr("raonarResposta");
		if (rr == "true") numPreguntes++;
	});
	return numPreguntes;
}

/**
 * Mostra el detall d'una resposta-questionari prèviament contestada.
 * 
 * Donat que aquesta funció és reutilitzada en cas d'alumne i professor, 
 * el paràmetre 'esAlumne' pretén discernir si qui crida la funció és un
 * alumne o un professor. 
 * 
 * @param codiRespostaQuestionari
 * @param esAlumne prendrà per valor 1 o 0
 */
function mostraRespostaQuestionariContestat(codiRespostaQuestionari, esAlumne) {
	
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialConsultarRespostaQuestionari").show();
	
	$.ajax({
        url: "/egradus/contestacio/consultaRespostaQuestionari.do",
        type: "post",
        // afegim el codi del qüestionari
        data: "codiRespostaQuestionari=" + codiRespostaQuestionari, 
        success: function(xml) {
        	var rspDataInici = $(xml).find("respostaQuestionari").attr("dataContestacioInici");
        	var rspDataFi    = $(xml).find("respostaQuestionari").attr("dataContestacioFi");
        	var rspDataCrr   = $(xml).find("respostaQuestionari").attr("dataCorreccio");
        	var rspDataRev   = $(xml).find("respostaQuestionari").attr("dataRevisio");
        	var rspNota      = $(xml).find("respostaQuestionari").attr("nota");
        	var rspNotaAnt   = $(xml).find("respostaQuestionari").attr("notaAntiga");
        	var anonim       = $(xml).find("respostaQuestionari").attr("anonim");
//        	var rspTextCrr   = $(xml).find("respostaQuestionari").attr("textCorreccio");
        	
        	// Tenim un camp habilitat per al text de revisió d'un qüestionari, però de moment no l'empleam
//        	var rspTextRev   = $(xml).find("respostaQuestionari").attr("textRevisio"); 
			
        	var qstNom       = $(xml).find("questionari").attr("nom");
        	var qstDescrip 	 = $(xml).find("questionari").attr("descripcio");
        	var numRec       = $(xml).find("numRec").text(); // núm. de preguntes de tipus REC
        	var numRr        = $(xml).find("numRr").text();  // núm. de preguntes amb Raonar Resposta
        	
        	var asiNom 		 = $(xml).find("assignador").attr("nom");
        	var asiLli1 	 = $(xml).find("assignador").attr("llinatge1");
        	var asiLli2 	 = $(xml).find("assignador").attr("llinatge2");
        	var asiAlies     = $(xml).find("assignador").attr("alies");
        	var assignador   = asiAlies + " (" + asiNom + " " + asiLli1 + " " + asiLli2 + ")";
        	
        	var aluNom 		 = $(xml).find("alumne").attr("nom");
        	var aluLli1 	 = $(xml).find("alumne").attr("llinatge1");
        	var aluLli2 	 = $(xml).find("alumne").attr("llinatge2");
        	var aluAlies     = $(xml).find("alumne").attr("alies");
        	var alumne       = aluAlies + " (" + aluNom + " " + aluLli1 + " " + aluLli2 + ")";
        	
        	var dataInici = new Date(rspDataInici);
        	var dataFi = new Date(rspDataFi);
        	var dataCrr = new Date(rspDataCrr);
        	var dataRev = new Date(rspDataRev);
        	
        	$("#consultarRespostaQuestionariNom").html(qstNom + "<br />" + qstDescrip);
        	$("#consultarRespostaQuestionariDataInici").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_DATFIN + ": </b>" + formatData(dataFi) + "</div>");
        	$("#consultarRespostaQuestionariDataTemps").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_TEMPS + ": </b>" + diferenciaDates(dataFi, dataInici) + "</div>");
        	if (esAlumne == 1) { // ALUMNE
        		$("#consultarRespostaQuestionariContestatPer").empty();
        		$("#consultarRespostaQuestionariAssignatPer").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_ASSIG + ": </b>" + assignador + "</div>");
        		
        		// Qüestionari anònim o no
            	var divAnonim;
                if (anonim == "true") 	divAnonim = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_QST_ANONIM;
                else 					divAnonim = "";
                
        		$("#consultarRespostaQuestionariAnonim").html("<div class=\"col-lg-12\">" + divAnonim + "</div>");
        	} else if (esAlumne == 0) { // PROFESSOR
        		$("#consultarRespostaQuestionariAssignatPer").empty();
        		
        		// Si la resposta-questionari és anònim, no hem de veure l'alumne
        		var divAnonimOAlumne;
                if (anonim == "true") 	divAnonimOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
                else 					divAnonimOAlumne = alumne;
        		
        		$("#consultarRespostaQuestionariContestatPer").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_ALUMNE + ": </b>" + divAnonimOAlumne + "</div>");
        	}
        	
        	// Data de correcció (en cas que s'hagi corregit)
        	if (rspDataCrr.length > 0) {
        		var textRespostaDataCorreccio = "<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_CORREGIDA + ": </b>" + formatData(dataCrr);
        		// si està consultant la funció un alumne i la pregunta NO és REC, indicarem que es tracta d'una correcció automàtica
        		if (numRec == 0 && esAlumne == 1 && rspNota.length > 0) textRespostaDataCorreccio += " <i>(" + MIS_QST_CONS_CRR_AUTOMATICA + ")</i>"; 
        		textRespostaDataCorreccio += "</div>";
        		$("#consultarRespostaQuestionariDataCorreccio").html(textRespostaDataCorreccio);
        	} else $("#consultarRespostaQuestionariDataCorreccio").empty();
        	
        	
        	// si té notaAntiga (i és diferent a la nota actual):
        	// OBSERVACIÓ: Feim la comparativa a través de si les dates de correcció i revisió són iguals o no,
        	// ja que podria donar-se el cas de que corregim avui, revisam demà, i que les notes actual i antiga
        	// coincideixen igualment, i en aquest cas voldriem veure-ho!
        	if (rspNota.length > 0 && rspNotaAnt.length > 0 && (dataCrr.getTime() != dataRev.getTime())) {
        		$("#consultarRespostaQuestionariNotaRevisada").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_NOTA_REV + ": </b>" + rspNota + "</div>");
        		rspNota = rspNotaAnt;
        	} else $("#consultarRespostaQuestionariNotaRevisada").empty();
        	
        	// Nota (en cas que s'hagi corregit)
        	if (rspNota.length > 0) {
        		// si la nota pot variar degut a que el professor la revisi en un futur, avisam!
        		var pendentRevisio = "";
        		if (numRr > 0 && rspDataRev.length == 0) pendentRevisio = " <i>(" + MIS_QST_CONS_PENDENT_REVISIO + ")</i>";
        		$("#consultarRespostaQuestionariNota").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_NOTA + ": </b>" + rspNota + pendentRevisio + "</div>");
        	} else {
        		if (esAlumne == 1 && numRec > 0) $("#consultarRespostaQuestionariNota").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_NOTA + ": </b><i>" + MIS_QST_CONS_PENDENT_CORRECCIO + " " + assignador + "</i></div>");
        		else $("#consultarRespostaQuestionariNota").empty();
        	}
        	
        	// Data de revisió (en cas que s'hagi revisat). La mostram sempre i quan s'hagi revisat
        	// i corregit en moments diferents.
        	if (rspDataRev.length > 0 && (dataCrr.getTime() != dataRev.getTime())) {
        		var textRespostaDataRevisio = "<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_REVISADA + ": </b>" + formatData(dataRev);
        		textRespostaDataRevisio += "</div>";
        		$("#consultarRespostaQuestionariDataRevisio").html(textRespostaDataRevisio);
        	} else $("#consultarRespostaQuestionariDataRevisio").empty();
        	
        	// tractam les preguntes del qüestionari
        	var preguntes = $(xml).find("preguntes > pregunta");
        	if (preguntes.length > 0) {
        		var divPreguntes = "";
	        	var indexPregunta = 1;
	        	preguntes.each(function(){
	        		var codiPre  = $(this).attr("codi");
	        		var enunciat = $(this).attr("enunciat");
	        		var tipus    = $(this).attr("tipus");
	        		var rr       = $(this).attr("raonarResposta");
	        		var pes      = $(this).attr("pes");
	        		
	        		// -----------------------------------------------------------------------------------
	        		// Construcció dels panells per a consultar la resposta de cadascuna de les preguntes 
	        		// del qüestionari
	        		// -----------------------------------------------------------------------------------
	        		
	        		// dins el qüestionari que estam consultant, obtenim el codi de la resposta-pregunta 
	            	// associada a la pregunta amb codi codiPre (la que estam recorrent actualment en el bucle)
	            	var codiRespostaPregunta = getRespostaPreguntaByPregunta(codiPre, xml);
	            	
	            	// agafam el text de raonament de resposta, de la resposta REC, de la correcció i de la revisió.
	            	// també agafam la nota i la nota antiga (si la resposta-pregunta s'ha revisat, tendrem una 
	            	// nota antiga que plasmar)
	            	var rspTextRR  = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "textRaonarResposta");
	            	var rspTextRec = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "textResposta");
	            	var rspTextCrr = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "textCorreccio");
	            	var rspTextRev = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "textRevisio");
	            	var rpNota     = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "nota");
	            	var rpNotaAnt  = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "notaAntiga");
	            	
	            	// si té notaAntiga:
	            	var divNotaRevisada = "";
	            	if (rpNota.length > 0 && rpNotaAnt.length > 0) {
	            		divNotaRevisada = 	"<div id=\"consultarRespostaQuestionariPregunta" + codiPre + "NotaRevisada\" class=\"row text-left\">" +
	            								"<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_NOTA_REV + ": </b>" + rpNota + "</div>" +
	            							"</div>";
	            		rpNota = rpNotaAnt;
	            	}
	            	
	            	// nota de la pregunta
	            	if (tipus == TIPUS_PREGUNTA_REC) {
	            		if (rpNota.length == 0) rpNota = " <i>(" + MIS_QST_CONS_CRR_MANUAL_PENDENT + " " + assignador + ")</i>";
	            		else                    rpNota += " <i>(" + MIS_QST_CONS_CRR_MANUAL_CORREGIDA + " " + assignador + ")</i>";
	            	} else {
	            		if (rpNota.length > 0) rpNota += " <i>(" + MIS_QST_CONS_CRR_AUTOMATICA + ")</i>";
	            	}
	        		
	            	var panellEnunciat = 	"<div id=\"consultarRespostaQuestionariPregunta" + codiPre + "Nota\" class=\"row text-left\">" +
	            								"<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_NOTA + ": </b>" + rpNota + "</div>" +
	            							"</div>" +
	            							divNotaRevisada + 
		            						"<div class=\"row\">" +
												"<div class=\"col-lg-12\">" +
													"<div class=\"panel panel-default\">" +
														"<div id=\"consultarRespostaQuestionariPregunta" + codiPre + "Enunciat\" class=\"panel-body text-center\">" + enunciat + "</div>" +
													"</div>" +
												"</div>" +
											"</div>";
	            	
	            	// Text de la Correccio
	            	var divTextCorreccio = "";
	            	if (rspTextCrr.length > 0) {
	            		divTextCorreccio = 	"<div class=\"form-group\">" +
												"<label for=\"consultarRespostaQuestionariPregunta" + codiPre + "TextCorreccioInput\">" + MIS_PRE_CONS_RESPOSTA_CRR_REC + "</label>" +
												"<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaQuestionariPregunta" + codiPre + "TextCorreccioInput\" name=\"correccio\" disabled>" + rspTextCrr + "</textarea>" +
											"</div>";
	            	}
	            	
	            	// Text de la Revisió
	            	var divTextRevisio = "";
	            	if (rspTextRev.length > 0) {
	            		divTextRevisio = 	"<div class=\"form-group\">" +
												"<label for=\"consultarRespostaQuestionariPregunta" + codiPre + "TextRevisioInput\">" + MIS_PRE_CONS_RESPOSTA_REV + "</label>" +
												"<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaQuestionariPregunta" + codiPre + "TextRevisioInput\" name=\"revisio\" disabled>" + rspTextRev + "</textarea>" +
											"</div>";
	            	}
	            	
	            	// obtenim un llistat amb les opcions marcades d'aquesta pregunta
	            	var opcionsMarcades = getNodeFillByRespostaPregunta(codiRespostaPregunta, xml, "opcioMarcada");
	            	var lstOpcMarcades = [];
	            	var i = 0;
	            	if (opcionsMarcades.length > 0) {
	            		opcionsMarcades.each(function(){
	            			lstOpcMarcades[i++] = $(this).attr("codiOpcio");
	                	});
	            	}
	            	
	            	// obtenim les opcions i les pintam a la vista d'un determinat color en funció de si
	            	// la opció s'ha acertat o fallat i de si s'ha contestat o no
	            	var opcionsORec = "";
	            	var opcions = $(this).find("opcio");
	            	if (opcions.length > 0) {
	            		var opcionsDesc = "";
	            		var opcioResultatClass = "";
	            		opcions.each(function(){
	            			var codiOpcio = $(this).attr("codi");
	                		var textOpcio = $(this).attr("text");
	                		var correctaOpcio = $(this).attr("correcta");
	                		
	                		// la opció és correcta, i l'alumne l'ha marcada!
	            			if (correctaOpcio == "true" && opcioContestada(codiOpcio, lstOpcMarcades)) 
	            				opcioResultatClass = "egradus-opcio-contestada-acertada";
	            			
	            			// la opció és correcta, però l'alumne no l'ha marcada
	            			else if (correctaOpcio == "true" && !opcioContestada(codiOpcio, lstOpcMarcades)) 
	            				opcioResultatClass = "egradus-opcio-no-contestada-fallada";
	            			
	            			// la opció és falsa, i l'alumne l'ha marcada
	            			else if (correctaOpcio == "false" && opcioContestada(codiOpcio, lstOpcMarcades)) 
	            				opcioResultatClass = "egradus-opcio-contestada-fallada";
	            			
	            			// la opció és falsa, però l'alumne no l'ha marcada!
	            			else if (correctaOpcio == "false" && !opcioContestada(codiOpcio, lstOpcMarcades)) 
	            				opcioResultatClass = "egradus-opcio-no-contestada-acertada";
	            			
	            			var correctaIcon = "";
	            			var correctaClass = "";
	            	        if (correctaOpcio == "true") {
	            	        	correctaIcon = "<span class=\"glyphicon glyphicon-ok\"></span>";
	            	        	correctaClass = "egradus-opcio-correcta";
	            	        } else {
	            	        	correctaIcon = "<span class=\"glyphicon glyphicon-remove\"></span>";
	            	        	correctaClass = "egradus-opcio-incorrecta";
	            	        }
	            			
	            			opcionsDesc +=  "<div class=\"row\">" +
	            								"<div class=\"col-xs-9\">" +
			            							"<div class=\"panel panel-default\">" +
												        "<div id=\"consultarRespostaQuestionariPreguntaCodiOpcio" + codiOpcio + "\" class=\"panel-body text-center consultarRespostaQuestionariPregunta" + codiPre + "Opcions " + opcioResultatClass + "\">" + textOpcio + "</div>" +
													"</div>" + 
												"</div>" + 
												"<div class=\"col-xs-3\">" +
													"<div class=\"panel panel-default\">" +
														"<div id=\"consultarRespostaQuestionariPreguntaCodiOpcio" + codiOpcio + "correcta\" class=\"panel-body text-center " + correctaClass + "\">" + correctaIcon + "</div>" +
													"</div>" +
												"</div>" + 
											"</div>";
	            			
	                	});
	            		opcionsORec = opcionsDesc;
	            	} else {
	            		// Si és una pregunta de tipus REC hem d'habilitar un camp de text
	            		// per a donar una resposta a la pregunta
	            		if (tipus == TIPUS_PREGUNTA_REC) {
	            			opcionsORec = "<div class=\"form-group\">" +
											   "<label for=\"consultarRespostaQuestionariContestacioPreguntaRECInput" + codiPre + "\">" + MIS_PRE_INI_CNT_REC + "</label>" +
											   "<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaQuestionariContestacioPreguntaRECInput" + codiPre + "\" disabled>" + rspTextRec + "</textarea>" +
										  "</div>";
	                	}
	            	}
	            	
	            	// Raonar resposta
	            	var raonarResposta = "";
	            	if (rr == "true") {
	            		raonarResposta = "<div class=\"form-group\">" +
										     "<label for=\"consultarRespostaQuestionariRaonarRespostaInputPregunta" + codiPre + "\">" + MIS_PRE_INI_CNT_RR + "</label>" +
										     "<textarea rows=\"4\" class=\"form-control\" id=\"consultarRespostaQuestionariRaonarRespostaInputPregunta" + codiPre + "\" disabled>" + rspTextRR + "</textarea>" + 
									     "</div>";
	            	}
	        		
	        		// pintam al panell central les opcions o el camp de REC
	            	// i el camp de raonar resposta
	        		var panellCentral = "<div class=\"row\">" + 
											"<div class=\"col-sm-6 col-md-6 col-lg-6\">" + 
												"<div class=\"row\">" + 
													"<div id=\"consultarRespostaQuestionariPreguntaOpcionsOREC" + codiPre + "\" class=\"col-sm-12 consultarRespostaQuestionariPreguntaOpcionsOREC\">" + opcionsORec + "</div>" + 
												"</div>" + 
												"<div class=\"row\">" + 
													"<div id=\"consultarRespostaQuestionariPreguntaTextCorreccioREC" + codiPre + "\" class=\"col-sm-12\">" + divTextCorreccio + "</div>" + 
												"</div>" + 
											"</div>" + 
											"<div class=\"col-sm-6 col-md-6 col-lg-6\">" + 
												"<div class=\"row\">" + 
													"<div id=\"consultarRespostaQuestionariPreguntaRaonarResposta" + codiPre + "\" class=\"col-sm-12\">" + raonarResposta + "</div>" + 
												"</div>" + 
												"<div class=\"row\">" + 
													"<div id=\"consultarRespostaQuestionariPreguntaTextRevisio" + codiPre + "\" class=\"col-sm-12\">" + divTextRevisio + "</div>" + 
												"</div>" + 
											"</div>" + 
										"</div>";
	        		
	        		
	        		// ------------------------------------------------------------------------------------
	        		// Incorporació dels panells amb la info associada als divs que es despleguen i pleguen
	        		// ------------------------------------------------------------------------------------
	        		
	        		// informació associada de la pregunta
	        		var descPregunta = panellEnunciat + panellCentral;
	        		
	        		// panell que sempre està visible
	        		var divEnunciat = "<div id=\"consultarRespostaQuestionariIcona" + codiPre + "\" class=\"col-xs-1 col-sm-1 col-md-1 col-lg-1\"><span class=\"glyphicon glyphicon-chevron-up\"></span></div>" +
	        						  "<div class=\"col-xs-9 col-sm-9 col-md-9 col-lg-9\">" + MIS_QST_INI_CNT_PREGUNTA + " " + indexPregunta++ + "</div>" +
	        						  "<div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2\">" + MIS_QST_INI_CNT_PREGUNTA_PES + ": " + pes + "</div>";
	        		
	        		// div sencer
	        	    var boxPregunta = "<div class=\"row\">" + 
							              "<div class=\"col-lg-12\">" + 
							        		  "<div class=\"panel panel-default\">" + 
								        		  "<div class=\"panel-heading\">" +
								        		      "<a href=\"javascript: desplegaPreguntaConsultaResposta(" + codiPre + ");\">" + 
								        		          "<div id=\"consultarRespostaQuestionariPregunta" + codiPre + "\" class=\"panel-body text-center\">" + divEnunciat + "</div>" +
								        		      "</a>" + 
								        		  "</div>" + 
							        			  "<div id=\"consultarRespostaQuestionariPreguntaInfoAssociada" + codiPre + "\" class=\"panel-body consultarRespostaQuestionariDescPregunta\">" + descPregunta + "</div>" + 
							        		  "</div>" + 
							        	  "</div>" + 
							          "</div>";
	        		
	        		divPreguntes += boxPregunta;
	        		
	        	});
	        	
	        	// afegim les preguntes
	        	$("#consultarRespostaQuestionariPreguntes").html(divPreguntes);
	        	
	        	// mostram, d'entrada, la informació associada de totes les preguntes
	        	// per, posteriorment, clicar damunt una i que es plegui o desplegui segons toqui
	        	$(".consultarRespostaQuestionariDescPregunta").show();
        	}
        },
        error:function(){
        	alert("error mostra resposta questionari contestat");
        }
    });
}

/**
 * Funció privada que, donat un codi de Pregunta, retorna el codi de la resposta-pregunta
 * associada a la pregunta. 
 * 
 * Realitzarem la cerca dins l'xml passat per paràmetre. Correspon a la vista:
 * respostaQuestionariXml.jsp
 * 
 * @param codiPre
 * @param xml
 */
function getRespostaPreguntaByPregunta(codiPre, xml) {
	return $(xml).find("respostaPregunta > pregunta[codi=" + codiPre + "]").parent().attr("codi");
}

/**
 * Funció privada que, donat un codi de resposta-pregunta, retorna el camp amb valor
 * passat per paràmetre de l'element resposta-pregunta identificat pel codi
 * 
 * Realitzarem la cerca dins l'xml passat per paràmetre. Correspon a la vista:
 * respostaQuestionariXml.jsp
 * 
 * @param codiRp
 * @param xml
 * @param valor
 * 			valor de l'atribut de l'XML que agafam
 * @returns
 */
function getValorAtributByRespostaPregunta(codiRp, xml, valor) {
	return $(xml).find("respostaPregunta[codi=" + codiRp + "]").attr(valor);
}

/**
 * Funció privada que, donat un codi de resposta-pregunta, retorna el node
 * fill passat per paràmetre de l'element resposta-pregunta identificat pel codi
 * 
 * Realitzarem la cerca dins l'xml passat per paràmetre. Correspon a la vista:
 * respostaQuestionariXml.jsp
 * 
 * @param codiRp
 * @param xml
 * @param nodeFill
 */
function getNodeFillByRespostaPregunta(codiRp, xml, nodeFill) {
	return $(xml).find("respostaPregunta[codi=" + codiRp + "]").find(nodeFill);
} 

/**
 * Inicia el procés de contestar una pregunta
 * 
 * @param codiRespostaPregunta
 */
function iniciaContestacioPregunta(codiRespostaPregunta) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialContestarPregunta").show();
	
	// Assignam el codi de la resposta-pregunta per a poder agafar-lo posteriorment
	$("#contestarPreguntaCodiRespostaPregunta").val(codiRespostaPregunta);
	
	$.ajax({
        url: "/egradus/contestacio/contestaPreguntaInici.do",
        type: "post",
        data: "codiRespostaPregunta=" + codiRespostaPregunta, 
        success: function(xml) {
        	
        	var rspDataInici = $(xml).find("respostaPregunta").attr("dataContestacioInici");
        	var anonima      = $(xml).find("respostaPregunta").attr("anonima");
        	
//        	var preCodi 	 = $(xml).find("pregunta").attr("codi");
        	var preEnunciat  = $(xml).find("pregunta").attr("enunciat");
        	var preTipus 	 = $(xml).find("pregunta").attr("tipus");
        	var preRr 		 = $(xml).find("pregunta").attr("raonarResposta");
        	
//        	var asiNom 		 = $(xml).find("assignador").attr("nom");
//        	var asiLli1 	 = $(xml).find("assignador").attr("llinatge1");
//        	var asiLli2 	 = $(xml).find("assignador").attr("llinatge2");
        	
        	var dataInici = new Date(rspDataInici);
        	
        	$("#contestarPreguntaEnunciat").html(preEnunciat);
        	$("#contestarPreguntaDataInici").html(MIS_PRE_INI_CNT_DATA + ": " + formatData(dataInici));
        	
        	// buidam el camp de Raonar Resposta que pugui haver aparegut en una contestació de pregunta anterior
        	$("#contestarPreguntaRaonarResposta").empty();
        	
        	var opcions = $(xml).find("opcio");
        	if (opcions.length > 0) {
        		var opcionsDesc = "";
        		opcions.each(function(){
        			opcionsDesc +=  "<div class=\"panel panel-default\">" +
    									"<a href=\"javascript: marcaOpcio(" + $(this).attr("codiOpcio") + ", \'" + preTipus + "\');\"><div id=\"contestarPreguntaCodiOpcio" + $(this).attr("codiOpcio") + "\" class=\"panel-body text-center contestarPreguntaOpcions\">" + $(this).attr("text") + "</div></a>" +
    								"</div>";
            	});
        		$("#contestarPreguntaOpcionsOREC").html(opcionsDesc);
        	} else {
        		// Si és una pregunta de tipus REC hem d'habilitar un camp de text
        		// per a donar una resposta a la pregunta
        		if (preTipus == TIPUS_PREGUNTA_REC) {
            		$("#contestarPreguntaOpcionsOREC").html("<div class=\"form-group\">" +
    															"<label for=\"contestarPreguntaContestacioPreguntaRECInput\">" + MIS_PRE_INI_CNT_REC + "</label>" +
    															"<textarea rows=\"4\" class=\"form-control\" id=\"contestarPreguntaContestacioPreguntaRECInput\" name=\"respostaREC\" placeholder=\"" + MIS_PRE_INI_CNT_REC_PLH + "\"></textarea>" +
    														"</div>");
            	}
        	}
        	
        	// Raonar resposta
        	if (preRr == "true") {
        		$("#contestarPreguntaRaonarResposta").html("<div class=\"form-group\">" +
															   "<label for=\"contestarPreguntaRaonarRespostaInput\">" + MIS_PRE_INI_CNT_RR + "</label>" +
															   "<textarea rows=\"4\" class=\"form-control\" id=\"contestarPreguntaRaonarRespostaInput\" name=\"raonarresposta\" placeholder=\"" + MIS_PRE_INI_CNT_RR_PLH + "\"></textarea>" + 
														   "</div>");
        	}
        	
        	// Pregunta anònima o no
        	var divAnonima;
            if (anonima == "true") 	divAnonima = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_PRE_ANONIMA;
            else 					divAnonima = "";
            $("#contestarPreguntaAnonima").html(divAnonima);
        	
        },
        error:function(){
        	alert("error inicia contestació pregunta");
        }
    });
}

/**
 * Context: s'està contestant la pregunta. Aquesta pregunta té opcions
 * per marcar. Aquesta funció s'encarrega d'indicar quina opció està
 * marcada i quina no. Per marcar una opció basta fer clic sobre la
 * opció. Per desmarcar-la, basta tornar a fer-li clic.
 * 
 * Si el tipus de pregunta és VOF o ES1 només podrà haver-hi una opció
 * marcada al mateix temps com a màxim. Si és ESN, podran haver-hi vàries.
 * 
 * @param codiOpcio
 * @param preTipus
 */
function marcaOpcio(codiOpcio, preTipus) {
	var idOpcio = "#contestarPreguntaCodiOpcio" + codiOpcio;
	if  ($(idOpcio).hasClass("egradus-opcio-marcada")) $(idOpcio).removeClass("egradus-opcio-marcada");
	else {
		// si la pregunta és ES1 o VOF, esborram qualsevol de les opcions que hagi estat marcada amb anterioritat
		// (només n'hi pot haver marcada una al mateix temps com a màxim)
		if (preTipus == TIPUS_PREGUNTA_ES1 || preTipus == TIPUS_PREGUNTA_VOF) $(".contestarPreguntaOpcions").removeClass("egradus-opcio-marcada");
		$(idOpcio).addClass("egradus-opcio-marcada");
	}
}

/**
 * Context: s'està contestant una pregunta del qüestionari. Aquesta pregunta 
 * té opcions per marcar. Aquesta funció s'encarrega d'indicar quina opció 
 * està marcada i quina no. Per marcar una opció basta fer clic sobre la
 * opció. Per desmarcar-la, basta tornar a fer-li clic.
 * 
 * Si el tipus de pregunta és VOF o ES1 només podrà haver-hi una opció
 * marcada al mateix temps com a màxim. Si és ESN, podran haver-hi vàries.
 * 
 * @param codiPregunta
 * @param codiOpcio
 * @param preTipus
 */
function marcaOpcioQuestionari(codiPregunta, codiOpcio, preTipus) {
	var idOpcio = "#contestarQuestionariPreguntaCodiOpcio" + codiOpcio;
	if  ($(idOpcio).hasClass("egradus-opcio-marcada")) $(idOpcio).removeClass("egradus-opcio-marcada");
	else {
		// si la pregunta és ES1 o VOF, esborram qualsevol de les opcions que hagi estat marcada amb anterioritat
		// (només n'hi pot haver marcada una al mateix temps com a màxim)
		if (preTipus == TIPUS_PREGUNTA_ES1 || preTipus == TIPUS_PREGUNTA_VOF) $(".contestarQuestionariPregunta" + codiPregunta + "Opcions").removeClass("egradus-opcio-marcada");
		$(idOpcio).addClass("egradus-opcio-marcada");
	}
}

/**
 * S'encarrega de finalitzar el procés de contestar una pregunta.
 */
function finalitzaContestacioPregunta() {
	// assignam el codi de la resposta-pregunta que hem inserit en el moment d'iniciar la 
	// contestació de la pregunta a la llista de paràmetres que passarem a la petició AJAX
	var values = "codiRespostaPregunta=" + $("#contestarPreguntaCodiRespostaPregunta").val();
	
	// agafam el codi de les opcions marcades
	var opcionsMarcades = $("#contestarPreguntaOpcionsOREC").find(".egradus-opcio-marcada");
	if (opcionsMarcades.length > 0) {
		var paramIdOpcio = "";
		opcionsMarcades.each(function(){
			paramIdOpcio += "&idopcionsmarcades=" + $(this).attr("id").substr  // l'id és de la forma "contestarPreguntaCodiOpcio6" i volem només el 6
			                                        (
			                                    	    ("contestarPreguntaCodiOpcio").length
			                                        ,   $(this).attr("id").length - ("contestarPreguntaCodiOpcio").length
			                                        );
		});
		// assignam els codis de les opcions marcades a la llista de paràmetres que passarem a la petició AJAX
		values += paramIdOpcio;
	}
	
	// assignam els camps de text omplits en el formulari a la llista de paràmetres que passarem a la petició AJAX
	values += "&" + $("#contestarPreguntaForm").serialize();
	
	$.ajax({
        url: "/egradus/contestacio/contestaPreguntaFi.do",
        type: "post",
        data: values, 
        success: function(xml) {
        	var codi     = $(xml).find("pregunta").attr("codi");
        	var enunciat = $(xml).find("pregunta").attr("enunciat");
        	
        	// mostram el modal window
        	$("#contestarPreguntaModalBoto").attr("onclick", "habilitaPreguntes();");
        	$("#contestarPreguntaModalMissatge").html(CNT_PREGUNTA_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + CNT_PREGUNTA_MODAL_MIS2);
        	$("#contestarPreguntaModal").modal("show");
        },
        error:function(){
        	alert("error finalitza contestació pregunta");
        }
    });
}

/**
 * Inicia el procés de contestar un qüestionari
 * 
 * @param codiRespostaQuestionari
 */
function iniciaContestacioQuestionari(codiRespostaQuestionari) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialContestarQuestionari").show();
	
	// Assignam el codi de la resposta-questionari per a poder agafar-lo posteriorment
	$("#contestarQuestionariCodiRespostaQuestionari").val(codiRespostaQuestionari);
	
	$.ajax({
        url: "/egradus/contestacio/contestaQuestionariInici.do",
        type: "post",
        data: "codiRespostaQuestionari=" + codiRespostaQuestionari, 
        success: function(xml) {
        	
        	var rspDataInici = $(xml).find("respostaQuestionari").attr("dataContestacioInici");
        	var anonim       = $(xml).find("respostaQuestionari").attr("anonim");
        	
//        	var qstCodi 	  = $(xml).find("questionari").attr("codi");
        	var qstNom        = $(xml).find("questionari").attr("nom");
        	var qstDescripcio = $(xml).find("questionari").attr("descripcio");
        	
//        	var asiNom 		 = $(xml).find("assignador").attr("nom");
//        	var asiLli1 	 = $(xml).find("assignador").attr("llinatge1");
//        	var asiLli2 	 = $(xml).find("assignador").attr("llinatge2");
        	
        	$("#contestarQuestionariNom").html(qstNom + "<br />" + qstDescripcio);
        	$("#contestarQuestionariDataInici").html(MIS_QST_INI_CNT_DATA + ": " + rspDataInici);
        	
        	// Qüestionari anònim o no
        	var divAnonim;
            if (anonim == "true") 	divAnonim = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_QST_ANONIM;
            else 					divAnonim = "";
            $("#contestarQuestionariAnonim").html(divAnonim);
        	
        	// tractam les preguntes del qüestionari
        	var preguntes = $(xml).find("preguntes > pregunta");
        	if (preguntes.length > 0) {
	        	var divPreguntes = "";
	        	var indexPregunta = 1;
	        	preguntes.each(function(){
	        		var codiPre  = $(this).attr("codi");
	        		var enunciat = $(this).attr("enunciat");
	        		var tipus    = $(this).attr("tipus");
	        		var rr       = $(this).attr("raonarResposta");
	        		var pes      = $(this).attr("pes");
	        		
	        		// -----------------------------------------------------------------------------------
	        		// Construcció dels panells per a contestar cadascuna de les preguntes del qüestionari
	        		// -----------------------------------------------------------------------------------
	            	var panellEnunciat = "<div class=\"row\">" +
											"<div class=\"col-lg-12\">" +
												"<div class=\"panel panel-default\">" +
													"<div id=\"contestarQuestionariPregunta" + codiPre + "Enunciat\" class=\"panel-body text-center\">" + enunciat + "</div>" +
												"</div>" +
											"</div>" +
										"</div>";
	            	
	            	
	            	// buidam els camps de Raonar Resposta que puguin haver aparegut en alguna contestació de pregunta anterior
	            	$(".contestarQuestionariPreguntesRaonarResposta").empty();
	            	
	            	var opcionsORec = "";
	            	var opcions = $(this).find("opcio");
	            	if (opcions.length > 0) {
	            		var opcionsDesc = "";
	            		opcions.each(function(){
	            			opcionsDesc +=  "<div class=\"panel panel-default\">" +
	        									"<a href=\"javascript: marcaOpcioQuestionari(" + codiPre + ", " + $(this).attr("codi") + ", \'" + tipus + "\');\"><div id=\"contestarQuestionariPreguntaCodiOpcio" + $(this).attr("codi") + "\" class=\"panel-body text-center contestarQuestionariPregunta" + codiPre + "Opcions\">" + $(this).attr("text") + "</div></a>" +
	        								"</div>";
	                	});
	            		opcionsORec = opcionsDesc;
	            	} else {
	            		// Si és una pregunta de tipus REC hem d'habilitar un camp de text
	            		// per a donar una resposta a la pregunta
	            		if (tipus == TIPUS_PREGUNTA_REC) {
	            			opcionsORec = "<div class=\"form-group\">" +
											   "<label for=\"contestarQuestionariContestacioPreguntaRECInput" + codiPre + "\">" + MIS_PRE_INI_CNT_REC + "</label>" +
											   "<textarea rows=\"4\" class=\"form-control\" id=\"contestarQuestionariContestacioPreguntaRECInput" + codiPre + "\" name=\"respostaREC\" placeholder=\"" + MIS_PRE_INI_CNT_REC_PLH + "\"></textarea>" +
										  "</div>";
	                	}
	            	}
	            	
	            	// Raonar resposta
	            	var raonarResposta = "";
	            	if (rr == "true") {
	            		raonarResposta = "<div class=\"form-group\">" +
										     "<label for=\"contestarQuestionariRaonarRespostaInputPregunta" + codiPre + "\">" + MIS_PRE_INI_CNT_RR + "</label>" +
										     "<textarea rows=\"4\" class=\"form-control\" id=\"contestarQuestionariRaonarRespostaInputPregunta" + codiPre + "\" name=\"raonarresposta\" placeholder=\"" + MIS_PRE_INI_CNT_RR_PLH + "\"></textarea>" + 
									     "</div>";
	            	}
	        		
	        		// pintam al panell central les opcions o el camp de REC
	            	// i el camp de raonar resposta
	        		var panellCentral = "<div class=\"row\">" +
											"<div id=\"contestarQuestionariPreguntaOpcionsOREC" + codiPre + "\" class=\"col-sm-6 col-md-6 col-lg-6 contestarQuestionariPreguntaOpcionsOREC\">" + opcionsORec + "</div>" +
											"<div id=\"contestarQuestionariPreguntaRaonarResposta" + codiPre + "\" class=\"col-sm-6 col-md-6 col-lg-6\">" + raonarResposta + "</div>" +
										"</div>";
	        		
	        		// ------------------------------------------------------------------------------------
	        		// Incorporació dels panells amb la info associada als divs que es despleguen i pleguen
	        		// ------------------------------------------------------------------------------------
	        		
	        		// informació associada de la pregunta
	        		var descPregunta = panellEnunciat + panellCentral;
	        		
	        		// panell que sempre està visible
	        		var divEnunciat = "<div id=\"contestarQuestionariIcona" + codiPre + "\" class=\"col-xs-1 col-sm-1 col-md-1 col-lg-1\"><span class=\"glyphicon glyphicon-chevron-down\"></span></div>" +
	        						  "<div class=\"col-xs-9 col-sm-9 col-md-9 col-lg-9\">" + MIS_QST_INI_CNT_PREGUNTA + " " + indexPregunta++ + "</div>" +
	        						  "<div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2\">" + MIS_QST_INI_CNT_PREGUNTA_PES + ": " + pes + "</div>";
	        		
	        		// div sencer
	        	    var boxPregunta = "<div class=\"row\">" + 
							              "<div class=\"col-lg-12\">" + 
							        		  "<div class=\"panel panel-default\">" + 
								        		  "<div class=\"panel-heading\">" +
								        		      "<a href=\"javascript: desplegaPregunta(" + codiPre + ");\">" + 
								        			     "<div id=\"contestarQuestionariPregunta" + codiPre + "\" class=\"panel-body text-center\">" + divEnunciat + "</div>" +
								        			  "</a>" +
								        		  "</div>" + 
							        			  "<div id=\"contestarQuestionariPreguntaInfoAssociada" + codiPre + "\" class=\"panel-body contestarQuestionariDescPregunta\">" + descPregunta + "</div>" + 
							        		  "</div>" + 
							        	  "</div>" + 
							          "</div>";
	        		
	        		divPreguntes += boxPregunta;
	        	});
	        	
	        	// afegim les preguntes
	        	$("#contestarQuestionariPreguntes").html(divPreguntes);
	        	
	        	// ocultam, d'entrada, la informació associada de totes les preguntes
	        	// per, posteriorment, clicar damunt una i que es desplegui per poder contestar-la
	        	$(".contestarQuestionariDescPregunta").hide();
        	}
        },
        error:function(){
        	alert("error inicia contestació qüestionari");
        }
    });
}

/**
 * funció privada que desplega o plega la informació associada d'una pregunta
 * (quan estam en plena contestació del qüestionari) i, a més, actualitza
 * l'icona
 * 
 * @param codiPregunta
 */
function desplegaPregunta(codiPregunta) {
	var divPregunta = $("#contestarQuestionariPreguntaInfoAssociada" + codiPregunta);
	var divIcona = $("#contestarQuestionariIcona" + codiPregunta);
	if (divPregunta.is(":visible")) {
		divPregunta.hide();
		divIcona.html("<span class=\"glyphicon glyphicon-chevron-down\"></span>");
	} else {
		divPregunta.show();
		divIcona.html("<span class=\"glyphicon glyphicon-chevron-up\"></span>");
	}
}

/**
 * funció privada que desplega o plega la informació associada d'una pregunta
 * (quan estam consultant la resposta del qüestionari) i, a més, actualitza
 * l'icona
 * 
 * @param codiPregunta
 */
function desplegaPreguntaConsultaResposta(codiPregunta) {
	var divPregunta = $("#consultarRespostaQuestionariPreguntaInfoAssociada" + codiPregunta);
	var divIcona = $("#consultarRespostaQuestionariIcona" + codiPregunta);
	if (divPregunta.is(":visible")) {
		divPregunta.hide();
		divIcona.html("<span class=\"glyphicon glyphicon-chevron-down\"></span>");
	} else {
		divPregunta.show();
		divIcona.html("<span class=\"glyphicon glyphicon-chevron-up\"></span>");
	}
}

/**
 * S'encarrega de finalitzar el procés de contestar un qüestionari.
 */
function finalitzaContestacioQuestionari() {
	// assignam el codi de la resposta-questionari que hem inserit en el moment d'iniciar la 
	// contestació del qüestionari a la llista de paràmetres que passarem a la petició AJAX
	var values = "codiRespostaQuestionari=" + $("#contestarQuestionariCodiRespostaQuestionari").val();
	
	// agafam el codi de les opcions marcades
	var opcionsMarcades = $(".contestarQuestionariPreguntaOpcionsOREC").find(".egradus-opcio-marcada");
	if (opcionsMarcades.length > 0) {
		var paramIdOpcio = "";
		opcionsMarcades.each(function(){
			paramIdOpcio += "&idopcionsmarcades=" + $(this).attr("id").substr  // l'id és de la forma "contestarQuestionariPreguntaCodiOpcio6" i volem només el 6
			                                        (
			                                    	    ("contestarQuestionariPreguntaCodiOpcio").length
			                                        ,   $(this).attr("id").length - ("contestarQuestionariPreguntaCodiOpcio").length
			                                        );
		});
		// assignam els codis de les opcions marcades a la llista de paràmetres que passarem a la petició AJAX
		values += paramIdOpcio;
	}
	
	// assignam els camps de text omplits en el formulari a la llista de paràmetres que passarem a la petició AJAX
	values += "&" + $("#contestarQuestionariForm").serialize();
	
	$.ajax({
        url: "/egradus/contestacio/contestaQuestionariFi.do",
        type: "post",
        data: values, 
        success: function(xml) {
        	var codi = $(xml).find("questionari").attr("codi");
        	var nom  = $(xml).find("questionari").attr("nom");
        	
        	// mostram el modal window
        	$("#contestarQuestionariModalBoto").attr("onclick", "habilitaQuestionaris();");
        	$("#contestarQuestionariModalMissatge").html(CNT_QUESTIONARI_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + CNT_QUESTIONARI_MODAL_MIS2);
        	$("#contestarQuestionariModal").modal("show");
        },
        error:function(){
        	alert("error finalitza contestació qüestionari");
        }
    });
}






