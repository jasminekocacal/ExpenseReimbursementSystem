package com.example.service;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.example.dao.ERSReimbursementDao;
import com.example.dao.ERSReimbursementDaoImpl;
import com.example.model.DetailedReimbursement;
import com.example.model.ERSReimbursement;
import com.example.model.ERSUser;

public class ERSReimbursementServiceImpl implements ERSReimbursementService{
	
//	ERSReimbursementDao rd = new ERSReimbursementDaoImpl("jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DBNAME"), System.getenv("TRAINING_DB_USERNAME"), System.getenv("TRAINING_DB_PASSWORD"));
	
	
/////////////////////////////////
	
	///VARIABLES
	
///////////////////////////////
	
	private String url;
	private String username;
	private String password;
	
	
	public ERSReimbursementServiceImpl(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public boolean addReimbursement(ERSUser user, double amount, String description, int typeId) {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		if(user != null && amount > 0 && user.getRoleId() > -1 && user.getRoleId() <=1 && typeId > -1 && typeId <= 3) {
			loggy.info("User " + user.getUserUsername() + " added a new reimbursement. Amount: " + amount + " Type: " + typeId);
			return rd.addReimbursement(user, amount, description, typeId);
		}
		
		return false;
	}

	@Override
	public ERSReimbursement getReimbursementById(int id) {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		ERSReimbursement reimbursement = rd.getReimbursementById(id);
		
		if(id != 0 && reimbursement.getId() != 0) {
			return reimbursement;
		}
		return null;
	}
	
	
	@Override
	public List<DetailedReimbursement> getDetailedReimbursementsByUser(ERSUser user) {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);

		if(user != null /*&& singleUserReimb.size() != 0*/) {
			return rd.getDetailedReimbursementsByUser(user);
		}
		
		return null;
	}
	
	public List<DetailedReimbursement> getPendingReimbursementsByUser(ERSUser user){
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		if(user != null) {
			
			loggy.info("User " + user.getUserUsername() + " viewed all their pending reimbursements");
			return rd.getDetailedPendingReimbursementsByUser(user);
		}
			
		return null;
	}
	
	
	public List<DetailedReimbursement> getApprovedReimbursementsByUser(ERSUser user){
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		if(user != null) {
			
			loggy.info("User " + user.getUserUsername() + " viewed all their approved reimbursements");
			return rd.getDetailedApprovedReimbursementsByUser(user);
		}

			
		return null;
	}
	
	public List<DetailedReimbursement> getDeniedReimbursementsByUser(ERSUser user){
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		if(user != null) {
			
			loggy.info("User " + user.getUserUsername() + " viewed all their denied reimbursements");
			return rd.getDetailedDeniedReimbursementsByUser(user);
		}

			
		return null;
	}

	@Override
	public List<DetailedReimbursement> getDetailedReimbursementsResolvedByFM(ERSUser user) {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		if(user != null && user.getRoleId() == 1 /*&& singleFMReimb.size() != 0*/) {
			
			loggy.info("FM User " + user.getUserUsername() + " looked at all of their previously resolved reimbursements");
			return rd.getDetailedReimbursementsResolvedByFM(user);
		}
		
		return null;
	}

	@Override
	public List<DetailedReimbursement> getDetailedReimbursementsPending() {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		List<DetailedReimbursement> listOfPendingReimbursements = rd.getDetailedReimbursementsPending();
		loggy.info("FM User viewed all the pending reimbursements");
		
		return listOfPendingReimbursements;
	}

	@Override
	public List<DetailedReimbursement> getDetailedReimbursementsResolved() {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		List<DetailedReimbursement> listOfResolvedReimbursements = rd.getDetailedReimbursementsResolved();
		
		return listOfResolvedReimbursements;
	}

	@Override
	public boolean resolveReimbursement(ERSUser resolver, int status, ERSReimbursement reimbursement) {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		final Logger loggy = Logger.getLogger(ERSReimbursementServiceImpl.class);
		loggy.setLevel(Level.ALL);
		
		if(resolver != null && status != 0 && resolver.getRoleId() == 1 && reimbursement != null) {
			
			loggy.info("FM User " + resolver.getUserUsername() + " updated a reimbursement");
			return rd.resolveReimbursement(resolver, status, reimbursement);
		}
		
		return false;	 
	}
	
	public ERSReimbursement getReimbById(int id) {
		
		ERSReimbursementDao rd = new ERSReimbursementDaoImpl(url, username, password);
		
		if(id > 0) {
			return rd.getReimbursementById(id);
		}
		return null;
	}

	
}
