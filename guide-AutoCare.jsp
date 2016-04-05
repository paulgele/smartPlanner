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
		<label for="scheduledTasks">Smart Calendar Automobile Maintenance Menu</label><br>
		<!-- <input type="text" class="form-control" id="textLastName" name="LastName"><br>  -->
	</h1>
	<h2>
		<label>Check box to select desired tasks.</label><br>
	</h2>	
	
	<form role="form" action="TheServlet" method="post">
  		<div>	<!-- <div class="form-group">  -->
			<input type="checkbox" name="changeOil"                    >Change oil
			<input type="hidden"   name="changeOil"  value="4"         > <!-- Default recurrence value in months (estimate) (Usually 5,000 miles.  See your owners manual for details about your vehicle.)-->
			<input type="hidden"   name="changeOil"  value="Change oil"><br>
			
			<input type="checkbox" name="flushCoolant"                       >Flush coolant
			<input type="hidden"   name="flushCoolant"  value="24"           >  <!-- Default recurrence value in months. -->
			<input type="hidden"   name="flushCoolant"  value="Flush Coolant"><br>

			<input type="checkbox" name="checkTirePressure"                              >Check tire pressure
			<input type="hidden"   name="checkTirePressure"   value="4"                  >  <!-- Default recurrence value in months. -->
			<input type="hidden"   name="checkTirePressure"   value="Check tire pressure"><br>

			<input type="checkbox" name="refillWiperFluid"                              >Refill wiper fluid
			<input type="hidden"   name="refillWiperFluid"    value="3"                 >  <!-- Default recurrence value in months. -->
			<input type="hidden"   name="refillWiperFluid"    value="Refill wiper fluid"><br>
<!--
			<input type="checkbox" name="testBattery"                       >Test battery
			<input type="hidden"   name="testBattery"   value="12"          >
			<input type="hidden"   name="testBattery"   value="Test battery"><br>

			<input type="checkbox" name="checkAirFilter"                           >Check air filter
			<input type="hidden"   name="checkAirFilter"   value="6"               >
			<input type="hidden"   name="checkAirFilter"   value="Check air filter"><br>

			<input type="checkbox" name="changeFuelFilter"                             >Change fuel filter
			<input type="hidden"   name="changeFuelFilter"   value="60"                >
			<input type="hidden"   name="changeFuelFilter"   value="Change fuel filter"><br>

			<input type="checkbox" name="checkTransFluidLevel"                                                  >Check automatic transmission fluid level
			<input type="hidden"   name="checkTransFluidLevel"  value="6"                                       >
			<input type="hidden"   name="checkTransFluidLevel"  value="Check automatic transmission fluid level"><br>

			<input type="checkbox" name="checkBrakes"                                        >Check brake wear and fluid level
			<input type="hidden"   name="checkBrakes"  value="12"                            >
			<input type="hidden"   name="checkBrakes"  value="Check brake wear & fluid level"><br>

			<input type="checkbox" name="inspectSteering"                                                       >Inspect steering system and lubricate as needed
			<input type="hidden"   name="inspectSteering"  value="24"                                           >
			<input type="hidden"   name="inspectSteering"  value="Inspect steering system & lubricate as needed"><br>
-->
		</div>
		<div>	<!-- <div class="form-group">  -->
			<input type="hidden" name="state" value="form02AutoMaintenance"><br>
			<input type="submit" value="Submit"><br>
		</div>
	</form>
</body>
</html>