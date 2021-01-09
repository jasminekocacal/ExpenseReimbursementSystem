package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.example.model.ERSUser;
import com.example.service.ERSReimbursementService;
import com.example.service.ERSReimbursementServiceImpl;
import com.example.service.ERSUserService;
import com.example.service.ERSUserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginController {

	/**
	 * Verifies user login credentials and redirects to Employee or Finance Manager page depending on their role
	 * @param request	HTTP Request
	 * @return correct page URI
	 */
	public static String login(HttpServletRequest request) {
		
		ERSUserService es = new ERSUserServiceImpl("jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DBNAME"), System.getenv("TRAINING_DB_USERNAME"), System.getenv("TRAINING_DB_PASSWORD"));
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		//Make sure that the user got here through a POST request, safeguarding
		if(!request.getMethod().equals("POST")) {
			return "/html/badlogin.html";
		}
		
		//extracting the form data
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//Create a temp variable that verifies their credentials; this method returns -1 if the user doesn't exist, 0 if they are an employee, and 1 if they are a finance
		//manager
		int roleId = es.verifyLoginCredentials(username, password);
		
		System.out.println(username + " " + password + " " + roleId);

		if(roleId != -1) {
			
			ERSUser user = es.getUserByUsername(username);
			
			//create a session with the acquired user if they've been verified
			request.getSession().setAttribute("loggeduser", user);
			
			
			//Redirect to the correct page depending on their role
			if(roleId == 0) {
				
				loggy.info("User " + user.getUserUsername() + " logged in");
				return "/html/employeePage.html";
				
			}else if(roleId == 1) {
				
				loggy.info("FM User " + user.getUserUsername() + " logged in");
				return "/html/financeManagerPage.html";	
			}

		}
		
		System.out.println("Didn't pass credentials");
		loggy.info("Failed login");
		return "/html/badlogin.html";
	}
	
	
	/**
	 * 
	 * @param req
	 * @param res
	 */
	public static void getSession(HttpServletRequest req, HttpServletResponse res) {
		
		 try {
			 ERSUser user = (ERSUser) req.getSession().getAttribute("loggeduser");
			res.getWriter().write(new ObjectMapper().writeValueAsString(user));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	public static void endSession(HttpServletRequest request, HttpServletResponse response)
			 throws JsonProcessingException, IOException{
		
			request.getSession().invalidate();
			response.sendRedirect("/Project1/loginPage.html");
		
		 
	}

}


