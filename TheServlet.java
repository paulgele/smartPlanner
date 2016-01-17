package TheServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	
    List<taskBean> 			htmlList = 			new ArrayList<>();
    List<taskBeanSummary> 	taskList = 			new ArrayList<>();
    List<taskBeanSummary> 	eventList = 		new ArrayList<>();
	List<taskBeanSummary> 	recurringTaskList = new ArrayList<>();  //Change this to a HashMap, to allow for deleting events later.
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}//doGet

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		
		String state = request.getParameter("state");
		
		HttpSession session = request.getSession();
		
		if(state.equals("form01HomeMaintenance")) {
			
			String [] changeHVAC_Filter 	= new String [3]; 
			String [] cleanWindows 			= new String [3];
			String [] cleanLeadersGutters 	= new String [3];
			String [] WaterHeater 			= new String [3];
			String [] WaterFilters 			= new String [3];
			String [] SmokeAlarmBattery 	= new String [3];
			String [] HVAC_Service 			= new String [3];
			String [] cleanChimney 			= new String [3];
			

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
			
			session.setAttribute("htmlList", htmlList);
			
			String url_guide_AutoCare = "/guide-AutoCare.html";
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

			session.setAttribute("htmlList", htmlList);
			
			String url_guideSummary = "/guideSummary.jsp";
			getServletContext().getRequestDispatcher(url_guideSummary).forward(request, response);	
		}//if form02AutoMaintenance
		else if(state.equals("form03guideSummary")) {
			
			String [] changeHVAC_Filter_Summary 	= new String [5]; 
			String [] cleanWindows_Summary 			= new String [5];
			String [] cleanLeadersGutters_Summary 	= new String [5];
			String [] WaterHeater_Summary 			= new String [5];
			String [] WaterFilters_Summary 			= new String [5];
			String [] SmokeAlarmBattery_Summary 	= new String [5];
			String [] HVAC_Service_Summary 			= new String [5];
			String [] cleanChimney_Summary 			= new String [5];
			
			String [] changeOil_Summary             = new String [5];
			String [] flushCoolant_Summary          = new String [5];
			String [] checkTirePressure_Summary     = new String [5];
			String [] refillWiperFluid_Summary      = new String [5];
			
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
			

			try{
				changeHVAC_Filter_Summary = request.getParameterValues("Change Furnace/ac air filter");
				if(changeHVAC_Filter_Summary[0].equals("Change Furnace/ac air filter")) { 
					taskBeanSummary beanSummary = new taskBeanSummary(changeHVAC_Filter_Summary[0], Integer.parseInt(changeHVAC_Filter_Summary[1]), changeHVAC_Filter_Summary[2], changeHVAC_Filter_Summary[3], changeHVAC_Filter_Summary[4]);
					taskList.add(beanSummary);
				}//if changeHVAC_Filter_Summary
				//out.println("<p>changeHVAC_Filter_Summary[0] = "   + changeHVAC_Filter_Summary[0]   + ", changeHVAC_Filter_Summary[1] = "   + changeHVAC_Filter_Summary[1]   + ", changeHVAC_Filter_Summary[2] = "   + changeHVAC_Filter_Summary[2]   + ", changeHVAC_Filter_Summary[3] = "   + changeHVAC_Filter_Summary[3]   + "</p><br>");
			} catch(NullPointerException e) {
				
			}//catch

			try{
				cleanWindows_Summary = request.getParameterValues("Clean windows");
				if(cleanWindows_Summary[0].equals("Clean windows")) {
					taskBeanSummary beanSummary = new taskBeanSummary(cleanWindows_Summary[0], Integer.parseInt(cleanWindows_Summary[1]), cleanWindows_Summary[2], cleanWindows_Summary[3], cleanWindows_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>cleanWindows_Summary[0] = "        + cleanWindows_Summary[0]        + ", cleanWindows_Summary[1] = "        + cleanWindows_Summary[1]        + ", cleanWindows_Summary[2] = "        + cleanWindows_Summary[2]        + ", cleanWindows_Summary[3] ="         + cleanWindows_Summary[3]        + "</p><br>");
			} catch(NullPointerException e) {
	
			}//catch
			
			try{
				cleanLeadersGutters_Summary = request.getParameterValues("Clean gutter and roof leaders");
				if(cleanLeadersGutters_Summary[0].equals("Clean gutter and roof leaders")) {
					taskBeanSummary beanSummary = new taskBeanSummary(cleanLeadersGutters_Summary[0], Integer.parseInt(cleanLeadersGutters_Summary[1]), cleanLeadersGutters_Summary[2], cleanLeadersGutters_Summary[3], cleanLeadersGutters_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>cleanLeadersGutters_Summary[0] = " + cleanLeadersGutters_Summary[0] + ", cleanLeadersGutters_Summary[1] = " + cleanLeadersGutters_Summary[1] + ", cleanLeadersGutters_Summary[2] = " + cleanLeadersGutters_Summary[2] + ", cleanLeadersGutters_Summary[3] = " + cleanLeadersGutters_Summary[3] + "</p><br>");
			} catch(NullPointerException e) {
			
			}//catch
				

			try{
				WaterHeater_Summary = request.getParameterValues("Water heater maintenance");
				if(WaterHeater_Summary[0].equals("Water heater maintenance")) {
					taskBeanSummary beanSummary = new taskBeanSummary(WaterHeater_Summary[0], Integer.parseInt(WaterHeater_Summary[1]), WaterHeater_Summary[2], WaterHeater_Summary[3], WaterHeater_Summary[4]);
					taskList.add(beanSummary);
				}//if
				//out.println("<p>WaterHeater_Summary[0] = "         + WaterHeater_Summary[0]         + ", WaterHeater_Summary[1] = "         + WaterHeater_Summary[1]         + ", WaterHeater_Summary[2] = "         + WaterHeater_Summary[2]         + ", WaterHeater_Summary[3] = "         + WaterHeater_Summary[3]         + "</p><br>");
			} catch(NullPointerException e) {
				
			}//catch
		
			
			/**
			XChange oil
			XFlush Coolant
			XCheck tire pressure
			XRefill wiper fluid
			...
			...
			**/
			
			try{
				changeOil_Summary = request.getParameterValues("Change oil");
				if(changeOil_Summary[0].equals("Change oil")) {
					taskBeanSummary beanSummary = new taskBeanSummary(changeOil_Summary[0], Integer.parseInt(changeOil_Summary[1]), changeOil_Summary[2], changeOil_Summary[3], changeOil_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>changeOil_Summary[0] = "         + changeOil_Summary[0]         + ", changeOil_Summary[1] = "         + changeOil_Summary[1]         + ", changeOil_Summary[2] = "         + changeOil_Summary[2]         + ", changeOil_Summary[3] ="          + changeOil_Summary[3]         + "</p><br>");
			} catch(NullPointerException e) {
			
			}//catch
		
			try{
				flushCoolant_Summary = request.getParameterValues("Flush Coolant");
				if(flushCoolant_Summary[0].equals("Flush Coolant")) {
					taskBeanSummary beanSummary = new taskBeanSummary(flushCoolant_Summary[0], Integer.parseInt(flushCoolant_Summary[1]), flushCoolant_Summary[2], flushCoolant_Summary[3], flushCoolant_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>flushCoolant_Summary[0] = "      + flushCoolant_Summary[0]      + ", flushCoolant_Summary[1] = "      + flushCoolant_Summary[1]      + ", flushCoolant_Summary[2] = "      + flushCoolant_Summary[2]      + ", flushCoolant_Summary[3] = "      + flushCoolant_Summary[3]      + "</p><br>");
			} catch(NullPointerException e) {

			}//catch

			try{
				checkTirePressure_Summary = request.getParameterValues("Check tire pressure");
				if(checkTirePressure_Summary[0].equals("Check tire pressure")) {
					taskBeanSummary beanSummary = new taskBeanSummary(checkTirePressure_Summary[0], Integer.parseInt(checkTirePressure_Summary[1]), checkTirePressure_Summary[2], checkTirePressure_Summary[3], checkTirePressure_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>checkTirePressure_Summary[0] = " + checkTirePressure_Summary[0] + ", checkTirePressure_Summary[1] = " + checkTirePressure_Summary[1] + ", checkTirePressure_Summary[2] = " + checkTirePressure_Summary[2] + ", checkTirePressure_Summary[3] = " + checkTirePressure_Summary[3] + "</p><br>");
			} catch(NullPointerException e) {
	
			}//catch
			
			try{
				refillWiperFluid_Summary = request.getParameterValues("Refill wiper fluid");
				if(refillWiperFluid_Summary[0].equals("Refill wiper fluid")) {
					taskBeanSummary beanSummary = new taskBeanSummary(refillWiperFluid_Summary[0], Integer.parseInt(refillWiperFluid_Summary[1]), refillWiperFluid_Summary[2], refillWiperFluid_Summary[3], refillWiperFluid_Summary[4]);
					taskList.add(beanSummary);
				}//if 
				//out.println("<p>refillWiperFluid_Summary[0] = "  + refillWiperFluid_Summary[0]  + ", WaterHeater_Summary[1] = "       + refillWiperFluid_Summary[1]  + ", refillWiperFluid_Summary[2] = "  + refillWiperFluid_Summary[2]  + ", refillWiperFluid_Summary[3] = "  + refillWiperFluid_Summary[3]  + "</p><br>");
			} catch(NullPointerException e) {
			
			}//catch

			
			eventList = recurringTaskBuilder.buildRecurringTasks(taskList);
			
			session.setAttribute("eventListAttr", eventList);

			String url_renderCalendar = "/renderCalendar.jsp";
			getServletContext().getRequestDispatcher(url_renderCalendar).forward(request, response);

		}//if form03guideSummary

	}//doPost

	
}//TheServlet
