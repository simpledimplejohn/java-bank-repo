package com.revature.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class User implements Serializable{
	
	// will be generated for us
	private int id; //in our db this is a SERIAL primary ke
	
	private String username;
	private String pwd;
	private Role role; //correlate to the ENUM in the db
	
	// data structure to hold accounts
	private List<Account> accounts;	
	
	
	// BEAN STUFF=======================
	// constructs
	public User() {};
	// fully paramterized constructor allows us to get the id
	public User(int id, String username, String pwd, Role role, List<Account> accounts) {
		super();
		this.id = id;
		this.username = username;
		this.pwd = pwd;
		this.role = role;
		this.accounts = accounts;
	}
	// fully paramaterized except for the id for sending to the database
	public User(String username, String pwd, Role role, List<Account> accounts) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.role = role;
		this.accounts = accounts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accounts, id, pwd, role, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(accounts, other.accounts) && id == other.id && Objects.equals(pwd, other.pwd)
				&& role == other.role && Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", pwd=" + pwd + ", role=" + role + ", accounts="
				+ accounts + "]";
	}
	
	// getters setters
	
	// hascode equals
}
