package eu.scattering.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.scattering.core.GlobalVariables;
import eu.scattering.core.WebTransferElement;

/**
 * Klasa zawierajaca metody zwiazane z polaczeniem.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class Client {

	/**
	 * Uproszczona metoda wysylajaca zapytanie.
	 * 
	 * @param conversionValue Wartosc przekazywana do konwersji.
	 * @return Wartosc wynikowa lub odpowiedz serwera.
	 */
	public static String getConversionServerValue(String conversionValue) {
		return getConversionServerValue(GlobalVariables.defaultConversionServerAddress,
				GlobalVariables.defaultConversionMethodString, conversionValue);
	}
	
	/**
	 * Uproszczona metoda wysylajaca zapytanie.
	 * 
	 * @param conversionType Typ konwersji (np. hexadecimal, roman).
	 * @param conversionValue Wartosc przekazywana do konwersji.
	 * @return Wartosc wynikowa lub odpowiedz serwera.
	 */
	public static String getConversionServerValue(String conversionType, String conversionValue) {
		return getConversionServerValue(GlobalVariables.defaultConversionServerAddress,
				conversionType, conversionValue);
	}
	
	/**
	 * Metoda wysylajaca zapytanie.
	 * Nie generuje ona wyjatkow, poniewaz jest duzo okazji do bledu, ktore mozna poprawic wysylajac zapytanie ponownie.
	 * Wartosci wejsciowe nie sa sprawdzane po stronie klienta. Powoduje to, ze oba elementy sa w miare niezalezne.
	 * Mozna zaktualizowac serwer (np. dodac nowe metody konwersji) bez potrzeby modyfikacji klienta.
	 * 
	 * @param conversionServerAddress Adres serwera z ktorym sie laczymy.
	 * @param conversionType Typ konwersji (np. hexadecimal, roman).
	 * @param conversionValue Wartosc przekazywana do konwersji.
	 * @return Wartosc wynikowa lub odpowiedz serwera.
	 */
	public static String getConversionServerValue(String conversionServerAddress, String conversionType, String conversionValue) {
		
		/* Odpowiedz otrzymana z serwera. */
		StringBuilder conversionServerResponseBuilder = new StringBuilder();
		/* Pojedyncza linia wiadomosci. */
		String conversionServerResponse;
		/* Warunek wykonania dalszych operacji. */
		boolean proceed = true;
		
		/* Polaczenie z serwerem. */
		HttpURLConnection conversionServerConnection = null;
		/* Utworzone strumienie. */
		BufferedReader conversionServerBufferedReader = null;
		OutputStream conversionServerOutputStream = null;
		
		try {
			/* Nawiazanie polaczenia. */
			URL conversionServerURL = new URL(conversionServerAddress);
			conversionServerConnection = (HttpURLConnection) conversionServerURL.openConnection();

			/* Ustawienie parametrow polaczenia. */
			conversionServerConnection.setDoOutput(true);
			conversionServerConnection.setRequestMethod("POST");
			conversionServerConnection.setRequestProperty("Content-Type", "application/json");
			
			/* Przygotowanie danych do wyslania. */
			String conversionString = new WebTransferElement(conversionValue, conversionType).toString();
			
			/* Wyslanie danych. */
			conversionServerOutputStream = conversionServerConnection.getOutputStream();
			conversionServerOutputStream.write(conversionString.getBytes());
			conversionServerOutputStream.flush();
			
			/* Sprawdzenie poprawnosci komunikacji. */
			if (conversionServerConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				conversionServerResponseBuilder.append("Server: Something went wrong - Error code: " 
						+ conversionServerConnection.getResponseCode());
				/* Zatrzymanie dalszych operacji bloku. */
				proceed = false;
			}

			/* Odebranie danych z serwera. */
			if (proceed) {
				conversionServerBufferedReader = new BufferedReader(new InputStreamReader(
						(conversionServerConnection.getInputStream())));	
				while ((conversionServerResponse = conversionServerBufferedReader.readLine()) != null) {
					conversionServerResponseBuilder.append(conversionServerResponse);
				}
			}
	
			/* Obsluga wyjatkow. */
		} catch (MalformedURLException e) {
			return "Client: The selected URI is invalid";
		} catch (IOException e) {
			return "Client: The requested I/O operation failed";
		} catch(Exception e) {
			return "Client: Something unexpected happened";
		} finally {
			/* Zamkniecie strumieni. */
			try {
				if (conversionServerOutputStream != null) {
					conversionServerOutputStream.close();
				}
				if (conversionServerBufferedReader != null) {
					conversionServerBufferedReader.close();
				}
			} catch (IOException e) {}	
			/* Ostateczne zamkniecie polaczenia. */
			if (conversionServerConnection != null) {
				conversionServerConnection.disconnect();
			}
		}
		
		/* Zwracanie odpowiedzi. */
		return conversionServerResponseBuilder.toString();
	}

}