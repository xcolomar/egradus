/*
 * egradus-estadístiques.js
 * ----------------------------------------------------------------------------------------
 * 
 * Funcionalitats de les estadístiques. Les estadístiques serviran bàsicament per
 * consultar. No es podran editar. Es podrà devallar un extracte (en .pdf o excel) de les
 * estadístiques desitjades.
 * 
 * En cas de que la persona sigui professor de l'assignatura, les estadístiques que tendrà 
 * són:
 * 1) Estadístiques de la seva participació al repositori
 * 2) Estadístiques del rendiment dels alumnes (de l'activitat que hagin tengut en les
 *    contestacions de preguntes i/o qüestionaris o de la participació en el fòrum)
 * 3) Estadístiques de les preguntes emprades a l'assignatura (les més complicades, etc)
 * 4) Estadístiques dels qüestionaris emprats a l'assignatura (idem que les preguntes)
 * 
 * En cas que la persona sigui alumne de l'assignatura, les estadístiques que tendrà són:
 * 1) Estadístiques de les preguntes que el professor li hagi enviat per contestar
 * 2) Estadístiques dels qüestionaris que el professor li hagi enviat per contestar 
 * 3) Estadístiques de la participació que hagi tengut en el fòrum de l'assignatura
 */

/**
 * habilita la vista sobre les estadístiques que té el PROFESSOR sobre les preguntes
 */
function habilitaEstadistiquesPrePro() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEstadistiquesPrePro").show();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya d'estadístiques de preguntes dels professors, per saber que ha
	// estat seleccionada pel professor
	$("#assignaturaMaterialEstadistiquesPro").addClass("egradus-color-pestanya");
	
	// buidam les estadístiques de les preguntes
	$(".estProPreguntesTaulaValor").empty();
	$("#estProPreguntesNoEstadistiquesUnaPregunta").empty();
	$("#estProPreguntesNoEstadistiquesConjunt").empty();
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// Omplim de preguntes l'input select
	var preSeleccionada = "";
	$.ajax({
        url: "/egradus/estadistiques/professor/getPreguntesDisponibles.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var inputSelectOptions = "";
        	var preguntes = $(xml).find("pregunta");
        	preguntes.each(function(){
        		var codi     = $(this).attr("codi");
        		var enunciat = $(this).attr("enunciat");
        		
        		// recollim les opcions que inserirem a l'input select
    	        inputSelectOptions += "<option id=\"estProPreguntesSelectPregunta" + codi + "\" value=\"" + codi + "\">" + codi + " - " + enunciat;
    	        
    	        // guardam el primer codi de pregunta que trobem
    	        if (preSeleccionada == "") preSeleccionada = codi;
        	});
        	
        	// inserim les opcions a l'input select de preguntes, i marcam com a seleccionada la 
        	// primera pregunta que hem trobat a la cerca
        	$("#estProPreguntesSelectUnaPregunta").html(inputSelectOptions);
        	if (preguntes.length == 0) {
        		// el professor no té preguntes amb les que visualitzar estadístiques
        		$("#estProPreguntesNoEstadistiquesUnaPregunta").html("<div class=\"alert alert-info\" role=\"alert\">" +
			        												      "<h4>" + EST_PRO_NO_PRE + "</h4><p>" + EST_PRO_NO_PRE_DESC + "</p>" +
			        												 "</div>");
        	} else {
        		$("#estProPreguntesSelectPregunta" + preSeleccionada).attr('selected', 'selected');
        		
        		// forçam la recàrrega de les estadístiques d'una pregunta
            	determinarEstProfessorPregunta();
        	}
        	
        },
        error:function(){
            alert("error habilita estadístiques de professors: preguntes");
        }
    });
	
	// forçam la recàrrega de les estadístiques de totes les preguntes d'una assignatura
	determinarEstProfessorPreguntesAssignatura(codiAssignatura);
}

/**
 * habilita la vista sobre les estadístiques que té el PROFESSOR sobre els qüestionaris
 */
function habilitaEstadistiquesQstPro() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEstadistiquesQstPro").show();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya d'estadístiques de qüestionaris dels professors, per saber que ha
	// estat seleccionada pel professor
	$("#assignaturaMaterialEstadistiquesPro").addClass("egradus-color-pestanya");
	
	// buidam les estadístiques dels qüestionaris
	$(".estProQuestionarisTaulaValor").empty();
	$("#estProQuestionarisNoEstadistiquesUnQuestionari").empty();
	$("#estProQuestionarisNoEstadistiquesConjunt").empty();
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// Omplim de qüestionaris l'input select
	var qstSeleccionat = "";
	$.ajax({
        url: "/egradus/estadistiques/professor/getQuestionarisDisponibles.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var inputSelectOptions = "";
        	var questionaris = $(xml).find("questionari");
        	questionaris.each(function(){
        		var codi = $(this).attr("codi");
        		var nom  = $(this).attr("nom");
        		
        		// recollim les opcions que inserirem a l'input select
    	        inputSelectOptions += "<option id=\"estProQuestionarisSelectQuestionari" + codi + "\" value=\"" + codi + "\">" + codi + " - " + nom;
    	        
    	        // guardam el primer codi de qüestionari que trobem
    	        if (qstSeleccionat == "") qstSeleccionat = codi;
        	});
        	
        	// inserim les opcions a l'input select de qüestionaris, i marcam com a seleccionat el 
        	// primer qüestionari que hem trobat a la cerca
        	$("#estProQuestionarisSelectUnQuestionari").html(inputSelectOptions);
        	if (questionaris.length == 0) {
        		// el professor no té qüestionaris amb els que visualitzar estadístiques
        		$("#estProQuestionarisNoEstadistiquesUnQuestionari").html("<div class=\"alert alert-info\" role=\"alert\">" +
			        														   "<h4>" + EST_PRO_NO_QST + "</h4><p>" + EST_PRO_NO_QST_DESC + "</p>" +
			        												      "</div>");
        	} else {
        		$("#estProQuestionarisSelectQuestionari" + qstSeleccionat).attr('selected', 'selected');
        		
        		// forçam la recàrrega de les estadístiques d'un qüestionari
            	determinarEstProfessorQuestionari();
        	}
        	
        },
        error:function(){
            alert("error habilita estadístiques de professors: questionaris");
        }
    });
	
	// forçam la recàrrega de les estadístiques de tots els qüestionaris d'una assignatura
	determinarEstProfessorQuestionarisAssignatura(codiAssignatura);
}

/**
 * habilita la vista sobre les estadístiques que veuen els 
 * professors sobre els alumnes
 */
function habilitaEstadistiquesAlu() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEstadistiquesAlu").show();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya d'estadístiques dels alumnes, per saber que ha
	// estat seleccionada pel professor
	$("#assignaturaMaterialEstadistiquesPro").addClass("egradus-color-pestanya");
	
	// buidam les estadístiques que es puguin haver mostrat anteriorment
	$(".estProAlumnes").empty();
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// Omplim l'input select per escollir alumne
	$.ajax({
        url: "/egradus/estadistiques/professor/getAlumnes.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var inputSelectAlumnes = "";
        	$(xml).find("alumne").each(function(){
        		var codi           = $(this).attr("codi");
        		var nom            = $(this).attr("nom");
        		var primerLlinatge = $(this).attr("llinatge1");
        		var segonLlinatge  = $(this).attr("llinatge2");
        		var alies          = $(this).attr("alies");
        		
        		var alumne = alies + " (" + nom + " " + primerLlinatge + " " + segonLlinatge + ")";
        		
        		inputSelectAlumnes += "<option id=\"estProAlumnesSelectAlumne" + codi + "\" value=\"" + codi + "\">" + alumne;
        	});
        	$("#estProAlumnesSelectAlumne").html(inputSelectAlumnes);
        },
        error:function(){
            alert("error habilita estadístiques de professors: alumnes disponibles");
        }
    });
}

/**
 * habilita la vista sobre les estadístiques del Repositori
 */
function habilitaEstadistiquesRep() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEstadistiquesRep").show();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya d'estadístiques del repositori, per saber que ha
	// estat seleccionada pel professor
	$("#assignaturaMaterialEstadistiquesPro").addClass("egradus-color-pestanya");
}

/**
 * habilita la vista dels ALUMNES per consultar les seves estadístiques de les preguntes
 */
function habilitaEstadistiquesPreAlu() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEstadistiquesPreAlu").show();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya d'estadístiques de preguntes dels alumnes, per saber que ha
	// estat seleccionada per l'alumne
	$("#assignaturaMaterialEstadistiquesAlu").addClass("egradus-color-pestanya");
	
	// definim un id base (ja que la problemàtica d'acontinuació segueix el mateix patró
	// que les estadístiques que veu el professor sobre un alumne (amb un altre id base)
	var idBase = "Alu";
	
	// esborram les gràfiques que puguin haver-se pintat en una consulta
	// d'estadístiques anterior i el div de la taula de valors
	$("#est" + idBase + "PreguntesGraficaNotes").empty();
	$("#est" + idBase + "PreguntesGraficaTempsResposta").empty();
	$("#est" + idBase + "PreguntesDivTaula").empty();
	
	// esborram el div que es mostra quan no existeixen estadístiques per l'alumne actual
	$("#est" + idBase + "PreguntesNoEstadistiques").empty();
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	$.ajax({
        url: "/egradus/estadistiques/getPreguntes.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	// pintam les estadístiques de les preguntes
        	estadistiquesPreguntesAlumne(xml, idBase);
        },
        error:function(){
            alert("error habilita estadístiques d'alumnes: preguntes");
        }
    });
}

/**
 * habilita la vista dels ALUMNES per consultar les seves estadístiques dels qüestionaris
 */
function habilitaEstadistiquesQstAlu() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEstadistiquesQstAlu").show();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya d'estadístiques de preguntes dels alumnes, per saber que ha
	// estat seleccionada per l'alumne
	$("#assignaturaMaterialEstadistiquesAlu").addClass("egradus-color-pestanya");
	
	// definim un id base (ja que la problemàtica d'acontinuació segueix el mateix patró
	// que les estadístiques que veu el professor sobre un alumne (amb un altre id base)
	var idBase = "Alu";
	
	// esborram les gràfiques que puguin haver-se pintat en una consulta
	// d'estadístiques anterior i el div de la taula de valors
	$("#est" + idBase + "QuestionarisGraficaNotes").empty();
	$("#est" + idBase + "QuestionarisGraficaTempsResposta").empty();
	$("#est" + idBase + "QuestionarisDivTaula").empty();
	$("#est" + idBase + "QuestionarisDivUnQuestionari").empty();
	
	// esborram el div que es mostra quan no existeixen estadístiques per l'alumne actual
	$("#est" + idBase + "QuestionarisNoEstadistiques").empty();
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	$.ajax({
        url: "/egradus/estadistiques/getQuestionaris.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	// pintam les estadístiques de les preguntes
        	estadistiquesQuestionarisAlumne(xml, idBase);
        },
        error:function(){
            alert("error habilita estadístiques d'alumnes: qüestionaris");
        }
    });
}

/**
 * Pinta la plantilla d'estadístiques de les preguntes d'un alumne, 
 * la informació del qual es troba a l'XML passat per paràmetre.
 * A més, com que aquesta funció és utilitzada pels següents casos:
 * 	· Estadístiques que veuen l'alumne sobre ell mateix
 *  · Estadístiques que veuen el professor sobre un alumne
 * passam un 'id' base distintiu pels dos casos.
 * 
 * @param xml
 * @param id
 */
