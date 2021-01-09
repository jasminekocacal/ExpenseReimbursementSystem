package com.example.service;

import java.util.List;

import com.example.model.ERSUser;

public interface ERSUserService {

	
	//CREATE
	
	
	//READ
	public List<ERSUser> getAllUsers();
	public ERSUser getUserById(int userId);
	public ERSUser getUserByUsername(String userUsername);
	
	//UPDATE
	public int verifyLoginCredentials(String userUsername, String userPassword);
	
	//DELETE
}
