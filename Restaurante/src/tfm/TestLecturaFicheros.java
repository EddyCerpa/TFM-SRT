package tfm;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Clase encargada de realizar un test de lectura sobre los diferentes tipos de
 * ficheros
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */
public class TestLecturaFicheros {
	public static void main(String[] args) {
		// String sDirectorio = "M:\\PruebaLetura";
		// String sDirectorio = "G:\\MIX";
		String sDirectorio = "M:\\TEST";
		Document doc = null;

		File f = new File(sDirectorio);
		if (f.exists()) {
		} else {
			System.out.println("El directorio no existe");
		}
		File[] ficheros = f.listFiles();

		for (int x = 0; x < ficheros.length; x++) {
			System.out.println(ficheros[x].getName());
			try {
				doc = Jsoup.parse(ficheros[x], null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Utilidades.obtenerNombreYTipo(doc);

			Ocio ocio = null;
			if (Utilidades.TIPO.toUpperCase().indexOf("ALQUILER") > -1)
				ocio = new AlquilerVacacional(doc, Utilidades.TIPO,
						Utilidades.NOMBRE);
			else if (Utilidades.TIPO.toUpperCase().indexOf("HOTEL") > -1) 
				ocio = new Hotel(doc, Utilidades.TIPO, Utilidades.NOMBRE);
			else if (Utilidades.TIPO.toUpperCase().indexOf("RESTAURANTE") > -1) //
				ocio = new Restaurante(doc, Utilidades.TIPO, Utilidades.NOMBRE);
			else if (Utilidades.TIPO.toUpperCase().indexOf("VUELO") > -1)
				ocio = new Vuelo(doc, Utilidades.TIPO, Utilidades.NOMBRE);
			else
				ocio = new QueHacer(doc, Utilidades.TIPO, Utilidades.NOMBRE);
			ocio.scan();
			ocio.mostrarListaUsuarios();
			ocio.mostrarListaComentarios();
			ocio.mostrarCaracteristicas();
			ocio.guardarEnCsv(ficheros[x].getName());

		}

	}
}
