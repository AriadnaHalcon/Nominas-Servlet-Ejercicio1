package laboral;

import exceptions.DatosNoCorrectosException;

/**
 * Clase Empleado que extiende de la clase Persona
 */
public class Empleado extends Persona{
	
	
	private int categoria;
	
	/**
	 * Propiedad anyos trabajados
	 */
	public int anyos;
	
	private int sueldo;

	/**
	 * Constructor de la clase Empleado con 3 parámetros
	 * @param nombre establecer nombre
	 * @param dni establecer dni
	 * @param sexo establecer sexo
	 */
	public Empleado(String nombre, String dni, String sexo) {
		super(nombre, dni, sexo);
		this.categoria = 1;
		this.anyos = 0;
	}

	/**
	 * Constructor de la clase Empleado con 5 parámetros
	 * @param nombre establecer nombre
	 * @param dni establecer dni
	 * @param sexo establecer sexo
	 * @param categoria establecer categoría 
	 * @param anyos establecer años
	 */
	public Empleado(String nombre, String dni, String sexo, int categoria, int anyos) {
		super(nombre, dni, sexo);
		this.categoria = categoria;
		this.anyos = anyos;
	}

	/**
	 * Método get para la propiedad categoría
	 * @return devuelve la propiedad categoría
	 */
	public int getCategoria() {
		return categoria;
	}
	
	

	public int getAnyos() {
		return anyos;
	}
	
	

	public void setAnyos(int anyos) {
		this.anyos = anyos;
	}
	
	public int getSueldo() {
        Nomina nomina = new Nomina();
        return nomina.sueldo(this); 
    }

	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}

	/**
	 * Método set para la propiedad categoría
	 * @param categoria establece la categoría
	 * @throws DatosNoCorrectosException lanza una excepción si la categoría no esta en un número entre el 1 y el 10
	 */
	public void setCategoria(int categoria) throws DatosNoCorrectosException{
		this.categoria = categoria;
		if(categoria < 1 && categoria > 10) {
			throw new DatosNoCorrectosException();
		}
	}
	
	/**
	 * Método para incrementar los años trabajados
	 * @throws DatosNoCorrectosException lanza una excepción si el año es un número negativo
	 */
	public void incrAnyo() throws DatosNoCorrectosException{
		if(anyos < 0) {
			throw new DatosNoCorrectosException();
		} else {
			anyos++;
		}
	}

	/**
	 * Método para imprimir los datos del empleado
	 * @return devuelve una cadena de texto con cada uno de los datos del empleado
	 */
	public String imprime() {
		return "Empleado [categoria=" + categoria + ", anyos=" + anyos + ", nombre= " + nombre + ", dni= " + dni + ", sexo= " + sexo + "]";
	}
 
	
}
