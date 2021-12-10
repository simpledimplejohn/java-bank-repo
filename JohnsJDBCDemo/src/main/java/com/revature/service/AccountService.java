package com.revature.service;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {
	// inject the AccountDAO object on which this class depends
	Scanner scan = new Scanner(System.in);
	
	public IAccountDAO adao = new AccountDAO(); //temporary change to public for testing
	
	public Account openAccount(User u) {
		
		// get user data
		System.out.println("Enter your initial deposite: ");
		double deposite = scan.nextDouble();
	
		
		// caputre that value
		// instantiate account with inital deposite
		Account a = new Account(0, deposite, u.getId(), true);
		// call the insert methoda and return the PK
		int pk = adao.insert(a);
		// set that accounts id = to the PK
		a.setId(pk);
		// return the account
		
		return a;
	}
	
	public void viewAllAcountsByOwner(int accOwnderId) {
		for (Account a : adao.findByOwner(accOwnderId)) {
			
			System.out.println(a);
		}
		
	}
	
	public List<Account> findByOwner(int userId) {
		return adao.findByOwner(userId); // calls from the AccountDAO findByOnwer
	}
	
	
	
}
