package com.accenture.adf.businesstier.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.dao.VisitorDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;
import com.accenture.adf.helper.FERSDbQuery;

/**
 * <br/>
 * CLASS DESCRIPTION: <br/>
 * 
 * A service class that implements the VisitorFacade. Makes visitor-related data
 * requests to the VisitorDAO class <br/>
 * 
 */

public class VisitorServiceImpl implements VisitorFacade {

	VisitorDAO vdao = new VisitorDAO(); ///new line added
	EventDAO edao = new EventDAO();
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private FERSDbQuery query;
	
	//LOGGER for logging all exceptions of VISITOR DAO
	private static Logger log = Logger.getLogger(VisitorServiceImpl.class);

	/**
	 * <br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	SERVICE CLASS for inserting new visitor<br/>
	 *  <br/>
	 *  
	 *  PSEUDOCODE: <br/>
	 *  Call insertData () in a new DAO object and store the returned Boolean value<br/>
	 *  Catch all possible exceptions and log to the error file the provided exception message.<br/>
	 *  If no exceptions occur, return the Boolean value<br/>
	 *  <br/>
	 *   
	 * 	@param visitor (type Visitor)
	 * 
	 * 	@return boolean
	 * 
	 * 	@throws ClassNotFoundException
	 * 	@throws SQLException
	 *	@throws Exception
	 *	  
	 */
	
	public boolean createVisitor(Visitor visitor) {

		boolean insertStatus = false;
		
		// TODO:  Add code here.....
		// TODO:  Pseudo-code are in the block comments above this method
		// TODO:  For more comprehensive pseudo-code with details, refer to the Component/Class Detailed Design Document
		try {
			insertStatus = vdao.insertData(visitor);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		
		} catch (SQLException e) {
			log.error(e.getMessage());

		} catch (Exception e) {
			log.error(e.getMessage());

		}
			
		return insertStatus;
	}

	/**
	 * <br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	SERVICE CLASS for searching visitor details<br/>
	 *  <br/>
	 *  
	 *  PSEUDOCODE: <br/>
	 *  Call searchUser () in a new DAO object and reference the returned Visitor object<br/>
	 *  Catch all possible exceptions and log to the error file the provided exception message.<br/>
	 *  If no exceptions occur, return the Visitor Object reference<br/>
	 *  <br/>
	 *   
	 * 	@param username (type String)
	 * 	@param password (type String)
	 * 
	 * 	@return Visitor
	 * 
	 * 	@throws ClassNotFoundException
	 * 	@throws SQLException
	 *	@throws Exception
	 *	  
	 */
	
	public Visitor searchVisitor (String username, String password) {

		
		Visitor visitor = new Visitor();
		
		try {
			visitor = vdao.searchUser(username, password);
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		// TODO:  Add code here.....
		// TODO:  Pseudo-code are in the block comments above this method
		// TODO:  For more comprehensive pseudo-code with details, refer to the Component/Class Detailed Design Document
			
		return visitor; // Replace null with "visitor" object, as required after completing the code in the TODOs above 
	}

	/**
	 * <br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	SERVICE CLASS for registering user to event<br/>
	 *  <br/>
	 *  
	 *  PSEUDOCODE: <br/>
	 * 	Call registerVisitorToEvent () in a new DAO object.<br/>
	 * 	Call updateEventNominations() in a new DAO object.<br/>
	 * 	Catch all possible exceptions and log to the error file the provided exception message.<br/> 
	 *  <br/>
	 *   
	 * 	@param visitor (type Visitor)
	 * 	@param eventid (type int)
	 * 
	 *	@throws ClassNotFoundException
	 * 	@throws SQLException
	 *	@throws Exception
	 *	  
	 */
	/*CLean*/
	public void RegisterVisitor(Visitor visitor, int eventid) {

		// TODO:  Add code here.....
		// TODO:  Pseudo-code are in the block comments above this method
		// TODO:  For more comprehensive pseudo-code with details, refer to the Component/Class Detailed Design Document
					
		
		
			try {
				vdao.registerVisitorToEvent(visitor, eventid);
				edao.updateEventNominations(eventid);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
					
	}

	/**
	 * <br/>
	 *  METHOD DESCRIPTION:<br/>
	 *	SERVICE CLASS for displaying all registered events<br/>
	 *  <br/>
	 *  
	 *  PSEUDOCODE: <br/>
	 * 	Call registeredEvents () in a new DAO object and reference the return ArrayList of Event objects<br/>
	 * 	Catch all possible exceptions and log to the error file the provided exception message.<br/>
	 * 	If no exceptions occur, return the reference to the list of event objects<br/>
	 *  <br/>
	 *   
	 * 	@param visitor (type Visitor)
	 * 
	 *	@throws ClassNotFoundException
	 * 	@throws SQLException
	 *	@throws Exception
	 *
	 *	@return List of Event Object Array 
	 *	  
	 */
	public ArrayList<Event> showRegisteredEvents(Visitor visitor) {

		// TODO:  Add code here.....
		// TODO:  Pseudo-code are in the block comments above this method
		// TODO:  For more comprehensive pseudo-code with details, refer to the Component/Class Detailed Design Document
				
		ArrayList<Event> registeredEventList = new ArrayList<Event>();
		
		try {
			registeredEventList = vdao.registeredEvents(visitor);
		} catch (ClassNotFoundException e) {
			log.equals(e.getMessage());

		} catch (SQLException e) {
			log.equals(e.getMessage());

		}
		
		
		return registeredEventList; // TODO Replace "null" with "ArrayList<Event>" collection, based on updates to code in the TODO section.
	}

	/**
	 * SERVICE CLASS for updating visitor details
	 */
	public int updateVisitorDetails(Visitor visitor) {
		VisitorDAO visitorDAO = new VisitorDAO();
		int status = 0;
		try {
			status = visitorDAO.updateVisitor(visitor);
		} catch (ClassNotFoundException exception) {
			log.info("Exception is :" + exception.getMessage());
			System.out.println(exception.getMessage());
		} catch (SQLException exception) {
			log.info("Exception is :" + exception.getMessage());
			System.out.println(exception.getMessage());
		}
		return status;
	}

	/**
	 * SERVICE CLASS for removing event registered by visitor
	 */
	public void unregisterEvent(Visitor visitor, int eventid) {

		VisitorDAO visitorDAO = new VisitorDAO();
		try {
			visitorDAO.unregisterEvent(visitor, eventid);
			//edao.updateEventDeletions(eventid);
		} catch (ClassNotFoundException exception) {
			System.out.println(exception.getMessage());

			log.info("Exception is :" + exception.getMessage());
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());

			log.info("Exception is :" + exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());

			log.info("Exception is :" + exception.getMessage());
		}
	}
	
	public int changePassword(Visitor visitor) throws FERSGenericException {

		//TODO: Create a new instance of VisitorDAO object
		VisitorDAO visitorDAO = new VisitorDAO();
		int status=0;
		 
		try{
			status = visitorDAO.changePassword(visitor);
		}catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
			log.error(e);
		}catch(SQLException sq){
			System.out.println(sq.getMessage());
			log.error(sq);
		}
		return status;

		//TODO: Within a return statement invoke changePassword() method on VisitorDAO with the new instance of the Visitor object created in previous TODO as password
		//TODO: Surround step 3 in a try..catch block for ClassNotFoundException and SQLException
		//TODO: In catch block log an error to log file with the error message from the exception object and rethrow the exception as FERSGenericException attaching original exception object

		}

}
