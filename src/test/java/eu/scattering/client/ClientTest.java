package eu.scattering.client;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.scattering.core.NumberConverter;
import eu.scattering.core.conversion.ConHexadecimal;
import eu.scattering.core.conversion.ConRoman;

/**
 * Metody testowania klienta.
 * Zakladamy, ze serwer dziala poprawnie (test podstawowych funkcji jest zawarty w innym pliku).
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class ClientTest {
	
	/**
	 * Sprawdzenie poprawnosci URI.
	 */
	@Test
	public void clientInvalidURI() {
		assertEquals("Server should not accept this URI", "Client: The selected URI is invalid", Client.getConversionServerValue("'%E0%A4%A'", "hexadecimal", "123"));
	}
	
	/**
	 * Sprawdzenie komunikacji.
	 */
	@Test
	public void clientRandomValueTest() {
		String valueRoman = NumberConverter.convertArabicNumerals("123", new ConRoman());
		String valueHexadecimal = NumberConverter.convertArabicNumerals("123", new ConHexadecimal());
		assertEquals("123 should be " + valueRoman, valueRoman, Client.getConversionServerValue("roman", "123"));
		assertEquals("123 should be " + valueHexadecimal, valueHexadecimal, Client.getConversionServerValue("hexadecimal", "123"));
	}
	
	/**
	 * Sprawdzenie reakcji na bledne dane.
	 */
	@Test
	public void clientInvalidValueTest() {
		assertEquals("Server should not accept this request", "Server: There is no such method", Client.getConversionServerValue("abc", "123"));
		assertEquals("Server should not accept this request", "Server: There is no such method", Client.getConversionServerValue(null, "123"));
		assertEquals("Server should not accept this request", "Server: This value cannot be converted", Client.getConversionServerValue("roman", "abc"));
		assertEquals("Server should not accept this request", "Server: This value cannot be converted", Client.getConversionServerValue("roman", null));
		assertEquals("Server should not accept this request", "Server: This value cannot be converted", Client.getConversionServerValue("hexadecimal", "abc"));
		assertEquals("Server should not accept this request", "Server: This value cannot be converted", Client.getConversionServerValue("hexadecimal", null));
	}

}
