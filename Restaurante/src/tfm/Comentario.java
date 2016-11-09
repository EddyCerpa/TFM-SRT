package tfm;

import java.util.ArrayList;

public class Comentario {
	
	private String titulo;
	private int valoracionOtorgada;
	private int valoracionMaxima;
	private String fechaOpinion;
	private String opinion;
	private String fechaVisita;
	private ArrayList<Valoracion> valoraciones;
	
	
	
	public Comentario(String titulo, int valoracionOtorgada ,
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
	
	
	public String getTitulo() {
		return titulo;
	}
	public int getValoracionMaxima() {
		return valoracionMaxima;
	}
	public int getValoracionOtorgada() {
		return valoracionOtorgada;
	}
	public String getFechaOpinion() {
		return fechaOpinion;
	}
	public String getOpinion() {
		return opinion;
	}
	public String getFechaVisita() {
		return fechaVisita;
	}
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
