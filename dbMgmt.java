package TheServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class dbMgmt {

	public dbMgmt() {
		// TODO Auto-generated constructor stub
	}
	
	static String  result_passd;
	
	public static Connection openDB()
			throws SQLException {

			String dbURL = "jdbc:mysql://localhost:3306/smartplanner";
			String username = "root";
			String passd = "JavaClass99";
			
			Connection connection = DriverManager.getConnection(dbURL, username, passd);

			return connection;
	}//openDB
	
	public static void executeUpdate(Connection conn, String query) throws SQLException {
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
	}// executeUpdate()
	
	
	public static void executeTaskDelete(Connection conn, String user, String query) throws SQLException {
		PreparedStatement psDelete = conn.prepareStatement(query);
		psDelete.setString(1, user);
		psDelete.executeUpdate();
	}//executeTaskDelete
	
	
	public static boolean executeCreateAcct(Connection conn, String user, String passWd, String queryNewAcct, String queryExistingLogin) throws SQLException {
		boolean createAcctSuccess = true;
		
		PreparedStatement psCreateAcctTest = conn.prepareStatement(queryExistingLogin);
		psCreateAcctTest.setString(1, user);
		ResultSet userSet = psCreateAcctTest.executeQuery();
		
		while(userSet.next()) {
			if( user.equals(userSet.getString("email")) ) {
				createAcctSuccess = false;
			}//if()
		}//while()
		
		if(createAcctSuccess==true) {
			PreparedStatement psCreateAcct = conn.prepareStatement(queryNewAcct);
			psCreateAcct.setString(1, user);
			psCreateAcct.setString(2, passWd);
			psCreateAcct.executeUpdate();
		}//if()
			
		return createAcctSuccess;
	}//executeTaskDelete
	
	
	public static int executeInsert(Connection conn, String query, 
			String userID, String taskName, int recurrenceInterval, 
			String taskDate, String startTime, String recurrenceType) throws SQLException { 
				PreparedStatement psInsert = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				psInsert.setString(1, userID);
				psInsert.setString(2, taskName);
				psInsert.setInt(3, recurrenceInterval);
				psInsert.setString(4, taskDate);
				psInsert.setString(5, startTime);
				psInsert.setString(6, recurrenceType);
				psInsert.executeUpdate(); //This executeUpdate() comes from the class java.sql.Statement.
							//Its differentiated from the method by the same name defined in
							//in this servlet by signature.
				
				ResultSet results = psInsert.getGeneratedKeys();
				results.next();
				int key = results.getInt(1);
				
				return key;
				
	}//executeUpdate for INSERT
	
	public static ArrayList<taskBeanSummary> executeTaskSelect(Connection conn, String query, String userID) throws SQLException {
			PreparedStatement psSelect =conn.prepareStatement(query);
			psSelect.setString(1, userID);
			ResultSet taskBeanSummarySet= psSelect.executeQuery();
			ArrayList<taskBeanSummary> arrList = new ArrayList<>();
			
			arrList.clear();
			
			while(taskBeanSummarySet.next()) {
				String taskName 		= taskBeanSummarySet.getString("taskName");
				String taskDate 		= taskBeanSummarySet.getString("taskDate");
				String taskTime 		= taskBeanSummarySet.getString("startTime");
				String recurrenceType 	= taskBeanSummarySet.getString("recurrenceType");
				int recurrenceInterval 	= taskBeanSummarySet.getInt("recurrenceInterval");
				int iD 					= taskBeanSummarySet.getInt("iD");
			
				taskBeanSummary bean = new taskBeanSummary(taskName, recurrenceInterval, taskDate, taskTime, recurrenceType, iD);
				arrList.add(bean);
			}//while()
				
			return arrList;
	}//executeQuery for Task SELECT


	public static String executeLoginSelect(Connection conn, String query, String userID, String passWd) throws SQLException {
		String loginSuccess = "false";
		
		System.out.println("dbMgmt::executeLoginSelect:: query is: " + query + " user=" + userID);
		PreparedStatement psSelect =conn.prepareStatement(query);
		psSelect.setString(1, userID);
		ResultSet userSet= psSelect.executeQuery();
		userSet.next();
		
		try { //Handle empty ResultSet.
			result_passd = userSet.getString("passd");	
		}catch (SQLException r) { 
			result_passd = null;
			//r.printStackTrace();
		}//catch
			
		if (passWd.equals(result_passd)) {
			loginSuccess = "true";
		}//if
		System.out.println("dbMgmt::executeLoginSelect:: loginSuccess= " + loginSuccess);
		return loginSuccess;
}//executeQuery for Login SELECT


	public static void printSqlTrace(SQLException e) {
		for (Throwable t : e) {
			t.printStackTrace();
		}//for
	}

}// dbMgmt

