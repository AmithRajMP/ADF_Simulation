package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.VisitorServiceImpl;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * Junit test class for VisitorServiceImpl
 *
 */
public class TestVisitorServiceImpl {
	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;
	

	private List<Event> visitorList;	
	private Visitor visitor;
	private VisitorServiceImpl visitorServiceImpl;

	/**
	 * Set up the initial methods 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {		
		visitorServiceImpl = new VisitorServiceImpl();
		visitor = new Visitor();
	}

	/**
	 * Deallocates the objects after execution of every method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		visitorServiceImpl = null;
		visitor = null;
	
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testCreateVisitor() {
		/**
		 * @TODO: Set the appropriate values for visitor object and
		 * call the method createVisitor by passing an argument of this visitor 
		 * object and then asserting the returned type of this method
		 */		
	//	visitorList.add("event1");
		
		
		visitor.setAddress("address");
		visitor.setEmail("email");
		visitor.setFirstName("firstName");
		visitor.setLastName("LastName");
		visitor.setPassword("password");
		visitor.setPhoneNumber("phoneNumber");
		visitor.setUserName("userName");
		
		
		boolean status = visitorServiceImpl.createVisitor(visitor);
		
		assertEquals(false, status);
		
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testSearchVisitor() {
		/**
		 * @TODO: Call searchVisitor method by passing the appropriate arguments 
		 * and then asserting the returned type visitor username with the argument passed
		 */		
		visitor = visitorServiceImpl.searchVisitor("bsmith", "password");
		
		
		//Visitor v = new Visitor();
		
		
		assertEquals("bsmith",visitor.getUserName() );

		
		
	}

	/**
	 * Test case for method RegisterVisitor
	 * @throws FERSGenericException 
	 */
	
	
	/*CLean*/
	@Test
	public void testRegisterVisitor() throws FERSGenericException {
		/**
		 * @TODO: Call RegisterVisitor method by passing visitor object which 
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of RegisterVisitor method 
		 */		
		visitor = visitorServiceImpl.searchVisitor("bsmith", "password");

		EventDAO edao = new EventDAO();
		visitorServiceImpl.RegisterVisitor(visitor, 1001);
		
		
		
		try {
			connection = FERSDataConnection.createConnection();
			
			String qry = "SELECT COUNT(*) AS EVENTCOUNT FROM EVENTSIGNUP WHERE EVENTID=? AND VISITORID=?";
			
			
			statement = connection.prepareStatement(qry);
			statement.setInt(1, 1001);
			statement.setInt(2, visitor.getVisitorId());
			
			resultSet = statement.executeQuery();
			
			 resultSet.next();
			
			 int count = resultSet.getInt(1);
			 
			 
			 assertEquals(1, count);
			 
			 
			 int retVal;
				int retVal2;
				connection = FERSDataConnection.createConnection();
				statement = connection.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = 1001");
				resultSet =	statement.executeQuery();
				resultSet.next();
				retVal = resultSet.getInt("SEATSAVAILABLE");
				retVal--;
				
				edao.updateEventNominations(1001); 
				
				
				resultSet =	statement.executeQuery();
				resultSet.next();
				retVal2 = resultSet.getInt("SEATSAVAILABLE");
				
				assertEquals(retVal,retVal2 );
			 
			 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		}
		
		
	}

	/**z
	 * Test case for method showRegisteredEvents
	 */
	@Test
	public void testShowRegisteredEvents() {
		/**
		 * @TODO: Call showRegisteredEvents method by passing visitor object which 
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of showRegisteredEvents method 
		 */		
		ArrayList<Event> registeredEventList = new ArrayList<Event>();

		visitor = visitorServiceImpl.searchVisitor("bsmith", "password");
		
		
		registeredEventList = visitorServiceImpl.showRegisteredEvents(visitor);
		
		assertEquals(1, registeredEventList.size());
	}

	/**
	 * Test case for method updateVisitorDetails
	 */
	@Test
	public void testUpdateVisitorDetails() {
		/**
		 * @TODO: Call updateVisitorDetails method by passing the visitor object which
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of updateVisitorDetails
		 */		
		visitor = visitorServiceImpl.searchVisitor("bsmith", "password");

		int status = visitorServiceImpl.updateVisitorDetails(visitor);
		
		assertEquals(1, status);
		
	}

	/**
	 * Test case for method unregisterEvent
	 */
	@Test
	public void testUnregisterEvent() throws ClassNotFoundException, SQLException {
		/**
		 * @TODO: Call unregisterEvent method by passing the visitor object which can be
		 * retrieved using searchVisitor method and then asserting the returned type 
		 * of unregisterEvent
		 */		
		
	int status=0;
		visitor = visitorServiceImpl.searchVisitor("jon", "xxxx");
		try {
			visitorServiceImpl.unregisterEvent(visitor,1001);
			String qry = "SELECT COUNT(*) AS EVENTCOUNT FROM EVENTSIGNUP WHERE EVENTID=1002 AND VISITORID=1001 ;";
			connection = FERSDataConnection.createConnection();
			statement = connection.prepareStatement(qry);
		
			resultSet= statement.executeQuery();
			resultSet.next();
			status=resultSet.getInt(1);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		assertEquals(0,status);
	

	}

}
