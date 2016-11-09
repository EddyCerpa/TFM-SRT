package tfm;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class AlgoritmoObj2 {

	private double   [][] matriz;
	private  ArrayList<Integer>[][] matrizItemsComunes; //= new ArrayList[FILAS][FILAS];
	private double matrizMSD[][] ;//= new double [FILAS][FILAS];
	public ArrayList<PorcentajeSimilitud []> usuariosSimilaresOrdenados;// = new ArrayList();
	
	double matrizEstimacion [][];
	ArrayList<PorcentajeSimilitud []> matrizEstimacionOrdenada;
	
	static int FILAS = 6;
	static int COLUMNAS = 12;
//	public static double[][] matrizPrueba = {
//		{1,2,-1,-1,2,-1,3,4,-1,4,1,-1},
//		{-1,-1,1,5,-1,5,3,1,-1,5,2,1},
//		{1,-1,-1,2,-1,1,-1,3,4,-1,-1,-1},
//		{-1,1,4,4,-1,-1,3,-1,5,4,-1,1},
//		{2,-1,5,-1,1,-1,1,-1,-1,-1,2,1},
//		{-1,-1,5,2,1,-1,-1,4,-1,1,-1,2}};
	
	public static double[][] matrizPrueba = {
			{2,3,-1,-1,1,-1,5,1,-1,4,1,-1},
			{2,-1,3,5,-1,2,4,-1,5,2,3,-1},
			{1,3,-1,5,-1,4,-1,2,-1,-1,-1,-1},
			{3,-1,2,3,-1,-1,1,-1,5,3,-1,1},
			{1,1,5,1,1,3,4,5,3,1,2,1},
			{-1,2,3,1,-1,3,-1,2,-1,4,-1,5}};
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Inicio");
		
		
//		PrintStream fichero = new PrintStream(new File("M:\\fichero.txt"));
//		System.setOut(fichero);
		
		
    	ConexionSQL conexionSQL = new ConexionSQL();
    	conexionSQL.conectar();
    	double[][]matriz = conexionSQL.getMATRIZ();
		AlgoritmoObj2 algoritmo = new AlgoritmoObj2(matriz, conexionSQL.getFILAS(), conexionSQL.getCOLUMNAS()) ;
		
//		for (int i = 0; i < COLUMNAS; i++) {
//			System.out.println(matriz[1][i]);
//		}
		
//		double[][] matriz = matrizPrueba;
//    	AlgoritmoObj2 algoritmo = new AlgoritmoObj2(matriz, 6, 12);
    	System.out.println("-------------------");
		algoritmo.obtencionDeItemsVotadosPorAmbosUsuarios();
		algoritmo.calculoMSD();
		algoritmo.crearListaCompatibilidadOrdenada();
		//algoritmo.pintarListaCompatibilidadOrdenada();
		
		int u = FILAS-1;
		algoritmo.construirMatrizEstimacionVotos(u);//cantidad de usarios con los que se quiere estimar 
//		System.out.println("MATRIZ DE ESTIMACION!---------------------------------");
		//algoritmo.pintarMatrizEstimacion();
		//System.out.println("MATRIZ DE ESTIMACION ORDENADA!---------------------------------");
		algoritmo.construirMatrizEstimacionOrdenada();
		//algoritmo.pintarMatrizEstimacionOrdenada();
		
		menu(algoritmo, conexionSQL);
		
	}
	


	private static void menu(AlgoritmoObj2 algoritmo, ConexionSQL conexionSQL) {

		
		int usuario = seleccionaOpcion("Introduce un usuario n [ 0, 1, 2,..., " + FILAS + " como máximo]", "Intenta introducir un número");
		int ususariosCompatibles = seleccionaOpcion("Introduce la cantidad de usuarios compatibles que quiere ver [ " + FILAS + " como máximo]", "Intenta introducir un número");
		
		/***/
		
		/***/
		System.out.println("El Usuario " + usuario + "es compatible con:");
		algoritmo.pintarListaCompatibilidadOrdenada(usuario, ususariosCompatibles);
		System.out.println("Los posibles ítems que le pueden gustar son:");
		ArrayList<PorcentajeSimilitud> aux = algoritmo.pintarMatrizEstimacionOrdenada(usuario);
		System.out.println("Los items asociados son");
		conexionSQL.consutaItemsSegunId(aux);
		System.out.println("Los restaurantes recomendados segun los ítems son:");
		conexionSQL.consultaRestauranteSeunItem(aux);
		
	}



	private static int seleccionaOpcion(String mensaje, String mensajeError) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println(mensaje);
		int opcion = 0;
		boolean ok = false;
		do {
			try {
				opcion = sc.nextInt();
				ok=true;
			} catch (InputMismatchException e) {
				// TODO: handle exception
				System.out.println(mensajeError);
				sc.next();
			}
			
		} while (!ok);
		
		return opcion ;
	}



	public AlgoritmoObj2(double[][] matriz, Integer filas, Integer columnas) { 
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
		// TODO Auto-generated method stub
		for (int u = 0; u < FILAS; u++) {
			for (int i = 0; i < FILAS; i++) {
				//System.out.println(matrizItemsComunes[u][i].get(2).toString());
				matrizMSD[u][i] = 0;
			}
			
		}
		
	}
	
	
	private  void obtencionDeItemsVotadosPorAmbosUsuarios() {
		//inicializarMatizItemsComunes();
		// TODO Auto-generated method stub
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
			for (int u = 0; u < FILAS; u++) {
				for (int i = 0; i < FILAS; i++) {
					//System.out.println(matrizItemsComunes[u][i].get(2).toString());
					matrizItemsComunes[u][i] = new ArrayList<Integer>(3);
				}
				
			}
		}
	
		private void calculoMSD() {
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
		
		private void crearListaCompatibilidadOrdenada() {
			// TODO Auto-generated method stub
			
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

//				if (!porcentajeSimilituds.isEmpty()){
					PorcentajeSimilitud [] array = porcentajeSimilituds.toArray(new PorcentajeSimilitud[porcentajeSimilituds.size()]);
					Arrays.sort(array);
					usuariosSimilaresOrdenados.add(array); 
//				}
				
				//listaUsuariosCompatiblesOrdenados.add(array); 
			}
		}
		
		
		
		
		
		private void construirMatrizEstimacionOrdenada() {
			// TODO Auto-generated method stub
			for (int i = 0; i < FILAS; i++) {
				PorcentajeSimilitud usatioItem [];
				usatioItem = new PorcentajeSimilitud [COLUMNAS];
				for (int j = 0; j < COLUMNAS; j++) {
					//usatioItem = new PorcentajeSimilitud(i, j);
					
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
		
//		public void pintarListaCompatibilidadOrdenada(int k) {
//			int i = 0;
//			for (PorcentajeSimilitud [] array: usuariosSimilaresOrdenados) {
//				System.out.println("usuario: " + i);
//				for (PorcentajeSimilitud porcentajeSimilitud : array) {
//					System.out.println(porcentajeSimilitud.toString());
//					k--;
//					if (k==0);
//						return;
//				}
//				i++;
//			}	
//		}
		
		
	public void pintarListaCompatibilidadOrdenada(int usuarios, int kUsuariosCompatibles) {
		PorcentajeSimilitud[] aux = usuariosSimilaresOrdenados.get(usuarios);
		for (int i = 0; i < kUsuariosCompatibles; i++) {
			System.out.println(aux[i].toString());
		}	
}
		
		
		/**
		 * @return **********************/
		
		private double[][] construirMatrizEstimacionVotos(int K){
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
		
		
//		/*Dado dos usuarios obtener el porcentaje de similitud entre ellos*/
//		private double getSimilitud (int usuarioX, int usuarioY){
//			return s.get(usuarioX)[usuarioY].getPorcentaje();
//		}
		
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
		
		private ArrayList<PorcentajeSimilitud> pintarMatrizEstimacionOrdenada(int usuario) {
			// TODO Auto-generated method stub
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
				// TODO Auto-generated constructor stub
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
			// TODO Auto-generated method stub
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
