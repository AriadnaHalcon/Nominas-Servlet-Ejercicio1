package laboral;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.DatosNoCorrectosException;

import java.sql.*;

/**
 * Clase con método main para probar las clases creadas en el proyecto
 */
public class CalculaNominas {

	/**
	 * Método main para la prueba de métodos
	 * @param args Argumentos de la clase
	 * @throws DatosNoCorrectosException Excepción para lanzar error en caso de que haya datos incorrectos
	 * @throws IOException Maneja los errores de entrada o salida
	 * @throws SQLException Maneja los errores relacionados con la base de datos
	 */
	public static void main(String[] args) throws DatosNoCorrectosException, IOException, SQLException {

		List<Empleado> empleados = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int opcion;

		BufferedReader br = new BufferedReader(new FileReader("empleados.txt"));
		String linea = null;

		while ((linea = br.readLine()) != null) {
			String[] cadena = linea.split(",");

			String nombre = cadena[0].trim();
			String dni = cadena[1].trim();
			String sexo = cadena[2].trim();
			int categoria = Integer.parseInt(cadena[3].trim());
			int anyos = Integer.parseInt(cadena[4].trim());

			empleados.add(new Empleado(nombre, dni, sexo, categoria, anyos));
		}
		br.close();

		escribeBinario(empleados);

		do {
			System.out.println("Menú principal:");
			System.out.println("1. Mostrar empleados");
			System.out.println("2. Mostrar sueldo de un empleado por DNI");
			System.out.println("3. Editar empleados");
			System.out.println("4. Recalcular sueldo de un empleado");
			System.out.println("5. Recalcular sueldos de todos los empleados");
			System.out.println("6. Alta de empleados (individual o por lotes)");
			System.out.println("7. Realizar copia de seguridad de la base de datos en ficheros");
			System.out.println("8. Eliminar empleado d la base de datos");
			System.out.println("0. Salir");
			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				mostrarEmpleados();
				break;
			case 2:
				mostrarSueldoEmpleado();
				break;
			case 3:
				mostrarSubmenu();
				break;
			case 4:
				String dni = sc.next();
				recalcularSueldoEmpleado(dni);
				break;
			case 5:
				recalcularSueldosTodosLosEmpleados();
				break;
			case 6:
				altaEmpleado();
				break;
			case 7:
				realizarCopiaDeSeguridad();
				break;
			case 8:
				System.out.println("Dime el DNI del empleado que se va a eliminar:");
				String dniEliminar = sc.nextLine();
				eliminarEmpleado(dniEliminar);
				break;
			case 0:
				System.out.println("Cerrando programa, adiós.");
				break;
			default:
				System.out.println("Debes insrtar un número del 0 al 7.");
			}
		} while (opcion != 0);

	}

	private static void escribeBinario(List<Empleado> empleados) throws IOException {
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("sueldos.dat"));
		Nomina nomina = new Nomina();
		for (Empleado emp : empleados) {
			String dni = emp.dni;
			int sueldo = nomina.sueldo(emp);
			o.writeUTF(dni);
			o.writeInt(sueldo);
		}
		o.close();
		System.out.println("Se ha escrito el archivo binario.");

	}

	private static void mostrarEmpleados() throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";
		String query = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleados";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String dni = rs.getString("dni");
				char sexo = rs.getString("sexo").charAt(0);
				int categoria = rs.getInt("categoria");
				int anyos = rs.getInt("anyos");
				System.out.println("Nombre: " + nombre + ", DNI: " + dni + ", Sexo: " + sexo + ", Categoría: "
						+ categoria + ", Años trabajados: " + anyos);
			}
		}
	}

	private static void mostrarSueldoEmpleado() throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el DNI del empleado:");
		String dni = sc.nextLine();

		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";
		String query = "SELECT sueldo FROM Nominas WHERE dni_empleado = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, dni);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int sueldo = rs.getInt("sueldo");
				System.out.println("Sueldo del empleado con DNI " + dni + ": " + sueldo);
			} else {
				System.out.println("No se encontró el sueldo para el DNI " + dni + ".");
			}
		}
	}

	private static void mostrarSubmenu() throws SQLException, DatosNoCorrectosException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce el DNI del empleado a modificar: ");
		String dni = sc.nextLine();

		Empleado emp = obtenerEmpleadoPorDni(dni);
		if (emp == null) {
			System.out.println("Empleado no encontrado.");
			return;
		}

		int opcion;
		do {
			System.out.println("Modificar datos del empleado: " + emp.nombre + " (" + emp.dni + ")");
			System.out.println("1. Modificar nombre");
			System.out.println("2. Modificar sexo (F/M)");
			System.out.println("3. Modificar categoría");
			System.out.println("4. Modificar años trabajados");
			System.out.println("0. Volver al menú principal");

			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				System.out.println("Introduce el nuevo nombre: ");
				String nuevoNombre = sc.nextLine();
				emp.nombre = nuevoNombre;
				actualizarEmpleadoEnBD(emp);
				break;
			case 2:
				System.out.println("Introduce el nuevo sexo (M/F): ");
				String nuevoSexo = sc.nextLine().toUpperCase();
				emp.sexo = nuevoSexo;
				actualizarEmpleadoEnBD(emp);
				break;
			case 3:
				System.out.println("Introduce la nueva categoría (1-10): ");
				int nuevaCategoria = sc.nextInt();
				emp.setCategoria(nuevaCategoria);
				recalcularActualizarSueldo(emp);
				actualizarEmpleadoEnBD(emp);
				break;
			case 4:
				System.out.println("Introduce los nuevos años trabajados: ");
				int nuevosAnyos = sc.nextInt();
				emp.anyos = nuevosAnyos;
				recalcularActualizarSueldo(emp);
				actualizarEmpleadoEnBD(emp);
				break;
			case 0:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Debes insertar un número del 0 al 4.");
			}
		} while (opcion != 0);
	}

	private static Empleado obtenerEmpleadoPorDni(String dni) throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";
		String query = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleados WHERE dni = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, dni);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String nombre = rs.getString("nombre");
				String sexo = rs.getString("sexo");
				int categoria = rs.getInt("categoria");
				int anyos = rs.getInt("anyos");
				return new Empleado(nombre, dni, sexo, categoria, anyos);
			} else {
				return null;
			}
		}
	}

	private static void actualizarEmpleadoEnBD(Empleado emp) throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";
		String query = "UPDATE Empleados SET nombre = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, emp.nombre);
			ps.setString(2, String.valueOf(emp.sexo));
			ps.setInt(3, emp.getCategoria());
			ps.setInt(4, emp.anyos);
			ps.setString(5, emp.dni);
			ps.executeUpdate();
		}
	}

	private static void recalcularSueldoEmpleado(String dni) throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";

		Nomina nomina = new Nomina();

		String querySelect = "SELECT categoria, anyos FROM Empleados WHERE dni = ?";
		String queryUpdate = "UPDATE Nominas SET sueldo = ? WHERE dni_empleado = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement psSelect = conn.prepareStatement(querySelect);
				PreparedStatement psUpdate = conn.prepareStatement(queryUpdate)) {

			psSelect.setString(1, dni);
			ResultSet rs = psSelect.executeQuery();

			if (rs.next()) {
				int categoria = rs.getInt("categoria");
				int anyos = rs.getInt("anyos");
				int nuevoSueldo = nomina.sueldo(new Empleado("", dni, "", categoria, anyos));

				psUpdate.setInt(1, nuevoSueldo);
				psUpdate.setString(2, dni);
				psUpdate.executeUpdate();

				System.out.println("Sueldo actualizado para el empleado con DNI: " + dni);
			} else {
				System.out.println("No se encontró el empleado con DNI: " + dni);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se ha podido recalcular el sueldo del empleado.");
		}
	}

	private static void recalcularSueldosTodosLosEmpleados() throws SQLException {
		List<Empleado> empleados = obtenerTodosLosEmpleados();
		for (Empleado emp : empleados) {
			recalcularActualizarSueldo(emp);
		}
		System.out.println("Sueldos recalculados para todos los empleados.");
	}

	private static void altaEmpleado() throws SQLException, DatosNoCorrectosException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quieres dar de alta un empleado individual o por lotes? (i/l): ");
		char opcion = sc.nextLine().toLowerCase().charAt(0);

		Nomina nomina = new Nomina();

		if (opcion == 'i') {
			System.out.println("Introduce los datos del empleado (nombre, dni, sexo, categoría, años trabajados):");
			String nombre = sc.nextLine();
			String dni = sc.nextLine();

			String sexo;
			while (true) {
				System.out.print("Sexo (F/M): ");
				sexo = sc.nextLine().toUpperCase();
				if (sexo == "F" || sexo == "M") {
					break;
				} else {
					System.out.println("Introduce un sexo válido (F o M).");
				}
			}

			int categoria = sc.nextInt();
			int anyos = sc.nextInt();
			Empleado nuevoEmpleado = new Empleado(nombre, dni, sexo, categoria, anyos);
			insertarEmpleadoEnDB(nuevoEmpleado);

			int sueldo = nomina.sueldo(nuevoEmpleado);
			insertarNominaEnDB(dni, sueldo);

		} else if (opcion == 'l') {
			BufferedReader br = new BufferedReader(new FileReader("empleadosNuevos.txt"));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(",");
				String sexoArchivo = datos[2];
				if (sexoArchivo != "F" && sexoArchivo != "M") {
					System.out.println("Sexo inválido para el empleado " + datos[0]);
					continue;
				}
				Empleado nuevoEmpleado = new Empleado(datos[0], datos[1], sexoArchivo, Integer.parseInt(datos[3]),
						Integer.parseInt(datos[4]));
				insertarEmpleadoEnDB(nuevoEmpleado);

				int sueldo = nomina.sueldo(nuevoEmpleado);
				insertarNominaEnDB(nuevoEmpleado.dni, sueldo);
			}
			br.close();
			System.out.println("Se han dado de alta a los empleados correctamente.");
		} else {
			System.out.println("No se ha podido dar de alta a los empleados.");
		}
	}

	private static void realizarCopiaDeSeguridad() throws IOException, SQLException {
		List<Empleado> empleados = obtenerTodosLosEmpleados();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("empleados_backup.dat"));
		oos.writeObject(empleados);
		oos.close();
		System.out.println("Copia de seguridad realizada correctamente.");
	}

	private static List<Empleado> obtenerTodosLosEmpleados() throws SQLException {
		List<Empleado> empleados = new ArrayList<>();
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";
		String query = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleados";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String dni = rs.getString("dni");
				String sexo = rs.getString("sexo");
				int categoria = rs.getInt("categoria");
				int anyos = rs.getInt("anyos");
				Empleado emp = new Empleado(nombre, dni, sexo, categoria, anyos);
				empleados.add(emp);
			}
		}
		return empleados;
	}

	private static void recalcularActualizarSueldo(Empleado emp) throws SQLException {
		Nomina nomina = new Nomina();
		int nuevoSueldo = nomina.sueldo(emp);
		actualizarSueldo(emp.dni, nuevoSueldo);
	}

	private static void actualizarSueldo(String dni, int nuevoSueldo) throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";

		String querySelect = "SELECT COUNT(*) FROM Nominas WHERE dni_empleado = ?";
		String queryUpdate = "UPDATE Nominas SET sueldo = ? WHERE dni_empleado = ?";
		String queryInsert = "INSERT INTO Nominas (dni_empleado, sueldo) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement psSelect = conn.prepareStatement(querySelect);
				PreparedStatement psUpdate = conn.prepareStatement(queryUpdate);
				PreparedStatement psInsert = conn.prepareStatement(queryInsert)) {

			psSelect.setString(1, dni);
			ResultSet rs = psSelect.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			if (count > 0) {
				psUpdate.setInt(1, nuevoSueldo);
				psUpdate.setString(2, dni);
				psUpdate.executeUpdate();
				System.out.println("Sueldo actualizado para el empleado con DNI: " + dni);
			} else {
				psInsert.setString(1, dni);
				psInsert.setInt(2, nuevoSueldo);
				psInsert.executeUpdate();
				System.out.println("Sueldo insertado para el empleado con DNI: " + dni);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se pudo actualizar o insertar el sueldo del empleado.");
		}
	}

	private static void insertarEmpleadoEnDB(Empleado emp) throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";
		String query = "INSERT INTO Empleados (nombre, dni, sexo, categoria, anyos) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, emp.nombre);
			ps.setString(2, emp.dni);
			ps.setString(3, String.valueOf(emp.sexo));
			ps.setInt(4, emp.getCategoria());
			ps.setInt(5, emp.anyos);
			ps.executeUpdate();
		}
	}

	private static void insertarNominaEnDB(String dni, int sueldo) throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";

		String query = "INSERT INTO Nominas (dni_empleado, sueldo) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, dni);
			ps.setInt(2, sueldo);
			ps.executeUpdate();

			System.out.println("Se ha insertado el sueldo para el empleado con DNI: " + dni);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se ha podido insertar el sueldo.");
		}
	}

	private static void eliminarEmpleado(String dni) throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/ejercicio2";
		String user = "root";
		String password = "123456";

		String queryDeleteNomina = "DELETE FROM Nominas WHERE dni_empleado = ?";
		String queryDeleteEmpleado = "DELETE FROM Empleados WHERE dni = ?";

		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			conn.setAutoCommit(false);

			try (PreparedStatement psDeleteNomina = conn.prepareStatement(queryDeleteNomina)) {
				psDeleteNomina.setString(1, dni);
				int nomAfectada = psDeleteNomina.executeUpdate();

				if (nomAfectada > 0) {
					System.out.println("El empleado con DNI " + dni + " ha sido eliminado de la tabla nominas.");
				} else {
					System.out.println("No se encontraron registros de Nominas para el empleado con DNI " + dni + ".");
				}
			}

			try (PreparedStatement psDeleteEmpleado = conn.prepareStatement(queryDeleteEmpleado)) {
				psDeleteEmpleado.setString(1, dni);
				int empAfectado = psDeleteEmpleado.executeUpdate();

				if (empAfectado > 0) {
					System.out.println("El empleado con DNI " + dni + " ha sido eliminado de la tabla empleados.");
				} else {
					System.out.println("No se ha encontrado al empleado con DNI " + dni + ".");
				}
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se ha podido eliminar al empleado con DNI " + dni);
		}
	}

	/**
	 * Constructor vacío para la clase con método main CalculaNominas
	 */
	public CalculaNominas() {
		super();
	}
	
	
}
