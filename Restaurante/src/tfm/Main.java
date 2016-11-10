package tfm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import tfm.Algoritmo.PorcentajeSimilitud;

/**
 * Clase principal que ejecuta el proceso main
 * 
 * @author Eddy Cuizaguana Cerpa
 *
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Inicio");

		ConexionSQL conexionSQL = new ConexionSQL();
		// conexionSQL.obtenerMatrizDeEntrada();
		// double[][] matriz = conexionSQL.getMATRIZ();
		// Algoritmo algoritmo = new Algoritmo(matriz, conexionSQL.getFILAS(),
		// conexionSQL.getCOLUMNAS());

		/**
		 * Si se quiere realizar la prueba utilizando otra matriz de entrada
		 * modificar las dos siguientes lineas
		 */
		double[][] matriz = Algoritmo.matrizPrueba;
		Algoritmo algoritmo = new Algoritmo(matriz, 6, 12);

		System.out.println("-------------------");
		algoritmo.crearMatrizItemsComunesEntreUsuarios();
		algoritmo.calculoMSD();
		algoritmo.crearListaCompatibilidadOrdenada();

		// cantidad de usarios con los que se quiere estimar
		int u = Algoritmo.FILAS - 1;
		algoritmo.construirMatrizEstimacionVotos(u);

		algoritmo.construirMatrizEstimacionOrdenada();

		menu(algoritmo, conexionSQL);

	}

	private static void menu(Algoritmo algoritmo, ConexionSQL conexionSQL) {

		int usuario = seleccionaOpcion("Introduce un usuario n [ 0, 1, 2,..., "
				+ Algoritmo.FILAS + " como máximo]",
				"Intenta introducir un número");
		int ususariosCompatibles = seleccionaOpcion(
				"Introduce la cantidad de usuarios compatibles que quiere ver [ "
						+ Algoritmo.FILAS + " como máximo]",
				"Intenta introducir un número");

		System.out.println("El Usuario " + usuario + "es compatible con:");
		algoritmo.pintarListaCompatibilidadOrdenada(usuario,
				ususariosCompatibles);
		System.out.println("Los posibles ítems que le pueden gustar son:");
		ArrayList<PorcentajeSimilitud> aux = algoritmo
				.pintarMatrizEstimacionOrdenada(usuario);
		System.out.println("Los items asociados son");
		conexionSQL.consutaItemsSegunId(aux);
		System.out
				.println("Los restaurantes recomendados segun los ítems son:");
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
				ok = true;
			} catch (InputMismatchException e) {
				System.out.println(mensajeError);
				sc.next();
			}

		} while (!ok);

		return opcion;
	}

}
