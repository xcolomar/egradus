/*
 * egradus-utilitats.js
 * ----------------------------------------------------------------------------------------
 * 
 * Utilitats bàsiques de dates per a la resta de funcionalitats Javascript d'Egradus.
 * 
 */


/**
 * funció que retorna el nom del dia de la setmana rebent la posició que ocupa el dia
 * en la setmana. Per exemple, 0 és diumenge, 3 dimecres, 5 divendres, etc.
 * 
 * @param day
 * @returns
 */
function getWeekDay(day) {
	var week = [MIS_DIUMENGE, MIS_DILLUNS, MIS_DIMARTS, MIS_DIMECRES, MIS_DIJOUS, MIS_DIVENDRES, MIS_DISSABTE];
	return week[day];
}

/**
 * funció que retorna el nom del mes rebent la posició que ocupa el mes en
 * l'any. Per exemple, 0 és gener, 1 febrer, 9 octubre, etc.
 * 
 * @param month
 * @returns
 */
function getMonthName(month) {
	var months = [MIS_GENER, MIS_FEBRER, MIS_MARC, MIS_ABRIL, MIS_MAIG, MIS_JUNY, MIS_JULIOL,
	              MIS_AGOST, MIS_SETEMBRE, MIS_OCTUBRE, MIS_NOVEMBRE, MIS_DECEMBRE];
	return months[month];
}

/**
 * funció auxiliar utilitzada per <b>diferenciaDates();</b>
 * que completa amb un 0 els temps menors a 10 (per ocupar les
 * dos xifres del format HH:MM:SS)
 * 
 * @param temps
 * @returns {String}
 */
function completaTemps(temps) {
	if (temps < 10) return "0" + temps;
	else return temps;
}

/**
 * funció auxiliar utilitzada que elimina el primer 0 en aquells casos
 * en què 'temps' vengui com a 05 en un format (HH:MM:SS). Si 'temps'
 * no comença per 0, la funció no té cap efecte.
 * 
 * @param temps
 * @returns {String}
 */
function formatejaTemps(temps) {
	if (temps.indexOf("0") == 0) return temps.substr(1, temps.length);
	else return temps;
}

/**
 * retorna la diferència entre una data d'inici i una data de fi
 * en format HH:MM:SS
 * 
 * @param dataFi
 * @param dataInici
 * @returns {String}
 */
function diferenciaDates(dataFi, dataInici) {
	return formatTempsBySegons(Math.round(dataFi.getTime() - dataInici.getTime())/1000);
}

/**
 * retorna la expressió en format HH:MM:SS associada al número 
 * de segons passat com a paràmetre
 * 
 * @param segons
 * @returns {String}
 */
function formatTempsBySegons(segons) {
	var temps;
	if (segons < 60) temps = "00:" + completaTemps(segons) + "s";
	else {
		if (segons < 3600) temps = completaTemps(Math.floor(segons/60)) + ":" + completaTemps((segons % 60)) + "min";
		else temps = completaTemps(Math.floor(segons/3600)) + ":" + completaTemps(Math.floor((segons % 3600)/60)) + ":" + completaTemps(((segons % 3600) % 60)) + "h";
	}
	return temps;
}

/**
 * retorna el num de segons equivalent
 * als segons representats pel string de la forma 00:SS
 * que rebem com a paràmetre
 * 
 * @param temps
 * @returns
 */
function tractamentSegons(temps) {
	return parseInt(formatejaTemps(temps.substring(temps.indexOf(":") + 1, temps.length)));
}

/**
 * retorna el num de segons equivalent
 * als minuts i segons representats pel string de la forma MM:SS
 * que rebem com a paràmetre
 * 
 * @param temps
 * @returns
 */
function tractamentMinutsISegons(temps) {
	return tractamentSegons(temps) + 
		parseInt((formatejaTemps(temps.substring(0, temps.indexOf(":"))) * 60));
}

/**
 * retorna la mateixa quantitat de segons sense decimals
 * @param segons
 */
function eliminaDecimals(segons) {
	var resultat;
	if (String(segons).indexOf(".") <= 0) resultat = segons;
	else resultat = parseInt(String(segons).substring(0, String(segons).indexOf(".")));
	return resultat;
}

/**
 * retorna el número de segons associat a la expressió 
 * 'temps' en format HH:MM:SS passada com a paràmetre
 * 
 * @param temps
 * @returns {String}
 */
function formatSegonsByTemps(temps) {
	var segons = 0;
	if (temps.indexOf("s") != -1) { 
		// llevam la lletra "s"
		temps = temps.substring(0, temps.indexOf("s"));
		segons = tractamentSegons(temps);
	} else if (temps.indexOf("min") != -1) {
		// llevam les lletres "min"
		temps = temps.substring(0, temps.indexOf("min"));
		segons = tractamentMinutsISegons(temps);
	} else if (temps.indexOf("h") != -1) {
		// llevam la lletra "h"
		temps = temps.substring(0, temps.indexOf("h"));
		var hores = formatejaTemps(temps.substring(0, temps.indexOf(":")));
		temps = temps.substring(temps.indexOf(":") + 1, temps.length);
		segons = tractamentMinutsISegons(temps) + parseInt(hores * 3600);
	}
	return segons;
}

/**
 * retorna la data (de tipus Date()) en el següent format:
 * dimarts, 25-Feb-2013 19:03:44
 * 
 * @param data
 * @returns {String}
 */
function formatData(data){
	return getWeekDay(data.getDay()) + ", " + completaTemps(data.getDate()) + "-" + getMonthName(data.getMonth()) + "-" + data.getFullYear() + " " + completaTemps(data.getHours()) + ":" + completaTemps(data.getMinutes()) + ":" + completaTemps(data.getSeconds());
}

/**
 * Retorna el decimal 'num' arrodonit a 'numDecimals' 
 * xifres decimals.
 * 
 * @param num
 * 			número decimal a arrodonir
 * @param numDecimals
 * 			número de decimals que arrodonirem
 */
function decimalArrodonit(num, numDecimals) {
	var d = Math.pow(10, numDecimals);
	return Math.round(num * d) / d;
}

/**
 * Funció que realitza la conversió de format i tipus d'una data
 * per a poder ser compatible en els navegadors Chrome, Firefox 
 * i IE7+
 * 
 * @param data
 * 			data tipus String en format yyyy-mm-dd hh:mm:ss.0 
 * 			(el darrer dígit per a les dècimes de segon)
 * @return data tipus Date() i en format yyyy/mm/dd hh:mm:ss
 * 			(sense dígit per a les dècimes de segon)
 */
function dataTextualNavegador(data) {
	return new Date(data.replace(/\-/g,'\/').replace(/\.[0-9]/g, ''));
}