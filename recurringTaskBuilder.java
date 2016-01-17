package TheServlet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;


public class recurringTaskBuilder {

static DayOfWeek 	nowDayOfWeek;
static int 			nowDayOfMonth;
static Month 		nowMonth;
static int 			nowYear;

static DayOfWeek 	nextDayOfWeek;
static int 			nextDayOfMonth;
static Month 		nextMonth;

static LocalDate anniversary;
static LocalDate today;

static List<taskBeanSummary> recurringTaskList = new ArrayList<>(); 
static int i=0;

static String[]		splitTaskDate = new String[3];
static String 		taskDate;
static String 		taskName;
static String 		recurrenceType;
static LocalDate	taskDateTemporal;

static LocalDate	mainTaskDateTemporal;

/**
	public recurringTaskBuilder() {
		// TODO Auto-generated constructor stub

	}//Constructor: recurringTaskBuilder
**/
	

	public static List<taskBeanSummary> buildRecurringTasks (List<taskBeanSummary> listIn) {
		
		today 			= LocalDate.now();
		anniversary		= today.plusYears((long) 1); //Calculate recurring tasks for a sliding 1 year into the future.
		
		nowDayOfWeek  	= today.getDayOfWeek();
		nowDayOfMonth 	= today.getDayOfMonth();
		nowMonth 		= today.getMonth();
		nowYear 		= today.getYear();
		
		
		System.out.println("nowDayOfWeek: " + nowDayOfWeek);
		System.out.println("nowDayOfMonth: " + nowDayOfMonth);
		System.out.println("nowMonth: " + nowMonth);
		System.out.println("nowYear: " + nowYear);
		System.out.println("nextYear: Year= " + anniversary.getYear() + ",  Month= " + anniversary.getMonth() + ", Day= " + anniversary.getDayOfWeek() + " the " + anniversary.getDayOfMonth() + "th.");
		System.out.println("");
		
		Iterator<taskBeanSummary> itrTaskBeanSummary = listIn.iterator();
		
		while (itrTaskBeanSummary.hasNext()) {
			
			taskBeanSummary taskListBean = itrTaskBeanSummary.next();
			
			System.out.println("Recurrence period= " 	+ taskListBean.getReschedulePeriod());
			System.out.println("Task name= " 			+ taskListBean.getTaskName());
			System.out.println("Task date= " 			+ taskListBean.getTaskDate());
			System.out.println("Recurrence Type= "		+ taskListBean.getRecurrenceType());

			taskName 		= taskListBean.getTaskName();
			taskDate 		= taskListBean.getTaskDate();
			recurrenceType	= taskListBean.getRecurrenceType();
			splitTaskDate 	= taskDate.split("-");
			
			taskDateTemporal = LocalDate.of(Integer.parseInt(splitTaskDate[0]), Integer.parseInt(splitTaskDate[1]), Integer.parseInt(splitTaskDate[2]));
			
			while (taskDateTemporal.isBefore(anniversary)) {
				
				if (i%taskListBean.getReschedulePeriod() == 0) { //Determine the months for which events are generated.
						
						System.out.println("i= " + i + ", i%taskListBean.getReschedulePeriod()= " + i%taskListBean.getReschedulePeriod());
						
						if (recurrenceType.equals("Same Weekday Each Month")) {

							LocalDate numFirstOfTheMonth	= today.minusDays((long) nowDayOfMonth-1);
							LocalDate numSecondOfTheMonth	= today.minusDays((long) nowDayOfMonth-2);
							LocalDate numThirdOfTheMonth	= today.minusDays((long) nowDayOfMonth-3);
							
							DayOfWeek firstDayOfTheMonth	= numFirstOfTheMonth.getDayOfWeek();
							DayOfWeek secondDayOfTheMonth	= numSecondOfTheMonth.getDayOfWeek();
							DayOfWeek thirdDayOfTheMonth	= numThirdOfTheMonth.getDayOfWeek();
							
							taskBeanSummary eventBean = new taskBeanSummary(taskListBean.getTaskName(), taskListBean.getReschedulePeriod(), taskDateTemporal.toString(), taskListBean.getTaskTime(), taskListBean.getRecurrenceType());
							recurringTaskList.add(eventBean);
							
							if (today.lengthOfMonth()==31) {
								if(	taskDateTemporal.getDayOfWeek().equals(firstDayOfTheMonth)|
									taskDateTemporal.getDayOfWeek().equals(secondDayOfTheMonth)|
									taskDateTemporal.getDayOfWeek().equals(thirdDayOfTheMonth)) {
										taskDateTemporal = taskDateTemporal.plusDays((long) 35);
								} else {
										taskDateTemporal = taskDateTemporal.plusDays((long) 28);
								}//else
							} else if (today.lengthOfMonth()==30) {
								if(	taskDateTemporal.getDayOfWeek().equals(firstDayOfTheMonth)|
									taskDateTemporal.getDayOfWeek().equals(secondDayOfTheMonth)) {
										taskDateTemporal = taskDateTemporal.plusDays((long) 35);
									} else {
										taskDateTemporal = taskDateTemporal.plusDays((long) 28);
									}//else
							} else if (today.lengthOfMonth()==29) {
								if(	taskDateTemporal.getDayOfWeek().equals(firstDayOfTheMonth)) {
										taskDateTemporal = taskDateTemporal.plusDays((long) 35);
									} else {
										taskDateTemporal = taskDateTemporal.plusDays((long) 28);
									}//else
							} else if (today.lengthOfMonth()==28) {
								taskDateTemporal = taskDateTemporal.plusDays((long) 28);
							}//else

							
							
						} else if (recurrenceType.equals("Same Day-of-the-Month Each Month")) {
		
							System.out.println("Length of Month: " + taskDateTemporal.lengthOfMonth());
							System.out.println(taskDateTemporal.getYear()+"/"+taskDateTemporal.getMonthValue()+"/"+taskDateTemporal.getDayOfMonth());
							System.out.println("Date String= " + taskDateTemporal.toString());
							taskBeanSummary eventBean = new taskBeanSummary(taskListBean.getTaskName(), taskListBean.getReschedulePeriod(), taskDateTemporal.toString(), taskListBean.getTaskTime(), taskListBean.getRecurrenceType());
							recurringTaskList.add(eventBean);
		
							taskDateTemporal = taskDateTemporal.plusDays((long) taskDateTemporal.lengthOfMonth());
						}//else if (recurrenceType)
				
				}//if(i%taskListBean.getReschedulePeriod() == 0)		
				
				i++;	
			}//while ()
			
			i=0;
			System.out.println("");
			
		}//while (itrTaskBeanSummary)
		
		return recurringTaskList;
		
	}//buildRecurringTasks

	
	
	

