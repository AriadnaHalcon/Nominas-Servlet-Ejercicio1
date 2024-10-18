package com.app.Ejercicio2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.Ejercicio2.Exceptions.DatosNoCorrectosException;
import com.app.Ejercicio2.Laboral.Empleado;
import com.app.Ejercicio2.dao.EmpleadoDAO;

/**
 * Servlet implementation class EmpleadoController
 */
@WebServlet(description = "administra peticiones para la tabla empleados", urlPatterns = { "/empleados" })
public class EmpleadoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmpleadoController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");

        if (opcion.equals("crear")) {
            System.out.println("Usted ha presionado la opción mostrar empleados");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
            requestDispatcher.forward(request, response);

        } else if (opcion.equals("listar")) {
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            List<Empleado> lista = new ArrayList<>();

            try {
                lista = empleadoDAO.obtenerEmpleados();
                for (Empleado empleado : lista) {
                    System.out.println(empleado);
                }

                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException | DatosNoCorrectosException e) {
                e.printStackTrace();
            }

            System.out.println("Usted ha presionado la opción listar");

        } else if (opcion.equals("editar")) {
            String dni = request.getParameter("dni");
            System.out.println("Editar dni: " + dni);
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            Empleado emp = null;

            try {
                emp = empleadoDAO.obtenerEmpleado(dni);
                request.setAttribute("empleado", emp);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException | DatosNoCorrectosException e) {
                e.printStackTrace();
            }

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcion = request.getParameter("opcion");

        if (opcion.equals("guardar")) {
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            Empleado empleado = new Empleado(
                request.getParameter("nombre"),
                request.getParameter("dni"),
                request.getParameter("sexo").charAt(0)
            );

            try {
                empleado.setCategoria(Integer.parseInt(request.getParameter("categoria")));
            } catch (DatosNoCorrectosException e) {
                e.printStackTrace();
            }
            empleado.anyos = Integer.parseInt(request.getParameter("anyos"));

            try {
                empleadoDAO.guardar(empleado);
                System.out.println("Empleado guardado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("editar")) {
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            Empleado empleado = new Empleado(
                request.getParameter("nombre"),
                request.getParameter("dni"),
                request.getParameter("sexo").charAt(0)
            );

            try {
                empleado.setCategoria(Integer.parseInt(request.getParameter("categoria")));
            } catch (DatosNoCorrectosException e) {
                e.printStackTrace();
            }
            empleado.anyos = Integer.parseInt(request.getParameter("anyos"));

            try {
                empleadoDAO.editar(empleado);
                System.out.println("Empleado editado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}