function estadistiquesPreguntesAlumne(xml, id) {
	// obtenim el número total d'estadístiques que hem trobat
	var numPreguntes = $(xml).find("estadistiquesPregunta").length;
	if (numPreguntes == 0) {
		// l'alumne no té preguntes amb les que visualitzar estadístiques
		
		// escull el missatge que es mostrarà quan no hi ha estadístiques en funció si
		// és l'alumne que consulta les seves estadístiques o és el professor que consulta
		// estadístiques d'un alumne
		var misNoEstadistiquesTitol = EST_ALU_NO_CNT;
		var misNoEstadistiquesDesc = EST_ALU_PRE_NO_CNT_DESC;
		if (id == "ProAlumnes") {
			misNoEstadistiquesTitol = EST_PRO_ALU_NO_CNT;
			misNoEstadistiquesDesc = EST_PRO_ALU_PRE_NO_CNT_DESC;
		}
		$("#est" + id + "PreguntesNoEstadistiques").html("<div class=\"alert alert-info\" role=\"alert\">" +
														"<h4>" + misNoEstadistiquesTitol + "</h4><p>" + misNoEstadistiquesDesc + "</p>" +
												  "</div>");
	} else {
		// Habilitam el div per a què pugui contenir la taula de valors
		$("#est" + id + "PreguntesDivTaula").html("<fieldset>" +
											    "<legend>" + EST_ALU_LEGEND_TAULA + "</legend>" +
											    "<div id=\"est" + id + "PreguntesContenidorTaulaInfo\"></div>" +
											    "<div id=\"est" + id + "PreguntesContenidorTaula\"></div>" +
										   "</fieldset>");
		
		// Habilitam els divs per a què puguin contenir gràfiques
		$("#est" + id + "PreguntesGraficaNotes").html("<fieldset>" +
												    "<legend>" + EST_ALU_LEGEND_GRAF_NOTES + "</legend>" +
												    chartTemplate("est" + id + "PreguntesChartNotes", null) +
											   "</fieldset>");
		$("#est" + id + "PreguntesGraficaTempsResposta").html("<fieldset>" +
												           "<legend>" + EST_ALU_LEGEND_GRAF_TEMPS_RESP + "</legend>" +
												           chartTemplate("est" + id + "PreguntesChartTempsResposta", null) +
												       "</fieldset>");
		
		// definim la capçalera de la taula de valors de les estadístiques
		var divTaula = "<table id=\"est" + id + "PreguntesTaula\" class=\"table table-striped table-bordered table-condensed table-hover\">" +
		        		   "<thead><tr>" +
							   "<th>" + MIS_TAULA_REPO_PRE_CODI + "</th><th>" + MIS_TAULA_REPO_PRE_ENUNCIAT + "</th><th>" + MIS_TAULA_REPO_PRE_TIPUS + "</th><th>" + EST_ALU_TAULA_DATA_CNT + "</th><th>" + EST_ALU_TAULA_DATA_CRR + "</th><th>" + EST_ALU_TAULA_NOTA + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_EGR + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_ASS + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_EGR + "</th>" +
						   "</tr></thead>";
		
		var arrayCodisPregunta = [];
		var arrayNotes = [];
		var arrayNotesMitjesAss = [];
		var arrayNotesMitjesEgr = [];
		var arrayTempsResposta = [];
		var arrayTempsRespostaMitjosAss = [];
		var arrayTempsRespostaMitjosEgr = [];
		var indexArray = 0;
		$(xml).find("estadistiquesPregunta").each(function(){
	    	var codi         = $(this).find("pregunta").attr("codi");
	        var enunciat     = $(this).find("pregunta").attr("enunciat");
	        var tipus        = $(this).find("pregunta").attr("tipus");
	        var datacntfin   = dataTextualNavegador($(this).find("dates").attr("dataContestacioFi"));
	        var datacrr      = dataTextualNavegador($(this).find("dates").attr("dataCorreccio"));
	        var nota         = $(this).find("nota").attr("nota");
	        var notaMitjaAss = $(this).find("nota").attr("mitjaAssignatura");
	        var notaMitjaEgr = $(this).find("nota").attr("mitjaEgradus");
	        var tempsResp    = Math.round($(this).find("tempsResposta").attr("tempsResposta"));
	        var tempsRespMigAss = Math.round($(this).find("tempsResposta").attr("mitjaAssignatura"));
	        var tempsRespMigEgr = Math.round($(this).find("tempsResposta").attr("mitjaEgradus"));
	        
	        // si l'enunciat és massa llarg com per sortir a la taula, s'acurçarà:
	        if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
	        
	        // tipus de la pregunta
	        var tip = "Text per defecte";
	        switch(tipus) {
		    	case TIPUS_PREGUNTA_ES1:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_ES1;
		    		break;
		    	case TIPUS_PREGUNTA_ESN:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_ESN;
		            break;
		    	case TIPUS_PREGUNTA_VOF:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_VOF;
		    		break;
		    	case TIPUS_PREGUNTA_REC:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_REC;
		            break;
		    }
	        
	        // omplim l'array de codis de pregunta
	        arrayCodisPregunta[indexArray] = codi;
	        
	        var numDecimals = 2;
	        var notaArrodonida = decimalArrodonit(nota, numDecimals);
	        var notaMitjaAssArrodonida = decimalArrodonit(notaMitjaAss, numDecimals);
	        var notaMitjaEgrArrodonida = decimalArrodonit(notaMitjaEgr, numDecimals);
	        
	        // omplim els arrays amb els valors
	        arrayNotes[indexArray] = notaArrodonida;
	        arrayNotesMitjesAss[indexArray] = notaMitjaAssArrodonida;
	        arrayNotesMitjesEgr[indexArray] = notaMitjaEgrArrodonida;
	        arrayTempsResposta[indexArray] = tempsResp;
	        arrayTempsRespostaMitjosAss[indexArray] = tempsRespMigAss;
	        arrayTempsRespostaMitjosEgr[indexArray] = tempsRespMigEgr;
	        indexArray++;
	        
	        // definim les files de la taula
	        divTaula += "<tr><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>"+ codi +"</a></td><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>"+ enunciat +"</a></td><td>" + tip + "</td><td>" + formatData(datacntfin) + "</td><td>" + formatData(datacrr) + "</td><td>" + notaArrodonida + "</td><td>" + notaMitjaAssArrodonida + "</td><td>" + notaMitjaEgrArrodonida + "</td><td>" + formatTempsBySegons(tempsResp) + "</td><td>"+ formatTempsBySegons(tempsRespMigAss) +"</td><td>"+ formatTempsBySegons(tempsRespMigEgr) +"</td></tr>";
	    });
		divTaula += "</table>";
		
		// pintam la taula
    	$("#est" + id + "PreguntesContenidorTaula").html(divTaula);
    	
    	var divTaulaInfo = 	"<div class=\"alert alert-info\" role=\"alert\">" +
    							"<span class=\"glyphicon glyphicon-info-sign\"></span> " +
    							EST_ALU_PRE_INFO +
    						"</div>";
    	
    	// pintam el div que explica els valors de la taula
    	$("#est" + id + "PreguntesContenidorTaulaInfo").html(divTaulaInfo);
    	
		// pintam les gràfiques
		graficaPolinomica3var("est" + id + "PreguntesChartNotes", 
				true, 
				arrayCodisPregunta, 
				arrayNotes, 					EST_ALU_LEGEND_GRAFICA_NOTES, 
				arrayNotesMitjesAss, 			EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_ASS,
				arrayNotesMitjesEgr, 			EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_EGR);
		graficaPolinomica3var("est" + id + "PreguntesChartTempsResposta", 
				false, 
				arrayCodisPregunta, 
				arrayTempsResposta, 			EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA,
				arrayTempsRespostaMitjosAss, 	EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_ASS,
				arrayTempsRespostaMitjosEgr, 	EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_EGR);
	}
}

/**
 * Pinta la plantilla d'estadístiques de les preguntes del conjunt d'alumnes
 * de l'assignatura, la informació del qual es troba a l'XML passat per paràmetre.
 * 
 * @param xml
 * @param id
 */
function estadistiquesPreguntesConjuntAlumnes(xml, id) {
	// obtenim el número total d'estadistiques de preguntes que hem trobat
	var numPreguntes = $(xml).find("estadistiquesPregunta").length;
	if (numPreguntes == 0) {
		// no hi han estadístiques
		$("#est" + id + "PreguntesNoEstadistiques").html("<div class=\"alert alert-info\" role=\"alert\">" +
														"<h4>" + EST_PRO_CONJUNT_ALU_NO_CNT + "</h4><p>" + EST_PRO_CONJUNT_ALU_PRE_NO_CNT_DESC + "</p>" +
												  "</div>");
	} else {
		// Habilitam el div per a què pugui contenir la taula de valors
		$("#est" + id + "PreguntesDivTaula").html("<fieldset>" +
											    "<legend>" + EST_ALU_LEGEND_TAULA + "</legend>" +
											    "<div id=\"est" + id + "PreguntesContenidorTaulaInfo\"></div>" +
											    "<div id=\"est" + id + "PreguntesContenidorTaula\"></div>" +
										   "</fieldset>");
		
		// Habilitam els divs per a què puguin contenir gràfiques
		$("#est" + id + "PreguntesGraficaNotes").html("<fieldset>" +
												    "<legend>" + EST_ALU_LEGEND_GRAF_NOTES_MITGES + "</legend>" +
												    chartTemplate("est" + id + "PreguntesChartNotes", null) +
											   "</fieldset>");
		$("#est" + id + "PreguntesGraficaTempsResposta").html("<fieldset>" +
												           "<legend>" + EST_ALU_LEGEND_GRAF_TEMPS_RESP_MITJOS + "</legend>" +
												           chartTemplate("est" + id + "PreguntesChartTempsResposta", null) +
												       "</fieldset>");
		
		// definim la capçalera de la taula de valors de les estadístiques
		var divTaula = "<table id=\"est" + id + "PreguntesTaula\" class=\"table table-striped table-bordered table-condensed table-hover\">" +
		        		   "<thead><tr>" +
							   "<th>" + MIS_TAULA_REPO_PRE_CODI + "</th><th>" + MIS_TAULA_REPO_PRE_ENUNCIAT + "</th><th>" + MIS_TAULA_REPO_PRE_TIPUS + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_EGR + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_ASS + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_EGR + "</th><th>" + EST_PRO_TAULA_NUM_CONTESTACIONS + "</th><th>" + EST_PRO_TAULA_DETALL + "</th>" +
						   "</tr></thead>";
		
		// definició d'arrays d'etiquetes i de valors per a les gràfiques
		var arrayCodisPregunta = [];
		var arrayNotesMitjesAss = [];
		var arrayNotesMitjesEgr = [];
		var arrayTempsRespostaMitjosAss = [];
		var arrayTempsRespostaMitjosEgr = [];
		var indexArray = 0;
		$(xml).find("estadistiquesPregunta").each(function(){
	    	var codi            = $(this).find("pregunta").attr("codi");
	        var enunciat        = $(this).find("pregunta").attr("enunciat");
	        var tipus           = $(this).find("pregunta").attr("tipus");
	        var numCnt          = $(this).find("pregunta").attr("numContestacions");
	        var notaMitjaAss    = $(this).find("nota").attr("mitjaAssignatura");
	        var notaMitjaEgr    = $(this).find("nota").attr("mitjaEgradus");
	        var tempsRespMigAss = eliminaDecimals($(this).find("tempsResposta").attr("mitjaAssignatura"));
	        var tempsRespMigEgr = eliminaDecimals($(this).find("tempsResposta").attr("mitjaEgradus"));
	        
	        var tempsRespMigAssFormatData = formatTempsBySegons(tempsRespMigAss);
	        var tempsRespMigEgrFormatData = formatTempsBySegons(tempsRespMigEgr);
	        
	        // obtenim les estadístiques detallades de cada pregunta:
	        // quins alumnes l'han contestada i quantes vegades cada un
	        var detall = "";
	        $(this).find("detall").each(function(){
//	        <detall>
//				<alumne 	nom="${det.alumneNom}" 
//							primerLlinatge="${det.alumnePrimerLlinatge}" 
//							segonLlinatge="${det.alumneSegonLlinatge}" 
//							alies="${det.alumneAlies}"
//							numContestacions="${det.numContestacions}"/>
//				<nota	mitjaAssignatura="${det.notaMitjaAssignatura}"/>
//			</detall>
	        	var detallAliAlu = $(this).find("alumne").attr("alies");
	        	var detallNumCnt = $(this).find("alumne").attr("numContestacions");
	        	
	        	detall += detallAliAlu + " (" + detallNumCnt + "), ";
	        });
	        detall = detall.substring(0, detall.length - 2); // per eliminar la darrera 'coma' i l'espai
	        
	        // si l'enunciat és massa llarg com per sortir a la taula, s'acurçarà:
	        if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
	        
	        // tipus de la pregunta
	        var tip = "Text per defecte";
	        switch(tipus) {
		    	case TIPUS_PREGUNTA_ES1:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_ES1;
		    		break;
		    	case TIPUS_PREGUNTA_ESN:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_ESN;
		            break;
		    	case TIPUS_PREGUNTA_VOF:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_VOF;
		    		break;
		    	case TIPUS_PREGUNTA_REC:
		    		tip = MIS_TAULA_REPO_PRE_TIPUS_REC;
		            break;
		    }
	        
	        // omplim els arrays amb els valors extrets
	        arrayCodisPregunta[indexArray] = codi;
	        arrayNotesMitjesAss[indexArray] = notaMitjaAss;
	        arrayNotesMitjesEgr[indexArray] = notaMitjaEgr;
	        arrayTempsRespostaMitjosAss[indexArray] = tempsRespMigAss;
	        arrayTempsRespostaMitjosEgr[indexArray] = tempsRespMigEgr;
	        indexArray++;
	        
	        // definim les files de la taula
	        divTaula += "<tr><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>"+ codi +"</a></td><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>"+ enunciat +"</a></td><td>" + tip + "</td><td>" + notaMitjaAss + "</td><td>" + notaMitjaEgr + "</td><td>" + tempsRespMigAssFormatData + "</td><td>" + tempsRespMigEgrFormatData + "</td><td>" + numCnt + "</td><td>" + detall + "</td></tr>";
	        
	    });
		divTaula += "</table>";
		
		// pintam la taula
    	$("#est" + id + "PreguntesContenidorTaula").html(divTaula);
    	
    	var divTaulaInfo = 	"<div class=\"alert alert-info\" role=\"alert\">" +
								"<span class=\"glyphicon glyphicon-info-sign\"></span> " +
								EST_CJT_ALU_PRE_INFO +
							"</div>";
		
		// pintam el div que explica els valors de la taula
		$("#est" + id + "PreguntesContenidorTaulaInfo").html(divTaulaInfo);
    	
		// pintam les gràfiques
		graficaPolinomica2var("est" + id + "PreguntesChartNotes", 
				true, 
				arrayCodisPregunta, 
				arrayNotesMitjesAss, EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_ASS,
				arrayNotesMitjesEgr, EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_EGR);
		graficaPolinomica2var("est" + id + "PreguntesChartTempsResposta", 
				false, 
				arrayCodisPregunta, 
				arrayTempsRespostaMitjosAss, EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_ASS,
				arrayTempsRespostaMitjosEgr, EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_EGR);
	}
}

