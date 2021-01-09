package com.example.model;

public class ERSUserRole {

	private int id;
	private String userRole;
	
	public ERSUserRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ERSUserRole(int id, String userRole) {
		super();
		this.id = id;
		this.userRole = userRole;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "ERSUserRoles [id=" + id + ", userRole=" + userRole + "]";
	}
	
	
}

