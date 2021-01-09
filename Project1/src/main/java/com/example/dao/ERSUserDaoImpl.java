package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.model.ERSUser;

public class ERSUserDaoImpl implements ERSUserDao {

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

/////////////////////////////

///VARIABLES FOR CONNECTING TO THE DATABASE

//////////////////////////////

	private String url;
	private String username;
	private String password;

	/**
	 * No args constructor
	 */
	public ERSUserDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor that allows user to switch out databases
	 * 
	 * @param url      the database url
	 * @param username the database username
	 * @param password the database password
	 */
	public ERSUserDaoImpl(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns a list of all of the reimbursements with user IDs, status ID, and
	 * type ID
	 */
	@Override
	public List<ERSUser> getAllUsers() {

		// arraylist of all the users, which will be populated with the result values
		List<ERSUser> listOfAllUsers = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			// Users read statement
			String sql = "SELECT * FROM ERS_user"; // Columns are (1)user id, (2)username, (3)password, (4)first name,
													// (5)last name, (6)email, (7)role ID

			// Create a prepared statement object
			PreparedStatement ps = conn.prepareStatement(sql);

			// The ResultSet object will maintain a cursor on its current row of data
			ResultSet rs = ps.executeQuery(); // <--query not update

			// The .next method moves the cursor to the next row of data and will return
			// false when there are no more rows to read
			while (rs.next()) {

				// We create new user objects so that we can use them in java and then populate
				// the arraylist listOfUsers so we can use it if we need it
				listOfAllUsers.add(new ERSUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getInt(7)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfAllUsers;
	}
	
	/**
	 * Returns the user object
	 * @param userId	ID number of user
	 */
	@Override
	public ERSUser getUserById(int userId) {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			
			//Prepare the user object that we will be returning
			ERSUser user = new ERSUser();
			
			// Users read statement
			// Columns are (1)user id, (2)username, (3)password, (4)first name, (5)last name, (6)email, (7)role ID
			String sql = "SELECT * FROM ERS_user WHERE user_id = ?"; 

			// Create a prepared statement object
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, userId); //This is what gets placed into the ? in the SQL statement 

			// The ResultSet object will maintain a cursor on its current row of data
			ResultSet rs = ps.executeQuery(); // <--query not update

			// The .next method moves the cursor to the next row of data and will return
			// false when there are no more rows to read
			while (rs.next()) {

				// We create the user object so we can use it if necessary
				user = new ERSUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
			}
			
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Returns the user object
	 * @param userUsername		client username
	 */
	@Override
	public ERSUser getUserByUsername(String userUsername) {
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			
			//Prepare the user object that we will be returning
			ERSUser user = new ERSUser();
			
			// Users read statement
			// Columns are (1)user id, (2)username, (3)password, (4)first name, (5)last name, (6)email, (7)role ID
			String sql = "SELECT * FROM ERS_user WHERE user_username = ?"; 

			// Create a prepared statement object
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, userUsername); //This is what gets placed into the ? in the SQL statement 

			// The ResultSet object will maintain a cursor on its current row of data
			ResultSet rs = ps.executeQuery(); // <--query not update

			// The .next method moves the cursor to the next row of data and will return
			// false when there are no more rows to read
			while (rs.next()) {

				// We create the user object so we can use it if necessary
				user = new ERSUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
			}
			
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Direct database interaction; creates a user table in H2 with dummy data
	 */
	@Override
	public void userh2InitDao() {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			String sql = "CREATE TABLE user_role_lookup(" + 
					"	user_role_id INTEGER PRIMARY KEY" + 
					"	, user_role VARCHAR(50) NOT NULL" + 
					");" + 
					" INSERT INTO user_role_lookup" + 
					" VALUES (0, 'employee')" + 
					"	,(1, 'finance_manager');" +
					" CREATE TABLE ERS_user(" + 
					"	user_id SERIAL PRIMARY KEY" + 
					"	, user_username VARCHAR(50) NOT NULL UNIQUE" + 
					"	, user_password VARCHAR(50) NOT NULL" + 
					"	, first_name VARCHAR(100) NOT NULL" + 
					"	, last_name VARCHAR(100) NOT NULL" + 
					"	, user_email VARCHAR(150) NOT NULL UNIQUE" + 
					"	, user_role_id INTEGER NOT NULL " + 
					"	, FOREIGN KEY(user_role_id) REFERENCES user_role_lookup(user_role_id)" + 
					");" + " INSERT INTO ers_user " + 
							"VALUES (DEFAULT, 'johnsmith', 'password1', 'John', 'Smith', 'johnsmith@gmail', 0)" + 
							"	,(DEFAULT, 'johndoe', 'password2', 'John', 'Doe', 'johndoe@gmail', 0)" + 
							"	,(DEFAULT, 'janedoe', 'password3', 'Jane', 'Doe', 'janedoe@gmail', 1)" + 
							"	,(DEFAULT, 'williamsmith', 'password4', 'William', 'Smith', 'williamsmith@gmail', 1)" + 
							"	,(DEFAULT, 'corneliusboy', 'password5', 'Cornelius', 'Boy', 'corneliusboy@gmail', 0)" + 
							"	,(DEFAULT, 'charlottebronte', 'password6', 'Charlotte', 'Bronte', 'charlottebronte@gmail', 0)" + 
							"	,(DEFAULT, 'emilyblunt', 'password7', 'Emily', 'Blunt', 'emilyblunt@gmail', 0);";

			// Creates a statement object which is for creating the table and the dummy data
			Statement state = conn.createStatement();

			// Performs the statement query
			state.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Direct database interaction with h2; destroys the user table and its dummy data
	 */
	@Override
	public void userh2DestroyDao() {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			// Create the string statement that will destroy the dummy table
			String sql = "" + "DROP TABLE ERS_user; DROP TABLE user_role_lookup;";

			Statement state = conn.createStatement();
			state.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
