package com.eris4.benchdb.database.pico4.session;

import java.io.IOException;

import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.pico4.PICO4PersistentObject;
import com.eris4.pico4.PICO4Serializable;
import com.eris4.pico4.streams.PICO4InputStream;
import com.eris4.pico4.streams.PICO4OutputStream;

public class SessionPico4Impl implements Session, PICO4PersistentObject {
	
	private int accountId;
	private int sessionId;
	private int vers = 0;
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

	@Override
	public void reload(PICO4Serializable persistentObject) {
		if (persistentObject instanceof SessionPico4Impl) {
			SessionPico4Impl session = (SessionPico4Impl) persistentObject;
			if (session.vers > vers){
				vers = session.vers;
				accountId = session.accountId;
				sessionId = session.sessionId;
				startTime = session.startTime;
			}
		}
	}

	@Override
	public void read(PICO4InputStream reader) throws IOException {
		try {
			vers = reader.readInt();
			accountId = reader.readInt();
			sessionId = reader.readInt();
			startTime = reader.readLong();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(PICO4OutputStream writer) throws IOException {
		vers++;
		writer.writeInt(vers);
		writer.writeInt(accountId);
		writer.writeInt(sessionId);
		writer.writeLong(startTime);
	}

}
