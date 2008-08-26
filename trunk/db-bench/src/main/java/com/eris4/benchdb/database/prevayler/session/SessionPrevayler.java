package com.eris4.benchdb.database.prevayler.session;

import java.io.Serializable;

import com.eris4.benchdb.test.session.domain.Session;

public class SessionPrevayler implements Session,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1450391307721378446L;
	private int accountId;
	private int sessionId;
	private long startTime;

	@Override
	public int getAccountId() {
		return accountId;
	}

	@Override
	public int getSessionId() {
		return sessionId;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public void setAccountId(int accountId) {	
		this.accountId = accountId;
	}

	@Override
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}
