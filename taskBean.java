package TheServlet;

import java.io.Serializable;

public class taskBean implements Serializable {

	public taskBean() {
		// TODO Auto-generated constructor stub
	}
	
	public taskBean(String status, int reschedulePeriod, String taskName) {
		this.status           = status;
		this.reschedulePeriod = reschedulePeriod;
		this.taskName         = taskName;
	}
	
	
	
	String status = null;
	int reschedulePeriod = 0;
	String taskName = "noTask";
	
	
	private void setStatus (String status) {
		this.status = status;
	}//setStatus
	
	public String getStatus () {
		return status;
	}//getStatus

	
	
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
	
	
}
