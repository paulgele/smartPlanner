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
	
	<h1>
		<label for="scheduledTasks">Smart Calendar Home Maintenance Menu </label><br>
	</h1>
	<h2>
		<label>Check box to select desired tasks.</label><br>
	</h2>
	
	<form role="form" action="TheServlet" method="post">
  		<div>	<!-- <div class="form-group">  -->
			<input type="checkbox" name="changeHVAC_Filter"                                       >Change Furnace/ac air filter
			<input type="hidden"   name="changeHVAC_Filter"   value="6"                           > <!-- Default recurrence value in months. -->
			<input type="hidden"   name="changeHVAC_Filter"   value="Change Furnace/ac air filter"><br>
			
			<input type="checkbox" name="cleanWindows"                        >Clean windows
			<input type="hidden"   name="cleanWindows"   value="12"           >  <!-- Default recurrence value. -->
			<input type="hidden"   name="cleanWindows"   value="Clean windows"><br>
			
			<input type="checkbox" name="cleanLeadersGutters"                                        >Clean gutter and roof leaders	
			<input type="hidden"   name="cleanLeadersGutters"   value="6"                            >  <!-- Default recurrence value in months. -->
			<input type="hidden"   name="cleanLeadersGutters"   value="Clean gutter and roof leaders"><br>
			
			<input type="checkbox" name="WaterHeater"                                   >Water heater maintenance
			<input type="hidden"   name="WaterHeater"   value="12"                      >   <!-- Default recurrence value in months. -->
			<input type="hidden"   name="WaterHeater"   value="Water heater maintenance"><br>
<!--			
			<input type="checkbox" name="WaterFilters"                                          >Change water purifier filter(s)
			<input type="hidden"   name="WaterFilters"   value="3"                              >
			<input type="hidden"   name="WaterFilters"   value="Change water purifier filter(s)"><br>
			
			<input type="checkbox" name="SmokeAlarmBattery"                                     >Test smoke alarm batteries
			<input type="hidden"   name="SmokeAlarmBattery"   value="3"                         >
			<input type="hidden"   name="SmokeAlarmBattery"   value="Test smoke alarm batteries"><br>
			
			<input type="checkbox" name="HVAC_Service"                                  >Furnace and a/c service
			<input type="hidden"   name="HVAC_Service"   value="6"                      >
			<input type="hidden"   name="HVAC_Service"   value="Furnace and a/c service"><br>
			
			<input type="checkbox" name="cleanChimney"                              >Clean chimney flute
			<input type="hidden"   name="cleanChimney"   value="12"                 >
			<input type="hidden"   name="cleanChimney"   value="Clean chimney flute"><br>
-->		
		</div>
		<div>	<!-- <div class="form-group">  -->
			<input type="hidden" name="state" value="form01HomeMaintenance"><br>
			<input type="submit" value="Submit"><br>
		</div>
	</form>
</body>
</html>