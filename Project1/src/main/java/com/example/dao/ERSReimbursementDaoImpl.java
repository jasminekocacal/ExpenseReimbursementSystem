package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.model.DetailedReimbursement;
import com.example.model.ERSReimbursement;
import com.example.model.ERSUser;

public class ERSReimbursementDaoImpl implements ERSReimbursementDao {

//////////////////////////////

///VARIABLES FOR CONNECTING TO THE DATABASE

//////////////////////////////

	private String url;
	private String username;
	private String password;

	/**
	 * Constructor that allows user to switch out databases
	 * 
	 * @param url      the database url
	 * @param username the database username
	 * @param password the database password
	 */
	public ERSReimbursementDaoImpl(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	* Allows a user to add a reimbursement; returns true if one record in database
	* was changed
	* 
	* @param user	      user object of the employee that added the reimbursement
	* @param amount      user input reimbursement amount, cannot be null
	* @param description user input description of the reimbursement
	* @param typeId      user input type id of the reimbursement, 0=other
	*                    1=lodging, 2=travel, 3=food
	*/
	@Override
	public boolean addReimbursement(ERSUser user, double amount, String description, int typeId) {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			// The SQL statement that we are inputting into our prepared statement
			// reimbursement id, date submitted, resolver, and date resolved are all automatically generated
			String sql = "INSERT INTO reimbursement VALUES (DEFAULT, ?, DEFAULT, NULL, ?, ?, NULL, 0, ?)"; // reimbursement id, (1)amount, date submitted, date resolved,
														// (2)reimbursement description, (3)author ID, resolver ID, status ID, (4)reimbursement type ID

			// Creating a prepared statement object
			PreparedStatement ps = conn.prepareStatement(sql);

			// Setting the values of the question mark --> input the parameter values
			ps.setDouble(1, amount);
			ps.setString(2, description);
			ps.setInt(3, user.getUserId());
			ps.setInt(4, typeId);

			int recordsChanged = ps.executeUpdate(); // THIS line is what sends the information to the DB

			// Boolean for a check to make sure that the sql values were actually changed, and that only 1 row was changed
			if (recordsChanged == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
		
		/**
		 * Returns list of all reimbursements
		 */
		@Override
		public List<ERSReimbursement> getAllReimbursements(){
			
			// arraylist of all the users, which will be populated with the result values
			List<ERSReimbursement> listOfAllReimbursements = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimbursement"; // Columns are (1)reimb id 2) reimb amount 3) date submitted 4)date resolved 5) description 6) author 7) resolver
												// 8) status 9) type

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfAllReimbursements.add(new ERSReimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3),
							rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfAllReimbursements;
		}
		
		/**
		 * Return reimbursement object based on given id; not for display
		 * @param id	Employee id
		 */
		@Override
		public ERSReimbursement getReimbursementById(int id) {
			
			// arraylist of all the users, which will be populated with the result values
			ERSReimbursement reimbursement = new ERSReimbursement();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimbursement WHERE reimb_id = ?"; // Columns are (1)reimb id 2) reimb amount 3) date submitted 4)date resolved
															//5) description 6) author 7) resolver	8) status 9) type

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Setting the values of the question mark --> input the parameter values
				ps.setInt(1, id);

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new reimbursement object
					reimbursement = new ERSReimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3),
							rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
				}
				//return the object that we just populated
				return reimbursement;

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		/**
		 * Returns the reimbursements that belong to a specific employee user; not used for display
		 * @param user		User object of the employee
		 */
		@Override
		public List<ERSReimbursement> getReimbursementsByUser(ERSUser user) {
			
			// arraylist of all the users, which will be populated with the result values
			List<ERSReimbursement> listOfUserReimbursements = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimbursement WHERE reimb_author = ?"; // Columns are (1)reimb id 2) reimb amount 3) date submitted 4)date resolved 5) description 
																	//6) author 7) resolver 8) status 9) type
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, user.getUserId()); //This is what gets placed into the ? in the SQL statement

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfUserReimbursements.add(new ERSReimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3),
							rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfUserReimbursements;
		}
		
		/**
		 * Returns list of reimbursements that were resolved by a specific finance manager; not used for display
		 * @param user	user object of finance manager
		 */
		public List<ERSReimbursement> getReimbursementsResolvedByFM(ERSUser user){
			
			// arraylist of all the users, which will be populated with the result values
			List<ERSReimbursement> listOfReimbursementsResolvedByFM = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimbursement WHERE reimb_resolver = ?"; // Columns are (1)reimb id 2) reimb amount 3) date submitted 4)date resolved 5) description 
																	//6) author 7) resolver 8) status 9) type
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, user.getUserId()); //This is what gets placed into the ? in the SQL statement

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfReimbursementsResolvedByFM.add(new ERSReimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3),
							rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfReimbursementsResolvedByFM;
		}
		
