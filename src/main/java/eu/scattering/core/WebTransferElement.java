package eu.scattering.core;

/**
 * Klasa zawierajaca dane przesylane na serwer.
 * 
 * @author	Krzysztof Skorupski
 * @version	1.0
 */
public class WebTransferElement {

	/* Wartosc przekazywana do konwersji */
	private String resultValue = GlobalVariables.defaultConversionValueString;
	/* Rodzaj konwersji */
	private String resultType = GlobalVariables.defaultConversionMethodString;
	
//--------------------------------------------------------------------------	
	
	public WebTransferElement() {}
	
	public WebTransferElement(String resultValue, String resultType) {
		this.resultValue = resultValue;
		this.resultType = resultType;
	}

//--------------------------------------------------------------------------	
	
	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

//--------------------------------------------------------------------------	
	/**
	 * Przedefiniowanie metody.
	 * Konwertowanie danych bezposrednio do formatu JSON.
	 */
	@Override
	public String toString() {
		return "{\"resultValue\":\"" + resultValue + "\",\"resultType\":\"" + resultType + "\"}";
	}

}