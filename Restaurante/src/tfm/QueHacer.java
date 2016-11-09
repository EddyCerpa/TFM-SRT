package tfm;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QueHacer extends Ocio{

	
	
	public QueHacer(Document doc_, String tIPO, String nOMBRE) {
		doc = doc_;
		tipo = tIPO;
		nombre = nOMBRE;
		
		usuarios = new ArrayList<Usuario>();
		comentarios = new ArrayList<Comentario>();
	}

	@Override
	protected void scan() {
		Elements informacion = doc.select(/*"div.extended.provider0"*/"div.deckB.review_collection div.reviewSelector  ");//reviewSelector

		for (Element info: informacion){

			Elements informaciónUsuarios = info.select("div.review.dyn_full_review.inlineReviewUpdate.provider0");
			
			if (informaciónUsuarios.toString().equalsIgnoreCase(""))//Es ncesario para captar la información que mo necesita que le des a "mas"
				informaciónUsuarios =info.select("div.review.basic_review.inlineReviewUpdate.provider0");
			
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
