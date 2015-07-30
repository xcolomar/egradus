/*
 * egradus-repositori.js
 * ----------------------------------------------------------------------------------------
 * 
 * Funcionalitats del repositori, com ara:
 * 1) Consultar el repositori de preguntes
 * 2) Consultar el repositori de qüestionaris
 * 3) Crear una nova pregunta o un nou qüestionari
 * 4) Consultar una pregunta o un qüestionari existents
 * 5) Assignar una pregunta o un qüestionari existents a un o diversos alumnes de 
 *    l'assignatura
 * 6) Modificar, publicar o eliminar una pregunta
 * 7) Modificar, publicar o eliminar un qüestionari
 */

function habilitaRepositoriPre() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialRepositoriPreguntes").show();
	
	// neteja el possible error que hi hagi hagut anteriorment
	// en la cerca d'una pregunta
	$("#cercaPreguntaError").empty();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya de repositori, per saber que ha estat seleccionada pel professor
	$("#assignaturaMaterialRepositori").addClass("egradus-color-pestanya");
	
	cercaPreguntes();
}

function habilitaRepositoriQst() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialRepositoriQuestionaris").show();
	
	// neteja el possible error que hi hagi hagut anteriorment
	// en la cerca d'un qüestionari
	$("#cercaQuestionariError").empty();
	
	// despintam les pestanyes del material (pestanyes forum, repositori, correccio, estadístiques, etc)
	$(".assignaturaMaterial").removeClass("egradus-color-pestanya");
	
	// pintam la pestanya de repositori, per saber que ha estat seleccionada pel professor
	$("#assignaturaMaterialRepositori").addClass("egradus-color-pestanya");
	
	cercaQuestionaris();
}

function habilitaCreacioPregunta() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialCreacioPregunta").show();
	
	// neteja els camps del formulari de creació de pregunta que es puguin haver 
	// quedat d'una creació anterior (inclòs algún possible error)
	$("#creaPreguntaError").empty();
	$("#creaPreguntaCheckBoxRaonarResposta").prop('checked', false);
	$("#creaPreguntaEnunciat").val("");
	$("#creaPreguntaDificultatTeorica").val("");
	
	// neteja, també, els camps del formulari de OPCIONS en la creació de la pregunta
	// que puguin haver quedat d'una creació anterior
	$(".creaPreguntaOpcio").val("");
	$(".creaPreguntaOpcioCheckBox").prop('checked', false);
	
	// El formulari de creació de preguntes varia en funció del tipus de pregunta indicat.
	// Aquesta funció deixa el form preparat inicialment en estat "ES1" (i el <select> marcat a "ES1")
	$("#creaPreguntaOpcioES1").attr('selected', 'selected');
	preparaElementsFormTipusPregunta(TIPUS_PREGUNTA_ES1);
}

function habilitaCreacioQuestionari() {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialCreacioQuestionari").show();
	
	// neteja els camps del formulari de creació de qüestionari que es puguin haver 
	// quedat d'una creació anterior (inclòs algún possible error)
	$("#creaQuestionariError").empty();
	$("#creaQuestionariNom").val("");
	$("#creaQuestionariDescripcio").val("");
	$("#creaQuestionariDificultatTeorica").val("");
//	$("#creaQuestionariPreguntesAfegides").empty();
	$(".preguntaAfegida").remove();
	
}

/**
 * habilita la descripció d'una pregunta
 * 
 * @param codiPregunta
 * 			codi de la pregunta
 * @param botons
 * 			paràmetre que podrà prendre per valors:
 * 				1: botons Edita/Publica/Elimina o Envia visibles
 * 				0: sense botons
 */
function habilitaDescripcioPregunta(codiPregunta, botons) {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	$.ajax({
        url: "/egradus/repositori/descripcioPregunta.xml?codiPregunta=" + codiPregunta + "&codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	ompleDescripcioPregunta(codiPregunta, botons, xml);
        },
        error:function(){
            alert(ERROR_DESCRIPCIO_PREGUNTA);
        }
    });
}

function habilitaEnviamentPregunta(codiPregunta) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEnviamentPregunta").show();
	
	$("#enviaPreguntaLlistatAlumnes").empty();
	$("#enviaPreguntaAnonima").prop('checked', false);
	
	// elimina el missatge d'error que pugui haver sortit amb anterioritat
	$("#enviaPreguntaError").empty();
	
	// obtenem la pregunta
	$.ajax({
        url: "/egradus/repositori/pregunta.xml?codiPregunta=" + codiPregunta,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var divPregunta = modalWindowPintaDescripcioPregunta($(xml).find("pregunta"), null, "enviaPregunta");
        	$("#enviaPreguntaDescripcioPregunta").html(divPregunta);
        },
        error:function(){
            alert("error habilitaEnviamentPregunta: en el get pregunta");
        }
    });
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	$.ajax({
        url: "/egradus/assignatura/alumnes.xml?codi=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	$(xml).find("alumne").each(function(){
        		var codi = $(this).attr("codi");
                var nom = $(this).attr("nom");
                var lli1 = $(this).attr("llinatge1");
                var lli2 = $(this).attr("llinatge2");
                var ali  = $(this).attr("alies");
                var alumne = ali + " (" + nom + " " + lli1 + " " + lli2 + ")";
                
                // L'alumne ha d'aparèixer com un checkbox
                var alumneCheckbox = "<div class=\"row padding-chbx-rdb-10\">" +
						    			  "<div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12\">" +
						    			  	  "<div class=\"row hover-chbx\">" +
						    			  	      "<div class=\"col-xs-9 col-sm-8 col-md-6 col-lg-4\">" +
						    			              alumne +
						    			          "</div>" +
						    			          "<div class=\"col-xs-3 col-sm-4 col-md-6 col-lg-8\">" +
						    			              " <input id=\"enviaPreguntaAlumne" + codi + "\" type=\"checkbox\" name=\"alumnes\" value=\"" + codi + "\">" +
						    			          "</div>" +
						    			      "</div>" +
						    			  "</div>" +
						    		  "</div>";
                
                $("#enviaPreguntaLlistatAlumnes").append(alumneCheckbox);
            });
        	
        	var botoEnviament = "<a class=\"btn btn-default pull-right\" href=\"javascript: enviaPregunta(" + codiPregunta + ");\">" + ENVIA_PREGUNTA_BOTO + "</a>";
        	$("#enviaPreguntaBotoEnviament").html(botoEnviament);
        },
        error:function(){
            alert("error habilita enviament pregunta");
        }
    });
}

function enviaPregunta(codi) {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	var values = $("#enviaPreguntaForm").serialize();
	$.ajax({
        url: "/egradus/repositori/enviaPregunta.do",
        type: "post",
        // afegim el codi de la pregunta i el de l'assignatura
        data: values + "&codiPregunta=" + codi + "&codiAssignatura=" + codiAssignatura, 
        success: function(xml) {
        	var errorPre = $(xml).find("errorPregunta").text();
        	var enunciat = $(xml).find("pregunta").attr("enunciat");
        	
        	if (errorPre.length != 0) {
        		$("#enviaPreguntaError").html(errorPre);
        	} else {
        		// mostram el modal window
            	$("#enviaPreguntaModalBotoCreacio").attr("onclick", "habilitaCreacioPregunta();");
            	$("#enviaPreguntaModalBotoRepositori").attr("onclick", "habilitaRepositoriPre();");
            	$("#enviaPreguntaModalMissatge").html(ENVIA_PREGUNTA_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + ENVIA_PREGUNTA_MODAL_MIS2);
            	$("#enviaPreguntaModal").modal("show");
            	
            	// elimina el missatge d'error que pugui haver sortit amb anterioritat
            	$("#enviaPreguntaError").empty();
        	}
        },
        error:function(){
        	alert("error envia pregunta");
        }
    });
}

function habilitaOpcioESN() {
	// obtenim l'id de la nova opció
	var idNovaOpcio = $(".opcioESN").length + 1;
	
	var opcio = '<div id="creaPreguntaOpcio' + idNovaOpcio + 'ESN" class="row opcioESN">' +
					'<div class="col-xs-9 col-sm-9 col-md-6 col-lg-5">' +
						'<div class="form-group">' +
							'<label for="creaPreguntaOpcio' + idNovaOpcio + 'Text">' + CREA_PREGUNTA_OPCIO_TEXT + '</label>' +
							'<input class="form-control creaPreguntaOpcio" id="creaPreguntaOpcio' + idNovaOpcio + 'Text" name="opciotextESN" type="text" placeholder="' + CREA_PREGUNTA_OPCIO_PLACEHOLDER + '"/>' +
						'</div>' +
					'</div>' +
					'<div class="col-xs-3 col-sm-3 col-md-6 col-lg-4 padding-chbx-rdb-30">' +
						'<div class="form-group">' +
							'<input class="creaPreguntaOpcioCheckBox" id="creaPreguntaCheckBoxOpcio' + idNovaOpcio + 'Correcta" type="checkbox" name="opciocorrectaESN" value="' + (idNovaOpcio - 1) + '"> ' +
							CREA_PREGUNTA_OPCIO_CORRECTA +
						'</div>' +
					'</div>' +
				'</div>';
	
	$("#creaPreguntaDivVeureOpcionsESN").append(opcio);
}

function eliminaOpcioESN() {
	var darreraOpcio = $(".opcioESN").length;
	if (darreraOpcio > 0) $("#creaPreguntaOpcio" + darreraOpcio + TIPUS_PREGUNTA_ESN).remove();
}

function habilitaOpcioES1() {
	// obtenim l'id de la nova opció
	var idNovaOpcio = $(".opcioES1").length + 1;
	
	var opcio = '<div id="creaPreguntaOpcio' + idNovaOpcio + 'ES1" class="row opcioES1">' +
					'<div class="col-xs-9 col-sm-9 col-md-6 col-lg-5">' +
						'<div class="form-group">' +
							'<label for="creaPreguntaOpcio' + idNovaOpcio + 'TextES1">' + CREA_PREGUNTA_OPCIO_TEXT + '</label>' +
							'<input class="form-control creaPreguntaOpcio" id="creaPreguntaOpcio' + idNovaOpcio + 'TextES1" name="opciotextES1" type="text" placeholder="' + CREA_PREGUNTA_OPCIO_PLACEHOLDER + '"/>' +
						'</div>' +
					'</div>' +
					'<div class="col-xs-3 col-sm-3 col-md-6 col-lg-4 padding-chbx-rdb-20">' +
						'<div class="radio">' +
							'<label>' +
								'<input type="radio" name="opciocorrectaES1" id="creaPreguntaRadioButtonOpcio' + idNovaOpcio + 'Correcta" value="' + (idNovaOpcio - 1) + '">' +
								CREA_PREGUNTA_OPCIO_CORRECTA +
							'</label>' +
						'</div>' +
					'</div>' +
				'</div>';
	
	$("#creaPreguntaDivVeureOpcionsES1").append(opcio);
}

function eliminaOpcioES1() {
	var darreraOpcio = $(".opcioES1").length;
	if (darreraOpcio > 0) $("#creaPreguntaOpcio" + darreraOpcio + TIPUS_PREGUNTA_ES1).remove();
}

