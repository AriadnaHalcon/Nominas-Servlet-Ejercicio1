package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.EmpleadoDAO;
import exceptions.DatosNoCorrectosException;
import laboral.Empleado;



/**
 * Servlet implementation class EmpleadoController
 */
@WebServlet("/empleados")
public class EmpleadoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmpleadoController() {
        super();
    }

    // Método doGet
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();

        try {
            if (opcion.equals("listar")) {
                // Muestra empleados sin sueldo
                List<Empleado> lista = empleadoDAO.obtenerEmpleados();
                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
                requestDispatcher.forward(request, response);

            } else if (opcion.equals("listarSueldo")) {
                // Muestra empleados con sueldo
                List<Empleado> lista = empleadoDAO.obtenerEmpleadosSueldo();
                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listarSueldo.jsp");
                requestDispatcher.forward(request, response);

            } else if (opcion.equals("listarEditar")) {
                // Muestra la lista con botones para editar
                List<Empleado> lista = empleadoDAO.obtenerEmpleados();
                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editarEmpleado.jsp");
                requestDispatcher.forward(request, response);

            } else if (opcion.equals("editar")) {
                // Redirige a la página para editar un empleado
                String dni = request.getParameter("dni");
                Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
                request.setAttribute("empleado", empleado);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);

            } else if (opcion.equals("mostrarSueldo")) { // Añade aquí tu bloque
                String dni = request.getParameter("dni");
                int sueldo = empleadoDAO.obtenerSueldoPorDni(dni); // Asumiendo que tienes este método en tu DAO
                request.setAttribute("sueldo", sueldo);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/buscarSueldo.jsp");
                requestDispatcher.forward(request, response);
                
            } else if (opcion.equals("buscarEmpleado")) {
                // Recoger parámetros de búsqueda del formulario
                String dni = request.getParameter("dni");
                String nombre = request.getParameter("nombre");
                String sexo = request.getParameter("sexo");
                String categoriaStr = request.getParameter("categoria");
                Integer categoria = (categoriaStr != null && !categoriaStr.isEmpty()) ? Integer.parseInt(categoriaStr) : null;
                String anyosStr = request.getParameter("anyos");
                Integer anyos = (anyosStr != null && !anyosStr.isEmpty()) ? Integer.parseInt(anyosStr) : null;

                List<Empleado> listaEmpleados = empleadoDAO.buscarEmpleados(dni, nombre, sexo, categoria, anyos);
                
                request.setAttribute("listaEmpleados", listaEmpleados);
                
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/buscarEmpleado.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (SQLException | DatosNoCorrectosException e) {
            e.printStackTrace();
        }
    }

    // Método doPost
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();

        if (opcion.equals("editar")) {
            // Editar un empleado
            String dni = request.getParameter("dni");
            try {
                Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
                empleado.setNombre(request.getParameter("nombre"));
                empleado.setSexo(request.getParameter("sexo"));
                empleado.setCategoria(Integer.parseInt(request.getParameter("categoria")));
                empleado.setAnyos(Integer.parseInt(request.getParameter("anyos")));

                empleadoDAO.editar(empleado);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException | DatosNoCorrectosException e) {
                e.printStackTrace();
            }
        } else if (opcion.equals("mostrarSueldo")) {
            // Consultar sueldo de un empleado por DNI
        	System.out.println("Hola");
            String dni = request.getParameter("dni");
            try {
                // Obtener el empleado
                Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
                if (empleado != null) {
                    int sueldo = empleado.getSueldo(); 
                    request.setAttribute("sueldo", sueldo);
                    request.setAttribute("empleado", empleado);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/mostrarSueldo.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute("error", "Empleado no encontrado.");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/consultarDni.jsp");
                    requestDispatcher.forward(request, response);
                }
            } catch (SQLException | DatosNoCorrectosException e) {
                e.printStackTrace();
            }
        }
    }
}