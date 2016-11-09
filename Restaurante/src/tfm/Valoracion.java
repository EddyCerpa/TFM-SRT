package tfm;

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

	public String getTipo() {
		return tipo;
	}

	public String getValor() {
		return valor;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return tipo +" "+ valor;
	}
	
	

}