function habilitaOpcioESNmodifica(codi, text, correcta) {
	// obtenim l'id de la nova opció
	var idNovaOpcio = $(".opcioESNmodifica").length + 1;
	
	var htmlText = "";
	if (text != null) htmlText = "value=\"" + text + "\"";
	
	var htmlCorrecta = ""; 
	if (correcta == "S") htmlCorrecta = "checked";
	
	var opcio = '<div id="modificaPreguntaOpcio' + idNovaOpcio + 'ESN" class="row opcioESNmodifica">' +
					'<div class="col-xs-9 col-sm-9 col-md-6 col-lg-5">' +
						'<div class="form-group">' +
							'<label for="modificaPreguntaOpcio' + idNovaOpcio + 'Text">' + CREA_PREGUNTA_OPCIO_TEXT + '</label>' +
							'<input class="form-control modificaPreguntaOpcio" id="modificaPreguntaOpcio' + idNovaOpcio + 'Text" name="opciotextESNmodifica" type="text" placeholder="' + CREA_PREGUNTA_OPCIO_PLACEHOLDER + '" ' + htmlText + '/>' +
						'</div>' +
					'</div>' +
					'<div class="col-xs-3 col-sm-3 col-md-6 col-lg-4 padding-chbx-rdb-30">' +
						'<div class="form-group">' +
							'<input class="modificaPreguntaOpcioCheckBox" id="modificaPreguntaCheckBoxOpcio' + idNovaOpcio + 'Correcta" type="checkbox" name="opciocorrectaESNmodifica" value="' + (idNovaOpcio - 1) + '" '+ htmlCorrecta + '> ' +
							CREA_PREGUNTA_OPCIO_CORRECTA +
						'</div>' +
					'</div>' +
				'</div>';
	
	$("#modificaPreguntaDivVeureOpcionsESN").append(opcio);
}

function eliminaOpcioESNmodifica() {
	var darreraOpcio = $(".opcioESNmodifica").length;
	if (darreraOpcio > 0) $("#modificaPreguntaOpcio" + darreraOpcio + TIPUS_PREGUNTA_ESN).remove();
}

function habilitaOpcioES1modifica(codi, text, correcta) {
	// obtenim l'id de la nova opció
	var idNovaOpcio = $(".opcioES1modifica").length + 1;
	
	var htmlText = "";
	if (text != null) htmlText = "value=\"" + text + "\"";
	
	var htmlCorrecta = ""; 
	if (correcta == "S") htmlCorrecta = "checked";
	
	var opcio = '<div id="modificaPreguntaOpcio' + idNovaOpcio + 'ES1" class="row opcioES1modifica">' +
					'<div class="col-xs-9 col-sm-9 col-md-6 col-lg-5">' +
						'<div class="form-group">' +
							'<label for="modificaPreguntaOpcio' + idNovaOpcio + 'TextES1">' + CREA_PREGUNTA_OPCIO_TEXT + '</label>' +
							'<input class="form-control modificaPreguntaOpcio" id="modificaPreguntaOpcio' + idNovaOpcio + 'TextES1" name="opciotextES1modifica" type="text" placeholder="' + CREA_PREGUNTA_OPCIO_PLACEHOLDER + '" ' + htmlText + '/>' +
						'</div>' +
					'</div>' +
					'<div class="col-xs-3 col-sm-3 col-md-6 col-lg-4 padding-chbx-rdb-20">' +
						'<div class="radio">' +
							'<label>' +
								'<input type="radio" name="opciocorrectaES1modifica" id="modificaPreguntaRadioButtonOpcio' + idNovaOpcio + 'Correcta" value="' + (idNovaOpcio - 1) + '" ' + htmlCorrecta + '> ' +
								CREA_PREGUNTA_OPCIO_CORRECTA +
							'</label>' +
						'</div>' +
					'</div>' +
				'</div>';
	
	$("#modificaPreguntaDivVeureOpcionsES1").append(opcio);
}

function eliminaOpcioES1modifica() {
	var darreraOpcio = $(".opcioES1modifica").length;
	if (darreraOpcio > 0) $("#modificaPreguntaOpcio" + darreraOpcio + TIPUS_PREGUNTA_ES1).remove();
}

/**
 * Pinta un modal window amb la descripció de la pregunta
 * 
 * @param codiPregunta
 * 			codi de la pregunta
 * @param botons
 * 			paràmetre que podrà prendre per valors:
 * 				1: botons Edita/Publica/Elimina o Envia visibles
 * 				0: sense botons
 * @param xml
 * 			xml amb la informació de la pregunta a pintar
 */
function ompleDescripcioPregunta(codiPregunta, botons, xml) {
	var estat = $(xml).find("pregunta").attr("estat");
	var numAlumnes = $(xml).find("numAlumnes").text();
	
	// mostram el modal window
	$("#descripcioPreguntaModalMissatge").html(
			modalWindowPintaDescripcioPregunta($(xml).find("pregunta"), null, "descripcioPregunta"));
	
	var modalFooter = "#descripcioPreguntaModal > .modal-dialog > .modal-content > .modal-footer";
	
	// netejam els botons del footer que hagin pogut quedar d'una visita anterior
	$("#descripcioPreguntaModalBotoEnvia").remove();
	$("#descripcioPreguntaModalBotoPublica").remove();
	$("#descripcioPreguntaModalBotoModifica").remove();
	$("#descripcioPreguntaModalBotoElimina").remove();
	
	// Cas en què volem disposar de botons Edita/Publica/Elimina o Envia
	if (botons == 1) {
		// El botó Envia només apareixerà en les preguntes d'estat públic
        var botoEnvia = "";
        var iconaEnvia = "<span class=\"glyphicon glyphicon-send\"></span>";
        if (estat == "public") {
        	// si l'assignatura no té alumnes, no se pot enviar la pregunta a ningú!
        	if (numAlumnes > 0) {
        		botoEnvia = "<button id=\"descripcioPreguntaModalBotoEnvia\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaEnviamentPregunta(" + codiPregunta + ")\">" + iconaEnvia + "  " + MIS_TAULA_REPO_PRE_ENVIA + "</a>";
        	} else {
        		botoEnvia = "<button id=\"descripcioPreguntaModalBotoEnvia\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + MIS_TAULA_REPO_PRE_NO_ALU + "\">" + iconaEnvia + "  " + MIS_TAULA_REPO_PRE_ENVIA + "</button>";
        	}
        }
        
        // El botó Publica només apareixerà en les preguntes d'estat editable
        var botoPublica = "";
        var iconaPublica = "<span class=\"glyphicon glyphicon-briefcase\"></span>";
        if (estat == "editable") {
        	botoPublica = "<button id=\"descripcioPreguntaModalBotoPublica\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaPublicacioPregunta(" + codiPregunta + ")\">" + iconaPublica + "  " + MIS_TAULA_REPO_PRE_PUBLICA + "</a>";
        }
        
        // El botó Modifica només apareixerà en les preguntes d'estat editable
        var botoModifica = "";
        var iconaModifica = "<span class=\"glyphicon glyphicon-edit\"></span>";
        if (estat == "editable") {
        	botoModifica = "<button id=\"descripcioPreguntaModalBotoModifica\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaModificacioPregunta(" + codiPregunta + ")\">" + iconaModifica + "  " + MIS_TAULA_REPO_PRE_MODIFICA + "</a>";
        }
        
        // El botó Elimina només apareixerà en les preguntes d'estat editable
        var botoElimina = "";
        var iconaElimina = "<span class=\"glyphicon glyphicon-trash\"></span>";
        if (estat == "editable") {
        	botoElimina = "<button id=\"descripcioPreguntaModalBotoElimina\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaEliminacioPregunta(" + codiPregunta + ")\">" + iconaElimina + "  " + MIS_TAULA_REPO_PRE_ELIMINA + "</a>";
        }
        
        // En funció de l'estat de la pregunta, volem un botó o un altre:
        if (estat == "editable") {
        	$(modalFooter).append(botoPublica);
        	$(modalFooter).append(botoModifica);
        	$(modalFooter).append(botoElimina);
        } else {
        	$(modalFooter).append(botoEnvia);
        }
	}
	
	$("#descripcioPreguntaModal").modal("show");
}

/**
 * Pinta un modal window amb la descripció del qüestionari
 * 
 * @param codiQuestionari
 * 			codi del qüestionari
 * @param botons
 * 			paràmetre que podrà prendre per valors:
 * 				1: botons Edita/Publica/Elimina o Envia visibles
 * 				0: sense botons
 * @param xml
 * 			xml amb la informació del qüestionari a pintar
 */
function ompleDescripcioQuestionari(codiQuestionari, botons, xml) {
	var estat = $(xml).find("questionari").attr("estat");
	var numAlumnes = $(xml).find("numAlumnes").text();
	
	// mostram el modal window
	$("#descripcioQuestionariModalMissatge").html(
			modalWindowPintaDescripcioQuestionari($(xml).find("questionari"), null, "descripcioQuestionari"));
	
	var modalFooter = "#descripcioQuestionariModal > .modal-dialog > .modal-content > .modal-footer";
	
	// netejam els botons del footer que hagin pogut quedar d'una visita anterior
	$("#descripcioQuestionariModalBotoEnvia").remove();
	$("#descripcioQuestionariModalBotoPublica").remove();
	$("#descripcioQuestionariModalBotoModifica").remove();
	$("#descripcioQuestionariModalBotoElimina").remove();
	
	// Cas en què volem disposar de botons Edita/Publica/Elimina o Envia
	if (botons == 1) {
		// El botó Envia només apareixerà en els qüestionaris d'estat públic
        var botoEnvia = "";
        var iconaEnvia = "<span class=\"glyphicon glyphicon-send\"></span>";
        if (estat == "public") {
        	// si l'assignatura no té alumnes, no se pot enviar el qüestionari a ningú!
        	if (numAlumnes > 0) {
        		botoEnvia = "<button id=\"descripcioQuestionariModalBotoEnvia\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaEnviamentQuestionari(" + codiQuestionari + ")\">" + iconaEnvia + "  " + MIS_TAULA_REPO_QST_ENVIA + "</a>";
        	} else {
        		botoEnvia = "<button id=\"descripcioQuestionariModalBotoEnvia\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + MIS_TAULA_REPO_QST_NO_ALU + "\">" + iconaEnvia + "  " + MIS_TAULA_REPO_QST_ENVIA + "</button>";
        	}
        }
        
        // El botó Publica només apareixerà en els qüestionaris d'estat editable
        var botoPublica = "";
        var iconaPublica = "<span class=\"glyphicon glyphicon-briefcase\"></span>";
        if (estat == "editable") {
        	botoPublica = "<button id=\"descripcioQuestionariModalBotoPublica\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaPublicacioQuestionari(" + codiQuestionari + ")\">" + iconaPublica + "  " + MIS_TAULA_REPO_QST_PUBLICA + "</a>";
        }
        
        // El botó Modifica només apareixerà en els qüestionaris d'estat editable
        var botoModifica = "";
        var iconaModifica = "<span class=\"glyphicon glyphicon-edit\"></span>";
        if (estat == "editable") {
        	botoModifica = "<button id=\"descripcioQuestionariModalBotoModifica\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaModificacioQuestionari(" + codiQuestionari + ")\">" + iconaModifica + "  " + MIS_TAULA_REPO_QST_MODIFICA + "</a>";
        }
        
        // El botó Elimina només apareixerà en els qüestionaris d'estat editable
        var botoElimina = "";
        var iconaElimina = "<span class=\"glyphicon glyphicon-trash\"></span>";
        if (estat == "editable") {
        	botoElimina = "<button id=\"descripcioQuestionariModalBotoElimina\" data-dismiss=\"modal\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"top\" onclick=\"habilitaEliminacioQuestionari(" + codiQuestionari + ")\">" + iconaElimina + "  " + MIS_TAULA_REPO_QST_ELIMINA + "</a>";
        }
        
        // En funció de l'estat del qüestionari, volem un botó o un altre:
        if (estat == "editable") {
        	$(modalFooter).append(botoPublica);
        	$(modalFooter).append(botoModifica);
        	$(modalFooter).append(botoElimina);
        } else {
        	$(modalFooter).append(botoEnvia);
        }
	}
	
	$("#descripcioQuestionariModal").modal("show");
}

