package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.dao.ERSReimbursementDao;
import com.example.dao.ERSReimbursementDaoImpl;
import com.example.dao.ERSUserDao;
import com.example.dao.ERSUserDaoImpl;
import com.example.model.ERSReimbursement;
import com.example.model.ERSUser;
import com.example.service.ERSReimbursementService;
import com.example.service.ERSReimbursementServiceImpl;
import com.example.service.ERSUserService;
import com.example.service.ERSUserServiceImpl;

public class ReimbTests {

	
/////////////////////////////////////

////////PRINT METHODS

////////////////////////////////////

	ERSUserDao ud = new ERSUserDaoImpl("jdbc:h2:./h2/data", "sa", "sa");
	ERSReimbursementDao rd = new ERSReimbursementDaoImpl("jdbc:h2:./h2/data", "sa", "sa");
	ERSUserService es = new ERSUserServiceImpl("jdbc:h2:./h2/data", "sa", "sa");
	ERSReimbursementService rs = new ERSReimbursementServiceImpl("jdbc:h2:./h2/data", "sa", "sa");
	
	public ERSUser u1 = new ERSUser(1, "johnsmith", "password1", "John", "Smith", "johnsmith@gmail", 0);
	public ERSUser u2 = new ERSUser(2, "johndoe", "password2", "John", "Doe", "johndoe@gmail", 0);
	public ERSUser u3 = new ERSUser(3, "janedoe", "password3", "Jane", "Doe", "janedoe@gmail", 1);
	public ERSUser u4 = new ERSUser(4, "williamsmith", "password4", "William", "Smith", "williamsmith@gmail", 1);
	public ERSUser u5 = new ERSUser(5, "corneliusboy", "password5", "Cornelius", "Boy", "corneliusboy@gmail", 0);
	public ERSUser u6 = new ERSUser(6, "charlottebronte", "password6", "Charlotte", "Bronte", "charlottebronte@gmail", 0);
	public ERSUser u7 = new ERSUser(7, "emilyblunt", "password7", "Emily", "Blunt", "emilyblunt@gmail", 0);
	public ERSUser fakeUser = new ERSUser(8, "fake", "fakepassword", "fake", "person", "fake@gmail", -1);
	

	

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("----------BEFORE CLASS----------");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("----------AFTER CLASS----------");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("-----before each test-----");
		ud.userh2InitDao();
		rd.reimbh2InitDao();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("-----after each test-----");
		rd.reimbh2DestroyDao();
		ud.userh2DestroyDao();
	}
	
	
