package org.projecte.egradus.utilities;

/**
 * Utilitats de format, com ara les expressions regulars 
 * per a mapetjar formats numèrics o mètodes per tractar
 * Strings i Floats.
 * 
 * @author Xavier
 *
 */
public class Format {
	
	public static final String FORMAT_DIGIT_ENTER  = "[0-9]";
	public static final String FORMAT_SEPARADOR    = "[.,']";
	
	public static final String FORMAT_ENTER        = FORMAT_DIGIT_ENTER + "+";
	public static final String FORMAT_DECIMAL      = FORMAT_ENTER + "(" + FORMAT_SEPARADOR + FORMAT_ENTER + ")?";
	
	public static final String FORMAT_ANY_ACADEMIC = FORMAT_DIGIT_ENTER + "{4}-" + FORMAT_DIGIT_ENTER + "{2}";
	
	/**
	 * Converteix el string s en un Float.
	 * Precondició: El string s ha de ser vàlid segons la expresió regular 'FORMAT_DECIMAL'
	 * 
	 * @param s
	 * @return
	 */
	public static Float converteixAFloat(String s) {
		return Float.valueOf(s.replace(',', '.').replace('\'', '.'));
	}
	
	/**
	 * Retorna true si els anys de l'any acadèmic representat pel string ssón consecutius.
	 * Retorna false en cas contrari.
	 * 
	 * Precondició: El string s ha de ser vàlid segons la expresió regular 'FORMAT_ANY_ACADEMIC'
	 * 
	 * @param s
	 * @return
	 */
	public static boolean anyAcademicCorrecte(String s) {
		Integer anyInici = Integer.parseInt(s.substring(0, 4));
		Integer anyFi = Integer.parseInt(s.substring(0, 2).concat(s.substring(5, 7)));
		return (anyInici).equals(anyFi - 1);
	}
	
}
