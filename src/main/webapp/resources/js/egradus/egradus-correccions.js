/*
 * egradus-correccions.js
 * ----------------------------------------------------------------------------------------
 * 
 * Funcionalitats per a que els professors puguin corregir les preguntes i qüestionaris que
 * els alumnes de la seva assignatura han contestat. També està contemplada la revisió tant
 * de preguntes com de qüestionaris.
 * 
 */

// habilita la vista correcció
function habilitaCorreccioPre() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialConsultaCorreccioPregunta").show();
	
	// netejam els llistats de preguntes
	$("#consultaCorreccionsPreguntesLlistatPendents").empty();
	$("#consultaCorreccionsPreguntesLlistatCorregides").empty();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya de correcció, per saber que ha estat seleccionada pel professor
	$("#assignaturaMaterialCorreccio").addClass("egradus-color-pestanya");
	
	$("#corregirPreguntaError").empty();
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// obtenim els llistats de preguntes REC pendents de corregir, pendents de revisar i preguntes REC corregides
	preguntesPendentsDeCorregir(codiAssignatura);
	preguntesCorregides(codiAssignatura);
}

function habilitaCorreccioQst() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialConsultaCorreccioQuestionari").show();
	
	// netejam els llistats de qüestionaris
	$("#consultaCorreccionsQuestionarisLlistatPendents").empty();
	$("#consultaCorreccionsQuestionarisLlistatCorregits").empty();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya de correcció, per saber que ha estat seleccionada pel professor
	$("#assignaturaMaterialCorreccio").addClass("egradus-color-pestanya");
	
	$("#corregirQuestionariError").empty();
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// obtenim els llistats de qüestionaris pendents de corregir, pendents de revisar i corregits
	questionarisPendentsDeCorregir(codiAssignatura);
	questionarisCorregits(codiAssignatura);
}

/**
 * obtenim el llistat de preguntes REC pendents de corregir o bé que tenguin RR
 * pendents de revisar, i actualitzam la vista amb aquesta informació
 * 
 * @param codiAssignatura
 */
function preguntesPendentsDeCorregir(codiAssignatura) {
	$.ajax({
        url: "/egradus/correccio/getPreguntesPendents.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-success\">" +
								   "<div class=\"panel-heading\">" + MIS_PRE_PENDENTS_CRR_O_REV + "</div>" +
					            "<ul class=\"list-group\">";
        	var preguntaDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var preguntes = $(xml).find("respostaPregunta");
        	if (preguntes.length > 0) {
        		preguntes.each(function(){
        			var codiRp   = $(this).attr("codi");
        			var anonima  = $(this).attr("anonima");
        			var dataCnt  = dataTextualNavegador($(this).attr("dataContestacioFi"));
	        		var codi     = $(this).find("pregunta").attr("codi");
	                var enunciat = $(this).find("pregunta").attr("enunciat");
	                var tipus    = $(this).find("pregunta").attr("tipus");
	                var rr       = $(this).find("pregunta").attr("raonarResposta");
	                var dt       = $(this).find("pregunta").attr("dificultatTeorica");
	                var dp       = $(this).find("pregunta").attr("dificultatPractica");
	                var alumneNom   = $(this).find("alumne").attr("nom");
	                var alumneLli1  = $(this).find("alumne").attr("llinatge1");
	                var alumneLli2  = $(this).find("alumne").attr("llinatge2");
	                var alumneAlies = $(this).find("alumne").attr("alies");
	                var alumne = "<i>" + alumneAlies + " (" + alumneNom + " " + alumneLli1 + " " + alumneLli2 + ")</i>";
	                
	                var rrLlapis;
	                if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
	                else              rrLlapis = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_PRE_PENDENTS_CORREGIR_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_PRE_PENDENTS_CORREGIR_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                // Si la resposta-pregunta és anònima, no hem de veure l'alumne
	        		var divAnonimaOAlumne;
	                if (anonima == "true") 	divAnonimaOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
	                else 					divAnonimaOAlumne = alumne;
	                
	                var infoPregunta = "<div class=\"row\">" +
		                			      "<div class=\"col-lg-3\">" + MIS_PRE_PENDENTS_CORREGIR_ROW_CONTESTADA_PER + ": " + divAnonimaOAlumne + "</div>" +
		                			      "<div class=\"col-lg-1\">" + MIS_PRE_PENDENTS_CORREGIR_ROW_TIPUS + ": " + tipus + "</div>" +
		                			      "<div class=\"col-lg-1\">" + dtText + "</div>" +
		                			      "<div class=\"col-lg-3\">" + dpText + "</div>" +
		                			      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
		                			      "<div class=\"col-lg-3\">" + MIS_PRE_PENDENTS_CORREGIR_ROW_REBUDA + ": " + formatData(dataCnt) + "</div>" +
		                			   "</div>";
	                
	                var textPregunta = "<div class=\"row\">" +
									      "<div class=\"col-lg-12\"><h4>" + codi + " - " + enunciat + "</h4></div>" +
									   "</div>";
	                
	                var preguntaBox = infoPregunta + textPregunta;
	                
	                preguntaDesc += "<li class=\"list-group-item\"><a href=\"javascript: iniciaCorreccioPregunta(" + codiRp + ");\">" + preguntaBox + "</a></li>";
	        	});
        		
        		$("#consultaCorreccionsPreguntesLlistatPendents").html(panelHeading + preguntaDesc + panelFooter);
        	} else {
        		preguntaDesc = "<div class=\"pregunta-buit\">" + MIS_PRE_PENDENTS_CRR_O_REV_BUIT + "</div>";
        		$("#consultaCorreccionsPreguntesLlistatPendents").html(panelHeading + preguntaDesc + panelFooter);
        	}
        },
        error:function(){
            alert("error obtenint les preguntes REC pendents de corregir");
        }
    });
}

/**
 * obtenim el llistat de preguntes REC corregides pel professor assignador actual (el tenim a la sessió)
 * i actualitzam la vista 'consultarCorreccionsPregunta.jsp' amb aquesta informació
 * 
 * @param codiAssignatura
 */