/**
 * Pinta la plantilla d'estadístiques dels qüestionaris d'un alumne, 
 * la informació del qual es troba a l'XML passat per paràmetre.
 * A més, com que aquesta funció és utilitzada pels següents casos:
 * 	· Estadístiques que veuen l'alumne sobre ell mateix
 *  · Estadístiques que veuen el professor sobre un alumne
 * passam un 'id' base distintiu pels dos casos.
 * 
 * @param xml
 * @param id
 */
function estadistiquesQuestionarisAlumne(xml, id) {
	// obtenim el número total de resposta-questionaris que hem trobat
	var numQuestionaris = $(xml).find("estadistiquesQuestionari").length;
	if (numQuestionaris == 0) {
		// l'alumne no té qüestionaris amb els que visualitzar estadístiques
		
		// escull el missatge que es mostrarà quan no hi ha estadístiques en funció si
		// és l'alumne que consulta les seves estadístiques o és el professor que consulta
		// estadístiques d'un alumne
		var misNoEstadistiquesTitol = EST_ALU_NO_CNT;
		var misNoEstadistiquesDesc = EST_ALU_QST_NO_CNT_DESC;
		if (id == "ProAlumnes") {
			misNoEstadistiquesTitol = EST_PRO_ALU_NO_CNT;
			misNoEstadistiquesDesc = EST_PRO_ALU_QST_NO_CNT_DESC;
		}
		$("#est" + id + "QuestionarisNoEstadistiques").html("<div class=\"alert alert-info\" role=\"alert\">" +
    													  "<h4>" + misNoEstadistiquesTitol + "</h4><p>" + misNoEstadistiquesDesc + "</p>" +
    												 "</div>");
	} else {
		// Habilitam el div per a què pugui contenir la taula de valors
		$("#est" + id + "QuestionarisDivTaula").html("<fieldset>" +
											       "<legend>" + EST_ALU_LEGEND_TAULA + "</legend>" +
											       "<div id=\"est" + id + "QuestionarisContenidorTaulaInfo\"></div>" +
											       "<div id=\"est" + id + "QuestionarisContenidorTaula\"></div>" +
										      "</fieldset>");
		
		// input select que escollirà un qüestionari per veure-ne les seves estadístiques
		var divInputSelect = 	"<div class=\"form-group col-xs-12 col-sm-9 col-md-6 col-lg-4\">" + 
    								"<label for=\"est" + id + "QuestionarisSelectIndividual\">" + EST_ALU_QST_LEGEND_SELECT_LABEL + "</label>" +
									"<select id=\"est" + id + "QuestionarisSelectIndividual\" class=\"form-control\" onchange=\"determinarEstQuestionari('" + id + "')\">" +
									"</select>" +
								"</div>";
		        		
		// div d'estadístiques d'un sol qüestionari
		var divUnQuestionari = 	"<fieldset>" +
									"<legend>" + EST_ALU_QST_LEGEND_TAULA_IND + "</legend>" +
									"<div class=\"row\">" +
										"<div class=\"col-xs-12\">" +
											"<div class=\"row\">" +
												"<div id=\"est" + id + "QuestionarisContenidorTaulaIndividualInfo\"></div>" +
											"</div>" + 
											"<div class=\"row\">" +
												divInputSelect +
											"</div>" +
											"<div class=\"row\">" +
												"<div class=\"col-xs-12\">" +
									        		"<div id=\"est" + id + "QuestionarisContenidorTaulaIndividual\"></div>" +
												"</div>" +
	        								"</div>" +
										"</div>" + 
									"</div>" +
									"<div class=\"row\">" +
										"<div class=\"col-xs-12 col-lg-6\">" +
							        		chartTemplate("est" + id + "QuestionarisChartIndividualNotes", null) + 
										"</div>" + 
										"<div class=\"col-xs-12 col-lg-6\">" +
							        		chartTemplate("est" + id + "QuestionarisChartIndividualPesos", null) + 
										"</div>" + 
									"</div>" + 
								"</fieldset>";
		
		$("#est" + id + "QuestionarisDivUnQuestionari").html(divUnQuestionari);
		
		// Habilitam els divs per a què puguin contenir gràfiques
		$("#est" + id + "QuestionarisGraficaNotes").html("<fieldset>" +
												       "<legend>" + EST_ALU_LEGEND_GRAF_NOTES + "</legend>" +
												       chartTemplate("est" + id + "QuestionarisChartNotes", null) + 
											      "</fieldset>");
		$("#est" + id + "QuestionarisGraficaTempsResposta").html("<fieldset>" +
												              "<legend>" + EST_ALU_LEGEND_GRAF_TEMPS_RESP + "</legend>" +
												              chartTemplate("est" + id + "QuestionarisChartTempsResposta", null) + 
												          "</fieldset>");
		
		// definim la capçalera de la taula de valors de les estadístiques
		var divTaula = "<table id=\"est" + id + "QuestionarisTaula\" class=\"table table-striped table-bordered table-condensed table-hover\">" +
		        		   "<thead><tr>" +
							   "<th>" + MIS_TAULA_REPO_QST_CODI + "</th><th>" + MIS_TAULA_REPO_QST_NOM + "</th><th>" + MIS_TAULA_REPO_QST_DESCRIPCIO + "</th><th>" + EST_ALU_TAULA_DATA_CNT + "</th><th>" + EST_ALU_TAULA_DATA_CRR + "</th><th>" + EST_ALU_TAULA_NOTA + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_EGR + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_ASS + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_EGR + "</th>" +
						   "</tr></thead>";
		
		// definim la capçalera de la taula de valors de les estadístiques individuals
		var divTaulaInd = "<table id=\"est" + id + "QuestionarisTaulaIndividual\" class=\"table table-striped table-bordered table-condensed table-hover\"></table>";
		
		// pel select input que selecciona de quin qüestionari volem consultar les estadístiques
		var rqSeleccionada = "";
		var inputSelectOptions = "";
		
		// definició d'arrays d'etiquetes i de valors per a les gràfiques
		var arrayCodisQuestionari = [];
		var arrayNotes = [];
		var arrayNotesMitjesAss = [];
		var arrayNotesMitjesEgr = [];
		var arrayTempsResposta = [];
		var arrayTempsRespostaMitjosAss = [];
		var arrayTempsRespostaMitjosEgr = [];
		var indexArray = 0;
		$(xml).find("estadistiquesQuestionari").each(function(){
			var codiRq       = $(this).find("questionari").attr("codiRq");
			var codi         = $(this).find("questionari").attr("codi");
	        var nom          = $(this).find("questionari").attr("nom");
	        var descripcio   = $(this).find("questionari").attr("descripcio");
	        var datacntfin   = dataTextualNavegador($(this).find("dates").attr("dataContestacioFi"));
	        var datacrr      = dataTextualNavegador($(this).find("dates").attr("dataCorreccio"));
	        var nota         = $(this).find("nota").attr("nota");
	        var notaMitjaAss = $(this).find("nota").attr("mitjaAssignatura");
	        var notaMitjaEgr = $(this).find("nota").attr("mitjaEgradus");
	        var tempsResp    = Math.round($(this).find("tempsResposta").attr("tempsResposta"));
	        var tempsRespMigAss = Math.round($(this).find("tempsResposta").attr("mitjaAssignatura"));
	        var tempsRespMigEgr = Math.round($(this).find("tempsResposta").attr("mitjaEgradus"));
	        
	        // si el nom és massa llarg com per sortir a la taula, s'acurçarà:
	        if (nom.length > 100) nom = nom.substring(0, 100) + "...";
	        
	        // si la descripció és massa llarg com per sortir a la taula, s'acurçarà:
	        if (descripcio.length > 100) descripcio = descripcio.substring(0, 100) + "...";
	        
	        // omplim l'array de codis de qüestionari
	        arrayCodisQuestionari[indexArray] = codi;
	        
	        // arrodonim els valors de les notes
	        var numDecimals         = 2;
	        var notaArrodonida      = decimalArrodonit(nota, numDecimals);
	        var notaMitjaAssArrodonida = decimalArrodonit(notaMitjaAss, numDecimals);
	        var notaMitjaEgrArrodonida = decimalArrodonit(notaMitjaEgr, numDecimals);
	        
	        // omplim els arrays amb els valors
	        arrayNotes[indexArray] = notaArrodonida;
	        arrayNotesMitjesAss[indexArray] = notaMitjaAssArrodonida;
	        arrayNotesMitjesEgr[indexArray] = notaMitjaEgrArrodonida;
	        arrayTempsResposta[indexArray] = tempsResp;
	        arrayTempsRespostaMitjosAss[indexArray] = tempsRespMigAss;
	        arrayTempsRespostaMitjosEgr[indexArray] = tempsRespMigEgr;
	        indexArray++;
	        
	        // definim les files de la taula
	        divTaula += "<tr><td><a href='javascript: habilitaDescripcioQuestionari(" + codi + ", 0);'>"+ codi +"</a></td><td><a href='javascript: habilitaDescripcioQuestionari(" + codi + ", 0);'>"+ nom +"</a></td><td>" + descripcio + "</td><td>" + formatData(datacntfin) + "</td><td>" + formatData(datacrr) + "</td><td>" + notaArrodonida + "</td><td>" + notaMitjaAssArrodonida + "</td><td>" + notaMitjaEgrArrodonida + "</td><td>" + formatTempsBySegons(tempsResp) + "</td><td>"+ formatTempsBySegons(tempsRespMigAss) +"</td><td>"+ formatTempsBySegons(tempsRespMigEgr) +"</td></tr>";
	        
	        // El primer codi que trobem serà el que es mostrarà d'inici
	        if (rqSeleccionada == "") rqSeleccionada = codiRq; 
	        
	        // recollim les opcions que inserirem a l'input select
	        inputSelectOptions += "<option id=\"est" + id + "QuestinarisSelectQuestionari" + codiRq + "\" value=\"" + codiRq + "\">" + codi + " - " + nom + " (ref: " + codiRq + ")";
	    });
		divTaula += "</table>";
		
		// pintam les taules (la d'estadístiques de qüestionaris i la d'estadístiques d'UN qüestionari)
    	$("#est" + id + "QuestionarisContenidorTaula").html(divTaula);
    	$("#est" + id + "QuestionarisContenidorTaulaIndividual").html(divTaulaInd);
    	
    	// missatge informatiu de les estadístiques dels Qüestionaris
    	var divTaulaInfo = 	"<div class=\"alert alert-info\" role=\"alert\">" +
								"<span class=\"glyphicon glyphicon-info-sign\"></span> " +
								EST_ALU_QST_INFO +
							"</div>";
    	$("#est" + id + "QuestionarisContenidorTaulaInfo").html(divTaulaInfo);
    	
    	// missatge informatiu de les estadístiques d'1 Qüestionari
    	var divTaulaIndividualInfo = 	"<div class=\"alert alert-info\" role=\"alert\">" +
											"<span class=\"glyphicon glyphicon-info-sign\"></span> " +
											EST_ALU_QST_IND_INFO +
										"</div>";
    	$("#est" + id + "QuestionarisContenidorTaulaIndividualInfo").html(divTaulaIndividualInfo);
    	
    	// inserim les opcions a l'input select que ens permet escollir UN qüestionari per a
        // veure les seves estadístiques i seleccionam el primer qüestionari
    	$("#est" + id + "QuestionarisSelectIndividual").html(inputSelectOptions);
    	$("#est" + id + "QuestinarisSelectQuestionari" + rqSeleccionada).attr('selected', 'selected');
    	determinarEstQuestionari(id); // forçam la càrrega de les preguntes del qüestionari seleccionat inicialment
		
		// pintam les gràfiques
		graficaPolinomica3var("est" + id + "QuestionarisChartNotes", 
				true, 
				arrayCodisQuestionari, 
				arrayNotes, 				 EST_ALU_LEGEND_GRAFICA_NOTES,
				arrayNotesMitjesAss, 		 EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_ASS,
				arrayNotesMitjesEgr, 		 EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_EGR);
		graficaPolinomica3var("est" + id + "QuestionarisChartTempsResposta", 
				false, 
				arrayCodisQuestionari, 
				arrayTempsResposta, 		 EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA,
				arrayTempsRespostaMitjosAss, EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_ASS,
				arrayTempsRespostaMitjosEgr, EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_EGR);
	}
}

/**
 * Pinta la plantilla d'estadístiques dels qüestionaris del conjunt d'alumnes
 * de l'assignatura, la informació del qual es troba a l'XML passat per paràmetre.
 * 
 * @param xml
 * @param id
 */
