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
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
}

h1, h2 {
    color: #4f4f4f; 
}


button {
    background-color: #f7b7d1; 
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 10px; 
    cursor: pointer;
    font-size: 1rem;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #f1a0c6; 
}


form {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}


table {
    width: 80%;
    margin-top: 20px;
    border-collapse: collapse;
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

tr:hover {
    background-color: #f1a0c6; 
}


a {
    color: #f7b7d1; 
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
    color: #f1a0c6; 
}


form input, form select {
    padding: 8px;
    margin-bottom: 10px;
    border-radius: 5px;
    border: 1px solid #f1a0c6;
    width: 100%;
    font-size: 1rem;
}

form input:focus, form select:focus {
    outline: none;
    border-color: #f7b7d1;
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
				</tr>
			</c:forEach>

		</tbody>

	</table>
	<form action="index.jsp" method="get" style="display: inline;">
		<button type="submit">Volver al menú</button>
	</form>
</body>
</html>