function preguntesCorregides(codiAssignatura) {
	$.ajax({
        url: "/egradus/correccio/getPreguntesCorregidesProfessor.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-danger\">" +
								   "<div class=\"panel-heading\">" + MIS_PRE_CRR_O_REV + "</div>" +
					            "<ul class=\"list-group\">";
        	var preguntaDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var preguntes = $(xml).find("respostaPregunta");
        	if (preguntes.length > 0) {
        		preguntes.each(function(){
        			var codiRp   = $(this).attr("codi");
        			var anonima  = $(this).attr("anonima");
        			var dataCrr  = dataTextualNavegador($(this).attr("dataCorreccio"));
	                var dataRev  = dataTextualNavegador($(this).attr("dataRevisio"));
	        		var codi     = $(this).find("pregunta").attr("codi");
	                var enunciat = $(this).find("pregunta").attr("enunciat");
	                var tipus    = $(this).find("pregunta").attr("tipus");
	                var rr       = $(this).find("pregunta").attr("raonarResposta");
	                var dt       = $(this).find("pregunta").attr("dificultatTeorica");
	                var dp       = $(this).find("pregunta").attr("dificultatPractica");
	                var alumneNom   = $(this).find("alumne").attr("nom");
	                var alumneLli1  = $(this).find("alumne").attr("llinatge1");
	                var alumneLli2  = $(this).find("alumne").attr("llinatge2");
	                var alumneAlies = $(this).find("alumne").attr("alies");
	                var alumne = "<i>" + alumneAlies + " (" + alumneNom + " " + alumneLli1 + " " + alumneLli2 + ")</i>";
	                
	                var rrLlapis;
	                if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
	                else              rrLlapis = "";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_PRE_CORREGIDES_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_PRE_CORREGIDES_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                var textCorregida = "";
	                if (tipus == TIPUS_PREGUNTA_REC) textCorregida = MIS_PRE_CONTESTADES_ROW_CORREGIDA;
	                else textCorregida = MIS_PRE_CONTESTADES_ROW_CORREGIDA_AUT;
	                
	                var textRevisada = "";
	                if (rr == "true") textRevisada = "<br /> " + MIS_PRE_CONTESTADES_ROW_REVISADA + ": " + formatData(dataRev);
	                
	                // Si la resposta-pregunta és anònima, no hem de veure l'alumne
	        		var divAnonimaOAlumne;
	                if (anonima == "true") 	divAnonimaOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
	                else 					divAnonimaOAlumne = alumne;
	                
	                var infoPregunta = "<div class=\"row\">" +
							               "<div class=\"col-lg-3\">" + MIS_PRE_CORREGIDES_ROW_CONTESTADA_PER + ": " + divAnonimaOAlumne + "</div>" +
						  			       "<div class=\"col-lg-1\">" + MIS_PRE_CORREGIDES_ROW_TIPUS + ": " + tipus + "</div>" +
						  			       "<div class=\"col-lg-1\">" + dtText + "</div>" +
						  			       "<div class=\"col-lg-3\">" + dpText + "</div>" +
						  			       "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
						  			       "<div class=\"col-lg-3\">" + textCorregida + ": " + formatData(dataCrr) + textRevisada + "</div>" +
		                			   "</div>";
	                
	                var textPregunta = "<div class=\"row\">" +
									      "<div class=\"col-lg-12\"><h4>" + codi + " - " + enunciat + "</h4></div>" +
									   "</div>";
	                
	                var preguntaBox = infoPregunta + textPregunta;
	                
	                preguntaDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaPreguntaCorregida(" + codiRp + ");\">" + preguntaBox + "</a></li>";
	        	});
        		
        		$("#consultaCorreccionsPreguntesLlistatCorregides").html(panelHeading + preguntaDesc + panelFooter);
        	} else {
        		preguntaDesc = "<div class=\"pregunta-buit\">" + MIS_PRE_CRR_O_REV_BUIT + "</div>";
        		$("#consultaCorreccionsPreguntesLlistatCorregides").html(panelHeading + preguntaDesc + panelFooter);
        	}
        },
        error:function(){
            alert("error obtenint les preguntes REC corregides");
        }
    });
}

/**
 * Mostra el detall d'una resposta-pregunta prèviament corregida.
 * 
 * NOTA: Donada la similitud d'aquesta funció amb la que s'encarrega
 * de mostrar el detall d'una resposta-pregunta prèviament CONTESTADA,
 * s'ha optat per fer ús d'aquesta segona funció. D'aquesta manera, 
 * els alumnes que vulguin consultar què acaben de contestar veuran el
 * mateix que els professors que vulguin consultar què acaben de
 * corregir.
 * 
 * @param codiRespostaPregunta
 */
function mostraRespostaPreguntaCorregida(codiRespostaPregunta) {
	// el segon paràmetre indica si la persona que crida aquesta funció
	// és professor ò alumne (per veure aspectes distints un de l'altre)
	// Si és alumne hem de passar 1, si és professor, 0.
	mostraRespostaPreguntaContestada(codiRespostaPregunta, 0);
}

/**
 * Inicia el procés de corregir o revisar manualment una pregunta
 * 
 * @param codiRespostaPregunta
 */
