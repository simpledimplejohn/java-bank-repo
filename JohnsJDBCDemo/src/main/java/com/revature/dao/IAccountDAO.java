package com.revature.dao;

import java.util.List;

import com.revature.models.Account;

public interface IAccountDAO {
	// CRUD methods for the account
	
	// insert
	int insert(Account a);
	
	// 3 read methods
	List<Account> findAll();
	Account findById(int id);
	List<Account> findByOwner(int accOwnerId);
	// checking account extends account so it is a type of account
	// Listkov substitution 
	
	// update
	boolean update(Account a);
	boolean delete(int id);
	
}
