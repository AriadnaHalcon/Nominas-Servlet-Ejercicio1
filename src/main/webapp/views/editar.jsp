<%@ page contentType="text/html; charset=ISO-8859-1" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Empleado</title>
<style>
body {
    font-family: 'Patrick Hand', sans-serif;
    background-color: #f4f4f9;
    text-align: center;
    padding: 20px;
}

h2 {
    color: #4f4f4f;
    text-align: center;
    margin-top: 20px;
}

form {
    width: 80%;
    max-width: 500px;
    margin: 20px auto;
    padding: 20px;
    background-color: #f7c7d8;
    border-radius: 10px;
}

input, select {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #f1a0c6;
    border-radius: 10px;
}

input {
    width: 95%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #f1a0c6;
    border-radius: 10px;
}

input:focus, select:focus {
    outline: none;
    border-color: #f7b7d1;
}

button {
    background-color: #f7b7d1;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    font-size: 1rem;
    width: 30%;
}

button:hover {
    background-color: #f1a0c6;
}

form input[type="submit"] {
    margin-top: 20px;
}
</style>
</head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Patrick+Hand&display=swap"
	rel="stylesheet">
<body>
	<h2>Editar Empleado</h2>

	<form action="empleados" method="post">
		<input type="hidden" name="opcion" value="editar"> <input
			type="hidden" name="dni" value="${empleado.dni}"> <label
			for="nombre">Nombre:</label> <input type="text" id="nombre"
			name="nombre" value="${empleado.nombre}" required><br> <br>
		<label for="sexo">Sexo:</label> <select id="sexo" name="sexo" required>
			<option value="M" <c:if test="${empleado.sexo == 'M'}"></c:if>>Masculino</option>
			<option value="F" <c:if test="${empleado.sexo == 'F'}"></c:if>>Femenino</option>
		</select><br> <br> <label for="categoria">Categoría:</label> <input
			type="number" id="categoria" name="categoria"
			value="${empleado.categoria}" required><br> <br> <label
			for="anyos">Años de Servicio:</label> <input type="number" id="anyos"
			name="anyos" value="${empleado.anyos}" required><br> <br>

		<button type="submit">Guardar Cambios</button>
	</form>

	<br>
	<form action="index.jsp" method="get" style="display: inline;">
		<button type="submit">Volver al menú</button>
	</form>
</body>
</html>