function creacioPregunta() {
	// validar en client que l'enunciat de la pregunta no excedeixi els 4000 caràcters que pot tenir.
	if ($('#creaPreguntaEnunciat').val().length > 4000){
		alert(CREA_PREGUNTA_ENUNCIAT_LLARG);
		return;
	}
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	var values = $("#creaPreguntaForm").serialize();
	$.ajax({
        url: "/egradus/repositori/creaPregunta.do",
        type: "post",
        data: values + "&codiAssignatura=" + codiAssignatura, // afegim el codi de l'assignatura
        success: function(xml) {
        	var xmlPregunta = $(xml).find("pregunta");
        	var codi     = xmlPregunta.attr("codi");
        	var enunciat = xmlPregunta.attr("enunciat");
        	var error    = $(xml).find("errorPregunta").text();
        	if (error.length != 0) {
        		$("#creaPreguntaError").html(error);
        	} else {
        		// esborram el contingut dels camps del formulari 
            	// després de la creació de la pregunta si tot ha anat bé
        		$("#creaPreguntaError").empty();
        		$("#creaPreguntaCheckBoxRaonarResposta").prop('checked', false);
        		$("#creaPreguntaEnunciat").val("");
            	$("#creaPreguntaDificultatTeorica").val("");
            	
            	// també esborram les opcions
            	$(".opcioES1").remove();
            	$(".opcioESN").remove();
            	
            	// mostram el modal window
            	var missatgeCapcalera = CREA_PREGUNTA_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + CREA_PREGUNTA_MODAL_MIS2;
            	$("#creaPreguntaModalMissatge").html(modalWindowPintaDescripcioPregunta(xmlPregunta, missatgeCapcalera, "creaPregunta"));
            	$("#creaPreguntaModal").modal("show");
        	}
        },
        error:function(){
        	alert(ERROR_CREACIO_PREGUNTA);
        }
    });
}

function cercaPreguntes() {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	var values = "codiAssignatura=" + codiAssignatura + "&" + $("#cercaPreguntaForm").serialize();
	$.ajax({
        url: "/egradus/repositori/cercaPreguntesFiltre.xml?" + values,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	$("#repositoriPreguntesTaula").empty(); // esborram el contingut abans de cada cerca
        	$("#repositoriPreguntesTaula").append("<thead><tr><th>" + MIS_TAULA_REPO_PRE_CODI + "</th><th>" + MIS_TAULA_REPO_PRE_ENUNCIAT + "</th><th>" + MIS_TAULA_REPO_PRE_TIPUS + "</th><th>" + MIS_TAULA_REPO_PRE_CREADOR + "</th><th>" + MIS_TAULA_REPO_PRE_DATA_ALTA + "</th><th>" + MIS_TAULA_REPO_PRE_RR + "</th><th>" + MIS_TAULA_REPO_PRE_ESTAT + "</th><th>" + MIS_TAULA_REPO_PRE_DT + "</th><th>" + MIS_TAULA_REPO_PRE_DP + "</th><th>" + MIS_TAULA_REPO_PRE_ACCIONS + "</th></tr></thead>");
            
        	var error = $(xml).find("errorPregunta").text();
        	if (error.length != 0) {
        		$("#cercaPreguntaError").html(error);
        	} else {
        		// no hi ha hagut error
        		omplirRepositoriPreguntes(xml);
        		
        		// neteja el possible error que hi hagi hagut anteriorment
        		// en la cerca d'una pregunta
        		$("#cercaPreguntaError").empty();
        	}
        },
        error:function(){
            alert("Error cerca preguntes amb filtres");
        }
    });
}

function cercaQuestionaris() {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	var values = "codiAssignatura=" + codiAssignatura + "&" + $("#cercaQuestionariForm").serialize();
	$.ajax({
        url: "/egradus/repositori/cercaQuestionarisFiltre.xml?" + values,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	$("#repositoriQuestionarisTaula").empty(); // esborram el contingut abans de cada cerca
        	$("#repositoriQuestionarisTaula").append("<thead><tr><th>" + MIS_TAULA_REPO_QST_CODI + "</th><th>" + MIS_TAULA_REPO_QST_NOM + "</th><th>" + MIS_TAULA_REPO_QST_DESCRIPCIO + "</th><th>" + MIS_TAULA_REPO_QST_CREADOR + "</th><th>" + MIS_TAULA_REPO_QST_DATA_ALTA + "</th><th>" + MIS_TAULA_REPO_QST_ESTAT + "</th><th>" + MIS_TAULA_REPO_QST_DT + "</th><th>" + MIS_TAULA_REPO_QST_DP + "</th><th>" + MIS_TAULA_REPO_QST_ACCIONS + "</th></tr></thead>");
            
        	var error = $(xml).find("errorQuestionari").text();
        	if (error.length != 0) {
        		$("#cercaQuestionariError").html(error);
        	} else {
        		// no hi ha hagut error
        		omplirRepositoriQuestionaris(xml);
        		
        		// neteja el possible error que hi hagi hagut anteriorment
        		// en la cerca d'un qüestionari
        		$("#cercaQuestionariError").empty();
        	}
        },
        error:function(){
            alert("Error cerca questionaris amb filtres");
        }
    });
}

function omplirRepositoriPreguntes(xml) {
	// agafam el número d'alumnes que té l'assignatura
	var numAlumnes = $(xml).find("numAlumnes").text();
	
	$(xml).find("pregunta").each(function(){
    	var codi     = $(this).attr("codi");
        var enunciat = $(this).attr("enunciat");
        var tipus    = $(this).attr("tipus");
        var dataalta = new Date($(this).attr("dataAlta"));
        var rr       = $(this).attr("raonarResposta");
        var estat    = $(this).attr("estat");
        var dt       = $(this).attr("dificultatTeorica");
        var dp       = $(this).attr("dificultatPractica");
        var creadorNom = $(this).find("creador").attr("nom");
        var creadorLli1 = $(this).find("creador").attr("llinatge1");
        var creadorLli2 = $(this).find("creador").attr("llinatge2");
        var creadorAli  = $(this).find("creador").attr("alies");
        var creador = creadorAli + " (" + creadorNom + " " + creadorLli1 + " " + creadorLli2 + ")";
        
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
        
        // si l'enunciat és massa llarg com per sortir a la taula, s'acurçarà:
        if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
        
        // El botó Envia només apareixerà en les preguntes d'estat públic
        var botoEnvia = "";
        var iconaEnvia = "<span class=\"glyphicon glyphicon-send\"></span>";
        if (estat == "public") {
        	// si l'assignatura no té alumnes, no se pot enviar la pregunta a ningú!
        	if (numAlumnes > 0) botoEnvia = "<a href=\"javascript: habilitaEnviamentPregunta(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaEnvia + "  " + MIS_TAULA_REPO_PRE_ENVIA + "</a>";
            else                botoEnvia = "<button type=\"button\" class=\"btn btn-default btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + MIS_TAULA_REPO_PRE_NO_ALU + "\">" + iconaEnvia + "  " + MIS_TAULA_REPO_PRE_ENVIA + "</button>";
        }
        
        // El botó Publica només apareixerà en les preguntes d'estat editable
        var botoPublica = "";
        var iconaPublica = "<span class=\"glyphicon glyphicon-briefcase\"></span>";
        if (estat == "editable") {
        	botoPublica = "<a href=\"javascript: habilitaPublicacioPregunta(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaPublica + "  " + MIS_TAULA_REPO_PRE_PUBLICA + "</a>";
        }
        
        // El botó Modifica només apareixerà en les preguntes d'estat editable
        var botoModifica = "";
        var iconaModifica = "<span class=\"glyphicon glyphicon-edit\"></span>";
        if (estat == "editable") {
        	botoModifica = "<a href=\"javascript: habilitaModificacioPregunta(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaModifica + "  " + MIS_TAULA_REPO_PRE_MODIFICA + "</a>";
        }
        
        // El botó Elimina només apareixerà en les preguntes d'estat editable
        var botoElimina = "";
        var iconaElimina = "<span class=\"glyphicon glyphicon-trash\"></span>";
        if (estat == "editable") {
        	botoElimina = "<a href=\"javascript: habilitaEliminacioPregunta(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaElimina + "  " + MIS_TAULA_REPO_PRE_ELIMINA + "</a>";
        }
        
        // Columna Estat (glyphicon)
        var textEstat = "";
        if (estat == "editable") textEstat = "<div class=\"row\"><div class=\"col-md-4 col-md-offset-4\"><span class=\"glyphicon glyphicon-wrench\"></span></div></div>";
        else                     textEstat = "<div class=\"row\"><div class=\"col-md-4 col-md-offset-4\"><span class=\"glyphicon glyphicon-briefcase\"></span></div></div>";
        
        // Columna Raonar Resposta (glyphicon)
        var textRr = "";
        if (rr == "true") textRr = "<div class=\"row\"><div class=\"col-md-4 col-md-offset-4\"><span class=\"glyphicon glyphicon-pencil\"></span></div></div>";
        
        // En funció de l'estat de la pregunta, volem un botó o un altre:
        var botonsEditablesOPublics = "";
        if (estat == "editable") botonsEditablesOPublics = botoModifica + botoElimina + botoPublica;
        else                     botonsEditablesOPublics = botoEnvia;
        
        var textHtml = "<tr><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 1);'>"+ codi +"</a></td><td><a href='javascript: habilitaDescripcioPregunta(" + codi + ", 1);'>"+ enunciat +"</a></td><td>" + tip + "</td><td>" + creador + "</td><td>" + formatData(dataalta) + "</td><td>" + textRr + "</td><td>" + textEstat + "</td><td>"+ dt +"</td><td>"+ dp +"</td><td>" + botonsEditablesOPublics + "</td></tr>";
        $("#repositoriPreguntesTaula").append(textHtml);
    });
}

/**
 * Mostram un missatge per pantalla que indica que
 * estam a punt d'eliminar la pregunta
 * 
 * @param codi
 */
function habilitaEliminacioPregunta(codi) {
	$.ajax({
		url: "/egradus/repositori/pregunta.xml?codiPregunta=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var enunciat = $(xml).find("pregunta").attr("enunciat");
        	var missatge = "<p>" + MIS_ELIMINA_PRE_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + MIS_ELIMINA_PRE_MODAL_MIS2 + "</p>" +
        				   "<p><b>" + MIS_ELIMINA_PRE_MODAL_MIS3 + " </b> " + MIS_ELIMINA_PRE_MODAL_MIS4 + "</p>";
        	$("#repositoriPreguntesModalEliminaMissatge").html(missatge);
        	$("#repositoriPreguntesModalBotoEliminar").attr("onclick", "eliminaPregunta(" + codi + ");");
        	$("#repositoriPreguntesModalElimina").modal("show");
        },
        error:function(){
        	alert("error habilitant eliminació pregunta");
        }
    });
}

/**
 * Eliminam la pregunta i mostram un missatge per pantalla que
 * indiqui que s'ha eliminat la pregunta satisfactòriament
 * 
 * @param codi
 */
function eliminaPregunta(codi) {
	$.ajax({
        url: "/egradus/repositori/eliminaPregunta.do",
        type: "post",
        data: "codiPregunta=" + codi,
        success: function(xml) {
        	var enunciat = $(xml).find("pregunta").attr("enunciat");
        	$("#repositoriPreguntesModalEliminadaMissatge").html("<p>" + MIS_ELIMINADA_PRE_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + MIS_ELIMINADA_PRE_MODAL_MIS2 + "</p>");
        	$("#repositoriPreguntesModalEliminada").modal("show");
        	
        	// tornam a la vista del repositori (forçam que recarregui la vista)
        	habilitaRepositoriPre();
        },
        error:function(){
        	alert("error eliminant la pregunta");
        }
    });	
}

/**
 * Mostram un missatge per pantalla que indica que
 * estam a punt d'accedir a una vista que ens permeti
 * modificar la pregunta
 * 
 * @param codi
 */
