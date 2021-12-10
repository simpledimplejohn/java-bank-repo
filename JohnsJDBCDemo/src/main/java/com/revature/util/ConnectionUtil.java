package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/*
 * JDBC (java database connectivity) and API included in the JRE
 */

public class ConnectionUtil {
	
	// instantiate a Logger and bind to the class
	private static Logger logger = Logger.getLogger(ConnectionUtil.class);
	
	private static Connection conn = null;
	// Singleton class
	// ONLY ONE INSTANCE OF THE CLASS
	// CANNOT INSTANTIATE
	// INSTANCE = CONNECTION to the database
	// Call the getInstance() method... 
	
	// 1. Constructor is private
	private ConnectionUtil() {
		super();
	}
	
	/*
	 * comes from a Java SQL file
	 * if it exists return the instance
	 * if not create it (instantiate)
	 * 
	 */
	
	public static Connection getConnection() {
		
		// Check if connection exists
		
		try {
			if(conn != null && !conn.isClosed()) {
			
			return conn;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// how to read the properties file
		Properties prop = new Properties();
		
		String url = ""; // retreaving the value of the url key
		String username = "";
		String password = "";
		
		// pass in url, username, password
		try {
			// if we can locate the file--then we can go forward
			prop.load(new FileReader("C:\\Users\\Niles\\Desktop\\working-repo-0\\JohnsJDBCDemo\\src\\main\\resources\\application.properties"));
			
			url = prop.getProperty("url"); // retreaving the value of the url key
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			
			
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("You established a connection!!!");
			
			logger.info("DB connection established");
			
			/*
			 * when the getConnection() is called
			 * the DriverManager will attempt to locate a driver
			 * looks for the postgress dependency in the POM file
			 */
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("SQL Exception thrown - Cannot establish DB connection");
			
			
			System.out.println("cannot establish DB connection");
	
			
			e.printStackTrace();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Cannot locate application.properties file");
			System.out.println("Cannot locate application.properties file");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("app.props does not work");
			System.out.println("app.props file does not work");
			e.printStackTrace();
		}
		return conn; // returns if connection established
		
	}
	
}