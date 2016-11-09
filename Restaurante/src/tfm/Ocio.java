package tfm;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public abstract class Ocio {
	protected String tipo;
	protected String nombre;
	
	protected Document doc;
	protected ArrayList<Usuario> usuarios;
	protected ArrayList<Comentario> comentarios;
	
	protected abstract void scan();
	
	//protected abstract void obtenerListaUsuarios (Element informaciónUsuarios);
	
	//protected abstract void obtenerListaComentarios(Element informaciónUsuarios);
	
	protected abstract void obtenerCaracteristica(Elements informaciónUsuarios);
	
//	protected abstract void mostrarListaUsuarios();
	
//	protected abstract void mostrarListaComentarios();
	
	protected abstract void mostrarCaracteristicas();
	
	protected abstract void guardarEnCsv(String nombre);
	
	
	
	protected void obtenerListaUsuarios (Elements informaciónUsuarios) {

		// TODO Auto-generated method stub
		String nombreUsuario = "";
		String loc = "";
		int nivel = 0;
		int opinionTotal = 0;
		int opinionRestaurante = 0;
		int votoUtil = 0;
		
//		System.out.println("--------------------------------------");
//		Elements informaciónUsuarios = informaciónUsuarios_.select("div.review.dyn_full_review.inlineReviewUpdate.provider0");
//		
//		
//		
//		
//		
//		if (informaciónUsuarios.toString().equalsIgnoreCase(""))//Es ncesario para captar la información que mo necesita que le des a "mas"
//			informaciónUsuarios =informaciónUsuarios_.select("div.review.basic_review.inlineReviewUpdate.provider0");
//
//		
		
		//***//
		
		Elements nombre = informaciónUsuarios.select("div.username.mo");
		if (nombre.size() != 0)
			//System.out.println(nombre.text());
			nombreUsuario = nombre.text();
//		else
//			System.out.println("-");
			
		
		Elements localizacion = informaciónUsuarios.select("div.location");
		if (!localizacion.text().equalsIgnoreCase(""))
			//System.out.println("-");
			loc = localizacion.text();
//		else
//			System.out.println(localización.text());
		

		
		Elements nivelUsuario = informaciónUsuarios.select("div.levelBadge.badge span");	
		if (nivelUsuario.size() != 0){				
			Element img = nivelUsuario.select("img").first();
			String contenido = img.attr("src");
			//System.out.println(extraerNumero(contenido));
			nivel = Utilidades.extraerNumero(contenido);
		}
//		else{
//			System.out.println("0");
//		}
		
		Elements opinionesTotales = informaciónUsuarios.select("div.reviewerBadge.badge span.badgeText");
		if (opinionesTotales.size() !=0){
			//System.out.println(extraerNumero(opinionesTotales.text()));
			opinionTotal = Utilidades.extraerNumero(opinionesTotales.text());
		}
//		else {
//			System.out.println("0");
//		}
		
		Elements opinionesRestaurantes =informaciónUsuarios.select("div.contributionReviewBadge.badge span.badgeText");
		if (opinionesRestaurantes.size() != 0){
			//System.out.println(extraerNumero(opinionesRestaurantes.text()));
			opinionRestaurante = Utilidades.extraerNumero(opinionesRestaurantes.text());
		}
//		else {
//			System.out.println("0");
//		}

		
		Elements votosUtiles =informaciónUsuarios.select("div.helpfulVotesBadge.badge.no_cpu span.badgeText");
		if (votosUtiles.size() != 0){
			//System.out.println(extraerNumero(votosUtiles.text()));
			votoUtil = 	Utilidades.extraerNumero(votosUtiles.text());
		}
//		else {
//			System.out.println("0");
//		}			
	
		Usuario us = new Usuario(nombreUsuario, loc, nivel, opinionTotal, opinionRestaurante, votoUtil);
		usuarios.add(us);
		
	
		
	}
	
	
	
	
	
	
	protected void obtenerListaComentarios(Element informaciónComentario_) {
		// TODO Auto-generated method stub
		 String titulo = "";
		 int valoracionOtorgada  = 0;
		 int valoracionMaxima = 0;
		 String fechaOpinion = "";
		 String opinion= "";
		 String fechaVisita = "";
		 ArrayList<Valoracion> valoraciones = new ArrayList<Valoracion>();
		
		 
		 //
		 Elements informaciónComentario = null;
		 Elements tituloComentario = null;
		
		 	
			
			informaciónComentario = informaciónComentario_.select("div.innerBubble");
			
			tituloComentario = informaciónComentario.select("div.quote span.noQuotes");
			
			if (tituloComentario.size() != 0) 
				titulo = tituloComentario.text();

			Elements valoracionComentario = informaciónComentario.select("div.rating.reviewItemInline");
			if (valoracionComentario.size() != 0) {
				Element img = valoracionComentario.select("img").first();
				String contenido = img.attr("alt");
				
				int numero = Utilidades.extraerNumero(contenido);
			    
		        int segundo = numero%10;
		        numero = numero/10;
		        int primero = numero%10;

		        /*3 de 5 */
			//	System.out.println(primero);/*3*/
			//	System.out.println(segundo);/*5*/
		        valoracionOtorgada = primero;
		        valoracionMaxima = segundo;
				
				
				if (!valoracionComentario.select("span.ratingDate").text().equalsIgnoreCase("")) //Estudiar que class=ratingDate -> Hoteles
					//System.out.println(valoracionComentario.select("span.ratingDate").text());// El comentario se realizó desde un móvil
					fechaOpinion = valoracionComentario.select("span.ratingDate").attr("title");
					if (fechaOpinion.equalsIgnoreCase(""))
						fechaOpinion =valoracionComentario.select("span.ratingDate").text();
						
			}

			
			Elements comentario = informaciónComentario.select("div.entry");
			if (comentario.size() != 0) 
				//System.out.println(comentario.text());
				opinion = comentario.text();
			
			Elements fechaVisitaRestaurante = informaciónComentario.select("ul.recommend span.recommend-titleInline");
			if (fechaVisitaRestaurante.size() != 0) 
				//System.out.println(fechaVisitaRestaurante.text());
				fechaVisita = fechaVisitaRestaurante.text();

			
			/*Valoracion*/
			Elements puntuaciones = informaciónComentario.select("li.recommend-answer");
			if (puntuaciones.size() !=0)
				for(Element puntuacion:puntuaciones){
					Elements ss = puntuacion.select("div.recommend-description");
					Valoracion valoracion = new Valoracion();
					if (ss.size()!=0){		
						//System.out.println(ss.text()); /*Servicios ...*/
						Element img = puntuacion.select("img").first();
						String contenido = img.attr("alt");
						//System.out.println(contenido);
						valoracion = new Valoracion(ss.text(), contenido.split(" ")[0]);
					}

					valoraciones.add(valoracion);
				}

			
		//	String a = informaciónComentario.select("div.rating-list").select("div.recommend").select("spam.recommend-titleInline.noRatings").text();
		//	System.out.println();
			comentarios.add(new Comentario(titulo, valoracionOtorgada, valoracionMaxima, fechaOpinion, opinion, fechaVisita, valoraciones));
			
	}
	
	protected  void mostrarListaUsuarios(){
		for (Usuario a: usuarios)
			System.out.println(a.toString());
	}
	
	protected  void mostrarListaComentarios(){
		for (Comentario b: comentarios)
			System.out.println(b.toString());
	}
	
}