function iniciaCorreccioPregunta(codiRespostaPregunta) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialCorreccioPregunta").show();
	
	// Assignam el codi de la resposta-pregunta per a poder agafar-lo posteriorment
	$("#corregirPreguntaCodiRespostaPregunta").val(codiRespostaPregunta);
	
	$.ajax({
        url: "/egradus/correccio/corregeixPreguntaInici.do",
        type: "post",
        data: "codiRespostaPregunta=" + codiRespostaPregunta, 
        success: function(xml) {

        	var rspDataInici = $(xml).find("respostaPregunta").attr("dataContestacioInici");
        	var rspDataFi    = $(xml).find("respostaPregunta").attr("dataContestacioFi");
        	var rspDataCrr   = $(xml).find("respostaPregunta").attr("dataCorreccio");
        	var corregida    = $(xml).find("respostaPregunta").attr("corregida");
        	var rspTextREC   = $(xml).find("respostaPregunta").attr("textResposta");
        	var rspTextRR    = $(xml).find("respostaPregunta").attr("textRaonarResposta");
        	var rspNota      = $(xml).find("respostaPregunta").attr("nota");
        	var anonima      = $(xml).find("respostaPregunta").attr("anonima");
        	
//        	var preCodi 	 = $(xml).find("pregunta").attr("codi");
        	var preEnunciat  = $(xml).find("pregunta").attr("enunciat");
        	var preTipus 	 = $(xml).find("pregunta").attr("tipus");
//        	var preRr 		 = $(xml).find("pregunta").attr("raonarResposta");
        	
        	var aluNom 		 = $(xml).find("alumne").attr("nom");
        	var aluLli1 	 = $(xml).find("alumne").attr("llinatge1");
        	var aluLli2 	 = $(xml).find("alumne").attr("llinatge2");
        	var aluAlies     = $(xml).find("alumne").attr("alies");
        	var alumne       = aluAlies + " (" + aluNom + " " + aluLli1 + " " + aluLli2 + ")";
        	
        	var dataInici = dataTextualNavegador(rspDataInici);
        	var dataFi = dataTextualNavegador(rspDataFi);
        	var dataCrr = dataTextualNavegador(rspDataCrr);
        	
        	// Si la resposta-pregunta és anònima, no hem de veure l'alumne
    		var divAnonimaOAlumne;
            if (anonima == "true") 	divAnonimaOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
            else 					divAnonimaOAlumne = alumne;
        	
            $("#corregirPreguntaEnunciat").html(preEnunciat);
        	$("#corregirPreguntaAlumneCnt").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_ALUMNE + ": </b>" + divAnonimaOAlumne + "</div>");
        	$("#corregirPreguntaDataFiCnt").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_DATFIN + ": </b>" + formatData(dataFi) + "</div>");
        	$("#corregirPreguntaDuracioCnt").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_TEMPS + ": </b>" + diferenciaDates(dataFi, dataInici) + "</div>");
        	
        	if (corregida == "true") {
        		// Informam amb el títol de la vista que es tracta d'una revisió de pregunta
        		$("#corregirPreguntaTitol").html(REV_PREGUNTA_TITOL);
        		$("#corregirPreguntaTitolDescripcio").html(REV_PREGUNTA_TITOL_DESC);
        		$("#corregirPreguntaBoto").html(REV_PREGUNTA_BOTO);
        		
        		// Afegim els camps referits a la nota i a la data de correcció la pregunta
	    		$("#corregirPreguntaNota").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_NOTA + ": </b>" + rspNota + "</div>");
	        	$("#corregirPreguntaDataCorreccio").html("<div class=\"col-lg-12\"><b>" + MIS_PRE_CONS_RESPOSTA_CORREGIDA + ": </b>" + formatData(dataCrr) + " <i>(" + MIS_QST_CONS_CRR_AUTOMATICA + ")</i></div>");
    		} else {
    			// Informam amb el títol de la vista que es tracta d'una correcció de pregunta
    			$("#corregirPreguntaTitol").html(CRR_PREGUNTA_TITOL);
        		$("#corregirPreguntaTitolDescripcio").html(CRR_PREGUNTA_TITOL_DESC);
        		$("#corregirPreguntaBoto").html(CRR_PREGUNTA_BOTO);
    		}
        	
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
        			
        			opcionsDesc +=  "<div class=\"panel panel-default\">" +
    									"<div id=\"corregirPreguntaCodiOpcio" + codiOpcio + "\" class=\"panel-body text-center " + opcioResultatClass + "\">" + textOpcio + "</div>" +
    								"</div>";
        		});
        		$("#corregirPreguntaOpcionsOREC").html(opcionsDesc);
        		
        		var divRevisio = 	"<div class=\"row\">" +
										"<div id=\"corregirPreguntaRaonarResposta\" class=\"col-sm-12\">" + 
											"<div class=\"form-group\">" +
											    "<label for=\"corregirPreguntaRaonarRespostaInput\">" + MIS_PRE_INI_CNT_RR + "</label>" +
												"<textarea rows=\"4\" class=\"form-control\" id=\"corregirPreguntaRaonarRespostaInput\" name=\"raonarresposta\" disabled>" + rspTextRR + "</textarea>" + 
										    "</div>" +
										"</div>" + 
									"</div>" + 
									"<div class=\"row\">" +
										"<div id=\"corregirPreguntaTextRevisio\" class=\"col-sm-12\">" + 
											"<div class=\"form-group\">" + 
												"<label for=\"corregirPreguntaTextRevisioInput\">" + CRR_PREGUNTA_REV + "</label>" + 
												"<textarea rows=\"4\" class=\"form-control\" id=\"corregirPreguntaTextRevisioInput\" name=\"textRevisio\" placeholder=\"" + CRR_PREGUNTA_REV_PLACEHOLDER + "\"></textarea>" + 
											"</div>" + 
										"</div>" + 
									"</div>" + 
									"<div class=\"row\">" +
										"<div id=\"corregirPreguntaNotaRevisio\" class=\"col-xs-6 col-sm-4\">" + 
											"<label for=\"corregirPreguntaNotaRevisioInput\">" + CRR_PREGUNTA_NOTA + "</label>" + 
											"<input class=\"form-control\" id=\"corregirPreguntaNotaRevisioInput\" name=\"nota\" type=\"text\" value=\"" + rspNota + "\" placeholder=\"" + CRR_PREGUNTA_NOTA_PLACEHOLDER + "\"/>" + 
										"</div>" +
									"</div>";
				
				$("#corregirPreguntaCorreccioORevisio").html(divRevisio);
        	} else {
        		// Si és una pregunta de tipus REC hem d'habilitar un camp de text
        		// per a donar una resposta a la pregunta
        		if (preTipus == TIPUS_PREGUNTA_REC) {
            		$("#corregirPreguntaOpcionsOREC").html("<div class=\"form-group\">" +
    												           "<label for=\"corregirPreguntaContestacioPreguntaRECInput\">" + MIS_PRE_INI_CNT_REC + "</label>" +
    													       "<textarea rows=\"4\" class=\"form-control\" id=\"corregirPreguntaContestacioPreguntaRECInput\" name=\"respostaREC\" disabled>" + rspTextREC + "</textarea>" +
    													   "</div>");
            		
            		var divCorreccio = 	"<div class=\"row\">" +
	            							"<div id=\"corregirPreguntaTextCorreccio\" class=\"col-sm-12\">" + 
												"<div class=\"form-group\">" + 
													"<label for=\"corregirPreguntaTextCorreccioInput\">" + CRR_PREGUNTA_CRR + "</label>" + 
													"<textarea rows=\"4\" class=\"form-control\" id=\"corregirPreguntaTextCorreccioInput\" name=\"textCorreccio\" placeholder=\"" + CRR_PREGUNTA_CRR_PLACEHOLDER + "\"></textarea>" + 
												"</div>" + 
											"</div>" + 
										"</div>" + 
										"<div class=\"row\">" +
											"<div id=\"corregirPreguntaNotaCorreccio\" class=\"col-xs-6 col-sm-4\">" + 
												"<label for=\"corregirPreguntaNotaCorreccioInput\">" + CRR_PREGUNTA_NOTA + "</label>" + 
												"<input class=\"form-control\" id=\"corregirPreguntaNotaCorreccioInput\" name=\"nota\" type=\"text\" placeholder=\"" + CRR_PREGUNTA_NOTA_PLACEHOLDER + "\"/>" + 
											"</div>" +
										"</div>";
            		
            		$("#corregirPreguntaCorreccioORevisio").html(divCorreccio);
            	}
        	}
        	
        },
        error:function(){
            alert("error per mostrar la vista per a corregir les preguntes REC");
        }
    });
}

