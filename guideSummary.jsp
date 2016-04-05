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

<!--  **[${x}]***  -->
	<form action="TheServlet" method="post">
		<input type="hidden" name="state" value="form07calendarMainPage">
		<input type="submit" value="Smart Calendar Main Page">
	</form>
	<form action="TheServlet" method="post">
		<input type="hidden" name="state" value="form08logOut">
		<input type="submit" value="Logout">
	</form>
	
	<form role="form" action="TheServlet" method="post">
  		<div>	<!-- <div class="form-group">  -->
  		<h1>
			<label for="guideSummary">Summary of Selected Tasks</label><br>
		</h1>
		<h2>
			<label>The "recurrence interval" offers a default value which you can change. </label><br>
			<!-- <input type="text" class="form-control" id="textLastName" name="LastName"><br>  -->
		</h2>
	
				<c:forEach var="item" items="${htmlList}">
					<br>
					${item.taskName}<br>
														<input type="hidden" name="${item.taskName}"  value="<c:out value='${item.taskName}' />" > 
					Task recurrence  interval (months):	<input type="text"   name="${item.taskName}"  value="<c:out value='${item.reschedulePeriod}' />" >, 
					Data(Format: YYYY-MM-DD): 			<input type="text"   name="${item.taskName}" >, 
					Start Time (Format: HH:MM): 		<input type="text"   name="${item.taskName}" ><br>
					Repeat Type:						<input type="radio"  name="${item.taskName}"  value="Same Day-of-the-Month Each Month" checked> Same Day-of-the-Month Each Month (ie, The 20th of each month)
														<input type="radio"  name="${item.taskName}"  value="Same Weekday Each Month"> Same Weekday Each Month (ie, The 2nd Tuesday of each month)
					<br>

<!--
	<tr>
		<td>${item.status}<br></td>
		<td>${item.reschedulePeriod}<br></td>
		<td>${item.taskName}<br></td>
	</tr>
-->
				</c:forEach>

		</div>
		
		<div>	<!-- <div class="form-group">  -->
			<input type="hidden" name="state" value="form03guideSummary"><br>
			<input type="submit" value="Submit"><br>
		</div>
	</form>

</body>
</html>