package com.eris4.benchdb.test.session.domain;

public interface Session {

	public abstract int getSessionId();

	public abstract void setSessionId(int sessionId);

	public abstract int getAccountId();

	public abstract void setAccountId(int accountId);

	public abstract long getStartTime();

	public abstract void setStartTime(long startTime);

}