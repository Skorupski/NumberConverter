package eu.scattering.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.scattering.core.NumberConverter;
import eu.scattering.core.conversion.ConHexadecimal;
import eu.scattering.core.conversion.ConRoman;

/**
 * Metody testowania podstawowych funkcji konwartera.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 *
 */
public class NumberConverterTest {

//-----------------------------------------------------------------------
	
	/* Lista zawierajace elementy testowe */
	public static List<String> elementTestList = new ArrayList<>();
	
//-----------------------------------------------------------------------
	
	/**
	 * Metoda wykonywana przed testami. W jezyku R wygenerowany zostal plik z wszystkimi mozliwymi
	 * liczbami rzymskimi. Ponadto, dla tych samych wartosci, podana zostala wartosc w systemie hexadecymalnym.
	 * x <- 1:3899; y <- as.roman(x); z <- as.hexmode(x); 
	 * Teoretycznie mozna wykonac test parametryczny ale ten sposob wydaje mi sie bardziej przejrzysty.
	 */
	@BeforeClass
	public static void exectueBeforeAll() {
		/* Wygenerowany plik CSV znajduje sie pod dana sciezka.*/
		Path romanTest = Paths.get("./src/test/resources/converterTest.csv");
		
		/* Jezeli plik nie istnieje, nie mozemy przeprowadzic wszystkich testow. 
		 * Generujemy najprostszy wyjatek typu RuntimeException (nie musimy go obslugiwac).*/
		if (!Files.exists(romanTest)) {
			throw new RuntimeException("The test file does not exist");
		}
		
		/* Jezeli nie mamy dostepu do pliku rowniez powinnismy przerwac testy. */
		try {
			elementTestList = Files.readAllLines(romanTest);
		} catch (IOException e) {
			throw new RuntimeException("The file 'converterTest.csv' cannot be accessed");
		}
	}
	
	/**
	 * Metoda wykonywana po wszystkich testach. Nie jest moze krytyczna ale usuniecie referencji nie zaszkodzi. 
	 */
	@AfterClass
	public static void exectueAfterAll() {
		elementTestList = null;
	}

//-----------------------------------------------------------------------	

	/**
	 * Test dla wszystkich rodzajow konwersji. Jezeli podamy dwa razy null
	 * powinnismy wygenerowac wyjatek.
	 */
	@Test (expected = NullPointerException.class)
	public void converterNullValueAllTest() {
		NumberConverter.convertArabicNumerals(null, null);
	}
	
	/**
	 * Test dla wszystkich rodzajow konwersji. Jezeli podamy null jako pierwszy argument
	 * powinnismy wygenerowac wyjatek.
	 */
	@Test (expected = NullPointerException.class)
	public void converterNullValue1Test() {
		NumberConverter.convertArabicNumerals(null, new ConRoman());
	}
	
	/**
	 * Test dla wszystkich rodzajow konwersji. Jezeli podamy null jako drugi argument
	 * powinnismy wygenerowac wyjatek.
	 */
	@Test (expected = NullPointerException.class)
	public void converterNullValue2Test() {
		NumberConverter.convertArabicNumerals("123", null);
	}
	
	/**
	 * Test dla wszystkich rodzajow konwersji. Jezeli zla wartosc do konwersji
	 * powinnismy wygenerowac wyjatek.
	 */
	@Test (expected = NumberFormatException.class)
	public void converterInvalidValueTest() {
		NumberConverter.convertArabicNumerals("abc", new ConRoman());
	}
	
//-----------------------------------------------------------------------	
	
	/**
	 * Test dla wartosci rzymskich. Jezeli przejdzie oznacza to, ze wszystkie wartosci
	 * sa poprawne i w zadnym mozliwym przypadku metoda nie zwroci blednego wyniku. Oczywiscie
	 * jest mozliwe, ze Java i R (wbudowana metoda) myla sie w tych samych momentach ale jest to bardzo malo
	 * prawdopodobne. Timeout 5s jest praktycznie nieosiagalny. Jezeli test bedzie trwal dluzej znaczy,
	 * ze cos namieszalismy w kodzie.
	 */
	@Test(timeout = 5000)
	public void converterDecimalToRomanValuesTest() {
		/* Zmienna pomocnicza. */
		String[] var;		
		/* Dla kazdej mozliwosci. */
		for (String line : elementTestList) {
			/* Czyscimy lancuch znakow. */
			var = line.trim().replace("\"", "").split(",");
			/* Przeprowadzamy test. */
			assertEquals(var[0] + " should be " + var[1], var[1], NumberConverter.convertArabicNumerals(var[0]));
		}
	}
	
	/**
	 * W przypadku liczb rzymskich nie ma wartosci mniejszych lub rownych zero.
	 */
	@Test (expected = NumberFormatException.class)
	public void converterDecimalToRomanValueBelowThresholdTest() {
		NumberConverter.convertArabicNumerals("0", new ConRoman());
	}
	
	/**
	 * W przypadku liczb rzymskich nie ma wartosci wiekszych niz 3899.
	 * Takie samo ograniczenie jest w specyfikacji R.
	 */
	@Test (expected = NumberFormatException.class)
	public void converterDecimalToRomanValueAboveThresholdTest() {
		NumberConverter.convertArabicNumerals("3900", new ConRoman());
	}
	
//-----------------------------------------------------------------------	
	
	/**
	 * Test dla wartosci hexadecymalnych. Nie jest juz tak wiarygodny jak poprzednio poniewaz nie zawiera
	 * wszystkich wartosci (nie jest to mozliwe a raczej sensowne). Prawie 4000 testow powinno wystarczyc.
	 */
	@Test(timeout = 5000)
	public void converterDecimalToHexadecimalValuesTest() {
		/* Zmienna pomocnicza. */
		String[] var;		
		/* Dla kazdej mozliwosci. */
		for (String line : elementTestList) {
			/* Czyscimy lancuch znakow. */
			var = line.trim().replace("\"", "").split(",");
			/* Przeprowadzamy test. */
			assertEquals(var[0] + " should be " + var[2], var[2], NumberConverter.convertArabicNumerals(var[0], new ConHexadecimal()));
		}
	}
	
	/**
	 * W przypadku liczb hexadecymalnych nie ma wartosci ujemnych. Oczywiscie tak jest tylko w tym programie
	 * (mozna usunac to ograniczenie usuwajac generowanie wyjatku w klasie 'ConHexadecimal'.
	 */
	@Test (expected = NumberFormatException.class)
	public void converterDecimalToHexadecimalValueBelowThresholdTest() {
		NumberConverter.convertArabicNumerals("-1", new ConHexadecimal());
	}

}