function estadistiquesQuestionarisConjuntAlumnes(xml, id) {
	// obtenim el número total d'estadistiques de qüestionaris que hem trobat
	var numQuestionaris = $(xml).find("estadistiquesQuestionari").length;
	if (numQuestionaris == 0) {
		// l'alumne no té qüestionaris amb els que visualitzar estadístiques
		$("#est" + id + "QuestionarisNoEstadistiques").html("<div class=\"alert alert-info\" role=\"alert\">" +
    													  "<h4>" + EST_PRO_CONJUNT_ALU_NO_CNT + "</h4><p>" + EST_PRO_CONJUNT_ALU_QST_NO_CNT_DESC + "</p>" +
    												 "</div>");
	} else {
		// Habilitam el div per a què pugui contenir la taula de valors
		$("#est" + id + "QuestionarisDivTaula").html("<fieldset>" +
													      "<legend>" + EST_ALU_LEGEND_TAULA + "</legend>" +
													      "<div id=\"est" + id + "QuestionarisContenidorTaulaInfo\"></div>" +
													      "<div id=\"est" + id + "QuestionarisContenidorTaula\"></div>" +
												     "</fieldset>");
		
		// input select que escollirà un qüestionari per veure-ne les seves estadístiques
		var divInputSelect = 	"<div class=\"form-group col-xs-12 col-sm-12 col-md-12 col-lg-9\">" + 
    								"<label for=\"est" + id + "QuestionarisSelectIndividual\">" + EST_ALU_QST_LEGEND_SELECT_LABEL + "</label>" +
									"<select id=\"est" + id + "QuestionarisSelectIndividual\" class=\"form-control\" onchange=\"determinarEstQuestionariConjuntAlumnes('" + id + "')\">" +
									"</select>" +
								"</div>";
		
		// div d'estadístiques d'un sol qüestionari
		var divUnQuestionari = 	"<fieldset>" +
									"<legend>" + EST_ALU_QST_LEGEND_TAULA_IND + "</legend>" +
									"<div class=\"row\">" +
										"<div class=\"col-xs-12\">" +
											"<div class=\"row\">" +
												"<div id=\"est" + id + "QuestionarisContenidorTaulaIndividualInfo\"></div>" +
											"</div>" + 
											"<div class=\"row\">" +
												divInputSelect +
											"</div>" +
											"<div class=\"row\">" +
												"<div class=\"col-xs-12\">" +
									        		"<div id=\"est" + id + "QuestionarisContenidorTaulaIndividual\"></div>" +
												"</div>" +
	        								"</div>" +
										"</div>" + 
									"</div>" +
									"<div class=\"row\">" +
										"<div class=\"col-xs-12 col-lg-6\">" +
							        		chartTemplate("est" + id + "QuestionarisChartIndividualNotes", null) + 
										"</div>" + 
										"<div class=\"col-xs-12 col-lg-6\">" +
							        		chartTemplate("est" + id + "QuestionarisChartIndividualPesos", null) + 
										"</div>" + 
									"</div>" + 
								"</fieldset>";
		
		$("#est" + id + "QuestionarisDivUnQuestionari").html(divUnQuestionari);
		
		// Habilitam els divs per a què puguin contenir gràfiques
		$("#est" + id + "QuestionarisGraficaNotes").html("<fieldset>" + 
												       "<legend>" + EST_ALU_LEGEND_GRAF_NOTES_MITGES + "</legend>" +
												       chartTemplate("est" + id + "QuestionarisChartNotes", null) + 
											      "</fieldset>");
		$("#est" + id + "QuestionarisGraficaTempsResposta").html("<fieldset>" +
												              "<legend>" + EST_ALU_LEGEND_GRAF_TEMPS_RESP_MITJOS + "</legend>" +
												              chartTemplate("est" + id + "QuestionarisChartTempsResposta", null) + 
												          "</fieldset>");
		
		// definim la capçalera de la taula de valors de les estadístiques
		var divTaula = "<table id=\"est" + id + "QuestionarisTaula\" class=\"table table-striped table-bordered table-condensed table-hover\">" +
		        		   "<thead><tr>" +
							   "<th>" + MIS_TAULA_REPO_QST_CODI + "</th><th>" + MIS_TAULA_REPO_QST_NOM + "</th><th>" + MIS_TAULA_REPO_QST_DESCRIPCIO + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_EGR + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_ASS + "</th><th>" + EST_ALU_TAULA_TEMPS_RESP_MIG_EGR + "</th><th>" + EST_PRO_TAULA_NUM_CONTESTACIONS + "</th><th>" + EST_PRO_TAULA_DETALL + "</th>" +
						   "</tr></thead>";
		
		// definim la capçalera de la taula de valors de les estadístiques individuals
		var divTaulaInd = "<table id=\"est" + id + "QuestionarisTaulaIndividual\" class=\"table table-striped table-bordered table-condensed table-hover\"></table>";
		
		// pel select input que selecciona de quin qüestionari volem consultar les estadístiques
		var questionariSeleccionat = "";
		var inputSelectOptions = "";
		
		// definició d'arrays d'etiquetes i de valors per a les gràfiques
		var arrayCodisQuestionari = [];
		var arrayNotesMitjesAss = [];
		var arrayNotesMitjesEgr = [];
		var arrayTempsRespostaMitjosAss = [];
		var arrayTempsRespostaMitjosEgr = [];
		var indexArray = 0;
		$(xml).find("estadistiquesQuestionari").each(function(){
			var codi         = $(this).find("questionari").attr("codi");
	        var nom          = $(this).find("questionari").attr("nom");
	        var descripcio   = $(this).find("questionari").attr("descripcio");
	        var numCnt		 = $(this).find("questionari").attr("numContestacions");
	        var notaMitjaAss    = $(this).find("nota").attr("mitjaAssignatura");
	        var notaMitjaEgr    = $(this).find("nota").attr("mitjaEgradus");
	        var tempsRespMigAss = eliminaDecimals($(this).find("tempsResposta").attr("mitjaAssignatura"));
	        var tempsRespMigEgr = eliminaDecimals($(this).find("tempsResposta").attr("mitjaEgradus"));
	        
	        var tempsRespMigAssFormatData = formatTempsBySegons(tempsRespMigAss);
	        var tempsRespMigEgrFormatData = formatTempsBySegons(tempsRespMigEgr);
	        
	        // obtenim les estadístiques detallades de cada qüestionari:
	        // quins alumnes l'han contestat i quantes vegades cada un
	        var detall = "";
	        $(this).find("detall").each(function(){
//	        <detall>
//				<alumne 	nom="${det.alumneNom}" 
//							primerLlinatge="${det.alumnePrimerLlinatge}" 
//							segonLlinatge="${det.alumneSegonLlinatge}" 
//							alies="${det.alumneAlies}"
//							numContestacions="${det.numContestacions}"/>
//				<nota	mitjaAssignatura="${det.notaMitjaAssignatura}"/>
//			</detall>
	        	var detallAliAlu = $(this).find("alumne").attr("alies");
	        	var detallNumCnt = $(this).find("alumne").attr("numContestacions");
	        	
	        	detall += detallAliAlu + " (" + detallNumCnt + "), ";
	        });
	        detall = detall.substring(0, detall.length - 2); // per eliminar la darrera 'coma' i l'espai
	        
	        // si el nom o la descripció són massa llargs com per sortir a la taula, s'acurçaran:
	        if (nom.length > 100) nom = nom.substring(0, 100) + "...";
	        if (descripcio.length > 100) descripcio = descripcio.substring(0, 100) + "...";
	        
	        // omplim els arrays amb els valors extrets
	        arrayCodisQuestionari[indexArray] = codi;
	        arrayNotesMitjesAss[indexArray] = notaMitjaAss;
	        arrayNotesMitjesEgr[indexArray] = notaMitjaEgr;
	        arrayTempsRespostaMitjosAss[indexArray] = tempsRespMigAss;
	        arrayTempsRespostaMitjosEgr[indexArray] = tempsRespMigEgr;
	        indexArray++;
	        
	        // definim les files de la taula
	        divTaula += "<tr><td><a href='javascript: habilitaDescripcioQuestionari(" + codi + ", 0);'>"+ codi +"</a></td><td><a href='javascript: habilitaDescripcioQuestionari(" + codi + ", 0);'>"+ nom +"</a></td><td>" + descripcio + "</td><td>" + notaMitjaAss + "</td><td>" + notaMitjaEgr + "</td><td>" + tempsRespMigAssFormatData + "</td><td>" + tempsRespMigEgrFormatData + "</td><td>" + numCnt + "</td><td>" + detall + "</td></tr>";
	        
	        // El primer codi que trobem serà el que es mostrarà d'inici
	        if (questionariSeleccionat == "") questionariSeleccionat = codi;
	        
	        // recollim les opcions que inserirem a l'input select
	        inputSelectOptions += "<option id=\"est" + id + "QuestinarisSelectQuestionari" + codi + "\" value=\"" + codi + "\">" + codi + " - " + nom;
	    });
		divTaula += "</table>";
		
		// pintam les taules (la d'estadístiques de qüestionaris i la d'estadístiques d'UN qüestionari)
    	$("#est" + id + "QuestionarisContenidorTaula").html(divTaula);
    	$("#est" + id + "QuestionarisContenidorTaulaIndividual").html(divTaulaInd);
    	
    	// pintam els divs que expliquen els valors de les taules
    	var divTaulaInfo = 	"<div class=\"alert alert-info\" role=\"alert\">" +
								"<span class=\"glyphicon glyphicon-info-sign\"></span> " +
								EST_CJT_ALU_QST_INFO +
							"</div>";
		var divTaulaIndividualInfo = 	"<div class=\"alert alert-info\" role=\"alert\">" +
											"<span class=\"glyphicon glyphicon-info-sign\"></span> " +
											EST_CJT_ALU_QST_IND_INFO +
										"</div>";
		$("#est" + id + "QuestionarisContenidorTaulaInfo").html(divTaulaInfo);
		$("#est" + id + "QuestionarisContenidorTaulaIndividualInfo").html(divTaulaIndividualInfo);
    	
    	// inserim les opcions a l'input select que ens permet escollir UN qüestionari per a
        // veure les seves estadístiques i seleccionam el primer qüestionari
    	$("#est" + id + "QuestionarisSelectIndividual").html(inputSelectOptions);
    	$("#est" + id + "QuestinarisSelectQuestionari" + questionariSeleccionat).attr('selected', 'selected');
    	
    	// forçam la càrrega de les preguntes del qüestionari seleccionat inicialment
    	determinarEstQuestionariConjuntAlumnes(id);
		
		// pintam les gràfiques
		graficaPolinomica2var("est" + id + "QuestionarisChartNotes", 
				true, 
				arrayCodisQuestionari, 
				arrayNotesMitjesAss, EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_ASS,
				arrayNotesMitjesEgr, EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_EGR);
		graficaPolinomica2var("est" + id + "QuestionarisChartTempsResposta", 
				false, 
				arrayCodisQuestionari, 
				arrayTempsRespostaMitjosAss, EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_ASS,
				arrayTempsRespostaMitjosEgr, EST_ALU_LEGEND_GRAFICA_TEMPS_RESPOSTA_MIG_EGR);
	}
}

/**
 * funció local que s'executa cada cop el professor canvia de pregunta en la selecció
 * de les estadístiques de les preguntes
 */