function habilitaModificacioPregunta(codi) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialModificacioPregunta").show();
	
	// Passam el codi de pregunta a la vista modificaPregunta, per a poder disposar d'ell quan
	// modifiquem la pregunta
	$("#modificaPreguntaCodi").val(codi);
	
	// neteja els camps del formulari de modificació de pregunta que es puguin haver 
	// quedat d'una modificació anterior (inclòs algún possible error)
	$("#modificaPreguntaError").empty();
	$("#modificaPreguntaCheckBoxRaonarResposta").prop("checked", false);
	$("#modificaPreguntaEnunciat").val("");
	$("#modificaPreguntaDificultatTeorica").val("");
	
	// agafam les dades de la pregunta:
	$.ajax({
		url: "/egradus/repositori/pregunta.xml?codiPregunta=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var enunciat = $(xml).find("pregunta").attr("enunciat");
        	var tipus    = $(xml).find("pregunta").attr("tipus");
        	var rr       = $(xml).find("pregunta").attr("raonarResposta");
        	var dt       = $(xml).find("pregunta").attr("dificultatTeorica");
        	
        	$("#modificaPreguntaDificultatTeorica").val(dt);
        	$("#modificaPreguntaEnunciat").val(enunciat);
        	
        	// marca el checkbox de raonar resposta si fa falta
        	if (rr == "true") $("#modificaPreguntaCheckBoxRaonarResposta").prop("checked", true);
        	
        	// deixa marcat el tipus de pregunta que toca
        	$("#modificaPreguntaOpcio" + tipus).attr('selected', 'selected');
        	preparaElementsFormTipusPreguntaModifica(tipus);
        	
        	$(xml).find("opcio").each(function(){
        		var codiOpcio     = $(this).attr("codi");
                var textOpcio     = $(this).attr("text");
                var correctaOpcio = $(this).attr("correcta");
                
                if (tipus == TIPUS_PREGUNTA_ES1) habilitaOpcioES1modifica(codiOpcio, textOpcio, correctaOpcio);
                if (tipus == TIPUS_PREGUNTA_ESN) habilitaOpcioESNmodifica(codiOpcio, textOpcio, correctaOpcio);
                // les preguntes VOF pinten les opcions directament a la vista
                // les preguntes REC no tenen opcions per pintar a la vista
        	});
        },
        error:function(){
        	alert("error obtenint la pregunta (dins la vista de modificació de pregunta)");
        }
    });	
}

function modificacioPregunta() {
	// validar en client que l'enunciat de la pregunta no excedeixi els 4000 caràcters que pot tenir.
	if ($('#modificaPreguntaEnunciat').val().length > 4000){
		alert(CREA_PREGUNTA_ENUNCIAT_LLARG);
		return;
	}
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// agafam el codi de pregunta que hem inserit en el moment d'entrar a la vista modificaPregunta
	var codiPregunta = $("#modificaPreguntaCodi").val();
	
	var values = $("#modificaPreguntaForm").serialize() + "&codiAssignatura=" + codiAssignatura + "&codiPregunta=" + codiPregunta;
	$.ajax({
        url: "/egradus/repositori/modificaPregunta.do",
        type: "post",
        data: values,
        success: function(xml) {
        	var xmlPregunta = $(xml).find("pregunta");
        	var codi     = xmlPregunta.attr("codi");
        	var enunciat = xmlPregunta.attr("enunciat");
        	var error    = $(xml).find("errorPregunta").text();
        	
        	if (error.length != 0) {
        		$("#modificaPreguntaError").html(error);
        	} else {
        		// esborram el contingut dels camps del formulari 
            	// després de la modificació de la pregunta si tot ha anat bé
        		$("#modificaPreguntaError").empty();
        		$("#modificaPreguntaCheckBoxRaonarResposta").prop('checked', false);
        		$("#modificaPreguntaEnunciat").val("");
            	$("#modificaPreguntaDificultatTeorica").val("");
            	
            	// també esborram les opcions
            	$(".opcioES1modifica").remove();
            	$(".opcioESNmodifica").remove();
            	
            	// mostram el modal window
            	var missatgeCapcalera = MODIFICA_PREGUNTA_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + MODIFICA_PREGUNTA_MODAL_MIS2;
            	$("#modificaPreguntaModalMissatge").html(modalWindowPintaDescripcioPregunta(xmlPregunta, missatgeCapcalera, "modificaPregunta"));
            	$("#modificaPreguntaModalBoto").attr("onclick", "habilitaRepositoriPre();");
            	$("#modificaPreguntaModal").modal("show");
        	}
        },
        error:function(){
        	alert("error modificació pregunta");
        }
    });
}

/**
 * determina el tipus de pregunta que s'ha seleccionat
 * a l'input select en la vista de modificació de pregunta
 */
function determinarTipusPreguntaModifica() {
	var tipus = $("select#modificaPreguntaTipus option").filter(":selected").val();
	preparaElementsFormTipusPreguntaModifica(tipus);
}

/**
 * Funció que modifica els camps del formulari (oculta o mostra les opcions, etc)
 * en la vista de modificació de la pregunta segons quin sigui el tipus de pregunta 
 * que es passa com a paràmetre
 * 
 * @param tipus
 */
function preparaElementsFormTipusPreguntaModifica(tipus) {
	// eliminar opcions ES1 i ESN
	$(".opcioES1modifica").remove();
	$(".opcioESNmodifica").remove();
	
	// esborram el missatge d'error que es pugui haver mostrat anteriorment
	$("#modificaPreguntaError").empty();
	
	switch(tipus) {
	case TIPUS_PREGUNTA_ES1:
		// divs per opcions
		$("#modificaPreguntaDivVeureOpcionsVOF").hide();
		$("#modificaPreguntaDivVeureOpcionsESN").hide();
		$("#modificaPreguntaDivVeureOpcionsES1").show();
		
		// divs per els botons d'afegir opcions
		$("#modificaPreguntaBotonsHabilitaOpcioES1").show();
		$("#modificaPreguntaBotonsHabilitaOpcioESN").hide();
		break;
	case TIPUS_PREGUNTA_ESN:
		// divs per opcions
		$("#modificaPreguntaDivVeureOpcionsVOF").hide();
		$("#modificaPreguntaDivVeureOpcionsES1").hide();
		$("#modificaPreguntaDivVeureOpcionsESN").show();
		
		// divs per els botons d'afegir opcions
		$("#modificaPreguntaBotonsHabilitaOpcioES1").hide();
		$("#modificaPreguntaBotonsHabilitaOpcioESN").show();
        break;
	case TIPUS_PREGUNTA_VOF:
		// divs per opcions
		$("#modificaPreguntaDivVeureOpcionsES1").hide();
		$("#modificaPreguntaDivVeureOpcionsESN").hide();
		$("#modificaPreguntaDivVeureOpcionsVOF").show();
		
		// divs per els botons d'afegir opcions
		$("#modificaPreguntaBotonsHabilitaOpcioES1").hide();
		$("#modificaPreguntaBotonsHabilitaOpcioESN").hide();
		break;
	case TIPUS_PREGUNTA_REC:
		// divs per opcions
		$("#modificaPreguntaDivVeureOpcionsVOF").hide();
		$("#modificaPreguntaDivVeureOpcionsES1").hide();
		$("#modificaPreguntaDivVeureOpcionsESN").hide();
		
		// divs per els botons d'afegir opcions
		$("#modificaPreguntaBotonsHabilitaOpcioES1").hide();
		$("#modificaPreguntaBotonsHabilitaOpcioESN").hide();
        break;
	}
}

/**
 * Mostram un missatge per pantalla que indica que
 * estam a punt de publicar la pregunta
 * 
 * @param codi
 */
function habilitaPublicacioPregunta(codi) {
	$.ajax({
		url: "/egradus/repositori/pregunta.xml?codiPregunta=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var enunciat = $(xml).find("pregunta").attr("enunciat");
        	var missatge = "<p>" + MIS_PUBLICA_PRE_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + MIS_PUBLICA_PRE_MODAL_MIS2 + "</p>" +
        				   "<p><b>" + MIS_PUBLICA_PRE_MODAL_MIS3 + " </b> " + MIS_PUBLICA_PRE_MODAL_MIS4 + "</p>";
        	$("#repositoriPreguntesModalMissatge").html(missatge);
        	$("#repositoriPreguntesModalBotoPublicar").attr("onclick", "publicaPregunta(" + codi + ");");
        	$("#repositoriPreguntesModal").modal("show");
        },
        error:function(){
        	alert("error habilitant publicació pregunta");
        }
    });	
}

/**
 * Publicam la pregunta i mostram un missatge per pantalla que
 * indiqui que s'ha publicat la pregunta satisfactòriament
 * 
 * @param codi
 */
function publicaPregunta(codi) {
	$.ajax({
        url: "/egradus/repositori/publicaPregunta.do",
        type: "post",
        data: "codiPregunta=" + codi,
        success: function(xml) {
        	var enunciat = $(xml).find("pregunta").attr("enunciat");
        	$("#repositoriPreguntesModalPublicadaMissatge").html("<p>" + MIS_PUBLICADA_PRE_MODAL_MIS1 + " <b>" + codi + " - " + enunciat + "</b> " + MIS_PUBLICADA_PRE_MODAL_MIS2 + "</p>");
        	$("#repositoriPreguntesModalPublicada").modal("show");
        	
        	// tornam a la vista del repositori (forçam que recarregui la vista)
        	habilitaRepositoriPre();
        },
        error:function(){
        	alert("error publicant la pregunta");
        }
    });	
}

/**
 * Funció que 'escolta' l'event onchange() del &lt;select&gt; i 
 * prepara els elements del form en funció del tipus modificat.
 */
function determinarTipusPregunta() {
	var tipus = $("select#creaPreguntaTipus option").filter(":selected").val();
	preparaElementsFormTipusPregunta(tipus);
}

/**
 * Funció que modifica els camps del formulari (oculta o mostra les opcions, etc)
 * en la vista de creació de la pregunta segons quin sigui el tipus de pregunta 
 * que es passa com a paràmetre
 * 
 * @param tipus
 */
function preparaElementsFormTipusPregunta(tipus) {
	// eliminar opcions ES1 i ESN
	$(".opcioES1").remove();
	$(".opcioESN").remove();
	
	// esborram el missatge d'error que es pugui haver mostrat anteriorment
	$("#creaPreguntaError").empty();
	
	switch(tipus) {
		case TIPUS_PREGUNTA_ES1:
			// divs per opcions
			$("#creaPreguntaDivVeureOpcionsVOF").hide();
			$("#creaPreguntaDivVeureOpcionsESN").hide();
			$("#creaPreguntaDivVeureOpcionsES1").show();
			
			// raonar resposta
			$("#creaPreguntaRaonarResposta").show();
			
			// divs per els botons d'afegir opcions
			$("#creaPreguntaBotonsHabilitaOpcioES1").show();
			$("#creaPreguntaBotonsHabilitaOpcioESN").hide();
			break;
		case TIPUS_PREGUNTA_ESN:
			// divs per opcions
			$("#creaPreguntaDivVeureOpcionsVOF").hide();
			$("#creaPreguntaDivVeureOpcionsES1").hide();
			$("#creaPreguntaDivVeureOpcionsESN").show();
			
			// raonar resposta
			$("#creaPreguntaRaonarResposta").show();
			
			// divs per els botons d'afegir opcions
			$("#creaPreguntaBotonsHabilitaOpcioES1").hide();
			$("#creaPreguntaBotonsHabilitaOpcioESN").show();
	        break;
		case TIPUS_PREGUNTA_VOF:
			// divs per opcions
			$("#creaPreguntaDivVeureOpcionsES1").hide();
			$("#creaPreguntaDivVeureOpcionsESN").hide();
			$("#creaPreguntaDivVeureOpcionsVOF").show();
			
			// raonar resposta
			$("#creaPreguntaRaonarResposta").show();
			
			// divs per els botons d'afegir opcions
			$("#creaPreguntaBotonsHabilitaOpcioES1").hide();
			$("#creaPreguntaBotonsHabilitaOpcioESN").hide();
			break;
		case TIPUS_PREGUNTA_REC:
			// divs per opcions
			$("#creaPreguntaDivVeureOpcionsVOF").hide();
			$("#creaPreguntaDivVeureOpcionsES1").hide();
			$("#creaPreguntaDivVeureOpcionsESN").hide();
			
			// raonar resposta
			$("#creaPreguntaRaonarResposta").hide();
			$("#creaPreguntaCheckBoxRaonarResposta").prop('checked', false);
			
			// divs per els botons d'afegir opcions
			$("#creaPreguntaBotonsHabilitaOpcioES1").hide();
			$("#creaPreguntaBotonsHabilitaOpcioESN").hide();
	        break;
	}
}

