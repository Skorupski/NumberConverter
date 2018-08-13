package eu.scattering.core;

import eu.scattering.core.conversion.ConMethod;
import eu.scattering.core.conversion.ConRoman;

/**
 * Klasa zawierajaca wartosci domyslne.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class GlobalVariables {

	/* Domyslny adres serwera. */
	public final static String defaultConversionServerAddress = "http://localhost:8080/NumberConverter-1.0/conversion";
	/* Domyslna metoda konwersji. */
	public final static ConMethod defaultConversionMethod = new ConRoman();
	/* Nazwa domyslnej metody konwersji. */
	public final static String defaultConversionMethodString = defaultConversionMethod.toString();
	/* Domyslna wartosc konwersji. */
	public final static String defaultConversionValueString = "123";
	
}
