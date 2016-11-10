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
		// TODO Auto-generated constructor stub
		doc = doc_;
		tipo = tIPO;
		nombre = nOMBRE;
		
		usuarios = new ArrayList<Usuario>();
		comentarios = new ArrayList<Comentario>();
	}

	@Override
	protected void scan() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
		Elements informacion = doc.select(/*"div.extended.provider0"*/"div.deckB.review_collection div.reviewSelector  ");//reviewSelector
		int i=0;
		for (Element info: informacion){
			
			System.out.println("--------------------------------------");
			Elements informaciónUsuarios = info.select("div.review.basic_review.inlineReviewUpdate.provider0");
			
			obtenerListaUsuarios(informaciónUsuarios);
			obtenerListaComentarios(info);
			
			i++;
		}
		
		//Elements informacionRestaurante = doc.select("div.dynamicBottom div.details_tab div.table_section div.row");
		
		System.out.println("comentarios:" + i);
		//Si se quiere se puede poner la información adicional
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
