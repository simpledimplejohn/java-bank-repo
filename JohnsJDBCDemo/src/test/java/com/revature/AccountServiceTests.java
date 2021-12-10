package com.revature;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.AccountDAO;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.service.AccountService;


public class AccountServiceTests {
	
	private AccountService aservice;
	
	private AccountDAO mockdao;
	
	@Before
	public void setup() {
		
		aservice = new AccountService(); //makes a new object to use the methods from here
		
		mockdao = mock(AccountDAO.class);  //would call the DB now we enter the data
	
		aservice.adao = mockdao; 
	}
	
	@After
	public void teardown() {
		aservice = null;
		mockdao = null;
	}
	
	
	
	@Test
	public void testFindByOwnerId_returnAccountList() {
		
		// create a new list of accounts to be in possession of a new user that we create
		List<Account> bobsAccounts = new LinkedList<Account>();
		
		Account a = new Account(1, 500, false);
		Account b = new Account(2, 600, true);
		bobsAccounts.add(a);
		bobsAccounts.add(b);
		
		User bob = new User(4, "bob", "secretpass", Role.Customer, bobsAccounts);
		
		// when we call our mockdao findByOwner (bobsid...) it returns the list of bobs accounts
		
		int bobsId = bob.getId();
		
		
		when(mockdao.findByOwner(bobsId)).thenReturn(bobsAccounts);
		

		
		// we have toi go to the Account Service and write this method ourselves
		List<Account> returnedAccounts = aservice.findByOwner(bobsId);
		
		System.out.println("when we call AccountDAO.findByOnwer(bob.getId()) the return should be: ");
		System.out.println(returnedAccounts);
		System.out.println("when we call just bobsAccounts we get ");
		System.out.println(bobsAccounts);
		
		// expected, actual
		assertEquals(bobsAccounts, returnedAccounts);		
	}
	
}