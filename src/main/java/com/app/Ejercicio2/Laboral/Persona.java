package com.app.Ejercicio2.Laboral;

/**
 * Clase persona
 */
public class Persona {

	/**
	 * Propiedad nombre
	 */
	public String nombre;

	/**
	 * Propiedad dni
	 */
	public String dni;

	/**
	 * Propiedad sexo
	 */
	public char sexo;

	/**
	 * Constructor de la clase Persona con 3 parámetros
	 * 
	 * @param nombre establecer parámetro nombre
	 * 
	 * @param dni    establecer parámetro dni
	 * 
	 * @param sexo   establecer parámetro sexo
	 */
	public Persona(String nombre, String dni, char sexo) {
		this.nombre = nombre;
		this.dni = dni;
		this.sexo = sexo;
	}

	/**
	 * Constructor con dos unicos parámetros
	 * 
	 * @param nombre establecer parámetro nombre
	 * 
	 * @param sexo   establecer parámetro sexo
	 */
	public Persona(String nombre, char sexo) {
		this.nombre = nombre;
		this.sexo = sexo;
	}

	public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }
	
	public char getSexo() {
		return sexo;
	}

	/**
	 * Metodo set para la propiedad dni
	 * 
	 * @param dni establecer dni
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", dni=" + dni + "]";
	}

}
