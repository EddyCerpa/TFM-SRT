package tfm;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase que representa un tipo de actividad "Alquiler vacional" que ofrece
 * TripAdvisor
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */

public class AlquilerVacacional extends Ocio {

	/**
	 * Constructora con argumentos
	 * 
	 * @param doc_
	 *            fichero del cual se extraerá la información
	 * @param tIPO
	 *            indica el tipo de ocio (vuelos, restaurantes, hoteles,
	 *            alquilevr vacacional)
	 * @param nOMBRE
	 *            nombre del empresa
	 */
	public AlquilerVacacional(Document doc_, String tIPO, String nOMBRE) {
		doc = doc_;
		tipo = tIPO;
		nombre = nOMBRE;
		usuarios = new ArrayList<Usuario>();
		comentarios = new ArrayList<Comentario>();
	}

	@Override
	protected void scan() {
		Elements informacion = doc
				.select("div.deckB.review_collection div.reviewSelector  ");
		for (Element info : informacion) {
			System.out.println("--------------------------------------");
			Elements informaciónUsuarios = info
					.select("div.vrrmReviewScroller");
			obtenerListaUsuarios(informaciónUsuarios);
			obtenerListaComentarios(info);
		}

	}

	@Override
	protected void obtenerCaracteristica(Elements informaciónUsuarios) {

	}

	@Override
	protected void mostrarCaracteristicas() {
	}

	@Override
	protected void guardarEnCsv(String nombre) {
	}

}