/**
 * Finalitza el procés de corregir manualment una pregunta REC
 * 
 * @param codiRespostaPregunta
 */
function finalitzaCorreccioPregunta() {
	
	// validar en client que el text de la correcció no excedeixi els 4000 caràcters que pot tenir.
	if ($("#corregirPreguntaTextCorreccioInput").length > 0) {
		if ($("#corregirPreguntaTextCorreccioInput").val().length > 4000){
			alert(MIS_PRE_CRR_TEXT_LLARG);
			return;
		}
	}
	
	// validar en client que el text de la revisió no excedeixi els 4000 caràcters que pot tenir.
	if ($("#corregirPreguntaTextRevisioInput").length > 0) {
		if ($("#corregirPreguntaTextRevisioInput").val().length > 4000){
			alert(MIS_PRE_REV_TEXT_LLARG);
			return;
		}
	}
	
	// assignam el codi de la resposta-pregunta que hem inserit en el moment d'iniciar la 
	// correcció de la pregunta a la llista de paràmetres que passarem a la petició AJAX
	var values = "codiRespostaPregunta=" + $("#corregirPreguntaCodiRespostaPregunta").val();
	
	// assignam els camps de text omplits en el formulari a la llista de paràmetres que passarem a la petició AJAX
	values += "&" + $("#corregirPreguntaForm").serialize();
	$.ajax({
        url: "/egradus/correccio/corregeixPreguntaFi.do",
        type: "post",
        data: values,
        success: function(xml) {
        	var codi      = $(xml).find("pregunta").attr("codi");
        	var enunciat  = $(xml).find("pregunta").attr("enunciat");
        	var corregida = $(xml).find("respostaPregunta").attr("corregida");
        	var revisada  = $(xml).find("respostaPregunta").attr("revisada");
        	var rspError  = $(xml).find("errorCorreccio").text();
        	
        	if (rspError.length != 0) {
        		$("#corregirPreguntaError").html(rspError);
        	} else {
        		$("#corregirPreguntaError").empty();
        		
            	// Elegim el títol i missatge del modal window
        		var modalTitol = "";
            	var modalMissatge = "";
            	if (corregida == "true" && revisada == "false") {
            		modalTitol = CRR_PREGUNTA_MODAL_TITOL;
            		modalMissatge = CRR_PREGUNTA_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + CRR_PREGUNTA_MODAL_MIS2;
            	}
            	if (corregida == "true" && revisada == "true") {
            		modalTitol = REV_PREGUNTA_MODAL_TITOL;
            		modalMissatge = REV_PREGUNTA_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + REV_PREGUNTA_MODAL_MIS2;
            	}
            	
            	// mostram el modal window
            	$("#corregirPreguntaModalBoto").attr("onclick", "habilitaCorreccioPre();");
            	$("#corregirPreguntaModalMissatge").html(modalMissatge);
            	$("#corregirPreguntaModalLabel").html(modalTitol);
            	$("#corregirPreguntaModal").modal("show");
        	}
        },
        error:function(){
        	alert("error finalitza correcció pregunta");
        }
    });
}

/**
 * obtenim el llistat de qüestionaris que contenguin alguna pregunta REC pendents 
 * de corregir o bé que tenguin alguna pregunta amb RR pendents de revisar, i 
 * actualitzam la vista amb aquesta informació
 * 
 * @param codiAssignatura
 */
function questionarisPendentsDeCorregir(codiAssignatura) {
	$.ajax({
        url: "/egradus/correccio/getQuestionarisPendentsCorregir.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-success\">" +
								   "<div class=\"panel-heading\">" + MIS_QST_PENDENTS_CRR_O_REV + "</div>" +
					            "<ul class=\"list-group\">";
        	var questionariDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var questionaris = $(xml).find("respostaQuestionari");
        	if (questionaris.length > 0) {
        		questionaris.each(function(){
        			var codiRq      = $(this).attr("codi");
        			var anonim     = $(this).attr("anonim");
        			var dataCnt     = dataTextualNavegador($(this).attr("dataContestacioFi"));
	        		var codi        = $(this).find("questionari").attr("codi");
	                var nom         = $(this).find("questionari").attr("nom");
	                var descripcio  = $(this).find("questionari").attr("descripcio");
	                var dt          = $(this).find("questionari").attr("dificultatTeorica");
	                var dp          = $(this).find("questionari").attr("dificultatPractica");
	                var alumneNom   = $(this).find("alumne").attr("nom");
	                var alumneLli1  = $(this).find("alumne").attr("llinatge1");
	                var alumneLli2  = $(this).find("alumne").attr("llinatge2");
	                var alumneAlies = $(this).find("alumne").attr("alies");
	                var alumne = "<i>" + alumneAlies + " (" + alumneNom + " " + alumneLli1 + " " + alumneLli2 + ")</i>";
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_QST_PENDENTS_CORREGIR_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_QST_PENDENTS_CORREGIR_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                // Si la resposta-questionari és anònim, no hem de veure l'alumne
	        		var divAnonimOAlumne;
	                if (anonim == "true") 	divAnonimOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
	                else 					divAnonimOAlumne = alumne;
	                
	                var infoQuestionari = "<div class=\"row\">" +
										      "<div class=\"col-lg-3\">" + MIS_QST_PENDENTS_CORREGIR_ROW_CONTESTAT_PER + ": " + divAnonimOAlumne + "</div>" +
										      "<div class=\"col-lg-1\">" + dtText + "</div>" +
										      "<div class=\"col-lg-3\">" + dpText + "</div>" +
										      "<div class=\"col-lg-offset-2 col-lg-3\">" + MIS_QST_PENDENTS_CORREGIR_ROW_REBUT + ": " + formatData(dataCnt) + "</div>" +
										   "</div>";
		
                	var textQuestionari = "<div class=\"row\">" +
										      "<div class=\"col-lg-12\"><h4>" + codi + " - " + nom + "</h4></div>" +
										   "</div>" +
										   "<div class=\"row\">" +
										      "<div class=\"col-lg-12\">" + descripcio + "</div>" +
										   "</div>";
		
					var questionariBox = infoQuestionari + textQuestionari;
		
					questionariDesc += "<li class=\"list-group-item\"><a href=\"javascript: iniciaCorreccioQuestionari(" + codiRq + ");\">" + questionariBox + "</a></li>";
    			});
			} else {
				questionariDesc = "<div class=\"questionari-buit\">" + MIS_QST_PENDENTS_CRR_O_REV_BUIT + "</div>";
			}
			$("#consultaCorreccionsQuestionarisLlistatPendents").html(panelHeading + questionariDesc + panelFooter);
		},
		error:function(){
			alert("error per mostrar la vista per a contestar els qüestionaris");
		}
	});
}

