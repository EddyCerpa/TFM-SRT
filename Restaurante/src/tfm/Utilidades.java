package tfm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Clase que implementa métodos útiles que serán/son necesarios para realizar
 * determinados procesos
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */
public class Utilidades {

	static String NOMBRE;
	static String TIPO;

	/*
	 * Superb Apartment Center of the City - Alquileres vacacionales en Madrid -
	 * TripAdvisor Hotel Orfila (Madrid, España): hotel opiniones y fotos -
	 * TripAdvisor DSTAGE, Madrid - Chueca - Fotos, Número de Teléfono y
	 * Restaurante Opiniones - TripAdvisor Vuelos y opiniones sobre Iberia -
	 * TripAdvisor Museo del Prado (Madrid) - los mejores consejos antes de
	 * salir - TripAdvisor
	 */

	/**
	 * Método que obtienen el nombre y el tipo de ocio sobre el fichero que se
	 * esta analizando
	 * 
	 * @param doc
	 *            documento a analizar
	 */
	public static void obtenerNombreYTipo(Document doc) {

		String titulo = doc.title().toString();
		int resultado = titulo.indexOf("Restaurante");
		if (resultado != -1) {
			TIPO = "Restaurante";
			NOMBRE = doc.select("div.heading_name_wrapper h1#HEADING").text();
		} else if (titulo.indexOf("Hotel") != -1) {
			TIPO = "Hotel";
			NOMBRE = doc.select("div.heading_name_wrapper h1#HEADING").text();
		} else if (titulo.indexOf("Vuelos") != -1) {
			TIPO = "Vuelos";
			NOMBRE = doc.select("div.heading_height").text();
		} else if (titulo.indexOf("Alquileres") != -1) {
			TIPO = "Alquileres";
			NOMBRE = doc.select("h1#HEADING.vrPgHdr").text();
		} else if (titulo.indexOf("salir") != -1) {
			TIPO = "Salir";
			NOMBRE = doc.select("h1#HEADING.heading_name").text();
		}
		System.out.println(TIPO + "*********** " + NOMBRE);
	}

	/**
	 * Método encargado de extraer un numero de una cadena de caracteres
	 * cualesquiera
	 * 
	 * @param cadena
	 *            cadena a analizar
	 * @return numero encontrado
	 */
	public static int extraerNumero(String cadena) {
		int salida = 0;
		int largo = 0;
		largo = cadena.length();
		String numero = "";
		int i;
		for (i = 0; i < largo; i++) {
			if (Character.isDigit(cadena.charAt(i)))
				numero = numero + cadena.charAt(i);
		}
		try {
			salida = Integer.valueOf(numero);
		} catch (NumberFormatException e) {
			System.err.println("numero no detectado");
		}
		return salida;

	}

	/**
	 * Método encargado de convertir una lista de tuplas (tipo, valor) en una
	 * cadena de caracteres
	 * 
	 * @param arrayList
	 *            lista que se quiere anañizar
	 * @return cadena de caracteres que se quiere analizar
	 */
	public static String toStringLista(ArrayList<Valoracion> arrayList) {
		if (arrayList.isEmpty())
			return "";
		StringBuilder cadena = new StringBuilder("");
		for (Valoracion valoracion : arrayList) {
			cadena.append(valoracion.toString() + ";");
		}
		System.out.println(cadena.substring(0, cadena.length() - 1));
		return cadena.substring(0, cadena.length() - 1);

	}

}
