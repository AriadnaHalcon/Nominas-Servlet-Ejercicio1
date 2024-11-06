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
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        String opcion = request.getParameter("opcion");
        
        // Verificar si 'opcion' es null o vacía antes de proceder
        if (opcion == null || opcion.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Opción no válida");
            return; // Terminar la ejecución si no se proporciona una opción válida
        }
        
        try {
            // Validación de la opción antes de procesar
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

            } else if (opcion.equals("consultarDni")) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/consultarDni.jsp");
                requestDispatcher.forward(request, response);

            } else {
                // Si la opción no es válida, enviar un error
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Opción no reconocida");
            }
        } catch (SQLException | DatosNoCorrectosException e) {
            e.printStackTrace();
            // Si ocurre una excepción, puedes mostrar una página de error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
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
            String dni = request.getParameter("dni");
            try {
                Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
                if (empleado != null) {
                    // Calcular el sueldo del empleado
                    int sueldo = empleado.getSueldo(); // Esto ahora devuelve el sueldo correctamente

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