/**
 * obtenim el llistat de qüestionaris (amb preguntes REC) corregits pel professor assignador actual (el 
 * tenim a la sessió) i actualitzam la vista amb aquesta informació
 * 
 * @param codiAssignatura
 */
function questionarisCorregits(codiAssignatura) {
	$.ajax({
        url: "/egradus/correccio/getQuestionarisCorregitsProfessor.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	
        	var panelHeading = "<div class=\"panel panel-danger\">" +
								   "<div class=\"panel-heading\">" + MIS_QST_CRR_O_REV + "</div>" +
					            "<ul class=\"list-group\">";
        	var questionariDesc = "";
			var panelFooter = "</ul></div>";
        	
        	var questionaris = $(xml).find("respostaQuestionari");
        	if (questionaris.length > 0) {
        		questionaris.each(function(){
        			var codiRq      = $(this).attr("codi");
        			var anonim     = $(this).attr("anonim");
        			var dataCrr    = dataTextualNavegador($(this).attr("dataCorreccio"));
	                var dataRev    = dataTextualNavegador($(this).attr("dataRevisio"));
	        		var codi        = $(this).find("questionari").attr("codi");
	                var nom         = $(this).find("questionari").attr("nom");
	                var descripcio  = $(this).find("questionari").attr("descripcio");
	                var dt          = $(this).find("questionari").attr("dificultatTeorica");
	                var dp          = $(this).find("questionari").attr("dificultatPractica");
	                var alumneNom   = $(this).find("alumne").attr("nom");
	                var alumneLli1  = $(this).find("alumne").attr("llinatge1");
	                var alumneLli2  = $(this).find("alumne").attr("llinatge2");
	                var alumneAlies = $(this).find("alumne").attr("alies");
	                var alumne = "<i>" + alumneAlies + " (" + alumneNom + " " + alumneLli1 + " " + alumneLli2 + ")</i>";
	                
	                // funcions definides a egradus-contestacions.js
	                var numRec = getNumPreguntesRec($(this).find("questionari"));
	                var numRr  = getNumPreguntesRr($(this).find("questionari"));
	                
	                var dtText;
	                if (dt.length > 0) dtText = MIS_QST_CORREGITS_ROW_DIF_TEO + ": " + dt;
	                else               dtText = "";
	                
	                var dpText;
	                if (dp.length > 0) dpText = MIS_QST_CORREGITS_ROW_DIF_PRA + ": " + dp;
	                else               dpText = "";
	                
	                // Si la resposta-questionari és anònim, no hem de veure l'alumne
	        		var divAnonimOAlumne;
	                if (anonim == "true") 	divAnonimOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
	                else 					divAnonimOAlumne = alumne;
	                
	                var textCorregit = "";
	                if (numRec > 0) textCorregit = MIS_QST_CONTESTATS_ROW_CORREGIT;
	                else textCorregit = MIS_QST_CONTESTATS_ROW_CORREGIT_AUT;
	                
	                var textRevisat = "";
	                if (numRr > 0) textRevisat = "<br /> " + MIS_QST_CONTESTATS_ROW_REVISAT + ": " + formatData(dataRev);
	                
	                var infoQuestionari = "<div class=\"row\">" +
									         "<div class=\"col-lg-3\">" + MIS_QST_CORREGITS_ROW_CONTESTAT_PER + ": " + divAnonimOAlumne + "</div>" +
									         "<div class=\"col-lg-1\">" + dtText + "</div>" +
									         "<div class=\"col-lg-3\">" + dpText + "</div>" +
									         "<div class=\"col-lg-offset-2 col-lg-3\">" + textCorregit + ": " + formatData(dataCrr) + textRevisat + "</div>" +
									      "</div>";
					
					var textQuestionari = "<div class=\"row\">" +
										      "<div class=\"col-lg-12\"><h4>" + codi + " - " + nom + "</h4></div>" +
										   "</div>" +
										   "<div class=\"row\">" +
										      "<div class=\"col-lg-12\">" + descripcio + "</div>" +
										   "</div>";
					
					var questionariBox = infoQuestionari + textQuestionari;
					
					questionariDesc += "<li class=\"list-group-item\"><a href=\"javascript: mostraRespostaQuestionariCorregit(" + codiRq + ");\">" + questionariBox + "</a></li>";
	        	});
        		
        		$("#consultaCorreccionsQuestionarisLlistatCorregits").html(panelHeading + questionariDesc + panelFooter);
        	} else {
        		questionariDesc = "<div class=\"questionari-buit\">" + MIS_QST_CRR_O_REV_BUIT + "</div>";
        		$("#consultaCorreccionsQuestionarisLlistatCorregits").html(panelHeading + questionariDesc + panelFooter);
        	}
        },
        error:function(){
            alert("error obtenint els qüestionaris REC corregits");
        }
    });
}

