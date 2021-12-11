package com.revature.dao;

import java.sql.Connection; //has getConnection()
import java.sql.PreparedStatement; // has conn.prepareStatment(sql)
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

/**
 * Instantiated in the service layer above user layer DB operations regading
 * User objects
 * 
 * This will incorporate all persistence logic
 * 
 * @author JohnBlalock
 *
 */

public class UserDAO implements IUserDAO {

	private static Logger logger = Logger.getLogger(UserDAO.class);

	@Override
	public int insert(User u) {

		try {

			// 1. capture a DB connection by calling ConnectionUtil getConnection()
			Connection conn = ConnectionUtil.getConnection();
			// 2. Generate a SQL statment like INSERT INTO users (user)
			String sql = "INSERT INTO johnmb.users (username, pwd, user_role) VALUES (?, ?, ?) RETURNING users.id";
			// 2b use a prepared statment to avoid SAL injection
			PreparedStatement stmt = conn.prepareStatement(sql);
			// 2c set the values through a complex step
			stmt.setString(1, u.getUsername()); // 1 points to the ? and sets it
			stmt.setString(2, u.getPwd()); 
			// transpose java enum
			stmt.setObject(3, u.getRole(), Types.OTHER);
			// Use a ResultSet to extract the Priary Key from the user that wsa
			// created/persisted
			ResultSet rs; // alows us to iterate over returned data
			if ((rs = stmt.executeQuery()) != null) {
				// if we return data, we can itterate over it with a cursor
				rs.next();
				// capture the first column of data returned
				int id = rs.getInt(1); // int of column #1
				logger.info("return User id #" + id);

				return id;
			}
		} catch (SQLException e) {
			logger.error("Unable to insert User - SQL exception found");
			e.printStackTrace();
		}

		// -1 in uncessful otherwise return PK

		return -1;

	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUserName(String username) {
		// 
		User u = new User();
		try(Connection conn = ConnectionUtil.getConnection()) {
			/////// SETUP SQL STATMENT
			String sql = "SELECT * FROM users WHERE username = ?"; // this is built into the JVDC
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username); // sets the ? to username
			////// DATABASE CALL HERE
			ResultSet rs; 
			if((rs = stmt.executeQuery()) != null) {
				// GRABS FROM EACH ROW OF THE DB
				// debuggin the result set
//				ResultSetMetaData rsmd = rs.getMetaData();
//				int columnsNumber = rsmd.getColumnCount();
//				while (rs.next()) {
//					for(int i =1; i <= columnsNumber; i++) {
//						if (i>1) System.out.println(", ");
//						String columnValue = rs.getString(i);
//						System.out.println(columnValue + " " + rsmd.getColumnName(i));
//					}
//				}
//				
				
//				
//				System.out.println("this is result set");
//				
//				System.out.println(rs.toString());
				//rs.next();
				
				if(rs.next()) {
					int id = rs.getInt("id");
				
				
				
		///// THESE HAVE TO MATCH THE OBJECT
				String returnedUsername = rs.getString("username");
				String password = rs.getString("pwd");
				Role role = Role.valueOf(rs.getString("user_role"));
			// not returning accounts, just a user
				u.setId(id);
				u.setUsername(returnedUsername);
				u.setPwd(password);
				u.setRole(role);
				}	
			} else {
				return u;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("SQL exception throw - can't retreive user from the DB");
			e.printStackTrace(); // trying to access a column that does not exist
		}
		return u;
	}

////////////// how do we get the users and their accounts joined??///////////////
	@Override
	public List<User> findAll() {
		// to do this we will need to return all the accounts as well
		// TODO Auto-generated method stub
		
		// empty list to store user objects
		List<User> allUsers = new LinkedList<User>();
		// try/catch with connection
		try (Connection conn = ConnectionUtil.getConnection()) {
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT users.id, users.username, users.pwd, users.user_role, accounts.id AS account_id, accounts.balance, \r\n"
					+ "accounts.active \r\n"
					+ "	FROM johnmb.users\r\n"
					+ "	LEFT JOIN johnmb.users_accounts_jt ON users.id = users_accounts_jt.acc_owner\r\n"
					+ "	LEFT JOIN johnmb.accounts ON accounts.id = users_accounts_jt.account;	\r\n";
			// grab user data from each row
			ResultSet rs = stmt.executeQuery(sql);
			// iterate through the table of data returned
			
			while (rs.next()) { //ResultSet gives us .next() 
			// grab id, username, password, role	
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("pwd");
				// convert the role into a java enum
				Role role = Role.valueOf(rs.getString("user_role")); //convert to enum
				
				int accId = rs.getInt("account_id");
				double balance = rs.getDouble("balance");
				boolean isActive = rs.getBoolean("active");
				
				// if accId = 0 that means user does not have an account
				if(accId == 0) {
					// if user has no account then add a new user with an empty account list
					
					allUsers.add(new User(id, username, password, role, new LinkedList<Account>()));
				} else {
					// if user has an account then make the following
					// id represents the users Id
					int ownerId = id;
					Account a = new Account(accId, balance, ownerId, isActive);
					
					// making sure there is not a repeate of the owner id
					// this is a stream allows us to perform extra function
					List<User> potentialOwners = allUsers.stream()
							// there will be two accounts with the same owner id
							// filtering them to make one id
							.filter((u) -> u.getId() == ownerId) 
							.collect(Collectors.toList());
					if (potentialOwners.isEmpty()) {
						List<Account> ownedAccounts = new LinkedList<Account>();
						ownedAccounts.add(a);
						
						User u = new User(ownerId, username, password, role, ownedAccounts);
						allUsers.add(u);
					} else {
						// if the owner already exists
						// get the first element
						User u = potentialOwners.get(0);
						// this is the logic that enables us to capture multiple accounts that belong to one person
						List<Account> ownedAccs = u.getAccounts();
						ownedAccs.add(a);
						u.setAccounts(ownedAccs);
						
					}
				}
				
			}
			// grab account data
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("SQL exception throw - can't retreive all user from the DB");
			e.printStackTrace(); // trying to access a column that does not exist
		}
		return allUsers;
	}

	
	@Override
	public boolean update(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
