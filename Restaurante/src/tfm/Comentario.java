package tfm;

import java.util.ArrayList;

/**
 * Clase que representa todo tipo de comentarios disponibles en la plaforma web
 * de TripAdvisor
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */

public class Comentario {

	private String titulo;
	private int valoracionOtorgada;
	private int valoracionMaxima;
	private String fechaOpinion;
	private String opinion;
	private String fechaVisita;
	private ArrayList<Valoracion> valoraciones;

	public Comentario(String titulo, int valoracionOtorgada,
			int valoracionMaxima, String fechaOpinion, String opinion,
			String fechaVisita, ArrayList<Valoracion> valoraciones) {

		this.titulo = titulo;
		this.valoracionMaxima = valoracionMaxima;
		this.valoracionOtorgada = valoracionOtorgada;
		this.fechaOpinion = fechaOpinion;
		this.opinion = opinion;
		this.fechaVisita = fechaVisita;
		this.valoraciones = valoraciones;
	}

	/**
	 * Obtenemos el título del comentario
	 * 
	 * @return título del comentario
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Obtenemos la valoración máxima que puede obtener el comentario
	 * 
	 * @return valoración máxima
	 */
	public int getValoracionMaxima() {
		return valoracionMaxima;
	}

	/**
	 * Obtenemos la valoración del comentario que un usuario le ha dado
	 * 
	 * @return valoracion de 0 a valoracion máxima
	 */
	public int getValoracionOtorgada() {
		return valoracionOtorgada;
	}

	/**
	 * Obtenemos la fecha de la opinion introducida en el comentario
	 * 
	 * @return cadena de caracteres que representa la fecha en la que se ha
	 *         introducido la opinion
	 */
	public String getFechaOpinion() {
		return fechaOpinion;
	}

	/**
	 * Obtenemso el texto (opinion) que de un usuario
	 * 
	 * @return cadena de caracteres
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * Obtenemos la fecha que el usuario ha visitado o asistido
	 * 
	 * @return cadena de caracteres que representa la fecha de visitas.
	 */
	public String getFechaVisita() {
		return fechaVisita;
	}

	/**
	 * Obtenemos la lista del detalle de valoraciones de un comentario
	 * 
	 * @return lista de tuplas (tipo de valoracion, valor).
	 */
	public ArrayList<Valoracion> getValoraciones() {
		return valoraciones;
	}

	@Override
	public String toString() {
		return "Comentario [titulo=" + titulo + ", valoracionMaxima="
				+ valoracionMaxima + ", valoracionOtorgada="
				+ valoracionOtorgada + ", fechaOpinion=" + fechaOpinion
				+ ", opinion=" + opinion + ", fechaVisita=" + fechaVisita
				+ ", valoraciones=" + valoraciones + "]";
	}

}
