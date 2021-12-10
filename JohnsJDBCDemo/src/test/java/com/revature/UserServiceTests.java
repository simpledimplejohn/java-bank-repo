package com.revature;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.UserDAO;
import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.service.UserService;

public class UserServiceTests {

	// declare a variable to point ot the class to be tested
	private UserService userv;
	// declare dependency of the class to be tested
	private UserDAO mockDao; //must change the user DAO to public
	// a private dummy user
	private User dummyUser; //a private user
	// test an exception, if a registured user had an id > 0
	
	@Before // before every test
	public void setup() {
		// make a new object from UserService
		userv = new UserService();
		// this comes from mokito
		mockDao = mock(UserDAO.class);  //copys the functions of the class
		// so instead of returning from the DB
		// we can give it dummy data
		userv.udao = mockDao; //set the UserService instance's DAO property to equal the mock Dao
		
		// set up a stub
		dummyUser = new User();
		dummyUser.setAccounts(new LinkedList<Account>());
		dummyUser.setId(0); // pretending this user was generated from the consolue
	}
	@After
	public void teardown() {
		userv = null;
		dummyUser = null;
		mockDao = null;
	}
	
	@Test //making sure that the id is properly generated
	public void testRegisterUser_returnsNewPkAsId() {
		dummyUser = new User(0, "spongebob", "pass", Role.Admin, new LinkedList<Account>());
		// not connecting to DB, so faking PK
		Random r = new Random();
		int fakePk = r.nextInt(100);
		
		// trick our app
		
		//when the mockDao.insert method is called
		// then return the fakePk
		// intercepting the call to the database
		when(mockDao.insert(dummyUser)).thenReturn(fakePk);  
		
		// the returned user of our registerd method SHOULD have the id that's returned by the insert(0 method on the dao)
		User registeredUser = userv.register(dummyUser);
		// assertequals: is the ID of the registered user equal to the fakePk that we generated?
		assertEquals(registeredUser.getId(), fakePk);
	}
	
	///////////////throwing error/////////////////////////
	@Test(expected=RegisterUserFailedException.class) // means its what we expect
	public void testRegisterUser_isGreaterThanZero_throwsExcpetion() {
		
		// create a User obj

		dummyUser.setId(0); //Stub force its Id to 1
		// call the service layer and run it
		System.out.println(dummyUser);
		userv.register(dummyUser); //should thrown an exception
	}
	
	
	
	
	
}