function determinarEstProfessorPregunta() {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// agafam el codi de pregunta seleccionat a l'input select
	var codiPregunta = $("select#estProPreguntesSelectUnaPregunta option").filter(":selected").val();
	
	var params = "codiAssignatura=" + codiAssignatura + "&codiPregunta=" + codiPregunta;
	$.ajax({
        url: "/egradus/estadistiques/professor/pregunta.xml?" + params,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	// estadístiques d'assignació directa mitjes d'assignatura
        	var asiDirAssRebudes = $(xml).find("assignacioDirecta > assignatura > rebudes").text();
        	var asiDirAssContestades = $(xml).find("assignacioDirecta > assignatura > contestades").text();
        	var asiDirAssCorregides = $(xml).find("assignacioDirecta > assignatura > corregides").text();
        	var asiDirAssAprovades = $(xml).find("assignacioDirecta > assignatura > aprovades").text();
        	var asiDirAssNotaMitja = $(xml).find("assignacioDirecta > assignatura > notaMitja").text();
        	var asiDirAssTempsRespostaMig = $(xml).find("assignacioDirecta > assignatura > tempsRespostaMig").text();
        	
        	// estadístiques d'assignació directa mitjes d'egradus
        	var asiDirEgrRebudes = $(xml).find("assignacioDirecta > egradus > rebudes").text();
        	var asiDirEgrContestades = $(xml).find("assignacioDirecta > egradus > contestades").text();
        	var asiDirEgrCorregides = $(xml).find("assignacioDirecta > egradus > corregides").text();
        	var asiDirEgrAprovades = $(xml).find("assignacioDirecta > egradus > aprovades").text();
        	var asiDirEgrNotaMitja = $(xml).find("assignacioDirecta > egradus > notaMitja").text();
        	var asiDirEgrTempsRespostaMig = $(xml).find("assignacioDirecta > egradus > tempsRespostaMig").text();
        	
        	// estadístiques d'assignació a través d'algun qüestionari mitjes d'assignatura
        	var asiQstAssRebudes = $(xml).find("assignacioQuestionari > assignatura > rebudes").text();
        	var asiQstAssContestades = $(xml).find("assignacioQuestionari > assignatura > contestades").text();
        	var asiQstAssCorregides = $(xml).find("assignacioQuestionari > assignatura > corregides").text();
        	var asiQstAssAprovades = $(xml).find("assignacioQuestionari > assignatura > aprovades").text();
        	var asiQstAssNotaMitja = $(xml).find("assignacioQuestionari > assignatura > notaMitja").text();
        	var asiQstAssTempsRespostaMig = $(xml).find("assignacioQuestionari > assignatura > tempsRespostaMig").text();
        	
        	// estadístiques d'assignació a través d'algun qüestionari mitjes d'egradus
        	var asiQstEgrRebudes = $(xml).find("assignacioQuestionari > egradus > rebudes").text();
        	var asiQstEgrContestades = $(xml).find("assignacioQuestionari > egradus > contestades").text();
        	var asiQstEgrCorregides = $(xml).find("assignacioQuestionari > egradus > corregides").text();
        	var asiQstEgrAprovades = $(xml).find("assignacioQuestionari > egradus > aprovades").text();
        	var asiQstEgrNotaMitja = $(xml).find("assignacioQuestionari > egradus > notaMitja").text();
        	var asiQstEgrTempsRespostaMig = $(xml).find("assignacioQuestionari > egradus > tempsRespostaMig").text();
        	
        	if (asiDirAssTempsRespostaMig.length > 0) asiDirAssTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiDirAssTempsRespostaMig/1000));
        	if (asiDirEgrTempsRespostaMig.length > 0) asiDirEgrTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiDirEgrTempsRespostaMig/1000));
        	if (asiQstAssTempsRespostaMig.length > 0) asiQstAssTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiQstAssTempsRespostaMig/1000));
        	if (asiQstEgrTempsRespostaMig.length > 0) asiQstEgrTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiQstEgrTempsRespostaMig/1000));
        	
        	var numDecimals = 2;
        	
        	$("#estProPreguntesTaulaRebudesAssignacioDirecta").html(asiDirAssRebudes);
        	$("#estProPreguntesTaulaContestadesAssignacioDirecta").html(asiDirAssContestades);
        	$("#estProPreguntesTaulaCorregidesAssignacioDirecta").html(asiDirAssCorregides);
        	$("#estProPreguntesTaulaAprovadesAssignacioDirecta").html(asiDirAssAprovades);
        	$("#estProPreguntesTaulaNotaMitjaAssignacioDirecta").html(decimalArrodonit(asiDirAssNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigAssignacioDirecta").html(asiDirAssTempsRespostaMig);
        	
        	$("#estProPreguntesTaulaRebudesEgradusAssignacioDirecta").html(asiDirEgrRebudes);
        	$("#estProPreguntesTaulaContestadesEgradusAssignacioDirecta").html(asiDirEgrContestades);
        	$("#estProPreguntesTaulaCorregidesEgradusAssignacioDirecta").html(asiDirEgrCorregides);
        	$("#estProPreguntesTaulaAprovadesEgradusAssignacioDirecta").html(asiDirEgrAprovades);
        	$("#estProPreguntesTaulaNotaMitjaEgradusAssignacioDirecta").html(decimalArrodonit(asiDirEgrNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigEgradusAssignacioDirecta").html(asiDirEgrTempsRespostaMig);
        	
        	$("#estProPreguntesTaulaRebudesAssignacioQuestionari").html(asiQstAssRebudes);
        	$("#estProPreguntesTaulaContestadesAssignacioQuestionari").html(asiQstAssContestades);
        	$("#estProPreguntesTaulaCorregidesAssignacioQuestionari").html(asiQstAssCorregides);
        	$("#estProPreguntesTaulaAprovadesAssignacioQuestionari").html(asiQstAssAprovades);
        	$("#estProPreguntesTaulaNotaMitjaAssignacioQuestionari").html(decimalArrodonit(asiQstAssNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigAssignacioQuestionari").html(asiQstAssTempsRespostaMig);
        	
        	$("#estProPreguntesTaulaRebudesEgradusAssignacioQuestionari").html(asiQstEgrRebudes);
        	$("#estProPreguntesTaulaContestadesEgradusAssignacioQuestionari").html(asiQstEgrContestades);
        	$("#estProPreguntesTaulaCorregidesEgradusAssignacioQuestionari").html(asiQstEgrCorregides);
        	$("#estProPreguntesTaulaAprovadesEgradusAssignacioQuestionari").html(asiQstEgrAprovades);
        	$("#estProPreguntesTaulaNotaMitjaEgradusAssignacioQuestionari").html(decimalArrodonit(asiQstEgrNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigEgradusAssignacioQuestionari").html(asiQstEgrTempsRespostaMig);
        },
        error:function(){
            alert("error determinant estadístiques de professors: una pregunta");
        }
    });
}

/**
 * funció local que mostra les estadístiques dels professors en referència a
 * totes les preguntes enviades en l'àmbit de l'assignatura actual
 * 
 * @param codiAssignatura
 */
function determinarEstProfessorPreguntesAssignatura(codiAssignatura) {
	$.ajax({
        url: "/egradus/estadistiques/professor/conjuntPreguntes.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	// estadístiques d'assignació directa mitjes d'assignatura
        	var asiDirAssRebudes = $(xml).find("assignacioDirecta > assignatura > rebudes").text();
        	var asiDirAssContestades = $(xml).find("assignacioDirecta > assignatura > contestades").text();
        	var asiDirAssCorregides = $(xml).find("assignacioDirecta > assignatura > corregides").text();
        	var asiDirAssAprovades = $(xml).find("assignacioDirecta > assignatura > aprovades").text();
        	var asiDirAssNotaMitja = $(xml).find("assignacioDirecta > assignatura > notaMitja").text();
        	var asiDirAssTempsRespostaMig = $(xml).find("assignacioDirecta > assignatura > tempsRespostaMig").text();
        	
        	// estadístiques d'assignació directa mitjes d'egradus
        	var asiDirEgrRebudes = $(xml).find("assignacioDirecta > egradus > rebudes").text();
        	var asiDirEgrContestades = $(xml).find("assignacioDirecta > egradus > contestades").text();
        	var asiDirEgrCorregides = $(xml).find("assignacioDirecta > egradus > corregides").text();
        	var asiDirEgrAprovades = $(xml).find("assignacioDirecta > egradus > aprovades").text();
        	var asiDirEgrNotaMitja = $(xml).find("assignacioDirecta > egradus > notaMitja").text();
        	var asiDirEgrTempsRespostaMig = $(xml).find("assignacioDirecta > egradus > tempsRespostaMig").text();
        	
        	// estadístiques d'assignació a través d'algun qüestionari mitjes d'assignatura
        	var asiQstAssRebudes = $(xml).find("assignacioQuestionari > assignatura > rebudes").text();
        	var asiQstAssContestades = $(xml).find("assignacioQuestionari > assignatura > contestades").text();
        	var asiQstAssCorregides = $(xml).find("assignacioQuestionari > assignatura > corregides").text();
        	var asiQstAssAprovades = $(xml).find("assignacioQuestionari > assignatura > aprovades").text();
        	var asiQstAssNotaMitja = $(xml).find("assignacioQuestionari > assignatura > notaMitja").text();
        	var asiQstAssTempsRespostaMig = $(xml).find("assignacioQuestionari > assignatura > tempsRespostaMig").text();
        	
        	// estadístiques d'assignació a través d'algun qüestionari mitjes d'egradus
        	var asiQstEgrRebudes = $(xml).find("assignacioQuestionari > egradus > rebudes").text();
        	var asiQstEgrContestades = $(xml).find("assignacioQuestionari > egradus > contestades").text();
        	var asiQstEgrCorregides = $(xml).find("assignacioQuestionari > egradus > corregides").text();
        	var asiQstEgrAprovades = $(xml).find("assignacioQuestionari > egradus > aprovades").text();
        	var asiQstEgrNotaMitja = $(xml).find("assignacioQuestionari > egradus > notaMitja").text();
        	var asiQstEgrTempsRespostaMig = $(xml).find("assignacioQuestionari > egradus > tempsRespostaMig").text();
        	
        	if (asiDirAssTempsRespostaMig.length > 0) asiDirAssTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiDirAssTempsRespostaMig/1000));
        	if (asiDirEgrTempsRespostaMig.length > 0) asiDirEgrTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiDirEgrTempsRespostaMig/1000));
        	if (asiQstAssTempsRespostaMig.length > 0) asiQstAssTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiQstAssTempsRespostaMig/1000));
        	if (asiQstEgrTempsRespostaMig.length > 0) asiQstEgrTempsRespostaMig = formatTempsBySegons(eliminaDecimals(asiQstEgrTempsRespostaMig/1000));
        	
        	var numDecimals = 2;
        	
        	$("#estProPreguntesTaulaRebudesAssignacioDirectaConjunt").html(asiDirAssRebudes);
        	$("#estProPreguntesTaulaContestadesAssignacioDirectaConjunt").html(asiDirAssContestades);
        	$("#estProPreguntesTaulaCorregidesAssignacioDirectaConjunt").html(asiDirAssCorregides);
        	$("#estProPreguntesTaulaAprovadesAssignacioDirectaConjunt").html(asiDirAssAprovades);
        	$("#estProPreguntesTaulaNotaMitjaAssignacioDirectaConjunt").html(decimalArrodonit(asiDirAssNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigAssignacioDirectaConjunt").html(asiDirAssTempsRespostaMig);
        	
        	$("#estProPreguntesTaulaRebudesEgradusAssignacioDirectaConjunt").html(asiDirEgrRebudes);
        	$("#estProPreguntesTaulaContestadesEgradusAssignacioDirectaConjunt").html(asiDirEgrContestades);
        	$("#estProPreguntesTaulaCorregidesEgradusAssignacioDirectaConjunt").html(asiDirEgrCorregides);
        	$("#estProPreguntesTaulaAprovadesEgradusAssignacioDirectaConjunt").html(asiDirEgrAprovades);
        	$("#estProPreguntesTaulaNotaMitjaEgradusAssignacioDirectaConjunt").html(decimalArrodonit(asiDirEgrNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigEgradusAssignacioDirectaConjunt").html(asiDirEgrTempsRespostaMig);
        	
        	$("#estProPreguntesTaulaRebudesAssignacioQuestionariConjunt").html(asiQstAssRebudes);
        	$("#estProPreguntesTaulaContestadesAssignacioQuestionariConjunt").html(asiQstAssContestades);
        	$("#estProPreguntesTaulaCorregidesAssignacioQuestionariConjunt").html(asiQstAssCorregides);
        	$("#estProPreguntesTaulaAprovadesAssignacioQuestionariConjunt").html(asiQstAssAprovades);
        	$("#estProPreguntesTaulaNotaMitjaAssignacioQuestionariConjunt").html(decimalArrodonit(asiQstAssNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigAssignacioQuestionariConjunt").html(asiQstAssTempsRespostaMig);
        	
        	$("#estProPreguntesTaulaRebudesEgradusAssignacioQuestionariConjunt").html(asiQstEgrRebudes);
        	$("#estProPreguntesTaulaContestadesEgradusAssignacioQuestionariConjunt").html(asiQstEgrContestades);
        	$("#estProPreguntesTaulaCorregidesEgradusAssignacioQuestionariConjunt").html(asiQstEgrCorregides);
        	$("#estProPreguntesTaulaAprovadesEgradusAssignacioQuestionariConjunt").html(asiQstEgrAprovades);
        	$("#estProPreguntesTaulaNotaMitjaEgradusAssignacioQuestionariConjunt").html(decimalArrodonit(asiQstEgrNotaMitja, numDecimals));
        	$("#estProPreguntesTaulaTempsRespostaMigEgradusAssignacioQuestionariConjunt").html(asiQstEgrTempsRespostaMig);
        },
        error:function(){
            alert("error determinant estadístiques de professors: conjunt de les preguntes");
        }
    });
}

/**
 * funció local que s'executa cada cop el professor canvia de qüestionari en la selecció
 * de les estadístiques dels qüestionaris
 */
function determinarEstProfessorQuestionari() {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// agafam el codi de qüestionari seleccionat a l'input select
	var codiQuestionari = $("select#estProQuestionarisSelectUnQuestionari option").filter(":selected").val();
	
	var params = "codiAssignatura=" + codiAssignatura + "&codiQuestionari=" + codiQuestionari;
	$.ajax({
        url: "/egradus/estadistiques/professor/questionari.xml?" + params,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	// estadístiques mitjes d'assignatura
        	var assRebuts = $(xml).find("assignatura > rebuts").text();
        	var assContestats = $(xml).find("assignatura > contestats").text();
        	var assCorregits = $(xml).find("assignatura > corregits").text();
        	var assAprovats = $(xml).find("assignatura > aprovats").text();
        	var assNotaMitja = $(xml).find("assignatura > notaMitja").text();
        	var assTempsRespostaMig = $(xml).find("assignatura > tempsRespostaMig").text();
        	
        	// estadístiques mitjes d'egradus
        	var egrRebuts = $(xml).find("egradus > rebuts").text();
        	var egrContestats = $(xml).find("egradus > contestats").text();
        	var egrCorregits = $(xml).find("egradus > corregits").text();
        	var egrAprovats = $(xml).find("egradus > aprovats").text();
        	var egrNotaMitja = $(xml).find("egradus > notaMitja").text();
        	var egrTempsRespostaMig = $(xml).find("egradus > tempsRespostaMig").text();
        	
        	if (assTempsRespostaMig.length > 0) assTempsRespostaMig = formatTempsBySegons(eliminaDecimals(assTempsRespostaMig/1000));
        	if (egrTempsRespostaMig.length > 0) egrTempsRespostaMig = formatTempsBySegons(eliminaDecimals(egrTempsRespostaMig/1000));
        	
        	var numDecimals = 2;
        	
        	$("#estProQuestionarisTaulaRebuts").html(assRebuts);
        	$("#estProQuestionarisTaulaContestats").html(assContestats);
        	$("#estProQuestionarisTaulaCorregits").html(assCorregits);
        	$("#estProQuestionarisTaulaAprovats").html(assAprovats);
        	$("#estProQuestionarisTaulaNotaMitja").html(decimalArrodonit(assNotaMitja, numDecimals));
        	$("#estProQuestionarisTaulaTempsRespostaMig").html(assTempsRespostaMig);
        	
        	$("#estProQuestionarisTaulaRebutsEgradus").html(egrRebuts);
        	$("#estProQuestionarisTaulaContestatsEgradus").html(egrContestats);
        	$("#estProQuestionarisTaulaCorregitsEgradus").html(egrCorregits);
        	$("#estProQuestionarisTaulaAprovatsEgradus").html(egrAprovats);
        	$("#estProQuestionarisTaulaNotaMitjaEgradus").html(decimalArrodonit(egrNotaMitja, numDecimals));
        	$("#estProQuestionarisTaulaTempsRespostaMigEgradus").html(egrTempsRespostaMig);
        },
        error:function(){
            alert("error determinant estadístiques de professors: un qüestionari");
        }
    });
}

/**
 * funció local que mostra les estadístiques dels professors en referència a
 * tots els qüestionaris enviats en l'àmbit de l'assignatura actual
 * 
 * @param codiAssignatura
 */
function determinarEstProfessorQuestionarisAssignatura(codiAssignatura) {
	$.ajax({
        url: "/egradus/estadistiques/professor/conjuntQuestionaris.xml?codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	// estadístiques mitjes d'assignatura
        	
        	var assRebuts = $(xml).find("assignatura > rebuts").text();
        	var assContestats = $(xml).find("assignatura > contestats").text();
        	var assCorregits = $(xml).find("assignatura > corregits").text();
        	var assAprovats = $(xml).find("assignatura > aprovats").text();
        	var assNotaMitja = $(xml).find("assignatura > notaMitja").text();
        	var assTempsRespostaMig = $(xml).find("assignatura > tempsRespostaMig").text();
        	
        	// estadístiques mitjes d'egradus
        	var egrRebuts = $(xml).find("egradus > rebuts").text();
        	var egrContestats = $(xml).find("egradus > contestats").text();
        	var egrCorregits = $(xml).find("egradus > corregits").text();
        	var egrAprovats = $(xml).find("egradus > aprovats").text();
        	var egrNotaMitja = $(xml).find("egradus > notaMitja").text();
        	var egrTempsRespostaMig = $(xml).find("egradus > tempsRespostaMig").text();
        	
        	if (assTempsRespostaMig.length > 0) assTempsRespostaMig = formatTempsBySegons(eliminaDecimals(assTempsRespostaMig/1000));
        	if (egrTempsRespostaMig.length > 0) egrTempsRespostaMig = formatTempsBySegons(eliminaDecimals(egrTempsRespostaMig/1000));
        	
        	var numDecimals = 2;
        	
        	$("#estProQuestionarisTaulaRebutsConjunt").html(assRebuts);
        	$("#estProQuestionarisTaulaContestatsConjunt").html(assContestats);
        	$("#estProQuestionarisTaulaCorregitsConjunt").html(assCorregits);
        	$("#estProQuestionarisTaulaAprovatsConjunt").html(assAprovats);
        	$("#estProQuestionarisTaulaNotaMitjaConjunt").html(decimalArrodonit(assNotaMitja, numDecimals));
        	$("#estProQuestionarisTaulaTempsRespostaMigConjunt").html(assTempsRespostaMig);
        	
        	$("#estProQuestionarisTaulaRebutsEgradusConjunt").html(egrRebuts);
        	$("#estProQuestionarisTaulaContestatsEgradusConjunt").html(egrContestats);
        	$("#estProQuestionarisTaulaCorregitsEgradusConjunt").html(egrCorregits);
        	$("#estProQuestionarisTaulaAprovatsEgradusConjunt").html(egrAprovats);
        	$("#estProQuestionarisTaulaNotaMitjaEgradusConjunt").html(decimalArrodonit(egrNotaMitja, numDecimals));
        	$("#estProQuestionarisTaulaTempsRespostaMigEgradusConjunt").html(egrTempsRespostaMig);
        	
        },
        error:function(){
            alert("error determinant estadístiques de professors: conjunt qüestionaris");
        }
    });
}

/**
 * funció local que s'executa cada cop que l'alumne (o el professor, 
 * depenent del cas que faci ús de la funció) canvia de qüestionari 
 * en la selecció de les estadístiques d'un alumne en referència a 
 * UN sol qüestionari.
 * 
 * @param id
 */
function determinarEstQuestionari(id) {
	// buidam els dos canvas per si ja s'havien dibuixat anteriorment
	emptyChart("est" + id + "QuestionarisChartIndividualNotes");
	emptyChart("est" + id + "QuestionarisChartIndividualPesos");
	
	// agafam el codi de la resposta-questionari seleccionada a l'input select
	var codiRq = $("select#est" + id + "QuestionarisSelectIndividual option").filter(":selected").val();
	$.ajax({
        url: "/egradus/estadistiques/getDetallRespostaQuestionari.xml?codiRq=" + codiRq,
        type: "get",  
        dataType: "xml",
        success: function(xml) {
        	var divTaula = 	"<thead><tr>" +
						      	"<th>" + MIS_TAULA_REPO_PRE_CODI + "</th><th>" + MIS_TAULA_REPO_PRE_ENUNCIAT + "</th><th>" + MIS_TAULA_REPO_PRE_TIPUS + "</th><th>" + EST_ALU_TAULA_PES + "</th><th>" + EST_ALU_TAULA_NOTA + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_EGR + "</th><th>" + EST_ALU_TAULA_PES_X_NOTA + "</th><th>" + EST_ALU_TAULA_PES_X_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_PES_X_NOTA_MITJA_EGR + "</th>" +
						    "</tr></thead>";
        	
        	// definició d'arrays d'etiquetes i de valors per a les gràfiques
    		var arrayCodisPregunta = [];
    		var arrayPesos = [];
    		var arrayNotes = [];
    		var arrayPesosXNotes = [];
    		var arrayNotesMitjesAss = [];
    		var arrayNotesMitjesEgr = [];
    		var arrayPesosXNotesMitgesAss = [];
    		var arrayPesosXNotesMitgesEgr = [];
    		var indexArray = 0;
        	$(xml).find("detallRespostaQuestionari").each(function(){
	        	var codi         = $(this).find("pregunta").attr("codi");
		        var enunciat     = $(this).find("pregunta").attr("enunciat");
		        var tipus 	     = $(this).find("pregunta").attr("tipus");
		        var pes 	     = $(this).find("pregunta").attr("pes");
		        var nota      	 = $(this).find("nota").attr("nota");
		        var notaMitjaAss = $(this).find("nota").attr("mitjaAssignatura");
		        var notaMitjaEgr = $(this).find("nota").attr("mitjaEgradus");
		        
		        // si l'enunciat és massa llarg com per sortir a la taula, s'acurçarà:
		        if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
	        	
		        // tipus de la pregunta
		        var tip = "Text per defecte";
		        switch(tipus) {
			    	case TIPUS_PREGUNTA_ES1:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_ES1;
			    		break;
			    	case TIPUS_PREGUNTA_ESN:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_ESN;
			            break;
			    	case TIPUS_PREGUNTA_VOF:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_VOF;
			    		break;
			    	case TIPUS_PREGUNTA_REC:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_REC;
			            break;
			    }
		        
		        // Arrodonim els valors
		        var numDecimals 			  = 2;
		        var pesArrodonit              = decimalArrodonit(pes, numDecimals);
		        var pesArrodonitX10           = decimalArrodonit(pes * 10, numDecimals);
		        var notaArrodonida            = decimalArrodonit(nota, numDecimals);
		        var pesXNotaArrodonit      	  = decimalArrodonit(pes * nota, numDecimals);
		        var notaMitjaAssArrodonida    = decimalArrodonit(notaMitjaAss, numDecimals);
		        var notaMitjaEgrArrodonida    = decimalArrodonit(notaMitjaEgr, numDecimals);
		        var pesXNotaMitjaAssArrodonit = decimalArrodonit(pes * notaMitjaAss, numDecimals);
		        var pesXNotaMitjaEgrArrodonit = decimalArrodonit(pes * notaMitjaEgr, numDecimals);
		        
		        // Assignam els valors als arrays
		        arrayCodisPregunta[indexArray] 		  = codi;
		        arrayPesos[indexArray] 				  = pesArrodonitX10;
		        arrayNotes[indexArray] 				  = notaArrodonida;
		        arrayPesosXNotes[indexArray] 		  = pesXNotaArrodonit;
		        arrayNotesMitjesAss[indexArray] 	  = notaMitjaAssArrodonida;
		        arrayNotesMitjesEgr[indexArray] 	  = notaMitjaEgrArrodonida;
		        arrayPesosXNotesMitgesAss[indexArray] = pesXNotaMitjaAssArrodonit;
		        arrayPesosXNotesMitgesEgr[indexArray] = pesXNotaMitjaEgrArrodonit;
		        indexArray++;
		        
		        // definim les files de la taula
		        divTaula += "<tr><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>" + codi + "</a></td><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>" + enunciat + "</a></td><td>" + tip + "</td><td>" + pesArrodonit + "</td><td>" + notaArrodonida + "</td><td>" + notaMitjaAssArrodonida + "</td><td>" + notaMitjaEgrArrodonida + "</td><td>" + pesXNotaArrodonit + "</td><td>" + pesXNotaMitjaAssArrodonit + "</td><td>" + pesXNotaMitjaEgrArrodonit + "</td>";
        	});
        	
        	$("#est" + id + "QuestionarisTaulaIndividual").html(divTaula);
        	
        	// pintam gràfiques d'estadístiques d'UN qüestionari
        	graficaPolinomica3var("est" + id + "QuestionarisChartIndividualNotes", 
        			true,
        			arrayCodisPregunta, 
        			arrayNotes, 		    	EST_ALU_LEGEND_GRAFICA_NOTES, 
        			arrayNotesMitjesAss, 		EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_ASS,
        			arrayNotesMitjesEgr, 		EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_EGR);
        	graficaPolinomica4var("est" + id + "QuestionarisChartIndividualPesos", 
        			true,
        			arrayCodisPregunta, 
        			arrayPesosXNotes,       	EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES,
        			arrayPesosXNotesMitgesAss, 	EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES_MITGES_ASS,
        			arrayPesosXNotesMitgesEgr, 	EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES_MITGES_EGR,
        			arrayPesos, 		    	EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS);
        },
        error:function(){
            alert("error habilita estadístiques d'alumnes: preguntes");
        }
    });
}

/**
 * funció local que s'executa cada cop que el professor canvia 
 * de qüestionari en la selecció de les estadístiques del conjunt 
 * d'alumnes de la seva assignatura en referència a UN sol 
 * qüestionari.
 * 
 * @param id
 */
function determinarEstQuestionariConjuntAlumnes(id) {
	// buidam els dos canvas per si ja s'havien dibuixat anteriorment
	emptyChart("est" + id + "QuestionarisChartIndividualNotes");
	emptyChart("est" + id + "QuestionarisChartIndividualPesos");
	
	// agafam el codi del qüestionari seleccionat a l'input select
	// NOTA: NO és un codi de resposta-questionari, sino d'un qüestionari!
	var codiQuestionari = $("select#est" + id + "QuestionarisSelectIndividual option").filter(":selected").val();
	$.ajax({
        url: "/egradus/estadistiques/professor/getEstadistiquesQuestionari.xml?codiQuestionari=" + codiQuestionari,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var divTaula = 	"<thead><tr>" +
						      	"<th>" + MIS_TAULA_REPO_PRE_CODI + "</th><th>" + MIS_TAULA_REPO_PRE_ENUNCIAT + "</th><th>" + MIS_TAULA_REPO_PRE_TIPUS + "</th><th>" + EST_ALU_TAULA_PES + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_NOTA_MITJA_EGR + "</th><th>" + EST_ALU_TAULA_PES_X_NOTA_MITJA_ASS + "</th><th>" + EST_ALU_TAULA_PES_X_NOTA_MITJA_EGR + "</th>" +
						    "</tr></thead>";
        	
        	// definició d'arrays d'etiquetes i de valors per a les gràfiques
    		var arrayCodisPregunta = [];
    		var arrayPesos = [];
    		var arrayNotesMitjesAss = [];
    		var arrayNotesMitjesEgr = [];
    		var arrayPesosXNotesMitgesAss = [];
    		var arrayPesosXNotesMitgesEgr = [];
    		var indexArray = 0;
        	$(xml).find("detallQuestionari").each(function(){
//        		<detallQuestionari>
//	    			<pregunta 	codi="${est.preguntaCodi}" 
//	    						enunciat="${est.preguntaEnunciat}" 
//	    						tipus="${est.preguntaTipus}"
//	    						pes="${est.preguntaPes}"/>
//	    			<nota 	mitjaAssignatura="${est.notaMitjaAssignatura}" 
//	    					mitjaEgradus="${est.notaMitjaEgradus}"/>
//	    		</detallQuestionari>
        		var codi         = $(this).find("pregunta").attr("codi");
		        var enunciat     = $(this).find("pregunta").attr("enunciat");
		        var tipus 	     = $(this).find("pregunta").attr("tipus");
		        var pes 	     = $(this).find("pregunta").attr("pes");
		        var notaMitjaAss = $(this).find("nota").attr("mitjaAssignatura");
		        var notaMitjaEgr = $(this).find("nota").attr("mitjaEgradus");
		        
		        // si l'enunciat és massa llarg com per sortir a la taula, s'acurçarà:
		        if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
	        	
		        // tipus de la pregunta
		        var tip = "Text per defecte";
		        switch(tipus) {
			    	case TIPUS_PREGUNTA_ES1:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_ES1;
			    		break;
			    	case TIPUS_PREGUNTA_ESN:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_ESN;
			            break;
			    	case TIPUS_PREGUNTA_VOF:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_VOF;
			    		break;
			    	case TIPUS_PREGUNTA_REC:
			    		tip = MIS_TAULA_REPO_PRE_TIPUS_REC;
			            break;
			    }
		        
		        // Arrodonim els valors
		        var numDecimals 			  = 2;
		        var pesArrodonit              = decimalArrodonit(pes, numDecimals);
		        var pesArrodonitX10           = decimalArrodonit(pes * 10, numDecimals);
		        var notaMitjaAssArrodonida    = decimalArrodonit(notaMitjaAss, numDecimals);
		        var notaMitjaEgrArrodonida    = decimalArrodonit(notaMitjaEgr, numDecimals);
		        var pesXNotaMitjaAssArrodonit = decimalArrodonit(pes * notaMitjaAss, numDecimals);
		        var pesXNotaMitjaEgrArrodonit = decimalArrodonit(pes * notaMitjaEgr, numDecimals);
		        
		        // Assignam els valors als arrays
		        arrayCodisPregunta[indexArray] 		  = codi;
		        arrayPesos[indexArray] 				  = pesArrodonitX10;
		        arrayNotesMitjesAss[indexArray] 	  = notaMitjaAssArrodonida;
		        arrayNotesMitjesEgr[indexArray] 	  = notaMitjaEgrArrodonida;
		        arrayPesosXNotesMitgesAss[indexArray] = pesXNotaMitjaAssArrodonit;
		        arrayPesosXNotesMitgesEgr[indexArray] = pesXNotaMitjaEgrArrodonit;
		        indexArray++;
		        
		        // definim les files de la taula
		        divTaula += "<tr><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>" + codi + "</a></td><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 0);'>" + enunciat + "</a></td><td>" + tip + "</td><td>" + pesArrodonit + "</td><td>" + notaMitjaAssArrodonida + "</td><td>" + notaMitjaEgrArrodonida + "</td><td>" + pesXNotaMitjaAssArrodonit + "</td><td>" + pesXNotaMitjaEgrArrodonit + "</td>";
        	});
        	$("#est" + id + "QuestionarisTaulaIndividual").html(divTaula);
        	
        	// pintam gràfiques d'estadístiques d'UN qüestionari
        	graficaPolinomica2var("est" + id + "QuestionarisChartIndividualNotes", 
        			true,
        			arrayCodisPregunta, 
        			arrayNotesMitjesAss, 		EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_ASS,
        			arrayNotesMitjesEgr, 		EST_ALU_LEGEND_GRAFICA_NOTES_MITJES_EGR);
        	graficaPolinomica3var("est" + id + "QuestionarisChartIndividualPesos", 
        			true,
        			arrayCodisPregunta, 
        			arrayPesosXNotesMitgesAss, 	EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES_MITGES_ASS,
        			arrayPesosXNotesMitgesEgr, 	EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS_X_NOTES_MITGES_EGR,
        			arrayPesos, 		    	EST_ALU_QST_LEGEND_GRAFICA_IND_PESOS);
        },
        error:function(){
            alert("error habilita estadístiques del professor sobre el conjunt d'alumnes: un qüestionari");
        }
    });
}

