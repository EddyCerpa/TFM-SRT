package tfm;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase que representa un tipo de actividad "Que hacer" que ofrece TripAdvisor
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */

public class QueHacer extends Ocio {

	public QueHacer(Document doc_, String tIPO, String nOMBRE) {
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
			Elements informaciónUsuarios = info
					.select("div.review.dyn_full_review.inlineReviewUpdate.provider0");
			// En el casao de que la información se muy extensa
			if (informaciónUsuarios.toString().equalsIgnoreCase(""))
				informaciónUsuarios = info
						.select("div.review.basic_review.inlineReviewUpdate.provider0");
			obtenerListaUsuarios(informaciónUsuarios);
			obtenerListaComentarios(info);
		}

	}

	@Override
	protected void obtenerCaracteristica(Elements informaciónUsuarios) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mostrarCaracteristicas() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void guardarEnCsv(String nombre) {
		// TODO Auto-generated method stub
	}

}