function creacioQuestionari() {
	// validar en client que la descripció del qüestionari no excedeixi els 4000 caràcters que pot tenir.
	if ($('#creaQuestionariDescripcio').val().length > 4000){
		alert(CREA_QUESTIONARI_DESCRIPCIO_LLARGA);
		return;
	}
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	var values = $("#creaQuestionariForm").serialize();
	$.ajax({
        url: "/egradus/repositori/creaQuestionari.do",
        type: "post",
        data: values + "&codiAssignatura=" + codiAssignatura, // afegim el codi de l'assignatura
        success: function(xml) {
        	var codi = $(xml).find("questionari").attr("codi");
        	var nom = $(xml).find("questionari").attr("nom");
        	var error = $(xml).find("errorQuestionari").text();
        	if (error.length != 0) {
        		$("#creaQuestionariError").html(error);
        	} else {
        		// esborram el contingut dels camps del formulari 
            	// després de la creació del qüestionari si tot ha anat bé
        		$("#creaQuestionariError").empty();
        		$("#creaQuestionariNom").val("");
        		$("#creaQuestionariDescripcio").val("");
            	$("#creaQuestionariDificultatTeorica").val("");
            	
            	// mostram el modal window
            	var missatgeCapcalera = CREA_QUESTIONARI_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + CREA_QUESTIONARI_MODAL_MIS2;
            	$("#creaQuestionariModalMissatge").html(modalWindowPintaDescripcioQuestionari(
            			$(xml).find("questionari"), missatgeCapcalera, "creaQuestionari"));
            	$("#creaQuestionariModal").modal("show");
            	
            	// netejam les preguntes afegides en una creació de qüestionari anterior
            	$("#creaQuestionariPreguntesAfegides").empty();
        	}
        },
        error:function(){
        	alert(ERROR_CREACIO_QUESTIONARI);
        }
    });
}

function omplirRepositoriQuestionaris(xml) {
	// agafam el número d'alumnes que té l'assignatura
	var numAlumnes = $(xml).find("numAlumnes").text();
	
	$(xml).find("questionari").each(function(){
    	var codi     = $(this).attr("codi");
        var nom      = $(this).attr("nom");
        var descrip  = $(this).attr("descripcio");
        var dataalta = new Date($(this).attr("dataAlta"));
        var estat    = $(this).attr("estat");
        var dt       = $(this).attr("dificultatTeorica");
        var dp       = $(this).attr("dificultatPractica");
        var creadorNom = $(this).find("creador").attr("nom");
        var creadorLli1 = $(this).find("creador").attr("llinatge1");
        var creadorLli2 = $(this).find("creador").attr("llinatge2");
        var creadorAli  = $(this).find("creador").attr("alies");
        var creador = creadorAli + " (" + creadorNom + " " + creadorLli1 + " " + creadorLli2 + ")";
        
        // si la descripció és massa llarga com per sortir a la taula, s'acurçarà:
        if (descrip.length > 100) descrip = descrip.substring(0, 100) + "...";
        
        // El botó Envia només apareixerà en els qüestionaris d'estat públic
        var botoEnvia = "";
        var iconaEnvia = "<span class=\"glyphicon glyphicon-send\"></span>";
        if (estat == "public") {
        	// si l'assignatura no té alumnes, no se pot enviar el qüestionari a ningú!
        	if (numAlumnes > 0) botoEnvia = "<a href=\"javascript: habilitaEnviamentQuestionari(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaEnvia + "  " + MIS_TAULA_REPO_QST_ENVIA + "</a>";
            else                botoEnvia = "<button type=\"button\" class=\"btn btn-default btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + MIS_TAULA_REPO_QST_NO_ALU + "\">" + iconaEnvia + "  " + MIS_TAULA_REPO_QST_ENVIA + "</button>";
        }
        
        // El botó Publica només apareixerà en els qüestionaris d'estat editable
        var botoPublica = "";
        var iconaPublica = "<span class=\"glyphicon glyphicon-briefcase\"></span>";
        if (estat == "editable") {
        	botoPublica = "<a href=\"javascript: habilitaPublicacioQuestionari(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaPublica + "  " + MIS_TAULA_REPO_QST_PUBLICA + "</a>";
        }
        
        // El botó Modifica només apareixerà en els qüestionaris d'estat editable
        var botoModifica = "";
        var iconaModifica = "<span class=\"glyphicon glyphicon-edit\"></span>";
        if (estat == "editable") {
        	botoModifica = "<a href=\"javascript: habilitaModificacioQuestionari(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaModifica + "  " + MIS_TAULA_REPO_QST_MODIFICA + "</a>";
        }
        
        // El botó Elimina només apareixerà en els qüestionaris d'estat editable
        var botoElimina = "";
        var iconaElimina = "<span class=\"glyphicon glyphicon-trash\"></span>";
        if (estat == "editable") {
        	botoElimina = "<a href=\"javascript: habilitaEliminacioQuestionari(" + codi + ");\" class=\"btn btn-default btn-sm\">" + iconaElimina + "  " + MIS_TAULA_REPO_QST_ELIMINA + "</a>";
        }
        
        // Columna Estat (glyphicon)
        var textEstat = "";
        if (estat == "editable") textEstat = "<div class=\"row\"><div class=\"col-md-4 col-md-offset-4\"><span class=\"glyphicon glyphicon-wrench\"></span></div></div>";
        else                     textEstat = "<div class=\"row\"><div class=\"col-md-4 col-md-offset-4\"><span class=\"glyphicon glyphicon-briefcase\"></span></div></div>";
        
        // En funció de l'estat del qüestionari, volem un botó o un altre:
        var botonsEditablesOPublics = "";
        if (estat == "editable") botonsEditablesOPublics = botoModifica + botoElimina + botoPublica;
        else                     botonsEditablesOPublics = botoEnvia;
        
        var textHtml = "<tr><td><a href='javascript: habilitaDescripcioQuestionari(" + codi + ", 1);'>"+ codi +"</a></td><td><a href='javascript: habilitaDescripcioQuestionari(" + codi + ", 1);'>"+ nom +"</a></td><td>" + descrip + "</td><td>" + creador + "</td><td>" + formatData(dataalta) + "</td><td>" + textEstat + "</td><td>"+ dt +"</td><td>"+ dp +"</td><td>" + botonsEditablesOPublics + "</td></tr>";
        $("#repositoriQuestionarisTaula").append(textHtml);
    });
}

/**
 * habilita la descripció d'un qüestionari
 * 
 * @param codiQuestionari
 * 			codi del qüestionari
 * @param botons
 * 			paràmetre que podrà prendre per valors:
 * 				1: botons Edita/Publica/Elimina o Envia visibles
 * 				0: sense botons
 */
function habilitaDescripcioQuestionari(codiQuestionari, botons) {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	$.ajax({
        url: "/egradus/repositori/descripcioQuestionari.xml?codiQuestionari=" + codiQuestionari + "&codiAssignatura=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	ompleDescripcioQuestionari(codiQuestionari, botons, xml);
        },
        error:function(){
            alert(ERROR_DESCRIPCIO_QUESTIONARI);
        }
    });
}

/**
 * Mostram un missatge per pantalla que indica que
 * estam a punt de publicar el qüestionari
 * 
 * @param codi
 */
function habilitaPublicacioQuestionari(codi) {
	$.ajax({
		url: "/egradus/repositori/questionari.xml?codiQuestionari=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var nom = $(xml).find("questionari").attr("nom");
        	var missatge = "<p>" + MIS_PUBLICA_QST_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + MIS_PUBLICA_QST_MODAL_MIS2 + "</p>" +
        				   "<p><b>" + MIS_PUBLICA_QST_MODAL_MIS3 + " </b> " + MIS_PUBLICA_QST_MODAL_MIS4 + "</p>";
        	$("#repositoriQuestionarisModalMissatge").html(missatge);
        	$("#repositoriQuestionarisModalBotoPublicar").attr("onclick", "publicaQuestionari(" + codi + ");");
        	$("#repositoriQuestionarisModal").modal("show");
        },
        error:function(){
        	alert("error habilitant publicació questionari");
        }
    });	
}

/**
 * Publicam el qüestionari i mostram un missatge per pantalla que
 * indiqui que s'ha publicat el qüestionari satisfactòriament
 * 
 * @param codi
 */
function publicaQuestionari(codi) {
	$.ajax({
        url: "/egradus/repositori/publicaQuestionari.do",
        type: "post",
        data: "codiQuestionari=" + codi,
        success: function(xml) {
        	var nom = $(xml).find("questionari").attr("nom");
        	$("#repositoriQuestionarisModalPublicatMissatge").html("<p>" + MIS_PUBLICAT_QST_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + MIS_PUBLICAT_QST_MODAL_MIS2 + "</p>");
        	$("#repositoriQuestionarisModalPublicat").modal("show");
        	
        	// tornam a la vista del repositori (forçam que recarregui la vista)
        	habilitaRepositoriQst();
        },
        error:function(){
        	alert("error publicant el qüestionari");
        }
    });	
}

/**
 * Mostram un missatge per pantalla que indica que
 * estam a punt d'accedir a una vista que ens permeti
 * modificar el qüestionari
 * 
 * @param codi
 */
function habilitaModificacioQuestionari(codi) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialModificacioQuestionari").show();
	
	// Passam el codi de qüestionari a la vista modificaQuestionari, per a poder disposar d'ell quan
	// modifiquem el qüestionari
	$("#modificaQuestionariCodi").val(codi);
	
	// neteja els camps del formulari de modificació de qüestionari que es puguin haver 
	// quedat d'una modificació anterior (inclòs algún possible error)
	$("#modificaQuestionariError").empty();
	$("#modificaQuestionariNom").val("");
	$("#modificaQuestionariDescripcio").val("");
	$("#modificaQuestionariDificultatTeorica").val("");
	
	// neteja les preguntes afegides d'una modificació anterior