		/**
		 * Returns list of all pending reimbursements; not used for display
		 */
		@Override
		public List<ERSReimbursement> getReimbursementsPending(){
			
			// arraylist of all the users, which will be populated with the result values
			List<ERSReimbursement> listOfAllReimbursementsPending = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimbursement WHERE reimb_status = 0"; // Columns are (1)reimb id 2) reimb amount 3) date submitted 4)date resolved 
															//5) description 6) author 7) resolver 8) status 9) type

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfAllReimbursementsPending.add(new ERSReimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3),
							rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfAllReimbursementsPending;
		}
		
		/**
		 * Returns all resolved (denied or accepted) reimbursements; not for display
		 */
		@Override
		public List<ERSReimbursement> getReimbursementsResolved(){
			
			// arraylist of all the users, which will be populated with the result values
			List<ERSReimbursement> listOfAllReimbursementsResolved = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimbursement WHERE reimb_status = 1 OR reimb_status = 2"; // Columns are (1)reimb id 2) reimb amount 3) date submitted 
																	//4)date resolved 5) description 6) author 7) resolver 8) status 9) type

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfAllReimbursementsResolved.add(new ERSReimbursement(rs.getInt(1), rs.getDouble(2), rs.getDate(3),
							rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfAllReimbursementsResolved;
		}
		
		/**
		 * Returns reimbursements belonging to a specific employee; includes status, type, and role Strings and the names of the users; used for display
		 * @param user		Employee user
		 */
		@Override
		public List<DetailedReimbursement> getDetailedReimbursementsByUser(ERSUser user) {
			
			// arraylist of all the users, which will be populated with the result values
			List<DetailedReimbursement> listOfUserDetailedReimbursements = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimb_lookups_users_join WHERE employee_user_id = ?"; 
				//columns are employee ID, employee first name, employee last name, reimbursement ID, amount, description, type, date submitted, status, date resolved, resolver ID
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, user.getUserId()); //This is what gets placed into the ? in the SQL statement

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfUserDetailedReimbursements.add(new DetailedReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getDate(10), rs.getInt(11)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfUserDetailedReimbursements;
		}
		
		public List<DetailedReimbursement> getDetailedPendingReimbursementsByUser(ERSUser user){
			
			// arraylist of all the users, which will be populated with the result values
			List<DetailedReimbursement> listOfUserPendingReimbursements = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimb_lookups_users_join WHERE employee_user_id = ? AND status = 'pending'"; 
				//columns are employee ID, employee first name, employee last name, reimbursement ID, amount, description, type, date submitted, status, date resolved, resolver ID
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, user.getUserId()); //This is what gets placed into the ? in the SQL statement

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfUserPendingReimbursements.add(new DetailedReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getDate(10), rs.getInt(11)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfUserPendingReimbursements;
		}
		
		
		public List<DetailedReimbursement> getDetailedApprovedReimbursementsByUser(ERSUser user){
		
			// arraylist of all the users, which will be populated with the result values
			List<DetailedReimbursement> listOfUserAcceptedReimbursements = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimb_lookups_users_join WHERE employee_user_id = ? AND status = 'approved'"; 
				//columns are employee ID, employee first name, employee last name, reimbursement ID, amount, description, type, date submitted, status, date resolved, resolver ID
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, user.getUserId()); //This is what gets placed into the ? in the SQL statement

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfUserAcceptedReimbursements.add(new DetailedReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getDate(10), rs.getInt(11)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfUserAcceptedReimbursements;
		}
		
		public List<DetailedReimbursement> getDetailedDeniedReimbursementsByUser(ERSUser user){
			
			// arraylist of all the users, which will be populated with the result values
			List<DetailedReimbursement> listOfUserDeniedReimbursements = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimb_lookups_users_join WHERE employee_user_id = ? AND status = 'denied'"; 
				//columns are employee ID, employee first name, employee last name, reimbursement ID, amount, description, type, date submitted, status, date resolved, resolver ID
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, user.getUserId()); //This is what gets placed into the ? in the SQL statement

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfUserDeniedReimbursements.add(new DetailedReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getDate(10), rs.getInt(11)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfUserDeniedReimbursements;
		}
		
		/**
		 * Returns list of reimbursements that were resolved by specific finance manager user; used for display
		 * @param user 		finance manager user object
		 */
		@Override
		public List<DetailedReimbursement> getDetailedReimbursementsResolvedByFM(ERSUser user){
			
			// arraylist of all the users, which will be populated with the result values
			List<DetailedReimbursement> listOfDetailedReimbursementsFM = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimb_lookups_users_join WHERE resolver_id = ?"; 
				//columns are employee ID, employee first name, employee last name, reimbursement ID, amount, description, type, date submitted, status, date resolved, resolver ID
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);
				
				ps.setInt(1, user.getUserId()); //This is what gets placed into the ? in the SQL statement

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfDetailedReimbursementsFM.add(new DetailedReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getDate(10), rs.getInt(11)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfDetailedReimbursementsFM;
		}

		/**
		 * Returns list of all pending reimbursements; used for display
		 */
		@Override
		public List<DetailedReimbursement> getDetailedReimbursementsPending() {
			
			// arraylist of all the users, which will be populated with the result values
			List<DetailedReimbursement> listOfDetailedReimbursementsPending = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimb_lookups_users_join WHERE status = 'pending'"; 
				//columns are employee ID, employee first name, employee last name, reimbursement ID, amount, description, type, date submitted, status, date resolved, resolver ID
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfDetailedReimbursementsPending.add(new DetailedReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getDate(10), rs.getInt(11)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfDetailedReimbursementsPending;
		}

		@Override
		public List<DetailedReimbursement> getDetailedReimbursementsResolved() {
			
			// arraylist of all the users, which will be populated with the result values
			List<DetailedReimbursement> listOfDetailedReimbursementsResolved = new ArrayList<>();

			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// Reimbursements read statement
				String sql = "SELECT * FROM reimb_lookups_users_join WHERE status = 'approved' OR status = 'denied'"; 
				//columns are employee ID, employee first name, employee last name, reimbursement ID, amount, description, type, date submitted, status, date resolved, resolver ID
			 

				// Create a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);

				// The ResultSet object will maintain a cursor on its current row of data
				ResultSet rs = ps.executeQuery(); // <--query not update

				// The .next method moves the cursor to the next row of data and will return
				// false when there are no more rows to read
				while (rs.next()) {

					// We create new user objects so that we can use them in java and then populate
					// the arraylist listOfReimbursements so we can use it if we need it
					listOfDetailedReimbursementsResolved.add(new DetailedReimbursement(rs.getInt(1), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7), rs.getDate(8), rs.getString(9), rs.getDate(10), rs.getInt(11)));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return listOfDetailedReimbursementsResolved;
		}

		@Override
		public boolean resolveReimbursement(ERSUser resolver, int status, ERSReimbursement reimbursement) {
			
			try (Connection conn = DriverManager.getConnection(url, username, password)) {

				// The SQL statement that we are inputting into our prepared statement
				// reimbursement date, resolver, and status will be changed
				String sql = "UPDATE reimbursement " + 
						"SET reimb_date_resolved = CURRENT_DATE" + 
						", reimb_resolver = ?" + 
						", reimb_status = ? " + 
						"WHERE reimb_id = ?;"; 
				
				//The ? are resolver user ID, status ID, reimbursement ID

				// Creating a prepared statement object
				PreparedStatement ps = conn.prepareStatement(sql);

				// Setting the values of the question mark --> input the parameter values
				ps.setInt(1, resolver.getUserId());
				ps.setInt(2, status);
				ps.setInt(3, reimbursement.getId());

				int recordsChanged = ps.executeUpdate(); // THIS line is what sends the information to the DB

				// Boolean for a check to make sure that the sql values were actually changed, and that only 1 row was changed
				if (recordsChanged > 0)
					return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	
	
	/**
	 * Creates reimbursement status lookup table, type lookup table, reimbursement table, reimbursement and user join table; also populates the lookup tables and adds
	 * dummy data to the reimbursement table
	 * Used for testing
	 */
	@Override
	public void reimbh2InitDao() {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			String sql = 
					"CREATE TABLE reimb_status_lookup(" + 
					"	status_id INTEGER PRIMARY KEY" + 
					"	, status VARCHAR(50) NOT NULL" + 
					"); " + 
					"" + 
					"CREATE TABLE reimb_type_lookup(" + 
					"	type_id INTEGER PRIMARY KEY" + 
					"	, reimb_type VARCHAR(50) NOT NULL" + 
					"); " +
					"INSERT INTO reimb_status_lookup " + 
					"VALUES (0, 'pending')" + 
					"	,(1, 'approved')" + 
					"	,(2, 'denied');" + 
					"	" + 
					"INSERT INTO reimb_type_lookup " + 
					"VALUES (0, 'other')" + 
					", (1, 'lodging')" + 
					", (2, 'travel')" + 
					", (3, 'food'); "+
					"" +
					"CREATE TABLE reimbursement(" + 
					"	reimb_id SERIAL PRIMARY KEY" + 
					"	, reimb_amount NUMERIC(20, 2) NOT NULL" + 
					"	, reimb_date_submitted DATE NOT NULL DEFAULT CURRENT_DATE" + 
					"	, reimb_date_resolved DATE " + 
					"	, reimb_description VARCHAR(250)" + 
					"	, reimb_author INTEGER NOT NULL" + 
					"	, reimb_resolver INTEGER" + 
					"	, reimb_status INTEGER NOT NULL" + 
					"	, reimb_type INTEGER NOT NULL" + 
					"	, FOREIGN KEY(reimb_author) REFERENCES ERS_user(user_id) ON DELETE CASCADE " + 
					"	, FOREIGN KEY(reimb_resolver) REFERENCES ERS_user(user_id)" + 
					"	, FOREIGN KEY(reimb_status) REFERENCES reimb_status_lookup(status_id)" + 
					"	, FOREIGN KEY(reimb_type) REFERENCES reimb_type_lookup(type_id)" + 
					"); " + 
					"CREATE VIEW reimb_lookups_users_join AS " + 
					"SELECT reimb_id, reimb_amount, reimb_date_submitted, reimb_date_resolved, reimb_description, u.user_username, e.user_username AS resolver, s.status, t.reimb_type FROM reimbursement r" + 
					"	INNER JOIN ers_user u ON reimb_author = u.user_id" + 
					"	LEFT JOIN ers_user e ON reimb_resolver = e.user_id" + 
					"	INNER JOIN reimb_status_lookup s ON reimb_status = s.status_id" + 
					"	INNER JOIN reimb_type_lookup t ON r.reimb_type = t.type_id; " + 
					"INSERT INTO reimbursement " + 
					"VALUES (7, 50.23, DEFAULT, null, 'Bought potatoes', 2, null, 0, 3)" + 
					"	, (8, 50.23, DEFAULT, null, 'Went to Malibu', 1, null, 0, 2)" + 
					"	, (9, 50.23, DEFAULT, '2021-01-23', 'Stayed at Hilton', 7, 3, 1, 1)" + 
					"	, (10, 50.23, DEFAULT, null, 'Stayed at HolidayInn', 6, null, 0, 1)" + 
					"	, (11, 50.23, DEFAULT, '2021-08-11', 'Bought souveniers', 2, 4, 2, 0)" + 
					"	, (12, 50.23, DEFAULT, null, 'Bought diamond potatoes', 1, null, 0, 0);";

			// Creates a statement object which is for creating the table and the dummy data
			Statement state = conn.createStatement();

			// Performs the statement query
			state.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Destroys the reimbursement type lookup table, status lookup table, reimbursement table, and the reimbursement join table
	 * Used for testing
	 */
	@Override
	public void reimbh2DestroyDao() {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {

			// Create the string statement that will destroy the dummy table
			String sql = "DROP TABLE reimb_lookups_users_join; " + 
					"DROP TABLE reimbursement; " + 
					"DROP TABLE reimb_type_lookup; " + 
					"DROP TABLE reimb_status_lookup;";

			Statement state = conn.createStatement();
			state.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
