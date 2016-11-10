package tfm;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase que representa un tipo de actividad "Vuelo" que ofrece TripAdvisor
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */
public class Vuelo extends Ocio {

	public Vuelo(Document doc_, String tIPO, String nOMBRE) {
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
					.select("div.review.basic_review.inlineReviewUpdate.provider0");
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
