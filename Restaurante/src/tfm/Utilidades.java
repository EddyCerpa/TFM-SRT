package tfm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Utilidades {

	static String NOMBRE;
	static String TIPO;
	
	
public static void main(String[] args) {
		
		//String sDirectorio = "M:\\PruebaLetura";
		//String sDirectorio = "G:\\MIX";
		String sDirectorio = "M:\\TEST";
		Document doc = null;
		
		File f = new File(sDirectorio);
		if (f.exists()){ }
			else { System.out.println("El directorio no existe");}
		File[] ficheros = f.listFiles();
		
		for (int x=0;x<ficheros.length;x++){
		  System.out.println(ficheros[x].getName());
		  try {
		
			  
			  doc = Jsoup.parse(ficheros[x],null);
		
		
		
//		try {
//			doc = Jsoup.parse(new File(/*"E:\\Restaurantes\\index.html"*/"M:\\DatosTripAdvisor\\data\\20161024195727\\index.html"), null); //restaurante
//			doc = Jsoup.parse(new File(/*"E:\\Restaurantes\\index.html"*/"M:\\DatosTripAdvisor\\data\\20160907162608\\index.html"), null);//hotel
//			doc = Jsoup.parse(new File(/*"E:\\Restaurantes\\index.html"*/"M:\\DatosTripAdvisor\\data\\20160908170657\\index.html"), null);//vuelos 
//			doc = Jsoup.parse(new File(/*"E:\\Restaurantes\\index.html"*/"M:\\DatosTripAdvisor\\data\\20160908170943\\index.html"), null);//AlquilerVacacional
//			doc = Jsoup.parse(new File(/*"E:\\Restaurantes\\index.html"*/"M:\\DatosTripAdvisor\\data\\20160910182144\\index.html"), null);//Cosas que hacer

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		  Utilidades.obtenerNombreYTipo(doc);
		
		  Ocio ocio = null;
		if (TIPO.toUpperCase().indexOf("ALQUILER") > -1)
			//obtenerDatosBusquedaAlquilerVacacional(doc);
			 ocio = new AlquilerVacacional(doc, TIPO, NOMBRE);
		else if (TIPO.toUpperCase().indexOf("HOTEL") > -1) //
			//obtenerDatosBusquedaHotel(doc);
			ocio = new Hotel(doc, TIPO, NOMBRE);
		else if (TIPO.toUpperCase().indexOf("RESTAURANTE") > -1) //
			//obtenerDatosBusquedaRestaurante(doc);
			ocio = new Restaurante(doc, TIPO, NOMBRE);
		else if (TIPO.toUpperCase().indexOf("VUELO") > -1)//
			//obtenerDatosBusquedaVuelo(doc);
			ocio = new Vuelo(doc, TIPO, NOMBRE);
		else 
			//obtenerDatosBusquedaCosasQueHacer(doc); //
			ocio = new QueHacer(doc, TIPO, NOMBRE);
				
		ocio.scan();
		ocio.mostrarListaUsuarios();
		ocio.mostrarListaComentarios();
		ocio.mostrarCaracteristicas();
		
		ocio.guardarEnCsv(ficheros[x].getName());	
		
	} 
		
	}


/*
Superb Apartment Center of the City - Alquileres vacacionales en Madrid - TripAdvisor
Hotel Orfila (Madrid, España): hotel opiniones y fotos - TripAdvisor
DSTAGE, Madrid - Chueca - Fotos, Número de Teléfono y Restaurante Opiniones - TripAdvisor
Vuelos y opiniones sobre Iberia - TripAdvisor
Museo del Prado (Madrid) - los mejores consejos antes de salir - TripAdvisor
*/

	private static void obtenerNombreYTipo(Document doc) {

		String titulo = doc.title().toString();
		
		int resultado = titulo.indexOf("Restaurante");
		
		if (resultado != -1){
			TIPO = "Restaurante";
			NOMBRE = doc.select("div.heading_name_wrapper h1#HEADING").text();
		}
		else if (titulo.indexOf("Hotel") != -1){
			TIPO = "Hotel";
			NOMBRE = doc.select("div.heading_name_wrapper h1#HEADING").text();
		}
		else if (titulo.indexOf("Vuelos") != -1){
			TIPO = "Vuelos";
			NOMBRE = doc.select("div.heading_height").text();
		}
		else if (titulo.indexOf("Alquileres") != -1){
			TIPO = "Alquileres";	
			NOMBRE = doc.select("h1#HEADING.vrPgHdr").text();
		}
		else if (titulo.indexOf("salir") != -1){
			TIPO = "Salir";	
			NOMBRE = doc.select("h1#HEADING.heading_name").text();
		}
		
		System.out.println(TIPO + "*********** " + NOMBRE);
		
	}
	
	
	
	public static int extraerNumero(String cadena) {
		int salida =0;
		int largo=0;
		largo=cadena.length();
		String numero="";
		int i;
		for(i=0; i <largo ; i++){ 
			if (Character.isDigit(cadena.charAt(i))) 
				numero=numero+cadena.charAt(i);
		} 
		try {
		salida = Integer.valueOf(numero);
		} catch (NumberFormatException e){
			System.err.println( "numero no detectado");
		}
		return salida;
	
	}
	
	
	public static String toStringLista(ArrayList<Valoracion> arrayList) {
		if (arrayList.isEmpty())
			return "";
		StringBuilder cadena = new StringBuilder("");
		for (Valoracion valoracion : arrayList) {
			cadena.append(valoracion.toString()+";");
		}
		System.out.println(cadena.substring(0, cadena.length()-1));
		return cadena.substring(0, cadena.length()-1);
		
	}
	
}
