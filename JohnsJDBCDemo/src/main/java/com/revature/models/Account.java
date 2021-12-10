package com.revature.models;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable{
	// instance variable names do not need to match DB names
	private int id;
	private double balance;
	private int accOwnerId;
	private boolean active;
	
	public Account() {}
	
	// with ID
	public Account(int id, double balance, int accOwnerId, boolean active) {
		super();
		this.id = id;
		this.balance = balance;
		this.accOwnerId = accOwnerId;
		this.active = active;
	}
	// without ID
	public Account(double balance, int accOwnerId, boolean active) {
		super();
		this.balance = balance;
		this.accOwnerId = accOwnerId;
		this.active = active;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getAccOwnerId() {
		return accOwnerId;
	}

	public void setAccOwnerId(int accOwnerId) {
		this.accOwnerId = accOwnerId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accOwnerId, active, balance, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return accOwnerId == other.accOwnerId && active == other.active
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance) && id == other.id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", accOwnerId=" + accOwnerId + ", active=" + active + "]";
	}
	
	
	
}
