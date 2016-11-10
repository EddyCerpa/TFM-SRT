package tfm;

/**
 * Clase que representa un usuario al que se le puede extraer la informaci�n
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */
public class Usuario {
	private String idUsario;
	private String localizaci�n;
	private int nivel;
	private int opinionesTotales;
	private int opinionesRestaurantesHoteles;
	private int votosUtiles;

	public Usuario(String idUsario, String localizaci�n, int nivel,
			int opinionesTotales, int opinionesRestaurantesHoteles,
			int votosUtiles) {
		this.idUsario = idUsario;
		this.localizaci�n = localizaci�n;
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
	 * Obtienen la localizaci�n del usuario
	 * 
	 * @return cadena de caracteres que representa la localizaci�n del usuario
	 *         (provincia, pais)
	 */
	public String getLocalizaci�n() {
		return localizaci�n;
	}

	/**
	 * Obtiene el nivel del usuario
	 * 
	 * @return nivel del usuario en formato num�rico (entero)
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
	 * Obtiene la cantidad de votos �tiles
	 * 
	 * @return cantidad de votos �tiles
	 */
	public int getVotosUtiles() {
		return votosUtiles;
	}

	@Override
	public String toString() {
		return "Usuario [idUsario=" + idUsario + ", localizaci�n="
				+ localizaci�n + ", nivel=" + nivel + ", opinionesTotales="
				+ opinionesTotales + ", opinionesRestaurantesHoteles="
				+ opinionesRestaurantesHoteles + ", votosUtiles=" + votosUtiles
				+ "]";
	}

}
