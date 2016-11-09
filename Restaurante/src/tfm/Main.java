package tfm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import tfm.Algoritmo.PorcentajeSimilitud;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Inicio");
		
    	ConexionSQL conexionSQL = new ConexionSQL();
    	conexionSQL.conectar();
    	double[][]matriz = conexionSQL.getMATRIZ();
		Algoritmo algoritmo = new Algoritmo(matriz, conexionSQL.getFILAS(), conexionSQL.getCOLUMNAS()) ;
		
		
//		double[][] matriz = matrizPrueba;
//    	AlgoritmoObj2 algoritmo = new AlgoritmoObj2(matriz, 6, 12);
    	System.out.println("-------------------");
		algoritmo.crearMatrizItemsComunesEntreUsuarios();
		algoritmo.calculoMSD();
		algoritmo.crearListaCompatibilidadOrdenada();
		//algoritmo.pintarListaCompatibilidadOrdenada();
		
		int u = Algoritmo.FILAS-1;
		algoritmo.construirMatrizEstimacionVotos(u);//cantidad de usarios con los que se quiere estimar 
//		System.out.println("MATRIZ DE ESTIMACION!---------------------------------");
		//algoritmo.pintarMatrizEstimacion();
		//System.out.println("MATRIZ DE ESTIMACION ORDENADA!---------------------------------");
		algoritmo.construirMatrizEstimacionOrdenada();
		//algoritmo.pintarMatrizEstimacionOrdenada();
		
		menu(algoritmo, conexionSQL);
		
	}
	
	

	private static void menu(Algoritmo algoritmo, ConexionSQL conexionSQL) {

		
		int usuario = seleccionaOpcion("Introduce un usuario n [ 0, 1, 2,..., " + Algoritmo.FILAS + " como máximo]", "Intenta introducir un número");
		int ususariosCompatibles = seleccionaOpcion("Introduce la cantidad de usuarios compatibles que quiere ver [ " + Algoritmo.FILAS + " como máximo]", "Intenta introducir un número");

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


}
