package com.eris4.benchdb.test.msisdn.domain;

public class MsisdnImpl implements Msisdn {
	
	private int msisdnId;
	private int accountId;
	
	public int getMsisdnId() {
		return msisdnId;
	}
	
	public void setMsisdnId(int msisdnId) {
		this.msisdnId = msisdnId;
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}	

}
