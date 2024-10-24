<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Consultar DNI para el sueldo</title>
</head>
<body>
    <form action="empleados" method="post">
        <input type="hidden" name="opcion" value="mostrarSueldo" />
        <label for="dni">Ingrese el DNI del empleado:</label>
        <input type="text" id="dni" name="dni" required />
        <button type="submit">Consultar</button>
    </form>
    <form action="index.jsp" method="get" style="display:inline;">
        <button type="submit">Volver al menú</button>
    </form>
</body>
</html>