package TheServlet;

import java.io.Serializable;

public class taskBeanSummary implements Serializable{

	public taskBeanSummary() {
		// TODO Auto-generated constructor stub
	}
	
	//Constructor
	public taskBeanSummary(String taskName, int reschedulePeriod, String taskDate, String taskTime, String recurrenceType) {
		this.reschedulePeriod 	= reschedulePeriod;
		this.taskName 			= taskName;
		this.taskDate 			= taskDate;
		this.taskTime 			= taskTime;
		this.recurrenceType 	= recurrenceType;
	}
	

	int reschedulePeriod = 0;
	String taskName = null;
	String taskDate = "2015-01-01";
	String taskTime = "00:00:00";
	String recurrenceType;
	
	
	//Getters & Setters
	private void setReschedulePeriod (int period) {
		reschedulePeriod = period;
	}//setDefaultReschedulePeriod
	
	public int getReschedulePeriod () {
		return reschedulePeriod;
	}//setDefaultReschedulePeriod
	
	
	
	private void setTaskName (String name) {
		taskName = name;
	}//setTaskName
	
	public String getTaskName () {
		return taskName;
	}//getTaskName
	
	
	
	private void setTaskDate (String taskDate) {
		this.taskDate = taskDate;
	}//setTaskDate
	
	public String getTaskDate () {
		return taskDate;
	}//getTaskDate
	

	
	private void setTaskTime (String taskTime) {
		this.taskTime = taskTime;
	}//setTaskTime
	
	public String getTaskTime () {
		return taskTime;
	}//getTaskTime
	
	

	private void setRecurrenceType (String recurrenceType) {
		this.recurrenceType = recurrenceType;
	}//setRecurrenceType
	
	public String getRecurrenceType () {
		return recurrenceType;
	}//getRecurrenceType
	
}//taskBeanSummary
