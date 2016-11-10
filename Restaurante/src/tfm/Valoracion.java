package tfm;

/**
 * Clase que representa la tupla valoraci�n (tipo, valor)
 * 
 * @author Eddy Cuizaguana
 *
 */
public class Valoracion {
	private String tipo;
	private String valor;

	public Valoracion(String tipo, String valor) {
		this.tipo = tipo;
		this.valor = valor;
	}

	public Valoracion() {
		this.tipo = "";
		this.valor = "";
	}

	/**
	 * M�todo con el que se obtiene el tipo de la tupla
	 * 
	 * @return cadena de caracteres que representa el tipo de la tupla
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * M�todo con el que se obtiene el valor de la tupla
	 * 
	 * @return cadena de caracteres que representa el valor de la tupla
	 */
	public String getValor() {
		return valor;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tipo + " " + valor;
	}

}
