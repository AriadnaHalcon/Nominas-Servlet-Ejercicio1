package com.app.Ejercicio2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.Ejercicio2.Exceptions.DatosNoCorrectosException;
import com.app.Ejercicio2.Laboral.Empleado;
import com.app.Ejercicio2.conexion.Conexion;

public class EmpleadoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;

	// guardar producto
	public boolean guardar(Empleado empleado) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql = "INSERT INTO empleados (dni, nombre, sexo, categoria, anyos) VALUES(?,?,?,?,?)";
			statement = connection.prepareStatement(sql);

			statement.setString(1, empleado.dni);
			statement.setString(2, empleado.nombre);
			statement.setString(3, String.valueOf(empleado.sexo));
			statement.setInt(4, empleado.getCategoria());
			statement.setInt(5, empleado.anyos);

			estadoOperacion = statement.executeUpdate() > 0;

			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	// editar empleado
	public boolean editar(Empleado empleado) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "UPDATE empleados SET nombre=?, sexo=?, categoria=?, anyos=? WHERE dni=?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, empleado.nombre);
			statement.setString(3, String.valueOf(empleado.sexo));
			statement.setInt(3, empleado.getCategoria());
			statement.setInt(4, empleado.anyos);

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	// listar empleado
	public List<Empleado> obtenerEmpleadosSueldo() throws SQLException, DatosNoCorrectosException {
		ResultSet resultSet = null;
		List<Empleado> listaEmpleados = new ArrayList<>();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
	        sql = "SELECT * FROM empleados";
	        statement = connection.prepareStatement(sql);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            Empleado empleado = new Empleado(
	                resultSet.getString("nombre"),
	                resultSet.getString("dni"),
	                resultSet.getString("sexo").charAt(0)
	            );
	            empleado.setCategoria(resultSet.getInt("categoria"));
	            empleado.anyos = resultSet.getInt("anyos");
	           	            
	            listaEmpleados.add(empleado);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return listaEmpleados;
	}

	// obtener lista de empleados y salarios
	public List<Empleado> obtenerEmpleados() throws SQLException, DatosNoCorrectosException {
		ResultSet resultSet = null;
		List<Empleado> listaEmpleados = new ArrayList<>();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
	        sql = "SELECT e.*, n.sueldo FROM empleados e LEFT JOIN nominas n ON e.dni = n.dni_empleado";
	        statement = connection.prepareStatement(sql);
	        resultSet = statement.executeQuery();

	        while (resultSet.next()) {
	            Empleado empleado = new Empleado(
	                resultSet.getString("nombre"),
	                resultSet.getString("dni"),
	                resultSet.getString("sexo").charAt(0)
	            );
	            empleado.setCategoria(resultSet.getInt("categoria"));
	            empleado.anyos = resultSet.getInt("anyos");
	            
	            int sueldo = resultSet.getInt("sueldo");
	            
	            System.out.println("Salario del empleado: " + sueldo);
	            
	            System.out.println("Empleado: " + empleado.dni + ", " + empleado.nombre + ", " + empleado.getCategoria());
	            
	            listaEmpleados.add(empleado);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return listaEmpleados;
	}

	// obtener empleado
	public Empleado obtenerEmpleado(String dni) throws SQLException, DatosNoCorrectosException {
		ResultSet resultSet = null;
		Empleado empleado = null;

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM empleados WHERE dni =?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, dni);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				empleado = new Empleado(resultSet.getString("nombre"),
						resultSet.getString("dni"), 
						resultSet.getString("sexo").charAt(0)
				);
				empleado.setCategoria(resultSet.getInt("categoria"));
				empleado.anyos = resultSet.getInt("anyos");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return empleado;
	}

	// obtener conexion pool
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
	}

}
