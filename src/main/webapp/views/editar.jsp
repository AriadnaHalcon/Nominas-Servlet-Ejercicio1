<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Editar Empleado</title>
</head>
<body>
    <h2>Editar Empleado</h2>
    
    <form action="empleados" method="post">
        <input type="hidden" name="opcion" value="editar">
        <input type="hidden" name="dni" value="${empleado.dni}">

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${empleado.nombre}" required><br><br>

        <label for="sexo">Sexo:</label>
        <select id="sexo" name="sexo" required>
            <option value="M" <c:if test="${empleado.sexo == 'M'}"></c:if>>Masculino</option>
			<option value="F" <c:if test="${empleado.sexo == 'F'}"></c:if>>Femenino</option>
        </select><br><br>

        <label for="categoria">Categoría:</label>
        <input type="number" id="categoria" name="categoria" value="${empleado.categoria}" required><br><br>

        <label for="anyos">Años de Servicio:</label>
        <input type="number" id="anyos" name="anyos" value="${empleado.anyos}" required><br><br>

        <button type="submit">Guardar Cambios</button>
    </form>

    <br>
    <form action="index.jsp" method="get" style="display:inline;">
        <button type="submit">Volver al menú</button>
    </form>
</body>
</html>