package eu.scattering.core.conversion;

/**
 * Klasa zawierajaca metode konwersji na liczby rzymskie.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class ConRoman implements ConMethod {
	
	/* Tablica zawierajaca wszystkie mozliwe kombinacja znakow. Jest na tyle krotka,
	 * ze moze zostaac umieszczona w kodzie (nie trzeba odczytywac z zewnetrznego zrodla).
	 * Zwielokrotnione znaki, np. MMM, mozna pominac ale kod bylby wtedy nieco mniej czytelny.*/
	private static final String[] valRoman = {
			"MMM", "MM", "M", "CM", "D", "CD",
			"CCC", "CC", "C", "XC", "L", "XL",
			"XXX", "XX", "X", "IX", "V", "IV",
			"III", "II", "I"};
	
	/* Wartosc przypisana do kazdego znaku. Obie tablice mozna polaczyc w jedna mape (klucz, wartosc)
	 * ale nie przyspieszyloby to dzialania programu i kod bylby trudniejszy w interpretacji.*/
	private static final int[] valArabic = {
			3000, 2000, 1000, 900, 500, 400,
			300, 200, 100, 90, 50, 40,
			30, 20, 10, 9, 5, 4,
			3, 2, 1};
	
	/**
	 * Konwersja z systemu dziesietnego na liczby rzymskie.
	 */
	@Override
	public String process(int data) throws NumberFormatException {
		/* Wartosc musi zawierac sie w okreslonym przedziale. */
		if (data < 1 || data > 3899) {
			throw new NumberFormatException("The input parameter must be greater than 0 and less than 3900");
		}
		
		/* Kopia argumentu. Nie jest to obowiazkowe, ale nie zabiera to duzo zasobow 
		 * i zawsze mozemy wrocic do oryginalnej wartosci. */
		int val = data;
		/* Tworzenie wartosci wyjsciowej */
		StringBuilder buildOutput = new StringBuilder();
		
		/* Petla dla kazdego znaku umieszczonego w tablicy */
		for (int i = 0; i < valArabic.length; i++) {
			/* Jezeli wartosc calkowita z dzielenia jest wieksza niz zero */
			if (val / valArabic[i] != 0) {
				/* Dodajemy powiazany znak */
				buildOutput.append(valRoman[i]);
				/* Modyfikujemy badana wartosc */
				val -= valArabic[i];
			}
		}
		
		return buildOutput.toString();
	}
	
	@Override
	public String toString() {
		return "roman";
	}
	
}