/**
 * Determina quin tipus d'estadístiques ha seleccionat el professor
 * 	· D'un alumne
 *  · Del conjunt d'alumnes de la seva assignatura
 * I pinta unes estadístiques o unes altres en funció del cas.
 */
function determinarEstAlumne() {
	var codiAlumne = "";
	var selectedVal = "";
	var selected = $("input[type='radio'][name='estproalumnesradio']:checked");
	if (selected.length > 0) selectedVal = selected.val();
	
	// ESTADÍSTIQUES SOBRE UN ALUMNE
	// -------------------------------------------------------------------------------------
	if (selectedVal == "unAlumne") {
		codiAlumne = $("#estProAlumnesSelectAlumne").find(":selected").val();
		
		// definim un id base (ja que la problemàtica d'acontinuació segueix el mateix patró
		// que les estadístiques que veu l'alumne sobre sí mateix (amb un altre id base)
		var idBase = "ProAlumnes";
		
		// esborram les gràfiques que puguin haver-se pintat en una consulta
		// d'estadístiques anterior i el div de la taula de valors
		$("#est" + idBase + "PreguntesGraficaNotes").empty();
		$("#est" + idBase + "PreguntesGraficaTempsResposta").empty();
		$("#est" + idBase + "PreguntesDivTaula").empty();
		$("#est" + idBase + "QuestionarisGraficaNotes").empty();
		$("#est" + idBase + "QuestionarisGraficaTempsResposta").empty();
		$("#est" + idBase + "QuestionarisDivTaula").empty();
		$("#est" + idBase + "QuestionarisDivUnQuestionari").empty();
		
		// esborram el div que es mostra quan no existeixen estadístiques per l'alumne actual
		$("#est" + idBase + "PreguntesNoEstadistiques").empty();
		$("#est" + idBase + "QuestionarisNoEstadistiques").empty();
		
		if (codiAlumne != null) {
			// preguntes
			$.ajax({
		        url: "/egradus/estadistiques/professor/getPreguntesAlumne.xml?codiAlumne=" + codiAlumne,
		        type: "get",
		        dataType: "xml",
		        success: function(xml) {
		        	// pintam les estadístiques de les preguntes
		        	estadistiquesPreguntesAlumne(xml, idBase);
		        },
		        error:function(){
		            alert("error habilita estadístiques de professors sobre alumnes: preguntes");
		        }
		    });
			
			// qüestionaris
			$.ajax({
		        url: "/egradus/estadistiques/professor/getQuestionarisAlumne.xml?codiAlumne=" + codiAlumne,
		        type: "get",
		        dataType: "xml",
		        success: function(xml) {
		        	// pintam les estadístiques de les preguntes
		        	estadistiquesQuestionarisAlumne(xml, idBase);
		        },
		        error:function(){
		            alert("error habilita estadístiques de professors sobre alumnes: qüestionaris");
		        }
		    });
		}
		
	// ESTADÍSTIQUES SOBRE EL CONJUNT D'ALUMNES DE L'ASSIGNATURA
	// -------------------------------------------------------------------------------------
	} else if (selectedVal == "conjuntAlumnes") {
		
		// definim un id base (ja que la problemàtica d'acontinuació segueix el mateix patró
		// que les estadístiques que veu l'alumne sobre sí mateix (amb un altre id base)
		var idBase = "ProAlumnes";
		
		// esborram les gràfiques que puguin haver-se pintat en una consulta
		// d'estadístiques anterior i el div de la taula de valors
		$("#est" + idBase + "PreguntesGraficaNotes").empty();
		$("#est" + idBase + "PreguntesGraficaTempsResposta").empty();
		$("#est" + idBase + "PreguntesDivTaula").empty();
		$("#est" + idBase + "QuestionarisGraficaNotes").empty();
		$("#est" + idBase + "QuestionarisGraficaTempsResposta").empty();
		$("#est" + idBase + "QuestionarisDivTaula").empty();
		$("#est" + idBase + "QuestionarisDivUnQuestionari").empty();
		
		// esborram el div que es mostra quan no existeixen estadístiques per l'alumne actual
		$("#est" + idBase + "PreguntesNoEstadistiques").empty();
		$("#est" + idBase + "QuestionarisNoEstadistiques").empty();
		
		// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
		var codiAssignatura = $("#materialCodiAssignatura").val();
		
		// preguntes
		$.ajax({
	        url: "/egradus/estadistiques/professor/getPreguntesConjuntAlumnes.xml?codiAssignatura=" + codiAssignatura,
	        type: "get",
	        dataType: "xml",
	        success: function(xml) {
	        	// pintam les estadístiques de les preguntes
	        	estadistiquesPreguntesConjuntAlumnes(xml, idBase);
	        },
	        error:function(){
	            alert("error habilita estadístiques de professors sobre el conjunt d'alumnes: preguntes");
	        }
	    });
		
		// qüestionaris
		$.ajax({
	        url: "/egradus/estadistiques/professor/getQuestionarisConjuntAlumnes.xml?codiAssignatura=" + codiAssignatura,
	        type: "get",
	        dataType: "xml",
	        success: function(xml) {
	        	// pintam les estadístiques de les preguntes
	        	estadistiquesQuestionarisConjuntAlumnes(xml, idBase);
	        },
	        error:function(){
	            alert("error habilita estadístiques de professors sobre el conjunt d'alumnes: qüestionaris");
	        }
	    });
	}
}

