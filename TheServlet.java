package TheServlet;

import java.io.IOException;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class TheServlet
 */
@WebServlet("/TheServlet")
public class TheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TheServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //Overhead fields
	HttpSession session;
	Connection connection;
	String state = null;
	String userID = null;
	String passWd = null;
	String newUserID;
	String newPassWd;
	String loginSuccess;
	
	//SQL Query Strings
	String useSmartPlannerDB 		= "USE smartplanner";
	
	//SQL Prepared Statement fields
	String preparedSqlLoginSELECT = "SELECT email, passd FROM logintable WHERE email=?";
	String preparedSqlCreateAcct = "INSERT INTO logintable (email, passd) VALUES (?,?)";
	
	//  Wizard tasks
	String preparedSqlINSERT = 
			"INSERT INTO tasktable (userID, taskName, recurrenceInterval, taskDate, startTime, recurrenceType)" + 
					"VALUES (?,?,?,?,?,?)";
	String preparedSqlDeleteAllTasks4User 	= "DELETE FROM tasktable WHERE userID=?";
	String preparedSqlTaskSELECT  = 
			"SELECT userID, taskName, recurrenceInterval, taskDate, startTime, recurrenceType, iD FROM tasktable WHERE userID=?";
	
	//  User tasks
	String preparedSqlUserTaskINSERT = 
			"INSERT INTO usertasktable (userID, taskName, recurrenceInterval, taskDate, startTime, recurrenceType)" +
					"VALUES (?,?,?,?,?,?)";
	String preparedSqlDeleteAllUserTasks4User 	= "DELETE FROM usertasktable WHERE userID=?";
	String preparedSqlUserTaskSELECT  = 
			"SELECT userID, taskName, recurrenceInterval, taskDate, startTime, recurrenceType, iD FROM usertasktable WHERE userID=?";
	int iD; //SQL AUTO_GENERATED key
	
	//HTML/JSP files Strings
	String url_renderCalendar		= "/WEB-INF/renderCalendar.jsp";
	String url_guide_HomeCare		= "/WEB-INF/guide-HomeCare.jsp";
	String url_guide_AutoCare		= "/WEB-INF/guide-AutoCare.jsp";
	String url_guideSummary 		= "/WEB-INF/guideSummary.jsp";
	String url_calendarEditForm		= "/WEB-INF/calendarEditForm.jsp";
	String url_calendarMain			= "/WEB-INF/calendarMain.jsp";
	String url_createAcct			= "/WEB-INF/createAcct.jsp";
	String url_userDefinedTasks		= "/WEB-INF/userDefinedTasks.jsp";
	String url_loginPage			= "/loginPage.jsp";
	
	
	//Collections
    List<taskBean> 			htmlList 			=	new ArrayList<>();
    List<taskBeanSummary> 	taskList 			=	new ArrayList<>();
    List<taskBeanSummary>	userTaskList 		=	new ArrayList<>();
    List<taskBeanSummary> 	taskListWithID 		= 	new ArrayList<>();
    List<taskBeanSummary> 	eventList 			=	new ArrayList<>();
    List<taskBeanSummary>	userEventList 		=	new ArrayList<>();
	List<taskBeanSummary> 	recurringTaskList 	=	new ArrayList<>();  //Change this to a HashMap, to allow for deleting events later.
	Map<String, String>		loginMap			=	new HashMap<>();
    

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}//doGet

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		state = request.getParameter("state");

		response.setContentType("text/html");
		session = request.getSession();	//Create a session object.
			
		//Load database driver.
		System.out.println("Create connection to database.....");
		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			session.setAttribute("userID", null);
			System.out.println("Set userID attribute = null...... ");
			System.out.println(" ");
			e.printStackTrace();
		}//catch
			
		
		//Create connection to the database.
		try {
			connection = dbMgmt.openDB();	//Create a connection object.
			             dbMgmt.executeUpdate(connection, useSmartPlannerDB);
		} catch (SQLException e) {
			session.setAttribute("userID", null);
			System.out.println("Set userID attribute = null...... ");
			System.out.println(" ");
			dbMgmt.printSqlTrace(e);
		}//catch (SQLException e) 

		
			
		if(state.equals("form04renderCalendar") || state.equals("form05editCalendarForm")) {
			//***********************************************************************************************************************************************
			//Read taskList collection from the database here, and then use method "recurringTaskBuilder.buildRecurringTasks()" to unroll the list before 
			//rendering the calendar.  This allows the user to login and then go straight to the calendar.
			
//			loginMap = (Map<String, String>) session.getAttribute("LoginData");
//			
//			if(loginMap.get("loginSuccess").equals("true")) {
//				getServletContext().getRequestDispatcher(url_calendarMain).forward(request, response);
//			} else { 
//				getServletContext().getRequestDispatcher(url_loginPage).forward(request, response);
//			}
			
			taskListWithID.clear();
			eventList.clear();
				
			try {//Read wizard tasks from the database.
				taskListWithID = dbMgmt.executeTaskSelect(connection, preparedSqlTaskSELECT, (String)session.getAttribute("userID"));
			} catch (SQLException a) {
				dbMgmt.printSqlTrace(a);
			}//catch
				
			session.setAttribute("taskListWithID", taskListWithID);
				
			eventList = recurringTaskBuilder.buildRecurringTasks(taskListWithID);
			session.setAttribute("eventList", eventList);

			if(state.equals("form04renderCalendar")) {
				System.out.println("form04renderCalendar:: String userID= " + userID);
				getServletContext().getRequestDispatcher(url_renderCalendar).forward(request, response);
			}
			if(state.equals("form05editCalendarForm")) {
				System.out.println("form05editCalendarForm:: String userID= " + userID);
				getServletContext().getRequestDispatcher(url_calendarEditForm).forward(request, response);
			}//
		}//form04renderCalendar OR form05editCalendarForm
		else if (state.equals("form000Main")) {
			loginSuccess=null;
			passWd = request.getParameter("passd");
			userID = request.getParameter("emailAddress");
			System.out.println("form000Main::getParameter(emailAddress), String userID= " + userID);
			System.out.println(" ");
			
//			loginMap.put("createAcctResult", " "); //A map is required for the EL language code ${...} in the login JSP's JSTL code
			request.setAttribute("LoginData", loginMap);			
			
			try {//Attempt Login
				//System.out.println("From form: userID= " +  userID + ", passWd= " + passWd);
				System.out.println("form000Main:: String userID= " + userID);
				loginSuccess = dbMgmt.executeLoginSelect(connection, preparedSqlLoginSELECT, userID, passWd);
				//System.out.println("loginSuccess== " + loginSuccess);
			} catch (SQLException a) {
				session.setAttribute("userID", null);
				System.out.println("form000Main:: Set userID attribute = null...... ");
				dbMgmt.printSqlTrace(a);
			}//catch
			
			//Test login
			if(loginSuccess.equals("true")) {
				loginMap.put("loginSuccess", "true"); //A map is required for the EL language code ${...} in the login JSP's JSTL code
				request.setAttribute("LoginData", loginMap);
				session.setAttribute("userID", userID);
				System.out.println("form000Main:: loginSuccess==true:: session.getAttribute(\"userID\") = " + session.getAttribute("userID"));
				getServletContext().getRequestDispatcher(url_calendarMain).forward(request, response);
			} else { //loginSuccess.equals("false")
				loginMap.put("loginSuccess", "false");//A map is required for the EL language code ${...} in the login JSP's JSTL code
				request.setAttribute("LoginData", loginMap);
				session.setAttribute("userID", null);
				System.out.println("form000Main:: loginSuccess==false:: session.getAttribute(\"userID\") = " + session.getAttribute("userID"));
				getServletContext().getRequestDispatcher(url_loginPage).forward(request, response);
			}//else
						
		}//form000Main
		else if(state.equals("form00GuideFirstPage")) {
			getServletContext().getRequestDispatcher(url_guide_HomeCare).forward(request, response);
		}//form00GuideFirstPage
		else if(state.equals("form07calendarMainPage")) {
			getServletContext().getRequestDispatcher(url_calendarMain).forward(request, response);
		}//form07calendarMainPage
		else if(state.equals("form08logOut")) {
			session.invalidate();  //invalidate() was generating errors.  Use removeAttribute() as a work-around.
//			session=null;
			connection.close();
			System.out.println("form08logOut:: Logging out...:   session= " + session);
			getServletContext().getRequestDispatcher(url_loginPage).forward(request, response);
		}//form08logOut
		else if(state.equals("form09createAcctForm")) {
			System.out.println("form09createAcctForm:: Forward to create-account form...");
//			loginMap.put("createAcctResult", " "); //A map is required for the EL language code ${...} in the login JSP's JSTL code
			request.setAttribute("LoginData", loginMap);
			getServletContext().getRequestDispatcher(url_createAcct).forward(request, response);
		}//form09createAcctForm
		else if(state.equals("form09createAcct")) {
			boolean createAcctTest=false;
			newUserID = request.getParameter("newUserID");
			newPassWd = request.getParameter("newPassWd");
			System.out.println("form09createAcct:: Submit create-account form...");
		
			System.out.println("form09createAcct:: newUserID= " + newUserID + ", newPassWd= " + newPassWd);
			
			loginMap.put("loginSuccess", "default");
			request.setAttribute("LoginData", loginMap);
			
			if (newUserID.isEmpty()||newPassWd.isEmpty()) {
				loginMap.put("createAcctResult", "Fail"); //Missing email address or password.  A map is required for the EL language code ${...} in the login JSP's JSTL code
				request.setAttribute("LoginData", loginMap);
			} else {
				try  {
					createAcctTest = dbMgmt.executeCreateAcct(connection, newUserID, newPassWd, preparedSqlCreateAcct, preparedSqlLoginSELECT);
				} catch (SQLException a) {
					System.out.println("state==form09createAcct: SQLException occurred...");
					dbMgmt.printSqlTrace(a);
				}//catch
					
				if(createAcctTest==true) {
					loginMap.put("createAcctResult", "Pass"); //New account created.  A map is required for the EL language code ${...} in the login JSP's JSTL code
					request.setAttribute("LoginData", loginMap);
				}
				else if(createAcctTest==false) {
					loginMap.put("createAcctResult", "Redundant"); //Account with this email address found.  A map is required for the EL language code ${...} in the login JSP's JSTL code
					request.setAttribute("LoginData", loginMap);
				}//else if()
			}//else
			
			Set<String> KS = loginMap.keySet();
			Iterator<String> KSitr = KS.iterator();
			String KSval;
			while(KSitr.hasNext()) {
				KSval = KSitr.next();
				System.out.println("form09createAcct:: Key= " + KSval + ", Value= " + loginMap.get(KSval));
			}//while
			
			getServletContext().getRequestDispatcher(url_loginPage).forward(request, response);
		}//form09createAcct
		else if(state.equals("form01HomeMaintenance")) {
			
			String [] changeHVAC_Filter 	= new String [3]; 
			String [] cleanWindows 			= new String [3];
			String [] cleanLeadersGutters 	= new String [3];
			String [] WaterHeater 			= new String [3];
			String [] WaterFilters 			= new String [3];
			String [] SmokeAlarmBattery 	= new String [3];
			String [] HVAC_Service 			= new String [3];
			String [] cleanChimney 			= new String [3];
			
System.out.println("form01HomeMaintenance:: Before htmlList.clear()= " + htmlList);
			htmlList.clear();
System.out.println("form01HomeMaintenance:: After htmlList.clear()= " + htmlList);
			
			changeHVAC_Filter = request.getParameterValues("changeHVAC_Filter");
			if(changeHVAC_Filter[0].equals("on")){
				taskBean bean = new taskBean(changeHVAC_Filter[0], Integer.parseInt(changeHVAC_Filter[1]), changeHVAC_Filter[2]);
				htmlList.add(bean);
			}//if  changeHVAC_Filter
			
			cleanWindows = request.getParameterValues("cleanWindows");
			if(cleanWindows[0].equals("on")){
				taskBean bean = new taskBean(cleanWindows[0], Integer.parseInt(cleanWindows[1]), cleanWindows[2]);
				htmlList.add(bean);
			}//if  cleanWindows

			cleanLeadersGutters = request.getParameterValues("cleanLeadersGutters");
			if(cleanLeadersGutters[0].equals("on")){
				taskBean bean = new taskBean(cleanLeadersGutters[0], Integer.parseInt(cleanLeadersGutters[1]), cleanLeadersGutters[2]);
				htmlList.add(bean);
			}//if  cleanLeadersGutters

			WaterHeater = request.getParameterValues("WaterHeater");
			if(WaterHeater[0].equals("on")){
				taskBean bean = new taskBean(WaterHeater[0], Integer.parseInt(WaterHeater[1]), WaterHeater[2]);
				htmlList.add(bean);
			}//if  WaterHeater
/**
			WaterFilters = request.getParameterValues("WaterFilters");
			if(WaterFilters[0].equals("on")){
				taskBean bean = new taskBean(WaterFilters[0], Integer.parseInt(WaterFilters[1]), WaterFilters[2]);
				htmlList.add(bean);
			}//if  WaterFilters
			
			SmokeAlarmBattery = request.getParameterValues("SmokeAlarmBattery");
			if(SmokeAlarmBattery[0].equals("on")){
				taskBean bean = new taskBean(SmokeAlarmBattery[0], Integer.parseInt(SmokeAlarmBattery[1]), SmokeAlarmBattery[2]);
				htmlList.add(bean);
			}//if  SmokeAlarmBattery

			HVAC_Service = request.getParameterValues("HVAC_Service");
			if(HVAC_Service[0].equals("on")){
				taskBean bean = new taskBean(HVAC_Service[0], Integer.parseInt(HVAC_Service[1]), HVAC_Service[2]);
				htmlList.add(bean);
			}//if  HVAC_Service
			
			cleanChimney = request.getParameterValues("cleanChimney");
			if(cleanChimney[0].equals("on")){
				taskBean bean = new taskBean(cleanChimney[0], Integer.parseInt(cleanChimney[1]), cleanChimney[2]);
				htmlList.add(bean);
			}//if  cleanChimney
**/
			
System.out.println("form01HomeMaintenance:: htmlList= " + htmlList);
			session.setAttribute("htmlList", htmlList);
			
			getServletContext().getRequestDispatcher(url_guide_AutoCare).forward(request, response);
			
		}//if form01HomeMaintenance
		else if(state.equals("form02AutoMaintenance")) {
			
			htmlList = (List<taskBean>) session.getAttribute("htmlList");
			
			String [] changeOil         = new String[3];
			String [] flushCoolant      = new String[3];
			String [] checkTirePressure = new String[3];
			String [] refillWiperFluid  = new String[3];
			

			changeOil = request.getParameterValues("changeOil");
			if(changeOil[0].equals("on")) {
				taskBean bean = new taskBean(changeOil[0], Integer.parseInt(changeOil[1]), changeOil[2]);
				htmlList.add(bean);
			}//if changeOil
			
			flushCoolant = request.getParameterValues("flushCoolant");
			if(flushCoolant[0].equals("on")) {
				taskBean bean = new taskBean(flushCoolant[0], Integer.parseInt(flushCoolant[1]), flushCoolant[2]);
				htmlList.add(bean);
			}//if flushCoolant
			
			checkTirePressure = request.getParameterValues("checkTirePressure");
			if(checkTirePressure[0].equals("on")) {
				taskBean bean = new taskBean(checkTirePressure[0], Integer.parseInt(checkTirePressure[1]), checkTirePressure[2]);
				htmlList.add(bean);
			}//if checkTirePressure

			refillWiperFluid = request.getParameterValues("refillWiperFluid");
			if(refillWiperFluid[0].equals("on")) {
				taskBean bean = new taskBean(refillWiperFluid[0], Integer.parseInt(refillWiperFluid[1]), refillWiperFluid[2]);
				htmlList.add(bean);
			}//if refillWiperFluid

/**
			//Test contents of ArrayList htmlList.
						Iterator <taskBean> htmlListIterator = htmlList.iterator();
						taskBean guideString = new taskBean();
						int i=0;
						while (htmlListIterator.hasNext()) {
						//for(String[] task : htmlList) {
							i++;
							out.println("<p>i = "+i);
							out.println(", htmlList size== " + htmlList.size() + "</p>");
							//out.println("  task[0]== " + task[0]);
							//out.println(", task[1]== " + task[1]);
							//out.println(", task[2]== " + task[2]);
							guideString = htmlListIterator.next();
							//out.println("<p>Iteration...</p>");
							out.println("<p>" +
									"  guideString[0]== " + guideString.getStatus() +  
									", guideString[1]== " + guideString.getReschedulePeriod() +  
									", guideString[2]== " + guideString.getTaskName() +  
									"</p><br>");
							out.println(" ");
						}//while
**/

System.out.println("form02AutoMaintenance:: After adding to htmlList= " + htmlList);
			session.setAttribute("htmlList", htmlList);
//			request.setAttribute("x", htmlList);
//			System.out.println(htmlList);

			getServletContext().getRequestDispatcher(url_guideSummary).forward(request, response);	
			
		}//if form02AutoMaintenance
		else if(state.equals("form03guideSummary")|| state.equals("form06editCalendarTasks")) {
			
			String [] changeHVAC_Filter_Summary 	= new String [6]; 
			String [] cleanWindows_Summary 			= new String [6];
			String [] cleanLeadersGutters_Summary 	= new String [6];
			String [] WaterHeater_Summary 			= new String [6];
			String [] WaterFilters_Summary 			= new String [6];
			String [] SmokeAlarmBattery_Summary 	= new String [6];
			String [] HVAC_Service_Summary 			= new String [6];
			String [] cleanChimney_Summary 			= new String [6];
			
			String [] changeOil_Summary             = new String [6];
			String [] flushCoolant_Summary          = new String [6];
			String [] checkTirePressure_Summary     = new String [6];
			String [] refillWiperFluid_Summary      = new String [6];
			
			/**
			X Change Furnace/ac air filter
			X Clean windows
			X Clean gutter and roof leaders
			X Water heater maintenance
			Change water purifier filter(s)
			Test smoke alarm batteries
			Furnace and a/c service
			Clean chimney flute
			**/			

			taskList.clear();
			
			System.out.println("From guideSummary.jsp: userID= " + userID);
			
			try {
				dbMgmt.executeTaskDelete(connection, (String)session.getAttribute("userID"), preparedSqlDeleteAllTasks4User); //Delete all tasks for current user from 'tasktable' to 
																				//allow a replacement group of tasks to be stored.
			} catch (SQLException e) {
				dbMgmt.printSqlTrace(e);
			}//catch (SQLException e) 

			
			try{
				changeHVAC_Filter_Summary = request.getParameterValues("Change Furnace/ac air filter");
				if((changeHVAC_Filter_Summary[0].equals("Change Furnace/ac air filter")) & (changeHVAC_Filter_Summary.length==5)) {  //Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(changeHVAC_Filter_Summary[0], Integer.parseInt(changeHVAC_Filter_Summary[1]), changeHVAC_Filter_Summary[2], changeHVAC_Filter_Summary[3], changeHVAC_Filter_Summary[4]);
					taskList.add(beanSummary);
					System.out.println("changeHVAC_Filter_Summary.length= " + changeHVAC_Filter_Summary.length + ".  Came from 'guideSummary.jsp'.");
				}//if changeHVAC_Filter_Summary & length==5
				else if ((changeHVAC_Filter_Summary[0].equals("Change Furnace/ac air filter")) & (changeHVAC_Filter_Summary.length==6)) { //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(changeHVAC_Filter_Summary[0], Integer.parseInt(changeHVAC_Filter_Summary[1]), changeHVAC_Filter_Summary[2], changeHVAC_Filter_Summary[3], changeHVAC_Filter_Summary[4], Integer.parseInt(changeHVAC_Filter_Summary[5]));
					taskList.add(beanSummary);		
					System.out.println("changeHVAC_Filter_Summary.length= " + changeHVAC_Filter_Summary.length + ".  Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked..");
				}//if changeHVAC_Filter_Summary & length==7
				//out.println("<p>changeHVAC_Filter_Summary[0] = "   + changeHVAC_Filter_Summary[0]   + ", changeHVAC_Filter_Summary[1] = "   + changeHVAC_Filter_Summary[1]   + ", changeHVAC_Filter_Summary[2] = "   + changeHVAC_Filter_Summary[2]   + ", changeHVAC_Filter_Summary[3] = "   + changeHVAC_Filter_Summary[3]   + "</p><br>");
			} catch(NullPointerException e) {
				
			}//catch

			try{
				cleanWindows_Summary = request.getParameterValues("Clean windows");
				System.out.println("cleanWindows_Summary.length= " + cleanWindows_Summary.length);
				if((cleanWindows_Summary[0].equals("Clean windows")) & (cleanWindows_Summary.length==5)) {  ///Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(cleanWindows_Summary[0], Integer.parseInt(cleanWindows_Summary[1]), cleanWindows_Summary[2], cleanWindows_Summary[3], cleanWindows_Summary[4]);
					taskList.add(beanSummary);
					System.out.println("cleanWindows_Summary.length= " + cleanWindows_Summary.length + ".  Came from 'guideSummary.jsp'.");
				}//if 
				else if((cleanWindows_Summary[0].equals("Clean windows")) & (cleanWindows_Summary.length==6)) {  //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(cleanWindows_Summary[0], Integer.parseInt(cleanWindows_Summary[1]), cleanWindows_Summary[2], cleanWindows_Summary[3], cleanWindows_Summary[4], Integer.parseInt(cleanWindows_Summary[5]));
					taskList.add(beanSummary);
					System.out.println("cleanWindows_Summary.length= " + cleanWindows_Summary.length + ".  Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked..");
				}//if
				//out.println("<p>cleanWindows_Summary[0] = "        + cleanWindows_Summary[0]        + ", cleanWindows_Summary[1] = "        + cleanWindows_Summary[1]        + ", cleanWindows_Summary[2] = "        + cleanWindows_Summary[2]        + ", cleanWindows_Summary[3] ="         + cleanWindows_Summary[3]        + "</p><br>");
			} catch(NullPointerException e) {
	
			}//catch
			
			try{
				cleanLeadersGutters_Summary = request.getParameterValues("Clean gutter and roof leaders");
				System.out.println("cleanLeadersGutters_Summary.length= " + cleanLeadersGutters_Summary.length);
				if((cleanLeadersGutters_Summary[0].equals("Clean gutter and roof leaders")) & (cleanLeadersGutters_Summary.length==5)) {  //Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(cleanLeadersGutters_Summary[0], Integer.parseInt(cleanLeadersGutters_Summary[1]), cleanLeadersGutters_Summary[2], cleanLeadersGutters_Summary[3], cleanLeadersGutters_Summary[4]);
					taskList.add(beanSummary);
					System.out.println("cleanLeadersGutters_Summary.length= " + cleanLeadersGutters_Summary.length + ".  Came from 'guideSummary.jsp'.");
				}//if 
				else if((cleanLeadersGutters_Summary[0].equals("Clean gutter and roof leaders")) & (cleanLeadersGutters_Summary.length==6)) {  //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(cleanLeadersGutters_Summary[0], Integer.parseInt(cleanLeadersGutters_Summary[1]), cleanLeadersGutters_Summary[2], cleanLeadersGutters_Summary[3], cleanLeadersGutters_Summary[4], Integer.parseInt(cleanLeadersGutters_Summary[5]));
					taskList.add(beanSummary);
					System.out.println("cleanLeadersGutters_Summary.length= " + cleanLeadersGutters_Summary.length + ".  Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked..");
				}//if 
				//out.println("<p>cleanLeadersGutters_Summary[0] = " + cleanLeadersGutters_Summary[0] + ", cleanLeadersGutters_Summary[1] = " + cleanLeadersGutters_Summary[1] + ", cleanLeadersGutters_Summary[2] = " + cleanLeadersGutters_Summary[2] + ", cleanLeadersGutters_Summary[3] = " + cleanLeadersGutters_Summary[3] + "</p><br>");
			} catch(NullPointerException e) {
			
			}//catch
				

			try{
				WaterHeater_Summary = request.getParameterValues("Water heater maintenance");
				System.out.println("WaterHeater_Summary.length= " + WaterHeater_Summary.length);
				if((WaterHeater_Summary[0].equals("Water heater maintenance")) & (WaterHeater_Summary.length==5)) {  //Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(WaterHeater_Summary[0], Integer.parseInt(WaterHeater_Summary[1]), WaterHeater_Summary[2], WaterHeater_Summary[3], WaterHeater_Summary[4]);
					taskList.add(beanSummary);
				}//if
				else if((WaterHeater_Summary[0].equals("Water heater maintenance")) & (WaterHeater_Summary.length==6)) {  //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(WaterHeater_Summary[0], Integer.parseInt(WaterHeater_Summary[1]), WaterHeater_Summary[2], WaterHeater_Summary[3], WaterHeater_Summary[4], Integer.parseInt(WaterHeater_Summary[5]));
					taskList.add(beanSummary);
				}//if
				//out.println("<p>WaterHeater_Summary[0] = "         + WaterHeater_Summary[0]         + ", WaterHeater_Summary[1] = "         + WaterHeater_Summary[1]         + ", WaterHeater_Summary[2] = "         + WaterHeater_Summary[2]         + ", WaterHeater_Summary[3] = "         + WaterHeater_Summary[3]         + "</p><br>");
			} catch(NullPointerException e) {
				
			}//catch
		
			
			/**
			X-Change oil
			X-Flush Coolant
			X-Check tire pressure
			X-Refill wiper fluid
			
			-checkAirFilter 
			-changeFuelFilter 
			-checkTransFluidLevel 
			-checkBrakes 
			-inspectSteering 
			**/
			
			try{
				changeOil_Summary = request.getParameterValues("Change oil");
				System.out.println("changeOil_Summary.length= " + changeOil_Summary.length);
				if((changeOil_Summary[0].equals("Change oil")) & (changeOil_Summary.length==5)) { //Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(changeOil_Summary[0], Integer.parseInt(changeOil_Summary[1]), changeOil_Summary[2], changeOil_Summary[3], changeOil_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				else if((changeOil_Summary[0].equals("Change oil")) & (changeOil_Summary.length==6)) { //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(changeOil_Summary[0], Integer.parseInt(changeOil_Summary[1]), changeOil_Summary[2], changeOil_Summary[3], changeOil_Summary[4], Integer.parseInt(changeOil_Summary[5]));
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>changeOil_Summary[0] = "         + changeOil_Summary[0]         + ", changeOil_Summary[1] = "         + changeOil_Summary[1]         + ", changeOil_Summary[2] = "         + changeOil_Summary[2]         + ", changeOil_Summary[3] ="          + changeOil_Summary[3]         + "</p><br>");
			} catch(NullPointerException e) {
			
			}//catch
		
			try{
				flushCoolant_Summary = request.getParameterValues("Flush Coolant");
				System.out.println("flushCoolant_Summary.length= " + flushCoolant_Summary.length);
				if((flushCoolant_Summary[0].equals("Flush Coolant")) & (flushCoolant_Summary.length==5)) {  ///Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(flushCoolant_Summary[0], Integer.parseInt(flushCoolant_Summary[1]), flushCoolant_Summary[2], flushCoolant_Summary[3], flushCoolant_Summary[4]);
					taskList.add(beanSummary);
					System.out.println("flushCoolant_Summary.length= " + flushCoolant_Summary.length + ".  Came from 'guideSummary.jsp'.");
				}//if 
				else if((flushCoolant_Summary[0].equals("Flush Coolant")) & (flushCoolant_Summary.length==6)) {  //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(flushCoolant_Summary[0], Integer.parseInt(flushCoolant_Summary[1]), flushCoolant_Summary[2], flushCoolant_Summary[3], flushCoolant_Summary[4], Integer.parseInt(flushCoolant_Summary[5]));
					taskList.add(beanSummary);
					System.out.println("flushCoolant_Summary.length= " + flushCoolant_Summary.length + ".  Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked..");
				}//if 
				//out.println("<p>flushCoolant_Summary[0] = "      + flushCoolant_Summary[0]      + ", flushCoolant_Summary[1] = "      + flushCoolant_Summary[1]      + ", flushCoolant_Summary[2] = "      + flushCoolant_Summary[2]      + ", flushCoolant_Summary[3] = "      + flushCoolant_Summary[3]      + "</p><br>");
			} catch(NullPointerException e) {

			}//catch

			try{
				checkTirePressure_Summary = request.getParameterValues("Check tire pressure");
				System.out.println("checkTirePressure_Summary.length= " + checkTirePressure_Summary.length);
				if((checkTirePressure_Summary[0].equals("Check tire pressure")) & (checkTirePressure_Summary.length==5)) {  //Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(checkTirePressure_Summary[0], Integer.parseInt(checkTirePressure_Summary[1]), checkTirePressure_Summary[2], checkTirePressure_Summary[3], checkTirePressure_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				else if((checkTirePressure_Summary[0].equals("Check tire pressure")) & (checkTirePressure_Summary.length==6)) {  //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(checkTirePressure_Summary[0], Integer.parseInt(checkTirePressure_Summary[1]), checkTirePressure_Summary[2], checkTirePressure_Summary[3], checkTirePressure_Summary[4], Integer.parseInt(checkTirePressure_Summary[5]));
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>checkTirePressure_Summary[0] = " + checkTirePressure_Summary[0] + ", checkTirePressure_Summary[1] = " + checkTirePressure_Summary[1] + ", checkTirePressure_Summary[2] = " + checkTirePressure_Summary[2] + ", checkTirePressure_Summary[3] = " + checkTirePressure_Summary[3] + "</p><br>");
			} catch(NullPointerException e) {
	
			}//catch
			
			try{
				refillWiperFluid_Summary = request.getParameterValues("Refill wiper fluid");
				System.out.println("refillWiperFluid_Summary.length= " + refillWiperFluid_Summary.length);
				if((refillWiperFluid_Summary[0].equals("Refill wiper fluid")) & (refillWiperFluid_Summary.length==5)) {  //Came from 'guideSummary.jsp'
					taskBeanSummary beanSummary = new taskBeanSummary(refillWiperFluid_Summary[0], Integer.parseInt(refillWiperFluid_Summary[1]), refillWiperFluid_Summary[2], refillWiperFluid_Summary[3], refillWiperFluid_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				else if((refillWiperFluid_Summary[0].equals("Refill wiper fluid")) & (refillWiperFluid_Summary.length==6)) {  //Came from 'calendarEditForm.jsp' && 'Delete' box NOT checked.  length==7 when 'Delete' box checked.
					taskBeanSummary beanSummary = new taskBeanSummary(refillWiperFluid_Summary[0], Integer.parseInt(refillWiperFluid_Summary[1]), refillWiperFluid_Summary[2], refillWiperFluid_Summary[3], refillWiperFluid_Summary[4], Integer.parseInt(refillWiperFluid_Summary[5]));
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>refillWiperFluid_Summary[0] = "  + refillWiperFluid_Summary[0]  + ", WaterHeater_Summary[1] = "       + refillWiperFluid_Summary[1]  + ", refillWiperFluid_Summary[2] = "  + refillWiperFluid_Summary[2]  + ", refillWiperFluid_Summary[3] = "  + refillWiperFluid_Summary[3]  + "</p><br>");
			} catch(NullPointerException e) {
			
			}//catch

			
			//*************************************************************************************************************
			//Write taskList collection into the database here, before unrolling the tasks in method "recurringTaskBuilder.buildRecurringTasks()",
			//to later allow removing all instances of any task, through a new jsp which has not yet been developed.  The new jsp will be
			//a modified version of 'guideSummary.jsp'.
			
			Iterator <taskBeanSummary> itr_taskList = taskList.iterator();
			taskBeanSummary beanSummary_inst = new taskBeanSummary();  //Scratch instance
			taskListWithID.clear();
			eventList.clear();
			
			System.out.println(" ");
			System.out.println("form03guideSummary || form06editCalendarTasks:: session.getAttribute(\"userID\")==  " + session.getAttribute("userID"));
			System.out.println("form03guideSummary || form06editCalendarTasks:: Tasks Created Through Wizard. Loop starts here....");
			while (itr_taskList.hasNext()) {
				try {
					beanSummary_inst = itr_taskList.next();

					System.out.println("form03guideSummary || form06editCalendarTasks:: beanSummary_inst.taskName				= "  + beanSummary_inst.getTaskName());
					System.out.println("form03guideSummary || form06editCalendarTasks:: beanSummary_inst.taskDate				= "  + beanSummary_inst.getTaskDate());
					System.out.println("form03guideSummary || form06editCalendarTasks:: beanSummary_inst.taskTime				= "  + beanSummary_inst.getTaskTime());
					System.out.println("form03guideSummary || form06editCalendarTasks:: beanSummary_inst.recurrenceType		= "  + beanSummary_inst.getRecurrenceType());
					System.out.println("form03guideSummary || form06editCalendarTasks:: beanSummary_inst.reschedulePeriod		= "  + beanSummary_inst.getReschedulePeriod());
					System.out.println("form03guideSummary || form06editCalendarTasks:: beanSummary_inst.id					= "  + beanSummary_inst.getId());

					
					iD = dbMgmt.executeInsert(connection, preparedSqlINSERT, (String)session.getAttribute("userID"), beanSummary_inst.getTaskName(), beanSummary_inst.getReschedulePeriod(), beanSummary_inst.getTaskDate(), beanSummary_inst.getTaskTime(), beanSummary_inst.getRecurrenceType());
					beanSummary_inst.setId(iD);
					taskListWithID.add(beanSummary_inst);
					System.out.println("New id= " + beanSummary_inst.getId());
					System.out.println("taskName= " + beanSummary_inst.getTaskName());
					System.out.println("getRecurrenceType= " + beanSummary_inst.getRecurrenceType());
				}
				catch (SQLException e) {
						dbMgmt.printSqlTrace(e);
				}
			}//while
			

			session.setAttribute("taskListWithID", taskListWithID);
			
			eventList = recurringTaskBuilder.buildRecurringTasks(taskListWithID);			
			session.setAttribute("eventList", eventList);

			getServletContext().getRequestDispatcher(url_renderCalendar).forward(request, response);

		}//if form03guideSummary || form06editCalendarTasks
		else if (state.equals("form10UserDefinedTaskForm")) {
			System.out.println("form10UserDefinedTaskForm:: Go to userDefinedTask.jsp form...");
			userTaskList.clear();
			getServletContext().getRequestDispatcher(url_userDefinedTasks).forward(request, response);
		}//
		else if(state.equals("form10UserDefinedTasksSubmit")) {
			String [] changeNewTask_Summary = new String [5];
			
			System.out.println("form10UserDefinedTasksSubmit:: ...");
			
			try{
				changeNewTask_Summary = request.getParameterValues("newTask");
		    	taskBeanSummary userTaskBeanSummary = new taskBeanSummary(changeNewTask_Summary[0], Integer.parseInt(changeNewTask_Summary[1]), changeNewTask_Summary[2], changeNewTask_Summary[3], changeNewTask_Summary[4]);
			    userTaskList.add(userTaskBeanSummary);	
			    
				if(changeNewTask_Summary.length==5) {
					try {
						iD = dbMgmt.executeInsert(connection, preparedSqlUserTaskINSERT, (String)session.getAttribute("userID"), userTaskBeanSummary.getTaskName(), userTaskBeanSummary.getReschedulePeriod(), userTaskBeanSummary.getTaskDate(), userTaskBeanSummary.getTaskTime(), userTaskBeanSummary.getRecurrenceType());
						userTaskBeanSummary.setId(iD);
					} catch (SQLException e) {
						dbMgmt.printSqlTrace(e);
					}//catch
				}//if()
			    
			} catch(NullPointerException e) {
							
			}//catch
			
			
			Iterator<taskBeanSummary> userTaskList_Itr = userTaskList.iterator();
			taskBeanSummary userTaskListInst;
			while (userTaskList_Itr.hasNext()) {
			
				userTaskListInst = userTaskList_Itr.next();
				
				System.out.println(" ");
				System.out.println("form10UserDefinedTasksSubmit:: userTaskListInst.taskName			= "  + userTaskListInst.getTaskName());
				System.out.println("form10UserDefinedTasksSubmit:: userTaskListInst.taskDate			= "  + userTaskListInst.getTaskDate());
				System.out.println("form10UserDefinedTasksSubmit:: userTaskListInst.taskTime			= "  + userTaskListInst.getTaskTime());
				System.out.println("form10UserDefinedTasksSubmit:: userTaskListInst.recurrenceType		= "  + userTaskListInst.getRecurrenceType());
				System.out.println("form10UserDefinedTasksSubmit:: userTaskListInst.reschedulePeriod	= "  + userTaskListInst.getReschedulePeriod());
				System.out.println("form10UserDefinedTasksSubmit:: userTaskListInst.id					= "  + userTaskListInst.getId());
				
			}//while()
			
			
			
			session.setAttribute("userTaskList", userTaskList);
			
			userEventList= recurringTaskBuilder.buildRecurringTasks(userTaskList);
			request.setAttribute("userEventList", userEventList);
			
			Iterator<taskBeanSummary> userEventList_Irt = userEventList.iterator();
			taskBeanSummary userEventListInst;

			while (userEventList_Irt.hasNext()) {
							userEventListInst = userEventList_Irt.next();
				
				System.out.println(" ");
				System.out.println("==userEventListInst== ");
				System.out.println(" ");
				System.out.println("form10UserDefinedTasksSubmit:: userEventListInst.taskName			= "  + userEventListInst.getTaskName());
				System.out.println("form10UserDefinedTasksSubmit:: userEventListInst.taskDate			= "  + userEventListInst.getTaskDate());
				System.out.println("form10UserDefinedTasksSubmit:: userEventListInst.taskTime			= "  + userEventListInst.getTaskTime());
				System.out.println("form10UserDefinedTasksSubmit:: userEventListInst.recurrenceType		= "  + userEventListInst.getRecurrenceType());
				System.out.println("form10UserDefinedTasksSubmit:: userEventListInst.reschedulePeriod	= "  + userEventListInst.getReschedulePeriod());
				System.out.println("form10UserDefinedTasksSubmit:: userEventListInst.id					= "  + userEventListInst.getId());
				
			}//while()
			
			getServletContext().getRequestDispatcher(url_renderCalendar).forward(request, response);
		}//form10UserDefinedTasksSubmit
		else if (state.equals(anObject))
		else if (state.equals("form10UserDefinedTaskEdit")){
			
			
			try {
				dbMgmt.executeTaskDelete(connection, (String)session.getAttribute("userID"), preparedSqlDeleteAllUserTasks4User); //Delete all tasks for current user from 'tasktable' to 
																				//allow a replacement group of tasks to be stored.
			} catch (SQLException e) {
				dbMgmt.printSqlTrace(e);
			}//catch (SQLException e) 
			
			getServletContext().getRequestDispatcher(url_renderCalendar).forward(request, response);
		}//form10UserDefinedTaskEdit

	}//doPost


	
}//TheServlet
