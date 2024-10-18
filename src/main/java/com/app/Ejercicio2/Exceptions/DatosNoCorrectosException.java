package com.app.Ejercicio2.Exceptions;

/**
 * Clase DatosNoCorrectosException que extiende de la herramienta Exception
 */
public class DatosNoCorrectosException extends Exception{
	
	/**
	 * Constructor vacío para la excepcion 
	 */
	public DatosNoCorrectosException() {
		super();
	}

	/**
	 * Constructor con mensaje para la excepción
	 * @param message establece el mensaje para el constructor
	 */
	public DatosNoCorrectosException(String message) {
		super(message);
	}
	
}
