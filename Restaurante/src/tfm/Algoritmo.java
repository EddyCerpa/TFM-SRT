package tfm;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Algoritmo {

	private double   [][] matriz;
	private  ArrayList<Integer>[][] matrizItemsComunes;
	private double matrizMSD[][] ;
	public ArrayList<PorcentajeSimilitud []> usuariosSimilaresOrdenados;
	double matrizEstimacion [][];
	ArrayList<PorcentajeSimilitud []> matrizEstimacionOrdenada;
	
	static int FILAS = 6;
	static int COLUMNAS = 12;

	public static double[][] matrizPrueba = {
			{2,3,-1,-1,1,-1,5,1,-1,4,1,-1},
			{2,-1,3,5,-1,2,4,-1,5,2,3,-1},
			{1,3,-1,5,-1,4,-1,2,-1,-1,-1,-1},
			{3,-1,2,3,-1,-1,1,-1,5,3,-1,1},
			{1,1,5,1,1,3,4,5,3,1,2,1},
			{-1,2,3,1,-1,3,-1,2,-1,4,-1,5}
	};
	
	
	
	




	public Algoritmo(double[][] matriz, Integer filas, Integer columnas) { 
		this.FILAS = filas;
		this.COLUMNAS = columnas;
		this.matriz = matriz;
		this.matrizItemsComunes = new ArrayList[FILAS][FILAS];
		this.matrizMSD = new double [FILAS][FILAS];
		matrizEstimacionOrdenada = new ArrayList<>();
		usuariosSimilaresOrdenados = new ArrayList();
		inicializaMatrizMSD();
		inicializarMatizItemsComunes();
	}
	
	
	private void inicializaMatrizMSD() {
		for (int u = 0; u < FILAS; u++)
			for (int i = 0; i < FILAS; i++) 
				matrizMSD[u][i] = 0;
	}
	
	
	public void obtencionDeItemsVotadosPorAmbosUsuarios() {
		for (int u = 0; u < FILAS; u++) {
			for (int j = u+1; j < FILAS; j++) {
				ArrayList<Integer> listaItem = new ArrayList<Integer>();
				for (int i = 0; i < COLUMNAS; i++) { 
					double  a = matriz [u][i];
					double b = matriz [j][i];
					
					if (a!=-1)
						if (b!=-1)
							listaItem.add(i);//añadimos el item	que hayan votado ambos usuarios
							
				}
				if (listaItem.isEmpty()){
					listaItem.add(-1); // si no exiten items que hayan votado ambos usuarios
					matrizItemsComunes[u][j] = listaItem;
				}
				else 
					matrizItemsComunes[u][j] = listaItem;
			}
		}
	}
	
		
		private void inicializarMatizItemsComunes() { 
			for (int u = 0; u < FILAS; u++) 
				for (int i = 0; i < FILAS; i++) 
					matrizItemsComunes[u][i] = new ArrayList<Integer>(3);		
		}
	
		public void calculoMSD() {
			// TODO Auto-generated method stub
			
			int bxy = 0; //numero de items comunes
			double rxi=0;
			double ryi=0;
			double max = 0;
			double min = 6; // ponemos como minimo este valor porque es > max
			
			
			for (int u1 = 0; u1 < FILAS; u1++) {
				for (int u2 = u1+1; u2 < FILAS; u2++) {
					//System.out.println(matrizItemsComunes[u][i].get(2).toString());
					ArrayList<Integer> lista = matrizItemsComunes[u1][u2];
					bxy=lista.size();
					if (lista.get(0)!= -1){
						
						for (Integer item : lista) {
							double puntuacionItemU1 = matriz[u1][item];
							double puntuacionItemU2= matriz[u2][item];
							// vemos maximo y minimo de los dos
							double maxAux = Math.max(puntuacionItemU1, puntuacionItemU2);
							double minAux = Math.min(puntuacionItemU1, puntuacionItemU2);
							
							max = Math.max(maxAux, max);
							min = Math.min(minAux, min);
						}
						double sum = 0;
						for (Integer item : lista) {
							double puntuacionItemU1 = matriz[u1][item];
							double puntuacionItemU2= matriz[u2][item];
							sum = sum + Math.pow(((puntuacionItemU1-puntuacionItemU2)/(max-min)), 2.0);			
						}
						double similitud = 1.0-((1.0/bxy)*sum);
						matrizMSD[u1][u2]=similitud;
					}
				}
			}
		}
		
		public void crearListaCompatibilidadOrdenada() {			
			for (int u1 = 0; u1 < FILAS; u1++) {
				ArrayList<PorcentajeSimilitud>porcentajeSimilituds = new ArrayList<PorcentajeSimilitud>();
				for (int u2 = 0; u2 < FILAS; u2++) {
					if (u1 != u2){
						
						if (matrizMSD[u1][u2] != -1)
							porcentajeSimilituds.add(new PorcentajeSimilitud(u2,matrizMSD[u1][u2]));
						if (matrizMSD[u2][u1] != -1)
							porcentajeSimilituds.add(new PorcentajeSimilitud(u2,matrizMSD[u2][u1]));
					}
				}
					PorcentajeSimilitud [] array = porcentajeSimilituds.toArray(new PorcentajeSimilitud[porcentajeSimilituds.size()]);
					Arrays.sort(array);
					usuariosSimilaresOrdenados.add(array); 
			}
		}
		
		
		
		
		
		public void construirMatrizEstimacionOrdenada() {
			for (int i = 0; i < FILAS; i++) {
				PorcentajeSimilitud usatioItem [];
				usatioItem = new PorcentajeSimilitud [COLUMNAS];
				for (int j = 0; j < COLUMNAS; j++) {
					usatioItem [j] = new PorcentajeSimilitud(j, matrizEstimacion[i][j]);
				}
				Arrays.sort(usatioItem);
				matrizEstimacionOrdenada.add(usatioItem) ;
			}
		}
		
		public void pintarListaCompatibilidadOrdenada() {
			int i = 0;
			for (PorcentajeSimilitud [] array: usuariosSimilaresOrdenados) {
				System.out.println("usuario: " + i);
				for (PorcentajeSimilitud porcentajeSimilitud : array) {
					System.out.println(porcentajeSimilitud.toString());
				}
				i++;
			}	
		}
		
		
	public void pintarListaCompatibilidadOrdenada(int usuarios, int kUsuariosCompatibles) {
		PorcentajeSimilitud[] aux = usuariosSimilaresOrdenados.get(usuarios);
		for (int i = 0; i < kUsuariosCompatibles; i++) 
			System.out.println(aux[i].toString());
	}
		
		
		/**
		 * @return **********************/
		
	public double[][] construirMatrizEstimacionVotos(int K){
			  matrizEstimacion = new double [FILAS][COLUMNAS];
			
			for (int i = 0; i < FILAS; i++) {
				/*obtenemso los k usuarios compatibles*/
				PorcentajeSimilitud[] aux = getKUsuariosCompatibles(K,i);
				for (int j = 0; j < COLUMNAS; j++) {
					double puntuacion =0;
					double similitud = 0;
					double x=0;
					double y=0;
					double votoEstimado =0;
					if (matriz[i][j] == -1){ /*para ver la posible valoración que le podría dar*/
						for (int j2 = 0; j2 < aux.length; j2++) {
							 puntuacion = getPuntuacion(aux[j2].getUsuario(),j);
							 if (puntuacion != -1){
								 similitud = aux[j2].getPorcentaje();
								x = (puntuacion*similitud)+x;
								y = similitud + y;
							 }
							
						}
						if (y==0)
							votoEstimado = 1;
						else 	
							votoEstimado = x/y;
						matrizEstimacion[i][j] = votoEstimado;
					}
					else 
						matrizEstimacion[i][j] = -1;
				}
			}
			return matrizEstimacion;
		}
		
		private double getPuntuacion (int usuario, int item){
			return matriz[usuario][item];
		}
		
		/*obtenemso los mejores primeros k usuarios compatibles con el porcentaje de similitud*/
		private PorcentajeSimilitud[] getKUsuariosCompatibles (int k, int usuarioX){
			PorcentajeSimilitud[] aux = usuariosSimilaresOrdenados.get(usuarioX);
			if (aux.length == 0){
				System.out.println("Probelma");
			}
			PorcentajeSimilitud[] aux2 = new PorcentajeSimilitud[k] ;
			for (int i = 0; i < k; i++) {
				aux2 [i]= aux [i];
			}
			return aux2;
		}
		
		
		public void pintarMatrizEstimacion() {
			for (int i = 0; i < FILAS; i++) {
				System.out.println();
				System.out.println("Usuario:" + (i+1));
				for (int j = 0; j < COLUMNAS; j++) {
					System.out.println(matrizEstimacion[i][j]);
				}
			}
			
		}
		
		
		
		
		private void pintarMatrizEstimacionOrdenada() {
			// TODO Auto-generated method stub
			int i = 0;
			
			for (PorcentajeSimilitud [] array: matrizEstimacionOrdenada) {
				System.out.println();
				System.out.println("usuario: " + (i+1));
				for (PorcentajeSimilitud porcentajeSimilitud : array) {
					System.out.println("item:" + (porcentajeSimilitud.getUsuario()+1) + " 	" + porcentajeSimilitud.getPorcentaje());
				}
				i++;
			}
		}
		
		public ArrayList<PorcentajeSimilitud> pintarMatrizEstimacionOrdenada(int usuario) {
			PorcentajeSimilitud[] aux = matrizEstimacionOrdenada.get(usuario);
			ArrayList<PorcentajeSimilitud> aux2 = new ArrayList<PorcentajeSimilitud>();
			for (PorcentajeSimilitud array: aux) {
				if (array.getPorcentaje() == -1){
					//System.out.println("A este usuario le gusta todos los ítems");
					return aux2;
				}
				System.out.printf("%-20s%-20s%-20s%-20s\n","item:",(array.getUsuario()), " estimación:", array.getPorcentaje());
				System.out.println();
				aux2.add(array);
					
			}
			return aux2;
		}
		
		
		static class PorcentajeSimilitud implements Comparable<PorcentajeSimilitud> {
			int usuario = 0;
			double porcentaje =0;
			
			public PorcentajeSimilitud(int usuario_, double porcentaje_) {
				usuario = usuario_;
				porcentaje = porcentaje_;
			}
			
			public double getPorcentaje() {
				return porcentaje;
			}
			
			public int getUsuario() {
				return usuario;
			}
			
			@Override
			public String toString() {
				return "Usuario: " + Integer.toString(usuario)+ " " + Double.toString(porcentaje)+ " %";
			}

			@Override
			public int compareTo(PorcentajeSimilitud o) {
				if (porcentaje > o.porcentaje) {
	                return -1;
	            }
	            if (porcentaje < o.porcentaje) {
	                return 1;
	            }
	            return 0;
			}
		}

}
