package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import exceptions.DatosNoCorrectosException;
import laboral.Empleado;
import laboral.Nomina;

public class EmpleadoDAO {
    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacion;

    // Guardar empleado
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

    // Editar empleado
    public boolean editar(Empleado empleado) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);
            sql = "UPDATE empleados SET nombre=?, sexo=?, categoria=?, anyos=? WHERE dni=?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, empleado.nombre);
            statement.setString(2, String.valueOf(empleado.sexo));
            statement.setInt(3, empleado.getCategoria());
            statement.setInt(4, empleado.anyos);
            statement.setString(5, empleado.dni);

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

    // Listar empleados con su sueldo
    public List<Empleado> obtenerEmpleadosSueldo() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM Empleados"; 
        
        try (Connection con = Conexion.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String sexo = rs.getString("sexo");
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");
                
                Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
                empleado.setSueldo(new Nomina().sueldo(empleado)); 
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }


    
    public int obtenerSueldoPorDni(String dni) throws SQLException {
        int sueldo = 0;
        String sql = "SELECT sueldo FROM nominas WHERE dni_empleado = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sueldo = resultSet.getInt("sueldo");
            }
        }
        return sueldo;
    }

    // Obtener lista de empleados
    public List<Empleado> obtenerEmpleados() throws SQLException, DatosNoCorrectosException {
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
                    resultSet.getString("sexo")
                );
                empleado.setCategoria(resultSet.getInt("categoria"));
                empleado.anyos = resultSet.getInt("anyos");
                listaEmpleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
            if (statement != null) try { statement.close(); } catch (SQLException e) {}
            if (connection != null) try { connection.close(); } catch (SQLException e) {}
        }
        return listaEmpleados;
    }

    // Obtener empleado por DNI
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
                empleado = new Empleado(
                    resultSet.getString("nombre"),
                    resultSet.getString("dni"), 
                    resultSet.getString("sexo")
                );
                empleado.setCategoria(resultSet.getInt("categoria"));
                empleado.anyos = resultSet.getInt("anyos");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleado;
    }
    
    public List<Empleado> buscarEmpleados(String dni, String nombre, String sexo, Integer categoria, Integer anyos) throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        
        // Construir la consulta SQL dinámicamente
        StringBuilder sql = new StringBuilder("SELECT * FROM empleados WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        if (dni != null && !dni.trim().isEmpty()) {
            sql.append(" AND dni LIKE ?");
            parametros.add("%" + dni.trim() + "%");
        }
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            parametros.add("%" + nombre.trim() + "%");
        }
        
        if (sexo != null) {
            sql.append(" AND sexo = ?");
            parametros.add(sexo);
        }
        
        if (categoria != null) {
            sql.append(" AND categoria = ?");
            parametros.add(categoria);
        }
        
        if (anyos != null) {
            sql.append(" AND anyos = ?");
            parametros.add(anyos);
        }

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                statement.setObject(i + 1, parametros.get(i));
            }
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Empleado empleado = new Empleado(
                    resultSet.getString("nombre"),
                    resultSet.getString("dni"),
                    resultSet.getString("sexo"),
                    resultSet.getInt("categoria"),
                    resultSet.getInt("anyos")
                );
                lista.add(empleado);
            }
        }
        
        return lista;
    }

    // Obtener conexión a la base de datos
    private Connection obtenerConexion() throws SQLException {
        return Conexion.getConnection();
    }
}
