package com.example.service;

import java.util.List;

import com.example.dao.ERSUserDao;
import com.example.dao.ERSUserDaoImpl;
import com.example.model.ERSUser;

public class ERSUserServiceImpl implements ERSUserService{

//	ERSUserDao ud = new ERSUserDaoImpl("jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DBNAME"), System.getenv("TRAINING_DB_USERNAME"), System.getenv("TRAINING_DB_PASSWORD"));
	
////////////////////////////////
	
	///VARIABLES

///////////////////////////////
	
	private String url;
	private String username;
	private String password;
	
	
	public ERSUserServiceImpl(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	

	@Override
	public List<ERSUser> getAllUsers() {
		
		ERSUserDao ud = new ERSUserDaoImpl(url, username, password);

		List<ERSUser> listOfAllUsers = ud.getAllUsers();
		
		return listOfAllUsers;
	}

	@Override
	public ERSUser getUserById(int userId) {
		
		ERSUserDao ud = new ERSUserDaoImpl(url, username, password);
		
		ERSUser user = ud.getUserById(userId);
		
		if(user.getUserUsername() != null)
			return user;
		
		return null;
	}

	@Override
	public ERSUser getUserByUsername(String userUsername) {
		
		ERSUserDao ud = new ERSUserDaoImpl(url, username, password);
		
		ERSUser user = ud.getUserByUsername(userUsername);
		
		if(user.getUserUsername() != null)
			return user;
		
		return null;
	}
	
	
	/**
	 * Ensures the username and password match
	 * @param userUsername
	 * @param userPassword
	 * @return the roleId of the user 0=Employee 1=Finance Manager -1=Not valid
	 */
	public int verifyLoginCredentials(String userUsername, String userPassword) {
		
		ERSUserDao ud = new ERSUserDaoImpl(url, username, password);
		
		
		//Set a temp variable to represent the role ID. -1 is the default, which means that the user was not verified
		int roleId = -1;
		
		//Get the user object
		ERSUser user = ud.getUserByUsername(userUsername);
		
		//Check that the user exists and that their password is correct 
		if(user.getUserUsername() != null && user.getUserPassword().equals(userPassword)) {
			
			//Since the check passed, we can get and return the role ID  of the user
			roleId = user.getRoleId();
		}
		return roleId;
	}



}