//	$("#modificaQuestionariPreguntesAfegides").empty();
	$(".preguntaAfegida").remove();
	
	// agafam les dades del qüestionari:
	$.ajax({
		url: "/egradus/repositori/questionari.xml?codiQuestionari=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var nom        = $(xml).find("questionari").attr("nom");
        	var descripcio = $(xml).find("questionari").attr("descripcio");
        	var dt         = $(xml).find("questionari").attr("dificultatTeorica");
        	
        	$("#modificaQuestionariNom").val(nom);
        	$("#modificaQuestionariDescripcio").val(descripcio);
        	$("#modificaQuestionariDificultatTeorica").val(dt);
        	
        	$(xml).find("pregunta").each(function(){
        		var codip       = $(this).attr("codi");
                var enunciat    = $(this).attr("enunciat");
                var dtp         = $(this).attr("dificultatTeorica");
                var dp          = $(this).attr("dificultatPractica");
                var rr          = $(this).attr("raonarResposta");
	        	var tipus       = $(this).attr("tipus");
	        	var pes         = $(this).attr("pes");
	            var dataAlta    = new Date($(this).attr("dataAlta"));
	            var creadorNom  = $(this).find("creador").attr("nom");
	            var creadorLli1 = $(this).find("creador").attr("llinatge1");
	            var creadorLli2 = $(this).find("creador").attr("llinatge2");
	            var creadorAli  = $(this).find("creador").attr("alies");
	            var creador = creadorAli + " (" + creadorNom + " " + creadorLli1 + " " + creadorLli2 + ")";
	            
	            // reutilitzarem els mateixos missatges del modal window d'afegir preguntes al
	            // qüestionari per a pintar-les a la vista de creació de qüestionari
	            var mis_preg_afegides_dif_teo = MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_TEO;
	            var mis_preg_afegides_dif_pra = MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_PRA;
	            var mis_preg_afegides_creador = MIS_QST_AFEGIR_PREGUNTES_ROW_CREADOR;
	            var mis_preg_afegides_tipus = MIS_QST_AFEGIR_PREGUNTES_ROW_TIPUS;
	            var mis_preg_afegides_creada = MIS_QST_AFEGIR_PREGUNTES_ROW_CREADA;
	            
	            // si l'enunciat és massa llarg com per sortir a la vista de creació de qüestionaris, s'acurçarà:
	            if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
	            
	            // Columna Raonar Resposta (glyphicon)
	            var rrLlapis;
	            if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
	            else              rrLlapis = "";
	            
	            var dtText;
	            if (dtp.length > 0) dtText = mis_preg_afegides_dif_teo + ": " + dtp;
	            else                dtText = "";
	            
	            var dpText;
	            if (dp.length > 0) dpText = mis_preg_afegides_dif_pra + ": " + dp;
	            else               dpText = "";
	            
	            // Informació de la pregunta
	            var infoPregunta = "<div class=\"row egradus-info-associada\">" +
	    						      "<div class=\"col-lg-3\">" + mis_preg_afegides_creador + ": " + creador + "</div>" +
	    						      "<div class=\"col-lg-1\">" + mis_preg_afegides_tipus + ": " + tipus + "</div>" +
	    						      "<div class=\"col-lg-1\">" + dtText + "</div>" +
	    						      "<div class=\"col-lg-3\">" + dpText + "</div>" +
	    						      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
	    						      "<div class=\"col-lg-3\">" + mis_preg_afegides_creada + ": " + formatData(dataAlta) + "</div>" +
	    						   "</div>";
	            
	            var textPregunta = "<div class=\"row\">" +
	    						      "<div class=\"col-lg-9 egradus-grandaria-pregunta\"><b>" + codip + " - " + enunciat + "</b></div>" +
	    						   "</div>";
	            
	            var inputHidden = "<input type=\"hidden\" id=\"modificaQuestionariPreguntaAfegidaInputCodi" + codip + "\" name=\"codipreguntes\" value=\"" + codip + "\"/>";
	            
	        	var divPregunta = "<div id=\"modificaQuestionariPreguntaAfegidaCodi" + codip + "\" class=\"col-lg-8 well well-sm egradus-vertical-align\">" + inputHidden + infoPregunta + textPregunta + "</div>";
	        	
	        	// Pès de la pregunta
	        	var divPes = "<div class=\"col-lg-3 egradus-vertical-align-30\">" + 
	        				 	"<label for=\"modificaQuestionariPreg" + codip + "\">" + CREA_QUESTIONARI_PES_PREGUNTA + "</label>" +
	        				 	"<input class=\"form-control\" id=\"modificaQuestionariPreg" + codip + "\" name=\"pespreguntes\" type=\"text\" placeholder=\"" + CREA_QUESTIONARI_PLACEHOLDER_PES + "\" value=\"" + pes + "\"/>" +
	        				 "</div>";
	        	
	        	// Botó per desvincular la pregunta del qüestionari
	        	var divEliminarPregunta = "<div class=\"col-lg-1 egradus-vertical-align-40\"><a data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + CREA_QUESTIONARI_DSV_PRE_TOOLTIP + "\" href=\"javascript: desvinculaPregunta(" + codip + ", 'modifica');\"><span class=\"glyphicon glyphicon-trash\"></span></a></div>";
	        	
	        	// Div complet: info pregunta + pes + botó per eliminar
	        	var divCompletPregunta = "<div id=\"modificaQuestionariPreguntaAfegida" + codip + "\" class=\"row preguntaAfegida\">" + divPregunta + divPes + divEliminarPregunta + "</div>";
	        	
	        	$("#modificaQuestionariPreguntesAfegides").append(divCompletPregunta);
        	});
        },
        error:function(){
        	alert("error obtenint la pregunta (dins la vista de modificació de qüestionari)");
        }
    });	
}

function modificacioQuestionari() {
	// validar en client que la descripció del qüestionari no excedeixi els 4000 caràcters que pot tenir.
	if ($('#modificaQuestionariDescripcio').val().length > 4000){
		alert(CREA_QUESTIONARI_DESCRIPCIO_LLARGA);
		return;
	}
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	// agafam el codi de qüestionari que hem inserit en el moment d'entrar a la vista modificaQuestionari
	var codiQuestionari = $("#modificaQuestionariCodi").val();
	
	var values = $("#modificaQuestionariForm").serialize() + "&codiAssignatura=" + codiAssignatura + "&codiQuestionari=" + codiQuestionari;
	$.ajax({
        url: "/egradus/repositori/modificaQuestionari.do",
        type: "post",
        data: values,
        success: function(xml) {
        	var codi  = $(xml).find("questionari").attr("codi");
        	var nom   = $(xml).find("questionari").attr("nom");
        	var error = $(xml).find("errorQuestionari").text();
        	
        	if (error.length != 0) {
        		$("#modificaQuestionariError").html(error);
        	} else {
        		// esborram el contingut dels camps del formulari 
            	// després de la modificació del qüestionari si tot ha anat bé
        		$("#modificaQuestionariError").empty();
        		$("#modificaQuestionariNom").val("");
        		$("#modificaQuestionariDescripcio").val("");
            	$("#modificaQuestionariDificultatTeorica").val("");
            	
            	// també esborram les preguntes afegides
            	$(".preguntaAfegida").remove();
            	
            	// mostram el modal window
            	var missatgeCapcalera = MODIFICA_QUESTIONARI_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + MODIFICA_QUESTIONARI_MODAL_MIS2;
            	$("#modificaQuestionariModalMissatge").html(modalWindowPintaDescripcioQuestionari(
            			$(xml).find("questionari"), missatgeCapcalera, "modificaQuestionari"));
            	$("#modificaQuestionariModalBoto").attr("onclick", "habilitaRepositoriQst();");
            	$("#modificaQuestionariModal").modal("show");
        	}
        },
        error:function(){
        	alert("error modificació qüestionari");
        }
    });
}

/**
 * Mostram un missatge per pantalla que indica que
 * estam a punt d'eliminar el qüestionari
 * 
 * @param codi
 */
function habilitaEliminacioQuestionari(codi) {
	$.ajax({
		url: "/egradus/repositori/questionari.xml?codiQuestionari=" + codi,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var nom = $(xml).find("questionari").attr("nom");
        	var missatge = "<p>" + MIS_ELIMINA_QST_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + MIS_ELIMINA_QST_MODAL_MIS2 + "</p>" +
        				   "<p><b>" + MIS_ELIMINA_QST_MODAL_MIS3 + " </b> " + MIS_ELIMINA_QST_MODAL_MIS4 + "</p>";
        	$("#repositoriQuestionarisModalEliminaMissatge").html(missatge);
        	$("#repositoriQuestionarisModalBotoEliminar").attr("onclick", "eliminaQuestionari(" + codi + ");");
        	$("#repositoriQuestionarisModalElimina").modal("show");
        },
        error:function(){
        	alert("error habilitant eliminació qüestionari");
        }
    });	
}

/**
 * Eliminam el qüestionari i mostram un missatge per pantalla que
 * indiqui que s'ha eliminat el qüestionari satisfactòriament
 * 
 * @param codi
 */
function eliminaQuestionari(codi) {
	$.ajax({
        url: "/egradus/repositori/eliminaQuestionari.do",
        type: "post",
        data: "codiQuestionari=" + codi,
        success: function(xml) {
        	var nom = $(xml).find("questionari").attr("nom");
        	$("#repositoriQuestionarisModalEliminatMissatge").html("<p>" + MIS_ELIMINAT_QST_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + MIS_ELIMINAT_QST_MODAL_MIS2 + "</p>");
        	$("#repositoriQuestionarisModalEliminat").modal("show");
        	
        	// tornam a la vista del repositori (forçam que recarregui la vista)
        	habilitaRepositoriQst();
        },
        error:function(){
        	alert("error eliminant el qüestionari");
        }
    });	
}

/**
 * Habilitam el modal window que mostra un formulari de cerca de
 * preguntes per tal d'incloure en la creacio/modificació de
 * qüestionari actual.
 * 
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 */
function habilitaAfegirPregunta(prefix) {
	var idBotoAcceptar = "#" + prefix + "QuestionariAfegirPreguntaModalBotoAcceptar";
	
	// mostram el modal window
	$("#" + prefix + "QuestionariAfegirPreguntaModal").modal("show");
	$(idBotoAcceptar).attr("onclick", "afegirPreguntes('" + prefix + "');");
	
	// eliminam les preguntes filtrades d'una cerca anterior, a fi d'obligar a que l'usuari
	// faci el filtrat novament
	$("#" + prefix + "QuestionariCercaPreguntaTaula").empty();
}

/**
 * recull les preguntes seleccionades en el modal window que cerca preguntes
 * i les pintam a la vista de creació de qüestionaris.
 * 
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 */
function afegirPreguntes(prefix) {
	// Ara hem de recórrer totes aquelles preguntes que tenguin el class 'egradus-pregunta-marcada'.
	// Però hi ha un problema: tant el modal window de creaQuestionari com el de modificaQuestionari
	// poden haver marcat preguntes a colque moment, i (com feim per tot), el moment en què eliminam
	// aquestes preguntes és en tornar a entrar a creaQuestionari (o modificaQuestionari). Però si
	// entram a creaQuestionari, no eliminam les preguntes del modal window de modificaQuestionari 
	// (ni viceversa), per tant, per evitar agafar les preguntes amb class 'egradus-pregunta-marcada'
	// dels dos modals windows, hem d'assegurar-mos de que siguin totes les preguntes marcades del
	// modal window actual (creaQuestionari o modificaQuestionari). Això ho solucionam fent un selector:
	//
	// ("id-del-div-modal-window-actual" > "classe egradus-pregunta-marcada")
	var selectorPare = "#" + prefix + "QuestionariCercaPreguntaTaula";
	
	// recorrem, per tant, les preguntes marcades del modal window actual:
	$(selectorPare + " > .egradus-pregunta-marcada").each(function(){
		
		// Els id's dels divs-pregunta seleccionades tendran aquesta forma
		var defaultIdPreguntes = prefix + "QuestionariAfegirPreguntaCodi";
		
		// Obtenim el codi de la pregunta marcada
		var divId = $(this).attr("id");
		var codiPregunta = divId.substring(defaultIdPreguntes.length, divId.length);
		
		// cercam la informació d'aquesta pregunta emprant el codi obtingut
		$.ajax({
			url: "/egradus/repositori/pregunta.xml?codiPregunta=" + codiPregunta,
	        type: "get",
	        dataType: "xml",
	        success: function(xml) {
	        	afegirPregunta(xml, prefix, codiPregunta);
	        },
	        error:function(){
	        	alert("error pintant la pregunta a la vista de creació/modificació de qüestionaris");
	        }
	    });
	});
}

