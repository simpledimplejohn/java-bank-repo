package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface IUserDAO {
	
	/*
	 * The DAO (DATA LAYER)
	 * which outlines the CRUD methods we will be using our DAO concrette class to perform on our database
	 * CRUD
	 */
	
	// insert a user object and return primary key
	int insert(User u); 
	
	
	// Read: return 1, return all
	User findById(int id); // return that user object with primary key
	User findByUserName(String username); 
	List<User> findAll(); // returns a list of all user objects in DB
	
	// Update: 
	boolean update(User u); // update, if it worked reutrn true
	
	// Delete:
	boolean delete(int id);
	
	
}
