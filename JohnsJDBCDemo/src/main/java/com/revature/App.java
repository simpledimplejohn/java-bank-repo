package com.revature;

import java.util.LinkedList;
import java.util.Scanner;

import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.service.AccountService;
import com.revature.service.UserService;

public class App {
	
	static Scanner scan = new Scanner(System.in);
	// ENTRY POINT of the application
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//run();
		UserService userv = new UserService();
		UserDAO newUserDAO = new UserDAO();
		
		
		User u = newUserDAO.findByUserName("John");
		//System.out.println(u.getUsername());
		System.out.println("should be null!!" +u);
		//System.out.println(newUserDAO.findByUserName("fdhsaklgdsfds"));
		
		if(u.getUsername() == null) {
			System.out.println("user does not exist");
		} else {
			System.out.println("user does esist");
		}
		
		
	}
	
	public static void run() {
	///////////USE INPUT VALIDATION HERE //////////////////////////////////////	
		UserService userv = new UserService();
		AccountService aserv = new AccountService();	
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome!");
		System.out.println("If you would like to login press 1\nIf you are a new Customer press 2");
		int input = 1;//scan.nextInt();
		
		if (input == 1) {
			//call login method
			//username and password
			//test these against the database
			System.out.println("Please enter your username");
			String username = scan.next();
			System.out.println("Please enter your password");
			String password = scan.next();
			
			User LoggedInUser = userv.login(username, password);
	/////////////now more logic for user
			System.out.println("Would you like to view all your accounts?");
		} else if (input == 2) {
			System.out.println("Enger username you would like to sign up with");
			String username = scan.next();
			String password = scan.next();
			User u = new User(0, username, password, Role.Customer, new LinkedList<Account>());
			userv.register(u);
		}
		
		
		
		
		
		
		
		
		
//		System.out.println("Welcome to the bank");
//		
//		System.out.println("Press 1 if you would like to register");
//		
//		int input = scan.nextInt();
//		
//		if (input == 1) {
//			System.out.println("Enter your user name: ");
//			String username = scan.next();
//			// is there are user name by that name??  Check first or check in sql
//			User u = new User(username, "secretPass", Role.Customer, null);
//			UserService uservice = new UserService();
//			uservice.register(u);
//		}
		
		
	}

}
