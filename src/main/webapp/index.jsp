<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<link rel="stylesheet" href="index.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Patrick+Hand&display=swap" rel="stylesheet">
<body>
  <h1>Bienvenido</h1>
  <h2>Este es el menú de opciones disponibles</h2>
  <table border="1">
  	<tr>
  		<td><a href="empleados?opcion=listar">Mostrar informacion de la base de datos</a></td>
  	</tr>
  	<tr>
  		<td><a href="empleados?opcion=mostrarSalario">Mostrar el salario de un empleado</a></td>
  	</tr>
  	<tr>
  		<td><a href="empleados?opcion=modificar">Modificar un empleado</a></td>
  	</tr>
  </table>
</body>
</html>