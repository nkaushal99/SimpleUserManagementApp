<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>User Management Application</title>
	</head>
	<body>
		<a href="<%=request.getContextPath() %>/list">Users</a>
	
		<c:if test="${user != null}">
			<form action="update" method="post">
		</c:if>
		<c:if test="${user == null}">
			<form action="insert" method="post">
		</c:if>

		<caption>
			<h2>
				<c:if test="${user != null}">
          			Edit User
          		</c:if>
				<c:if test="${user == null}">
          			Add New User
          		</c:if>
			</h2>
		</caption>

		<c:if test="${user != null}">
			<input type="hidden" name="id" value="<c:out value='${user.id}' />" >
		</c:if>
		
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" value="<c:out value='${user.username}' />" placeholder="enter here" name="username" required="required"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type="email" value="<c:out value='${user.email}' />" placeholder="enter here" name="email"></td>
			</tr>
			<tr>
				<td>Country:</td>
				<td><input type="text" value="<c:out value='${user.country}' />" placeholder="enter here" name="country"></td>
			</tr>
			
		</table>
		<button type="submit">Save</button>
		</form>

	</body>
</html>