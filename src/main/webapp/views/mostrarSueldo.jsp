<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Mostrar Sueldo</title>
</head>
<link rel="stylesheet" href="index.css">
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
    <form action="index.jsp" method="get" style="display:inline;">
       	<button type="submit">Volver al menú</button>
    </form>
</body>
</html>