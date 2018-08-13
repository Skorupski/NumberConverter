package eu.scattering.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import eu.scattering.core.NumberConverter;
import eu.scattering.core.WebTransferElement;
import eu.scattering.core.conversion.ConHexadecimal;
import eu.scattering.core.conversion.ConMethod;
import eu.scattering.core.conversion.ConRoman;

/**
 * Klasa opisujaca dzialanie serwera.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
@Path("/conversion")
public class Server {
	
	/**
	 * Obsluga komenty POST.
	 * 
	 * @param input Dane wejsciowe zawierajace sposob konwersji i wartosc.
	 * @return Odpowiedz serwera.
	 */
	@POST
	@Consumes("application/json")
    public Response getConversion(WebTransferElement input) {
		
		/* Metoda konwersji. */
		ConMethod method = null;
		
		/* Wybor metody konwersji. */
		switch (input.getResultType()) {
		case "roman" :
			method = new ConRoman();
			break;
		case "hexadecimal" :
			method = new ConHexadecimal();
			break;
		default :
			/* Kazda uwzgledniona odpowiedz serwera uznajemy za poprawna. */
			return Response.status(201).entity("Server: There is no such method").build();	
		}
		
		/* Wiadomosc wyjsciowa. */
		String result = "EMPTY";
		try {
			result = NumberConverter.convertArabicNumerals(input.getResultValue(), method);
		} catch (Exception e) {
			return Response.status(201).entity("Server: This value cannot be converted").build();
		} 
		
		/* Przeslanie wartosci lub komunikatu bledu. */
		return Response.status(201).entity(result).build();
    }
	
	@Override
	public String toString() {
		return "server class";
	}
}
