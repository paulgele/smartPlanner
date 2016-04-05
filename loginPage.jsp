<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Planner</title>
</head>
<body>
	<h1>
		<label>Smart Planner</label><br>
	</h1>
	<h2>
		<label>A Personal Guide Through Your Day</label><br>
	</h2>

 		<c:if test="${LoginData.loginSuccess == 'false'}">
			<h2>ERROR: Login failed. Email Address/Password pair are incorrect.</h2>
		</c:if>
		
		<c:if test="${LoginData.createAcctResult == 'Pass'}">
			<h2>New account created.  Please login.</h2>
		</c:if>
		<c:if test="${LoginData.createAcctResult == 'Fail'}">
			<h2>ERROR: Account creation failed.  Either email address or password was not provided.</h2>
		</c:if>
		<c:if test="${LoginData.createAcctResult == 'Redundant'}">
			<h2>ERROR: Account creation failed.  An account with this email address already exists.</h2>
		</c:if>


		<form action="TheServlet" method="post">
			<label>Email Address</label><br>
			<input type="email" name="emailAddress"><br>

			<label>Password</label><br>
			<input type="password" name="passd"><br>

			<input type="hidden" name="state" value="form000Main">
			<input type="submit" value="Submit">
		</form>
		<br>
		<br>
		<br>
		<form action="TheServlet" method="post">
			<input type="hidden" name="state" value="form09createAcctForm">
			<input type="submit" value="Create Account">
		</form>
		
</body>
</html>