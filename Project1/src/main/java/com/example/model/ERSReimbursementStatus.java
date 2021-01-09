package com.example.model;

public class ERSReimbursementStatus {

	private int id;
	private String status;
	
	public ERSReimbursementStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ERSReimbursementStatus(int statusId, String status) {
		super();
		this.id = statusId;
		this.status = status;
	}
	public int getStatusId() {
		return id;
	}
	public void setStatusId(int statusId) {
		this.id = statusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ERSReimbursementStatus [statusId=" + id + ", status=" + status + "]";
	}
	
	
}
