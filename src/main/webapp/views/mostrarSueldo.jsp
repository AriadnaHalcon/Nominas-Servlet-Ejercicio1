<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mostrar Sueldo</title>
<style>
body {
    font-family: 'Patrick Hand', sans-serif;
    background-color: #f4f4f9;
    text-align: center;
    padding: 20px;
}

h1 {
    color: #4f4f4f;
    text-align: center;
    margin-top: 20px;
}

form {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

button {
    background-color: #f7b7d1;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    font-size: 1rem;
}

button:hover {
    background-color: #f1a0c6;
}
</style>
</head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Patrick+Hand&display=swap"
	rel="stylesheet">
<body>
	<h1>Detalles del Empleado</h1>
	<c:if test="${not empty empleado}">
		<p>Nombre: ${empleado.nombre}</p>
		<p>DNI: ${empleado.dni}</p>
		<p>Sexo: ${empleado.sexo}</p>
		<p>Sueldo: ${sueldo}</p>
	</c:if>
	<c:if test="${not empty error}">
		<p style="color: red;">${error}</p>
	</c:if>
	<form action="index.jsp" method="get" style="display: inline;">
		<button type="submit">Volver al menú</button>
	</form>
</body>
</html>