/**
 * Inicia el procés de corregir o revisar manualment un qüestionari
 * 
 * @param codiRespostaQuestionari
 */
function iniciaCorreccioQuestionari(codiRespostaQuestionari) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialCorreccioQuestionari").show();
	
	// Assignam el codi de la resposta-pregunta per a poder agafar-lo posteriorment
	$("#corregirQuestionariCodiRespostaQuestionari").val(codiRespostaQuestionari);
	
	$.ajax({
        url: "/egradus/correccio/corregeixQuestionariInici.do",
        type: "post",
        data: "codiRespostaQuestionari=" + codiRespostaQuestionari, 
        success: function(xml) {
        	var rspDataInici = $(xml).find("respostaQuestionari").attr("dataContestacioInici");
        	var rspDataFi    = $(xml).find("respostaQuestionari").attr("dataContestacioFi");
        	var rspDataCrr   = $(xml).find("respostaQuestionari").attr("dataCorreccio");
        	var rspNota      = $(xml).find("respostaQuestionari").attr("nota");
        	var corregit     = $(xml).find("respostaQuestionari").attr("corregit");
        	var anonim      = $(xml).find("respostaQuestionari").attr("anonim");
//        	var rspTextCrr   = $(xml).find("respostaQuestionari").attr("textCorreccio");
			
        	var qstNom       = $(xml).find("questionari").attr("nom");
        	var qstDescrip 	 = $(xml).find("questionari").attr("descripcio");
        	
//        	var asiNom 		 = $(xml).find("assignador").attr("nom");
//        	var asiLli1 	 = $(xml).find("assignador").attr("llinatge1");
//        	var asiLli2 	 = $(xml).find("assignador").attr("llinatge2");
//        	var asiAlies     = $(xml).find("assignador").attr("alies");
//        	var assignador   = asiAlies + " (" + asiNom + " " + asiLli1 + " " + asiLli2 + ")";
        	
        	var aluNom 		 = $(xml).find("alumne").attr("nom");
        	var aluLli1 	 = $(xml).find("alumne").attr("llinatge1");
        	var aluLli2 	 = $(xml).find("alumne").attr("llinatge2");
        	var aluAlies     = $(xml).find("alumne").attr("alies");
        	var alumne       = aluAlies + " (" + aluNom + " " + aluLli1 + " " + aluLli2 + ")";
        	
        	var dataInici = dataTextualNavegador(rspDataInici);
        	var dataFi = dataTextualNavegador(rspDataFi);
        	var dataCrr = dataTextualNavegador(rspDataCrr);
        	
        	// Si la resposta-questionari és anònim, no hem de veure l'alumne
    		var divAnonimOAlumne;
            if (anonim == "true") 	divAnonimOAlumne = "<span class=\"glyphicon glyphicon-eye-close\"></span> " + MIS_ALU_ANONIM;
            else 					divAnonimOAlumne = alumne;
        	
        	$("#corregirQuestionariNom").html(qstNom + "<br />" + qstDescrip);
        	$("#corregirQuestionariDataFiCnt").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_DATFIN + ": </b>" + formatData(dataFi) + "</div>");
        	$("#corregirQuestionariDataTemps").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_TEMPS + ": </b>" + diferenciaDates(dataFi, dataInici) + "</div>");
    		$("#corregirQuestionariContestatPer").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_ALUMNE + ": </b>" + divAnonimOAlumne + "</div>");
    		
    		if (corregit == "true") {
    			// Informam amb el títol de la vista que es tracta d'una revisió de qüestionari
    			$("#corregirQuestionariTitol").html(REV_QUESTIONARI_TITOL);
        		$("#corregirQuestionariTitolDescripcio").html(REV_QUESTIONARI_TITOL_DESC);
        		$("#corregirQuestionariBoto").html(REV_QUESTIONARI_BOTO);
    			
        		// Afegim els camps referits a la nota i a la data de correcció la qüestionari
	    		$("#corregirQuestionariNota").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_NOTA + ": </b>" + rspNota + "</div>");
	        	$("#corregirQuestionariDataCorreccio").html("<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_CORREGIDA + ": </b>" + formatData(dataCrr) + " <i>(" + MIS_QST_CONS_CRR_AUTOMATICA + ")</i></div>");
    		} else {
    			// Informam amb el títol de la vista que es tracta d'una correcció de qüestionari
    			$("#corregirQuestionariTitol").html(CRR_QUESTIONARI_TITOL);
        		$("#corregirQuestionariTitolDescripcio").html(CRR_QUESTIONARI_TITOL_DESC);
        		$("#corregirQuestionariBoto").html(CRR_QUESTIONARI_BOTO);
    		}
    		
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
	        		// NOTA IMPORTANT!! la implementació de la funció 'getRespostaPreguntaByPregunta()' i de
	        		// 'getValorAtributByRespostaPregunta()' està a egradus-contestacions.js
	            	var codiRespostaPregunta = getRespostaPreguntaByPregunta(codiPre, xml);
	            	
	            	// agafam el text de raonament de resposta, de la resposta REC i la nota
	            	var rspTextRR  = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "textRaonarResposta");
	            	var rspTextRec = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "textResposta");
	            	var rpNota     = getValorAtributByRespostaPregunta(codiRespostaPregunta, xml, "nota");
	            	
	            	// nota de la pregunta
	            	var textNota = "";
	            	if (tipus == TIPUS_PREGUNTA_REC) {
	            		textNota = " <i>(" + MIS_QST_CONS_CRR_MANUAL_PENDENT_PROFE_ACTUAL + ")</i>";
	            	} else {
	            		if (rpNota.length > 0) textNota = rpNota + " <i>(" + MIS_QST_CONS_CRR_AUTOMATICA + ")</i>";
	            	}
	        		
	            	var panellEnunciat = "<div class=\"row\">" +
											"<div class=\"col-lg-12\">" +
												"<div class=\"panel panel-default\">" +
													"<div class=\"col-lg-12\"><b>" + MIS_QST_CONS_RESPOSTA_NOTA + ": </b>" + textNota + "</div>" +
													"<div id=\"corregirQuestionariPregunta" + codiPre + "Enunciat\" class=\"panel-body text-center\">" + enunciat + "</div>" +
												"</div>" +
											"</div>" +
										"</div>";
	            	
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
	            	var correccioORevisio = "";
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
	            			
	            			opcionsDesc +=  "<div class=\"panel panel-default\">" +
	        							        "<div id=\"corregirQuestionariPreguntaCodiOpcio" + codiOpcio + "\" class=\"panel-body text-center corregirQuestionariPregunta" + codiPre + "Opcions " + opcioResultatClass + "\">" + textOpcio + "</div>" +
	        								"</div>";
	                	});
	            		opcionsORec = opcionsDesc;
	            		
	            		var divRevisio = "";
	            		if (rr == "true") {
		            		divRevisio = 	"<div class=\"row\">" +
												"<div id=\"corregirQuestionariPregunta" + codiPre + "RaonarResposta\" class=\"col-sm-12\">" + 
													"<div class=\"form-group\">" +
														"<label for=\"corregirQuestionariPregunta" + codiPre + "RaonarRespostaInput\">" + MIS_PRE_INI_CNT_RR + "</label>" +
														"<textarea rows=\"4\" class=\"form-control\" id=\"corregirQuestionariPregunta" + codiPre + "RaonarRespostaInput\" name=\"raonarresposta\" disabled>" + rspTextRR + "</textarea>" + 
													"</div>" +
												"</div>" + 
											"</div>" + 
											"<div class=\"row\">" +
												"<div id=\"corregirQuestionariPregunta" + codiPre + "TextRevisio\" class=\"col-sm-12\">" + 
													"<div class=\"form-group\">" + 
														"<label for=\"corregirQuestionariPregunta" + codiPre + "TextRevisioInput\">" + CRR_PREGUNTA_REV + "</label>" + 
														"<textarea rows=\"4\" class=\"form-control corregirQuestionariTextRevisioInput\" id=\"corregirQuestionariPregunta" + codiPre + "TextRevisioInput\" name=\"textRevisio\" placeholder=\"" + CRR_PREGUNTA_REV_PLACEHOLDER + "\"></textarea>" + 
													"</div>" + 
												"</div>" + 
											"</div>" + 
											"<div class=\"row\">" +
												"<div id=\"corregirQuestionariPregunta" + codiPre + "NotaRevisio\" class=\"col-sm-6\">" + 
													"<label for=\"corregirQuestionariPregunta" + codiPre + "NotaRevisioInput\">" + CRR_PREGUNTA_NOTA + "</label>" + 
													"<input class=\"form-control\" id=\"corregirQuestionariPregunta" + codiPre + "NotaRevisioInput\" name=\"nota\" type=\"text\" value=\"" + rpNota + "\" placeholder=\"" + CRR_PREGUNTA_NOTA_PLACEHOLDER + "\"/>" + 
												"</div>" +
											"</div>";
	            		}
	            		correccioORevisio = divRevisio;
	            	} else {
	            		// Si és una pregunta de tipus REC hem d'habilitar un camp de text
	            		// per a donar una resposta a la pregunta
	            		if (tipus == TIPUS_PREGUNTA_REC) {
	            			opcionsORec = "<div class=\"form-group\">" +
											   "<label for=\"corregirQuestionariCorreccioPreguntaRECInput" + codiPre + "\">" + MIS_PRE_INI_CNT_REC + "</label>" +
											   "<textarea rows=\"4\" class=\"form-control\" id=\"corregirQuestionariCorreccioPreguntaRECInput" + codiPre + "\" disabled>" + rspTextRec + "</textarea>" +
										  "</div>";
	            			
	            			var divCorreccio = 	"<div class=\"row\">" +
													"<div id=\"corregirQuestionariPregunta" + codiPre + "TextCorreccio\" class=\"col-sm-12\">" + 
														"<div class=\"form-group\">" + 
															"<label for=\"corregirQuestionariPregunta" + codiPre + "TextCorreccioInput\">" + CRR_PREGUNTA_CRR + "</label>" + 
															"<textarea rows=\"4\" class=\"form-control corregirQuestionariTextCorreccioInput\" id=\"corregirQuestionariPregunta" + codiPre + "TextCorreccioInput\" name=\"textCorreccio\" placeholder=\"" + CRR_PREGUNTA_CRR_PLACEHOLDER + "\"></textarea>" + 
														"</div>" + 
													"</div>" + 
												"</div>" + 
												"<div class=\"row\">" +
													"<div id=\"corregirQuestionariPregunta" + codiPre + "NotaCorreccio\" class=\"col-sm-6\">" + 
														"<label for=\"corregirQuestionariPregunta" + codiPre + "NotaCorreccioInput\">" + CRR_PREGUNTA_NOTA + "</label>" + 
														"<input class=\"form-control\" id=\"corregirQuestionariPregunta" + codiPre + "NotaCorreccioInput\" name=\"nota\" type=\"text\" placeholder=\"" + CRR_PREGUNTA_NOTA_PLACEHOLDER + "\"/>" + 
													"</div>" +
												"</div>";
	            			
	            			correccioORevisio = divCorreccio;
	                	}
	            	}
	        		
	        		// pintam al panell central les opcions o el camp de REC
	            	// i el camp de correcció o el de revisió
	        		var panellCentral = "<div class=\"row\">" +
											"<div id=\"corregirQuestionariPreguntaOpcionsOREC" + codiPre + "\" class=\"col-sm-6 col-md-6 col-lg-6\">" + opcionsORec + "</div>" +
											"<div id=\"corregirQuestionariPreguntaCorreccioORevisio" + codiPre + "\" class=\"col-sm-6 col-md-6 col-lg-6\">" + correccioORevisio + "</div>" +
										"</div>";
	        		
	        		// ------------------------------------------------------------------------------------
	        		// Incorporació dels panells amb la info associada als divs que es despleguen i pleguen
	        		// ------------------------------------------------------------------------------------
	        		
	        		// informació associada de la pregunta
	        		var descPregunta = panellEnunciat + panellCentral;
	        		
	        		// panell que sempre està visible
	        		var divEnunciat = "<div id=\"corregirQuestionariIcona" + codiPre + "\" class=\"col-xs-1 col-sm-1 col-md-1 col-lg-1\"><span class=\"glyphicon glyphicon-chevron-up\"></span></div>" +
	        						  "<div class=\"col-xs-9 col-sm-9 col-md-9 col-lg-9\">" + MIS_QST_INI_CNT_PREGUNTA + " " + indexPregunta++ + "</div>" +
	        						  "<div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2\">" + MIS_QST_INI_CNT_PREGUNTA_PES + ": " + pes + "</div>";
	        		
	        		// div sencer
	        	    var boxPregunta = "<div class=\"row\">" + 
							              "<div class=\"col-lg-12\">" + 
							        		  "<div class=\"panel panel-default\">" + 
								        		  "<div class=\"panel-heading\">" +
								        		      "<a href=\"javascript: desplegaPreguntaCorregirQuestionari(" + codiPre + ");\">" + 
								        		          "<div id=\"corregirQuestionariPregunta" + codiPre + "\" class=\"panel-body text-center\">" + divEnunciat + "</div>" +
								        		      "</a>" + 
								        		  "</div>" + 
							        			  "<div id=\"corregirQuestionariPreguntaInfoAssociada" + codiPre + "\" class=\"panel-body corregirQuestionariDescPregunta\">" + descPregunta + "</div>" + 
							        		  "</div>" + 
							        	  "</div>" + 
							          "</div>";
	        		
	        		divPreguntes += boxPregunta;
	        		
	        	});
	        	
	        	// afegim les preguntes
	        	$("#corregirQuestionariPreguntes").html(divPreguntes);
	        	
	        	// mostram, d'entrada, la informació associada de totes les preguntes
	        	$(".corregirQuestionariDescPregunta").show();
        	}
        },
        error:function(){
            alert("error per mostrar la vista per a corregir els qüestionaris");
        }
    });
}

