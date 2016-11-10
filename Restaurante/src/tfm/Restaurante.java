package tfm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clase que representa un tipo de actividad "Restaurante" que ofrece TripAdvisor
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */

public class Restaurante extends Ocio {
	
	private ArrayList<Valoracion> resumenDePuntuaciones;
	private String precioMedio;
	private String tipoDeCocina;
	private String comidas;
	private String caracteristicasDelRestaurante;
	private String aconsejablePara;
	private String horarios;
	private String direccionCompleta;
	private String descripcion;

	
	
	
	public Restaurante(Document doc_, String tipo_, String nombre_) {
		doc = doc_;
		tipo = tipo_;
		nombre =nombre_;
		usuarios = new ArrayList<Usuario>();
		comentarios = new ArrayList<Comentario>();
		resumenDePuntuaciones = new ArrayList<Valoracion>();
		
		precioMedio = "";
		tipoDeCocina= "";
		comidas = "";
		caracteristicasDelRestaurante = "";
		aconsejablePara="";
		horarios = "";
		direccionCompleta = "";
		descripcion = "";
	}

	@Override
	protected void obtenerCaracteristica(Elements informacionRestaurante2) {

		ArrayList<Valoracion> resumenDePuntuaciones = new ArrayList<Valoracion>();
		String aux [] = {"","","","","",""};
		Elements informacionRestaurante = informacionRestaurante2.select("div.table_section div.row");
		
		for(Element e : informacionRestaurante){
			String tipoDeInformacionValoes = e.select("div.colTitle").text();
			String tipoDeInformacion = e.select("div.title").text();
			if (tipoDeInformacionValoes.equalsIgnoreCase("Resumen de puntuaciones")){
				String row = e.select("div").first().attr("class").toString();
				if (row.equalsIgnoreCase("row")){
					/*Valoracion*/
					Elements puntuaciones = e.select("div.ratingRow.wrap");
					if (puntuaciones.size() !=0) //solo si la tabla no contiene elementos
						for(Element puntuacion:puntuaciones){
							String tipo = "";
							String valoracion = "";
							Elements ss = puntuacion.select("div.label.part");
							if (ss.size()!=0){ // entra solo si existe valoracions del restaurante		
								//System.out.println(ss.text()); /*Servicios ...*/
								tipo = ss.text();
								Element img = puntuacion.select("img").first();
								if (img != null){ // se puede dar elcaso de que no tenga ninguna imagen ...
									 valoracion = img.attr("alt").split(" ")[0];
								}
								resumenDePuntuaciones.add(new Valoracion(tipo, valoracion));
							}
							else 
								System.out.println("-");
							
						}
				}
			}
			else if (tipoDeInformacion.equalsIgnoreCase("Precio medio"))
				aux[0]= e.select("div.content").text();
			else if (tipoDeInformacion.equalsIgnoreCase("Tipo de cocina"))
				aux[1]= e.select("div.content").text();
			else if (tipoDeInformacion.equalsIgnoreCase("Comidas"))
				aux[2]= e.select("div.content").text();
			else if (tipoDeInformacion.equalsIgnoreCase("Características del restaurante"))
				aux[3]= e.select("div.content").text();
			else if (tipoDeInformacion.equalsIgnoreCase("Aconsejable para"))
				aux[4]= e.select("div.content").text();
			else if (tipoDeInformacion.equalsIgnoreCase("Horario de apertura"))
				aux[5]= e.select("div.content").text();

		}
		precioMedio = aux[0];
		tipoDeCocina = aux[1];
		comidas = aux[2];
		caracteristicasDelRestaurante = aux[3];
		aconsejablePara= aux[4];
		horarios = aux[5];
		direccionCompleta = informacionRestaurante2.select("div.additional_info div.content ul.detailsContent").text();
		descripcion = informacionRestaurante2.select("div.additional_info div.content").last().text();
	
	}

	@Override
	protected void mostrarCaracteristicas() {
		System.out.println("tipo: "+ tipo);
		System.out.println("Nombre:" + nombre);
		System.out.println("Resumen de valoración: " + Utilidades.toStringLista(resumenDePuntuaciones)); //devuelve la lista de lementos separadas por ;
		System.out.println("Precio medio: " + precioMedio);
		System.out.println("Tipo de cocina: " + tipoDeCocina);
		System.out.println("Comidas: " + comidas);
		System.out.println("Caracteristicas: " + caracteristicasDelRestaurante);
		System.out.println("Aconsejable para: " + aconsejablePara);
		System.out.println("Horarios: " + horarios);
		System.out.println("Direccion completa: " + direccionCompleta);
		System.out.println("Descripcion: " + descripcion);
		
	}
	
