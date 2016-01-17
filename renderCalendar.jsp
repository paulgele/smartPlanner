<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel='stylesheet' href='/ITSE_2077-Capstone-Dev/css/fullcalendar-2.5.0/fullcalendar.css' /> 
<script src='/ITSE_2077-Capstone-Dev/css/fullcalendar-2.5.0/lib/jquery.min.js'></script> 
<script src='/ITSE_2077-Capstone-Dev/css/fullcalendar-2.5.0/lib/moment.min.js'></script> 
<script src='/ITSE_2077-Capstone-Dev/css/fullcalendar-2.5.0/fullcalendar.js'></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task Calendar</title>


<script>


$(document).ready(function() {

    // page is now ready, initialize the calendar...

	$('#calendar').fullCalendar({
        	// put your options and callbacks here
        	
	    events: [

			<c:forEach var="item" items="${eventList}">
				{	
				 	title  : '${item.taskName}',
			  		start  : '${item.taskDate}'
			 	 	//id     : '001',
			  		//${item.reschedulePeriod}
			  		//${item.taskTime}
			  		// end    : '2015-12-10'
			  		//
			  		//http://stackoverflow.com/questions/6315210/problem-with-appending-html-and-jstl-code-in-a-div-object-using-jquery
			  		//JavaScript and jQuery gets executed client side, and JSTL gets compiled server side, 
			  		//BEFORE javascript even reaches the client ... 
				},
			</c:forEach>


/*
	        {
	            title  : 'event1',
	            id     : '001',
	            start  : '2015-12-10',
	            end    : '2015-12-10'
	        },
	        {
	            title  : 'event2',
	            id     : '002',
	            start  : '2015-12-05',
	            end    : '2015-12-08'
	        },
	        {
	            title  : 'event3',
	            id     : '003',
	            start  : '2015-12-14T12:30:00',
	            allDay : false // will make the time show
	        }
	      
*/
	        
    	]

	})

});
</script> 

</head>
<body>

	<div id='calendar'></div>

</body>
</html>
