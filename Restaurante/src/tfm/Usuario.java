package tfm;

public class Usuario {
	private String idUsario;
	private String localización;
	private int nivel;
	private int opinionesTotales;
	private int opinionesRestaurantesHoteles;
	private int votosUtiles;
	
	public Usuario() {}
	
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

	public String getIdUsario() {
		return idUsario;
	}

	public String getLocalización() {
		return localización;
	}

	public int getNivel() {
		return nivel;
	}

	public int getOpinionesTotales() {
		return opinionesTotales;
	}

	public int getOpinionesRestaurantesHoteles() {
		return opinionesRestaurantesHoteles;
	}

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
