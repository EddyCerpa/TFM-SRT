package tfm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import tfm.AlgoritmoObj2.PorcentajeSimilitud;

public class ConexionSQL {
	 
    private String cadenaConexion = "jdbc:postgresql://192.168.43.41:5432/postgres?" + "user=tfm&password=postgres";
    private double MATRIZ [][];
    private Integer FILAS =0;
    private Integer COLUMNAS =0;
    
    
    public static void main(String[] arg) {	
    	ConexionSQL conexionSQL = new ConexionSQL();
    	conexionSQL.conectar();
    }

    
    
    public void consultaRestauranteSeunItem(ArrayList<PorcentajeSimilitud> aux){
    	Connection conexion = null;
        Statement sentencia = null;
        ResultSet resultado = null;
        
        String a="";
        /*Sentencia whwere para encontrar los items asociados al id*/
        for (PorcentajeSimilitud porcentajeSimilitud : aux) {
        	int n_item = porcentajeSimilitud.getUsuario();
			a+= "N="+n_item+ " OR ";
		}
        a = a.substring(0, (a.length()-4));
        
    	 try {
	            Class.forName("org.postgresql.Driver");
	            conexion = DriverManager.getConnection(cadenaConexion);
	            sentencia = conexion.createStatement();
	            String consultaSQL = "SELECT * FROM ITEMS INNER JOIN RESTAURANTE_ITEMS USING (id_item) where " + a;
	
	            resultado = sentencia.executeQuery(consultaSQL); 
	            while (resultado.next()) { 
	               int n_item = resultado.getInt("N");
	               String id_item= resultado.getString("ID_ITEM");
	               String id_restaurante= resultado.getString("ID_RESTAURANTE");
	               System.out.printf("%-20s%-20s%-20s\n", n_item, id_item, id_restaurante);
	               System.out.println();
	               
	            }
	            
	            System.out.println("Ranking restaurantes recomendados: ");
	            consultaSQL = "SELECT id_restaurante, count(*) as COUNT FROM ITEMS INNER JOIN RESTAURANTE_ITEMS USING (id_item) where " + a + "group by id_restaurante order by count DESC";
	        	
	            resultado = sentencia.executeQuery(consultaSQL); 
	            while (resultado.next()) { 
	               
	               String id_restaurante= resultado.getString("ID_RESTAURANTE");
	               int count = resultado.getInt("COUNT");
	               System.out.printf("%-20s%-20s\n", id_restaurante + ",", count);
	               System.out.println();
	               
	            }
	            
	            
         } catch (Exception e) {
             e.printStackTrace();
             conexion = null;
         } finally {
             if (resultado != null) {
                 try {
                     resultado.close();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
             if (sentencia != null) {
                 try {
                     sentencia.close();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
             if (conexion != null) {
                 try {
                     conexion.close();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }
    }
    
    
    public void consutaItemsSegunId (ArrayList<PorcentajeSimilitud> aux){
		Connection conexion = null;
        Statement sentencia = null;
        ResultSet resultado = null;
        String a="";
        /*Sentencia whwere para encontrar los items asociados al id*/
        for (PorcentajeSimilitud porcentajeSimilitud : aux) {
        	int n_item = porcentajeSimilitud.getUsuario();
			a+= "N="+n_item+ " OR ";
		}
        a = a.substring(0, (a.length()-4));
        
        try {
	            Class.forName("org.postgresql.Driver");
	            conexion = DriverManager.getConnection(cadenaConexion);
	            sentencia = conexion.createStatement();
	            String consultaSQL = "SELECT N, ID_ITEM FROM ITEMS Where " + a;
	
	            resultado = sentencia.executeQuery(consultaSQL);
	            while (resultado.next()) { 
	               int n_item = resultado.getInt("N");
	               String id_item= resultado.getString("ID_ITEM");
	                System.out.printf("%-20s%-20s\n", n_item, id_item);
	                System.out.println();
	            }
            } catch (Exception e) {
                e.printStackTrace();
                conexion = null;
            } finally {
                if (resultado != null) {
                    try {
                        resultado.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (sentencia != null) {
                    try {
                        sentencia.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    }
    
    
	public void conectar() {
		inicializarMatriz();
		Connection conexion = null;
        Statement sentencia = null;
        ResultSet resultado = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(cadenaConexion);
            sentencia = conexion.createStatement();
            String consultaSQL = "SELECT count (*) as N FROM  usuario_item_puntuacion";

            resultado = sentencia.executeQuery(consultaSQL);
            while (resultado.next()) {
            	FILAS = resultado.getInt("N"); 
               // String id = resultado.getString("ID_TIPO_VALORACION");
//                int nombres = resultado.getInt("CANTIDAD_OPINIONES");
//                int apellidos = resultado.getInt("NIVEL");
//              System.out.println( " " /*+ nombres + " " + apellidos*/ + FILAS);
            }
            
            consultaSQL = "SELECT count (*) as N FROM  items";
            resultado = sentencia.executeQuery(consultaSQL);
            while (resultado.next()) {
            	COLUMNAS = resultado.getInt("N"); 
//            	 System.out.println(COLUMNAS);
            }
            
            MATRIZ= new double[FILAS][COLUMNAS];
            inicializarMatriz();
            consultaSQL = "SELECT *  FROM  usuario_item_puntuacion";
            resultado = sentencia.executeQuery(consultaSQL);
            while (resultado.next()) {
            	 FILAS= resultado.getInt("N"); 
            	 COLUMNAS = resultado.getInt("ITEM");
            	 double puntuacion = resultado.getInt("PUNTUACION_GENERAL");
            	 MATRIZ [FILAS][COLUMNAS]= puntuacion;
//            	 System.out.println(FILAS + " "+ COLUMNAS+ " " + puntuacion);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            conexion = null;
        } finally {
            if (resultado != null) {
                try {
                    resultado.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}

	private void inicializarMatriz() {
		for (int i = 0; i < FILAS; i++) {
			for (int j = 0; j < COLUMNAS; j++) {
				MATRIZ [i][j] = -1;
			}
		}
	}

	public double[][] getMATRIZ() {
		return MATRIZ;
	}

	public Integer getFILAS() {
		return FILAS;
	}

	public Integer getCOLUMNAS() {
		return COLUMNAS;
	}
}