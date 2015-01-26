/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz implements Cloneable {
    private int[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new int[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new int[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    //cagp determinante de una matriz: extraido de http://www.sc.ehu.es/sbweb/fisica/cursoJava/numerico/matrices/codigo/Matriz.java
    public int determinante() throws DimensionesIncompatibles, CloneNotSupportedException{
        Matriz d=(Matriz)this.clone();
        int  deter = 0;
        if (d.getDimension().width!= d.getDimension().height)
        {
            // Error la matriz tiene que ser cuandrada
            throw new DimensionesIncompatibles("La matrices tiene que ser cuadrada para poder calcular su determinante");        

        }
        try
        {
            int dimension = d.getDimension().width;
            for(int k=0; k<dimension-1; k++){
                for(int i=k+1; i<dimension; i++){
                    for(int j=k+1; j<dimension; j++){
                        d.datos[i][j]-=d.datos[i][k]*d.datos[k][j]/d.datos[k][k];
                    }
                }
            }
            for(int i=0; i<dimension; i++){
               deter*=d.datos[i][i];
            }
        }
        catch(ArithmeticException e)
        {
            deter = 0;
        }
        return deter;
    }
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    } 
	
	//cagp: matriz inversa, codigo adaptado de http://www.sc.ehu.es/sbweb/fisica/cursoJava/numerico/matrices/codigo/Matriz.java
	public static Matriz inversa(Matriz d) throws DimensionesIncompatibles, CloneNotSupportedException{
            
		int n = d.getDimension().width; //dimension de la matriz
		Matriz a=(Matriz)d.clone();
                int determinante = 0;
                String error = "La matriz tiene que ser cuadrada y tener determinante no nulo para ser invertida";
                try
                {
                    determinante = a.determinante();
                 
                }
                catch (DimensionesIncompatibles | CloneNotSupportedException e)
                {
                    // Error la matriz tiene que ser cuandrada
                    determinante = 0;
                    error = error + ": "+e.toString();
                }
                if (determinante == 0)
                {
                    System.out.println("Determinante "+determinante);
                    throw new DimensionesIncompatibles(error); 
                }
 		Matriz b=new Matriz(n,n,false); //matriz de los terminos independientes
		Matriz c=new Matriz(n,n,false); //matriz de las incognitas
		//matriz unidad
		for(int i=0; i<n; i++){
			b.datos[i][i]=1;
		}
		//transformaci�n de la matriz y de los terminos independientes
		for(int k=0; k<n-1; k++){
			for(int i=k+1; i<n; i++){
				//terminos independientes
				for(int s=0; s<n; s++){
					b.datos[i][s]-=a.datos[i][k]*b.datos[k][s]/a.datos[k][k];
				}
				//elementos de la matriz
				for(int j=k+1; j<n; j++){
					a.datos[i][j]-=a.datos[i][k]*a.datos[k][j]/a.datos[k][k];
				}
			}
		}
		//c�lculo de las incognitas, elementos de la matriz inversa
		for(int s=0; s<n; s++){
			c.datos[n-1][s]=b.datos[n-1][s]/a.datos[n-1][n-1];
			for(int i=n-2; i>=0; i--){
				c.datos[i][s]=b.datos[i][s]/a.datos[i][i];
				for(int k=n-1; k>i; k--){
					c.datos[i][s]-=a.datos[i][k]*c.datos[k][s]/a.datos[i][i];
				}
			}
		}
		return c;
	} 
      @Override
      public Object clone() throws CloneNotSupportedException{
        int filas = this.getDimension().height;
        int columnas = this.getDimension().width;
        Matriz obj = new Matriz(filas,columnas,false);

        //aquí está la clave  para clonar la matriz bidimensional
        for (int i=0;i<filas;i++)
        {
            for (int j=0;i<columnas;i++)
            {
                obj.datos[i][j] = this.datos[i][j];
            }
        }
        return obj;
    }

    
	public static Matriz productoDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().height.equals(b.getDimension().width)) throw new DimensionesIncompatibles("La producto de matrices requiere matrices de dimensiones (n,m) y (m,n)");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = b.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
				for (k = 0; k < columnasA; i++) {
					matrizResultante.datos[i][j] += a.datos[i][k] * b.datos[k][j]; 
				}                
            } 
        } 
        return matrizResultante; 
    }
	

    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3d", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
}
