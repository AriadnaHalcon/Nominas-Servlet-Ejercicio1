<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Empleados</title>
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

table {
    width: 80%;
    margin-top: 30px;
    background-color: #f7c7d8;
    border-radius: 10px;
    overflow: hidden;
}

th, td {
    padding: 12px;
    text-align: left;
}

th {
    background-color: #f1a0c6;
    color: white;
}

tr:nth-child(even) {
    background-color: #f9d0e0;
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
	<h1>Lista de Empleados</h1>
	<table border="1">
		<thead>
			<tr>
				<th>DNI</th>
				<th>Nombre</th>
				<th>Sexo</th>
				<th>Categoría</th>
				<th>Años</th>
				<th>Sueldo</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="empleado" items="${lista}">
				<tr>
					<td><c:out value="${ empleado.dni}"></c:out></td>
					<td><c:out value="${ empleado.nombre}"></c:out></td>
					<td><c:out value="${ empleado.sexo}"></c:out></td>
					<td><c:out value="${ empleado.categoria}"></c:out></td>
					<td><c:out value="${ empleado.anyos}"></c:out></td>
					<td><c:out value="${ sueldo}"></c:out></td>
					</td>
				</tr>
			</c:forEach>

		</tbody>

	</table>
	<form action="index.jsp" method="get" style="display: inline;">
		<button type="submit">Volver al menú</button>
	</form>
</body>
</html>