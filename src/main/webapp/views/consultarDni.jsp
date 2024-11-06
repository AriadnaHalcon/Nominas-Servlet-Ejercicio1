<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Consultar DNI para el sueldo</title>
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

input {
    width: 95%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #f1a0c6;
    border-radius: 10px;
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
	<form action="empleados" method="post">
		<input type="hidden" name="opcion" value="mostrarSueldo" /> <label
			for="dni">Ingrese el DNI del empleado:</label> <input type="text"
			id="dni" name="dni" required />
		<button type="submit">Consultar</button>
	</form>
	<br>
	<form action="index.jsp" method="get" style="display: inline;">
		<button type="submit">Volver al menú</button>
	</form>
</body>
</html>