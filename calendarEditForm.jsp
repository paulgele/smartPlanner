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
			<label for="scheduledTasks">Smart Calendar Task Edit/Delete Tool</label><br>
			<!-- <input type="text" class="form-control" id="textLastName" name="LastName"><br>  -->
		</h1>
		<h2>
			<label>Edit task parameters as needed, or check box to delete desired tasks.</label><br>
		</h2>	

<%-- 		==Test for valid login session.  Redirect to login page if user navigated around the login page.==
			<c:choose>
			<c:when test="${LoginErrorReport.loginKey != true}">
				<c:redirect url="/loginPage.jsp" />
			</c:when>
			<c:otherwise> 
--%>
				<c:forEach var="item" items="${taskListWithID}">
					<br>
					${item.taskName}<br>
													<input type="hidden" 	name="${item.taskName}"  value="<c:out value='${item.taskName}'         />"> 
					Task repeat interval (months): 	<input type="text"   	name="${item.taskName}"  value="<c:out value='${item.reschedulePeriod}' />">, 
					Data(Format: YYYY-MM-DD): 		<input type="text"   	name="${item.taskName}"  value="<c:out value='${item.taskDate}'         />">, 
					Start Time (Format: HH:MM): 	<input type="text"   	name="${item.taskName}" ><br>
					Repeat Type:					<input type="text"  	name="${item.taskName}"  value="<c:out value='${item.recurrenceType}'   />">,
													<input type="hidden"    name="${item.taskName}"  value="<c:out value='${item.id}'               />">
					Check box to Delete this Task:	<input type="checkbox"  name="${item.taskName}"> <br>
					<br>
				</c:forEach>
<%--			</c:otherwise>
		</c:choose>
--%>		
		</div>
		
		<div>	<!-- <div class="form-group">  -->
			<input type="hidden" name="state" value="form06editCalendarTasks"><br>
			<input type="submit" value="Submit"><br>
		</div>
	</form>

</body>
</html>