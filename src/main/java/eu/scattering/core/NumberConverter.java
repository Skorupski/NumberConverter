package eu.scattering.core;

import eu.scattering.core.conversion.ConMethod;

/**
 * Klasa zawierajaca metody konwersji liczby w systemie dziesietnym na alternatywne.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class NumberConverter {
	
	/**
	 * Metoda konwersji liczby zapisanej w systemie dziesietnym na alternatywny.
	 * 
	 * @param conversionData Wartosc, na ktorrej chcemy dokonac konwersji.
	 * @return Otrzymana wartosc.
	 * @throws NullPointerException Jezeli jeden z argumentow bedzie wynosil null.
	 * @throws NumberFormatException Jezeli konwersja nie bedzie mozliwa.
	 */
	public static String convertArabicNumerals(String conversionData) 
		throws NullPointerException, NumberFormatException {
		
		/* Przekazanie argumentow do glownej metody. */
		return convertArabicNumerals(conversionData, GlobalVariables.defaultConversionMethod);
	}
	
	/**
	 * Metoda konwersji liczby zapisanej w systemie dziesietnym na alternatywny.
	 * 
	 * @param conversionData Wartosc, na ktorej chcemy dokonac konwersji.
	 * @param conversionMethod Wybor metody konwersji.
	 * @return Otrzymana wartosc.
	 * @throws NullPointerException Jezeli jeden z argumentow bedzie wynosil null.
	 * @throws NumberFormatException Jezeli konwersja nie bedzie mozliwa.
	 */
	public static String convertArabicNumerals(String conversionData, ConMethod conversionMethod) 
		throws NullPointerException, NumberFormatException {
		
		/* Oba wyjatki sa typu unchecked, wiec teoretycznie nie musimy ich uwzgledniac w nazwie metody. 
		 * Mimo to lepiej wiedziec czego sie spodziewac. */
		if (conversionData == null) {
			throw new NullPointerException("The input parameter is undefined, i.e. data == null");
		}
		if (conversionMethod == null) {
			throw new NullPointerException("The conversion method is undefined, i.e. method == null");
		}
		
		/* Tworzymy nowa zmienna, bedzie ona przechowywala wartosc wejsciowa po konwersji do systemu dziesietnego */
		int val;
		
		/* Dokonujemy konwersji. Jak sie nie uda wychwytujemy wyjatek i generujemy nowy (chociaz jest on tego samego typu). */
		try  {
			val = Integer.parseInt(conversionData);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("The input parameter cannot be converted to a numeral");
		}
		
		/* Wykonanie odpowiedniej metody */
		return conversionMethod.process(val);
	}		

}