	public static void main (String[] args) {
		
		taskBeanSummary bean1= new taskBeanSummary("A"	, 5		, "2016-01-17"	, "C"	, "Same Weekday");
		taskBeanSummary bean2= new taskBeanSummary("AA"	, 55	, "2016-02-02"	, "CC"	, "Same Weekday");
		taskBeanSummary bean3= new taskBeanSummary("AAA", 555	, "2016-03-23"	, "CCC"	, "Same Weekday");
		
		List<taskBeanSummary> mainTempList = new ArrayList<>();
		
		mainTempList.add(bean1);
		mainTempList.add(bean2);
		mainTempList.add(bean3);
		
		recurringTaskBuilder.buildRecurringTasks(mainTempList);
		
		Iterator<taskBeanSummary> mainItr = mainTempList.iterator();
		
		String[] splitTaskDateMain = new String[3];
		
		while(mainItr.hasNext()) {
			
			taskBeanSummary abc = mainItr.next();
			System.out.println("taskName: " + abc.taskName);
			splitTaskDateMain = abc.taskDate.split("-");
			
			System.out.println("splitTaskDateMain[0]: " + splitTaskDateMain[0]);
			System.out.println("splitTaskDateMain[1]: " + splitTaskDateMain[1]);
			System.out.println("splitTaskDateMain[2]: " + splitTaskDateMain[2]);
			
			mainTaskDateTemporal = LocalDate.of(Integer.parseInt(splitTaskDateMain[0]), Integer.parseInt(splitTaskDateMain[1]), Integer.parseInt(splitTaskDateMain[2]));
			
			System.out.println("Year: " + mainTaskDateTemporal.getYear() + ", " + "Month: " + mainTaskDateTemporal.getMonth() + ", " + "Day: " + mainTaskDateTemporal.getDayOfMonth()+ ", " + "Day-of-the-Week: " + mainTaskDateTemporal.getDayOfWeek());
			System.out.println("");
		}//while(mainItr.hasNext())
		
		
	}//main

	
}//Class: recurringTaskBuilder