///////////////////////DAO TESTS
	
	@Test
	public void getUserByIdTest() {
		
		assertEquals("John smith", u1, ud.getUserById(1));
		assertEquals("Emily Blunt", u7, ud.getUserById(7));
		assertEquals("William Smith", u4, ud.getUserById(4));
	}
	
	@Test
	public void getUserByUsernameTest() {
		
		assertEquals("John smith", u1, ud.getUserByUsername("johnsmith"));
		assertEquals("Big Dog", u7, ud.getUserByUsername("emilyblunt"));
		assertEquals("William Smith", u4, ud.getUserByUsername("williamsmith"));
	}
	
	
	@Test
	public void addReimbursementTest() {
		
		assertTrue(rd.addReimbursement(u5, 12.57, "Bought arrows", 0));
		assertTrue(rd.addReimbursement(u7, 0, "Stayed at a hotel", 1));
		assertTrue(rd.addReimbursement(u6, 5.5, "Bought alcohol", 3));
	}
	
	///////////////USER SERVICE TESTS
	
	@Test
	public void getAllUsersTest() {
		
		List<ERSUser> listOfUsers = new ArrayList<>();
		listOfUsers.add(u1);
		listOfUsers.add(u2);
		listOfUsers.add(u3);
		listOfUsers.add(u4);
		listOfUsers.add(u5);
		listOfUsers.add(u6);
		listOfUsers.add(u7);
		
		assertEquals("Grab all users", listOfUsers, es.getAllUsers());
	}
	
	@Test
	public void getUserByIdServiceTest() {
		
		assertEquals("Charlotte Bronte", u6, es.getUserById(6));
		assertEquals("Jane Doe", u3, es.getUserById(3));
		assertEquals("fake user", null, es.getUserById(8));
		assertEquals("null user", null, es.getUserById(0));
	}
	
	public void getUserByUsernameServiceTest() {
		
		assertEquals("Charlotte Bronte employee", u6, es.getUserByUsername("charlottebronte"));
		assertEquals("Jane Doe FM", u3, es.getUserByUsername("janedoe"));
		assertEquals("fake user", null, es.getUserByUsername("bigdog"));
		assertEquals("null user", null, es.getUserByUsername(null));
	}
	
	@Test
	public void verifyLoginTest() {
		
		assertEquals("The user williamsmith has password4 and is a 1", 1, es.verifyLoginCredentials("williamsmith", "password4"));
		assertEquals("The user emilyblunt has password7 and is a 0", 0, es.verifyLoginCredentials("emilyblunt", "password7"));
		assertEquals("Expected failure username", -1, es.verifyLoginCredentials("person", "password4"));
		assertEquals("Expected failure password", -1, es.verifyLoginCredentials("charlottebronte", "password"));
	}
	

/////////////////////REIMBURSEMENT SERVICE TESTS
	
	@Test
	public void addReimbServiceTest() {
		
		assertTrue("normal reimbursement", rs.addReimbursement(u1, 32.43, "Stayed at friend's house", 1));
		assertFalse("null user reimb", rs.addReimbursement(null, 32.43, "Stayed at friend's house", 1));
		assertFalse("fake user reimb", rs.addReimbursement(fakeUser, 32.43, "Stayed at friend's house", 1));
		assertFalse("negative amount reimb", rs.addReimbursement(u1, -32.43, "Stayed at friend's house", 1));
		assertTrue("no description reimbursement", rs.addReimbursement(u1, 32.43, null, 1));
		assertFalse("not real type reimbursement", rs.addReimbursement(u1, 32.43, "Stayed at friend's house", -1));
	}

	@Test
	public void getReimbursementByIdServiceTest() {
		
		ERSReimbursement r7 = rd.getReimbursementById(7);

		
		assertEquals("normal reimbursement", r7, rs.getReimbursementById(7));
		assertEquals("user doesn't exist", null, rs.getReimbursementById(0));
	}
	
	@Test
	public void resolveReimbursementServiceTest() {
		
		ERSReimbursement r7 = rd.getReimbursementById(7);
		ERSReimbursement r8 = rd.getReimbursementById(8);
		ERSReimbursement r9 = rd.getReimbursementById(9);
		ERSReimbursement r10 = rd.getReimbursementById(10);
		ERSReimbursement r11 = rd.getReimbursementById(11);
		ERSReimbursement r12 = rd.getReimbursementById(12);
		ERSReimbursement fakereimb = rd.getReimbursementById(32);
		
		assertTrue("normal reimbursement", rs.resolveReimbursement(u3, 1, r7));
		assertFalse("null resolver user", rs.resolveReimbursement(null, 1, r8));
		assertFalse("fake reimbursement", rs.resolveReimbursement(u3, 1, fakereimb));
		assertFalse("not an FM user", rs.resolveReimbursement(u1, 1, r9));
		
	}
	
	@Test
	public void getReimbByIdServiceTest() {
		
		ERSReimbursement r7 = rd.getReimbursementById(7);
		
		assertEquals("normal reimb", r7, rs.getReimbursementById(7));
		assertEquals("id negative", null, rs.getReimbursementById(-1));
	}
	
	@Test
	public void mainTest() {
		System.out.println(ud.getAllUsers());
		
	}

}
