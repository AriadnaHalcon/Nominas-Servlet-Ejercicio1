package laboral;

/**
 * Clase Nómina
 */
public class Nomina {

	private static final int SUELDO_BASE[] = {50000, 70000, 90000, 110000, 130000, 150000, 170000,  190000, 210000,230000};
	
	/**
	 * Método para calcular el sueldo del empleado dependiendo de los años trabajados
	 * @param emp establece el parámetro empleado de la clase Empleado
	 * @return devuelve un número entero con el sueldo correcto
	 */
	public int sueldo(Empleado emp) {
		int sueldo = SUELDO_BASE[emp.getCategoria() - 1] + (5000*emp.anyos);
		return sueldo;
	}
	
	/**
	 * Constructor vacío de la clase Nómina
	 */
	public Nomina() {
		
	}
}
