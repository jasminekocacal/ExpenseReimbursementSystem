package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.DetailedReimbursement;
import com.example.model.ERSReimbursement;
import com.example.model.ERSUser;
import com.example.service.ERSReimbursementService;
import com.example.service.ERSReimbursementServiceImpl;
import com.example.service.ERSUserService;
import com.example.service.ERSUserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FinanceManagerController {

	public static ERSUserService us = new ERSUserServiceImpl("jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DBNAME"), System.getenv("TRAINING_DB_USERNAME"), System.getenv("TRAINING_DB_PASSWORD"));
	public static ERSReimbursementService rs = new ERSReimbursementServiceImpl("jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DBNAME"), System.getenv("TRAINING_DB_USERNAME"), System.getenv("TRAINING_DB_PASSWORD"));

	
	public static void allPendingFinder(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		
		System.out.println("In pending finder");
		
		/*
		 * THIS IS WHERE YOU'D GO TO THE DATABASE TO GET THE OBJECTS TO SEND TO THE CLIENT
		 */
		List<DetailedReimbursement> listOfPendingReimbs = rs.getDetailedReimbursementsPending();

		response.getWriter().write(new ObjectMapper().writeValueAsString(listOfPendingReimbs));
		
	}
	
	public static void allResolvedFinder(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		
		ERSUser fMUser = (ERSUser) request.getSession().getAttribute("loggeduser");
		
		List<DetailedReimbursement> listOfFMResolvedReimbs = rs.getDetailedReimbursementsResolvedByFM(fMUser);
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(listOfFMResolvedReimbs));
	}
	
	public static void updateReimbursement(HttpServletRequest request, HttpServletResponse response)
			 throws JsonProcessingException, IOException{

		
		System.out.println("In update reimbursement method");
		
		ERSUser resolver = (ERSUser) request.getSession().getAttribute("loggeduser");
		
		
		System.out.println(request.getParameter("reimbIdName"));
		System.out.println(request.getParameter("type"));
		
		
		int reimbId = Integer.parseInt(request.getParameter("reimbIdName"));
		int status = Integer.parseInt(request.getParameter("type"));
		
		ERSReimbursement reimbursement = rs.getReimbursementById(reimbId);
		
		rs.resolveReimbursement(resolver, status, reimbursement);

		
	}
	

	
	
}
