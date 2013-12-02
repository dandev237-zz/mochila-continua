package mochila;

import java.util.Arrays;

/**
 * Proyecto Java que implementa el problema de la mochila continua con un enfoque voraz.
 * 
 * Enunciado: Dada una mochila con una capacidad determinada y una serie de objetos caracterizados por
 * su valor y su peso, hallar una combinación de objetos que maximice el valor de los objetos contenidos
 * en la mochila, sin superar la capacidad de la misma. Podrán fraccionarse objetos en caso de ser necesario.
 * 
 * @author Daniel Alejandro Castro García
 */

public class Mochila {
	
	private float capacidad;
	
	public Mochila(float c){
		capacidad = c;
	}
	
	/**
	 * Método que recibe un array de tipo Objeto que representa los objetos a insertar en la mochila.
	 * Al finalizar, la Mochila quedará todo lo llena posible, atendiendo a un heurístico de seleccionar
	 * antes los objetos con mayor ratio valor/peso.
	 * 
	 * Complejidad del método llenarMochila: O(nlog n)
	 * Si el array de objetos llegase ordenado, la complejidad seria de orden lineal.
	 * 
	 * @param objetos	Array de objetos disponibles para insertar en la mochila
	 */
	private void llenarMochila(Objeto[] objetos){
		
		/*
		 * Esta llamada ordena el array de objetos en orden descendente atendiendo al parámetro
		 * valorPeso de cada objeto.
		 * 
		 * Complejidad del método Arrays.sort: O(nlog n)
		 * Referencia: http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#sort%28byte[]%29
		 */
		Arrays.sort(objetos, new ObjetoComparator());
		
		float pesoMochila = 0; //Indica el peso actual de la mochila
		float valorMochila = 0; //Indica el valor de los objetos contenidos en la mochila
		
		int i = 0;
		int[] solucion = new int[objetos.length];
		
		while((i < objetos.length) && (pesoMochila + objetos[i].getPeso() <= capacidad)){
			//podemos coger el objeto entero
			pesoMochila += (float)objetos[i].getPeso();
			valorMochila += (float)objetos[i].getValor();
			solucion[i] = i;
			i++;
		}
		if(i <= objetos.length && pesoMochila < capacidad){ //Quedan candidatos por considerar y hay hueco en la mochila
			//Hay que partir el objeto al que apunta i en objetos
			float capacidadRestante = capacidad - pesoMochila;
			
			valorMochila += (float)objetos[i].getValorPeso() * capacidadRestante;
			pesoMochila = capacidad; //La mochila queda llena después de coger el trozo
		}
		
		
		System.out.println("Capacidad de la mochila: " + capacidad);
		System.out.println("Peso de la mochila después del proceso: " + pesoMochila);
		System.out.println("Valor de la mochila después del proceso: " + valorMochila);
		System.out.print("Objetos insertados en la mochila: ");
		for(int j = 0; j < i; j++){
			System.out.print(solucion[j] + " ");
		}
	}
	
	
	/*
	 * Método para probar el correcto funcionamiento de la clase
	 */
	public static void main(String[] args){
		
		final int NUM_OBJETOS = 8;
		final float CAPACIDAD = 25;
		
		Objeto[] objetos = new Objeto[NUM_OBJETOS];
		float[] pesos = {2, 2, 4, 1, 2, 3, 12, 6};
		int[] valores = {4, 6, 8, 3, 6, 9, 12, 8};
		
		
		for(int i = 0; i < NUM_OBJETOS; i++){
			objetos[i] = new Objeto(pesos[i], valores[i]);
		}
		
		Mochila mochila = new Mochila(CAPACIDAD);
		
		mochila.llenarMochila(objetos);
	}

}
