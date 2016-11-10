package tfm;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase que representa un tipo de actividad "Hotel" que ofrece TripAdvisor
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */

public class Hotel extends Ocio {

	public Hotel(Document doc_, String tIPO, String nOMBRE) {
		doc = doc_;
		tipo = tIPO;
		nombre = nOMBRE;
		usuarios = new ArrayList<Usuario>();
		comentarios = new ArrayList<Comentario>();
	}

	@Override
	protected void scan() {
		Elements informacion = doc
				.select("div.deckB.review_collection div.reviewSelector  ");// reviewSelector
		for (Element info : informacion) {
			Elements informaciónUsuarios = info
					.select("div.review.dyn_full_review.inlineReviewUpdate.provider0");
			// Entra en el caso de que el comentario sea corto (menos de 100
			// caracteres)
			if (informaciónUsuarios.toString().equalsIgnoreCase(""))
				informaciónUsuarios = info
						.select("div.review.basic_review.inlineReviewUpdate.provider0");
			obtenerListaUsuarios(informaciónUsuarios);
			obtenerListaComentarios(info);
		}
	}

	@Override
	protected void mostrarCaracteristicas() {
		System.err
				.println("Falta implementar mostrar las caracteristicas del holtel");
	}

	@Override
	protected void obtenerCaracteristica(Elements informaciónUsuarios) {
	}

	@Override
	protected void guardarEnCsv(String nombre) {
	}

}