/**
 * funció privada que desplega o plega la informació associada d'una pregunta
 * (quan estam corregint la resposta del qüestionari) i, a més, actualitza
 * l'icona
 * 
 * @param codiPregunta
 */
function desplegaPreguntaCorregirQuestionari(codiPregunta) {
	var divPregunta = $("#corregirQuestionariPreguntaInfoAssociada" + codiPregunta);
	var divIcona = $("#corregirQuestionariIcona" + codiPregunta);
	if (divPregunta.is(":visible")) {
		divPregunta.hide();
		divIcona.html("<span class=\"glyphicon glyphicon-chevron-down\"></span>");
	} else {
		divPregunta.show();
		divIcona.html("<span class=\"glyphicon glyphicon-chevron-up\"></span>");
	}
}

/**
 * finalitza el procés de corregir manualment un qüestionari
 * amb preguntes REC
 */
function finalitzaCorreccioQuestionari() {
	// validar en client que el text de la correcció de les preguntes del qüestionari
	// no excedeixi els 4000 caràcters que pot tenir.
	var sortir = false;
	$(".corregirQuestionariTextCorreccioInput").each(function(sortir){
		if ($(this).val().length > 4000) {
			alert(MIS_PRE_CRR_TEXT_LLARG);
			sortir = true;
			return;
		}
	});
	if (sortir) return;
	
	// validar en client que el text de la revisió de les preguntes del qüestionari
	// no excedeixi els 4000 caràcters que pot tenir.
	$(".corregirQuestionariTextRevisioInput").each(function(sortir){
		if ($(this).val().length > 4000) {
			alert(MIS_QST_REV_TEXT_LLARG);
			sortir = true;
			return;
		}
	});
	if (sortir) return;
	
	// assignam el codi de la resposta-questionari que hem inserit en el moment d'iniciar la 
	// correcció del qüestionari a la llista de paràmetres que passarem a la petició AJAX
	var values = "codiRespostaQuestionari=" + $("#corregirQuestionariCodiRespostaQuestionari").val();
	
	// assignam els camps de text omplits en el formulari a la llista de paràmetres que passarem a la petició AJAX
	values += "&" + $("#corregirQuestionariForm").serialize();
	
	$.ajax({
        url: "/egradus/correccio/corregeixQuestionariFi.do",
        type: "post",
        data: values,
        success: function(xml) {
        	var codi     = $(xml).find("questionari").attr("codi");
        	var nom      = $(xml).find("questionari").attr("nom");
        	var numRec   = $(xml).find("questionari").find("numRec").text();
        	var rspError = $(xml).find("errorCorreccio").text();
        	
        	if (rspError.length != 0) {
        		$("#corregirQuestionariError").html(rspError);
        	} else {
        		$("#corregirQuestionariError").empty();
        		
        		// Elegim el títol i missatge del modal window
        		var modalTitol = "";
            	var modalMissatge = "";
            	if (numRec > 0) {
            		modalTitol = CRR_QUESTIONARI_MODAL_TITOL;
            		modalMissatge = CRR_QUESTIONARI_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + CRR_QUESTIONARI_MODAL_MIS2;
            	} else {
            		modalTitol = REV_QUESTIONARI_MODAL_TITOL;
            		modalMissatge = REV_QUESTIONARI_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + REV_QUESTIONARI_MODAL_MIS2;
            	}
            	
            	// mostram el modal window
            	$("#corregirQuestionariModalBoto").attr("onclick", "habilitaCorreccioQst();");
            	$("#corregirQuestionariModalMissatge").html(modalMissatge);
            	$("#corregirQuestionariModalLabel").html(modalTitol);
            	$("#corregirQuestionariModal").modal("show");
        	}
        },
        error:function(){
        	alert("error finalitza correcció qüestionari");
        }
    });
}

/**
 * Mostra el detall d'una resposta-questionari prèviament corregida.
 * 
 * NOTA: Donada la similitud d'aquesta funció amb la que s'encarrega
 * de mostrar el detall d'una resposta-questionari prèviament CONTESTADA,
 * s'ha optat per fer ús d'aquesta segona funció. D'aquesta manera, 
 * els alumnes que vulguin consultar què acaben de contestar veuran el
 * mateix que els professors que vulguin consultar què acaben de
 * corregir.
 * 
 * @param codiRespostaQuestionari
 */
function mostraRespostaQuestionariCorregit(codiRespostaQuestionari) {
	// el segon paràmetre indica si la persona que crida aquesta funció
	// és professor ò alumne (per veure aspectes distints un de l'altre)
	// Si és alumne hem de passar 1, si és professor, 0.
	mostraRespostaQuestionariContestat(codiRespostaQuestionari, 0);
}










