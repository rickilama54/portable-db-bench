package com.eris4.benchdb.test.account.domain;

public class AccountImpl implements Account{
	
	private int accountId;
	private double balance;

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public int getAccountId() {
		return accountId;
	}

	public double getBalance() {
		return balance;
	}

	public AccountImpl(){}
	
	public AccountImpl(int accountId,double balance){
		this.accountId = accountId;
		this.balance = balance;
	}


}
