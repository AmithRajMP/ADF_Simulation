package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.adf.businesstier.controller.VisitorController;
import com.accenture.adf.businesstier.dao.VisitorDAO;
import com.accenture.adf.businesstier.entity.Visitor;

/**
 * Junit test case to test the class VisitorController
 *
 */
public class TestVisitorController {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private ModelAndView modelAndView;
	private VisitorController controller;
	private VisitorDAO visitorDao;

	/**
	 * Set up initial methods required before execution of every method
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		modelAndView = new ModelAndView();
		controller = new VisitorController();
		session = new MockHttpSession();
		response = new MockHttpServletResponse();
		visitorDao = new VisitorDAO();
	}

	/**
	 * Deallocate objects after execution of every method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null
		 */
		modelAndView = null;
		controller = null;
		session = null;
		response = null;
		visitorDao = null;
	}

	/**
	 * Positive test case to test the method newVisitor
	 */
	@Test
	public void testNewVisitor_Positive() {
		try {
			request = new MockHttpServletRequest("GET", "/newVistor.htm");

			request.setParameter("USERNAME", "ylee");
			request.setParameter("PASSWORD", "password");
			request.setParameter("FIRSTNAME", "TestVFname");
			request.setParameter("LASTNAME", "lname");
			request.setParameter("EMAIL", "mail");
			request.setParameter("PHONENO", "11111");
			request.setParameter("ADDRESS", "testAddress");
			modelAndView = controller.newVisitor(request, response);
		} catch (Exception exception) {
			fail("Exception");
		}
		assertEquals("/registration.jsp", modelAndView.getViewName());
	}

	/**
	 * Negative test case to test the method newVisitor
	 */
	@Test
	public void testNewVisitor_Negative() {
		/**
		 * @TODO: Call newVisitor method by passing request object as null and
		 *        asserting the model view name
		 */
		try {
			request = new MockHttpServletRequest("GET", "/newVistor.htm");
			modelAndView = controller.newVisitor(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals(null, modelAndView.getViewName());
	}

	/**
	 * Positive test case to test the method searchVisitor
	 */
	@Test
	public void testSearchVisitor_Positive() {
		/**
		 * @TODO: Create MockHttpServletRequest object Set request parameters
		 *        for USERNAME and PASSWORD for valid values Call searchVisitor
		 *        method and assert model view name
		 */
		request = new MockHttpServletRequest("GET", "/searchVisitor.htm");
		request.setParameter("USERNAME", "ylee");
		request.setParameter("PASSWORD", "password");
		try {
			modelAndView = controller.searchVisitor(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals("/visitormain.jsp", modelAndView.getViewName());
	}

	/**
	 * Negative test case of invalid user for method searchVisitor
	 */
	@Test
	public void testSearchVisitor_Negative_InvalidUser() {
		/**
		 * @TODO: Create MockHttpServletRequest object Set request parameters
		 *        for USERNAME and PASSWORD for invalid values Call
		 *        searchVisitor method and assert model view name
		 */
		request = new MockHttpServletRequest("GET", "/searchVisitor.htm");
		request.setParameter("USERNAME", "amith");
		request.setParameter("PASSWORD", "rajmp");
		try {
			modelAndView = controller.searchVisitor(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals("/index.jsp", modelAndView.getViewName());
	}

	/**
	 * Negative test case for method searchVisitor
	 */
	@Test
	public void testSearchVisitor_Negative() {
		/**
		 * @TODO: Call searchVisitor method by passing request object as null
		 *        and asserting the model view name
		 */
		request = new MockHttpServletRequest("GET", "/searchVisitor.htm");
		try {
			modelAndView = controller.searchVisitor(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals(null, modelAndView.getViewName());
	}

	/**
	 * Positive test case for method registerVisitor
	 */
	@Test
	public void testRegisterVisitor_Positive() throws ClassNotFoundException, SQLException {
		/**
		 * @TODO: Create MockHttpServletRequest object Set visitor object in
		 *        VISITOR session by calling searchUser method from visitorDAO
		 *        Set request parameters for USERNAME and PASSWORD for valid
		 *        values Call registerVisitor method and assert model view name
		 */
		request = new MockHttpServletRequest("GET", "/eventreg.htm");
		session.setAttribute("VISITOR", visitorDao.searchUser("ylee", "password"));
		request.setParameter("USERNAME", "ylee");
		request.setParameter("PASSWORD", "password");
		try {
			modelAndView = controller.registerVisitor(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals("/visitormain.jsp", modelAndView.getViewName());
	}

	/**
	 * Negaative test case for method registerVisitor
	 */
	@Test
	public void testRegisterVisitor_Negative() {
		/**
		 * @TODO: Call registerVisitor method by passing request object as null
		 *        and asserting the model view name
		 */
		request = new MockHttpServletRequest("GET", "/eventreg.htm");
		try {
			modelAndView = controller.registerVisitor(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals("/visitormain.jsp", modelAndView.getViewName());
	}

	/**
	 * Positive test case for method updateVisitor
	 */
	@Test
	public void testUpdateVisitor_Positive() throws ClassNotFoundException, SQLException {
		/**
		 * @TODO: Create MockHttpServletRequest object Set visitor object in
		 *        VISITOR session by calling searchUser method from visitorDAO
		 *        Set request parameters for all valid user values Call
		 *        updateVisitor method and assert model view name
		 */
		request = new MockHttpServletRequest("GET", "/updatevisitor.htm");
		session.setAttribute("VISITOR", visitorDao.searchUser("ylee", "password"));
		request.setParameter("USERNAME", "ylee");
		request.setParameter("PASSWORD", "password");
		try {
			modelAndView = controller.updateVisitor(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals("/updatevisitor.jsp", modelAndView.getViewName());
	}

	/**
	 * Negative test case for method updateVisitor
	 */
	@Test
	public void testUpdateVisitor_Negative() {
		/**
		 * @TODO: Call updateVisitor method by passing request object as null
		 *        and asserting the model view name
		 */
		try {
			modelAndView = controller.updateVisitor(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals(null, modelAndView.getViewName());
	}

	/**
	 * Positive test case for method unregisterEvent
	 */
	@Test
	public void testUnregisterEvent_Positive() throws ClassNotFoundException, SQLException {
		/**
		 * @TODO: Create MockHttpServletRequest object Set visitor object in
		 *        VISITOR session by calling searchUser method from visitorDAO
		 *        Set request parameters for all USERNAME, PASSWORD and eventId
		 *        values Call unregisterEvent method and assert model view name
		 */
		request = new MockHttpServletRequest("GET", "/eventunreg.htm");
		session.setAttribute("VISITOR", visitorDao.searchUser("ylee", "password"));
		request.setParameter("USERNAME", "ylee");
		request.setParameter("PASSWORD", "password");
		request.setParameter("eventId", "1001");
		try {
			modelAndView = controller.unregisterEvent(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals("/visitormain.jsp", modelAndView.getViewName());
	}

	/**
	 * Negative test case for method unregisterEvent
	 */
	@Test
	public void testUnregisterEvent_Negative() {
		/**
		 * @TODO: Call unregisterEvent method by passing request object as null
		 *        and asserting the model view name
		 */
		request = new MockHttpServletRequest("GET", "/eventunreg.htm");
		try {
			modelAndView = controller.unregisterEvent(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception");
		}
		assertEquals(null, modelAndView.getViewName());
	}

	@Test

	public void testChangePassword_Positive() {

		try {

			request = new MockHttpServletRequest("GET", "/changePWD.htm");

			Visitor visitor = visitorDao.searchUser("ylee", "password");
			
			session.setAttribute("VISITOR", visitor);
			//request.setSession(session);
			request.setSession(session);
			
//			request.setParameter("USERNAME", "npatel");
//			request.setParameter("PASSWORD", "password");
			
			// TODO: Set visitor object to session
			
			// TODO: Set session object to mock request object
		
			request.setParameter("password", "asad");

			modelAndView = controller.changePassword(request, response);

		} catch (Exception exception) {

			fail("Exception");

		}
//		assertEquals("/index.jsp", modelAndView.getViewName());
		assertEquals("success", modelAndView.getModelMap().get("status"));

		// To make sure password is reset to the original value after testing

//		request.setParameter("password", "password");
//
//		modelAndView = controller.changePassword(request, response);

	}

	@Test

	public void testChangePassword_PasswordNull() {

		try {

			request = new MockHttpServletRequest("GET", "/changePWD.htm");

			// TODO: Search visitor with user as ‘ylee’ and password as
			// ‘password’

			Visitor visitor = visitorDao.searchUser("ylee", "password");
			session.setAttribute("VISITOR", visitor);

			request.setSession(session);

			// TODO: Invoke changePassword() method on controller with mock
			// request and response objects are arguments

		} catch (Exception exception) {

			fail("Exception");

		}

		assertEquals("error", modelAndView.getModelMap().get("status"));

	}

	// TODO: Add a test method to verify change password functionality with
	// visitor as null

}