/**
 * Adapta el radiobutton d'estadístiques que veuen els professors 
 * sobre els alumnes per a que el professor vegi deshabilitades les
 * opcions per escollir alumne.
 */
function adaptaRadioButtonConjuntAlumnes() {
	$("#estProAlumnesSelectAlumne").prop('disabled', true);
}

/**
 * Adapta el radiobutton d'estadístiques que veuen els professors 
 * sobre els alumnes per a que el professor vegi habilitades les
 * opcions per escollir alumne.
 */
function adaptaRadioButtonUnAlumne() {
	$("#estProAlumnesSelectAlumne").prop('disabled', false);
}

/**
 * habilita la vista d'estadístiques del fòrum (només pels alumnes)
 */
function habilitaEstadistiquesForum() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEstadistiquesForum").show();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya d'estadístiques de preguntes dels alumnes, per saber que ha
	// estat seleccionada per l'alumne
	$("#assignaturaMaterialEstadistiquesAlu").addClass("egradus-color-pestanya");
}

/**
 * pinta una gràfica polinòmica d'una variable
 * 
 * @param idCanvas
 * 			id del canvas on es pintarà la gràfica
 * @param notesChart
 * 			'true' si la gràfica representarà Notes de 0 a 10
 * 			'false' en cas contrari
 * @param arrayLabels
 * 			conjunt d'etiquetes (Eix X de la gràfica)
 * @param arrayDataset
 * 			conjunt de dades
 * @param nomDataset
 * 			nom del conjunt de dades
 */
function graficaPolinomica1var(idCanvas, notesChart, arrayLabels, arrayDataset, nomDataset) {
	var sets = [
	       	        {
	    	        	label: 					nomDataset,
	    	            fillColor: 				BLAU_FONS,
	    	            strokeColor: 			BLAU_PRIM,
	    	            pointColor: 			BLAU_FORT,
	    	            pointStrokeColor: 		BLANC,
	    	            pointHighlightFill: 	BLANC,
	    	            pointHighlightStroke: 	BLAU_PRIM,
	    	            data: 					arrayDataset
	    	        }
	    	   ];
	
	var data = {labels: arrayLabels, datasets: sets};
	
	// Definim els paràmetres de manera distinta a si és una gràfica
	// de notes o no
	var parametres = [];
	if (notesChart) 	parametres = {scaleOverride: true, scaleSteps: 10, scaleStepWidth: 1, scaleStartValue: 0};
	else 				parametres = {scaleBeginAtZero: true};
	
	// definim el Chart polinòmic i retornam la gràfica
	return defineixLineChart(idCanvas, data, parametres);
}

