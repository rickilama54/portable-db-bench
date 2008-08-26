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

	@Override
	public int getAccountId() {
		return accountId;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
