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

			Elements informaci�nUsuarios = info.select("div.review.dyn_full_review.inlineReviewUpdate.provider0");
			
			if (informaci�nUsuarios.toString().equalsIgnoreCase(""))//Es ncesario para captar la informaci�n que mo necesita que le des a "mas"
				informaci�nUsuarios =info.select("div.review.basic_review.inlineReviewUpdate.provider0");
			
			obtenerListaUsuarios(informaci�nUsuarios);
			obtenerListaComentarios(info);
		}
		
	}

	@Override
	protected void obtenerCaracteristica(Elements informaci�nUsuarios) {
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
