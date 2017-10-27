package com.accenture.adf.businesstier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;
import com.accenture.adf.helper.FERSDbQuery;

/**
 * 
 * <br/>
 * CLASS DESCRIPTION:<br/>
 * A Data Access Object (DAO) class for handling and managing visitor related
 * data requested, used, and processed in the application and maintained in the
 * database. The interface between the application and visitor data persisting
 * in the database.
 * 
 */

public class VisitorDAO {

	// LOGGER for handling all transaction messages in VISITORDAO
	private static Logger log = Logger.getLogger(VisitorDAO.class);

	// JDBC API classes for data persistence
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private FERSDbQuery query;

	// Default constructor for injecting Spring dependencies for SQL queries
	public VisitorDAO() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		query = (FERSDbQuery) context.getBean("SqlBean");
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method to loading visitor details into VISITOR table in database<br/>
	 * and validating about existing visitor details before inserting a visitor
	 * <br/>
	 * 
	 * <br/>
	 * PSEUDOCODE: <br/>
	 * Create a connection to database<br/>
	 * Prepare a statement object using the connection that uses a query that
	 * inserts visitor information <br/>
	 * into the visitor table <br/>
	 * Execute a statement object selects all the usernames from the visitor
	 * table<br/>
	 * if the username is not in the visitor table <br/>
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * 
	 * @return boolean
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */
	/* METHOD DONE */
	public boolean insertData(Visitor visitor) throws ClassNotFoundException, SQLException, Exception {

		connection = FERSDataConnection.createConnection();
		String iQry = query.getInsertQuery();

		statement = connection.prepareStatement(query.getValidateVisitor());
		resultSet = statement.executeQuery();

		boolean useFound = false;
		while (resultSet.next()) {
			if (visitor.getUserName().equalsIgnoreCase(resultSet.getString("username"))) {
				useFound = true;
				break;
			}
		}

		Boolean flag = false;
		if (!useFound) {
			statement = connection.prepareStatement(iQry);
			statement.setString(1, visitor.getUserName());
			statement.setString(2, visitor.getPassword());
			statement.setString(3, visitor.getFirstName());
			statement.setString(4, visitor.getLastName());
			statement.setString(5, visitor.getEmail());
			statement.setString(6, visitor.getPhoneNumber());
			statement.setString(7, visitor.getAddress());

			int retVal = statement.executeUpdate();
			if (retVal > 0)
				flag = true;
		}
		FERSDataConnection.closeConnection();
		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method
		// TODO: For more comprehensive pseudo-code with details, refer to the
		// Component/Class Detailed Design Document

		return flag;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method for searching for visitor details using USERNAME and PASSWORD
	 * <br/>
	 * 
	 * <br/>
	 * PSEUDOCODE: <br/>
	 * Create a connection to database<br/>
	 * Prepare a statement object using the connection<br/>
	 * that uses a query that retrieves all the data from the visitor table
	 * based on the username and password provided. Execute the query and <br/>
	 * Using a WHILE LOOP, store the results in the result set record in the
	 * visitor object.<br/>
	 * 
	 * @param username
	 *            (type String)
	 * @param password
	 *            (type String)
	 * 
	 * @return Visitor
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * 
	 */
	/* DONE */
	public Visitor searchUser(String username, String password) throws ClassNotFoundException, SQLException {

		Visitor visitor = new Visitor();

		connection = FERSDataConnection.createConnection();
		String sqry = query.getSearchQuery();
		statement = connection.prepareStatement(sqry);
		statement.setString(1, username);
		statement.setString(2, password);

		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			visitor.setFirstName(resultSet.getString("firstname"));
			visitor.setLastName(resultSet.getString("lastname"));
			visitor.setUserName(resultSet.getString("username"));
			visitor.setPassword(resultSet.getString("password"));
			visitor.setEmail(resultSet.getString("email"));
			visitor.setPhoneNumber(resultSet.getString("phonenumber"));
			visitor.setAddress(resultSet.getString("address"));
			visitor.setVisitorId(resultSet.getInt("visitorid"));
		}

		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method
		// TODO: For more comprehensive pseudo-code with details, refer to the
		// Component/Class Detailed Design Document
		return visitor;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO method to register visitor to specific event and checking about
	 * status of visitor to particular event. <br/>
	 * 
	 * PSEUDO-CODE: <br/>
	 * Create a connection to the database <br/>
	 * Prepare a statement object using the connection: that inserts the visitor
	 * and event IDs into the EVENTSESSIONSIGNUP table <br/>
	 * Execute the query to perform the update <br/>
	 * 
	 * 
	 * @param visitor
	 * @param eventid
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */
	/* clean */
	public void registerVisitorToEvent(Visitor visitor, int eventid)
			throws ClassNotFoundException, SQLException, Exception {

		int status;
		Boolean alreadyRegistered = false;
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getCheckEvent());
		statement.setInt(1, eventid);
		statement.setInt(2, visitor.getVisitorId());

		resultSet = statement.executeQuery();
		resultSet.next();

		status = resultSet.getInt(1);

		if (status >= 1)
			alreadyRegistered = true;
		else
			alreadyRegistered = false;

		if (!alreadyRegistered) {
			String rqry = query.getRegisterQuery();
			statement = connection.prepareStatement(rqry);
			statement.setInt(1, eventid);
			statement.setInt(2, visitor.getVisitorId());

			statement.executeUpdate();
		} else {
			throw new FERSGenericException("Already Registered", new Exception());
		}
		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method
		// TODO: For more comprehensive pseudo-code with details, refer to the
		// Component/Class Detailed Design Document

	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method to display all the events registered by particular visitor
	 * <br/>
	 * 
	 * PSEUDO-CODE: <br/>
	 * Create a connection to the database <br/>
	 * Prepare a statement object using the connection: that returns the event
	 * information for all the events that are registered to a visitor<br/>
	 * Execute the query to retrieve the results into a result set<br/>
	 * Place each event record‘s information in an event list. <br/>
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * 
	 * @return Collection of Event Arrays (type Event)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 */

	/* DONE */

	public ArrayList<Event> registeredEvents(Visitor visitor) throws ClassNotFoundException, SQLException {

		ArrayList<Event> registeredEventList = new ArrayList<Event>();

		connection = FERSDataConnection.createConnection();
		String statusQuery = query.getStatusQuery();
		statement = connection.prepareStatement(statusQuery);
		statement.setInt(1, visitor.getVisitorId());

		resultSet = statement.executeQuery();
		// SELECT E1.EVENTID, E1.NAME, E1.DESCRIPTION, E1.PLACES, E1.DURATION,
		// E1.EVENTTYPE , E2.SIGNUPID FROM EVENT E1, EVENTSIGNUP E2 WHERE
		// E1.EVENTID = E2.EVENTID AND E2.VISITORID = 1001;
		while (resultSet.next()) {
			Event e = new Event();
			e.setDescription(resultSet.getString("description"));
			e.setName(resultSet.getString("name"));
			e.setEventid(resultSet.getInt("eventid"));
			e.setPlace(resultSet.getString("places"));
			e.setDuration(resultSet.getString("duration"));
			e.setEventtype(resultSet.getString("eventtype"));
			e.setSignupid(resultSet.getInt("signupid"));

			registeredEventList.add(e);
		}

		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method
		// TODO: For more comprehensive pseudo-code with details, refer to the
		// Component/Class Detailed Design Document

		return registeredEventList;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method to update visitor with additional information <br/>
	 * <br/>
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * 
	 * @return int
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * 
	 */
	public int updateVisitor(Visitor visitor) throws ClassNotFoundException, SQLException {
		System.out.println("0");
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getUpdateQuery());
		statement.setString(1, visitor.getFirstName());
		statement.setString(2, visitor.getLastName());
		statement.setString(3, visitor.getUserName());
		statement.setString(4, visitor.getPassword());
		statement.setString(5, visitor.getEmail());
		statement.setString(6, visitor.getPhoneNumber());
		statement.setString(7, visitor.getAddress());
		statement.setInt(8, visitor.getVisitorId());

		int status = statement.executeUpdate();
		System.out.println(status);
		log.info("Updating visitor details in Database for Visitor ID :" + visitor.getVisitorId());
		FERSDataConnection.closeConnection();
		return status;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO method to unregister from events <br/>
	 * 
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * @param eventid
	 *            (type int)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */
	/* clean */

	public void unregisterEvent(Visitor visitor, int eventid) throws ClassNotFoundException, SQLException, Exception {
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getDeleteEventQuery());
		statement.setInt(1, eventid);
		statement.setInt(2, visitor.getVisitorId());
		int status = statement.executeUpdate();
		if (status <= 0)
			throw new FERSGenericException("Records not Present", new Exception());
		log.info("unregistering event in Database for the visitor :" + visitor.getFirstName());
		FERSDataConnection.closeConnection();
	}

	public int changePassword(Visitor visitor) throws ClassNotFoundException, SQLException {
		// TODO: Declare a variable with name status of type int and initialize
		// with -1
		// TODO: Start a try block
		// TODO: Get a database connection from FERSDataConnection class
		int status = -1;
		try {
			connection = FERSDataConnection.createConnection();

			if (connection != null) {
				// TODO: Add an if statement to check if Visitor object is null
				if (visitor != null) {
					if (matchWithOldPwd(visitor)) {
						status = -10;
					} else {
						statement = connection.prepareStatement(query.getChangePWDQuery());
						//System.out.println(visitor.getPassword());
						statement.setString(1, visitor.getPassword());
						statement.setInt(2, visitor.getVisitorId());
						status = statement.executeUpdate();
						if (status > 0) {
							log.info("Visitor password successfully modified");
						}

						// TODO: create a prepared statement with password
						// change query
						// TODO: Set the values of visitor and password to the
						// query parameters
						// TODO: Execute the password change query
						// TODO: Print a log message ‘Visitor password
						// successfully modified’ along with visitor ID
					}
				} else {
					log.error("Visitor details are invalid");
					// TODO: log an error message ‘Visitor details are invalid’
					// to log file
				}
			} else {

				throw new SQLException("Connection Error, could not establish connection with database");

			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		// TODO: Add a finally block to close prepared statement and connection
		// objects

		// TODO: Return status value
		return status;
	}

	private boolean matchWithOldPwd(Visitor visitor) throws SQLException{

			      String currentPWD = "";
			      try{
			    	  //connection = FERSDataConnection.createConnection();
			    	  statement = connection.prepareStatement(query.getVerifyPWDQuery());
			//TODO: Create a prepared statement with with verify password query
			    	  statement.setInt(1,visitor.getVisitorId());
			            //TODO: Set the visitor ID as parameter to the query
			    	  resultSet = statement.executeQuery();
			    	  while(resultSet.next()){
			    		  currentPWD = resultSet.getString("PASSWORD");
			    	  }
			//TODO: Execute the query and retrieve the current password if resultset  has values
			            if(currentPWD.equalsIgnoreCase(visitor.getPassword())){
			log.info("New password must be different from previous password, please choose a different password");
			return true;

			            }

			      }finally{
			    	 statement.close();
			      }
			      //TODO: Add a finally block to clean up prepared statement
			      //TODO: Return false after finally block
			      return false;

			}

}