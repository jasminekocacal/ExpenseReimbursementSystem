package com.example.service;

import java.util.List;

import com.example.model.DetailedReimbursement;
import com.example.model.ERSReimbursement;
import com.example.model.ERSUser;

public interface ERSReimbursementService {

	//CREATE
	public boolean addReimbursement(ERSUser user, double amount, String description, int typeId);
	
	//READ
	public ERSReimbursement getReimbursementById(int id);
	public List<DetailedReimbursement> getDetailedReimbursementsByUser(ERSUser user);
	public List<DetailedReimbursement> getPendingReimbursementsByUser(ERSUser user);
	public List<DetailedReimbursement> getApprovedReimbursementsByUser(ERSUser user); 
	public List<DetailedReimbursement> getDeniedReimbursementsByUser(ERSUser user);
	public List<DetailedReimbursement> getDetailedReimbursementsResolvedByFM(ERSUser user);
	public List<DetailedReimbursement> getDetailedReimbursementsPending();
	public List<DetailedReimbursement> getDetailedReimbursementsResolved();

	//UDPATE
	public boolean resolveReimbursement(ERSUser resolver, int status, ERSReimbursement reimbursement);
	
	//DELETE
}
