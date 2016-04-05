<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Smart Calendar</title>
</head>
<body>
	<h1>
		<label>Smart Calendar: Account Creation Page</label><br><br>
	</h1>

	<form role="form" action="TheServlet" method="post">

		<label for="textUsername">Email Address: </label>
		<input type="email" name="newUserID"><br>

		<label for="textPassword">Password: </label>
		<input type="password" name="newPassWd"><br>

		<input type="hidden" name="state" value="form09createAcct"><br>
		<input type="submit" value="Submit"><br>
	</form>
</body>
</html>