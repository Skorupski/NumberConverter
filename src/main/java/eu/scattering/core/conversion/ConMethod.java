package eu.scattering.core.conversion;

/**
 * Interfejs funkcyjny zawierajacy metode konwersji danych. 
 * Pozwala na zastosowanie wyrazen lambda, chociaz nie zostalo to wykorzystane w projekcie.
 * Budowa zblizony jest do "Function", chcialem jednak stworzyc cos bardziej czytelnego dla projektu.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
@FunctionalInterface
public interface ConMethod {
	
	/**
	 * Metoda konwersji danych.
	 * 
	 * @param data Liczba w systemie dziesietnym. Zakladamy, ze jest to integer (na podstawie wytycznych projektu).
	 * @return Wartosc po konwersji. Jest to lancuch znakow poniewaz rozne metody moga dzialac w inny sposob.
	 * @throws NumberFormatException Nawet jezeli podana wartosc jest poprawna moze nie byc uwzgledniona w niektorych
	 * systemach. Przykladowo, zero nie ma swojego odpowiednika w liczbach rzymskich.
	 */
	String process(int data) throws NumberFormatException;
}
