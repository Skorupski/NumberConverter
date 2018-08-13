package eu.scattering.core.conversion;

/**
 * Klasa zawierajaca metode konwersji do systemu szesnastkowego.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class ConHexadecimal implements ConMethod {

	/**
	 * Konwersja z systemu dziesietnego do szesnastkowego.
	 */
	@Override
	public String process(int data) throws NumberFormatException {
		/* To jedyna modyfikacja dodana w kodzie. Zakladamy, ze wartosci nie sa ujemne */
		if (data < 0) {
			throw new NumberFormatException("The input parameter must be greater or equal zero");
		}
		/* Nie ma potrzeby pisania metody skoro jej implementacja juz istnieje.
		 * Zostala ona sprawdzona przez wielu programistow. Stworzenie czegos wlasnego 
		 * zwiekszy prawdopodobienstwo wystapienia bledow.*/
		return Integer.toHexString(data);
	}
	
	@Override
	public String toString() {
		return "hexadecimal";
	}

}