/**
 * funció local que pinta una de les preguntes marcades en el modal window de cerca
 * de preguntes a la vista de creació de qüestionaris 
 * 
 * @param xml
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 * @param codiPregunta
 */
function afegirPregunta(xml, prefix, codiPregunta) {
	var enunciat = $(xml).find("pregunta").attr("enunciat");
	var tipus    = $(xml).find("pregunta").attr("tipus");
    var dataAlta = new Date($(xml).find("pregunta").attr("dataAlta"));
    var rr       = $(xml).find("pregunta").attr("raonarResposta");
    var dt       = $(xml).find("pregunta").attr("dificultatTeorica");
    var dp       = $(xml).find("pregunta").attr("dificultatPractica");
    var creadorNom = $(xml).find("creador").attr("nom");
    var creadorLli1 = $(xml).find("creador").attr("llinatge1");
    var creadorLli2 = $(xml).find("creador").attr("llinatge2");
    var creadorAli  = $(xml).find("creador").attr("alies");
    var creador = creadorAli + " (" + creadorNom + " " + creadorLli1 + " " + creadorLli2 + ")";
    
    // reutilitzarem els mateixos missatges del modal window d'afegir preguntes al
    // qüestionari per a pintar-les a la vista de creació de qüestionari
    var mis_preg_afegides_dif_teo = MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_TEO;
    var mis_preg_afegides_dif_pra = MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_PRA;
    var mis_preg_afegides_creador = MIS_QST_AFEGIR_PREGUNTES_ROW_CREADOR;
    var mis_preg_afegides_tipus = MIS_QST_AFEGIR_PREGUNTES_ROW_TIPUS;
    var mis_preg_afegides_creada = MIS_QST_AFEGIR_PREGUNTES_ROW_CREADA;
    
    // si l'enunciat és massa llarg com per sortir a la vista de creació de qüestionaris, s'acurçarà:
    if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
    
    // Columna Raonar Resposta (glyphicon)
    var rrLlapis;
    if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
    else              rrLlapis = "";
    
    var dtText;
    if (dt.length > 0) dtText = mis_preg_afegides_dif_teo + ": " + dt;
    else               dtText = "";
    
    var dpText;
    if (dp.length > 0) dpText = mis_preg_afegides_dif_pra + ": " + dp;
    else               dpText = "";
    
    // Informació de la pregunta
    var infoPregunta = "<div class=\"row egradus-info-associada\">" +
					      "<div class=\"col-lg-3\">" + mis_preg_afegides_creador + ": " + creador + "</div>" +
					      "<div class=\"col-lg-1\">" + mis_preg_afegides_tipus + ": " + tipus + "</div>" +
					      "<div class=\"col-lg-1\">" + dtText + "</div>" +
					      "<div class=\"col-lg-3\">" + dpText + "</div>" +
					      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
					      "<div class=\"col-lg-3\">" + mis_preg_afegides_creada + ": " + formatData(dataAlta) + "</div>" +
					   "</div>";
    
    var textPregunta = "<div class=\"row\">" +
					      "<div class=\"col-lg-9 egradus-grandaria-pregunta\"><b>" + codiPregunta + " - " + enunciat + "</b></div>" +
					   "</div>";
    
    var inputHidden = "<input type=\"hidden\" id=\"" + prefix + "QuestionariPreguntaAfegidaInputCodi" + codiPregunta + "\" name=\"codipreguntes\" value=\"" + codiPregunta + "\"/>";
    
	var divPregunta = "<div id=\"" + prefix + "QuestionariPreguntaAfegidaCodi" + codiPregunta + "\" class=\"col-lg-8 well well-sm egradus-vertical-align\">" + inputHidden + infoPregunta + textPregunta + "</div>";
	
	// Pès de la pregunta
	var divPes = "<div class=\"col-lg-3 egradus-vertical-align-30\">" + 
				 	"<label for=\"" + prefix + "QuestionariPreg" + codiPregunta + "\">" + CREA_QUESTIONARI_PES_PREGUNTA + "</label>" +
				 	"<input class=\"form-control\" id=\"" + prefix + "QuestionariPreg" + codiPregunta + "\" name=\"pespreguntes\" type=\"text\" placeholder=\"" + CREA_QUESTIONARI_PLACEHOLDER_PES + "\"/>" +
				 "</div>";
	
	// Botó per desvincular la pregunta del qüestionari
	var divEliminarPregunta = "<div class=\"col-lg-1 egradus-vertical-align-40\"><a data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + CREA_QUESTIONARI_DSV_PRE_TOOLTIP + "\" href=\"javascript: desvinculaPregunta(" + codiPregunta + ", '" + prefix + "');\"><span class=\"glyphicon glyphicon-trash\"></span></a></div>";
	
	// Div complet: info pregunta + pes + botó per eliminar
	var divCompletPregunta = "<div id=\"" + prefix + "QuestionariPreguntaAfegida" + codiPregunta + "\" class=\"row preguntaAfegida\">" + divPregunta + divPes + divEliminarPregunta + "</div>";
	
	$("#" + prefix + "QuestionariPreguntesAfegides").append(divCompletPregunta);
}

/**
 * en el moment d'afegir preguntes al qüestionari que estam
 * creant, aquesta funció desvincula la pregunta del qüestionari
 * 
 * @param codiPregunta
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 */
function desvinculaPregunta(codiPregunta, prefix) {
	$("#" + prefix + "QuestionariPreguntaAfegida" + codiPregunta).remove();
}

/**
 * en el moment d'afegir preguntes al qüestionari que estam
 * creant, aquesta funció cerca i mostra les preguntes filtrades
 * per a, posteriorment, marcar-les i afegir-les al qüestionari
 * 
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 */
function cercaPreguntaDinsCreaQuestionari(prefix) {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	var values = "codiAssignatura=" + codiAssignatura + "&" + $("#" + prefix + "QuestionariCercaPreguntaForm").serialize();
	
	// també li passam el paràmetre estat, ja que empram el mateix mètode de Controlador que la cerca de preguntes
	// dins el repositori de preguntes, i aquest mètode espera obtenir un estat (encara que sigui nul).
	// Volem que les preguntes afegides al qüestionari siguin en estat públic, per tant:
	values += "&estat=public";
	
	$.ajax({
        url: "/egradus/repositori/cercaPreguntesFiltre.xml?" + values,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	$("#" + prefix + "QuestionariCercaPreguntaTaula").empty(); // esborram el contingut abans de cada cerca
            
        	var error = $(xml).find("errorPregunta").text();
        	if (error.length != 0) {
        		$("#" + prefix + "QuestionariCercaPreguntaError").html(error);
        	} else {
        		// no hi ha hagut error
        		omplirResultatCercaPreguntaDinsCreaQuestionari(xml, prefix);
        		
        		// neteja el possible error que hi hagi hagut anteriorment
        		// en la cerca d'una pregunta
        		$("#" + prefix + "QuestionariCercaPreguntaError").empty();
        	}
        },
        error:function(){
            alert("Error cerca preguntes amb filtres (dins la creació de qüestionaris)");
        }
    });
}

/**
 * funció local que pinta dins el modal window de cerca de preguntes aquelles
 * preguntes que s'hagin filtrat
 * 
 * @param xml
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 */
function omplirResultatCercaPreguntaDinsCreaQuestionari(xml, prefix) {
	$(xml).find("pregunta").each(function(){
    	var codi     = $(this).attr("codi");
        var enunciat = $(this).attr("enunciat");
        var tipus    = $(this).attr("tipus");
        var dataAlta = new Date($(this).attr("dataAlta"));
        var rr       = $(this).attr("raonarResposta");
        var dt       = $(this).attr("dificultatTeorica");
        var dp       = $(this).attr("dificultatPractica");
        var creadorNom = $(this).find("creador").attr("nom");
        var creadorLli1 = $(this).find("creador").attr("llinatge1");
        var creadorLli2 = $(this).find("creador").attr("llinatge2");
        var creadorAli  = $(this).find("creador").attr("alies");
        var creador = creadorAli + " (" + creadorNom + " " + creadorLli1 + " " + creadorLli2 + ")";
        
        // si l'enunciat és massa llarg com per sortir a la taula, s'acurçarà:
        if (enunciat.length > 100) enunciat = enunciat.substring(0, 100) + "...";
        
        // Columna Raonar Resposta (glyphicon)
        var rrLlapis;
        if (rr == "true") rrLlapis = "<span class=\"glyphicon glyphicon-pencil\"></span>";
        else              rrLlapis = "";
        
        var dtText;
        if (dt.length > 0) dtText = MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_TEO + ": " + dt;
        else               dtText = "";
        
        var dpText;
        if (dp.length > 0) dpText = MIS_QST_AFEGIR_PREGUNTES_ROW_DIF_PRA + ": " + dp;
        else               dpText = "";

        var infoPregunta = "<div class=\"row egradus-info-associada\">" +
						      "<div class=\"col-lg-3\">" + MIS_QST_AFEGIR_PREGUNTES_ROW_CREADOR + ": " + creador + "</div>" +
						      "<div class=\"col-lg-1\">" + MIS_QST_AFEGIR_PREGUNTES_ROW_TIPUS + ": " + tipus + "</div>" +
						      "<div class=\"col-lg-1\">" + dtText + "</div>" +
						      "<div class=\"col-lg-3\">" + dpText + "</div>" +
						      "<div class=\"col-lg-1\">" + rrLlapis + "</div>" +
						      "<div class=\"col-lg-3\">" + MIS_QST_AFEGIR_PREGUNTES_ROW_CREADA + ": " + formatData(dataAlta) + "</div>" +
						   "</div>";
        
        var textPregunta = "<div class=\"row\">" +
						      "<div class=\"col-lg-12 egradus-grandaria-pregunta\"><b>" + codi + " - " + enunciat + "</b></div>" +
						   "</div>";
        
        // si la pregunta ha estat seleccionada anteriorment, no ha de poder-se tornar a marcar!
        var divPregunta = "";
        if (preguntaJaAfegida(codi, prefix) == "false") {
        	divPregunta = "<div id=\"" + prefix + "QuestionariAfegirPreguntaCodi" + codi + "\" class=\"well well-sm\"><a href=\"javascript: marcaPregunta(" + codi + ", '" + prefix + "');\">" + infoPregunta + textPregunta + "</a></div>";
        } else {
        	var infoAfegida = "<p class=\"egradus-pregunta-afegida\"><strong>" + MIS_QST_AFEGIR_PREGUNTES_PRE_AFEGIDA + "</strong></p>";
        	divPregunta = "<div id=\"" + prefix + "QuestionariAfegirPreguntaCodi" + codi + "\" class=\"well well-sm egradus-pregunta-afegida\">" + infoPregunta + infoAfegida + textPregunta + "</div>";
        }
        
        $("#" + prefix + "QuestionariCercaPreguntaTaula").append(divPregunta);
    });
}

/**
 * Funció local que retorna true si la pregunta amb codi 'codi'
 * ha sigut afegida al qüestionari anteriorment. Retorna 'false'
 * en cas contrari.
 * 
 * @param codi
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 */
function preguntaJaAfegida(codi, prefix) {
	var trobada = "false";
	
	var defaultIdPreguntaAfegida = prefix + "QuestionariPreguntaAfegida";
	
	// obtenim les preguntes que ja han sigut afegides al qüestionari anteriorment
	// a fi de pintar-les d'un altre color quan tornin a sortir a la cerca, així
	// l'usuari no podrà clicar-les per a afegir-les de nou.
	$(".preguntaAfegida").each(function(){
		// Obtenim el codi de la pregunta marcada
		var divId = $(this).attr("id");
		var codiPregunta = divId.substring(defaultIdPreguntaAfegida.length, divId.length);
		
		// si coincideixen, actualitzam el valor de 'trobada'
		if (codi == codiPregunta) trobada = "true";
	});
	return trobada;
}

