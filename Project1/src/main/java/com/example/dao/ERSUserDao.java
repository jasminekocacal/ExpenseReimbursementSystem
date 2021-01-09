package com.example.dao;

import java.util.List;

import com.example.model.ERSUser;

public interface ERSUserDao {

	
	//CRUD METHODS
	
	//CREATE
	
	//READ
	public List<ERSUser> getAllUsers();
	public ERSUser getUserById(int userId);
	public ERSUser getUserByUsername(String userUsername);
	
	//UPDATE
	
	
	//DELETE
	
	//H2 METHODS
	public void userh2InitDao();
	public void userh2DestroyDao();
	
}
