package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.DetailedReimbursement;
import com.example.model.ERSUser;
import com.example.service.ERSReimbursementService;
import com.example.service.ERSReimbursementServiceImpl;
import com.example.service.ERSUserService;
import com.example.service.ERSUserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeController {

	public static ERSUserService us = new ERSUserServiceImpl("jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DBNAME"), System.getenv("TRAINING_DB_USERNAME"), System.getenv("TRAINING_DB_PASSWORD"));
	public static ERSReimbursementService rs = new ERSReimbursementServiceImpl("jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DBNAME"), System.getenv("TRAINING_DB_USERNAME"), System.getenv("TRAINING_DB_PASSWORD"));
	
	public static void allPendingReimbursementsFinder (HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException, IOException {
		
		
		ERSUser user = (ERSUser) req.getSession().getAttribute("loggeduser");
		
		List<DetailedReimbursement> listOfEmployeePendingReimbs = rs.getPendingReimbursementsByUser(user);
		
		resp.getWriter().write(new ObjectMapper().writeValueAsString(listOfEmployeePendingReimbs));
		
	}
	
	public static void allApprovedReimbursementsFinder (HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException, IOException {
		
		System.out.println("In the approved reimbursements finder");
		
		ERSUser user = (ERSUser) req.getSession().getAttribute("loggeduser");
		
		List<DetailedReimbursement> listOfEmployeeApprovedReimbs = rs.getApprovedReimbursementsByUser(user);
		
		resp.getWriter().write(new ObjectMapper().writeValueAsString(listOfEmployeeApprovedReimbs));
		
	}
	
	public static void allDeniedReimbursementsFinder (HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException, IOException {
		
		
		ERSUser user = (ERSUser) req.getSession().getAttribute("loggeduser");
		
		List<DetailedReimbursement> listOfEmployeeDeniedReimbs = rs.getDeniedReimbursementsByUser(user);
		
		resp.getWriter().write(new ObjectMapper().writeValueAsString(listOfEmployeeDeniedReimbs));
		
	}
	
	public static void addReimbursement(HttpServletRequest request, HttpServletResponse response)
			 throws JsonProcessingException, IOException{

		
		System.out.println("In addreimbursement method");
		
		ERSUser user = (ERSUser) request.getSession().getAttribute("loggeduser");
		
		System.out.println(request.getParameter("amount"));
		System.out.println(request.getParameter("description"));
		System.out.println(request.getParameter("type"));
		
		double amount = Double.parseDouble(request.getParameter("amount"));
		String description = request.getParameter("description");
		int type = Integer.parseInt(request.getParameter("type"));
		
		rs.addReimbursement(user, amount, description, type);

		
	}
	

	
}
