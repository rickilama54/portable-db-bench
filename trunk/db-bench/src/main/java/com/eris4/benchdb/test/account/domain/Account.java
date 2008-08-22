package com.eris4.benchdb.test.account.domain;

public interface Account {

	public abstract void setAccountId(int accountId);

	public abstract void setBalance(double balance);

	public abstract int getAccountId();

	public abstract double getBalance();

}