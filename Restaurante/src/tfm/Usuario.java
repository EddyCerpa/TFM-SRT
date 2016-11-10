package tfm;

/**
 * Clase que representa un usuario al que se le puede extraer la información
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */
public class Usuario {
	private String idUsario;
	private String localización;
	private int nivel;
	private int opinionesTotales;
	private int opinionesRestaurantesHoteles;
	private int votosUtiles;

	public Usuario(String idUsario, String localización, int nivel,
			int opinionesTotales, int opinionesRestaurantesHoteles,
			int votosUtiles) {
		this.idUsario = idUsario;
		this.localización = localización;
		this.nivel = nivel;
		this.opinionesTotales = opinionesTotales;
		this.opinionesRestaurantesHoteles = opinionesRestaurantesHoteles;
		this.votosUtiles = votosUtiles;
	}

	/**
	 * Obtiene el identificador del usuario (nick)
	 * 
	 * @return cadena de caracteres que representa el nick del usuario
	 */
	public String getIdUsario() {
		return idUsario;
	}

	/**
	 * Obtienen la localización del usuario
	 * 
	 * @return cadena de caracteres que representa la localización del usuario
	 *         (provincia, pais)
	 */
	public String getLocalización() {
		return localización;
	}

	/**
	 * Obtiene el nivel del usuario
	 * 
	 * @return nivel del usuario en formato numérico (entero)
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * Obtienen el numero de opiniones totales que realizado un usuario
	 * 
	 * @return numero de opiniones totales
	 */
	public int getOpinionesTotales() {
		return opinionesTotales;
	}

	/**
	 * Obtiene la cantidad de opiniones sobre restaurantes y hoteles que ha
	 * realizado de moento un usuario
	 * 
	 * @return numero de opiniones sobre restaurantes y hoteles (0...n)
	 */
	public int getOpinionesRestaurantesHoteles() {
		return opinionesRestaurantesHoteles;
	}

	/**
	 * Obtiene la cantidad de votos útiles
	 * 
	 * @return cantidad de votos útiles
	 */
	public int getVotosUtiles() {
		return votosUtiles;
	}

	@Override
	public String toString() {
		return "Usuario [idUsario=" + idUsario + ", localización="
				+ localización + ", nivel=" + nivel + ", opinionesTotales="
				+ opinionesTotales + ", opinionesRestaurantesHoteles="
				+ opinionesRestaurantesHoteles + ", votosUtiles=" + votosUtiles
				+ "]";
	}

}
