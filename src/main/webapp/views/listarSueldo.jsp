<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Empleados</title>
</head>
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
					<td><c:out value="${ empleado.sueldo}"></c:out></td>
					</td>
				</tr>
			</c:forEach>
            
		</tbody>
		
	</table>
	<form action="index.jsp" method="get" style="display:inline;">
        <button type="submit">Volver al menú</button>
    </form>
</body>
</html>