/**
 * Context: s'estan seleccionant les preguntes que formaran part del qüestionari. 
 * Aquesta funció s'encarrega d'indicar quina pregunta està marcada i quina no.
 * Per marcar una pregunta basta fer clic sobre la ella. Per desmarcar-la, 
 * basta tornar a fer-li clic.
 * 
 * Poden haver-hi vàries preguntes marcades al mateix temps.
 * 
 * @param codiOpcio
 * @param prefix
 * 			pot valer 'crea' o 'modifica'. L'emplearem per adreçar
 *  els div's de les vistes creaQuestionari.jsp o modificacioQuestionari.jsp
 */
function marcaPregunta(codiPregunta, prefix) {
	var idPregunta = "#" + prefix + "QuestionariAfegirPreguntaCodi" + codiPregunta;
	if  ($(idPregunta).hasClass("egradus-pregunta-marcada")) $(idPregunta).removeClass("egradus-pregunta-marcada");
	else 											   	     $(idPregunta).addClass("egradus-pregunta-marcada");
}


function habilitaEnviamentQuestionari(codiQuestionari) {
	// ocultam tots els divs de material i mostram el que toca
	$(".egradus-material").hide();
	$("#materialEnviamentQuestionari").show();
	
	$("#enviaQuestionariLlistatAlumnes").empty();
	
	// elimina el missatge d'error que pugui haver sortit amb anterioritat
	$("#enviaQuestionariError").empty();
	
	// obtenem el qüestionari
	$.ajax({
        url: "/egradus/repositori/questionari.xml?codiQuestionari=" + codiQuestionari,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	var divQuestionari = modalWindowPintaDescripcioQuestionari($(xml).find("questionari"), null, "enviaQuestionari");
        	$("#enviaQuestionariDescripcioQuestionari").html(divQuestionari);
        },
        error:function(){
            alert("error habilitaEnviamentQuestionari: en el get questionari");
        }
    });
	
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	$.ajax({
        url: "/egradus/assignatura/alumnes.xml?codi=" + codiAssignatura,
        type: "get",
        dataType: "xml",
        success: function(xml) {
        	$(xml).find("alumne").each(function(){
        		var codi = $(this).attr("codi");
                var nom = $(this).attr("nom");
                var lli1 = $(this).attr("llinatge1");
                var lli2 = $(this).attr("llinatge2");
                var ali  = $(this).attr("alies");
                var alumne = ali + " (" + nom + " " + lli1 + " " + lli2 + ")";
                
                // L'alumne ha d'aparèixer com un checkbox
                var alumneCheckbox = "<div class=\"row padding-chbx-rdb-10\">" +
						    			  "<div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12\">" +
						    			  	  "<div class=\"row hover-chbx\">" +
						    			  	      "<div class=\"col-xs-4 col-sm-4 col-md-3 col-lg-3\">" +
						    			              alumne +
						    			          "</div>" +
						    			          "<div class=\"col-xs-8 col-sm-8 col-md-9 col-lg-9\">" +
						    			              " <input id=\"enviaQuestionariAlumne" + codi + "\" type=\"checkbox\" name=\"alumnes\" value=\"" + codi + "\">" +
						    			          "</div>" +
						    			      "</div>" +
						    			  "</div>" +
						    		  "</div>";
                
                $("#enviaQuestionariLlistatAlumnes").append(alumneCheckbox);
            });
        	
        	var botoEnviament = "<a class=\"btn btn-default pull-right\" href=\"javascript: enviaQuestionari(" + codiQuestionari + ");\">" + ENVIA_QUESTIONARI_BOTO + "</a>";
        	$("#enviaQuestionariBotoEnviament").html(botoEnviament);
        },
        error:function(){
            alert("error habilita enviament qüestionari");
        }
    });
}

function enviaQuestionari(codi) {
	// agafam el codi d'assignatura que hem inserit en el moment d'entrar al material de l'assignatura
	var codiAssignatura = $("#materialCodiAssignatura").val();
	
	var values = $("#enviaQuestionariForm").serialize();
	$.ajax({
        url: "/egradus/repositori/enviaQuestionari.do",
        type: "post",
        // afegim el codi del qüestionari i el de l'assignatura
        data: values + "&codiQuestionari=" + codi + "&codiAssignatura=" + codiAssignatura, 
        success: function(xml) {
        	var errorQst = $(xml).find("errorQuestionari").text();
        	var nom      = $(xml).find("questionari").attr("nom");
        	
        	if (errorQst.length != 0) {
        		$("#enviaQuestionariError").html(errorQst);
        	} else {
        		// mostram el modal window
            	$("#enviaQuestionariModalBotoCreacio").attr("onclick", "habilitaCreacioQuestionari();");
            	$("#enviaQuestionariModalBotoRepositori").attr("onclick", "habilitaRepositoriQst();");
            	$("#enviaQuestionariModalMissatge").html(ENVIA_QUESTIONARI_MODAL_MIS1 + " <b>" + codi + " - " + nom + "</b> " + ENVIA_QUESTIONARI_MODAL_MIS2);
            	$("#enviaQuestionariModal").modal("show");
            	
            	// elimina el missatge d'error que pugui haver sortit amb anterioritat
            	$("#enviaQuestionariError").empty();
        	}
        },
        error:function(){
        	alert("error envia qüestionari");
        }
    });
}

/**
 * Construeix un troç d'HTML que representa la descripció de la
 * pregunta de l'XML passat per paràmetre
 * 
 * Donat que la funció s'emplearà en l'àmbit de la creació i 
 * modificació de preguntes, i també com a part de la creació i 
 * modificació de qüestionaris, hem de passar un id base per a
 * escriure els id's dels divs que toca a cada cas.
 * 
 * @param xml
 * 				xml amb la informació de la pregunta que hem de representar
 * @param missatgeModal
 * 				missatge de capçalera del modal (no el títol) sino la 
 * 				informació de que la pregunta ha sigut creada/modificada amb èxit
 * @param idBase
 * 				id de base que s'emplearà per crear els elements HTML amb id's
 * 				corresponents a la creació o modificació de pregunta o creació o
 * 				modificació de qüestionari, segons sigui el cas.
 */
function modalWindowPintaDescripcioPregunta(xml, missatgeModal, idBase) {
	var enunciat = $(xml).attr("enunciat");
	var tipus    = $(xml).attr("tipus");
	var dt       = $(xml).attr("dificultatTeorica");
	var rr       = $(xml).attr("raonarResposta");
	
	var divModal = "";
	
	// només en els casos en què es cridi aquesta funció en la creació o modificació
	// d'una pregunta, assignarem un missatge de capçalera "la pregunta X s'ha creat/modificat
	// amb èxit", ja que quan creem o modifiquem un qüestionari, no necessitam informar
	// de que cada una de les preguntes que conté s'ha creat/modificat amb èxit.
	if (missatgeModal != null) divModal += missatgeModal + "<br /><br />";
	
	// Tipus
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
    divModal += "<b>" + MIS_TAULA_REPO_PRE_TIPUS + ":</b> " + tip + "<br />";
	
    // Dificultat teòrica
    divModal += "<b>" + MIS_TAULA_REPO_PRE_DT + ":</b> " + dt + "<br />";
    
    //Raonar resposta
    if (rr == "true")      divModal += "<b>" + MIS_TAULA_REPO_PRE_RR + ":</b> " + DESCRIPCIO_PREGUNTA_SI_RR + "<br />";
	else if(rr == "false") divModal += "<b>" + MIS_TAULA_REPO_PRE_RR + ":</b> " + DESCRIPCIO_PREGUNTA_NO_RR + "<br />";
    
    // Enunciat
	divModal += "<div class=\"row\">" +
					"<div class=\"col-xs-12\">" +
						"<div class=\"panel panel-default\">" +
							"<div id=\"" + idBase + "Enunciat\" class=\"panel-body text-center\"><b>" + enunciat + "</b></div>" +
						"</div>" +
					"</div>" +
				"</div>";
	
	// Opcions
	if (tipus != TIPUS_PREGUNTA_REC) {
		$(xml).find("opcio").each(function(){
			var codiOpcio = $(this).attr("codi");
			var correcta = $(this).attr("correcta");
			var text = $(this).attr("text");
			
			var correctaIcon = "";
	        if (correcta == 'S')	correctaIcon = "<span class=\"glyphicon glyphicon-ok\"></span>";
	        else 					correctaIcon = "<span class=\"glyphicon glyphicon-remove\"></span>";
	        
	        divModal += "<div class=\"row\">" +
							"<div class=\"col-xs-offset-2 col-xs-5 col-sm-6\">" +
								"<div class=\"panel panel-default\">" +
									"<div id=\"" + idBase + "ModalOpcio" + codiOpcio + "Text\" class=\"panel-body text-center\">" + text + "</div>" +
								"</div>" +
							"</div>" +
							"<div class=\"col-xs-3 col-sm-2\">" +
								"<div class=\"panel panel-default\">" +
									"<div id=\"" + idBase + "ModalOpcio" + codiOpcio + "Correcta\" class=\"panel-body text-center\">" + correctaIcon + "</div>" +
								"</div>" +
							"</div>" +
						"</div>";
    	});
	}
	
	return divModal;
}

/**
 * Construeix un troç d'HTML que representa la descripció del
 * qüestionari de l'XML passat per paràmetre
 * 
 * Donat que la funció s'emplearà en l'àmbit de la creació i 
 * modificació de qüestionaris, hem de passar un id base per 
 * a escriure els id's dels divs que toca a cada cas.
 * 
 * @param xml
 * 				xml amb la informació del qüestionari que hem de representar
 * @param missatgeModal
 * 				missatge de capçalera del modal (no el títol) sino la 
 * 				informació de que el qüestionari ha sigut creat/modificat amb èxit
 * @param idBase
 * 				id de base que s'emplearà per crear els elements HTML amb id's
 * 				corresponents a la creació o modificació de qüestionari, segons 
 * 				sigui el cas.
 */
function modalWindowPintaDescripcioQuestionari(xml, missatgeModal, idBase) {
	var nom        = $(xml).attr("nom");
	var descripcio = $(xml).attr("descripcio");
	var dt         = $(xml).attr("dificultatTeorica");
	
	// Missatge de capçalera
//	var divModal = missatgeModal + "<br /><br />";
	var divModal = "";
	if (missatgeModal != null) divModal += missatgeModal + "<br /><br />";
	
    // Dificultat teòrica
    divModal += "<b>" + MIS_TAULA_REPO_PRE_DT + ":</b> " + dt + "<br />";
    
    // Nom + descripció
	divModal += "<div class=\"row\">" +
					"<div class=\"col-xs-12\">" +
						"<div class=\"panel panel-default\">" +
							"<div id=\"" + idBase + "NomIDescripcio\" class=\"panel-body text-center\">" +
									"<h4><b>" + nom + "</b></h4>" +
									"<br />" + 
									descripcio + 
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>";
	
	// Preguntes
	var indexPregunta = 1;
	$(xml).find("pregunta").each(function(){
		var codiPregunta = $(this).attr("codi");
		var pesPregunta  = $(this).attr("pes");
		
		divModal += "<h4>" + MIS_QST_INI_CNT_PREGUNTA + " " + indexPregunta++ + "</h4>";
		divModal += "<div class=\"row\">" +
						"<div class=\"col-xs-12\">" +
							"<div class=\"panel panel-default\">" +
								"<div id=\"" + idBase + "Pregunta" + codiPregunta + "Contenidor\" class=\"panel-body\">" +
									"<b>" + MIS_QST_INI_CNT_PREGUNTA_PES + ":</b> " + pesPregunta + 
										"<br />" +
										modalWindowPintaDescripcioPregunta($(this), null, idBase + "Pregunta" + codiPregunta) +
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>";
	});
	
	return divModal;
}

