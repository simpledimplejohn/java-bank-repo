package com.revature.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;

public class UserService {
	private Logger logger = Logger.getLogger(UserService.class);
	// inject DAO object into this class so that it can use its methods
	// Dependency injection

//=======================================================
// make this public for testing purposes
	public IUserDAO udao = new UserDAO(); //dependency injection
//=============================================================
	
	public User register(User u) {
		// check what we get from the DB and cleans it up
		// if there is an id, means user exists
		if (u.getId() != 0) {
			throw new RegisterUserFailedException("User not valid to register, Id was " + u.getId() + " and not zero"); // make a custom exception
		}
		
		// if the ID is 0 then we can make a new user
		// this is how we use the methods
		// insert() is what makes the database call
		int generatedId = udao.insert(u); // returns PK to user
		
		// is the PK -1?
		if(generatedId != -1 && generatedId != u.getId()) { 
		// set the user id to the id generated by the database
			u.setId(generatedId);
		} else {
			throw new RegisterUserFailedException("User's Id was either -1 or did not change after insertion into DB"); //CHANGE THIS LATER TO CUSTOM EXCPETION
		}
		
		logger.info("Sucessfully registered the user with the id of " + u.getId());
		
		return u;
		
	}
	// returns a user
	public User login(String username, String password) {
		// call DAO find by username
		User returnedUser = udao.findByUserName(username);
		if (returnedUser.getPwd() == password) {
			System.out.println("Successfully logged in!");
			return returnedUser;
		} 
		
		return null;
	}
	
	public void listAllAccounts() {
		// return all the user accounts that exit in the database
		// you a forEach
		List<User> users = udao.findAll();
		
		users.forEach(u -> System.out.println(u));
		// could also be a enhaced for loop
	}
	
	public boolean userExists(String username) {
		if(udao.findByUserName(username) != null) {
			return true;
		}
		return false;
	}
	

}
