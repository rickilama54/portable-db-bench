package com.eris4.benchdb.database.prevayler.msisdn;

import java.io.Serializable;

import com.eris4.benchdb.test.msisdn.domain.Msisdn;

public class MsisdnPrevayler implements Msisdn,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8767274726266938014L;
	private int accountId;
	private int msisdnId;

	
	public int getAccountId() {
		return accountId;
	}

	
	public int getMsisdnId() {
		return msisdnId;
	}

	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	
	public void setMsisdnId(int msisdnId) {
		this.msisdnId = msisdnId;
	}

}
