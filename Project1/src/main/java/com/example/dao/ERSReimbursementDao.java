package com.example.dao;

import java.util.List;

import com.example.model.DetailedReimbursement;
import com.example.model.ERSReimbursement;
import com.example.model.ERSUser;

public interface ERSReimbursementDao {

	
	//CRUD METHODS
	
	//CREATE
	public boolean addReimbursement(ERSUser user, double amount, String description, int typeId);
	
	//READ
	public ERSReimbursement getReimbursementById(int id);
	public List<ERSReimbursement> getAllReimbursements();
	public List<ERSReimbursement> getReimbursementsByUser(ERSUser user);
	public List<ERSReimbursement> getReimbursementsResolvedByFM(ERSUser user);
	public List<ERSReimbursement> getReimbursementsPending();
	public List<ERSReimbursement> getReimbursementsResolved();
	public List<DetailedReimbursement> getDetailedReimbursementsByUser(ERSUser user);
	public List<DetailedReimbursement> getDetailedPendingReimbursementsByUser(ERSUser user);
	public List<DetailedReimbursement> getDetailedApprovedReimbursementsByUser(ERSUser user);
	public List<DetailedReimbursement> getDetailedDeniedReimbursementsByUser(ERSUser user);
	public List<DetailedReimbursement> getDetailedReimbursementsResolvedByFM(ERSUser user);
	public List<DetailedReimbursement> getDetailedReimbursementsPending();
	public List<DetailedReimbursement> getDetailedReimbursementsResolved();

	//UDPATE
	public boolean resolveReimbursement(ERSUser resolver, int status, ERSReimbursement reimbursement);
	
	//DELETE
	
	
	
	//H2 METHODS
	public void reimbh2InitDao();
	public void reimbh2DestroyDao();
}
