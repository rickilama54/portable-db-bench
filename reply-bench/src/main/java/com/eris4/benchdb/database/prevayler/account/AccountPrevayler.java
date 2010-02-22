package com.eris4.benchdb.database.prevayler.account;

import java.io.Serializable;

import com.eris4.benchdb.test.account.domain.Account;

public class AccountPrevayler implements Account, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5087275466259603311L;
	private int accountId;
	private double balance;

	
	public int getAccountId() {
		return accountId;
	}

	
	public double getBalance() {
		return balance;
	}

	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
