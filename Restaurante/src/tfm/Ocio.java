package tfm;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase abstracta de la que extienden los tipos de ocio que tiene disponible
 * TripAdvisor en la palatforma web
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */
public abstract class Ocio {
	protected String tipo;
	protected String nombre;

	protected Document doc;
	protected ArrayList<Usuario> usuarios;
	protected ArrayList<Comentario> comentarios;

	protected abstract void scan();

	// protected abstract void obtenerListaUsuarios (Element
	// informaciónUsuarios);

	// protected abstract void obtenerListaComentarios(Element
	// informaciónUsuarios);

	protected abstract void obtenerCaracteristica(Elements informaciónUsuarios);

	// protected abstract void mostrarListaUsuarios();

	// protected abstract void mostrarListaComentarios();

	protected abstract void mostrarCaracteristicas();

	/**
	 * Metodo encargado de estructurar la información en un fichero csv
	 * 
	 * @param nombre
	 *            nombre que se le quiere dar al fichero de salida
	 */
	protected abstract void guardarEnCsv(String nombre);

	/**
	 * Metodo que obtiene la lista de usuarios de un fichero html
	 * 
	 * @param informaciónUsuarios
	 *            contiene toda la inforamción relacionada con los usuarios
	 */
	protected void obtenerListaUsuarios(Elements informaciónUsuarios) {
		String nombreUsuario = "";
		String loc = "";
		int nivel = 0;
		int opinionTotal = 0;
		int opinionRestaurante = 0;
		int votoUtil = 0;

		Elements nombre = informaciónUsuarios.select("div.username.mo");
		if (nombre.size() != 0)
			nombreUsuario = nombre.text();

		Elements localizacion = informaciónUsuarios.select("div.location");
		if (!localizacion.text().equalsIgnoreCase(""))
			loc = localizacion.text();
		Elements nivelUsuario = informaciónUsuarios
				.select("div.levelBadge.badge span");
		if (nivelUsuario.size() != 0) {
			Element img = nivelUsuario.select("img").first();
			String contenido = img.attr("src");
			nivel = Utilidades.extraerNumero(contenido);
		}

		Elements opinionesTotales = informaciónUsuarios
				.select("div.reviewerBadge.badge span.badgeText");
		if (opinionesTotales.size() != 0)
			opinionTotal = Utilidades.extraerNumero(opinionesTotales.text());

		Elements opinionesRestaurantes = informaciónUsuarios
				.select("div.contributionReviewBadge.badge span.badgeText");
		if (opinionesRestaurantes.size() != 0)
			opinionRestaurante = Utilidades.extraerNumero(opinionesRestaurantes
					.text());

		Elements votosUtiles = informaciónUsuarios
				.select("div.helpfulVotesBadge.badge.no_cpu span.badgeText");
		if (votosUtiles.size() != 0)
			votoUtil = Utilidades.extraerNumero(votosUtiles.text());

		Usuario us = new Usuario(nombreUsuario, loc, nivel, opinionTotal,
				opinionRestaurante, votoUtil);
		usuarios.add(us);
	}

	/**
	 * Obtiene la información de los comentarios detalladamente
	 * 
	 * @param informaciónComentario_ donde se encuntra la información a extraer
	 */
	protected void obtenerListaComentarios(Element informaciónComentario_) {
		String titulo = "";
		int valoracionOtorgada = 0;
		int valoracionMaxima = 0;
		String fechaOpinion = "";
		String opinion = "";
		String fechaVisita = "";
		ArrayList<Valoracion> valoraciones = new ArrayList<Valoracion>();
		Elements informaciónComentario = null;
		Elements tituloComentario = null;

		informaciónComentario = informaciónComentario_
				.select("div.innerBubble");
		tituloComentario = informaciónComentario
				.select("div.quote span.noQuotes");
		if (tituloComentario.size() != 0)
			titulo = tituloComentario.text();

		Elements valoracionComentario = informaciónComentario
				.select("div.rating.reviewItemInline");
		if (valoracionComentario.size() != 0) {
			Element img = valoracionComentario.select("img").first();
			String contenido = img.attr("alt");
			int numero = Utilidades.extraerNumero(contenido);
			int segundo = numero % 10;
			numero = numero / 10;
			int primero = numero % 10;

			/* 3 de 5 */
			// System.out.println(primero);/*3*/
			// System.out.println(segundo);/*5*/
			valoracionOtorgada = primero;
			valoracionMaxima = segundo;
			if (!valoracionComentario.select("span.ratingDate").text()
					.equalsIgnoreCase(""))
				fechaOpinion = valoracionComentario.select("span.ratingDate")
						.attr("title");
			if (fechaOpinion.equalsIgnoreCase(""))
				fechaOpinion = valoracionComentario.select("span.ratingDate")
						.text();
		}

		Elements comentario = informaciónComentario.select("div.entry");
		if (comentario.size() != 0)
			opinion = comentario.text();
		Elements fechaVisitaRestaurante = informaciónComentario
				.select("ul.recommend span.recommend-titleInline");
		if (fechaVisitaRestaurante.size() != 0)
			fechaVisita = fechaVisitaRestaurante.text();
		/* Valoracion */
		Elements puntuaciones = informaciónComentario
				.select("li.recommend-answer");
		if (puntuaciones.size() != 0)
			for (Element puntuacion : puntuaciones) {
				Elements ss = puntuacion.select("div.recommend-description");
				Valoracion valoracion = new Valoracion();
				if (ss.size() != 0) {
					/* Servicios ... */
					Element img = puntuacion.select("img").first();
					String contenido = img.attr("alt");
					valoracion = new Valoracion(ss.text(),
							contenido.split(" ")[0]);
				}

				valoraciones.add(valoracion);
			}
		comentarios.add(new Comentario(titulo, valoracionOtorgada,
				valoracionMaxima, fechaOpinion, opinion, fechaVisita,
				valoraciones));
	}

	/**
	 * Muetra las ista de todos los usuarios con todos su
	 * detalles/carcaterísticas
	 */
	protected void mostrarListaUsuarios() {
		for (Usuario a : usuarios)
			System.out.println(a.toString());
	}

	/**
	 * Muestra la lista de comentarios con todas sus características
	 */
	protected void mostrarListaComentarios() {
		for (Comentario b : comentarios)
			System.out.println(b.toString());
	}

}
