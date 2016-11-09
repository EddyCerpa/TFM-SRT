package tfm;

import java.util.ArrayList;
import java.util.Arrays;

public class AlgoritmoObj {

	private double[][] matriz;
	private ArrayList<Integer>[][] matrizItemsComunes;
	private double matrizMSD[][];
	public ArrayList<PorcentajeSimilitud[]> listaPorsentajeSimilitud;

	static int FILAS;
	static int COLUMNAS;
	public static double[][] matrizPrueba = {
			{ 1, 2, -1, -1, 2, -1, 3, 4, -1, 4, 1, -1 },
			{ -1, -1, 1, 5, -1, 5, 3, 1, -1, 5, 2, 1 },
			{ 1, -1, -1, 2, -1, 1, -1, 3, 4, -1, -1, -1 },
			{ -1, 1, 4, 4, -1, -1, 3, -1, 5, 4, -1, 1 },
			{ 2, -1, 5, -1, 1, -1, 1, -1, -1, -1, 2, 1 },
			{ -1, -1, 5, 2, 1, -1, -1, 4, -1, 1, -1, 2 } };

	public static void main(String[] args) {

		ConexionSQL conexionSQL = new ConexionSQL();
		conexionSQL.conectar();
		double[][] matriz = conexionSQL.getMATRIZ();
		// matriz = matrizPrueba;
		AlgoritmoObj algoritmo = new AlgoritmoObj(matriz,
				conexionSQL.getFILAS(), conexionSQL.getCOLUMNAS());
		// AlgoritmoObj algoritmo = new AlgoritmoObj(matriz, 6, 12);
		algoritmo.obtencionDeItemsVotadosPorAmbosUsuarios();
		algoritmo.calculoMSD();
		algoritmo.crearListaCompatibilidadOrdenada();
		algoritmo.pintarListaCompatibilidadOrdenada();

	}

	public AlgoritmoObj(double[][] matriz, Integer filas, Integer columnas) {
		this.FILAS = filas;
		this.COLUMNAS = columnas;
		this.matriz = matriz;
		this.matrizItemsComunes = new ArrayList[FILAS][FILAS];
		this.matrizMSD = new double[FILAS][FILAS];
		listaPorsentajeSimilitud = new ArrayList();
		inicializaMatrizMSD();
		inicializarMatizItemsComunes();
	}

	private void inicializaMatrizMSD() {
		for (int u = 0; u < FILAS; u++) {
			for (int i = 0; i < FILAS; i++) {
				matrizMSD[u][i] = -1;
			}
		}
	}

	private void obtencionDeItemsVotadosPorAmbosUsuarios() {
		for (int u = 0; u < FILAS; u++) {
			for (int j = u + 1; j < FILAS; j++) {
				ArrayList<Integer> listaItem = new ArrayList<Integer>(3);
				for (int i = 0; i < COLUMNAS; i++) {
					double a = matriz[u][i];
					double b = matriz[j][i];

					if (a != -1)
						if (b != -1)
							listaItem.add(i);// añadimos el item que hayan
												// votado ambos usuarios

				}
				if (listaItem.isEmpty())
					listaItem.add(-1); // si no exiten items que hayan votado
										// ambos usuarios
				else
					matrizItemsComunes[u][j] = listaItem;
			}
		}
	}

	private void inicializarMatizItemsComunes() {
		for (int u = 0; u < FILAS; u++) {
			for (int i = 0; i < FILAS; i++) {
				matrizItemsComunes[u][i] = new ArrayList<Integer>(3);
			}

		}
	}

	private void calculoMSD() {
		int bxy = 0; // numero de items comunes
		double rxi = 0;
		double ryi = 0;
		double max = 0;
		double min = 6; // ponemos como minimo este valor porque es > max

		for (int u1 = 0; u1 < FILAS; u1++) {
			for (int u2 = u1 + 1; u2 < FILAS; u2++) {
				ArrayList<Integer> lista = matrizItemsComunes[u1][u2];
				bxy = lista.size();

				for (Integer item : lista) {
					double puntuacionItemU1 = matriz[u1][item];
					double puntuacionItemU2 = matriz[u2][item];
					// vemos maximo y minimo de los dos
					double maxAux = Math
							.max(puntuacionItemU1, puntuacionItemU2);
					double minAux = Math
							.min(puntuacionItemU1, puntuacionItemU2);

					max = Math.max(maxAux, max);
					min = Math.min(minAux, min);
				}
				double sum = 0;
				for (Integer item : lista) {
					double puntuacionItemU1 = matriz[u1][item];
					double puntuacionItemU2 = matriz[u2][item];
					sum = sum
							+ Math.pow(
									((puntuacionItemU1 - puntuacionItemU2) / (max - min)),
									2.0);
				}
				double similitud = 1.0 - ((1.0 / bxy) * sum);
				matrizMSD[u1][u2] = similitud;
			}
		}
	}

	private void crearListaCompatibilidadOrdenada() {
		for (int u1 = 0; u1 < FILAS; u1++) {
			ArrayList<PorcentajeSimilitud> porcentajeSimilituds = new ArrayList<PorcentajeSimilitud>();
			for (int u2 = 0; u2 < FILAS; u2++) {
				if (u1 != u2) {

					if (matrizMSD[u1][u2] != -1)
						porcentajeSimilituds.add(new PorcentajeSimilitud(u2,
								matrizMSD[u1][u2]));
					if (matrizMSD[u2][u1] != -1)
						porcentajeSimilituds.add(new PorcentajeSimilitud(u2,
								matrizMSD[u2][u1]));
				}
			}
			PorcentajeSimilitud[] array = porcentajeSimilituds
					.toArray(new PorcentajeSimilitud[porcentajeSimilituds
							.size()]);
			Arrays.sort(array);
			listaPorsentajeSimilitud.add(array);
		}
	}

	public void pintarListaCompatibilidadOrdenada() {
		int i = 0;

		for (PorcentajeSimilitud[] array : listaPorsentajeSimilitud) {
			System.out.println("usuario: " + i);
			for (PorcentajeSimilitud porcentajeSimilitud : array) {
				System.out.println(porcentajeSimilitud.toString());
			}
			i++;
		}

	}

	static class PorcentajeSimilitud implements Comparable<PorcentajeSimilitud> {
		int usuario = 0;
		double porcentaje = 0;

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
			return Integer.toString(usuario) + " "
					+ Double.toString(porcentaje);
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