	@Override
	protected void scan() {
		
		Elements informacion = doc.select("div.deckB.review_collection div.reviewSelector  ");
		
		int i=0;
		for (Element info: informacion){
			Elements informaciónUsuarios = info.select("div.review.dyn_full_review.inlineReviewUpdate.provider0");
			if (informaciónUsuarios.toString().equalsIgnoreCase(""))//Es ncesario para captar la información que mo necesita que le des a "mas"
				informaciónUsuarios =info.select("div.review.basic_review.inlineReviewUpdate.provider0");
			obtenerListaUsuarios(informaciónUsuarios);
			obtenerListaComentarios(info);
			i++;
		}
		
		Elements informacionRestaurante = doc.select("div.dynamicBottom div.details_tab");
		obtenerCaracteristica(informacionRestaurante);
		System.out.println("comentarios:" + i);
	}
	
	
	
	@Override
	protected void guardarEnCsv(String nombre) {
			
			String csvFile = "M:\\CSVS_TEST\\"+nombre+tipo+"_"+this.nombre+".csv";
	        FileWriter writer = null;
			try {
				writer = new FileWriter(csvFile.replace("&", "").replace("\"", ""));
			} catch (IOException e) {
				e.printStackTrace();
			}

	        try {
				CSVUtils.writeLine(writer, Arrays.asList(
						"Tipo",
						"NombreRestaurante_",
						
						"NombreUsuario_", 
						"LocalizaciónUsario_", 
						"NivelUsuario_", 
						"OpinionesTotalesUsuario_", 
					//	"opinionesRestaurantes", 
						"VotosUtiles_", 
						
						"IDComentario_",//se crea aqui
						"ValoracionComentario_",
						"TituloComentario_",
						"Comentario_",
						"FechaCometario_",
						"FechaVisitaRestaurante_",
						"ValoracionesComentario_",
						
						"ResumenPuntuacionesRestaurante_",
						"PrecioMedio_",
						"TipoCocina_",
						"Comida_",
						"CaracteristicasDelResaurante_",
						"AconsejablePara_",
						"Horarios_",
						"DireccionCompletaRestaurante_",
						"Descripcion_"
						
						));
			} catch (IOException e) {
				e.printStackTrace();
			}

	        if (usuarios.isEmpty()){
	    			try {
	    				CSVUtils.writeLine(writer, Arrays.asList(
	    						
	    						tipo,
	    						nombre,
	    						
	    						"", 
	    						"",
	    						"",
	    						"", 
	    						//String.valueOf(usuarios.get(k).getOpinionesRestaurantesHoteles()), 
	    						"",
	    						
	    						"",
	    						"",
	    						"",
	    						"",
	    						"",
	    						"",
	    						"",

	    						Utilidades.toStringLista(resumenDePuntuaciones), //devuelve la lista de lementos separadas por ;
	    						precioMedio,
	    						tipoDeCocina,
	    						comidas,
	    						caracteristicasDelRestaurante,
	    						aconsejablePara,
	    						horarios,
	    						direccionCompleta,
	    						descripcion
	    						    						
	    						), ',', '¬');
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		
	        }
	        else
			for (int k =0; k < usuarios.size();k++ ){
	        //custom separator + quote
				int longitudValoracionesComentarios = comentarios.get(k).getValoraciones().toString().length();
				try {
					CSVUtils.writeLine(writer, Arrays.asList(
							
							tipo,
							this.nombre,
							
							usuarios.get(k).getIdUsario().toString(), 
							usuarios.get(k).getLocalización().toString(), 
							String.valueOf(usuarios.get(k).getNivel()), 
							String.valueOf(usuarios.get(k).getOpinionesTotales()),  
							//String.valueOf(usuarios.get(k).getOpinionesRestaurantesHoteles()), 
							String.valueOf(usuarios.get(k).getVotosUtiles()),
							
							usuarios.get(k).getIdUsario().toString()+comentarios.get(k).getTitulo().substring(0,2)+"_"+comentarios.get(k).getOpinion().substring(0, 3).replaceAll(" ", ""),
							String.valueOf(comentarios.get(k).getValoracionOtorgada()),
							comentarios.get(k).getTitulo(),
							comentarios.get(k).getOpinion(), //texto
							comentarios.get(k).getFechaOpinion().replaceAll("Opinión escrita el ", ""),
							comentarios.get(k).getFechaVisita(),
							comentarios.get(k).getValoraciones().toString().substring(1,(longitudValoracionesComentarios - 1)).replaceAll(", ", ";"),
							
							Utilidades.toStringLista(resumenDePuntuaciones), //devuelve la lista de lementos separadas por ;
							precioMedio,
							tipoDeCocina,
							comidas,
							caracteristicasDelRestaurante,
							aconsejablePara,
							horarios,
							direccionCompleta,
							descripcion
							
							), ';', '¬');
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


	        try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