/**
 * pinta una gràfica polinòmica de dues variables
 * 
 * @param idCanvas
 * 			id del canvas on es pintarà la gràfica
 * @param notesChart
 * 			'true' si la gràfica representarà Notes de 0 a 10
 * 			'false' en cas contrari
 * @param arrayLabels
 * 			conjunt d'etiquetes (Eix X de la gràfica)
 * @param arrayDataset1
 * 			conjunt de dades 1
 * @param nomDataset1
 * 			nom del conjunt de dades 1
 * @param arrayDataset2
 * 			conjunt de dades 2
 * @param nomDataset2
 * 			nom del conjunt de dades 2
 */
function graficaPolinomica2var(idCanvas, notesChart, arrayLabels, arrayDataset1, nomDataset1, arrayDataset2, nomDataset2) {
	var sets = [
			        {
			        	label: 					nomDataset1,
			            fillColor: 				BLAU_FONS,
			            strokeColor: 			BLAU_PRIM,
			            pointColor: 			BLAU_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	BLAU_PRIM,
			            data: 					arrayDataset1
			        },
			        {
			            label: 					nomDataset2,
			            fillColor: 				VERMELL_FONS,
			            strokeColor: 			VERMELL_PRIM,
			            pointColor: 			VERMELL_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke:	VERMELL_PRIM,
			            data: 					arrayDataset2
			        }
		       ];
	
	var data = {labels: arrayLabels, datasets: sets};
	
	// Definim els paràmetres de manera distinta a si és una gràfica
	// de notes o no
	var parametres = [];
	if (notesChart) 	parametres = {scaleOverride: true, scaleSteps: 10, scaleStepWidth: 1, scaleStartValue: 0};
	else 				parametres = {scaleBeginAtZero: true};
	
	// definim el Chart polinòmic i retornam la gràfica
	return defineixLineChart(idCanvas, data, parametres);
}

/**
 * pinta una gràfica polinòmica de tres variables
 * 
 * @param idCanvas
 * 			id del canvas on es pintarà la gràfica
 * @param notesChart
 * 			'true' si la gràfica representarà Notes de 0 a 10
 * 			'false' en cas contrari
 * @param arrayLabels
 * 			conjunt d'etiquetes (Eix X de la gràfica)
 * @param arrayDataset1
 * 			conjunt de dades 1
 * @param nomDataset1
 * 			nom del conjunt de dades 1
 * @param arrayDataset2
 * 			conjunt de dades 2
 * @param nomDataset2
 * 			nom del conjunt de dades 2
 * @param arrayDataset3
 * 			conjunt de dades 3
 * @param nomDataset3
 * 			nom del conjunt de dades 3
 */
function graficaPolinomica3var(idCanvas, notesChart, arrayLabels, arrayDataset1, nomDataset1, arrayDataset2, nomDataset2, arrayDataset3, nomDataset3) {
	var sets = [
			        {
			        	label: 					nomDataset1,
			            fillColor: 				BLAU_FONS,
			            strokeColor: 			BLAU_PRIM,
			            pointColor: 			BLAU_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	BLAU_PRIM,
			            data: 					arrayDataset1
			        },
			        {
			            label: 					nomDataset2,
			            fillColor: 				VERMELL_FONS,
			            strokeColor: 			VERMELL_PRIM,
			            pointColor: 			VERMELL_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	VERMELL_PRIM,
			            data: 					arrayDataset2
			        },
			        {
			            label: 					nomDataset3,
			            fillColor: 				VERD_FONS,
			            strokeColor: 			VERD_PRIM,
			            pointColor: 			VERD_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	VERD_PRIM,
			            data: 					arrayDataset3
			        }
		       ];
	
	var data = {labels: arrayLabels, datasets: sets};
	
	// Definim els paràmetres de manera distinta a si és una gràfica
	// de notes o no
	var parametres = [];
	if (notesChart) 	parametres = {scaleOverride: true, scaleSteps: 10, scaleStepWidth: 1, scaleStartValue: 0};
	else 				parametres = {scaleBeginAtZero: true};
	
	// definim el Chart polinòmic i retornam la gràfica
	return defineixLineChart(idCanvas, data, parametres);
}

/**
 * pinta una gràfica polinòmica de tres variables
 * 
 * @param idCanvas
 * 			id del canvas on es pintarà la gràfica
 * @param notesChart
 * 			'true' si la gràfica representarà Notes de 0 a 10
 * 			'false' en cas contrari
 * @param arrayLabels
 * 			conjunt d'etiquetes (Eix X de la gràfica)
 * @param arrayDataset1
 * 			conjunt de dades 1
 * @param nomDataset1
 * 			nom del conjunt de dades 1
 * @param arrayDataset2
 * 			conjunt de dades 2
 * @param nomDataset2
 * 			nom del conjunt de dades 2
 * @param arrayDataset3
 * 			conjunt de dades 3
 * @param nomDataset3
 * 			nom del conjunt de dades 3
 * @param arrayDataset4
 * 			conjunt de dades 4
 * @param nomDataset4
 * 			nom del conjunt de dades 4
 */
function graficaPolinomica4var(idCanvas, notesChart, arrayLabels, arrayDataset1, nomDataset1, arrayDataset2, nomDataset2, arrayDataset3, nomDataset3, arrayDataset4, nomDataset4) {
	var sets = [
			        {
			        	label: 					nomDataset1,
			            fillColor: 				BLAU_FONS,
			            strokeColor: 			BLAU_PRIM,
			            pointColor: 			BLAU_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	BLAU_PRIM,
			            data: 					arrayDataset1
			        },
			        {
			            label: 					nomDataset2,
			            fillColor: 				VERMELL_FONS,
			            strokeColor: 			VERMELL_PRIM,
			            pointColor: 			VERMELL_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	VERMELL_PRIM,
			            data: 					arrayDataset2
			        },
			        {
			            label: 					nomDataset3,
			            fillColor: 				VERD_FONS,
			            strokeColor: 			VERD_PRIM,
			            pointColor: 			VERD_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	VERD_PRIM,
			            data: 					arrayDataset3
			        },
			        {
			            label: 					nomDataset4,
			            fillColor: 				GROC_FONS,
			            strokeColor: 			GROC_PRIM,
			            pointColor: 			GROC_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	GROC_PRIM,
			            data: 					arrayDataset4
			        }
		       ];
	
	var data = {labels: arrayLabels, datasets: sets};
	
	// Definim els paràmetres de manera distinta a si és una gràfica
	// de notes o no
	var parametres = [];
	if (notesChart) 	parametres = {scaleOverride: true, scaleSteps: 10, scaleStepWidth: 1, scaleStartValue: 0};
	else 				parametres = {scaleBeginAtZero: true};
	
	// definim el Chart polinòmic i retornam la gràfica
	return defineixLineChart(idCanvas, data, parametres);
}

/**
 * pinta una gràfica polinòmica de tres variables
 * 
 * @param idCanvas
 * 			id del canvas on es pintarà la gràfica
 * @param notesChart
 * 			'true' si la gràfica representarà Notes de 0 a 10
 * 			'false' en cas contrari
 * @param arrayLabels
 * 			conjunt d'etiquetes (Eix X de la gràfica)
 * @param arrayDataset1
 * 			conjunt de dades 1
 * @param nomDataset1
 * 			nom del conjunt de dades 1
 * @param arrayDataset2
 * 			conjunt de dades 2
 * @param nomDataset2
 * 			nom del conjunt de dades 2
 * @param arrayDataset3
 * 			conjunt de dades 3
 * @param nomDataset3
 * 			nom del conjunt de dades 3
 * @param arrayDataset4
 * 			conjunt de dades 4
 * @param nomDataset4
 * 			nom del conjunt de dades 4
 * @param arrayDataset5
 * 			conjunt de dades 5
 * @param nomDataset5
 * 			nom del conjunt de dades 5
 */
function graficaPolinomica5var(idCanvas, notesChart, arrayLabels, arrayDataset1, nomDataset1, arrayDataset2, nomDataset2, arrayDataset3, nomDataset3, arrayDataset4, nomDataset4, arrayDataset5, nomDataset5) {
	var sets = [
			        {
			        	label: 					nomDataset1,
			            fillColor: 				BLAU_FONS,
			            strokeColor: 			BLAU_PRIM,
			            pointColor: 			BLAU_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	BLAU_PRIM,
			            data: 					arrayDataset1
			        },
			        {
			            label: 					nomDataset2,
			            fillColor: 				VERMELL_FONS,
			            strokeColor: 			VERMELL_PRIM,
			            pointColor: 			VERMELL_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	VERMELL_PRIM,
			            data: 					arrayDataset2
			        },
			        {
			            label: 					nomDataset3,
			            fillColor: 				VERD_FONS,
			            strokeColor: 			VERD_PRIM,
			            pointColor: 			VERD_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	VERD_PRIM,
			            data: 					arrayDataset3
			        },
			        {
			            label: 					nomDataset4,
			            fillColor: 				GROC_FONS,
			            strokeColor: 			GROC_PRIM,
			            pointColor: 			GROC_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	GROC_PRIM,
			            data: 					arrayDataset4
			        },
			        {
			            label: 					nomDataset5,
			            fillColor: 				GRIS_FONS,
			            strokeColor: 			GRIS_PRIM,
			            pointColor: 			GRIS_FORT,
			            pointStrokeColor: 		BLANC,
			            pointHighlightFill: 	BLANC,
			            pointHighlightStroke: 	GRIS_PRIM,
			            data: 					arrayDataset5
			        }
		       ];
	
	var data = {labels: arrayLabels, datasets: sets};
	
	// Definim els paràmetres de manera distinta a si és una gràfica
	// de notes o no
	var parametres = [];
	if (notesChart) 	parametres = {scaleOverride: true, scaleSteps: 10, scaleStepWidth: 1, scaleStartValue: 0};
	else 				parametres = {scaleBeginAtZero: true};
	
	// definim el Chart polinòmic i retornam la gràfica
	return defineixLineChart(idCanvas, data, parametres);
}

/**
 * Defineix el Chart amb les dades i els paràmetres indicats,
 * genera la llegenda de les variables implicades a la gràfica
 * i retorna la variable de la gràfica
 * 
 * @param idCanvas
 * 			id del canvas on es pintarà la gràfica
 * @param data
 * 			conjunt de dades a pintar
 * @param parametres
 * 			paràmetres per al Chart
 * @returns 
 * 			variable de la gràfica
 */
function defineixLineChart(idCanvas, data, parametres) {
	// patró que segueix la llegenda de variables de la gràfica
	var legendPattern = "<div class=\"col-xs-10 col-sm-8 col-lg-6\">" +
							"<div class=\"panel panel-default\">" +
								"<div class=\"panel-body\">" +
									"<% for (var i=0; i<datasets.length; i++) { %>" +
										"<span style=\"padding-left: 8px; padding-right: 8px; margin-right: 10px; background-color:<%=datasets[i].strokeColor%>\"></span>" +
										"<% if (datasets[i].label) { %><%= datasets[i].label %><% } %>" +
										"<br />" +
									"<% } %>" +
								"</div>" + 
							"</div>" +
						"</div>";
	
	// afegim els paràmetres llegenda i responsive
	parametres["legendTemplate"] = legendPattern;
	parametres["responsive"] = true;
	
	var ctx = $("#" + idCanvas)[0].getContext("2d");
	var grafica = new Chart(ctx).Line(data, parametres);
	
	// pinta la llegenda de la gràfica
	$("#" + idCanvas + "Legend").html(grafica.generateLegend());
	return grafica;
}

/**
 * retorna la plantilla que definirà una gràfica. Tendrà el següent esquelet:
 * 
 * <ul>
 * <li>div contenidor amb id = 'idChart + "Div"' i class = 'classDiv' (segons si
 * l'atribut que reb la funció és null o no)
 * 		<ul>
 * 			<li>div legend amb id = 'idChart + "Legend"'</li>
 * 			<li>canvas amb id = 'idChart'</li> 
 * 		</ul>
 * </li>
 * </ul>
 * 
 * @param idChart
 * @param classDiv
 * 			class del div contenidor (si és null no s'afegirà class)
 * @returns {String}
 */
function chartTemplate(idChart, classDiv) {
	var divClass = "";
	if (classDiv != null) divClass = " class=\"" + classDiv + "\"";
	return 	"<div id=\"" + idChart + "Div\"" + divClass + ">" +
				contingutChartTemplate(idChart) +
			"</div>";
}

/**
 * Donat que no existeix una funció clara per buidar un Chart 
 * suportat a Chart.js, recarregam l'element <canvas> amb id 
 * 'idChart' al div contenidor amb id 'idChart + "Div"'
 * 
 * A més, generam el div amb id 'idChart + "Legend"' on 
 * escriurem la llegenda d'aquell Chart
 * 
 * @param idChart
 */
function emptyChart(idChart) {
	$("#" + idChart + "Div").html(contingutChartTemplate(idChart));
}

/**
 * label, llegenda i canvas que es crea i es destrueix cada cop que volem
 * pintar una gràfica en el seu div contenidor
 * 
 * @param idChart
 * @returns
 */
function contingutChartTemplate(idChart) {
	return 	"<label for=\"" + idChart + "Legend\" class=\"legend-padding\">" + EST_GRAFICA_LLEGENDA + "</label>" + 
			"<div id=\"" + idChart + "Legend\"></div>" +
			"<canvas id=\"" + idChart + "\"></canvas>";
}
