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
		<table>
			<tr><th>Account = ${userID}</th></tr>
		</table>
		<br>

		<form action="TheServlet" method="post">
			<input type="hidden" name="state" value="form08logOut">
			<input type="submit" value="Logout">
		</form>
  		<h1>
			<label for="scheduledTasks">Smart Calendar Main Page</label><br>
			<!-- <input type="text" class="form-control" id="textLastName" name="LastName"><br>  -->
		</h1>
		
		<h2>
			<label for="scheduledTasks">Wizard Tasks</label><br>
		</h2>

				<form action="TheServlet" method="post">
					<input type="hidden" name="state" value="form00GuideFirstPage">
					<input type="submit" value="Task Wizard"> WARNING: Re-running this function after the first time will cancel & replace all Wizard tasks created earlier.
				</form>
				<br>
				<form action="TheServlet" method="post">
					<input type="hidden" name="state" value="form04renderCalendar">
					<input type="submit" value="View Calendar">
				</form>
				<br>
				<form action="TheServlet" method="post">
					<input type="hidden" name="state" value="form05editCalendarForm">
					<input type="submit" value="Edit/Delete Calendar Wizard Tasks">
				</form>
				<br><br>
				
		<h2>
			<label for="scheduledTasks">User Defined Tasks</label><br>
		</h2>
				<form action="TheServlet" method="post">
					<input type="hidden" name="state" value="form10UserDefinedTaskForm">
					<input type="submit" value="Create User Defined Tasks">
				</form>
				<br>
				<form action="TheServlet" method="post">
					<input type="hidden" name="state" value="form11renderUserCalendar">
					<input type="submit" value="View UserCalendar">
				</form>
</body>
</html>